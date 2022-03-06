// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRMod;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketSetPlayerRotation implements IMessage
{
    private float rotYaw;
    
    public LOTRPacketSetPlayerRotation() {
    }
    
    public LOTRPacketSetPlayerRotation(final float f) {
        this.rotYaw = f;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeFloat(this.rotYaw);
    }
    
    public void fromBytes(final ByteBuf data) {
        this.rotYaw = data.readFloat();
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketSetPlayerRotation, IMessage>
    {
        public IMessage onMessage(final LOTRPacketSetPlayerRotation packet, final MessageContext context) {
            final EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
            if (entityplayer != null) {
                ((Entity)entityplayer).rotationYaw = packet.rotYaw;
            }
            return null;
        }
    }
}
