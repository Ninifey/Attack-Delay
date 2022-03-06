// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.gui;

import lotr.common.entity.npc.LOTRHireableBase;
import net.minecraft.world.World;
import lotr.common.entity.npc.LOTRUnitTradeable;
import net.minecraft.entity.player.EntityPlayer;

public class LOTRGuiUnitTrade extends LOTRGuiHireBase
{
    public LOTRGuiUnitTrade(final EntityPlayer entityplayer, final LOTRUnitTradeable trader, final World world) {
        super(entityplayer, trader, world);
        this.setTrades(trader.getUnits());
    }
}
