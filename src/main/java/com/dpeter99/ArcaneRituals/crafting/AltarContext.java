package com.dpeter99.ArcaneRituals.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import java.util.List;

public class AltarContext extends RecipeWrapper {
    List<ItemStack> items;
    String altar_type;

    public AltarContext(IItemHandlerModifiable inv) {
        super(inv);
    }
}
