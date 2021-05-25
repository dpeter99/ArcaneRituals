package com.dpeter99.arcanerituals.registry;

import com.dpeter99.arcanerituals.ArcaneRituals;
import com.dpeter99.arcanerituals.screens.AltarScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = ArcaneRituals.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientInit {

  @SubscribeEvent
  public static void clientStartup(final FMLClientSetupEvent event) {
    event.enqueueWork(() -> {

      ScreenManager.register(ARRegistry.DEMONIC_ALTAR_CONTAINER.get(), AltarScreen::new);

    });

  }
}