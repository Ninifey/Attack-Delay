// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import lotr.common.entity.npc.LOTREntityTree;
import lotr.common.entity.npc.LOTREntityMallornEnt;
import lotr.common.LOTRMod;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketMallornEntSummon implements IMessage
{
    private int mallornEntID;
    private int summonedID;
    
    public LOTRPacketMallornEntSummon() {
    }
    
    public LOTRPacketMallornEntSummon(final int id1, final int id2) {
        this.mallornEntID = id1;
        this.summonedID = id1;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeInt(this.mallornEntID);
        data.writeInt(this.summonedID);
    }
    
    public void fromBytes(final ByteBuf data) {
        this.mallornEntID = data.readInt();
        this.summonedID = data.readInt();
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketMallornEntSummon, IMessage>
    {
        public IMessage onMessage(final LOTRPacketMallornEntSummon packet, final MessageContext context) {
            final World world = LOTRMod.proxy.getClientWorld();
            final Entity entity = world.getEntityByID(packet.mallornEntID);
            if (entity instanceof LOTREntityMallornEnt) {
                final LOTREntityMallornEnt ent = (LOTREntityMallornEnt)entity;
                final Entity summoned = world.getEntityByID(packet.summonedID);
                if (summoned instanceof LOTREntityTree) {
                    ent.spawnEntSummonParticles((LOTREntityTree)summoned);
                }
            }
            return null;
        }
    }
}
