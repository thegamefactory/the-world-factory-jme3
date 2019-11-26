package com.thegamefactory.theworldfactory.graphics.jme3;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.thegamefactory.theworldfactory.core.ecs.ComponentLifecycleListener;
import com.thegamefactory.theworldfactory.core.ecs.Entity;
import com.thegamefactory.theworldfactory.core.ecs.EntityContainer;
import com.thegamefactory.theworldfactory.core.geo.PositionComponent;
import lombok.Builder;

/**
 * A {@link ComponentLifecycleListener} that listens on the attachement and detachment of any entity with a {@link PositionComponent}; when it
 * happens, it attaches and detaches a corresponding {@link Jme3SpatialComponent} which is rendered on the screen.
 */
@Builder
public class Jme3ComponentLifecycleListener implements ComponentLifecycleListener<PositionComponent> {
    private final EntityContainer entityContainer;
    private final Node parent;
    private final AssetManager assetManager;
    private final Mesh[] meshes = new Mesh[]{
            new Box(0.5f, 0.5f, 0.5f),
            new Sphere(20, 20, 0.5f)
    };

    @Override
    public void onComponentAttached(final Entity entity, final PositionComponent positionComponent) {
        final Mesh mesh = meshes[entity.getEntityId().getId() % meshes.length];
        final Geometry geom = new Geometry(entity.getEntityId().toString(), mesh);
        geom.setLocalTranslation(
                positionComponent.getPositionX(),
                positionComponent.getPositionY(),
                0f);
        final Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.randomColor());
        geom.setMaterial(mat);

        parent.attachChild(geom);
        entityContainer.attachComponent(entity.getEntityId(), new Jme3SpatialComponent(geom));
    }

    @Override
    public void onComponentDetached(final Entity entity, final PositionComponent component) {
        final Jme3SpatialComponent jme3SpatialComponent = entityContainer.detachComponent(entity.getEntityId(), Jme3SpatialComponent.class);
        parent.detachChild(jme3SpatialComponent.getSpatial());
    }

    private String geometryName(final Entity entity) {
        return "entity" + entity.getEntityId();
    }
}
