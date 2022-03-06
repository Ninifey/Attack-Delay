// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client;

import java.util.HashMap;
import lotr.common.LOTRLevelData;
import java.util.Iterator;
import lotr.common.LOTRDimension;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.fac.LOTRFaction;
import java.util.Map;

public class LOTRAlignmentTicker
{
    private static Map<LOTRFaction, LOTRAlignmentTicker> allFactionTickers;
    private final LOTRFaction theFac;
    private float oldAlign;
    private float newAlign;
    private int moveTick;
    private int prevMoveTick;
    private static final int moveTime = 20;
    public int flashTick;
    private static final int flashTime = 30;
    public int numericalTick;
    private static final int numericalTime = 200;
    
    public static LOTRAlignmentTicker forFaction(final LOTRFaction fac) {
        LOTRAlignmentTicker ticker = LOTRAlignmentTicker.allFactionTickers.get(fac);
        if (ticker == null) {
            ticker = new LOTRAlignmentTicker(fac);
            LOTRAlignmentTicker.allFactionTickers.put(fac, ticker);
        }
        return ticker;
    }
    
    public static void updateAll(final EntityPlayer entityplayer, final boolean forceInstant) {
        for (final LOTRDimension dim : LOTRDimension.values()) {
            for (final LOTRFaction fac : dim.factionList) {
                forFaction(fac).update(entityplayer, forceInstant);
            }
        }
    }
    
    public LOTRAlignmentTicker(final LOTRFaction f) {
        this.moveTick = 0;
        this.prevMoveTick = 0;
        this.theFac = f;
    }
    
    public void update(final EntityPlayer entityplayer, final boolean forceInstant) {
        final float curAlign = LOTRLevelData.getData(entityplayer).getAlignment(this.theFac);
        if (forceInstant) {
            final float n = curAlign;
            this.newAlign = n;
            this.oldAlign = n;
            final int n2 = 0;
            this.moveTick = n2;
            this.prevMoveTick = n2;
            this.flashTick = 0;
            this.numericalTick = 0;
        }
        else {
            if (this.newAlign != curAlign) {
                this.oldAlign = this.newAlign;
                this.newAlign = curAlign;
                final int n3 = 20;
                this.moveTick = n3;
                this.prevMoveTick = n3;
                this.flashTick = 30;
                this.numericalTick = 200;
            }
            this.prevMoveTick = this.moveTick;
            if (this.moveTick > 0) {
                --this.moveTick;
                if (this.moveTick <= 0) {
                    this.oldAlign = this.newAlign;
                }
            }
            if (this.flashTick > 0) {
                --this.flashTick;
            }
            if (this.numericalTick > 0) {
                --this.numericalTick;
            }
        }
    }
    
    public float getInterpolatedAlignment(final float f) {
        if (this.moveTick == 0) {
            return this.oldAlign;
        }
        float tickF = this.prevMoveTick + (this.moveTick - this.prevMoveTick) * f;
        tickF /= 20.0f;
        tickF = 1.0f - tickF;
        final float align = this.oldAlign + (this.newAlign - this.oldAlign) * tickF;
        return align;
    }
    
    static {
        LOTRAlignmentTicker.allFactionTickers = new HashMap<LOTRFaction, LOTRAlignmentTicker>();
    }
}
