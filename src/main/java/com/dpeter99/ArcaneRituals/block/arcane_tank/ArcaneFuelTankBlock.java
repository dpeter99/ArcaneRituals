package com.dpeter99.ArcaneRituals.block.arcane_tank;

import com.dpeter99.ArcaneRituals.block.arcane_anvil.ArcaneAnvilTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class ArcaneFuelTankBlock extends Block {

    //TileEntityType<?> tileEntityFactory;

    public ArcaneFuelTankBlock(/*TileEntityType<?> tileEntityFactory,*/ Properties properties) {
        super(properties.notSolid());

        //this.tileEntityFactory = tileEntityFactory;
    }


    //region Tile Entity

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ForgeRegistries.TILE_ENTITIES.getValue(this.getRegistryName()).create();
        //return tileEntityFactory.create();
    }

    //endregion

}
