package com.thegamefactory.theworldfactory.core;

import java.awt.Point;
import java.util.Optional;

public class GeoMap implements EntityListener {
    private final Point size;
    private final Entity[] entities;

    GeoMap(final Point size) {
        this.size = size;
        entities = new Entity[size.x * size.y];
    }

    Optional<Entity> getEntity(final Point position) {
        return Optional.ofNullable(entities[getIndex(position)]);
    }

    @Override
    public void onEntityAttached(final Entity entity) {
        final int index = getIndex(entity.getPosition());
        if (entities[index] == null) {
            entities[index] = entity;
        } else {
            throw new IllegalArgumentException("Already occupied, cannot attach: " + entity.getPosition());
        }
    }

    @Override
    public void onEntityDetached(final Entity entity) {
        final int index = getIndex(entity.getPosition());
        if (entities[index] != null) {
            entities[index] = null;
        } else {
            throw new IllegalArgumentException("Empty position, cannot detach: " + entity.getPosition());
        }
    }

    private int getIndex(final Point position) {
        return position.x * size.y + position.y;
    }
}
