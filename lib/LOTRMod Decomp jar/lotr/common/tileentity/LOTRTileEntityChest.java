// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.block.LOTRBlockChest;
import java.util.Iterator;
import java.util.List;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;

public class LOTRTileEntityChest extends TileEntity implements IInventory
{
    private ItemStack[] chestContents;
    public float lidAngle;
    public float prevLidAngle;
    public String textureName;
    private int numPlayersUsing;
    private int ticksSinceSync;
    private String customName;
    
    public LOTRTileEntityChest() {
        this.chestContents = new ItemStack[this.getSizeInventory()];
    }
    
    public int getSizeInventory() {
        return 27;
    }
    
    public ItemStack getStackInSlot(final int i) {
        return this.chestContents[i];
    }
    
    public ItemStack decrStackSize(final int i, final int j) {
        if (this.chestContents[i] == null) {
            return null;
        }
        if (this.chestContents[i].stackSize <= j) {
            final ItemStack itemstack = this.chestContents[i];
            this.chestContents[i] = null;
            this.onInventoryChanged();
            return itemstack;
        }
        final ItemStack itemstack = this.chestContents[i].splitStack(j);
        if (this.chestContents[i].stackSize == 0) {
            this.chestContents[i] = null;
        }
        this.onInventoryChanged();
        return itemstack;
    }
    
    public ItemStack getStackInSlotOnClosing(final int i) {
        if (this.chestContents[i] != null) {
            final ItemStack itemstack = this.chestContents[i];
            this.chestContents[i] = null;
            return itemstack;
        }
        return null;
    }
    
    public void setInventorySlotContents(final int i, final ItemStack itemstack) {
        this.chestContents[i] = itemstack;
        if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()) {
            itemstack.stackSize = this.getInventoryStackLimit();
        }
        this.onInventoryChanged();
    }
    
    public String getInventoryName() {
        return this.isInventoryNameLocalized() ? this.customName : "container.chest";
    }
    
    public boolean isInventoryNameLocalized() {
        return this.customName != null && this.customName.length() > 0;
    }
    
    public void setCustomName(final String s) {
        this.customName = s;
    }
    
    public void readFromNBT(final NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        final NBTTagList itemTags = nbt.getTagList("Items", 10);
        this.chestContents = new ItemStack[this.getSizeInventory()];
        for (int i = 0; i < itemTags.tagCount(); ++i) {
            final NBTTagCompound slotData = itemTags.getCompoundTagAt(i);
            final int slot = slotData.getByte("Slot") & 0xFF;
            if (slot >= 0 && slot < this.chestContents.length) {
                this.chestContents[slot] = ItemStack.loadItemStackFromNBT(slotData);
            }
        }
        if (nbt.func_150297_b("CustomName", 8)) {
            this.customName = nbt.getString("CustomName");
        }
    }
    
    public void writeToNBT(final NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        final NBTTagList itemTags = new NBTTagList();
        for (int i = 0; i < this.chestContents.length; ++i) {
            if (this.chestContents[i] != null) {
                final NBTTagCompound slotData = new NBTTagCompound();
                slotData.setByte("Slot", (byte)i);
                this.chestContents[i].writeToNBT(slotData);
                itemTags.appendTag((NBTBase)slotData);
            }
        }
        nbt.setTag("Items", (NBTBase)itemTags);
        if (this.isInventoryNameLocalized()) {
            nbt.setString("CustomName", this.customName);
        }
    }
    
    public int getInventoryStackLimit() {
        return 64;
    }
    
    public boolean isUseableByPlayer(final EntityPlayer entityplayer) {
        return super.worldObj.getTileEntity(super.xCoord, super.yCoord, super.zCoord) == this && entityplayer.getDistanceSq(super.xCoord + 0.5, super.yCoord + 0.5, super.zCoord + 0.5) <= 64.0;
    }
    
    public void updateEntity() {
        super.updateEntity();
        this.prevLidAngle = this.lidAngle;
        ++this.ticksSinceSync;
        if (!super.worldObj.isClient && this.numPlayersUsing != 0 && (this.ticksSinceSync + super.xCoord + super.yCoord + super.zCoord) % 200 == 0) {
            this.numPlayersUsing = 0;
            final float range = 5.0f;
            final List players = super.worldObj.getEntitiesWithinAABB((Class)EntityPlayer.class, AxisAlignedBB.getBoundingBox((double)(super.xCoord - range), (double)(super.yCoord - range), (double)(super.zCoord - range), (double)(super.xCoord + 1 + range), (double)(super.yCoord + 1 + range), (double)(super.zCoord + 1 + range)));
            for (final Object obj : players) {
                final EntityPlayer entityplayer = (EntityPlayer)obj;
                if (entityplayer.openContainer instanceof ContainerChest) {
                    final IInventory iinventory = ((ContainerChest)entityplayer.openContainer).getLowerChestInventory();
                    if (iinventory != this) {
                        continue;
                    }
                    ++this.numPlayersUsing;
                }
            }
        }
        if (this.numPlayersUsing > 0 && this.lidAngle == 0.0f) {
            super.worldObj.playSoundEffect(super.xCoord + 0.5, super.yCoord + 0.5, super.zCoord + 0.5, "random.chestopen", 0.5f, super.worldObj.rand.nextFloat() * 0.1f + 0.9f);
        }
        if ((this.numPlayersUsing == 0 && this.lidAngle > 0.0f) || (this.numPlayersUsing > 0 && this.lidAngle < 1.0f)) {
            final float pre = this.lidAngle;
            final float incr = 0.1f;
            if (this.numPlayersUsing > 0) {
                this.lidAngle += incr;
            }
            else {
                this.lidAngle -= incr;
            }
            this.lidAngle = Math.min(this.lidAngle, 1.0f);
            this.lidAngle = Math.max(this.lidAngle, 0.0f);
            final float thr = 0.5f;
            if (this.lidAngle < thr && pre >= thr) {
                super.worldObj.playSoundEffect(super.xCoord + 0.5, super.yCoord + 0.5, super.zCoord + 0.5, "random.chestclosed", 0.5f, super.worldObj.rand.nextFloat() * 0.1f + 0.9f);
            }
        }
    }
    
    public void openChest() {
        if (this.numPlayersUsing < 0) {
            this.numPlayersUsing = 0;
        }
        ++this.numPlayersUsing;
        super.worldObj.func_147452_c(super.xCoord, super.yCoord, super.zCoord, this.getBlockType(), 1, this.numPlayersUsing);
        super.worldObj.notifyBlocksOfNeighborChange(super.xCoord, super.yCoord, super.zCoord, this.getBlockType());
        super.worldObj.notifyBlocksOfNeighborChange(super.xCoord, super.yCoord - 1, super.zCoord, this.getBlockType());
    }
    
    public void closeChest() {
        if (this.getBlockType() instanceof LOTRBlockChest) {
            --this.numPlayersUsing;
            super.worldObj.func_147452_c(super.xCoord, super.yCoord, super.zCoord, this.getBlockType(), 1, this.numPlayersUsing);
            super.worldObj.notifyBlocksOfNeighborChange(super.xCoord, super.yCoord, super.zCoord, this.getBlockType());
            super.worldObj.notifyBlocksOfNeighborChange(super.xCoord, super.yCoord - 1, super.zCoord, this.getBlockType());
        }
    }
    
    public boolean receiveClientEvent(final int i, final int j) {
        if (i == 1) {
            this.numPlayersUsing = j;
            return true;
        }
        return super.receiveClientEvent(i, j);
    }
    
    public boolean isItemValidForSlot(final int i, final ItemStack itemstack) {
        return true;
    }
    
    public void invalidate() {
        super.invalidate();
        this.updateContainingBlockInfo();
    }
    
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return AxisAlignedBB.getBoundingBox((double)(super.xCoord - 1), (double)super.yCoord, (double)(super.zCoord - 1), (double)(super.xCoord + 2), (double)(super.yCoord + 2), (double)(super.zCoord + 2));
    }
}
