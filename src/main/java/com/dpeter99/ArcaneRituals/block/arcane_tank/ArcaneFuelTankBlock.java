package com.dpeter99.ArcaneRituals.block.arcane_tank;

import com.dpeter99.ArcaneRituals.block.arcane_anvil.ArcaneAnvilTileEntity;
import com.dpeter99.bloodylib.block.BlockWithTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class ArcaneFuelTankBlock extends BlockWithTileEntity {

    public ArcaneFuelTankBlock(Properties properties) {
        super(properties.notSolid());

    }



}
