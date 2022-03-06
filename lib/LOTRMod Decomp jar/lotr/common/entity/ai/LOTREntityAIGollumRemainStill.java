// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.ai;

import net.minecraft.entity.Entity;
import lotr.common.entity.npc.LOTREntityGollum;
import net.minecraft.entity.ai.EntityAIBase;

public class LOTREntityAIGollumRemainStill extends EntityAIBase
{
    private LOTREntityGollum theGollum;
    
    public LOTREntityAIGollumRemainStill(final LOTREntityGollum entity) {
        this.theGollum = entity;
        this.setMutexBits(5);
    }
    
    public boolean shouldExecute() {
        return this.theGollum.getGollumOwner() != null && !this.theGollum.isInWater() && ((Entity)this.theGollum).onGround && this.theGollum.isGollumSitting();
    }
    
    public void startExecuting() {
        this.theGollum.getNavigator().clearPathEntity();
    }
}
