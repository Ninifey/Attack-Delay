// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import lotr.common.LOTRMod;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketEnvironmentOverlay implements IMessage
{
    private Overlay overlay;
    
    public LOTRPacketEnvironmentOverlay() {
    }
    
    public LOTRPacketEnvironmentOverlay(final Overlay o) {
        this.overlay = o;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeByte(this.overlay.ordinal());
    }
    
    public void fromBytes(final ByteBuf data) {
        final int overlayID = data.readByte();
        this.overlay = Overlay.values()[overlayID];
    }
    
    public enum Overlay
    {
        FROST, 
        BURN;
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketEnvironmentOverlay, IMessage>
    {
        public IMessage onMessage(final LOTRPacketEnvironmentOverlay packet, final MessageContext context) {
            if (packet.overlay == Overlay.FROST) {
                LOTRMod.proxy.showFrostOverlay();
            }
            else if (packet.overlay == Overlay.BURN) {
                LOTRMod.proxy.showBurnOverlay();
            }
            return null;
        }
    }
}
