package com.dpeter99.ArcaneRituals.block;

import com.dpeter99.ArcaneRituals.Registries;
import com.dpeter99.ArcaneRituals.block.arcane_tank.ArcaneFuelTankBlock;
import com.dpeter99.ArcaneRituals.block.arcane_tank.ArcaneFuelTankTileEntity;
import com.dpeter99.ArcaneRituals.fluid.ArcaneFluids;
import com.dpeter99.bloodylib.TileEntityBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;

public class ArcaneBlocks {


    public static final RegistryObject<Block> ARCANE_FUEL_TANK_SMALL_BLOCK =
            Registries.BLOCK_REGISTRY.register("arcane_fuel_tank_small",
                    () -> new ArcaneFuelTankBlock(AbstractBlock.Properties.create(Material.IRON)));

    public static final RegistryObject<TileEntityType<?>> ARCANE_FUEL_TANK_SMALL_TILE_ENTITY =
            TileEntityBuilder.Build(Registries.TILE_ENTITY_REGISTRY,
                    (t) -> new ArcaneFuelTankTileEntity(t, 10000, () -> ArcaneFluids.blood.get()),
                    ARCANE_FUEL_TANK_SMALL_BLOCK);

}
