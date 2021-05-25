package com.dpeter99.arcanerituals.containers;

import com.dpeter99.arcanerituals.registry.ARRegistry;
import com.dpeter99.arcanerituals.tileentities.AltarTileEntity;
import com.dpeter99.bloodylib.ui.containers.SimpleContainer;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nullable;

public class AltarContainer  extends SimpleContainer {



    protected Block blocktype;


    public static AltarContainer createClientContainer(int id, PlayerInventory playerInventory, PacketBuffer packetBuffer) {
        BlockPos pos = packetBuffer.readBlockPos();
        return new AltarContainer(id,playerInventory.player.level,pos, playerInventory,new IntArray(4));
    }

    public AltarContainer(int id, World world, BlockPos pos, PlayerInventory playerInventory, IIntArray altarData) {
        super(ARRegistry.DEMONIC_ALTAR_CONTAINER.get(), id, playerInventory, world, pos);

        //this.playerInventory = new InvWrapper(playerInventory);


        //this.blocktype = blocktype;


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

    @Override
    public boolean stillValid(PlayerEntity playerEntity) {
        return stillValid(
                IWorldPosCallable.create(tileEntity.getLevel(), tileEntity.getBlockPos()),
                playerEntity,
                ARRegistry.DEMONIC_ALTAR.get());
    }
}
