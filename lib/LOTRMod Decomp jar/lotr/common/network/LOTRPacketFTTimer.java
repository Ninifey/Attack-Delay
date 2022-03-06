// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketFTTimer implements IMessage
{
    private int timer;
    
    public LOTRPacketFTTimer() {
    }
    
    public LOTRPacketFTTimer(final int i) {
        this.timer = i;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeInt(this.timer);
    }
    
    public void fromBytes(final ByteBuf data) {
        this.timer = data.readInt();
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketFTTimer, IMessage>
    {
        public IMessage onMessage(final LOTRPacketFTTimer packet, final MessageContext context) {
            final EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
            LOTRLevelData.getData(entityplayer).setFTTimer(packet.timer);
            return null;
        }
    }
}
