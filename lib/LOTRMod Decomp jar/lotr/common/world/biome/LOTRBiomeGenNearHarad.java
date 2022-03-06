// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.WorldGenDeadBush;
import net.minecraft.world.gen.feature.WorldGenCactus;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.LOTRAchievement;
import lotr.common.entity.npc.LOTREntityBandit;
import lotr.common.entity.npc.LOTREntityBanditHarad;
import lotr.common.world.spawning.LOTREventSpawner;
import lotr.common.world.village.LOTRVillageGen;
import lotr.common.world.village.LOTRVillageGenHaradNomad;
import lotr.common.world.structure2.LOTRWorldGenHaradRuinedFort;
import lotr.common.world.structure2.LOTRWorldGenMumakSkeleton;
import lotr.common.world.structure2.LOTRWorldGenStoneRuin;
import lotr.common.world.structure2.LOTRWorldGenHaradPyramid;
import lotr.common.world.structure.LOTRWorldGenHaradObelisk;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.world.gen.feature.WorldGenMinable;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.spawning.LOTRSpawnList;
import lotr.common.world.spawning.LOTRBiomeSpawnList;
import lotr.common.entity.animal.LOTREntityDesertScorpion;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.entity.animal.LOTREntityCamel;
import lotr.common.world.feature.LOTRWorldGenBoulder;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

public class LOTRBiomeGenNearHarad extends LOTRBiome
{
    private static NoiseGeneratorPerlin noiseAridGrass;
    private WorldGenerator boulderGen;
    private WorldGenerator boulderGenSandstone;
    
    public LOTRBiomeGenNearHarad(final int i, final boolean major) {
        super(i, major);
        this.boulderGen = new LOTRWorldGenBoulder(Blocks.stone, 0, 1, 3);
        this.boulderGenSandstone = new LOTRWorldGenBoulder(Blocks.sandstone, 0, 1, 3);
        this.setDisableRain();
        super.topBlock = (Block)Blocks.sand;
        super.fillerBlock = (Block)Blocks.sand;
        super.spawnableCreatureList.clear();
        super.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityCamel.class, 10, 2, 6));
        super.spawnableLOTRAmbientList.clear();
        super.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry((Class)LOTREntityDesertScorpion.class, 10, 4, 4));
        final LOTRBiomeSpawnList.FactionContainer factionList = super.npcSpawnList.newFactionList(100);
        final LOTRBiomeSpawnList.SpawnListContainer[] lists = new LOTRBiomeSpawnList.SpawnListContainer[2];
        final int n = 0;
        final LOTRBiomeSpawnList npcSpawnList = super.npcSpawnList;
        lists[n] = LOTRBiomeSpawnList.entry(LOTRSpawnList.NOMADS, 20).setSpawnChance(10000);
        final int n2 = 1;
        final LOTRBiomeSpawnList npcSpawnList2 = super.npcSpawnList;
        lists[n2] = LOTRBiomeSpawnList.entry(LOTRSpawnList.NOMAD_WARRIORS, 15).setSpawnChance(10000);
        factionList.add(lists);
        super.variantChance = 0.8f;
        this.addBiomeVariant(LOTRBiomeVariant.DUNES, 0.5f);
        this.addBiomeVariant(LOTRBiomeVariant.STEPPE);
        this.addBiomeVariant(LOTRBiomeVariant.HILLS);
        this.addBiomeVariant(LOTRBiomeVariant.BOULDERS_RED);
        this.addBiomeVariant(LOTRBiomeVariant.DEADFOREST_OAK);
        this.addBiomeVariant(LOTRBiomeVariant.SCRUBLAND_SAND);
        super.decorator.addOre((WorldGenerator)new WorldGenMinable(Blocks.lapis_ore, 6), 1.0f, 0, 48);
        super.decorator.grassPerChunk = 0;
        super.decorator.doubleGrassPerChunk = 0;
        super.decorator.cactiPerChunk = 0;
        super.decorator.deadBushPerChunk = 0;
        super.decorator.addTree(LOTRTreeType.OAK_DEAD, 800);
        super.decorator.addTree(LOTRTreeType.OAK_DESERT, 200);
        this.registerHaradFlowers();
        super.biomeColors.setFog(16180681);
        super.decorator.addRandomStructure(new LOTRWorldGenHaradObelisk(false), 3000);
        super.decorator.addRandomStructure(new LOTRWorldGenHaradPyramid(false), 3000);
        super.decorator.addRandomStructure(new LOTRWorldGenStoneRuin.NEAR_HARAD(1, 4), 2000);
        super.decorator.addRandomStructure(new LOTRWorldGenMumakSkeleton(false), 1500);
        super.decorator.addRandomStructure(new LOTRWorldGenHaradRuinedFort(false), 3000);
        super.decorator.addVillage(new LOTRVillageGenHaradNomad(this, 0.05f));
        this.clearTravellingTraders();
        this.setBanditChance(LOTREventSpawner.EventChance.BANDIT_RARE);
        this.setBanditEntityClass(LOTREntityBanditHarad.class);
    }
    
    @Override
    public LOTRAchievement getBiomeAchievement() {
        return LOTRAchievement.enterNearHarad;
    }
    
    @Override
    public LOTRWaypoint.Region getBiomeWaypoints() {
        return LOTRWaypoint.Region.HARAD_DESERT;
    }
    
    @Override
    public LOTRMusicRegion.Sub getBiomeMusic() {
        return LOTRMusicRegion.NEAR_HARAD.getSubregion("desert");
    }
    
    @Override
    public boolean getEnableRiver() {
        return false;
    }
    
    @Override
    public LOTRRoadType getRoadBlock() {
        return LOTRRoadType.HARAD.setRepair(0.5f);
    }
    
    @Override
    public void decorate(final World world, final Random random, final int i, final int k) {
        int grasses;
        final int preGrasses = grasses = super.decorator.grassPerChunk;
        final double d1 = LOTRBiomeGenNearHarad.noiseAridGrass.func_151601_a(i * 0.002, k * 0.002);
        if (d1 > 0.5) {
            ++grasses;
        }
        super.decorator.grassPerChunk = grasses;
        super.decorate(world, random, i, k);
        super.decorator.grassPerChunk = preGrasses;
        if (random.nextInt(50) == 0) {
            final int i2 = i + random.nextInt(16) + 8;
            final int k2 = k + random.nextInt(16) + 8;
            final int j1 = world.getHeightValue(i2, k2);
            new WorldGenCactus().generate(world, random, i2, j1, k2);
        }
        if (random.nextInt(16) == 0) {
            final int i2 = i + random.nextInt(16) + 8;
            final int k2 = k + random.nextInt(16) + 8;
            final int j1 = world.getHeightValue(i2, k2);
            new WorldGenDeadBush((Block)Blocks.deadbush).generate(world, random, i2, j1, k2);
        }
        if (random.nextInt(120) == 0) {
            for (int boulders = 1 + random.nextInt(4), l = 0; l < boulders; ++l) {
                final int i3 = i + random.nextInt(16) + 8;
                final int k3 = k + random.nextInt(16) + 8;
                final int j2 = world.getHeightValue(i3, k3);
                if (random.nextBoolean()) {
                    this.boulderGen.generate(world, random, i3, j2, k3);
                }
                else {
                    this.boulderGenSandstone.generate(world, random, i3, j2, k3);
                }
            }
        }
        if (random.nextInt(2000) == 0) {
            for (int trees = 1 + random.nextInt(4), l = 0; l < trees; ++l) {
                final int i3 = i + random.nextInt(8) + 8;
                final int k3 = k + random.nextInt(8) + 8;
                final int j2 = world.getHeightValue(i3, k3);
                super.decorator.genTree(world, random, i3, j2, k3);
            }
        }
    }
    
    @Override
    public float getTreeIncreaseChance() {
        return 5.0E-4f;
    }
    
    @Override
    public GrassBlockAndMeta getRandomGrass(final Random random) {
        return new GrassBlockAndMeta(LOTRMod.aridGrass, 0);
    }
    
    @Override
    public float getChanceToSpawnAnimals() {
        return 0.05f;
    }
    
    static {
        LOTRBiomeGenNearHarad.noiseAridGrass = new NoiseGeneratorPerlin(new Random(62926025827260L), 1);
    }
    
    public interface ImmuneToHeat
    {
    }
}
