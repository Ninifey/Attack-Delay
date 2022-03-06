// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.structure2.LOTRWorldGenBlueMountainsHouse;
import net.minecraft.block.Block;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.LOTRAchievement;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.entity.npc.LOTREntityDaleMerchant;
import lotr.common.entity.npc.LOTREntityScrapTrader;
import lotr.common.entity.npc.LOTREntityIronHillsMerchant;
import lotr.common.entity.npc.LOTREntityRivendellTrader;
import lotr.common.world.structure2.LOTRWorldGenBlueMountainsSmithy;
import lotr.common.world.structure.LOTRWorldGenBlueMountainsStronghold;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.spawning.LOTRBiomeSpawnList;

public class LOTRBiomeGenBlueMountains extends LOTRBiome
{
    public LOTRBiomeGenBlueMountains(final int i, final boolean major) {
        super(i, major);
        final LOTRBiomeSpawnList.FactionContainer factionList = super.npcSpawnList.newFactionList(600);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists = { null };
        final int n = 0;
        final LOTRBiomeSpawnList npcSpawnList = super.npcSpawnList;
        lists[n] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLUE_DWARVES, 10);
        factionList.add(lists);
        final LOTRBiomeSpawnList.FactionContainer factionList2 = super.npcSpawnList.newFactionList(1, 2.0f);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists2 = new LOTRBiomeSpawnList.SpawnListContainer[3];
        final int n2 = 0;
        final LOTRBiomeSpawnList npcSpawnList2 = super.npcSpawnList;
        lists2[n2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_ORCS, 4);
        final int n3 = 1;
        final LOTRBiomeSpawnList npcSpawnList3 = super.npcSpawnList;
        lists2[n3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_WARGS, 1);
        final int n4 = 2;
        final LOTRBiomeSpawnList npcSpawnList4 = super.npcSpawnList;
        lists2[n4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_URUKS, 1).setConquestThreshold(50.0f);
        factionList2.add(lists2);
        final LOTRBiomeSpawnList.FactionContainer factionList3 = super.npcSpawnList.newFactionList(0, 2.0f);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists3 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n5 = 0;
        final LOTRBiomeSpawnList npcSpawnList5 = super.npcSpawnList;
        lists3[n5] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_ORCS, 4);
        final int n6 = 1;
        final LOTRBiomeSpawnList npcSpawnList6 = super.npcSpawnList;
        lists3[n6] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_WARGS, 1);
        factionList3.add(lists3);
        super.variantChance = 0.2f;
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_MOUNTAINS);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_BEECH, 0.2f);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_BIRCH, 0.2f);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LARCH, 0.2f);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_PINE, 0.2f);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_ASPEN, 0.2f);
        super.decorator.biomeGemFactor = 1.0f;
        super.decorator.addSoil((WorldGenerator)new WorldGenMinable(LOTRMod.rock, 3, 60, Blocks.stone), 6.0f, 0, 96);
        super.decorator.addOre((WorldGenerator)new WorldGenMinable(Blocks.coal_ore, 8), 10.0f, 0, 128);
        super.decorator.addOre((WorldGenerator)new WorldGenMinable(Blocks.iron_ore, 4), 10.0f, 0, 96);
        super.decorator.addOre((WorldGenerator)new WorldGenMinable(LOTRMod.oreGlowstone, 4), 8.0f, 0, 48);
        super.decorator.treesPerChunk = 1;
        super.decorator.willowPerChunk = 1;
        super.decorator.flowersPerChunk = 1;
        super.decorator.grassPerChunk = 6;
        super.decorator.doubleGrassPerChunk = 1;
        super.decorator.generateWater = false;
        super.decorator.generateLava = false;
        super.decorator.generateCobwebs = false;
        super.decorator.addTree(LOTRTreeType.OAK, 300);
        super.decorator.addTree(LOTRTreeType.OAK_LARGE, 100);
        super.decorator.addTree(LOTRTreeType.SPRUCE, 500);
        super.decorator.addTree(LOTRTreeType.BIRCH, 400);
        super.decorator.addTree(LOTRTreeType.FIR, 500);
        super.decorator.addTree(LOTRTreeType.PINE, 500);
        this.registerMountainsFlowers();
        this.addFlower(LOTRMod.dwarfHerb, 0, 1);
        super.biomeColors.setSky(7506425);
        super.decorator.addRandomStructure(new LOTRWorldGenBlueMountainsStronghold(false), 400);
        super.decorator.addRandomStructure(new LOTRWorldGenBlueMountainsSmithy(false), 150);
        this.registerTravellingTrader(LOTREntityRivendellTrader.class);
        this.registerTravellingTrader(LOTREntityIronHillsMerchant.class);
        this.registerTravellingTrader(LOTREntityScrapTrader.class);
        this.registerTravellingTrader(LOTREntityDaleMerchant.class);
        this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
        super.invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD, LOTREventSpawner.EventChance.RARE);
        super.invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD_WARG, LOTREventSpawner.EventChance.UNCOMMON);
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterBlueMountains;
    }
    
    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.BLUE_MOUNTAINS;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.DWARVEN.getSubregion("blueMountains");
    }
    
    @Override
    public boolean getEnableRiver() {
        return false;
    }
    
    @Override
    public LOTRRoadType getRoadBlock() {
        return LOTRRoadType.DWARVEN;
    }
    
    @Override
    protected void generateMountainTerrain(final World world, final Random random, final Block[] blocks, final byte[] meta, final int i, final int k, final int xzIndex, final int ySize, final int height, final int rockDepth, final LOTRBiomeVariant variant) {
        final int snowHeight = 110 - rockDepth;
        for (int stoneHeight = snowHeight - 20, j = ySize - 1; j >= stoneHeight; --j) {
            final int index = xzIndex * ySize + j;
            final Block block = blocks[index];
            if (j >= snowHeight && block == super.topBlock) {
                blocks[index] = Blocks.snow;
                meta[index] = 0;
            }
            else if (block == super.topBlock || block == super.fillerBlock) {
                blocks[index] = LOTRMod.rock;
                meta[index] = 3;
            }
        }
    }
    
    @Override
    public void decorate(final World world, final Random random, final int i, final int k) {
        super.decorate(world, random, i, k);
        for (int l = 0; l < 4; ++l) {
            final int i2 = i + random.nextInt(16) + 8;
            final int j1 = 70 + random.nextInt(80);
            final int k2 = k + random.nextInt(16) + 8;
            new LOTRWorldGenBlueMountainsHouse(false).generate(world, random, i2, j1, k2);
        }
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return 0.2f;
    }
}
