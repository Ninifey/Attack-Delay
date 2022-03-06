// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.ai;

import java.util.Iterator;
import lotr.common.LOTRMod;
import lotr.common.LOTRLevelData;
import lotr.common.entity.npc.LOTREntityBandit;
import java.util.Comparator;
import java.util.List;
import java.util.Collections;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.entity.npc.LOTREntityNPCRideable;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.ai.EntityAITarget;

public class LOTREntityAINearestAttackableTargetBasic extends EntityAITarget
{
    private final Class targetClass;
    private final int targetChance;
    private final TargetSorter targetSorter;
    private final IEntitySelector targetSelector;
    private EntityLivingBase targetEntity;
    
    public LOTREntityAINearestAttackableTargetBasic(final EntityCreature entity, final Class cls, final int chance, final boolean checkSight) {
        this(entity, cls, chance, checkSight, false, null);
    }
    
    public LOTREntityAINearestAttackableTargetBasic(final EntityCreature entity, final Class cls, final int chance, final boolean checkSight, final IEntitySelector selector) {
        this(entity, cls, chance, checkSight, false, selector);
    }
    
    public LOTREntityAINearestAttackableTargetBasic(final EntityCreature entity, final Class cls, final int chance, final boolean checkSight, final boolean nearby, final IEntitySelector selector) {
        super(entity, checkSight, nearby);
        this.targetClass = cls;
        this.targetChance = chance;
        this.targetSorter = new TargetSorter((EntityLivingBase)entity);
        this.setMutexBits(1);
        this.targetSelector = (IEntitySelector)new IEntitySelector() {
            public boolean isEntityApplicable(final Entity testEntity) {
                if (testEntity instanceof EntityLivingBase) {
                    final EntityLivingBase testEntityLiving = (EntityLivingBase)testEntity;
                    return (selector == null || selector.isEntityApplicable((Entity)testEntityLiving)) && LOTREntityAINearestAttackableTargetBasic.this.isSuitableTarget(testEntityLiving, false);
                }
                return false;
            }
        };
    }
    
    public boolean shouldExecute() {
        if (this.targetChance > 0 && super.taskOwner.getRNG().nextInt(this.targetChance) != 0) {
            return false;
        }
        if (super.taskOwner instanceof LOTREntityNPC) {
            final LOTREntityNPC npc = (LOTREntityNPC)super.taskOwner;
            if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.isHalted()) {
                return false;
            }
            if (npc.isChild()) {
                return false;
            }
        }
        if (super.taskOwner instanceof LOTREntityNPCRideable) {
            final LOTREntityNPCRideable rideable = (LOTREntityNPCRideable)super.taskOwner;
            if (rideable.isNPCTamed() || ((Entity)rideable).riddenByEntity instanceof EntityPlayer) {
                return false;
            }
        }
        final double range = this.getTargetDistance();
        final double rangeY = Math.min(range, 8.0);
        final List entities = ((Entity)super.taskOwner).worldObj.selectEntitiesWithinAABB(this.targetClass, ((Entity)super.taskOwner).boundingBox.expand(range, rangeY, range), this.targetSelector);
        Collections.sort((List<Object>)entities, (Comparator<? super Object>)this.targetSorter);
        if (entities.isEmpty()) {
            return false;
        }
        this.targetEntity = entities.get(0);
        return true;
    }
    
    public void startExecuting() {
        super.taskOwner.setAttackTarget(this.targetEntity);
        super.startExecuting();
    }
    
    protected boolean isSuitableTarget(final EntityLivingBase entity, final boolean flag) {
        if (entity == ((Entity)super.taskOwner).ridingEntity || entity == ((Entity)super.taskOwner).riddenByEntity) {
            return false;
        }
        if (!super.isSuitableTarget(entity, flag)) {
            return false;
        }
        if (entity instanceof EntityPlayer) {
            return this.isPlayerSuitableTarget((EntityPlayer)entity);
        }
        return !(entity instanceof LOTREntityBandit) || (super.taskOwner instanceof LOTREntityNPC && ((LOTREntityNPC)super.taskOwner).hiredNPCInfo.isActive);
    }
    
    protected boolean isPlayerSuitableTarget(final EntityPlayer entityplayer) {
        return this.isPlayerSuitableAlignmentTarget(entityplayer);
    }
    
    protected boolean isPlayerSuitableAlignmentTarget(final EntityPlayer entityplayer) {
        final float alignment = LOTRLevelData.getData(entityplayer).getAlignment(LOTRMod.getNPCFaction((Entity)super.taskOwner));
        return alignment < 0.0f;
    }
    
    public static class TargetSorter implements Comparator<Entity>
    {
        private final EntityLivingBase theNPC;
        
        public TargetSorter(final EntityLivingBase entity) {
            this.theNPC = entity;
        }
        
        @Override
        public int compare(final Entity e1, final Entity e2) {
            final double d1 = this.distanceMetricSq(e1);
            final double d2 = this.distanceMetricSq(e2);
            if (d1 < d2) {
                return -1;
            }
            if (d1 > d2) {
                return 1;
            }
            return 0;
        }
        
        private double distanceMetricSq(final Entity target) {
            double dSq = this.theNPC.getDistanceSqToEntity(target);
            final double avg = 12.0;
            final double avgSq = avg * avg;
            dSq /= avgSq;
            int dupes = 0;
            final double nearRange = 8.0;
            final List nearbyEntities = ((Entity)this.theNPC).worldObj.getEntitiesWithinAABB((Class)LOTREntityNPC.class, ((Entity)this.theNPC).boundingBox.expand(nearRange, nearRange, nearRange));
            for (final Object obj : nearbyEntities) {
                final LOTREntityNPC nearby = (LOTREntityNPC)obj;
                if (nearby == this.theNPC) {
                    continue;
                }
                if (!nearby.isEntityAlive() || nearby.getAttackTarget() != target) {
                    continue;
                }
                ++dupes;
            }
            final int dupesSq = dupes * dupes;
            return dSq + dupesSq;
        }
    }
}
