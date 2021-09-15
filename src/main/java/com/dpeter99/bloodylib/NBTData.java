package com.dpeter99.bloodylib;

import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public abstract class NBTData implements INBTSerializable<CompoundTag> {

    /*required*/ public NBTData() {
    }

    public static <T extends NBTData> T get(Class<T> clazz, CompoundTag nbt){
        try {
            T data = (T) clazz.newInstance();

            data.deserializeNBT(nbt);

            return data;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static <T extends NBTData> T fromStack(Class<T> clazz, ItemStack stack) {

        if (stack.hasTag()) {
            CompoundTag nbt = stack.getTag();
            return NBTData.get(clazz, nbt);
        } else {
            try {
                return clazz.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
