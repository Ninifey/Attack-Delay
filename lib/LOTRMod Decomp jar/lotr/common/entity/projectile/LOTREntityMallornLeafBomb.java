// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.projectile;

import java.util.Iterator;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityNPC;
import java.util.List;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.block.Block;
import lotr.common.LOTRMod;
import net.minecraft.util.Vec3;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import java.util.UUID;
import net.minecraft.entity.projectile.EntityThrowable;

public class LOTREntityMallornLeafBomb extends EntityThrowable
{
    private UUID throwerUUID;
    private int leavesAge;
    private static int MAX_LEAVES_AGE;
    public float leavesDamage;
    
    public LOTREntityMallornLeafBomb(final World world) {
        super(world);
        this.setSize(2.0f, 2.0f);
        this.setPosition(((Entity)this).posX, ((Entity)this).posY, ((Entity)this).posZ);
    }
    
    public LOTREntityMallornLeafBomb(final World world, final EntityLivingBase thrower) {
        super(world, thrower);
        this.setSize(2.0f, 2.0f);
        this.setPosition(((Entity)this).posX, ((Entity)this).posY, ((Entity)this).posZ);
        this.throwerUUID = thrower.getUniqueID();
    }
    
    public LOTREntityMallornLeafBomb(final World world, final EntityLivingBase thrower, final EntityLivingBase target) {
        super(world, thrower);
        this.setSize(2.0f, 2.0f);
        this.setPosition(((Entity)this).posX, ((Entity)this).posY, ((Entity)this).posZ);
        this.throwerUUID = thrower.getUniqueID();
        ((Entity)this).posY = ((Entity)thrower).posY + thrower.getEyeHeight() - 0.1;
        final double dx = ((Entity)target).posX - ((Entity)thrower).posX;
        final double dy = ((Entity)target).boundingBox.minY + ((Entity)target).height / 3.0f - ((Entity)this).posY;
        final double dz = ((Entity)target).posZ - ((Entity)thrower).posZ;
        final double dxz = MathHelper.sqrt_double(dx * dx + dz * dz);
        if (dxz >= 1.0E-7) {
            final float f2 = (float)(Math.atan2(dz, dx) * 180.0 / 3.141592653589793) - 90.0f;
            final float f3 = (float)(-(Math.atan2(dy, dxz) * 180.0 / 3.141592653589793));
            final double d4 = dx / dxz;
            final double d5 = dz / dxz;
            this.setLocationAndAngles(((Entity)thrower).posX + d4, ((Entity)this).posY, ((Entity)thrower).posZ + d5, f2, f3);
            ((Entity)this).yOffset = 0.0f;
            this.setThrowableHeading(dx, dy, dz, this.func_70182_d(), 1.0f);
        }
    }
    
    public LOTREntityMallornLeafBomb(final World world, final double d, final double d1, final double d2) {
        super(world, d, d1, d2);
        this.setSize(2.0f, 2.0f);
        this.setPosition(((Entity)this).posX, ((Entity)this).posY, ((Entity)this).posZ);
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setInteger("LeavesAge", this.leavesAge);
        nbt.setFloat("LeavesDamage", this.leavesDamage);
        if (this.throwerUUID != null) {
            nbt.setString("ThrowerUUID", this.throwerUUID.toString());
        }
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.leavesAge = nbt.getInteger("LeavesAge");
        this.leavesDamage = nbt.getFloat("LeavesDamage");
        if (nbt.hasKey("ThrowerUUID")) {
            this.throwerUUID = UUID.fromString(nbt.getString("ThrowerUUID"));
        }
    }
    
    public void onUpdate() {
        super.onUpdate();
        if (!((Entity)this).worldObj.isClient) {
            ++this.leavesAge;
            if (this.leavesAge >= LOTREntityMallornLeafBomb.MAX_LEAVES_AGE) {
                this.explode(null);
            }
        }
        else {
            final Vec3 axis = Vec3.createVectorHelper(-((Entity)this).motionX, -((Entity)this).motionY, -((Entity)this).motionZ);
            for (int leaves = 20, l = 0; l < leaves; ++l) {
                final float angle = l / (float)leaves * 2.0f * 3.1415927f;
                final Vec3 rotate = Vec3.createVectorHelper(1.0, 1.0, 1.0);
                rotate.rotateAroundX((float)Math.toRadians(40.0));
                rotate.rotateAroundY(angle);
                final float dot = (float)rotate.dotProduct(axis);
                final Vec3 parallel = Vec3.createVectorHelper(axis.xCoord * dot, axis.yCoord * dot, axis.zCoord * dot);
                final Vec3 perp = parallel.subtract(rotate);
                final Vec3 cross = rotate.crossProduct(axis);
                final float sin = MathHelper.sin(-angle);
                final float cos = MathHelper.cos(-angle);
                final Vec3 crossSin = Vec3.createVectorHelper(cross.xCoord * sin, cross.yCoord * sin, cross.zCoord * sin);
                final Vec3 perpCos = Vec3.createVectorHelper(perp.xCoord * cos, perp.yCoord * cos, perp.zCoord * cos);
                final Vec3 result = parallel.addVector(crossSin.xCoord + perpCos.xCoord, crossSin.yCoord + perpCos.yCoord, crossSin.zCoord + perpCos.zCoord);
                final double d = ((Entity)this).posX;
                final double d2 = ((Entity)this).posY;
                final double d3 = ((Entity)this).posZ;
                final double d4 = result.xCoord / 10.0;
                final double d5 = result.yCoord / 10.0;
                final double d6 = result.zCoord / 10.0;
                LOTRMod.proxy.spawnParticle("leafGold_30", d, d2, d3, d4, d5, d6);
                LOTRMod.proxy.spawnParticle("mEntHeal_" + Block.getIdFromBlock(LOTRMod.leaves) + "_" + 1, d, d2, d3, d4 * 0.5, d5 * 0.5, d6 * 0.5);
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
        if (!((Entity)this).worldObj.isClient) {
            final double range = 2.0;
            final List entities = ((Entity)this).worldObj.getEntitiesWithinAABB((Class)EntityLivingBase.class, ((Entity)this).boundingBox.expand(range, range, range));
            if (!entities.isEmpty()) {
                for (int i = 0; i < entities.size(); ++i) {
                    final EntityLivingBase entity = entities.get(i);
                    if (this.isEntityVulnerable((Entity)entity)) {
                        final float damage = this.leavesDamage / Math.max(1.0f, this.getDistanceToEntity((Entity)entity));
                        if (damage > 0.0f) {
                            entity.attackEntityFrom(DamageSource.causeMobDamage(this.getThrower()), damage);
                        }
                    }
                }
            }
            this.setDead();
        }
    }
    
    private boolean isEntityVulnerable(final Entity target) {
        if (target == this.getThrower()) {
            return false;
        }
        if (!(target instanceof EntityLivingBase)) {
            return false;
        }
        final EntityLivingBase livingTarget = (EntityLivingBase)target;
        final EntityLivingBase thrower = this.getThrower();
        if (thrower instanceof LOTREntityNPC) {
            ((LOTREntityNPC)thrower).getJumpHelper().doJump();
            return LOTRMod.canNPCAttackEntity((EntityCreature)thrower, livingTarget, false);
        }
        return true;
    }
    
    public EntityLivingBase getThrower() {
        if (this.throwerUUID != null) {
            for (final Object obj : ((Entity)this).worldObj.loadedEntityList) {
                final Entity entity = (Entity)obj;
                if (entity instanceof EntityLivingBase && entity.getUniqueID().equals(this.throwerUUID)) {
                    return (EntityLivingBase)entity;
                }
            }
        }
        return null;
    }
    
    protected float func_70182_d() {
        return 1.0f;
    }
    
    protected float getGravityVelocity() {
        return 0.0f;
    }
    
    static {
        LOTREntityMallornLeafBomb.MAX_LEAVES_AGE = 200;
    }
}
