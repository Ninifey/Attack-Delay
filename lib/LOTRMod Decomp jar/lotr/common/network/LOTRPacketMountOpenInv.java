// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.entity.npc.LOTREntityNPCRideable;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketMountOpenInv implements IMessage
{
    public void toBytes(final ByteBuf data) {
    }
    
    public void fromBytes(final ByteBuf data) {
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketMountOpenInv, IMessage>
    {
        public IMessage onMessage(final LOTRPacketMountOpenInv packet, final MessageContext context) {
            final EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            if (((Entity)entityplayer).ridingEntity instanceof LOTREntityNPCRideable) {
                ((LOTREntityNPCRideable)((Entity)entityplayer).ridingEntity).openGUI((EntityPlayer)entityplayer);
            }
            return null;
        }
    }
}
