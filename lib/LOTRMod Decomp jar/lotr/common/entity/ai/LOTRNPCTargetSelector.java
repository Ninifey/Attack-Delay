// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.ai;

import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRLevelData;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.Entity;
import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.EntityLiving;
import net.minecraft.command.IEntitySelector;

public class LOTRNPCTargetSelector implements IEntitySelector
{
    private EntityLiving owner;
    private LOTRFaction ownerFaction;
    
    public LOTRNPCTargetSelector(final EntityLiving entity) {
        this.owner = entity;
        this.ownerFaction = LOTRMod.getNPCFaction((Entity)entity);
    }
    
    public boolean isEntityApplicable(final Entity target) {
        if (this.ownerFaction == LOTRFaction.HOSTILE && (target.getClass().isAssignableFrom(this.owner.getClass()) || this.owner.getClass().isAssignableFrom(target.getClass()))) {
            return false;
        }
        if (target.isEntityAlive()) {
            if (target instanceof LOTREntityNPC && !((LOTREntityNPC)target).canBeFreelyTargetedBy(this.owner)) {
                return false;
            }
            if (!this.ownerFaction.approvesWarCrimes && target instanceof LOTREntityNPC && ((LOTREntityNPC)target).isCivilianNPC()) {
                return false;
            }
            final LOTRFaction targetFaction = LOTRMod.getNPCFaction(target);
            if (this.ownerFaction.isBadRelation(targetFaction)) {
                return true;
            }
            if (this.ownerFaction.isNeutral(targetFaction)) {
                EntityPlayer hiringPlayer = null;
                if (this.owner instanceof LOTREntityNPC) {
                    final LOTREntityNPC npc = (LOTREntityNPC)this.owner;
                    if (npc.hiredNPCInfo.isActive) {
                        hiringPlayer = npc.hiredNPCInfo.getHiringPlayer();
                    }
                }
                if (hiringPlayer != null) {
                    final float alignment = LOTRLevelData.getData(hiringPlayer).getAlignment(targetFaction);
                    if (alignment < 0.0f) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
