package com.dpeter99.ArcaneRituals.arcane_fluid;

import net.minecraft.nbt.CompoundNBT;

import java.util.List;

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
     * Examples:
     * FluidContainer has 1000mB of A and 500mB of B is being add in a 2000mB container
     * The A fluid is completely converted to 1200mB of C
     * mixFluidsInContainer(A(1000), B(500), cont) -> [C(1200mB)]
     *
     * 1000mB of A and 500mB of B is being add in a 2000mB container
     * The A fluid is converted fully but some B fluid remains.
     * mixFluidsInContainer(A(1000), B(500), cont) -> [C(2000mB), B(250mB)]
     *
     * @param thisFluid the current fluidStack of this fluid, should be modified to contain the result of the mix
     * @param b
     * @param availableSpace
     * @return
     */
    abstract List<FluidStack> mixFluidsInContainer(FluidStack thisFluid, FluidStack b, FluidContainer container);

    abstract class FluidData{

        public abstract CompoundNBT writeToNBT();
        public abstract void readFromNBT();

    }

    class MixResult{
        FluidStack res;
        int usedAmount;
    }

}
