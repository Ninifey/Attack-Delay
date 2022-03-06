// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.feature.LOTRWorldGenSkullPile;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.LOTRAchievement;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.structure2.LOTRWorldGenStoneRuin;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.entity.animal.LOTREntityGemsbok;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.entity.animal.LOTREntityRhino;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import lotr.common.LOTRMod;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenHalfTrollForest extends LOTRBiomeGenFarHarad
{
    private WorldGenerator deadMoundGen;
    
    public LOTRBiomeGenHalfTrollForest(final int i, final boolean major) {
        super(i, major);
        this.deadMoundGen = new LOTRWorldGenBoulder(LOTRMod.wasteBlock, 0, 1, 2);
        super.spawnableCreatureList.clear();
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityRhino.class, 8, 4, 4));
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityGemsbok.class, 4, 4, 4));
        super.spawnableLOTRAmbientList.clear();
        super.npcSpawnList.clear();
        final LOTRBiomeSpawnList.FactionContainer factionList = super.npcSpawnList.newFactionList(100, 0.0f);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists = { null };
        final int n = 0;
        final LOTRBiomeSpawnList npcSpawnList = super.npcSpawnList;
        lists[n] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HALF_TROLLS, 10).setSpawnChance(1000);
        factionList.add(lists);
        final LOTRBiomeSpawnList.FactionContainer factionList2 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists2 = { null };
        final int n2 = 0;
        final LOTRBiomeSpawnList npcSpawnList2 = super.npcSpawnList;
        lists2[n2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HALF_TROLLS, 10);
        factionList2.add(lists2);
        final LOTRBiomeSpawnList.FactionContainer factionList3 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists3 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n3 = 0;
        final LOTRBiomeSpawnList npcSpawnList3 = super.npcSpawnList;
        lists3[n3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 10);
        final int n4 = 1;
        final LOTRBiomeSpawnList npcSpawnList4 = super.npcSpawnList;
        lists3[n4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 2).setConquestThreshold(50.0f);
        factionList3.add(lists3);
        final LOTRBiomeSpawnList.FactionContainer factionList4 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists4 = { null };
        final int n5 = 0;
        final LOTRBiomeSpawnList npcSpawnList5 = super.npcSpawnList;
        lists4[n5] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GULF_WARRIORS, 10);
        factionList4.add(lists4);
        final LOTRBiomeSpawnList.FactionContainer factionList5 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists5 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n6 = 0;
        final LOTRBiomeSpawnList npcSpawnList6 = super.npcSpawnList;
        lists5[n6] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORWAITH_WARRIORS, 10);
        final int n7 = 1;
        final LOTRBiomeSpawnList npcSpawnList7 = super.npcSpawnList;
        lists5[n7] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORWAITH, 5);
        factionList5.add(lists5);
        final LOTRBiomeSpawnList.FactionContainer factionList6 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists6 = { null };
        final int n8 = 0;
        final LOTRBiomeSpawnList npcSpawnList8 = super.npcSpawnList;
        lists6[n8] = LOTRBiomeSpawnList.entry(LOTRSpawnList.TAURETHRIM_WARRIORS, 10);
        factionList6.add(lists6);
        this.clearBiomeVariants();
        super.variantChance = 0.7f;
        this.addBiomeVariant(LOTRBiomeVariant.FLOWERS);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS_FOREST);
        this.addBiomeVariant(LOTRBiomeVariant.CLEARING);
        this.addBiomeVariant(LOTRBiomeVariant.WASTELAND);
        super.decorator.treesPerChunk = 3;
        super.decorator.vinesPerChunk = 4;
        super.decorator.logsPerChunk = 2;
        super.decorator.grassPerChunk = 10;
        super.decorator.doubleGrassPerChunk = 10;
        super.decorator.flowersPerChunk = 1;
        super.decorator.doubleFlowersPerChunk = 1;
        super.decorator.clearTrees();
        super.decorator.addTree(LOTRTreeType.ACACIA, 600);
        super.decorator.addTree(LOTRTreeType.OAK_DESERT, 200);
        super.decorator.addTree(LOTRTreeType.BAOBAB, 20);
        super.decorator.addTree(LOTRTreeType.ACACIA_DEAD, 300);
        super.decorator.addTree(LOTRTreeType.OAK_DEAD, 100);
        super.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.TAUREDAIN(1, 2), 500);
        super.biomeColors.setSky(12698028);
        super.biomeColors.setClouds(14869216);
        super.biomeColors.setFog(8885125);
        super.biomeColors.setFoggy(true);
        super.biomeColors.setWater(10923394);
        this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
        super.invasionSpawns.addInvasion(LOTRInvasions.HALF_TROLL, LOTREventSpawner.EventChance.UNCOMMON);
        super.invasionSpawns.addInvasion(LOTRInvasions.TAUREDAIN, LOTREventSpawner.EventChance.RARE);
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterHalfTrollForest;
    }
    
    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.PERTOROGWAITH_FOREST;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.PERTOROGWAITH.getSubregion("pertorogwaith");
    }
    
    @Override
    public void decorate(final World world, final Random random, final int i, final int k) {
        super.decorate(world, random, i, k);
        if (random.nextInt(40) == 0) {
            for (int l = 0; l < 3; ++l) {
                final int i2 = i + random.nextInt(16) + 8;
                final int k2 = k + random.nextInt(16) + 8;
                final int j1 = world.getHeightValue(i2, k2);
                this.deadMoundGen.generate(world, random, i2, j1, k2);
                new LOTRWorldGenSkullPile().generate(world, random, i2, j1, k2);
            }
        }
    }
    
    @Override
    public float getTreeIncreaseChance() {
        return 0.5f;
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return 0.05f;
    }
    
    @Override
    public boolean canSpawnHostilesInDay() {
        return true;
    }
}
