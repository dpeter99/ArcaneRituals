package com.dpeter99.arcanerituals.registry;

import java.util.Objects;
import java.util.function.Supplier;

import com.dpeter99.arcanerituals.ArcaneRituals;
import com.dpeter99.arcanerituals.blocks.DemonicAltarBlock;
import com.dpeter99.arcanerituals.containers.AltarContainer;
import com.dpeter99.arcanerituals.fluids.Blood;
import com.dpeter99.arcanerituals.items.ItemSacrificialKnife;
import com.dpeter99.arcanerituals.items.ItemVial;
import com.dpeter99.arcanerituals.registry.mobblood.MobBlood;
import com.dpeter99.arcanerituals.tileentities.AltarTileEntity;
import com.dpeter99.bloodylib.ui.containers.SimpleContainer;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.*;

public class ARRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ArcaneRituals.MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ArcaneRituals.MODID);

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, ArcaneRituals.MODID);
    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, ArcaneRituals.MODID);

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, ArcaneRituals.MODID);




    public static final RegistryObject<Block> DEMONIC_ALTAR = BLOCKS.register("demonic_altar", ()-> new DemonicAltarBlock(AbstractBlock.Properties.of(Material.METAL)));
    public static final RegistryObject<TileEntityType<?>> DEMONIC_ALTAR_TE = TILE_ENTITIES.register("demonic_altar", () -> TileEntityType.Builder.of(AltarTileEntity::new, DEMONIC_ALTAR.get()).build(null));

    public static final RegistryObject<ContainerType<AltarContainer>> DEMONIC_ALTAR_CONTAINER = CONTAINER_TYPES.register("altar", () -> IForgeContainerType.create(AltarContainer::createClientContainer));

    public static final RegistryObject<BlockItem> DEMONIC_ALTAR_ITEM = ITEMS.register("demonic_altar", () -> new BlockItem(DEMONIC_ALTAR.get(), new Item.Properties().tab(ArcaneRituals.TAB)) );


    public static final RegistryObject<Blood> BLOOD = FLUIDS.register("blood", Blood::new);

    public static final RegistryObject<Item> VIAL = ITEMS.register("vial", () -> new ItemVial(new Item.Properties().tab(ArcaneRituals.TAB)));

    public static final RegistryObject<Item> SACRIFICIAL_KNIFE = ITEMS.register("sacrificial_knife",
            () -> new ItemSacrificialKnife(new Item.Properties().durability(55).tab(ArcaneRituals.TAB)));

    public static void initialize() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(modBus);
        BLOCKS.register(modBus);
        FLUIDS.register(modBus);

        TILE_ENTITIES.register(modBus);
        CONTAINER_TYPES.register(modBus);
    }

    public static <T extends IForgeRegistryEntry<?>> ResourceLocation getName(T type) {
        return Objects.requireNonNull(type.getRegistryName());
    }

    public static <T extends IForgeRegistryEntry<?>> ResourceLocation getName(Supplier<T> supplier) {
        return getName(supplier.get());
    }



}
