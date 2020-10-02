package com.dpeter99.ArcaneRituals.util.ui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

@Deprecated
public abstract class SimpleScreen<T extends Container> extends ContainerScreen<T> {

    public int sourceHeight= 256;
    public int sourceWidth= 256;

    public ResourceLocation GUI;


    public SimpleScreen(T screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    public void blit_help(MatrixStack matrixStack, int destX, int destY, TextureRegion_old source) {
        this.blit(matrixStack, destX, destY, this.getBlitOffset(), source.getStartX(), source.getStartY(), source.getSizeX(), source.getSizeY(), sourceHeight, sourceWidth);
    }

}
