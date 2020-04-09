package com.dpeter99.ArcaneRituals.structure;

import com.dpeter99.ArcaneRituals.block.ArcaneBlocks;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.BlockStateMatcher;
import net.minecraft.state.IProperty;
import net.minecraft.util.CachedBlockInfo;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.structure.Structure;

public class BloodAltar implements IStructure {


    private BlockPattern pattern;

    @Override
    public boolean detectStructure(World world, BlockPos pos) {
        BlockPattern.PatternHelper patternhelper = getMatchPattern().match(world,pos);
        if (patternhelper != null) {
            return true;
        }
        return false;
    }

    @Override
    public void createStructure(World world, BlockPos pos) {
        BlockPattern.PatternHelper patternhelper = getMatchPattern().match(world,pos);
        if (patternhelper != null) {
            for (int i = 0; i < getMatchPattern().getPalmLength(); i++){
                CachedBlockInfo cachedblockinfo = patternhelper.translateOffset(i, 0, 0);
                world.playEvent(2001, cachedblockinfo.getPos(), Block.getStateId(cachedblockinfo.getBlockState()));
            }

            CachedBlockInfo cachedblockinfo = patternhelper.translateOffset(1, 0, 0);

            //BlockState blockstate1 = world.getBlockState(cachedblockinfo.getPos);
            BlockState b = cachedblockinfo.getBlockState();



            world.setBlockState(cachedblockinfo.getPos(), cachedblockinfo.getBlockState() );
        }

    }

    private BlockPattern getMatchPattern() {
        if (this.pattern == null) {
            this.pattern = BlockPatternBuilder.start()
                    .aisle("scs")
                    .where('c', CachedBlockInfo.hasState(BlockStateMatcher.forBlock(Blocks.COBBLESTONE)))
                    .where('s', CachedBlockInfo.hasState(BlockStateMatcher.forBlock(Blocks.COBBLESTONE_SLAB)))
                    .build();
        }

        return this.pattern;
    }

}
