// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.ai;

import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRFoods;
import lotr.common.entity.npc.LOTREntityNPC;

public class LOTREntityAIEat extends LOTREntityAIConsumeBase
{
    public LOTREntityAIEat(final LOTREntityNPC entity, final LOTRFoods foods, final int chance) {
        super(entity, foods, chance);
    }
    
    @Override
    protected ItemStack createConsumable() {
        return super.foodPool.getRandomFood(super.rand);
    }
    
    @Override
    protected void updateConsumeTick(final int tick) {
        if (tick % 4 == 0) {
            super.theEntity.spawnFoodParticles();
            super.theEntity.playSound("random.eat", 0.5f + 0.5f * super.rand.nextInt(2), (super.rand.nextFloat() - super.rand.nextFloat()) * 0.2f + 1.0f);
        }
    }
    
    @Override
    protected void consume() {
        final ItemStack itemstack = super.theEntity.getHeldItem();
        final Item item = itemstack.getItem();
        if (item instanceof ItemFood) {
            final ItemFood food = (ItemFood)item;
            super.theEntity.heal((float)food.func_150905_g(itemstack));
        }
    }
}
