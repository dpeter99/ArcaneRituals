package com.dpeter99.arcanerituals.registry;

import com.dpeter99.arcanerituals.ArcaneRituals;
import com.dpeter99.arcanerituals.blocks.DemonicAltarBlock;

import com.dpeter99.arcanerituals.containers.AltarContainer;
import com.dpeter99.arcanerituals.crafting.altarcrafting.AltarRecipe;
import com.dpeter99.arcanerituals.crafting.altarcrafting.AltarRecipeSerializer;
import com.dpeter99.arcanerituals.fluids.Blood;
import com.dpeter99.arcanerituals.item.ItemSacrificialKnife;
import com.dpeter99.arcanerituals.items.ItemVial;
import com.dpeter99.arcanerituals.items.RingOfProtection;
import com.dpeter99.arcanerituals.tileentities.AltarTileEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Objects;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class ARRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ArcaneRituals.MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ArcaneRituals.MODID);

    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, ArcaneRituals.MODID);
    public static final DeferredRegister<MenuType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, ArcaneRituals.MODID);

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, ArcaneRituals.MODID);

    //public static final DeferredRegister<IRecipe<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registry.RECIPE_TYPE, ArcaneRituals.MODID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ArcaneRituals.MODID);






    public static final RegistryObject<AltarRecipeSerializer> ALTAR_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(AltarRecipe.RECIPE_TYPE_NAME, AltarRecipeSerializer::new);


    public static final RegistryObject<Block> DEMONIC_ALTAR = BLOCKS.register("demonic_altar", ()-> new DemonicAltarBlock(BlockBehaviour.Properties.of(Material.METAL)));
    public static final RegistryObject<BlockEntityType<AltarTileEntity>> DEMONIC_ALTAR_TE = TILE_ENTITIES.register("demonic_altar", () -> BlockEntityType.Builder.of(AltarTileEntity::new, DEMONIC_ALTAR.get()).build(null));

    public static final RegistryObject<MenuType<AltarContainer>> DEMONIC_ALTAR_CONTAINER = CONTAINER_TYPES.register("altar", () -> IForgeContainerType.create(AltarContainer::createClientContainer));

    public static final RegistryObject<BlockItem> DEMONIC_ALTAR_ITEM = ITEMS.register("demonic_altar", () -> new BlockItem(DEMONIC_ALTAR.get(), new Item.Properties().tab(ArcaneRituals.TAB)) );



    public static final RegistryObject<Blood> BLOOD = FLUIDS.register("blood", Blood::new);

    public static final RegistryObject<Item> VIAL = ITEMS.register("vial", () -> new ItemVial(new Item.Properties().tab(ArcaneRituals.TAB)));

    public static final RegistryObject<Item> SACRIFICIAL_KNIFE = ITEMS.register("sacrificial_knife", () -> new ItemSacrificialKnife(defItemProps().durability(55)));



    public static final RegistryObject<Item> BAT_WING = ITEMS.register("bat_wing", () -> new Item(defItemProps()));

    public static final RegistryObject<Item> HAMMER = ITEMS.register("hammer", () -> new Item(defItemProps().stacksTo(1)));

    public static final RegistryObject<Item> BLOOD_HAMMER = ITEMS.register("blood_hammer", () -> new Item(defItemProps().stacksTo(1)));

    public static final RegistryObject<Item> IRON_RING = ITEMS.register("iron_ring", () -> new Item(defItemProps().stacksTo(1)));


    public static final RegistryObject<Item> GOLDEN_RUNE_PLATE = ITEMS.register("golden_rune_plate", () -> new Item(defItemProps()));
    public static final RegistryObject<Item> GOLDEN_PLATE = ITEMS.register("golden_plate", () -> new Item(defItemProps()));


    public static final RegistryObject<Item> RING_OF_PROTECTION_1 = ITEMS.register("ring_of_protection_1", () -> new RingOfProtection(defItemProps().stacksTo(1),1));
    public static final RegistryObject<Item> RING_OF_PROTECTION_2 = ITEMS.register("ring_of_protection_2", () -> new RingOfProtection(defItemProps().stacksTo(1),2));
    public static final RegistryObject<Item> RING_OF_PROTECTION_3 = ITEMS.register("ring_of_protection_3", () -> new RingOfProtection(defItemProps().stacksTo(1),3));

    //public static final RegistryObject<Item> IRON_RING = ITEMS.register("ring_of_protection", () -> new Item(defItemProps()..stacksTo(1)));




    public static void initialize() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(modBus);
        BLOCKS.register(modBus);
        FLUIDS.register(modBus);

        TILE_ENTITIES.register(modBus);
        CONTAINER_TYPES.register(modBus);
        RECIPE_SERIALIZERS.register(modBus);
    }

    @SubscribeEvent
    public static void onModelRegistryEvent(ModelRegistryEvent event){
        /*
        ModelLoaderRegistry.registerLoader(ArcaneRituals.location("fluid_holder"),
                FluidHolderRenderer.Loader.INSTANCE);
         */
    }

    public static <T extends IForgeRegistryEntry<?>> ResourceLocation getName(T type) {
        return Objects.requireNonNull(type.getRegistryName());
    }

    public static <T extends IForgeRegistryEntry<?>> ResourceLocation getName(Supplier<T> supplier) {
        return getName(supplier.get());
    }

    public static Item.Properties defItemProps(){
        return new Item.Properties().tab(ArcaneRituals.TAB);
    }

}
