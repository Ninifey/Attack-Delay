// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.fx;

import net.minecraft.entity.Entity;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.util.MathHelper;
import java.awt.Color;
import net.minecraft.world.World;
import net.minecraft.client.particle.EntitySpellParticleFX;

public class LOTREntityRiverWaterFX extends EntitySpellParticleFX
{
    public LOTREntityRiverWaterFX(final World world, final double d, final double d1, final double d2, final double d3, final double d4, final double d5, final int color) {
        super(world, d, d1, d2, d3, d4, d5);
        final Color c = new Color(color);
        final float[] rgb = c.getColorComponents(null);
        ((EntityFX)this).particleRed = MathHelper.randomFloatClamp(((Entity)this).rand, rgb[0] - 0.3f, rgb[0] + 0.3f);
        ((EntityFX)this).particleGreen = MathHelper.randomFloatClamp(((Entity)this).rand, rgb[1] - 0.3f, rgb[1] + 0.3f);
        ((EntityFX)this).particleBlue = MathHelper.randomFloatClamp(((Entity)this).rand, rgb[2] - 0.3f, rgb[2] + 0.3f);
        ((EntityFX)this).particleRed = MathHelper.clamp_float(((EntityFX)this).particleRed, 0.0f, 1.0f);
        ((EntityFX)this).particleGreen = MathHelper.clamp_float(((EntityFX)this).particleGreen, 0.0f, 1.0f);
        ((EntityFX)this).particleBlue = MathHelper.clamp_float(((EntityFX)this).particleBlue, 0.0f, 1.0f);
        ((EntityFX)this).particleScale = 0.5f + ((Entity)this).rand.nextFloat() * 0.5f;
        ((EntityFX)this).particleMaxAge = 20 + ((Entity)this).rand.nextInt(20);
        ((Entity)this).motionX = d3;
        ((Entity)this).motionY = d4;
        ((Entity)this).motionZ = d5;
    }
    
    public void onUpdate() {
        super.onUpdate();
        ((EntityFX)this).particleAlpha = 0.5f + 0.5f * (((EntityFX)this).particleAge / (float)((EntityFX)this).particleMaxAge);
    }
}
