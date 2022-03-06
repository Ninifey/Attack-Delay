// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.ai;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.entity.npc.LOTREntityBandit;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.EntityCreature;

public class LOTREntityAINearestAttackableTargetBandit extends LOTREntityAINearestAttackableTargetBasic
{
    public LOTREntityAINearestAttackableTargetBandit(final EntityCreature entity, final Class targetClass, final int chance, final boolean flag) {
        super(entity, targetClass, chance, flag);
    }
    
    public LOTREntityAINearestAttackableTargetBandit(final EntityCreature entity, final Class targetClass, final int chance, final boolean flag, final IEntitySelector selector) {
        super(entity, targetClass, chance, flag, selector);
    }
    
    @Override
    public boolean shouldExecute() {
        final LOTREntityBandit bandit = (LOTREntityBandit)super.taskOwner;
        return bandit.banditInventory.isEmpty() && super.shouldExecute();
    }
    
    @Override
    protected boolean isSuitableTarget(final EntityLivingBase entity, final boolean flag) {
        return entity instanceof EntityPlayer && super.isSuitableTarget(entity, flag);
    }
    
    @Override
    protected boolean isPlayerSuitableTarget(final EntityPlayer entityplayer) {
        final LOTREntityBandit bandit = (LOTREntityBandit)super.taskOwner;
        return !bandit.canStealFromPlayerInv(entityplayer) && super.isPlayerSuitableTarget(entityplayer);
    }
}
