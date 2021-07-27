package com.dpeter99.arcanerituals.screens;

import com.dpeter99.arcanerituals.ArcaneRituals;
import com.dpeter99.arcanerituals.containers.AltarContainer;
import com.dpeter99.arcanerituals.screens.graphicalelements.FluidDisplay;
import com.dpeter99.arcanerituals.screens.graphicalelements.GlyphDrawer;
import com.dpeter99.bloodylib.fluid.AdvancedFluid;
import com.dpeter99.bloodylib.math.Vector2i;
import com.dpeter99.bloodylib.ui.Sprite;
import com.dpeter99.bloodylib.ui.screens.BloodyContainerScreen;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fmlclient.gui.GuiUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AltarScreen extends BloodyContainerScreen<AltarContainer> {

    private static final ResourceLocation GUI = new ResourceLocation(ArcaneRituals.MODID, "textures/gui/demonic_altar.png");

    private static final int WIDTH = 176;
    private static final int HEIGHT = 226;

    //GlyphDrawer glyphs;

    //List<TextureRegion_old> fluidStates = new ArrayList<TextureRegion_old>();

    public AltarScreen(AltarContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn, GUI);

        this.imageHeight = HEIGHT;
        this.imageWidth = WIDTH;

        List<Vector2i> glyphPos = new ArrayList<>();
        {
            glyphPos.add(Vector2i.of(67, 15));
            glyphPos.add(Vector2i.of(53, 22));
            glyphPos.add(Vector2i.of(42, 33));
            glyphPos.add(Vector2i.of(35, 47));

            glyphPos.add(Vector2i.of(35, 84));
            glyphPos.add(Vector2i.of(42, 98));
            glyphPos.add(Vector2i.of(53, 109));
            glyphPos.add(Vector2i.of(67, 116));

            glyphPos.add(Vector2i.of(97, 116));
            glyphPos.add(Vector2i.of(111, 109));
            glyphPos.add(Vector2i.of(122, 98));
            glyphPos.add(Vector2i.of(129, 84));

            glyphPos.add(Vector2i.of(129, 47));
            glyphPos.add(Vector2i.of(122, 33));
            glyphPos.add(Vector2i.of(111, 22));
            glyphPos.add(Vector2i.of(97, 15));
        }

        Random r = new Random();

        GlyphDrawer glyphDrawer = new GlyphDrawer(r.nextInt(),glyphPos,
                GlyphDrawer.glyphListHorizontal(Vector2i.of(0,232),8,12,12),
                GlyphDrawer.glyphListHorizontal(Vector2i.of(0,244),8,12,12),
                i->{
            float a = ((float) glyphPos.size()/this.getMenu().getProgressFrom())* getMenu().getProgress();

            return i < a;
        });

        this.addWidget(glyphDrawer);

        List<Sprite> fluidStates = new ArrayList<>();
        fluidStates.add(new Sprite(Vector2i.of(180, 0), Vector2i.of(22, 22)));
        fluidStates.add(new Sprite(Vector2i.of(180, 22), Vector2i.of(44, 44)));
        fluidStates.add(new Sprite(Vector2i.of(180, 66), Vector2i.of(59, 61)));
        fluidStates.add(new Sprite(Vector2i.of(176, 128), Vector2i.of(80, 80)));


        FluidDisplay blood = new FluidDisplay(screenContainer::getFluidAmount,
                screenContainer::getMaxFluidAmount,
                88,72,
                fluidStates
        );

        this.addWidget(blood);
    }

    @Override
    protected void init() {
        super.init();
        //this.topPos -= 31;
        addVerticalOffset(-31);
    }

    @Override
    protected void renderTooltip(PoseStack matrixStack, int mouseX, int mouseY) {
        super.renderTooltip(matrixStack, mouseX, mouseY);

        int bloodCenterX = getGuiLeft() + 88;
        int bloodCenterY = getGuiTop() + 72;
        float d = (float)Math.sqrt(Math.pow(bloodCenterX - mouseX,2) + Math.pow(bloodCenterY - mouseY,2));

        if(d <= 40) {
            List<Component> text = new ArrayList<>();

            FluidStack fluidStack = menu.getTileEntity().tank.getFluid();
            Fluid fluid = fluidStack.getFluid();
            if(fluid.isSame(Fluids.EMPTY)) {
                text.add(new TextComponent("Empty"));
            }
            else{
                text.add(menu.getTileEntity().tank.getFluid().getDisplayName());
                text.add(new TextComponent(fluidStack.getAmount() + "mB" ));

                if(fluid instanceof AdvancedFluid){
                    text.add(((AdvancedFluid) fluid).getInfoText(fluidStack));
                }
            }
            //this.renderTooltip(matrixStack,text,mouseX,mouseY);
            GuiUtils.drawHoveringText(matrixStack,text,mouseX,mouseY, width, height, -1, this.font);

        }
    }

}
