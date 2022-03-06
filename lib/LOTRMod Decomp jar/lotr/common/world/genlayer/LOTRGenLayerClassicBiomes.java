// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.genlayer;

import java.util.List;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.world.World;
import lotr.common.LOTRDimension;

public class LOTRGenLayerClassicBiomes extends LOTRGenLayer
{
    private LOTRDimension dimension;
    
    public LOTRGenLayerClassicBiomes(final long l, final LOTRGenLayer layer, final LOTRDimension dim) {
        super(l);
        super.lotrParent = layer;
        this.dimension = dim;
    }
    
    @Override
    public int[] getInts(final World world, final int i, final int k, final int xSize, final int zSize) {
        final int[] oceans = super.lotrParent.getInts(world, i, k, xSize, zSize);
        final int[] ints = LOTRIntCache.get(world).getIntArray(xSize * zSize);
        for (int k2 = 0; k2 < zSize; ++k2) {
            for (int i2 = 0; i2 < xSize; ++i2) {
                this.initChunkSeed((long)(i + i2), (long)(k + k2));
                final int isOcean = oceans[i2 + k2 * xSize];
                int biomeID;
                if (isOcean == 1) {
                    biomeID = LOTRBiome.ocean.biomeID;
                }
                else {
                    final List<LOTRBiome> biomeList = this.dimension.majorBiomes;
                    final int randIndex = this.nextInt(biomeList.size());
                    final LOTRBiome biome = biomeList.get(randIndex);
                    biomeID = biome.biomeID;
                }
                ints[i2 + k2 * xSize] = biomeID;
            }
        }
        return ints;
    }
}
