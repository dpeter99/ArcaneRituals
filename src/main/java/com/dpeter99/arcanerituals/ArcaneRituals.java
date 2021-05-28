package com.dpeter99.arcanerituals;

import com.dpeter99.arcanerituals.registry.ARRegistry;
import com.dpeter99.arcanerituals.registry.mobblood.MobBloodManager;
import com.dpeter99.bloodylib.TestKt;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.theillusivec4.curios.api.SlotTypeMessage;

@Mod("arcanerituals")
public class ArcaneRituals {

    public static final String MODID = "arcanerituals";

    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    public ArcaneRituals() {
        ARRegistry.initialize();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);

        MinecraftForge.EVENT_BUS.register(this);

        LOGGER.error(TestKt.INSTANCE.getTEST());


    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("ring").build());
    }

    public static ResourceLocation location(String path)
    {
        return new ResourceLocation(MODID, path);
    }


    @SubscribeEvent
    public void onAddReloadListenerEvent(AddReloadListenerEvent event){
        event.addListener(new MobBloodManager());
    }



    public static final ItemGroup TAB = new ItemGroup("arcane_rituals") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ARRegistry.DEMONIC_ALTAR_ITEM.get());
        }
    };

}
