// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.feature;

import net.minecraft.block.Block;
import lotr.common.LOTRMod;
import net.minecraft.world.IBlockAccess;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenClover extends WorldGenerator
{
    public boolean generate(final World world, final Random random, final int i, int j, final int k) {
        Block block = null;
        do {
            block = world.getBlock(i, j, k);
            if (!block.isAir((IBlockAccess)world, i, j, k) && !block.isLeaves((IBlockAccess)world, i, j, k)) {
                break;
            }
        } while (--j > 0);
        for (int l = 0; l < 128; ++l) {
            final int i2 = i + random.nextInt(8) - random.nextInt(8);
            final int j2 = j + random.nextInt(4) - random.nextInt(4);
            final int k2 = k + random.nextInt(8) - random.nextInt(8);
            if (world.isAirBlock(i2, j2, k2) && LOTRMod.clover.canBlockStay(world, i2, j2, k2)) {
                if (random.nextInt(500) == 0) {
                    world.setBlock(i2, j2, k2, LOTRMod.clover, 1, 2);
                }
                else {
                    world.setBlock(i2, j2, k2, LOTRMod.clover, 0, 2);
                }
            }
        }
        return true;
    }
}
