// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.genlayer;

import net.minecraft.world.World;

public class LOTRGenLayerNarrowRivers extends LOTRGenLayer
{
    private final int maxRange;
    
    public LOTRGenLayerNarrowRivers(final long l, final LOTRGenLayer layer, final int r) {
        super(l);
        super.lotrParent = layer;
        this.maxRange = r;
    }
    
    @Override
    public int[] getInts(final World world, final int i, final int k, final int xSize, final int zSize) {
        final int[] rivers = super.lotrParent.getInts(world, i - this.maxRange, k - this.maxRange, xSize + this.maxRange * 2, zSize + this.maxRange * 2);
        final int[] ints = LOTRIntCache.get(world).getIntArray(xSize * zSize);
        for (int k2 = 0; k2 < zSize; ++k2) {
            for (int i2 = 0; i2 < xSize; ++i2) {
                this.initChunkSeed((long)(i + i2), (long)(k + k2));
                int isRiver = rivers[i2 + this.maxRange + (k2 + this.maxRange) * (xSize + this.maxRange * 2)];
                Label_0254: {
                    if (isRiver > 0) {
                        for (int range = 1; range <= this.maxRange; ++range) {
                            for (int k3 = k2 - range; k3 <= k2 + range; ++k3) {
                                for (int i3 = i2 - range; i3 <= i2 + range; ++i3) {
                                    if (Math.abs(i3 - i2) == range || Math.abs(k3 - k2) == range) {
                                        final int subRiver = rivers[i3 + this.maxRange + (k3 + this.maxRange) * (xSize + this.maxRange * 2)];
                                        if (subRiver == 0) {
                                            isRiver = 0;
                                            break Label_0254;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                ints[i2 + k2 * xSize] = isRiver;
            }
        }
        return ints;
    }
}
