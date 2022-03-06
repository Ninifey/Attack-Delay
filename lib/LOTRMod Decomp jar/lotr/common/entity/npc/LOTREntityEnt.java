// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.item.ItemStack;
import lotr.common.item.LOTRItemEntDraught;
import lotr.common.LOTRAchievement;
import net.minecraft.block.Block;
import lotr.common.block.LOTRBlockCorruptMallorn;
import net.minecraft.util.DamageSource;
import net.minecraft.entity.Entity;
import lotr.common.LOTRMod;
import net.minecraft.util.MathHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.ai.LOTREntityAIEntHealSapling;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.world.World;
import net.minecraft.util.ChunkCoordinates;
import java.util.Random;

public class LOTREntityEnt extends LOTREntityTree
{
    private Random branchRand;
    public int eyesClosed;
    public ChunkCoordinates saplingHealTarget;
    public boolean canHealSapling;
    
    public LOTREntityEnt(final World world) {
        super(world);
        this.branchRand = new Random();
        this.canHealSapling = true;
        this.setSize(1.4f, 4.6f);
        this.getNavigator().setAvoidsWater(true);
        ((EntityLiving)this).tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        ((EntityLiving)this).tasks.addTask(1, (EntityAIBase)new LOTREntityAIEntHealSapling(this, 1.5));
        ((EntityLiving)this).tasks.addTask(1, (EntityAIBase)new LOTREntityAIAttackOnCollide(this, 2.0, false));
        ((EntityLiving)this).tasks.addTask(2, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0));
        ((EntityLiving)this).tasks.addTask(3, (EntityAIBase)new EntityAIWatchClosest2((EntityLiving)this, (Class)EntityPlayer.class, 12.0f, 0.02f));
        ((EntityLiving)this).tasks.addTask(3, (EntityAIBase)new EntityAIWatchClosest2((EntityLiving)this, (Class)LOTREntityNPC.class, 8.0f, 0.02f));
        ((EntityLiving)this).tasks.addTask(4, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityLiving.class, 10.0f, 0.02f));
        ((EntityLiving)this).tasks.addTask(5, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.addTargetTasks(true);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        ((Entity)this).dataWatcher.addObject(18, (Object)0);
    }
    
    public boolean isHealingSapling() {
        return ((Entity)this).dataWatcher.getWatchableObjectByte(18) == 1;
    }
    
    public void setHealingSapling(final boolean flag) {
        ((Entity)this).dataWatcher.updateObject(18, (Object)(byte)(flag ? 1 : 0));
    }
    
    @Override
    public void setupNPCName() {
        super.familyInfo.setName(LOTRNames.getEntName(((Entity)this).rand));
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
        this.getEntityAttribute(LOTREntityNPC.npcAttackDamage).setBaseValue(7.0);
    }
    
    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.FANGORN;
    }
    
    @Override
    public void setAttackTarget(final EntityLivingBase target) {
        super.setAttackTarget(target);
        if (this.getAttackTarget() == null) {
            this.canHealSapling = true;
        }
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        if (this.saplingHealTarget != null) {
            nbt.setInteger("SaplingHealX", this.saplingHealTarget.posX);
            nbt.setInteger("SaplingHealY", this.saplingHealTarget.posY);
            nbt.setInteger("SaplingHealZ", this.saplingHealTarget.posZ);
        }
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        if (nbt.hasKey("EntName")) {
            super.familyInfo.setName(nbt.getString("EntName"));
        }
        if (nbt.hasKey("SaplingHealX")) {
            final int x = nbt.getInteger("SaplingHealX");
            final int y = nbt.getInteger("SaplingHealY");
            final int z = nbt.getInteger("SaplingHealZ");
            this.saplingHealTarget = new ChunkCoordinates(x, y, z);
        }
    }
    
    @Override
    public String getNPCName() {
        return super.familyInfo.getName();
    }
    
    public int getExtraHeadBranches() {
        long l = this.getUniqueID().getLeastSignificantBits();
        l ^= (l * 365620672396L ^ l * 12784892284L);
        l = l * l * 18569660L + l * 6639092L;
        this.branchRand.setSeed(l);
        if (this.branchRand.nextBoolean()) {
            return 0;
        }
        return MathHelper.getRandomIntegerInRange(this.branchRand, 2, 5);
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (((Entity)this).worldObj.isClient) {
            if (this.eyesClosed > 0) {
                --this.eyesClosed;
            }
            else if (((Entity)this).rand.nextInt(400) == 0) {
                this.eyesClosed = 30;
            }
            if (this.isHealingSapling()) {
                for (int l = 0; l < 2; ++l) {
                    float angle = ((EntityLivingBase)this).rotationYawHead + 90.0f + MathHelper.randomFloatClamp(((Entity)this).rand, -40.0f, 40.0f);
                    angle = (float)Math.toRadians(angle);
                    final double d = ((Entity)this).posX + MathHelper.cos(angle) * 1.5;
                    final double d2 = ((Entity)this).boundingBox.minY + ((Entity)this).height * MathHelper.randomFloatClamp(((Entity)this).rand, 0.3f, 0.6f);
                    final double d3 = ((Entity)this).posZ + MathHelper.sin(angle) * 1.5;
                    final double d4 = MathHelper.cos(angle) * 0.06;
                    final double d5 = -0.03;
                    final double d6 = MathHelper.sin(angle) * 0.06;
                    LOTRMod.proxy.spawnParticle("leafGold_30", d, d2, d3, d4, d5, d6);
                }
            }
        }
    }
    
    @Override
    public boolean attackEntityAsMob(final Entity entity) {
        if (super.attackEntityAsMob(entity)) {
            final float knockbackModifier = 1.5f;
            entity.addVelocity((double)(-MathHelper.sin(((Entity)this).rotationYaw * 3.1415927f / 180.0f) * knockbackModifier * 0.5f), 0.15, (double)(MathHelper.cos(((Entity)this).rotationYaw * 3.1415927f / 180.0f) * knockbackModifier * 0.5f));
            return true;
        }
        return false;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damagesource, final float f) {
        final boolean flag = super.attackEntityFrom(damagesource, f);
        if (!((Entity)this).worldObj.isClient && flag) {
            if (damagesource.getEntity() != null) {
                this.setHealingSapling(false);
            }
            if (this.getAttackTarget() != null) {
                this.canHealSapling = false;
            }
        }
        return flag;
    }
    
    @Override
    public void onDeath(final DamageSource damagesource) {
        super.onDeath(damagesource);
        if (!((Entity)this).worldObj.isClient && damagesource.getEntity() instanceof EntityPlayer && this.saplingHealTarget != null) {
            final int i = this.saplingHealTarget.posX;
            final int j = this.saplingHealTarget.posY;
            final int k = this.saplingHealTarget.posZ;
            final Block block = ((Entity)this).worldObj.getBlock(i, j, k);
            int meta = ((Entity)this).worldObj.getBlockMetadata(i, j, k);
            if (block == LOTRMod.corruptMallorn) {
                if (++meta >= LOTRBlockCorruptMallorn.ENT_KILLS) {
                    LOTRBlockCorruptMallorn.summonEntBoss(((Entity)this).worldObj, i, j, k);
                }
                else {
                    ((Entity)this).worldObj.setBlockMetadata(i, j, k, meta, 3);
                }
            }
        }
    }
    
    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killEnt;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 3.0f;
    }
    
    @Override
    protected void dropFewItems(final boolean flag, final int i) {
        super.dropFewItems(flag, i);
        if (flag) {
            int dropChance = 10 - i * 2;
            if (dropChance < 1) {
                dropChance = 1;
            }
            if (((Entity)this).rand.nextInt(dropChance) == 0) {
                this.entityDropItem(new ItemStack(LOTRMod.entDraught, 1, ((Entity)this).rand.nextInt(LOTRItemEntDraught.draughtTypes.length)), 0.0f);
            }
        }
    }
    
    @Override
    protected int getExperiencePoints(final EntityPlayer entityplayer) {
        return 5 + ((Entity)this).rand.nextInt(6);
    }
    
    protected float getSoundVolume() {
        return 1.5f;
    }
    
    protected void func_145780_a(final int i, final int j, final int k, final Block block) {
        this.playSound("lotr:ent.step", 0.75f, this.getSoundPitch());
    }
    
    @Override
    protected LOTRAchievement getTalkAchievement() {
        return LOTRAchievement.talkEnt;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (this.isFriendly(entityplayer)) {
            return "ent/ent/friendly";
        }
        return "ent/ent/hostile";
    }
}
