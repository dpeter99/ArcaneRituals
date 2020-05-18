package com.dpeter99.ArcaneRituals.util.ui;

import com.dpeter99.ArcaneRituals.tileentity.WitchAltarContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.ITextComponent;

public abstract class SimpleScreen<T extends Container> extends ContainerScreen<T> {

    public int sourceHeight= 256;
    public int sourceWidth= 256;

    public SimpleScreen(T screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    public void blit_help(int destX, int destY, TextureRegion source) {
        this.blit(destX, destY, this.getBlitOffset(), source.getStartX(), source.getStartY(), source.getSizeX(), source.getSizeY(), sourceHeight, sourceWidth);
    }

}
