package com.dpeter99.ArcaneRituals;

import com.dpeter99.ArcaneRituals.altars.demonic.DemonicAltarContainer;
import com.dpeter99.ArcaneRituals.altars.demonic.DemonicAltarTileEntity;
import com.dpeter99.ArcaneRituals.altars.necromantic.NecromanticAltarContainer;
import com.dpeter99.ArcaneRituals.altars.necromantic.NecromanticAltarTileEntity;
import com.dpeter99.ArcaneRituals.block.arcane_anvil.ArcaneAnvilContainer;
import com.dpeter99.ArcaneRituals.block.arcane_anvil.ArcaneAnvilTileEntity;

import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder("arcanerituals")
public class ArcaneTileEntities {



    public static final TileEntityType<NecromanticAltarTileEntity> necromantic_altar = null;
    public static final ContainerType<NecromanticAltarContainer> necromantic_altar_container = null;

    public static final TileEntityType<DemonicAltarTileEntity> demonic_altar = null;
    public static final ContainerType<DemonicAltarContainer> demonic_altar_container = null;



    public static final TileEntityType<ArcaneAnvilTileEntity> arcane_anvil = null;
    public static final ContainerType<ArcaneAnvilContainer> arcane_anvil_container = null;

    public static final TileEntityType<ArcaneAnvilTileEntity> small_tank_anvil = null;

}
