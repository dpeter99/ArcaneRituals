package com.dpeter99.arcanerituals.tileentities;

import com.dpeter99.arcanerituals.containers.AltarContainer;
import com.dpeter99.arcanerituals.registry.ARRegistry;
import com.dpeter99.bloodylib.FluidHelper;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AltarTileEntity extends TileEntity implements INamedContainerProvider, ITickableTileEntity {

    public AltarTileEntity() {
        super(ARRegistry.DEMONIC_ALTAR_TE.get());
    }


    public FluidTank tank = new FluidTank(4000, (FluidStack s) -> { return s.getFluid().isSame(ARRegistry.BLOOD.get());} );


    /**
     * 0-3 : crafting slots
     * 4: center
     * 6: fluid in
     * 7: fluid out
     */
    public final ItemStackHandler inventory = new ItemStackHandler(7) {

        @Override
        protected int getStackLimit(int slot, ItemStack stack) {
            return 1;
        }

        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            setChanged();
            if (slot == 5) {
                //newFluidItem = true;
            }
            //needRefreshRecipe = true;
        }

        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            return super.insertItem(slot,stack,simulate);
        }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {

            if (slot == 5 || slot == 6) {
                return isFluidContainer(stack);
            } else {
                return super.isItemValid(slot, stack);
            }
        }


    };


    public static final int FUEL_AMOUNT = 0;
    public static final int FUEL_AMOUNT_MAX = 1;
    protected final IIntArray data = new IIntArray() {
        public int get(int index) {
            switch (index) {
                case FUEL_AMOUNT:
                    return tank.getFluidAmount();
                case FUEL_AMOUNT_MAX:
                    return AltarTileEntity.this.tank.getCapacity();
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

                default:
            }

        }

        public int getCount() {
            return 2;
        }
    };

    private final LazyOptional<IItemHandler> inventory_provider = LazyOptional.of(() -> inventory);
    private final LazyOptional<IFluidHandler> fluid_provider = LazyOptional.of(() -> tank);


    @Override
    public void tick() {
        ItemStack stack_in = inventory.getStackInSlot(5);
        if(stack_in != ItemStack.EMPTY && isFluidContainer(stack_in)){
            IFluidHandler fluid_handler = stack_in.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).resolve().get();
            if(!FluidHelper.isEmpty(fluid_handler))
                FluidUtil.tryFluidTransfer(tank,fluid_handler,1000,true);
        }

        ItemStack stack_out = inventory.getStackInSlot(6);
        if(stack_out != ItemStack.EMPTY && isFluidContainer(stack_out)){
            IFluidHandler fluid_handler = stack_out.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).resolve().get();
            if(FluidHelper.isEmpty(fluid_handler))
                FluidUtil.tryFluidTransfer(fluid_handler,tank,1000,true);
        }
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        CompoundNBT tank_nbt = new CompoundNBT();
        tank.writeToNBT(tank_nbt);
        nbt.put("tank", tank_nbt);

        CompoundNBT inv_tag = inventory.serializeNBT();
        nbt.put("inventory", inv_tag);

        return super.save(nbt);
    }

    @Override
    public void load(BlockState p_230337_1_, CompoundNBT nbt) {

        CompoundNBT tank_nbt = nbt.getCompound("tank");
        tank.readFromNBT(tank_nbt);

        CompoundNBT invTag = nbt.getCompound("inventory");
        inventory.deserializeNBT(invTag);

        super.load(p_230337_1_, nbt);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent(getType().getRegistryName().getPath());
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInv, PlayerEntity playerEntity) {
        return new AltarContainer(id,level,worldPosition,playerInv,data);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return inventory_provider.cast();
        }
        else if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return fluid_provider.cast();
        }
        return super.getCapability(cap, side);
    }

    public boolean isFluidContainer(@Nonnull ItemStack stack) {
        return stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).isPresent();
    }
}
