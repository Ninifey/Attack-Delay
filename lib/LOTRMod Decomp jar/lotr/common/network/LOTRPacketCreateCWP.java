// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import lotr.common.LOTRPlayerData;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayerMP;
import lotr.common.world.map.LOTRCustomWaypoint;
import net.minecraft.entity.Entity;
import lotr.common.LOTRBannerProtection;
import net.minecraft.util.IChatComponent;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRLevelData;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketCreateCWP implements IMessage
{
    private String name;
    
    public LOTRPacketCreateCWP() {
    }
    
    public LOTRPacketCreateCWP(final String s) {
        this.name = s;
    }
    
    public void toBytes(final ByteBuf data) {
        final byte[] nameBytes = this.name.getBytes(Charsets.UTF_8);
        data.writeShort(nameBytes.length);
        data.writeBytes(nameBytes);
    }
    
    public void fromBytes(final ByteBuf data) {
        final int length = data.readShort();
        this.name = data.readBytes(length).toString(Charsets.UTF_8);
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketCreateCWP, IMessage>
    {
        public IMessage onMessage(final LOTRPacketCreateCWP packet, final MessageContext context) {
            final EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            final World world = ((Entity)entityplayer).worldObj;
            final LOTRPlayerData pd = LOTRLevelData.getData((EntityPlayer)entityplayer);
            final int numWaypoints = pd.getCustomWaypoints().size();
            if (numWaypoints <= pd.getMaxCustomWaypoints()) {
                final IChatComponent[] protectionMessage = { null };
                final boolean protection = LOTRBannerProtection.isProtectedByBanner(world, (Entity)entityplayer, LOTRBannerProtection.forPlayer_returnMessage((EntityPlayer)entityplayer, protectionMessage), true);
                if (!protection) {
                    final String wpName = LOTRCustomWaypoint.validateCustomName(packet.name);
                    if (wpName != null) {
                        LOTRCustomWaypoint.createForPlayer(wpName, (EntityPlayer)entityplayer);
                    }
                }
                else {
                    final IChatComponent clientMessage = protectionMessage[0];
                    final LOTRPacketCWPProtectionMessage packetMessage = new LOTRPacketCWPProtectionMessage(clientMessage);
                    LOTRPacketHandler.networkWrapper.sendTo((IMessage)packetMessage, entityplayer);
                }
            }
            return null;
        }
    }
}
