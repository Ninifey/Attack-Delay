// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.feature;

import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenWaterPlant extends WorldGenerator
{
    private Block plant;
    
    public LOTRWorldGenWaterPlant(final Block block) {
        this.plant = block;
    }
    
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        for (int l = 0; l < 32; ++l) {
            final int i2 = i + random.nextInt(4) - random.nextInt(4);
            final int j2 = j;
            final int k2 = k + random.nextInt(4) - random.nextInt(4);
            if (world.isAirBlock(i2, j2, k2) && this.plant.canPlaceBlockAt(world, i2, j2, k2)) {
                world.setBlock(i2, j2, k2, this.plant, 0, 2);
            }
        }
        return true;
    }
}
