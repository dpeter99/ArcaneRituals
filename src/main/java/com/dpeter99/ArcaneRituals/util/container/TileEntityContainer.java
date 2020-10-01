package com.dpeter99.ArcaneRituals.util.container;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.LazyValue;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public abstract class TileEntityContainer<T extends TileEntity> extends SimpleContainer {

    protected T tileEntity;
    protected IItemHandler inv;

    protected TileEntityContainer(@Nullable ContainerType<?> type, int id, World world, BlockPos pos, PlayerInventory playerInventory) {
        super(type, id);

        tileEntity = (T) world.getTileEntity(pos);

        LazyOptional<IItemHandler> itemHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);

        if(itemHandler.isPresent()){
            inv = itemHandler.orElse(null);
        }
    }


}
