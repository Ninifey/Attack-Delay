// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.fx;

import net.minecraft.entity.Entity;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.client.particle.EntityDiggingFX;

public class LOTREntityMallornEntHealFX extends EntityDiggingFX
{
    public LOTREntityMallornEntHealFX(final World world, final double d, final double d1, final double d2, final double d3, final double d4, final double d5, final Block block, final int meta, final int color) {
        super(world, d, d1, d2, d3, d4, d5, block, meta);
        ((EntityFX)this).particleRed *= (color >> 16 & 0xFF) / 255.0f;
        ((EntityFX)this).particleGreen *= (color >> 8 & 0xFF) / 255.0f;
        ((EntityFX)this).particleBlue *= (color & 0xFF) / 255.0f;
        ((EntityFX)this).particleScale *= 2.0f;
        ((EntityFX)this).particleMaxAge = 30;
        ((Entity)this).motionX = d3;
        ((Entity)this).motionY = d4;
        ((Entity)this).motionZ = d5;
        ((EntityFX)this).particleGravity = 0.0f;
        ((Entity)this).renderDistanceWeight = 10.0;
        ((Entity)this).noClip = true;
    }
}
