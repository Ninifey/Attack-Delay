// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.animal;

import net.minecraft.nbt.NBTTagCompound;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.util.DamageSource;
import net.minecraft.entity.Entity;
import lotr.common.item.LOTRItemLionRug;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWander;
import lotr.common.entity.ai.LOTREntityAILionChase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.init.Items;
import lotr.common.entity.ai.LOTREntityAIMFMate;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.world.World;
import net.minecraft.entity.ai.EntityAIBase;

public abstract class LOTREntityLionBase extends LOTREntityAnimalMF
{
    private EntityAIBase attackAI;
    private EntityAIBase panicAI;
    private EntityAIBase targetNearAI;
    private int hostileTick;
    private boolean prevIsChild;
    
    public LOTREntityLionBase(final World world) {
        super(world);
        this.attackAI = new LOTREntityAIAttackOnCollide((EntityCreature)this, 1.5, false);
        this.panicAI = (EntityAIBase)new EntityAIPanic((EntityCreature)this, 1.5);
        this.targetNearAI = (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, (Class)EntityPlayer.class, 0, true);
        this.hostileTick = 0;
        this.prevIsChild = true;
        this.setSize(1.4f, 1.6f);
        this.getNavigator().setAvoidsWater(true);
        ((EntityLiving)this).tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        ((EntityLiving)this).tasks.addTask(2, this.panicAI);
        ((EntityLiving)this).tasks.addTask(3, (EntityAIBase)new LOTREntityAIMFMate(this, 1.0));
        ((EntityLiving)this).tasks.addTask(4, (EntityAIBase)new EntityAITempt((EntityCreature)this, 1.4, Items.fish, false));
        ((EntityLiving)this).tasks.addTask(5, (EntityAIBase)new EntityAIFollowParent((EntityAnimal)this, 1.4));
        ((EntityLiving)this).tasks.addTask(6, (EntityAIBase)new LOTREntityAILionChase(this, 1.5));
        ((EntityLiving)this).tasks.addTask(7, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0));
        ((EntityLiving)this).tasks.addTask(8, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityPlayer.class, 8.0f));
        ((EntityLiving)this).tasks.addTask(9, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        ((EntityLiving)this).targetTasks.addTask(0, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false));
        ((EntityLiving)this).targetTasks.addTask(1, this.targetNearAI);
    }
    
    @Override
    public Class getAnimalMFBaseClass() {
        return LOTREntityLionBase.class;
    }
    
    public void entityInit() {
        super.entityInit();
        ((Entity)this).dataWatcher.addObject(20, (Object)0);
    }
    
    public boolean isHostile() {
        return ((Entity)this).dataWatcher.getWatchableObjectByte(20) == 1;
    }
    
    public void setHostile(final boolean flag) {
        ((Entity)this).dataWatcher.updateObject(20, (Object)(byte)(flag ? 1 : 0));
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0);
    }
    
    public boolean isAIEnabled() {
        return true;
    }
    
    public void onLivingUpdate() {
        if (!((Entity)this).worldObj.isClient) {
            final boolean isChild = this.isChild();
            if (isChild != this.prevIsChild) {
                if (isChild) {
                    ((EntityLiving)this).tasks.removeTask(this.attackAI);
                    ((EntityLiving)this).tasks.addTask(2, this.panicAI);
                    ((EntityLiving)this).targetTasks.removeTask(this.targetNearAI);
                }
                else {
                    ((EntityLiving)this).tasks.removeTask(this.panicAI);
                    if (this.hostileTick > 0) {
                        ((EntityLiving)this).tasks.addTask(1, this.attackAI);
                        ((EntityLiving)this).targetTasks.addTask(1, this.targetNearAI);
                    }
                    else {
                        ((EntityLiving)this).tasks.removeTask(this.attackAI);
                        ((EntityLiving)this).targetTasks.removeTask(this.targetNearAI);
                    }
                }
            }
        }
        super.onLivingUpdate();
        if (!((Entity)this).worldObj.isClient && this.getAttackTarget() != null) {
            final EntityLivingBase entity = this.getAttackTarget();
            if (!entity.isEntityAlive() || (entity instanceof EntityPlayer && ((EntityPlayer)entity).capabilities.isCreativeMode)) {
                this.setAttackTarget((EntityLivingBase)null);
            }
        }
        if (!((Entity)this).worldObj.isClient) {
            if (this.hostileTick > 0 && this.getAttackTarget() == null) {
                --this.hostileTick;
            }
            this.setHostile(this.hostileTick > 0);
            if (this.isHostile()) {
                this.resetInLove();
            }
        }
    }
    
    protected void dropFewItems(final boolean flag, final int i) {
        for (int furs = 1 + ((Entity)this).rand.nextInt(3) + 1, l = 0; l < furs; ++l) {
            this.func_145779_a(LOTRMod.lionFur, 1);
        }
        for (int meats = ((Entity)this).rand.nextInt(2) + 1 + ((Entity)this).rand.nextInt(1 + i), j = 0; j < meats; ++j) {
            if (this.isBurning()) {
                this.func_145779_a(LOTRMod.lionCooked, 1);
            }
            else {
                this.func_145779_a(LOTRMod.lionRaw, 1);
            }
        }
        if (flag) {
            int rugChance = 30 - i * 5;
            rugChance = Math.max(rugChance, 1);
            if (((Entity)this).rand.nextInt(rugChance) == 0) {
                this.entityDropItem(new ItemStack(LOTRMod.lionRug, 1, this.getLionRugType().lionID), 0.0f);
            }
        }
    }
    
    protected abstract LOTRItemLionRug.LionRugType getLionRugType();
    
    protected int getExperiencePoints(final EntityPlayer entityplayer) {
        return 2 + ((Entity)this).worldObj.rand.nextInt(3);
    }
    
    public boolean attackEntityAsMob(final Entity entity) {
        final float f = (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
        return entity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), f);
    }
    
    public EntityAgeable createChild(final EntityAgeable entity) {
        return (EntityAgeable)(((Entity)this).rand.nextBoolean() ? new LOTREntityLion(((Entity)this).worldObj) : new LOTREntityLioness(((Entity)this).worldObj));
    }
    
    public boolean attackEntityFrom(final DamageSource damagesource, final float f) {
        final boolean flag = super.attackEntityFrom(damagesource, f);
        if (flag) {
            final Entity attacker = damagesource.getEntity();
            if (attacker instanceof EntityLivingBase) {
                if (this.isChild()) {
                    final double range = 12.0;
                    final List list = ((Entity)this).worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, ((Entity)this).boundingBox.expand(range, range, range));
                    for (final Object obj : list) {
                        final Entity entity = (Entity)obj;
                        if (entity instanceof LOTREntityLionBase) {
                            final LOTREntityLionBase lion = (LOTREntityLionBase)entity;
                            if (lion.isChild()) {
                                continue;
                            }
                            lion.becomeAngryAt((EntityLivingBase)attacker);
                        }
                    }
                }
                else {
                    this.becomeAngryAt((EntityLivingBase)attacker);
                }
            }
        }
        return flag;
    }
    
    private void becomeAngryAt(final EntityLivingBase entity) {
        this.setAttackTarget(entity);
        this.hostileTick = 200;
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setInteger("Angry", this.hostileTick);
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.hostileTick = nbt.getInteger("Angry");
    }
    
    public boolean isBreedingItem(final ItemStack itemstack) {
        return itemstack.getItem() == Items.fish;
    }
    
    public boolean interact(final EntityPlayer entityplayer) {
        return !this.isHostile() && super.interact(entityplayer);
    }
    
    public int getTalkInterval() {
        return 300;
    }
    
    protected String getLivingSound() {
        return "lotr:lion.say";
    }
    
    protected String getHurtSound() {
        return "lotr:lion.hurt";
    }
    
    protected String getDeathSound() {
        return "lotr:lion.death";
    }
}
