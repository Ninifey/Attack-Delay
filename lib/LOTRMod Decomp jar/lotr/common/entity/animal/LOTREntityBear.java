// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.animal;

import lotr.common.entity.LOTREntities;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.util.MathHelper;
import lotr.common.world.LOTRWorldChunkManager;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.nbt.NBTTagCompound;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.util.DamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.init.Items;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.world.World;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityAnimal;

public class LOTREntityBear extends EntityAnimal implements LOTRAnimalSpawnConditions
{
    private EntityAIBase attackAI;
    private EntityAIBase panicAI;
    private EntityAIBase targetNearAI;
    private int hostileTick;
    private boolean prevIsChild;
    
    public LOTREntityBear(final World world) {
        super(world);
        this.attackAI = new LOTREntityAIAttackOnCollide((EntityCreature)this, 1.7, false);
        this.panicAI = (EntityAIBase)new EntityAIPanic((EntityCreature)this, 1.5);
        this.targetNearAI = (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, (Class)EntityPlayer.class, 0, true);
        this.hostileTick = 0;
        this.prevIsChild = true;
        this.setSize(1.6f, 1.8f);
        this.getNavigator().setAvoidsWater(true);
        ((EntityLiving)this).tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        ((EntityLiving)this).tasks.addTask(2, this.panicAI);
        ((EntityLiving)this).tasks.addTask(3, (EntityAIBase)new EntityAIMate((EntityAnimal)this, 1.0));
        ((EntityLiving)this).tasks.addTask(4, (EntityAIBase)new EntityAITempt((EntityCreature)this, 1.4, Items.fish, false));
        ((EntityLiving)this).tasks.addTask(5, (EntityAIBase)new EntityAIFollowParent((EntityAnimal)this, 1.4));
        ((EntityLiving)this).tasks.addTask(6, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0));
        ((EntityLiving)this).tasks.addTask(7, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityPlayer.class, 8.0f));
        ((EntityLiving)this).tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        ((EntityLiving)this).targetTasks.addTask(0, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false));
        ((EntityLiving)this).targetTasks.addTask(1, this.targetNearAI);
    }
    
    public void entityInit() {
        super.entityInit();
        ((Entity)this).dataWatcher.addObject(18, (Object)0);
        ((Entity)this).dataWatcher.addObject(20, (Object)0);
        this.setBearType(BearType.forID(((Entity)this).rand.nextInt(BearType.values().length)));
    }
    
    public BearType getBearType() {
        final int i = ((Entity)this).dataWatcher.getWatchableObjectByte(18);
        return BearType.forID(i);
    }
    
    public void setBearType(final BearType t) {
        ((Entity)this).dataWatcher.updateObject(18, (Object)(byte)t.bearID);
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
    
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        if (data == null) {
            data = (IEntityLivingData)new BearGroupSpawnData();
            ((BearGroupSpawnData)data).numSpawned = 1;
        }
        else if (data instanceof BearGroupSpawnData) {
            final BearGroupSpawnData bgsd = (BearGroupSpawnData)data;
            if (bgsd.numSpawned >= 1 && ((Entity)this).rand.nextBoolean()) {
                this.setGrowingAge(-24000);
            }
            final BearGroupSpawnData bearGroupSpawnData = bgsd;
            ++bearGroupSpawnData.numSpawned;
        }
        if (((Entity)this).rand.nextInt(10000) == 0) {
            this.setCustomNameTag("Wojtek");
        }
        return data;
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
        for (int furs = 1 + ((Entity)this).rand.nextInt(3) + ((Entity)this).rand.nextInt(i + 1), l = 0; l < furs; ++l) {
            this.func_145779_a(LOTRMod.fur, 1);
        }
        if (flag) {
            int rugChance = 30 - i * 5;
            rugChance = Math.max(rugChance, 1);
            if (((Entity)this).rand.nextInt(rugChance) == 0) {
                this.entityDropItem(new ItemStack(LOTRMod.bearRug, 1, this.getBearType().bearID), 0.0f);
            }
        }
    }
    
    protected int getExperiencePoints(final EntityPlayer entityplayer) {
        return 2 + ((Entity)this).worldObj.rand.nextInt(3);
    }
    
    public boolean attackEntityAsMob(final Entity entity) {
        final float f = (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
        return entity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), f);
    }
    
    public EntityAgeable createChild(final EntityAgeable entity) {
        final LOTREntityBear mate = (LOTREntityBear)entity;
        final LOTREntityBear child = new LOTREntityBear(((Entity)this).worldObj);
        if (((Entity)this).rand.nextBoolean()) {
            child.setBearType(this.getBearType());
        }
        else {
            child.setBearType(mate.getBearType());
        }
        return (EntityAgeable)child;
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
                        if (entity instanceof LOTREntityBear) {
                            final LOTREntityBear bear = (LOTREntityBear)entity;
                            if (bear.isChild()) {
                                continue;
                            }
                            bear.becomeAngryAt((EntityLivingBase)attacker);
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
        nbt.setByte("BearType", (byte)this.getBearType().bearID);
        nbt.setInteger("Angry", this.hostileTick);
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        if (nbt.hasKey("BearType")) {
            this.setBearType(BearType.forID(nbt.getByte("BearType")));
        }
        this.hostileTick = nbt.getInteger("Angry");
    }
    
    public boolean isBreedingItem(final ItemStack itemstack) {
        return itemstack.getItem() == Items.fish;
    }
    
    public boolean interact(final EntityPlayer entityplayer) {
        return !this.isHostile() && super.interact(entityplayer);
    }
    
    public boolean canWorldGenSpawnAt(final int i, final int j, final int k, final LOTRBiome biome, final LOTRBiomeVariant variant) {
        final int trees = biome.decorator.getVariantTreesPerChunk(variant);
        return trees >= 1;
    }
    
    public boolean getCanSpawnHere() {
        final WorldChunkManager worldChunkMgr = ((Entity)this).worldObj.getWorldChunkManager();
        if (worldChunkMgr instanceof LOTRWorldChunkManager) {
            final int i = MathHelper.floor_double(((Entity)this).posX);
            final int j = MathHelper.floor_double(((Entity)this).boundingBox.minY);
            final int k = MathHelper.floor_double(((Entity)this).posZ);
            final LOTRBiome biome = (LOTRBiome)((Entity)this).worldObj.getBiomeGenForCoords(i, k);
            final LOTRBiomeVariant variant = ((LOTRWorldChunkManager)worldChunkMgr).getBiomeVariantAt(i, k);
            return super.getCanSpawnHere() && this.canWorldGenSpawnAt(i, j, k, biome, variant);
        }
        return super.getCanSpawnHere();
    }
    
    protected String getLivingSound() {
        return "lotr:bear.say";
    }
    
    protected String getHurtSound() {
        return "lotr:bear.hurt";
    }
    
    protected String getDeathSound() {
        return "lotr:bear.death";
    }
    
    public int getTalkInterval() {
        return 200;
    }
    
    public ItemStack getPickedResult(final MovingObjectPosition target) {
        return new ItemStack(LOTRMod.spawnEgg, 1, LOTREntities.getEntityID((Entity)this));
    }
    
    private static class BearGroupSpawnData implements IEntityLivingData
    {
        public int numSpawned;
        
        private BearGroupSpawnData() {
            this.numSpawned = 0;
        }
    }
    
    public enum BearType
    {
        LIGHT(0), 
        DARK(1), 
        BLACK(2);
        
        public final int bearID;
        
        private BearType(final int i) {
            this.bearID = i;
        }
        
        public String textureName() {
            return this.name().toLowerCase();
        }
        
        public static BearType forID(final int ID) {
            for (final BearType t : values()) {
                if (t.bearID == ID) {
                    return t;
                }
            }
            return BearType.LIGHT;
        }
        
        public static String[] bearTypeNames() {
            final String[] names = new String[values().length];
            for (int i = 0; i < names.length; ++i) {
                names[i] = values()[i].textureName();
            }
            return names;
        }
    }
}
