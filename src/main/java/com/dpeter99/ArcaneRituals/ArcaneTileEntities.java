package com.dpeter99.ArcaneRituals;

import com.dpeter99.ArcaneRituals.altars.demonic.DemonicAltarContainer;
import com.dpeter99.ArcaneRituals.altars.demonic.DemonicAltarTileEntity;
import com.dpeter99.ArcaneRituals.altars.necromantic.NecromanticAltarContainer;
import com.dpeter99.ArcaneRituals.altars.necromantic.NecromanticAltarTileEntity;
import com.dpeter99.ArcaneRituals.tileentity.TileArcaneAnvil;
import com.dpeter99.ArcaneRituals.tileentity.WitchAltarContainer;
import com.dpeter99.ArcaneRituals.tileentity.WitchAltarTileEntity;
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

    public static final TileEntityType<DemonicAltarTileEntity> demonic_altar = null;
    public static final ContainerType<DemonicAltarContainer> demonic_altar_container = null;



    public static final TileEntityType<TileArcaneAnvil> arcane_anvil = null;

}
