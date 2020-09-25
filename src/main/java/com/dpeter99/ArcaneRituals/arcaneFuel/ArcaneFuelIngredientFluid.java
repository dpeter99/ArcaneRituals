package com.dpeter99.ArcaneRituals.arcaneFuel;

import com.dpeter99.ArcaneRituals.crafting.AltarContext;
import com.dpeter99.ArcaneRituals.crafting.AltarContextFluid;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fluids.FluidStack;

import java.util.stream.Stream;

public class ArcaneFuelIngredientFluid extends ArcaneFuelIngredient<FluidStack>{

    public ArcaneFuelIngredientFluid(FluidStack matching) {
        super(matching);
    }

    @Override
    public boolean test(AltarContext context) {

        if(context instanceof AltarContextFluid){
            FluidStack ctx = ((AltarContextFluid)context).getFuel();

            boolean match = ctx.containsFluid(matching);
        }

        return false;
    }

    @Override
    public void write(PacketBuffer buffer) {
        buffer.writeString(fuel_type.getRegistryName().toString());
        buffer.writeFluidStack(matching);
    }

}
