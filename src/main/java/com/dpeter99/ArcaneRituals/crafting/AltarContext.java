package com.dpeter99.ArcaneRituals.crafting;

import com.dpeter99.ArcaneRituals.arcaneFuel.ArcaneFuelIngredient;
import com.dpeter99.ArcaneRituals.arcaneFuel.IArcaneFuel;
import com.dpeter99.ArcaneRituals.arcaneFuel.instance.ArcaneFuelFluidInstance;
import com.dpeter99.ArcaneRituals.arcaneFuel.instance.ArcaneFuelInstance;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.RecipeWrapper;

///This contains the context for the altar crafting
///The subtypes Fluid and Item should be used to specify the arcane fuel available
public class AltarContext extends RecipeWrapper {

    String altar_type;

    ArcaneFuelInstance fuel;


    public AltarContext(IItemHandlerModifiable inv, ArcaneFuelInstance fuel, String altar_type) {
        super(inv);
        this.altar_type = altar_type;
        this.fuel = fuel;
    }

    public ArcaneFuelInstance getFuel(){return fuel;};
}
