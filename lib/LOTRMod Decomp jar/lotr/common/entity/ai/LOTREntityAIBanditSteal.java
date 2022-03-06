// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.ai;

import java.util.Collections;
import java.util.Arrays;
import java.util.Iterator;
import lotr.common.recipe.LOTRRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.entity.player.InventoryPlayer;
import lotr.common.LOTRLevelData;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentTranslation;
import lotr.common.item.LOTRItemPouch;
import net.minecraft.item.ItemTool;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemArmor;
import lotr.common.item.LOTRItemRing;
import lotr.common.item.LOTRValuableItems;
import lotr.common.item.LOTRItemGem;
import lotr.common.item.LOTRItemCoin;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.Entity;
import java.util.List;
import java.util.ArrayList;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.entity.npc.LOTREntityBandit;
import net.minecraft.entity.ai.EntityAIBase;

public class LOTREntityAIBanditSteal extends EntityAIBase
{
    private LOTREntityBandit theBandit;
    private EntityPlayer targetPlayer;
    private EntityPlayer prevTargetPlayer;
    private double speed;
    private int chaseTimer;
    private int rePathDelay;
    
    public LOTREntityAIBanditSteal(final LOTREntityBandit bandit, final double d) {
        this.theBandit = bandit;
        this.speed = d;
        this.setMutexBits(3);
    }
    
    public boolean shouldExecute() {
        if (!this.theBandit.banditInventory.isEmpty()) {
            return false;
        }
        final List players = ((Entity)this.theBandit).worldObj.getEntitiesWithinAABB((Class)EntityPlayer.class, ((Entity)this.theBandit).boundingBox.expand(32.0, 32.0, 32.0));
        final List validTargets = new ArrayList();
        for (int i = 0; i < players.size(); ++i) {
            final EntityPlayer entityplayer = players.get(i);
            if (!entityplayer.capabilities.isCreativeMode) {
                if (this.theBandit.canStealFromPlayerInv(entityplayer)) {
                    validTargets.add(entityplayer);
                }
            }
        }
        if (validTargets.isEmpty()) {
            return false;
        }
        this.targetPlayer = validTargets.get(this.theBandit.getRNG().nextInt(validTargets.size()));
        if (this.targetPlayer != this.prevTargetPlayer) {
            this.theBandit.sendSpeechBank(this.targetPlayer, this.theBandit.getSpeechBank(this.targetPlayer));
        }
        return true;
    }
    
    public void startExecuting() {
        this.chaseTimer = 600;
    }
    
    public void updateTask() {
        --this.chaseTimer;
        this.theBandit.getLookHelper().setLookPositionWithEntity((Entity)this.targetPlayer, 30.0f, 30.0f);
        --this.rePathDelay;
        if (this.rePathDelay <= 0) {
            this.rePathDelay = 10;
            this.theBandit.getNavigator().tryMoveToEntityLiving((Entity)this.targetPlayer, this.speed);
        }
        if (this.theBandit.getDistanceSqToEntity((Entity)this.targetPlayer) <= 2.0) {
            this.chaseTimer = 0;
            this.steal();
        }
    }
    
    public boolean continueExecuting() {
        return this.targetPlayer != null && this.targetPlayer.isEntityAlive() && !this.targetPlayer.capabilities.isCreativeMode && this.theBandit.canStealFromPlayerInv(this.targetPlayer) && this.chaseTimer > 0 && this.theBandit.getDistanceSqToEntity((Entity)this.targetPlayer) < 256.0;
    }
    
    public void resetTask() {
        this.chaseTimer = 0;
        this.rePathDelay = 0;
        if (this.targetPlayer != null) {
            this.prevTargetPlayer = this.targetPlayer;
        }
        this.targetPlayer = null;
    }
    
    private void steal() {
        final InventoryPlayer inv = this.targetPlayer.inventory;
        final int thefts = MathHelper.getRandomIntegerInRange(this.theBandit.getRNG(), 1, LOTREntityBandit.MAX_THEFTS);
        boolean stolenSomething = false;
        for (int i = 0; i < thefts; ++i) {
            if (this.tryStealItem(inv, LOTRItemCoin.class)) {
                stolenSomething = true;
            }
            if (this.tryStealItem(inv, LOTRItemGem.class)) {
                stolenSomething = true;
            }
            else if (this.tryStealItem(inv, LOTRValuableItems.getToolMaterials())) {
                stolenSomething = true;
            }
            else if (this.tryStealItem(inv, LOTRItemRing.class)) {
                stolenSomething = true;
            }
            else if (this.tryStealItem(inv, ItemArmor.class)) {
                stolenSomething = true;
            }
            else if (this.tryStealItem(inv, ItemSword.class)) {
                stolenSomething = true;
            }
            else if (this.tryStealItem(inv, ItemTool.class)) {
                stolenSomething = true;
            }
            else if (this.tryStealItem(inv, LOTRItemPouch.class)) {
                stolenSomething = true;
            }
            else if (this.tryStealItem(inv)) {
                stolenSomething = true;
            }
        }
        if (stolenSomething) {
            this.targetPlayer.addChatMessage((IChatComponent)new ChatComponentTranslation("chat.lotr.banditSteal", new Object[0]));
            this.theBandit.playSound("mob.horse.leather", 0.5f, 1.0f);
            LOTRLevelData.getData(this.targetPlayer).cancelFastTravel();
        }
    }
    
    private boolean tryStealItem(final InventoryPlayer inv, final Item item) {
        return this.tryStealItem_do(inv, new BanditItemFilter() {
            @Override
            public boolean isApplicable(final ItemStack itemstack) {
                return itemstack.getItem() == item;
            }
        });
    }
    
    private boolean tryStealItem(final InventoryPlayer inv, final Class itemclass) {
        return this.tryStealItem_do(inv, new BanditItemFilter() {
            @Override
            public boolean isApplicable(final ItemStack itemstack) {
                return itemclass.isAssignableFrom(itemstack.getItem().getClass());
            }
        });
    }
    
    private boolean tryStealItem(final InventoryPlayer inv, final List<ItemStack> itemList) {
        return this.tryStealItem_do(inv, new BanditItemFilter() {
            @Override
            public boolean isApplicable(final ItemStack itemstack) {
                for (final ItemStack listItem : itemList) {
                    if (LOTRRecipes.checkItemEquals(listItem, itemstack)) {
                        return true;
                    }
                }
                return false;
            }
        });
    }
    
    private boolean tryStealItem(final InventoryPlayer inv) {
        return this.tryStealItem_do(inv, new BanditItemFilter() {
            @Override
            public boolean isApplicable(final ItemStack itemstack) {
                return true;
            }
        });
    }
    
    private boolean tryStealItem_do(final InventoryPlayer inv, final BanditItemFilter filter) {
        Integer[] inventorySlots = new Integer[inv.mainInventory.length];
        for (int l = 0; l < inventorySlots.length; ++l) {
            inventorySlots[l] = l;
        }
        final List<Integer> slotsAsList = Arrays.asList(inventorySlots);
        Collections.shuffle(slotsAsList);
        final Integer[] array;
        inventorySlots = (array = slotsAsList.toArray(inventorySlots));
        for (final int slot : array) {
            if (slot != inv.currentItem) {
                final ItemStack itemstack = inv.getStackInSlot(slot);
                if (itemstack != null) {
                    if (filter.isApplicable(itemstack) && this.stealItem(inv, slot)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private int getRandomTheftAmount(final ItemStack itemstack) {
        return MathHelper.getRandomIntegerInRange(this.theBandit.getRNG(), 1, 8);
    }
    
    private boolean stealItem(final InventoryPlayer inv, final int slot) {
        final ItemStack playerItem = inv.getStackInSlot(slot);
        int theft = this.getRandomTheftAmount(playerItem);
        if (theft > playerItem.stackSize) {
            theft = playerItem.stackSize;
        }
        int banditSlot = 0;
        while (this.theBandit.banditInventory.getStackInSlot(banditSlot) != null) {
            if (++banditSlot >= this.theBandit.banditInventory.getSizeInventory()) {
                return false;
            }
        }
        final ItemStack stolenItem = playerItem.copy();
        stolenItem.stackSize = theft;
        this.theBandit.banditInventory.setInventorySlotContents(banditSlot, stolenItem);
        final ItemStack itemStack = playerItem;
        itemStack.stackSize -= theft;
        if (playerItem.stackSize <= 0) {
            inv.setInventorySlotContents(slot, (ItemStack)null);
        }
        return this.theBandit.isNPCPersistent = true;
    }
    
    private abstract class BanditItemFilter
    {
        public abstract boolean isApplicable(final ItemStack p0);
    }
}
