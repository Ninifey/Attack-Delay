// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.ai;

import net.minecraft.entity.Entity;
import lotr.common.entity.npc.LOTRBoss;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.world.World;
import net.minecraft.entity.ai.EntityAIBase;

public class LOTREntityAIBossJumpAttack extends EntityAIBase
{
    private World theWorld;
    private LOTREntityNPC theBoss;
    private double jumpSpeed;
    private float jumpChance;
    
    public LOTREntityAIBossJumpAttack(final LOTREntityNPC boss, final double d, final float f) {
        this.theBoss = boss;
        this.theWorld = ((Entity)boss).worldObj;
        this.jumpSpeed = d;
        this.jumpChance = f;
        this.setMutexBits(3);
    }
    
    public boolean shouldExecute() {
        if (this.theBoss.getAttackTarget() == null) {
            return false;
        }
        if (this.theBoss.bossInfo.jumpAttack) {
            return false;
        }
        if (this.theBoss.getRNG().nextInt(20) == 0) {
            float f = ((LOTRBoss)this.theBoss).getBaseChanceModifier();
            f *= this.jumpChance;
            final int enemies = this.theBoss.bossInfo.getNearbyEnemies().size();
            if (enemies > 1.0f) {
                f *= enemies * 4.0f;
            }
            float distance = this.theBoss.getDistanceToEntity((Entity)this.theBoss.getAttackTarget());
            distance = 8.0f - distance;
            distance /= 2.0f;
            if (distance > 1.0f) {
                f *= distance;
            }
            return this.theBoss.getRNG().nextFloat() < f;
        }
        return false;
    }
    
    public void startExecuting() {
        this.theBoss.bossInfo.doJumpAttack(this.jumpSpeed);
    }
}
