package com.dpeter99.ArcaneRituals;

import com.dpeter99.ArcaneRituals.altars.demonic.DemonicAltarBlock;
import com.dpeter99.ArcaneRituals.altars.demonic.DemonicAltarContainer;
import com.dpeter99.ArcaneRituals.altars.demonic.DemonicAltarTileEntity;
import com.dpeter99.ArcaneRituals.altars.necromantic.NecromanticAltarBlock;
import com.dpeter99.ArcaneRituals.altars.necromantic.NecromanticAltarContainer;
import com.dpeter99.ArcaneRituals.altars.necromantic.NecromanticAltarTileEntity;
import com.dpeter99.ArcaneRituals.arcaneFuel.ArcaneFuelType;
import com.dpeter99.ArcaneRituals.arcaneFuel.IArcaneFuel;
import com.dpeter99.ArcaneRituals.block.ArcaneBlocks_OLD;
import com.dpeter99.ArcaneRituals.block.arcane_anvil.ArcaneAnvilContainer;
import com.dpeter99.ArcaneRituals.block.arcane_anvil.ArcaneAnvilBlock;

import com.dpeter99.ArcaneRituals.block.arcane_tank.ArcaneFuelTankBlock;
import com.dpeter99.ArcaneRituals.block.arcane_tank.ArcaneFuelTankTileEntity;
import com.dpeter99.ArcaneRituals.client.renderer.FluidHolderRenderer;
import com.dpeter99.ArcaneRituals.crafting.AltarRecipe;
import com.dpeter99.ArcaneRituals.fluid.ArcaneFluids;
import com.dpeter99.ArcaneRituals.fluid.Blood;
import com.dpeter99.ArcaneRituals.item.*;
import com.dpeter99.ArcaneRituals.item.sacrificialKnife.ItemSacrificialKnife;
import com.dpeter99.ArcaneRituals.block.arcane_anvil.ArcaneAnvilTileEntity;
import com.dpeter99.bloodylib.TileEntityBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

// You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
// Event bus for receiving Registry Events)
@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class Registries {

    public static ItemGroup group;


    public static final DeferredRegister<Item> ITEM_REGISTRY =
            DeferredRegister.create(ForgeRegistries.ITEMS, ArcaneRituals.MODID);

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_REGISTRY =
            DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, ArcaneRituals.MODID);

    public static final DeferredRegister<Block> BLOCK_REGISTRY =
            DeferredRegister.create(ForgeRegistries.BLOCKS, ArcaneRituals.MODID);

    public static final DeferredRegister<Fluid> FLUID_REGISTRY =
            DeferredRegister.create(ForgeRegistries.FLUIDS, ArcaneRituals.MODID);



    public static final RegistryObject<TileEntityType<?>> ARCANE_ANVIL_TILE_ENTITY =
            Registries.TILE_ENTITY_REGISTRY.register("arcane_anvil",
                    () -> TileEntityType.Builder.create(ArcaneAnvilTileEntity::new, ArcaneBlocks_OLD.arcane_anvil).build(null));


    public static void init(){
        group = new ArcaneItemGroup();

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        TILE_ENTITY_REGISTRY.register(modEventBus);
        BLOCK_REGISTRY.register(modEventBus);

        DeferredRegister.create(ArcaneFuelType.class,ArcaneRituals.MODID);
    }

    @SubscribeEvent
    public static void onNewRegistry(RegistryEvent.NewRegistry registry){
        RegistryBuilder<ArcaneFuelType> registryBuilder = new RegistryBuilder<ArcaneFuelType>();
        registryBuilder.setName(ArcaneRituals.location("arcane_fuel"));
        registryBuilder.setType(ArcaneFuelType.class);
        registryBuilder.create();
    }


    @SubscribeEvent
    public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
        // register a new block here

        IForgeRegistry<Block> reg = blockRegistryEvent.getRegistry();

        reg.register(new NecromanticAltarBlock());
        reg.register(new DemonicAltarBlock());

        reg.register(new ArcaneAnvilBlock().setRegistryName(ArcaneRituals.location("arcane_anvil")));
    }

    @SubscribeEvent
    public static void onItemRegistry(final RegistryEvent.Register<Item> itemRegistryEvent){
         IForgeRegistry<Item> reg = itemRegistryEvent.getRegistry();

         reg.register(new BasicWand());
         reg.register(new ArcaneBook());

         reg.register(new BlockItem(ArcaneBlocks_OLD.arcane_anvil, new Item.Properties()).setRegistryName("arcane_anvil"));
    }

    @SubscribeEvent
    public static void onTileRegistry(final RegistryEvent.Register<TileEntityType<?>> itemRegistryEvent){
        IForgeRegistry<TileEntityType<?>> reg = itemRegistryEvent.getRegistry();

        reg.register(TileEntityType.Builder.create(NecromanticAltarTileEntity::new, ArcaneBlocks_OLD.necromantic_altar).build(null).setRegistryName("necromantic_altar"));
        reg.register(TileEntityType.Builder.create(DemonicAltarTileEntity::new, ArcaneBlocks_OLD.demonic_altar).build(null).setRegistryName("demonic_altar"));
    }

    @SubscribeEvent
    public static void onContainerRegistry(final RegistryEvent.Register<ContainerType<?>> itemRegistryEvent){
        IForgeRegistry<ContainerType<?>> reg = itemRegistryEvent.getRegistry();

        reg.register(IForgeContainerType.create((windowId, inv, data) -> {
            BlockPos pos = data.readBlockPos();
            return new NecromanticAltarContainer(windowId, ArcaneRituals.proxy.getClientWorld(), pos, inv);
        }).setRegistryName("necromantic_altar_container"));

        reg.register(IForgeContainerType.create((windowId, inv, data) -> {
            BlockPos pos = data.readBlockPos();
            return new DemonicAltarContainer(windowId, ArcaneRituals.proxy.getClientWorld(), pos, inv);
        }).setRegistryName("demonic_altar_container"));

        reg.register(IForgeContainerType.create((windowId, inv, data) -> {
            BlockPos pos = data.readBlockPos();
            return new ArcaneAnvilContainer(windowId, ArcaneRituals.proxy.getClientWorld(), pos, inv);
        }).setRegistryName("arcane_anvil_container"));
    }

    @SubscribeEvent
    public static void onRecipeSerializerRegistry(RegistryEvent.Register<IRecipeSerializer<?>> itemRegistryEvent){
        IForgeRegistry<IRecipeSerializer<?>> reg = itemRegistryEvent.getRegistry();

        reg.register(new AltarRecipe.Serializer().setRegistryName(AltarRecipe.RECIPE_TYPE_NAME));
    }

    @SubscribeEvent
    public static void onFluidRegistry(RegistryEvent.Register<Fluid> itemRegistryEvent){
        IForgeRegistry<Fluid> reg = itemRegistryEvent.getRegistry();

        //reg.register(new Blood());


    }

    @SubscribeEvent
    public static void onParticleRegistry(RegistryEvent.Register<ParticleType<?>> itemRegistryEvent){
        IForgeRegistry<ParticleType<?>> reg = itemRegistryEvent.getRegistry();

        reg.register(new BasicParticleType(true).setRegistryName("altar_demonic"));


    }

    @SubscribeEvent
    public static void onModelRegistryEvent(ModelRegistryEvent event){
        ModelLoaderRegistry.registerLoader(ArcaneRituals.location("fluid_holder"),
                FluidHolderRenderer.Loader.INSTANCE);
    }


    @SubscribeEvent
    public static void onArcaneFuelRegistry(final RegistryEvent.Register<ArcaneFuelType> blockRegistryEvent) {
        // register a new block here
        ArcaneRituals.LOGGER.info("HELLO from Register Block");

        IForgeRegistry<ArcaneFuelType> reg = blockRegistryEvent.getRegistry();

        reg.register(new ArcaneFuelType(() -> (IArcaneFuel) ArcaneFluids.blood.get()).setRegistryName(ArcaneRituals.location("blood")));
    }


    private static class ArcaneItemGroup extends ItemGroup {
        public ArcaneItemGroup() {
            super("items_group");
        }

        @Override
        public ItemStack createIcon() {
            ItemStack stack =new ItemStack(ArcaneItems.vial.get());
            FluidUtil.getFluidHandler(stack).ifPresent(
                    fluidcont ->{
                        fluidcont.drain(Integer.MAX_VALUE, IFluidHandler.FluidAction.EXECUTE);
                        fluidcont.fill(new FluidStack(ArcaneFluids.blood.get(),10000), IFluidHandler.FluidAction.EXECUTE);
                    }
            );

            return stack;
        }

        /**
         * Fills {@code items} with all items that are in this group.
         *
         * @param items
         */
        @Override
        public void fill(NonNullList<ItemStack> items) {
            super.fill(items);

            //items.add(new ItemStack(ArcaneItems_old.vial.get()));
            items.add(new ItemStack(ArcaneItems_old.arcane_book));
            //items.add(new ItemStack(ArcaneItems_old.bat_wing));
            items.add(new ItemStack(ArcaneItems_old.sacrificial_knife));
        }
    }
}
