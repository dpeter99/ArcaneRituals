package com.dpeter99.ArcaneRituals.item;

import com.dpeter99.ArcaneRituals.ArcaneItems;
import com.dpeter99.ArcaneRituals.fluid.ArcaneFluids;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.GlassBottleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStackSimple;

public class ItemSacrificialKnife extends Item {

    public ItemSacrificialKnife() {
        super(new Properties());
        setRegistryName("sacrificial_knife");
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if(playerIn.getHealth() > 1){
            playerIn.attackEntityFrom(DamageSource.GENERIC,1.0f);
            if(playerIn.inventory.hasItemStack(new ItemStack(Items.GLASS_BOTTLE))){

                playerIn.inventory.mainInventory.forEach((item)->{
                        if(item.getItem() == Items.GLASS_BOTTLE) {
                            item.shrink(1);
                            return;
                        }
                });

                ItemStack new_vial = new ItemStack(ArcaneItems.blood_vial);
                new_vial.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent(
                        fluidInv ->
                        {
                            fluidInv.fill(new FluidStack(ArcaneFluids.blood,100000), IFluidHandler.FluidAction.EXECUTE);
                        }
                );

                playerIn.addItemStackToInventory(new_vial);
            }
        }

        return ActionResult.resultSuccess(itemstack);
    }
}
