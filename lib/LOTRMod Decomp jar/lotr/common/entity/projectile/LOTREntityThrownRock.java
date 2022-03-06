// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.projectile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import net.minecraft.item.Item;
import net.minecraft.init.Blocks;
import net.minecraft.entity.IEntityLivingData;
import lotr.common.entity.npc.LOTREntityMountainTroll;
import lotr.common.entity.npc.LOTREntityTroll;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraft.entity.projectile.EntityThrowable;

public class LOTREntityThrownRock extends EntityThrowable
{
    private int rockRotation;
    private float damage;
    
    public LOTREntityThrownRock(final World world) {
        super(world);
        this.setSize(4.0f, 4.0f);
    }
    
    public LOTREntityThrownRock(final World world, final EntityLivingBase entityliving) {
        super(world, entityliving);
        this.setSize(4.0f, 4.0f);
    }
    
    public LOTREntityThrownRock(final World world, final double d, final double d1, final double d2) {
        super(world, d, d1, d2);
        this.setSize(4.0f, 4.0f);
    }
    
    public void setDamage(final float f) {
        this.damage = f;
    }
    
    public void entityInit() {
        super.entityInit();
        ((Entity)this).dataWatcher.addObject(16, (Object)0);
    }
    
    public boolean getSpawnsTroll() {
        return ((Entity)this).dataWatcher.getWatchableObjectByte(16) == 1;
    }
    
    public void setSpawnsTroll(final boolean flag) {
        ((Entity)this).dataWatcher.updateObject(16, (Object)(byte)(flag ? 1 : 0));
    }
    
    public void onUpdate() {
        super.onUpdate();
        if (!super.inGround) {
            ++this.rockRotation;
            if (this.rockRotation > 60) {
                this.rockRotation = 0;
            }
            ((Entity)this).rotationPitch = this.rockRotation / 60.0f * 360.0f;
            while (((Entity)this).rotationPitch - ((Entity)this).prevRotationPitch < -180.0f) {
                ((Entity)this).prevRotationPitch -= 360.0f;
            }
            while (((Entity)this).rotationPitch - ((Entity)this).prevRotationPitch >= 180.0f) {
                ((Entity)this).prevRotationPitch += 360.0f;
            }
        }
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setFloat("RockDamage", this.damage);
        nbt.setBoolean("Troll", this.getSpawnsTroll());
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.setDamage(nbt.getFloat("RockDamage"));
        this.setSpawnsTroll(nbt.getBoolean("Troll"));
    }
    
    protected void onImpact(final MovingObjectPosition m) {
        if (!((Entity)this).worldObj.isClient) {
            boolean flag = false;
            if (m.entityHit != null && m.entityHit.attackEntityFrom(DamageSource.causeThrownDamage((Entity)this, (Entity)this.getThrower()), this.damage)) {
                flag = true;
            }
            if (m.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                flag = true;
            }
            if (flag) {
                if (this.getSpawnsTroll()) {
                    LOTREntityTroll troll = new LOTREntityTroll(((Entity)this).worldObj);
                    if (((Entity)this).rand.nextInt(3) == 0) {
                        troll = new LOTREntityMountainTroll(((Entity)this).worldObj);
                    }
                    troll.setLocationAndAngles(((Entity)this).posX, ((Entity)this).posY, ((Entity)this).posZ, ((Entity)this).rand.nextFloat() * 360.0f, 0.0f);
                    troll.onSpawnWithEgg(null);
                    ((Entity)this).worldObj.spawnEntityInWorld((Entity)troll);
                }
                ((Entity)this).worldObj.setEntityState((Entity)this, (byte)15);
                for (int drops = 1 + ((Entity)this).rand.nextInt(3), l = 0; l < drops; ++l) {
                    this.func_145779_a(Item.getItemFromBlock(Blocks.cobblestone), 1);
                }
                this.playSound("lotr:troll.rockSmash", 2.0f, (1.0f + (((Entity)this).worldObj.rand.nextFloat() - ((Entity)this).worldObj.rand.nextFloat()) * 0.2f) * 0.7f);
                this.setDead();
            }
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void handleHealthUpdate(final byte b) {
        if (b == 15) {
            for (int l = 0; l < 32; ++l) {
                LOTRMod.proxy.spawnParticle("largeStone", ((Entity)this).posX + ((Entity)this).rand.nextGaussian() * ((Entity)this).width, ((Entity)this).posY + ((Entity)this).rand.nextDouble() * ((Entity)this).height, ((Entity)this).posZ + ((Entity)this).rand.nextGaussian() * ((Entity)this).width, 0.0, 0.0, 0.0);
            }
        }
        else {
            super.handleHealthUpdate(b);
        }
    }
    
    protected float func_70182_d() {
        return 0.75f;
    }
    
    protected float getGravityVelocity() {
        return 0.1f;
    }
}
