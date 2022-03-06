// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import net.minecraft.world.gen.feature.WorldGenVines;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.LOTRAchievement;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.structure2.LOTRWorldGenStoneRuin;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenMinable;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.entity.animal.LOTREntityJungleScorpion;
import lotr.common.entity.animal.LOTREntityMidges;
import lotr.common.entity.animal.LOTREntityButterfly;
import lotr.common.entity.animal.LOTREntityBird;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.entity.animal.LOTREntityFlamingo;
import lotr.common.LOTRMod;
import lotr.common.world.feature.LOTRWorldGenObsidianGravel;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenFarHaradJungle extends LOTRBiomeGenFarHarad
{
    private WorldGenerator obsidianGen;
    protected int obsidianGravelRarity;
    
    public LOTRBiomeGenFarHaradJungle(final int i, final boolean major) {
        super(i, major);
        this.obsidianGen = new LOTRWorldGenObsidianGravel();
        this.obsidianGravelRarity = 20;
        if (this.isMuddy()) {
            super.topBlock = LOTRMod.mudGrass;
            super.fillerBlock = LOTRMod.mud;
        }
        super.spawnableCreatureList.clear();
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityFlamingo.class, 10, 4, 4));
        super.spawnableLOTRAmbientList.clear();
        super.spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityBird.class, 10, 4, 4));
        super.spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityButterfly.class, 15, 4, 4));
        if (this.isMuddy()) {
            super.spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityMidges.class, 10, 4, 4));
        }
        super.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityJungleScorpion.class, 30, 4, 4));
        super.npcSpawnList.clear();
        final LOTRBiomeSpawnList.FactionContainer factionList = super.npcSpawnList.newFactionList(100, 0.0f);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n = 0;
        final LOTRBiomeSpawnList npcSpawnList = super.npcSpawnList;
        lists[n] = LOTRBiomeSpawnList.entry(LOTRSpawnList.TAURETHRIM, 10).setSpawnChance(5000);
        final int n2 = 1;
        final LOTRBiomeSpawnList npcSpawnList2 = super.npcSpawnList;
        lists[n2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.TAURETHRIM_WARRIORS, 30).setSpawnChance(5000);
        factionList.add(lists);
        final LOTRBiomeSpawnList.FactionContainer factionList2 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists2 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n3 = 0;
        final LOTRBiomeSpawnList npcSpawnList3 = super.npcSpawnList;
        lists2[n3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.TAURETHRIM_WARRIORS, 10);
        final int n4 = 1;
        final LOTRBiomeSpawnList npcSpawnList4 = super.npcSpawnList;
        lists2[n4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.TAURETHRIM, 4);
        factionList2.add(lists2);
        final LOTRBiomeSpawnList.FactionContainer factionList3 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists3 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n5 = 0;
        final LOTRBiomeSpawnList npcSpawnList5 = super.npcSpawnList;
        lists3[n5] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 10);
        final int n6 = 1;
        final LOTRBiomeSpawnList npcSpawnList6 = super.npcSpawnList;
        lists3[n6] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 2).setConquestThreshold(50.0f);
        factionList3.add(lists3);
        final LOTRBiomeSpawnList.FactionContainer factionList4 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists4 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n7 = 0;
        final LOTRBiomeSpawnList npcSpawnList7 = super.npcSpawnList;
        lists4[n7] = LOTRBiomeSpawnList.entry(LOTRSpawnList.SOUTHRON_WARRIORS, 10);
        final int n8 = 1;
        final LOTRBiomeSpawnList npcSpawnList8 = super.npcSpawnList;
        lists4[n8] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GULF_WARRIORS, 10);
        factionList4.add(lists4);
        final LOTRBiomeSpawnList.FactionContainer factionList5 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists5 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n9 = 0;
        final LOTRBiomeSpawnList npcSpawnList9 = super.npcSpawnList;
        lists5[n9] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORWAITH_WARRIORS, 10);
        final int n10 = 1;
        final LOTRBiomeSpawnList npcSpawnList10 = super.npcSpawnList;
        lists5[n10] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORWAITH, 5);
        factionList5.add(lists5);
        final LOTRBiomeSpawnList.FactionContainer factionList6 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists6 = { null };
        final int n11 = 0;
        final LOTRBiomeSpawnList npcSpawnList11 = super.npcSpawnList;
        lists6[n11] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HALF_TROLLS, 10);
        factionList6.add(lists6);
        this.addBiomeVariant(LOTRBiomeVariant.FLOWERS);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS);
        this.addBiomeVariant(LOTRBiomeVariant.MOUNTAIN);
        this.addBiomeVariant(LOTRBiomeVariant.JUNGLE_DENSE);
        if (this.isMuddy()) {
            super.decorator.addSoil((WorldGenerator)new WorldGenMinable(LOTRMod.mud, 32), 80.0f, 0, 256);
            super.decorator.addSoil((WorldGenerator)new WorldGenMinable(LOTRMod.mud, 32), 80.0f, 0, 64);
        }
        super.decorator.addOre((WorldGenerator)new WorldGenMinable(Blocks.gold_ore, 4), 3.0f, 0, 48);
        super.decorator.addGem((WorldGenerator)new WorldGenMinable(LOTRMod.oreGem, 4, 8, Blocks.stone), 3.0f, 0, 48);
        super.decorator.treesPerChunk = 40;
        super.decorator.vinesPerChunk = 50;
        super.decorator.flowersPerChunk = 4;
        super.decorator.doubleFlowersPerChunk = 4;
        super.decorator.grassPerChunk = 15;
        super.decorator.doubleGrassPerChunk = 10;
        super.decorator.enableFern = true;
        super.decorator.canePerChunk = 5;
        super.decorator.cornPerChunk = 10;
        super.decorator.melonPerChunk = 0.2f;
        super.decorator.clearTrees();
        super.decorator.addTree(LOTRTreeType.JUNGLE, 1000);
        super.decorator.addTree(LOTRTreeType.JUNGLE_LARGE, 500);
        super.decorator.addTree(LOTRTreeType.MAHOGANY, 500);
        super.decorator.addTree(LOTRTreeType.JUNGLE_SHRUB, 1000);
        super.decorator.addTree(LOTRTreeType.MANGO, 20);
        super.decorator.addTree(LOTRTreeType.BANANA, 50);
        this.registerJungleFlowers();
        super.biomeColors.setGrass(10607421);
        super.biomeColors.setFoliage(8376636);
        super.biomeColors.setSky(11977908);
        super.biomeColors.setFog(11254938);
        super.biomeColors.setWater(4104311);
        super.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.TAUREDAIN(1, 4), 100);
        super.invasionSpawns.addInvasion(LOTRInvasions.MOREDAIN, LOTREventSpawner.EventChance.RARE);
        super.invasionSpawns.addInvasion(LOTRInvasions.TAUREDAIN, LOTREventSpawner.EventChance.RARE);
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterFarHaradJungle;
    }
    
    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.FAR_HARAD_JUNGLE;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.FAR_HARAD_JUNGLE.getSubregion("jungle");
    }
    
    @Override
    public LOTRRoadType getRoadBlock() {
        return LOTRRoadType.TAUREDAIN.setRepair(0.8f);
    }
    
    public boolean hasJungleLakes() {
        return true;
    }
    
    public boolean isMuddy() {
        return true;
    }
    
    @Override
    protected double modifyStoneNoiseForFiller(double stoneNoise) {
        if (this.isMuddy()) {
            stoneNoise += 40.0;
        }
        return stoneNoise;
    }
    
    @Override
    public void decorate(final World world, final Random random, final int i, final int k) {
        super.decorate(world, random, i, k);
        final WorldGenVines vines = new WorldGenVines();
        for (int l = 0; l < 10; ++l) {
            final int i2 = i + random.nextInt(16) + 8;
            final int j1 = 24;
            final int k2 = k + random.nextInt(16) + 8;
            vines.generate(world, random, i2, j1, k2);
        }
        if (this.obsidianGravelRarity > 0 && random.nextInt(this.obsidianGravelRarity) == 0) {
            final int i3 = i + random.nextInt(16) + 8;
            final int k3 = k + random.nextInt(16) + 8;
            final int j1 = world.getTopSolidOrLiquidBlock(i3, k3);
            this.obsidianGen.generate(world, random, i3, j1, k3);
        }
    }
    
    @Override
    public GrassBlockAndMeta getRandomGrass(final Random random) {
        if (random.nextInt(4) == 0) {
            return new GrassBlockAndMeta(LOTRMod.tallGrass, 5);
        }
        return super.getRandomGrass(random);
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return 0.25f;
    }
}
