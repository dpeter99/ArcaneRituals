package com.dpeter99.ArcaneRituals.altars.necromantic;

import com.dpeter99.ArcaneRituals.Arcanerituals;
import com.dpeter99.ArcaneRituals.screen.GlyphDrawer;
import com.dpeter99.ArcaneRituals.util.ui.SimpleScreen;
import com.dpeter99.ArcaneRituals.util.ui.TextureRegion;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.List;

public class NecromanticAltarScreen extends SimpleScreen<NecromanticAltarContainer> {

    private ResourceLocation GUI = new ResourceLocation(Arcanerituals.MODID, "textures/gui/necromantic_altar.png");

    private static final int WIDTH = 176;
    private static final int HEIGHT = 225;

    GlyphDrawer glyphs;

    List<TextureRegion> fluidStates = new ArrayList<TextureRegion>();

    public NecromanticAltarScreen(NecromanticAltarContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);

        this.ySize = HEIGHT;
        this.xSize = WIDTH;

        InitGlyphs(screenContainer);
        FluidIndicatorSetup();
    }

    private void InitGlyphs(NecromanticAltarContainer screenContainer) {
        glyphs = new GlyphDrawer(this, screenContainer.getGlypSeed());

        for (int i = 0; i < 8; i++) {
            int startX = i * 12;
            glyphs.addGlyph(new TextureRegion(startX, 232, 12, 12), false);
        }

        for (int i = 0; i < 8; i++) {
            int startX = i * 12;
            glyphs.addGlyph(new TextureRegion(startX, 244, 12, 12), true);
        }

        glyphs.addGlyphPos(67, 15);
        glyphs.addGlyphPos(53, 22);
        glyphs.addGlyphPos(42, 33);
        glyphs.addGlyphPos(35, 47);

        glyphs.addGlyphPos(35, 84);
        glyphs.addGlyphPos(42, 98);
        glyphs.addGlyphPos(53, 109);
        glyphs.addGlyphPos(67, 116);

        glyphs.addGlyphPos(97, 116);
        glyphs.addGlyphPos(111, 109);
        glyphs.addGlyphPos(122, 98);
        glyphs.addGlyphPos(129, 84);

        glyphs.addGlyphPos(129, 47);
        glyphs.addGlyphPos(122, 33);
        glyphs.addGlyphPos(111, 22);
        glyphs.addGlyphPos(97, 15);

        glyphs.Randomize();
    }

    private void FluidIndicatorSetup() {
        fluidStates.add(new TextureRegion(180, 0, 22, 22));
        fluidStates.add(new TextureRegion(180, 22, 44, 44));
        fluidStates.add(new TextureRegion(180, 66, 59, 61));
        fluidStates.add(new TextureRegion(176, 128, 80, 80));
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

        drawBackground();
        drawFluid(container.getFluidAmount());
        drawGlyps();
    }

    private void drawBackground() {
        int relX = (this.width - WIDTH) / 2;
        int relY = (this.height - HEIGHT) / 2;
        this.blit(relX, relY, 0, 0, WIDTH, HEIGHT);
    }

    private void drawFluid(int level) {
        TextureRegion source = null;
        if (level > 0 && level <= 25) {
            source = fluidStates.get(0);
        } else if (level > 25 && level <= 50) {
            source = fluidStates.get(1);
        } else if (level > 50 && level <= 75) {
            source = fluidStates.get(2);
        } else if (level > 75 && level <= 100) {
            source = fluidStates.get(3);
        }
        if (source != null) {
            int toX = getGuiLeft() + 88 - (source.getSizeX() / 2);
            int toY = getGuiTop() + 72 - (source.getSizeY() / 2);

            this.blit_help(toX, toY, source);
        }
    }

    private void drawGlyps() {
        float p = 1 - ((float) container.getProgress() / container.getProgressFrom());
        float step = 1.0f / 17;

        glyphs.drawGlyps(5);
    }
}
