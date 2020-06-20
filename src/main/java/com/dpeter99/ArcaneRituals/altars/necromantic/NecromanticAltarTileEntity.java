package com.dpeter99.ArcaneRituals.altars.necromantic;

import com.dpeter99.ArcaneRituals.altars.AbstractAltarTileEntity;
import com.dpeter99.ArcaneRituals.tileentity.ArcaneTileEntities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nullable;

public class NecromanticAltarTileEntity extends AbstractAltarTileEntity {

    public Blood fuel = new Blood(0,"");

    public NecromanticAltarTileEntity() {
        super(ArcaneTileEntities.necromantic_altar);
    }

    @Override
    protected int getArcaneFuelAmount() {
        return fuel.amount;
    }

    @Override
    protected void removeArcaneFuel(int amount) {
        fuel.amount -= amount;
    }

    @Override
    protected String getAltarType() {
        return "necromantic_altar";
    }

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent(getType().getRegistryName().getPath());
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new NecromanticAltarContainer(i, world, pos, playerInventory, altarData);
    }
}
