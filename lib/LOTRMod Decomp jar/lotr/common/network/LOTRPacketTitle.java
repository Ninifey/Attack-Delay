// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import lotr.common.LOTRPlayerData;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraft.util.EnumChatFormatting;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRTitle;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketTitle implements IMessage
{
    private LOTRTitle.PlayerTitle playerTitle;
    
    public LOTRPacketTitle() {
    }
    
    public LOTRPacketTitle(final LOTRTitle.PlayerTitle t) {
        this.playerTitle = t;
    }
    
    public void toBytes(final ByteBuf data) {
        if (this.playerTitle == null) {
            data.writeShort(-1);
            data.writeByte(-1);
        }
        else {
            data.writeShort(this.playerTitle.getTitle().titleID);
            data.writeByte((int)this.playerTitle.getColor().getFormattingCode());
        }
    }
    
    public void fromBytes(final ByteBuf data) {
        final int titleID = data.readShort();
        final LOTRTitle title = LOTRTitle.forID(titleID);
        final int colorID = data.readByte();
        final EnumChatFormatting color = LOTRTitle.PlayerTitle.colorForID(colorID);
        if (title != null && color != null) {
            this.playerTitle = new LOTRTitle.PlayerTitle(title, color);
        }
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketTitle, IMessage>
    {
        public IMessage onMessage(final LOTRPacketTitle packet, final MessageContext context) {
            final EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
            final LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
            final LOTRTitle.PlayerTitle title = packet.playerTitle;
            if (title == null) {
                pd.setPlayerTitle(null);
            }
            else {
                pd.setPlayerTitle(title);
            }
            return null;
        }
    }
}
