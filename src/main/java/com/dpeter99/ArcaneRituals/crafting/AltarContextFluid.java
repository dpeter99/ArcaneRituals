package com.dpeter99.ArcaneRituals.crafting;

import com.dpeter99.ArcaneRituals.arcaneFuel.ArcaneFuelIngredient;
import com.dpeter99.ArcaneRituals.arcaneFuel.ArcaneFuelIngredientFluid;
import com.dpeter99.ArcaneRituals.arcaneFuel.IArcaneFuelFluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandlerModifiable;

public class AltarContextFluid extends AltarContext<FluidStack> {

    FluidStack tank;

    public AltarContextFluid(IItemHandlerModifiable inv, FluidStack tank, String altar_type) {
        super(inv, altar_type);
        this.tank = tank;
    }

    @Override
    public FluidStack getFuel() {
        return tank;
    }
}
