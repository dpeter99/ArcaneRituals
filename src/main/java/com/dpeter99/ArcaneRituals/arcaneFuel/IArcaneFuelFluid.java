package com.dpeter99.ArcaneRituals.arcaneFuel;

import com.dpeter99.ArcaneRituals.arcaneFuel.IArcaneFuel;
import net.minecraftforge.fluids.FluidStack;

public interface IArcaneFuelFluid extends IArcaneFuel {

    boolean match(FluidStack a, FluidStack b);

}
