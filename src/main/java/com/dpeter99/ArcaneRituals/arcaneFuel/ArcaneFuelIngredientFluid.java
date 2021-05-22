package com.dpeter99.ArcaneRituals.arcaneFuel;

import com.dpeter99.ArcaneRituals.arcaneFuel.instance.ArcaneFuelFluidInstance;
import com.dpeter99.ArcaneRituals.arcaneFuel.instance.ArcaneFuelInstance;
//import com.dpeter99.ArcaneRituals.crafting.AltarContextFluid;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fluids.FluidStack;

public class ArcaneFuelIngredientFluid extends ArcaneFuelIngredient<FluidStack>{

    public ArcaneFuelIngredientFluid(FluidStack matching) {
        super(matching);
    }

    @Override
    public int getAmount() {
        return matching.getAmount();
    }

    @Override
    public boolean test(ArcaneFuelInstance context) {

        if(context instanceof ArcaneFuelFluidInstance){
            FluidStack ctx = ((ArcaneFuelFluidInstance)context).getFuel();

            return ctx.containsFluid(matching);
        }

        return false;
    }

    @Override
    public void writeData(PacketBuffer buffer) {

        buffer.writeFluidStack(matching);
    }

    @Override
    public void readData(PacketBuffer buffer) {

        matching = buffer.readFluidStack();
    }

}
