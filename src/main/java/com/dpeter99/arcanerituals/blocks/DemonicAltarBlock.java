package com.dpeter99.arcanerituals.blocks;

import com.dpeter99.arcanerituals.tileentities.AltarTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class DemonicAltarBlock extends Block {

    public DemonicAltarBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new AltarTileEntity();
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult p_225533_6_) {
        if (!worldIn.isClientSide) {
            //ItemStack current_item = player.inventory.getSelected();
            AltarTileEntity tileEntity = (AltarTileEntity) worldIn.getBlockEntity(pos);
            if(tileEntity != null){

                /*
                if (current_item.getItem().equals(ArcaneItems.vial.get())) {

                    tileEntity.fillFromRightClick(current_item, player);

                } else if (current_item.getItem() == ArcaneItems_old.basic_wand) {

                    tileEntity.startCrafting();

                } else if(!tileEntity.isWorking()) {
                    NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) tileEntity, tileEntity.getPos());
                }
                 */

                NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) tileEntity, tileEntity.getBlockPos());

            }
            else {
                throw new IllegalStateException("Our Tile Entity is missing!");
            }
            return ActionResultType.CONSUME;
        }
        return ActionResultType.CONSUME; // super.onBlockActivated(state, worldIn, pos, player, handIn, p_225533_6_);
    }

}
