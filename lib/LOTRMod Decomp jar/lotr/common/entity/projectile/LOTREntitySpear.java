// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.projectile;

import lotr.common.item.LOTRItemSpear;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntitySpear extends LOTREntityProjectileBase
{
    public LOTREntitySpear(final World world) {
        super(world);
    }
    
    public LOTREntitySpear(final World world, final ItemStack item, final double d, final double d1, final double d2) {
        super(world, item, d, d1, d2);
    }
    
    public LOTREntitySpear(final World world, final EntityLivingBase entityliving, final ItemStack item, final float charge) {
        super(world, entityliving, item, charge);
    }
    
    public LOTREntitySpear(final World world, final EntityLivingBase entityliving, final EntityLivingBase target, final ItemStack item, final float charge, final float inaccuracy) {
        super(world, entityliving, target, item, charge, inaccuracy);
    }
    
    @Override
    public float getBaseImpactDamage(final Entity entity, final ItemStack itemstack) {
        final float speed = MathHelper.sqrt_double(super.motionX * super.motionX + super.motionY * super.motionY + super.motionZ * super.motionZ);
        final float damage = ((LOTRItemSpear)itemstack.getItem()).getRangedDamageMultiplier(itemstack, super.shootingEntity, entity);
        return speed * damage;
    }
}
