package com.dpeter99.bloodylib;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;

public final class StackUtils {

    private StackUtils() {
    }

    public static ItemStack size(ItemStack stack, int size) {
        if (size <= 0 || stack.isEmpty()) {
            return ItemStack.EMPTY;
        }
        return ItemHandlerHelper.copyStackWithSize(stack, size);
    }

}