package com.dpeter99.arcanerituals.registry;

import java.util.Objects;
import java.util.function.Supplier;

import com.dpeter99.arcanerituals.ArcaneRituals;
import com.dpeter99.arcanerituals.blocks.DemonicAltarBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class ARRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ArcaneRituals.MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ArcaneRituals.MODID);

    public static final DeferredRegister<TileEntityType<?>> TE_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, ArcaneRituals.MODID);
    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, ArcaneRituals.MODID);

    public static final RegistryObject<Block> DEMONIC_ALTAR = BLOCKS.register("demonic_altar", ()-> new DemonicAltarBlock(AbstractBlock.Properties.of(Material.METAL)));
    public static final RegistryObject<BlockItem> DEMONIC_ALTAR_ITEM = ITEMS.register("demonic_altar", () -> new BlockItem(DEMONIC_ALTAR.get(), new Item.Properties().tab(ArcaneRituals.TAB)) );



    public static void initialize() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(modBus);
        BLOCKS.register(modBus);
        TE_TYPES.register(modBus);
        CONTAINER_TYPES.register(modBus);
    }

    public static <T extends IForgeRegistryEntry<?>> ResourceLocation getName(T type) {
        return Objects.requireNonNull(type.getRegistryName());
    }

    public static <T extends IForgeRegistryEntry<?>> ResourceLocation getName(Supplier<T> supplier) {
        return getName(supplier.get());
    }

}
