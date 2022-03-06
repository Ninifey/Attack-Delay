// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.genlayer;

import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.biome.LOTRBiomeGenNearHarad;
import net.minecraft.world.World;
import lotr.common.LOTRDimension;

public class LOTRGenLayerNearHaradRiverbanks extends LOTRGenLayer
{
    private LOTRGenLayer biomeLayer;
    private LOTRGenLayer mapRiverLayer;
    private LOTRDimension dimension;
    
    public LOTRGenLayerNearHaradRiverbanks(final long l, final LOTRGenLayer biomes, final LOTRGenLayer rivers, final LOTRDimension dim) {
        super(l);
        this.biomeLayer = biomes;
        this.mapRiverLayer = rivers;
        this.dimension = dim;
    }
    
    @Override
    public void initWorldGenSeed(final long l) {
        super.initWorldGenSeed(l);
        this.biomeLayer.initWorldGenSeed(l);
        this.mapRiverLayer.initWorldGenSeed(l);
    }
    
    @Override
    public int[] getInts(final World world, final int i, final int k, final int xSize, final int zSize) {
        final int[] biomes = this.biomeLayer.getInts(world, i - 2, k - 2, xSize + 3, zSize + 3);
        final int[] mapRivers = this.mapRiverLayer.getInts(world, i - 2, k - 2, xSize + 3, zSize + 3);
        final int[] ints = LOTRIntCache.get(world).getIntArray(xSize * zSize);
        for (int k2 = 0; k2 < zSize; ++k2) {
            for (int i2 = 0; i2 < xSize; ++i2) {
                this.initChunkSeed((long)(i + i2), (long)(k + k2));
                final int biomeID = biomes[i2 + 2 + (k2 + 2) * (xSize + 3)];
                final LOTRBiome biome = this.dimension.biomeList[biomeID];
                int newBiomeID = biomeID;
                if (biome instanceof LOTRBiomeGenNearHarad) {
                    boolean adjRiver = false;
                    for (int i3 = -2; i3 <= 1; ++i3) {
                        for (int k3 = -2; k3 <= 1; ++k3) {
                            if (i3 == -2 || k3 == -2 || i3 == 1 || k3 == 1) {}
                            final int adjRiverCode = mapRivers[i2 + 2 + i3 + (k2 + 2 + k3) * (xSize + 3)];
                            if (adjRiverCode == 2) {
                                adjRiver = true;
                            }
                        }
                    }
                    if (adjRiver) {
                        newBiomeID = LOTRBiome.nearHaradRiverbank.biomeID;
                    }
                }
                ints[i2 + k2 * xSize] = newBiomeID;
            }
        }
        return ints;
    }
}
