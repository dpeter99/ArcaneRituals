package com.dpeter99.ArcaneRituals.util.container;

import net.minecraft.inventory.container.ContainerType;

import javax.annotation.Nullable;

public abstract class TileEnityContainer extends SimpleContainer {

    protected TileEnityContainer(@Nullable ContainerType<?> type, int id) {
        super(type, id);
    }
}
