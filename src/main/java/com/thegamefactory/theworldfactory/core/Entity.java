package com.thegamefactory.theworldfactory.core;

import lombok.Builder;
import lombok.Data;

import java.awt.Point;

@Builder
@Data
public class Entity {
    private final int entityId;
    private final int graphicModelId;
    private final Point position;

    public int getPositionX() {
        return position.x;
    }

    public int getPositionY() {
        return position.y;
    }
}
