package com.dpeter99.ArcaneRituals.arcaneFuel;

import com.dpeter99.ArcaneRituals.crafting.AltarContext;
import com.dpeter99.ArcaneRituals.crafting.AltarContextFluid;
import com.google.gson.JsonObject;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.stream.Stream;

//Ingredient for testing a singe arcane fuel
public abstract class ArcaneFuelIngredient<T> {


    public static ArcaneFuelIngredient<?> deserialize(JsonObject jsonObject){
        //Get the "type" from the JsonObject
        String type = jsonObject.get("type").getAsString();

        //Find the type in the ArcaneFuelEntry registry
        ArcaneFuelType fuelType = GameRegistry.findRegistry(ArcaneFuelType.class).getValue(ResourceLocation.tryCreate(type));

        //Load in ....
        ArcaneFuelIngredient<?> fuel = fuelType.parseIngredient(jsonObject);

        return fuel;
    }

    //The registered fuel type
    ArcaneFuelType fuel_type;

    //In the future it should support an array
    T matching;

    public ArcaneFuelIngredient(T matching) {
        this.matching = matching;
    }

    public abstract boolean test(AltarContext context);

    public abstract void write(PacketBuffer buffer);

    public static ArcaneFuelIngredient<?> read(PacketBuffer buffer) {

        String fuel_type = buffer.readString();

        ArcaneFuelType fuelType = GameRegistry.findRegistry(ArcaneFuelType.class).getValue(ResourceLocation.tryCreate(fuel_type));



    }

}
