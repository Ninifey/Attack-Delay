// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import net.minecraft.world.gen.feature.WorldGenDoublePlant;
import lotr.common.world.feature.LOTRWorldGenYams;
import lotr.common.LOTRAchievement;
import java.util.Random;
import net.minecraft.world.World;
import java.util.List;
import lotr.common.world.spawning.LOTRInvasions;
import lotr.common.entity.npc.LOTREntityBandit;
import lotr.common.entity.npc.LOTREntityBanditHarad;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.entity.npc.LOTREntityNomadMerchant;
import lotr.common.world.structure2.LOTRWorldGenStoneRuin;
import lotr.common.world.structure2.LOTRWorldGenMoredainCamp;
import lotr.common.world.structure2.LOTRWorldGenMoredainVillage;
import lotr.common.world.feature.LOTRWorldGenSand;
import net.minecraft.world.gen.feature.WorldGenMinable;
import lotr.common.LOTRMod;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenerator;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

public class LOTRBiomeGenFarHaradSavannah extends LOTRBiomeGenFarHarad
{
    private static NoiseGeneratorPerlin populatedNoise;
    protected LOTRBiomeSpawnList populatedSpawnList;
    private WorldGenerator boulderGen;
    
    public LOTRBiomeGenFarHaradSavannah(final int i, final boolean major) {
        super(i, major);
        this.populatedSpawnList = new LOTRBiomeSpawnList(this);
        this.boulderGen = new LOTRWorldGenBoulder(Blocks.stone, 0, 1, 3);
        super.npcSpawnList.clear();
        final LOTRBiomeSpawnList.FactionContainer factionList = super.npcSpawnList.newFactionList(100, 0.0f);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n = 0;
        final LOTRBiomeSpawnList npcSpawnList = super.npcSpawnList;
        lists[n] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORWAITH, 10).setSpawnChance(10000);
        final int n2 = 1;
        final LOTRBiomeSpawnList npcSpawnList2 = super.npcSpawnList;
        lists[n2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORWAITH_WARRIORS, 5).setSpawnChance(10000);
        factionList.add(lists);
        final LOTRBiomeSpawnList.FactionContainer factionList2 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists2 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n3 = 0;
        final LOTRBiomeSpawnList npcSpawnList3 = super.npcSpawnList;
        lists2[n3] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORWAITH, 5);
        final int n4 = 1;
        final LOTRBiomeSpawnList npcSpawnList4 = super.npcSpawnList;
        lists2[n4] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORWAITH_WARRIORS, 10);
        factionList2.add(lists2);
        final LOTRBiomeSpawnList.FactionContainer factionList3 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists3 = { null };
        final int n5 = 0;
        final LOTRBiomeSpawnList npcSpawnList5 = super.npcSpawnList;
        lists3[n5] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GONDOR_SOLDIERS, 10);
        factionList3.add(lists3);
        final LOTRBiomeSpawnList.FactionContainer factionList4 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists4 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n6 = 0;
        final LOTRBiomeSpawnList npcSpawnList6 = super.npcSpawnList;
        lists4[n6] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORDOR_ORCS, 10);
        final int n7 = 1;
        final LOTRBiomeSpawnList npcSpawnList7 = super.npcSpawnList;
        lists4[n7] = LOTRBiomeSpawnList.entry(LOTRSpawnList.BLACK_URUKS, 1).setConquestThreshold(50.0f);
        factionList4.add(lists4);
        final LOTRBiomeSpawnList.FactionContainer factionList5 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists5 = new LOTRBiomeSpawnList.SpawnListContainer[3];
        final int n8 = 0;
        final LOTRBiomeSpawnList npcSpawnList8 = super.npcSpawnList;
        lists5[n8] = LOTRBiomeSpawnList.entry(LOTRSpawnList.SOUTHRON_WARRIORS, 10);
        final int n9 = 1;
        final LOTRBiomeSpawnList npcSpawnList9 = super.npcSpawnList;
        lists5[n9] = LOTRBiomeSpawnList.entry(LOTRSpawnList.NOMAD_WARRIORS, 5);
        final int n10 = 2;
        final LOTRBiomeSpawnList npcSpawnList10 = super.npcSpawnList;
        lists5[n10] = LOTRBiomeSpawnList.entry(LOTRSpawnList.GULF_WARRIORS, 10);
        factionList5.add(lists5);
        final LOTRBiomeSpawnList.FactionContainer factionList6 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists6 = new LOTRBiomeSpawnList.SpawnListContainer[4];
        final int n11 = 0;
        final LOTRBiomeSpawnList npcSpawnList11 = super.npcSpawnList;
        lists6[n11] = LOTRBiomeSpawnList.entry(LOTRSpawnList.TAURETHRIM_WARRIORS, 10);
        final int n12 = 1;
        final LOTRBiomeSpawnList npcSpawnList12 = super.npcSpawnList;
        lists6[n12] = LOTRBiomeSpawnList.entry(LOTRSpawnList.TAURETHRIM, 5);
        final int n13 = 2;
        final LOTRBiomeSpawnList npcSpawnList13 = super.npcSpawnList;
        lists6[n13] = LOTRBiomeSpawnList.entry(LOTRSpawnList.TAURETHRIM, 5).setConquestThreshold(100.0f);
        final int n14 = 3;
        final LOTRBiomeSpawnList npcSpawnList14 = super.npcSpawnList;
        lists6[n14] = LOTRBiomeSpawnList.entry(LOTRSpawnList.TAURETHRIM, 5).setConquestThreshold(200.0f);
        factionList6.add(lists6);
        final LOTRBiomeSpawnList.FactionContainer factionList7 = super.npcSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists7 = { null };
        final int n15 = 0;
        final LOTRBiomeSpawnList npcSpawnList15 = super.npcSpawnList;
        lists7[n15] = LOTRBiomeSpawnList.entry(LOTRSpawnList.HALF_TROLLS, 10);
        factionList7.add(lists7);
        final LOTRBiomeSpawnList.FactionContainer factionList8 = this.populatedSpawnList.newFactionList(0);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists8 = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n16 = 0;
        final LOTRBiomeSpawnList npcSpawnList16 = super.npcSpawnList;
        lists8[n16] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORWAITH, 10);
        final int n17 = 1;
        final LOTRBiomeSpawnList npcSpawnList17 = super.npcSpawnList;
        lists8[n17] = LOTRBiomeSpawnList.entry(LOTRSpawnList.MORWAITH_WARRIORS, 5);
        factionList8.add(lists8);
        super.variantChance = 0.3f;
        this.addBiomeVariant(LOTRBiomeVariant.FLOWERS);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST);
        this.addBiomeVariant(LOTRBiomeVariant.FOREST_LIGHT);
        this.addBiomeVariant(LOTRBiomeVariant.STEPPE);
        this.addBiomeVariant(LOTRBiomeVariant.STEPPE_BARREN);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS_FOREST);
        this.addBiomeVariant(LOTRBiomeVariant.SHRUBLAND_OAK);
        this.addBiomeVariant(LOTRBiomeVariant.SAVANNAH_BAOBAB, 3.0f);
        this.addBiomeVariant(LOTRBiomeVariant.SCRUBLAND, 2.0f);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS_SCRUBLAND);
        this.addBiomeVariant(LOTRBiomeVariant.WASTELAND);
        super.decorator.addSoil((WorldGenerator)new WorldGenMinable(LOTRMod.redClay, 32, Blocks.dirt), 40.0f, 0, 80);
        super.decorator.setTreeCluster(3, 60);
        super.decorator.clayGen = new LOTRWorldGenSand(LOTRMod.redClay, 5, 1);
        super.decorator.clayPerChunk = 4;
        super.decorator.grassPerChunk = 10;
        super.decorator.doubleGrassPerChunk = 12;
        super.decorator.flowersPerChunk = 3;
        super.decorator.doubleFlowersPerChunk = 1;
        super.decorator.melonPerChunk = 0.01f;
        super.decorator.addRandomStructure(new LOTRWorldGenMoredainVillage(false), 200);
        super.decorator.addRandomStructure(new LOTRWorldGenMoredainCamp(false), 500);
        super.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.TAUREDAIN(1, 2), 5000);
        this.registerTravellingTrader(LOTREntityNomadMerchant.class);
        this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
        this.setBanditEntityClass(LOTREntityBanditHarad.class);
        super.invasionSpawns.addInvasion(LOTRInvasions.MOREDAIN, LOTREventSpawner.EventChance.UNCOMMON);
        super.invasionSpawns.addInvasion(LOTRInvasions.TAUREDAIN, LOTREventSpawner.EventChance.RARE);
    }
    
    @Override
    public void addBiomeF3Info(final List info, final World world, final LOTRBiomeVariant variant, final int i, final int j, final int k) {
        super.addBiomeF3Info(info, world, variant, i, j, k);
        final boolean populated = isBiomePopulated(i, j, k);
        info.add("HaradPopulated: " + populated);
    }
    
    public static boolean isBiomePopulated(final int i, final int j, final int k) {
        final double scale = 8.0E-4;
        final double d = LOTRBiomeGenFarHaradSavannah.populatedNoise.func_151601_a(i * scale, k * scale);
        return d > 0.5;
    }
    
    @Override
    public LOTRBiomeSpawnList getNPCSpawnList(final World world, final Random random, final int i, final int j, final int k, final LOTRBiomeVariant variant) {
        if (isBiomePopulated(i, j, k)) {
            return this.populatedSpawnList;
        }
        return super.getNPCSpawnList(world, random, i, j, k, variant);
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterFarHaradSavannah;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.FAR_HARAD.getSubregion("savannah");
    }
    
    @Override
    public void decorate(final World world, final Random random, final int i, final int k) {
        super.decorate(world, random, i, k);
        if (random.nextInt(32) == 0) {
            for (int boulders = 1 + random.nextInt(4), l = 0; l < boulders; ++l) {
                final int i2 = i + random.nextInt(16) + 8;
                final int k2 = k + random.nextInt(16) + 8;
                this.boulderGen.generate(world, random, i2, world.getHeightValue(i2, k2), k2);
            }
        }
        if (random.nextInt(6) == 0) {
            final int i3 = i + random.nextInt(16) + 8;
            final int j1 = random.nextInt(128);
            final int k3 = k + random.nextInt(16) + 8;
            new LOTRWorldGenYams().generate(world, random, i3, j1, k3);
        }
    }
    
    @Override
    public float getTreeIncreaseChance() {
        return 0.1f;
    }
    
    @Override
    public WorldGenerator getRandomWorldGenForDoubleFlower(final Random random) {
        if (random.nextInt(6) == 0) {
            final WorldGenDoublePlant gen = new WorldGenDoublePlant();
            gen.func_150548_a(0);
            return (WorldGenerator)gen;
        }
        return super.getRandomWorldGenForDoubleFlower(random);
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return 0.75f;
    }
    
    @Override
    public int spawnCountMultiplier() {
        return 3;
    }
    
    static {
        LOTRBiomeGenFarHaradSavannah.populatedNoise = new NoiseGeneratorPerlin(new Random(100L), 1);
    }
}
