package com.dpeter99.ArcaneRituals.tileentity;

import com.dpeter99.ArcaneRituals.ArcaneTileEntities;
import com.dpeter99.ArcaneRituals.block.ArcaneBlocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import java.util.Random;

public class WitchAltarContainer extends Container
{
    private WitchAltarTileEntity tileEntity;
    private IItemHandler playerInventory;
    private final IIntArray altarData;

    private int seed;

    public WitchAltarContainer(int id, World world, BlockPos pos, PlayerInventory playerInventory) {
        this(id, world, pos, playerInventory, new IntArray(4));
    }


    public WitchAltarContainer(int id, World world, BlockPos pos, PlayerInventory playerInventory, IIntArray altarData) {
        super(ArcaneTileEntities.witch_altar_continer, id);

        this.tileEntity = (WitchAltarTileEntity) world.getTileEntity(pos);
        this.playerInventory = new InvWrapper( playerInventory);
        this.altarData = altarData;

        tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
            addSlot(new SlotItemHandler(h, 0, 28, 12));
            addSlot(new SlotItemHandler(h, 1, 28, 116));
            addSlot(new SlotItemHandler(h, 2, 132, 116));
            addSlot(new SlotItemHandler(h, 3, 132, 12));
            addSlot(new SlotItemHandler(h, 4, 80, 13));
        });
        layoutPlayerInventorySlots(5,8,144);

        trackIntArray(altarData);

        seed = new Random().nextInt(10000000);
    }

    public int getBloodLevel() {
        return altarData.get(0);
    }

    public int getProgress(){return altarData.get(1);}

    public int getProgressFrom(){return altarData.get(2);}

    public int getGlypSeed(){
        return seed;
    }

    public WitchAltarTileEntity getTileEntity(){
        return (WitchAltarTileEntity)tileEntity;
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            itemstack = stack.copy();
            if (index <= 5) {
                if (!this.mergeItemStack(stack, 6, 37, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(stack, itemstack);
            }
            if (stack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (stack.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, stack);
        }

        return itemstack;
    }

    /**
     * Determines whether supplied player can use this container
     *
     * @param playerIn
     */
    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(),tileEntity.getPos()),playerIn, ArcaneBlocks.witch_altar);
    }

    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0 ; i < amount ; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0 ; j < verAmount ; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    private void layoutPlayerInventorySlots(int index, int leftCol, int topRow) {
        // Player inventory
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }
}
