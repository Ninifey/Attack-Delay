// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.projectile;

import lotr.common.LOTRMod;
import lotr.common.fac.LOTRFaction;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import java.util.List;
import net.minecraft.util.DamageSource;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketWeaponFX;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraft.entity.projectile.EntityThrowable;

public class LOTREntityGandalfFireball extends EntityThrowable
{
    public int animationTick;
    
    public LOTREntityGandalfFireball(final World world) {
        super(world);
    }
    
    public LOTREntityGandalfFireball(final World world, final EntityLivingBase entityliving) {
        super(world, entityliving);
    }
    
    public LOTREntityGandalfFireball(final World world, final double d, final double d1, final double d2) {
        super(world, d, d1, d2);
    }
    
    protected void entityInit() {
        super.entityInit();
        ((Entity)this).dataWatcher.addObject(16, (Object)0);
    }
    
    public int getFireballAge() {
        return ((Entity)this).dataWatcher.getWatchableObjectShort(16);
    }
    
    public void setFireballAge(final int age) {
        ((Entity)this).dataWatcher.updateObject(16, (Object)(short)age);
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setInteger("FireballAge", this.getFireballAge());
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.setFireballAge(nbt.getInteger("FireballAge"));
    }
    
    public void onUpdate() {
        super.onUpdate();
        if (((Entity)this).ticksExisted % 5 == 0) {
            ++this.animationTick;
            if (this.animationTick >= 4) {
                this.animationTick = 0;
            }
        }
        if (!((Entity)this).worldObj.isClient) {
            this.setFireballAge(this.getFireballAge() + 1);
            if (this.getFireballAge() >= 200) {
                this.explode(null);
            }
        }
    }
    
    protected void onImpact(final MovingObjectPosition m) {
        if (!((Entity)this).worldObj.isClient) {
            if (m.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                this.explode(null);
            }
            else if (m.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
                final Entity entity = m.entityHit;
                if (this.isEntityVulnerable(entity)) {
                    this.explode(entity);
                }
            }
        }
    }
    
    private void explode(final Entity target) {
        if (((Entity)this).worldObj.isClient) {
            return;
        }
        ((Entity)this).worldObj.playSoundAtEntity((Entity)this, "lotr:item.gandalfFireball", 4.0f, (((Entity)this).rand.nextFloat() - ((Entity)this).rand.nextFloat()) * 0.2f + 1.0f);
        final LOTRPacketWeaponFX packet = new LOTRPacketWeaponFX(LOTRPacketWeaponFX.Type.FIREBALL_GANDALF_WHITE, (Entity)this);
        LOTRPacketHandler.networkWrapper.sendToAllAround((IMessage)packet, LOTRPacketHandler.nearEntity((Entity)this, 64.0));
        if (target != null && this.isEntityVulnerable(target)) {
            target.attackEntityFrom(DamageSource.causeMobDamage(this.getThrower()), 10.0f);
        }
        final List entities = ((Entity)this).worldObj.getEntitiesWithinAABB((Class)EntityLivingBase.class, ((Entity)this).boundingBox.expand(6.0, 6.0, 6.0));
        if (!entities.isEmpty()) {
            for (int i = 0; i < entities.size(); ++i) {
                final EntityLivingBase entity = entities.get(i);
                if (entity != target && this.isEntityVulnerable((Entity)entity)) {
                    final float damage = 10.0f - this.getDistanceToEntity((Entity)entity) * 0.5f;
                    if (damage > 0.0f) {
                        entity.attackEntityFrom(DamageSource.causeMobDamage(this.getThrower()), damage);
                    }
                }
            }
        }
        this.setDead();
    }
    
    private boolean isEntityVulnerable(final Entity entity) {
        if (entity == this.getThrower()) {
            return false;
        }
        if (!(entity instanceof EntityLivingBase)) {
            return false;
        }
        if (entity instanceof EntityPlayer) {
            return LOTRLevelData.getData((EntityPlayer)entity).getAlignment(LOTRFaction.HIGH_ELF) < 0.0f;
        }
        return !LOTRFaction.HIGH_ELF.isGoodRelation(LOTRMod.getNPCFaction(entity));
    }
    
    protected float func_70182_d() {
        return 1.5f;
    }
    
    protected float getGravityVelocity() {
        return 0.0f;
    }
}
