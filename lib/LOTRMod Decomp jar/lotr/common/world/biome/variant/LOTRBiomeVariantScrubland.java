// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome.variant;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import lotr.common.world.biome.LOTRBiome;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.block.Block;

public class LOTRBiomeVariantScrubland extends LOTRBiomeVariant
{
    private Block stoneBlock;
    
    public LOTRBiomeVariantScrubland(final int i, final String s, final Block block) {
        super(i, s, VariantScale.LARGE);
        this.setTemperatureRainfall(0.0f, -0.2f);
        this.setTrees(0.8f);
        this.setGrass(0.5f);
        this.addTreeTypes(0.6f, LOTRTreeType.OAK_SHRUB, 100);
        this.stoneBlock = block;
        this.disableVillages();
    }
    
    @Override
    public void generateVariantTerrain(final World world, final Random random, final Block[] blocks, final byte[] meta, final int i, final int k, final int height, final LOTRBiome biome) {
        final int chunkX = i & 0xF;
        final int chunkZ = k & 0xF;
        final int xzIndex = chunkX * 16 + chunkZ;
        final int ySize = blocks.length / 256;
        double d1 = LOTRBiome.biomeTerrainNoise.func_151601_a(i * 0.005, k * 0.005);
        double d2 = LOTRBiome.biomeTerrainNoise.func_151601_a(i * 0.07, k * 0.07);
        double d3 = LOTRBiome.biomeTerrainNoise.func_151601_a(i * 0.3, k * 0.3);
        if (d1 + d2 + d3 > 0.6) {
            final int j = height;
            final int index = xzIndex * ySize + j;
            if (d1 + d2 > 0.7 && random.nextInt(3) != 0) {
                blocks[index] = (Block)Blocks.sand;
                meta[index] = 0;
            }
            else if (random.nextInt(5) == 0) {
                blocks[index] = Blocks.gravel;
                meta[index] = 0;
            }
            else {
                blocks[index] = this.stoneBlock;
                meta[index] = 0;
            }
            if (random.nextInt(30) == 0) {
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
        d1 = LOTRBiome.biomeTerrainNoise.func_151601_a(i * 0.008, k * 0.008);
        d2 = LOTRBiome.biomeTerrainNoise.func_151601_a(i * 0.05, k * 0.05);
        d3 = LOTRBiome.biomeTerrainNoise.func_151601_a(i * 0.6, k * 0.6);
        if (d1 + d2 + d3 > 0.8 && random.nextInt(3) == 0) {
            final int j = height;
            final int index = xzIndex * ySize + j;
            final Block above = blocks[index + 1];
            final Block block = blocks[index];
            if (block.isOpaqueCube() && above.getMaterial() == Material.air) {
                blocks[index + 1] = (Block)Blocks.leaves;
                meta[index + 1] = 4;
                if (random.nextInt(5) == 0) {
                    blocks[index + 2] = (Block)Blocks.leaves;
                    meta[index + 2] = 4;
                }
            }
        }
        if (random.nextInt(3000) == 0) {
            final int j = height;
            final int index = xzIndex * ySize + j;
            blocks[index] = biome.fillerBlock;
            meta[index] = (byte)biome.fillerBlockMeta;
            for (int logHeight = 1 + random.nextInt(4), j2 = 1; j2 <= logHeight; ++j2) {
                blocks[index + j2] = Blocks.log;
                meta[index + j2] = 0;
            }
        }
    }
}
