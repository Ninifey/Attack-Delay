// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.feature;

import net.minecraft.block.Block;
import lotr.common.LOTRMod;
import net.minecraft.util.MathHelper;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenBlastedLand extends WorldGenerator
{
    private boolean aflame;
    
    public LOTRWorldGenBlastedLand(final boolean flag) {
        this.aflame = flag;
    }
    
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        final Block block = world.getBlock(i, j - 1, k);
        if (block != Blocks.grass && block != Blocks.dirt && block != Blocks.stone) {
            return false;
        }
        for (int radius = 5 + random.nextInt(8), i2 = i - radius; i2 <= i + radius; ++i2) {
            for (int j2 = j - radius / 2; j2 <= j + radius / 2; ++j2) {
                for (int k2 = k - radius; k2 <= k + radius; ++k2) {
                    final Block block2 = world.getBlock(i2, j2, k2);
                    if (block2 == Blocks.grass || block2 == Blocks.dirt || block2 == Blocks.stone) {
                        final int i3 = i2 - i;
                        final int j3 = j2 - j;
                        final int k3 = k2 - k;
                        final double d = Math.sqrt(i3 * i3 + j3 * j3 + k3 * k3);
                        int chance = MathHelper.floor_double(d / 2.0);
                        if (chance < 1) {
                            chance = 1;
                        }
                        if (random.nextInt(chance) == 0) {
                            world.setBlock(i2, j2, k2, LOTRMod.wasteBlock, 0, 2);
                        }
                        if (this.aflame && d < radius / 2.0 && random.nextInt(10) == 0 && !world.getBlock(i2, j2 + 1, k2).isOpaqueCube()) {
                            world.setBlock(i2, j2, k2, LOTRMod.wasteBlock, 0, 2);
                            world.setBlock(i2, j2 + 1, k2, (Block)Blocks.fire, 0, 2);
                        }
                    }
                }
            }
        }
        return true;
    }
}
