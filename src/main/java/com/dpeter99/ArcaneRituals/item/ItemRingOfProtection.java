package com.dpeter99.ArcaneRituals.item;

import com.dpeter99.ArcaneRituals.Registries;
import com.google.common.collect.Multimap;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;

import java.util.UUID;

public class ItemRingOfProtection extends Item {

    protected static final UUID PROTECTION_MODIFIER = UUID.randomUUID();

    int level;

    public ItemRingOfProtection(int level) {
        super(new Properties().maxStackSize(1).group(Registries.group));
        this.level = level;
    }

    /**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
     *
     * @param equipmentSlot
     */
    @SuppressWarnings("deprecation")
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
}
