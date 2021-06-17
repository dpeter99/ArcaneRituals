package com.dpeter99.arcanerituals.crafting.altarcrafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class AltarContext extends RecipeWrapper {

    String altar_type;


    ItemStack fuelItem;
    FluidStack fuelFluid;

    public AltarContext(IItemHandlerModifiable inv, String altar_type, ItemStack fuel) {
        super(inv);
        this.altar_type = altar_type;
        this.fuelItem = fuel;
    }

    public AltarContext(IItemHandlerModifiable inv, String altar_type, FluidStack fuel) {
        super(inv);
        this.altar_type = altar_type;
        this.fuelFluid = fuel;
    }

    public Object getFuel() {
        if(fuelItem != null && !fuelItem.isEmpty())
            return fuelItem;
        if(!fuelFluid.isEmpty())
            return fuelFluid;

        return null;
    }

}