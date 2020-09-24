package com.dpeter99.ArcaneRituals.crafting;

import net.minecraftforge.fluids.FluidStack;

public abstract class ArcaneFuelFluid implements IArcaneFuel {

    public abstract boolean match(FluidStack a, FluidStack b);

}
