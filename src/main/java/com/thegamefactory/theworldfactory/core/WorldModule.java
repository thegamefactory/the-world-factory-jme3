package com.thegamefactory.theworldfactory.core;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import lombok.Builder;

import java.awt.Point;
import java.util.Random;

@Builder
public class WorldModule extends AbstractModule {
    private final Point worldSize;

    @Provides
    @Singleton
    public World world(final Random random) {
        return new World(worldSize, random);
    }

    @Provides
    @Singleton
    public Random random() {
        return new Random();
    }
}
