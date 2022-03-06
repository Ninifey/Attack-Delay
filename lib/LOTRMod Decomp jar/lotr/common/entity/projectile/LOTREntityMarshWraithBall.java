// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.projectile;

import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityThrowable;

public class LOTREntityMarshWraithBall extends EntityThrowable
{
    public int animationTick;
    public Entity attackTarget;
    
    public LOTREntityMarshWraithBall(final World world) {
        super(world);
        this.setSize(0.75f, 0.75f);
    }
    
    public LOTREntityMarshWraithBall(final World world, final EntityLivingBase entityliving) {
        super(world, entityliving);
        this.setSize(0.75f, 0.75f);
    }
    
    public LOTREntityMarshWraithBall(final World world, final double d, final double d1, final double d2) {
        super(world, d, d1, d2);
        this.setSize(0.75f, 0.75f);
    }
    
    public LOTREntityMarshWraithBall(final World world, final EntityLivingBase entityliving, final EntityLivingBase target) {
        super(world, entityliving);
        this.setSize(0.75f, 0.75f);
        this.attackTarget = (Entity)target;
        ((Entity)this).posX = ((Entity)entityliving).posX;
        ((Entity)this).posY = ((Entity)entityliving).boundingBox.minY + entityliving.getEyeHeight() - 0.1;
        ((Entity)this).posZ = ((Entity)entityliving).posZ;
        this.setPosition(((Entity)this).posX, ((Entity)this).posY, ((Entity)this).posZ);
        final double d = ((Entity)target).posX - ((Entity)this).posX;
        final double d2 = ((Entity)target).boundingBox.minY + ((Entity)target).height / 2.0f - ((Entity)this).posY;
        final double d3 = ((Entity)target).posZ - ((Entity)this).posZ;
        final double d4 = MathHelper.sqrt_double(d * d + d3 * d3);
        if (d4 >= 1.0E-7) {
            final float f2 = (float)(Math.atan2(d3, d) * 180.0 / 3.141592653589793) - 90.0f;
            final float f3 = (float)(-(Math.atan2(d2, d4) * 180.0 / 3.141592653589793));
            final double d5 = d / d4;
            final double d6 = d3 / d4;
            this.setLocationAndAngles(((Entity)this).posX, ((Entity)this).posY, ((Entity)this).posZ, f2, f3);
            this.setThrowableHeading(d, d2, d3, this.func_70182_d(), 1.0f);
        }
    }
    
    protected void entityInit() {
        super.entityInit();
        ((Entity)this).dataWatcher.addObject(16, (Object)0);
    }
    
    public int getBallAge() {
        return ((Entity)this).dataWatcher.getWatchableObjectShort(16);
    }
    
    public void setBallAge(final int age) {
        ((Entity)this).dataWatcher.updateObject(16, (Object)(short)age);
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setInteger("BallAge", this.getBallAge());
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.setBallAge(nbt.getInteger("BallAge"));
    }
    
    public void onUpdate() {
        super.onUpdate();
        if (((Entity)this).ticksExisted % 2 == 0) {
            ++this.animationTick;
            if (this.animationTick >= 16) {
                this.animationTick = 0;
            }
        }
        if (!((Entity)this).worldObj.isClient) {
            this.setBallAge(this.getBallAge() + 1);
            if (this.getBallAge() >= 200) {
                this.setDead();
            }
        }
    }
    
    protected void onImpact(final MovingObjectPosition m) {
        if (m.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            if (!((Entity)this).worldObj.isClient) {
                this.setDead();
            }
        }
        else if (m.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
            final Entity entity = m.entityHit;
            if (this.attackTarget != null && entity == this.attackTarget) {
                if (entity.attackEntityFrom(DamageSource.causeThrownDamage((Entity)this, (Entity)this.getThrower()), 5.0f) && entity instanceof EntityLivingBase) {
                    final int duration = 5;
                    ((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, duration * 20, 0));
                }
                if (!((Entity)this).worldObj.isClient) {
                    this.setDead();
                }
            }
        }
    }
    
    protected float func_70182_d() {
        return 0.5f;
    }
    
    protected float getGravityVelocity() {
        return 0.0f;
    }
}
