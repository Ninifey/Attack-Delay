// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.genlayer;

import net.minecraft.world.World;

public class LOTRGenLayerBiomeVariants extends LOTRGenLayer
{
    public static int RANDOM_MAX;
    
    public LOTRGenLayerBiomeVariants(final long l) {
        super(l);
    }
    
    @Override
    public int[] getInts(final World world, final int i, final int k, final int xSize, final int zSize) {
        final int[] ints = LOTRIntCache.get(world).getIntArray(xSize * zSize);
        for (int k2 = 0; k2 < zSize; ++k2) {
            for (int i2 = 0; i2 < xSize; ++i2) {
                this.initChunkSeed((long)(i + i2), (long)(k + k2));
                ints[i2 + k2 * xSize] = this.nextInt(LOTRGenLayerBiomeVariants.RANDOM_MAX);
            }
        }
        return ints;
    }
    
    static {
        LOTRGenLayerBiomeVariants.RANDOM_MAX = 10000;
    }
}
