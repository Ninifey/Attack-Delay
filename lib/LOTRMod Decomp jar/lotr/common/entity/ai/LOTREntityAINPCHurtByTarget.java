// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.ai.EntityAIHurtByTarget;

public class LOTREntityAINPCHurtByTarget extends EntityAIHurtByTarget
{
    public LOTREntityAINPCHurtByTarget(final LOTREntityNPC npc, final boolean flag) {
        super((EntityCreature)npc, flag);
    }
    
    protected boolean isSuitableTarget(final EntityLivingBase entity, final boolean flag) {
        if (entity == ((Entity)((EntityAITarget)this).taskOwner).ridingEntity || entity == ((Entity)((EntityAITarget)this).taskOwner).riddenByEntity) {
            return false;
        }
        final int homeX = ((EntityAITarget)this).taskOwner.getHomePosition().posX;
        final int homeY = ((EntityAITarget)this).taskOwner.getHomePosition().posY;
        final int homeZ = ((EntityAITarget)this).taskOwner.getHomePosition().posZ;
        final int homeRange = (int)((EntityAITarget)this).taskOwner.func_110174_bM();
        ((EntityAITarget)this).taskOwner.detachHome();
        final boolean superSuitable = super.isSuitableTarget(entity, flag);
        ((EntityAITarget)this).taskOwner.setHomeArea(homeX, homeY, homeZ, homeRange);
        return superSuitable;
    }
}
