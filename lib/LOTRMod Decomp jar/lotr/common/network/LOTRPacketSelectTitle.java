// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import lotr.common.LOTRPlayerData;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRLevelData;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraft.util.EnumChatFormatting;
import io.netty.buffer.ByteBuf;
import lotr.common.LOTRTitle;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketSelectTitle implements IMessage
{
    private LOTRTitle.PlayerTitle playerTitle;
    
    public LOTRPacketSelectTitle() {
    }
    
    public LOTRPacketSelectTitle(final LOTRTitle.PlayerTitle t) {
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
    
    public static class Handler implements IMessageHandler<LOTRPacketSelectTitle, IMessage>
    {
        public IMessage onMessage(final LOTRPacketSelectTitle packet, final MessageContext context) {
            final EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            final LOTRPlayerData pd = LOTRLevelData.getData((EntityPlayer)entityplayer);
            final LOTRTitle.PlayerTitle title = packet.playerTitle;
            if (title == null) {
                pd.setPlayerTitle(null);
            }
            else if (title.getTitle().canPlayerUse((EntityPlayer)entityplayer)) {
                pd.setPlayerTitle(title);
            }
            return null;
        }
    }
}
