// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenDoublePlant;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.LOTRAchievement;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.entity.npc.LOTREntityScrapTrader;
import lotr.common.entity.npc.LOTREntityGaladhrimTrader;
import lotr.common.world.structure2.LOTRWorldGenSmallStoneRuin;
import lotr.common.world.structure2.LOTRWorldGenIthilienHideout;
import lotr.common.world.structure2.LOTRWorldGenGondorObelisk;
import lotr.common.world.structure.LOTRWorldGenRuinedGondorTower;
import lotr.common.world.structure.LOTRWorldGenGondorRuins;
import lotr.common.world.structure2.LOTRWorldGenRuinedBeaconTower;
import net.minecraft.block.Block;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.entity.animal.LOTREntityDeer;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.entity.animal.LOTREntityHorse;

public class LOTRBiomeGenIthilien extends LOTRBiome
{
    public LOTRBiomeGenIthilien(final int i, final boolean major) {
        super(i, major);
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityHorse.class, 5, 2, 6));
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityDeer.class, 10, 4, 6));
        final LOTRBiomeSpawnList.FactionContainer factionList = super.npcSpawnList.newFactionList(10);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists = { null };
        final int n = 0;
        final LOTRBiomeSpawnList npcSpawnList = super.npcSpawnList;
        lists[n] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RANGERS_ITHILIEN, 3).setSpawnChance(100);
        factionList.add(lists);
        final LOTRBiomeSpawnList.FactionContainer factionList2 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists2 = new LOTRBiomeSpawnList.SpawnListContainer[10];
        final int n2 = 0;
        final LOTRBiomeSpawnList npcSpawnList2 = super.npcSpawnList;
        lists2[n2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_SOLDIERS, 10);
        final int n3 = 1;
        final LOTRBiomeSpawnList npcSpawnList3 = super.npcSpawnList;
        lists2[n3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RANGERS_ITHILIEN, 10);
        final int n4 = 2;
        final LOTRBiomeSpawnList npcSpawnList4 = super.npcSpawnList;
        lists2[n4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.LOSSARNACH_SOLDIERS, 4);
        final int n5 = 3;
        final LOTRBiomeSpawnList npcSpawnList5 = super.npcSpawnList;
        lists2[n5] = LOTRBiomeSpawnList.entry(LOTRSpawnList.LEBENNIN_SOLDIERS, 4).setConquestThreshold(50.0f);
        final int n6 = 4;
        final LOTRBiomeSpawnList npcSpawnList6 = super.npcSpawnList;
        lists2[n6] = LOTRBiomeSpawnList.entry(LOTRSpawnList.LAMEDON_HILLMEN, 1).setConquestThreshold(50.0f);
        final int n7 = 5;
        final LOTRBiomeSpawnList npcSpawnList7 = super.npcSpawnList;
        lists2[n7] = LOTRBiomeSpawnList.entry(LOTRSpawnList.LAMEDON_SOLDIERS, 1).setConquestThreshold(50.0f);
        final int n8 = 6;
        final LOTRBiomeSpawnList npcSpawnList8 = super.npcSpawnList;
        lists2[n8] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACKROOT_SOLDIERS, 1).setConquestThreshold(50.0f);
        final int n9 = 7;
        final LOTRBiomeSpawnList npcSpawnList9 = super.npcSpawnList;
        lists2[n9] = LOTRBiomeSpawnList.entry(LOTRSpawnList.PINNATH_GELIN_SOLDIERS, 1).setConquestThreshold(50.0f);
        final int n10 = 8;
        final LOTRBiomeSpawnList npcSpawnList10 = super.npcSpawnList;
        lists2[n10] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DOL_AMROTH_SOLDIERS, 2).setConquestThreshold(100.0f);
        final int n11 = 9;
        final LOTRBiomeSpawnList npcSpawnList11 = super.npcSpawnList;
        lists2[n11] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_MEN, 10).setConquestThreshold(500.0f);
        factionList2.add(lists2);
        final LOTRBiomeSpawnList.FactionContainer factionList3 = super.npcSpawnList.newFactionList(90);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists3 = new LOTRBiomeSpawnList.SpawnListContainer[6];
        final int n12 = 0;
        final LOTRBiomeSpawnList npcSpawnList12 = super.npcSpawnList;
        lists3[n12] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 10);
        final int n13 = 1;
        final LOTRBiomeSpawnList npcSpawnList13 = super.npcSpawnList;
        lists3[n13] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_BOMBARDIERS, 1);
        final int n14 = 2;
        final LOTRBiomeSpawnList npcSpawnList14 = super.npcSpawnList;
        lists3[n14] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_WARGS, 2);
        final int n15 = 3;
        final LOTRBiomeSpawnList npcSpawnList15 = super.npcSpawnList;
        lists3[n15] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 2).setConquestThreshold(50.0f);
        final int n16 = 4;
        final LOTRBiomeSpawnList npcSpawnList16 = super.npcSpawnList;
        lists3[n16] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_SPIDERS, 1).setConquestThreshold(100.0f);
        final int n17 = 5;
        final LOTRBiomeSpawnList npcSpawnList17 = super.npcSpawnList;
        lists3[n17] = LOTRBiomeSpawnList.entry(LOTRSpawnList.OLOG_HAI, 1).setConquestThreshold(150.0f);
        factionList3.add(lists3);
        final LOTRBiomeSpawnList.FactionContainer factionList4 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists4 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n18 = 0;
        final LOTRBiomeSpawnList npcSpawnList18 = super.npcSpawnList;
        lists4[n18] = LOTRBiomeSpawnList.entry(LOTRSpawnList.URUK_HAI, 10);
        final int n19 = 1;
        final LOTRBiomeSpawnList npcSpawnList19 = super.npcSpawnList;
        lists4[n19] = LOTRBiomeSpawnList.entry(LOTRSpawnList.URUK_WARGS, 2).setConquestThreshold(50.0f);
        factionList4.add(lists4);
        final LOTRBiomeSpawnList.FactionContainer factionList5 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists5 = { null };
        final int n20 = 0;
        final LOTRBiomeSpawnList npcSpawnList20 = super.npcSpawnList;
        lists5[n20] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ROHIRRIM_WARRIORS, 10);
        factionList5.add(lists5);
        final LOTRBiomeSpawnList.FactionContainer factionList6 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists6 = new LOTRBiomeSpawnList.SpawnListContainer[4];
        final int n21 = 0;
        final LOTRBiomeSpawnList npcSpawnList21 = super.npcSpawnList;
        lists6[n21] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HARNEDHRIM, 2).setConquestThreshold(100.0f);
        final int n22 = 1;
        final LOTRBiomeSpawnList npcSpawnList22 = super.npcSpawnList;
        lists6[n22] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HARNEDOR_WARRIORS, 10);
        final int n23 = 2;
        final LOTRBiomeSpawnList npcSpawnList23 = super.npcSpawnList;
        lists6[n23] = LOTRBiomeSpawnList.entry(LOTRSpawnList.COAST_SOUTHRONS, 2).setConquestThreshold(100.0f);
        final int n24 = 3;
        final LOTRBiomeSpawnList npcSpawnList24 = super.npcSpawnList;
        lists6[n24] = LOTRBiomeSpawnList.entry(LOTRSpawnList.SOUTHRON_WARRIORS, 10);
        factionList6.add(lists6);
        final LOTRBiomeSpawnList.FactionContainer factionList7 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists7 = { null };
        final int n25 = 0;
        final LOTRBiomeSpawnList npcSpawnList25 = super.npcSpawnList;
        lists7[n25] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORWAITH_WARRIORS, 10);
        factionList7.add(lists7);
        final LOTRBiomeSpawnList.FactionContainer factionList8 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists8 = new LOTRBiomeSpawnList.SpawnListContainer[3];
        final int n26 = 0;
        final LOTRBiomeSpawnList npcSpawnList26 = super.npcSpawnList;
        lists8[n26] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLINGS, 2).setConquestThreshold(100.0f);
        final int n27 = 1;
        final LOTRBiomeSpawnList npcSpawnList27 = super.npcSpawnList;
        lists8[n27] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLING_WARRIORS, 10);
        final int n28 = 2;
        final LOTRBiomeSpawnList npcSpawnList28 = super.npcSpawnList;
        lists8[n28] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLING_GOLD_WARRIORS, 2).setConquestThreshold(50.0f);
        factionList8.add(lists8);
        final LOTRBiomeSpawnList.FactionContainer factionList9 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists9 = { null };
        final int n29 = 0;
        final LOTRBiomeSpawnList npcSpawnList29 = super.npcSpawnList;
        lists9[n29] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HALF_TROLLS, 10);
        factionList9.add(lists9);
        super.variantChance = 0.7f;
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_NORMAL_OAK_NOSTEPPE);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT, 4.0f);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST, 2.0f);
        this.addBiomeVariant(LOTRBiomeVariant.SHRUBLAND_OAK, 4.0f);
        this.addBiomeVariant(LOTRBiomeVariant.SCRUBLAND, 3.0f);
        this.addBiomeVariant(LOTRBiomeVariant.DENSEFOREST_LEBETHRON);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_BEECH, 0.5f);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_BIRCH, 0.5f);
        this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_APPLE_PEAR, 0.2f);
        this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_PLUM, 0.2f);
        this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_OLIVE, 0.1f);
        super.decorator.addSoil((WorldGenerator)new WorldGenMinable(LOTRMod.rock, 1, 60, Blocks.stone), 2.0f, 0, 64);
        super.decorator.treesPerChunk = 0;
        super.decorator.willowPerChunk = 2;
        super.decorator.logsPerChunk = 1;
        super.decorator.flowersPerChunk = 4;
        super.decorator.doubleFlowersPerChunk = 4;
        super.decorator.grassPerChunk = 10;
        super.decorator.doubleGrassPerChunk = 4;
        super.decorator.waterlilyPerChunk = 2;
        super.decorator.generateAthelas = true;
        super.decorator.addTree(LOTRTreeType.OAK, 500);
        super.decorator.addTree(LOTRTreeType.OAK_TALL, 100);
        super.decorator.addTree(LOTRTreeType.OAK_LARGE, 200);
        super.decorator.addTree(LOTRTreeType.LEBETHRON, 100);
        super.decorator.addTree(LOTRTreeType.LEBETHRON_LARGE, 50);
        super.decorator.addTree(LOTRTreeType.BIRCH, 150);
        super.decorator.addTree(LOTRTreeType.BIRCH_LARGE, 50);
        super.decorator.addTree(LOTRTreeType.CEDAR, 200);
        super.decorator.addTree(LOTRTreeType.CHESTNUT, 100);
        super.decorator.addTree(LOTRTreeType.CHESTNUT_LARGE, 50);
        super.decorator.addTree(LOTRTreeType.PINE, 100);
        super.decorator.addTree(LOTRTreeType.CYPRESS, 100);
        super.decorator.addTree(LOTRTreeType.CYPRESS_LARGE, 10);
        super.decorator.addTree(LOTRTreeType.APPLE, 5);
        super.decorator.addTree(LOTRTreeType.PEAR, 5);
        super.decorator.addTree(LOTRTreeType.OLIVE, 5);
        super.decorator.addTree(LOTRTreeType.OLIVE_LARGE, 5);
        super.decorator.addTree(LOTRTreeType.ALMOND, 15);
        this.registerForestFlowers();
        this.addFlower(LOTRMod.asphodel, 0, 10);
        this.addFlower((Block)Blocks.red_flower, 2, 5);
        super.decorator.generateOrcDungeon = true;
        super.decorator.addRandomStructure(new LOTRWorldGenRuinedBeaconTower(false), 200);
        super.decorator.addRandomStructure(new LOTRWorldGenGondorRuins(), 500);
        super.decorator.addRandomStructure(new LOTRWorldGenRuinedGondorTower(false), 500);
        super.decorator.addRandomStructure(new LOTRWorldGenGondorObelisk(false), 800);
        super.decorator.addRandomStructure(new LOTRWorldGenIthilienHideout(false), 50);
        super.decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 400);
        this.registerTravellingTrader(LOTREntityGaladhrimTrader.class);
        this.registerTravellingTrader(LOTREntityScrapTrader.class);
        this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_UNCOMMON);
        super.invasionSpawns.addInvasion(LOTRInvasions.GONDOR, LOTREventSpawner.EventChance.UNCOMMON);
        super.invasionSpawns.addInvasion(LOTRInvasions.GONDOR_ITHILIEN, LOTREventSpawner.EventChance.COMMON);
        super.invasionSpawns.addInvasion(LOTRInvasions.GONDOR_DOL_AMROTH, LOTREventSpawner.EventChance.RARE);
        super.invasionSpawns.addInvasion(LOTRInvasions.MORDOR, LOTREventSpawner.EventChance.COMMON);
        super.invasionSpawns.addInvasion(LOTRInvasions.MORDOR_BLACK_URUK, LOTREventSpawner.EventChance.RARE);
        super.invasionSpawns.addInvasion(LOTRInvasions.MORDOR_NAN_UNGOL, LOTREventSpawner.EventChance.RARE);
        super.invasionSpawns.addInvasion(LOTRInvasions.MORDOR_WARG, LOTREventSpawner.EventChance.UNCOMMON);
        super.invasionSpawns.addInvasion(LOTRInvasions.NEAR_HARAD_HARNEDOR, LOTREventSpawner.EventChance.UNCOMMON);
        super.invasionSpawns.addInvasion(LOTRInvasions.NEAR_HARAD_COAST, LOTREventSpawner.EventChance.RARE);
        super.invasionSpawns.addInvasion(LOTRInvasions.NEAR_HARAD_CORSAIR, LOTREventSpawner.EventChance.RARE);
        super.invasionSpawns.addInvasion(LOTRInvasions.RHUN, LOTREventSpawner.EventChance.UNCOMMON);
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterIthilien;
    }
    
    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.ITHILIEN;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.GONDOR.getSubregion("ithilien");
    }
    
    @Override
    public LOTRRoadType getRoadBlock() {
        return LOTRRoadType.GONDOR_RUINED.setRepair(0.7f);
    }
    
    @Override
    public void decorate(final World world, final Random random, final int i, final int k) {
        super.decorate(world, random, i, k);
        if (random.nextInt(3) == 0) {
            final int i2 = i + random.nextInt(16) + 8;
            final int j1 = random.nextInt(128);
            final int k2 = k + random.nextInt(16) + 8;
            final WorldGenDoublePlant doubleFlowerGen = new WorldGenDoublePlant();
            doubleFlowerGen.func_150548_a(0);
            doubleFlowerGen.generate(world, random, i2, j1, k2);
        }
        if (random.nextInt(24) == 0) {
            final int i2 = i + random.nextInt(16) + 8;
            final int j1 = random.nextInt(128);
            final int k2 = k + random.nextInt(16) + 8;
            new WorldGenFlowers(LOTRMod.pipeweedPlant).generate(world, random, i2, j1, k2);
        }
    }
    
    @Override
    public float getTreeIncreaseChance() {
        return 0.4f;
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return 0.5f;
    }
}
