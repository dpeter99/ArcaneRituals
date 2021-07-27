package com.dpeter99.bloodylib.ui.screens;

import com.dpeter99.bloodylib.math.Vector2i;
import com.dpeter99.bloodylib.ui.Sprite;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class BloodyContainerScreen<TILE extends AbstractContainerMenu> extends AbstractContainerScreen<TILE> {

    private ResourceLocation GUI;

    private List<GraphicalElement> graphicalElements = new ArrayList<>();


    protected int sourceAtlasHeight = 256;
    protected int sourceAtlasWidth = 256;

    public BloodyContainerScreen(TILE p_i51105_1_, Inventory p_i51105_2_, Component p_i51105_3_, ResourceLocation guiAtlas) {
        super(p_i51105_1_, p_i51105_2_, p_i51105_3_);

        GUI = guiAtlas;
    }

    protected void addWidget(GraphicalElement graphicalElement){
        graphicalElement.init(this);
        graphicalElements.add(graphicalElement);
    }

    protected void addVerticalOffset(int amount){
        if(this.topPos + amount > 0){
            this.topPos += amount;
        }
    }


    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack,mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack,mouseX,mouseY);
    }

    @Override
    protected void renderLabels(PoseStack matrixStack,int mouseX, int mouseY) {
        //drawString(Minecraft.getInstance().fontRenderer, "Energy: ", 10, 10, 0xffffff);
    }

    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        //this.minecraft.getTextureManager().bind(GUI);
        RenderSystem.setShaderTexture(0, GUI);

        drawBackground(matrixStack);

        for (GraphicalElement el :
                graphicalElements) {
            el.render(matrixStack,mouseX,mouseY,partialTicks);
        }
        
    }

    private void drawBackground(PoseStack matrixStack) {
        this.blit(matrixStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
    }


    public void blit(PoseStack matrixStack, int destX, int destY, Sprite source) {
        Vector2i start = source.getStart();
        Vector2i size = source.getSize();

        this.blit(matrixStack, destX, destY, this.getBlitOffset(), start.x, start.y, size.x, size.y, sourceAtlasHeight, sourceAtlasWidth);
    }

}
