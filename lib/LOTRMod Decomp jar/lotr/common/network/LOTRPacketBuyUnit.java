// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import net.minecraft.entity.Entity;
import lotr.common.entity.npc.LOTRUnitTradeEntry;
import lotr.common.entity.npc.LOTRHireableBase;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.util.LOTRLog;
import lotr.common.LOTRSquadrons;
import lotr.common.entity.npc.LOTRMercenaryTradeEntry;
import lotr.common.entity.npc.LOTRMercenary;
import lotr.common.entity.npc.LOTRUnitTradeable;
import lotr.common.inventory.LOTRContainerUnitTrade;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import com.google.common.base.Charsets;
import net.minecraft.util.StringUtils;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketBuyUnit implements IMessage
{
    private int tradeIndex;
    private String squadron;
    
    public LOTRPacketBuyUnit() {
    }
    
    public LOTRPacketBuyUnit(final int tr, final String s) {
        this.tradeIndex = tr;
        this.squadron = s;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeByte(this.tradeIndex);
        if (!StringUtils.isNullOrEmpty(this.squadron)) {
            final byte[] squadronBytes = this.squadron.getBytes(Charsets.UTF_8);
            data.writeInt(squadronBytes.length);
            data.writeBytes(squadronBytes);
        }
        else {
            data.writeInt(-1);
        }
    }
    
    public void fromBytes(final ByteBuf data) {
        this.tradeIndex = data.readByte();
        final int squadronLength = data.readInt();
        if (squadronLength > -1) {
            this.squadron = data.readBytes(squadronLength).toString(Charsets.UTF_8);
        }
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketBuyUnit, IMessage>
    {
        public IMessage onMessage(final LOTRPacketBuyUnit packet, final MessageContext context) {
            final EntityPlayer entityplayer = (EntityPlayer)context.getServerHandler().playerEntity;
            final World world = ((Entity)entityplayer).worldObj;
            final Container container = entityplayer.openContainer;
            if (container != null && container instanceof LOTRContainerUnitTrade) {
                final LOTRContainerUnitTrade tradeContainer = (LOTRContainerUnitTrade)container;
                final LOTRHireableBase unitTrader = tradeContainer.theUnitTrader;
                final int tradeIndex = packet.tradeIndex;
                LOTRUnitTradeEntry trade = null;
                if (unitTrader instanceof LOTRUnitTradeable) {
                    final LOTRUnitTradeEntry[] tradeList = ((LOTRUnitTradeable)unitTrader).getUnits().tradeEntries;
                    if (tradeIndex >= 0 && tradeIndex < tradeList.length) {
                        trade = tradeList[tradeIndex];
                    }
                }
                else if (unitTrader instanceof LOTRMercenary) {
                    trade = LOTRMercenaryTradeEntry.createFor((LOTRMercenary)unitTrader);
                }
                String squadron = packet.squadron;
                squadron = LOTRSquadrons.checkAcceptableLength(squadron);
                if (trade != null) {
                    trade.hireUnit(entityplayer, unitTrader, squadron);
                    if (unitTrader instanceof LOTRMercenary) {
                        entityplayer.closeScreen();
                    }
                }
                else {
                    LOTRLog.logger.error("LOTR: Error player " + entityplayer.getCommandSenderName() + " trying to hire unit from " + unitTrader.getNPCName() + " - trade is null or bad index!");
                }
            }
            return null;
        }
    }
}
