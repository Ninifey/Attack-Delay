// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.tileentity;

import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.network.NetworkManager;
import net.minecraft.init.Items;
import java.util.List;
import java.util.Collections;
import lotr.common.inventory.LOTRSlotStackSize;
import java.util.ArrayList;
import org.apache.commons.lang3.ArrayUtils;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.block.LOTRBlockForgeBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;

public abstract class LOTRTileEntityForgeBase extends TileEntity implements IInventory, ISidedInventory
{
    protected ItemStack[] inventory;
    private String specialForgeName;
    public int forgeSmeltTime;
    public int currentItemFuelValue;
    public int currentSmeltTime;
    public int[] inputSlots;
    public int[] outputSlots;
    public int fuelSlot;
    
    public LOTRTileEntityForgeBase() {
        this.forgeSmeltTime = 0;
        this.currentItemFuelValue = 0;
        this.currentSmeltTime = 0;
        this.inventory = new ItemStack[this.getForgeInvSize()];
        this.setupForgeSlots();
    }
    
    public abstract int getForgeInvSize();
    
    public abstract void setupForgeSlots();
    
    public abstract int getSmeltingDuration();
    
    protected boolean canMachineInsertInput(final ItemStack itemstack) {
        return true;
    }
    
    protected boolean canMachineInsertFuel(final ItemStack itemstack) {
        return TileEntityFurnace.func_145954_b(itemstack);
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
        return this.isInventoryNameLocalized() ? this.specialForgeName : this.getForgeName();
    }
    
    public abstract String getForgeName();
    
    public boolean isInventoryNameLocalized() {
        return this.specialForgeName != null && this.specialForgeName.length() > 0;
    }
    
    public void setSpecialForgeName(final String s) {
        this.specialForgeName = s;
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
        this.forgeSmeltTime = nbt.getShort("BurnTime");
        this.currentSmeltTime = nbt.getShort("SmeltTime");
        this.currentItemFuelValue = TileEntityFurnace.func_145952_a(this.inventory[this.fuelSlot]);
        if (nbt.hasKey("CustomName")) {
            this.specialForgeName = nbt.getString("CustomName");
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
        nbt.setShort("BurnTime", (short)this.forgeSmeltTime);
        nbt.setShort("SmeltTime", (short)this.currentSmeltTime);
        if (this.isInventoryNameLocalized()) {
            nbt.setString("CustomName", this.specialForgeName);
        }
    }
    
    public int getInventoryStackLimit() {
        return 64;
    }
    
    @SideOnly(Side.CLIENT)
    public int getSmeltProgressScaled(final int i) {
        return this.currentSmeltTime * i / this.getSmeltingDuration();
    }
    
    @SideOnly(Side.CLIENT)
    public int getSmeltTimeRemainingScaled(final int i) {
        if (this.currentItemFuelValue == 0) {
            this.currentItemFuelValue = this.getSmeltingDuration();
        }
        return this.forgeSmeltTime * i / this.currentItemFuelValue;
    }
    
    public boolean isSmelting() {
        return this.forgeSmeltTime > 0;
    }
    
    protected void toggleForgeActive() {
        LOTRBlockForgeBase.toggleForgeActive(super.worldObj, super.xCoord, super.yCoord, super.zCoord);
    }
    
    protected abstract boolean canDoSmelting();
    
    protected abstract void doSmelt();
    
    public void updateEntity() {
        final boolean smelting = this.forgeSmeltTime > 0;
        boolean needUpdate = false;
        if (this.forgeSmeltTime > 0) {
            --this.forgeSmeltTime;
        }
        if (!super.worldObj.isClient) {
            if (this.forgeSmeltTime == 0 && this.canDoSmelting()) {
                final int func_145952_a = TileEntityFurnace.func_145952_a(this.inventory[this.fuelSlot]);
                this.forgeSmeltTime = func_145952_a;
                this.currentItemFuelValue = func_145952_a;
                if (this.forgeSmeltTime > 0) {
                    needUpdate = true;
                    if (this.inventory[this.fuelSlot] != null) {
                        final ItemStack itemStack = this.inventory[this.fuelSlot];
                        --itemStack.stackSize;
                        if (this.inventory[this.fuelSlot].stackSize == 0) {
                            this.inventory[this.fuelSlot] = this.inventory[this.fuelSlot].getItem().getContainerItem(this.inventory[this.fuelSlot]);
                        }
                    }
                }
            }
            if (this.isSmelting() && this.canDoSmelting()) {
                ++this.currentSmeltTime;
                if (this.currentSmeltTime == this.getSmeltingDuration()) {
                    this.currentSmeltTime = 0;
                    this.doSmelt();
                    needUpdate = true;
                }
            }
            else {
                this.currentSmeltTime = 0;
            }
            if (smelting != this.forgeSmeltTime > 0) {
                needUpdate = true;
                this.toggleForgeActive();
            }
        }
        if (needUpdate) {
            this.onInventoryChanged();
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
        if (ArrayUtils.contains(this.inputSlots, slot)) {
            return this.canMachineInsertInput(itemstack);
        }
        return slot == this.fuelSlot && this.canMachineInsertFuel(itemstack);
    }
    
    public int[] getSlotsForFace(final int side) {
        if (side == 0) {
            final List<Integer> list = new ArrayList<Integer>();
            for (final int i : this.outputSlots) {
                list.add(i);
            }
            list.add(this.fuelSlot);
            final int[] temp = new int[list.size()];
            for (int j = 0; j < temp.length; ++j) {
                temp[j] = list.get(j);
            }
            return temp;
        }
        if (side == 1) {
            final List<LOTRSlotStackSize> list2 = new ArrayList<LOTRSlotStackSize>();
            for (int k = 0; k < this.inputSlots.length; ++k) {
                final int slot = this.inputSlots[k];
                final int size = (this.getStackInSlot(slot) == null) ? 0 : this.getStackInSlot(slot).stackSize;
                list2.add(new LOTRSlotStackSize(slot, size));
            }
            Collections.sort(list2);
            final int[] temp = new int[this.inputSlots.length];
            for (int j = 0; j < temp.length; ++j) {
                final LOTRSlotStackSize obj = list2.get(j);
                temp[j] = obj.slot;
            }
            return temp;
        }
        return new int[] { this.fuelSlot };
    }
    
    public boolean canInsertItem(final int slot, final ItemStack itemstack, final int side) {
        return this.isItemValidForSlot(slot, itemstack);
    }
    
    public boolean canExtractItem(final int slot, final ItemStack itemstack, final int side) {
        return side != 0 || slot != this.fuelSlot || itemstack.getItem() == Items.bucket;
    }
    
    public void onDataPacket(final NetworkManager networkManager, final S35PacketUpdateTileEntity packet) {
        if (packet.func_148857_g() != null && packet.func_148857_g().hasKey("CustomName")) {
            this.specialForgeName = packet.func_148857_g().getString("CustomName");
        }
    }
}
