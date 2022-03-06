// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.feature;

import net.minecraft.block.Block;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenCaveCobwebs extends WorldGenerator
{
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        for (int l = 0; l < 64; ++l) {
            final int i2 = i - random.nextInt(6) + random.nextInt(6);
            final int j2 = j - random.nextInt(4) + random.nextInt(4);
            final int k2 = k - random.nextInt(6) + random.nextInt(6);
            if (world.isAirBlock(i2, j2, k2)) {
                boolean flag = false;
                if (this.isStoneBlock(world, i2 - 1, j2, k2)) {
                    flag = true;
                }
                if (this.isStoneBlock(world, i2 + 1, j2, k2)) {
                    flag = true;
                }
                if (this.isStoneBlock(world, i2, j2 - 1, k2)) {
                    flag = true;
                }
                if (this.isStoneBlock(world, i2, j2 + 1, k2)) {
                    flag = true;
                }
                if (this.isStoneBlock(world, i2, j2, k2 - 1)) {
                    flag = true;
                }
                if (this.isStoneBlock(world, i2, j2, k2 + 1)) {
                    flag = true;
                }
                if (flag) {
                    world.setBlock(i2, j2, k2, Blocks.web, 0, 2);
                }
            }
        }
        return true;
    }
    
    private boolean isStoneBlock(final World world, final int i, final int j, final int k) {
        final Block block = world.getBlock(i, j, k);
        return block == Blocks.stone || block == LOTRMod.rock;
    }
}
