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

public class LOTRPacketNPCCombatStance implements IMessage
{
    private int entityID;
    private boolean combatStance;
    
    public LOTRPacketNPCCombatStance() {
    }
    
    public LOTRPacketNPCCombatStance(final int id, final boolean flag) {
        this.entityID = id;
        this.combatStance = flag;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeInt(this.entityID);
        data.writeBoolean(this.combatStance);
    }
    
    public void fromBytes(final ByteBuf data) {
        this.entityID = data.readInt();
        this.combatStance = data.readBoolean();
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketNPCCombatStance, IMessage>
    {
        public IMessage onMessage(final LOTRPacketNPCCombatStance packet, final MessageContext context) {
            final World world = LOTRMod.proxy.getClientWorld();
            final Entity entity = world.getEntityByID(packet.entityID);
            if (entity instanceof LOTREntityNPC) {
                ((LOTREntityNPC)entity).clientCombatStance = packet.combatStance;
            }
            return null;
        }
    }
}
