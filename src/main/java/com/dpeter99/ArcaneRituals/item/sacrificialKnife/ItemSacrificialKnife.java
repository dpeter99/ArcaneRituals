package com.dpeter99.ArcaneRituals.item.sacrificialKnife;

import com.dpeter99.ArcaneRituals.MobBlood;
import com.dpeter99.ArcaneRituals.fluid.ArcaneFluids;
import com.dpeter99.ArcaneRituals.fluid.Blood;
import com.dpeter99.ArcaneRituals.item.ArcaneItems;
import com.dpeter99.ArcaneRituals.item.ItemVial;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class ItemSacrificialKnife extends Item {

    public ItemSacrificialKnife() {
        super(new Properties().defaultMaxDamage(55));
        setRegistryName("sacrificial_knife");
    }

    public void makeFullVial(PlayerEntity playerIn, Blood.BloodData bloodData) {
        if (playerIn.inventory.hasItemStack(new ItemStack(ArcaneItems.vial))) {
            AtomicBoolean done = new AtomicBoolean(false);
            playerIn.inventory.mainInventory.forEach((item) -> {
                if (!done.get() && item.getItem() == ArcaneItems.vial && ItemVial.isEmpty(item)) {
                    item.shrink(1);

                    ItemStack new_vial = new ItemStack(ArcaneItems.vial);
                    ItemVial.setFluid(new_vial, ArcaneFluids.blood.makeFluidStack(1000, bloodData));
                    playerIn.addItemStackToInventory(new_vial);

                    done.set(true);
                }
            });


        }
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {

        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if (playerIn.isSneaking()) {

            KnifeData data = KnifeData.fromStack(itemstack);
            data.hit_needed = 0;
            data.hit_count = 0;
            data.target = new UUID(0,0);
            itemstack.setTag(data.serialzie());

        } else {


            if (playerIn.getHealth() > 1) {
                playerIn.attackEntityFrom(DamageSource.GENERIC, 5.0f);

                makeFullVial(playerIn, new Blood.BloodData(Blood.PLAYER_BLOOD, playerIn.getUniqueID()));
            }
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
        if (attacker instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) attacker;

            ResourceLocation target_name = target.getType().getRegistryName();

            KnifeData data = KnifeData.fromStack(stack);
            if (data.target.equals(target.getUniqueID())) {
                data.hit_count++;

            } else {
                data.target = target.getUniqueID();
                data.hit_count = 1;

                MobBlood mobBlood = MobBlood.get(target_name);
                if (mobBlood != null)
                    data.hit_needed = mobBlood.getHppv();
                else
                    data.hit_needed = -1;

            }
            if (data.hit_count == data.hit_needed) {
                makeFullVial(player, new Blood.BloodData(target_name.toString()));
                data.hit_count = 0;
            }

            stack.setTag(data.serialzie());
        }

        return super.hitEntity(stack, target, attacker);
    }

    /**
     * Called each tick as long the item is on a player inventory. Uses by maps to check if is on a player hand and
     * update it's contents.
     *
     * @param stack
     * @param worldIn
     * @param entityIn
     * @param itemSlot
     * @param isSelected
     */
    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);


    }


}


