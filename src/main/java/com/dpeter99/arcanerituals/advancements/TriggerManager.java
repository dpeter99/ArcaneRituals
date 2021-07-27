package com.dpeter99.arcanerituals.advancements;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;

public class TriggerManager
{
    public static final BloodDrainTrigger BLOOD_DRAIN_TRIGGER = (BloodDrainTrigger)register(new BloodDrainTrigger());

    private static <T extends SimpleCriterionTrigger<?>> T register(T trigger) {
        return CriteriaTriggers.register(trigger);
    }

    public static void init() {
        CriteriaTriggers.register(new BloodDrainTrigger());
    }
}