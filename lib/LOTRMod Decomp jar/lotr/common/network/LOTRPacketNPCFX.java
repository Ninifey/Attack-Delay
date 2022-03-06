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
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketNPCFX implements IMessage
{
    private int entityID;
    private FXType type;
    
    public LOTRPacketNPCFX() {
    }
    
    public LOTRPacketNPCFX(final int i, final FXType t) {
        this.entityID = i;
        this.type = t;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeInt(this.entityID);
        data.writeByte(this.type.ordinal());
    }
    
    public void fromBytes(final ByteBuf data) {
        this.entityID = data.readInt();
        final int typeID = data.readByte();
        this.type = FXType.values()[typeID];
    }
    
    public enum FXType
    {
        HEARTS, 
        EATING, 
        SMOKE;
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketNPCFX, IMessage>
    {
        public IMessage onMessage(final LOTRPacketNPCFX packet, final MessageContext context) {
            final World world = LOTRMod.proxy.getClientWorld();
            final Entity entity = world.getEntityByID(packet.entityID);
            if (entity instanceof LOTREntityNPC) {
                final LOTREntityNPC npc = (LOTREntityNPC)entity;
                if (packet.type == FXType.HEARTS) {
                    npc.spawnHearts();
                }
                else if (packet.type == FXType.EATING) {
                    npc.spawnFoodParticles();
                }
                else if (packet.type == FXType.SMOKE) {
                    npc.spawnSmokes();
                }
            }
            return null;
        }
    }
}
