// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.entity.player.EntityPlayerMP;
import lotr.common.LOTRNetHandlerPlayServer;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketMountControl implements IMessage
{
    public double posX;
    public double posY;
    public double posZ;
    public float rotationYaw;
    public float rotationPitch;
    
    public LOTRPacketMountControl() {
    }
    
    public LOTRPacketMountControl(final Entity entity) {
        this.posX = entity.posX;
        this.posY = entity.posY;
        this.posZ = entity.posZ;
        this.rotationYaw = entity.rotationYaw;
        this.rotationPitch = entity.rotationPitch;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeDouble(this.posX);
        data.writeDouble(this.posY);
        data.writeDouble(this.posZ);
        data.writeFloat(this.rotationYaw);
        data.writeFloat(this.rotationPitch);
    }
    
    public void fromBytes(final ByteBuf data) {
        this.posX = data.readDouble();
        this.posY = data.readDouble();
        this.posZ = data.readDouble();
        this.rotationYaw = data.readFloat();
        this.rotationPitch = data.readFloat();
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketMountControl, IMessage>
    {
        public IMessage onMessage(final LOTRPacketMountControl packet, final MessageContext context) {
            final EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            final NetHandlerPlayServer handler = entityplayer.playerNetServerHandler;
            if (handler instanceof LOTRNetHandlerPlayServer) {
                ((LOTRNetHandlerPlayServer)handler).processMountControl(packet);
            }
            return null;
        }
    }
}
