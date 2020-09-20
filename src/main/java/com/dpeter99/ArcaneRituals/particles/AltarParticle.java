package com.dpeter99.ArcaneRituals.particles;

import net.minecraft.client.particle.*;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;

public abstract class AltarParticle extends SpriteTexturedParticle {

    private final double portalPosX;
    private final double portalPosY;
    private final double portalPosZ;

    public AltarParticle(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn);
        this.motionX = xSpeedIn;
        this.motionY = ySpeedIn;
        this.motionZ = zSpeedIn;
        this.posX = xCoordIn;
        this.posY = yCoordIn;
        this.posZ = zCoordIn;
        this.portalPosX = this.posX;
        this.portalPosY = this.posY;
        this.portalPosZ = this.posZ;
        this.particleScale = 0.1F * (this.rand.nextFloat() * 0.2F + 0.5F);
        float f = this.rand.nextFloat() * 0.6F + 0.4F;
        Color col = getColor();
        this.particleRed = f * (col.getRed()/255.0f);
        this.particleGreen = f * (col.getGreen()/255.0f);
        this.particleBlue = f * (col.getBlue()/255.0f);
        this.maxAge = (int)(Math.random() * 10.0D) + 40;
    }

    protected abstract Color getColor();

    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public void move(double x, double y, double z) {
        this.setBoundingBox(this.getBoundingBox().offset(x, y, z));
        this.resetPositionToBB();
    }

    public float getScale(float scaleFactor) {
        float f = ((float)this.age + scaleFactor) / (float)this.maxAge;
        f = 1.0F - f;
        f = f * f;
        f = 1.0F - f;
        return this.particleScale * f;
    }

    public int getBrightnessForRender(float partialTick) {
        int i = super.getBrightnessForRender(partialTick);
        float f = (float)this.age / (float)this.maxAge;
        f = f * f;
        f = f * f;
        int j = i & 255;
        int k = i >> 16 & 255;
        k = k + (int)(f * 15.0F * 16.0F);
        if (k > 240) {
            k = 240;
        }

        return j | k << 16;
    }

    public void tick() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.age++ >= this.maxAge) {
            this.setExpired();
        } else {
            float f = (float)this.age / (float)this.maxAge;
            float f1 = -f + f * f * 2.0F;
            float f2 = 1.0F - f1;
            this.posX = this.portalPosX + this.motionX * (double)f2;
            this.posY = this.portalPosY + this.motionY * (double)f2 + (double)(1.0F - f);
            this.posZ = this.portalPosZ + this.motionZ * (double)f2;
        }
    }


}
