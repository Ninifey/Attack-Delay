// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.LOTRAchievement;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.structure2.LOTRWorldGenRhudaurCastle;
import lotr.common.world.structure2.LOTRWorldGenSmallStoneRuin;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.entity.animal.LOTREntityBear;
import lotr.common.entity.animal.LOTREntityElk;
import lotr.common.entity.animal.LOTREntityDeer;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenTrollshaws extends LOTRBiome
{
    private WorldGenerator boulderGen;
    
    public LOTRBiomeGenTrollshaws(final int i, final boolean major) {
        super(i, major);
        this.boulderGen = new LOTRWorldGenBoulder(Blocks.stone, 0, 2, 6);
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityHorse.class, 8, 2, 6));
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)EntityWolf.class, 10, 4, 8));
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityDeer.class, 20, 4, 6));
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityElk.class, 6, 4, 6));
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityBear.class, 10, 1, 4));
        final LOTRBiomeSpawnList.FactionContainer factionList = super.npcSpawnList.newFactionList(10);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists = { null };
        final int n = 0;
        final LOTRBiomeSpawnList npcSpawnList = super.npcSpawnList;
        lists[n] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RANGERS_NORTH, 10).setSpawnChance(1000);
        factionList.add(lists);
        final LOTRBiomeSpawnList.FactionContainer factionList2 = super.npcSpawnList.newFactionList(60);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists2 = new LOTRBiomeSpawnList.SpawnListContainer[3];
        final int n2 = 0;
        final LOTRBiomeSpawnList npcSpawnList2 = super.npcSpawnList;
        lists2[n2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_ORCS, 10);
        final int n3 = 1;
        final LOTRBiomeSpawnList npcSpawnList3 = super.npcSpawnList;
        lists2[n3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_WARGS, 2);
        final int n4 = 2;
        final LOTRBiomeSpawnList npcSpawnList4 = super.npcSpawnList;
        lists2[n4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_URUKS, 1).setConquestThreshold(100.0f);
        factionList2.add(lists2);
        final LOTRBiomeSpawnList.FactionContainer factionList3 = super.npcSpawnList.newFactionList(40);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists3 = { null };
        final int n5 = 0;
        final LOTRBiomeSpawnList npcSpawnList5 = super.npcSpawnList;
        lists3[n5] = LOTRBiomeSpawnList.entry(LOTRSpawnList.TROLLS, 10);
        factionList3.add(lists3);
        final LOTRBiomeSpawnList.FactionContainer factionList4 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists4 = new LOTRBiomeSpawnList.SpawnListContainer[3];
        final int n6 = 0;
        final LOTRBiomeSpawnList npcSpawnList6 = super.npcSpawnList;
        lists4[n6] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_ORCS, 10);
        final int n7 = 1;
        final LOTRBiomeSpawnList npcSpawnList7 = super.npcSpawnList;
        lists4[n7] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_WARGS, 2);
        final int n8 = 2;
        final LOTRBiomeSpawnList npcSpawnList8 = super.npcSpawnList;
        lists4[n8] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_HILLMEN, 10);
        factionList4.add(lists4);
        final LOTRBiomeSpawnList.FactionContainer factionList5 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists5 = { null };
        final int n9 = 0;
        final LOTRBiomeSpawnList npcSpawnList9 = super.npcSpawnList;
        lists5[n9] = LOTRBiomeSpawnList.entry(LOTRSpawnList.RIVENDELL_WARRIORS, 10);
        factionList5.add(lists5);
        this.addBiomeVariantSet(LOTRBiomeVariant.SET_FOREST);
        super.decorator.treesPerChunk = 1;
        super.decorator.willowPerChunk = 1;
        super.decorator.flowersPerChunk = 3;
        super.decorator.doubleFlowersPerChunk = 2;
        super.decorator.grassPerChunk = 6;
        super.decorator.doubleGrassPerChunk = 2;
        super.decorator.generateAthelas = true;
        super.decorator.addTree(LOTRTreeType.OAK, 500);
        super.decorator.addTree(LOTRTreeType.OAK_TALL, 500);
        super.decorator.addTree(LOTRTreeType.OAK_LARGE, 200);
        super.decorator.addTree(LOTRTreeType.BEECH, 500);
        super.decorator.addTree(LOTRTreeType.BEECH_LARGE, 200);
        super.decorator.addTree(LOTRTreeType.SPRUCE, 100);
        super.decorator.addTree(LOTRTreeType.FIR, 100);
        super.decorator.addTree(LOTRTreeType.PINE, 100);
        super.decorator.addTree(LOTRTreeType.MAPLE, 50);
        super.decorator.addTree(LOTRTreeType.MAPLE_LARGE, 20);
        super.decorator.addTree(LOTRTreeType.ASPEN, 100);
        super.decorator.addTree(LOTRTreeType.ASPEN_LARGE, 20);
        this.registerForestFlowers();
        super.decorator.generateOrcDungeon = true;
        super.decorator.generateTrollHoard = true;
        super.decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 300);
        super.decorator.addRandomStructure(new LOTRWorldGenRhudaurCastle(false), 1000);
        this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_COMMON);
        super.invasionSpawns.addInvasion(LOTRInvasions.RANGER_NORTH, LOTREventSpawner.EventChance.UNCOMMON);
        super.invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD, LOTREventSpawner.EventChance.UNCOMMON);
        super.invasionSpawns.addInvasion(LOTRInvasions.GUNDABAD_WARG, LOTREventSpawner.EventChance.UNCOMMON);
        super.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR, LOTREventSpawner.EventChance.RARE);
        super.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR_HILLMEN, LOTREventSpawner.EventChance.UNCOMMON);
        super.invasionSpawns.addInvasion(LOTRInvasions.ANGMAR_WARG, LOTREventSpawner.EventChance.RARE);
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterTrollshaws;
    }
    
    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.TROLLSHAWS;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.ERIADOR.getSubregion("trollshaws");
    }
    
    @Override
    public LOTRRoadType getRoadBlock() {
        return LOTRRoadType.ARNOR.setRepair(0.7f);
    }
    
    @Override
    public void decorate(final World world, final Random random, final int i, final int k) {
        super.decorate(world, random, i, k);
        if (random.nextInt(8) == 0) {
            for (int l = 0; l < 3; ++l) {
                final int i2 = i + random.nextInt(16) + 8;
                final int k2 = k + random.nextInt(16) + 8;
                this.boulderGen.generate(world, random, i2, world.getHeightValue(i2, k2), k2);
            }
        }
        for (int l = 0; l < 10; ++l) {
            final int i2 = i + random.nextInt(16) + 8;
            final int k2 = k + random.nextInt(16) + 8;
            final int j1 = world.getHeightValue(i2, k2);
            if (j1 < 82) {
                super.decorator.genTree(world, random, i2, j1, k2);
            }
        }
    }
}
