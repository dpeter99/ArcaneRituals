package com.dpeter99.arcanerituals.advancements;

import net.minecraft.advancements.CriteriaTriggers;

public class TriggerManager
{
    public static void init() {
        CriteriaTriggers.register(new BloodDrainTrigger());
    }
}