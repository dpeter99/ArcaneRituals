package com.dpeter99.ArcaneRituals.item;

import com.dpeter99.ArcaneRituals.ArcaneItems;
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
                        }
                });

                playerIn.addItemStackToInventory(new ItemStack(ArcaneItems.blood_bottle));
            }
        }

        return ActionResult.resultSuccess(itemstack);
    }
}
