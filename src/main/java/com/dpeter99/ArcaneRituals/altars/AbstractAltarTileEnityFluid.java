package com.dpeter99.ArcaneRituals.altars;

import com.dpeter99.ArcaneRituals.fluid.ArcaneFluids;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class AbstractAltarTileEnityFluid extends AbstractAltarTileEntity {

    public FluidTank tank = new FluidTank(4000, (FluidStack s) -> { return s.getFluid().isEquivalentTo(ArcaneFluids.blood.get());} );

    private final LazyOptional<IFluidHandler> fluid_provider = LazyOptional.of(() -> tank);


    public AbstractAltarTileEnityFluid(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    protected abstract Fluid getFluidType();

    @Override
    protected int getArcaneFuelAmount() {
        return tank.getFluidAmount();
    }

    @Override
    protected void removeArcaneFuel(int amount) {
        tank.drain(amount, IFluidHandler.FluidAction.EXECUTE);
    }


    @Override
    public void tick() {
        if (world.isRemote)
            return;

        if (newFluidItem) {

            LazyOptional<IFluidHandlerItem> capability = inventory.getStackInSlot(5).getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY);

            capability.ifPresent(
                    fluidInv ->
                    {
                        fillFrom(fluidInv);
                        newFluidItem = false;
                    }
            );
            //flag = true;
            this.markDirty();
            world.notifyBlockUpdate(pos,getBlockState(),getBlockState(),2);

        }

        super.tick();
    }

    public void fillFrom(IFluidHandlerItem fluidHandler) {

        if(fluidHandler.getFluidInTank(0).isEmpty()){

            int drained = fluidHandler.fill(tank.getFluid(), IFluidHandler.FluidAction.EXECUTE);
            tank.drain(drained, IFluidHandler.FluidAction.EXECUTE);
        }
        else {

            //Check if the fluid is the same as what we have
            if(fluidHandler.getFluidInTank(0).getFluid().isEquivalentTo(tank.getFluid().getFluid()) || tank.isEmpty()) {

                FluidStack drained = fluidHandler.drain(tank.getCapacity() - tank.getFluidAmount(), IFluidHandler.FluidAction.SIMULATE);

                if (tank.fill(drained, IFluidHandler.FluidAction.SIMULATE) > 0) {
                    tank.fill(fluidHandler.drain(tank.getCapacity() - tank.getFluidAmount(), IFluidHandler.FluidAction.EXECUTE), IFluidHandler.FluidAction.EXECUTE);
                    //inventory.setStackInSlot(5, fluidInv.getContainer());

                    world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), 2);
                }


            }
        }
    }

    public void fillFromRightClick(ItemStack item, PlayerEntity playerEntity) {

        ItemStack single_item = item.copy();
        single_item.setCount(1);

/*


        FluidUtil.getFluidHandler(single_item).ifPresent(
                fluidHandler -> {
                    //Check if the fluid is the same as what we have
                    if(fluidHandler.getFluidInTank(0).getFluid().isEquivalentTo(tank.getFluid().getFluid())) {

                        //See how much we can drain from the item
                        FluidStack drained = fluidHandler.drain(tank.getCapacity() - tank.getFluidAmount(), IFluidHandler.FluidAction.SIMULATE);
                        if(tank.fill(drained, IFluidHandler.FluidAction.SIMULATE)>0) {
                            tank.fill(fluidHandler.drain(tank.getCapacity() - tank.getFluidAmount(), IFluidHandler.FluidAction.EXECUTE), IFluidHandler.FluidAction.EXECUTE);

                            item.shrink(1);
                            world.notifyBlockUpdate(pos,getBlockState(),getBlockState(),2);
                        }

                    }
                }
        );
*/
        FluidUtil.getFluidHandler(single_item).ifPresent(
                this::fillFrom
        );

        item.shrink(1);

        playerEntity.inventory.addItemStackToInventory(single_item);

    }


    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
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
        return new SUpdateTileEntityPacket(this.pos, -1, this.getUpdateTag());
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
        this.read( world.getBlockState(pkt.getPos()) ,pkt.getNbtCompound());
    }

    /**
     * Get an NBT compound to sync to the client with SPacketChunkData, used for initial loading of the chunk or when
     * many blocks change at once. This compound comes back to you clientside in {@link #handleUpdateTag}
     */
    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
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
