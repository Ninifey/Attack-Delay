// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.genlayer;

import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.biome.LOTRBiomeGenNearHaradOasis;
import net.minecraft.world.World;
import lotr.common.LOTRDimension;

public class LOTRGenLayerOasisLake extends LOTRGenLayer
{
    private LOTRDimension dimension;
    
    public LOTRGenLayerOasisLake(final long l, final LOTRGenLayer layer, final LOTRDimension dim) {
        super(l);
        super.lotrParent = layer;
        this.dimension = dim;
    }
    
    @Override
    public int[] getInts(final World world, final int i, final int k, final int xSize, final int zSize) {
        final int[] biomes = super.lotrParent.getInts(world, i - 1, k - 1, xSize + 2, zSize + 2);
        final int[] ints = LOTRIntCache.get(world).getIntArray(xSize * zSize);
        for (int k2 = 0; k2 < zSize; ++k2) {
            for (int i2 = 0; i2 < xSize; ++i2) {
                this.initChunkSeed((long)(i + i2), (long)(k + k2));
                final int biomeID = biomes[i2 + 1 + (k2 + 1) * (xSize + 2)];
                final LOTRBiome biome = this.dimension.biomeList[biomeID];
                int newBiomeID = biomeID;
                if (biome instanceof LOTRBiomeGenNearHaradOasis) {
                    boolean surrounded = true;
                    for (int i3 = -1; i3 <= 1; ++i3) {
                        for (int k3 = -1; k3 <= 1; ++k3) {
                            final int adjBiomeID = biomes[i2 + 1 + i3 + (k2 + 1 + k3) * (xSize + 2)];
                            final LOTRBiome adjBiome = this.dimension.biomeList[adjBiomeID];
                            if (!(adjBiome instanceof LOTRBiomeGenNearHaradOasis)) {
                                surrounded = false;
                            }
                        }
                    }
                    if (surrounded) {
                        newBiomeID = LOTRBiome.lake.biomeID;
                    }
                }
                ints[i2 + k2 * xSize] = newBiomeID;
            }
        }
        return ints;
    }
}
