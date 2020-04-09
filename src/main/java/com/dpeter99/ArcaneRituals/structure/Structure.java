package com.dpeter99.ArcaneRituals.structure;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

interface IStructure {

    boolean detectStructure(World world, BlockPos pos);

    void createStructure(World world, BlockPos pos);
}
