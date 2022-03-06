// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.network;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import lotr.common.tileentity.LOTRTileEntitySign;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LOTRPacketEditSign implements IMessage
{
    private int posX;
    private int posY;
    private int posZ;
    private String[] signText;
    
    public LOTRPacketEditSign() {
    }
    
    public LOTRPacketEditSign(final LOTRTileEntitySign sign) {
        this.posX = sign.xCoord;
        this.posY = sign.yCoord;
        this.posZ = sign.zCoord;
        this.signText = sign.signText;
    }
    
    public void toBytes(final ByteBuf data) {
        data.writeInt(this.posX);
        data.writeInt(this.posY);
        data.writeInt(this.posZ);
        data.writeByte(this.signText.length);
        for (int i = 0; i < this.signText.length; ++i) {
            final String line = this.signText[i];
            if (line == null) {
                data.writeShort(-1);
            }
            else {
                final byte[] lineBytes = line.getBytes(Charsets.UTF_8);
                data.writeShort(lineBytes.length);
                data.writeBytes(lineBytes);
            }
        }
    }
    
    public void fromBytes(final ByteBuf data) {
        this.posX = data.readInt();
        this.posY = data.readInt();
        this.posZ = data.readInt();
        final int lines = data.readByte();
        this.signText = new String[lines];
        for (int i = 0; i < this.signText.length; ++i) {
            final int length = data.readShort();
            if (length > -1) {
                final String line = data.readBytes(length).toString(Charsets.UTF_8);
                this.signText[i] = line;
            }
            else {
                this.signText[i] = "";
            }
        }
    }
    
    public static class Handler implements IMessageHandler<LOTRPacketEditSign, IMessage>
    {
        public IMessage onMessage(final LOTRPacketEditSign packet, final MessageContext context) {
            final EntityPlayerMP entityplayer = context.getServerHandler().playerEntity;
            entityplayer.func_143004_u();
            final World world = ((Entity)entityplayer).worldObj;
            final int i = packet.posX;
            final int j = packet.posY;
            final int k = packet.posZ;
            final String[] newText = packet.signText;
            if (world.blockExists(i, j, k)) {
                final TileEntity te = world.getTileEntity(i, j, k);
                if (te instanceof LOTRTileEntitySign) {
                    final LOTRTileEntitySign sign = (LOTRTileEntitySign)te;
                    if (!sign.isEditable() || sign.getEditingPlayer() != entityplayer) {
                        MinecraftServer.getServer().logWarning("Player " + entityplayer.getCommandSenderName() + " just tried to change non-editable LOTR sign");
                        return null;
                    }
                    for (int l = 0; l < sign.getNumLines(); ++l) {
                        final String line = newText[l];
                        boolean valid = true;
                        if (line.length() > 15) {
                            valid = false;
                        }
                        else {
                            for (int c = 0; c < line.length(); ++c) {
                                if (!ChatAllowedCharacters.isAllowedCharacter(line.charAt(c))) {
                                    valid = false;
                                }
                            }
                        }
                        if (!valid) {
                            newText[l] = "!?";
                        }
                    }
                    System.arraycopy(newText, 0, sign.signText, 0, sign.getNumLines());
                    sign.onInventoryChanged();
                    world.markBlockForUpdate(i, j, k);
                }
            }
            return null;
        }
    }
}
