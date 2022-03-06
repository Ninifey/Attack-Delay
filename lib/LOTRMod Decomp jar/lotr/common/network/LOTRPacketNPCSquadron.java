// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayerMP;
import lotr.common.LOTRSquadrons;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import com.google.common.base.Charsets;
import net.minecraft.util.StringUtils;
import io.netty.buffer.ByteBuf;
import lotr.common.entity.npc.LOTREntityNPC;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketNPCSquadron implements IMessage
{
    private int npcID;
    private String squadron;
    
    public LOTRPacketNPCSquadron() {
    }
    
    public LOTRPacketNPCSquadron(final LOTREntityNPC npc, final String s) {
        this.npcID = npc.getEntityId();
        this.squadron = s;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeInt(this.npcID);
        if (StringUtils.isNullOrEmpty(this.squadron)) {
            data.writeInt(-1);
        }
        else {
            final byte[] sqBytes = this.squadron.getBytes(Charsets.UTF_8);
            data.writeInt(sqBytes.length);
            data.writeBytes(sqBytes);
        }
    }
    
    public void fromBytes(final ByteBuf data) {
        this.npcID = data.readInt();
        final int length = data.readInt();
        if (length > -1) {
            this.squadron = data.readBytes(length).toString(Charsets.UTF_8);
        }
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketNPCSquadron, IMessage>
    {
        public IMessage onMessage(final LOTRPacketNPCSquadron packet, final MessageContext context) {
            final EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            final World world = ((Entity)entityplayer).worldObj;
            final Entity npc = world.getEntityByID(packet.npcID);
            if (npc != null && npc instanceof LOTREntityNPC) {
                final LOTREntityNPC hiredNPC = (LOTREntityNPC)npc;
                if (hiredNPC.hiredNPCInfo.isActive && hiredNPC.hiredNPCInfo.getHiringPlayer() == entityplayer) {
                    String squadron = packet.squadron;
                    if (!StringUtils.isNullOrEmpty(squadron)) {
                        squadron = LOTRSquadrons.checkAcceptableLength(squadron);
                        hiredNPC.hiredNPCInfo.setSquadron(squadron);
                    }
                    else {
                        hiredNPC.hiredNPCInfo.setSquadron("");
                    }
                }
            }
            return null;
        }
    }
}
