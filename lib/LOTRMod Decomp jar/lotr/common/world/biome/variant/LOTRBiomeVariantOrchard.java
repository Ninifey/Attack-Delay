// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome.variant;

import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.init.Blocks;
import com.google.common.math.IntMath;
import net.minecraft.block.material.Material;
import lotr.common.world.map.LOTRRoads;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.block.Block;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRBiomeVariantOrchard extends LOTRBiomeVariant
{
    public LOTRBiomeVariantOrchard(final int i, final String s) {
        super(i, s, VariantScale.SMALL);
        this.setTemperatureRainfall(0.1f, 0.2f);
        this.setHeight(0.0f, 0.4f);
        this.setTrees(0.0f);
        this.setGrass(0.5f);
        this.disableStructuresVillages();
    }
    
    @Override
    public void generateVariantTerrain(final World world, final Random random, final Block[] blocks, final byte[] meta, final int i, final int k, final int height, final LOTRBiome biome) {
        final int chunkX = i & 0xF;
        final int chunkZ = k & 0xF;
        final int xzIndex = chunkX * 16 + chunkZ;
        final int ySize = blocks.length / 256;
        if (!LOTRRoads.isRoadAt(i, k)) {
            int j = 128;
            while (j >= 0) {
                final int index = xzIndex * ySize + j;
                final Block above = blocks[index + 1];
                final Block block = blocks[index];
                if (block.isOpaqueCube() && above.getMaterial() == Material.air) {
                    final int i2 = IntMath.mod(i, 32);
                    final int k2 = IntMath.mod(k, 16);
                    if (i2 != 6 && i2 != 7 && i2 != 8 && k2 == 0) {
                        blocks[index + 1] = Blocks.fence;
                        meta[index + 1] = 0;
                        break;
                    }
                    break;
                }
                else {
                    --j;
                }
            }
        }
    }
    
    @Override
    public void decorateVariant(final World world, final Random random, final int i, final int k, final LOTRBiome biome) {
        for (final int i2 : new int[] { i + 3, i + 11 }) {
            final int k2 = k + 8;
            final int j1 = world.getHeightValue(i2, k2);
            final WorldGenerator treeGen = (WorldGenerator)this.getRandomTree(random).create(false, random);
            treeGen.generate(world, random, i2, j1, k2);
        }
    }
}
