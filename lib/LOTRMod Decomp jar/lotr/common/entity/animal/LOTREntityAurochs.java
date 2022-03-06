// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.animal;

import lotr.common.entity.LOTREntities;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.entity.EntityAgeable;
import lotr.common.LOTRMod;
import net.minecraft.init.Items;
import java.util.List;
import net.minecraft.util.MathHelper;
import net.minecraft.util.DamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.SharedMonsterAttributes;
import java.util.UUID;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.LOTREntityUtils;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.world.World;
import net.minecraft.entity.ai.EntityAIBase;
import lotr.common.entity.LOTRRandomSkinEntity;
import net.minecraft.entity.passive.EntityCow;

public class LOTREntityAurochs extends EntityCow implements LOTRRandomSkinEntity
{
    private EntityAIBase attackAI;
    private EntityAIBase panicAI;
    private boolean prevIsChild;
    protected final float aurochsWidth;
    protected final float aurochsHeight;
    
    public LOTREntityAurochs(final World world) {
        super(world);
        this.prevIsChild = true;
        this.aurochsWidth = 1.5f;
        this.aurochsHeight = 1.7f;
        this.setSize(this.aurochsWidth, this.aurochsHeight);
        final EntityAITasks.EntityAITaskEntry panic = LOTREntityUtils.removeAITask((EntityCreature)this, EntityAIPanic.class);
        ((EntityLiving)this).tasks.addTask(panic.priority, panic.action);
        this.panicAI = panic.action;
        this.attackAI = this.createAurochsAttackAI();
        ((EntityLiving)this).targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false));
    }
    
    protected EntityAIBase createAurochsAttackAI() {
        return new LOTREntityAIAttackOnCollide((EntityCreature)this, 1.7, true);
    }
    
    public void setUniqueID(final UUID uuid) {
        ((Entity)this).entityUniqueID = uuid;
    }
    
    protected void entityInit() {
        super.entityInit();
        ((Entity)this).dataWatcher.addObject(20, (Object)0);
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0);
    }
    
    public boolean isAurochsEnraged() {
        return ((Entity)this).dataWatcher.getWatchableObjectByte(20) == 1;
    }
    
    public void setAurochsEnraged(final boolean flag) {
        ((Entity)this).dataWatcher.updateObject(20, (Object)(byte)(flag ? 1 : 0));
    }
    
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!((Entity)this).worldObj.isClient) {
            final boolean isChild = this.isChild();
            if (isChild != this.prevIsChild) {
                if (isChild) {
                    final EntityAITasks.EntityAITaskEntry taskEntry = LOTREntityUtils.removeAITask((EntityCreature)this, this.attackAI.getClass());
                    ((EntityLiving)this).tasks.addTask(taskEntry.priority, this.panicAI);
                }
                else {
                    final EntityAITasks.EntityAITaskEntry taskEntry = LOTREntityUtils.removeAITask((EntityCreature)this, this.panicAI.getClass());
                    ((EntityLiving)this).tasks.addTask(taskEntry.priority, this.attackAI);
                }
            }
            if (this.getAttackTarget() != null) {
                final EntityLivingBase target = this.getAttackTarget();
                if (!target.isEntityAlive() || (target instanceof EntityPlayer && ((EntityPlayer)target).capabilities.isCreativeMode)) {
                    this.setAttackTarget((EntityLivingBase)null);
                }
            }
            if (((Entity)this).riddenByEntity instanceof EntityLiving) {
                final EntityLivingBase target = ((EntityLiving)((Entity)this).riddenByEntity).getAttackTarget();
                this.setAttackTarget(target);
            }
            else if (((Entity)this).riddenByEntity instanceof EntityPlayer) {
                this.setAttackTarget((EntityLivingBase)null);
            }
            this.setAurochsEnraged(this.getAttackTarget() != null);
        }
        this.prevIsChild = this.isChild();
    }
    
    public boolean interact(final EntityPlayer entityplayer) {
        return !this.isAurochsEnraged() && super.interact(entityplayer);
    }
    
    public boolean attackEntityAsMob(final Entity entity) {
        final float f = (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
        final boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), f);
        if (flag) {
            final float kb = 0.75f;
            entity.addVelocity((double)(-MathHelper.sin(((Entity)this).rotationYaw * 3.1415927f / 180.0f) * kb * 0.5f), 0.0, (double)(MathHelper.cos(((Entity)this).rotationYaw * 3.1415927f / 180.0f) * kb * 0.5f));
        }
        return flag;
    }
    
    public boolean attackEntityFrom(final DamageSource damagesource, final float f) {
        final boolean flag = super.attackEntityFrom(damagesource, f);
        if (flag && this.isChild()) {
            final Entity attacker = damagesource.getEntity();
            if (attacker instanceof EntityLivingBase) {
                final List list = ((Entity)this).worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, ((Entity)this).boundingBox.expand(12.0, 12.0, 12.0));
                for (int i = 0; i < list.size(); ++i) {
                    final Entity entity = list.get(i);
                    if (entity.getClass() == this.getClass()) {
                        final LOTREntityAurochs aurochs = (LOTREntityAurochs)entity;
                        if (!aurochs.isChild()) {
                            aurochs.setAttackTarget((EntityLivingBase)attacker);
                        }
                    }
                }
            }
        }
        return flag;
    }
    
    protected void dropFewItems(final boolean flag, final int i) {
        for (int hides = 2 + ((Entity)this).rand.nextInt(3) + ((Entity)this).rand.nextInt(1 + i), l = 0; l < hides; ++l) {
            this.func_145779_a(Items.leather, 1);
        }
        for (int meats = 2 + ((Entity)this).rand.nextInt(3) + ((Entity)this).rand.nextInt(1 + i), j = 0; j < meats; ++j) {
            if (this.isBurning()) {
                this.func_145779_a(Items.cooked_beef, 1);
            }
            else {
                this.func_145779_a(Items.beef, 1);
            }
        }
        this.dropHornItem(flag, i);
    }
    
    protected void dropHornItem(final boolean flag, final int i) {
        this.func_145779_a(LOTRMod.horn, 1);
    }
    
    public EntityCow createChild(final EntityAgeable entity) {
        return new LOTREntityAurochs(((Entity)this).worldObj);
    }
    
    protected String getLivingSound() {
        return "lotr:aurochs.say";
    }
    
    protected String getHurtSound() {
        return "lotr:aurochs.hurt";
    }
    
    protected String getDeathSound() {
        return "lotr:aurochs.hurt";
    }
    
    protected float getSoundVolume() {
        return 1.0f;
    }
    
    protected float getSoundPitch() {
        return super.getSoundPitch() * 0.75f;
    }
    
    public int getTalkInterval() {
        return 200;
    }
    
    public ItemStack getPickedResult(final MovingObjectPosition target) {
        return new ItemStack(LOTRMod.spawnEgg, 1, LOTREntities.getEntityID((Entity)this));
    }
}
