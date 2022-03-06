// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import net.minecraft.init.Blocks;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import net.minecraft.block.Block;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRBiomeGenForodwaithMountains extends LOTRBiomeGenForodwaith
{
    public LOTRBiomeGenForodwaithMountains(final int i, final boolean major) {
        super(i, major);
    }
    
    @Override
    protected void generateMountainTerrain(final World world, final Random random, final Block[] blocks, final byte[] meta, final int i, final int k, final int xzIndex, final int ySize, final int height, final int rockDepth, final LOTRBiomeVariant variant) {
        final int snowHeight = 100 - rockDepth;
        for (int stoneHeight = snowHeight - 30, j = ySize - 1; j >= stoneHeight; --j) {
            final int index = xzIndex * ySize + j;
            final Block block = blocks[index];
            if (j >= snowHeight && block == super.topBlock) {
                blocks[index] = Blocks.snow;
                meta[index] = 0;
            }
            else if (block == super.topBlock || block == super.fillerBlock) {
                blocks[index] = Blocks.stone;
                meta[index] = 0;
            }
        }
    }
}
