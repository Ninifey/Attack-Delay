// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.feature.LOTRWorldGenSkullPile;
import lotr.common.world.feature.LOTRTreeType;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.LOTRAchievement;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.entity.npc.LOTREntityScrapTrader;
import lotr.common.world.structure2.LOTRWorldGenSmallStoneRuin;
import lotr.common.world.feature.LOTRWorldGenBlastedLand;
import lotr.common.world.structure.LOTRWorldGenRuinedRohanWatchtower;
import lotr.common.world.structure2.LOTRWorldGenUrukWargPit;
import lotr.common.world.structure2.LOTRWorldGenUrukCamp;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import lotr.common.LOTRMod;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenRohanUruk extends LOTRBiomeGenRohan
{
    private WorldGenerator deadMoundGen;
    
    public LOTRBiomeGenRohanUruk(final int i, final boolean major) {
        super(i, major);
        this.deadMoundGen = new LOTRWorldGenBoulder(LOTRMod.wasteBlock, 0, 1, 3);
        super.npcSpawnList.clear();
        final LOTRBiomeSpawnList.FactionContainer factionList = super.npcSpawnList.newFactionList(100);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n = 0;
        final LOTRBiomeSpawnList npcSpawnList = super.npcSpawnList;
        lists[n] = LOTRBiomeSpawnList.entry(LOTRSpawnList.URUK_HAI, 10);
        final int n2 = 1;
        final LOTRBiomeSpawnList npcSpawnList2 = super.npcSpawnList;
        lists[n2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.URUK_WARGS, 4);
        factionList.add(lists);
        final LOTRBiomeSpawnList.FactionContainer factionList2 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists2 = { null };
        final int n3 = 0;
        final LOTRBiomeSpawnList npcSpawnList3 = super.npcSpawnList;
        lists2[n3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ROHIRRIM_WARRIORS, 10);
        factionList2.add(lists2);
        final LOTRBiomeSpawnList.FactionContainer factionList3 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists3 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n4 = 0;
        final LOTRBiomeSpawnList npcSpawnList4 = super.npcSpawnList;
        lists3[n4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DUNLENDINGS, 10);
        final int n5 = 1;
        final LOTRBiomeSpawnList npcSpawnList5 = super.npcSpawnList;
        lists3[n5] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DUNLENDING_WARRIORS, 10);
        factionList3.add(lists3);
        final LOTRBiomeSpawnList.FactionContainer factionList4 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists4 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n6 = 0;
        final LOTRBiomeSpawnList npcSpawnList6 = super.npcSpawnList;
        lists4[n6] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ENTS, 5);
        final int n7 = 1;
        final LOTRBiomeSpawnList npcSpawnList7 = super.npcSpawnList;
        lists4[n7] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HUORNS, 20);
        factionList4.add(lists4);
        this.clearBiomeVariants();
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS);
        this.addBiomeVariant(LOTRBiomeVariant.DEADFOREST_OAK);
        super.decorator.resetTreeCluster();
        super.decorator.willowPerChunk = 0;
        super.decorator.flowersPerChunk = 1;
        super.decorator.grassPerChunk = 6;
        super.decorator.doubleGrassPerChunk = 1;
        super.decorator.clearRandomStructures();
        super.decorator.addRandomStructure(new LOTRWorldGenUrukCamp(false), 120);
        super.decorator.addRandomStructure(new LOTRWorldGenUrukWargPit(false), 300);
        super.decorator.addRandomStructure(new LOTRWorldGenRuinedRohanWatchtower(false), 300);
        super.decorator.addRandomStructure(new LOTRWorldGenBlastedLand(true), 40);
        super.decorator.addRandomStructure(new LOTRWorldGenSmallStoneRuin(false), 300);
        super.decorator.clearVillages();
        this.clearTravellingTraders();
        this.registerTravellingTrader(LOTREntityScrapTrader.class);
        this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_UNCOMMON);
        super.invasionSpawns.clearInvasions();
        super.invasionSpawns.addInvasion(LOTRInvasions.ROHAN, LOTREventSpawner.EventChance.COMMON);
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterRohanUrukHighlands;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.ISENGARD.getSubregion("rohan");
    }
    
    @Override
    public boolean getEnableRiver() {
        return false;
    }
    
    @Override
    public void decorate(final World world, final Random random, final int i, final int k) {
        super.decorate(world, random, i, k);
        if (random.nextInt(30) == 0) {
            final WorldGenerator treeGen = (WorldGenerator)((random.nextInt(3) == 0) ? LOTRTreeType.OAK_DEAD.create(false, random) : LOTRTreeType.CHARRED.create(false, random));
            for (int trees = 3 + random.nextInt(5), l = 0; l < trees; ++l) {
                final int i2 = i + random.nextInt(16) + 8;
                final int k2 = k + random.nextInt(16) + 8;
                treeGen.generate(world, random, i2, world.getHeightValue(i2, k2), k2);
            }
        }
        if (random.nextInt(32) == 0) {
            for (int j = 0; j < 3; ++j) {
                final int i3 = i + random.nextInt(16) + 8;
                final int k3 = k + random.nextInt(16) + 8;
                final int j2 = world.getHeightValue(i3, k3);
                this.deadMoundGen.generate(world, random, i3, j2, k3);
                new LOTRWorldGenSkullPile().generate(world, random, i3, j2, k3);
            }
        }
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return 0.1f;
    }
    
    @Override
    public boolean canSpawnHostilesInDay() {
        return true;
    }
    
    @Override
    public int spawnCountMultiplier() {
        return 3;
    }
}
