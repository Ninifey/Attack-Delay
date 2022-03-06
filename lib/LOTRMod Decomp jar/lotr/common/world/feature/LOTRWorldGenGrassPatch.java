// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.feature;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenGrassPatch extends WorldGenerator
{
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        if (world.getBlock(i, j - 1, k) != Blocks.stone) {
            return false;
        }
        final int radius = 3 + random.nextInt(3);
        final int heightValue = world.getHeightValue(i, k);
        for (int i2 = i - radius; i2 <= i + radius; ++i2) {
            for (int k2 = k - radius; k2 <= k + radius; ++k2) {
                final int i3 = i2 - i;
                final int k3 = k2 - k;
                if (i3 * i3 + k3 * k3 < radius * radius && world.getHeightValue(i2, k2) == heightValue) {
                    for (int j2 = heightValue - 1; j2 > heightValue - 5; --j2) {
                        final Block block = world.getBlock(i2, j2, k2);
                        if (block != Blocks.dirt && block != Blocks.stone) {
                            break;
                        }
                        if (j2 == heightValue - 1) {
                            world.setBlock(i2, j2, k2, (Block)Blocks.grass, 0, 2);
                        }
                        else {
                            world.setBlock(i2, j2, k2, Blocks.dirt, 0, 2);
                        }
                    }
                }
            }
        }
        return true;
    }
}
