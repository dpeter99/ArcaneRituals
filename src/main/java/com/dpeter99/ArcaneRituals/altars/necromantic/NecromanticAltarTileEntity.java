package com.dpeter99.ArcaneRituals.altars.necromantic;

import com.dpeter99.ArcaneRituals.altars.AbstractAltarTileEntity;
import com.dpeter99.ArcaneRituals.tileentity.ArcaneTileEntities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nullable;

public class NecromanticAltarTileEntity extends AbstractAltarTileEntity {



    public NecromanticAltarTileEntity() {
        super(ArcaneTileEntities.necromantic_altar);
    }

    @Override
    protected int getArcaneFuelAmount() {
        return 0;
    }

    @Override
    protected void removeArcaneFuel(int amount) {

    }

    @Override
    protected String getAltarType() {
        return "necromantic_altar";
    }

    @Override
    public ITextComponent getDisplayName() {
        return null;
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return null;
    }
}
