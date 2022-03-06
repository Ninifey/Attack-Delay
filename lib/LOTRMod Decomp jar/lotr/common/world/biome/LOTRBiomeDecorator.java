// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.biome;

import lotr.common.world.structure2.LOTRWorldGenStructureBase2;
import lotr.common.world.structure.LOTRWorldGenStructureBase;
import lotr.common.world.feature.LOTRWorldGenStreams;
import lotr.common.world.feature.LOTRWorldGenBerryBush;
import net.minecraft.util.MathHelper;
import net.minecraft.world.gen.feature.WorldGenDeadBush;
import lotr.common.world.feature.LOTRWorldGenBushes;
import lotr.common.world.feature.LOTRWorldGenFallenLeaves;
import lotr.common.world.structure2.LOTRWorldGenTicketBooth;
import lotr.common.world.structure2.LOTRWorldGenGrukHouse;
import lotr.common.world.structure.LOTRWorldGenMarshHut;
import lotr.common.world.map.LOTRRoads;
import lotr.common.world.LOTRWorldChunkManager;
import java.util.Iterator;
import lotr.common.world.LOTRChunkProvider;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import java.util.Collection;
import net.minecraft.util.WeightedRandom;
import lotr.common.world.feature.LOTRWorldGenTrollHoard;
import lotr.common.world.structure.LOTRWorldGenOrcDungeon;
import net.minecraft.world.gen.feature.WorldGenMelon;
import net.minecraft.world.gen.feature.WorldGenCactus;
import net.minecraft.world.gen.feature.WorldGenVines;
import lotr.common.world.feature.LOTRWorldGenStalactites;
import lotr.common.world.feature.LOTRWorldGenCaveCobwebs;
import net.minecraft.world.gen.feature.WorldGenWaterlily;
import net.minecraft.world.gen.feature.WorldGenPumpkin;
import lotr.common.world.feature.LOTRWorldGenCorn;
import lotr.common.world.feature.LOTRWorldGenReeds;
import net.minecraft.world.gen.feature.WorldGenReed;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import lotr.common.world.feature.LOTRWorldGenLogs;
import lotr.common.world.feature.LOTRWorldGenBiomeFlowers;
import lotr.common.world.feature.LOTRWorldGenSurfaceGravel;
import net.minecraft.block.Block;
import lotr.common.world.feature.LOTRWorldGenSand;
import java.util.ArrayList;
import lotr.common.LOTRMod;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.init.Blocks;
import lotr.common.world.village.LOTRVillageGen;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.world.gen.feature.WorldGenerator;
import java.util.List;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRBiomeDecorator
{
    private World worldObj;
    private Random rand;
    private int chunkX;
    private int chunkZ;
    private LOTRBiome biome;
    private List<OreGenerant> biomeSoils;
    private List<OreGenerant> biomeOres;
    private List<OreGenerant> biomeGems;
    public float biomeOreFactor;
    public float biomeGemFactor;
    protected WorldGenerator clayGen;
    private WorldGenerator sandGen;
    private WorldGenerator whiteSandGen;
    private WorldGenerator quagmireGen;
    private WorldGenerator surfaceGravelGen;
    private WorldGenerator flowerGen;
    private WorldGenerator logGen;
    private WorldGenerator mushroomBrownGen;
    private WorldGenerator mushroomRedGen;
    private WorldGenerator caneGen;
    private WorldGenerator reedGen;
    private WorldGenerator dryReedGen;
    private WorldGenerator cornGen;
    private WorldGenerator pumpkinGen;
    private WorldGenerator waterlilyGen;
    private WorldGenerator cobwebGen;
    private WorldGenerator stalactiteGen;
    private WorldGenerator vinesGen;
    private WorldGenerator cactusGen;
    private WorldGenerator melonGen;
    public int sandPerChunk;
    public int clayPerChunk;
    public int quagmirePerChunk;
    public int treesPerChunk;
    public int willowPerChunk;
    public int logsPerChunk;
    public int vinesPerChunk;
    public int flowersPerChunk;
    public int doubleFlowersPerChunk;
    public int grassPerChunk;
    public int doubleGrassPerChunk;
    public boolean enableFern;
    public boolean enableSpecialGrasses;
    public int deadBushPerChunk;
    public int waterlilyPerChunk;
    public int mushroomsPerChunk;
    public boolean enableRandomMushroom;
    public int canePerChunk;
    public int reedPerChunk;
    public float dryReedChance;
    public int cornPerChunk;
    public int cactiPerChunk;
    public float melonPerChunk;
    public boolean generateWater;
    public boolean generateLava;
    public boolean generateCobwebs;
    public boolean generateAthelas;
    public boolean whiteSand;
    private int treeClusterSize;
    private int treeClusterChance;
    private WorldGenerator orcDungeonGen;
    private WorldGenerator trollHoardGen;
    public boolean generateOrcDungeon;
    public boolean generateTrollHoard;
    private List<LOTRTreeType.WeightedTreeType> treeTypes;
    private Random structureRand;
    private List<RandomStructure> randomStructures;
    private List<LOTRVillageGen> villages;
    
    public void addSoil(final WorldGenerator gen, final float f, final int min, final int max) {
        this.biomeSoils.add(new OreGenerant(gen, f, min, max));
    }
    
    public void addOre(final WorldGenerator gen, final float f, final int min, final int max) {
        this.biomeOres.add(new OreGenerant(gen, f, min, max));
    }
    
    public void addGem(final WorldGenerator gen, final float f, final int min, final int max) {
        this.biomeGems.add(new OreGenerant(gen, f, min, max));
    }
    
    public void clearOres() {
        this.biomeSoils.clear();
        this.biomeOres.clear();
        this.biomeGems.clear();
    }
    
    private void addDefaultOres() {
        this.addSoil((WorldGenerator)new WorldGenMinable(Blocks.dirt, 32), 40.0f, 0, 256);
        this.addSoil((WorldGenerator)new WorldGenMinable(Blocks.gravel, 32), 20.0f, 0, 256);
        this.addOre((WorldGenerator)new WorldGenMinable(Blocks.coal_ore, 16), 40.0f, 0, 128);
        this.addOre((WorldGenerator)new WorldGenMinable(LOTRMod.oreCopper, 8), 16.0f, 0, 128);
        this.addOre((WorldGenerator)new WorldGenMinable(LOTRMod.oreTin, 8), 16.0f, 0, 128);
        this.addOre((WorldGenerator)new WorldGenMinable(Blocks.iron_ore, 8), 20.0f, 0, 64);
        this.addOre((WorldGenerator)new WorldGenMinable(LOTRMod.oreSulfur, 8), 2.0f, 0, 64);
        this.addOre((WorldGenerator)new WorldGenMinable(LOTRMod.oreSaltpeter, 8), 2.0f, 0, 64);
        this.addOre((WorldGenerator)new WorldGenMinable(LOTRMod.oreSalt, 12), 2.0f, 0, 64);
        this.addOre((WorldGenerator)new WorldGenMinable(Blocks.gold_ore, 8), 2.0f, 0, 32);
        this.addOre((WorldGenerator)new WorldGenMinable(LOTRMod.oreSilver, 8), 3.0f, 0, 32);
        this.addGem((WorldGenerator)new WorldGenMinable(LOTRMod.oreGem, 1, 6, Blocks.stone), 2.0f, 0, 64);
        this.addGem((WorldGenerator)new WorldGenMinable(LOTRMod.oreGem, 0, 6, Blocks.stone), 2.0f, 0, 64);
        this.addGem((WorldGenerator)new WorldGenMinable(LOTRMod.oreGem, 4, 5, Blocks.stone), 1.5f, 0, 48);
        this.addGem((WorldGenerator)new WorldGenMinable(LOTRMod.oreGem, 6, 5, Blocks.stone), 1.5f, 0, 48);
        this.addGem((WorldGenerator)new WorldGenMinable(LOTRMod.oreGem, 2, 4, Blocks.stone), 1.0f, 0, 32);
        this.addGem((WorldGenerator)new WorldGenMinable(LOTRMod.oreGem, 3, 4, Blocks.stone), 1.0f, 0, 32);
        this.addGem((WorldGenerator)new WorldGenMinable(LOTRMod.oreGem, 7, 4, Blocks.stone), 0.75f, 0, 24);
        this.addGem((WorldGenerator)new WorldGenMinable(LOTRMod.oreGem, 5, 4, Blocks.stone), 0.5f, 0, 16);
    }
    
    public LOTRBiomeDecorator(final LOTRBiome lotrbiome) {
        this.biomeSoils = new ArrayList<OreGenerant>();
        this.biomeOres = new ArrayList<OreGenerant>();
        this.biomeGems = new ArrayList<OreGenerant>();
        this.biomeOreFactor = 1.0f;
        this.biomeGemFactor = 0.5f;
        this.clayGen = new LOTRWorldGenSand(Blocks.clay, 5, 1);
        this.sandGen = new LOTRWorldGenSand((Block)Blocks.sand, 7, 2);
        this.whiteSandGen = new LOTRWorldGenSand(LOTRMod.whiteSand, 7, 2);
        this.quagmireGen = new LOTRWorldGenSand(LOTRMod.quagmire, 7, 2);
        this.surfaceGravelGen = new LOTRWorldGenSurfaceGravel();
        this.flowerGen = new LOTRWorldGenBiomeFlowers();
        this.logGen = new LOTRWorldGenLogs();
        this.mushroomBrownGen = (WorldGenerator)new WorldGenFlowers((Block)Blocks.brown_mushroom);
        this.mushroomRedGen = (WorldGenerator)new WorldGenFlowers((Block)Blocks.red_mushroom);
        this.caneGen = (WorldGenerator)new WorldGenReed();
        this.reedGen = new LOTRWorldGenReeds(LOTRMod.reeds);
        this.dryReedGen = new LOTRWorldGenReeds(LOTRMod.driedReeds);
        this.cornGen = new LOTRWorldGenCorn();
        this.pumpkinGen = (WorldGenerator)new WorldGenPumpkin();
        this.waterlilyGen = (WorldGenerator)new WorldGenWaterlily();
        this.cobwebGen = new LOTRWorldGenCaveCobwebs();
        this.stalactiteGen = new LOTRWorldGenStalactites();
        this.vinesGen = (WorldGenerator)new WorldGenVines();
        this.cactusGen = (WorldGenerator)new WorldGenCactus();
        this.melonGen = (WorldGenerator)new WorldGenMelon();
        this.sandPerChunk = 4;
        this.clayPerChunk = 3;
        this.quagmirePerChunk = 0;
        this.treesPerChunk = 0;
        this.willowPerChunk = 0;
        this.logsPerChunk = 0;
        this.vinesPerChunk = 0;
        this.flowersPerChunk = 2;
        this.doubleFlowersPerChunk = 0;
        this.grassPerChunk = 1;
        this.doubleGrassPerChunk = 0;
        this.enableFern = false;
        this.enableSpecialGrasses = true;
        this.deadBushPerChunk = 0;
        this.waterlilyPerChunk = 0;
        this.mushroomsPerChunk = 0;
        this.enableRandomMushroom = true;
        this.canePerChunk = 0;
        this.reedPerChunk = 1;
        this.dryReedChance = 0.1f;
        this.cornPerChunk = 0;
        this.cactiPerChunk = 0;
        this.melonPerChunk = 0.0f;
        this.generateWater = true;
        this.generateLava = true;
        this.generateCobwebs = true;
        this.generateAthelas = false;
        this.whiteSand = false;
        this.treeClusterChance = -1;
        this.orcDungeonGen = new LOTRWorldGenOrcDungeon(false);
        this.trollHoardGen = new LOTRWorldGenTrollHoard();
        this.generateOrcDungeon = false;
        this.generateTrollHoard = false;
        this.treeTypes = new ArrayList<LOTRTreeType.WeightedTreeType>();
        this.structureRand = new Random();
        this.randomStructures = new ArrayList<RandomStructure>();
        this.villages = new ArrayList<LOTRVillageGen>();
        this.biome = lotrbiome;
        this.addDefaultOres();
    }
    
    public void addTree(final LOTRTreeType type, final int weight) {
        this.treeTypes.add(new LOTRTreeType.WeightedTreeType(type, weight));
    }
    
    public void clearTrees() {
        this.treeTypes.clear();
    }
    
    public LOTRTreeType getRandomTree(final Random random) {
        if (this.treeTypes.isEmpty()) {
            return LOTRTreeType.OAK;
        }
        final WeightedRandom.Item item = WeightedRandom.getRandomItem(random, (Collection)this.treeTypes);
        return ((LOTRTreeType.WeightedTreeType)item).treeType;
    }
    
    public LOTRTreeType getRandomTreeForVariant(final Random random, final LOTRBiomeVariant variant) {
        if (variant.treeTypes.isEmpty()) {
            return this.getRandomTree(random);
        }
        final float f = variant.variantTreeChance;
        if (random.nextFloat() < f) {
            return variant.getRandomTree(random);
        }
        return this.getRandomTree(random);
    }
    
    public void genTree(final World world, final Random random, final int i, final int j, final int k) {
        final WorldGenerator treeGen = (WorldGenerator)this.biome.getTreeGen(world, random, i, j, k);
        treeGen.generate(world, random, i, j, k);
    }
    
    public void setTreeCluster(final int size, final int chance) {
        this.treeClusterSize = size;
        this.treeClusterChance = chance;
    }
    
    public void resetTreeCluster() {
        this.setTreeCluster(0, -1);
    }
    
    public void addRandomStructure(final WorldGenerator structure, final int chunkChance) {
        this.randomStructures.add(new RandomStructure(structure, chunkChance));
    }
    
    public void clearRandomStructures() {
        this.randomStructures.clear();
    }
    
    public void addVillage(final LOTRVillageGen village) {
        this.villages.add(village);
    }
    
    public void clearVillages() {
        this.villages.clear();
    }
    
    public void checkForVillages(final World world, final int i, final int k, final LOTRChunkProvider.ChunkFlags chunkFlags) {
        chunkFlags.isVillage = false;
        chunkFlags.isFlatVillage = false;
        for (final LOTRVillageGen village : this.villages) {
            final List<LOTRVillageGen.AbstractInstance> instances = village.getNearbyVillagesAtPosition(world, i, k);
            if (!instances.isEmpty()) {
                chunkFlags.isVillage = true;
                for (final LOTRVillageGen.AbstractInstance inst : instances) {
                    if (inst.isFlat()) {
                        chunkFlags.isFlatVillage = true;
                    }
                }
            }
        }
    }
    
    public int getVariantTreesPerChunk(final LOTRBiomeVariant variant) {
        int trees = this.treesPerChunk;
        if (variant.treeFactor > 1.0f) {
            trees = Math.max(trees, 1);
        }
        trees = Math.round(trees * variant.treeFactor);
        return trees;
    }
    
    public void decorate(final World world, final Random random, final int i, final int k) {
        this.worldObj = world;
        this.rand = random;
        this.chunkX = i;
        this.chunkZ = k;
        this.decorate();
    }
    
    private void decorate() {
        final LOTRBiomeVariant biomeVariant = ((LOTRWorldChunkManager)this.worldObj.getWorldChunkManager()).getBiomeVariantAt(this.chunkX + 8, this.chunkZ + 8);
        this.generateOres();
        biomeVariant.decorateVariant(this.worldObj, this.rand, this.chunkX, this.chunkZ, this.biome);
        if (this.rand.nextBoolean() && this.generateCobwebs) {
            final int i = this.chunkX + this.rand.nextInt(16) + 8;
            final int j = this.rand.nextInt(60);
            final int k = this.chunkZ + this.rand.nextInt(16) + 8;
            this.cobwebGen.generate(this.worldObj, this.rand, i, j, k);
        }
        for (int l = 0; l < 3; ++l) {
            final int m = this.chunkX + this.rand.nextInt(16) + 8;
            final int j2 = this.rand.nextInt(60);
            final int k2 = this.chunkZ + this.rand.nextInt(16) + 8;
            this.stalactiteGen.generate(this.worldObj, this.rand, m, j2, k2);
        }
        for (int l = 0; l < this.quagmirePerChunk; ++l) {
            final int m = this.chunkX + this.rand.nextInt(16) + 8;
            final int k = this.chunkZ + this.rand.nextInt(16) + 8;
            this.quagmireGen.generate(this.worldObj, this.rand, m, this.worldObj.getTopSolidOrLiquidBlock(m, k), k);
        }
        for (int l = 0; l < this.sandPerChunk; ++l) {
            final int m = this.chunkX + this.rand.nextInt(16) + 8;
            final int k = this.chunkZ + this.rand.nextInt(16) + 8;
            WorldGenerator biomeSandGenerator = this.sandGen;
            if (this.whiteSand) {
                biomeSandGenerator = this.whiteSandGen;
            }
            biomeSandGenerator.generate(this.worldObj, this.rand, m, this.worldObj.getTopSolidOrLiquidBlock(m, k), k);
        }
        for (int l = 0; l < this.clayPerChunk; ++l) {
            final int m = this.chunkX + this.rand.nextInt(16) + 8;
            final int k = this.chunkZ + this.rand.nextInt(16) + 8;
            this.clayGen.generate(this.worldObj, this.rand, m, this.worldObj.getTopSolidOrLiquidBlock(m, k), k);
        }
        if (this.rand.nextInt(60) == 0) {
            final int i = this.chunkX + this.rand.nextInt(16) + 8;
            final int k3 = this.chunkZ + this.rand.nextInt(16) + 8;
            this.surfaceGravelGen.generate(this.worldObj, this.rand, i, 0, k3);
        }
        if (!biomeVariant.disableStructures && Math.abs(this.chunkX) > 32 && Math.abs(this.chunkZ) > 32) {
            long seed = (long)(this.chunkX * 1879267) ^ this.chunkZ * 67209689L;
            seed = seed * seed * 5829687L + seed * 2876L;
            this.structureRand.setSeed(seed);
            final boolean roadNear = LOTRRoads.isRoadNear(this.chunkX + 8, this.chunkZ + 8, 16) >= 0.0f;
            if (!roadNear) {
                for (final RandomStructure randomstructure : this.randomStructures) {
                    if (this.structureRand.nextInt(randomstructure.chunkChance) == 0) {
                        final int i2 = this.chunkX + this.rand.nextInt(16) + 8;
                        final int k4 = this.chunkZ + this.rand.nextInt(16) + 8;
                        final int j3 = this.worldObj.getTopSolidOrLiquidBlock(i2, k4);
                        randomstructure.structureGen.generate(this.worldObj, this.rand, i2, j3, k4);
                    }
                }
            }
            for (final LOTRVillageGen village : this.villages) {
                village.generateInChunk(this.worldObj, this.chunkX, this.chunkZ);
            }
        }
        if (LOTRWorldGenMarshHut.generatesAt(this.worldObj, this.chunkX, this.chunkZ)) {
            final int i = this.chunkX + 8;
            final int k3 = this.chunkZ + 8;
            final int j2 = this.worldObj.getTopSolidOrLiquidBlock(i, k3);
            final LOTRWorldGenStructureBase house = new LOTRWorldGenMarshHut();
            house.restrictions = false;
            house.generate(this.worldObj, this.rand, i, j2, k3);
        }
        if (LOTRWorldGenGrukHouse.generatesAt(this.worldObj, this.chunkX, this.chunkZ)) {
            final int i = this.chunkX + 8;
            final int k3 = this.chunkZ + 8;
            final int j2 = this.worldObj.getTopSolidOrLiquidBlock(i, k3);
            final LOTRWorldGenStructureBase2 house2 = new LOTRWorldGenGrukHouse(false);
            house2.restrictions = false;
            house2.generateWithSetRotation(this.worldObj, this.rand, i, j2, k3, 2);
        }
        if (LOTRWorldGenTicketBooth.generatesAt(this.worldObj, this.chunkX, this.chunkZ)) {
            final int i = this.chunkX + 8;
            final int k3 = this.chunkZ + 8;
            final int j2 = this.worldObj.getTopSolidOrLiquidBlock(i, k3);
            final LOTRWorldGenStructureBase2 booth = new LOTRWorldGenTicketBooth(false);
            booth.restrictions = false;
            booth.generateWithSetRotation(this.worldObj, this.rand, i, j2, k3, 3);
        }
        int trees = this.getVariantTreesPerChunk(biomeVariant);
        if (this.rand.nextFloat() < this.biome.getTreeIncreaseChance() * biomeVariant.treeFactor) {
            ++trees;
        }
        final float reciprocalTreeFactor = 1.0f / Math.max(biomeVariant.treeFactor, 0.001f);
        final int cluster = Math.round(this.treeClusterChance * reciprocalTreeFactor);
        if (cluster > 0) {
            final Random chunkRand = new Random();
            long seed2 = (long)(this.chunkX / this.treeClusterSize * 3129871) ^ this.chunkZ / this.treeClusterSize * 116129781L;
            seed2 = seed2 * seed2 * 42317861L + seed2 * 11L;
            chunkRand.setSeed(seed2);
            if (chunkRand.nextInt(cluster) == 0) {
                trees += 6 + this.rand.nextInt(5);
            }
        }
        for (int l2 = 0; l2 < trees; ++l2) {
            final int i3 = this.chunkX + this.rand.nextInt(16) + 8;
            final int k5 = this.chunkZ + this.rand.nextInt(16) + 8;
            final WorldGenerator treeGen = (WorldGenerator)this.getRandomTreeForVariant(this.rand, biomeVariant).create(false, this.rand);
            treeGen.generate(this.worldObj, this.rand, i3, this.worldObj.getHeightValue(i3, k5), k5);
        }
        for (int l2 = 0; l2 < this.willowPerChunk; ++l2) {
            final int i3 = this.chunkX + this.rand.nextInt(16) + 8;
            final int k5 = this.chunkZ + this.rand.nextInt(16) + 8;
            final WorldGenerator treeGen = (WorldGenerator)LOTRTreeType.WILLOW_WATER.create(false, this.rand);
            treeGen.generate(this.worldObj, this.rand, i3, this.worldObj.getHeightValue(i3, k5), k5);
        }
        if (trees > 0) {
            final float fallenLeaves = trees / 2.0f;
            int fallenLeavesI = (int)fallenLeaves;
            final float fallenLeavesR = fallenLeaves - fallenLeavesI;
            if (this.rand.nextFloat() < fallenLeavesR) {
                ++fallenLeavesI;
            }
            for (int l3 = 0; l3 < fallenLeaves; ++l3) {
                final int i4 = this.chunkX + this.rand.nextInt(16) + 8;
                final int k6 = this.chunkZ + this.rand.nextInt(16) + 8;
                new LOTRWorldGenFallenLeaves().generate(this.worldObj, this.rand, i4, this.worldObj.getTopSolidOrLiquidBlock(i4, k6), k6);
            }
        }
        if (trees > 0) {
            final float bushes = trees / 3.0f;
            int bushesI = (int)bushes;
            final float bushesR = bushes - bushesI;
            if (this.rand.nextFloat() < bushesR) {
                ++bushesI;
            }
            for (int l3 = 0; l3 < bushes; ++l3) {
                final int i4 = this.chunkX + this.rand.nextInt(16) + 8;
                final int k6 = this.chunkZ + this.rand.nextInt(16) + 8;
                new LOTRWorldGenBushes().generate(this.worldObj, this.rand, i4, this.worldObj.getTopSolidOrLiquidBlock(i4, k6), k6);
            }
        }
        for (int l2 = 0; l2 < this.logsPerChunk; ++l2) {
            final int i3 = this.chunkX + this.rand.nextInt(16) + 8;
            final int k5 = this.chunkZ + this.rand.nextInt(16) + 8;
            this.logGen.generate(this.worldObj, this.rand, i3, this.worldObj.getHeightValue(i3, k5), k5);
        }
        for (int l2 = 0; l2 < this.vinesPerChunk; ++l2) {
            final int i3 = this.chunkX + this.rand.nextInt(16) + 8;
            final int j4 = 64;
            final int k4 = this.chunkZ + this.rand.nextInt(16) + 8;
            this.vinesGen.generate(this.worldObj, this.rand, i3, j4, k4);
        }
        int flowers = this.flowersPerChunk;
        flowers = Math.round(flowers * biomeVariant.flowerFactor);
        for (int l4 = 0; l4 < flowers; ++l4) {
            final int i2 = this.chunkX + this.rand.nextInt(16) + 8;
            final int j5 = this.rand.nextInt(128);
            final int k7 = this.chunkZ + this.rand.nextInt(16) + 8;
            this.flowerGen.generate(this.worldObj, this.rand, i2, j5, k7);
        }
        int doubleFlowers = this.doubleFlowersPerChunk;
        doubleFlowers = Math.round(doubleFlowers * biomeVariant.flowerFactor);
        for (int l5 = 0; l5 < doubleFlowers; ++l5) {
            final int i5 = this.chunkX + this.rand.nextInt(16) + 8;
            final int j3 = this.rand.nextInt(128);
            final int k6 = this.chunkZ + this.rand.nextInt(16) + 8;
            final WorldGenerator doubleFlowerGen = this.biome.getRandomWorldGenForDoubleFlower(this.rand);
            doubleFlowerGen.generate(this.worldObj, this.rand, i5, j3, k6);
        }
        int grasses = this.grassPerChunk;
        grasses = Math.round(grasses * biomeVariant.grassFactor);
        for (int l3 = 0; l3 < grasses; ++l3) {
            final int i4 = this.chunkX + this.rand.nextInt(16) + 8;
            final int j6 = this.rand.nextInt(128);
            final int k8 = this.chunkZ + this.rand.nextInt(16) + 8;
            final WorldGenerator grassGen = this.biome.getRandomWorldGenForGrass(this.rand);
            grassGen.generate(this.worldObj, this.rand, i4, j6, k8);
        }
        int doubleGrasses = this.doubleGrassPerChunk;
        doubleGrasses = Math.round(doubleGrasses * biomeVariant.grassFactor);
        for (int l6 = 0; l6 < doubleGrasses; ++l6) {
            final int i6 = this.chunkX + this.rand.nextInt(16) + 8;
            final int j7 = this.rand.nextInt(128);
            final int k9 = this.chunkZ + this.rand.nextInt(16) + 8;
            final WorldGenerator grassGen2 = this.biome.getRandomWorldGenForDoubleGrass(this.rand);
            grassGen2.generate(this.worldObj, this.rand, i6, j7, k9);
        }
        for (int l6 = 0; l6 < this.deadBushPerChunk; ++l6) {
            final int i6 = this.chunkX + this.rand.nextInt(16) + 8;
            final int j7 = this.rand.nextInt(128);
            final int k9 = this.chunkZ + this.rand.nextInt(16) + 8;
            new WorldGenDeadBush((Block)Blocks.deadbush).generate(this.worldObj, this.rand, i6, j7, k9);
        }
        for (int l6 = 0; l6 < this.waterlilyPerChunk; ++l6) {
            int k8;
            int i6;
            int j8;
            for (i6 = this.chunkX + this.rand.nextInt(16) + 8, k8 = this.chunkZ + this.rand.nextInt(16) + 8, j8 = this.rand.nextInt(128); j8 > 0 && this.worldObj.getBlock(i6, j8 - 1, k8) == Blocks.air; --j8) {}
            this.waterlilyGen.generate(this.worldObj, this.rand, i6, j8, k8);
        }
        for (int l6 = 0; l6 < this.mushroomsPerChunk; ++l6) {
            if (this.rand.nextInt(4) == 0) {
                final int i6 = this.chunkX + this.rand.nextInt(16) + 8;
                final int k8 = this.chunkZ + this.rand.nextInt(16) + 8;
                final int j8 = this.worldObj.getHeightValue(i6, k8);
                this.mushroomBrownGen.generate(this.worldObj, this.rand, i6, j8, k8);
            }
            if (this.rand.nextInt(8) == 0) {
                final int i6 = this.chunkX + this.rand.nextInt(16) + 8;
                final int k8 = this.chunkZ + this.rand.nextInt(16) + 8;
                final int j8 = this.worldObj.getHeightValue(i6, k8);
                this.mushroomRedGen.generate(this.worldObj, this.rand, i6, j8, k8);
            }
        }
        if (this.enableRandomMushroom) {
            if (this.rand.nextInt(4) == 0) {
                final int i4 = this.chunkX + this.rand.nextInt(16) + 8;
                final int j6 = this.rand.nextInt(128);
                final int k8 = this.chunkZ + this.rand.nextInt(16) + 8;
                this.mushroomBrownGen.generate(this.worldObj, this.rand, i4, j6, k8);
            }
            if (this.rand.nextInt(8) == 0) {
                final int i4 = this.chunkX + this.rand.nextInt(16) + 8;
                final int j6 = this.rand.nextInt(128);
                final int k8 = this.chunkZ + this.rand.nextInt(16) + 8;
                this.mushroomRedGen.generate(this.worldObj, this.rand, i4, j6, k8);
            }
        }
        for (int l6 = 0; l6 < this.canePerChunk; ++l6) {
            final int i6 = this.chunkX + this.rand.nextInt(16) + 8;
            final int j7 = this.rand.nextInt(128);
            final int k9 = this.chunkZ + this.rand.nextInt(16) + 8;
            this.caneGen.generate(this.worldObj, this.rand, i6, j7, k9);
        }
        for (int l6 = 0; l6 < 10; ++l6) {
            final int i6 = this.chunkX + this.rand.nextInt(16) + 8;
            final int j7 = this.rand.nextInt(128);
            final int k9 = this.chunkZ + this.rand.nextInt(16) + 8;
            this.caneGen.generate(this.worldObj, this.rand, i6, j7, k9);
        }
        for (int l6 = 0; l6 < this.reedPerChunk; ++l6) {
            int k8;
            int i6;
            int j8;
            for (i6 = this.chunkX + this.rand.nextInt(16) + 8, k8 = this.chunkZ + this.rand.nextInt(16) + 8, j8 = this.rand.nextInt(128); j8 > 0 && this.worldObj.getBlock(i6, j8 - 1, k8) == Blocks.air; --j8) {}
            if (this.rand.nextFloat() < this.dryReedChance) {
                this.dryReedGen.generate(this.worldObj, this.rand, i6, j8, k8);
            }
            else {
                this.reedGen.generate(this.worldObj, this.rand, i6, j8, k8);
            }
        }
        for (int l6 = 0; l6 < this.cornPerChunk; ++l6) {
            final int i6 = this.chunkX + this.rand.nextInt(16) + 8;
            final int j7 = this.rand.nextInt(128);
            final int k9 = this.chunkZ + this.rand.nextInt(16) + 8;
            this.cornGen.generate(this.worldObj, this.rand, i6, j7, k9);
        }
        for (int l6 = 0; l6 < this.cactiPerChunk; ++l6) {
            final int i6 = this.chunkX + this.rand.nextInt(16) + 8;
            final int j7 = this.rand.nextInt(128);
            final int k9 = this.chunkZ + this.rand.nextInt(16) + 8;
            this.cactusGen.generate(this.worldObj, this.rand, i6, j7, k9);
        }
        if (this.melonPerChunk > 0.0f) {
            final int melonInt = MathHelper.floor_double((double)this.melonPerChunk);
            final float melonF = this.melonPerChunk - melonInt;
            for (int l7 = 0; l7 < melonInt; ++l7) {
                final int i7 = this.chunkX + this.rand.nextInt(16) + 8;
                final int k10 = this.chunkZ + this.rand.nextInt(16) + 8;
                final int j9 = this.worldObj.getHeightValue(i7, k10);
                this.melonGen.generate(this.worldObj, this.rand, i7, j9, k10);
            }
            if (this.rand.nextFloat() < melonF) {
                final int i8 = this.chunkX + this.rand.nextInt(16) + 8;
                final int k9 = this.chunkZ + this.rand.nextInt(16) + 8;
                final int j10 = this.worldObj.getHeightValue(i8, k9);
                this.melonGen.generate(this.worldObj, this.rand, i8, j10, k9);
            }
        }
        if (this.flowersPerChunk > 0 && this.rand.nextInt(32) == 0) {
            final int i4 = this.chunkX + this.rand.nextInt(16) + 8;
            final int j6 = this.rand.nextInt(128);
            final int k8 = this.chunkZ + this.rand.nextInt(16) + 8;
            this.pumpkinGen.generate(this.worldObj, this.rand, i4, j6, k8);
        }
        if (this.flowersPerChunk > 0 && this.rand.nextInt(4) == 0) {
            final int i4 = this.chunkX + this.rand.nextInt(16) + 8;
            final int j6 = this.rand.nextInt(128);
            final int k8 = this.chunkZ + this.rand.nextInt(16) + 8;
            new LOTRWorldGenBerryBush().generate(this.worldObj, this.rand, i4, j6, k8);
        }
        if (this.generateAthelas && this.rand.nextInt(30) == 0) {
            final int i4 = this.chunkX + this.rand.nextInt(16) + 8;
            final int j6 = this.rand.nextInt(128);
            final int k8 = this.chunkZ + this.rand.nextInt(16) + 8;
            new WorldGenFlowers(LOTRMod.athelas).generate(this.worldObj, this.rand, i4, j6, k8);
        }
        if (this.generateWater) {
            final WorldGenerator waterGen = new LOTRWorldGenStreams((Block)Blocks.flowing_water);
            for (int l8 = 0; l8 < 50; ++l8) {
                final int i8 = this.chunkX + this.rand.nextInt(16) + 8;
                final int j8 = this.rand.nextInt(this.rand.nextInt(120) + 8);
                final int k10 = this.chunkZ + this.rand.nextInt(16) + 8;
                waterGen.generate(this.worldObj, this.rand, i8, j8, k10);
            }
            if (this.biome.minHeight > 1.0f) {
                for (int l8 = 0; l8 < 50; ++l8) {
                    final int i8 = this.chunkX + this.rand.nextInt(16) + 8;
                    final int j8 = 100 + this.rand.nextInt(150);
                    final int k10 = this.chunkZ + this.rand.nextInt(16) + 8;
                    waterGen.generate(this.worldObj, this.rand, i8, j8, k10);
                }
            }
        }
        if (this.generateLava) {
            final WorldGenerator lavaGen = new LOTRWorldGenStreams((Block)Blocks.flowing_lava);
            int lava = 20;
            if (this.biome instanceof LOTRBiomeGenMordor) {
                lava = 50;
            }
            for (int l7 = 0; l7 < lava; ++l7) {
                final int i7 = this.chunkX + this.rand.nextInt(16) + 8;
                final int j10 = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(112) + 8) + 8);
                final int k11 = this.chunkZ + this.rand.nextInt(16) + 8;
                lavaGen.generate(this.worldObj, this.rand, i7, j10, k11);
            }
        }
        if (this.generateOrcDungeon) {
            for (int l6 = 0; l6 < 6; ++l6) {
                final int i6 = this.chunkX + this.rand.nextInt(16) + 8;
                final int j7 = this.rand.nextInt(128);
                final int k9 = this.chunkZ + this.rand.nextInt(16) + 8;
                this.orcDungeonGen.generate(this.worldObj, this.rand, i6, j7, k9);
            }
        }
        if (this.generateTrollHoard) {
            for (int l6 = 0; l6 < 2; ++l6) {
                final int i6 = this.chunkX + this.rand.nextInt(16) + 8;
                final int j7 = MathHelper.getRandomIntegerInRange(this.rand, 36, 90);
                final int k9 = this.chunkZ + this.rand.nextInt(16) + 8;
                this.trollHoardGen.generate(this.worldObj, this.rand, i6, j7, k9);
            }
        }
        if (biomeVariant.boulderGen != null && this.rand.nextInt(biomeVariant.boulderChance) == 0) {
            for (int boulders = MathHelper.getRandomIntegerInRange(this.rand, 1, biomeVariant.boulderMax), l8 = 0; l8 < boulders; ++l8) {
                final int i8 = this.chunkX + this.rand.nextInt(16) + 8;
                final int k9 = this.chunkZ + this.rand.nextInt(16) + 8;
                biomeVariant.boulderGen.generate(this.worldObj, this.rand, i8, this.worldObj.getHeightValue(i8, k9), k9);
            }
        }
    }
    
    private void generateOres() {
        for (final OreGenerant soil : this.biomeSoils) {
            this.genStandardOre(soil.oreChance, soil.oreGen, soil.minHeight, soil.maxHeight);
        }
        for (final OreGenerant ore : this.biomeOres) {
            final float f = ore.oreChance * this.biomeOreFactor;
            this.genStandardOre(f, ore.oreGen, ore.minHeight, ore.maxHeight);
        }
        for (final OreGenerant gem : this.biomeGems) {
            final float f = gem.oreChance * this.biomeGemFactor;
            this.genStandardOre(f, gem.oreGen, gem.minHeight, gem.maxHeight);
        }
    }
    
    private void genStandardOre(float ores, final WorldGenerator oreGen, final int minHeight, final int maxHeight) {
        while (ores > 0.0f) {
            final boolean generate = ores >= 1.0f || this.rand.nextFloat() < ores;
            --ores;
            if (generate) {
                final int i = this.chunkX + this.rand.nextInt(16);
                final int j = MathHelper.getRandomIntegerInRange(this.rand, minHeight, maxHeight);
                final int k = this.chunkZ + this.rand.nextInt(16);
                oreGen.generate(this.worldObj, this.rand, i, j, k);
            }
        }
    }
    
    private class OreGenerant
    {
        private WorldGenerator oreGen;
        private float oreChance;
        private int minHeight;
        private int maxHeight;
        
        public OreGenerant(final WorldGenerator gen, final float f, final int min, final int max) {
            this.oreGen = gen;
            this.oreChance = f;
            this.minHeight = min;
            this.maxHeight = max;
        }
    }
    
    private class RandomStructure
    {
        public WorldGenerator structureGen;
        public int chunkChance;
        
        public RandomStructure(final WorldGenerator w, final int i) {
            this.structureGen = w;
            this.chunkChance = i;
        }
    }
}
