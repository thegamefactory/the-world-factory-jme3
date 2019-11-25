package com.thegamefactory.theworldfactory.core;

public interface EntityEventProducer {
    void registerListener(final EntityListener listener);

    void deregisterListener(final EntityListener listener);
}
