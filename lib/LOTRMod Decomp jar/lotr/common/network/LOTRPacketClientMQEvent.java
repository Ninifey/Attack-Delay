// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import lotr.common.LOTRPlayerData;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.quest.LOTRMiniQuestEvent;
import lotr.common.LOTRLevelData;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketClientMQEvent implements IMessage
{
    private ClientMQEvent type;
    
    public LOTRPacketClientMQEvent() {
    }
    
    public LOTRPacketClientMQEvent(final ClientMQEvent t) {
        this.type = t;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeByte(this.type.ordinal());
    }
    
    public void fromBytes(final ByteBuf data) {
        final int typeID = data.readByte();
        if (typeID >= 0 && typeID < ClientMQEvent.values().length) {
            this.type = ClientMQEvent.values()[typeID];
        }
    }
    
    public enum ClientMQEvent
    {
        MAP, 
        FACTIONS;
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketClientMQEvent, IMessage>
    {
        public IMessage onMessage(final LOTRPacketClientMQEvent packet, final MessageContext context) {
            final EntityPlayer entityplayer = (EntityPlayer)context.getServerHandler().playerEntity;
            final LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
            if (packet.type == ClientMQEvent.MAP) {
                pd.distributeMQEvent(new LOTRMiniQuestEvent.ViewMap());
            }
            else if (packet.type == ClientMQEvent.FACTIONS) {
                pd.distributeMQEvent(new LOTRMiniQuestEvent.ViewFactions());
            }
            return null;
        }
    }
}
