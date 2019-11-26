package com.thegamefactory.theworldfactory.graphics.jme3;

import com.jme3.scene.Spatial;
import com.thegamefactory.theworldfactory.core.ecs.Component;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * A {@link Component} for entities which need to be rendered.
 */
@Getter
@RequiredArgsConstructor
final class Jme3SpatialComponent implements Component {
    private final Spatial spatial;
}
