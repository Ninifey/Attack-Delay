// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import net.minecraft.block.Block;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.LOTRAchievement;
import lotr.common.world.structure2.LOTRWorldGenSmallStoneRuin;
import lotr.common.world.structure.LOTRWorldGenOrcSlaverTower;
import lotr.common.world.structure.LOTRWorldGenNurnWheatFarm;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import net.minecraft.init.Blocks;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import lotr.common.LOTRMod;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenNurn extends LOTRBiomeGenMordor
{
    protected WorldGenerator nurnBoulderGen;
    
    public LOTRBiomeGenNurn(final int i, final boolean major) {
        super(i, major);
        this.nurnBoulderGen = new LOTRWorldGenBoulder(LOTRMod.rock, 0, 1, 3);
        super.topBlock = (Block)Blocks.grass;
        super.fillerBlock = Blocks.dirt;
        super.enableRain = true;
        super.npcSpawnList.clear();
        final LOTRBiomeSpawnList.FactionContainer factionList = super.npcSpawnList.newFactionList(100);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists = new LOTRBiomeSpawnList.SpawnListContainer[6];
        final int n = 0;
        final LOTRBiomeSpawnList npcSpawnList = super.npcSpawnList;
        lists[n] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 30);
        final int n2 = 1;
        final LOTRBiomeSpawnList npcSpawnList2 = super.npcSpawnList;
        lists[n2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_BOMBARDIERS, 5);
        final int n3 = 2;
        final LOTRBiomeSpawnList npcSpawnList3 = super.npcSpawnList;
        lists[n3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_WARGS, 5).setConquestThreshold(50.0f);
        final int n4 = 3;
        final LOTRBiomeSpawnList npcSpawnList4 = super.npcSpawnList;
        lists[n4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 2).setConquestThreshold(50.0f);
        final int n5 = 4;
        final LOTRBiomeSpawnList npcSpawnList5 = super.npcSpawnList;
        lists[n5] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 2).setConquestThreshold(100.0f);
        final int n6 = 5;
        final LOTRBiomeSpawnList npcSpawnList6 = super.npcSpawnList;
        lists[n6] = LOTRBiomeSpawnList.entry(LOTRSpawnList.OLOG_HAI, 2).setConquestThreshold(200.0f);
        factionList.add(lists);
        final LOTRBiomeSpawnList.FactionContainer factionList2 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists2 = { null };
        final int n7 = 0;
        final LOTRBiomeSpawnList npcSpawnList7 = super.npcSpawnList;
        lists2[n7] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ROHIRRIM_WARRIORS, 10);
        factionList2.add(lists2);
        final LOTRBiomeSpawnList.FactionContainer factionList3 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists3 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n8 = 0;
        final LOTRBiomeSpawnList npcSpawnList8 = super.npcSpawnList;
        lists3[n8] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_SOLDIERS, 10);
        final int n9 = 1;
        final LOTRBiomeSpawnList npcSpawnList9 = super.npcSpawnList;
        lists3[n9] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RANGERS_ITHILIEN, 3);
        factionList3.add(lists3);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
        this.addBiomeVariant(LOTRBiomeVariant.STEPPE);
        this.addBiomeVariant(LOTRBiomeVariant.STEPPE_BARREN);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS_FOREST);
        this.addBiomeVariant(LOTRBiomeVariant.DEADFOREST_OAK);
        super.decorator.setTreeCluster(6, 30);
        super.decorator.willowPerChunk = 1;
        super.decorator.flowersPerChunk = 0;
        super.decorator.doubleFlowersPerChunk = 0;
        super.decorator.grassPerChunk = 8;
        super.decorator.dryReedChance = 0.6f;
        super.decorator.generateWater = true;
        super.decorator.addTree(LOTRTreeType.OAK, 500);
        super.decorator.addTree(LOTRTreeType.OAK_LARGE, 100);
        super.decorator.addTree(LOTRTreeType.OAK_DESERT, 500);
        super.decorator.addTree(LOTRTreeType.CEDAR, 100);
        super.decorator.addTree(LOTRTreeType.OAK_DEAD, 200);
        super.decorator.addTree(LOTRTreeType.CHARRED, 200);
        super.decorator.clearRandomStructures();
        super.decorator.addRandomStructure(new LOTRWorldGenNurnWheatFarm(false), 40);
        super.decorator.addRandomStructure(new LOTRWorldGenOrcSlaverTower(false), 200);
        super.decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 400);
        super.biomeColors.setGrass(10066237);
        super.biomeColors.setFoliage(7042874);
        super.biomeColors.setSky(10526098);
        super.biomeColors.resetClouds();
        super.biomeColors.resetFog();
        super.biomeColors.setWater(8877157);
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterNurn;
    }
    
    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.NURN;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.MORDOR.getSubregion("nurn");
    }
    
    @Override
    public boolean isGorgoroth() {
        return false;
    }
    
    @Override
    public void decorate(final World world, final Random random, final int i, final int k) {
        super.decorate(world, random, i, k);
        if (random.nextInt(40) == 0) {
            for (int l = 0; l < 4; ++l) {
                final int i2 = i + random.nextInt(16) + 8;
                final int k2 = k + random.nextInt(16) + 8;
                this.nurnBoulderGen.generate(world, random, i2, world.getHeightValue(i2, k2), k2);
            }
        }
    }
    
    @Override
    public float getTreeIncreaseChance() {
        return 0.25f;
    }
    
    @Override
    public int spawnCountMultiplier() {
        return 3;
    }
}
