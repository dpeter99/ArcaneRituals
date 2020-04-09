package com.dpeter99.ArcaneRituals.tileentity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
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

    public WitchAltarTileEntity() {
        super(ArcaneTileEntities.witch_altar);
    }

    @Override
    public void tick() {
        if(world.isRemote){
            //System.out.println("TEST");
        }
    }

    @Override
    public void read(CompoundNBT compound) {
        CompoundNBT tag = compound.getCompound("inventory");
        getHandler().deserializeNBT(tag);
        super.read(compound);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        CompoundNBT tag = handler.serializeNBT();
        compound.put("inventory",tag);
        return super.write(compound);
    }

    private ItemStackHandler getHandler(){
        if(handler == null){
            handler = new ItemStackHandler(1){
                @Override
                public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                    return stack.getItem() == Items.DIAMOND;
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
        return new WitchAltarContainer(i, world, pos, playerInventory, playerEntity);
    }
}
