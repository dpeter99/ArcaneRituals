package com.dpeter99.ArcaneRituals.arcaneFuel;

import com.google.gson.JsonObject;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.function.Supplier;

public class ArcaneFuelType extends ForgeRegistryEntry<ArcaneFuelType> {

    Supplier<IArcaneFuel> fuel;

    public ArcaneFuelType(Supplier<IArcaneFuel> fuelSupplier) {
        this.fuel = fuelSupplier;
    }

    public ArcaneFuelIngredient parseIngredient(JsonObject jsonObject){
        return fuel.get().parseIngredient(jsonObject);
    }

    Class<? extends ArcaneFuelIngredient<?>> getIngredientType(){
        return fuel.get().getIngredientType();
    }


}
