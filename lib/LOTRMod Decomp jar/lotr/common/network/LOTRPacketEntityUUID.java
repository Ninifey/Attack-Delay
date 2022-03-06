// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.LOTRMod;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import io.netty.buffer.ByteBuf;
import java.util.UUID;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketEntityUUID implements IMessage
{
    private int entityID;
    private UUID entityUUID;
    
    public LOTRPacketEntityUUID() {
    }
    
    public LOTRPacketEntityUUID(final int id, final UUID uuid) {
        this.entityID = id;
        this.entityUUID = uuid;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeInt(this.entityID);
        data.writeLong(this.entityUUID.getMostSignificantBits());
        data.writeLong(this.entityUUID.getLeastSignificantBits());
    }
    
    public void fromBytes(final ByteBuf data) {
        this.entityID = data.readInt();
        this.entityUUID = new UUID(data.readLong(), data.readLong());
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketEntityUUID, IMessage>
    {
        public IMessage onMessage(final LOTRPacketEntityUUID packet, final MessageContext context) {
            final World world = LOTRMod.proxy.getClientWorld();
            final Entity entity = world.getEntityByID(packet.entityID);
            if (entity instanceof LOTRRandomSkinEntity) {
                final LOTRRandomSkinEntity npc = (LOTRRandomSkinEntity)entity;
                npc.setUniqueID(packet.entityUUID);
            }
            return null;
        }
    }
}
