package com.dpeter99.ArcaneRituals.setup;

import com.dpeter99.ArcaneRituals.block.ArcaneBlocks;
import com.dpeter99.ArcaneRituals.screen.WitchAltarScreen;
import com.dpeter99.ArcaneRituals.tileentity.ArcaneTileEntities;
import javafx.stage.Screen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class ClientProxy implements IProxy {

    public void init(){
        ScreenManager.registerFactory(ArcaneTileEntities.witch_altar_continer, WitchAltarScreen::new);
    }

    @Override
    public World getClientWorld() {
        return Minecraft.getInstance().world;
    }

    @Override
    public PlayerEntity getClientPlayer() {
        return Minecraft.getInstance().player;
    }
}