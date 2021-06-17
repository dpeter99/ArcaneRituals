package com.dpeter99.arcanerituals.items;

import com.dpeter99.arcanerituals.fluids.Blood;
import com.dpeter99.arcanerituals.registry.ARRegistry;
import com.dpeter99.arcanerituals.registry.mobblood.MobBlood;
import com.dpeter99.arcanerituals.registry.mobblood.MobBloodManager;
import com.dpeter99.bloodylib.FluidHelper;
import com.dpeter99.bloodylib.NBTData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class ItemSacrificialKnife extends Item {

    public ItemSacrificialKnife(Properties builder) {
        super(builder);
    }

    public void makeFullVial(PlayerEntity playerIn, Blood.BloodData bloodData) {
        if (playerIn.inventory.contains(new ItemStack(ARRegistry.VIAL.get()))) {
            AtomicBoolean done = new AtomicBoolean(false);

            playerIn.inventory.items.forEach((item) -> {
                if(!done.get() && item.getItem() == ARRegistry.VIAL.get()){

                    Optional<IFluidHandlerItem> cap = FluidUtil.getFluidHandler(item).resolve();
                    if(cap.isPresent()&& FluidHelper.isEmpty(cap.get())){
                        item.shrink(1);

                        ItemStack new_vial =  ItemVial.make(Blood.makeFluidStack(ARRegistry.BLOOD.get(),1000, bloodData));
                        playerIn.addItem(new_vial);

                        done.set(true);
                    }

                }
            });
        }
    }

    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {

        ItemStack itemstack = playerIn.getItemInHand(handIn);

        if (playerIn.isCrouching()) {

            KnifeData data = NBTData.fromStack(KnifeData.class,itemstack); // KnifeData.fromStack(itemstack);
            data.hit_needed = 0;
            data.hit_count = 0;
            data.target = new UUID(0,0);
            itemstack.setTag(data.serializeNBT());

        } else {
            if (playerIn.getHealth() > 1) {
                playerIn.hurt(DamageSource.GENERIC, 5.0f);

                makeFullVial(playerIn, new Blood.BloodData(Blood.PLAYER_BLOOD, playerIn.getUUID()));
            }
        }
        return ActionResult.success(itemstack);
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
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) attacker;

            ResourceLocation target_name = target.getType().getRegistryName();

            KnifeData data = NBTData.fromStack(KnifeData.class,stack);
            if (data.target.equals(target.getUUID())) {
                data.hit_count++;

            } else {
                data.target = target.getUUID();
                data.hit_count = 1;

                MobBlood mobBlood = MobBloodManager.get(target_name);
                if (mobBlood != null)
                    data.hit_needed = mobBlood.getHppv();
                else
                    data.hit_needed = -1;

            }
            if (data.hit_count == data.hit_needed) {
                makeFullVial(player, new Blood.BloodData(target_name.toString()));
                data.hit_count = 0;
            }

            stack.setTag(data.serializeNBT());
        }

        return super.hurtEnemy(stack, target, attacker);
    }


    public static class KnifeData extends NBTData {

        public int hit_count;
        public int hit_needed;
        public UUID target;

        public KnifeData(){
            super();
        }

        public KnifeData(int hit_count, int hit_needed, UUID target) {
            this.hit_count = hit_count;
            this.hit_needed = hit_needed;
            this.target = target;
        }

        @Override
        public CompoundNBT serializeNBT() {
            CompoundNBT nbt = new CompoundNBT();
            nbt.putInt("hit_count", hit_count);
            nbt.putInt("hit_needed", hit_needed);
            nbt.putUUID("target", target);
            return nbt;
        }

        @Override
        public void deserializeNBT(CompoundNBT nbt) {
            hit_count = nbt.getInt("hit_count");
            hit_needed = nbt.getInt("hit_needed");
            target = new UUID(0,0);
            if(nbt.contains("target")) {
                target = nbt.getUUID("target");
            }
        }
    }


}
