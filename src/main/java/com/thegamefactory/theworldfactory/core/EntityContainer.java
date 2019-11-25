package com.thegamefactory.theworldfactory.core;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class EntityContainer implements EntityEventProducer {
    private final Map<Integer, Entity> entities = new HashMap<>();
    private final List<EntityListener> listeners = new LinkedList<>();

    @Override
    public void registerListener(final EntityListener listener) {
        listeners.add(listener);
    }

    @Override
    public void deregisterListener(final EntityListener listener) {
        listeners.remove(listener);
    }

    void attachEntity(final Entity entity) {
        if (entities.containsKey(entity.getEntityId())) {
            throw new IllegalArgumentException("Duplicate entity id: " + entity.getEntityId());
        }
        entities.put(entity.getEntityId(), entity);
        listeners.forEach(l -> l.onEntityAttached(entity));
    }

    void detachEntity(final Entity entity) {
        if (!entities.containsKey(entity.getEntityId())) {
            throw new IllegalArgumentException("Entity is not attached: " + entity.getEntityId());
        }
        entities.remove(entity.getEntityId());
        listeners.forEach(l -> l.onEntityDetached(entity));

    }
}
