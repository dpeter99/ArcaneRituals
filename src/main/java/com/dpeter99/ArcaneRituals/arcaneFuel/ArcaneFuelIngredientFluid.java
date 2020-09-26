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
    public int getAmount() {
        return matching.getAmount();
    }

    @Override
    public boolean test(AltarContext context) {

        if(context instanceof AltarContextFluid){
            FluidStack ctx = ((AltarContextFluid)context).getFuel();

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
