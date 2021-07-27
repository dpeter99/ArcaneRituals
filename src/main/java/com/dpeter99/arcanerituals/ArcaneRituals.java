package com.dpeter99.arcanerituals;

import com.dpeter99.arcanerituals.advancements.TriggerManager;
import com.dpeter99.arcanerituals.items.RingOfProtection;
import com.dpeter99.arcanerituals.registry.ARRegistry;
import com.dpeter99.arcanerituals.registry.mobblood.MobBloodManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import top.theillusivec4.curios.api.CuriosApi;
//import top.theillusivec4.curios.api.SlotTypeMessage;

import java.util.Optional;

//import com.dpeter99.bloodylib.TestKt;

@Mod("arcanerituals")
public class ArcaneRituals {

    public static final String MODID = "arcanerituals";

    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    public ArcaneRituals() {
        ARRegistry.initialize();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);

        //LOGGER.error(TestKt.INSTANCE.getTEST());


    }

    public void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            TriggerManager.init();
        });
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        //InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("ring").build());
    }

    public static ResourceLocation location(String path)
    {
        return new ResourceLocation(MODID, path);
    }


    @SubscribeEvent
    public void onAddReloadListenerEvent(AddReloadListenerEvent event){
        event.addListener(new MobBloodManager());
    }

    private static final int ring_protection_s = 5;

    @SubscribeEvent
    public void test(LivingHurtEvent event){
        //TODO: CURIOS
        /*
        Optional<ImmutableTriple<String, Integer, ItemStack>> ring = CuriosApi.getCuriosHelper().findEquippedCurio(i -> (i.getItem() instanceof RingOfProtection), event.getEntityLiving());
        ring.ifPresent(triple -> ((RingOfProtection) triple.right.getItem()).entityDamaged(event));
         */
    }


    public static final CreativeModeTab TAB = new CreativeModeTab("arcane_rituals") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ARRegistry.DEMONIC_ALTAR_ITEM.get());
        }
    };

}
