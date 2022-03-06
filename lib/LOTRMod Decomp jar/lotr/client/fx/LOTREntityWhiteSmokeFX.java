// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.fx;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.client.particle.EntitySmokeFX;

public class LOTREntityWhiteSmokeFX extends EntitySmokeFX
{
    public LOTREntityWhiteSmokeFX(final World world, final double d, final double d1, final double d2, final double d3, final double d4, final double d5) {
        super(world, d, d1, d2, d3, d4, d5);
        final float randomFloatClamp;
        final float grey = randomFloatClamp = MathHelper.randomFloatClamp(((Entity)this).rand, 0.6f, 1.0f);
        ((EntityFX)this).particleBlue = randomFloatClamp;
        ((EntityFX)this).particleGreen = randomFloatClamp;
        ((EntityFX)this).particleRed = randomFloatClamp;
    }
}
