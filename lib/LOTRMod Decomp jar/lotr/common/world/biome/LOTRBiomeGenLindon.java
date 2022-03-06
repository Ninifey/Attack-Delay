// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.LOTRAchievement;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.entity.npc.LOTREntityRivendellTrader;
import lotr.common.entity.npc.LOTREntityBlueDwarfMerchant;
import lotr.common.entity.npc.LOTREntityGaladhrimTrader;
import lotr.common.world.structure2.LOTRWorldGenHighElvenTower;
import lotr.common.world.structure2.LOTRWorldGenHighElvenForge;
import lotr.common.world.structure.LOTRWorldGenHighElvenHall;
import lotr.common.world.structure2.LOTRWorldGenHighElfHouse;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import lotr.common.LOTRMod;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.entity.animal.LOTREntitySeagull;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.entity.animal.LOTREntityHorse;

public class LOTRBiomeGenLindon extends LOTRBiome
{
    public LOTRBiomeGenLindon(final int i, final boolean major) {
        super(i, major);
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityHorse.class, 5, 2, 6));
        super.spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntitySeagull.class, 6, 4, 4));
        final LOTRBiomeSpawnList.FactionContainer factionList = super.npcSpawnList.newFactionList(100);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n = 0;
        final LOTRBiomeSpawnList npcSpawnList = super.npcSpawnList;
        lists[n] = LOTRBiomeSpawnList.entry(LOTRSpawnList.LINDON_ELVES, 10);
        final int n2 = 1;
        final LOTRBiomeSpawnList npcSpawnList2 = super.npcSpawnList;
        lists[n2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.LINDON_WARRIORS, 2);
        factionList.add(lists);
        final LOTRBiomeSpawnList.FactionContainer factionList2 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists2 = new LOTRBiomeSpawnList.SpawnListContainer[3];
        final int n3 = 0;
        final LOTRBiomeSpawnList npcSpawnList3 = super.npcSpawnList;
        lists2[n3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_ORCS, 10);
        final int n4 = 1;
        final LOTRBiomeSpawnList npcSpawnList4 = super.npcSpawnList;
        lists2[n4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_WARGS, 2);
        final int n5 = 2;
        final LOTRBiomeSpawnList npcSpawnList5 = super.npcSpawnList;
        lists2[n5] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_URUKS, 1).setConquestThreshold(50.0f);
        factionList2.add(lists2);
        final LOTRBiomeSpawnList.FactionContainer factionList3 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists3 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n6 = 0;
        final LOTRBiomeSpawnList npcSpawnList6 = super.npcSpawnList;
        lists3[n6] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_ORCS, 10);
        final int n7 = 1;
        final LOTRBiomeSpawnList npcSpawnList7 = super.npcSpawnList;
        lists3[n7] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_WARGS, 2);
        factionList3.add(lists3);
        super.npcSpawnList.conquestGainRate = 0.5f;
        this.addBiomeVariant(LOTRBiomeVariant.FLOWERS);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
        this.addBiomeVariant(LOTRBiomeVariant.STEPPE);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS_FOREST);
        this.addBiomeVariant(LOTRBiomeVariant.DENSEFOREST_OAK);
        this.addBiomeVariant(LOTRBiomeVariant.DENSEFOREST_BIRCH);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_BEECH, 0.2f);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_BIRCH, 0.5f);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_ASPEN, 0.5f);
        this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_APPLE_PEAR, 1.0f);
        super.decorator.addOre((WorldGenerator)new WorldGenMinable(LOTRMod.oreQuendite, 6), 6.0f, 0, 48);
        super.decorator.setTreeCluster(10, 20);
        super.decorator.treesPerChunk = 0;
        super.decorator.willowPerChunk = 1;
        super.decorator.flowersPerChunk = 3;
        super.decorator.grassPerChunk = 8;
        super.decorator.doubleGrassPerChunk = 1;
        super.decorator.whiteSand = true;
        super.decorator.addTree(LOTRTreeType.OAK, 100);
        super.decorator.addTree(LOTRTreeType.OAK_LARGE, 25);
        super.decorator.addTree(LOTRTreeType.BIRCH, 500);
        super.decorator.addTree(LOTRTreeType.BIRCH_TALL, 500);
        super.decorator.addTree(LOTRTreeType.BIRCH_LARGE, 200);
        super.decorator.addTree(LOTRTreeType.BIRCH_PARTY, 50);
        super.decorator.addTree(LOTRTreeType.BEECH, 100);
        super.decorator.addTree(LOTRTreeType.BEECH_LARGE, 25);
        super.decorator.addTree(LOTRTreeType.CHESTNUT, 40);
        super.decorator.addTree(LOTRTreeType.CHESTNUT_LARGE, 10);
        super.decorator.addTree(LOTRTreeType.ASPEN, 300);
        super.decorator.addTree(LOTRTreeType.ASPEN_LARGE, 100);
        super.decorator.addTree(LOTRTreeType.APPLE, 2);
        super.decorator.addTree(LOTRTreeType.PEAR, 2);
        this.registerPlainsFlowers();
        super.decorator.addRandomStructure(new LOTRWorldGenHighElfHouse(false), 300);
        super.decorator.addRandomStructure(new LOTRWorldGenHighElvenHall(false), 600);
        super.decorator.addRandomStructure(new LOTRWorldGenHighElvenForge(false), 1000);
        super.decorator.addRandomStructure(new LOTRWorldGenHighElvenTower(false), 900);
        this.registerTravellingTrader(LOTREntityGaladhrimTrader.class);
        this.registerTravellingTrader(LOTREntityBlueDwarfMerchant.class);
        this.registerTravellingTrader(LOTREntityRivendellTrader.class);
        this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
        super.invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD, LOTREventSpawner.EventChance.RARE);
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterLindon;
    }
    
    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.LINDON;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.LINDON.getSubregion("lindon");
    }
    
    @Override
    public LOTRRoadType getRoadBlock() {
        return LOTRRoadType.HIGH_ELVEN;
    }
    
    @Override
    public boolean hasSeasonalGrass() {
        return false;
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return 0.25f;
    }
    
    @Override
    public int spawnCountMultiplier() {
        return 5;
    }
}
