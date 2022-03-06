// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.LOTRMod;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import io.netty.buffer.ByteBuf;
import lotr.common.entity.npc.LOTRHiredNPCInfo;
import lotr.common.entity.npc.LOTRUnitTradeEntry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketHiredGui implements IMessage
{
    private int entityID;
    private boolean openGui;
    public boolean isActive;
    public boolean canMove;
    public boolean teleportAutomatically;
    public int mobKills;
    public float alignmentRequired;
    public LOTRUnitTradeEntry.PledgeType pledgeType;
    public boolean inCombat;
    public boolean guardMode;
    public int guardRange;
    public LOTRHiredNPCInfo.Task task;
    
    public LOTRPacketHiredGui() {
    }
    
    public LOTRPacketHiredGui(final int i, final boolean flag) {
        this.entityID = i;
        this.openGui = flag;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeInt(this.entityID);
        data.writeBoolean(this.openGui);
        data.writeBoolean(this.isActive);
        data.writeBoolean(this.canMove);
        data.writeBoolean(this.teleportAutomatically);
        data.writeInt(this.mobKills);
        data.writeFloat(this.alignmentRequired);
        data.writeByte(this.pledgeType.typeID);
        data.writeBoolean(this.inCombat);
        data.writeBoolean(this.guardMode);
        data.writeInt(this.guardRange);
        data.writeByte(this.task.ordinal());
    }
    
    public void fromBytes(final ByteBuf data) {
        this.entityID = data.readInt();
        this.openGui = data.readBoolean();
        this.isActive = data.readBoolean();
        this.canMove = data.readBoolean();
        this.teleportAutomatically = data.readBoolean();
        this.mobKills = data.readInt();
        this.alignmentRequired = data.readFloat();
        this.pledgeType = LOTRUnitTradeEntry.PledgeType.forID(data.readByte());
        this.inCombat = data.readBoolean();
        this.guardMode = data.readBoolean();
        this.guardRange = data.readInt();
        this.task = LOTRHiredNPCInfo.Task.forID(data.readByte());
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketHiredGui, IMessage>
    {
        public IMessage onMessage(final LOTRPacketHiredGui packet, final MessageContext context) {
            final EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
            final World world = LOTRMod.proxy.getClientWorld();
            final Entity entity = world.getEntityByID(packet.entityID);
            if (entity instanceof LOTREntityNPC) {
                final LOTREntityNPC npc = (LOTREntityNPC)entity;
                if (npc.hiredNPCInfo.getHiringPlayer() == entityplayer) {
                    npc.hiredNPCInfo.receiveClientPacket(packet);
                    if (packet.openGui) {
                        LOTRMod.proxy.openHiredNPCGui(npc);
                    }
                }
            }
            return null;
        }
    }
}
