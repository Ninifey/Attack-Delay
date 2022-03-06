// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import lotr.common.entity.npc.LOTRUnitTradeEntries;
import lotr.common.entity.npc.LOTRUnitTradeEntry;
import lotr.common.entity.npc.LOTRMercenaryTradeEntry;
import lotr.common.entity.npc.LOTRHireableBase;
import net.minecraft.world.World;
import lotr.common.entity.npc.LOTRMercenary;
import net.minecraft.entity.player.EntityPlayer;

public class LOTRGuiMercenaryHire extends LOTRGuiHireBase
{
    public LOTRGuiMercenaryHire(final EntityPlayer entityplayer, final LOTRMercenary mercenary, final World world) {
        super(entityplayer, mercenary, world);
        final LOTRUnitTradeEntry e = LOTRMercenaryTradeEntry.createFor(mercenary);
        final LOTRUnitTradeEntries trades = new LOTRUnitTradeEntries(0.0f, new LOTRUnitTradeEntry[] { e });
        this.setTrades(trades);
    }
}
