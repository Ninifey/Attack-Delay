// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.feature.LOTRWorldGenEntJars;
import lotr.common.world.feature.LOTRWorldGenWaterPlant;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.LOTRAchievement;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.entity.npc.LOTREntityGaladhrimTrader;
import lotr.common.LOTRMod;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.entity.animal.LOTREntityCrebain;
import lotr.common.entity.animal.LOTREntityBear;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.entity.animal.LOTREntityDeer;

public class LOTRBiomeGenFangorn extends LOTRBiome
{
    private boolean isBirchFangorn;
    
    public LOTRBiomeGenFangorn(final int i, final boolean major) {
        super(i, major);
        this.isBirchFangorn = false;
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityDeer.class, 30, 4, 6));
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityBear.class, 4, 1, 4));
        super.spawnableLOTRAmbientList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityCrebain.class, 6, 4, 4));
        final LOTRBiomeSpawnList.FactionContainer factionList = super.npcSpawnList.newFactionList(100);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n = 0;
        final LOTRBiomeSpawnList npcSpawnList = super.npcSpawnList;
        lists[n] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ENTS, 10);
        final int n2 = 1;
        final LOTRBiomeSpawnList npcSpawnList2 = super.npcSpawnList;
        lists[n2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HUORNS, 20);
        factionList.add(lists);
        final LOTRBiomeSpawnList.FactionContainer factionList2 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists2 = new LOTRBiomeSpawnList.SpawnListContainer[3];
        final int n3 = 0;
        final LOTRBiomeSpawnList npcSpawnList3 = super.npcSpawnList;
        lists2[n3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_ORCS, 10);
        final int n4 = 1;
        final LOTRBiomeSpawnList npcSpawnList4 = super.npcSpawnList;
        lists2[n4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_WARGS, 2);
        final int n5 = 2;
        final LOTRBiomeSpawnList npcSpawnList5 = super.npcSpawnList;
        lists2[n5] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_URUKS, 2).setConquestThreshold(50.0f);
        factionList2.add(lists2);
        final LOTRBiomeSpawnList.FactionContainer factionList3 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists3 = new LOTRBiomeSpawnList.SpawnListContainer[3];
        final int n6 = 0;
        final LOTRBiomeSpawnList npcSpawnList6 = super.npcSpawnList;
        lists3[n6] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DOL_GULDUR_ORCS, 10);
        final int n7 = 1;
        final LOTRBiomeSpawnList npcSpawnList7 = super.npcSpawnList;
        lists3[n7] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MIRKWOOD_SPIDERS, 2);
        final int n8 = 2;
        final LOTRBiomeSpawnList npcSpawnList8 = super.npcSpawnList;
        lists3[n8] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MIRK_TROLLS, 1).setConquestThreshold(200.0f);
        factionList3.add(lists3);
        final LOTRBiomeSpawnList.FactionContainer factionList4 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists4 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n9 = 0;
        final LOTRBiomeSpawnList npcSpawnList9 = super.npcSpawnList;
        lists4[n9] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DUNLENDINGS, 10);
        final int n10 = 1;
        final LOTRBiomeSpawnList npcSpawnList10 = super.npcSpawnList;
        lists4[n10] = LOTRBiomeSpawnList.entry(LOTRSpawnList.DUNLENDING_WARRIORS, 10);
        factionList4.add(lists4);
        final LOTRBiomeSpawnList.FactionContainer factionList5 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists5 = new LOTRBiomeSpawnList.SpawnListContainer[3];
        final int n11 = 0;
        final LOTRBiomeSpawnList npcSpawnList11 = super.npcSpawnList;
        lists5[n11] = LOTRBiomeSpawnList.entry(LOTRSpawnList.URUK_HAI, 10);
        final int n12 = 1;
        final LOTRBiomeSpawnList npcSpawnList12 = super.npcSpawnList;
        lists5[n12] = LOTRBiomeSpawnList.entry(LOTRSpawnList.URUK_WARGS, 2);
        final int n13 = 2;
        final LOTRBiomeSpawnList npcSpawnList13 = super.npcSpawnList;
        lists5[n13] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ISENGARD_SNAGA, 5);
        factionList5.add(lists5);
        final LOTRBiomeSpawnList.FactionContainer factionList6 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists6 = new LOTRBiomeSpawnList.SpawnListContainer[4];
        final int n14 = 0;
        final LOTRBiomeSpawnList npcSpawnList14 = super.npcSpawnList;
        lists6[n14] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 10);
        final int n15 = 1;
        final LOTRBiomeSpawnList npcSpawnList15 = super.npcSpawnList;
        lists6[n15] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_WARGS, 2);
        final int n16 = 2;
        final LOTRBiomeSpawnList npcSpawnList16 = super.npcSpawnList;
        lists6[n16] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 2).setConquestThreshold(50.0f);
        final int n17 = 3;
        final LOTRBiomeSpawnList npcSpawnList17 = super.npcSpawnList;
        lists6[n17] = LOTRBiomeSpawnList.entry(LOTRSpawnList.OLOG_HAI, 1).setConquestThreshold(200.0f);
        factionList6.add(lists6);
        super.npcSpawnList.conquestGainRate = 0.2f;
        this.addBiomeVariant(LOTRBiomeVariant.FLOWERS);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
        super.decorator.treesPerChunk = 12;
        super.decorator.willowPerChunk = 3;
        super.decorator.logsPerChunk = 5;
        super.decorator.flowersPerChunk = 6;
        super.decorator.doubleFlowersPerChunk = 1;
        super.decorator.grassPerChunk = 12;
        super.decorator.doubleGrassPerChunk = 6;
        super.decorator.enableFern = true;
        super.decorator.addTree(LOTRTreeType.DARK_OAK, 400);
        super.decorator.addTree(LOTRTreeType.OAK, 100);
        super.decorator.addTree(LOTRTreeType.OAK_TALL, 200);
        super.decorator.addTree(LOTRTreeType.OAK_TALLER, 200);
        super.decorator.addTree(LOTRTreeType.OAK_LARGE, 100);
        super.decorator.addTree(LOTRTreeType.BIRCH, 20);
        super.decorator.addTree(LOTRTreeType.BIRCH_TALL, 20);
        super.decorator.addTree(LOTRTreeType.BIRCH_LARGE, 10);
        super.decorator.addTree(LOTRTreeType.BEECH, 20);
        super.decorator.addTree(LOTRTreeType.BEECH_LARGE, 10);
        super.decorator.addTree(LOTRTreeType.OAK_FANGORN, 50);
        super.decorator.addTree(LOTRTreeType.BEECH_FANGORN, 20);
        super.decorator.addTree(LOTRTreeType.ASPEN, 50);
        super.decorator.addTree(LOTRTreeType.ASPEN_LARGE, 10);
        this.registerForestFlowers();
        this.addFlower(LOTRMod.fangornPlant, 0, 1);
        this.addFlower(LOTRMod.fangornPlant, 1, 1);
        this.addFlower(LOTRMod.fangornPlant, 2, 1);
        this.addFlower(LOTRMod.fangornPlant, 3, 1);
        this.addFlower(LOTRMod.fangornPlant, 4, 1);
        this.addFlower(LOTRMod.fangornPlant, 5, 1);
        super.biomeColors.setSky(7774322);
        super.biomeColors.setFog(3308875);
        super.biomeColors.setFoggy(true);
        this.registerTravellingTrader(LOTREntityGaladhrimTrader.class);
        this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
    }
    
    public LOTRBiomeGenFangorn setBirchFangorn() {
        this.isBirchFangorn = true;
        super.decorator.addTree(LOTRTreeType.BIRCH, 2000);
        super.decorator.addTree(LOTRTreeType.BIRCH_TALL, 2000);
        super.decorator.addTree(LOTRTreeType.BIRCH_LARGE, 2000);
        super.decorator.addTree(LOTRTreeType.BIRCH_FANGORN, 1000);
        return this;
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterFangorn;
    }
    
    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.FANGORN;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.FANGORN.getSubregion("fangorn");
    }
    
    @Override
    public void decorate(final World world, final Random random, final int i, final int k) {
        super.decorate(world, random, i, k);
        if (random.nextInt(2) == 0) {
            int i2;
            int k2;
            int j1;
            for (i2 = i + random.nextInt(16) + 8, k2 = k + random.nextInt(16) + 8, j1 = 64 + random.nextInt(64); j1 > 0 && world.getBlock(i2, j1 - 1, k2) == Blocks.air; --j1) {}
            new LOTRWorldGenWaterPlant(LOTRMod.fangornRiverweed).generate(world, random, i2, j1, k2);
        }
        if (random.nextInt(10) == 0) {
            final int i2 = i + random.nextInt(16) + 8;
            final int k2 = k + random.nextInt(16) + 8;
            final int j1 = world.getHeightValue(i2, k2);
            new LOTRWorldGenEntJars().generate(world, random, i2, j1, k2);
        }
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return 0.25f;
    }
    
    @Override
    public int spawnCountMultiplier() {
        return 3;
    }
}
