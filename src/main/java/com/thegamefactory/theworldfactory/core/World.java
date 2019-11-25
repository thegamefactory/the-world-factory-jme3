package com.thegamefactory.theworldfactory.core;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

@RequiredArgsConstructor
public class World implements EntityEventProducer {
    @Getter
    private final Point size;
    private final Random random;

    private int entityId;

    private final GeoMap geoMap;
    private final EntityContainer entityContainer = new EntityContainer();
    private final ArrayList<Entity> entitiesRandomlyCreated = new ArrayList<>();

    private float timeCounter = 0;

    World(final Point size, final Random random) {
        this.size = size;
        this.random = random;

        geoMap = new GeoMap(size);
        entityContainer.registerListener(geoMap);
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
            final Entity newEntity = Entity.builder()
                    .entityId(entityId++)
                    .graphicModelId(random.nextInt(256))
                    .position(pos)
                    .build();
            entityContainer.attachEntity(newEntity);
            entitiesRandomlyCreated.add(newEntity);
        } else if (destroy) {
            final int removingEntityIndex = random.nextInt(entitiesRandomlyCreated.size());
            final Entity removingEntity = entitiesRandomlyCreated.remove(removingEntityIndex);
            entityContainer.detachEntity(removingEntity);
        }
    }

    @Override
    public void registerListener(final EntityListener listener) {
        entityContainer.registerListener(listener);
    }

    @Override
    public void deregisterListener(final EntityListener listener) {
        entityContainer.deregisterListener(listener);
    }
}
