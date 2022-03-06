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
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.entity.animal.LOTREntityBear;
import lotr.common.entity.animal.LOTREntityElk;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.entity.passive.EntityWolf;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenColdfells extends LOTRBiome
{
    private WorldGenerator boulderGen;
    
    public LOTRBiomeGenColdfells(final int i, final boolean major) {
        super(i, major);
        this.boulderGen = new LOTRWorldGenBoulder(Blocks.stone, 0, 2, 4);
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)EntityWolf.class, 10, 4, 8));
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityElk.class, 4, 4, 6));
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityBear.class, 6, 1, 4));
        final LOTRBiomeSpawnList.FactionContainer factionList = super.npcSpawnList.newFactionList(50);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists = new LOTRBiomeSpawnList.SpawnListContainer[3];
        final int n = 0;
        final LOTRBiomeSpawnList npcSpawnList = super.npcSpawnList;
        lists[n] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_ORCS, 10);
        final int n2 = 1;
        final LOTRBiomeSpawnList npcSpawnList2 = super.npcSpawnList;
        lists[n2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_URUKS, 2);
        final int n3 = 2;
        final LOTRBiomeSpawnList npcSpawnList3 = super.npcSpawnList;
        lists[n3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_WARGS, 5);
        factionList.add(lists);
        final LOTRBiomeSpawnList.FactionContainer factionList2 = super.npcSpawnList.newFactionList(50);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists2 = new LOTRBiomeSpawnList.SpawnListContainer[5];
        final int n4 = 0;
        final LOTRBiomeSpawnList npcSpawnList4 = super.npcSpawnList;
        lists2[n4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.TROLLS, 15);
        final int n5 = 1;
        final LOTRBiomeSpawnList npcSpawnList5 = super.npcSpawnList;
        lists2[n5] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_HILLMEN, 5).setSpawnChance(2000);
        final int n6 = 2;
        final LOTRBiomeSpawnList npcSpawnList6 = super.npcSpawnList;
        lists2[n6] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_ORCS, 5).setConquestOnly();
        final int n7 = 3;
        final LOTRBiomeSpawnList npcSpawnList7 = super.npcSpawnList;
        lists2[n7] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_WARGS, 5).setConquestOnly();
        final int n8 = 4;
        final LOTRBiomeSpawnList npcSpawnList8 = super.npcSpawnList;
        lists2[n8] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HILL_TROLLS, 5).setConquestThreshold(50.0f);
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
        super.decorator.biomeGemFactor = 0.75f;
        super.decorator.treesPerChunk = 2;
        super.decorator.willowPerChunk = 1;
        super.decorator.flowersPerChunk = 2;
        super.decorator.doubleFlowersPerChunk = 1;
        super.decorator.grassPerChunk = 6;
        super.decorator.doubleGrassPerChunk = 2;
        super.decorator.generateAthelas = true;
        super.decorator.addTree(LOTRTreeType.FIR, 500);
        super.decorator.addTree(LOTRTreeType.PINE, 500);
        super.decorator.addTree(LOTRTreeType.SPRUCE, 400);
        super.decorator.addTree(LOTRTreeType.SPRUCE_THIN, 200);
        super.decorator.addTree(LOTRTreeType.OAK, 200);
        super.decorator.addTree(LOTRTreeType.OAK_LARGE, 30);
        super.decorator.addTree(LOTRTreeType.LARCH, 300);
        super.decorator.addTree(LOTRTreeType.MAPLE, 100);
        super.decorator.addTree(LOTRTreeType.MAPLE_LARGE, 10);
        this.registerForestFlowers();
        super.decorator.generateOrcDungeon = true;
        super.decorator.generateTrollHoard = true;
        super.decorator.addRandomStructure(new LOTRWorldGenRuinedDunedainTower(false), 500);
        super.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.STONE(1, 4), 100);
        super.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.ARNOR(1, 4), 100);
        super.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.ANGMAR(1, 4), 100);
        super.decorator.addRandomStructure(new LOTRWorldGenAngmarHillmanVillage(false), 2000);
        super.decorator.addRandomStructure(new LOTRWorldGenAngmarHillmanHouse(false), 800);
        super.decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 400);
        super.decorator.addRandomStructure(new LOTRWorldGenRhudaurCastle(false), 3000);
        this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_UNCOMMON);
        super.invasionSpawns.addInvasion(LOTRInvasions.RANGER_NORTH, LOTREventSpawner.EventChance.UNCOMMON);
        super.invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD, LOTREventSpawner.EventChance.UNCOMMON);
        super.invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD_WARG, LOTREventSpawner.EventChance.UNCOMMON);
        super.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR, LOTREventSpawner.EventChance.UNCOMMON);
        super.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR_HILLMEN, LOTREventSpawner.EventChance.UNCOMMON);
        super.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR_WARG, LOTREventSpawner.EventChance.UNCOMMON);
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterColdfells;
    }
    
    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.COLDFELLS;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.ANGMAR.getSubregion("coldfells");
    }
    
    @Override
    public void decorate(final World world, final Random random, final int i, final int k) {
        super.decorate(world, random, i, k);
        if (random.nextInt(3) == 0) {
            for (int l = 0; l < 3; ++l) {
                final int i2 = i + random.nextInt(16) + 8;
                final int k2 = k + random.nextInt(16) + 8;
                this.boulderGen.generate(world, random, i2, world.getHeightValue(i2, k2), k2);
            }
        }
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return 0.1f;
    }
}
