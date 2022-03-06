// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import net.minecraft.block.Block;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.LOTRAchievement;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.structure2.LOTRWorldGenSmallStoneRuin;
import lotr.common.world.structure2.LOTRWorldGenAngmarHillmanVillage;
import lotr.common.world.structure2.LOTRWorldGenAngmarCamp;
import lotr.common.world.structure2.LOTRWorldGenAngmarWargPit;
import lotr.common.world.structure.LOTRWorldGenAngmarShrine;
import lotr.common.world.feature.LOTRWorldGenBlastedLand;
import lotr.common.world.structure2.LOTRWorldGenStoneRuin;
import lotr.common.world.structure.LOTRWorldGenAngmarTower;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.world.gen.feature.WorldGenMinable;
import lotr.common.LOTRMod;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.entity.animal.LOTREntityBear;
import lotr.common.entity.animal.LOTREntityDeer;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.entity.passive.EntityWolf;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenAngmar extends LOTRBiome
{
    private WorldGenerator boulderGen;
    
    public LOTRBiomeGenAngmar(final int i, final boolean major) {
        super(i, major);
        this.boulderGen = new LOTRWorldGenBoulder(Blocks.stone, 0, 1, 2);
        super.spawnableCreatureList.clear();
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)EntityWolf.class, 10, 4, 8));
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityDeer.class, 2, 4, 8));
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityBear.class, 4, 1, 4));
        super.spawnableWaterCreatureList.clear();
        super.spawnableLOTRAmbientList.clear();
        final LOTRBiomeSpawnList.FactionContainer factionList = super.npcSpawnList.newFactionList(100);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists = new LOTRBiomeSpawnList.SpawnListContainer[7];
        final int n = 0;
        final LOTRBiomeSpawnList npcSpawnList = super.npcSpawnList;
        lists[n] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_ORCS, 30);
        final int n2 = 1;
        final LOTRBiomeSpawnList npcSpawnList2 = super.npcSpawnList;
        lists[n2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_BOMBARDIERS, 5);
        final int n3 = 2;
        final LOTRBiomeSpawnList npcSpawnList3 = super.npcSpawnList;
        lists[n3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_WARGS, 30);
        final int n4 = 3;
        final LOTRBiomeSpawnList npcSpawnList4 = super.npcSpawnList;
        lists[n4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.TROLLS, 30);
        final int n5 = 4;
        final LOTRBiomeSpawnList npcSpawnList5 = super.npcSpawnList;
        lists[n5] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HILL_TROLLS, 20);
        final int n6 = 5;
        final LOTRBiomeSpawnList npcSpawnList6 = super.npcSpawnList;
        lists[n6] = LOTRBiomeSpawnList.entry(LOTRSpawnList.SNOW_TROLLS, 5);
        final int n7 = 6;
        final LOTRBiomeSpawnList npcSpawnList7 = super.npcSpawnList;
        lists[n7] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_HILLMEN, 20);
        factionList.add(lists);
        final LOTRBiomeSpawnList.FactionContainer factionList2 = super.npcSpawnList.newFactionList(2, 0.0f);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists2 = { null };
        final int n8 = 0;
        final LOTRBiomeSpawnList npcSpawnList8 = super.npcSpawnList;
        lists2[n8] = LOTRBiomeSpawnList.entry(LOTRSpawnList.WICKED_DWARVES, 10);
        factionList2.add(lists2);
        final LOTRBiomeSpawnList.FactionContainer factionList3 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists3 = { null };
        final int n9 = 0;
        final LOTRBiomeSpawnList npcSpawnList9 = super.npcSpawnList;
        lists3[n9] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RANGERS_NORTH, 10);
        factionList3.add(lists3);
        final LOTRBiomeSpawnList.FactionContainer factionList4 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists4 = { null };
        final int n10 = 0;
        final LOTRBiomeSpawnList npcSpawnList10 = super.npcSpawnList;
        lists4[n10] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RIVENDELL_WARRIORS, 10);
        factionList4.add(lists4);
        super.npcSpawnList.conquestGainRate = 0.5f;
        this.addBiomeVariant(LOTRBiomeVariant.FOREST);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
        this.addBiomeVariant(LOTRBiomeVariant.STEPPE);
        this.addBiomeVariant(LOTRBiomeVariant.STEPPE_BARREN);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS_FOREST);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_PINE, 1.0f);
        super.decorator.addOre((WorldGenerator)new WorldGenMinable(LOTRMod.oreMorgulIron, 8), 20.0f, 0, 64);
        super.decorator.addOre((WorldGenerator)new WorldGenMinable(LOTRMod.oreGulduril, 8), 8.0f, 0, 32);
        super.decorator.flowersPerChunk = 0;
        super.decorator.grassPerChunk = 4;
        super.decorator.doubleGrassPerChunk = 1;
        super.decorator.addTree(LOTRTreeType.SPRUCE_THIN, 100);
        super.decorator.addTree(LOTRTreeType.SPRUCE, 200);
        super.decorator.addTree(LOTRTreeType.SPRUCE_DEAD, 150);
        super.decorator.addTree(LOTRTreeType.CHARRED, 150);
        super.decorator.addTree(LOTRTreeType.FIR, 100);
        super.decorator.addTree(LOTRTreeType.PINE, 200);
        super.biomeColors.setGrass(7896151);
        super.biomeColors.setSky(5324595);
        super.biomeColors.setClouds(1644825);
        super.biomeColors.setFog(1644825);
        super.decorator.generateOrcDungeon = true;
        super.decorator.generateTrollHoard = true;
        super.decorator.addRandomStructure(new LOTRWorldGenAngmarTower(false), 300);
        super.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.ANGMAR(1, 4), 40);
        super.decorator.addRandomStructure(new LOTRWorldGenBlastedLand(false), 40);
        super.decorator.addRandomStructure(new LOTRWorldGenAngmarShrine(false), 200);
        super.decorator.addRandomStructure(new LOTRWorldGenAngmarWargPit(false), 200);
        super.decorator.addRandomStructure(new LOTRWorldGenAngmarCamp(false), 50);
        super.decorator.addRandomStructure(new LOTRWorldGenAngmarHillmanVillage(false), 400);
        super.decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 300);
        this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
        super.invasionSpawns.addInvasion(LOTRInvasions.RANGER_NORTH, LOTREventSpawner.EventChance.RARE);
        super.invasionSpawns.addInvasion(LOTRInvasions.HIGH_ELF_LINDON, LOTREventSpawner.EventChance.RARE);
        super.invasionSpawns.addInvasion(LOTRInvasions.HIGH_ELF_RIVENDELL, LOTREventSpawner.EventChance.RARE);
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterAngmar;
    }
    
    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.ANGMAR;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.ANGMAR.getSubregion("angmar");
    }
    
    @Override
    public boolean getEnableRiver() {
        return false;
    }
    
    @Override
    public void generateBiomeTerrain(final World world, final Random random, final Block[] blocks, final byte[] meta, final int i, final int k, final double stoneNoise, final int height, final LOTRBiomeVariant variant) {
        final Block topBlock_pre = super.topBlock;
        final int topBlockMeta_pre = super.topBlockMeta;
        final Block fillerBlock_pre = super.fillerBlock;
        final int fillerBlockMeta_pre = super.fillerBlockMeta;
        final double d1 = LOTRBiome.biomeTerrainNoise.func_151601_a(i * 0.07, k * 0.07);
        final double d2 = LOTRBiome.biomeTerrainNoise.func_151601_a(i * 0.4, k * 0.4);
        if (d1 + d2 > 0.5) {
            super.topBlock = Blocks.stone;
            super.topBlockMeta = 0;
            super.fillerBlock = super.topBlock;
            super.fillerBlockMeta = super.topBlockMeta;
        }
        super.generateBiomeTerrain(world, random, blocks, meta, i, k, stoneNoise, height, variant);
        super.topBlock = topBlock_pre;
        super.topBlockMeta = topBlockMeta_pre;
        super.fillerBlock = fillerBlock_pre;
        super.fillerBlockMeta = fillerBlockMeta_pre;
    }
    
    @Override
    public void decorate(final World world, final Random random, final int i, final int k) {
        super.decorate(world, random, i, k);
        for (int l = 0; l < 4; ++l) {
            final int i2 = i + random.nextInt(16) + 8;
            final int k2 = k + random.nextInt(16) + 8;
            final int j1 = world.getHeightValue(i2, k2);
            if (j1 > 80) {
                super.decorator.genTree(world, random, i2, j1, k2);
            }
        }
        if (random.nextInt(6) == 0) {
            final int i3 = i + random.nextInt(16) + 8;
            final int k3 = k + random.nextInt(16) + 8;
            this.boulderGen.generate(world, random, i3, world.getHeightValue(i3, k3), k3);
        }
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return 0.1f;
    }
    
    @Override
    public int spawnCountMultiplier() {
        return 3;
    }
    
    @Override
    public boolean canSpawnHostilesInDay() {
        return true;
    }
    
    @Override
    public float getTreeIncreaseChance() {
        return 0.25f;
    }
}
