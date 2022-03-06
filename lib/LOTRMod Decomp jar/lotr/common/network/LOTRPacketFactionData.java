// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import lotr.common.fac.LOTRFactionData;
import lotr.common.LOTRPlayerData;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import java.io.IOException;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.network.PacketBuffer;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import lotr.common.fac.LOTRFaction;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketFactionData implements IMessage
{
    private LOTRFaction faction;
    private NBTTagCompound factionNBT;
    
    public LOTRPacketFactionData() {
    }
    
    public LOTRPacketFactionData(final LOTRFaction f, final NBTTagCompound nbt) {
        this.faction = f;
        this.factionNBT = nbt;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeByte(this.faction.ordinal());
        try {
            new PacketBuffer(data).writeNBTTagCompoundToBuffer(this.factionNBT);
        }
        catch (IOException e) {
            FMLLog.severe("Error writing faction data", new Object[0]);
            e.printStackTrace();
        }
    }
    
    public void fromBytes(final ByteBuf data) {
        final int factionID = data.readByte();
        this.faction = LOTRFaction.forID(factionID);
        try {
            this.factionNBT = new PacketBuffer(data).readNBTTagCompoundFromBuffer();
        }
        catch (IOException e) {
            FMLLog.severe("Error reading faction data", new Object[0]);
            e.printStackTrace();
        }
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketFactionData, IMessage>
    {
        public IMessage onMessage(final LOTRPacketFactionData packet, final MessageContext context) {
            if (!LOTRMod.proxy.isSingleplayer()) {
                final EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
                final LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
                final LOTRFaction faction = packet.faction;
                if (faction != null) {
                    final LOTRFactionData factionData = pd.getFactionData(faction);
                    factionData.load(packet.factionNBT);
                }
            }
            return null;
        }
    }
}
