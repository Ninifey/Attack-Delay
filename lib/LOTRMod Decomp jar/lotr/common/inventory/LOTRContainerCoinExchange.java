// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.inventory;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.InventoryBasic;
import lotr.common.item.LOTRItemCoin;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.inventory.ICrafting;
import lotr.common.LOTRMod;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.Slot;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.world.World;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Container;

public class LOTRContainerCoinExchange extends Container
{
    public IInventory coinInputInv;
    public IInventory exchangeInv;
    private World theWorld;
    public LOTREntityNPC theTraderNPC;
    public boolean exchanged;
    
    public LOTRContainerCoinExchange(final EntityPlayer entityplayer, final LOTREntityNPC npc) {
        this.coinInputInv = (IInventory)new InventoryCoinExchangeSlot(1);
        this.exchangeInv = (IInventory)new InventoryCoinExchangeSlot(2);
        this.exchanged = false;
        this.theWorld = ((Entity)entityplayer).worldObj;
        this.theTraderNPC = npc;
        this.addSlotToContainer((Slot)new Slot(this.coinInputInv, 0, 80, 46) {
            public boolean isItemValid(final ItemStack itemstack) {
                return super.isItemValid(itemstack) && itemstack != null && itemstack.getItem() == LOTRMod.silverCoin;
            }
        });
        this.addSlotToContainer((Slot)new SlotCoinResult(this.exchangeInv, 0, 26, 46));
        this.addSlotToContainer((Slot)new SlotCoinResult(this.exchangeInv, 1, 134, 46));
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot((IInventory)entityplayer.inventory, j + i * 9 + 9, 8 + j * 18, 106 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot((IInventory)entityplayer.inventory, i, 8 + i * 18, 164));
        }
        this.onCraftMatrixChanged(this.coinInputInv);
    }
    
    public boolean canInteractWith(final EntityPlayer entityplayer) {
        return true;
    }
    
    public void handleExchangePacket(final int slot) {
        if (!this.exchanged && this.coinInputInv.getStackInSlot(0) != null && slot >= 0 && slot < this.exchangeInv.getSizeInventory() && this.exchangeInv.getStackInSlot(slot) != null) {
            this.exchanged = true;
            final int coins = this.exchangeInv.getStackInSlot(slot).stackSize;
            int coinsTaken = 0;
            if (slot == 0) {
                coinsTaken = coins / 10;
            }
            else if (slot == 1) {
                coinsTaken = coins * 10;
            }
            this.coinInputInv.decrStackSize(0, coinsTaken);
            for (int i = 0; i < this.exchangeInv.getSizeInventory(); ++i) {
                if (i != slot) {
                    this.exchangeInv.setInventorySlotContents(i, (ItemStack)null);
                }
            }
            this.detectAndSendChanges();
            this.theTraderNPC.playTradeSound();
        }
    }
    
    public void onCraftGuiOpened(final ICrafting crafting) {
        this.sendClientExchangedData(crafting);
        super.onCraftGuiOpened(crafting);
    }
    
    public void detectAndSendChanges() {
        for (int i = 0; i < super.crafters.size(); ++i) {
            final ICrafting crafting = super.crafters.get(i);
            this.sendClientExchangedData(crafting);
        }
        super.detectAndSendChanges();
    }
    
    private void sendClientExchangedData(final ICrafting crafting) {
        crafting.sendProgressBarUpdate((Container)this, 0, (int)(this.exchanged ? 1 : 0));
    }
    
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(final int i, final int j) {
        if (i == 0) {
            this.exchanged = (j == 1);
        }
    }
    
    public void onCraftMatrixChanged(final IInventory inv) {
        if (inv == this.coinInputInv) {
            if (!this.exchanged) {
                final ItemStack coin = this.coinInputInv.getStackInSlot(0);
                if (coin != null && coin.getItem() == LOTRMod.silverCoin && coin.stackSize > 0) {
                    final int coins = coin.stackSize;
                    final int coinType = coin.getItemDamage();
                    if (coinType > 0) {
                        int coinsFloor;
                        for (coinsFloor = coins; coinsFloor * 10 > this.exchangeInv.getInventoryStackLimit(); --coinsFloor) {}
                        this.exchangeInv.setInventorySlotContents(0, new ItemStack(LOTRMod.silverCoin, coinsFloor * 10, coinType - 1));
                    }
                    else {
                        this.exchangeInv.setInventorySlotContents(0, (ItemStack)null);
                    }
                    if (coinType < LOTRItemCoin.values.length - 1 && coins >= 10) {
                        this.exchangeInv.setInventorySlotContents(1, new ItemStack(LOTRMod.silverCoin, coins / 10, coinType + 1));
                    }
                    else {
                        this.exchangeInv.setInventorySlotContents(1, (ItemStack)null);
                    }
                }
                else {
                    this.exchangeInv.setInventorySlotContents(0, (ItemStack)null);
                    this.exchangeInv.setInventorySlotContents(1, (ItemStack)null);
                }
            }
        }
        else if (inv == this.exchangeInv && this.exchanged) {
            boolean anyItems = false;
            for (int i = 0; i < this.exchangeInv.getSizeInventory(); ++i) {
                if (this.exchangeInv.getStackInSlot(i) != null) {
                    anyItems = true;
                }
            }
            if (!anyItems) {
                this.exchanged = false;
                this.onCraftMatrixChanged(this.coinInputInv);
            }
        }
        super.onCraftMatrixChanged(inv);
    }
    
    public void onContainerClosed(final EntityPlayer entityplayer) {
        super.onContainerClosed(entityplayer);
        if (!((Entity)entityplayer).worldObj.isClient) {
            for (int i = 0; i < this.coinInputInv.getSizeInventory(); ++i) {
                final ItemStack itemstack = this.coinInputInv.getStackInSlotOnClosing(i);
                if (itemstack != null) {
                    entityplayer.dropPlayerItemWithRandomChoice(itemstack, false);
                }
            }
            if (this.exchanged) {
                for (int i = 0; i < this.exchangeInv.getSizeInventory(); ++i) {
                    final ItemStack itemstack = this.exchangeInv.getStackInSlotOnClosing(i);
                    if (itemstack != null) {
                        entityplayer.dropPlayerItemWithRandomChoice(itemstack, false);
                    }
                }
            }
        }
    }
    
    public ItemStack transferStackInSlot(final EntityPlayer entityplayer, final int i) {
        ItemStack itemstack = null;
        final Slot slot = super.inventorySlots.get(i);
        if (slot != null && slot.getHasStack()) {
            final ItemStack itemstack2 = slot.getStack();
            itemstack = itemstack2.copy();
            if (i < 3) {
                if (!this.mergeItemStack(itemstack2, 3, 39, true)) {
                    return null;
                }
                this.onCraftMatrixChanged(slot.inventory);
            }
            else {
                boolean flag = false;
                final Slot coinSlot = super.inventorySlots.get(0);
                final ItemStack coinStack = coinSlot.getStack();
                if (coinSlot.isItemValid(itemstack2) && this.mergeItemStack(itemstack2, 0, 1, true)) {
                    flag = true;
                }
                if (!flag) {
                    if (i >= 3 && i < 30) {
                        if (!this.mergeItemStack(itemstack2, 30, 39, false)) {
                            return null;
                        }
                    }
                    else if (!this.mergeItemStack(itemstack2, 3, 30, false)) {
                        return null;
                    }
                }
            }
            if (itemstack2.stackSize == 0) {
                slot.putStack((ItemStack)null);
                this.detectAndSendChanges();
            }
            else {
                slot.onSlotChanged();
            }
            if (itemstack2.stackSize == itemstack.stackSize) {
                return null;
            }
            slot.onPickupFromSlot(entityplayer, itemstack2);
        }
        return itemstack;
    }
    
    protected void retrySlotClick(final int i, final int j, final boolean flag, final EntityPlayer entityplayer) {
    }
    
    public class InventoryCoinExchangeSlot extends InventoryBasic
    {
        public InventoryCoinExchangeSlot(final int i) {
            super("coinExchange", true, i);
        }
        
        public void onInventoryChanged() {
            super.onInventoryChanged();
            LOTRContainerCoinExchange.this.onCraftMatrixChanged((IInventory)this);
        }
    }
}
