// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.village;

import java.util.HashMap;
import net.minecraft.world.ChunkCoordIntPair;
import java.util.Map;

public class LOTRVillagePositionCache
{
    private Map<ChunkCoordIntPair, Result> cacheMap;
    private static final int MAX_SIZE = 20000;
    
    public LOTRVillagePositionCache() {
        this.cacheMap = new HashMap<ChunkCoordIntPair, Result>();
    }
    
    public void markResult(final int chunkX, final int chunkZ, final boolean flag) {
        if (this.cacheMap.size() >= 20000) {
            this.clearCache();
        }
        this.cacheMap.put(this.getChunkKey(chunkX, chunkZ), flag ? Result.TRUE : Result.FALSE);
    }
    
    public boolean isVillageAt(final int chunkX, final int chunkZ) {
        return this.cacheMap.get(this.getChunkKey(chunkX, chunkZ)) == Result.TRUE;
    }
    
    public boolean isVillageNotAt(final int chunkX, final int chunkZ) {
        return this.cacheMap.get(this.getChunkKey(chunkX, chunkZ)) == Result.FALSE;
    }
    
    private ChunkCoordIntPair getChunkKey(final int chunkX, final int chunkZ) {
        return new ChunkCoordIntPair(chunkX, chunkZ);
    }
    
    public void clearCache() {
        this.cacheMap.clear();
    }
    
    private enum Result
    {
        NONE, 
        TRUE, 
        FALSE;
    }
}
