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

public class LOTRPacketNPCIsOfferingQuest implements IMessage
{
    private int entityID;
    public boolean offering;
    public int offerColor;
    
    public LOTRPacketNPCIsOfferingQuest() {
    }
    
    public LOTRPacketNPCIsOfferingQuest(final int id, final boolean flag, final int color) {
        this.entityID = id;
        this.offering = flag;
        this.offerColor = color;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeInt(this.entityID);
        data.writeBoolean(this.offering);
        data.writeInt(this.offerColor);
    }
    
    public void fromBytes(final ByteBuf data) {
        this.entityID = data.readInt();
        this.offering = data.readBoolean();
        this.offerColor = data.readInt();
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketNPCIsOfferingQuest, IMessage>
    {
        public IMessage onMessage(final LOTRPacketNPCIsOfferingQuest packet, final MessageContext context) {
            final World world = LOTRMod.proxy.getClientWorld();
            final Entity entity = world.getEntityByID(packet.entityID);
            if (entity instanceof LOTREntityNPC) {
                ((LOTREntityNPC)entity).questInfo.receiveData(packet);
            }
            return null;
        }
    }
}
