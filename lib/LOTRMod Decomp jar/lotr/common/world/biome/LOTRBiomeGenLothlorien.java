// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import net.minecraft.block.Block;
import lotr.common.world.structure2.LOTRWorldGenElfHouse;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.LOTRAchievement;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.entity.npc.LOTREntityRivendellTrader;
import lotr.common.world.structure2.LOTRWorldGenGaladhrimForge;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import lotr.common.LOTRMod;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.entity.animal.LOTREntitySwan;
import lotr.common.entity.animal.LOTREntityRabbit;
import lotr.common.entity.animal.LOTREntityBird;
import lotr.common.entity.animal.LOTREntityButterfly;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.entity.animal.LOTREntityDeer;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.entity.animal.LOTREntityHorse;

public class LOTRBiomeGenLothlorien extends LOTRBiome
{
    public LOTRBiomeGenLothlorien(final int i, final boolean major) {
        super(i, major);
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityHorse.class, 20, 4, 6));
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityDeer.class, 30, 4, 6));
        super.spawnableWaterCreatureList.clear();
        super.spawnableCaveCreatureList.clear();
        final LOTRBiomeSpawnList.FactionContainer factionList = super.npcSpawnList.newFactionList(100);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists = new LOTRBiomeSpawnList.SpawnListContainer[3];
        final int n = 0;
        final LOTRBiomeSpawnList npcSpawnList = super.npcSpawnList;
        lists[n] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GALADHRIM, 10).setSpawnChance(50);
        final int n2 = 1;
        final LOTRBiomeSpawnList npcSpawnList2 = super.npcSpawnList;
        lists[n2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GALADHRIM_WARRIORS, 2).setSpawnChance(50);
        final int n3 = 2;
        final LOTRBiomeSpawnList npcSpawnList3 = super.npcSpawnList;
        lists[n3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GALADHRIM_WARDENS, 1).setSpawnChance(50);
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
        lists2[n6] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_URUKS, 2).setConquestThreshold(50.0f);
        factionList2.add(lists2);
        final LOTRBiomeSpawnList.FactionContainer factionList3 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists3 = new LOTRBiomeSpawnList.SpawnListContainer[3];
        final int n7 = 0;
        final LOTRBiomeSpawnList npcSpawnList7 = super.npcSpawnList;
        lists3[n7] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DOL_GULDUR_ORCS, 10);
        final int n8 = 1;
        final LOTRBiomeSpawnList npcSpawnList8 = super.npcSpawnList;
        lists3[n8] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MIRKWOOD_SPIDERS, 2);
        final int n9 = 2;
        final LOTRBiomeSpawnList npcSpawnList9 = super.npcSpawnList;
        lists3[n9] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MIRK_TROLLS, 1).setConquestThreshold(100.0f);
        factionList3.add(lists3);
        final LOTRBiomeSpawnList.FactionContainer factionList4 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists4 = new LOTRBiomeSpawnList.SpawnListContainer[4];
        final int n10 = 0;
        final LOTRBiomeSpawnList npcSpawnList10 = super.npcSpawnList;
        lists4[n10] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 10);
        final int n11 = 1;
        final LOTRBiomeSpawnList npcSpawnList11 = super.npcSpawnList;
        lists4[n11] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_WARGS, 2);
        final int n12 = 2;
        final LOTRBiomeSpawnList npcSpawnList12 = super.npcSpawnList;
        lists4[n12] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 1).setConquestThreshold(50.0f);
        final int n13 = 3;
        final LOTRBiomeSpawnList npcSpawnList13 = super.npcSpawnList;
        lists4[n13] = LOTRBiomeSpawnList.entry(LOTRSpawnList.OLOG_HAI, 1).setConquestThreshold(100.0f);
        factionList4.add(lists4);
        super.npcSpawnList.conquestGainRate = 0.2f;
        super.spawnableLOTRAmbientList.clear();
        super.spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityButterfly.class, 10, 4, 4));
        super.spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityBird.class, 10, 4, 4));
        super.spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityRabbit.class, 6, 4, 4));
        super.spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntitySwan.class, 15, 4, 8));
        super.variantChance = 0.7f;
        this.addBiomeVariant(LOTRBiomeVariant.FLOWERS);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT, 2.0f);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS_FOREST);
        this.addBiomeVariant(LOTRBiomeVariant.CLEARING, 0.5f);
        super.decorator.addOre((WorldGenerator)new WorldGenMinable(LOTRMod.oreQuendite, 6), 6.0f, 0, 48);
        super.enablePodzol = false;
        super.decorator.treesPerChunk = 3;
        super.decorator.willowPerChunk = 2;
        super.decorator.flowersPerChunk = 6;
        super.decorator.grassPerChunk = 8;
        super.decorator.doubleGrassPerChunk = 2;
        super.decorator.generateLava = false;
        super.decorator.generateCobwebs = false;
        super.decorator.whiteSand = true;
        super.decorator.addTree(LOTRTreeType.OAK, 300);
        super.decorator.addTree(LOTRTreeType.OAK_LARGE, 50);
        super.decorator.addTree(LOTRTreeType.LARCH, 200);
        super.decorator.addTree(LOTRTreeType.BEECH, 100);
        super.decorator.addTree(LOTRTreeType.BEECH_LARGE, 20);
        super.decorator.addTree(LOTRTreeType.MALLORN, 300);
        super.decorator.addTree(LOTRTreeType.MALLORN_BOUGHS, 600);
        super.decorator.addTree(LOTRTreeType.MALLORN_PARTY, 100);
        super.decorator.addTree(LOTRTreeType.MALLORN_EXTREME, 30);
        super.decorator.addTree(LOTRTreeType.ASPEN, 100);
        super.decorator.addTree(LOTRTreeType.ASPEN_LARGE, 20);
        super.decorator.addTree(LOTRTreeType.LAIRELOSSE, 50);
        this.registerForestFlowers();
        this.addFlower(LOTRMod.elanor, 0, 30);
        this.addFlower(LOTRMod.niphredil, 0, 20);
        super.biomeColors.setGrass(11527451);
        super.biomeColors.setFog(16770660);
        super.decorator.addRandomStructure(new LOTRWorldGenGaladhrimForge(false), 120);
        this.registerTravellingTrader(LOTREntityRivendellTrader.class);
        this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterLothlorien;
    }
    
    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.LOTHLORIEN;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.LOTHLORIEN.getSubregion("lothlorien");
    }
    
    @Override
    public LOTRRoadType getRoadBlock() {
        return LOTRRoadType.GALADHRIM;
    }
    
    @Override
    public boolean hasSeasonalGrass() {
        return false;
    }
    
    @Override
    public void decorate(final World world, final Random random, final int i, final int k) {
        super.decorate(world, random, i, k);
        for (int l = 0; l < 120; ++l) {
            final int i2 = i + random.nextInt(16) + 8;
            final int j1 = 60 + random.nextInt(50);
            final int k2 = k + random.nextInt(16) + 8;
            if (world.isAirBlock(i2, j1 - 1, k2) && world.isAirBlock(i2, j1, k2)) {
                if (world.isAirBlock(i2, j1 + 1, k2)) {
                    final Block torchBlock = LOTRWorldGenElfHouse.getRandomTorch(random);
                    if (world.getBlock(i2 - 1, j1, k2) == LOTRMod.wood && world.getBlockMetadata(i2 - 1, j1, k2) == 1 && world.isAirBlock(i2 - 1, j1, k2 - 1) && world.isAirBlock(i2 - 1, j1, k2 + 1)) {
                        world.setBlock(i2, j1, k2, torchBlock, 1, 2);
                    }
                    else if (world.getBlock(i2 + 1, j1, k2) == LOTRMod.wood && world.getBlockMetadata(i2 + 1, j1, k2) == 1 && world.isAirBlock(i2 + 1, j1, k2 - 1) && world.isAirBlock(i2 + 1, j1, k2 + 1)) {
                        world.setBlock(i2, j1, k2, torchBlock, 2, 2);
                    }
                    else if (world.getBlock(i2, j1, k2 - 1) == LOTRMod.wood && world.getBlockMetadata(i2, j1, k2 - 1) == 1 && world.isAirBlock(i2 - 1, j1, k2 - 1) && world.isAirBlock(i2 + 1, j1, k2 - 1)) {
                        world.setBlock(i2, j1, k2, torchBlock, 3, 2);
                    }
                    else if (world.getBlock(i2, j1, k2 + 1) == LOTRMod.wood && world.getBlockMetadata(i2, j1, k2 + 1) == 1 && world.isAirBlock(i2 - 1, j1, k2 + 1) && world.isAirBlock(i2 + 1, j1, k2 + 1)) {
                        world.setBlock(i2, j1, k2, torchBlock, 4, 2);
                    }
                }
            }
        }
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return 0.5f;
    }
}
