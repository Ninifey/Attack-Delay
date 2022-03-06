// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import java.util.Random;
import net.minecraft.world.World;
import lotr.common.LOTRMod;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketBlockFX implements IMessage
{
    private Type type;
    private int blockX;
    private int blockY;
    private int blockZ;
    
    public LOTRPacketBlockFX() {
    }
    
    public LOTRPacketBlockFX(final Type t, final int i, final int j, final int k) {
        this.type = t;
        this.blockX = i;
        this.blockY = j;
        this.blockZ = k;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeByte(this.type.ordinal());
        data.writeInt(this.blockX);
        data.writeInt(this.blockY);
        data.writeInt(this.blockZ);
    }
    
    public void fromBytes(final ByteBuf data) {
        final int typeID = data.readByte();
        this.type = Type.values()[typeID];
        this.blockX = data.readInt();
        this.blockY = data.readInt();
        this.blockZ = data.readInt();
    }
    
    public enum Type
    {
        UTUMNO_EVAPORATE;
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketBlockFX, IMessage>
    {
        public IMessage onMessage(final LOTRPacketBlockFX packet, final MessageContext context) {
            final World world = LOTRMod.proxy.getClientWorld();
            final int i = packet.blockX;
            final int j = packet.blockY;
            final int k = packet.blockZ;
            final Random rand = world.rand;
            if (packet.type == Type.UTUMNO_EVAPORATE) {
                for (int l = 0; l < 8; ++l) {
                    world.spawnParticle("largesmoke", (double)(i + rand.nextFloat()), (double)(j + rand.nextFloat()), (double)(k + rand.nextFloat()), 0.0, 0.0, 0.0);
                }
            }
            return null;
        }
    }
}
