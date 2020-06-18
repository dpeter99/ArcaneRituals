package com.dpeter99.ArcaneRituals.item;

import com.dpeter99.ArcaneRituals.ArcaneItems;
import com.dpeter99.ArcaneRituals.fluid.ArcaneFluids;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.GlassBottleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
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

    public ItemStack makeFullVial(PlayerEntity playerIn){
        if(playerIn.inventory.hasItemStack(new ItemStack(ArcaneItems.vial))){

            playerIn.inventory.mainInventory.forEach((item)->{
                if(item.getItem() == ArcaneItems.vial) {
                    item.shrink(1);
                    return;
                }
            });

            ItemStack new_vial = new ItemStack(ArcaneItems.blood_vial);
            return new_vial;
        }
        return null;
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if(playerIn.getHealth() > 1){
            playerIn.attackEntityFrom(DamageSource.GENERIC,1.0f);

            ItemStack new_vial = makeFullVial(playerIn);
            ItemBloodVial.SetOwner(new_vial,playerIn.getUniqueID());
            playerIn.addItemStackToInventory(new_vial);
        }

        return ActionResult.resultSuccess(itemstack);
    }

    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     *
     * @param stack
     * @param target
     * @param attacker
     */
    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if(attacker instanceof PlayerEntity ){
            PlayerEntity player = (PlayerEntity)attacker;

            ItemStack new_vial = makeFullVial(player);
            ItemBloodVial.SetOwner(new_vial,target.getEntityString());
            player.addItemStackToInventory(new_vial);

        }

        return super.hitEntity(stack, target, attacker);
    }
}
