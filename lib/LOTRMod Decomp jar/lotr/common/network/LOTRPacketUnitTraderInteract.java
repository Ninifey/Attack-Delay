// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayerMP;
import lotr.common.entity.npc.LOTRHireableBase;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.player.EntityPlayer;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketUnitTraderInteract implements IMessage
{
    private int traderID;
    private int traderAction;
    
    public LOTRPacketUnitTraderInteract() {
    }
    
    public LOTRPacketUnitTraderInteract(final int idt, final int a) {
        this.traderID = idt;
        this.traderAction = a;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeInt(this.traderID);
        data.writeByte(this.traderAction);
    }
    
    public void fromBytes(final ByteBuf data) {
        this.traderID = data.readInt();
        this.traderAction = data.readByte();
    }
    
    protected void openTradeGUI(final EntityPlayer entityplayer, final LOTREntityNPC trader) {
        entityplayer.openGui((Object)LOTRMod.instance, 7, ((Entity)entityplayer).worldObj, trader.getEntityId(), 0, 0);
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketUnitTraderInteract, IMessage>
    {
        public IMessage onMessage(final LOTRPacketUnitTraderInteract packet, final MessageContext context) {
            final EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            final World world = ((Entity)entityplayer).worldObj;
            final Entity trader = world.getEntityByID(packet.traderID);
            if (trader != null && trader instanceof LOTRHireableBase) {
                final LOTRHireableBase tradeableTrader = (LOTRHireableBase)trader;
                final LOTREntityNPC livingTrader = (LOTREntityNPC)trader;
                final int action = packet.traderAction;
                boolean closeScreen = false;
                if (action == 0) {
                    livingTrader.npcTalkTick = livingTrader.getNPCTalkInterval();
                    closeScreen = livingTrader.interactFirst((EntityPlayer)entityplayer);
                }
                else if (action == 1 && tradeableTrader.canTradeWith((EntityPlayer)entityplayer)) {
                    packet.openTradeGUI((EntityPlayer)entityplayer, livingTrader);
                }
                if (closeScreen) {
                    entityplayer.closeScreen();
                }
            }
            return null;
        }
    }
}
