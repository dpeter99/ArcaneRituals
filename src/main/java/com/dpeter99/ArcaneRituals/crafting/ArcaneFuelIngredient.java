package com.dpeter99.ArcaneRituals.crafting;

import com.google.gson.JsonObject;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

//Ingredient for testing a singe arcane fuel
public class ArcaneFuelIngredient {


    public static ArcaneFuelIngredient deserialize(JsonObject jsonObject){
        //Get the "type" from the JsonObject
        String type = jsonObject.get("type").getAsString();

        //Find the type in the ArcaneFuelEntry registry
        ArcaneFuelType fuelType = GameRegistry.findRegistry(ArcaneFuelType.class).getValue(ResourceLocation.tryCreate(type));

        //Load in ....
        IArcaneFuel fuel = fuelType.parse(jsonObject);

        return new ArcaneFuelIngredient(fuel);
    }

    //In the future it should support an array
    IArcaneFuel matching;

    public ArcaneFuelIngredient(IArcaneFuel matching) {
        this.matching = matching;
    }

    public boolean test(AltarContext context){
        //Check if the supplied fuel is equivalent to the one we are holding



        return false;
    }

}
