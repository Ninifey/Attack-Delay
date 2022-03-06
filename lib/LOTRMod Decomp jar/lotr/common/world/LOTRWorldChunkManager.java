// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world;

import net.minecraft.world.ChunkPosition;
import java.util.Random;
import java.util.List;
import net.minecraft.world.chunk.Chunk;
import cpw.mods.fml.common.FMLLog;
import lotr.common.world.biome.variant.LOTRBiomeVariantStorage;
import lotr.common.world.biome.variant.LOTRBiomeVariantList;
import lotr.common.world.biome.LOTRBiomeGenFarHaradMangrove;
import lotr.common.world.biome.LOTRBiomeGenFarHaradJungle;
import lotr.common.world.genlayer.LOTRGenLayerBiomeVariantsLake;
import lotr.common.world.genlayer.LOTRGenLayerBiomeVariants;
import lotr.common.world.map.LOTRFixedStructures;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import lotr.common.world.genlayer.LOTRIntCache;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.world.genlayer.LOTRGenLayerZoomVoronoi;
import lotr.common.world.genlayer.LOTRGenLayerWorld;
import java.util.HashMap;
import net.minecraft.world.gen.structure.MapGenStructure;
import lotr.common.world.village.LOTRVillagePositionCache;
import lotr.common.world.village.LOTRVillageGen;
import java.util.Map;
import net.minecraft.world.biome.BiomeCache;
import lotr.common.world.genlayer.LOTRGenLayer;
import lotr.common.LOTRDimension;
import net.minecraft.world.World;
import net.minecraft.world.biome.WorldChunkManager;

public class LOTRWorldChunkManager extends WorldChunkManager
{
    private World worldObj;
    private LOTRDimension lotrDimension;
    private LOTRGenLayer[] chunkGenLayers;
    private LOTRGenLayer[] worldLayers;
    private BiomeCache biomeCache;
    private static int LAYER_BIOME;
    private static int LAYER_VARIANTS_LARGE;
    private static int LAYER_VARIANTS_SMALL;
    private static int LAYER_VARIANTS_LAKES;
    private static int LAYER_VARIANTS_RIVERS;
    private Map<LOTRVillageGen, LOTRVillagePositionCache> villageCacheMap;
    private Map<MapGenStructure, LOTRVillagePositionCache> structureCacheMap;
    
    public LOTRWorldChunkManager(final World world, final LOTRDimension dim) {
        this.villageCacheMap = new HashMap<LOTRVillageGen, LOTRVillagePositionCache>();
        this.structureCacheMap = new HashMap<MapGenStructure, LOTRVillagePositionCache>();
        this.worldObj = world;
        this.biomeCache = new BiomeCache((WorldChunkManager)this);
        this.lotrDimension = dim;
        this.setupGenLayers();
    }
    
    private void setupGenLayers() {
        final long seed = this.worldObj.getSeed() + 1954L;
        this.chunkGenLayers = LOTRGenLayerWorld.createWorld(this.lotrDimension, this.worldObj.getWorldInfo().getTerrainType());
        this.worldLayers = new LOTRGenLayer[this.chunkGenLayers.length];
        for (int i = 0; i < this.worldLayers.length; ++i) {
            final LOTRGenLayer layer = this.chunkGenLayers[i];
            this.worldLayers[i] = new LOTRGenLayerZoomVoronoi(10L, layer);
        }
        for (int i = 0; i < this.worldLayers.length; ++i) {
            this.chunkGenLayers[i].initWorldGenSeed(seed);
            this.worldLayers[i].initWorldGenSeed(seed);
        }
    }
    
    public BiomeGenBase getBiomeGenAt(final int i, final int k) {
        return this.biomeCache.getBiomeGenAt(i, k);
    }
    
    public float[] getRainfall(float[] rainfall, final int i, final int k, final int xSize, final int zSize) {
        LOTRIntCache.get(this.worldObj).resetIntCache();
        if (rainfall == null || rainfall.length < xSize * zSize) {
            rainfall = new float[xSize * zSize];
        }
        final int[] ints = this.worldLayers[LOTRWorldChunkManager.LAYER_BIOME].getInts(this.worldObj, i, k, xSize, zSize);
        for (int l = 0; l < xSize * zSize; ++l) {
            final int biomeID = ints[l];
            float f = this.lotrDimension.biomeList[biomeID].getIntRainfall() / 65536.0f;
            if (f > 1.0f) {
                f = 1.0f;
            }
            rainfall[l] = f;
        }
        return rainfall;
    }
    
    @SideOnly(Side.CLIENT)
    public float getTemperatureAtHeight(final float f, final int height) {
        if (this.worldObj.isClient && LOTRMod.isChristmas()) {
            return 0.0f;
        }
        return f;
    }
    
    public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] biomes, final int i, final int k, final int xSize, final int zSize) {
        LOTRIntCache.get(this.worldObj).resetIntCache();
        if (biomes == null || biomes.length < xSize * zSize) {
            biomes = new BiomeGenBase[xSize * zSize];
        }
        final int[] ints = this.chunkGenLayers[LOTRWorldChunkManager.LAYER_BIOME].getInts(this.worldObj, i, k, xSize, zSize);
        for (int l = 0; l < xSize * zSize; ++l) {
            final int biomeID = ints[l];
            biomes[l] = this.lotrDimension.biomeList[biomeID];
        }
        return biomes;
    }
    
    public BiomeGenBase[] loadBlockGeneratorData(final BiomeGenBase[] biomes, final int i, final int k, final int xSize, final int zSize) {
        return this.getBiomeGenAt(biomes, i, k, xSize, zSize, true);
    }
    
    public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] biomes, final int i, final int k, final int xSize, final int zSize, final boolean useCache) {
        LOTRIntCache.get(this.worldObj).resetIntCache();
        if (biomes == null || biomes.length < xSize * zSize) {
            biomes = new BiomeGenBase[xSize * zSize];
        }
        if (useCache && xSize == 16 && zSize == 16 && (i & 0xF) == 0x0 && (k & 0xF) == 0x0) {
            final BiomeGenBase[] cachedBiomes = this.biomeCache.getCachedBiomes(i, k);
            System.arraycopy(cachedBiomes, 0, biomes, 0, xSize * zSize);
            return biomes;
        }
        final int[] ints = this.worldLayers[LOTRWorldChunkManager.LAYER_BIOME].getInts(this.worldObj, i, k, xSize, zSize);
        for (int l = 0; l < xSize * zSize; ++l) {
            final int biomeID = ints[l];
            biomes[l] = this.lotrDimension.biomeList[biomeID];
        }
        return biomes;
    }
    
    public LOTRBiomeVariant[] getVariantsChunkGen(final LOTRBiomeVariant[] variants, final int i, final int k, final int xSize, final int zSize, final BiomeGenBase[] biomeSource) {
        return this.getBiomeVariantsFromLayers(variants, i, k, xSize, zSize, biomeSource, true);
    }
    
    public LOTRBiomeVariant[] getBiomeVariants(final LOTRBiomeVariant[] variants, final int i, final int k, final int xSize, final int zSize) {
        return this.getBiomeVariantsFromLayers(variants, i, k, xSize, zSize, null, false);
    }
    
    private LOTRBiomeVariant[] getBiomeVariantsFromLayers(LOTRBiomeVariant[] variants, final int i, final int k, final int xSize, final int zSize, final BiomeGenBase[] biomeSource, final boolean isChunkGeneration) {
        LOTRIntCache.get(this.worldObj).resetIntCache();
        BiomeGenBase[] biomes = new BiomeGenBase[xSize * zSize];
        if (biomeSource != null) {
            biomes = biomeSource;
        }
        else {
            for (int k2 = 0; k2 < zSize; ++k2) {
                for (int i2 = 0; i2 < xSize; ++i2) {
                    final int index = i2 + k2 * xSize;
                    biomes[index] = this.worldObj.getBiomeGenForCoords(i + i2, k + k2);
                }
            }
        }
        if (variants == null || variants.length < xSize * zSize) {
            variants = new LOTRBiomeVariant[xSize * zSize];
        }
        final LOTRGenLayer[] sourceGenLayers = isChunkGeneration ? this.chunkGenLayers : this.worldLayers;
        final LOTRGenLayer variantsLarge = sourceGenLayers[LOTRWorldChunkManager.LAYER_VARIANTS_LARGE];
        final LOTRGenLayer variantsSmall = sourceGenLayers[LOTRWorldChunkManager.LAYER_VARIANTS_SMALL];
        final LOTRGenLayer variantsLakes = sourceGenLayers[LOTRWorldChunkManager.LAYER_VARIANTS_LAKES];
        final LOTRGenLayer variantsRivers = sourceGenLayers[LOTRWorldChunkManager.LAYER_VARIANTS_RIVERS];
        final int[] variantsLargeInts = variantsLarge.getInts(this.worldObj, i, k, xSize, zSize);
        final int[] variantsSmallInts = variantsSmall.getInts(this.worldObj, i, k, xSize, zSize);
        final int[] variantsLakesInts = variantsLakes.getInts(this.worldObj, i, k, xSize, zSize);
        final int[] variantsRiversInts = variantsRivers.getInts(this.worldObj, i, k, xSize, zSize);
        for (int k3 = 0; k3 < zSize; ++k3) {
            for (int i3 = 0; i3 < xSize; ++i3) {
                final int index2 = i3 + k3 * xSize;
                final LOTRBiome biome = (LOTRBiome)biomes[index2];
                LOTRBiomeVariant variant = LOTRBiomeVariant.STANDARD;
                int xPos = i + i3;
                int zPos = k + k3;
                if (isChunkGeneration) {
                    xPos <<= 2;
                    zPos <<= 2;
                }
                final boolean[] flags = LOTRFixedStructures._mountainNear_structureNear(this.worldObj, xPos, zPos);
                final boolean mountainNear = flags[0];
                final boolean structureNear = flags[1];
                if (!mountainNear) {
                    final float variantChance = biome.variantChance;
                    if (variantChance > 0.0f) {
                        for (int pass = 0; pass <= 1; ++pass) {
                            final LOTRBiomeVariantList variantList = (pass == 0) ? biome.getBiomeVariantsLarge() : biome.getBiomeVariantsSmall();
                            if (!variantList.isEmpty()) {
                                final int[] sourceInts = (pass == 0) ? variantsLargeInts : variantsSmallInts;
                                final int variantCode = sourceInts[index2];
                                final float variantF = variantCode / (float)LOTRGenLayerBiomeVariants.RANDOM_MAX;
                                if (variantF < variantChance) {
                                    final float variantFNormalised = variantF / variantChance;
                                    variant = variantList.get(variantFNormalised);
                                    break;
                                }
                                variant = LOTRBiomeVariant.STANDARD;
                            }
                        }
                    }
                    if (!structureNear && biome.getEnableRiver()) {
                        final int lakeCode = variantsLakesInts[index2];
                        if (LOTRGenLayerBiomeVariantsLake.getFlag(lakeCode, 1)) {
                            variant = LOTRBiomeVariant.LAKE;
                        }
                        if (LOTRGenLayerBiomeVariantsLake.getFlag(lakeCode, 2) && biome instanceof LOTRBiomeGenFarHaradJungle && ((LOTRBiomeGenFarHaradJungle)biome).hasJungleLakes()) {
                            variant = LOTRBiomeVariant.LAKE;
                        }
                        if (LOTRGenLayerBiomeVariantsLake.getFlag(lakeCode, 4) && biome instanceof LOTRBiomeGenFarHaradMangrove) {
                            variant = LOTRBiomeVariant.LAKE;
                        }
                    }
                }
                final int riverCode = variantsRiversInts[index2];
                if (riverCode == 2) {
                    variant = LOTRBiomeVariant.RIVER;
                }
                else if (riverCode == 1 && biome.getEnableRiver() && !structureNear && !mountainNear) {
                    variant = LOTRBiomeVariant.RIVER;
                }
                variants[index2] = variant;
            }
        }
        return variants;
    }
    
    public LOTRBiomeVariant getBiomeVariantAt(final int i, final int k) {
        final Chunk chunk = this.worldObj.getChunkFromBlockCoords(i, k);
        if (chunk != null) {
            final byte[] variants = LOTRBiomeVariantStorage.getChunkBiomeVariants(this.worldObj, chunk);
            if (variants != null) {
                if (variants.length == 256) {
                    final int chunkX = i & 0xF;
                    final int chunkZ = k & 0xF;
                    final byte variantID = variants[chunkX + chunkZ * 16];
                    return LOTRBiomeVariant.getVariantForID(variantID);
                }
                FMLLog.severe("Found chunk biome variant array of unexpected length " + variants.length, new Object[0]);
            }
        }
        if (!this.worldObj.isClient) {
            return this.getBiomeVariants(null, i, k, 1, 1)[0];
        }
        return LOTRBiomeVariant.STANDARD;
    }
    
    public boolean areBiomesViable(final int i, final int k, final int range, final List list) {
        LOTRIntCache.get(this.worldObj).resetIntCache();
        final int i2 = i - range >> 2;
        final int k2 = k - range >> 2;
        final int i3 = i + range >> 2;
        final int k3 = k + range >> 2;
        final int i4 = i3 - i2 + 1;
        final int k4 = k3 - k2 + 1;
        final int[] ints = this.chunkGenLayers[LOTRWorldChunkManager.LAYER_BIOME].getInts(this.worldObj, i2, k2, i4, k4);
        for (int l = 0; l < i4 * k4; ++l) {
            final BiomeGenBase biome = this.lotrDimension.biomeList[ints[l]];
            if (!list.contains(biome)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean areVariantsSuitableVillage(final int i, final int k, final int range, final boolean requireFlat) {
        final int i2 = i - range >> 2;
        final int k2 = k - range >> 2;
        final int i3 = i + range >> 2;
        final int k3 = k + range >> 2;
        final int i4 = i3 - i2 + 1;
        final int k4 = k3 - k2 + 1;
        final BiomeGenBase[] biomes = this.getBiomesForGeneration(null, i2, k2, i4, k4);
        final LOTRBiomeVariant[] variantsChunkGen;
        final LOTRBiomeVariant[] variants = variantsChunkGen = this.getVariantsChunkGen(null, i2, k2, i4, k4, biomes);
        for (final LOTRBiomeVariant v : variantsChunkGen) {
            if (v.hillFactor > 1.6f || (requireFlat && v.hillFactor > 1.0f)) {
                return false;
            }
            if (v.treeFactor > 1.0f) {
                return false;
            }
            if (v.disableVillages) {
                return false;
            }
            if (v.absoluteHeight && v.absoluteHeightLevel < 0.0f) {
                return false;
            }
        }
        return true;
    }
    
    public LOTRVillagePositionCache getVillageCache(final LOTRVillageGen village) {
        LOTRVillagePositionCache cache = this.villageCacheMap.get(village);
        if (cache == null) {
            cache = new LOTRVillagePositionCache();
            this.villageCacheMap.put(village, cache);
        }
        return cache;
    }
    
    public LOTRVillagePositionCache getStructureCache(final MapGenStructure structure) {
        LOTRVillagePositionCache cache = this.structureCacheMap.get(structure);
        if (cache == null) {
            cache = new LOTRVillagePositionCache();
            this.structureCacheMap.put(structure, cache);
        }
        return cache;
    }
    
    public ChunkPosition findBiomePosition(final int i, final int k, final int range, final List list, final Random random) {
        LOTRIntCache.get(this.worldObj).resetIntCache();
        final int i2 = i - range >> 2;
        final int k2 = k - range >> 2;
        final int i3 = i + range >> 2;
        final int k3 = k + range >> 2;
        final int i4 = i3 - i2 + 1;
        final int k4 = k3 - k2 + 1;
        final int[] ints = this.chunkGenLayers[LOTRWorldChunkManager.LAYER_BIOME].getInts(this.worldObj, i2, k2, i4, k4);
        ChunkPosition chunkpos = null;
        int j = 0;
        for (int l = 0; l < i4 * k4; ++l) {
            final int xPos = i2 + l % i4 << 2;
            final int zPos = k2 + l / i4 << 2;
            final BiomeGenBase biome = this.lotrDimension.biomeList[ints[l]];
            if (list.contains(biome) && (chunkpos == null || random.nextInt(j + 1) == 0)) {
                chunkpos = new ChunkPosition(xPos, 0, zPos);
                ++j;
            }
        }
        return chunkpos;
    }
    
    public void cleanupCache() {
        this.biomeCache.cleanupCache();
    }
    
    static {
        LOTRWorldChunkManager.LAYER_BIOME = 0;
        LOTRWorldChunkManager.LAYER_VARIANTS_LARGE = 1;
        LOTRWorldChunkManager.LAYER_VARIANTS_SMALL = 2;
        LOTRWorldChunkManager.LAYER_VARIANTS_LAKES = 3;
        LOTRWorldChunkManager.LAYER_VARIANTS_RIVERS = 4;
    }
}
