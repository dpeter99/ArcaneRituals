package com.dpeter99.arcanerituals.containers;

import com.dpeter99.arcanerituals.registry.ARRegistry;
import com.dpeter99.arcanerituals.tileentities.AltarTileEntity;
import com.dpeter99.bloodylib.ui.containers.SimpleContainer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class AltarContainer  extends SimpleContainer {



    protected Block blocktype;
    private ContainerData altarData;


    public static AltarContainer createClientContainer(int id, Inventory playerInventory, FriendlyByteBuf packetBuffer) {
        BlockPos pos = packetBuffer.readBlockPos();
        return new AltarContainer(id,playerInventory.player.level,pos, playerInventory,new SimpleContainerData(4));
    }

    public AltarContainer(int id, Level world, BlockPos pos, Inventory playerInventory, ContainerData altarData) {
        super(ARRegistry.DEMONIC_ALTAR_CONTAINER.get(), id, playerInventory, world, pos);

        this.altarData = altarData;
        //REALLY??? DATA SLOTS??? COMMON MOJANK
        addDataSlots(altarData);

        addSlots();
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
            addSlot(new SlotItemHandler(h, 6, 80+20, 116));
        });
        layoutPlayerInventorySlots(8,144);
    }

    @Override
    public boolean stillValid(Player playerEntity) {
        return stillValid(
                ContainerLevelAccess.create(tileEntity.getLevel(), tileEntity.getBlockPos()),
                playerEntity,
                ARRegistry.DEMONIC_ALTAR.get());
    }

    public int getFluidAmount() {
        return altarData.get(AltarTileEntity.FUEL_AMOUNT);
    }
    public int getMaxFluidAmount() {
        return altarData.get(AltarTileEntity.FUEL_AMOUNT_MAX);
    }

    public AltarTileEntity getTileEntity() {
        return (AltarTileEntity) tileEntity;
    }

    public int getProgress(){return altarData.get(AltarTileEntity.PROGRESS);}

    public int getProgressFrom(){return altarData.get(AltarTileEntity.PROGRESS_FROM);}
}