// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import cpw.mods.fml.common.network.internal.EntitySpawnHandler;
import lotr.common.util.LOTRLog;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import io.netty.channel.ChannelHandlerContext;
import cpw.mods.fml.common.network.internal.FMLRuntimeCodec;
import cpw.mods.fml.common.network.internal.FMLMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketSpawnMob implements IMessage
{
    private ByteBuf spawnData;
    
    public LOTRPacketSpawnMob() {
    }
    
    public LOTRPacketSpawnMob(final ByteBuf data) {
        this.spawnData = data.copy();
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeInt(this.spawnData.readableBytes());
        data.writeBytes(this.spawnData);
    }
    
    public void fromBytes(final ByteBuf data) {
        final int len = data.readInt();
        this.spawnData = data.readBytes(len);
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketSpawnMob, IMessage>
    {
        public IMessage onMessage(final LOTRPacketSpawnMob packet, final MessageContext context) {
            final FMLMessage.EntitySpawnMessage msg = new FMLMessage.EntitySpawnMessage();
            new FMLRuntimeCodec().decodeInto((ChannelHandlerContext)null, packet.spawnData, (FMLMessage)msg);
            int modEntityID = 999999999;
            double x = 999.0;
            double y = 999.0;
            double z = 999.0;
            try {
                modEntityID = (int)ObfuscationReflectionHelper.getPrivateValue((Class)FMLMessage.EntitySpawnMessage.class, (Object)msg, new String[] { "modEntityTypeId" });
                x = (double)ObfuscationReflectionHelper.getPrivateValue((Class)FMLMessage.EntitySpawnMessage.class, (Object)msg, new String[] { "scaledX" });
                y = (double)ObfuscationReflectionHelper.getPrivateValue((Class)FMLMessage.EntitySpawnMessage.class, (Object)msg, new String[] { "scaledY" });
                z = (double)ObfuscationReflectionHelper.getPrivateValue((Class)FMLMessage.EntitySpawnMessage.class, (Object)msg, new String[] { "scaledZ" });
            }
            catch (Exception ex) {}
            LOTRLog.logger.info("LOTR: Received mob spawn packet: " + modEntityID + "[" + x + ", " + y + ", " + z + "]");
            try {
                new AdhocEntitySpawnHandler().channelRead0(null, (FMLMessage.EntityMessage)msg);
            }
            catch (Exception e) {
                LOTRLog.logger.error("LOTR: FATAL ERROR spawning entity!!!");
                e.printStackTrace();
            }
            return null;
        }
    }
    
    private static class AdhocEntitySpawnHandler extends EntitySpawnHandler
    {
        public void channelRead0(final ChannelHandlerContext ctx, final FMLMessage.EntityMessage msg) throws Exception {
            super.channelRead0(ctx, msg);
        }
    }
}
