package com.dpeter99.arcanerituals.screens.graphicalelements;


import com.dpeter99.bloodylib.math.Vector2i;
import com.dpeter99.bloodylib.ui.Sprite;
import com.dpeter99.bloodylib.ui.screens.GraphicalElement;
import com.mojang.blaze3d.matrix.MatrixStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class GlyphDrawer extends GraphicalElement {

    List<Glyph> glyphs = new ArrayList<>();
    private Predicate<Integer> activePredicate;


    public GlyphDrawer(int seed, List<Vector2i> pos, List<Sprite> active, List<Sprite> inactive, Predicate<Integer> activePred) {
        activePredicate = activePred;

        Random r = new Random(seed);

        for (int i = 0; i < pos.size(); i++) {
            int glyph = r.nextInt(active.size());
            glyphs.add(new Glyph(pos.get(i),active.get(glyph),inactive.get(glyph)));
        }

    }

    @Override
    public GraphicalElement.RenderLayer getRenderLayer() {
        return RenderLayer.bg;
    }

    @Override
    public void render(MatrixStack matrixStack, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
        for (int i = 0; i < glyphs.size(); i++) {
            Glyph g = glyphs.get(i);
            drawGlyp(matrixStack,g, activePredicate.test(i));
        }
    }

    private void drawGlyp(MatrixStack matrixStack, Glyph g, boolean active) {
        Vector2i glyph_pos = g.pos.add(Vector2i.of(screen.getGuiLeft(),screen.getGuiTop()));

        Sprite s = g.getSprite(active);

        screen.blit(matrixStack,glyph_pos.x,glyph_pos.y,s);
    }

    public static List<Sprite> glyphListHorizontal(Vector2i start, int count, int width, int height){
        List<Sprite> sprites = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            int startX = i * width;
            Vector2i pos = start.addX(startX);
            sprites.add(new Sprite(pos, Vector2i.of(width,height)));
        }

        return sprites;
    }


    public class Glyph{

        Vector2i pos;

        Sprite spriteOn;
        Sprite spriteOff;

        public Glyph(Vector2i pos, Sprite spriteOn, Sprite spriteOff) {
            this.pos = pos;
            this.spriteOn = spriteOn;
            this.spriteOff = spriteOff;
        }

        public Sprite getSprite(boolean active){
            if (active) return spriteOn;
            else return spriteOff;
        }
    }

}
