package com.dpeter99.bloodylib.fluid;

import net.minecraft.nbt.CompoundTag;

public abstract class FluidData {

    public abstract CompoundTag writeToNBT();
    public abstract void readFromNBT(CompoundTag nbt);

}