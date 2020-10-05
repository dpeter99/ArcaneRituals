package com.dpeter99.bloodylib;

import com.dpeter99.ArcaneRituals.ArcaneTileEntities;
import com.dpeter99.ArcaneRituals.Registries;
import com.dpeter99.ArcaneRituals.block.ArcaneBlocks;
import com.dpeter99.ArcaneRituals.block.arcane_tank.ArcaneFuelTankTileEntity;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Function;
import java.util.function.Supplier;

public class TileEntityBuilder {

    public static RegistryObject<TileEntityType<?>> Build(DeferredRegister<TileEntityType<?>> registry, Function<TileEntityType<?>,? extends TileEntity> tile, RegistryObject<Block> block){

        return registry.register(block.getId().getPath(), () -> TileEntityType.Builder.create(() -> createTE(tile, block.getId()), block.get()).build(null));

    }

    public static <T extends TileEntity> TileEntity createTE(Function<TileEntityType<?>,? extends TileEntity> tile, ResourceLocation name){

        TileEntityType<?> type = ForgeRegistries.TILE_ENTITIES.getValue(name);


        return tile.apply(type);

    }

}
