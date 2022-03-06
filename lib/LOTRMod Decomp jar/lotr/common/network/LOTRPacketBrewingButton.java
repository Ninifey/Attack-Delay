// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import net.minecraft.inventory.Container;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.inventory.LOTRContainerBarrel;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketBrewingButton implements IMessage
{
    public void toBytes(final ByteBuf data) {
    }
    
    public void fromBytes(final ByteBuf data) {
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketBrewingButton, IMessage>
    {
        public IMessage onMessage(final LOTRPacketBrewingButton packet, final MessageContext context) {
            final EntityPlayer entityplayer = (EntityPlayer)context.getServerHandler().playerEntity;
            final Container container = entityplayer.openContainer;
            if (container != null && container instanceof LOTRContainerBarrel) {
                ((LOTRContainerBarrel)container).theBarrel.handleBrewingButtonPress();
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.brewDrinkInBarrel);
            }
            return null;
        }
    }
}
