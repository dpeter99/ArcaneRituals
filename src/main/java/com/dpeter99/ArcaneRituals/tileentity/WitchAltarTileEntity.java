package com.dpeter99.ArcaneRituals.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WitchAltarTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

    private ItemStackHandler handler;

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

    }

    @Override
    public void read(CompoundNBT compound) {
        CompoundNBT tag = compound.getCompound("inventory");
        getHandler().deserializeNBT(tag);

        bloodLevel = compound.getInt("bloodLevel");

        super.read(compound);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        CompoundNBT tag = handler.serializeNBT();
        compound.put("inventory",tag);
        compound.putInt("bloodLevel",bloodLevel);
        return super.write(compound);
    }

    private ItemStackHandler getHandler(){
        if(handler == null){
            handler = new ItemStackHandler(5){
                @Override
                public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                    return true;// stack.getItem() == Items.DIAMOND;
                }
            };
        }
        return handler;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return LazyOptional.of(()->(T) getHandler());
        }
        return super.getCapability(cap, side);
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


}
