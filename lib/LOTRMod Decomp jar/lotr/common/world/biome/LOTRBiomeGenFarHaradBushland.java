// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenFarHaradBushland extends LOTRBiomeGenFarHarad
{
    private WorldGenerator boulderGen;
    
    public LOTRBiomeGenFarHaradBushland(final int i, final boolean major) {
        super(i, major);
        this.boulderGen = new LOTRWorldGenBoulder(Blocks.stone, 0, 1, 3);
        this.addBiomeVariant(LOTRBiomeVariant.FLOWERS);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
        this.addBiomeVariant(LOTRBiomeVariant.STEPPE);
        this.addBiomeVariant(LOTRBiomeVariant.STEPPE_BARREN);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS_FOREST);
        this.addBiomeVariant(LOTRBiomeVariant.DEADFOREST_OAK);
        this.addBiomeVariant(LOTRBiomeVariant.SHRUBLAND_OAK);
        super.decorator.treesPerChunk = 0;
        super.decorator.setTreeCluster(3, 3);
        super.decorator.logsPerChunk = 1;
        super.decorator.grassPerChunk = 16;
        super.decorator.doubleGrassPerChunk = 10;
        super.decorator.cornPerChunk = 4;
        super.decorator.addTree(LOTRTreeType.BIRCH, 100);
        super.decorator.addTree(LOTRTreeType.BIRCH_TALL, 100);
        super.decorator.addTree(LOTRTreeType.BIRCH_LARGE, 25);
        super.biomeColors.setGrass(13414999);
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.FAR_HARAD.getSubregion("bushland");
    }
    
    @Override
    public void decorate(final World world, final Random random, final int i, final int k) {
        super.decorate(world, random, i, k);
        if (random.nextInt(32) == 0) {
            for (int boulders = 1 + random.nextInt(4), l = 0; l < boulders; ++l) {
                final int i2 = i + random.nextInt(16) + 8;
                final int k2 = k + random.nextInt(16) + 8;
                this.boulderGen.generate(world, random, i2, world.getHeightValue(i2, k2), k2);
            }
        }
        if (random.nextInt(16) == 0) {
            for (int termites = 1 + random.nextInt(4), l = 0; l < termites; ++l) {
                final int i2 = i + random.nextInt(16) + 8;
                final int k2 = k + random.nextInt(16) + 8;
                final int j1 = world.getHeightValue(i2, k2);
                new LOTRWorldGenBoulder(LOTRMod.termiteMound, 0, 1, 4).generate(world, random, i2, j1, k2);
                for (int x = 0; x < 5; ++x) {
                    final int i3 = i2 - random.nextInt(5) + random.nextInt(5);
                    final int k3 = k2 - random.nextInt(5) + random.nextInt(5);
                    final int j2 = world.getHeightValue(i3, k3);
                    if (world.getBlock(i3, j2 - 1, k2).isOpaqueCube()) {
                        for (int j3 = j2 + random.nextInt(4), j4 = j2; j4 <= j3; ++j4) {
                            world.setBlock(i3, j4, k3, LOTRMod.termiteMound);
                            world.getBlock(i3, j4 - 1, k3).onPlantGrow(world, i3, j4 - 1, k3, i3, j4 - 1, k3);
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public float getTreeIncreaseChance() {
        return 0.05f;
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return 0.75f;
    }
}
