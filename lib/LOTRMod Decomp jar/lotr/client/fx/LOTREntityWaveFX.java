// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.fx;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.client.particle.EntityExplodeFX;

public class LOTREntityWaveFX extends EntityExplodeFX
{
    private final float initScale;
    private final float finalScale;
    private final double origMotionX;
    private final double origMotionZ;
    
    public LOTREntityWaveFX(final World world, final double d, final double d1, final double d2, final double d3, final double d4, final double d5) {
        super(world, d, d1, d2, d3, d4, d5);
        ((Entity)this).motionX = d3;
        this.origMotionX = d3;
        ((Entity)this).motionY = d4;
        ((Entity)this).motionZ = d5;
        this.origMotionZ = d5;
        ((EntityFX)this).particleScale = 1.0f + ((Entity)this).rand.nextFloat() * 4.0f;
        this.initScale = ((EntityFX)this).particleScale;
        this.finalScale = this.initScale * MathHelper.randomFloatClamp(((Entity)this).rand, 1.2f, 2.0f);
        ((EntityFX)this).particleMaxAge = MathHelper.getRandomIntegerInRange(((Entity)this).rand, 60, 80);
        ((EntityFX)this).particleAlpha = 0.0f;
    }
    
    public void onUpdate() {
        ((Entity)this).prevPosX = ((Entity)this).posX;
        ((Entity)this).prevPosY = ((Entity)this).posY;
        ((Entity)this).prevPosZ = ((Entity)this).posZ;
        if (((EntityFX)this).particleAge++ >= ((EntityFX)this).particleMaxAge) {
            this.setDead();
        }
        if (((EntityFX)this).particleAlpha < 1.0f) {
            ((EntityFX)this).particleAlpha += 0.02f;
            ((EntityFX)this).particleAlpha = Math.min(((EntityFX)this).particleAlpha, 1.0f);
        }
        ((EntityFX)this).particleScale = this.initScale + ((EntityFX)this).particleAge / (float)((EntityFX)this).particleMaxAge * (this.finalScale - this.initScale);
        this.setParticleTextureIndex((((EntityFX)this).particleMaxAge - ((EntityFX)this).particleAge) % 8);
        this.handleWaterMovement();
        if (((Entity)this).inWater) {
            ((Entity)this).motionY += MathHelper.randomFloatClamp(((Entity)this).rand, 0.04f, 0.1f);
            ((Entity)this).motionX = this.origMotionX;
            ((Entity)this).motionZ = this.origMotionZ;
        }
        else {
            ((Entity)this).motionY -= 0.02;
            ((Entity)this).motionX *= 0.98;
            ((Entity)this).motionZ *= 0.98;
        }
        this.moveEntity(((Entity)this).motionX, ((Entity)this).motionY, ((Entity)this).motionZ);
        ((Entity)this).motionY *= 0.995;
    }
}
