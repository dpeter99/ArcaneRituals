package com.dpeter99.ArcaneRituals.screen;

import com.dpeter99.ArcaneRituals.Arcanerituals;
import com.dpeter99.ArcaneRituals.tileentity.WitchAltarContainer;
import com.dpeter99.ArcaneRituals.util.ui.TextureRegion;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WitchAltarScreen extends ContainerScreen<WitchAltarContainer> {

    private static final int WIDTH = 176;
    private static final int HEIGHT = 225;

    private ResourceLocation GUI = new ResourceLocation(Arcanerituals.MODID, "textures/gui/witch_altar.png");

    List<TextureRegion> glyps_normal = new ArrayList<>();
    List<TextureRegion> glyps_active = new ArrayList<>();
    Random r;
    int glyph_ids[] = new int[16];

    public WitchAltarScreen(WitchAltarContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.ySize = HEIGHT;
        this.xSize = WIDTH;
        //this.width = 512;

        glyps_normal.add(new TextureRegion(0, 232, 12, 12));
        glyps_normal.add(new TextureRegion(12, 232, 12, 12));
        glyps_normal.add(new TextureRegion(24, 232, 12, 12));
        glyps_normal.add(new TextureRegion(36, 232, 12, 12));
        glyps_normal.add(new TextureRegion(48, 232, 12, 12));
        glyps_normal.add(new TextureRegion(60, 232, 12, 12));
        glyps_normal.add(new TextureRegion(72, 232, 12, 12));
        glyps_normal.add(new TextureRegion(84, 232, 12, 12));

        glyps_active.add(new TextureRegion(0, 244, 12, 12));
        glyps_active.add(new TextureRegion(12, 244, 12, 12));
        glyps_active.add(new TextureRegion(24, 244, 12, 12));
        glyps_active.add(new TextureRegion(36, 244, 12, 12));
        glyps_active.add(new TextureRegion(48, 244, 12, 12));
        glyps_active.add(new TextureRegion(60, 244, 12, 12));
        glyps_active.add(new TextureRegion(72, 244, 12, 12));
        glyps_active.add(new TextureRegion(84, 244, 12, 12));

        r = new Random(container.getGlypSeed());
        for (int i = 0; i < 16; i++) {
            glyph_ids[i] = r.nextInt(8);
        }
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

        drawBackground();
        int level = container.getBloodLevel();

        drawBlood(level);

        drawGlyps();


        bloodTooltip(mouseX,mouseY);
    }

    private void drawGlyps() {

        float p = 1-((float)container.getProgress()/container.getProgressFrom());
        float step = 1.0f/17;

        drawGlyp(0, 67 + getGuiLeft(), 15 + getGuiTop(), p > step * 1);
        drawGlyp(1, 53 + getGuiLeft(), 22 + getGuiTop(), p > step * 2);
        drawGlyp(2, 42 + getGuiLeft(), 33 + getGuiTop(), p > step * 3);
        drawGlyp(3, 35 + getGuiLeft(), 47 + getGuiTop(), p > step * 4);

        drawGlyp(4, 35 + getGuiLeft(), 84 + getGuiTop(), p > step * 5);
        drawGlyp(5, 42 + getGuiLeft(), 98 + getGuiTop(), p > step * 6);
        drawGlyp(6, 53 + getGuiLeft(), 109 + getGuiTop(), p > step * 7);
        drawGlyp(7, 67 + getGuiLeft(), 116 + getGuiTop(), p > step * 8);

        drawGlyp(8, 97 + getGuiLeft(), 116 + getGuiTop(), p > step * 9);
        drawGlyp(9, 111 + getGuiLeft(), 109 + getGuiTop(), p > step * 10);
        drawGlyp(10, 122 + getGuiLeft(), 98 + getGuiTop(), p > step * 11);
        drawGlyp(11, 129 + getGuiLeft(), 84 + getGuiTop(), p > step * 12);

        drawGlyp(12, 129 + getGuiLeft(), 47 + getGuiTop(), p > step * 13);
        drawGlyp(13, 122 + getGuiLeft(), 33 + getGuiTop(), p > step * 14);
        drawGlyp(14, 111 + getGuiLeft(), 22 + getGuiTop(), p > step * 15);
        drawGlyp(15, 97 + getGuiLeft(), 15 + getGuiTop(), p > step * 16);
    }

    private void drawBackground() {
        int relX = (this.width - WIDTH) / 2;
        int relY = (this.height - HEIGHT) / 2;
        this.blit(relX, relY, 0, 0, WIDTH, HEIGHT);
    }

    private void drawBlood(int level) {
        int startX = 0;
        int startY = 0;
        int sizeX = 0;
        int sizeY = 0;
        int toX = getGuiLeft() + 88;
        int toY = getGuiTop() + 72;
        if (level > 0 && level <= 25) {
            startX = 180;
            startY = 0;
            sizeX = 22;
            sizeY = 22;
            //this.blit(relX + 88, relY + 49, 206, 0, 44, 44);
        } else if (level > 25 && level <= 50) {
            startX = 180;
            startY = 22;
            sizeX = 44;
            sizeY = 44;
            //this.blit(relX + 66, relY + 49, 206, 0, 44, 44);
        } else if (level > 50 && level <= 75) {
            startX = 180;
            startY = 66;
            sizeX = 59;
            sizeY = 61;
            //this.blit(relX + 66, relY + 49, 206, 0, 44, 44);
        } else if (level > 75 && level <= 100) {
            startX = 176;
            startY = 128;
            sizeX = 80;
            sizeY = 80;
            //this.blit(relX + 66, relY + 49, 206, 0, 44, 44);
        }

        this.blit(toX - (sizeX / 2), toY - (sizeY / 2), startX, startY, sizeX, sizeY);
    }

    private void drawGlyp(int i, int xPos, int yPos, boolean active) {
        TextureRegion glyp;
        if (active) glyp = glyps_active.get(glyph_ids[i]);
        else glyp = glyps_normal.get(glyph_ids[i]);

        blit_help(xPos, yPos, glyp);
    }

    public void blit_help(int destX, int destY, TextureRegion source) {
        this.blit(destX, destY, this.getBlitOffset(), source.getStartX(), source.getStartY(), source.getSizeX(), source.getSizeY(), 256, 256);
    }

    public void bloodTooltip(int mouseX, int mouseY){
        int bloodCenterX = getGuiLeft() + 88;
        int bloodCenterY = getGuiTop() + 72;
        float d = (float)Math.sqrt(Math.pow(bloodCenterX - mouseX,2) + Math.pow(bloodCenterY - mouseY,2));

        if(d <= 40) {
            List<String> text = new ArrayList<>();
            text.add("Blood");
            text.add(container.getBloodLevel() + "/100" );
            this.renderTooltip(text,mouseX,mouseY);
        }
    }

}
