// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraft.entity.projectile.EntitySnowball;

public class LOTREntityTrollSnowball extends EntitySnowball
{
    public LOTREntityTrollSnowball(final World world) {
        super(world);
    }
    
    public LOTREntityTrollSnowball(final World world, final EntityLivingBase entity) {
        super(world, entity);
    }
    
    public LOTREntityTrollSnowball(final World world, final double d, final double d1, final double d2) {
        super(world, d, d1, d2);
    }
    
    protected void onImpact(final MovingObjectPosition target) {
        if (target.entityHit != null) {
            final float damage = 3.0f;
            target.entityHit.attackEntityFrom(DamageSource.causeThrownDamage((Entity)this, (Entity)this.getThrower()), damage);
        }
        for (int i = 0; i < 8; ++i) {
            ((Entity)this).worldObj.spawnParticle("snowballpoof", ((Entity)this).posX, ((Entity)this).posY, ((Entity)this).posZ, 0.0, 0.0, 0.0);
        }
        if (!((Entity)this).worldObj.isClient) {
            this.setDead();
        }
    }
}
