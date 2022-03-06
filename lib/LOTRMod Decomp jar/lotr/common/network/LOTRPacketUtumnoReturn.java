// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import lotr.common.LOTRMod;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketUtumnoReturn implements IMessage
{
    private double posX;
    private double posZ;
    
    public LOTRPacketUtumnoReturn() {
    }
    
    public LOTRPacketUtumnoReturn(final double x, final double z) {
        this.posX = x;
        this.posZ = z;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeDouble(this.posX);
        data.writeDouble(this.posZ);
    }
    
    public void fromBytes(final ByteBuf data) {
        this.posX = data.readDouble();
        this.posZ = data.readDouble();
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketUtumnoReturn, IMessage>
    {
        public IMessage onMessage(final LOTRPacketUtumnoReturn packet, final MessageContext context) {
            final EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
            entityplayer.setPosition(packet.posX, ((Entity)entityplayer).posY, packet.posZ);
            LOTRMod.proxy.setUtumnoReturnPortalCoords(entityplayer, MathHelper.floor_double(packet.posX), MathHelper.floor_double(packet.posZ));
            return null;
        }
    }
}
