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
import net.minecraft.item.Item;
import lotr.common.LOTRMod;
import net.minecraft.item.ItemFood;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.crafting.FurnaceRecipes;
import lotr.common.block.LOTRBlockHobbitOven;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;

public class LOTRTileEntityHobbitOven extends TileEntity implements IInventory, ISidedInventory
{
    private ItemStack[] inventory;
    public int ovenCookTime;
    public int currentItemFuelValue;
    public int currentCookTime;
    private String specialOvenName;
    private int[] inputSlots;
    private int[] outputSlots;
    private int fuelSlot;
    
    public LOTRTileEntityHobbitOven() {
        this.inventory = new ItemStack[19];
        this.ovenCookTime = 0;
        this.currentItemFuelValue = 0;
        this.currentCookTime = 0;
        this.inputSlots = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
        this.outputSlots = new int[] { 9, 10, 11, 12, 13, 14, 15, 16, 17 };
        this.fuelSlot = 18;
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
        return this.isInventoryNameLocalized() ? this.specialOvenName : StatCollector.translateToLocal("container.lotr.hobbitOven");
    }
    
    public boolean isInventoryNameLocalized() {
        return this.specialOvenName != null && this.specialOvenName.length() > 0;
    }
    
    public void setOvenName(final String s) {
        this.specialOvenName = s;
    }
    
    public void readFromNBT(final NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        final NBTTagList items = nbt.getTagList("Items", 10);
        this.inventory = new ItemStack[this.getSizeInventory()];
        for (int i = 0; i < items.tagCount(); ++i) {
            final NBTTagCompound itemData = items.getCompoundTagAt(i);
            final byte byte0 = itemData.getByte("Slot");
            if (byte0 >= 0 && byte0 < this.inventory.length) {
                this.inventory[byte0] = ItemStack.loadItemStackFromNBT(itemData);
            }
        }
        this.ovenCookTime = nbt.getShort("BurnTime");
        this.currentCookTime = nbt.getShort("CookTime");
        this.currentItemFuelValue = TileEntityFurnace.func_145952_a(this.inventory[18]);
        if (nbt.hasKey("CustomName")) {
            this.specialOvenName = nbt.getString("CustomName");
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
        nbt.setShort("BurnTime", (short)this.ovenCookTime);
        nbt.setShort("CookTime", (short)this.currentCookTime);
        if (this.isInventoryNameLocalized()) {
            nbt.setString("CustomName", this.specialOvenName);
        }
    }
    
    public int getInventoryStackLimit() {
        return 64;
    }
    
    @SideOnly(Side.CLIENT)
    public int getCookProgressScaled(final int i) {
        return this.currentCookTime * i / 400;
    }
    
    @SideOnly(Side.CLIENT)
    public int getCookTimeRemainingScaled(final int i) {
        if (this.currentItemFuelValue == 0) {
            this.currentItemFuelValue = 400;
        }
        return this.ovenCookTime * i / this.currentItemFuelValue;
    }
    
    public boolean isCooking() {
        return this.ovenCookTime > 0;
    }
    
    public void updateEntity() {
        final boolean cooking = this.ovenCookTime > 0;
        boolean needUpdate = false;
        if (this.ovenCookTime > 0) {
            --this.ovenCookTime;
        }
        if (!super.worldObj.isClient) {
            if (this.ovenCookTime == 0 && this.canCookAnyItem()) {
                final int func_145952_a = TileEntityFurnace.func_145952_a(this.inventory[18]);
                this.ovenCookTime = func_145952_a;
                this.currentItemFuelValue = func_145952_a;
                if (this.ovenCookTime > 0) {
                    needUpdate = true;
                    if (this.inventory[18] != null) {
                        final ItemStack itemStack = this.inventory[18];
                        --itemStack.stackSize;
                        if (this.inventory[18].stackSize == 0) {
                            this.inventory[18] = this.inventory[18].getItem().getContainerItem(this.inventory[18]);
                        }
                    }
                }
            }
            if (this.isCooking() && this.canCookAnyItem()) {
                ++this.currentCookTime;
                if (this.currentCookTime == 400) {
                    this.currentCookTime = 0;
                    for (int i = 0; i < 9; ++i) {
                        this.cookItem(i);
                    }
                    needUpdate = true;
                }
            }
            else {
                this.currentCookTime = 0;
            }
            if (cooking != this.ovenCookTime > 0) {
                needUpdate = true;
                LOTRBlockHobbitOven.setOvenActive(super.worldObj, super.xCoord, super.yCoord, super.zCoord);
            }
        }
        if (needUpdate) {
            this.onInventoryChanged();
        }
    }
    
    private boolean canCookAnyItem() {
        for (int i = 0; i < 9; ++i) {
            if (this.canCook(i)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean canCook(final int i) {
        if (this.inventory[i] == null) {
            return false;
        }
        final ItemStack result = FurnaceRecipes.smelting().func_151395_a(this.inventory[i]);
        if (!isCookResultAcceptable(result)) {
            return false;
        }
        if (this.inventory[i + 9] == null) {
            return true;
        }
        if (!this.inventory[i + 9].isItemEqual(result)) {
            return false;
        }
        final int resultSize = this.inventory[i + 9].stackSize + result.stackSize;
        return resultSize <= this.getInventoryStackLimit() && resultSize <= result.getMaxStackSize();
    }
    
    private void cookItem(final int i) {
        if (this.canCook(i)) {
            final ItemStack itemstack = FurnaceRecipes.smelting().func_151395_a(this.inventory[i]);
            if (this.inventory[i + 9] == null) {
                this.inventory[i + 9] = itemstack.copy();
            }
            else if (this.inventory[i + 9].isItemEqual(itemstack)) {
                final ItemStack itemStack = this.inventory[i + 9];
                itemStack.stackSize += itemstack.stackSize;
            }
            final ItemStack itemStack2 = this.inventory[i];
            --itemStack2.stackSize;
            if (this.inventory[i].stackSize <= 0) {
                this.inventory[i] = null;
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
    
    public static boolean isCookResultAcceptable(final ItemStack result) {
        if (result == null) {
            return false;
        }
        final Item item = result.getItem();
        return item instanceof ItemFood || item == LOTRMod.pipeweed || item == Item.getItemFromBlock(LOTRMod.driedReeds);
    }
    
    public boolean isItemValidForSlot(final int slot, final ItemStack itemstack) {
        if (slot < 9) {
            return itemstack != null && isCookResultAcceptable(FurnaceRecipes.smelting().func_151395_a(itemstack));
        }
        return slot >= 18 && TileEntityFurnace.func_145954_b(itemstack);
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
            final List list2 = new ArrayList();
            for (int k = 0; k < this.inputSlots.length; ++k) {
                final int slot = this.inputSlots[k];
                final int size = (this.getStackInSlot(slot) == null) ? 0 : this.getStackInSlot(slot).stackSize;
                list2.add(new LOTRSlotStackSize(slot, size));
            }
            Collections.sort((List<Comparable>)list2);
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
            this.specialOvenName = packet.func_148857_g().getString("CustomName");
        }
    }
}
