// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.inventory;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagCompound;
import lotr.common.item.LOTRItemPouch;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryBasic;

public class LOTRInventoryPouch extends InventoryBasic
{
    private LOTRContainerPouch theContainer;
    private EntityPlayer thePlayer;
    private boolean isTemporary;
    private ItemStack tempPouchItem;
    
    public LOTRInventoryPouch(final EntityPlayer entityplayer, final LOTRContainerPouch container) {
        super(entityplayer.inventory.getCurrentItem().getDisplayName(), true, LOTRItemPouch.getCapacity(entityplayer.inventory.getCurrentItem()));
        this.isTemporary = false;
        this.thePlayer = entityplayer;
        this.theContainer = container;
        if (!((Entity)this.thePlayer).worldObj.isClient) {
            this.loadPouchContents();
        }
    }
    
    public LOTRInventoryPouch(final ItemStack itemstack) {
        super("tempPouch", true, LOTRItemPouch.getCapacity(itemstack));
        this.isTemporary = true;
        this.tempPouchItem = itemstack;
        this.loadPouchContents();
    }
    
    public ItemStack getPouchItem() {
        if (this.isTemporary) {
            return this.tempPouchItem;
        }
        return this.thePlayer.inventory.getCurrentItem();
    }
    
    public void onInventoryChanged() {
        super.onInventoryChanged();
        if (this.isTemporary || !((Entity)this.thePlayer).worldObj.isClient) {
            this.savePouchContents();
        }
    }
    
    private void loadPouchContents() {
        if (this.getPouchItem().hasTagCompound() && this.getPouchItem().getTagCompound().hasKey("LOTRPouchData")) {
            final NBTTagCompound nbt = this.getPouchItem().getTagCompound().getCompoundTag("LOTRPouchData");
            final NBTTagList items = nbt.getTagList("Items", 10);
            for (int i = 0; i < items.tagCount(); ++i) {
                final NBTTagCompound itemData = items.getCompoundTagAt(i);
                final int slot = itemData.getByte("Slot");
                if (slot >= 0 && slot < this.getSizeInventory()) {
                    this.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(itemData));
                }
            }
        }
        if (!this.isTemporary) {
            this.theContainer.syncPouchItem(this.getPouchItem());
        }
    }
    
    private void savePouchContents() {
        if (!this.getPouchItem().hasTagCompound()) {
            this.getPouchItem().setTagCompound(new NBTTagCompound());
        }
        final NBTTagCompound nbt = new NBTTagCompound();
        final NBTTagList items = new NBTTagList();
        for (int i = 0; i < this.getSizeInventory(); ++i) {
            final ItemStack itemstack = this.getStackInSlot(i);
            if (itemstack != null) {
                final NBTTagCompound itemData = new NBTTagCompound();
                itemData.setByte("Slot", (byte)i);
                itemstack.writeToNBT(itemData);
                items.appendTag((NBTBase)itemData);
            }
        }
        nbt.setTag("Items", (NBTBase)items);
        this.getPouchItem().getTagCompound().setTag("LOTRPouchData", (NBTBase)nbt);
        if (!this.isTemporary) {
            this.theContainer.syncPouchItem(this.getPouchItem());
        }
    }
}
