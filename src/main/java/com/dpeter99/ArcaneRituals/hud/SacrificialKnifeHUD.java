package com.dpeter99.ArcaneRituals.hud;

import com.dpeter99.ArcaneRituals.ArcaneRituals;
import com.dpeter99.ArcaneRituals.item.ArcaneItems_old;
import com.dpeter99.ArcaneRituals.item.sacrificialKnife.KnifeData;
import com.dpeter99.ArcaneRituals.util.ui.TextureRegion_old;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;

public class SacrificialKnifeHUD extends AbstractGui {

    private ResourceLocation GUI_TEXTURE = ArcaneRituals.location("textures/gui/hud/sacrificial_knife_hud.png");
    private final Minecraft client;

    private TextureRegion_old flask;
    private TextureRegion_old flask_background;
    private TextureRegion_old blood;

    public SacrificialKnifeHUD(Minecraft clientIn) {
        this.client = clientIn;

        flask_background = new TextureRegion_old(24,0,24,48, 128,64);

        flask = new TextureRegion_old(0,0,24,48, 128,64);

        blood = new TextureRegion_old(48, 8,24, 40 , 128,64);
    }

    public void render(MatrixStack stack, MainWindow window){
        blood = new TextureRegion_old(48, 8,24, 40 , 128,64);


        ItemStack itemStack = client.player.getHeldItem(Hand.MAIN_HAND);
        if(itemStack.getItem().equals(ArcaneItems_old.sacrificial_knife)){

            KnifeData data = KnifeData.fromStack(itemStack);
            float percent = (float) (data.hit_count) / data.hit_needed;
            int bloodHeight = (int)(40 * percent);

            this.client.getTextureManager().bindTexture(GUI_TEXTURE);

            int pos_y = (window.getScaledHeight()/5) * 2;

            RenderSystem.enableBlend();

            //blit(stack,10,pos_y,24*2,48*2, this.getBlitOffset(), this.getBlitOffset(),24,48,64,64);
            flask_background.blitSized(stack,10,pos_y,24,48);

            TextureRegion_old draw_blood =  new TextureRegion_old(48, (int) (8+(40-bloodHeight)),24, bloodHeight , 128,64);
            draw_blood.blitSized(stack, 10, pos_y+8+(40-bloodHeight), 24,draw_blood.getSizeY());

            flask.blitSized(stack,10,pos_y,24,48);

        }


    }

}
