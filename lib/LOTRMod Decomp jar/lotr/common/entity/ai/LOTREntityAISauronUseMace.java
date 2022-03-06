// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.ai;

import lotr.common.item.LOTRItemSauronMace;
import java.util.List;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.Entity;
import lotr.common.LOTRMod;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import lotr.common.entity.npc.LOTREntitySauron;
import net.minecraft.entity.ai.EntityAIBase;

public class LOTREntityAISauronUseMace extends EntityAIBase
{
    private LOTREntitySauron theSauron;
    private int attackTick;
    private World theWorld;
    
    public LOTREntityAISauronUseMace(final LOTREntitySauron sauron) {
        this.attackTick = 0;
        this.theSauron = sauron;
        this.theWorld = ((Entity)this.theSauron).worldObj;
        this.setMutexBits(3);
    }
    
    public boolean shouldExecute() {
        int targets = 0;
        final List nearbyEntities = this.theWorld.getEntitiesWithinAABB((Class)EntityLivingBase.class, ((Entity)this.theSauron).boundingBox.expand(6.0, 6.0, 6.0));
        for (int i = 0; i < nearbyEntities.size(); ++i) {
            final EntityLivingBase entity = nearbyEntities.get(i);
            if (entity.isEntityAlive()) {
                if (entity instanceof EntityPlayer) {
                    final EntityPlayer entityplayer = (EntityPlayer)entity;
                    if (!entityplayer.capabilities.isCreativeMode && (LOTRLevelData.getData(entityplayer).getAlignment(this.theSauron.getFaction()) < 0.0f || this.theSauron.getAttackTarget() == entityplayer)) {
                        ++targets;
                    }
                }
                else if (this.theSauron.getFaction().isBadRelation(LOTRMod.getNPCFaction((Entity)entity))) {
                    ++targets;
                }
                else if (this.theSauron.getAttackTarget() == entity || (entity instanceof EntityLiving && ((EntityLiving)entity).getAttackTarget() == this.theSauron)) {
                    ++targets;
                }
            }
        }
        return targets >= 4 || (targets > 0 && this.theSauron.getRNG().nextInt(100) == 0);
    }
    
    public boolean continueExecuting() {
        return this.theSauron.getIsUsingMace();
    }
    
    public void startExecuting() {
        this.attackTick = 40;
        this.theSauron.setIsUsingMace(true);
    }
    
    public void resetTask() {
        this.attackTick = 40;
        this.theSauron.setIsUsingMace(false);
    }
    
    public void updateTask() {
        this.attackTick = Math.max(this.attackTick - 1, 0);
        if (this.attackTick <= 0) {
            this.attackTick = 40;
            LOTRItemSauronMace.useSauronMace(this.theSauron.getEquipmentInSlot(0), this.theWorld, (EntityLivingBase)this.theSauron);
            this.theSauron.setIsUsingMace(false);
        }
    }
}
