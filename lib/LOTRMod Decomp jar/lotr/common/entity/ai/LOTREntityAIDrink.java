// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.ai;

import java.util.List;
import net.minecraft.util.MathHelper;
import lotr.common.entity.npc.LOTRTradeable;
import lotr.common.LOTRMod;
import net.minecraft.entity.Entity;
import net.minecraft.command.IEntitySelector;
import net.minecraft.item.Item;
import lotr.common.item.LOTRItemMug;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRFoods;
import lotr.common.entity.npc.LOTREntityNPC;

public class LOTREntityAIDrink extends LOTREntityAIConsumeBase
{
    public LOTREntityAIDrink(final LOTREntityNPC entity, final LOTRFoods foods, final int chance) {
        super(entity, foods, chance);
    }
    
    @Override
    protected ItemStack createConsumable() {
        final ItemStack drink = super.foodPool.getRandomFood(super.rand);
        final Item item = drink.getItem();
        if (item instanceof LOTRItemMug && ((LOTRItemMug)item).isBrewable) {
            LOTRItemMug.setStrengthMeta(drink, 1 + super.rand.nextInt(3));
        }
        return drink;
    }
    
    @Override
    protected boolean shouldConsume() {
        return (super.theEntity.isDrunkard() && super.rand.nextInt(100) == 0) || super.shouldConsume();
    }
    
    @Override
    protected int getConsumeTime() {
        int time = super.getConsumeTime();
        if (super.theEntity.isDrunkard()) {
            time *= 1 + super.rand.nextInt(4);
        }
        return time;
    }
    
    @Override
    protected void updateConsumeTick(final int tick) {
        if (tick % 4 == 0) {
            super.theEntity.playSound("random.drink", 0.5f, super.rand.nextFloat() * 0.1f + 0.9f);
        }
    }
    
    @Override
    protected void consume() {
        final ItemStack itemstack = super.theEntity.getHeldItem();
        final Item item = itemstack.getItem();
        if (item instanceof LOTRItemMug) {
            final LOTRItemMug drink = (LOTRItemMug)item;
            drink.applyToNPC(super.theEntity, itemstack);
            if (drink.alcoholicity > 0.0f && super.theEntity.canGetDrunk() && !super.theEntity.isDrunkard() && super.rand.nextInt(3) == 0) {
                final double range = 12.0;
                final IEntitySelector selectNonEnemyBartenders = (IEntitySelector)new IEntitySelector() {
                    public boolean isEntityApplicable(final Entity entity) {
                        return entity.isEntityAlive() && !LOTRMod.getNPCFaction(entity).isBadRelation(LOTREntityAIDrink.this.theEntity.getFaction());
                    }
                };
                final List nearbyBartenders = ((Entity)super.theEntity).worldObj.selectEntitiesWithinAABB((Class)LOTRTradeable.Bartender.class, ((Entity)super.theEntity).boundingBox.expand(range, range, range), selectNonEnemyBartenders);
                if (!nearbyBartenders.isEmpty()) {
                    int drunkTime = MathHelper.getRandomIntegerInRange(super.rand, 30, 1500);
                    drunkTime *= 20;
                    super.theEntity.familyInfo.setDrunkTime(drunkTime);
                }
            }
        }
    }
}
