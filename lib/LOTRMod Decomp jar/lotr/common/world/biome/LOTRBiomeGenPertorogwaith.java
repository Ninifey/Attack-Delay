// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.feature.LOTRWorldGenSkullPile;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.LOTRAchievement;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.structure2.LOTRWorldGenStoneRuin;
import lotr.common.world.structure2.LOTRWorldGenHalfTrollWarlordHouse;
import lotr.common.world.structure2.LOTRWorldGenHalfTrollHouse;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.entity.animal.LOTREntityGemsbok;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.entity.animal.LOTREntityRhino;
import lotr.common.LOTRMod;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenPertorogwaith extends LOTRBiome
{
    private WorldGenerator boulderGen;
    private WorldGenerator clayBoulderGen;
    private WorldGenerator deadMoundGen;
    
    public LOTRBiomeGenPertorogwaith(final int i, final boolean major) {
        super(i, major);
        this.boulderGen = new LOTRWorldGenBoulder(Blocks.stone, 0, 1, 3);
        this.clayBoulderGen = new LOTRWorldGenBoulder(Blocks.hardened_clay, 0, 1, 3);
        this.deadMoundGen = new LOTRWorldGenBoulder(LOTRMod.wasteBlock, 0, 1, 3);
        super.spawnableCreatureList.clear();
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityRhino.class, 8, 4, 4));
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityGemsbok.class, 4, 4, 4));
        super.spawnableLOTRAmbientList.clear();
        final LOTRBiomeSpawnList.FactionContainer factionList = super.npcSpawnList.newFactionList(100);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists = { null };
        final int n = 0;
        final LOTRBiomeSpawnList npcSpawnList = super.npcSpawnList;
        lists[n] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HALF_TROLLS, 10);
        factionList.add(lists);
        final LOTRBiomeSpawnList.FactionContainer factionList2 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists2 = new LOTRBiomeSpawnList.SpawnListContainer[3];
        final int n2 = 0;
        final LOTRBiomeSpawnList npcSpawnList2 = super.npcSpawnList;
        lists2[n2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 10);
        final int n3 = 1;
        final LOTRBiomeSpawnList npcSpawnList3 = super.npcSpawnList;
        lists2[n3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 2).setConquestThreshold(50.0f);
        final int n4 = 2;
        final LOTRBiomeSpawnList npcSpawnList4 = super.npcSpawnList;
        lists2[n4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.OLOG_HAI, 1).setConquestThreshold(200.0f);
        factionList2.add(lists2);
        final LOTRBiomeSpawnList.FactionContainer factionList3 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists3 = { null };
        final int n5 = 0;
        final LOTRBiomeSpawnList npcSpawnList5 = super.npcSpawnList;
        lists3[n5] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GULF_WARRIORS, 10);
        factionList3.add(lists3);
        final LOTRBiomeSpawnList.FactionContainer factionList4 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists4 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n6 = 0;
        final LOTRBiomeSpawnList npcSpawnList6 = super.npcSpawnList;
        lists4[n6] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORWAITH_WARRIORS, 10);
        final int n7 = 1;
        final LOTRBiomeSpawnList npcSpawnList7 = super.npcSpawnList;
        lists4[n7] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORWAITH, 3);
        factionList4.add(lists4);
        final LOTRBiomeSpawnList.FactionContainer factionList5 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists5 = { null };
        final int n8 = 0;
        final LOTRBiomeSpawnList npcSpawnList8 = super.npcSpawnList;
        lists5[n8] = LOTRBiomeSpawnList.entry(LOTRSpawnList.TAURETHRIM_WARRIORS, 10);
        factionList5.add(lists5);
        super.variantChance = 0.6f;
        this.addBiomeVariant(LOTRBiomeVariant.FOREST);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
        this.addBiomeVariant(LOTRBiomeVariant.STEPPE);
        this.addBiomeVariant(LOTRBiomeVariant.STEPPE_BARREN);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS_FOREST);
        this.addBiomeVariant(LOTRBiomeVariant.DEADFOREST_OAK);
        this.addBiomeVariant(LOTRBiomeVariant.SHRUBLAND_OAK);
        this.addBiomeVariant(LOTRBiomeVariant.SCRUBLAND, 3.0f);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS_SCRUBLAND, 2.0f);
        this.addBiomeVariant(LOTRBiomeVariant.WASTELAND, 4.0f);
        super.decorator.grassPerChunk = 10;
        super.decorator.doubleGrassPerChunk = 4;
        super.decorator.flowersPerChunk = 0;
        super.decorator.canePerChunk = 10;
        super.decorator.addTree(LOTRTreeType.OAK_DESERT, 50);
        super.decorator.addTree(LOTRTreeType.OAK_DEAD, 100);
        super.decorator.addTree(LOTRTreeType.ACACIA, 100);
        super.decorator.addTree(LOTRTreeType.ACACIA_DEAD, 200);
        super.decorator.addTree(LOTRTreeType.BAOBAB, 10);
        this.registerHaradFlowers();
        super.decorator.addRandomStructure(new LOTRWorldGenHalfTrollHouse(false), 40);
        super.decorator.addRandomStructure(new LOTRWorldGenHalfTrollWarlordHouse(false), 200);
        super.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.MORDOR(1, 3), 100);
        super.biomeColors.setSky(8551538);
        super.biomeColors.setClouds(7500401);
        super.biomeColors.setFog(7500401);
        super.biomeColors.setWater(9080439);
        this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterPertorogwaith;
    }
    
    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.PERTOROGWAITH;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.PERTOROGWAITH.getSubregion("pertorogwaith");
    }
    
    @Override
    public void decorate(final World world, final Random random, final int i, final int k) {
        super.decorate(world, random, i, k);
        if (random.nextInt(6) == 0) {
            for (int boulders = 1 + random.nextInt(4), l = 0; l < boulders; ++l) {
                final int i2 = i + random.nextInt(16) + 8;
                final int k2 = k + random.nextInt(16) + 8;
                this.boulderGen.generate(world, random, i2, world.getHeightValue(i2, k2), k2);
            }
        }
        if (random.nextInt(12) == 0) {
            for (int boulders = 1 + random.nextInt(4), l = 0; l < boulders; ++l) {
                final int i2 = i + random.nextInt(16) + 8;
                final int k2 = k + random.nextInt(16) + 8;
                this.clayBoulderGen.generate(world, random, i2, world.getHeightValue(i2, k2), k2);
            }
        }
        if (random.nextInt(40) == 0) {
            for (int j = 0; j < 3; ++j) {
                final int i3 = i + random.nextInt(16) + 8;
                final int k3 = k + random.nextInt(16) + 8;
                final int j2 = world.getHeightValue(i3, k3);
                this.deadMoundGen.generate(world, random, i3, j2, k3);
                new LOTRWorldGenSkullPile().generate(world, random, i3, j2, k3);
            }
        }
    }
    
    @Override
    public float getTreeIncreaseChance() {
        return 0.25f;
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return 0.05f;
    }
    
    @Override
    public boolean canSpawnHostilesInDay() {
        return true;
    }
    
    @Override
    public int spawnCountMultiplier() {
        return 2;
    }
}
