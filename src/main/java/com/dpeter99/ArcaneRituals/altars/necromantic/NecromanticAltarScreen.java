package com.dpeter99.ArcaneRituals.altars.necromantic;

import com.dpeter99.ArcaneRituals.ArcaneRituals;
import com.dpeter99.ArcaneRituals.fluid.AdvancedFluid;
import com.dpeter99.ArcaneRituals.screen.GlyphDrawer;
import com.dpeter99.ArcaneRituals.util.ui.SimpleScreen;
import com.dpeter99.ArcaneRituals.util.ui.TextureRegion;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.client.gui.GuiUtils;

import java.util.ArrayList;
import java.util.List;

public class NecromanticAltarScreen extends SimpleScreen<NecromanticAltarContainer> {

    private ResourceLocation GUI = new ResourceLocation(ArcaneRituals.MODID, "textures/gui/necromantic_altar.png");

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
    public void render(MatrixStack matrixStack,int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack,mouseX, mouseY, partialTicks);
        //this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
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
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI);

        drawBackground(matrixStack);
        drawFluid(matrixStack,container.getFluidAmount());
        drawGlyps(matrixStack);
        drawTooltip(matrixStack,mouseX,mouseY);
    }

    private void drawBackground(MatrixStack matrixStack) {
        int relX = (this.width - WIDTH) / 2;
        int relY = (this.height - HEIGHT) / 2;
        this.blit(matrixStack,relX, relY, 0, 0, WIDTH, HEIGHT);
    }

    private void drawFluid(MatrixStack matrixStack,int level) {
        TextureRegion source = null;
        float step = 5000.0f/4;
        if        (step * 0 < level && level <= step * 1) {
            source = fluidStates.get(0);
        } else if (step * 1 < level && level <= step * 2) {
            source = fluidStates.get(1);
        } else if (step * 2 < level && level <= step * 3) {
            source = fluidStates.get(2);
        } else if (step * 3 < level) {
            source = fluidStates.get(3);
        }
        if (source != null) {
            int toX = getGuiLeft() + 88 - (source.getSizeX() / 2);
            int toY = getGuiTop() + 72 - (source.getSizeY() / 2);

            this.blit_help(matrixStack,toX, toY, source);
        }
    }

    private void drawGlyps(MatrixStack matrixStack) {
        float p = 1 - ((float) container.getProgress() / container.getProgressFrom());
        float step = 1.0f / 17;

        glyphs.drawGlyps(matrixStack,5);
    }

    public void drawTooltip(MatrixStack matrixStack,int mouseX, int mouseY){
        int bloodCenterX = getGuiLeft() + 88;
        int bloodCenterY = getGuiTop() + 72;
        float d = (float)Math.sqrt(Math.pow(bloodCenterX - mouseX,2) + Math.pow(bloodCenterY - mouseY,2));

        if(d <= 40) {
            List<ITextComponent> text = new ArrayList<>();
            //text.add("Blood");

            FluidStack fluidStack = container.getTileEntity().tank.getFluid();
            Fluid fluid = fluidStack.getFluid();
            if(fluid.isEquivalentTo(Fluids.EMPTY)) {
                text.add(new StringTextComponent("Empty"));
            }
            else{
                text.add(container.getTileEntity().tank.getFluid().getDisplayName());
                text.add(new StringTextComponent(fluidStack.getAmount() + "mB" ));

                if(fluid instanceof AdvancedFluid){
                    text.add(((AdvancedFluid) fluid).getInfoText(fluidStack));
                }
            }

            //this.renderTooltip(text,mouseX,mouseY);
            GuiUtils.drawHoveringText(matrixStack,text,mouseX,mouseY, width, height, -1, this.font);

        }
    }
}
