package com.dpeter99.ArcaneRituals.arcane_fluid;

import net.minecraft.nbt.CompoundNBT;

public abstract class Fluid {



    /**
     * Used for adding default data to the FluidStack
     * (Could be used for example to store purity)
     *
     * @param s
     */
    abstract void setupFluidStack(FluidStack s);


    /**
     * Mixes 2 fluids in a container context.
     *
     *
     * @param thisFluid the current fluidStack of this fluid, should be modified to contain the result of the mix
     * @param b
     * @param availableSpace
     * @return
     */
    abstract int mixFluidsInContainer(FluidStack thisFluid, FluidStack b, int availableSpace);

    abstract class FluidData{

        public abstract CompoundNBT writeToNBT();
        public abstract void readFromNBT();

    }

    class MixResult{
        FluidStack res;
        int usedAmount;
    }

}
