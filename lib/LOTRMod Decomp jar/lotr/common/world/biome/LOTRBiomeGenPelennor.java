// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.map.LOTRWorldGenRammasEchor;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.LOTRAchievement;
import lotr.common.world.village.LOTRVillageGen;
import lotr.common.world.village.LOTRVillageGenGondor;
import lotr.common.world.structure2.LOTRWorldGenGondorStructure;
import lotr.common.world.spawning.LOTREventSpawner;
import net.minecraft.world.gen.feature.WorldGenerator;
import lotr.common.world.structure2.LOTRWorldGenGondorTurret;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.spawning.LOTRBiomeSpawnList;

public class LOTRBiomeGenPelennor extends LOTRBiomeGenGondor
{
    public LOTRBiomeGenPelennor(final int i, final boolean major) {
        super(i, major);
        super.npcSpawnList.clear();
        final LOTRBiomeSpawnList.FactionContainer factionList = super.npcSpawnList.newFactionList(100);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists = { null };
        final int n = 0;
        final LOTRBiomeSpawnList npcSpawnList = super.npcSpawnList;
        lists[n] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_SOLDIERS, 10);
        factionList.add(lists);
        final LOTRBiomeSpawnList.FactionContainer factionList2 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists2 = new LOTRBiomeSpawnList.SpawnListContainer[6];
        final int n2 = 0;
        final LOTRBiomeSpawnList npcSpawnList2 = super.npcSpawnList;
        lists2[n2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 20);
        final int n3 = 1;
        final LOTRBiomeSpawnList npcSpawnList3 = super.npcSpawnList;
        lists2[n3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_BOMBARDIERS, 1);
        final int n4 = 2;
        final LOTRBiomeSpawnList npcSpawnList4 = super.npcSpawnList;
        lists2[n4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_WARGS, 4).setConquestThreshold(50.0f);
        final int n5 = 3;
        final LOTRBiomeSpawnList npcSpawnList5 = super.npcSpawnList;
        lists2[n5] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 1).setConquestThreshold(50.0f);
        final int n6 = 4;
        final LOTRBiomeSpawnList npcSpawnList6 = super.npcSpawnList;
        lists2[n6] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 2).setConquestThreshold(100.0f);
        final int n7 = 5;
        final LOTRBiomeSpawnList npcSpawnList7 = super.npcSpawnList;
        lists2[n7] = LOTRBiomeSpawnList.entry(LOTRSpawnList.OLOG_HAI, 1).setConquestThreshold(200.0f);
        factionList2.add(lists2);
        final LOTRBiomeSpawnList.FactionContainer factionList3 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists3 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n8 = 0;
        final LOTRBiomeSpawnList npcSpawnList8 = super.npcSpawnList;
        lists3[n8] = LOTRBiomeSpawnList.entry(LOTRSpawnList.URUK_HAI, 10);
        final int n9 = 1;
        final LOTRBiomeSpawnList npcSpawnList9 = super.npcSpawnList;
        lists3[n9] = LOTRBiomeSpawnList.entry(LOTRSpawnList.URUK_WARGS, 2).setConquestThreshold(50.0f);
        factionList3.add(lists3);
        final LOTRBiomeSpawnList.FactionContainer factionList4 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists4 = new LOTRBiomeSpawnList.SpawnListContainer[4];
        final int n10 = 0;
        final LOTRBiomeSpawnList npcSpawnList10 = super.npcSpawnList;
        lists4[n10] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HARNEDHRIM, 2).setConquestThreshold(100.0f);
        final int n11 = 1;
        final LOTRBiomeSpawnList npcSpawnList11 = super.npcSpawnList;
        lists4[n11] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HARNEDOR_WARRIORS, 10);
        final int n12 = 2;
        final LOTRBiomeSpawnList npcSpawnList12 = super.npcSpawnList;
        lists4[n12] = LOTRBiomeSpawnList.entry(LOTRSpawnList.COAST_SOUTHRONS, 2).setConquestThreshold(100.0f);
        final int n13 = 3;
        final LOTRBiomeSpawnList npcSpawnList13 = super.npcSpawnList;
        lists4[n13] = LOTRBiomeSpawnList.entry(LOTRSpawnList.SOUTHRON_WARRIORS, 10);
        factionList4.add(lists4);
        final LOTRBiomeSpawnList.FactionContainer factionList5 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists5 = new LOTRBiomeSpawnList.SpawnListContainer[3];
        final int n14 = 0;
        final LOTRBiomeSpawnList npcSpawnList14 = super.npcSpawnList;
        lists5[n14] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLINGS, 2).setConquestThreshold(100.0f);
        final int n15 = 1;
        final LOTRBiomeSpawnList npcSpawnList15 = super.npcSpawnList;
        lists5[n15] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLING_WARRIORS, 10);
        final int n16 = 2;
        final LOTRBiomeSpawnList npcSpawnList16 = super.npcSpawnList;
        lists5[n16] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLING_GOLD_WARRIORS, 2).setConquestThreshold(50.0f);
        factionList5.add(lists5);
        final LOTRBiomeSpawnList.FactionContainer factionList6 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists6 = { null };
        final int n17 = 0;
        final LOTRBiomeSpawnList npcSpawnList17 = super.npcSpawnList;
        lists6[n17] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HALF_TROLLS, 10);
        factionList6.add(lists6);
        super.npcSpawnList.conquestGainRate = 0.2f;
        this.clearBiomeVariants();
        super.decorator.setTreeCluster(8, 32);
        super.decorator.flowersPerChunk = 6;
        super.decorator.grassPerChunk = 6;
        super.decorator.doubleGrassPerChunk = 1;
        this.registerPlainsFlowers();
        super.decorator.clearRandomStructures();
        super.decorator.addRandomStructure(new LOTRWorldGenGondorTurret(false), 500);
        this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
        super.invasionSpawns.clearInvasions();
    }
    
    @Override
    protected void addFiefdomStructures() {
        super.decorator.addVillage(new LOTRVillageGenGondor(this, LOTRWorldGenGondorStructure.GondorFiefdom.GONDOR, 1.0f));
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterPelennor;
    }
    
    public boolean hasDomesticAnimals() {
        return true;
    }
    
    @Override
    public LOTRRoadType getRoadBlock() {
        return LOTRRoadType.GONDOR;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.GONDOR.getSubregion("pelennor");
    }
    
    @Override
    public void decorate(final World world, final Random random, final int i, final int k) {
        super.decorate(world, random, i, k);
        LOTRWorldGenRammasEchor.INSTANCE.generateWithSetRotation(world, random, i, 0, k, 0);
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return 0.5f;
    }
    
    @Override
    public float getTreeIncreaseChance() {
        return 0.02f;
    }
    
    @Override
    public int spawnCountMultiplier() {
        return 2;
    }
}
