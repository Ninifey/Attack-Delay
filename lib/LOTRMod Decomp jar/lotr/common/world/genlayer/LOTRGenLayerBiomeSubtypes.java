// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.genlayer;

import lotr.common.world.biome.LOTRBiome;
import net.minecraft.world.World;

public class LOTRGenLayerBiomeSubtypes extends LOTRGenLayer
{
    private LOTRGenLayer biomeLayer;
    private LOTRGenLayer variantsLayer;
    
    public LOTRGenLayerBiomeSubtypes(final long l, final LOTRGenLayer biomes, final LOTRGenLayer subtypes) {
        super(l);
        this.biomeLayer = biomes;
        this.variantsLayer = subtypes;
    }
    
    @Override
    public void initWorldGenSeed(final long l) {
        this.biomeLayer.initWorldGenSeed(l);
        this.variantsLayer.initWorldGenSeed(l);
        super.initWorldGenSeed(l);
    }
    
    @Override
    public int[] getInts(final World world, final int i, final int k, final int xSize, final int zSize) {
        final int[] biomes = this.biomeLayer.getInts(world, i, k, xSize, zSize);
        final int[] variants = this.variantsLayer.getInts(world, i, k, xSize, zSize);
        final int[] ints = LOTRIntCache.get(world).getIntArray(xSize * zSize);
        for (int k2 = 0; k2 < zSize; ++k2) {
            for (int i2 = 0; i2 < xSize; ++i2) {
                this.initChunkSeed((long)(i + i2), (long)(k + k2));
                final int biome = biomes[i2 + k2 * xSize];
                final int variant = variants[i2 + k2 * xSize];
                int newBiome = biome;
                if (biome == LOTRBiome.shire.biomeID && variant < 15 && variant != 0) {
                    newBiome = LOTRBiome.shireWoodlands.biomeID;
                }
                else if (biome == LOTRBiome.forodwaithMountains.biomeID && variant < 5) {
                    newBiome = LOTRBiome.forodwaithGlacier.biomeID;
                }
                else if (biome == LOTRBiome.farHarad.biomeID && variant < 20) {
                    newBiome = LOTRBiome.farHaradForest.biomeID;
                }
                else if (biome == LOTRBiome.farHaradJungle.biomeID && variant < 15) {
                    newBiome = LOTRBiome.tauredainClearing.biomeID;
                }
                else if (biome == LOTRBiome.pertorogwaith.biomeID && variant < 15) {
                    newBiome = LOTRBiome.farHaradVolcano.biomeID;
                }
                else if (biome == LOTRBiome.ocean.biomeID && variant < 2) {
                    newBiome = LOTRBiome.island.biomeID;
                }
                if (newBiome != biome) {
                    ints[i2 + k2 * xSize] = newBiome;
                }
                else {
                    ints[i2 + k2 * xSize] = biome;
                }
            }
        }
        return ints;
    }
}
