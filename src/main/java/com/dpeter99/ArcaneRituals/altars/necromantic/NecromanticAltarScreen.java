package com.dpeter99.ArcaneRituals.altars.necromantic;

import com.dpeter99.ArcaneRituals.Arcanerituals;
import com.dpeter99.ArcaneRituals.util.ui.SimpleScreen;
import com.dpeter99.ArcaneRituals.util.ui.TextureRegion;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class NecromanticAltarScreen extends SimpleScreen<NecromanticAltarContainer> {

    private ResourceLocation GUI = new ResourceLocation(Arcanerituals.MODID, "textures/gui/witch_altar.png");

    private static final int WIDTH = 176;
    private static final int HEIGHT = 225;

    public NecromanticAltarScreen(NecromanticAltarContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);

        this.ySize = HEIGHT;
        this.xSize = WIDTH;
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

    /**
     * Draws the background layer of this container (behind the items).
     *
     * @param partialTicks
     * @param mouseX
     * @param mouseY
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI);

        TextureRegion background = new TextureRegion(0,0,WIDTH,HEIGHT);
    }
}
