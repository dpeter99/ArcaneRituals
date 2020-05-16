package com.dpeter99.ArcaneRituals.screen;

import com.dpeter99.ArcaneRituals.Arcanerituals;
import com.dpeter99.ArcaneRituals.tileentity.WitchAltarContainer;
import com.dpeter99.ArcaneRituals.tileentity.WitchAltarTileEntity;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class WitchAltarScreen extends ContainerScreen<WitchAltarContainer> {

    private static final int WIDTH = 175;
    private static final int HEIGHT = 225;

    private ResourceLocation GUI = new ResourceLocation(Arcanerituals.MODID, "textures/gui/witch_altar.png");

    public WitchAltarScreen(WitchAltarContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.ySize = HEIGHT;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        //drawString(Minecraft.getInstance().fontRenderer, "Energy: ", 10, 10, 0xffffff);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI);
        int relX = (this.width - this.xSize) / 2;
        int relY = (this.height - HEIGHT) / 2;
        this.blit(relX, relY, 0, 0, this.xSize, HEIGHT);
        int level = container.getBloodLevel();

        int startX = 0;
        int startY = 0;
        int sizeX = 0;
        int sizeY = 0;
        int toX = relX + 88;
        int toY = relY + 72;
        if(level > 0 && level <= 25) {
            startX = 180;
            startY = 0;
            sizeX = 22;
            sizeY = 22;
            //this.blit(relX + 88, relY + 49, 206, 0, 44, 44);
        }
        else if(level > 25 && level <= 50) {
            startX = 180;
            startY = 22;
            sizeX = 44;
            sizeY = 44;
            //this.blit(relX + 66, relY + 49, 206, 0, 44, 44);
        }
        else if(level > 50 && level <= 75) {
            startX = 180;
            startY = 66;
            sizeX = 59;
            sizeY = 61;
            //this.blit(relX + 66, relY + 49, 206, 0, 44, 44);
        }
        else if(level > 75 && level <= 100) {
            startX = 176;
            startY = 128;
            sizeX = 80;
            sizeY = 80;
            //this.blit(relX + 66, relY + 49, 206, 0, 44, 44);
        }

        this.blit(toX - (sizeX/2), toY - (sizeY/2), startX, startY, sizeX, sizeY);
    }

    @Override
    public int getXSize() {
        return WIDTH;
    }

    @Override
    public int getYSize() {
        return HEIGHT;
    }

    @Override
    public int getGuiLeft() {
        return (this.width - this.xSize) / 2;
    }

    @Override
    public int getGuiTop() {
        return (this.height - HEIGHT) / 2;
    }
}
