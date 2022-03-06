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

public class LOTREntityMorgulPortalFX extends EntitySpellParticleFX
{
    public LOTREntityMorgulPortalFX(final World world, final double d, final double d1, final double d2, final double d3, final double d4, final double d5) {
        super(world, d, d1, d2, d3, d4, d5);
        ((EntityFX)this).particleRed = 0.2f;
        ((EntityFX)this).particleGreen = 0.8f;
        ((EntityFX)this).particleBlue = 0.4f;
        ((EntityFX)this).particleScale = 0.5f + ((Entity)this).rand.nextFloat() * 0.5f;
        ((EntityFX)this).particleMaxAge = 20 + ((Entity)this).rand.nextInt(20);
        ((Entity)this).motionX = d3;
        ((Entity)this).motionY = d4;
        ((Entity)this).motionZ = d5;
    }
    
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender(final float f) {
        return 15728880;
    }
    
    public float getBrightness(final float f) {
        return 1.0f;
    }
    
    public void onUpdate() {
        super.onUpdate();
        ((EntityFX)this).particleAlpha = 0.5f + 0.5f * (((EntityFX)this).particleAge / (float)((EntityFX)this).particleMaxAge);
        ((Entity)this).motionX *= 1.1;
        ((Entity)this).motionZ *= 1.1;
    }
}
