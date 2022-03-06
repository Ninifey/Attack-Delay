// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.LOTRAchievement;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.entity.npc.LOTREntityDorwinionMerchantMan;
import lotr.common.entity.npc.LOTREntityDaleMerchant;
import lotr.common.entity.npc.LOTREntityScrapTrader;
import lotr.common.entity.npc.LOTREntityIronHillsMerchant;
import lotr.common.entity.npc.LOTREntityGaladhrimTrader;
import lotr.common.world.village.LOTRVillageGen;
import lotr.common.world.village.LOTRVillageGenRohan;
import lotr.common.world.structure2.LOTRWorldGenSmallStoneRuin;
import lotr.common.world.structure2.LOTRWorldGenRohanWatchtower;
import lotr.common.world.structure.LOTRWorldGenRohanBarrow;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.init.Blocks;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.entity.animal.LOTREntityBear;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import lotr.common.LOTRMod;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenRohan extends LOTRBiome
{
    private WorldGenerator boulderGen;
    
    public LOTRBiomeGenRohan(final int i, final boolean major) {
        super(i, major);
        this.boulderGen = new LOTRWorldGenBoulder(LOTRMod.rock, 2, 1, 4);
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityHorse.class, 30, 2, 6));
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityBear.class, 3, 1, 4));
        final LOTRBiomeSpawnList.FactionContainer factionList = super.npcSpawnList.newFactionList(100, 0.0f);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists = { null };
        final int n = 0;
        final LOTRBiomeSpawnList npcSpawnList = super.npcSpawnList;
        lists[n] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ROHIRRIM_WARRIORS, 10).setSpawnChance(100);
        factionList.add(lists);
        final LOTRBiomeSpawnList.FactionContainer factionList2 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists2 = { null };
        final int n2 = 0;
        final LOTRBiomeSpawnList npcSpawnList2 = super.npcSpawnList;
        lists2[n2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ROHIRRIM_WARRIORS, 10);
        factionList2.add(lists2);
        final LOTRBiomeSpawnList.FactionContainer factionList3 = super.npcSpawnList.newFactionList(5);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists3 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n3 = 0;
        final LOTRBiomeSpawnList npcSpawnList3 = super.npcSpawnList;
        lists3[n3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.URUK_HAI, 10);
        final int n4 = 1;
        final LOTRBiomeSpawnList npcSpawnList4 = super.npcSpawnList;
        lists3[n4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.URUK_WARGS, 4);
        factionList3.add(lists3);
        final LOTRBiomeSpawnList.FactionContainer factionList4 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists4 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n5 = 0;
        final LOTRBiomeSpawnList npcSpawnList5 = super.npcSpawnList;
        lists4[n5] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DUNLENDINGS, 10);
        final int n6 = 1;
        final LOTRBiomeSpawnList npcSpawnList6 = super.npcSpawnList;
        lists4[n6] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DUNLENDING_WARRIORS, 10);
        factionList4.add(lists4);
        final LOTRBiomeSpawnList.FactionContainer factionList5 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists5 = new LOTRBiomeSpawnList.SpawnListContainer[3];
        final int n7 = 0;
        final LOTRBiomeSpawnList npcSpawnList7 = super.npcSpawnList;
        lists5[n7] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DOL_GULDUR_ORCS, 10);
        final int n8 = 1;
        final LOTRBiomeSpawnList npcSpawnList8 = super.npcSpawnList;
        lists5[n8] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MIRKWOOD_SPIDERS, 2).setConquestThreshold(50.0f);
        final int n9 = 2;
        final LOTRBiomeSpawnList npcSpawnList9 = super.npcSpawnList;
        lists5[n9] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MIRK_TROLLS, 1).setConquestThreshold(200.0f);
        factionList5.add(lists5);
        final LOTRBiomeSpawnList.FactionContainer factionList6 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists6 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n10 = 0;
        final LOTRBiomeSpawnList npcSpawnList10 = super.npcSpawnList;
        lists6[n10] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ENTS, 5);
        final int n11 = 1;
        final LOTRBiomeSpawnList npcSpawnList11 = super.npcSpawnList;
        lists6[n11] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HUORNS, 20);
        factionList6.add(lists6);
        final LOTRBiomeSpawnList.FactionContainer factionList7 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists7 = { null };
        final int n12 = 0;
        final LOTRBiomeSpawnList npcSpawnList12 = super.npcSpawnList;
        lists7[n12] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_SOLDIERS, 10);
        factionList7.add(lists7);
        final LOTRBiomeSpawnList.FactionContainer factionList8 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists8 = new LOTRBiomeSpawnList.SpawnListContainer[4];
        final int n13 = 0;
        final LOTRBiomeSpawnList npcSpawnList13 = super.npcSpawnList;
        lists8[n13] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 10);
        final int n14 = 1;
        final LOTRBiomeSpawnList npcSpawnList14 = super.npcSpawnList;
        lists8[n14] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_WARGS, 2);
        final int n15 = 2;
        final LOTRBiomeSpawnList npcSpawnList15 = super.npcSpawnList;
        lists8[n15] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 2).setConquestThreshold(50.0f);
        final int n16 = 3;
        final LOTRBiomeSpawnList npcSpawnList16 = super.npcSpawnList;
        lists8[n16] = LOTRBiomeSpawnList.entry(LOTRSpawnList.OLOG_HAI, 1).setConquestThreshold(200.0f);
        factionList8.add(lists8);
        super.variantChance = 0.3f;
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_NORMAL_OAK);
        this.addBiomeVariant(LOTRBiomeVariant.BOULDERS_ROHAN);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_BEECH, 0.5f);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_BIRCH, 0.5f);
        this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_APPLE_PEAR, 0.5f);
        super.decorator.addSoil((WorldGenerator)new WorldGenMinable(LOTRMod.rock, 2, 60, Blocks.stone), 2.0f, 0, 64);
        super.decorator.setTreeCluster(12, 30);
        super.decorator.willowPerChunk = 1;
        super.decorator.flowersPerChunk = 4;
        super.decorator.grassPerChunk = 15;
        super.decorator.doubleGrassPerChunk = 5;
        super.decorator.addTree(LOTRTreeType.OAK, 400);
        super.decorator.addTree(LOTRTreeType.OAK_LARGE, 800);
        super.decorator.addTree(LOTRTreeType.BIRCH, 20);
        super.decorator.addTree(LOTRTreeType.BIRCH_LARGE, 10);
        super.decorator.addTree(LOTRTreeType.BEECH, 20);
        super.decorator.addTree(LOTRTreeType.BEECH_LARGE, 10);
        super.decorator.addTree(LOTRTreeType.PINE, 20);
        super.decorator.addTree(LOTRTreeType.APPLE, 2);
        super.decorator.addTree(LOTRTreeType.PEAR, 2);
        this.registerPlainsFlowers();
        this.addFlower(LOTRMod.simbelmyne, 0, 2);
        super.decorator.addRandomStructure(new LOTRWorldGenRohanBarrow(false), 600);
        super.decorator.addRandomStructure(new LOTRWorldGenRohanWatchtower(false), 600);
        super.decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 500);
        super.decorator.addVillage(new LOTRVillageGenRohan(this, 0.6f));
        this.registerTravellingTrader(LOTREntityGaladhrimTrader.class);
        this.registerTravellingTrader(LOTREntityIronHillsMerchant.class);
        this.registerTravellingTrader(LOTREntityScrapTrader.class);
        this.registerTravellingTrader(LOTREntityDaleMerchant.class);
        this.registerTravellingTrader(LOTREntityDorwinionMerchantMan.class);
        this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
        super.invasionSpawns.addInvasion(LOTRInvasions.DUNLAND, LOTREventSpawner.EventChance.UNCOMMON);
        super.invasionSpawns.addInvasion(LOTRInvasions.URUK_HAI, LOTREventSpawner.EventChance.UNCOMMON);
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterRohan;
    }
    
    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.ROHAN;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.ROHAN.getSubregion("rohan");
    }
    
    @Override
    public LOTRRoadType getRoadBlock() {
        return LOTRRoadType.ROHAN;
    }
    
    @Override
    public void decorate(final World world, final Random random, final int i, final int k) {
        super.decorate(world, random, i, k);
        if (random.nextInt(32) == 0) {
            for (int l = 0; l < 3; ++l) {
                final int i2 = i + random.nextInt(16) + 8;
                final int k2 = k + random.nextInt(16) + 8;
                this.boulderGen.generate(world, random, i2, world.getHeightValue(i2, k2), k2);
            }
        }
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return 0.5f;
    }
    
    @Override
    public float getTreeIncreaseChance() {
        return 0.05f;
    }
    
    @Override
    public boolean canSpawnHostilesInDay() {
        return false;
    }
    
    @Override
    public int spawnCountMultiplier() {
        return 3;
    }
}
