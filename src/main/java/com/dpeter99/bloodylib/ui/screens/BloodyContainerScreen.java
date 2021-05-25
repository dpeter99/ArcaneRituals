package com.dpeter99.bloodylib.ui.screens;

import com.dpeter99.bloodylib.math.Vector2i;
import com.dpeter99.bloodylib.ui.Sprite;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.List;

public class BloodyContainerScreen<TILE extends Container> extends ContainerScreen<TILE> {

    private ResourceLocation GUI;

    private List<GraphicalElement> graphicalElements = new ArrayList<>();


    protected int sourceAtlasHeight = 256;
    protected int sourceAtlasWidth = 256;

    public BloodyContainerScreen(TILE p_i51105_1_, PlayerInventory p_i51105_2_, ITextComponent p_i51105_3_, ResourceLocation guiAtlas) {
        super(p_i51105_1_, p_i51105_2_, p_i51105_3_);

        GUI = guiAtlas;
    }

    protected void addWidget(GraphicalElement graphicalElement){
        graphicalElement.init(this);
        graphicalElements.add(graphicalElement);
    }


    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack,mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack,mouseX,mouseY);
    }

    @Override
    protected void renderLabels(MatrixStack matrixStack,int mouseX, int mouseY) {
        //drawString(Minecraft.getInstance().fontRenderer, "Energy: ", 10, 10, 0xffffff);
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(GUI);

        drawBackground(matrixStack);

        for (GraphicalElement el :
                graphicalElements) {
            el.render(matrixStack,mouseX,mouseY,partialTicks);
        }
        
    }

    private void drawBackground(MatrixStack matrixStack) {
        this.blit(matrixStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
    }


    public void blit(MatrixStack matrixStack, int destX, int destY, Sprite source) {
        Vector2i start = source.getStart();
        Vector2i size = source.getSize();

        this.blit(matrixStack, destX, destY, this.getBlitOffset(), start.x, start.y, size.x, size.y, sourceAtlasHeight, sourceAtlasWidth);
    }

}
