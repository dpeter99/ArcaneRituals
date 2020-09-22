package com.dpeter99.ArcaneRituals.altars.demonic;

import com.dpeter99.ArcaneRituals.particles.AltarParticle;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;

public class DemonicAltarParicle extends AltarParticle {

    private DemonicAltarParicle(ClientWorld worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
    }

    @Override
    protected Color getColor() {
        return new Color(131,29,29);
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite p_i50607_1_) {
            this.spriteSet = p_i50607_1_;
        }

        public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            DemonicAltarParicle portalparticle = new DemonicAltarParicle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
            portalparticle.selectSpriteRandomly(this.spriteSet);
            return portalparticle;
        }
    }

}
