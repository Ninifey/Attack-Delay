// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import lotr.common.LOTRPlayerData;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import java.io.IOException;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.network.PacketBuffer;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketMiniquest implements IMessage
{
    private NBTTagCompound miniquestData;
    private boolean completed;
    
    public LOTRPacketMiniquest() {
    }
    
    public LOTRPacketMiniquest(final NBTTagCompound nbt, final boolean flag) {
        this.miniquestData = nbt;
        this.completed = flag;
    }
    
    public void toBytes(final ByteBuf data) {
        try {
            new PacketBuffer(data).writeNBTTagCompoundToBuffer(this.miniquestData);
        }
        catch (IOException e) {
            FMLLog.severe("LOTR: Error writing miniquest data", new Object[0]);
            e.printStackTrace();
        }
        data.writeBoolean(this.completed);
    }
    
    public void fromBytes(final ByteBuf data) {
        try {
            this.miniquestData = new PacketBuffer(data).readNBTTagCompoundFromBuffer();
        }
        catch (IOException e) {
            FMLLog.severe("LOTR: Error reading miniquest data", new Object[0]);
            e.printStackTrace();
        }
        this.completed = data.readBoolean();
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketMiniquest, IMessage>
    {
        public IMessage onMessage(final LOTRPacketMiniquest packet, final MessageContext context) {
            if (!LOTRMod.proxy.isSingleplayer()) {
                final EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
                final LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
                final LOTRMiniQuest miniquest = LOTRMiniQuest.loadQuestFromNBT(packet.miniquestData, pd);
                if (miniquest != null) {
                    final LOTRMiniQuest existingQuest = pd.getMiniQuestForID(miniquest.questUUID, packet.completed);
                    if (existingQuest == null) {
                        if (packet.completed) {
                            pd.addMiniQuestCompleted(miniquest);
                        }
                        else {
                            pd.addMiniQuest(miniquest);
                        }
                    }
                    else {
                        existingQuest.readFromNBT(packet.miniquestData);
                    }
                }
            }
            return null;
        }
    }
}
