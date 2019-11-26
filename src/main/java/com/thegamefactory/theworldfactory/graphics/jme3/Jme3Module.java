package com.thegamefactory.theworldfactory.graphics.jme3;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Quad;
import com.thegamefactory.theworldfactory.core.World;
import com.thegamefactory.theworldfactory.core.ecs.EntityContainer;
import com.thegamefactory.theworldfactory.core.geo.PositionComponent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Jme3Module extends AbstractModule {
    private final SimpleApplication simpleApplication;

    @Override
    public void configure() {
    }

    @Provides
    @Singleton
    @Named("world")
    public Node getWorld(@Named("terrain") final Spatial terrain) {
        final Node world = new Node();
        world.attachChild(terrain);
        return world;
    }

    @Provides
    @Singleton
    @Named("terrain")
    public Spatial getTerrain(final AssetManager assetManager, final World world) {
        final Quad terrainQuad = new Quad(world.getSize().x, world.getSize().y);
        final Geometry terrainGeom = new Geometry("terrain", terrainQuad);
        final Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Red);
        terrainGeom.setMaterial(mat);
        terrainGeom.setLocalTranslation(-0.5f, -0.5f, -0.5f);
        return terrainGeom;
    }

    @Provides
    @Singleton
    public Jme3ComponentLifecycleListener jme3EntityListener(
            final AssetManager assetManager,
            final EntityContainer entityContainer,
            @Named("world") final Node worldNode,
            final World world) {
        final Jme3ComponentLifecycleListener jme3ComponentLifecycleListener = Jme3ComponentLifecycleListener.builder()
                .assetManager(assetManager)
                .entityContainer(entityContainer)
                .parent(worldNode)
                .build();
        world.registerComponentLifecycleListener(jme3ComponentLifecycleListener, PositionComponent.class);
        return jme3ComponentLifecycleListener;
    }

    @Provides
    @Singleton
    public AssetManager assetManager() {
        return simpleApplication.getAssetManager();
    }

}
