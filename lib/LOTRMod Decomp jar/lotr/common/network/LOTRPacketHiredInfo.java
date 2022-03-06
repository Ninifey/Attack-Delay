// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.LOTRMod;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import com.google.common.base.Charsets;
import net.minecraft.util.StringUtils;
import io.netty.buffer.ByteBuf;
import java.util.UUID;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketHiredInfo implements IMessage
{
    private int entityID;
    public boolean isHired;
    public UUID hiringPlayer;
    public String squadron;
    
    public LOTRPacketHiredInfo() {
    }
    
    public LOTRPacketHiredInfo(final int i, final UUID player, final String sq) {
        this.entityID = i;
        this.hiringPlayer = player;
        this.isHired = (this.hiringPlayer != null);
        this.squadron = sq;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeInt(this.entityID);
        data.writeBoolean(this.isHired);
        if (this.isHired) {
            data.writeLong(this.hiringPlayer.getMostSignificantBits());
            data.writeLong(this.hiringPlayer.getLeastSignificantBits());
        }
        if (StringUtils.isNullOrEmpty(this.squadron)) {
            data.writeShort(-1);
        }
        else {
            final byte[] sqBytes = this.squadron.getBytes(Charsets.UTF_8);
            data.writeShort(sqBytes.length);
            data.writeBytes(sqBytes);
        }
    }
    
    public void fromBytes(final ByteBuf data) {
        this.entityID = data.readInt();
        this.isHired = data.readBoolean();
        if (this.isHired) {
            this.hiringPlayer = new UUID(data.readLong(), data.readLong());
        }
        else {
            this.hiringPlayer = null;
        }
        final int sqLength = data.readShort();
        if (sqLength > -1) {
            this.squadron = data.readBytes(sqLength).toString(Charsets.UTF_8);
        }
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketHiredInfo, IMessage>
    {
        public IMessage onMessage(final LOTRPacketHiredInfo packet, final MessageContext context) {
            final World world = LOTRMod.proxy.getClientWorld();
            final Entity entity = world.getEntityByID(packet.entityID);
            if (entity instanceof LOTREntityNPC) {
                ((LOTREntityNPC)entity).hiredNPCInfo.receiveData(packet);
            }
            return null;
        }
    }
}
