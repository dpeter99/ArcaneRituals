package com.dpeter99.ArcaneRituals.altars;

import com.dpeter99.ArcaneRituals.util.container.SimpleContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nullable;

public abstract class AbstractAltarContainer<T extends AbstractAltarTileEntity> extends SimpleContainer {

    protected T tileEntity;
    private final IIntArray altarData;

    private int seed;

    public AbstractAltarContainer(@Nullable ContainerType<?> type, int id, World world, BlockPos pos, PlayerInventory playerInventory) {
        this(type,id, world, pos, playerInventory, new IntArray(4));
    }

    protected AbstractAltarContainer(@Nullable ContainerType<?> type, int id, World world, BlockPos pos, PlayerInventory playerInventory, IIntArray altarData) {
        super(type, id);

        this.playerInventory = new InvWrapper(playerInventory);
        this.tileEntity = (T) world.getTileEntity(pos);

        this.altarData = altarData;
        trackIntArray(altarData);

    }

    public int getProgress(){return altarData.get(AbstractAltarTileEntity.PROGRESS);}

    public int getProgressFrom(){return altarData.get(AbstractAltarTileEntity.PROGRESS_FROM);}

    public int getGlypSeed(){
        return seed;
    }

    public int getFluidAmount() { return altarData.get(AbstractAltarTileEntity.FOCUSE_AMOUNT);}

    /**
     * Determines whether supplied player can use this container
     *
     * @param playerIn
     */
    @Override
    public abstract boolean canInteractWith(PlayerEntity playerIn) ;




}
