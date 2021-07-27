package com.dpeter99.arcanerituals.items;

import com.dpeter99.arcanerituals.ArcaneRituals;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
//import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.lang.reflect.Method;

public class RingOfProtection extends Item /*implements ICurioItem*/ {

    private static final int RING_PROTECTION_S = 5;

    private int level;

    public RingOfProtection(Properties properties, int level) {
        super(properties);
        this.level = level;
    }

    //TODO: LATER
/*
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        atts.put(Attributes.ARMOR,
                new AttributeModifier(uuid, ArcaneRituals.MODID + ":armor_bonus", 2*level,
                        AttributeModifier.Operation.ADDITION));
        return atts;
    }
*/

    public int getLevel() {
        return level;
    }

    public void entityDamaged(LivingHurtEvent event){

        Method getDamageAfterArmorAbsorb = ObfuscationReflectionHelper.findMethod(LivingEntity.class, "getDamageAfterArmorAbsorb", DamageSource.class, float.class);
        float reducedDamage = 0;
        try {
            reducedDamage = (float) getDamageAfterArmorAbsorb.invoke(event.getEntityLiving(),event.getSource(),event.getAmount());
        } catch (Exception e) {
            e.printStackTrace();
        }

        float c = (RING_PROTECTION_S /(reducedDamage+ RING_PROTECTION_S));
        c *= (level+(RING_PROTECTION_S /10f))/5f;

        float f = event.getEntityLiving().getRandom().nextFloat();
        if(f <= c){
            event.setCanceled(true);
            ArcaneRituals.LOGGER.info("Saved," + reducedDamage + "damage from the player");
        }

    }
}
