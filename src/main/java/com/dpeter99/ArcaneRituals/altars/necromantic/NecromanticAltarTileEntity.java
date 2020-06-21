package com.dpeter99.ArcaneRituals.altars.necromantic;

import com.dpeter99.ArcaneRituals.altars.AbstractAltarTileEntity;
import com.dpeter99.ArcaneRituals.fluid.ArcaneFluids;
import com.dpeter99.ArcaneRituals.tileentity.ArcaneTileEntities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
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
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
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
    public void tick() {
        if (world.isRemote)
            return;

        boolean flag=false;
        if (newFluidItem) {

            LazyOptional<IFluidHandlerItem> capability = inventory.getStackInSlot(5).getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY);

            capability.ifPresent(
                    fluidInv ->
                    {
                        FluidStack drained = fluidInv.drain(tank.getCapacity() - tank.getFluidAmount(), IFluidHandler.FluidAction.SIMULATE);
                        if(tank.fill(drained, IFluidHandler.FluidAction.SIMULATE)>0) {
                            tank.fill(fluidInv.drain(tank.getCapacity() - tank.getFluidAmount(), IFluidHandler.FluidAction.EXECUTE), IFluidHandler.FluidAction.EXECUTE);
                            //inventory.setStackInSlot(5, fluidInv.getContainer());
                        }
                        newFluidItem = false;
                    }
            );
            flag = true;
        }
        if(flag){
            this.markDirty();
        }

        super.tick();
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


    /**
     * Retrieves packet to send to the client whenever this Tile Entity is resynced via World.notifyBlockUpdate. For
     * modded TE's, this packet comes back to you clientside in {@link #onDataPacket}
     */
    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, 3, this.getUpdateTag());
    }

    /**
     * Called when you receive a TileEntityData packet for the location this
     * TileEntity is currently in. On the client, the NetworkManager will always
     * be the remote server. On the server, it will be whomever is responsible for
     * sending the packet.
     *
     * @param net The NetworkManager the packet originated from
     * @param pkt The data packet
     */
    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        super.onDataPacket(net,pkt);
        this.read(pkt.getNbtCompound());
    }


    /**
     * Get an NBT compound to sync to the client with SPacketChunkData, used for initial loading of the chunk or when
     * many blocks change at once. This compound comes back to you clientside in {@link handleUpdateTag}
     */
    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    /**
     * Called when the chunk's TE update tag, gotten from {@link #getUpdateTag()}, is received on the client.
     * <p>
     * Used to handle this tag in a special way. By default this simply calls {@link #readFromNBT(NBTTagCompound)}.
     *
     * @param tag The {@link NBTTagCompound} sent from {@link #getUpdateTag()}
     */
    @Override
    public void handleUpdateTag(CompoundNBT tag) {
        read(tag);
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
