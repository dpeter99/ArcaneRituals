package com.dpeter99.ArcaneRituals.block.arcane_tank;

import com.dpeter99.ArcaneRituals.fluid.ArcaneFluids;
import com.dpeter99.bloodylib.tileEntity.IActivateTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;
import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class ArcaneFuelTankTileEntity extends TileEntity implements IActivateTileEntity {

    public FluidTank tank;
    private final LazyOptional<IFluidHandler> fluid_provider = LazyOptional.of(() -> tank);

    //public ArcaneFuelTankTileEntity(TileEntityType<?> tileEntityTypeIn, int capacity, RegistryObject<Fluid> type) {
    public ArcaneFuelTankTileEntity(TileEntityType<?> tileEntityTypeIn, int capacity, Supplier<Fluid> type) {
        super(tileEntityTypeIn);

        tank = new FluidTank(capacity, (FluidStack s) -> { return s.getFluid().isEquivalentTo(type.get());} );
    }


    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        tank.readFromNBT(nbt.getCompound("tank"));

        super.read(state, nbt);
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt) {
        CompoundNBT tank_nbt = new CompoundNBT();
        tank.writeToNBT(tank_nbt);
        nbt.put("tank", tank_nbt);

        return super.write(nbt);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {

        if(cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
            return fluid_provider.cast();
        }

        return super.getCapability(cap, side);
    }


    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult p_225533_6_) {
        ItemStack current_item = player.inventory.getCurrentItem();

        LazyOptional<IFluidHandlerItem> handlerLazyOptional = current_item.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY);
        if (handlerLazyOptional.isPresent()){
            IFluidHandlerItem fluidHandler = handlerLazyOptional.orElse(null);

            FluidUtil.interactWithFluidHandler(player,handIn,tank);
        }

        return null;
    }
}
