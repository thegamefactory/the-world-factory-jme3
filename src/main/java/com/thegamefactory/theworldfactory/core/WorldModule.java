package com.thegamefactory.theworldfactory.core;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.thegamefactory.theworldfactory.core.ecs.EntityContainer;

import java.awt.Point;
import java.util.Random;

/**
 * Guice DI for {@link World}.
 */
public class WorldModule extends AbstractModule {
    private final Point worldSize = new Point(5, 5);

    @Provides
    @Singleton
    public World world(final EntityContainer entityContainer, final Random random) {
        return new World(entityContainer, worldSize, random);
    }

    @Provides
    @Singleton
    public EntityContainer entityContainer() {
        return new EntityContainer();
    }

    @Provides
    @Singleton
    public Random random() {
        return new Random();
    }
}
