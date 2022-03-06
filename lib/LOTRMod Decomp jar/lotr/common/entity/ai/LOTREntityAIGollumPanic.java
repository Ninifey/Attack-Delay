// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.ai;

import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityGollum;
import net.minecraft.entity.ai.EntityAIPanic;

public class LOTREntityAIGollumPanic extends EntityAIPanic
{
    private LOTREntityGollum theGollum;
    
    public LOTREntityAIGollumPanic(final LOTREntityGollum gollum, final double d) {
        super((EntityCreature)gollum, d);
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
