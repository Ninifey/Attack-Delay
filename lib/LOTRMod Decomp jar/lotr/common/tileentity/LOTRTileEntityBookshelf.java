// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.tileentity;

import net.minecraft.item.Item;
import lotr.common.item.LOTRItemModifierTemplate;
import net.minecraft.init.Items;
import net.minecraft.item.ItemMapBase;
import net.minecraft.item.ItemEnchantedBook;
import lotr.common.LOTRMod;
import lotr.common.item.LOTRItemRedBook;
import net.minecraft.item.ItemEditableBook;
import net.minecraft.item.ItemWritableBook;
import net.minecraft.item.ItemBook;
import lotr.common.block.LOTRBlockBookshelfStorage;
import java.util.Iterator;
import java.util.List;
import lotr.common.inventory.LOTRContainerBookshelf;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;

public class LOTRTileEntityBookshelf extends TileEntity implements IInventory
{
    private ItemStack[] chestContents;
    public int numPlayersUsing;
    private int ticksSinceSync;
    
    public LOTRTileEntityBookshelf() {
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
        return "container.lotr.bookshelf";
    }
    
    public boolean isInventoryNameLocalized() {
        return false;
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
    }
    
    public int getInventoryStackLimit() {
        return 64;
    }
    
    public boolean isUseableByPlayer(final EntityPlayer entityplayer) {
        return super.worldObj.getTileEntity(super.xCoord, super.yCoord, super.zCoord) == this && entityplayer.getDistanceSq(super.xCoord + 0.5, super.yCoord + 0.5, super.zCoord + 0.5) <= 64.0;
    }
    
    public void updateEntity() {
        super.updateEntity();
        ++this.ticksSinceSync;
        if (!super.worldObj.isClient && this.numPlayersUsing != 0 && (this.ticksSinceSync + super.xCoord + super.yCoord + super.zCoord) % 200 == 0) {
            this.numPlayersUsing = 0;
            final float range = 16.0f;
            final List players = super.worldObj.getEntitiesWithinAABB((Class)EntityPlayer.class, AxisAlignedBB.getBoundingBox((double)(super.xCoord - range), (double)(super.yCoord - range), (double)(super.zCoord - range), (double)(super.xCoord + 1 + range), (double)(super.yCoord + 1 + range), (double)(super.zCoord + 1 + range)));
            for (final Object obj : players) {
                final EntityPlayer entityplayer = (EntityPlayer)obj;
                if (entityplayer.openContainer instanceof LOTRContainerBookshelf) {
                    final IInventory playerShelfInv = (IInventory)((LOTRContainerBookshelf)entityplayer.openContainer).shelfInv;
                    if (playerShelfInv != this) {
                        continue;
                    }
                    ++this.numPlayersUsing;
                }
            }
        }
    }
    
    public void openChest() {
        if (this.numPlayersUsing < 0) {
            this.numPlayersUsing = 0;
        }
        ++this.numPlayersUsing;
    }
    
    public void closeChest() {
        if (this.getBlockType() instanceof LOTRBlockBookshelfStorage) {
            --this.numPlayersUsing;
        }
    }
    
    public boolean isItemValidForSlot(final int i, final ItemStack itemstack) {
        return isBookItem(itemstack);
    }
    
    public void invalidate() {
        super.invalidate();
        this.updateContainingBlockInfo();
    }
    
    public static boolean isBookItem(final ItemStack itemstack) {
        if (itemstack != null) {
            final Item item = itemstack.getItem();
            if (item instanceof ItemBook || item instanceof ItemWritableBook || item instanceof ItemEditableBook) {
                return true;
            }
            if (item instanceof LOTRItemRedBook || item == LOTRMod.mithrilBook) {
                return true;
            }
            if (item instanceof ItemEnchantedBook) {
                return true;
            }
            if (item instanceof ItemMapBase) {
                return true;
            }
            if (item == Items.paper) {
                return true;
            }
            if (item instanceof LOTRItemModifierTemplate) {
                return true;
            }
        }
        return false;
    }
}
