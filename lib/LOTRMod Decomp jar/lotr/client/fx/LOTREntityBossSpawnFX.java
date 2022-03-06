// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.fx;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.client.particle.EntityDiggingFX;

public class LOTREntityBossSpawnFX extends EntityDiggingFX
{
    public LOTREntityBossSpawnFX(final World world, final double d, final double d1, final double d2, final double d3, final double d4, final double d5, final Block block, final int meta) {
        super(world, d, d1, d2, d3, d4, d5, block, meta);
        ((EntityFX)this).particleScale *= 2.0f;
    }
}
