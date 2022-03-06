// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.ai;

import net.minecraft.entity.Entity;
import java.util.List;
import lotr.common.entity.npc.LOTREntityHuornBase;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.EntityCreature;

public class LOTREntityAINearestAttackableTargetHuorn extends LOTREntityAINearestAttackableTargetBasic
{
    public LOTREntityAINearestAttackableTargetHuorn(final EntityCreature entity, final Class targetClass, final int chance, final boolean flag) {
        super(entity, targetClass, chance, flag);
    }
    
    public LOTREntityAINearestAttackableTargetHuorn(final EntityCreature entity, final Class targetClass, final int chance, final boolean flag, final IEntitySelector selector) {
        super(entity, targetClass, chance, flag, selector);
    }
    
    @Override
    public boolean shouldExecute() {
        int chance = 400;
        final List nearbyHuorns = ((Entity)super.taskOwner).worldObj.getEntitiesWithinAABB((Class)LOTREntityHuornBase.class, ((Entity)super.taskOwner).boundingBox.expand(24.0, 8.0, 24.0));
        for (int i = 0; i < nearbyHuorns.size(); ++i) {
            final LOTREntityHuornBase huorn = nearbyHuorns.get(i);
            if (huorn.getAttackTarget() != null) {
                chance /= 2;
            }
        }
        if (chance < 20) {
            chance = 20;
        }
        return super.taskOwner.getRNG().nextInt(chance) == 0 && super.shouldExecute();
    }
}
