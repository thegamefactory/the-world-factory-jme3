package com.thegamefactory.theworldfactory.core.geo;

import com.thegamefactory.theworldfactory.core.ecs.Component;
import lombok.Data;

import java.awt.Point;

/**
 * A {@link Component} indicating the position of an entity.
 */
@Data
public final class PositionComponent implements Component {
    private final Point position;

    public int getPositionX() {
        return position.x;
    }

    public int getPositionY() {
        return position.y;
    }
}
