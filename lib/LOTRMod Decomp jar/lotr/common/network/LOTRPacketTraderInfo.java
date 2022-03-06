// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import net.minecraft.inventory.Container;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.inventory.LOTRContainerTrade;
import lotr.common.LOTRMod;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import java.io.IOException;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.network.PacketBuffer;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketTraderInfo implements IMessage
{
    public NBTTagCompound traderData;
    
    public LOTRPacketTraderInfo() {
    }
    
    public LOTRPacketTraderInfo(final NBTTagCompound nbt) {
        this.traderData = nbt;
    }
    
    public void toBytes(final ByteBuf data) {
        try {
            new PacketBuffer(data).writeNBTTagCompoundToBuffer(this.traderData);
        }
        catch (IOException e) {
            FMLLog.severe("Error writing trader data", new Object[0]);
            e.printStackTrace();
        }
    }
    
    public void fromBytes(final ByteBuf data) {
        try {
            this.traderData = new PacketBuffer(data).readNBTTagCompoundFromBuffer();
        }
        catch (IOException e) {
            FMLLog.severe("Error reading trader data", new Object[0]);
            e.printStackTrace();
        }
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketTraderInfo, IMessage>
    {
        public IMessage onMessage(final LOTRPacketTraderInfo packet, final MessageContext context) {
            final EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
            final Container container = entityplayer.openContainer;
            if (container instanceof LOTRContainerTrade) {
                final LOTRContainerTrade containerTrade = (LOTRContainerTrade)container;
                containerTrade.theTraderNPC.traderNPCInfo.receiveClientPacket(packet);
            }
            return null;
        }
    }
}
