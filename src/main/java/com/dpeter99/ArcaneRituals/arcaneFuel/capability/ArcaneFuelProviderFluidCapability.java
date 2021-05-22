package com.dpeter99.ArcaneRituals.arcaneFuel.capability;

import com.dpeter99.ArcaneRituals.arcaneFuel.ArcaneFuelIngredient;
import com.dpeter99.ArcaneRituals.arcaneFuel.ArcaneFuelIngredientFluid;
import com.dpeter99.ArcaneRituals.arcaneFuel.IArcaneFuel;
import com.dpeter99.ArcaneRituals.arcaneFuel.instance.ArcaneFuelFluidInstance;
import net.minecraft.fluid.Fluid;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class ArcaneFuelProviderFluidCapability implements IArcaneFuelProvider {

    IFluidHandler fluidHandler;

    public ArcaneFuelProviderFluidCapability(IFluidHandler fluidHandler) {

        this.fluidHandler = fluidHandler;

    }

    @Override
    public int canFulfill(ArcaneFuelIngredient<?> type) {

        //If it is not a fluid fuel than we for sure can't do it XD
        if(type instanceof ArcaneFuelIngredientFluid) {

            //Go through all tanks to find one that does satisfy the ingredient
            for (int i = 0; i < fluidHandler.getTanks(); i++) {
                Fluid fluid = fluidHandler.getFluidInTank(i).getFluid();

                if (fluid instanceof IArcaneFuel) {

                    if (type.test(new ArcaneFuelFluidInstance(fluidHandler.getFluidInTank(i)))) {
                        return fluidHandler.getFluidInTank(i).getAmount();
                    }

                }
            }

        }

        return 0;
    }
}
