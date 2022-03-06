// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.feature;

import net.minecraft.block.material.Material;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenReeds extends WorldGenerator
{
    private Block reedBlock;
    
    public LOTRWorldGenReeds(final Block block) {
        this.reedBlock = block;
    }
    
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        int l = 0;
    Label_0003:
        while (l < 16) {
            final int i2 = i + random.nextInt(8) - random.nextInt(8);
            final int j2 = j + random.nextInt(4) - random.nextInt(4);
            final int k2 = k + random.nextInt(8) - random.nextInt(8);
            final int maxDepth = 5;
            while (true) {
                for (int j3 = j2 - 1; j3 > 0 && world.getBlock(i2, j3, k2).getMaterial() == Material.water; --j3) {
                    if (j3 < j2 - maxDepth) {
                        ++l;
                        continue Label_0003;
                    }
                }
                if (world.isBlockFreezable(i2, j2 - 1, k2)) {
                    continue;
                }
                for (int reedHeight = 1 + random.nextInt(3), j4 = j2; j4 < j2 + reedHeight; ++j4) {
                    if (world.isAirBlock(i2, j4, k2) && this.reedBlock.canBlockStay(world, i2, j4, k2)) {
                        world.setBlock(i2, j4, k2, this.reedBlock, 0, 2);
                    }
                }
                continue;
            }
        }
        return true;
    }
}
