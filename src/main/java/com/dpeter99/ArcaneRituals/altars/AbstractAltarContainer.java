package com.dpeter99.ArcaneRituals.altars;

import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;

import javax.annotation.Nullable;

public abstract class AbstractAltarContainer extends Container {

    protected AbstractAltarContainer(@Nullable ContainerType<?> type, int id) {
        super(type, id);
    }

}
