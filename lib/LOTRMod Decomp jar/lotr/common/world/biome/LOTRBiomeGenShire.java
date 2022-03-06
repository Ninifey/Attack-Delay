// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import net.minecraft.world.gen.feature.WorldGenDoublePlant;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import lotr.common.LOTRMod;
import lotr.common.world.feature.LOTRWorldGenClover;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.biome.variant.LOTRBiomeVariantOrchard;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.entity.npc.LOTREntityRivendellTrader;
import lotr.common.entity.npc.LOTREntityDaleMerchant;
import lotr.common.entity.npc.LOTREntityScrapTrader;
import lotr.common.entity.npc.LOTREntityIronHillsMerchant;
import lotr.common.entity.npc.LOTREntityBlueDwarfMerchant;
import lotr.common.entity.npc.LOTREntityGaladhrimTrader;
import lotr.common.world.structure2.LOTRWorldGenStoneRuin;
import lotr.common.world.structure.LOTRWorldGenHobbitPicnicBench;
import lotr.common.world.structure2.LOTRWorldGenHobbitFarm;
import lotr.common.world.structure2.LOTRWorldGenHobbitWindmill;
import lotr.common.world.structure2.LOTRWorldGenHobbitTavern;
import lotr.common.world.structure2.LOTRWorldGenHobbitHouse;
import net.minecraft.world.gen.feature.WorldGenerator;
import lotr.common.world.structure2.LOTRWorldGenHobbitHole;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.spawning.LOTRSpawnList;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.entity.animal.LOTREntityShirePony;
import lotr.common.world.spawning.LOTRBiomeSpawnList;

public class LOTRBiomeGenShire extends LOTRBiome
{
    private LOTRBiomeSpawnList orcharderSpawnList;
    private static final float orcharderSpawnWeight = 0.3f;
    
    public LOTRBiomeGenShire(final int i, final boolean major) {
        super(i, major);
        this.orcharderSpawnList = new LOTRBiomeSpawnList(this);
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityShirePony.class, 12, 2, 6));
        final LOTRBiomeSpawnList.FactionContainer factionList = super.npcSpawnList.newFactionList(100);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists = { null };
        final int n = 0;
        final LOTRBiomeSpawnList npcSpawnList = super.npcSpawnList;
        lists[n] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HOBBITS, 10);
        factionList.add(lists);
        final LOTRBiomeSpawnList.FactionContainer factionList2 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists2 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n2 = 0;
        final LOTRBiomeSpawnList npcSpawnList2 = super.npcSpawnList;
        lists2[n2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_ORCS, 10);
        final int n3 = 1;
        final LOTRBiomeSpawnList npcSpawnList3 = super.npcSpawnList;
        lists2[n3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GUNDABAD_WARGS, 3).setConquestThreshold(100.0f);
        factionList2.add(lists2);
        final LOTRBiomeSpawnList.FactionContainer factionList3 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists3 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n4 = 0;
        final LOTRBiomeSpawnList npcSpawnList4 = super.npcSpawnList;
        lists3[n4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_ORCS, 10);
        final int n5 = 1;
        final LOTRBiomeSpawnList npcSpawnList5 = super.npcSpawnList;
        lists3[n5] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ANGMAR_WARGS, 3).setConquestThreshold(100.0f);
        factionList3.add(lists3);
        final LOTRBiomeSpawnList.FactionContainer factionList4 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists4 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n6 = 0;
        final LOTRBiomeSpawnList npcSpawnList6 = super.npcSpawnList;
        lists4[n6] = LOTRBiomeSpawnList.entry(LOTRSpawnList.ISENGARD_SNAGA, 10);
        final int n7 = 1;
        final LOTRBiomeSpawnList npcSpawnList7 = super.npcSpawnList;
        lists4[n7] = LOTRBiomeSpawnList.entry(LOTRSpawnList.URUK_HAI, 5);
        factionList4.add(lists4);
        super.npcSpawnList.conquestGainRate = 0.2f;
        final LOTRBiomeSpawnList.FactionContainer factionList5 = this.orcharderSpawnList.newFactionList(100);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists5 = { null };
        final int n8 = 0;
        final LOTRBiomeSpawnList npcSpawnList8 = super.npcSpawnList;
        lists5[n8] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HOBBITS_ORCHARD);
        factionList5.add(lists5);
        super.variantChance = 0.25f;
        this.addBiomeVariant(LOTRBiomeVariant.FLOWERS);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS_FOREST);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_BIRCH, 0.5f);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_ASPEN, 0.5f);
        this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_SHIRE, 1.0f);
        this.addBiomeVariant(LOTRBiomeVariant.ORCHARD_PLUM, 0.3f);
        super.decorator.willowPerChunk = 1;
        super.decorator.flowersPerChunk = 3;
        super.decorator.doubleFlowersPerChunk = 1;
        super.decorator.grassPerChunk = 6;
        super.decorator.generateLava = false;
        super.decorator.addTree(LOTRTreeType.OAK, 1000);
        super.decorator.addTree(LOTRTreeType.OAK_LARGE, 400);
        super.decorator.addTree(LOTRTreeType.OAK_PARTY, 10);
        super.decorator.addTree(LOTRTreeType.CHESTNUT, 250);
        super.decorator.addTree(LOTRTreeType.CHESTNUT_LARGE, 100);
        super.decorator.addTree(LOTRTreeType.BIRCH, 25);
        super.decorator.addTree(LOTRTreeType.BIRCH_LARGE, 10);
        super.decorator.addTree(LOTRTreeType.ASPEN, 50);
        super.decorator.addTree(LOTRTreeType.ASPEN_LARGE, 10);
        super.decorator.addTree(LOTRTreeType.APPLE, 5);
        super.decorator.addTree(LOTRTreeType.PEAR, 5);
        super.decorator.addTree(LOTRTreeType.CHERRY, 2);
        super.decorator.addTree(LOTRTreeType.PLUM, 5);
        this.registerPlainsFlowers();
        super.biomeColors.setGrass(8111137);
        if (this.hasShireStructures()) {
            if (this.getClass() == LOTRBiomeGenShire.class) {
                super.decorator.addRandomStructure(new LOTRWorldGenHobbitHole(false), 30);
                super.decorator.addRandomStructure(new LOTRWorldGenHobbitHouse(false), 45);
                super.decorator.addRandomStructure(new LOTRWorldGenHobbitTavern(false), 70);
                super.decorator.addRandomStructure(new LOTRWorldGenHobbitWindmill(false), 600);
                super.decorator.addRandomStructure(new LOTRWorldGenHobbitFarm(false), 500);
            }
            super.decorator.addRandomStructure(new LOTRWorldGenHobbitPicnicBench(false), 40);
            super.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.STONE(1, 4), 1500);
            super.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.ARNOR(1, 4), 1500);
        }
        this.registerTravellingTrader(LOTREntityGaladhrimTrader.class);
        this.registerTravellingTrader(LOTREntityBlueDwarfMerchant.class);
        this.registerTravellingTrader(LOTREntityIronHillsMerchant.class);
        this.registerTravellingTrader(LOTREntityScrapTrader.class);
        this.registerTravellingTrader(LOTREntityDaleMerchant.class);
        this.registerTravellingTrader(LOTREntityRivendellTrader.class);
        this.setBanditChance(LOTREventSpawner.EventChance.NEVER);
    }
    
    @Override
    public LOTRBiomeSpawnList getNPCSpawnList(final World world, final Random random, final int i, final int j, final int k, final LOTRBiomeVariant variant) {
        if (variant instanceof LOTRBiomeVariantOrchard && random.nextFloat() < 0.3f) {
            return this.orcharderSpawnList;
        }
        return super.getNPCSpawnList(world, random, i, j, k, variant);
    }
    
    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.SHIRE;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.SHIRE.getSubregion("shire");
    }
    
    public boolean hasDomesticAnimals() {
        return true;
    }
    
    public boolean hasShireStructures() {
        return true;
    }
    
    @Override
    public void decorate(final World world, final Random random, final int i, final int k) {
        super.decorate(world, random, i, k);
        for (int l = 0; l < super.decorator.grassPerChunk / 2; ++l) {
            final int i2 = i + random.nextInt(16) + 8;
            final int j1 = random.nextInt(128);
            final int k2 = k + random.nextInt(16) + 8;
            new LOTRWorldGenClover().generate(world, random, i2, j1, k2);
        }
        if (random.nextInt(6) == 0) {
            final int i3 = i + random.nextInt(16) + 8;
            final int j2 = random.nextInt(128);
            final int k3 = k + random.nextInt(16) + 8;
            new WorldGenFlowers(LOTRMod.pipeweedPlant).generate(world, random, i3, j2, k3);
        }
        if (super.decorator.doubleFlowersPerChunk > 0 && random.nextInt(6) == 0) {
            final int i3 = i + random.nextInt(16) + 8;
            final int j2 = random.nextInt(128);
            final int k3 = k + random.nextInt(16) + 8;
            final WorldGenDoublePlant doubleFlowerGen = new WorldGenDoublePlant();
            doubleFlowerGen.func_150548_a(0);
            doubleFlowerGen.generate(world, random, i3, j2, k3);
        }
    }
    
    @Override
    public float getTreeIncreaseChance() {
        return 0.5f;
    }
    
    @Override
    public int spawnCountMultiplier() {
        return 3;
    }
}
