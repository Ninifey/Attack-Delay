// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.genlayer;

import net.minecraft.world.World;
import com.google.common.math.IntMath;

public class LOTRGenLayerBiomeVariantsLake extends LOTRGenLayer
{
    public static final int FLAG_LAKE = 1;
    public static final int FLAG_JUNGLE = 2;
    public static final int FLAG_MANGROVE = 4;
    private int zoomScale;
    private int lakeFlags;
    
    public LOTRGenLayerBiomeVariantsLake(final long l, final LOTRGenLayer layer, final int i) {
        super(l);
        this.lakeFlags = 0;
        super.lotrParent = layer;
        this.zoomScale = IntMath.pow(2, i);
    }
    
    public LOTRGenLayerBiomeVariantsLake setLakeFlags(final int... flags) {
        for (final int f : flags) {
            this.lakeFlags = setFlag(this.lakeFlags, f);
        }
        return this;
    }
    
    public static int setFlag(int param, final int flag) {
        param |= flag;
        return param;
    }
    
    public static boolean getFlag(final int param, final int flag) {
        return (param & flag) == flag;
    }
    
    @Override
    public int[] getInts(final World world, final int i, final int k, final int xSize, final int zSize) {
        final int[] baseInts = (int[])((super.lotrParent == null) ? null : super.lotrParent.getInts(world, i, k, xSize, zSize));
        final int[] ints = LOTRIntCache.get(world).getIntArray(xSize * zSize);
        for (int k2 = 0; k2 < zSize; ++k2) {
            for (int i2 = 0; i2 < xSize; ++i2) {
                this.initChunkSeed((long)(i + i2), (long)(k + k2));
                int baseInt = (baseInts == null) ? 0 : baseInts[i2 + k2 * xSize];
                if (getFlag(this.lakeFlags, 1) && this.nextInt(30 * this.zoomScale * this.zoomScale * this.zoomScale) == 2) {
                    baseInt = setFlag(baseInt, 1);
                }
                if (getFlag(this.lakeFlags, 2) && this.nextInt(12) == 3) {
                    baseInt = setFlag(baseInt, 2);
                }
                if (getFlag(this.lakeFlags, 4) && this.nextInt(10) == 1) {
                    baseInt = setFlag(baseInt, 4);
                }
                ints[i2 + k2 * xSize] = baseInt;
            }
        }
        return ints;
    }
}
