package com.dpeter99.ArcaneRituals.crafting;

import com.dpeter99.ArcaneRituals.arcaneFuel.ArcaneFuelIngredient;
import com.dpeter99.ArcaneRituals.arcaneFuel.IArcaneFuel;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.RecipeWrapper;

///This contains the context for the altar crafting
///The subtypes Fluid and Item should be used to specify the arcane fuel available
public abstract class AltarContext<T> extends RecipeWrapper {

    String altar_type;


    public AltarContext(IItemHandlerModifiable inv, String altar_type) {
        super(inv);
        this.altar_type = altar_type;
    }

    public abstract T getFuel();
}
