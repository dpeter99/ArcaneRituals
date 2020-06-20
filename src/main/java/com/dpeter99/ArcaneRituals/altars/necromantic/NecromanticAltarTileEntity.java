package com.dpeter99.ArcaneRituals.altars.necromantic;

import com.dpeter99.ArcaneRituals.altars.AbstractAltarTileEntity;
import com.dpeter99.ArcaneRituals.fluid.ArcaneFluids;
import com.dpeter99.ArcaneRituals.tileentity.ArcaneTileEntities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.storage.loot.ILootConditionConsumer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class NecromanticAltarTileEntity extends AbstractAltarTileEntity {

    //public Blood fuel = new Blood(0,"");

    FluidTank tank = new FluidTank(4000, (FluidStack s) -> { return s.getFluid().isEquivalentTo(ArcaneFluids.blood);} );

    private LazyOptional<Object> fluid_provider;

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



    public NecromanticAltarTileEntity() {
        super(ArcaneTileEntities.necromantic_altar);
    }

    @Override
    public void read(CompoundNBT nbt) {
        super.read(nbt);
        CompoundNBT tank_nbt = nbt.getCompound("tank");
        tank.readFromNBT(tank_nbt);
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt) {
        CompoundNBT tank_nbt = new CompoundNBT();
        tank.writeToNBT(tank_nbt);
        nbt.put("tank", tank_nbt);

        return super.write(nbt);
    }

    @Override
    protected int getArcaneFuelAmount() {
        return tank.getFluidAmount();
    }

    @Override
    protected void removeArcaneFuel(int amount) {
        tank.drain(amount, IFluidHandler.FluidAction.EXECUTE);
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
        return new NecromanticAltarContainer(i, world, pos, playerInventory, altarData, data);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
         if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return fluid_provider.cast();
        }
        return super.getCapability(cap, side);
    }
}
