package com.dpeter99.arcanerituals.advancements;

import com.dpeter99.arcanerituals.ArcaneRituals;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class BloodDrainTrigger extends SimpleCriterionTrigger<BloodDrainTrigger.Instance> {

    private static final ResourceLocation ID = ArcaneRituals.location("blood_drained");

    public BloodDrainTrigger() {

    }

    @Override
    protected Instance createInstance(JsonObject jsonObject, EntityPredicate.Composite andPredicate, DeserializationContext conditionArrayParser) {
        ResourceLocation loc = ResourceLocation.tryParse(jsonObject.get("blood_source").getAsString());

        return new Instance(loc,andPredicate);
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }


    public void trigger(ServerPlayer player, ResourceLocation bloodSource) {

        this.trigger(player, (cInstance) -> {
            return cInstance.matches(player, bloodSource);
        });
    }



    public static class Instance extends AbstractCriterionTriggerInstance {

        public ResourceLocation bloodSource;

        public Instance(ResourceLocation p_i231464_1_, EntityPredicate.Composite p_i231464_2_) {
            super(BloodDrainTrigger.ID, p_i231464_2_);
            this.bloodSource = p_i231464_1_;
        }

        public boolean matches(ServerPlayer p_235050_1_, ResourceLocation source) {
            return bloodSource.equals(source);
        }

        public JsonObject serializeToJson(SerializationContext p_230240_1_) {
            JsonObject json = super.serializeToJson(p_230240_1_);

            json.addProperty("blood_source", bloodSource.toString());

            return json;
        }


    }

}
