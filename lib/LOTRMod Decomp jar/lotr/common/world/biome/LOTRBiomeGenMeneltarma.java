// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import net.minecraft.world.gen.feature.WorldGenFlowers;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import net.minecraft.block.Block;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.LOTRAchievement;
import lotr.common.world.map.LOTRWaypoint;
import java.util.List;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.LOTRMod;
import java.util.Collection;
import java.util.ArrayList;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenMeneltarma extends LOTRBiomeGenOcean
{
    private WorldGenerator boulderGen;
    
    public LOTRBiomeGenMeneltarma(final int i, final boolean major) {
        super(i, major);
        this.boulderGen = new LOTRWorldGenBoulder(Blocks.stone, 0, 1, 4);
        super.spawnableCreatureList.clear();
        super.spawnableWaterCreatureList.clear();
        super.spawnableLOTRAmbientList.clear();
        super.decorator.setTreeCluster(8, 20);
        super.decorator.treesPerChunk = 0;
        super.decorator.flowersPerChunk = 5;
        super.decorator.doubleFlowersPerChunk = 1;
        super.decorator.grassPerChunk = 6;
        super.decorator.doubleGrassPerChunk = 2;
        super.decorator.generateAthelas = true;
        super.decorator.clearTrees();
        super.decorator.addTree(LOTRTreeType.CEDAR, 1000);
        super.decorator.addTree(LOTRTreeType.CEDAR_LARGE, 500);
        super.decorator.addTree(LOTRTreeType.OAK, 200);
        super.decorator.addTree(LOTRTreeType.OAK_TALL, 200);
        super.decorator.addTree(LOTRTreeType.OAK_LARGE, 400);
        super.decorator.addTree(LOTRTreeType.BIRCH, 200);
        super.decorator.addTree(LOTRTreeType.BIRCH_TALL, 200);
        super.decorator.addTree(LOTRTreeType.BIRCH_LARGE, 400);
        super.decorator.addTree(LOTRTreeType.BEECH, 200);
        super.decorator.addTree(LOTRTreeType.BEECH_LARGE, 400);
        final List flowerDupes = new ArrayList();
        for (int l = 0; l < 10; ++l) {
            this.flowers.clear();
            this.registerPlainsFlowers();
            flowerDupes.addAll(this.flowers);
        }
        this.flowers.clear();
        this.flowers.addAll(flowerDupes);
        this.addFlower(LOTRMod.athelas, 0, 10);
        super.decorator.clearRandomStructures();
        this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
    }
    
    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.MENELTARMA;
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterMeneltarma;
    }
    
    @Override
    public boolean isHiddenBiome() {
        return true;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.SEA.getSubregion("meneltarma");
    }
    
    @Override
    public void generateBiomeTerrain(final World world, final Random random, final Block[] blocks, final byte[] meta, final int i, final int k, final double stoneNoise, final int height, final LOTRBiomeVariant variant) {
        final Block topBlock_pre = super.topBlock;
        final int topBlockMeta_pre = super.topBlockMeta;
        final Block fillerBlock_pre = super.fillerBlock;
        final int fillerBlockMeta_pre = super.fillerBlockMeta;
        final double d1 = LOTRBiome.biomeTerrainNoise.func_151601_a(i * 0.1, k * 0.1);
        if (d1 > -0.1) {
            super.topBlock = Blocks.stone;
            super.topBlockMeta = 0;
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
        if (random.nextInt(2) == 0) {
            for (int l = 0; l < 3; ++l) {
                final int i2 = i + random.nextInt(16) + 8;
                final int k2 = k + random.nextInt(16) + 8;
                this.boulderGen.generate(world, random, i2, world.getHeightValue(i2, k2), k2);
            }
        }
        if (random.nextInt(3) == 0) {
            final int i3 = i + random.nextInt(16) + 8;
            final int j1 = random.nextInt(128);
            final int k2 = k + random.nextInt(16) + 8;
            new WorldGenFlowers(LOTRMod.athelas).generate(world, random, i3, j1, k2);
        }
    }
    
    @Override
    public float getTreeIncreaseChance() {
        return 0.2f;
    }
}
