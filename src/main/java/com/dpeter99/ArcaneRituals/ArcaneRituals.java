package com.dpeter99.ArcaneRituals;

import com.dpeter99.ArcaneRituals.altars.demonic.DemonicAltarParicle;
import com.dpeter99.ArcaneRituals.hud.SacrificialKnifeHUD;
import com.dpeter99.ArcaneRituals.particles.ArcaneParticles;
import com.dpeter99.ArcaneRituals.setup.ClientProxy;
import com.dpeter99.ArcaneRituals.setup.IProxy;
import com.dpeter99.ArcaneRituals.setup.ServerProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.theillusivec4.curios.api.SlotTypeMessage;
//import top.theillusivec4.curios.api.CuriosAPI;
//import top.theillusivec4.curios.api.imc.CurioIMCMessage;

@Mod("arcanerituals")
public class ArcaneRituals {

    public static final String MODID = "arcanerituals";

    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    public static final IProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());

    public ArcaneRituals() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onParticleFactoryRegistry);

        Registries.init();


        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        knifeHUD = new SacrificialKnifeHUD(Minecraft.getInstance());
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");

        proxy.init();
    }

    private void doClientStuff(final FMLClientSetupEvent event)
    {
        // do something that can only be done on the client
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);

    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        //InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("ring").build());
    }



    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    @SubscribeEvent
    public void onParticleFactoryRegistry(ParticleFactoryRegisterEvent event){
        Minecraft.getInstance().particles.registerFactory(ArcaneParticles.altar_demonic, DemonicAltarParicle.Factory::new);
    }

    @SubscribeEvent
    public void onAddReloadListenerEvent(AddReloadListenerEvent event){
        event.addListener(new MobBloodLoader());
    }

    static SacrificialKnifeHUD knifeHUD;

    @SubscribeEvent
    public void onHud(RenderGameOverlayEvent.Post event){
        if(event.getType() == RenderGameOverlayEvent.ElementType.ALL) {

            knifeHUD.render(event.getMatrixStack(),event.getWindow());

        }
    }

    public static ResourceLocation location(String path)
    {
        return new ResourceLocation(MODID, path);
    }

}
