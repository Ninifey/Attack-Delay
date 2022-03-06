// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.village;

import net.minecraft.block.BlockSlab;
import lotr.common.util.CentredSquareArray;
import lotr.common.world.structure2.LOTRWorldGenStructureBase2;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import lotr.common.world.map.LOTRRoadType;
import java.util.Iterator;
import lotr.common.world.map.LOTRFixedStructures;
import lotr.common.world.map.LOTRMountains;
import lotr.common.world.map.LOTRRoads;
import net.minecraft.util.MathHelper;
import lotr.common.world.LOTRWorldChunkManager;
import net.minecraft.world.World;
import java.util.ArrayList;
import java.util.Random;
import net.minecraft.world.biome.BiomeGenBase;
import java.util.List;
import lotr.common.world.biome.LOTRBiome;

public abstract class LOTRVillageGen
{
    protected LOTRBiome villageBiome;
    private List<BiomeGenBase> spawnBiomes;
    private static Random villageRand;
    protected int gridScale;
    protected int gridRandomDisplace;
    protected float spawnChance;
    protected int villageChunkSize;
    public static final double SQRT2;
    
    public LOTRVillageGen(final LOTRBiome biome) {
        this.villageBiome = biome;
        (this.spawnBiomes = new ArrayList<BiomeGenBase>()).add(this.villageBiome);
    }
    
    public final AbstractInstance createAndSetupVillageInstance(final World world, final int i, final int k, final Random random) {
        final AbstractInstance instance = this.createVillageInstance(world, i, k, random);
        instance.setupBaseAndVillageProperties();
        return instance;
    }
    
    protected abstract AbstractInstance createVillageInstance(final World p0, final int p1, final int p2, final Random p3);
    
    private static void seedVillageRand(final World world, final int i, final int k) {
        final long seed = i * 6890360793007L + k * 456879569029062L + world.getWorldInfo().getSeed() + 274893855L;
        LOTRVillageGen.villageRand.setSeed(seed);
    }
    
    private boolean isVillageCentre(final World world, final int chunkX, final int chunkZ) {
        final LOTRWorldChunkManager worldChunkMgr = (LOTRWorldChunkManager)world.getWorldChunkManager();
        final LOTRVillagePositionCache cache = worldChunkMgr.getVillageCache(this);
        if (cache.isVillageAt(chunkX, chunkZ)) {
            return true;
        }
        if (cache.isVillageNotAt(chunkX, chunkZ)) {
            return false;
        }
        int i2 = MathHelper.floor_double(chunkX / (double)this.gridScale);
        int k2 = MathHelper.floor_double(chunkZ / (double)this.gridScale);
        seedVillageRand(world, i2, k2);
        i2 *= this.gridScale;
        k2 *= this.gridScale;
        i2 += MathHelper.getRandomIntegerInRange(LOTRVillageGen.villageRand, -this.gridRandomDisplace, this.gridRandomDisplace);
        k2 += MathHelper.getRandomIntegerInRange(LOTRVillageGen.villageRand, -this.gridRandomDisplace, this.gridRandomDisplace);
        if (chunkX == i2 && chunkZ == k2) {
            final int i3 = chunkX * 16 + 8;
            final int k3 = chunkZ * 16 + 8;
            final int villageRange = this.villageChunkSize * 16;
            if (LOTRVillageGen.villageRand.nextFloat() < this.spawnChance) {
                final int diagRange = (int)Math.round((villageRange + 8) * LOTRVillageGen.SQRT2);
                boolean anythingNear = false;
                anythingNear = (LOTRRoads.isRoadNear(i3, k3, diagRange) >= 0.0f);
                if (!anythingNear) {
                    anythingNear = LOTRMountains.mountainNear(i3, k3, diagRange);
                    if (!anythingNear) {
                        anythingNear = LOTRFixedStructures.structureNear(world, i3, k3, diagRange);
                    }
                }
                if (!anythingNear) {
                    seedVillageRand(world, i3, k3);
                    final AbstractInstance instance = this.createAndSetupVillageInstance(world, i3, k3, LOTRVillageGen.villageRand);
                    final boolean flat = instance.isFlat();
                    if (worldChunkMgr.areBiomesViable(i3, k3, villageRange, this.spawnBiomes) && worldChunkMgr.areVariantsSuitableVillage(i3, k3, villageRange, flat)) {
                        cache.markResult(chunkX, chunkZ, true);
                        return true;
                    }
                }
            }
        }
        cache.markResult(chunkX, chunkZ, false);
        return false;
    }
    
    private List<AbstractInstance> getNearbyVillages(final World world, final int chunkX, final int chunkZ) {
        final List<AbstractInstance> villages = new ArrayList<AbstractInstance>();
        for (int i = chunkX - this.villageChunkSize; i <= chunkX + this.villageChunkSize; ++i) {
            for (int k = chunkZ - this.villageChunkSize; k <= chunkZ + this.villageChunkSize; ++k) {
                if (this.isVillageCentre(world, i, k)) {
                    final int centreX = (i << 4) + 8;
                    final int centreZ = (k << 4) + 8;
                    seedVillageRand(world, centreX, centreZ);
                    final AbstractInstance instance = this.createAndSetupVillageInstance(world, centreX, centreZ, LOTRVillageGen.villageRand);
                    villages.add(instance);
                }
            }
        }
        return villages;
    }
    
    public List<AbstractInstance> getNearbyVillagesAtPosition(final World world, final int i, final int k) {
        final int chunkX = i >> 4;
        final int chunkZ = k >> 4;
        return this.getNearbyVillages(world, chunkX, chunkZ);
    }
    
    public void generateInChunk(final World world, final int i, final int k) {
        final List<AbstractInstance> villages = this.getNearbyVillagesAtPosition(world, i, k);
        for (final AbstractInstance instance : villages) {
            instance.setupVillageStructures();
            this.generateInstanceInChunk(instance, world, i, k);
        }
    }
    
    public void generateInstanceInChunk(final AbstractInstance instance, final World world, final int i, final int k) {
        for (int i2 = i; i2 <= i + 15; ++i2) {
            for (int k2 = k; k2 <= k + 15; ++k2) {
                final BiomeGenBase biome = world.getBiomeGenForCoords(i2, k2);
                final Object[] pathData = this.getHeight_getPath_isSlab(instance, world, i2, k2, biome);
                final LOTRRoadType pathType = (LOTRRoadType)pathData[1];
                if (pathType != null) {
                    final int j1 = (int)pathData[0];
                    final boolean isSlab = (boolean)pathData[2];
                    instance.setupWorldPositionSeed(i2, k2);
                    final LOTRRoadType.RoadBlock roadblock = pathType.getBlock(instance.instanceRand, biome, true, isSlab);
                    world.setBlock(i2, j1, k2, roadblock.block, roadblock.meta, 2);
                    final Block above = world.getBlock(i2, j1 + 1, k2);
                    if (!above.canBlockStay(world, i2, j1 + 1, k2)) {
                        world.setBlock(i2, j1 + 1, k2, Blocks.air, 0, 3);
                    }
                }
                instance.setupWorldPositionSeed(i2, k2);
                for (final StructureInfo struct : instance.structures) {
                    final int[] coords = instance.getWorldCoords(struct.posX, struct.posZ);
                    if (i2 == coords[0] && k2 == coords[1]) {
                        final int j2 = world.getTopSolidOrLiquidBlock(i2, k2);
                        int minHeight = 62;
                        final int terrainTypeMinHeight = world.provider.terrainType.getMinimumSpawnHeight(world);
                        if (terrainTypeMinHeight < 62) {
                            minHeight = terrainTypeMinHeight - 1;
                        }
                        if (j2 <= minHeight) {
                            continue;
                        }
                        struct.structure.generateWithSetRotation(world, instance.instanceRand, i2, j2, k2, instance.getStructureRotation(struct.rotation));
                    }
                }
            }
        }
    }
    
    private Object[] getHeight_getPath_isSlab(final AbstractInstance instance, final World world, final int i, final int k, final BiomeGenBase biome) {
        instance.setupWorldPositionSeed(i, k);
        final int[] coords = instance.getRelativeCoords(i, k);
        final int i2 = coords[0];
        final int k2 = coords[1];
        final LOTRRoadType road = instance.getPath(instance.instanceRand, i2, k2);
        boolean isPath = false;
        boolean isSlab = false;
        final int j1 = this.getTopTerrainBlock(world, i, k, biome, false);
        if (road != null && j1 > 0 && LOTRWorldGenStructureBase2.isSurfaceStatic(world, i, j1, k)) {
            isPath = true;
            final int slabRange = 1;
            final CentredSquareArray<Integer> slabArray = new CentredSquareArray<Integer>(slabRange);
            slabArray.fill(j1);
            for (int i3 = -slabRange; i3 <= slabRange; ++i3) {
                for (int k3 = -slabRange; k3 <= slabRange; ++k3) {
                    final int i4 = i + i3;
                    final int k4 = k + k3;
                    if (i3 != 0 || k3 != 0) {
                        final int j2 = this.getTopTerrainBlock(world, i4, k4, biome, false);
                        if (j2 > 0 && j2 < j1) {
                            slabArray.set(i3, k3, j2);
                        }
                    }
                }
            }
            if (slabArray.get(-1, 0) < j1 || slabArray.get(1, 0) < j1 || slabArray.get(0, -1) < j1 || slabArray.get(0, 1) < j1) {
                isSlab = true;
            }
            else if (slabArray.get(-1, -1) < j1 || slabArray.get(1, -1) < j1 || slabArray.get(-1, 1) < j1 || slabArray.get(1, 1) < j1) {
                isSlab = true;
            }
            if (isSlab && world.getBlock(i, j1 + 1, k).isOpaqueCube()) {
                isSlab = false;
            }
        }
        final Object[] ret = new Object[3];
        if (isPath) {
            ret[0] = j1;
            ret[1] = road;
            ret[2] = isSlab;
        }
        else {
            ret[0] = -1;
            ret[1] = null;
            ret[2] = false;
        }
        return ret;
    }
    
    private int getTopTerrainBlock(final World world, final int i, final int k, final BiomeGenBase biome, final boolean acceptSlab) {
        int j = world.getTopSolidOrLiquidBlock(i, k) - 1;
        while (!world.getBlock(i, j + 1, k).getMaterial().isLiquid()) {
            final Block block = world.getBlock(i, j, k);
            final Block below = world.getBlock(i, j - 1, k);
            if (block.isOpaqueCube() || (acceptSlab && block instanceof BlockSlab && below.isOpaqueCube())) {
                return j;
            }
            if (--j <= 62) {
                return -1;
            }
        }
        return -1;
    }
    
    public void generateCompleteVillageInstance(final AbstractInstance instance, final World world, final int i, final int k) {
        instance.setupVillageStructures();
        for (int i2 = -this.villageChunkSize; i2 <= this.villageChunkSize; ++i2) {
            for (int k2 = -this.villageChunkSize; k2 <= this.villageChunkSize; ++k2) {
                final int i3 = i - 8 + i2 * 16;
                final int k3 = k - 8 + k2 * 16;
                this.generateInstanceInChunk(instance, world, i3, k3);
            }
        }
    }
    
    static {
        LOTRVillageGen.villageRand = new Random();
        SQRT2 = Math.sqrt(2.0);
    }
    
    private class StructureInfo
    {
        public LOTRWorldGenStructureBase2 structure;
        public int posX;
        public int posZ;
        public int rotation;
        
        public StructureInfo(final LOTRWorldGenStructureBase2 s, final int x, final int z, final int r) {
            this.structure = s;
            this.posX = x;
            this.posZ = z;
            this.rotation = r;
        }
    }
    
    public abstract class AbstractInstance
    {
        private World theWorld;
        private Random instanceRand;
        private long instanceRandSeed;
        private int centreX;
        private int centreZ;
        private int rotationMode;
        private List<StructureInfo> structures;
        
        protected AbstractInstance(final World world, final int i, final int k, final Random random) {
            this.structures = new ArrayList<StructureInfo>();
            this.theWorld = world;
            this.instanceRand = new Random();
            this.instanceRandSeed = random.nextLong();
            this.centreX = i;
            this.centreZ = k;
        }
        
        protected final void setupBaseAndVillageProperties() {
            this.setupVillageSeed();
            this.rotationMode = this.instanceRand.nextInt(4);
            this.setupVillageProperties(this.instanceRand);
        }
        
        protected abstract void setupVillageProperties(final Random p0);
        
        private void setupVillageSeed() {
            long seed = this.centreX * 580682095692076767L + this.centreZ * 12789948968296726L + this.theWorld.getWorldInfo().getSeed() + 49920968939865L;
            seed += this.instanceRandSeed;
            this.instanceRand.setSeed(seed);
        }
        
        public void setRotation(final int i) {
            this.rotationMode = i;
        }
        
        private void setupWorldPositionSeed(final int i, final int k) {
            this.setupVillageSeed();
            final int[] coords = this.getRelativeCoords(i, k);
            final int i2 = coords[0];
            final int k2 = coords[1];
            final long seed1 = this.instanceRand.nextLong();
            final long seed2 = this.instanceRand.nextLong();
            final long seed3 = i2 * seed1 + k2 * seed2 ^ this.theWorld.getWorldInfo().getSeed();
            this.instanceRand.setSeed(seed3);
        }
        
        public abstract boolean isFlat();
        
        protected final void setupVillageStructures() {
            this.setupVillageSeed();
            this.structures.clear();
            this.addVillageStructures(this.instanceRand);
        }
        
        protected abstract void addVillageStructures(final Random p0);
        
        protected void addStructure(final LOTRWorldGenStructureBase2 structure, final int x, final int z, final int r) {
            this.addStructure(structure, x, z, r, false);
        }
        
        protected void addStructure(final LOTRWorldGenStructureBase2 structure, final int x, final int z, final int r, final boolean force) {
            structure.villageInstance = this;
            structure.restrictions = !force;
            if (force) {
                structure.shouldFindSurface = true;
            }
            this.structures.add(new StructureInfo(structure, x, z, r));
        }
        
        protected abstract LOTRRoadType getPath(final Random p0, final int p1, final int p2);
        
        public abstract boolean isVillageSurface(final World p0, final int p1, final int p2, final int p3);
        
        private int[] getWorldCoords(final int xRel, final int zRel) {
            int xWorld = this.centreX;
            int zWorld = this.centreZ;
            switch (this.rotationMode) {
                case 0: {
                    xWorld = this.centreX - xRel;
                    zWorld = this.centreZ - zRel;
                    break;
                }
                case 1: {
                    xWorld = this.centreX + zRel;
                    zWorld = this.centreZ - xRel;
                    break;
                }
                case 2: {
                    xWorld = this.centreX + xRel;
                    zWorld = this.centreZ + zRel;
                    break;
                }
                case 3: {
                    xWorld = this.centreX - zRel;
                    zWorld = this.centreZ + xRel;
                    break;
                }
            }
            return new int[] { xWorld, zWorld };
        }
        
        private int[] getRelativeCoords(final int xWorld, final int zWorld) {
            int xRel = 0;
            int zRel = 0;
            switch (this.rotationMode) {
                case 0: {
                    xRel = this.centreX - xWorld;
                    zRel = this.centreZ - zWorld;
                    break;
                }
                case 1: {
                    xRel = this.centreZ - zWorld;
                    zRel = xWorld - this.centreX;
                    break;
                }
                case 2: {
                    xRel = xWorld - this.centreX;
                    zRel = zWorld - this.centreZ;
                    break;
                }
                case 3: {
                    xRel = zWorld - this.centreZ;
                    zRel = this.centreX - xWorld;
                    break;
                }
            }
            return new int[] { xRel, zRel };
        }
        
        private int getStructureRotation(final int r) {
            return (r + (this.rotationMode + 2)) % 4;
        }
    }
}
