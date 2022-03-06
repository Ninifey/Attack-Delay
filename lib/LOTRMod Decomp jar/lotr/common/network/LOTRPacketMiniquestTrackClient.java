// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import lotr.common.LOTRPlayerData;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import io.netty.buffer.ByteBuf;
import java.util.UUID;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketMiniquestTrackClient implements IMessage
{
    private UUID questID;
    
    public LOTRPacketMiniquestTrackClient() {
    }
    
    public LOTRPacketMiniquestTrackClient(final UUID uuid) {
        this.questID = uuid;
    }
    
    public void toBytes(final ByteBuf data) {
        final boolean hasQuest = this.questID != null;
        data.writeBoolean(hasQuest);
        if (hasQuest) {
            data.writeLong(this.questID.getMostSignificantBits());
            data.writeLong(this.questID.getLeastSignificantBits());
        }
    }
    
    public void fromBytes(final ByteBuf data) {
        final boolean hasQuest = data.readBoolean();
        if (hasQuest) {
            this.questID = new UUID(data.readLong(), data.readLong());
        }
        else {
            this.questID = null;
        }
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketMiniquestTrackClient, IMessage>
    {
        public IMessage onMessage(final LOTRPacketMiniquestTrackClient packet, final MessageContext context) {
            if (!LOTRMod.proxy.isSingleplayer()) {
                final EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
                final LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
                pd.setTrackingMiniQuestID(packet.questID);
            }
            return null;
        }
    }
}
