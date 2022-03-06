// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import lotr.common.inventory.LOTRInventoryNPC;

public class LOTRInventoryHiredReplacedItems extends LOTRInventoryNPC
{
    private static final int REPLACED_SIZE = 7;
    private boolean[] hasReplacedEquipment;
    public static final int HELMET = 0;
    public static final int BODY = 1;
    public static final int LEGS = 2;
    public static final int BOOTS = 3;
    public static final int MELEE = 4;
    public static final int BOMB = 5;
    public static final int RANGED = 6;
    private boolean replacedMeleeWeapons;
    
    public LOTRInventoryHiredReplacedItems(final LOTREntityNPC npc) {
        super("HiredReplacedItems", npc, 7);
        this.hasReplacedEquipment = new boolean[7];
        this.replacedMeleeWeapons = false;
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        for (int i = 0; i < this.hasReplacedEquipment.length; ++i) {
            final boolean flag = this.hasReplacedEquipment[i];
            nbt.setBoolean("ReplacedFlag_" + i, flag);
        }
        nbt.setBoolean("ReplacedMelee", this.replacedMeleeWeapons);
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        for (int i = 0; i < this.hasReplacedEquipment.length; ++i) {
            final boolean flag = nbt.getBoolean("ReplacedFlag_" + i);
            this.hasReplacedEquipment[i] = flag;
        }
        this.replacedMeleeWeapons = nbt.getBoolean("ReplacedMelee");
    }
    
    private ItemStack getReplacedEquipment(final int i) {
        final ItemStack item = this.getStackInSlot(i);
        return (item == null) ? null : item.copy();
    }
    
    private void setReplacedEquipment(final int i, final ItemStack item, final boolean flag) {
        this.setInventorySlotContents(i, item);
        this.hasReplacedEquipment[i] = flag;
        if (!flag && i == 4) {
            if (this.replacedMeleeWeapons) {
                super.theNPC.npcItemsInv.setIdleItem(super.theNPC.npcItemsInv.getReplacedIdleItem());
                super.theNPC.npcItemsInv.setMeleeWeaponMounted(super.theNPC.npcItemsInv.getReplacedMeleeWeaponMounted());
                super.theNPC.npcItemsInv.setIdleItemMounted(super.theNPC.npcItemsInv.getReplacedIdleItemMounted());
                super.theNPC.npcItemsInv.setReplacedMeleeWeaponMounted(null);
                super.theNPC.npcItemsInv.setReplacedIdleItem(null);
                super.theNPC.npcItemsInv.setReplacedIdleItemMounted(null);
                this.replacedMeleeWeapons = false;
            }
            this.updateHeldItem();
        }
    }
    
    public boolean hasReplacedEquipment(final int i) {
        return this.hasReplacedEquipment[i];
    }
    
    private void equipReplacement(final int i, final ItemStack itemstack) {
        if (i == 4) {
            boolean idleMelee = false;
            if (ItemStack.areItemStacksEqual(super.theNPC.npcItemsInv.getMeleeWeapon(), super.theNPC.npcItemsInv.getIdleItem())) {
                idleMelee = true;
            }
            super.theNPC.npcItemsInv.setMeleeWeapon(itemstack);
            if (!this.replacedMeleeWeapons) {
                super.theNPC.npcItemsInv.setReplacedIdleItem(super.theNPC.npcItemsInv.getIdleItem());
                super.theNPC.npcItemsInv.setReplacedMeleeWeaponMounted(super.theNPC.npcItemsInv.getMeleeWeaponMounted());
                super.theNPC.npcItemsInv.setReplacedIdleItemMounted(super.theNPC.npcItemsInv.getIdleItemMounted());
                this.replacedMeleeWeapons = true;
            }
            super.theNPC.npcItemsInv.setMeleeWeaponMounted(itemstack);
            if (idleMelee) {
                super.theNPC.npcItemsInv.setIdleItem(itemstack);
                super.theNPC.npcItemsInv.setIdleItemMounted(itemstack);
            }
            this.updateHeldItem();
        }
        else if (i == 6) {
            super.theNPC.npcItemsInv.setRangedWeapon(itemstack);
            this.updateHeldItem();
        }
        else if (i == 5) {
            super.theNPC.npcItemsInv.setBomb(itemstack);
            this.updateHeldItem();
        }
        else {
            super.theNPC.setCurrentItemOrArmor(this.getNPCArmorSlot(i), itemstack);
        }
    }
    
    public ItemStack getEquippedReplacement(final int i) {
        if (i == 4) {
            return super.theNPC.npcItemsInv.getMeleeWeapon();
        }
        if (i == 6) {
            return super.theNPC.npcItemsInv.getRangedWeapon();
        }
        if (i == 5) {
            return super.theNPC.npcItemsInv.getBomb();
        }
        return super.theNPC.getEquipmentInSlot(this.getNPCArmorSlot(i));
    }
    
    private int getNPCArmorSlot(final int i) {
        return 4 - i;
    }
    
    public void onEquipmentChanged(final int i, final ItemStack newItem) {
        if (newItem == null) {
            if (this.hasReplacedEquipment(i)) {
                final ItemStack itemstack = this.getReplacedEquipment(i);
                this.equipReplacement(i, itemstack);
                this.setReplacedEquipment(i, null, false);
            }
        }
        else {
            if (!this.hasReplacedEquipment(i)) {
                final ItemStack itemstack = this.getEquippedReplacement(i);
                this.setReplacedEquipment(i, itemstack, true);
            }
            this.equipReplacement(i, newItem.copy());
        }
    }
    
    private void updateHeldItem() {
        if (!super.theNPC.npcItemsInv.getIsEating()) {
            super.theNPC.refreshCurrentAttackMode();
        }
    }
    
    public void dropAllReplacedItems() {
        for (int i = 0; i < 7; ++i) {
            if (this.hasReplacedEquipment(i)) {
                final ItemStack itemstack = this.getEquippedReplacement(i);
                if (itemstack != null) {
                    super.theNPC.npcDropItem(itemstack, 0.0f, false);
                    this.equipReplacement(i, this.getReplacedEquipment(i));
                    this.setReplacedEquipment(i, null, false);
                }
            }
        }
    }
}
