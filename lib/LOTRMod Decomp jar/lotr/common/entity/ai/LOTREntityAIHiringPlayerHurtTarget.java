// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.ai;

import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRMod;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.ai.EntityAITarget;

public class LOTREntityAIHiringPlayerHurtTarget extends EntityAITarget
{
    private LOTREntityNPC theNPC;
    private EntityLivingBase theTarget;
    private int playerLastAttackerTime;
    
    public LOTREntityAIHiringPlayerHurtTarget(final LOTREntityNPC entity) {
        super((EntityCreature)entity, false);
        this.theNPC = entity;
        this.setMutexBits(1);
    }
    
    public boolean shouldExecute() {
        if (!this.theNPC.hiredNPCInfo.isActive || this.theNPC.hiredNPCInfo.isHalted()) {
            return false;
        }
        final EntityPlayer entityplayer = this.theNPC.hiredNPCInfo.getHiringPlayer();
        if (entityplayer == null) {
            return false;
        }
        this.theTarget = entityplayer.getLastAttacker();
        final int i = entityplayer.getLastAttackerTime();
        return i != this.playerLastAttackerTime && LOTRMod.canNPCAttackEntity(this.theNPC, this.theTarget, true) && this.isSuitableTarget(this.theTarget, false);
    }
    
    public void startExecuting() {
        this.theNPC.setAttackTarget(this.theTarget);
        this.theNPC.hiredNPCInfo.wasAttackCommanded = true;
        final EntityPlayer entityplayer = this.theNPC.hiredNPCInfo.getHiringPlayer();
        if (entityplayer != null) {
            this.playerLastAttackerTime = entityplayer.getLastAttackerTime();
        }
        super.startExecuting();
    }
}
