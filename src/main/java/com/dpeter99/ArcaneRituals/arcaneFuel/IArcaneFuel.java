package com.dpeter99.ArcaneRituals.arcaneFuel;

import com.google.gson.JsonObject;
import net.minecraft.util.ResourceLocation;

//Implemented by the Item / Fuel
public interface IArcaneFuel {

    ResourceLocation getResourceName();

    ArcaneFuelIngredient parseIngredient(JsonObject jsonObject);

}
