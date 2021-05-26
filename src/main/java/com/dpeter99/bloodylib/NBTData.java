package com.dpeter99.bloodylib;

import com.dpeter99.arcanerituals.items.ItemSacrificialKnife;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.UUID;

public abstract class NBTData implements INBTSerializable<CompoundNBT> {

    /*required*/ public NBTData() {
    }

    public static <T extends NBTData> T get(Class<T> clazz, CompoundNBT nbt){
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
            CompoundNBT nbt = stack.getTag();
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
