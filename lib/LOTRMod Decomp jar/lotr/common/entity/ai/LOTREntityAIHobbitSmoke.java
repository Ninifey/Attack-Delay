// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.ai;

import net.minecraft.entity.Entity;
import lotr.common.item.LOTRItemHobbitPipe;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.entity.projectile.LOTREntitySmokeRing;
import lotr.common.LOTRMod;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRFoods;
import lotr.common.entity.npc.LOTREntityNPC;

public class LOTREntityAIHobbitSmoke extends LOTREntityAIConsumeBase
{
    public LOTREntityAIHobbitSmoke(final LOTREntityNPC entity, final int chance) {
        super(entity, null, chance);
    }
    
    @Override
    protected ItemStack createConsumable() {
        return new ItemStack(LOTRMod.hobbitPipe);
    }
    
    @Override
    protected void updateConsumeTick(final int tick) {
    }
    
    @Override
    protected void consume() {
        final LOTREntitySmokeRing smoke = new LOTREntitySmokeRing(((Entity)super.theEntity).worldObj, (EntityLivingBase)super.theEntity);
        int color = 0;
        final ItemStack itemstack = super.theEntity.getHeldItem();
        if (itemstack != null && itemstack.getItem() instanceof LOTRItemHobbitPipe) {
            color = LOTRItemHobbitPipe.getSmokeColor(itemstack);
        }
        smoke.setSmokeColour(color);
        ((Entity)super.theEntity).worldObj.spawnEntityInWorld((Entity)smoke);
        super.theEntity.playSound("lotr:item.puff", 1.0f, (super.rand.nextFloat() - super.rand.nextFloat()) * 0.2f + 1.0f);
        super.theEntity.heal(2.0f);
    }
}
