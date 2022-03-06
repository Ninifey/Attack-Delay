// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayerMP;
import lotr.common.entity.npc.LOTREntityNPC;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketHiredUnitDismiss implements IMessage
{
    private int entityID;
    private int action;
    
    public LOTRPacketHiredUnitDismiss() {
    }
    
    public LOTRPacketHiredUnitDismiss(final int id, final int a) {
        this.entityID = id;
        this.action = a;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeInt(this.entityID);
        data.writeByte(this.action);
    }
    
    public void fromBytes(final ByteBuf data) {
        this.entityID = data.readInt();
        this.action = data.readByte();
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketHiredUnitDismiss, IMessage>
    {
        public IMessage onMessage(final LOTRPacketHiredUnitDismiss packet, final MessageContext context) {
            final EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            final World world = ((Entity)entityplayer).worldObj;
            final Entity npc = world.getEntityByID(packet.entityID);
            if (npc != null && npc instanceof LOTREntityNPC) {
                final LOTREntityNPC hiredNPC = (LOTREntityNPC)npc;
                if (hiredNPC.hiredNPCInfo.isActive && hiredNPC.hiredNPCInfo.getHiringPlayer() == entityplayer) {
                    final int action = packet.action;
                    boolean closeScreen = false;
                    if (action == 0) {
                        hiredNPC.hiredNPCInfo.dismissUnit(false);
                        final Entity mount = ((Entity)hiredNPC).ridingEntity;
                        final Entity rider = ((Entity)hiredNPC).riddenByEntity;
                        if (mount instanceof LOTREntityNPC) {
                            final LOTREntityNPC mountNPC = (LOTREntityNPC)mount;
                            if (mountNPC.hiredNPCInfo.isActive && mountNPC.hiredNPCInfo.getHiringPlayer() == entityplayer) {
                                mountNPC.hiredNPCInfo.dismissUnit(false);
                            }
                        }
                        if (rider instanceof LOTREntityNPC) {
                            final LOTREntityNPC riderNPC = (LOTREntityNPC)rider;
                            if (riderNPC.hiredNPCInfo.isActive && riderNPC.hiredNPCInfo.getHiringPlayer() == entityplayer) {
                                riderNPC.hiredNPCInfo.dismissUnit(false);
                            }
                        }
                        closeScreen = true;
                    }
                    else if (action == 1) {}
                    if (closeScreen) {
                        entityplayer.closeScreen();
                    }
                }
            }
            return null;
        }
    }
}
