// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.projectile;

import net.minecraft.item.Item;
import lotr.common.item.LOTRItemDagger;
import lotr.common.item.LOTRItemDart;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTREntityDart extends LOTREntityProjectileBase
{
    public static final float DEF_DART_DAMAGE = 1.0f;
    public float dartDamageFactor;
    
    public LOTREntityDart(final World world) {
        super(world);
        this.dartDamageFactor = 1.0f;
    }
    
    public LOTREntityDart(final World world, final ItemStack item, final double d, final double d1, final double d2) {
        super(world, item, d, d1, d2);
        this.dartDamageFactor = 1.0f;
    }
    
    public LOTREntityDart(final World world, final EntityLivingBase entityliving, final ItemStack item, final float charge) {
        super(world, entityliving, item, charge);
        this.dartDamageFactor = 1.0f;
    }
    
    public LOTREntityDart(final World world, final EntityLivingBase entityliving, final EntityLivingBase target, final ItemStack item, final float charge, final float inaccuracy) {
        super(world, entityliving, target, item, charge, inaccuracy);
        this.dartDamageFactor = 1.0f;
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setFloat("DartDamage", this.dartDamageFactor);
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.dartDamageFactor = nbt.getFloat("DartDamage");
    }
    
    @Override
    public float getBaseImpactDamage(final Entity entity, final ItemStack itemstack) {
        final float speed = MathHelper.sqrt_double(super.motionX * super.motionX + super.motionY * super.motionY + super.motionZ * super.motionZ);
        return speed * this.dartDamageFactor;
    }
    
    @Override
    protected float getKnockbackFactor() {
        return 0.5f;
    }
    
    @Override
    protected void onCollideWithTarget(final Entity entity) {
        if (!super.worldObj.isClient && entity instanceof EntityLivingBase) {
            final ItemStack itemstack = this.getProjectileItem();
            if (itemstack != null) {
                final Item item = itemstack.getItem();
                if (item instanceof LOTRItemDart && ((LOTRItemDart)item).isPoisoned) {
                    LOTRItemDagger.applyStandardPoison((EntityLivingBase)entity);
                }
            }
        }
        super.onCollideWithTarget(entity);
    }
    
    @Override
    public int maxTicksInGround() {
        return 1200;
    }
}
