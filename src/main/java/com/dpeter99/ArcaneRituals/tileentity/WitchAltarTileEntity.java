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

    private LazyOptional<IItemHandler> lazy_inventory = LazyOptional.of(this::createHandler);
    IItemHandler item_handler;

    private boolean needRefreshRecipe = true;

    private AltarRecipe current_recipe;


    private int bloodLevel = 0;

    protected final IIntArray altarData = new IIntArray() {
        public int get(int index) {
            switch (index){
                case 0:
                    return bloodLevel;
                default:
                    return 0;
            }
        }

        public void set(int index, int value) {
            switch(index) {
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

    public int getBloodLevel(){
        return bloodLevel;
    }

    public boolean hasSpaceForBlood(){
        return bloodLevel < 100;
    }

    public void setBloodLevel(int bloodLevel) {
        this.bloodLevel = bloodLevel;
        this.markDirty();
    }

    public void addBlood(int i){
        bloodLevel += i;
        this.markDirty();
    }



    @Override
    public void tick() {
        if (world.isRemote)
            return;

        if(needRefreshRecipe){
            System.out.println("WO HOooo");
            findRecipe();
            needRefreshRecipe = false;
        }
    }

    private void findRecipe(){
        current_recipe = AltarRecipe.getRecipe(world,new AltarContext((IItemHandlerModifiable)lazy_inventory.cast())).orElse(null);
    }

    @Override
    public void read(CompoundNBT nbt) {
        CompoundNBT invTag = nbt.getCompound("inv");
        lazy_inventory.ifPresent(h -> ((INBTSerializable<CompoundNBT>)h).deserializeNBT(invTag));

        bloodLevel = nbt.getInt("bloodLevel");

        super.read(nbt);
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt) {
        lazy_inventory.ifPresent(h -> {
            CompoundNBT compound = ((INBTSerializable<CompoundNBT>)h).serializeNBT();
            nbt.put("inv", compound);
        });

        //nbt.put("inventory",tag);
        nbt.putInt("bloodLevel",bloodLevel);
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


    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazy_inventory.cast();
        }
        return super.getCapability(cap, side);
    }

}
