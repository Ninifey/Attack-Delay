// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.quest;

import net.minecraft.util.MathHelper;
import java.util.Random;
import lotr.common.item.LOTRItemMug;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import lotr.common.LOTRPlayerData;
import net.minecraft.item.ItemStack;

public class LOTRMiniQuestCollect extends LOTRMiniQuest
{
    public ItemStack collectItem;
    public int collectTarget;
    public int amountGiven;
    
    public LOTRMiniQuestCollect(final LOTRPlayerData pd) {
        super(pd);
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        if (this.collectItem != null) {
            final NBTTagCompound itemData = new NBTTagCompound();
            this.collectItem.writeToNBT(itemData);
            nbt.setTag("Item", (NBTBase)itemData);
        }
        nbt.setInteger("Target", this.collectTarget);
        nbt.setInteger("Given", this.amountGiven);
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        if (nbt.hasKey("Item")) {
            final NBTTagCompound itemData = nbt.getCompoundTag("Item");
            this.collectItem = ItemStack.loadItemStackFromNBT(itemData);
        }
        this.collectTarget = nbt.getInteger("Target");
        this.amountGiven = nbt.getInteger("Given");
    }
    
    @Override
    public boolean isValidQuest() {
        return super.isValidQuest() && this.collectItem != null && this.collectTarget > 0;
    }
    
    @Override
    public String getQuestObjective() {
        return StatCollector.translateToLocalFormatted("lotr.miniquest.collect", new Object[] { this.collectTarget, this.collectItem.getDisplayName() });
    }
    
    @Override
    public String getObjectiveInSpeech() {
        return this.collectTarget + " " + this.collectItem.getDisplayName();
    }
    
    @Override
    public String getProgressedObjectiveInSpeech() {
        return this.collectTarget - this.amountGiven + " " + this.collectItem.getDisplayName();
    }
    
    @Override
    public String getQuestProgress() {
        return StatCollector.translateToLocalFormatted("lotr.miniquest.collect.progress", new Object[] { this.amountGiven, this.collectTarget });
    }
    
    @Override
    public String getQuestProgressShorthand() {
        return StatCollector.translateToLocalFormatted("lotr.miniquest.progressShort", new Object[] { this.amountGiven, this.collectTarget });
    }
    
    @Override
    public float getCompletionFactor() {
        return this.amountGiven / (float)this.collectTarget;
    }
    
    @Override
    public ItemStack getQuestIcon() {
        return this.collectItem;
    }
    
    @Override
    public void onInteract(final EntityPlayer entityplayer, final LOTREntityNPC npc) {
        final int prevAmountGiven = this.amountGiven;
        final List<Integer> slotNumbers = new ArrayList<Integer>();
        slotNumbers.add(entityplayer.inventory.currentItem);
        for (int slot = 0; slot < entityplayer.inventory.mainInventory.length; ++slot) {
            if (!slotNumbers.contains(slot)) {
                slotNumbers.add(slot);
            }
        }
        for (final int slot2 : slotNumbers) {
            ItemStack itemstack = entityplayer.inventory.mainInventory[slot2];
            if (this.isQuestItem(itemstack)) {
                final int amountRemaining = this.collectTarget - this.amountGiven;
                if (itemstack.stackSize >= amountRemaining) {
                    final ItemStack itemStack = itemstack;
                    itemStack.stackSize -= amountRemaining;
                    if (itemstack.stackSize <= 0) {
                        itemstack = null;
                    }
                    entityplayer.inventory.setInventorySlotContents(slot2, itemstack);
                    this.amountGiven += amountRemaining;
                }
                else {
                    this.amountGiven += itemstack.stackSize;
                    entityplayer.inventory.setInventorySlotContents(slot2, (ItemStack)null);
                }
            }
            if (this.amountGiven >= this.collectTarget) {
                this.complete(entityplayer, npc);
                break;
            }
        }
        if (this.amountGiven > prevAmountGiven && !this.isCompleted()) {
            this.updateQuest();
        }
        if (!this.isCompleted()) {
            this.sendProgressSpeechbank(entityplayer, npc);
        }
    }
    
    protected boolean isQuestItem(final ItemStack itemstack) {
        if (itemstack == null) {
            return false;
        }
        if (LOTRItemMug.isItemFullDrink(this.collectItem)) {
            final ItemStack collectDrink = LOTRItemMug.getEquivalentDrink(this.collectItem);
            final ItemStack offerDrink = LOTRItemMug.getEquivalentDrink(itemstack);
            return collectDrink.getItem() == offerDrink.getItem();
        }
        return itemstack.getItem() == this.collectItem.getItem() && (this.collectItem.getItemDamage() == 32767 || itemstack.getItemDamage() == this.collectItem.getItemDamage());
    }
    
    @Override
    public float getAlignmentBonus() {
        float f = (float)this.collectTarget;
        f *= super.rewardFactor;
        return Math.max(f, 1.0f);
    }
    
    @Override
    public int getCoinBonus() {
        return Math.round(this.getAlignmentBonus() * 2.0f);
    }
    
    public static class QFCollect<Q extends LOTRMiniQuestCollect> extends QuestFactoryBase<Q>
    {
        private ItemStack collectItem;
        private int minTarget;
        private int maxTarget;
        
        public QFCollect(final String name) {
            super(name);
        }
        
        public QFCollect setCollectItem(final ItemStack itemstack, final int min, final int max) {
            this.collectItem = itemstack;
            if (this.collectItem.isItemStackDamageable()) {
                this.collectItem.setItemDamage(32767);
            }
            this.minTarget = min;
            this.maxTarget = max;
            return this;
        }
        
        @Override
        public Class getQuestClass() {
            return LOTRMiniQuestCollect.class;
        }
        
        @Override
        public Q createQuest(final LOTREntityNPC npc, final Random rand) {
            final Q quest = super.createQuest(npc, rand);
            quest.collectItem = this.collectItem.copy();
            quest.collectTarget = MathHelper.getRandomIntegerInRange(rand, this.minTarget, this.maxTarget);
            return quest;
        }
    }
}
