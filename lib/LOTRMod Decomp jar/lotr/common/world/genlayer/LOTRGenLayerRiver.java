// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.genlayer;

import net.minecraft.world.World;

public class LOTRGenLayerRiver extends LOTRGenLayer
{
    public static final int RANDOM_RIVER = 1;
    public static final int MAP_RIVER = 2;
    
    public LOTRGenLayerRiver(final long l, final LOTRGenLayer layer) {
        super(l);
        super.lotrParent = layer;
    }
    
    @Override
    public int[] getInts(final World world, final int i, final int k, final int xSize, final int zSize) {
        final int i2 = i - 1;
        final int k2 = k - 1;
        final int i3 = xSize + 2;
        final int k3 = zSize + 2;
        final int[] riverInit = super.lotrParent.getInts(world, i2, k2, i3, k3);
        final int[] ints = LOTRIntCache.get(world).getIntArray(xSize * zSize);
        for (int k4 = 0; k4 < zSize; ++k4) {
            for (int i4 = 0; i4 < xSize; ++i4) {
                final int centre = riverInit[i4 + 1 + (k4 + 1) * i3];
                final int xn = riverInit[i4 + 0 + (k4 + 1) * i3];
                final int xp = riverInit[i4 + 2 + (k4 + 1) * i3];
                final int zn = riverInit[i4 + 1 + (k4 + 0) * i3];
                final int zp = riverInit[i4 + 1 + (k4 + 2) * i3];
                if (centre == xn && centre == zn && centre == xp && centre == zp) {
                    ints[i4 + k4 * xSize] = 0;
                }
                else {
                    ints[i4 + k4 * xSize] = 1;
                }
            }
        }
        return ints;
    }
}
