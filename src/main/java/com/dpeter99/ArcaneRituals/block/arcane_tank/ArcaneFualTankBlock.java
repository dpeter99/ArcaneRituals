package com.dpeter99.ArcaneRituals.block.arcane_tank;

import com.dpeter99.ArcaneRituals.block.arcane_anvil.ArcaneAnvilTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class ArcaneFualTankBlock extends Block {

    public ArcaneFualTankBlock(Properties properties) {
        super(properties.notSolid());
    }



    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new ArcaneFuelTankTileEntity();
    }


}
