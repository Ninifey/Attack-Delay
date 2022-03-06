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

public class LOTRPacketHiredUnitInteract implements IMessage
{
    private int entityID;
    private int entityAction;
    
    public LOTRPacketHiredUnitInteract() {
    }
    
    public LOTRPacketHiredUnitInteract(final int id, final int a) {
        this.entityID = id;
        this.entityAction = a;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeInt(this.entityID);
        data.writeByte(this.entityAction);
    }
    
    public void fromBytes(final ByteBuf data) {
        this.entityID = data.readInt();
        this.entityAction = data.readByte();
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketHiredUnitInteract, IMessage>
    {
        public IMessage onMessage(final LOTRPacketHiredUnitInteract packet, final MessageContext context) {
            final EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            final World world = ((Entity)entityplayer).worldObj;
            final Entity npc = world.getEntityByID(packet.entityID);
            if (npc != null && npc instanceof LOTREntityNPC) {
                final LOTREntityNPC hiredNPC = (LOTREntityNPC)npc;
                if (hiredNPC.hiredNPCInfo.isActive && hiredNPC.hiredNPCInfo.getHiringPlayer() == entityplayer) {
                    final int action = packet.entityAction;
                    boolean closeScreen = false;
                    if (action == 0) {
                        hiredNPC.npcTalkTick = hiredNPC.getNPCTalkInterval();
                        closeScreen = hiredNPC.speakTo((EntityPlayer)entityplayer);
                    }
                    else if (action == 1) {
                        hiredNPC.hiredNPCInfo.sendClientPacket(true);
                    }
                    else if (action == 2) {}
                    if (closeScreen) {
                        entityplayer.closeScreen();
                    }
                }
            }
            return null;
        }
    }
}
