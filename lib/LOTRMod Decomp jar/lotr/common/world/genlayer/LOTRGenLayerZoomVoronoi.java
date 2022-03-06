// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.genlayer;

import net.minecraft.world.World;

public class LOTRGenLayerZoomVoronoi extends LOTRGenLayer
{
    private int zoomScale;
    private double zoomDivisor;
    
    public LOTRGenLayerZoomVoronoi(final long seed, final LOTRGenLayer layer) {
        super(seed);
        this.zoomScale = 1024;
        this.zoomDivisor = this.zoomScale - 0.5;
        super.lotrParent = layer;
    }
    
    @Override
    public int[] getInts(final World world, int i, int k, final int xSize, final int zSize) {
        i -= 2;
        k -= 2;
        final int i2 = i >> 2;
        final int k2 = k >> 2;
        final int xSizeZoom = (xSize >> 2) + 2;
        final int zSizeZoom = (zSize >> 2) + 2;
        final int[] variants = super.lotrParent.getInts(world, i2, k2, xSizeZoom, zSizeZoom);
        final int i3 = xSizeZoom - 1 << 2;
        final int k3 = zSizeZoom - 1 << 2;
        final int[] ints = LOTRIntCache.get(world).getIntArray(i3 * k3);
        for (int k4 = 0; k4 < zSizeZoom - 1; ++k4) {
            int i4 = 0;
            int int00 = variants[i4 + 0 + (k4 + 0) * xSizeZoom];
            int int2 = variants[i4 + 0 + (k4 + 1) * xSizeZoom];
            while (i4 < xSizeZoom - 1) {
                final double d0 = 3.6;
                this.initChunkSeed((long)(i4 + i2 << 2), (long)(k4 + k2 << 2));
                final double d00_a = this.nextInt(this.zoomScale) / this.zoomDivisor * d0;
                final double d00_b = this.nextInt(this.zoomScale) / this.zoomDivisor * d0;
                this.initChunkSeed((long)(i4 + i2 + 1 << 2), (long)(k4 + k2 << 2));
                final double d10_a = this.nextInt(this.zoomScale) / this.zoomDivisor * d0 + 4.0;
                final double d10_b = this.nextInt(this.zoomScale) / this.zoomDivisor * d0;
                this.initChunkSeed((long)(i4 + i2 << 2), (long)(k4 + k2 + 1 << 2));
                final double d01_a = this.nextInt(this.zoomScale) / this.zoomDivisor * d0;
                final double d01_b = this.nextInt(this.zoomScale) / this.zoomDivisor * d0 + 4.0;
                this.initChunkSeed((long)(i4 + i2 + 1 << 2), (long)(k4 + k2 + 1 << 2));
                final double d11_a = this.nextInt(this.zoomScale) / this.zoomDivisor * d0 + 4.0;
                final double d11_b = this.nextInt(this.zoomScale) / this.zoomDivisor * d0 + 4.0;
                final int int3 = variants[i4 + 1 + (k4 + 0) * xSizeZoom];
                final int int4 = variants[i4 + 1 + (k4 + 1) * xSizeZoom];
                for (int k5 = 0; k5 < 4; ++k5) {
                    int index = ((k4 << 2) + k5) * i3 + (i4 << 2);
                    for (int i5 = 0; i5 < 4; ++i5) {
                        final double d2 = (k5 - d00_b) * (k5 - d00_b) + (i5 - d00_a) * (i5 - d00_a);
                        final double d3 = (k5 - d10_b) * (k5 - d10_b) + (i5 - d10_a) * (i5 - d10_a);
                        final double d4 = (k5 - d01_b) * (k5 - d01_b) + (i5 - d01_a) * (i5 - d01_a);
                        final double d5 = (k5 - d11_b) * (k5 - d11_b) + (i5 - d11_a) * (i5 - d11_a);
                        if (d2 < d3 && d2 < d4 && d2 < d5) {
                            ints[index] = int00;
                            ++index;
                        }
                        else if (d3 < d2 && d3 < d4 && d3 < d5) {
                            ints[index] = int3;
                            ++index;
                        }
                        else if (d4 < d2 && d4 < d3 && d4 < d5) {
                            ints[index] = int2;
                            ++index;
                        }
                        else {
                            ints[index] = int4;
                            ++index;
                        }
                    }
                }
                int00 = int3;
                int2 = int4;
                ++i4;
            }
        }
        final int[] zoomedInts = LOTRIntCache.get(world).getIntArray(xSize * zSize);
        for (int k6 = 0; k6 < zSize; ++k6) {
            System.arraycopy(ints, (k6 + (k & 0x3)) * i3 + (i & 0x3), zoomedInts, k6 * xSize, xSize);
        }
        return zoomedInts;
    }
}
