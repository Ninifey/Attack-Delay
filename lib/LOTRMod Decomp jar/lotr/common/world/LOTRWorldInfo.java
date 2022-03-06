// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world;

import net.minecraft.world.storage.WorldInfo;
import net.minecraft.world.storage.DerivedWorldInfo;

public class LOTRWorldInfo extends DerivedWorldInfo
{
    private long lotrTotalTime;
    private long lotrWorldTime;
    
    public LOTRWorldInfo(final WorldInfo worldinfo) {
        super(worldinfo);
    }
    
    public long getWorldTotalTime() {
        return this.lotrTotalTime;
    }
    
    public long getWorldTime() {
        return this.lotrWorldTime;
    }
    
    public void incrementTotalWorldTime(final long time) {
    }
    
    public void setWorldTime(final long time) {
    }
    
    public void lotr_setTotalTime(final long time) {
        this.lotrTotalTime = time;
    }
    
    public void lotr_setWorldTime(final long time) {
        this.lotrWorldTime = time;
    }
}
