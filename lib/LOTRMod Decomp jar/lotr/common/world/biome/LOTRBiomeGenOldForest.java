// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.map.LOTRWaypoint;
import lotr.common.LOTRAchievement;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.spawning.LOTRBiomeSpawnList;

public class LOTRBiomeGenOldForest extends LOTRBiome
{
    public LOTRBiomeGenOldForest(final int i, final boolean major) {
        super(i, major);
        final LOTRBiomeSpawnList.FactionContainer factionList = super.npcSpawnList.newFactionList(100);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists = { null };
        final int n = 0;
        final LOTRBiomeSpawnList npcSpawnList = super.npcSpawnList;
        lists[n] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DARK_HUORNS, 10);
        factionList.add(lists);
        final LOTRBiomeSpawnList.FactionContainer factionList2 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists2 = { null };
        final int n2 = 0;
        final LOTRBiomeSpawnList npcSpawnList2 = super.npcSpawnList;
        lists2[n2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HOBBITS, 10);
        factionList2.add(lists2);
        final LOTRBiomeSpawnList.FactionContainer factionList3 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists3 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n3 = 0;
        final LOTRBiomeSpawnList npcSpawnList3 = super.npcSpawnList;
        lists3[n3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_ORCS, 10);
        final int n4 = 1;
        final LOTRBiomeSpawnList npcSpawnList4 = super.npcSpawnList;
        lists3[n4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_WARGS, 2);
        factionList3.add(lists3);
        final LOTRBiomeSpawnList.FactionContainer factionList4 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists4 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n5 = 0;
        final LOTRBiomeSpawnList npcSpawnList5 = super.npcSpawnList;
        lists4[n5] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_ORCS, 10);
        final int n6 = 1;
        final LOTRBiomeSpawnList npcSpawnList6 = super.npcSpawnList;
        lists4[n6] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_WARGS, 2);
        factionList4.add(lists4);
        final LOTRBiomeSpawnList.FactionContainer factionList5 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists5 = new LOTRBiomeSpawnList.SpawnListContainer[3];
        final int n7 = 0;
        final LOTRBiomeSpawnList npcSpawnList7 = super.npcSpawnList;
        lists5[n7] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ISENGARD_SNAGA, 5);
        final int n8 = 1;
        final LOTRBiomeSpawnList npcSpawnList8 = super.npcSpawnList;
        lists5[n8] = LOTRBiomeSpawnList.entry(LOTRSpawnList.URUK_HAI, 10);
        final int n9 = 2;
        final LOTRBiomeSpawnList npcSpawnList9 = super.npcSpawnList;
        lists5[n9] = LOTRBiomeSpawnList.entry(LOTRSpawnList.URUK_WARGS, 2).setConquestThreshold(50.0f);
        factionList5.add(lists5);
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_FOREST);
        super.decorator.treesPerChunk = 16;
        super.decorator.willowPerChunk = 2;
        super.decorator.flowersPerChunk = 1;
        super.decorator.grassPerChunk = 12;
        super.decorator.doubleGrassPerChunk = 5;
        super.decorator.enableFern = true;
        super.decorator.mushroomsPerChunk = 2;
        super.decorator.addTree(LOTRTreeType.OAK, 500);
        super.decorator.addTree(LOTRTreeType.OAK_TALL, 1000);
        super.decorator.addTree(LOTRTreeType.OAK_TALLER, 200);
        super.decorator.addTree(LOTRTreeType.OAK_LARGE, 500);
        super.decorator.addTree(LOTRTreeType.DARK_OAK, 1000);
        super.decorator.addTree(LOTRTreeType.FIR, 500);
        super.decorator.addTree(LOTRTreeType.PINE, 500);
        this.registerForestFlowers();
        super.biomeColors.setGrass(4686398);
        super.biomeColors.setFoliage(3172394);
        super.biomeColors.setFog(1651225);
        super.biomeColors.setFoggy(true);
        this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterOldForest;
    }
    
    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.OLD_FOREST;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.OLD_FOREST.getSubregion("oldForest");
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return 0.0f;
    }
}
