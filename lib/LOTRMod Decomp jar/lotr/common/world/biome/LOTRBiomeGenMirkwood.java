// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.LOTRAchievement;
import net.minecraft.world.gen.feature.WorldGenerator;
import lotr.common.world.structure.LOTRWorldGenRuinedWoodElfTower;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.entity.animal.LOTREntityButterfly;
import lotr.common.entity.animal.LOTREntityDeer;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.entity.animal.LOTREntityElk;

public abstract class LOTRBiomeGenMirkwood extends LOTRBiome
{
    public LOTRBiomeGenMirkwood(final int i, final boolean major) {
        super(i, major);
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityElk.class, 30, 4, 6));
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityDeer.class, 30, 4, 6));
        super.spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityButterfly.class, 10, 4, 4));
        final LOTRBiomeSpawnList.FactionContainer factionList = super.npcSpawnList.newFactionList(100);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists = new LOTRBiomeSpawnList.SpawnListContainer[3];
        final int n = 0;
        final LOTRBiomeSpawnList npcSpawnList = super.npcSpawnList;
        lists[n] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MIRKWOOD_SPIDERS, 10);
        final int n2 = 1;
        final LOTRBiomeSpawnList npcSpawnList2 = super.npcSpawnList;
        lists[n2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DOL_GULDUR_ORCS, 10).setConquestOnly();
        final int n3 = 2;
        final LOTRBiomeSpawnList npcSpawnList3 = super.npcSpawnList;
        lists[n3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MIRK_TROLLS, 2).setConquestThreshold(100.0f);
        factionList.add(lists);
        final LOTRBiomeSpawnList.FactionContainer factionList2 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists2 = new LOTRBiomeSpawnList.SpawnListContainer[3];
        final int n4 = 0;
        final LOTRBiomeSpawnList npcSpawnList4 = super.npcSpawnList;
        lists2[n4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_ORCS, 10);
        final int n5 = 1;
        final LOTRBiomeSpawnList npcSpawnList5 = super.npcSpawnList;
        lists2[n5] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_WARGS, 2);
        final int n6 = 2;
        final LOTRBiomeSpawnList npcSpawnList6 = super.npcSpawnList;
        lists2[n6] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_URUKS, 1).setConquestThreshold(100.0f);
        factionList2.add(lists2);
        final LOTRBiomeSpawnList.FactionContainer factionList3 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists3 = new LOTRBiomeSpawnList.SpawnListContainer[4];
        final int n7 = 0;
        final LOTRBiomeSpawnList npcSpawnList7 = super.npcSpawnList;
        lists3[n7] = LOTRBiomeSpawnList.entry(LOTRSpawnList.WOOD_ELF_WARRIORS, 10);
        final int n8 = 1;
        final LOTRBiomeSpawnList npcSpawnList8 = super.npcSpawnList;
        lists3[n8] = LOTRBiomeSpawnList.entry(LOTRSpawnList.WOOD_ELVES, 5).setConquestThreshold(50.0f);
        final int n9 = 2;
        final LOTRBiomeSpawnList npcSpawnList9 = super.npcSpawnList;
        lists3[n9] = LOTRBiomeSpawnList.entry(LOTRSpawnList.WOOD_ELVES, 5).setConquestThreshold(200.0f);
        final int n10 = 3;
        final LOTRBiomeSpawnList npcSpawnList10 = super.npcSpawnList;
        lists3[n10] = LOTRBiomeSpawnList.entry(LOTRSpawnList.WOOD_ELVES, 5).setConquestThreshold(400.0f);
        factionList3.add(lists3);
        final LOTRBiomeSpawnList.FactionContainer factionList4 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists4 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n11 = 0;
        final LOTRBiomeSpawnList npcSpawnList11 = super.npcSpawnList;
        lists4[n11] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GALADHRIM_WARRIORS, 10);
        final int n12 = 1;
        final LOTRBiomeSpawnList npcSpawnList12 = super.npcSpawnList;
        lists4[n12] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GALADHRIM_WARDENS, 5);
        factionList4.add(lists4);
        final LOTRBiomeSpawnList.FactionContainer factionList5 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists5 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n13 = 0;
        final LOTRBiomeSpawnList npcSpawnList13 = super.npcSpawnList;
        lists5[n13] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HUORNS, 20);
        final int n14 = 1;
        final LOTRBiomeSpawnList npcSpawnList14 = super.npcSpawnList;
        lists5[n14] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ENTS, 10).setConquestThreshold(50.0f);
        factionList5.add(lists5);
        this.registerForestFlowers();
        super.decorator.addRandomStructure(new LOTRWorldGenRuinedWoodElfTower(false), 500);
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterMirkwood;
    }
    
    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.MIRKWOOD;
    }
    
    @Override
    public LOTRRoadType getRoadBlock() {
        return LOTRRoadType.WOOD_ELVEN_RUINED.setRepair(0.6f);
    }
    
    @Override
    public LOTRRoadType.BridgeType getBridgeBlock() {
        return LOTRRoadType.BridgeType.MIRKWOOD;
    }
}
