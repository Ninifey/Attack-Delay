// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.animal;

import java.util.List;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.DamageSource;
import lotr.common.LOTRMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import lotr.common.LOTRReflection;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;

public class LOTREntityRhino extends LOTREntityHorse
{
    public LOTREntityRhino(final World world) {
        super(world);
        this.setSize(1.7f, 1.9f);
    }
    
    @Override
    protected boolean isMountHostile() {
        return true;
    }
    
    @Override
    protected EntityAIBase createMountAttackAI() {
        return new LOTREntityAIAttackOnCollide((EntityCreature)this, 1.0, true);
    }
    
    public int getHorseType() {
        return 0;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0);
    }
    
    @Override
    protected void onLOTRHorseSpawn() {
        double maxHealth = this.getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue();
        maxHealth *= 1.5;
        maxHealth = Math.max(maxHealth, 40.0);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(maxHealth);
        double speed = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
        speed *= 1.2;
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(speed);
        double jumpStrength = this.getEntityAttribute(LOTRReflection.getHorseJumpStrength()).getAttributeValue();
        jumpStrength *= 0.5;
        this.getEntityAttribute(LOTRReflection.getHorseJumpStrength()).setBaseValue(jumpStrength);
    }
    
    @Override
    protected double clampChildHealth(final double health) {
        return MathHelper.clamp_double(health, 20.0, 50.0);
    }
    
    @Override
    protected double clampChildJump(final double jump) {
        return MathHelper.clamp_double(jump, 0.2, 0.8);
    }
    
    @Override
    protected double clampChildSpeed(final double speed) {
        return MathHelper.clamp_double(speed, 0.12, 0.42);
    }
    
    @Override
    public boolean isBreedingItem(final ItemStack itemstack) {
        return itemstack != null && itemstack.getItem() == Items.wheat;
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!((Entity)this).worldObj.isClient) {
            if (((Entity)this).riddenByEntity instanceof EntityLivingBase) {
                final EntityLivingBase rhinoRider = (EntityLivingBase)((Entity)this).riddenByEntity;
                final float momentum = MathHelper.sqrt_double(((Entity)this).motionX * ((Entity)this).motionX + ((Entity)this).motionZ * ((Entity)this).motionZ);
                if (momentum > 0.2f) {
                    this.setSprinting(true);
                }
                else {
                    this.setSprinting(false);
                }
                if (momentum >= 0.32f) {
                    final float strength = momentum * 15.0f;
                    final Vec3 position = Vec3.createVectorHelper(((Entity)this).posX, ((Entity)this).posY, ((Entity)this).posZ);
                    final Vec3 look = this.getLookVec();
                    final float sightWidth = 1.0f;
                    final double range = 0.5;
                    final List list = ((Entity)this).worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, ((Entity)this).boundingBox.contract(1.0, 1.0, 1.0).addCoord(look.xCoord * range, look.yCoord * range, look.zCoord * range).expand((double)sightWidth, (double)sightWidth, (double)sightWidth));
                    boolean hitAnyEntities = false;
                    for (int i = 0; i < list.size(); ++i) {
                        final Entity obj = list.get(i);
                        if (obj instanceof EntityLivingBase) {
                            final EntityLivingBase entity = (EntityLivingBase)obj;
                            if (entity != rhinoRider) {
                                if (!(rhinoRider instanceof EntityPlayer) || LOTRMod.canPlayerAttackEntity((EntityPlayer)rhinoRider, entity, false)) {
                                    if (!(rhinoRider instanceof EntityCreature) || LOTRMod.canNPCAttackEntity((EntityCreature)rhinoRider, entity, false)) {
                                        final boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), strength);
                                        if (flag) {
                                            final float knockback = strength * 0.05f;
                                            entity.addVelocity((double)(-MathHelper.sin(((Entity)this).rotationYaw * 3.1415927f / 180.0f) * knockback), (double)knockback, (double)(MathHelper.cos(((Entity)this).rotationYaw * 3.1415927f / 180.0f) * knockback));
                                            hitAnyEntities = true;
                                            if (entity instanceof EntityLiving) {
                                                final EntityLiving entityliving = (EntityLiving)entity;
                                                if (entityliving.getAttackTarget() == this) {
                                                    entityliving.getNavigator().clearPathEntity();
                                                    entityliving.setAttackTarget(rhinoRider);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (hitAnyEntities) {
                        ((Entity)this).worldObj.playSoundAtEntity((Entity)this, "lotr:troll.ologHai_hammer", 1.0f, (((Entity)this).rand.nextFloat() - ((Entity)this).rand.nextFloat()) * 0.2f + 1.0f);
                    }
                }
            }
            else if (this.getAttackTarget() != null) {
                final float momentum2 = MathHelper.sqrt_double(((Entity)this).motionX * ((Entity)this).motionX + ((Entity)this).motionZ * ((Entity)this).motionZ);
                if (momentum2 > 0.2f) {
                    this.setSprinting(true);
                }
                else {
                    this.setSprinting(false);
                }
            }
            else {
                this.setSprinting(false);
            }
        }
    }
    
    protected void dropFewItems(final boolean flag, final int i) {
        for (int j = ((Entity)this).rand.nextInt(2) + ((Entity)this).rand.nextInt(1 + i), k = 0; k < j; ++k) {
            this.func_145779_a(LOTRMod.rhinoHorn, 1);
        }
        for (int meat = ((Entity)this).rand.nextInt(3) + ((Entity)this).rand.nextInt(1 + i), l = 0; l < meat; ++l) {
            if (this.isBurning()) {
                this.func_145779_a(LOTRMod.rhinoCooked, 1);
            }
            else {
                this.func_145779_a(LOTRMod.rhinoRaw, 1);
            }
        }
    }
    
    protected String getLivingSound() {
        return "lotr:rhino.say";
    }
    
    protected String getHurtSound() {
        return "lotr:rhino.hurt";
    }
    
    protected String getDeathSound() {
        return "lotr:rhino.death";
    }
    
    protected String getAngrySoundName() {
        return "lotr:rhino.say";
    }
}
