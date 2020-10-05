package com.dpeter99.ArcaneRituals.altars.demonic;

import com.dpeter99.ArcaneRituals.item.ArcaneItems;
import com.dpeter99.ArcaneRituals.particles.ArcaneParticles;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.Random;

public class DemonicAltarBlock extends Block {

    protected static final VoxelShape BASE_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 13.0D, 16.0D);

    public DemonicAltarBlock() {
        super(Properties.create(Material.IRON)
                .notSolid());

        setRegistryName("demonic_altar");
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return BASE_SHAPE;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new DemonicAltarTileEntity();
    }


    @Override
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        //super.animateTick(stateIn, worldIn, pos, rand);

        DemonicAltarTileEntity tile = (DemonicAltarTileEntity)worldIn.getTileEntity(pos);
        if(tile.isWorking()) {

            double posX = (double) pos.getX() + 0.5;
            double posY = (double) pos.getY() + 0.5;
            double posZ = (double) pos.getZ() + 0.5;


            for (int i = 0; i < 30; i++) {

                double radian = Math.PI * 2 * rand.nextDouble();
                double r_posX = posX + (Math.cos(radian) * 1);
                double r_posY = posY;
                double r_posZ = posZ + (Math.sin(radian) * 1);

                double speed_X = r_posX - posX;
                double speed_Y = r_posY - posY;
                double speed_Z = r_posZ - posZ;

                worldIn.addParticle(ArcaneParticles.altar_demonic, posX, posY, posZ, speed_X, speed_Y, speed_Z);
                //worldIn.spawn
            }
        }

    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult p_225533_6_) {
        if (!worldIn.isRemote) {
            ItemStack current_item = player.inventory.getCurrentItem();
            DemonicAltarTileEntity tileEntity = (DemonicAltarTileEntity) worldIn.getTileEntity(pos);
            if(tileEntity != null){

                if (current_item.getItem().equals(ArcaneItems.vial)) {

                    tileEntity.fillFromRightClick(current_item, player);

                } else if (current_item.getItem() == ArcaneItems.basic_wand) {

                    tileEntity.startCrafting();

                } else if(!tileEntity.isWorking()) {
                    NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) tileEntity, tileEntity.getPos());
                }

            }
            else {
                throw new IllegalStateException("Our Tile Entity is missing!");
            }
            return ActionResultType.CONSUME;
        }
        return ActionResultType.CONSUME; // super.onBlockActivated(state, worldIn, pos, player, handIn, p_225533_6_);
    }
}
