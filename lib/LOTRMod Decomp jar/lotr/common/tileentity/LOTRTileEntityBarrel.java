// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.tileentity;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.network.Packet;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagCompound;
import lotr.common.item.LOTRItemMug;
import net.minecraft.util.StatCollector;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.item.LOTRPoisonedDrinks;
import net.minecraft.entity.player.EntityPlayerMP;
import lotr.common.recipe.LOTRBrewingRecipes;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;

public class LOTRTileEntityBarrel extends TileEntity implements IInventory
{
    public static final int EMPTY = 0;
    public static final int BREWING = 1;
    public static final int FULL = 2;
    public static final int brewTime = 12000;
    public static final int brewAnimTime = 32;
    private ItemStack[] inventory;
    public int barrelMode;
    public int brewingTime;
    public int brewingAnim;
    public int brewingAnimPrev;
    private String specialBarrelName;
    public List players;
    public static final int BARREL_SLOT = 9;
    
    public LOTRTileEntityBarrel() {
        this.inventory = new ItemStack[10];
        this.players = new ArrayList();
    }
    
    public ItemStack getBrewedDrink() {
        if (this.barrelMode == 2 && this.inventory[9] != null) {
            final ItemStack itemstack = this.inventory[9].copy();
            return itemstack;
        }
        return null;
    }
    
    public void consumeMugRefill() {
        if (this.barrelMode == 2 && this.inventory[9] != null) {
            final ItemStack itemStack = this.inventory[9];
            --itemStack.stackSize;
            if (this.inventory[9].stackSize <= 0) {
                this.inventory[9] = null;
                this.barrelMode = 0;
            }
        }
    }
    
    private void updateBrewingRecipe() {
        if (this.barrelMode == 0) {
            this.inventory[9] = LOTRBrewingRecipes.findMatchingRecipe(this);
        }
    }
    
    public void handleBrewingButtonPress() {
        if (this.barrelMode == 0 && this.inventory[9] != null) {
            this.barrelMode = 1;
            for (int i = 0; i < 9; ++i) {
                if (this.inventory[i] != null) {
                    ItemStack containerItem = null;
                    if (this.inventory[i].getItem().hasContainerItem(this.inventory[i])) {
                        containerItem = this.inventory[i].getItem().getContainerItem(this.inventory[i]);
                        if (containerItem.isItemStackDamageable() && containerItem.getItemDamage() > containerItem.getMaxDamage()) {
                            containerItem = null;
                        }
                    }
                    final ItemStack itemStack = this.inventory[i];
                    --itemStack.stackSize;
                    if (this.inventory[i].stackSize <= 0) {
                        this.inventory[i] = null;
                        if (containerItem != null) {
                            this.inventory[i] = containerItem;
                        }
                    }
                }
            }
            if (!super.worldObj.isClient) {
                for (int i = 0; i < this.players.size(); ++i) {
                    final EntityPlayerMP entityplayer = this.players.get(i);
                    ((EntityPlayer)entityplayer).openContainer.detectAndSendChanges();
                    entityplayer.sendContainerToPlayer(((EntityPlayer)entityplayer).openContainer);
                }
            }
        }
        else if (this.barrelMode == 1 && this.inventory[9] != null && this.inventory[9].getItemDamage() > 0) {
            this.barrelMode = 2;
            this.brewingTime = 0;
            final ItemStack itemstack = this.inventory[9].copy();
            itemstack.setItemDamage(itemstack.getItemDamage() - 1);
            this.inventory[9] = itemstack;
        }
    }
    
    public boolean canPoisonBarrel() {
        if (this.barrelMode != 0 && this.inventory[9] != null) {
            final ItemStack itemstack = this.inventory[9];
            return LOTRPoisonedDrinks.canPoison(itemstack) && !LOTRPoisonedDrinks.isDrinkPoisoned(itemstack);
        }
        return false;
    }
    
    public void poisonBarrel(final EntityPlayer entityplayer) {
        final ItemStack itemstack = this.inventory[9];
        LOTRPoisonedDrinks.setDrinkPoisoned(itemstack, true);
        LOTRPoisonedDrinks.setPoisonerPlayer(itemstack, entityplayer);
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
            if (i != 9) {
                this.updateBrewingRecipe();
            }
            return itemstack;
        }
        final ItemStack itemstack = this.inventory[i].splitStack(j);
        if (this.inventory[i].stackSize == 0) {
            this.inventory[i] = null;
        }
        if (i != 9) {
            this.updateBrewingRecipe();
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
        if (i != 9) {
            this.updateBrewingRecipe();
        }
    }
    
    public String getInventoryName() {
        return this.isInventoryNameLocalized() ? this.specialBarrelName : StatCollector.translateToLocal("container.lotr.barrel");
    }
    
    public String getInvSubtitle() {
        final ItemStack brewingItem = this.getStackInSlot(9);
        if (this.barrelMode == 0) {
            return StatCollector.translateToLocal("container.lotr.barrel.empty");
        }
        if (this.barrelMode == 1 && brewingItem != null) {
            return StatCollector.translateToLocalFormatted("container.lotr.barrel.brewing", new Object[] { brewingItem.getDisplayName(), LOTRItemMug.getStrengthSubtitle(brewingItem) });
        }
        if (this.barrelMode == 2 && brewingItem != null) {
            return StatCollector.translateToLocalFormatted("container.lotr.barrel.full", new Object[] { brewingItem.getDisplayName(), LOTRItemMug.getStrengthSubtitle(brewingItem), brewingItem.stackSize });
        }
        return "";
    }
    
    public boolean isInventoryNameLocalized() {
        return this.specialBarrelName != null && this.specialBarrelName.length() > 0;
    }
    
    public void setBarrelName(final String s) {
        this.specialBarrelName = s;
    }
    
    public void readFromNBT(final NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.readBarrelFromNBT(nbt);
    }
    
    public void readBarrelFromNBT(final NBTTagCompound nbt) {
        final NBTTagList items = nbt.getTagList("Items", 10);
        this.inventory = new ItemStack[this.getSizeInventory()];
        for (int i = 0; i < items.tagCount(); ++i) {
            final NBTTagCompound itemData = items.getCompoundTagAt(i);
            final int slot = itemData.getByte("Slot");
            if (slot >= 0 && slot < this.inventory.length) {
                this.inventory[slot] = ItemStack.loadItemStackFromNBT(itemData);
            }
        }
        this.barrelMode = nbt.getByte("BarrelMode");
        this.brewingTime = nbt.getInteger("BrewingTime");
        if (nbt.hasKey("CustomName")) {
            this.specialBarrelName = nbt.getString("CustomName");
        }
    }
    
    public void writeToNBT(final NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        this.writeBarrelToNBT(nbt);
    }
    
    public void writeBarrelToNBT(final NBTTagCompound nbt) {
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
        nbt.setByte("BarrelMode", (byte)this.barrelMode);
        nbt.setInteger("BrewingTime", this.brewingTime);
        if (this.isInventoryNameLocalized()) {
            nbt.setString("CustomName", this.specialBarrelName);
        }
    }
    
    public int getInventoryStackLimit() {
        return 64;
    }
    
    public int getBrewProgressScaled(final int i) {
        return this.brewingTime * i / 12000;
    }
    
    public int getBrewAnimationProgressScaled(final int i) {
        return this.brewingAnim * i / 32;
    }
    
    public float getBrewAnimationProgressScaledF(final int i, final float f) {
        final float f2 = this.brewingAnimPrev * (float)i / 32.0f;
        final float f3 = this.brewingAnim * (float)i / 32.0f;
        return f2 + (f3 - f2) * f;
    }
    
    public int getBarrelFullAmountScaled(final int i) {
        return (this.inventory[9] == null) ? 0 : (this.inventory[9].stackSize * i / LOTRBrewingRecipes.BARREL_CAPACITY);
    }
    
    public void updateEntity() {
        boolean needUpdate = false;
        if (!super.worldObj.isClient) {
            if (this.barrelMode == 1) {
                if (this.inventory[9] != null) {
                    ++this.brewingTime;
                    if (this.brewingTime >= 12000) {
                        this.brewingTime = 0;
                        if (this.inventory[9].getItemDamage() < 4) {
                            this.inventory[9].setItemDamage(this.inventory[9].getItemDamage() + 1);
                            needUpdate = true;
                        }
                        else {
                            this.barrelMode = 2;
                        }
                    }
                }
                else {
                    this.barrelMode = 0;
                }
            }
            else {
                this.brewingTime = 0;
            }
            if (this.barrelMode == 2 && this.inventory[9] == null) {
                this.barrelMode = 0;
            }
        }
        else {
            this.brewingAnimPrev = this.brewingAnim;
            if (this.barrelMode == 1) {
                ++this.brewingAnim;
                if (this.brewingAnim >= 32) {
                    this.brewingAnim = 0;
                    this.brewingAnimPrev = this.brewingAnim;
                }
            }
            else {
                this.brewingAnim = 0;
                this.brewingAnimPrev = this.brewingAnim;
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
        return false;
    }
    
    public Packet getDescriptionPacket() {
        final NBTTagCompound data = new NBTTagCompound();
        this.writeBarrelToNBT(data);
        return (Packet)new S35PacketUpdateTileEntity(super.xCoord, super.yCoord, super.zCoord, 0, data);
    }
    
    public void onDataPacket(final NetworkManager manager, final S35PacketUpdateTileEntity packet) {
        final NBTTagCompound data = packet.func_148857_g();
        this.readBarrelFromNBT(data);
    }
}
