package com.dpeter99.ArcaneRituals.util;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.util.LazyOptional;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.type.capability.ICurio;
//import top.theillusivec4.curios.api.capability.CuriosCapability;
//import top.theillusivec4.curios.api.capability.ICurio;

public class CapCurioItem {
    public CapCurioItem() {
    }

    public static void register() {
        CapabilityManager.INSTANCE.register(ICurio.class, new Capability.IStorage<ICurio>() {
            public INBT writeNBT(Capability<ICurio> capability, ICurio instance, Direction side) {
                return new CompoundNBT();
            }

            public void readNBT(Capability<ICurio> capability, ICurio instance, Direction side, INBT nbt) {
            }
        }, () -> {
            return new CapCurioItem.CurioWrapper();
        });
    }

    public static ICapabilityProvider createProvider(ICurio curio) {
        return new CapCurioItem.Provider(curio);
    }

    public static class Provider implements ICapabilityProvider {
        final LazyOptional<ICurio> capability;

        Provider(ICurio curio) {
            this.capability = LazyOptional.of(() -> {
                return curio;
            });
        }

        @Nonnull
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            return CuriosCapability.ITEM.orEmpty(cap, this.capability);
        }
    }

    private static class CurioWrapper implements ICurio {
        private CurioWrapper() {
        }
    }
}
