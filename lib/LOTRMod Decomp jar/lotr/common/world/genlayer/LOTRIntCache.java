// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.genlayer;

import java.util.Collection;
import net.minecraft.world.World;
import java.util.ArrayList;
import java.util.List;

public class LOTRIntCache
{
    private static LOTRIntCache SERVER;
    private static LOTRIntCache CLIENT;
    private int intCacheSize;
    private List<int[]> freeSmallArrays;
    private List<int[]> inUseSmallArrays;
    private List<int[]> freeLargeArrays;
    private List<int[]> inUseLargeArrays;
    
    public LOTRIntCache() {
        this.intCacheSize = 256;
        this.freeSmallArrays = new ArrayList<int[]>();
        this.inUseSmallArrays = new ArrayList<int[]>();
        this.freeLargeArrays = new ArrayList<int[]>();
        this.inUseLargeArrays = new ArrayList<int[]>();
    }
    
    public static LOTRIntCache get(final World world) {
        if (!world.isClient) {
            return LOTRIntCache.SERVER;
        }
        return LOTRIntCache.CLIENT;
    }
    
    public int[] getIntArray(final int size) {
        if (size <= 256) {
            if (this.freeSmallArrays.isEmpty()) {
                final int[] ints = new int[256];
                this.inUseSmallArrays.add(ints);
                return ints;
            }
            final int[] ints = this.freeSmallArrays.remove(this.freeSmallArrays.size() - 1);
            this.inUseSmallArrays.add(ints);
            return ints;
        }
        else {
            if (size > this.intCacheSize) {
                this.intCacheSize = size;
                this.freeLargeArrays.clear();
                this.inUseLargeArrays.clear();
                final int[] ints = new int[this.intCacheSize];
                this.inUseLargeArrays.add(ints);
                return ints;
            }
            if (this.freeLargeArrays.isEmpty()) {
                final int[] ints = new int[this.intCacheSize];
                this.inUseLargeArrays.add(ints);
                return ints;
            }
            final int[] ints = this.freeLargeArrays.remove(this.freeLargeArrays.size() - 1);
            this.inUseLargeArrays.add(ints);
            return ints;
        }
    }
    
    public void resetIntCache() {
        if (!this.freeLargeArrays.isEmpty()) {
            this.freeLargeArrays.remove(this.freeLargeArrays.size() - 1);
        }
        if (!this.freeSmallArrays.isEmpty()) {
            this.freeSmallArrays.remove(this.freeSmallArrays.size() - 1);
        }
        this.freeLargeArrays.addAll(this.inUseLargeArrays);
        this.freeSmallArrays.addAll(this.inUseSmallArrays);
        this.inUseLargeArrays.clear();
        this.inUseSmallArrays.clear();
    }
    
    public String getCacheSizes() {
        return "cache: " + this.freeLargeArrays.size() + ", tcache: " + this.freeSmallArrays.size() + ", allocated: " + this.inUseLargeArrays.size() + ", tallocated: " + this.inUseSmallArrays.size();
    }
    
    static {
        LOTRIntCache.SERVER = new LOTRIntCache();
        LOTRIntCache.CLIENT = new LOTRIntCache();
    }
}
