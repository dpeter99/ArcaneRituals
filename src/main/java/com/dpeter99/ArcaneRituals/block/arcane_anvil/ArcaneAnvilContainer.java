package com.dpeter99.ArcaneRituals.block.arcane_anvil;

import com.dpeter99.ArcaneRituals.ArcaneTileEntities;
import com.dpeter99.ArcaneRituals.util.container.SimpleContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ArcaneAnvilContainer extends SimpleContainer {


    public ArcaneAnvilContainer(int id, World world, BlockPos pos, PlayerInventory playerInventory) {
        super(ArcaneTileEntities.arcane_anvil_container, id);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }


    @Override
    protected void addSlots() {
        addSlot(new Slot(inv))
    }
}
