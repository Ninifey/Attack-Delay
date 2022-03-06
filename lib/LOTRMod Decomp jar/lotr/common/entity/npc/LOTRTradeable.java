// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.fac.LOTRFaction;

public interface LOTRTradeable
{
    String getNPCName();
    
    LOTRFaction getFaction();
    
    LOTRTradeEntries getBuyPool();
    
    LOTRTradeEntries getSellPool();
    
    boolean canTradeWith(final EntityPlayer p0);
    
    void onPlayerTrade(final EntityPlayer p0, final LOTRTradeEntries.TradeType p1, final ItemStack p2);
    
    boolean shouldTraderRespawn();
    
    public interface Smith extends LOTRTradeable
    {
    }
    
    public interface Bartender extends LOTRTradeable
    {
    }
}
