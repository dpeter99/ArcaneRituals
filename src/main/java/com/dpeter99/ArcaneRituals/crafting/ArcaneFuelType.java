package com.dpeter99.ArcaneRituals.crafting;

import com.google.gson.JsonObject;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.function.Supplier;

public class ArcaneFuelType extends ForgeRegistryEntry<ArcaneFuelType> {

    Supplier<IArcaneFuel> fuel;

    public ArcaneFuelType(Supplier<IArcaneFuel> fuelSupplier) {
        this.fuel = fuelSupplier;
    }

    public IArcaneFuel parse(JsonObject jsonObject){
        return fuel.get().parse(jsonObject);
    }
}
