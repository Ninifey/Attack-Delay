// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.map.LOTRWaypoint;
import lotr.common.LOTRAchievement;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.LOTRMod;

public class LOTRBiomeGenRivendellHills extends LOTRBiomeGenRivendell
{
    public LOTRBiomeGenRivendellHills(final int i, final boolean major) {
        super(i, major);
        super.fillerBlock = LOTRMod.rock;
        super.fillerBlockMeta = 5;
        super.npcSpawnList.clear();
        final LOTRBiomeSpawnList.FactionContainer factionList = super.npcSpawnList.newFactionList(100);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists = { null };
        final int n = 0;
        final LOTRBiomeSpawnList npcSpawnList = super.npcSpawnList;
        lists[n] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RIVENDELL_WARRIORS, 10).setSpawnChance(500);
        factionList.add(lists);
        final LOTRBiomeSpawnList.FactionContainer factionList2 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists2 = new LOTRBiomeSpawnList.SpawnListContainer[3];
        final int n2 = 0;
        final LOTRBiomeSpawnList npcSpawnList2 = super.npcSpawnList;
        lists2[n2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_ORCS, 10);
        final int n3 = 1;
        final LOTRBiomeSpawnList npcSpawnList3 = super.npcSpawnList;
        lists2[n3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_WARGS, 2);
        final int n4 = 2;
        final LOTRBiomeSpawnList npcSpawnList4 = super.npcSpawnList;
        lists2[n4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_URUKS, 2).setConquestThreshold(100.0f);
        factionList2.add(lists2);
        final LOTRBiomeSpawnList.FactionContainer factionList3 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists3 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n5 = 0;
        final LOTRBiomeSpawnList npcSpawnList5 = super.npcSpawnList;
        lists3[n5] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_ORCS, 10);
        final int n6 = 1;
        final LOTRBiomeSpawnList npcSpawnList6 = super.npcSpawnList;
        lists3[n6] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_WARGS, 2);
        factionList3.add(lists3);
        super.npcSpawnList.conquestGainRate = 0.2f;
        this.clearBiomeVariants();
        super.variantChance = 0.4f;
        this.addBiomeVariant(LOTRBiomeVariant.FLOWERS);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
        super.decorator.treesPerChunk = 3;
        super.decorator.flowersPerChunk = 2;
        super.decorator.grassPerChunk = 10;
        super.decorator.doubleGrassPerChunk = 2;
        super.decorator.clearTrees();
        super.decorator.addTree(LOTRTreeType.PINE, 1000);
        super.decorator.addTree(LOTRTreeType.PINE_SHRUB, 200);
        super.decorator.addTree(LOTRTreeType.FIR, 100);
        super.decorator.addTree(LOTRTreeType.SPRUCE, 100);
        super.decorator.addTree(LOTRTreeType.ASPEN, 100);
        super.decorator.addTree(LOTRTreeType.ASPEN_LARGE, 50);
        super.decorator.addTree(LOTRTreeType.OAK, 100);
        super.decorator.addTree(LOTRTreeType.OAK_LARGE, 50);
        super.biomeColors.resetGrass();
        super.decorator.clearRandomStructures();
        super.invasionSpawns.clearInvasions();
        super.invasionSpawns.addInvasion(LOTRInvasions.HIGH_ELF_RIVENDELL, LOTREventSpawner.EventChance.UNCOMMON);
        super.invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD, LOTREventSpawner.EventChance.RARE);
        super.invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD_WARG, LOTREventSpawner.EventChance.RARE);
        super.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR, LOTREventSpawner.EventChance.RARE);
        super.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR_HILLMEN, LOTREventSpawner.EventChance.RARE);
        super.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR_WARG, LOTREventSpawner.EventChance.RARE);
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRBiome.loneLands.getBiomeAchievement();
    }
    
    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRBiome.loneLands.getBiomeWaypoints();
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRBiome.loneLands.getBiomeMusic();
    }
    
    @Override
    public boolean hasSeasonalGrass() {
        return true;
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return 1.0f;
    }
    
    @Override
    public int spawnCountMultiplier() {
        return 1;
    }
}
