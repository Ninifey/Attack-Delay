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
import java.io.IOException;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.network.PacketBuffer;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketLoginPlayerData implements IMessage
{
    private NBTTagCompound playerData;
    
    public LOTRPacketLoginPlayerData() {
    }
    
    public LOTRPacketLoginPlayerData(final NBTTagCompound nbt) {
        this.playerData = nbt;
    }
    
    public void toBytes(final ByteBuf data) {
        try {
            new PacketBuffer(data).writeNBTTagCompoundToBuffer(this.playerData);
        }
        catch (IOException e) {
            FMLLog.severe("Error writing LOTR login player data", new Object[0]);
            e.printStackTrace();
        }
    }
    
    public void fromBytes(final ByteBuf data) {
        try {
            this.playerData = new PacketBuffer(data).readNBTTagCompoundFromBuffer();
        }
        catch (IOException e) {
            FMLLog.severe("Error reading LOTR login player data", new Object[0]);
            e.printStackTrace();
        }
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketLoginPlayerData, IMessage>
    {
        public IMessage onMessage(final LOTRPacketLoginPlayerData packet, final MessageContext context) {
            final NBTTagCompound nbt = packet.playerData;
            final EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
            final LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
            if (!LOTRMod.proxy.isSingleplayer()) {
                pd.load(nbt);
            }
            LOTRMod.proxy.setWaypointModes(pd.showWaypoints(), pd.showCustomWaypoints(), pd.showHiddenSharedWaypoints());
            return null;
        }
    }
}
