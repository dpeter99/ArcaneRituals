package com.dpeter99.ArcaneRituals.item;

import com.dpeter99.ArcaneRituals.Registries;
import com.dpeter99.ArcaneRituals.item.sacrificialKnife.ItemSacrificialKnife;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

public class ArcaneItems {

    public static final RegistryObject<Item> vial =
            Registries.ITEM_REGISTRY.register("vial",
                    () -> new ItemVial(new Item.Properties().group(Registries.group)));

    public static final RegistryObject<Item> bat_wing =
            Registries.ITEM_REGISTRY.register("bat_wing",
                    () -> new Item(new Item.Properties().group(Registries.group)));

    public static final RegistryObject<Item> hammer =
            Registries.ITEM_REGISTRY.register("hammer",
                    () -> new Item(new Item.Properties().group(Registries.group).maxStackSize(1)));

    public static final RegistryObject<Item> blood_hammer =
            Registries.ITEM_REGISTRY.register("blood_hammer",
                    () -> new Item(new Item.Properties().group(Registries.group).maxStackSize(1)));

    public static final RegistryObject<Item> iron_ring =
            Registries.ITEM_REGISTRY.register("iron_ring",
                    () -> new Item(new Item.Properties().group(Registries.group).maxStackSize(1)));

    public static final RegistryObject<Item> ring_of_protection =
            Registries.ITEM_REGISTRY.register("ring_of_protection",
                    () -> new ItemRingOfProtection(1));

    public static final RegistryObject<Item> sacrificial_knife =
            Registries.ITEM_REGISTRY.register("sacrificial_knife",
                    () -> new ItemSacrificialKnife(new Item.Properties().defaultMaxDamage(55)));
}
