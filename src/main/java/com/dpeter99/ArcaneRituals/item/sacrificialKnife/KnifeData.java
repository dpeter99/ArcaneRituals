package com.dpeter99.ArcaneRituals.item.sacrificialKnife;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

import java.util.UUID;

public class KnifeData {

    public int hit_count;
    public int hit_needed;
    public UUID target;

    public KnifeData(int hit_count, int hit_needed, UUID target) {
        this.hit_count = hit_count;
        this.hit_needed = hit_needed;
        this.target = target;
    }

    public static KnifeData fromStack(ItemStack stack) {

        if (stack.hasTag()) {
            CompoundNBT nbt = stack.getTag();
            return deserialize(nbt);
        } else {
            return new KnifeData(0, 0, new UUID(0,0));
        }

    }

    public static KnifeData deserialize(CompoundNBT nbt) {

        int hit_count = nbt.getInt("hit_count");
        int hit_needed = nbt.getInt("hit_needed");
        UUID target = nbt.getUniqueId("target");

        return new KnifeData(hit_count, hit_needed, target);
    }

    public CompoundNBT serialzie() {

        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("hit_count", hit_count);
        nbt.putInt("hit_needed", hit_needed);
        nbt.putUniqueId("target", target);
        return nbt;
    }
}
