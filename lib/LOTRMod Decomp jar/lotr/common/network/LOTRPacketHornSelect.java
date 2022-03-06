// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRMod;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketHornSelect implements IMessage
{
    private int hornType;
    
    public LOTRPacketHornSelect() {
    }
    
    public LOTRPacketHornSelect(final int h) {
        this.hornType = h;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeByte(this.hornType);
    }
    
    public void fromBytes(final ByteBuf data) {
        this.hornType = data.readByte();
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketHornSelect, IMessage>
    {
        public IMessage onMessage(final LOTRPacketHornSelect packet, final MessageContext context) {
            final EntityPlayer entityplayer = (EntityPlayer)context.getServerHandler().playerEntity;
            final ItemStack itemstack = entityplayer.inventory.getCurrentItem();
            if (itemstack != null && itemstack.getItem() == LOTRMod.commandHorn && itemstack.getItemDamage() == 0) {
                itemstack.setItemDamage(packet.hornType);
            }
            return null;
        }
    }
}
