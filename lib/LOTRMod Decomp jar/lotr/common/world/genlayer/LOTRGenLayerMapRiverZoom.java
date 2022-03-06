// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.genlayer;

import net.minecraft.world.World;

public class LOTRGenLayerMapRiverZoom extends LOTRGenLayer
{
    public LOTRGenLayerMapRiverZoom(final long l, final LOTRGenLayer layer) {
        super(l);
        super.lotrParent = layer;
    }
    
    @Override
    public int[] getInts(final World world, final int i, final int k, final int xSize, final int zSize) {
        final int i2 = (i >> 1) - 1;
        final int k2 = (k >> 1) - 1;
        final int xSizeZoom = (xSize >> 1) + 4;
        final int zSizeZoom = (zSize >> 1) + 4;
        final int[] rivers = super.lotrParent.getInts(world, i2, k2, xSizeZoom, zSizeZoom);
        final int i3 = xSizeZoom - 3 << 1;
        final int k3 = zSizeZoom - 3 << 1;
        final int[] ints = LOTRIntCache.get(world).getIntArray(i3 * k3);
        for (int k4 = 0; k4 < zSizeZoom - 3; ++k4) {
            for (int i4 = 0; i4 < xSizeZoom - 3; ++i4) {
                this.initChunkSeed((long)(i4 + (i2 + 1) << 1), (long)(k4 + (k2 + 1) << 1));
                for (int i5 = 0; i5 <= 1; ++i5) {
                    for (int k5 = 0; k5 <= 1; ++k5) {
                        int int00 = rivers[i4 + 1 + (k4 + 1) * xSizeZoom];
                        final int opp = (int00 == 0) ? 2 : 0;
                        boolean replaceCorner = false;
                        if (i5 == 0 && k5 == 0) {
                            replaceCorner = (rivers[i4 + 0 + (k4 + 0) * xSizeZoom] == opp && rivers[i4 + 1 + (k4 + 0) * xSizeZoom] == opp && rivers[i4 + 0 + (k4 + 1) * xSizeZoom] == opp);
                        }
                        if (i5 == 1 && k5 == 0) {
                            replaceCorner = (rivers[i4 + 1 + (k4 + 0) * xSizeZoom] == opp && rivers[i4 + 2 + (k4 + 0) * xSizeZoom] == opp && rivers[i4 + 2 + (k4 + 1) * xSizeZoom] == opp);
                        }
                        if (i5 == 0 && k5 == 1) {
                            replaceCorner = (rivers[i4 + 0 + (k4 + 1) * xSizeZoom] == opp && rivers[i4 + 0 + (k4 + 2) * xSizeZoom] == opp && rivers[i4 + 1 + (k4 + 2) * xSizeZoom] == opp);
                        }
                        if (i5 == 1 && k5 == 1) {
                            replaceCorner = (rivers[i4 + 2 + (k4 + 1) * xSizeZoom] == opp && rivers[i4 + 1 + (k4 + 2) * xSizeZoom] == opp && rivers[i4 + 2 + (k4 + 2) * xSizeZoom] == opp);
                        }
                        if (replaceCorner) {
                            int00 = opp;
                        }
                        final int index = (i4 << 1) + i5 + ((k4 << 1) + k5) * i3;
                        ints[index] = int00;
                    }
                }
            }
        }
        final int[] zoomedInts = LOTRIntCache.get(world).getIntArray(xSize * zSize);
        for (int k6 = 0; k6 < zSize; ++k6) {
            System.arraycopy(ints, (k6 + (k & 0x1)) * i3 + (i & 0x1), zoomedInts, k6 * xSize, xSize);
        }
        return zoomedInts;
    }
}
