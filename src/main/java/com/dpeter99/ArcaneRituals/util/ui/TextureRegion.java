package com.dpeter99.ArcaneRituals.util.ui;

import com.dpeter99.ArcaneRituals.util.Vector2i;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.AbstractGui;

public class TextureRegion implements IDrawable {
    Vector2i start;
    Vector2i size;

    Vector2i destStart;
    Vector2i destSize;

    SourceTexture source;

    public TextureRegion(SourceTexture source, int startX, int startY, int sizeX, int sizeY, int destX, int destY, int dest_sizeX, int dest_sizeY) {
        this.start = new Vector2i(startX, startY);
        this.size = new Vector2i(sizeX,sizeY);

        this.destStart = new Vector2i(destX,destY);
        this.destSize = new Vector2i(dest_sizeX,dest_sizeY);


        this.source = source;
    }

    public TextureRegion(SourceTexture source, int startX, int startY, int sizeX, int sizeY, int destX, int destY) {
        this(source,startX,startY,sizeX,sizeY,destX,destY,sizeX,sizeY);
    }


    public void blit(MatrixStack matrixStack, int destX, int destY, int blitoffset){
        AbstractGui.blit(matrixStack, destX, destY, blitoffset, start.x, start.y, size.x, size.y, source.height, source.width);
    }

    public void blitSized(MatrixStack matrixStack, int destX, int destY, int sizeX, int sizeY){

        AbstractGui.blit(matrixStack, destX, destY, sizeX, sizeY, start.x, start.y, this.size.x, this.size.y, source.width, source.height);

    }

    @Override
    public void draw(MatrixStack matrixStack, int guiLeft, int guiTop) {
        AbstractGui.blit(matrixStack, destStart.x + guiLeft, destStart.y + guiTop, destSize.x, destSize.y, start.x, start.y, this.size.x, this.size.y, source.width, source.height);
    }
}
