// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.LOTRAchievement;
import lotr.common.world.structure2.LOTRWorldGenSmallStoneRuin;
import lotr.common.world.structure2.LOTRWorldGenDolGuldurSpiderPit;
import lotr.common.world.structure2.LOTRWorldGenDolGuldurCamp;
import lotr.common.world.structure2.LOTRWorldGenDolGuldurTower;
import lotr.common.world.structure2.LOTRWorldGenDolGuldurAltar;
import lotr.common.world.structure2.LOTRWorldGenStoneRuin;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import lotr.common.LOTRMod;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.entity.animal.LOTREntityGorcrow;

public class LOTRBiomeGenDolGuldur extends LOTRBiomeGenMirkwoodCorrupted
{
    public LOTRBiomeGenDolGuldur(final int i, final boolean major) {
        super(i, major);
        super.spawnableCreatureList.clear();
        super.spawnableWaterCreatureList.clear();
        super.spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityGorcrow.class, 8, 4, 4));
        super.npcSpawnList.clear();
        final LOTRBiomeSpawnList.FactionContainer factionList = super.npcSpawnList.newFactionList(100);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists = new LOTRBiomeSpawnList.SpawnListContainer[3];
        final int n = 0;
        final LOTRBiomeSpawnList npcSpawnList = super.npcSpawnList;
        lists[n] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MIRKWOOD_SPIDERS, 20);
        final int n2 = 1;
        final LOTRBiomeSpawnList npcSpawnList2 = super.npcSpawnList;
        lists[n2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DOL_GULDUR_ORCS, 30);
        final int n3 = 2;
        final LOTRBiomeSpawnList npcSpawnList3 = super.npcSpawnList;
        lists[n3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MIRK_TROLLS, 5);
        factionList.add(lists);
        final LOTRBiomeSpawnList.FactionContainer factionList2 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists2 = new LOTRBiomeSpawnList.SpawnListContainer[4];
        final int n4 = 0;
        final LOTRBiomeSpawnList npcSpawnList4 = super.npcSpawnList;
        lists2[n4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.WOOD_ELF_WARRIORS, 10);
        final int n5 = 1;
        final LOTRBiomeSpawnList npcSpawnList5 = super.npcSpawnList;
        lists2[n5] = LOTRBiomeSpawnList.entry(LOTRSpawnList.WOOD_ELVES, 5).setConquestThreshold(50.0f);
        final int n6 = 2;
        final LOTRBiomeSpawnList npcSpawnList6 = super.npcSpawnList;
        lists2[n6] = LOTRBiomeSpawnList.entry(LOTRSpawnList.WOOD_ELVES, 5).setConquestThreshold(200.0f);
        final int n7 = 3;
        final LOTRBiomeSpawnList npcSpawnList7 = super.npcSpawnList;
        lists2[n7] = LOTRBiomeSpawnList.entry(LOTRSpawnList.WOOD_ELVES, 5).setConquestThreshold(400.0f);
        factionList2.add(lists2);
        final LOTRBiomeSpawnList.FactionContainer factionList3 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists3 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n8 = 0;
        final LOTRBiomeSpawnList npcSpawnList8 = super.npcSpawnList;
        lists3[n8] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GALADHRIM_WARRIORS, 10);
        final int n9 = 1;
        final LOTRBiomeSpawnList npcSpawnList9 = super.npcSpawnList;
        lists3[n9] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GALADHRIM_WARDENS, 3);
        factionList3.add(lists3);
        super.npcSpawnList.conquestGainRate = 0.2f;
        super.decorator.addOre((WorldGenerator)new WorldGenMinable(LOTRMod.oreMorgulIron, 8), 20.0f, 0, 64);
        super.decorator.addOre((WorldGenerator)new WorldGenMinable(LOTRMod.oreGulduril, 8), 8.0f, 0, 32);
        super.decorator.treesPerChunk = 1;
        super.decorator.vinesPerChunk = 2;
        super.decorator.flowersPerChunk = 0;
        super.decorator.grassPerChunk = 6;
        super.decorator.doubleGrassPerChunk = 1;
        super.decorator.clearTrees();
        super.decorator.addTree(LOTRTreeType.MIRK_OAK, 200);
        super.decorator.addTree(LOTRTreeType.MIRK_OAK_DEAD, 1000);
        super.biomeColors.setGrass(3032113);
        super.biomeColors.setFoliage(3032113);
        super.biomeColors.setSky(4343633);
        super.biomeColors.setClouds(2632757);
        super.biomeColors.setFoggy(true);
        super.decorator.clearRandomStructures();
        super.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.DOL_GULDUR(1, 4), 5);
        super.decorator.addRandomStructure(new LOTRWorldGenDolGuldurAltar(false), 160);
        super.decorator.addRandomStructure(new LOTRWorldGenDolGuldurTower(false), 80);
        super.decorator.addRandomStructure(new LOTRWorldGenDolGuldurCamp(false), 50);
        super.decorator.addRandomStructure(new LOTRWorldGenDolGuldurSpiderPit(false), 50);
        super.decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 400);
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterDolGuldur;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.MIRKWOOD.getSubregion("dolGuldur");
    }
    
    @Override
    public float getTreeIncreaseChance() {
        return 0.25f;
    }
    
    @Override
    public boolean canSpawnHostilesInDay() {
        return true;
    }
}
