package com.dpeter99.arcanerituals.items;

import com.dpeter99.arcanerituals.fluids.Blood;
import com.dpeter99.arcanerituals.registry.ARRegistry;
import com.dpeter99.bloodylib.fluid.AdvancedFluid;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStackSimple;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class ItemVial extends Item {

    private static ItemStack emptyStack;

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

    public static boolean isEmpty(ItemStack stack){
        LazyOptional<IFluidHandlerItem> capability = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY);
        if(capability.isPresent()){
            return capability.orElse(null).getFluidInTank(0).isEmpty();
        }
        return false;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);

        FluidStack fluidStack = getFluid(stack);
        Fluid fluid = fluidStack.getFluid();
        if(fluid instanceof AdvancedFluid){
            tooltip.add(((AdvancedFluid) fluid).getInfoText(fluidStack));
        }
    }

    @Override
    public ITextComponent getName(ItemStack stack) {

        FluidStack fluid = getFluid(stack);
        ITextComponent this_name =new TranslationTextComponent(this.getDescriptionId(stack));
        if (!fluid.getFluid().isSame(Fluids.EMPTY)) {
            return new TranslationTextComponent("%s %s",
                    new TranslationTextComponent(fluid.getTranslationKey()),
                    new TranslationTextComponent(this.getDescriptionId(stack))
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
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        FluidHandlerItemStackSimple fluid;

        fluid = new FluidHandlerItemStackSimple(stack, 250) {
            @Override
            public void setFluid(FluidStack fluid){
                super.setFluid(fluid);
            }
        };
        return fluid;
    }
}