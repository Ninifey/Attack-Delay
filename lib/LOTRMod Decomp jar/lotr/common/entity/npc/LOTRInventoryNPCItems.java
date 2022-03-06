// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import lotr.common.inventory.LOTRInventoryNPC;

public class LOTRInventoryNPCItems extends LOTRInventoryNPC
{
    private static int IDLE_ITEM;
    private static int WEAPON_MELEE;
    private static int WEAPON_RANGED;
    private static int SPEAR_BACKUP;
    private static int EATING_BACKUP;
    private static int IDLE_ITEM_MOUNTED;
    private static int WEAPON_MELEE_MOUNTED;
    private static int REPLACED_IDLE;
    private static int REPLACED_MELEE_MOUNTED;
    private static int REPLACED_IDLE_MOUNTED;
    private static int BOMBING_ITEM;
    private static int BOMB;
    private boolean isEating;
    
    public LOTRInventoryNPCItems(final LOTREntityNPC npc) {
        super("NPCItemsInv", npc, 12);
        this.isEating = false;
    }
    
    public void setIsEating(final boolean flag) {
        this.isEating = flag;
        super.theNPC.sendIsEatingToWatchers();
    }
    
    public boolean getIsEating() {
        return this.isEating;
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setBoolean("NPCEating", this.isEating);
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.isEating = nbt.getBoolean("NPCEating");
        if (this.isEating) {
            super.theNPC.setCurrentItemOrArmor(0, this.getEatingBackup());
            this.setEatingBackup(null);
            this.setIsEating(false);
        }
    }
    
    public ItemStack getIdleItem() {
        final ItemStack item = this.getStackInSlot(LOTRInventoryNPCItems.IDLE_ITEM);
        return (item == null) ? null : item.copy();
    }
    
    public void setIdleItem(final ItemStack item) {
        this.setInventorySlotContents(LOTRInventoryNPCItems.IDLE_ITEM, item);
    }
    
    public ItemStack getMeleeWeapon() {
        final ItemStack item = this.getStackInSlot(LOTRInventoryNPCItems.WEAPON_MELEE);
        return (item == null) ? null : item.copy();
    }
    
    public void setMeleeWeapon(final ItemStack item) {
        this.setInventorySlotContents(LOTRInventoryNPCItems.WEAPON_MELEE, item);
    }
    
    public ItemStack getRangedWeapon() {
        final ItemStack item = this.getStackInSlot(LOTRInventoryNPCItems.WEAPON_RANGED);
        return (item == null) ? null : item.copy();
    }
    
    public void setRangedWeapon(final ItemStack item) {
        this.setInventorySlotContents(LOTRInventoryNPCItems.WEAPON_RANGED, item);
    }
    
    public ItemStack getSpearBackup() {
        final ItemStack item = this.getStackInSlot(LOTRInventoryNPCItems.SPEAR_BACKUP);
        return (item == null) ? null : item.copy();
    }
    
    public void setSpearBackup(final ItemStack item) {
        this.setInventorySlotContents(LOTRInventoryNPCItems.SPEAR_BACKUP, item);
    }
    
    public ItemStack getEatingBackup() {
        final ItemStack item = this.getStackInSlot(LOTRInventoryNPCItems.EATING_BACKUP);
        return (item == null) ? null : item.copy();
    }
    
    public void setEatingBackup(final ItemStack item) {
        this.setInventorySlotContents(LOTRInventoryNPCItems.EATING_BACKUP, item);
    }
    
    public ItemStack getIdleItemMounted() {
        final ItemStack item = this.getStackInSlot(LOTRInventoryNPCItems.IDLE_ITEM_MOUNTED);
        return (item == null) ? null : item.copy();
    }
    
    public void setIdleItemMounted(final ItemStack item) {
        this.setInventorySlotContents(LOTRInventoryNPCItems.IDLE_ITEM_MOUNTED, item);
    }
    
    public ItemStack getMeleeWeaponMounted() {
        final ItemStack item = this.getStackInSlot(LOTRInventoryNPCItems.WEAPON_MELEE_MOUNTED);
        return (item == null) ? null : item.copy();
    }
    
    public void setMeleeWeaponMounted(final ItemStack item) {
        this.setInventorySlotContents(LOTRInventoryNPCItems.WEAPON_MELEE_MOUNTED, item);
    }
    
    public ItemStack getReplacedIdleItem() {
        final ItemStack item = this.getStackInSlot(LOTRInventoryNPCItems.REPLACED_IDLE);
        return (item == null) ? null : item.copy();
    }
    
    public void setReplacedIdleItem(final ItemStack item) {
        this.setInventorySlotContents(LOTRInventoryNPCItems.REPLACED_IDLE, item);
    }
    
    public ItemStack getReplacedMeleeWeaponMounted() {
        final ItemStack item = this.getStackInSlot(LOTRInventoryNPCItems.REPLACED_MELEE_MOUNTED);
        return (item == null) ? null : item.copy();
    }
    
    public void setReplacedMeleeWeaponMounted(final ItemStack item) {
        this.setInventorySlotContents(LOTRInventoryNPCItems.REPLACED_MELEE_MOUNTED, item);
    }
    
    public ItemStack getReplacedIdleItemMounted() {
        final ItemStack item = this.getStackInSlot(LOTRInventoryNPCItems.REPLACED_IDLE_MOUNTED);
        return (item == null) ? null : item.copy();
    }
    
    public void setReplacedIdleItemMounted(final ItemStack item) {
        this.setInventorySlotContents(LOTRInventoryNPCItems.REPLACED_IDLE_MOUNTED, item);
    }
    
    public ItemStack getBombingItem() {
        final ItemStack item = this.getStackInSlot(LOTRInventoryNPCItems.BOMBING_ITEM);
        return (item == null) ? null : item.copy();
    }
    
    public void setBombingItem(final ItemStack item) {
        this.setInventorySlotContents(LOTRInventoryNPCItems.BOMBING_ITEM, item);
    }
    
    public ItemStack getBomb() {
        final ItemStack item = this.getStackInSlot(LOTRInventoryNPCItems.BOMB);
        return (item == null) ? null : item.copy();
    }
    
    public void setBomb(final ItemStack item) {
        this.setInventorySlotContents(LOTRInventoryNPCItems.BOMB, item);
    }
    
    static {
        LOTRInventoryNPCItems.IDLE_ITEM = 0;
        LOTRInventoryNPCItems.WEAPON_MELEE = 1;
        LOTRInventoryNPCItems.WEAPON_RANGED = 2;
        LOTRInventoryNPCItems.SPEAR_BACKUP = 3;
        LOTRInventoryNPCItems.EATING_BACKUP = 4;
        LOTRInventoryNPCItems.IDLE_ITEM_MOUNTED = 5;
        LOTRInventoryNPCItems.WEAPON_MELEE_MOUNTED = 6;
        LOTRInventoryNPCItems.REPLACED_IDLE = 7;
        LOTRInventoryNPCItems.REPLACED_MELEE_MOUNTED = 8;
        LOTRInventoryNPCItems.REPLACED_IDLE_MOUNTED = 9;
        LOTRInventoryNPCItems.BOMBING_ITEM = 10;
        LOTRInventoryNPCItems.BOMB = 11;
    }
}
