package com.thegamefactory.theworldfactory.application;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import com.jme3.app.SimpleApplication;
import com.jme3.scene.Node;
import com.thegamefactory.theworldfactory.core.World;
import com.thegamefactory.theworldfactory.core.WorldModule;
import com.thegamefactory.theworldfactory.graphics.jme3.Jme3EntityListener;
import com.thegamefactory.theworldfactory.graphics.jme3.Jme3Module;

import java.awt.Point;

public class TwfApplication extends SimpleApplication {
    public static void main(final String[] args) {
        final TwfApplication app = new TwfApplication();
        app.start(); // start the game
    }

    private final Injector injector;
    private final World world;

    private TwfApplication() {
        final WorldModule worldModule = WorldModule.builder()
                .worldSize(new Point(5, 5))
                .build();

        injector = Guice.createInjector(worldModule, new Jme3Module(this));

        world = injector.getInstance(World.class);
    }

    @Override
    public void simpleInitApp() {
        final Node worldNode = injector.getInstance(Key.get(Node.class, Names.named("world")));
        rootNode.attachChild(worldNode);

        injector.getInstance(Jme3EntityListener.class);
    }

    @Override
    public void simpleUpdate(final float tpf) {
        world.update(tpf);
    }
}