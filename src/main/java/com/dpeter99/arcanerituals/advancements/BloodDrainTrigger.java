package com.dpeter99.arcanerituals.advancements;

import com.dpeter99.arcanerituals.ArcaneRituals;
import com.google.gson.JsonObject;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.KilledTrigger;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.loot.LootContext;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

public class BloodDrainTrigger extends AbstractCriterionTrigger<BloodDrainTrigger.Instance> {

    private static final ResourceLocation ID = ArcaneRituals.location("blood_drained");

    public BloodDrainTrigger() {

    }

    @Override
    protected Instance createInstance(JsonObject jsonObject, EntityPredicate.AndPredicate andPredicate, ConditionArrayParser conditionArrayParser) {
        ResourceLocation loc = ResourceLocation.tryParse(jsonObject.get("blood_source").getAsString());

        return new Instance(loc,andPredicate);
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }


    public void trigger(ServerPlayerEntity player, ResourceLocation bloodSource) {

        this.trigger(player, (cInstance) -> {
            return cInstance.matches(player, bloodSource);
        });
    }



    public static class Instance extends CriterionInstance {

        public ResourceLocation bloodSource;

        public Instance(ResourceLocation p_i231464_1_, EntityPredicate.AndPredicate p_i231464_2_) {
            super(BloodDrainTrigger.ID, p_i231464_2_);
            this.bloodSource = p_i231464_1_;
        }

        public boolean matches(ServerPlayerEntity p_235050_1_, ResourceLocation source) {
            return bloodSource.equals(source);
        }

        public JsonObject serializeToJson(ConditionArraySerializer p_230240_1_) {
            JsonObject json = super.serializeToJson(p_230240_1_);

            json.addProperty("blood_source", bloodSource.toString());

            return json;
        }


    }

}
