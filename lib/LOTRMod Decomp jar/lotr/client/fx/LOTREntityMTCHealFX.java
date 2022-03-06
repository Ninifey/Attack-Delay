// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.fx;

import net.minecraft.entity.Entity;
import net.minecraft.client.particle.EntityFX;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.World;
import net.minecraft.client.particle.EntitySpellParticleFX;

public class LOTREntityMTCHealFX extends EntitySpellParticleFX
{
    private int baseTextureIndex;
    
    public LOTREntityMTCHealFX(final World world, final double d, final double d1, final double d2, final double d3, final double d4, final double d5) {
        super(world, d, d1, d2, d3, d4, d5);
        ((EntityFX)this).particleRed = 1.0f;
        ((EntityFX)this).particleGreen = 0.3f;
        ((EntityFX)this).particleBlue = 0.3f;
        ((EntityFX)this).particleScale *= 3.0f;
        ((EntityFX)this).particleMaxAge = 30;
        ((Entity)this).motionX = d3;
        ((Entity)this).motionY = d4;
        ((Entity)this).motionZ = d5;
        ((Entity)this).renderDistanceWeight = 10.0;
        ((Entity)this).noClip = true;
        this.setBaseSpellTextureIndex(128);
    }
    
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender(final float f) {
        return 15728880;
    }
    
    public float getBrightness(final float f) {
        return 1.0f;
    }
    
    public void onUpdate() {
        ((Entity)this).prevPosX = ((Entity)this).posX;
        ((Entity)this).prevPosY = ((Entity)this).posY;
        ((Entity)this).prevPosZ = ((Entity)this).posZ;
        if (((EntityFX)this).particleAge++ >= ((EntityFX)this).particleMaxAge) {
            this.setDead();
        }
        this.setParticleTextureIndex(this.baseTextureIndex + (7 - ((EntityFX)this).particleAge * 8 / ((EntityFX)this).particleMaxAge));
        this.moveEntity(((Entity)this).motionX, ((Entity)this).motionY, ((Entity)this).motionZ);
        ((EntityFX)this).particleAlpha = 0.5f + 0.5f * (((EntityFX)this).particleAge / (float)((EntityFX)this).particleMaxAge);
    }
    
    public void setBaseSpellTextureIndex(final int i) {
        super.setBaseSpellTextureIndex(i);
        this.baseTextureIndex = i;
    }
}
