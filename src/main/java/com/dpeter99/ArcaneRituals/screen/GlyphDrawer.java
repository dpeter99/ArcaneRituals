package com.dpeter99.ArcaneRituals.screen;

import com.dpeter99.ArcaneRituals.util.ui.SimpleScreen;
import com.dpeter99.ArcaneRituals.util.ui.TextureRegion;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.math.vector.Vector2f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GlyphDrawer {

    SimpleScreen screen;

    public List<Vector2f> gliph_pos = new ArrayList<>();
    public List<TextureRegion> glyps_normal = new ArrayList<>();
    public List<TextureRegion> glyps_active = new ArrayList<>();
    Random r;
    public int glyph_ids[];
    boolean seeded;

    public GlyphDrawer(SimpleScreen screen, int seed) {
        this.screen = screen;


        r = new Random(seed);


    }

    public void Randomize(){
        glyph_ids = new int[gliph_pos.size()];
        for (int i = 0; i < 16; i++) {
            glyph_ids[i]= r.nextInt(8);
        }
    }

    public void addGlyphPos(int x, int y) {
        gliph_pos.add(new Vector2f(x, y));
    }

    public void addGlyph(TextureRegion textureRegion, boolean active) {
        if (active) {
            glyps_active.add(textureRegion);
        } else {
            glyps_normal.add(textureRegion);
        }
    }

    public void drawGlyps(MatrixStack matrixStack,int activatedCount) {
        for (int i = 0; i < gliph_pos.size(); i++) {
            drawGlyp(matrixStack,i, (int) gliph_pos.get(i).x, (int) gliph_pos.get(i).y, i < activatedCount);
        }
    }

    private void drawGlyp(MatrixStack matrixStack, int i, int xPos, int yPos, boolean active) {
        xPos += screen.getGuiLeft();
        yPos += screen.getGuiTop();

        TextureRegion glyp;
        if (active) glyp = glyps_active.get(glyph_ids[i]);
        else glyp = glyps_normal.get(glyph_ids[i]);

        screen.blit_help(matrixStack,xPos, yPos, glyp);
    }


}
