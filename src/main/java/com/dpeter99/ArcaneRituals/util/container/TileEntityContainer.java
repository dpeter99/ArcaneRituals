package com.dpeter99.ArcaneRituals.util.container;

import net.minecraft.inventory.container.ContainerType;

import javax.annotation.Nullable;

public abstract class TileEntityContainer extends SimpleContainer {

    protected TileEntityContainer(@Nullable ContainerType<?> type, int id) {
        super(type, id);
    }
}
