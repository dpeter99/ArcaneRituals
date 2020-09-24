package com.dpeter99.ArcaneRituals.crafting;

import net.minecraft.fluid.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.items.IItemHandlerModifiable;

public class AltarContextFluid extends AltarContext {

    FluidStack tank;

    public AltarContextFluid(IItemHandlerModifiable inv, FluidStack tank, String altar_type) {
        super(inv, altar_type);
        this.tank = tank;
    }

    @Override
    public boolean match(ArcaneFuelIngredient fuel) {

        if(!(fuel instanceof ArcaneFuelFluid))
        {
            //We only have liquid fuel in the context :(
            return false;
        }
        else {
            return  ((ArcaneFuelFluid) fuel).match(tank);
        }
    }
}
