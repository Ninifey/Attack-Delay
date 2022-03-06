// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome.variant;

import net.minecraft.init.Blocks;
import lotr.common.world.biome.LOTRBiome;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.block.Block;

public class LOTRBiomeVariantWasteland extends LOTRBiomeVariant
{
    private Block stoneBlock;
    
    public LOTRBiomeVariantWasteland(final int i, final String s, final Block block) {
        super(i, s, VariantScale.LARGE);
        this.setTemperatureRainfall(0.0f, -0.3f);
        this.setTrees(0.1f);
        this.setGrass(0.3f);
        this.setFlowers(0.3f);
        this.stoneBlock = block;
        this.disableVillages();
    }
    
    @Override
    public void generateVariantTerrain(final World world, final Random random, final Block[] blocks, final byte[] meta, final int i, final int k, final int height, final LOTRBiome biome) {
        final int chunkX = i & 0xF;
        final int chunkZ = k & 0xF;
        final int xzIndex = chunkX * 16 + chunkZ;
        final int ySize = blocks.length / 256;
        final double d1 = LOTRBiome.biomeTerrainNoise.func_151601_a(i * 0.04, k * 0.04);
        double d2 = LOTRBiome.biomeTerrainNoise.func_151601_a(i * 0.3, k * 0.3);
        d2 *= 0.3;
        final double d3 = LOTRBiomeVariant.podzolNoise.func_151601_a(i * 0.04, k * 0.04);
        double d4 = LOTRBiomeVariant.podzolNoise.func_151601_a(i * 0.3, k * 0.3);
        d4 *= 0.3;
        if (d3 + d4 > 0.5) {
            final int j = height;
            final int index = xzIndex * ySize + j;
            blocks[index] = Blocks.dirt;
            meta[index] = 1;
        }
        else if (d1 + d2 > -0.3) {
            final int j = height;
            final int index = xzIndex * ySize + j;
            if (random.nextInt(5) == 0) {
                blocks[index] = Blocks.gravel;
                meta[index] = 0;
            }
            else {
                blocks[index] = this.stoneBlock;
                meta[index] = 0;
            }
            if (random.nextInt(50) == 0) {
                if (random.nextInt(3) == 0) {
                    blocks[index + 1] = this.stoneBlock;
                    meta[index + 1] = 0;
                }
                else {
                    blocks[index + 1] = Blocks.gravel;
                    meta[index + 1] = 0;
                }
            }
        }
    }
}
