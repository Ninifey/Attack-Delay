// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.ai;

import net.minecraft.item.ItemStack;
import lotr.common.LOTRFoods;
import java.util.Random;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.ai.EntityAIBase;

public abstract class LOTREntityAIConsumeBase extends EntityAIBase
{
    protected LOTREntityNPC theEntity;
    protected Random rand;
    protected LOTRFoods foodPool;
    private int chanceToConsume;
    private int consumeTick;
    
    public LOTREntityAIConsumeBase(final LOTREntityNPC entity, final LOTRFoods foods, final int chance) {
        this.theEntity = entity;
        this.rand = this.theEntity.getRNG();
        this.foodPool = foods;
        this.chanceToConsume = chance;
        this.setMutexBits(3);
    }
    
    public boolean shouldExecute() {
        return !this.theEntity.isChild() && this.theEntity.getAttackTarget() == null && !this.theEntity.npcItemsInv.getIsEating() && this.shouldConsume();
    }
    
    protected boolean shouldConsume() {
        final boolean needsHeal = this.theEntity.getHealth() < this.theEntity.getMaxHealth();
        return (needsHeal && this.rand.nextInt(this.chanceToConsume / 4) == 0) || this.rand.nextInt(this.chanceToConsume) == 0;
    }
    
    public void startExecuting() {
        this.theEntity.npcItemsInv.setEatingBackup(this.theEntity.getHeldItem());
        this.theEntity.npcItemsInv.setIsEating(true);
        this.theEntity.setCurrentItemOrArmor(0, this.createConsumable());
        this.consumeTick = this.getConsumeTime();
    }
    
    protected int getConsumeTime() {
        return 32;
    }
    
    public void updateTask() {
        this.updateConsumeTick(--this.consumeTick);
        if (this.consumeTick == 0) {
            this.consume();
        }
    }
    
    protected abstract ItemStack createConsumable();
    
    protected abstract void updateConsumeTick(final int p0);
    
    protected abstract void consume();
    
    public boolean continueExecuting() {
        return this.consumeTick > 0 && this.theEntity.getHeldItem() != null && this.theEntity.getAttackTarget() == null;
    }
    
    public void resetTask() {
        this.theEntity.setCurrentItemOrArmor(0, this.theEntity.npcItemsInv.getEatingBackup());
        this.theEntity.npcItemsInv.setEatingBackup(null);
        this.theEntity.npcItemsInv.setIsEating(false);
        this.theEntity.refreshCurrentAttackMode();
        this.consumeTick = 0;
    }
}
