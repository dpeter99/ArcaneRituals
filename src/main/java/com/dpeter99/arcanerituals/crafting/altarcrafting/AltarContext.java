package com.dpeter99.arcanerituals.crafting.altarcrafting;

import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class AltarContext extends RecipeWrapper {

    String altar_type;

    //ArcaneFuelInstance fuel;


    public AltarContext(IItemHandlerModifiable inv, String altar_type) {
        super(inv);
        this.altar_type = altar_type;
        //this.fuel = fuel;
    }

    //public ArcaneFuelInstance getFuel(){return fuel;};
}