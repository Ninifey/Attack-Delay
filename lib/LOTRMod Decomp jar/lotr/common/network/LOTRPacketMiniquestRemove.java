// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import lotr.common.LOTRPlayerData;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.FMLLog;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import io.netty.buffer.ByteBuf;
import lotr.common.quest.LOTRMiniQuest;
import java.util.UUID;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketMiniquestRemove implements IMessage
{
    private UUID questUUID;
    private boolean wasCompleted;
    private boolean addToCompleted;
    
    public LOTRPacketMiniquestRemove() {
    }
    
    public LOTRPacketMiniquestRemove(final LOTRMiniQuest quest, final boolean wc, final boolean atc) {
        this.questUUID = quest.questUUID;
        this.wasCompleted = wc;
        this.addToCompleted = atc;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeLong(this.questUUID.getMostSignificantBits());
        data.writeLong(this.questUUID.getLeastSignificantBits());
        data.writeBoolean(this.wasCompleted);
        data.writeBoolean(this.addToCompleted);
    }
    
    public void fromBytes(final ByteBuf data) {
        this.questUUID = new UUID(data.readLong(), data.readLong());
        this.wasCompleted = data.readBoolean();
        this.addToCompleted = data.readBoolean();
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketMiniquestRemove, IMessage>
    {
        public IMessage onMessage(final LOTRPacketMiniquestRemove packet, final MessageContext context) {
            if (!LOTRMod.proxy.isSingleplayer()) {
                final EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
                final LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
                final LOTRMiniQuest removeQuest = pd.getMiniQuestForID(packet.questUUID, packet.wasCompleted);
                if (removeQuest != null) {
                    if (packet.addToCompleted) {
                        pd.completeMiniQuest(removeQuest);
                    }
                    else {
                        pd.removeMiniQuest(removeQuest, packet.wasCompleted);
                    }
                }
                else {
                    FMLLog.warning("Tried to remove a LOTR miniquest that doesn't exist", new Object[0]);
                }
            }
            return null;
        }
    }
}
