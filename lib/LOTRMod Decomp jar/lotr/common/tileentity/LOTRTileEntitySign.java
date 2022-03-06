// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.tileentity;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketOpenSignEditor;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.network.Packet;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public abstract class LOTRTileEntitySign extends TileEntity
{
    public String[] signText;
    public static final int MAX_LINE_LENGTH = 15;
    public int lineBeingEdited;
    private boolean editable;
    private EntityPlayer editingPlayer;
    public boolean isFakeGuiSign;
    
    public LOTRTileEntitySign() {
        this.lineBeingEdited = -1;
        this.editable = true;
        this.isFakeGuiSign = false;
        this.signText = new String[this.getNumLines()];
        for (int l = 0; l < this.signText.length; ++l) {
            this.signText[l] = "";
        }
    }
    
    public abstract int getNumLines();
    
    public void writeToNBT(final NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        this.writeSignText(nbt);
    }
    
    private void writeSignText(final NBTTagCompound nbt) {
        for (int i = 0; i < this.signText.length; ++i) {
            nbt.setString("Text" + (i + 1), this.signText[i]);
        }
    }
    
    public void readFromNBT(final NBTTagCompound nbt) {
        this.editable = false;
        super.readFromNBT(nbt);
        this.readSignText(nbt);
    }
    
    private void readSignText(final NBTTagCompound nbt) {
        for (int i = 0; i < this.signText.length; ++i) {
            this.signText[i] = nbt.getString("Text" + (i + 1));
            if (this.signText[i].length() > 15) {
                this.signText[i] = this.signText[i].substring(0, 15);
            }
        }
    }
    
    public Packet getDescriptionPacket() {
        final NBTTagCompound data = new NBTTagCompound();
        this.writeSignText(data);
        return (Packet)new S35PacketUpdateTileEntity(super.xCoord, super.yCoord, super.zCoord, 0, data);
    }
    
    public void onDataPacket(final NetworkManager manager, final S35PacketUpdateTileEntity packet) {
        final NBTTagCompound data = packet.func_148857_g();
        this.readSignText(data);
    }
    
    public boolean isEditable() {
        return this.editable;
    }
    
    public void setEditable(final boolean flag) {
        if (!(this.editable = flag)) {
            this.editingPlayer = null;
        }
    }
    
    public void setEditingPlayer(final EntityPlayer entityplayer) {
        this.editingPlayer = entityplayer;
    }
    
    public EntityPlayer getEditingPlayer() {
        return this.editingPlayer;
    }
    
    public void openEditGUI(final EntityPlayerMP entityplayer) {
        this.setEditingPlayer((EntityPlayer)entityplayer);
        final LOTRPacketOpenSignEditor packet = new LOTRPacketOpenSignEditor(this);
        LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, entityplayer);
    }
}
