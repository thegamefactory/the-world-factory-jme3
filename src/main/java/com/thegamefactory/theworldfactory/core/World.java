package com.thegamefactory.theworldfactory.core;

import com.thegamefactory.theworldfactory.core.ecs.Component;
import com.thegamefactory.theworldfactory.core.ecs.ComponentLifecycleListener;
import com.thegamefactory.theworldfactory.core.ecs.Entity;
import com.thegamefactory.theworldfactory.core.ecs.EntityContainer;
import com.thegamefactory.theworldfactory.core.ecs.EntityEventsProducer;
import com.thegamefactory.theworldfactory.core.ecs.EntityFactory;
import com.thegamefactory.theworldfactory.core.ecs.EntityId;
import com.thegamefactory.theworldfactory.core.ecs.EntityLifecycleListener;
import com.thegamefactory.theworldfactory.core.geo.GeoMap;
import com.thegamefactory.theworldfactory.core.geo.PositionComponent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/**
 * Top class representing the world.
 */
@RequiredArgsConstructor
public class World implements EntityEventsProducer {
    @Getter
    private final Point size;
    private final Random random;

    private final GeoMap geoMap;

    private final EntityFactory entityFactory = new EntityFactory();
    private final EntityContainer entityContainer;
    private final ArrayList<EntityId> entitiesRandomlyCreated = new ArrayList<>();

    private float timeCounter = 0;

    World(final EntityContainer entityContainer, final Point size, final Random random) {
        this.entityContainer = entityContainer;
        this.size = size;
        this.random = random;

        geoMap = new GeoMap(size);
        entityContainer.registerComponentLifecycleListener(geoMap, PositionComponent.class);
    }

    public void update(final float tpf) {
        timeCounter += tpf;
        boolean create = false;
        boolean destroy = false;
        if (entitiesRandomlyCreated.size() < ((size.x * size.y) / 2)) {
            create = true;
        } else if (timeCounter > 1) {
            timeCounter -= 1;
            if (entitiesRandomlyCreated.size() < ((size.x * size.y)) && random.nextInt(2) % 2 == 0) {
                create = true;
            } else {
                destroy = true;
            }
        }

        if (create) {
            final Point pos = new Point();
            do {
                pos.x = random.nextInt(size.x);
                pos.y = random.nextInt(size.y);
            } while (geoMap.getEntity(pos).isPresent());
            final Entity newEntity = entityFactory.create();
            entityContainer.attachEntity(newEntity);
            entityContainer.attachComponent(newEntity.getEntityId(), new PositionComponent(pos));
            entitiesRandomlyCreated.add(newEntity.getEntityId());
        } else if (destroy) {
            final int removingEntityIndex = random.nextInt(entitiesRandomlyCreated.size());
            final EntityId removingEntityId = entitiesRandomlyCreated.remove(removingEntityIndex);
            entityContainer.detachEntity(removingEntityId);
        }
    }

    @Override
    public void registerEntityLifecycleListener(final EntityLifecycleListener listener) {
        entityContainer.registerEntityLifecycleListener(listener);
    }

    @Override
    public void deregisterEntityLifecycleListener(final EntityLifecycleListener listener) {
        entityContainer.deregisterEntityLifecycleListener(listener);
    }

    @Override
    public <T extends Component> void registerComponentLifecycleListener(final ComponentLifecycleListener<T> listener, final Class<T> component) {
        entityContainer.registerComponentLifecycleListener(listener, component);
    }

    @Override
    public <T extends Component> void deregisterComponentLifecycleListener(final ComponentLifecycleListener<T> listener, final Class<T> component) {
        entityContainer.deregisterComponentLifecycleListener(listener, component);
    }
}
