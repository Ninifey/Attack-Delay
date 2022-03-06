// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.genlayer;

import net.minecraft.world.World;

public class LOTRGenLayerZoom extends LOTRGenLayer
{
    public LOTRGenLayerZoom(final long l, final LOTRGenLayer layer) {
        super(l);
        super.lotrParent = layer;
    }
    
    @Override
    public int[] getInts(final World world, final int i, final int k, final int xSize, final int zSize) {
        final int i2 = i >> 1;
        final int k2 = k >> 1;
        final int xSizeZoom = (xSize >> 1) + 2;
        final int zSizeZoom = (zSize >> 1) + 2;
        final int[] variants = super.lotrParent.getInts(world, i2, k2, xSizeZoom, zSizeZoom);
        final int i3 = xSizeZoom - 1 << 1;
        final int k3 = zSizeZoom - 1 << 1;
        final int[] ints = LOTRIntCache.get(world).getIntArray(i3 * k3);
        for (int k4 = 0; k4 < zSizeZoom - 1; ++k4) {
            for (int i4 = 0; i4 < xSizeZoom - 1; ++i4) {
                this.initChunkSeed((long)(i4 + i2 << 1), (long)(k4 + k2 << 1));
                final int int00 = variants[i4 + 0 + (k4 + 0) * xSizeZoom];
                final int int2 = variants[i4 + 0 + (k4 + 1) * xSizeZoom];
                final int int3 = variants[i4 + 1 + (k4 + 0) * xSizeZoom];
                final int int4 = variants[i4 + 1 + (k4 + 1) * xSizeZoom];
                final int index = (i4 << 1) + (k4 << 1) * i3;
                ints[index] = int00;
                ints[index + 1] = this.func_151619_a(new int[] { int00, int3 });
                ints[index + i3] = this.func_151619_a(new int[] { int00, int2 });
                ints[index + i3 + 1] = this.func_151617_b(int00, int3, int2, int4);
            }
        }
        final int[] zoomedInts = LOTRIntCache.get(world).getIntArray(xSize * zSize);
        for (int k5 = 0; k5 < zSize; ++k5) {
            System.arraycopy(ints, (k5 + (k & 0x1)) * i3 + (i & 0x1), zoomedInts, k5 * xSize, xSize);
        }
        return zoomedInts;
    }
    
    public static LOTRGenLayer magnify(final long seed, final LOTRGenLayer source, final int zooms) {
        LOTRGenLayer layer = source;
        for (int i = 0; i < zooms; ++i) {
            layer = new LOTRGenLayerZoom(seed + i, layer);
        }
        return layer;
    }
}
