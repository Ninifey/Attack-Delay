// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.LOTRAchievement;
import lotr.common.world.village.LOTRVillageGen;
import lotr.common.world.village.LOTRVillageGenGondor;
import lotr.common.world.structure2.LOTRWorldGenGondorStructure;
import lotr.common.world.structure2.LOTRWorldGenBlackrootWatchfort;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.LOTRMod;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenerator;

public class LOTRBiomeGenBlackrootVale extends LOTRBiomeGenGondor
{
    private WorldGenerator boulderGen;
    
    public LOTRBiomeGenBlackrootVale(final int i, final boolean major) {
        super(i, major);
        this.boulderGen = new LOTRWorldGenBoulder(Blocks.stone, 0, 1, 2);
        super.npcSpawnList.clear();
        final LOTRBiomeSpawnList.FactionContainer factionList = super.npcSpawnList.newFactionList(100, 0.0f);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists = { null };
        final int n = 0;
        final LOTRBiomeSpawnList npcSpawnList = super.npcSpawnList;
        lists[n] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACKROOT_SOLDIERS, 10).setSpawnChance(100);
        factionList.add(lists);
        final LOTRBiomeSpawnList.FactionContainer factionList2 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists2 = { null };
        final int n2 = 0;
        final LOTRBiomeSpawnList npcSpawnList2 = super.npcSpawnList;
        lists2[n2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACKROOT_SOLDIERS, 10);
        factionList2.add(lists2);
        final LOTRBiomeSpawnList.FactionContainer factionList3 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists3 = new LOTRBiomeSpawnList.SpawnListContainer[5];
        final int n3 = 0;
        final LOTRBiomeSpawnList npcSpawnList3 = super.npcSpawnList;
        lists3[n3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 20);
        final int n4 = 1;
        final LOTRBiomeSpawnList npcSpawnList4 = super.npcSpawnList;
        lists3[n4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_WARGS, 4).setConquestThreshold(50.0f);
        final int n5 = 2;
        final LOTRBiomeSpawnList npcSpawnList5 = super.npcSpawnList;
        lists3[n5] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 1).setConquestThreshold(50.0f);
        final int n6 = 3;
        final LOTRBiomeSpawnList npcSpawnList6 = super.npcSpawnList;
        lists3[n6] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 2).setConquestThreshold(100.0f);
        final int n7 = 4;
        final LOTRBiomeSpawnList npcSpawnList7 = super.npcSpawnList;
        lists3[n7] = LOTRBiomeSpawnList.entry(LOTRSpawnList.OLOG_HAI, 1).setConquestThreshold(200.0f);
        factionList3.add(lists3);
        final LOTRBiomeSpawnList.FactionContainer factionList4 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists4 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n8 = 0;
        final LOTRBiomeSpawnList npcSpawnList8 = super.npcSpawnList;
        lists4[n8] = LOTRBiomeSpawnList.entry(LOTRSpawnList.URUK_HAI, 10);
        final int n9 = 1;
        final LOTRBiomeSpawnList npcSpawnList9 = super.npcSpawnList;
        lists4[n9] = LOTRBiomeSpawnList.entry(LOTRSpawnList.URUK_WARGS, 2).setConquestThreshold(50.0f);
        factionList4.add(lists4);
        final LOTRBiomeSpawnList.FactionContainer factionList5 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists5 = new LOTRBiomeSpawnList.SpawnListContainer[4];
        final int n10 = 0;
        final LOTRBiomeSpawnList npcSpawnList10 = super.npcSpawnList;
        lists5[n10] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HARNEDHRIM, 2).setConquestThreshold(100.0f);
        final int n11 = 1;
        final LOTRBiomeSpawnList npcSpawnList11 = super.npcSpawnList;
        lists5[n11] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HARNEDOR_WARRIORS, 10);
        final int n12 = 2;
        final LOTRBiomeSpawnList npcSpawnList12 = super.npcSpawnList;
        lists5[n12] = LOTRBiomeSpawnList.entry(LOTRSpawnList.COAST_SOUTHRONS, 2).setConquestThreshold(100.0f);
        final int n13 = 3;
        final LOTRBiomeSpawnList npcSpawnList13 = super.npcSpawnList;
        lists5[n13] = LOTRBiomeSpawnList.entry(LOTRSpawnList.SOUTHRON_WARRIORS, 10);
        factionList5.add(lists5);
        final LOTRBiomeSpawnList.FactionContainer factionList6 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists6 = new LOTRBiomeSpawnList.SpawnListContainer[3];
        final int n14 = 0;
        final LOTRBiomeSpawnList npcSpawnList14 = super.npcSpawnList;
        lists6[n14] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLINGS, 2).setConquestThreshold(100.0f);
        final int n15 = 1;
        final LOTRBiomeSpawnList npcSpawnList15 = super.npcSpawnList;
        lists6[n15] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLING_WARRIORS, 10);
        final int n16 = 2;
        final LOTRBiomeSpawnList npcSpawnList16 = super.npcSpawnList;
        lists6[n16] = LOTRBiomeSpawnList.entry(LOTRSpawnList.EASTERLING_GOLD_WARRIORS, 2).setConquestThreshold(50.0f);
        factionList6.add(lists6);
        final LOTRBiomeSpawnList.FactionContainer factionList7 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists7 = { null };
        final int n17 = 0;
        final LOTRBiomeSpawnList npcSpawnList17 = super.npcSpawnList;
        lists7[n17] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HALF_TROLLS, 10);
        factionList7.add(lists7);
        this.clearBiomeVariants();
        super.variantChance = 0.3f;
        this.addBiomeVariant(LOTRBiomeVariant.FLOWERS);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS_FOREST);
        this.addBiomeVariant(LOTRBiomeVariant.DENSEFOREST_OAK);
        this.addBiomeVariant(LOTRBiomeVariant.DEADFOREST_OAK);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_BIRCH, 0.5f);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LARCH, 0.5f);
        this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_APPLE_PEAR, 0.5f);
        this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_PLUM, 0.5f);
        super.decorator.setTreeCluster(8, 20);
        super.decorator.treesPerChunk = 0;
        super.decorator.flowersPerChunk = 8;
        super.decorator.doubleFlowersPerChunk = 2;
        super.decorator.grassPerChunk = 12;
        super.decorator.doubleGrassPerChunk = 3;
        super.decorator.clearTrees();
        super.decorator.addTree(LOTRTreeType.OAK, 500);
        super.decorator.addTree(LOTRTreeType.OAK_LARGE, 100);
        super.decorator.addTree(LOTRTreeType.DARK_OAK, 500);
        super.decorator.addTree(LOTRTreeType.FIR, 300);
        super.decorator.addTree(LOTRTreeType.LARCH, 300);
        super.decorator.addTree(LOTRTreeType.ASPEN, 100);
        super.decorator.addTree(LOTRTreeType.BIRCH, 50);
        super.decorator.addTree(LOTRTreeType.BIRCH_LARGE, 10);
        super.decorator.addTree(LOTRTreeType.CHESTNUT, 200);
        super.decorator.addTree(LOTRTreeType.CHESTNUT_LARGE, 50);
        super.decorator.addTree(LOTRTreeType.APPLE, 5);
        super.decorator.addTree(LOTRTreeType.PEAR, 5);
        super.decorator.addTree(LOTRTreeType.PLUM, 5);
        super.decorator.addTree(LOTRTreeType.OLIVE, 1);
        super.decorator.addTree(LOTRTreeType.ALMOND, 1);
        this.addFlower(LOTRMod.blackroot, 0, 60);
        super.biomeColors.setGrass(7704878);
        this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
    }
    
    @Override
    protected void addFiefdomStructures() {
        super.decorator.addRandomStructure(new LOTRWorldGenBlackrootWatchfort(false), 1000);
        super.decorator.addVillage(new LOTRVillageGenGondor(this, LOTRWorldGenGondorStructure.GondorFiefdom.BLACKROOT_VALE, 0.6f));
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterBlackrootVale;
    }
    
    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.BLACKROOT_VALE;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.GONDOR.getSubregion("blackroot");
    }
    
    @Override
    public void decorate(final World world, final Random random, final int i, final int k) {
        super.decorate(world, random, i, k);
        if (random.nextInt(30) == 0) {
            for (int l = 0; l < 5; ++l) {
                final int i2 = i + random.nextInt(16) + 8;
                final int k2 = k + random.nextInt(16) + 8;
                this.boulderGen.generate(world, random, i2, world.getHeightValue(i2, k2), k2);
            }
        }
    }
    
    @Override
    public float getTreeIncreaseChance() {
        return 0.2f;
    }
}
