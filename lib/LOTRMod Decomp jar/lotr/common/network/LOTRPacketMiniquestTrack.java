// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import lotr.common.LOTRPlayerData;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRLevelData;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import io.netty.buffer.ByteBuf;
import lotr.common.quest.LOTRMiniQuest;
import java.util.UUID;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketMiniquestTrack implements IMessage
{
    private UUID questID;
    
    public LOTRPacketMiniquestTrack() {
    }
    
    public LOTRPacketMiniquestTrack(final LOTRMiniQuest quest) {
        this.questID = ((quest == null) ? null : quest.questUUID);
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
    
    public static class Handler implements IMessageHandler<LOTRPacketMiniquestTrack, IMessage>
    {
        public IMessage onMessage(final LOTRPacketMiniquestTrack packet, final MessageContext context) {
            final EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            final LOTRPlayerData pd = LOTRLevelData.getData((EntityPlayer)entityplayer);
            if (packet.questID == null) {
                pd.setTrackingMiniQuestID(null);
            }
            else {
                pd.setTrackingMiniQuestID(packet.questID);
            }
            return null;
        }
    }
}
