package com.dpeter99.ArcaneRituals;

import com.dpeter99.ArcaneRituals.altars.demonic.DemonicAltarBlock;
import com.dpeter99.ArcaneRituals.altars.demonic.DemonicAltarContainer;
import com.dpeter99.ArcaneRituals.altars.demonic.DemonicAltarTileEntity;
import com.dpeter99.ArcaneRituals.altars.necromantic.NecromanticAltarBlock;
import com.dpeter99.ArcaneRituals.altars.necromantic.NecromanticAltarContainer;
import com.dpeter99.ArcaneRituals.altars.necromantic.NecromanticAltarTileEntity;
import com.dpeter99.ArcaneRituals.block.ArcaneBlocks;
import com.dpeter99.ArcaneRituals.block.WitchAltarBlock;
import com.dpeter99.ArcaneRituals.crafting.AltarRecipe;
import com.dpeter99.ArcaneRituals.fluid.ArcaneFluids;
import com.dpeter99.ArcaneRituals.fluid.Blood;
import com.dpeter99.ArcaneRituals.item.*;
import com.dpeter99.ArcaneRituals.tileentity.WitchAltarContainer;
import com.dpeter99.ArcaneRituals.tileentity.WitchAltarTileEntity;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

// You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
// Event bus for receiving Registry Events)
@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class Registries {


    public static ItemGroup group;

    public static void init(){

         group = new ArcaneItemGroup();

    }


    @SubscribeEvent
    public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
        // register a new block here
        ArcaneRituals.LOGGER.info("HELLO from Register Block");

        IForgeRegistry<Block> reg = blockRegistryEvent.getRegistry();

        reg.register(new WitchAltarBlock());
        reg.register(new NecromanticAltarBlock());
        reg.register(new DemonicAltarBlock());
    }

    @SubscribeEvent
    public static void onItemRegistry(final RegistryEvent.Register<Item> itemRegistryEvent){
         IForgeRegistry<Item> reg = itemRegistryEvent.getRegistry();

         reg.register(new Item(new Item.Properties()).setRegistryName("bat_wing"));

         reg.register(new BasicWand());
         reg.register(new ArcaneBook());
         reg.register(new ItemSacrificialKnife());

         reg.register(new ItemVial().setRegistryName("vial"));
    }

    @SubscribeEvent
    public static void onTileRegistry(final RegistryEvent.Register<TileEntityType<?>> itemRegistryEvent){
        IForgeRegistry<TileEntityType<?>> reg = itemRegistryEvent.getRegistry();

        reg.register(TileEntityType.Builder.create(WitchAltarTileEntity::new, ArcaneBlocks.witch_altar).build(null).setRegistryName("witch_altar"));
        reg.register(TileEntityType.Builder.create(NecromanticAltarTileEntity::new, ArcaneBlocks.necromantic_altar).build(null).setRegistryName("necromantic_altar"));
        reg.register(TileEntityType.Builder.create(DemonicAltarTileEntity::new, ArcaneBlocks.demonic_altar).build(null).setRegistryName("demonic_altar"));
    }

    @SubscribeEvent
    public static void onContainerRegistry(final RegistryEvent.Register<ContainerType<?>> itemRegistryEvent){
        IForgeRegistry<ContainerType<?>> reg = itemRegistryEvent.getRegistry();

        reg.register(IForgeContainerType.create((windowId, inv, data) -> {
            BlockPos pos = data.readBlockPos();
            return new WitchAltarContainer(windowId, ArcaneRituals.proxy.getClientWorld(), pos, inv);
        }).setRegistryName("witch_altar_continer"));

        reg.register(IForgeContainerType.create((windowId, inv, data) -> {
            BlockPos pos = data.readBlockPos();
            return new NecromanticAltarContainer(windowId, ArcaneRituals.proxy.getClientWorld(), pos, inv);
        }).setRegistryName("necromantic_altar_container"));

        reg.register(IForgeContainerType.create((windowId, inv, data) -> {
            BlockPos pos = data.readBlockPos();
            return new DemonicAltarContainer(windowId, ArcaneRituals.proxy.getClientWorld(), pos, inv);
        }).setRegistryName("demonic_altar_container"));
    }

    @SubscribeEvent
    public static void onRecipeSerializerRegistry(RegistryEvent.Register<IRecipeSerializer<?>> itemRegistryEvent){
        IForgeRegistry<IRecipeSerializer<?>> reg = itemRegistryEvent.getRegistry();

        reg.register(new AltarRecipe.Serializer().setRegistryName(AltarRecipe.RECIPE_TYPE_NAME));
    }

    @SubscribeEvent
    public static void onFluidRegistry(RegistryEvent.Register<Fluid> itemRegistryEvent){
        IForgeRegistry<Fluid> reg = itemRegistryEvent.getRegistry();

        reg.register(new Blood());


    }

    private static class ArcaneItemGroup extends ItemGroup {
        public ArcaneItemGroup() {
            super("items_group");
        }

        @Override
        public ItemStack createIcon() {
            ItemStack stack =new ItemStack(ArcaneItems.vial);
            FluidUtil.getFluidHandler(stack).ifPresent(
                    fluidcont ->{
                        fluidcont.drain(Integer.MAX_VALUE, IFluidHandler.FluidAction.EXECUTE);
                        fluidcont.fill(new FluidStack(ArcaneFluids.blood,10000), IFluidHandler.FluidAction.EXECUTE);
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

            items.add(new ItemStack(ArcaneItems.vial));
            items.add(new ItemStack(ArcaneItems.arcane_book));
            items.add(new ItemStack(ArcaneItems.bat_wing));
            items.add(new ItemStack(ArcaneItems.sacrificial_knife));
        }
    }
}
