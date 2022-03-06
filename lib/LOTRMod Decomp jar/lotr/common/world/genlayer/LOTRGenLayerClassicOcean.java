// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.genlayer;

import net.minecraft.world.World;

public class LOTRGenLayerClassicOcean extends LOTRGenLayer
{
    public LOTRGenLayerClassicOcean(final long l) {
        super(l);
    }
    
    @Override
    public int[] getInts(final World world, final int i, final int k, final int xSize, final int zSize) {
        final int[] ints = LOTRIntCache.get(world).getIntArray(xSize * zSize);
        for (int k2 = 0; k2 < zSize; ++k2) {
            for (int i2 = 0; i2 < xSize; ++i2) {
                this.initChunkSeed((long)(i + i2), (long)(k + k2));
                final boolean ocean = this.nextInt(5) == 0;
                if (ocean) {
                    ints[i2 + k2 * xSize] = 1;
                }
                else {
                    ints[i2 + k2 * xSize] = 0;
                }
            }
        }
        return ints;
    }
}
