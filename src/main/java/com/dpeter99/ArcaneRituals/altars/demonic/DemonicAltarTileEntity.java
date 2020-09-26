package com.dpeter99.ArcaneRituals.altars.demonic;

import com.dpeter99.ArcaneRituals.ArcaneTileEntities;
import com.dpeter99.ArcaneRituals.altars.AbstractAltarTileEnityFluid;
import com.dpeter99.ArcaneRituals.crafting.AltarContext;
import com.dpeter99.ArcaneRituals.crafting.AltarContextFluid;
import com.dpeter99.ArcaneRituals.fluid.ArcaneFluids;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.IIntArray;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nullable;

public class DemonicAltarTileEntity extends AbstractAltarTileEnityFluid {

    public static final int FUEL_AMOUNT = 0;
    protected final IIntArray data = new IIntArray() {
        public int get(int index) {
            switch (index) {
                case FUEL_AMOUNT:
                    return tank.getFluidAmount();
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

        public int size() {
            return 1;
        }
    };


    public DemonicAltarTileEntity() {
        super(ArcaneTileEntities.demonic_altar);
    }

    @Override
    protected Fluid getFluidType() {
        return ArcaneFluids.blood;
    }

    @Override
    protected String getAltarType() {
        return "demonic_altar";
    }

    @Override
    protected void duringCrafting() {

    }

    @Override
    protected AltarContext getContext() {
        return new AltarContextFluid(inventory,tank.getFluidInTank(0),getAltarType());
    }

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent(getType().getRegistryName().getPath());
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new DemonicAltarContainer(i, world, pos, playerInventory, altarData,data);
    }
}
