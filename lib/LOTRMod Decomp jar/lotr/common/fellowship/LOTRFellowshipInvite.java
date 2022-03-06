// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.fellowship;

import java.util.UUID;

public class LOTRFellowshipInvite
{
    public final UUID fellowshipID;
    public final UUID inviterID;
    
    public LOTRFellowshipInvite(final UUID fs, final UUID inviter) {
        this.fellowshipID = fs;
        this.inviterID = inviter;
    }
}
