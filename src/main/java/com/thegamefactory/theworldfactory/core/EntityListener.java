package com.thegamefactory.theworldfactory.core;

public interface EntityListener {
    void onEntityAttached(final Entity entity);

    void onEntityDetached(final Entity entity);
}
