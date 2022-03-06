// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.ai;

import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityGollum;
import net.minecraft.entity.ai.EntityAIAvoidEntity;

public class LOTREntityAIGollumAvoidEntity extends EntityAIAvoidEntity
{
    private LOTREntityGollum theGollum;
    
    public LOTREntityAIGollumAvoidEntity(final LOTREntityGollum gollum, final Class entityClass, final float f, final double d, final double d1) {
        super((EntityCreature)gollum, entityClass, f, d, d1);
        this.theGollum = gollum;
    }
    
    public void startExecuting() {
        super.startExecuting();
        this.theGollum.setGollumFleeing(true);
    }
    
    public void resetTask() {
        super.resetTask();
        this.theGollum.setGollumFleeing(false);
    }
}
