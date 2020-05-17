package com.dpeter99.ArcaneRituals.tileentity;

import com.dpeter99.ArcaneRituals.altars.AbstractAltarTileEntity;
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

public class WitchAltarTileEntity extends AbstractAltarTileEntity {

    private int bloodLevel = 0;

    protected final IIntArray altarData = new IIntArray() {
        public int get(int index) {
            switch (index) {
                case 0:
                    return bloodLevel;
                case 1:
                    return progress;
                case 2:
                    return progress_from;
                default:
                    return 0;
            }
        }

        public void set(int index, int value) {
            switch (index) {
                case 0:
                    bloodLevel = value;
                case 1:
                    progress = value;
                case 2:
                    progress_from = value;
                default:
            }

        }

        public int size() {
            return 3;
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
        needRefreshRecipe = true;
        this.markDirty();
    }

    public void addBlood(int i) {
        bloodLevel += i;
        //needRefreshRecipe = true;
        this.markDirty();
    }

    @Override
    protected int getArcaneFuelAmount() {
        return bloodLevel;
    }

    @Override
    protected void removeArcaneFuel(int amount) {
        bloodLevel -= amount;
    }

    @Override
    protected String getAltarType() {
        return "witch_altar";
    }


    @Override
    public void read(CompoundNBT nbt) {
        bloodLevel = nbt.getInt("bloodLevel");

        super.read(nbt);
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt) {
        nbt.putInt("bloodLevel", bloodLevel);

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

}
