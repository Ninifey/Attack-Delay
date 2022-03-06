// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.fx;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.client.particle.EntityFlameFX;

public class LOTREntityMarshLightFX extends EntityFlameFX
{
    public LOTREntityMarshLightFX(final World world, final double d, final double d1, final double d2, final double d3, final double d4, final double d5) {
        super(world, d, d1, d2, d3, d4, d5);
        ((Entity)this).motionX = d3;
        ((Entity)this).motionY = d4;
        ((Entity)this).motionZ = d5;
        this.setParticleTextureIndex(49);
        ((EntityFX)this).particleMaxAge = 40 + ((Entity)this).rand.nextInt(20);
        final float particleRed = 0.75f + ((Entity)this).rand.nextFloat() * 0.25f;
        ((EntityFX)this).particleBlue = particleRed;
        ((EntityFX)this).particleGreen = particleRed;
        ((EntityFX)this).particleRed = particleRed;
    }
}
