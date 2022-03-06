// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.genlayer;

import net.minecraft.world.World;

public class LOTRGenLayerIncludeMapRivers extends LOTRGenLayer
{
    private LOTRGenLayer riverLayer;
    private LOTRGenLayer mapRiverLayer;
    
    public LOTRGenLayerIncludeMapRivers(final long l, final LOTRGenLayer rivers, final LOTRGenLayer mapRivers) {
        super(l);
        this.riverLayer = rivers;
        this.mapRiverLayer = mapRivers;
    }
    
    @Override
    public void initWorldGenSeed(final long l) {
        super.initWorldGenSeed(l);
        this.riverLayer.initWorldGenSeed(l);
        this.mapRiverLayer.initWorldGenSeed(l);
    }
    
    @Override
    public int[] getInts(final World world, final int i, final int k, final int xSize, final int zSize) {
        final int[] rivers = this.riverLayer.getInts(world, i, k, xSize, zSize);
        final int[] mapRivers = this.mapRiverLayer.getInts(world, i, k, xSize, zSize);
        final int[] ints = LOTRIntCache.get(world).getIntArray(xSize * zSize);
        for (int k2 = 0; k2 < zSize; ++k2) {
            for (int i2 = 0; i2 < xSize; ++i2) {
                this.initChunkSeed((long)(i + i2), (long)(k + k2));
                final int index = i2 + k2 * xSize;
                final int isRiver = rivers[index];
                final int isMapRiver = mapRivers[index];
                if (isMapRiver == 2) {
                    ints[index] = isMapRiver;
                }
                else {
                    ints[index] = isRiver;
                }
            }
        }
        return ints;
    }
}
