// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.structure2.LOTRWorldGenDwarfHouse;
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
import lotr.common.entity.npc.LOTREntityBlueDwarfMerchant;
import lotr.common.world.structure2.LOTRWorldGenDwarfSmithy;
import lotr.common.world.structure2.LOTRWorldGenDwarvenTower;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.LOTRMod;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.init.Blocks;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.entity.animal.LOTREntityBear;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.entity.animal.LOTREntityWildBoar;

public class LOTRBiomeGenIronHills extends LOTRBiome
{
    public LOTRBiomeGenIronHills(final int i, final boolean major) {
        super(i, major);
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityWildBoar.class, 50, 4, 4));
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityBear.class, 8, 1, 4));
        final LOTRBiomeSpawnList.FactionContainer factionList = super.npcSpawnList.newFactionList(500);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists = { null };
        final int n = 0;
        final LOTRBiomeSpawnList npcSpawnList = super.npcSpawnList;
        lists[n] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DWARVES, 10);
        factionList.add(lists);
        final LOTRBiomeSpawnList.FactionContainer factionList2 = super.npcSpawnList.newFactionList(0, 1.0f);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists2 = new LOTRBiomeSpawnList.SpawnListContainer[4];
        final int n2 = 0;
        final LOTRBiomeSpawnList npcSpawnList2 = super.npcSpawnList;
        lists2[n2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_ORCS, 10);
        final int n3 = 1;
        final LOTRBiomeSpawnList npcSpawnList3 = super.npcSpawnList;
        lists2[n3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_WARGS, 2).setConquestOnly();
        final int n4 = 2;
        final LOTRBiomeSpawnList npcSpawnList4 = super.npcSpawnList;
        lists2[n4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_URUKS, 1).setConquestThreshold(50.0f);
        final int n5 = 3;
        final LOTRBiomeSpawnList npcSpawnList5 = super.npcSpawnList;
        lists2[n5] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_URUKS, 1).setConquestThreshold(100.0f);
        factionList2.add(lists2);
        final LOTRBiomeSpawnList.FactionContainer factionList3 = super.npcSpawnList.newFactionList(0, 1.0f);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists3 = new LOTRBiomeSpawnList.SpawnListContainer[4];
        final int n6 = 0;
        final LOTRBiomeSpawnList npcSpawnList6 = super.npcSpawnList;
        lists3[n6] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 10);
        final int n7 = 1;
        final LOTRBiomeSpawnList npcSpawnList7 = super.npcSpawnList;
        lists3[n7] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_WARGS, 2);
        final int n8 = 2;
        final LOTRBiomeSpawnList npcSpawnList8 = super.npcSpawnList;
        lists3[n8] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 2).setConquestThreshold(50.0f);
        final int n9 = 3;
        final LOTRBiomeSpawnList npcSpawnList9 = super.npcSpawnList;
        lists3[n9] = LOTRBiomeSpawnList.entry(LOTRSpawnList.OLOG_HAI, 1).setConquestThreshold(200.0f);
        factionList3.add(lists3);
        final LOTRBiomeSpawnList.FactionContainer factionList4 = super.npcSpawnList.newFactionList(0, 1.0f);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists4 = new LOTRBiomeSpawnList.SpawnListContainer[3];
        final int n10 = 0;
        final LOTRBiomeSpawnList npcSpawnList10 = super.npcSpawnList;
        lists4[n10] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLING_WARRIORS, 10);
        final int n11 = 1;
        final LOTRBiomeSpawnList npcSpawnList11 = super.npcSpawnList;
        lists4[n11] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLING_GOLD_WARRIORS, 1);
        final int n12 = 2;
        final LOTRBiomeSpawnList npcSpawnList12 = super.npcSpawnList;
        lists4[n12] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLING_GOLD_WARRIORS, 2).setConquestThreshold(50.0f);
        factionList4.add(lists4);
        super.variantChance = 0.3f;
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_MOUNTAINS);
        super.decorator.biomeGemFactor = 0.75f;
        super.decorator.addOre((WorldGenerator)new WorldGenMinable(Blocks.iron_ore, 4), 20.0f, 0, 96);
        super.decorator.addOre((WorldGenerator)new WorldGenMinable(Blocks.gold_ore, 4), 2.0f, 0, 48);
        super.decorator.addOre((WorldGenerator)new WorldGenMinable(LOTRMod.oreSilver, 4), 2.0f, 0, 48);
        super.decorator.addOre((WorldGenerator)new WorldGenMinable(LOTRMod.oreGlowstone, 4), 8.0f, 0, 48);
        super.decorator.treesPerChunk = 0;
        super.decorator.flowersPerChunk = 1;
        super.decorator.grassPerChunk = 8;
        super.decorator.doubleGrassPerChunk = 1;
        super.decorator.generateWater = false;
        super.decorator.generateLava = false;
        super.decorator.generateCobwebs = false;
        super.decorator.addTree(LOTRTreeType.SPRUCE, 500);
        super.decorator.addTree(LOTRTreeType.SPRUCE_MEGA, 200);
        super.decorator.addTree(LOTRTreeType.SPRUCE_MEGA_THIN, 50);
        super.decorator.addTree(LOTRTreeType.FIR, 400);
        super.decorator.addTree(LOTRTreeType.PINE, 400);
        this.registerMountainsFlowers();
        this.addFlower(LOTRMod.dwarfHerb, 0, 1);
        super.decorator.addRandomStructure(new LOTRWorldGenDwarvenTower(false), 300);
        super.decorator.addRandomStructure(new LOTRWorldGenDwarfSmithy(false), 150);
        this.registerTravellingTrader(LOTREntityBlueDwarfMerchant.class);
        this.registerTravellingTrader(LOTREntityScrapTrader.class);
        this.registerTravellingTrader(LOTREntityDaleMerchant.class);
        this.registerTravellingTrader(LOTREntityDorwinionMerchantMan.class);
        this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
        super.invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD, LOTREventSpawner.EventChance.RARE);
        super.invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD_WARG, LOTREventSpawner.EventChance.UNCOMMON);
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterIronHills;
    }
    
    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.IRON_HILLS;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.DWARVEN.getSubregion("ironHills");
    }
    
    @Override
    public LOTRRoadType getRoadBlock() {
        return LOTRRoadType.DWARVEN;
    }
    
    @Override
    public void decorate(final World world, final Random random, final int i, final int k) {
        super.decorate(world, random, i, k);
        for (int l = 0; l < 4; ++l) {
            final int i2 = i + random.nextInt(16) + 8;
            final int j1 = 70 + random.nextInt(60);
            final int k2 = k + random.nextInt(16) + 8;
            new LOTRWorldGenDwarfHouse(false).generate(world, random, i2, j1, k2);
        }
        for (int l = 0; l < 8; ++l) {
            final int i2 = i + random.nextInt(16) + 8;
            final int k3 = k + random.nextInt(16) + 8;
            final int j2 = world.getHeightValue(i2, k3);
            if (j2 > 80) {
                super.decorator.genTree(world, random, i2, j2, k3);
            }
        }
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return 0.25f;
    }
    
    @Override
    public float getTreeIncreaseChance() {
        return 0.1f;
    }
}
