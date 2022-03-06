// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.player.EntityPlayer;
import lotr.common.fac.LOTRFaction;

public interface LOTRHireableBase
{
    String getNPCName();
    
    LOTRFaction getFaction();
    
    boolean canTradeWith(final EntityPlayer p0);
    
    void onUnitTrade(final EntityPlayer p0);
}
