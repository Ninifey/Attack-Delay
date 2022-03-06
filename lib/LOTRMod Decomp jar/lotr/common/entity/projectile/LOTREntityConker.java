// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.projectile;

import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraft.entity.projectile.EntityThrowable;

public class LOTREntityConker extends EntityThrowable
{
    public LOTREntityConker(final World world) {
        super(world);
    }
    
    public LOTREntityConker(final World world, final EntityLivingBase entity) {
        super(world, entity);
    }
    
    public LOTREntityConker(final World world, final double d, final double d1, final double d2) {
        super(world, d, d1, d2);
    }
    
    protected void onImpact(final MovingObjectPosition m) {
        if (m.entityHit != null) {
            m.entityHit.attackEntityFrom(DamageSource.causeThrownDamage((Entity)this, (Entity)this.getThrower()), 1.0f);
        }
        if (!((Entity)this).worldObj.isClient) {
            this.entityDropItem(new ItemStack(LOTRMod.chestnut), 0.0f);
            this.setDead();
        }
    }
    
    protected float func_70182_d() {
        return 1.0f;
    }
    
    protected float getGravityVelocity() {
        return 0.04f;
    }
}
