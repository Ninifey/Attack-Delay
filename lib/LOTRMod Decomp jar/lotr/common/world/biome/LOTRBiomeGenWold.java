// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import net.minecraft.init.Blocks;
import net.minecraft.block.Block;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.village.LOTRVillageGen;
import lotr.common.world.village.LOTRVillageGenRohan;
import lotr.common.world.structure2.LOTRWorldGenRohanWatchtower;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import lotr.common.LOTRMod;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenWold extends LOTRBiomeGenRohan
{
    private WorldGenerator woldBoulderGen;
    
    public LOTRBiomeGenWold(final int i, final boolean major) {
        super(i, major);
        this.woldBoulderGen = new LOTRWorldGenBoulder(LOTRMod.rock, 2, 2, 4);
        this.clearBiomeVariants();
        this.addBiomeVariant(LOTRBiomeVariant.FLOWERS);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
        this.addBiomeVariant(LOTRBiomeVariant.STEPPE);
        this.addBiomeVariant(LOTRBiomeVariant.STEPPE_BARREN);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS);
        this.addBiomeVariant(LOTRBiomeVariant.DEADFOREST_OAK);
        super.decorator.treesPerChunk = 0;
        super.decorator.setTreeCluster(8, 100);
        super.decorator.flowersPerChunk = 1;
        super.decorator.grassPerChunk = 6;
        super.decorator.doubleGrassPerChunk = 1;
        super.decorator.addTree(LOTRTreeType.OAK_DEAD, 400);
        super.decorator.addTree(LOTRTreeType.BEECH_DEAD, 400);
        this.registerPlainsFlowers();
        super.decorator.clearRandomStructures();
        super.decorator.addRandomStructure(new LOTRWorldGenRohanWatchtower(false), 1000);
        super.decorator.clearVillages();
        super.decorator.addVillage(new LOTRVillageGenRohan(this, 0.25f));
        this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_UNCOMMON);
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.ROHAN.getSubregion("wold");
    }
    
    @Override
    public void generateBiomeTerrain(final World world, final Random random, final Block[] blocks, final byte[] meta, final int i, final int k, final double stoneNoise, final int height, final LOTRBiomeVariant variant) {
        final Block topBlock_pre = super.topBlock;
        final int topBlockMeta_pre = super.topBlockMeta;
        final Block fillerBlock_pre = super.fillerBlock;
        final int fillerBlockMeta_pre = super.fillerBlockMeta;
        final double d1 = LOTRBiome.biomeTerrainNoise.func_151601_a(i * 0.005, k * 0.005);
        final double d2 = LOTRBiome.biomeTerrainNoise.func_151601_a(i * 0.4, k * 0.4);
        if (d1 + d2 > 1.0) {
            super.topBlock = Blocks.dirt;
            super.topBlockMeta = 1;
            super.fillerBlock = super.topBlock;
            super.fillerBlockMeta = super.topBlockMeta;
        }
        super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
        super.topBlock = topBlock_pre;
        super.topBlockMeta = topBlockMeta_pre;
        super.fillerBlock = fillerBlock_pre;
        super.fillerBlockMeta = fillerBlockMeta_pre;
    }
    
    @Override
    public void decorate(final World world, final Random random, final int i, final int k) {
        super.decorate(world, random, i, k);
        if (random.nextInt(16) == 0) {
            for (int l = 0; l < 4; ++l) {
                final int i2 = i + random.nextInt(16) + 8;
                final int k2 = k + random.nextInt(16) + 8;
                this.woldBoulderGen.generate(world, random, i2, world.getHeightValue(i2, k2), k2);
            }
        }
        if (random.nextInt(30) == 0) {
            for (int rocks = 10 + random.nextInt(20), j = 0; j < rocks; ++j) {
                final int i3 = i + random.nextInt(16) + 8;
                final int k3 = k + random.nextInt(16) + 8;
                final int j2 = world.getHeightValue(i3, k3);
                final Block block = world.getBlock(i3, j2 - 1, k3);
                if (block == super.topBlock || block == super.fillerBlock) {
                    Block rockBlock;
                    int rockMeta;
                    if (random.nextBoolean()) {
                        rockBlock = LOTRMod.rock;
                        rockMeta = 2;
                    }
                    else if (random.nextInt(5) == 0) {
                        rockBlock = Blocks.gravel;
                        rockMeta = 0;
                    }
                    else {
                        rockBlock = Blocks.stone;
                        rockMeta = 0;
                    }
                    if (random.nextInt(3) == 0) {
                        world.setBlock(i3, j2 - 1, k3, rockBlock, rockMeta, 2);
                    }
                    else {
                        world.setBlock(i3, j2, k3, rockBlock, rockMeta, 2);
                        block.onPlantGrow(world, i3, j2 - 1, k3, i3, j2, k3);
                    }
                }
            }
        }
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return 0.1f;
    }
    
    @Override
    public float getTreeIncreaseChance() {
        return 0.005f;
    }
    
    @Override
    public int spawnCountMultiplier() {
        return super.spawnCountMultiplier() * 3;
    }
}
