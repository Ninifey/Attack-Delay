// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.ai;

import net.minecraft.entity.Entity;
import java.util.List;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.LOTRConfig;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityOrc;

public class LOTREntityAIOrcSkirmish extends LOTREntityAINearestAttackableTargetBasic
{
    private LOTREntityOrc theOrc;
    
    public LOTREntityAIOrcSkirmish(final LOTREntityOrc orc, final boolean flag) {
        super(orc, LOTREntityOrc.class, 0, flag, null);
        this.theOrc = orc;
    }
    
    @Override
    public boolean shouldExecute() {
        if (!LOTRConfig.enableOrcSkirmish) {
            return false;
        }
        if (!this.canOrcSkirmish((EntityLivingBase)this.theOrc)) {
            return false;
        }
        if (!this.theOrc.isOrcSkirmishing()) {
            int chance = 20000;
            final List nearbyOrcs = ((Entity)this.theOrc).worldObj.getEntitiesWithinAABB((Class)LOTREntityOrc.class, ((Entity)this.theOrc).boundingBox.expand(16.0, 8.0, 16.0));
            for (int i = 0; i < nearbyOrcs.size(); ++i) {
                final LOTREntityOrc orc = nearbyOrcs.get(i);
                if (orc.isOrcSkirmishing()) {
                    chance /= 10;
                }
            }
            if (chance < 40) {
                chance = 40;
            }
            if (this.theOrc.getRNG().nextInt(chance) != 0) {
                return false;
            }
        }
        return super.shouldExecute();
    }
    
    @Override
    protected boolean isSuitableTarget(final EntityLivingBase entity, final boolean flag) {
        return this.canOrcSkirmish(entity) && super.isSuitableTarget(entity, flag);
    }
    
    private boolean canOrcSkirmish(final EntityLivingBase entity) {
        if (entity instanceof LOTREntityOrc) {
            final LOTREntityOrc orc = (LOTREntityOrc)entity;
            return !orc.isTrader() && !orc.hiredNPCInfo.isActive && ((Entity)orc).ridingEntity == null && orc.canOrcSkirmish();
        }
        return false;
    }
    
    @Override
    public void startExecuting() {
        super.startExecuting();
        this.theOrc.setOrcSkirmishing();
    }
}
