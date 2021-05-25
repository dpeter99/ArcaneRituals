package com.dpeter99.arcanerituals.tileentities;

import com.dpeter99.arcanerituals.containers.AltarContainer;
import com.dpeter99.arcanerituals.registry.ARRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IIntArray;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AltarTileEntity extends TileEntity implements INamedContainerProvider {

    public AltarTileEntity() {
        super(ARRegistry.DEMONIC_ALTAR_TE.get());
    }

    public final ItemStackHandler inventory = new ItemStackHandler(6) {

        @Override
        protected int getStackLimit(int slot, ItemStack stack) {
            return 1;
        }

        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            //markDirty();
            if (slot == 5) {
                //newFluidItem = true;
            }
            //needRefreshRecipe = true;
        }

        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            /*
            if (!isWorking()) {
                return super.insertItem(slot, stack, simulate);
            } else {
                return super.insertItem(slot, stack, simulate);
                //return stack;
            }
             */
            return super.insertItem(slot,stack,simulate);
        }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            /*
            if (slot == 5) {
                return fluidContainer(stack);
            }
            if (isWorking()) {
                return false;
            } else {
                return super.isItemValid(slot, stack);
            }
            */
            return true;
        }

        public boolean fluidContainer(@Nonnull ItemStack stack) {
            return stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).isPresent();
        }
    };

    public static final int FUEL_AMOUNT = 0;
    protected final IIntArray data = new IIntArray() {
        public int get(int index) {
            switch (index) {
                case FUEL_AMOUNT:
                    return 1;// tank.getFluidAmount();
                default:
                    return 0;
            }
        }

        public void set(int index, int value) {
            switch (index) {
                case FUEL_AMOUNT:
                    //progress = fuel;
                default:
            }

        }

        public int getCount() {
            return 1;
        }
    };

    private final LazyOptional<IItemHandler> inventory_provider = LazyOptional.of(() -> inventory);

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent(getType().getRegistryName().getPath());
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInv, PlayerEntity playerEntity) {
        return new AltarContainer(id,level,worldPosition,playerInv,data);
    }
}
