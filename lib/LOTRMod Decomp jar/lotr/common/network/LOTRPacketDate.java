// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import lotr.common.LOTRMod;
import lotr.common.LOTRDate;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import java.io.IOException;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.network.PacketBuffer;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketDate implements IMessage
{
    public NBTTagCompound dateData;
    private boolean doUpdate;
    
    public LOTRPacketDate() {
    }
    
    public LOTRPacketDate(final NBTTagCompound nbt, final boolean flag) {
        this.dateData = nbt;
        this.doUpdate = flag;
    }
    
    public void toBytes(final ByteBuf data) {
        try {
            new PacketBuffer(data).writeNBTTagCompoundToBuffer(this.dateData);
        }
        catch (IOException e) {
            FMLLog.severe("Error writing LOTR date", new Object[0]);
            e.printStackTrace();
        }
        data.writeBoolean(this.doUpdate);
    }
    
    public void fromBytes(final ByteBuf data) {
        try {
            this.dateData = new PacketBuffer(data).readNBTTagCompoundFromBuffer();
        }
        catch (IOException e) {
            FMLLog.severe("Error reading LOTR date", new Object[0]);
            e.printStackTrace();
        }
        this.doUpdate = data.readBoolean();
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketDate, IMessage>
    {
        public IMessage onMessage(final LOTRPacketDate packet, final MessageContext context) {
            LOTRDate.loadDates(packet.dateData);
            if (packet.doUpdate) {
                LOTRMod.proxy.displayNewDate();
            }
            return null;
        }
    }
}
