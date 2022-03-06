// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.tileentity;

import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.network.NetworkManager;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.recipe.LOTRMillstoneRecipes;
import lotr.common.block.LOTRBlockMillstone;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;

public class LOTRTileEntityMillstone extends TileEntity implements IInventory, ISidedInventory
{
    protected ItemStack[] inventory;
    private static final int inputSlot = 0;
    private static final int outputSlot = 1;
    private String specialMillstoneName;
    public boolean isMilling;
    public int currentMillTime;
    private static final int fullMillTime = 200;
    
    public LOTRTileEntityMillstone() {
        this.inventory = new ItemStack[2];
        this.currentMillTime = 0;
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
            return itemstack;
        }
        final ItemStack itemstack = this.inventory[i].splitStack(j);
        if (this.inventory[i].stackSize == 0) {
            this.inventory[i] = null;
        }
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
    }
    
    public String getInventoryName() {
        return this.isInventoryNameLocalized() ? this.specialMillstoneName : StatCollector.translateToLocal("container.lotr.millstone");
    }
    
    public boolean isInventoryNameLocalized() {
        return this.specialMillstoneName != null && this.specialMillstoneName.length() > 0;
    }
    
    public void setSpecialMillstoneName(final String s) {
        this.specialMillstoneName = s;
    }
    
    public void readFromNBT(final NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        final NBTTagList items = nbt.getTagList("Items", 10);
        this.inventory = new ItemStack[this.getSizeInventory()];
        for (int i = 0; i < items.tagCount(); ++i) {
            final NBTTagCompound itemData = items.getCompoundTagAt(i);
            final int slot = itemData.getByte("Slot");
            if (slot >= 0 && slot < this.inventory.length) {
                this.inventory[slot] = ItemStack.loadItemStackFromNBT(itemData);
            }
        }
        this.isMilling = nbt.getBoolean("Milling");
        this.currentMillTime = nbt.getInteger("MillTime");
        if (nbt.hasKey("CustomName")) {
            this.specialMillstoneName = nbt.getString("CustomName");
        }
    }
    
    public void writeToNBT(final NBTTagCompound nbt) {
        super.writeToNBT(nbt);
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
        nbt.setBoolean("Milling", this.isMilling);
        nbt.setInteger("MillTime", this.currentMillTime);
        if (this.isInventoryNameLocalized()) {
            nbt.setString("CustomName", this.specialMillstoneName);
        }
    }
    
    public int getInventoryStackLimit() {
        return 64;
    }
    
    @SideOnly(Side.CLIENT)
    public int getMillProgressScaled(final int i) {
        return this.currentMillTime * i / 200;
    }
    
    public boolean isMilling() {
        return this.isMilling;
    }
    
    protected void toggleMillstoneActive() {
        LOTRBlockMillstone.toggleMillstoneActive(super.worldObj, super.xCoord, super.yCoord, super.zCoord);
    }
    
    public void updateEntity() {
        boolean needUpdate = false;
        if (!super.worldObj.isClient) {
            final boolean powered = super.worldObj.isBlockIndirectlyGettingPowered(super.xCoord, super.yCoord, super.zCoord);
            if (powered && !this.isMilling) {
                this.isMilling = true;
                this.currentMillTime = 0;
                needUpdate = true;
                this.toggleMillstoneActive();
            }
            else if (!powered && this.isMilling) {
                this.isMilling = false;
                this.currentMillTime = 0;
                needUpdate = true;
                this.toggleMillstoneActive();
            }
            if (this.isMilling && this.canMill()) {
                ++this.currentMillTime;
                if (this.currentMillTime == 200) {
                    this.currentMillTime = 0;
                    this.millItem();
                    needUpdate = true;
                }
            }
            else if (this.currentMillTime != 0) {
                this.currentMillTime = 0;
                needUpdate = true;
            }
        }
        if (needUpdate) {
            this.onInventoryChanged();
        }
    }
    
    private boolean canMill() {
        final ItemStack itemstack = this.inventory[0];
        if (itemstack == null) {
            return false;
        }
        final LOTRMillstoneRecipes.MillstoneResult result = LOTRMillstoneRecipes.getMillingResult(itemstack);
        if (result == null) {
            return false;
        }
        final ItemStack resultItem = result.resultItem;
        final ItemStack inResultSlot = this.inventory[1];
        if (inResultSlot == null) {
            return true;
        }
        if (!inResultSlot.isItemEqual(resultItem)) {
            return false;
        }
        final int resultSize = inResultSlot.stackSize + resultItem.stackSize;
        return resultSize <= this.getInventoryStackLimit() && resultSize <= resultItem.getMaxStackSize();
    }
    
    private void millItem() {
        if (this.canMill()) {
            final ItemStack itemstack = this.inventory[0];
            final LOTRMillstoneRecipes.MillstoneResult result = LOTRMillstoneRecipes.getMillingResult(itemstack);
            final ItemStack resultItem = result.resultItem;
            final float chance = result.chance;
            if (super.worldObj.rand.nextFloat() < chance) {
                ItemStack inResultSlot = this.inventory[1];
                if (inResultSlot == null) {
                    inResultSlot = resultItem.copy();
                }
                else if (inResultSlot.isItemEqual(resultItem)) {
                    final ItemStack itemStack = inResultSlot;
                    itemStack.stackSize += resultItem.stackSize;
                }
                this.inventory[1] = inResultSlot;
            }
            final ItemStack itemStack2 = itemstack;
            --itemStack2.stackSize;
            if (itemstack.stackSize <= 0) {
                this.inventory[0] = null;
            }
        }
    }
    
    public boolean isUseableByPlayer(final EntityPlayer entityplayer) {
        return super.worldObj.getTileEntity(super.xCoord, super.yCoord, super.zCoord) == this && entityplayer.getDistanceSq(super.xCoord + 0.5, super.yCoord + 0.5, super.zCoord + 0.5) <= 64.0;
    }
    
    public void openChest() {
    }
    
    public void closeChest() {
    }
    
    public boolean isItemValidForSlot(final int slot, final ItemStack itemstack) {
        return slot == 0 && itemstack != null && LOTRMillstoneRecipes.getMillingResult(itemstack) != null;
    }
    
    public int[] getSlotsForFace(final int side) {
        if (side == 0) {
            return new int[] { 1 };
        }
        if (side == 1) {
            return new int[] { 0 };
        }
        return new int[] { 0 };
    }
    
    public boolean canInsertItem(final int slot, final ItemStack itemstack, final int side) {
        return this.isItemValidForSlot(slot, itemstack);
    }
    
    public boolean canExtractItem(final int slot, final ItemStack itemstack, final int side) {
        return side != 0 || true;
    }
    
    public void onDataPacket(final NetworkManager networkManager, final S35PacketUpdateTileEntity packet) {
        if (packet.func_148857_g() != null && packet.func_148857_g().hasKey("CustomName")) {
            this.specialMillstoneName = packet.func_148857_g().getString("CustomName");
        }
    }
}
