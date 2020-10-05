package com.dpeter99.ArcaneRituals.arcaneFuel;

import net.minecraft.fluid.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class ArcaneFuelProviderFluidCapability implements IArcaneFuelProvider {

    IFluidHandler fluidHandler;

    public ArcaneFuelProviderFluidCapability(IFluidHandler fluidHandler) {

        this.fluidHandler = fluidHandler;

    }

    @Override
    public int hasArcaneFuel(ArcaneFuelIngredient<?> type) {

        for (int i = 0; i < fluidHandler.getTanks(); i++) {
            Fluid fluid = fluidHandler.getFluidInTank(i).getFluid();

            if(fluid instanceof IArcaneFuel){
                if(type instanceof ArcaneFuelIngredientFluid)
                    
                if(){
                    return fluidHandler.getFluidInTank(i).getAmount();
                }

            }
        }

        return 0;
    }
}
