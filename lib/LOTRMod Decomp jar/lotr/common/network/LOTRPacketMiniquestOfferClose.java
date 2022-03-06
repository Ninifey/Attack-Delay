// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.entity.npc.LOTREntityNPC;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketMiniquestOfferClose implements IMessage
{
    private int npcID;
    private boolean accept;
    
    public LOTRPacketMiniquestOfferClose() {
    }
    
    public LOTRPacketMiniquestOfferClose(final int id, final boolean flag) {
        this.npcID = id;
        this.accept = flag;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeInt(this.npcID);
        data.writeBoolean(this.accept);
    }
    
    public void fromBytes(final ByteBuf data) {
        this.npcID = data.readInt();
        this.accept = data.readBoolean();
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketMiniquestOfferClose, IMessage>
    {
        public IMessage onMessage(final LOTRPacketMiniquestOfferClose packet, final MessageContext context) {
            final EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            final World world = ((Entity)entityplayer).worldObj;
            final Entity npcEntity = world.getEntityByID(packet.npcID);
            if (npcEntity instanceof LOTREntityNPC) {
                final LOTREntityNPC npc = (LOTREntityNPC)npcEntity;
                npc.questInfo.receiveOfferResponse((EntityPlayer)entityplayer, packet.accept);
            }
            return null;
        }
    }
}
