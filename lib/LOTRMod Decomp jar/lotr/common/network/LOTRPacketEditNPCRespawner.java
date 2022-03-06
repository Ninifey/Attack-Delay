// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayerMP;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import java.io.IOException;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.network.PacketBuffer;
import io.netty.buffer.ByteBuf;
import lotr.common.entity.LOTREntityNPCRespawner;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketEditNPCRespawner implements IMessage
{
    private int spawnerID;
    private NBTTagCompound spawnerData;
    public boolean destroy;
    
    public LOTRPacketEditNPCRespawner() {
    }
    
    public LOTRPacketEditNPCRespawner(final LOTREntityNPCRespawner spawner) {
        this.spawnerID = spawner.getEntityId();
        spawner.writeSpawnerDataToNBT(this.spawnerData = new NBTTagCompound());
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeInt(this.spawnerID);
        try {
            new PacketBuffer(data).writeNBTTagCompoundToBuffer(this.spawnerData);
        }
        catch (IOException e) {
            FMLLog.severe("Error writing spawner data", new Object[0]);
            e.printStackTrace();
        }
        data.writeBoolean(this.destroy);
    }
    
    public void fromBytes(final ByteBuf data) {
        this.spawnerID = data.readInt();
        try {
            this.spawnerData = new PacketBuffer(data).readNBTTagCompoundFromBuffer();
        }
        catch (IOException e) {
            FMLLog.severe("Error reading spawner data", new Object[0]);
            e.printStackTrace();
        }
        this.destroy = data.readBoolean();
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketEditNPCRespawner, IMessage>
    {
        public IMessage onMessage(final LOTRPacketEditNPCRespawner packet, final MessageContext context) {
            final EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            final World world = ((Entity)entityplayer).worldObj;
            final Entity sEntity = world.getEntityByID(packet.spawnerID);
            if (sEntity instanceof LOTREntityNPCRespawner) {
                final LOTREntityNPCRespawner spawner = (LOTREntityNPCRespawner)sEntity;
                if (((EntityPlayer)entityplayer).capabilities.isCreativeMode) {
                    spawner.readSpawnerDataFromNBT(packet.spawnerData);
                }
                if (packet.destroy) {
                    spawner.onBreak();
                }
            }
            return null;
        }
    }
}
