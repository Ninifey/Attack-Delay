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

public class LOTRPacketBiomeVariantsUnwatch implements IMessage
{
    private int chunkX;
    private int chunkZ;
    
    public LOTRPacketBiomeVariantsUnwatch() {
    }
    
    public LOTRPacketBiomeVariantsUnwatch(final int x, final int z) {
        this.chunkX = x;
        this.chunkZ = z;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeInt(this.chunkX);
        data.writeInt(this.chunkZ);
    }
    
    public void fromBytes(final ByteBuf data) {
        this.chunkX = data.readInt();
        this.chunkZ = data.readInt();
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketBiomeVariantsUnwatch, IMessage>
    {
        public IMessage onMessage(final LOTRPacketBiomeVariantsUnwatch packet, final MessageContext context) {
            final World world = LOTRMod.proxy.getClientWorld();
            final int chunkX = packet.chunkX;
            final int chunkZ = packet.chunkZ;
            if (world.blockExists(chunkX << 4, 0, chunkZ << 4)) {
                LOTRBiomeVariantStorage.clearChunkBiomeVariants(world, new ChunkCoordIntPair(chunkX, chunkZ));
            }
            else {
                FMLLog.severe("Client received LOTR biome variant unwatch packet for nonexistent chunk at %d, %d", new Object[] { chunkX << 4, chunkZ << 4 });
            }
            return null;
        }
    }
}
