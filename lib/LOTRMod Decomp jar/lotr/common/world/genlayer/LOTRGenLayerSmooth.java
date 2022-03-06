// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.genlayer;

import net.minecraft.world.World;

public class LOTRGenLayerSmooth extends LOTRGenLayer
{
    public LOTRGenLayerSmooth(final long l, final LOTRGenLayer layer) {
        super(l);
        super.lotrParent = layer;
    }
    
    @Override
    public int[] getInts(final World world, final int i, final int k, final int xSize, final int zSize) {
        final int i2 = i - 1;
        final int k2 = k - 1;
        final int xSizeP = xSize + 2;
        final int zSizeP = zSize + 2;
        final int[] biomes = super.lotrParent.getInts(world, i2, k2, xSizeP, zSizeP);
        final int[] ints = LOTRIntCache.get(world).getIntArray(xSize * zSize);
        for (int k3 = 0; k3 < zSize; ++k3) {
            for (int i3 = 0; i3 < xSize; ++i3) {
                int centre = biomes[i3 + 1 + (k3 + 1) * xSizeP];
                final int xn = biomes[i3 + 0 + (k3 + 1) * xSizeP];
                final int xp = biomes[i3 + 2 + (k3 + 1) * xSizeP];
                final int zn = biomes[i3 + 1 + (k3 + 0) * xSizeP];
                final int zp = biomes[i3 + 1 + (k3 + 2) * xSizeP];
                if (xn == xp && zn == zp) {
                    this.initChunkSeed((long)(i3 + i), (long)(k3 + k));
                    if (this.nextInt(2) == 0) {
                        centre = xn;
                    }
                    else {
                        centre = zn;
                    }
                }
                else {
                    if (xn == xp) {
                        centre = xn;
                    }
                    if (zn == zp) {
                        centre = zn;
                    }
                }
                ints[i3 + k3 * xSize] = centre;
            }
        }
        return ints;
    }
}
