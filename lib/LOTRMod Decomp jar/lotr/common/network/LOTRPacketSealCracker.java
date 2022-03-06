// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import net.minecraft.inventory.Container;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.inventory.LOTRContainerDaleCracker;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketSealCracker implements IMessage
{
    public void toBytes(final ByteBuf data) {
    }
    
    public void fromBytes(final ByteBuf data) {
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketSealCracker, IMessage>
    {
        public IMessage onMessage(final LOTRPacketSealCracker packet, final MessageContext context) {
            final EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            final Container container = ((EntityPlayer)entityplayer).openContainer;
            if (container instanceof LOTRContainerDaleCracker) {
                final LOTRContainerDaleCracker cracker = (LOTRContainerDaleCracker)container;
                cracker.receiveSealingPacket((EntityPlayer)entityplayer);
            }
            return null;
        }
    }
}
