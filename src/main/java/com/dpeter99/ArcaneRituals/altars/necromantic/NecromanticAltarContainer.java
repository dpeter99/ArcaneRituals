package com.dpeter99.ArcaneRituals.altars.necromantic;

import com.dpeter99.ArcaneRituals.altars.AbstractAltarContainer;
import com.dpeter99.ArcaneRituals.block.ArcaneBlocks_OLD;
import com.dpeter99.ArcaneRituals.ArcaneTileEntities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class NecromanticAltarContainer extends AbstractAltarContainer<NecromanticAltarTileEntity> {

    IIntArray necromanticData;

    public NecromanticAltarContainer(int id, World world, BlockPos pos, PlayerInventory playerInventory) {
        this(id, world, pos, playerInventory, new IntArray(4), new IntArray(1));
    }

    public NecromanticAltarContainer(int id, World world, BlockPos pos, PlayerInventory playerInventory, IIntArray altarData, IIntArray necromanticData) {
        super(ArcaneTileEntities.necromantic_altar_container, id,world,pos,playerInventory,altarData);

        this.necromanticData = necromanticData;
        trackIntArray(necromanticData);




    }

    @Override
    protected void addSlots() {
        tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
            addSlot(new SlotItemHandler(h, 0, 28, 12));
            addSlot(new SlotItemHandler(h, 1, 28, 116));
            addSlot(new SlotItemHandler(h, 2, 132, 116));
            addSlot(new SlotItemHandler(h, 3, 132, 12));
            addSlot(new SlotItemHandler(h, 4, 80, 13));

            addSlot(new SlotItemHandler(h, 5, 80, 116));
        });
        layoutPlayerInventorySlots(8,144);
    }

    /**
     * Determines whether supplied player can use this container
     *
     * @param playerIn
     */
    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(),tileEntity.getPos()),playerIn, ArcaneBlocks_OLD.necromantic_altar);
    }



}
