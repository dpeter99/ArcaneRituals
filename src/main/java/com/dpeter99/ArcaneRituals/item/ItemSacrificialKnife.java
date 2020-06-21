package com.dpeter99.ArcaneRituals.item;

import com.dpeter99.ArcaneRituals.ArcaneItems;
import com.dpeter99.ArcaneRituals.fluid.ArcaneFluids;
import com.dpeter99.ArcaneRituals.fluid.Blood;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.concurrent.atomic.AtomicBoolean;

public class ItemSacrificialKnife extends Item {

    public ItemSacrificialKnife() {
        super(new Properties());
        setRegistryName("sacrificial_knife");
    }

    public void makeFullVial(PlayerEntity playerIn, Blood.BloodData bloodData){
        if(playerIn.inventory.hasItemStack(new ItemStack(ArcaneItems.vial))){
            AtomicBoolean done= new AtomicBoolean(false);
            playerIn.inventory.mainInventory.forEach((item)->{
                if(!done.get() && item.getItem() == ArcaneItems.vial && ItemVial.isEmpty(item)) {
                    item.shrink(1);

                    ItemStack new_vial = new ItemStack(ArcaneItems.vial);
                    ItemVial.setFluid(new_vial, ArcaneFluids.blood.makeFluidStack(1000,bloodData));
                    playerIn.addItemStackToInventory(new_vial);

                    done.set(true);
                }
            });


        }
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if(playerIn.getHealth() > 1){
            playerIn.attackEntityFrom(DamageSource.GENERIC,1.0f);

            makeFullVial(playerIn, new Blood.BloodData(Blood.PLAYER_BLOOD,playerIn.getUniqueID()));
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

            makeFullVial(player,new Blood.BloodData(target.getEntityString()));
        }

        return super.hitEntity(stack, target, attacker);
    }
}
