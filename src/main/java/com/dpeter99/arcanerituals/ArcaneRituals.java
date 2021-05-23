package com.dpeter99.arcanerituals;

import com.dpeter99.arcanerituals.registry.ARRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
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
    }

    public static ResourceLocation location(String path)
    {
        return new ResourceLocation(MODID, path);
    }

    public static final ItemGroup TAB = new ItemGroup("NOTravenutils") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ARRegistry.DEMONIC_ALTAR_ITEM.get());
        }
    };

}
