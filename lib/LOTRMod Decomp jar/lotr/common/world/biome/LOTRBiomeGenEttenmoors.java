// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.LOTRAchievement;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.structure2.LOTRWorldGenRhudaurCastle;
import lotr.common.world.structure2.LOTRWorldGenSmallStoneRuin;
import lotr.common.world.structure2.LOTRWorldGenAngmarHillmanHouse;
import lotr.common.world.structure2.LOTRWorldGenAngmarHillmanVillage;
import lotr.common.world.structure2.LOTRWorldGenStoneRuin;
import lotr.common.world.structure.LOTRWorldGenRuinedDunedainTower;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.entity.animal.LOTREntityButterfly;
import lotr.common.entity.animal.LOTREntityBird;
import lotr.common.entity.animal.LOTREntityBear;
import lotr.common.entity.animal.LOTREntityElk;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.entity.passive.EntityWolf;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenEttenmoors extends LOTRBiome
{
    private WorldGenerator boulderGenLarge;
    private WorldGenerator boulderGenSmall;
    
    public LOTRBiomeGenEttenmoors(final int i, final boolean major) {
        super(i, major);
        this.boulderGenLarge = new LOTRWorldGenBoulder(Blocks.stone, 0, 2, 5);
        this.boulderGenSmall = new LOTRWorldGenBoulder(Blocks.stone, 0, 1, 2);
        super.spawnableCreatureList.clear();
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)EntityWolf.class, 10, 4, 8));
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityElk.class, 6, 4, 6));
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityBear.class, 6, 1, 4));
        super.spawnableLOTRAmbientList.clear();
        super.spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityBird.class, 10, 4, 4));
        super.spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityButterfly.class, 10, 4, 4));
        final LOTRBiomeSpawnList.FactionContainer factionList = super.npcSpawnList.newFactionList(35);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists = new LOTRBiomeSpawnList.SpawnListContainer[3];
        final int n = 0;
        final LOTRBiomeSpawnList npcSpawnList = super.npcSpawnList;
        lists[n] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_ORCS, 30);
        final int n2 = 1;
        final LOTRBiomeSpawnList npcSpawnList2 = super.npcSpawnList;
        lists[n2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_URUKS, 7);
        final int n3 = 2;
        final LOTRBiomeSpawnList npcSpawnList3 = super.npcSpawnList;
        lists[n3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_WARGS, 10);
        factionList.add(lists);
        final LOTRBiomeSpawnList.FactionContainer factionList2 = super.npcSpawnList.newFactionList(70);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists2 = new LOTRBiomeSpawnList.SpawnListContainer[5];
        final int n4 = 0;
        final LOTRBiomeSpawnList npcSpawnList4 = super.npcSpawnList;
        lists2[n4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.TROLLS, 40);
        final int n5 = 1;
        final LOTRBiomeSpawnList npcSpawnList5 = super.npcSpawnList;
        lists2[n5] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HILL_TROLLS, 20);
        final int n6 = 2;
        final LOTRBiomeSpawnList npcSpawnList6 = super.npcSpawnList;
        lists2[n6] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_HILLMEN, 20).setSpawnChance(500);
        final int n7 = 3;
        final LOTRBiomeSpawnList npcSpawnList7 = super.npcSpawnList;
        lists2[n7] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_ORCS, 15);
        final int n8 = 4;
        final LOTRBiomeSpawnList npcSpawnList8 = super.npcSpawnList;
        lists2[n8] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_WARGS, 5);
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
        super.npcSpawnList.conquestGainRate = 0.75f;
        super.biomeTerrain.setXZScale(100.0);
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_MOUNTAINS);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_PINE, 1.0f);
        super.decorator.biomeGemFactor = 0.75f;
        super.decorator.flowersPerChunk = 1;
        super.decorator.grassPerChunk = 4;
        super.decorator.doubleGrassPerChunk = 2;
        super.decorator.generateAthelas = true;
        super.decorator.addTree(LOTRTreeType.FIR, 400);
        super.decorator.addTree(LOTRTreeType.PINE, 800);
        super.decorator.addTree(LOTRTreeType.SPRUCE, 500);
        super.decorator.addTree(LOTRTreeType.SPRUCE_THIN, 500);
        super.decorator.addTree(LOTRTreeType.SPRUCE_DEAD, 200);
        super.decorator.addTree(LOTRTreeType.SPRUCE_MEGA, 100);
        this.registerTaigaFlowers();
        super.decorator.generateOrcDungeon = true;
        super.decorator.generateTrollHoard = true;
        super.decorator.addRandomStructure(new LOTRWorldGenRuinedDunedainTower(false), 500);
        super.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.STONE(1, 4), 100);
        super.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.ARNOR(1, 4), 100);
        super.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.ANGMAR(1, 4), 100);
        super.decorator.addRandomStructure(new LOTRWorldGenAngmarHillmanVillage(false), 1000);
        super.decorator.addRandomStructure(new LOTRWorldGenAngmarHillmanHouse(false), 500);
        super.decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 400);
        super.decorator.addRandomStructure(new LOTRWorldGenRhudaurCastle(false), 3000);
        this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_UNCOMMON);
        super.invasionSpawns.addInvasion(LOTRInvasions.RANGER_NORTH, LOTREventSpawner.EventChance.UNCOMMON);
        super.invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD, LOTREventSpawner.EventChance.COMMON);
        super.invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD_WARG, LOTREventSpawner.EventChance.COMMON);
        super.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR, LOTREventSpawner.EventChance.COMMON);
        super.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR_HILLMEN, LOTREventSpawner.EventChance.COMMON);
        super.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR_WARG, LOTREventSpawner.EventChance.COMMON);
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterEttenmoors;
    }
    
    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.ETTENMOORS;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.ANGMAR.getSubregion("ettenmoors");
    }
    
    @Override
    public void decorate(final World world, final Random random, final int i, final int k) {
        super.decorate(world, random, i, k);
        for (int l = 0; l < 3; ++l) {
            final int i2 = i + random.nextInt(16) + 8;
            final int k2 = k + random.nextInt(16) + 8;
            final int j1 = world.getHeightValue(i2, k2);
            if (j1 > 84) {
                super.decorator.genTree(world, random, i2, j1, k2);
            }
        }
        if (random.nextInt(4) == 0) {
            for (int l = 0; l < 3; ++l) {
                final int i2 = i + random.nextInt(16) + 8;
                final int k2 = k + random.nextInt(16) + 8;
                this.boulderGenLarge.generate(world, random, i2, world.getHeightValue(i2, k2), k2);
            }
        }
        for (int l = 0; l < 2; ++l) {
            final int i2 = i + random.nextInt(16) + 8;
            final int k2 = k + random.nextInt(16) + 8;
            this.boulderGenSmall.generate(world, random, i2, world.getHeightValue(i2, k2), k2);
        }
    }
    
    @Override
    public float getTreeIncreaseChance() {
        return 0.25f;
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return 0.1f;
    }
}
