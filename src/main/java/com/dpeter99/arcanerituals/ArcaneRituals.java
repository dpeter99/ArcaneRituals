package com.dpeter99.arcanerituals;

import com.dpeter99.arcanerituals.registry.ARRegistry;
import com.dpeter99.arcanerituals.registry.mobblood.MobBloodManager;
import com.dpeter99.bloodylib.TestKt;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("arcanerituals")
public class ArcaneRituals {

    public static final String MODID = "arcanerituals";

    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    public ArcaneRituals() {
        ARRegistry.initialize();

        LOGGER.error(TestKt.INSTANCE.getTEST());
    }

    public static ResourceLocation location(String path)
    {
        return new ResourceLocation(MODID, path);
    }


    @SubscribeEvent
    public void onAddReloadListenerEvent(AddReloadListenerEvent event){
        event.addListener(new MobBloodManager());
    }


    public static final ItemGroup TAB = new ItemGroup("NOTravenutils") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ARRegistry.DEMONIC_ALTAR_ITEM.get());
        }
    };

}
