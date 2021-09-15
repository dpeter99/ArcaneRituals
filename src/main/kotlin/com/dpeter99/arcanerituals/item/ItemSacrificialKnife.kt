package com.dpeter99.arcanerituals.item

import net.minecraft.world.entity.player.Player
import com.dpeter99.arcanerituals.fluids.Blood.BloodData
import net.minecraft.world.item.ItemStack
import com.dpeter99.arcanerituals.registry.ARRegistry
import java.util.concurrent.atomic.AtomicBoolean
import net.minecraftforge.fluids.FluidUtil
import com.dpeter99.bloodylib.FluidHelper
import com.dpeter99.arcanerituals.items.ItemVial
import com.dpeter99.arcanerituals.fluids.Blood
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import com.dpeter99.bloodylib.NBTData
import java.util.UUID
import net.minecraft.world.damagesource.DamageSource
import com.dpeter99.arcanerituals.advancements.TriggerManager
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.LivingEntity
import com.dpeter99.arcanerituals.registry.mobblood.MobBloodManager
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.item.Item
import net.minecraft.world.level.Level
import java.util.function.Consumer

class ItemSacrificialKnife(builder: Properties?) : Item(builder) {

    fun makeFullVial(playerIn: Player, bloodData: BloodData?) {
        if (playerIn.inventory.contains(ItemStack(ARRegistry.VIAL.get()))) {
            val done = AtomicBoolean(false)
            playerIn.inventory.items.forEach(Consumer { item: ItemStack ->
                if (!done.get() && item.item === ARRegistry.VIAL.get()) {
                    val cap = FluidUtil.getFluidHandler(item).resolve()
                    if (cap.isPresent && FluidHelper.isEmpty(cap.get())) {
                        item.shrink(1)
                        val new_vial = ItemVial.make(Blood.makeFluidStack(ARRegistry.BLOOD.get(), 1000, bloodData))
                        playerIn.addItem(new_vial)
                        done.set(true)
                    }
                }
            })
        }
    }

    override fun use(worldIn: Level, playerIn: Player, handIn: InteractionHand): InteractionResultHolder<ItemStack> {
        val itemstack = playerIn.getItemInHand(handIn)
        if (playerIn.isCrouching) {
            val data = NBTData.fromStack(KnifeData::class.java, itemstack) // KnifeData.fromStack(itemstack);
            data.hit_needed = 0
            data.hit_count = 0
            data.target = UUID(0, 0)
            itemstack.tag = data.serializeNBT()
        } else {
            if (playerIn.health > 1) {
                playerIn.hurt(DamageSource.GENERIC, 5.0f)
                makeFullVial(playerIn, BloodData(Blood.PLAYER_BLOOD, playerIn.uuid))
                if (!worldIn.isClientSide) {
                    TriggerManager.BLOOD_DRAIN_TRIGGER.trigger(playerIn as ServerPlayer, Blood.PLAYER_BLOOD)
                }
            }
        }
        return InteractionResultHolder.success(itemstack)
    }

    override fun hurtEnemy(stack: ItemStack, target: LivingEntity, attacker: LivingEntity): Boolean {
        if (attacker is Player) {
            val target_name = target.type.registryName
            val data = NBTData.fromStack(KnifeData::class.java, stack)
            if (data.target == target.uuid) {
                data.hit_count++
            } else {
                data.target = target.uuid
                data.hit_count = 1
                val mobBlood = MobBloodManager.get(target_name)
                if (mobBlood != null) data.hit_needed = mobBlood.hppv else data.hit_needed = -1
            }
            if (data.hit_count == data.hit_needed) {
                makeFullVial(attacker, BloodData(target_name))
                data.hit_count = 0
                if (!target.level.isClientSide) {
                    TriggerManager.BLOOD_DRAIN_TRIGGER.trigger(attacker as ServerPlayer, target_name)
                }
            }
            stack.tag = data.serializeNBT()
        }
        return super.hurtEnemy(stack, target, attacker)
    }

    class KnifeData : NBTData {
        var hit_count = 0
        var hit_needed = 0
        var target: UUID? = null

        constructor() : super() {}

        constructor(hit_count: Int, hit_needed: Int, target: UUID?) {
            this.hit_count = hit_count
            this.hit_needed = hit_needed
            this.target = target
        }

        override fun serializeNBT(): CompoundTag {
            val nbt = CompoundTag()
            nbt.putInt("hit_count", hit_count)
            nbt.putInt("hit_needed", hit_needed)
            nbt.putUUID("target", target)
            return nbt
        }

        override fun deserializeNBT(nbt: CompoundTag) {
            hit_count = nbt.getInt("hit_count")
            hit_needed = nbt.getInt("hit_needed")
            target = UUID(0, 0)
            if (nbt.contains("target")) {
                target = nbt.getUUID("target")
            }
        }
    }
}