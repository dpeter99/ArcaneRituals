package com.dpeter99.ArcaneRituals.item;

import com.dpeter99.ArcaneRituals.Registries;
import com.dpeter99.ArcaneRituals.util.CapCurioItem;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import top.theillusivec4.curios.api.CuriosAPI;
import top.theillusivec4.curios.api.capability.ICurio;


import javax.annotation.Nonnull;
import java.util.UUID;

public class ItemRingOfProtection extends Item {

    protected static final UUID PROTECTION_MODIFIER = UUID.fromString("88f7714e-e520-48be-9577-0c31b770365e");

    int level;

    public ItemRingOfProtection(int level) {
        super(new Properties().maxStackSize(1).group(Registries.group));
        this.level = level;
    }

    public ICapabilityProvider initCapabilities(final ItemStack stack, CompoundNBT unused) {
        return CapCurioItem.createProvider(new ICurio() {

            public void playEquipSound(LivingEntity livingEntity) {
                livingEntity.world.playSound((PlayerEntity)null, livingEntity.getPosition(), SoundEvents.ITEM_ARMOR_EQUIP_GOLD, SoundCategory.NEUTRAL, 1.0F, 1.0F);
            }

            public Multimap<String, AttributeModifier> getAttributeModifiers(String identifier) {
                Multimap<String, AttributeModifier> atts = HashMultimap.create();
                if (CuriosAPI.getCurioTags(stack.getItem()).contains(identifier)) {
                    atts.put(
                            SharedMonsterAttributes.ARMOR_TOUGHNESS.getName(),
                            new AttributeModifier(PROTECTION_MODIFIER, "Extra Health", level, AttributeModifier.Operation.ADDITION)
                    );
                }

                return atts;
            }

            @Nonnull
            public DropRule getDropRule(LivingEntity livingEntity) {
                return DropRule.ALWAYS_KEEP;
            }

            public boolean canRightClickEquip() {
                return true;
            }
        });
    }

    public boolean hasEffect(ItemStack stack) {
        return true;
    }

    /**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
     *
     * @param equipmentSlot
     */
    /*
    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(equipmentSlot);
        //if (equipmentSlot == EquipmentSlotType.) {
            multimap.put(
                    SharedMonsterAttributes.MAX_HEALTH.getName(),
                    new AttributeModifier(PROTECTION_MODIFIER, "Extra Health", this.level, AttributeModifier.Operation.ADDITION)
            );
        //}

        return multimap;
    }
    */

}
