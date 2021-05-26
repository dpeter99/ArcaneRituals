package com.dpeter99.bloodylib.fluid;

import net.minecraft.nbt.CompoundNBT;

public abstract class FluidData {

    public abstract CompoundNBT writeToNBT();
    public abstract void readFromNBT(CompoundNBT nbt);

}