package com.dpeter99.arcanerituals.screens.graphicalelements;

import com.dpeter99.bloodylib.ui.Sprite;
import com.dpeter99.bloodylib.ui.screens.GraphicalElement;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

import java.util.List;
import java.util.function.Supplier;

public class FluidDisplay extends GraphicalElement {


    private Supplier<Integer> max;
    private int xpos;
    private int ypos;
    private List<Sprite> fluid_states;
    private Supplier<Integer> amount;

    public FluidDisplay(Supplier<Integer> amount,Supplier<Integer> max, int xpos, int ypos, List<Sprite> fluid_states) {

        this.amount = amount;
        this.max = max;
        this.xpos = xpos;
        this.ypos = ypos;
        this.fluid_states = fluid_states;
    }

    @Override
    public RenderLayer getRenderLayer() {
        return RenderLayer.bg;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {

        float increment = max.get()/(fluid_states.size()*1.0f);
        int state = (int)Math.floor( amount.get() / increment);

        if(state == fluid_states.size()){
            state--;
        }
        Sprite source = null;

        if(fluid_states.size() > state && state > 0)
            source = fluid_states.get(state);

        if(source != null)
        {
            int toX = screen.getGuiLeft() + xpos - (source.getSizeX() / 2);
            int toY = screen.getGuiTop() + ypos - (source.getSizeY() / 2);

            screen.blit(matrixStack, toX, toY, source);
        }
    }
}
