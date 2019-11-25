package com.thegamefactory.theworldfactory.graphics.jme3;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.thegamefactory.theworldfactory.core.Entity;
import com.thegamefactory.theworldfactory.core.EntityListener;
import lombok.Builder;

@Builder
public class Jme3EntityListener implements EntityListener {
    private final Node parent;
    private final AssetManager assetManager;
    private final Mesh[] meshes = new Mesh[]{
            new Box(0.5f, 0.5f, 0.5f),
            new Sphere(20, 20, 0.5f)
    };


    @Override
    public void onEntityAttached(final Entity entity) {
        final Mesh mesh = meshes[entity.getGraphicModelId() % meshes.length];
        final Geometry geom = new Geometry(geometryName(entity), mesh);
        geom.setLocalTranslation(
                entity.getPositionX(),
                entity.getPositionY(),
                0f);
        final Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.randomColor());
        geom.setMaterial(mat);
        parent.attachChild(geom);
    }

    @Override
    public void onEntityDetached(final Entity entity) {
        parent.detachChildNamed(geometryName(entity));
    }

    private String geometryName(final Entity entity) {
        return "entity" + entity.getEntityId();
    }
}
