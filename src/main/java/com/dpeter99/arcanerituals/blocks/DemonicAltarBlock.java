package com.dpeter99.arcanerituals.blocks;

import com.dpeter99.arcanerituals.registry.ARRegistry;
import com.dpeter99.arcanerituals.tileentities.AltarTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

import javax.annotation.Nullable;

public class DemonicAltarBlock extends BaseEntityBlock {
    
    protected static final VoxelShape SHAPE = Shapes.or(
            box(0, 0, 0, 16, 1, 16),
            box(0, 0, 0, 2, 11, 16),
            box(14, 0, 0, 16, 11, 16),
            box(0, 0, 0, 16, 11, 2),
            box(0, 0, 14, 16, 11, 16)
    );

    public DemonicAltarBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }
    
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    //TODO: Later
/*
    @Override
    public boolean hasBlockEntity(BlockState state) {
        return true;
    }


    @Nullable
    @Override
    public BlockEntity createTileEntity(BlockState state, BlockGetter world) {
        return new AltarTileEntity();
    }
    */

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult p_225533_6_) {
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

                NetworkHooks.openGui((ServerPlayer) player, (MenuProvider) tileEntity, tileEntity.getBlockPos());

            }
            else {
                throw new IllegalStateException("Our Tile Entity is missing!");
            }
            return InteractionResult.CONSUME;
        }
        return InteractionResult.CONSUME; // super.onBlockActivated(state, worldIn, pos, player, handIn, p_225533_6_);
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AltarTileEntity(pos, state);
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
        return createTickerHelper(p_153214_, ARRegistry.DEMONIC_ALTAR_TE.get(), AltarTileEntity::serverTick);
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public <T extends BlockEntity> GameEventListener getListener(Level p_153210_, T p_153211_) {
        return null;//EntityBlock.super.getListener(p_153210_, p_153211_);
    }
}
