// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import lotr.common.entity.npc.LOTREntityMallornEnt;
import lotr.common.LOTRMod;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import java.io.IOException;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.network.PacketBuffer;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketMallornEntHeal implements IMessage
{
    private int entityID;
    public NBTTagCompound healingData;
    
    public LOTRPacketMallornEntHeal() {
    }
    
    public LOTRPacketMallornEntHeal(final int id, final NBTTagCompound nbt) {
        this.entityID = id;
        this.healingData = nbt;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeInt(this.entityID);
        try {
            new PacketBuffer(data).writeNBTTagCompoundToBuffer(this.healingData);
        }
        catch (IOException e) {
            FMLLog.severe("Error writing MEnt healing data", new Object[0]);
            e.printStackTrace();
        }
    }
    
    public void fromBytes(final ByteBuf data) {
        this.entityID = data.readInt();
        try {
            this.healingData = new PacketBuffer(data).readNBTTagCompoundFromBuffer();
        }
        catch (IOException e) {
            FMLLog.severe("Error reading MEnt healing data", new Object[0]);
            e.printStackTrace();
        }
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketMallornEntHeal, IMessage>
    {
        public IMessage onMessage(final LOTRPacketMallornEntHeal packet, final MessageContext context) {
            final World world = LOTRMod.proxy.getClientWorld();
            final Entity entity = world.getEntityByID(packet.entityID);
            if (entity instanceof LOTREntityMallornEnt) {
                final LOTREntityMallornEnt ent = (LOTREntityMallornEnt)entity;
                ent.receiveClientHealing(packet.healingData);
            }
            return null;
        }
    }
}
