package com.dpeter99.arcanerituals.items;

import com.dpeter99.arcanerituals.registry.ARRegistry;
import com.dpeter99.bloodylib.fluid.AdvancedFluid;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStackSimple;
import org.lwjgl.system.CallbackI;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

import net.minecraft.world.item.Item.Properties;

public class ItemVial extends Item {


    public ItemVial(Properties builder) {
        super(builder);
    }

    public static void setFluid(ItemStack stack, FluidStack f){
        stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent(
                fluidinv -> {
                    fluidinv.drain(Integer.MAX_VALUE, IFluidHandler.FluidAction.EXECUTE);
                    fluidinv.fill(f, IFluidHandler.FluidAction.EXECUTE);
                }
        );
    }

    public static ItemStack make(FluidStack f){
        ItemStack new_vial = new ItemStack(ARRegistry.VIAL.get());
        ItemVial.setFluid(new_vial,f);
        return new_vial;
    }

    public static FluidStack getFluid(ItemStack stack){
        if(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY == null)
            return FluidStack.EMPTY;

        LazyOptional<IFluidHandlerItem> capability = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY);
        if(capability.isPresent()){
            Optional<IFluidHandlerItem> resolve = capability.resolve();
            if(resolve.isPresent()) {
                return resolve.get().getFluidInTank(0);
            }
        }
        return FluidStack.EMPTY;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);

        FluidStack fluidStack = getFluid(stack);
        Fluid fluid = fluidStack.getFluid();
        if(fluid instanceof AdvancedFluid){
            tooltip.add(((AdvancedFluid) fluid).getInfoText(fluidStack));
        }
    }

    @Override
    public Component getName(ItemStack stack) {

        FluidStack fluid = getFluid(stack);
        Component this_name =new TranslatableComponent(this.getDescriptionId(stack));
        if (!fluid.getFluid().isSame(Fluids.EMPTY)) {
            return new TranslatableComponent("%s %s",
                    new TranslatableComponent(fluid.getTranslationKey()),
                    new TranslatableComponent(this.getDescriptionId(stack))
            );
        }

        return super.getName(stack);
    }

    /**
     * Called from ItemStack.setItem, will hold extra data for the life of this
     * ItemStack. Can be retrieved from stack.getCapabilities() The NBT can be null
     * if this is not called from readNBT or if the item the stack is changing FROM
     * is different then this item, or the previous item had no capabilities.
     * <p>
     * This is called BEFORE the stacks item is set so you can use stack.getItem()
     * to see the OLD item. Remember that getItem CAN return null.
     *
     * @param stack The ItemStack
     * @param nbt   NBT of this item serialized, or null.
     * @return A holder instance associated with this ItemStack where you can hold
     * capabilities for the life of this item.
     */
    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        FluidHandlerItemStack fluid;

        fluid = new FluidHandlerItemStack(stack,250) {
            @Override
            public void setFluid(FluidStack fluid){
                super.setFluid(fluid);
            }



        };

        /*
        fluid = new FluidHandlerItemStack(stack,250){

        }
         */

        return fluid;
    }
}