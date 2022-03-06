// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.genlayer;

import lotr.common.world.biome.LOTRBiome;
import net.minecraft.world.World;

public class LOTRGenLayerExtractMapRivers extends LOTRGenLayer
{
    public LOTRGenLayerExtractMapRivers(final long l, final LOTRGenLayer layer) {
        super(l);
        super.lotrParent = layer;
    }
    
    @Override
    public int[] getInts(final World world, final int i, final int k, final int xSize, final int zSize) {
        final int[] biomes = super.lotrParent.getInts(world, i, k, xSize, zSize);
        final int[] ints = LOTRIntCache.get(world).getIntArray(xSize * zSize);
        for (int k2 = 0; k2 < zSize; ++k2) {
            for (int i2 = 0; i2 < xSize; ++i2) {
                this.initChunkSeed((long)(i + i2), (long)(k + k2));
                final int biomeID = biomes[i2 + k2 * xSize];
                if (biomeID == LOTRBiome.river.biomeID) {
                    ints[i2 + k2 * xSize] = 2;
                }
                else {
                    ints[i2 + k2 * xSize] = 0;
                }
            }
        }
        return ints;
    }
}
