package com.dpeter99.ArcaneRituals.altars;

import com.dpeter99.ArcaneRituals.crafting.AltarContext;
import com.dpeter99.ArcaneRituals.crafting.AltarRecipe;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class AbstractAltarTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

    protected boolean needRefreshRecipe = true;
    protected boolean newFluidItem = false;

    public final ItemStackHandler inventory = new ItemStackHandler(6) {

        @Override
        protected int getStackLimit(int slot, ItemStack stack) {
            return 1;
        }

        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            markDirty();
            if (slot == 5) {
                newFluidItem = true;
            }
            //needRefreshRecipe = true;
        }

        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            if (!isWorking()) {
                return super.insertItem(slot, stack, simulate);
            } else {
                return super.insertItem(slot, stack, simulate);
                //return stack;
            }
        }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            if (slot == 5) {
                return fluidContainer(stack);
            }
            if (isWorking()) {
                return false;
            } else {
                return super.isItemValid(slot, stack);
            }

        }

        public boolean fluidContainer(@Nonnull ItemStack stack) {
            return stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).isPresent();
        }
    };

    private final LazyOptional<IItemHandler> inventory_provider = LazyOptional.of(() -> inventory);

    public static final int PROGRESS = 0;
    public static final int PROGRESS_FROM = 1;
    public static final int FOCUSE_AMOUNT = 2;
    protected final IIntArray altarData = new IIntArray() {
        public int get(int index) {
            switch (index) {
                case PROGRESS:
                    return progress;
                case PROGRESS_FROM:
                    return progress_from;
                case FOCUSE_AMOUNT:
                    return getArcaneFuelAmount();
                default:
                    return 0;
            }
        }

        public void set(int index, int value) {
            switch (index) {
                case PROGRESS:
                    progress = value;
                case PROGRESS_FROM:
                    progress_from = value;
                case FOCUSE_AMOUNT:
                    //Can't be set
                default:
            }

        }

        public int size() {
            return 3;
        }
    };


    protected int progress = 0;
    protected int progress_from = 0;

    private boolean isWorking() {
        return this.progress > 0;
    }

    private String current_recipe;

    private AltarRecipe getCurrent_recipe() {
        return (AltarRecipe) world.getRecipeManager().getRecipe(ResourceLocation.tryCreate(current_recipe)).orElse(null);
    }

    protected abstract int getArcaneFuelAmount();

    protected abstract void removeArcaneFuel(int amount);


    protected abstract String getAltarType();

    protected abstract void duringCrafting();



    public AbstractAltarTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    @Override
    public void tick() {
        boolean flag = false;

        if (world.isRemote)
            return;

        if (needRefreshRecipe) {

            checkRecipe();
            if (current_recipe != null) {
                System.out.println("Recipe: " + current_recipe);
            }
            needRefreshRecipe = false;
            flag = true;
        }

        if (progress > 0) {
            progress--;
            if (progress <= 0 && current_recipe != null) {
                System.out.println("Recipe finished: " + current_recipe);
                inventory.setStackInSlot(4, getCurrent_recipe().getRecipeOutput().copy());
                current_recipe = null;
                progress_from = 0;
                flag = true;
            }
        }

        if (flag) {
            this.markDirty();
            //TODO: Add the ability for subclasses to update without sending multiple
            world.notifyBlockUpdate(pos,getBlockState(),getBlockState(),2);
        }

    }



    public void startCrafting() {
        needRefreshRecipe = true;
    }

    private void checkRecipe() {
        if (isWorking()) {
            System.out.printf("How did we get here");
            return;
        }

        AltarRecipe recipe = AltarRecipe.getRecipe(world, new AltarContext(inventory, getArcaneFuelAmount(), getAltarType())).orElse(null);
        if (recipe != null) {
            current_recipe = recipe.getId().toString();
            System.out.println("Recipe found: " + recipe.getId().toString());
            progress = 100;
            progress_from = 100;
            inventory.setStackInSlot(0, ItemStack.EMPTY);
            inventory.setStackInSlot(1, ItemStack.EMPTY);
            inventory.setStackInSlot(2, ItemStack.EMPTY);
            inventory.setStackInSlot(3, ItemStack.EMPTY);
            inventory.setStackInSlot(4, ItemStack.EMPTY);
            removeArcaneFuel(recipe.fuel_amount);
        }
    }


    @Override
    public void read(CompoundNBT nbt) {
        CompoundNBT invTag = nbt.getCompound("inventory");
        inventory.deserializeNBT(invTag);

        progress = nbt.getInt("progress");
        progress_from = nbt.getInt("progress_from");

        current_recipe = nbt.getString("current_recipe");

        super.read(nbt);
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt) {
        CompoundNBT inv_tag = inventory.serializeNBT();
        nbt.put("inventory", inv_tag);

        nbt.putInt("progress", progress);
        nbt.putInt("progress_from", progress_from);

        nbt.putString("current_recipe", current_recipe != null ? current_recipe : "");
        return super.write(nbt);
    }



    public abstract ITextComponent getDisplayName();

    @Nullable
    public abstract Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return inventory_provider.cast();
        }
        return super.getCapability(cap, side);
    }
}
