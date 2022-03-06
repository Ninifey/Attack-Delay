// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.tileentity;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.network.Packet;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;

public class LOTRTileEntityArmorStand extends TileEntity implements IInventory
{
    private ItemStack[] inventory;
    public int ticksExisted;
    
    public LOTRTileEntityArmorStand() {
        this.inventory = new ItemStack[4];
    }
    
    public void setWorldObj(final World world) {
        super.setWorldObj(world);
        this.ticksExisted = world.rand.nextInt(100);
    }
    
    public void updateEntity() {
        ++this.ticksExisted;
    }
    
    public int getSizeInventory() {
        return this.inventory.length;
    }
    
    public ItemStack getStackInSlot(final int i) {
        return this.inventory[i];
    }
    
    public ItemStack decrStackSize(final int i, final int j) {
        if (this.inventory[i] == null) {
            return null;
        }
        if (this.inventory[i].stackSize <= j) {
            final ItemStack itemstack = this.inventory[i];
            this.inventory[i] = null;
            this.onInventoryChanged();
            return itemstack;
        }
        final ItemStack itemstack = this.inventory[i].splitStack(j);
        if (this.inventory[i].stackSize == 0) {
            this.inventory[i] = null;
        }
        this.onInventoryChanged();
        return itemstack;
    }
    
    public ItemStack getStackInSlotOnClosing(final int i) {
        if (this.inventory[i] != null) {
            final ItemStack itemstack = this.inventory[i];
            this.inventory[i] = null;
            return itemstack;
        }
        return null;
    }
    
    public void setInventorySlotContents(final int i, final ItemStack itemstack) {
        this.inventory[i] = itemstack;
        if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()) {
            itemstack.stackSize = this.getInventoryStackLimit();
        }
        this.onInventoryChanged();
    }
    
    public void onInventoryChanged() {
        super.onInventoryChanged();
        super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
    }
    
    public String getInventoryName() {
        return StatCollector.translateToLocal("container.lotr.armorStand");
    }
    
    public boolean isInventoryNameLocalized() {
        return false;
    }
    
    public void readFromNBT(final NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.readArmorStandFromNBT(nbt);
    }
    
    private void readArmorStandFromNBT(final NBTTagCompound nbt) {
        final NBTTagList items = nbt.getTagList("Items", 10);
        this.inventory = new ItemStack[this.getSizeInventory()];
        for (int i = 0; i < items.tagCount(); ++i) {
            final NBTTagCompound itemData = items.getCompoundTagAt(i);
            final byte byte0 = itemData.getByte("Slot");
            if (byte0 >= 0 && byte0 < this.inventory.length) {
                this.inventory[byte0] = ItemStack.loadItemStackFromNBT(itemData);
            }
        }
    }
    
    public void writeToNBT(final NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        this.writeArmorStandToNBT(nbt);
    }
    
    private void writeArmorStandToNBT(final NBTTagCompound nbt) {
        final NBTTagList items = new NBTTagList();
        for (int i = 0; i < this.inventory.length; ++i) {
            if (this.inventory[i] != null) {
                final NBTTagCompound itemData = new NBTTagCompound();
                itemData.setByte("Slot", (byte)i);
                this.inventory[i].writeToNBT(itemData);
                items.appendTag((NBTBase)itemData);
            }
        }
        nbt.setTag("Items", (NBTBase)items);
    }
    
    public int getInventoryStackLimit() {
        return 64;
    }
    
    public boolean isUseableByPlayer(final EntityPlayer entityplayer) {
        return super.worldObj.getTileEntity(super.xCoord, super.yCoord, super.zCoord) == this && entityplayer.getDistanceSq(super.xCoord + 0.5, super.yCoord + 0.5, super.zCoord + 0.5) <= 64.0;
    }
    
    public void openChest() {
    }
    
    public void closeChest() {
    }
    
    public boolean isItemValidForSlot(final int slot, final ItemStack itemstack) {
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return AxisAlignedBB.getBoundingBox((double)(super.xCoord - 1), (double)super.yCoord, (double)(super.zCoord - 1), (double)(super.xCoord + 1), (double)(super.yCoord + 2), (double)(super.zCoord + 1));
    }
    
    public Packet getDescriptionPacket() {
        final NBTTagCompound data = new NBTTagCompound();
        this.writeArmorStandToNBT(data);
        return (Packet)new S35PacketUpdateTileEntity(super.xCoord, super.yCoord, super.zCoord, 0, data);
    }
    
    public void onDataPacket(final NetworkManager manager, final S35PacketUpdateTileEntity packet) {
        this.readArmorStandFromNBT(packet.func_148857_g());
    }
}
