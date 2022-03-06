// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIAvoidEntity;

public class LOTREntityAIAvoidWithChance extends EntityAIAvoidEntity
{
    private EntityCreature theEntity;
    private float chance;
    private String soundEffect;
    
    public LOTREntityAIAvoidWithChance(final EntityCreature entity, final Class avoidClass, final float f, final double d, final double d1, final float c) {
        this(entity, avoidClass, f, d, d1, c, null);
    }
    
    public LOTREntityAIAvoidWithChance(final EntityCreature entity, final Class avoidClass, final float f, final double d, final double d1, final float c, final String s) {
        super(entity, avoidClass, f, d, d1);
        this.theEntity = entity;
        this.chance = c;
        this.soundEffect = s;
    }
    
    public boolean shouldExecute() {
        return this.theEntity.getRNG().nextFloat() < this.chance && super.shouldExecute();
    }
    
    public void startExecuting() {
        super.startExecuting();
        if (this.soundEffect != null) {
            this.theEntity.playSound(this.soundEffect, 0.5f, (this.theEntity.getRNG().nextFloat() - this.theEntity.getRNG().nextFloat()) * 0.2f + 1.0f);
        }
    }
}
