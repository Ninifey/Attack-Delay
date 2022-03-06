// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import net.minecraft.init.Blocks;
import net.minecraft.block.Block;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.LOTRAchievement;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.entity.animal.LOTREntityBear;
import lotr.common.entity.animal.LOTREntityDeer;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.entity.passive.EntityWolf;

public class LOTRBiomeGenRhunRedForest extends LOTRBiomeGenRhunLand
{
    public LOTRBiomeGenRhunRedForest(final int i, final boolean major) {
        super(i, major);
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)EntityWolf.class, 16, 4, 8));
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityDeer.class, 20, 4, 6));
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityBear.class, 10, 1, 4));
        super.npcSpawnList.clear();
        final LOTRBiomeSpawnList.FactionContainer factionList = super.npcSpawnList.newFactionList(100);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists = new LOTRBiomeSpawnList.SpawnListContainer[3];
        final int n = 0;
        final LOTRBiomeSpawnList npcSpawnList = super.npcSpawnList;
        lists[n] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLING_WARRIORS, 10).setSpawnChance(1000);
        final int n2 = 1;
        final LOTRBiomeSpawnList npcSpawnList2 = super.npcSpawnList;
        lists[n2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLING_WARRIORS, 8).setConquestOnly();
        final int n3 = 2;
        final LOTRBiomeSpawnList npcSpawnList3 = super.npcSpawnList;
        lists[n3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLING_GOLD_WARRIORS, 1).setConquestOnly();
        factionList.add(lists);
        final LOTRBiomeSpawnList.FactionContainer factionList2 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists2 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n4 = 0;
        final LOTRBiomeSpawnList npcSpawnList4 = super.npcSpawnList;
        lists2[n4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DORWINION_ELVES, 10);
        final int n5 = 1;
        final LOTRBiomeSpawnList npcSpawnList5 = super.npcSpawnList;
        lists2[n5] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DORWINION_ELF_WARRIORS, 3);
        factionList2.add(lists2);
        super.npcSpawnList.conquestGainRate = 0.5f;
        this.clearBiomeVariants();
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_FOREST);
        super.decorator.treesPerChunk = 6;
        super.decorator.logsPerChunk = 1;
        super.decorator.flowersPerChunk = 4;
        super.decorator.doubleFlowersPerChunk = 1;
        super.decorator.grassPerChunk = 8;
        super.decorator.doubleGrassPerChunk = 2;
        super.decorator.enableFern = true;
        super.decorator.addTree(LOTRTreeType.REDWOOD, 10000);
        super.decorator.addTree(LOTRTreeType.REDWOOD_2, 10000);
        super.decorator.addTree(LOTRTreeType.REDWOOD_3, 5000);
        super.decorator.addTree(LOTRTreeType.REDWOOD_4, 5000);
        super.decorator.addTree(LOTRTreeType.REDWOOD_5, 2000);
        this.registerForestFlowers();
        super.decorator.clearRandomStructures();
        super.decorator.clearVillages();
        super.biomeColors.resetGrass();
        super.biomeColors.setGrass(8951356);
        super.biomeColors.setFog(11259063);
        super.biomeColors.setFoggy(true);
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterRhunRedwood;
    }
    
    @Override
    public void generateBiomeTerrain(final World world, final Random random, final Block[] blocks, final byte[] meta, final int i, final int k, final double stoneNoise, final int height, final LOTRBiomeVariant variant) {
        super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
        final int chunkX = i & 0xF;
        final int chunkZ = k & 0xF;
        final int xzIndex = chunkX * 16 + chunkZ;
        final int ySize = blocks.length / 256;
        if (variant.treeFactor >= 1.0f) {
            final double d1 = LOTRBiome.biomeTerrainNoise.func_151601_a(i * 0.05, k * 0.05);
            final double d2 = LOTRBiome.biomeTerrainNoise.func_151601_a(i * 0.4, k * 0.4);
            if (d1 + d2 > -0.8) {
                final int index = xzIndex * ySize + height;
                if (random.nextFloat() < 0.75f) {
                    blocks[index] = Blocks.dirt;
                    meta[index] = 2;
                }
            }
        }
    }
    
    @Override
    public GrassBlockAndMeta getRandomGrass(final Random random) {
        if (random.nextFloat() < 0.7f) {
            return new GrassBlockAndMeta((Block)Blocks.tallgrass, 2);
        }
        return super.getRandomGrass(random);
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return 0.5f;
    }
}
