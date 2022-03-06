// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.tileentity;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.network.Packet;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import lotr.common.LOTRMod;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.ItemFood;
import net.minecraft.block.Block;
import lotr.common.block.LOTRBlockKebabStand;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;

public class LOTRTileEntityKebabStand extends TileEntity implements IInventory
{
    public static final int MEAT_SLOTS = 8;
    private ItemStack[] inventory;
    private boolean[] cooked;
    private int cookTime;
    private static final int cookTimeMax = 200;
    private int fuelTime;
    private boolean cookedClient;
    private boolean cookingClient;
    private int meatAmountClient;
    private float kebabSpin;
    private float prevKebabSpin;
    
    public LOTRTileEntityKebabStand() {
        this.inventory = new ItemStack[8];
        this.cooked = new boolean[8];
    }
    
    public String getStandTextureName() {
        final Block block = this.getBlockType();
        if (block instanceof LOTRBlockKebabStand) {
            return ((LOTRBlockKebabStand)block).getStandTextureName();
        }
        return "";
    }
    
    public float getKebabSpin(final float f) {
        return this.prevKebabSpin + (this.kebabSpin - this.prevKebabSpin) * f;
    }
    
    public boolean isCooked() {
        if (super.worldObj != null && super.worldObj.isClient) {
            return this.cookedClient;
        }
        for (int i = 0; i < this.getSizeInventory(); ++i) {
            if (this.cooked[i]) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isFullyCooked() {
        for (int i = 0; i < this.getSizeInventory(); ++i) {
            final ItemStack itemstack = this.getStackInSlot(i);
            if (itemstack != null && !this.cooked[i]) {
                return false;
            }
        }
        return true;
    }
    
    public boolean isCooking() {
        if (super.worldObj != null && super.worldObj.isClient) {
            return this.cookingClient;
        }
        return this.fuelTime > 0;
    }
    
    public int getMeatAmount() {
        if (super.worldObj != null && super.worldObj.isClient) {
            return this.meatAmountClient;
        }
        int meats = 0;
        for (int i = 0; i < this.getSizeInventory(); ++i) {
            final ItemStack itemstack = this.getStackInSlot(i);
            if (itemstack != null) {
                ++meats;
            }
        }
        return meats;
    }
    
    public boolean isMeat(final ItemStack meat) {
        if (meat == null) {
            return false;
        }
        final Item item = meat.getItem();
        if (item instanceof ItemFood) {
            final ItemFood itemfood = (ItemFood)item;
            if (itemfood.isWolfsFavoriteMeat()) {
                final ItemStack cookedFood = FurnaceRecipes.smelting().func_151395_a(meat);
                return cookedFood != null;
            }
        }
        return false;
    }
    
    public boolean hasEmptyMeatSlot() {
        for (int i = 0; i < this.getSizeInventory(); ++i) {
            final ItemStack itemstack = this.getStackInSlot(i);
            if (itemstack == null) {
                return true;
            }
        }
        return false;
    }
    
    public boolean addMeat(final ItemStack meat) {
        final ItemStack copyMeat = meat.copy();
        copyMeat.stackSize = 1;
        boolean added = false;
        for (int i = 0; i < this.getSizeInventory(); ++i) {
            final ItemStack itemstack = this.getStackInSlot(i);
            if (itemstack == null) {
                this.setInventorySlotContents(i, copyMeat);
                this.cooked[i] = false;
                added = true;
                break;
            }
        }
        super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
        this.onInventoryChanged();
        return added;
    }
    
    public ItemStack removeFirstMeat() {
        ItemStack meat = null;
        for (int i = this.getSizeInventory() - 1; i >= 0; --i) {
            final ItemStack itemstack = this.getStackInSlot(i);
            if (itemstack != null && this.cooked[i]) {
                meat = itemstack;
                this.setInventorySlotContents(i, null);
                this.cooked[i] = false;
                break;
            }
        }
        if (meat == null) {
            for (int i = this.getSizeInventory() - 1; i >= 0; --i) {
                final ItemStack itemstack = this.getStackInSlot(i);
                if (itemstack != null && !this.cooked[i]) {
                    meat = itemstack;
                    this.setInventorySlotContents(i, null);
                    break;
                }
            }
        }
        if (this.isCooking() && this.getMeatAmount() == 0) {
            this.stopCooking();
        }
        super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
        this.onInventoryChanged();
        return meat;
    }
    
    private boolean canCook() {
        return !this.isFullyCooked() && this.getMeatAmount() > 0;
    }
    
    private void startCooking(final int i) {
        this.cookTime = 0;
        this.fuelTime = i;
    }
    
    private void addFuel(final int i) {
        this.fuelTime += i;
    }
    
    private void stopCooking() {
        this.cookTime = 0;
        this.fuelTime = 0;
    }
    
    public void updateEntity() {
        super.updateEntity();
        if (!super.worldObj.isClient) {
            final boolean prevCooking = this.isCooking();
            final boolean prevCooked = this.isCooked();
            if (this.isCooking()) {
                if (!this.canCook()) {
                    this.stopCooking();
                }
                else {
                    ++this.cookTime;
                    if (this.cookTime > this.fuelTime) {
                        final int fuel = this.takeFuelFromBelow();
                        if (fuel > 0) {
                            this.addFuel(fuel);
                        }
                        else {
                            this.stopCooking();
                        }
                    }
                    else if (this.cookTime >= 200) {
                        this.cookFirstMeat();
                    }
                }
            }
            else if (this.canCook()) {
                final int fuel = this.takeFuelFromBelow();
                if (fuel > 0) {
                    this.startCooking(fuel);
                }
            }
            if (this.isCooking() != prevCooking || this.isCooked() != prevCooked) {
                super.worldObj.markBlockForUpdate(super.xCoord, super.yCoord, super.zCoord);
                this.onInventoryChanged();
            }
        }
        else if (this.isCooking()) {
            this.prevKebabSpin = this.kebabSpin;
            this.kebabSpin += 4.0f;
            if (super.worldObj.rand.nextInt(4) == 0) {
                final double d = super.xCoord + super.worldObj.rand.nextFloat();
                final double d2 = super.yCoord + super.worldObj.rand.nextFloat() * 0.2f;
                final double d3 = super.zCoord + super.worldObj.rand.nextFloat();
                super.worldObj.spawnParticle("smoke", d, d2, d3, 0.0, 0.0, 0.0);
                super.worldObj.spawnParticle("flame", d, d2, d3, 0.0, 0.0, 0.0);
            }
        }
        else if (this.kebabSpin > 0.0f) {
            this.prevKebabSpin = this.kebabSpin;
            this.kebabSpin += 20.0f;
            if ((float)Math.ceil(this.kebabSpin / 360.0f) > (float)Math.ceil(this.prevKebabSpin / 360.0f)) {
                final float ds = this.kebabSpin - this.prevKebabSpin;
                this.kebabSpin = 0.0f;
                this.prevKebabSpin = this.kebabSpin - ds;
            }
        }
        else {
            final float n = 0.0f;
            this.kebabSpin = n;
            this.prevKebabSpin = n;
        }
    }
    
    private void cookFirstMeat() {
        this.cookTime = 0;
        this.fuelTime -= 200;
        for (int i = this.getSizeInventory() - 1; i >= 0; --i) {
            final ItemStack itemstack = this.getStackInSlot(i);
            if (itemstack != null && !this.cooked[i]) {
                this.setInventorySlotContents(i, new ItemStack(LOTRMod.kebab));
                this.cooked[i] = true;
                break;
            }
        }
    }
    
    private int takeFuelFromBelow() {
        final TileEntity belowTE = super.worldObj.getTileEntity(super.xCoord, super.yCoord - 1, super.zCoord);
        if (belowTE instanceof IInventory) {
            final IInventory inv = (IInventory)belowTE;
            for (int i = 0; i < inv.getSizeInventory(); ++i) {
                final ItemStack itemstack = inv.getStackInSlot(i);
                if (itemstack != null && TileEntityFurnace.func_145954_b(itemstack)) {
                    final int fuel = TileEntityFurnace.func_145952_a(itemstack);
                    final ItemStack itemStack = itemstack;
                    --itemStack.stackSize;
                    if (itemstack.stackSize <= 0) {
                        inv.setInventorySlotContents(i, (ItemStack)null);
                    }
                    else {
                        inv.setInventorySlotContents(i, itemstack);
                    }
                    return fuel;
                }
            }
        }
        return 0;
    }
    
    public void generateCookedKebab(final int kebab) {
        for (int i = 0; i < kebab && i < this.getSizeInventory(); ++i) {
            this.setInventorySlotContents(i, new ItemStack(LOTRMod.kebab));
            this.cooked[i] = true;
        }
    }
    
    public boolean shouldSaveBlockData() {
        return this.getMeatAmount() > 0;
    }
    
    public void onReplaced() {
        this.stopCooking();
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
        return "KebabStand";
    }
    
    public boolean isInventoryNameLocalized() {
        return false;
    }
    
    public void writeToNBT(final NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        this.writeKebabStandToNBT(nbt);
    }
    
    public void writeKebabStandToNBT(final NBTTagCompound nbt) {
        final NBTTagList items = new NBTTagList();
        for (int i = 0; i < this.inventory.length; ++i) {
            final NBTTagCompound itemData = new NBTTagCompound();
            itemData.setByte("Slot", (byte)i);
            final ItemStack slotItem = this.inventory[i];
            final boolean slotCooked = this.cooked[i];
            itemData.setBoolean("SlotItem", slotItem != null);
            if (slotItem != null) {
                slotItem.writeToNBT(itemData);
            }
            itemData.setBoolean("SlotCooked", slotCooked);
            items.appendTag((NBTBase)itemData);
        }
        nbt.setTag("Items", (NBTBase)items);
        nbt.setShort("CookTime", (short)this.cookTime);
        nbt.setShort("FuelTime", (short)this.fuelTime);
    }
    
    public void readFromNBT(final NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.readKebabStandFromNBT(nbt);
    }
    
    public void readKebabStandFromNBT(final NBTTagCompound nbt) {
        final NBTTagList items = nbt.getTagList("Items", 10);
        this.inventory = new ItemStack[this.getSizeInventory()];
        this.cooked = new boolean[this.inventory.length];
        for (int i = 0; i < items.tagCount(); ++i) {
            final NBTTagCompound itemData = items.getCompoundTagAt(i);
            final int slot = itemData.getByte("Slot");
            if (slot >= 0 && slot < this.inventory.length) {
                final boolean slotItem = itemData.getBoolean("SlotItem");
                if (slotItem) {
                    this.inventory[slot] = ItemStack.loadItemStackFromNBT(itemData);
                }
                final boolean slotCooked = itemData.getBoolean("SlotCooked");
                this.cooked[i] = slotCooked;
            }
        }
        this.cookTime = nbt.getShort("CookTime");
        this.fuelTime = nbt.getShort("FuelTime");
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
    
    public Packet getDescriptionPacket() {
        final NBTTagCompound data = new NBTTagCompound();
        data.setBoolean("Cooked", this.isCooked());
        data.setBoolean("Cooking", this.isCooking());
        data.setByte("Meats", (byte)this.getMeatAmount());
        return (Packet)new S35PacketUpdateTileEntity(super.xCoord, super.yCoord, super.zCoord, 0, data);
    }
    
    public void onDataPacket(final NetworkManager manager, final S35PacketUpdateTileEntity packet) {
        final NBTTagCompound data = packet.func_148857_g();
        this.cookedClient = data.getBoolean("Cooked");
        this.cookingClient = data.getBoolean("Cooking");
        this.meatAmountClient = data.getByte("Meats");
    }
}
