package com.dpeter99.ArcaneRituals.tileentity;

import com.dpeter99.ArcaneRituals.crafting.AltarContext;
import com.dpeter99.ArcaneRituals.crafting.AltarRecipe;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WitchAltarTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

    //private LazyOptional<IItemHandler> lazy_inventory = LazyOptional.of(this::createHandler);
    //IItemHandler item_handler;

    public final ItemStackHandler inventory = new ItemStackHandler(5) {

        @Override
        protected int getStackLimit(int slot, ItemStack stack)
        {
            return 1;
        }

        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            markDirty();
            needRefreshRecipe = true;
        }

        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            if(!isWorking()) {
                return super.insertItem(slot, stack, simulate);
            }
            else{
                return stack;
            }
        }
    };

    private final LazyOptional<IItemHandler> inventory_provider = LazyOptional.of(() -> inventory);


    private boolean needRefreshRecipe = true;


    private String current_recipe;

    private int bloodLevel = 0;
    private int progress = 0;

    private boolean isWorking() {
        return this.progress > 0;
    }

    protected final IIntArray altarData = new IIntArray() {
        public int get(int index) {
            switch (index) {
                case 0:
                    return bloodLevel;
                default:
                    return 0;
            }
        }

        public void set(int index, int value) {
            switch (index) {
                case 0:
                    bloodLevel = value;
                default:
            }

        }

        public int size() {
            return 1;
        }
    };


    public WitchAltarTileEntity() {
        super(ArcaneTileEntities.witch_altar);
    }

    public int getBloodLevel() {
        return bloodLevel;
    }

    public boolean hasSpaceForBlood() {
        return bloodLevel < 100;
    }

    public void setBloodLevel(int bloodLevel) {
        this.bloodLevel = bloodLevel;
        this.markDirty();
    }

    public void addBlood(int i) {
        bloodLevel += i;
        this.markDirty();
    }


    @Override
    public void tick() {
        boolean flag = false;

        if (world.isRemote)
            return;

        if (needRefreshRecipe) {

            findRecipe();
            if (current_recipe != null) {
                System.out.println("Recipe: " + current_recipe);
            }
            needRefreshRecipe = false;
            flag = true;
        }

        if (progress > 0) {
            progress--;
            if(progress <= 0 && current_recipe != null){
                System.out.println("Recipe finished: " + current_recipe);
                inventory.setStackInSlot(4,getCurrent_recipe().getRecipeOutput());
                current_recipe = null;
                flag = true;
            }
        }

        if(flag){
            this.markDirty();
        }

    }

    private void findRecipe() {
        if(isWorking()) {
            System.out.printf("How did we get here");
            return;
        }

        AltarRecipe recipe = AltarRecipe.getRecipe(world, new AltarContext(inventory)).orElse(null);
        if (recipe != null) {
            current_recipe = recipe.getId().toString();
            System.out.println("Recipe found: " + recipe.getId().toString());
            progress = 100;
            inventory.setStackInSlot(0,ItemStack.EMPTY);
            inventory.setStackInSlot(1,ItemStack.EMPTY);
            inventory.setStackInSlot(2,ItemStack.EMPTY);
            inventory.setStackInSlot(3,ItemStack.EMPTY);
            inventory.setStackInSlot(4,ItemStack.EMPTY);
        }
    }

    private AltarRecipe getCurrent_recipe(){
        return (AltarRecipe) world.getRecipeManager().getRecipe(ResourceLocation.tryCreate(current_recipe)).orElse(null);
    }

    @Override
    public void read(CompoundNBT nbt) {
        CompoundNBT invTag = nbt.getCompound("inventory");
        inventory.deserializeNBT(invTag);

        bloodLevel = nbt.getInt("bloodLevel");
        progress = nbt.getInt("progress");

        current_recipe = nbt.getString("current_recipe");

        super.read(nbt);
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt) {
        CompoundNBT inv_tag = inventory.serializeNBT();

        nbt.put("inventory", inv_tag);

        nbt.putInt("bloodLevel", bloodLevel);
        nbt.putInt("progress", progress);

        nbt.putString("current_recipe", current_recipe != null ? current_recipe : "");
        return super.write(nbt);
    }


    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent(getType().getRegistryName().getPath());
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new WitchAltarContainer(i, world, pos, playerInventory, altarData);
    }

/*
    private IItemHandler createHandler() {
        item_handler = new ItemStackHandler(5){
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return true;// stack.getItem() == Items.DIAMOND;
            }

            @Override
            protected void onContentsChanged(int slot)
            {
                super.onContentsChanged(slot);
                markDirty();
                needRefreshRecipe = true;
            }
        };
        return item_handler;
    }
*/

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return inventory_provider.cast();
        }
        return super.getCapability(cap, side);
    }

}
