// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.feature;

import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.util.MathHelper;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRWorldGenObsidianGravel extends WorldGenerator
{
    private Block genBlock;
    private int genMeta;
    
    public LOTRWorldGenObsidianGravel() {
        this.genBlock = LOTRMod.obsidianGravel;
        this.genMeta = 0;
    }
    
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        final BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
        final Block below = world.getBlock(i, j - 1, k);
        if (below != biome.topBlock && below != biome.fillerBlock && below != Blocks.stone) {
            return false;
        }
        final int numBlocks = MathHelper.getRandomIntegerInRange(random, 6, 16);
        final float angle = random.nextFloat() * 3.1415927f;
        final float sin = MathHelper.sin(angle);
        final float cos = MathHelper.sin(angle);
        final float div = 8.0f;
        final double xMin = i - sin * numBlocks / div;
        final double xMax = i + sin * numBlocks / div;
        final double zMin = k - cos * numBlocks / div;
        final double zMax = k + cos * numBlocks / div;
        final double yMin = j + random.nextInt(3) - 2;
        final double yMax = j + random.nextInt(3) - 2;
        for (int l = 0; l <= numBlocks; ++l) {
            final float lerp = l / (float)numBlocks;
            final double xLerp = xMin + (xMax - xMin) * lerp;
            final double yLerp = yMin + (yMax - yMin) * lerp;
            final double zLerp = zMin + (zMax - zMin) * lerp;
            final double d9 = random.nextDouble() * numBlocks / 16.0;
            final double d10 = (MathHelper.sin(l * 3.1415927f / numBlocks) + 1.0f) * d9 + 1.0;
            final double d11 = (MathHelper.sin(l * 3.1415927f / numBlocks) + 1.0f) * d9 + 1.0;
            final int i2 = MathHelper.floor_double(xLerp - d10 / 2.0);
            final int j2 = MathHelper.floor_double(yLerp - d11 / 2.0);
            final int k2 = MathHelper.floor_double(zLerp - d10 / 2.0);
            final int l2 = MathHelper.floor_double(xLerp + d10 / 2.0);
            final int i3 = MathHelper.floor_double(yLerp + d11 / 2.0);
            final int j3 = MathHelper.floor_double(zLerp + d10 / 2.0);
            for (int k3 = i2; k3 <= l2; ++k3) {
                final double d12 = (k3 + 0.5 - xLerp) / (d10 / 2.0);
                if (d12 * d12 < 1.0) {
                    for (int l3 = j2; l3 <= i3; ++l3) {
                        final double d13 = (l3 + 0.5 - yLerp) / (d11 / 2.0);
                        if (d12 * d12 + d13 * d13 < 1.0) {
                            for (int i4 = k2; i4 <= j3; ++i4) {
                                final double d14 = (i4 + 0.5 - zLerp) / (d10 / 2.0);
                                if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0 && this.canReplace(world, k3, l3, i4)) {
                                    world.setBlock(k3, l3, i4, this.genBlock, this.genMeta, 2);
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
    
    private boolean canReplace(final World world, final int i, final int j, final int k) {
        final Block block = world.getBlock(i, j, k);
        final BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
        return block == biome.topBlock || block == biome.fillerBlock || block == Blocks.stone || block.isReplaceable((IBlockAccess)world, i, j, k);
    }
}
