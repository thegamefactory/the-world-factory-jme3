package com.thegamefactory.theworldfactory.core.ecs;

import lombok.Data;

/**
 * An integer wrapper to increase entity id safety typing.
 * {@link EntityId}s should be unique.
 */
@Data
public class EntityId {
    final int id;

    EntityId(final int id) {
        this.id = id;
    }
}
