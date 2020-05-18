package com.dpeter99.ArcaneRituals.tileentity;

import com.dpeter99.ArcaneRituals.altars.necromantic.NecromanticAltarContainer;
import com.dpeter99.ArcaneRituals.altars.necromantic.NecromanticAltarTileEntity;
import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder("arcanerituals")
public class ArcaneTileEntities {

    public static final TileEntityType<WitchAltarTileEntity> witch_altar = null;
    public static final ContainerType<WitchAltarContainer> witch_altar_continer = null;

    public static final TileEntityType<NecromanticAltarTileEntity> necromantic_altar = null;
    public static final ContainerType<NecromanticAltarContainer> necromantic_altar_container = null;
    //public static final ContainerType<WitchAltarContainer> witch_altar_continer = null;

}
