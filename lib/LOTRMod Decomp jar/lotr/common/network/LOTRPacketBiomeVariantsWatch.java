// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import net.minecraft.world.World;
import cpw.mods.fml.common.FMLLog;
import lotr.common.world.biome.variant.LOTRBiomeVariantStorage;
import net.minecraft.world.ChunkCoordIntPair;
import lotr.common.LOTRMod;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketBiomeVariantsWatch implements IMessage
{
    private int chunkX;
    private int chunkZ;
    private byte[] variants;
    
    public LOTRPacketBiomeVariantsWatch() {
    }
    
    public LOTRPacketBiomeVariantsWatch(final int x, final int z, final byte[] v) {
        this.chunkX = x;
        this.chunkZ = z;
        this.variants = v;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeInt(this.chunkX);
        data.writeInt(this.chunkZ);
        data.writeInt(this.variants.length);
        data.writeBytes(this.variants);
    }
    
    public void fromBytes(final ByteBuf data) {
        this.chunkX = data.readInt();
        this.chunkZ = data.readInt();
        final int length = data.readInt();
        this.variants = data.readBytes(length).array();
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketBiomeVariantsWatch, IMessage>
    {
        public IMessage onMessage(final LOTRPacketBiomeVariantsWatch packet, final MessageContext context) {
            final World world = LOTRMod.proxy.getClientWorld();
            final int chunkX = packet.chunkX;
            final int chunkZ = packet.chunkZ;
            if (world.blockExists(chunkX << 4, 0, chunkZ << 4)) {
                LOTRBiomeVariantStorage.setChunkBiomeVariants(world, new ChunkCoordIntPair(chunkX, chunkZ), packet.variants);
            }
            else {
                FMLLog.severe("Client received LOTR biome variant data for nonexistent chunk at %d, %d", new Object[] { chunkX << 4, chunkZ << 4 });
            }
            return null;
        }
    }
}
