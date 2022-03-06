// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayerMP;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTRHiredNPCInfo;
import lotr.common.entity.npc.LOTREntityNPC;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketHiredUnitCommand implements IMessage
{
    private int entityID;
    private int page;
    private int action;
    private int value;
    
    public LOTRPacketHiredUnitCommand() {
    }
    
    public LOTRPacketHiredUnitCommand(final int eid, final int p, final int a, final int v) {
        this.entityID = eid;
        this.page = p;
        this.action = a;
        this.value = v;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeInt(this.entityID);
        data.writeByte(this.page);
        data.writeByte(this.action);
        data.writeByte(this.value);
    }
    
    public void fromBytes(final ByteBuf data) {
        this.entityID = data.readInt();
        this.page = data.readByte();
        this.action = data.readByte();
        this.value = data.readByte();
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketHiredUnitCommand, IMessage>
    {
        public IMessage onMessage(final LOTRPacketHiredUnitCommand packet, final MessageContext context) {
            final EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            final World world = ((Entity)entityplayer).worldObj;
            final Entity npc = world.getEntityByID(packet.entityID);
            if (npc != null && npc instanceof LOTREntityNPC) {
                final LOTREntityNPC hiredNPC = (LOTREntityNPC)npc;
                if (hiredNPC.hiredNPCInfo.isActive && hiredNPC.hiredNPCInfo.getHiringPlayer() == entityplayer) {
                    final int page = packet.page;
                    final int action = packet.action;
                    final int value = packet.value;
                    if (action == -1) {
                        hiredNPC.hiredNPCInfo.isGuiOpen = false;
                    }
                    else {
                        final LOTRHiredNPCInfo.Task task = hiredNPC.hiredNPCInfo.getTask();
                        if (task == LOTRHiredNPCInfo.Task.WARRIOR) {
                            if (page == 0) {
                                entityplayer.openGui((Object)LOTRMod.instance, 46, world, hiredNPC.getEntityId(), 0, 0);
                            }
                            else if (page == 1) {
                                if (action == 0) {
                                    hiredNPC.hiredNPCInfo.teleportAutomatically = !hiredNPC.hiredNPCInfo.teleportAutomatically;
                                }
                                else if (action == 1) {
                                    hiredNPC.hiredNPCInfo.setGuardMode(!hiredNPC.hiredNPCInfo.isGuardMode());
                                }
                                else if (action == 2) {
                                    hiredNPC.hiredNPCInfo.setGuardRange(value);
                                }
                            }
                        }
                        else if (task == LOTRHiredNPCInfo.Task.FARMER) {
                            if (action == 0) {
                                hiredNPC.hiredNPCInfo.setGuardMode(!hiredNPC.hiredNPCInfo.isGuardMode());
                            }
                            else if (action == 1) {
                                hiredNPC.hiredNPCInfo.setGuardRange(value);
                            }
                            else if (action == 2) {
                                entityplayer.openGui((Object)LOTRMod.instance, 22, world, hiredNPC.getEntityId(), 0, 0);
                            }
                        }
                        hiredNPC.hiredNPCInfo.sendClientPacket(false);
                    }
                }
            }
            return null;
        }
    }
}
