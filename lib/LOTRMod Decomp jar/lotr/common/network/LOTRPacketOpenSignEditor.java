// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRMod;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import io.netty.buffer.ByteBuf;
import lotr.common.tileentity.LOTRTileEntitySign;
import net.minecraft.block.Block;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketOpenSignEditor implements IMessage
{
    private int posX;
    private int posY;
    private int posZ;
    private Block blockType;
    private int blockMeta;
    
    public LOTRPacketOpenSignEditor() {
    }
    
    public LOTRPacketOpenSignEditor(final LOTRTileEntitySign sign) {
        this.posX = sign.xCoord;
        this.posY = sign.yCoord;
        this.posZ = sign.zCoord;
        this.blockType = sign.getBlockType();
        this.blockMeta = sign.getBlockMetadata();
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeInt(this.posX);
        data.writeInt(this.posY);
        data.writeInt(this.posZ);
        data.writeShort(Block.getIdFromBlock(this.blockType));
        data.writeByte(this.blockMeta);
    }
    
    public void fromBytes(final ByteBuf data) {
        this.posX = data.readInt();
        this.posY = data.readInt();
        this.posZ = data.readInt();
        this.blockType = Block.getBlockById((int)data.readShort());
        this.blockMeta = data.readByte();
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketOpenSignEditor, IMessage>
    {
        public IMessage onMessage(final LOTRPacketOpenSignEditor packet, final MessageContext context) {
            final EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
            final World world = LOTRMod.proxy.getClientWorld();
            world.setBlock(packet.posX, packet.posY, packet.posZ, packet.blockType, packet.blockMeta, 3);
            entityplayer.openGui((Object)LOTRMod.instance, 47, world, packet.posX, packet.posY, packet.posZ);
            return null;
        }
    }
}
