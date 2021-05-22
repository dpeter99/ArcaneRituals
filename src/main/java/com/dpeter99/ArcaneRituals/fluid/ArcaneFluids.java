package com.dpeter99.ArcaneRituals.fluid;

import com.dpeter99.ArcaneRituals.Registries;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ObjectHolder;

//@ObjectHolder("arcanerituals")
public class ArcaneFluids {

    public static final RegistryObject<Blood> blood = Registries.FLUID_REGISTRY.register(
            "blood",
            () -> new Blood()
    );

}
