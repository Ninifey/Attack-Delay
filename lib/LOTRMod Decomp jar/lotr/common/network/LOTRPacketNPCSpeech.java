// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import lotr.common.LOTRConfig;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.LOTRMod;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketNPCSpeech implements IMessage
{
    private int entityID;
    private String speech;
    
    public LOTRPacketNPCSpeech() {
    }
    
    public LOTRPacketNPCSpeech(final int id, final String s) {
        this.entityID = id;
        this.speech = s;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeInt(this.entityID);
        final byte[] speechBytes = this.speech.getBytes(Charsets.UTF_8);
        data.writeInt(speechBytes.length);
        data.writeBytes(speechBytes);
    }
    
    public void fromBytes(final ByteBuf data) {
        this.entityID = data.readInt();
        final int length = data.readInt();
        this.speech = data.readBytes(length).toString(Charsets.UTF_8);
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketNPCSpeech, IMessage>
    {
        public IMessage onMessage(final LOTRPacketNPCSpeech packet, final MessageContext context) {
            final World world = LOTRMod.proxy.getClientWorld();
            final EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
            final Entity entity = world.getEntityByID(packet.entityID);
            if (entity instanceof LOTREntityNPC) {
                final LOTREntityNPC npc = (LOTREntityNPC)entity;
                if (LOTRConfig.immersiveSpeech) {
                    LOTRMod.proxy.clientReceiveSpeech(npc, packet.speech);
                }
                if (!LOTRConfig.immersiveSpeech || LOTRConfig.immersiveSpeechChatLog) {
                    final String name = npc.getCommandSenderName();
                    final String message = EnumChatFormatting.YELLOW + "<" + name + "> " + EnumChatFormatting.WHITE + packet.speech;
                    entityplayer.addChatMessage((IChatComponent)new ChatComponentText(message));
                }
            }
            return null;
        }
    }
}
