// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import net.minecraft.init.Blocks;
import net.minecraft.block.Block;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.LOTRAchievement;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.structure2.LOTRWorldGenSmallStoneRuin;
import lotr.common.world.structure2.LOTRWorldGenRuinedDwarvenTower;
import lotr.common.world.structure2.LOTRWorldGenStoneRuin;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import lotr.common.LOTRMod;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.spawning.LOTRBiomeSpawnList;

public class LOTRBiomeGenMistyMountains extends LOTRBiome
{
    public LOTRBiomeGenMistyMountains(final int i, final boolean major) {
        super(i, major);
        super.spawnableCreatureList.clear();
        super.spawnableLOTRAmbientList.clear();
        final LOTRBiomeSpawnList.FactionContainer factionList = super.npcSpawnList.newFactionList(100);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists = new LOTRBiomeSpawnList.SpawnListContainer[3];
        final int n = 0;
        final LOTRBiomeSpawnList npcSpawnList = super.npcSpawnList;
        lists[n] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_ORCS, 30);
        final int n2 = 1;
        final LOTRBiomeSpawnList npcSpawnList2 = super.npcSpawnList;
        lists[n2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_WARGS, 20);
        final int n3 = 2;
        final LOTRBiomeSpawnList npcSpawnList3 = super.npcSpawnList;
        lists[n3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_URUKS, 7);
        factionList.add(lists);
        final LOTRBiomeSpawnList.FactionContainer factionList2 = super.npcSpawnList.newFactionList(20);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists2 = new LOTRBiomeSpawnList.SpawnListContainer[4];
        final int n4 = 0;
        final LOTRBiomeSpawnList npcSpawnList4 = super.npcSpawnList;
        lists2[n4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.SNOW_TROLLS, 1).setSpawnChance(5000);
        final int n5 = 1;
        final LOTRBiomeSpawnList npcSpawnList5 = super.npcSpawnList;
        lists2[n5] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_ORCS, 30).setConquestOnly();
        final int n6 = 2;
        final LOTRBiomeSpawnList npcSpawnList6 = super.npcSpawnList;
        lists2[n6] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_WARGS, 20).setConquestOnly();
        final int n7 = 3;
        final LOTRBiomeSpawnList npcSpawnList7 = super.npcSpawnList;
        lists2[n7] = LOTRBiomeSpawnList.entry(LOTRSpawnList.SNOW_TROLLS, 6).setConquestOnly();
        factionList2.add(lists2);
        final LOTRBiomeSpawnList.FactionContainer factionList3 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists3 = { null };
        final int n8 = 0;
        final LOTRBiomeSpawnList npcSpawnList8 = super.npcSpawnList;
        lists3[n8] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLUE_DWARVES, 10);
        factionList3.add(lists3);
        final LOTRBiomeSpawnList.FactionContainer factionList4 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists4 = { null };
        final int n9 = 0;
        final LOTRBiomeSpawnList npcSpawnList9 = super.npcSpawnList;
        lists4[n9] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DWARVES, 10);
        factionList4.add(lists4);
        final LOTRBiomeSpawnList.FactionContainer factionList5 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists5 = new LOTRBiomeSpawnList.SpawnListContainer[3];
        final int n10 = 0;
        final LOTRBiomeSpawnList npcSpawnList10 = super.npcSpawnList;
        lists5[n10] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ISENGARD_SNAGA, 15);
        final int n11 = 1;
        final LOTRBiomeSpawnList npcSpawnList11 = super.npcSpawnList;
        lists5[n11] = LOTRBiomeSpawnList.entry(LOTRSpawnList.URUK_HAI, 30);
        final int n12 = 2;
        final LOTRBiomeSpawnList npcSpawnList12 = super.npcSpawnList;
        lists5[n12] = LOTRBiomeSpawnList.entry(LOTRSpawnList.URUK_WARGS, 20);
        factionList5.add(lists5);
        super.variantChance = 0.1f;
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_MOUNTAINS);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LARCH, 0.3f);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_PINE, 0.3f);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_ASPEN, 0.3f);
        super.decorator.biomeGemFactor = 1.0f;
        super.decorator.addOre((WorldGenerator)new WorldGenMinable(LOTRMod.oreMithril, 6), 0.25f, 0, 16);
        super.decorator.addOre((WorldGenerator)new WorldGenMinable(LOTRMod.oreGlowstone, 4), 8.0f, 0, 48);
        super.decorator.flowersPerChunk = 1;
        super.decorator.grassPerChunk = 3;
        super.decorator.doubleGrassPerChunk = 1;
        super.decorator.generateWater = false;
        super.decorator.addTree(LOTRTreeType.SPRUCE, 400);
        super.decorator.addTree(LOTRTreeType.SPRUCE_THIN, 400);
        super.decorator.addTree(LOTRTreeType.LARCH, 300);
        super.decorator.addTree(LOTRTreeType.SPRUCE_MEGA, 100);
        super.decorator.addTree(LOTRTreeType.SPRUCE_MEGA_THIN, 20);
        super.decorator.addTree(LOTRTreeType.FIR, 500);
        super.decorator.addTree(LOTRTreeType.PINE, 500);
        this.registerMountainsFlowers();
        super.biomeColors.setSky(12241873);
        super.decorator.generateOrcDungeon = true;
        super.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.STONE(1, 4), 500);
        super.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.DWARVEN(1, 4), 1500);
        super.decorator.addRandomStructure(new LOTRWorldGenRuinedDwarvenTower(false), 1000);
        super.decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 400);
        this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterMistyMountains;
    }
    
    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.MISTY_MOUNTAINS;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.MISTY_MOUNTAINS.getSubregion("mistyMountains");
    }
    
    @Override
    public boolean getEnableRiver() {
        return false;
    }
    
    @Override
    protected void generateMountainTerrain(final World world, final Random random, final Block[] blocks, final byte[] meta, final int i, final int k, final int xzIndex, final int ySize, final int height, final int rockDepth, final LOTRBiomeVariant variant) {
        final int snowHeight = 120 - rockDepth;
        for (int stoneHeight = snowHeight - 30, j = ySize - 1; j >= stoneHeight; --j) {
            final int index = xzIndex * ySize + j;
            final Block block = blocks[index];
            if (j >= snowHeight && block == super.topBlock) {
                blocks[index] = Blocks.snow;
                meta[index] = 0;
            }
            else if (block == super.topBlock || block == super.fillerBlock) {
                blocks[index] = Blocks.stone;
                meta[index] = 0;
            }
        }
    }
    
    @Override
    public void decorate(final World world, final Random random, final int i, final int k) {
        super.decorate(world, random, i, k);
        for (int count = 0; count < 2; ++count) {
            final int i2 = i + random.nextInt(16) + 8;
            final int k2 = k + random.nextInt(16) + 8;
            final int j1 = world.getHeightValue(i2, k2);
            if (j1 < 100) {
                super.decorator.genTree(world, random, i2, j1, k2);
            }
        }
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return 0.0f;
    }
    
    @Override
    public float getTreeIncreaseChance() {
        return 0.05f;
    }
}
