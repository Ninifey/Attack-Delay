// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.projectile;

import net.minecraft.entity.Entity;
import lotr.common.entity.animal.LOTREntityTermite;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraft.entity.projectile.EntityThrowable;

public class LOTREntityThrownTermite extends EntityThrowable
{
    public LOTREntityThrownTermite(final World world) {
        super(world);
    }
    
    public LOTREntityThrownTermite(final World world, final EntityLivingBase throwingEntity) {
        super(world, throwingEntity);
    }
    
    public LOTREntityThrownTermite(final World world, final double d, final double d1, final double d2) {
        super(world, d, d1, d2);
    }
    
    protected void onImpact(final MovingObjectPosition movingobjectposition) {
        if (!((Entity)this).worldObj.isClient) {
            ((Entity)this).worldObj.createExplosion((Entity)this, ((Entity)this).posX, ((Entity)this).posY, ((Entity)this).posZ, LOTREntityTermite.explosionSize, true);
            this.setDead();
        }
    }
}
