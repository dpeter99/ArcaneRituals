package com.dpeter99.arcanerituals.tileentities;

import com.dpeter99.arcanerituals.containers.AltarContainer;
import com.dpeter99.arcanerituals.crafting.altarcrafting.AltarContext;
import com.dpeter99.arcanerituals.crafting.altarcrafting.AltarRecipe;
import com.dpeter99.arcanerituals.registry.ARRegistry;
import com.dpeter99.bloodylib.FluidHelper;
import com.dpeter99.bloodylib.StackUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AltarTileEntity extends TileEntity implements INamedContainerProvider, ITickableTileEntity {

    public AltarTileEntity() {
        super(ARRegistry.DEMONIC_ALTAR_TE.get());
    }

    protected int progress = 0;
    protected int progress_from = 0;

    public boolean isWorking(){
        return progress  > 0;
    }

    AltarRecipe recipe;

    public FluidTank tank = new FluidTank(4000, (FluidStack s) -> {
        return s.getFluid().isSame(ARRegistry.BLOOD.get());
    });


    /**
     * 0-3 : crafting slots
     * 4: center
     * 5: fluid in
     * 6: fluid out
     */
    public final ItemStackHandler inventory = new ItemStackHandler(7) {

        @Override
        protected int getStackLimit(int slot, ItemStack stack) {
            if (slot == 5 || slot == 6)
                return 64;
            return 1;
        }

        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            setChanged();
        }

        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            return super.insertItem(slot, stack, simulate);
        }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {

            if (slot == 5 || slot == 6) {
                return FluidHelper.isFluidContainer(stack);
            } else {
                return super.isItemValid(slot, stack);
            }
        }

        @NotNull
        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            if(isWorking()){
                return ItemStack.EMPTY;
            }
            return super.extractItem(slot, amount, simulate);
        }
    };


    public static final int FUEL_AMOUNT = 0;
    public static final int FUEL_AMOUNT_MAX = 1;
    public static final int PROGRESS = 2;
    public static final int PROGRESS_FROM = 3;
    protected final IIntArray data = new IIntArray() {
        public int get(int index) {
            switch (index) {
                case FUEL_AMOUNT:
                    return tank.getFluidAmount();
                case FUEL_AMOUNT_MAX:
                    return AltarTileEntity.this.tank.getCapacity();
                case PROGRESS:
                    return progress;
                case PROGRESS_FROM:
                    return progress_from;
                default:
                    return 0;
            }
        }

        public void set(int index, int value) {
            switch (index) {
                case FUEL_AMOUNT:
                    break;
                case FUEL_AMOUNT_MAX:
                    break;
                case PROGRESS:
                    progress = value;
                    break;
                case PROGRESS_FROM:
                    progress_from = value;
                    break;
                default:
            }

        }

        public int getCount() {
            return 4;
        }
    };

    private final LazyOptional<IItemHandler> inventory_provider = LazyOptional.of(() -> inventory);
    private final LazyOptional<IFluidHandler> fluid_provider = LazyOptional.of(() -> tank);


    private enum FluidDirection {
        Draining,
        Filling,
        NONE
    }

    private FluidDirection fluidDirection = FluidDirection.NONE;


    @Override
    public void tick() {
        if (level.isClientSide)
            return;

        ItemStack stack = inventory.getStackInSlot(5);
        if (!stack.isEmpty() && FluidHelper.isFluidContainer(stack)) {

            ItemStack itemStack = StackUtils.size(stack, 1);
            IFluidHandlerItem fluid_handler = FluidUtil.getFluidHandler(itemStack).resolve().get();
            //Fresh item
            if (fluidDirection == FluidDirection.NONE) {
                //The tank has something in it
                if (!FluidHelper.isEmpty(fluid_handler)) {
                    fluidDirection = FluidDirection.Draining;
                } else {
                    fluidDirection = FluidDirection.Filling;
                }
            }

            //transfer fluid from item to internal
            if (fluidDirection == FluidDirection.Draining) {
                drainFluid(stack, itemStack, fluid_handler);

            } else if (fluidDirection == FluidDirection.Filling) {
                //transfer out some fluid
                fillFluid(stack,itemStack,fluid_handler);
            }
        } else {
            fluidDirection = FluidDirection.NONE;
        }

        if(progress > 0){
            progress --;
            if(progress <= 0){

                inventory.setStackInSlot(0, ItemStack.EMPTY);
                inventory.setStackInSlot(1, ItemStack.EMPTY);
                inventory.setStackInSlot(2, ItemStack.EMPTY);
                inventory.setStackInSlot(3, ItemStack.EMPTY);
                inventory.setStackInSlot(4, ItemStack.EMPTY);

                tank.drain(recipe.fuel.getAmount(), IFluidHandler.FluidAction.EXECUTE);

                inventory.setStackInSlot(4, recipe.result.copy());

                this.setChanged();
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 2);
            }
            this.setChanged();
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 2);
        }

        if(!inventory.getStackInSlot(4).isEmpty() && !isWorking()){
            recipe = AltarRecipe.getRecipe(level, getRecipeContext()).orElse(null);
            if(recipe != null){
                progress = recipe.work_amount;
                progress_from = recipe.work_amount;
            }
        }

    }

    protected AltarContext getRecipeContext() {
        return new AltarContext(inventory,"demonic_altar",tank.getFluidInTank(0));
    }

    private void drainFluid(ItemStack stack, ItemStack itemStack, IFluidHandlerItem fluid_handler) {
        FluidStack simStack = FluidUtil.tryFluidTransfer(tank, fluid_handler, Integer.MAX_VALUE, false);

        FluidStack drained = fluid_handler.drain(simStack.getAmount(), IFluidHandler.FluidAction.EXECUTE);


        if(stack.getCount() == 1){

            tank.fill(drained, IFluidHandler.FluidAction.EXECUTE);

            if(tank.getSpace() <= 0 || FluidHelper.isEmpty(fluid_handler)) {
                if(inventory.insertItem(6, itemStack, true).isEmpty()){
                    inventory.setStackInSlot(5,ItemStack.EMPTY);
                    inventory.insertItem(6,itemStack, false);
                }
            }
            else {
                inventory.setStackInSlot(5, itemStack);
            }
        }
        else {
            if (inventory.insertItem(6, itemStack, true).isEmpty()) {

                //FluidUtil.tryFluidTransfer(tank, fluid_handler, amount, true);
                tank.fill(drained, IFluidHandler.FluidAction.EXECUTE);

                if (FluidHelper.isEmpty(fluid_handler)) {
                    inventory.insertItem(6, itemStack, false);

                    stack.shrink(1);
                }

            }
        }

        this.setChanged();
        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 2);
    }

    private void fillFluid(ItemStack stack, ItemStack itemStack, IFluidHandlerItem fluid_handler){
        FluidStack simStack = FluidUtil.tryFluidTransfer(fluid_handler, tank, Integer.MAX_VALUE, false);

        int drained = fluid_handler.fill(simStack, IFluidHandler.FluidAction.EXECUTE);


        if(stack.getCount() == 1){

            tank.drain(drained, IFluidHandler.FluidAction.EXECUTE);

            if(FluidHelper.isFull(fluid_handler)) {
                if(inventory.insertItem(6, itemStack, true).isEmpty()){
                    inventory.setStackInSlot(5,ItemStack.EMPTY);
                    inventory.insertItem(6,itemStack, false);
                }
            }
            else {
                inventory.setStackInSlot(5, itemStack);
            }
        }
        else {
            if (inventory.insertItem(6, itemStack, true).isEmpty()) {

                //FluidUtil.tryFluidTransfer(tank, fluid_handler, amount, true);
                tank.drain(drained, IFluidHandler.FluidAction.EXECUTE);

                    inventory.insertItem(6, itemStack, false);

                    stack.shrink(1);
            }
        }

        this.setChanged();
        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 2);
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        CompoundNBT tank_nbt = new CompoundNBT();
        tank.writeToNBT(tank_nbt);
        nbt.put("tank", tank_nbt);

        CompoundNBT inv_tag = inventory.serializeNBT();
        nbt.put("inventory", inv_tag);

        nbt.putInt("progress", progress);
        nbt.putInt("progress_from", progress_from);

        return super.save(nbt);
    }

    @Override
    public void load(BlockState p_230337_1_, CompoundNBT nbt) {

        CompoundNBT tank_nbt = nbt.getCompound("tank");
        tank.readFromNBT(tank_nbt);

        CompoundNBT invTag = nbt.getCompound("inventory");
        inventory.deserializeNBT(invTag);

        progress = nbt.getInt("progress");
        progress_from = nbt.getInt("progress_from");

        super.load(p_230337_1_, nbt);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent(getType().getRegistryName().getPath());
    }

    @Override
    public Container createMenu(int id, PlayerInventory playerInv, PlayerEntity playerEntity) {
        return new AltarContainer(id, level, worldPosition, playerInv, data);
    }

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return inventory_provider.cast();
        } else if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return fluid_provider.cast();
        }
        return super.getCapability(cap, side);
    }




    /**
     * Retrieves packet to send to the client whenever this Tile Entity is resynced via World.notifyBlockUpdate. For
     * modded TE's, this packet comes back to you clientside in {@link #onDataPacket}
     */
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.getBlockPos(), -1, this.getUpdateTag());
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
        this.load( level.getBlockState(pkt.getPos()) ,pkt.getTag());
    }

    /**
     * Get an NBT compound to sync to the client with SPacketChunkData, used for initial loading of the chunk or when
     * many blocks change at once. This compound comes back to you clientside in {@link #handleUpdateTag}
     */
    @Override
    public CompoundNBT getUpdateTag() {
        return this.save(new CompoundNBT());
    }
}
