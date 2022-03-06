// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import lotr.common.LOTRPlayerData;
import net.minecraft.entity.player.EntityPlayerMP;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRLevelData;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import io.netty.buffer.ByteBuf;
import lotr.common.quest.LOTRMiniQuest;
import java.util.UUID;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketDeleteMiniquest implements IMessage
{
    private UUID questUUID;
    private boolean completed;
    
    public LOTRPacketDeleteMiniquest() {
    }
    
    public LOTRPacketDeleteMiniquest(final LOTRMiniQuest quest) {
        this.questUUID = quest.questUUID;
        this.completed = quest.isCompleted();
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeLong(this.questUUID.getMostSignificantBits());
        data.writeLong(this.questUUID.getLeastSignificantBits());
        data.writeBoolean(this.completed);
    }
    
    public void fromBytes(final ByteBuf data) {
        this.questUUID = new UUID(data.readLong(), data.readLong());
        this.completed = data.readBoolean();
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketDeleteMiniquest, IMessage>
    {
        public IMessage onMessage(final LOTRPacketDeleteMiniquest packet, final MessageContext context) {
            final EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            final LOTRPlayerData pd = LOTRLevelData.getData((EntityPlayer)entityplayer);
            final LOTRMiniQuest removeQuest = pd.getMiniQuestForID(packet.questUUID, packet.completed);
            if (removeQuest != null) {
                pd.removeMiniQuest(removeQuest, packet.completed);
            }
            else {
                FMLLog.warning("Tried to remove a LOTR miniquest that doesn't exist", new Object[0]);
            }
            return null;
        }
    }
}
