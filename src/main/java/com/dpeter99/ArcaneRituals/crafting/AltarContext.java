package com.dpeter99.ArcaneRituals.crafting;

import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import java.util.List;

///This contains the context for the altar crafting
///The subtypes Fluid and Item should be used to specify the arcane fuel available
public abstract class AltarContext extends RecipeWrapper {

    String altar_type;


    public AltarContext(IItemHandlerModifiable inv, String altar_type) {
        super(inv);
        this.altar_type = altar_type;
    }

    public abstract boolean match(IArcaneFuel fuel);
}
