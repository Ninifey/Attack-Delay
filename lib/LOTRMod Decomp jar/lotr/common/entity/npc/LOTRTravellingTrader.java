// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.player.EntityPlayer;

public interface LOTRTravellingTrader extends LOTRTradeable
{
    void startTraderVisiting(final EntityPlayer p0);
    
    LOTREntityNPC createTravellingEscort();
    
    String getDepartureSpeech();
}
