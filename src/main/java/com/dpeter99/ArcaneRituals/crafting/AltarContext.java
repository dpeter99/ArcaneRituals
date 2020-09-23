package com.dpeter99.ArcaneRituals.crafting;

import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import java.util.List;

public abstract class AltarContext extends RecipeWrapper {


    public int fuel_amount;
    String altar_type;

    public AltarContext(IItemHandlerModifiable inv, int fuel_amount, String altar_type) {
        super(inv);
        this.fuel_amount = fuel_amount;
        this.altar_type = altar_type;
    }

    public abstract IArcaneFuel GetFuel();
}
