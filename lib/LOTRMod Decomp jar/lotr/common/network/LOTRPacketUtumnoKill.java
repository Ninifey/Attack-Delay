// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import lotr.common.LOTRMod;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketUtumnoKill implements IMessage
{
    private int entityID;
    private int blockX;
    private int blockY;
    private int blockZ;
    
    public LOTRPacketUtumnoKill() {
    }
    
    public LOTRPacketUtumnoKill(final int id, final int i, final int j, final int k) {
        this.entityID = id;
        this.blockX = i;
        this.blockY = j;
        this.blockZ = k;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeInt(this.entityID);
        data.writeInt(this.blockX);
        data.writeInt(this.blockY);
        data.writeInt(this.blockZ);
    }
    
    public void fromBytes(final ByteBuf data) {
        this.entityID = data.readInt();
        this.blockX = data.readInt();
        this.blockY = data.readInt();
        this.blockZ = data.readInt();
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketUtumnoKill, IMessage>
    {
        public IMessage onMessage(final LOTRPacketUtumnoKill packet, final MessageContext context) {
            final World world = LOTRMod.proxy.getClientWorld();
            final Entity entity = world.getEntityByID(packet.entityID);
            if (entity != null) {
                final int i1 = packet.blockX;
                final int j1 = packet.blockY;
                final int k1 = packet.blockZ;
                final Block block = world.getBlock(i1, j1, k1);
                block.setBlockBoundsBasedOnState((IBlockAccess)world, i1, j1, k1);
                final double blockTop = block.getBlockBoundsMaxY();
                for (int l = 0; l < 8; ++l) {
                    final double d = i1 + MathHelper.getRandomDoubleInRange(world.rand, 0.25, 0.75);
                    final double d2 = j1 + 0.1;
                    final double d3 = k1 + MathHelper.getRandomDoubleInRange(world.rand, 0.25, 0.75);
                    final double d4 = 0.0;
                    final double d5 = MathHelper.getRandomDoubleInRange(world.rand, 0.1, 0.2);
                    final double d6 = 0.0;
                    LOTRMod.proxy.spawnParticle("utumnoKill", d, d2, d3, d4, d5, d6);
                }
                for (int l = 0; l < 12; ++l) {
                    final double d = entity.posX + world.rand.nextGaussian() * 0.8;
                    final double d2 = entity.boundingBox.minY + entity.height * 0.7 + world.rand.nextGaussian() * 0.2;
                    final double d3 = entity.posZ + world.rand.nextGaussian() * 0.8;
                    double d4 = i1 + 0.5 - d;
                    double d5 = j1 + blockTop - d2;
                    double d6 = k1 + 0.5 - d3;
                    final double v = 0.05;
                    d4 *= v;
                    d5 *= v;
                    d6 *= v;
                    LOTRMod.proxy.spawnParticle("utumnoKill", d, d2, d3, d4, d5, d6);
                }
            }
            return null;
        }
    }
}
