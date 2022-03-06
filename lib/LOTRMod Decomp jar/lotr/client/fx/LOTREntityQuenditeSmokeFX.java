// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.fx;

import net.minecraft.entity.Entity;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;
import net.minecraft.client.particle.EntitySmokeFX;

public class LOTREntityQuenditeSmokeFX extends EntitySmokeFX
{
    public LOTREntityQuenditeSmokeFX(final World world, final double d, final double d1, final double d2, final double d3, final double d4, final double d5) {
        super(world, d, d1, d2, d3, d4, d5);
        ((EntityFX)this).particleRed = ((Entity)this).rand.nextFloat() * 0.3f;
        ((EntityFX)this).particleGreen = 0.5f + ((Entity)this).rand.nextFloat() * 0.5f;
        ((EntityFX)this).particleBlue = 1.0f;
    }
}
