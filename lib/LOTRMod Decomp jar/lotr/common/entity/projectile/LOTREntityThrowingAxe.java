// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.projectile;

import net.minecraft.util.MathHelper;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import lotr.common.item.LOTRItemThrowingAxe;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityThrowingAxe extends LOTREntityProjectileBase
{
    private int axeRotation;
    
    public LOTREntityThrowingAxe(final World world) {
        super(world);
    }
    
    public LOTREntityThrowingAxe(final World world, final ItemStack item, final double d, final double d1, final double d2) {
        super(world, item, d, d1, d2);
    }
    
    public LOTREntityThrowingAxe(final World world, final EntityLivingBase entityliving, final ItemStack item, final float charge) {
        super(world, entityliving, item, charge);
    }
    
    public LOTREntityThrowingAxe(final World world, final EntityLivingBase entityliving, final EntityLivingBase target, final ItemStack item, final float charge, final float inaccuracy) {
        super(world, entityliving, target, item, charge, inaccuracy);
    }
    
    private boolean isThrowingAxe() {
        final Item item = this.getProjectileItem().getItem();
        return item instanceof LOTRItemThrowingAxe;
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!super.inGround) {
            ++this.axeRotation;
            if (this.axeRotation > 9) {
                this.axeRotation = 0;
            }
            super.rotationPitch = this.axeRotation / 9.0f * 360.0f;
        }
        if (!this.isThrowingAxe()) {
            this.setDead();
        }
    }
    
    @Override
    public float getBaseImpactDamage(final Entity entity, final ItemStack itemstack) {
        if (!this.isThrowingAxe()) {
            return 0.0f;
        }
        final float speed = MathHelper.sqrt_double(super.motionX * super.motionX + super.motionY * super.motionY + super.motionZ * super.motionZ);
        final float damage = ((LOTRItemThrowingAxe)itemstack.getItem()).getRangedDamageMultiplier(itemstack, super.shootingEntity, entity);
        return speed * damage;
    }
}
