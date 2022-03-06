// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.LOTRMod;
import net.minecraft.world.gen.feature.WorldGenDeadBush;
import net.minecraft.world.gen.feature.WorldGenCactus;
import net.minecraft.block.Block;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.LOTRAchievement;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.entity.animal.LOTREntityDesertScorpion;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.entity.animal.LOTREntityCamel;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenLastDesert extends LOTRBiome
{
    protected WorldGenerator boulderGen;
    
    public LOTRBiomeGenLastDesert(final int i, final boolean major) {
        super(i, major);
        this.boulderGen = new LOTRWorldGenBoulder(Blocks.stone, 0, 1, 3);
        this.setDisableRain();
        super.topBlock = (Block)Blocks.sand;
        super.fillerBlock = (Block)Blocks.sand;
        super.spawnableCreatureList.clear();
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityCamel.class, 10, 2, 6));
        super.spawnableLOTRAmbientList.clear();
        super.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityDesertScorpion.class, 10, 4, 4));
        super.npcSpawnList.clear();
        super.variantChance = 0.3f;
        this.addBiomeVariant(LOTRBiomeVariant.STEPPE);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS);
        super.decorator.grassPerChunk = 0;
        super.decorator.doubleGrassPerChunk = 0;
        super.decorator.flowersPerChunk = 0;
        super.decorator.doubleFlowersPerChunk = 0;
        super.decorator.cactiPerChunk = 0;
        super.decorator.deadBushPerChunk = 0;
        super.decorator.addTree(LOTRTreeType.OAK_DEAD, 1000);
        this.registerRhunPlainsFlowers();
        super.biomeColors.setGrass(16767886);
        super.biomeColors.setSky(14736588);
        super.biomeColors.setClouds(10853237);
        super.biomeColors.setFog(14406319);
        this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterLastDesert;
    }
    
    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.RHUN;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.RHUN.getSubregion("lastDesert");
    }
    
    @Override
    public boolean getEnableRiver() {
        return false;
    }
    
    @Override
    public void generateBiomeTerrain(final World world, final Random random, final Block[] blocks, final byte[] meta, final int i, final int k, final double stoneNoise, final int height, final LOTRBiomeVariant variant) {
        final Block topBlock_pre = super.topBlock;
        final int topBlockMeta_pre = super.topBlockMeta;
        final Block fillerBlock_pre = super.fillerBlock;
        final int fillerBlockMeta_pre = super.fillerBlockMeta;
        final double d1 = LOTRBiome.biomeTerrainNoise.func_151601_a(i * 0.07, k * 0.07);
        double d2 = LOTRBiome.biomeTerrainNoise.func_151601_a(i * 0.4, k * 0.4);
        d2 *= 0.6;
        if (d1 + d2 > 0.7) {
            super.topBlock = (Block)Blocks.grass;
            super.topBlockMeta = 0;
            super.fillerBlock = Blocks.dirt;
            super.fillerBlockMeta = 0;
        }
        else if (d1 + d2 > 0.2) {
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
        if (random.nextInt(8) == 0) {
            final int i2 = i + random.nextInt(16) + 8;
            final int k2 = k + random.nextInt(16) + 8;
            final int j1 = world.getHeightValue(i2, k2);
            this.getRandomWorldGenForGrass(random).generate(world, random, i2, j1, k2);
        }
        if (random.nextInt(100) == 0) {
            final int i2 = i + random.nextInt(16) + 8;
            final int k2 = k + random.nextInt(16) + 8;
            final int j1 = world.getHeightValue(i2, k2);
            new WorldGenCactus().generate(world, random, i2, j1, k2);
        }
        if (random.nextInt(20) == 0) {
            final int i2 = i + random.nextInt(16) + 8;
            final int k2 = k + random.nextInt(16) + 8;
            final int j1 = world.getHeightValue(i2, k2);
            new WorldGenDeadBush((Block)Blocks.deadbush).generate(world, random, i2, j1, k2);
        }
        if (random.nextInt(32) == 0) {
            for (int boulders = 1 + random.nextInt(4), l = 0; l < boulders; ++l) {
                final int i3 = i + random.nextInt(16) + 8;
                final int k3 = k + random.nextInt(16) + 8;
                this.boulderGen.generate(world, random, i3, world.getHeightValue(i3, k3), k3);
            }
        }
        if (random.nextInt(500) == 0) {
            for (int trees = 1 + random.nextInt(4), l = 0; l < trees; ++l) {
                final int i3 = i + random.nextInt(8) + 8;
                final int k3 = k + random.nextInt(8) + 8;
                final int j2 = world.getHeightValue(i3, k3);
                super.decorator.genTree(world, random, i3, j2, k3);
            }
        }
    }
    
    @Override
    public float getTreeIncreaseChance() {
        return 0.0f;
    }
    
    @Override
    public GrassBlockAndMeta getRandomGrass(final Random random) {
        return new GrassBlockAndMeta(LOTRMod.aridGrass, 0);
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return 0.02f;
    }
}
