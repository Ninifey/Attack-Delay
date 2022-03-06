// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world;

import lotr.common.LOTRLevelData;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.chunk.IChunkProvider;
import lotr.common.LOTRDimension;

public class LOTRWorldProviderMiddleEarth extends LOTRWorldProvider
{
    @Override
    public LOTRDimension getLOTRDimension() {
        return LOTRDimension.MIDDLE_EARTH;
    }
    
    public IChunkProvider createChunkGenerator() {
        return (IChunkProvider)new LOTRChunkProvider(super.worldObj, super.worldObj.getSeed());
    }
    
    public ChunkCoordinates getSpawnPoint() {
        return new ChunkCoordinates(LOTRLevelData.middleEarthPortalX, LOTRLevelData.middleEarthPortalY, LOTRLevelData.middleEarthPortalZ);
    }
    
    public void setSpawnPoint(final int i, final int j, final int k) {
    }
    
    public void setRingPortalLocation(final int i, final int j, final int k) {
        LOTRLevelData.markMiddleEarthPortalLocation(i, j, k);
    }
}
