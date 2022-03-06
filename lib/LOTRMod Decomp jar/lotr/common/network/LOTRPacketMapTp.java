// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.server.CommandTeleport;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketMapTp implements IMessage
{
    private int xCoord;
    private int zCoord;
    
    public LOTRPacketMapTp() {
    }
    
    public LOTRPacketMapTp(final int x, final int z) {
        this.xCoord = x;
        this.zCoord = z;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeInt(this.xCoord);
        data.writeInt(this.zCoord);
    }
    
    public void fromBytes(final ByteBuf data) {
        this.xCoord = data.readInt();
        this.zCoord = data.readInt();
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketMapTp, IMessage>
    {
        public IMessage onMessage(final LOTRPacketMapTp packet, final MessageContext context) {
            final EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            final World world = ((Entity)entityplayer).worldObj;
            final boolean isOp = MinecraftServer.getServer().getConfigurationManager().func_152596_g(entityplayer.getGameProfile());
            if (isOp) {
                final int i = packet.xCoord;
                final int k = packet.zCoord;
                final int j = world.getTopSolidOrLiquidBlock(i, k);
                final String[] args = { Integer.toString(i), Integer.toString(j), Integer.toString(k) };
                new CommandTeleport().processCommand((ICommandSender)entityplayer, args);
            }
            return null;
        }
    }
}
