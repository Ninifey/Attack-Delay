// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import lotr.common.LOTRPlayerData;
import lotr.common.fellowship.LOTRFellowship;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRLevelData;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import io.netty.buffer.ByteBuf;
import lotr.common.fellowship.LOTRFellowshipClient;

public class LOTRPacketFellowshipSetIcon extends LOTRPacketFellowshipDo
{
    public LOTRPacketFellowshipSetIcon() {
    }
    
    public LOTRPacketFellowshipSetIcon(final LOTRFellowshipClient fs) {
        super(fs);
    }
    
    @Override
    public void toBytes(final ByteBuf data) {
        super.toBytes(data);
    }
    
    @Override
    public void fromBytes(final ByteBuf data) {
        super.fromBytes(data);
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketFellowshipSetIcon, IMessage>
    {
        public IMessage onMessage(final LOTRPacketFellowshipSetIcon packet, final MessageContext context) {
            final EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            final LOTRFellowship fellowship = packet.getFellowship();
            if (fellowship != null) {
                final LOTRPlayerData playerData = LOTRLevelData.getData((EntityPlayer)entityplayer);
                final ItemStack itemstack = entityplayer.getHeldItem();
                if (itemstack != null) {
                    final ItemStack newStack = itemstack.copy();
                    newStack.stackSize = 1;
                    playerData.setFellowshipIcon(fellowship, newStack);
                }
                else {
                    playerData.setFellowshipIcon(fellowship, null);
                }
            }
            return null;
        }
    }
}
