package com.dpeter99.ArcaneRituals.setup;

import com.dpeter99.ArcaneRituals.altars.demonic.DemonicAltarRenderer;
import com.dpeter99.ArcaneRituals.altars.demonic.DemonicAltarScreen;
import com.dpeter99.ArcaneRituals.altars.necromantic.NecromanticAltarScreen;
import com.dpeter99.ArcaneRituals.block.ArcaneBlocks_OLD;
import com.dpeter99.ArcaneRituals.block.arcane_anvil.ArcaneAnvilScreen;
import com.dpeter99.ArcaneRituals.ArcaneTileEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class ClientProxy implements IProxy {

    public void init(){
        ScreenManager.registerFactory(ArcaneTileEntities.necromantic_altar_container, NecromanticAltarScreen::new);
        ScreenManager.registerFactory(ArcaneTileEntities.demonic_altar_container, DemonicAltarScreen::new);

        ScreenManager.registerFactory(ArcaneTileEntities.arcane_anvil_container, ArcaneAnvilScreen::new);

        DemonicAltarRenderer.register();
        RenderTypeLookup.setRenderLayer(ArcaneBlocks_OLD.demonic_altar, (RenderType) -> true);


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