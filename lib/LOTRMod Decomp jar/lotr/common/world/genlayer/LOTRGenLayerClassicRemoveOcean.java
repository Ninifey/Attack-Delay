// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.genlayer;

import net.minecraft.world.World;

public class LOTRGenLayerClassicRemoveOcean extends LOTRGenLayer
{
    public LOTRGenLayerClassicRemoveOcean(final long l, final LOTRGenLayer layer) {
        super(l);
        super.lotrParent = layer;
    }
    
    @Override
    public int[] getInts(final World world, final int i, final int k, final int xSize, final int zSize) {
        final int[] oceans = super.lotrParent.getInts(world, i, k, xSize, zSize);
        final int[] ints = LOTRIntCache.get(world).getIntArray(xSize * zSize);
        for (int k2 = 0; k2 < zSize; ++k2) {
            for (int i2 = 0; i2 < xSize; ++i2) {
                this.initChunkSeed((long)(i + i2), (long)(k + k2));
                int isOcean = oceans[i2 + k2 * xSize];
                if (Math.abs(i + i2) <= 1 && Math.abs(k + k2) <= 1) {
                    isOcean = 0;
                }
                ints[i2 + k2 * xSize] = isOcean;
            }
        }
        return ints;
    }
}
