// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.LOTRAchievement;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.entity.npc.LOTREntityDorwinionMerchantElf;
import lotr.common.entity.npc.LOTREntityGaladhrimTrader;
import lotr.common.world.structure2.LOTRWorldGenWoodElvenForge;
import lotr.common.world.structure.LOTRWorldGenWoodElfTower;
import net.minecraft.world.gen.feature.WorldGenerator;
import lotr.common.world.structure2.LOTRWorldGenWoodElfHouse;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.entity.animal.LOTREntityButterfly;
import lotr.common.entity.animal.LOTREntityBear;
import lotr.common.entity.animal.LOTREntityDeer;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.entity.animal.LOTREntityElk;

public class LOTRBiomeGenWoodlandRealm extends LOTRBiome
{
    public LOTRBiomeGenWoodlandRealm(final int i, final boolean major) {
        super(i, major);
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityElk.class, 30, 4, 6));
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityDeer.class, 20, 4, 6));
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityBear.class, 2, 1, 4));
        super.spawnableCaveCreatureList.clear();
        super.spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityButterfly.class, 10, 4, 4));
        final LOTRBiomeSpawnList.FactionContainer factionList = super.npcSpawnList.newFactionList(100);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n = 0;
        final LOTRBiomeSpawnList npcSpawnList = super.npcSpawnList;
        lists[n] = LOTRBiomeSpawnList.entry(LOTRSpawnList.WOOD_ELVES, 10);
        final int n2 = 1;
        final LOTRBiomeSpawnList npcSpawnList2 = super.npcSpawnList;
        lists[n2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.WOOD_ELF_WARRIORS, 3);
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
        lists2[n5] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_URUKS, 1).setConquestThreshold(100.0f);
        factionList2.add(lists2);
        final LOTRBiomeSpawnList.FactionContainer factionList3 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists3 = new LOTRBiomeSpawnList.SpawnListContainer[3];
        final int n6 = 0;
        final LOTRBiomeSpawnList npcSpawnList6 = super.npcSpawnList;
        lists3[n6] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MIRKWOOD_SPIDERS, 10);
        final int n7 = 1;
        final LOTRBiomeSpawnList npcSpawnList7 = super.npcSpawnList;
        lists3[n7] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DOL_GULDUR_ORCS, 10);
        final int n8 = 2;
        final LOTRBiomeSpawnList npcSpawnList8 = super.npcSpawnList;
        lists3[n8] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MIRK_TROLLS, 1).setConquestThreshold(100.0f);
        factionList3.add(lists3);
        super.npcSpawnList.conquestGainRate = 0.2f;
        this.addBiomeVariant(LOTRBiomeVariant.FOREST);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_BEECH, 0.1f);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_BIRCH, 0.1f);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LARCH, 0.1f);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_PINE, 0.1f);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_ASPEN, 0.1f);
        super.variantChance = 0.3f;
        super.decorator.treesPerChunk = 1;
        super.decorator.willowPerChunk = 2;
        super.decorator.flowersPerChunk = 3;
        super.decorator.doubleFlowersPerChunk = 1;
        super.decorator.grassPerChunk = 4;
        super.decorator.doubleGrassPerChunk = 1;
        super.decorator.enableFern = true;
        super.decorator.generateLava = false;
        super.decorator.generateCobwebs = false;
        super.decorator.addTree(LOTRTreeType.GREEN_OAK, 500);
        super.decorator.addTree(LOTRTreeType.GREEN_OAK_LARGE, 50);
        super.decorator.addTree(LOTRTreeType.GREEN_OAK_EXTREME, 80);
        super.decorator.addTree(LOTRTreeType.RED_OAK, 40);
        super.decorator.addTree(LOTRTreeType.RED_OAK_LARGE, 20);
        super.decorator.addTree(LOTRTreeType.OAK, 50);
        super.decorator.addTree(LOTRTreeType.OAK_LARGE, 100);
        super.decorator.addTree(LOTRTreeType.SPRUCE, 100);
        super.decorator.addTree(LOTRTreeType.CHESTNUT, 50);
        super.decorator.addTree(LOTRTreeType.CHESTNUT_LARGE, 50);
        super.decorator.addTree(LOTRTreeType.BEECH, 50);
        super.decorator.addTree(LOTRTreeType.BEECH_LARGE, 100);
        super.decorator.addTree(LOTRTreeType.LARCH, 100);
        super.decorator.addTree(LOTRTreeType.FIR, 200);
        super.decorator.addTree(LOTRTreeType.PINE, 200);
        super.decorator.addTree(LOTRTreeType.ASPEN, 50);
        super.decorator.addTree(LOTRTreeType.ASPEN_LARGE, 10);
        this.registerForestFlowers();
        super.decorator.clearRandomStructures();
        super.decorator.addRandomStructure(new LOTRWorldGenWoodElfHouse(false), 16);
        super.decorator.addRandomStructure(new LOTRWorldGenWoodElfTower(false), 100);
        super.decorator.addRandomStructure(new LOTRWorldGenWoodElvenForge(false), 80);
        this.registerTravellingTrader(LOTREntityGaladhrimTrader.class);
        this.registerTravellingTrader(LOTREntityDorwinionMerchantElf.class);
        this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
        super.invasionSpawns.addInvasion(LOTRInvasions.DOL_GULDUR, LOTREventSpawner.EventChance.UNCOMMON);
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterWoodlandRealm;
    }
    
    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.WOODLAND_REALM;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.WOODLAND_REALM.getSubregion("woodlandRealm");
    }
    
    @Override
    public LOTRRoadType getRoadBlock() {
        return LOTRRoadType.WOOD_ELVEN;
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return 0.5f;
    }
    
    @Override
    public int spawnCountMultiplier() {
        return 3;
    }
}
