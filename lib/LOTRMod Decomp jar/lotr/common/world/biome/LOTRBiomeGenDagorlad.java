// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.biome.variant.LOTRBiomeVariant;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.LOTRAchievement;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.LOTRMod;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

public class LOTRBiomeGenDagorlad extends LOTRBiome
{
    private NoiseGeneratorPerlin noiseDirt;
    private NoiseGeneratorPerlin noiseGravel;
    private NoiseGeneratorPerlin noiseMordorGravel;
    private WorldGenerator boulderGen;
    
    public LOTRBiomeGenDagorlad(final int i, final boolean major) {
        super(i, major);
        this.noiseDirt = new NoiseGeneratorPerlin(new Random(42956029606L), 1);
        this.noiseGravel = new NoiseGeneratorPerlin(new Random(7185609602367L), 1);
        this.noiseMordorGravel = new NoiseGeneratorPerlin(new Random(12480634985056L), 1);
        this.boulderGen = new LOTRWorldGenBoulder(Blocks.stone, 0, 1, 2);
        super.topBlock = LOTRMod.mordorDirt;
        super.topBlockMeta = 0;
        super.fillerBlock = LOTRMod.mordorDirt;
        super.fillerBlockMeta = 0;
        super.spawnableCreatureList.clear();
        super.spawnableWaterCreatureList.clear();
        super.spawnableLOTRAmbientList.clear();
        final LOTRBiomeSpawnList.FactionContainer factionList = super.npcSpawnList.newFactionList(100);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists = new LOTRBiomeSpawnList.SpawnListContainer[3];
        final int n = 0;
        final LOTRBiomeSpawnList npcSpawnList = super.npcSpawnList;
        lists[n] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 10);
        final int n2 = 1;
        final LOTRBiomeSpawnList npcSpawnList2 = super.npcSpawnList;
        lists[n2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_BOMBARDIERS, 1);
        final int n3 = 2;
        final LOTRBiomeSpawnList npcSpawnList3 = super.npcSpawnList;
        lists[n3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_WARGS, 2).setConquestOnly();
        factionList.add(lists);
        final LOTRBiomeSpawnList.FactionContainer factionList2 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists2 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n4 = 0;
        final LOTRBiomeSpawnList npcSpawnList4 = super.npcSpawnList;
        lists2[n4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_SOLDIERS, 5);
        final int n5 = 1;
        final LOTRBiomeSpawnList npcSpawnList5 = super.npcSpawnList;
        lists2[n5] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RANGERS_ITHILIEN, 5);
        factionList2.add(lists2);
        super.decorator.treesPerChunk = 0;
        super.decorator.flowersPerChunk = 0;
        super.decorator.grassPerChunk = 0;
        super.decorator.addTree(LOTRTreeType.CHARRED, 1000);
        super.biomeColors.setSky(5523773);
        super.biomeColors.setClouds(3355443);
        super.biomeColors.setFog(6710886);
        super.biomeColors.setWater(2498845);
        this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
        super.invasionSpawns.addInvasion(LOTRInvasions.MORDOR, LOTREventSpawner.EventChance.COMMON);
        super.invasionSpawns.addInvasion(LOTRInvasions.MORDOR_BLACK_URUK, LOTREventSpawner.EventChance.UNCOMMON);
        super.invasionSpawns.addInvasion(LOTRInvasions.MORDOR_WARG, LOTREventSpawner.EventChance.UNCOMMON);
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterDagorlad;
    }
    
    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.DAGORLAD;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.MORDOR.getSubregion("dagorlad");
    }
    
    @Override
    public boolean getEnableRiver() {
        return false;
    }
    
    @Override
    public LOTRRoadType getRoadBlock() {
        return LOTRRoadType.MORDOR;
    }
    
    @Override
    public void generateBiomeTerrain(final World world, final Random random, final Block[] blocks, final byte[] meta, final int i, final int k, final double stoneNoise, final int height, final LOTRBiomeVariant variant) {
        final Block topBlock_pre = super.topBlock;
        final int topBlockMeta_pre = super.topBlockMeta;
        final Block fillerBlock_pre = super.fillerBlock;
        final int fillerBlockMeta_pre = super.fillerBlockMeta;
        final double d1 = this.noiseDirt.func_151601_a(i * 0.09, k * 0.09);
        final double d2 = this.noiseDirt.func_151601_a(i * 0.6, k * 0.6);
        final double d3 = this.noiseGravel.func_151601_a(i * 0.09, k * 0.09);
        final double d4 = this.noiseGravel.func_151601_a(i * 0.6, k * 0.6);
        final double d5 = this.noiseMordorGravel.func_151601_a(i * 0.09, k * 0.09);
        final double d6 = this.noiseMordorGravel.func_151601_a(i * 0.6, k * 0.6);
        if (d5 + d6 > 0.5) {
            super.topBlock = LOTRMod.mordorGravel;
            super.topBlockMeta = 0;
        }
        else if (d3 + d4 > 0.6) {
            super.topBlock = Blocks.gravel;
            super.topBlockMeta = 0;
        }
        else if (d1 + d2 > 0.6) {
            super.topBlock = Blocks.dirt;
            super.topBlockMeta = 1;
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
        if (random.nextInt(24) == 0) {
            for (int boulders = 1 + random.nextInt(4), l = 0; l < boulders; ++l) {
                final int i2 = i + random.nextInt(16) + 8;
                final int k2 = k + random.nextInt(16) + 8;
                this.boulderGen.generate(world, random, i2, world.getHeightValue(i2, k2), k2);
            }
        }
    }
    
    @Override
    public float getTreeIncreaseChance() {
        return 0.05f;
    }
    
    @Override
    public int spawnCountMultiplier() {
        return 3;
    }
}
