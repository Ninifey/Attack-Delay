// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import lotr.common.LOTRMod;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import java.io.IOException;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.network.PacketBuffer;
import io.netty.buffer.ByteBuf;
import lotr.common.entity.LOTREntityNPCRespawner;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketNPCRespawner implements IMessage
{
    private int spawnerID;
    private NBTTagCompound spawnerData;
    
    public LOTRPacketNPCRespawner() {
    }
    
    public LOTRPacketNPCRespawner(final LOTREntityNPCRespawner spawner) {
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
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketNPCRespawner, IMessage>
    {
        public IMessage onMessage(final LOTRPacketNPCRespawner packet, final MessageContext context) {
            final World world = LOTRMod.proxy.getClientWorld();
            final EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
            final Entity entity = world.getEntityByID(packet.spawnerID);
            if (entity instanceof LOTREntityNPCRespawner) {
                final LOTREntityNPCRespawner spawner = (LOTREntityNPCRespawner)entity;
                spawner.readSpawnerDataFromNBT(packet.spawnerData);
                entityplayer.openGui((Object)LOTRMod.instance, 45, world, entity.getEntityId(), 0, 0);
            }
            return null;
        }
    }
}
