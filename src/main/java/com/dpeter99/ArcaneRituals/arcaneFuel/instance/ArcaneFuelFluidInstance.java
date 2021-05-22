package com.dpeter99.ArcaneRituals.arcaneFuel.instance;

import com.dpeter99.ArcaneRituals.arcaneFuel.IArcaneFuel;
import com.dpeter99.ArcaneRituals.arcaneFuel.instance.ArcaneFuelInstance;
import net.minecraft.fluid.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class ArcaneFuelFluidInstance extends ArcaneFuelInstance {

    FluidStack stack;

    public ArcaneFuelFluidInstance(FluidStack fluid) {
        Fluid f = fluid.getFluid();
        if(f instanceof IArcaneFuel) {
            fuelType = (IArcaneFuel) f;
            stack = fluid;
        }

    }

    public FluidStack getFuel() {
        return stack;
    }
}
