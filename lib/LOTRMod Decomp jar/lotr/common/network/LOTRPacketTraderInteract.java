// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayerMP;
import lotr.common.LOTRMod;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTRTradeable;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketTraderInteract implements IMessage
{
    private int traderID;
    private int traderAction;
    
    public LOTRPacketTraderInteract() {
    }
    
    public LOTRPacketTraderInteract(final int idt, final int a) {
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
    
    public static class Handler implements IMessageHandler<LOTRPacketTraderInteract, IMessage>
    {
        public IMessage onMessage(final LOTRPacketTraderInteract packet, final MessageContext context) {
            final EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            final World world = ((Entity)entityplayer).worldObj;
            final Entity trader = world.getEntityByID(packet.traderID);
            if (trader != null && trader instanceof LOTRTradeable) {
                final LOTRTradeable tradeableTrader = (LOTRTradeable)trader;
                final LOTREntityNPC livingTrader = (LOTREntityNPC)trader;
                final int action = packet.traderAction;
                boolean closeScreen = false;
                if (action == 0) {
                    livingTrader.npcTalkTick = livingTrader.getNPCTalkInterval();
                    closeScreen = livingTrader.interactFirst((EntityPlayer)entityplayer);
                }
                else if (action == 1 && tradeableTrader.canTradeWith((EntityPlayer)entityplayer)) {
                    entityplayer.openGui((Object)LOTRMod.instance, 3, world, livingTrader.getEntityId(), 0, 0);
                }
                else if (action == 2 && tradeableTrader.canTradeWith((EntityPlayer)entityplayer)) {
                    entityplayer.openGui((Object)LOTRMod.instance, 35, world, livingTrader.getEntityId(), 0, 0);
                }
                else if (action == 3 && tradeableTrader.canTradeWith((EntityPlayer)entityplayer) && tradeableTrader instanceof LOTRTradeable.Smith) {
                    entityplayer.openGui((Object)LOTRMod.instance, 54, world, livingTrader.getEntityId(), 0, 0);
                }
                if (closeScreen) {
                    entityplayer.closeScreen();
                }
            }
            return null;
        }
    }
}
