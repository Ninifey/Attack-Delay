// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import net.minecraft.world.gen.feature.WorldGenerator;
import lotr.common.world.feature.LOTRWorldGenStreams;
import lotr.common.world.feature.LOTRWorldGenVolcanoCrater;
import net.minecraft.init.Blocks;
import net.minecraft.block.Block;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.biome.variant.LOTRBiomeVariant;

public class LOTRBiomeGenFarHaradJungleMountains extends LOTRBiomeGenFarHaradJungle
{
    public LOTRBiomeGenFarHaradJungleMountains(final int i, final boolean major) {
        super(i, major);
        super.obsidianGravelRarity = 5;
        super.npcSpawnList.clear();
        this.clearBiomeVariants();
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_MOUNTAINS);
        super.decorator.biomeGemFactor = 1.0f;
        super.decorator.treesPerChunk = 0;
        super.biomeColors.setSky(10659994);
        super.biomeColors.setFog(9805451);
        super.invasionSpawns.clearInvasions();
    }
    
    @Override
    public boolean hasJungleLakes() {
        return false;
    }
    
    @Override
    public boolean getEnableRiver() {
        return false;
    }
    
    @Override
    protected void generateMountainTerrain(final World world, final Random random, final Block[] blocks, final byte[] meta, final int i, final int k, final int xzIndex, final int ySize, final int height, final int rockDepth, final LOTRBiomeVariant variant) {
        final int stoneHeight = 120 - rockDepth;
        boolean generateMud = false;
        final int muds = 0;
        final double d1 = LOTRBiome.biomeTerrainNoise.func_151601_a(i * 0.09, k * 0.09);
        final double d2 = LOTRBiome.biomeTerrainNoise.func_151601_a(i * 0.4, k * 0.4);
        if (d1 + d2 > 0.4) {
            generateMud = true;
        }
        for (int j = ySize - 1; j >= stoneHeight; --j) {
            final int index = xzIndex * ySize + j;
            final Block block = blocks[index];
            if (block == super.topBlock || block == super.fillerBlock) {
                if (!generateMud || muds >= 4 + random.nextInt(2)) {
                    blocks[index] = Blocks.stone;
                    meta[index] = 0;
                }
            }
        }
    }
    
    @Override
    public void decorate(final World world, final Random random, final int i, final int k) {
        super.decorate(world, random, i, k);
        for (int l = 0; l < 3; ++l) {
            for (int craters = 1 + random.nextInt(3), l2 = 0; l2 < craters; ++l2) {
                final int i2 = i + random.nextInt(16) + 8;
                final int k2 = k + random.nextInt(16) + 8;
                final int j1 = world.getHeightValue(i2, k2);
                if (j1 > 110) {
                    new LOTRWorldGenVolcanoCrater().generate(world, random, i2, j1, k2);
                }
            }
        }
        for (int l = 0; l < 12; ++l) {
            final int i3 = i + random.nextInt(16) + 8;
            final int k3 = k + random.nextInt(16) + 8;
            final int j2 = world.getHeightValue(i3, k3);
            if (j2 < 120 || random.nextInt(20) == 0) {
                super.decorator.genTree(world, random, i3, j2, k3);
            }
        }
        final WorldGenerator lavaGen = new LOTRWorldGenStreams((Block)Blocks.flowing_lava);
        for (int m = 0; m < 5; ++m) {
            final int i4 = i + random.nextInt(16) + 8;
            final int j2 = 140 + random.nextInt(50);
            final int k2 = k + random.nextInt(16) + 8;
            lavaGen.generate(world, random, i4, j2, k2);
        }
    }
}
