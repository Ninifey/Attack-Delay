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

public class LOTREntityAIHiringPlayerHurtByTarget extends EntityAITarget
{
    private LOTREntityNPC theNPC;
    private EntityLivingBase theTarget;
    private int playerRevengeTimer;
    
    public LOTREntityAIHiringPlayerHurtByTarget(final LOTREntityNPC entity) {
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
        this.theTarget = entityplayer.getAITarget();
        final int i = entityplayer.func_142015_aE();
        return i != this.playerRevengeTimer && LOTRMod.canNPCAttackEntity(this.theNPC, this.theTarget, true) && this.isSuitableTarget(this.theTarget, false);
    }
    
    public void startExecuting() {
        this.theNPC.setAttackTarget(this.theTarget);
        final EntityPlayer entityplayer = this.theNPC.hiredNPCInfo.getHiringPlayer();
        if (entityplayer != null) {
            this.playerRevengeTimer = entityplayer.func_142015_aE();
        }
        super.startExecuting();
    }
}
