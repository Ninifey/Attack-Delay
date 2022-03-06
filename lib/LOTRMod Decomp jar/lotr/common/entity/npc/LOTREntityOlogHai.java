// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import java.util.List;
import net.minecraft.util.DamageSource;
import lotr.common.LOTRMod;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.Entity;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.SharedMonsterAttributes;
import lotr.common.entity.ai.LOTREntityAINearestAttackableTargetBasic;
import lotr.common.entity.ai.LOTREntityAINearestAttackableTargetOrc;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.ai.EntityAIWander;
import lotr.common.entity.ai.LOTREntityAIFollowHiringPlayer;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.ai.LOTREntityAIHiredRemainStill;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.world.World;

public class LOTREntityOlogHai extends LOTREntityTroll
{
    public LOTREntityOlogHai(final World world) {
        super(world);
        ((EntityLiving)this).tasks.taskEntries.clear();
        ((EntityLiving)this).targetTasks.taskEntries.clear();
        ((EntityLiving)this).tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        ((EntityLiving)this).tasks.addTask(1, (EntityAIBase)new LOTREntityAIHiredRemainStill(this));
        ((EntityLiving)this).tasks.addTask(2, (EntityAIBase)new LOTREntityAIAttackOnCollide(this, 2.0, false));
        ((EntityLiving)this).tasks.addTask(3, (EntityAIBase)new LOTREntityAIFollowHiringPlayer(this));
        ((EntityLiving)this).tasks.addTask(4, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0));
        ((EntityLiving)this).tasks.addTask(5, (EntityAIBase)new EntityAIWatchClosest2((EntityLiving)this, (Class)EntityPlayer.class, 12.0f, 0.02f));
        ((EntityLiving)this).tasks.addTask(5, (EntityAIBase)new EntityAIWatchClosest2((EntityLiving)this, (Class)LOTREntityNPC.class, 8.0f, 0.02f));
        ((EntityLiving)this).tasks.addTask(6, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityLiving.class, 12.0f, 0.01f));
        ((EntityLiving)this).tasks.addTask(7, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.addTargetTasks(true, LOTREntityAINearestAttackableTargetOrc.class);
        super.trollImmuneToSun = true;
    }
    
    @Override
    public float getTrollScale() {
        return 1.25f;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(80.0);
        this.getEntityAttribute(LOTREntityNPC.npcAttackDamage).setBaseValue(7.0);
    }
    
    @Override
    public int getTotalArmorValue() {
        return 15;
    }
    
    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.MORDOR;
    }
    
    @Override
    protected boolean hasTrollName() {
        return false;
    }
    
    @Override
    protected boolean canTrollBeTickled(final EntityPlayer entityplayer) {
        return false;
    }
    
    public void onUpdate() {
        super.onUpdate();
    }
    
    @Override
    public boolean attackEntityAsMob(final Entity entity) {
        if (super.attackEntityAsMob(entity)) {
            final float attackDamage = (float)this.getEntityAttribute(LOTREntityNPC.npcAttackDamage).getAttributeValue();
            final float knockbackModifier = 0.25f * attackDamage;
            entity.addVelocity((double)(-MathHelper.sin(((Entity)this).rotationYaw * 3.1415927f / 180.0f) * knockbackModifier * 0.5f), 0.0, (double)(MathHelper.cos(((Entity)this).rotationYaw * 3.1415927f / 180.0f) * knockbackModifier * 0.5f));
            ((Entity)this).worldObj.playSoundAtEntity(entity, "lotr:troll.ologHai_hammer", 1.0f, (((Entity)this).rand.nextFloat() - ((Entity)this).rand.nextFloat()) * 0.2f + 1.0f);
            if (!((Entity)this).worldObj.isClient) {
                final List entities = ((Entity)this).worldObj.getEntitiesWithinAABB((Class)EntityLivingBase.class, entity.boundingBox.expand(4.0, 4.0, 4.0));
                if (!entities.isEmpty()) {
                    for (int i = 0; i < entities.size(); ++i) {
                        final EntityLivingBase hitEntity = entities.get(i);
                        if (hitEntity != this && hitEntity != entity) {
                            if (LOTRMod.canNPCAttackEntity(this, hitEntity, false)) {
                                float strength = 4.0f - entity.getDistanceToEntity((Entity)hitEntity);
                                ++strength;
                                if (strength > 4.0f) {
                                    strength = 4.0f;
                                }
                                if (hitEntity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), strength / 4.0f * attackDamage)) {
                                    float knockback = strength * 0.25f;
                                    if (knockback < 0.75f) {
                                        knockback = 0.75f;
                                    }
                                    hitEntity.addVelocity((double)(-MathHelper.sin(((Entity)this).rotationYaw * 3.1415927f / 180.0f) * knockback * 0.5f), 0.2 + 0.12 * knockback, (double)(MathHelper.cos(((Entity)this).rotationYaw * 3.1415927f / 180.0f) * knockback * 0.5f));
                                }
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killOlogHai;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 4.0f;
    }
    
    @Override
    public void dropTrollItems(final boolean flag, final int i) {
        if (flag) {
            int rareDropChance = 8 - i;
            if (rareDropChance < 1) {
                rareDropChance = 1;
            }
            if (((Entity)this).rand.nextInt(rareDropChance) == 0) {
                for (int drops = 1 + ((Entity)this).rand.nextInt(2) + ((Entity)this).rand.nextInt(i + 1), j = 0; j < drops; ++j) {
                    this.func_145779_a(LOTRMod.orcSteel, 1);
                }
            }
        }
    }
    
    @Override
    protected int getExperiencePoints(final EntityPlayer entityplayer) {
        return 5 + ((Entity)this).rand.nextInt(8);
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        return null;
    }
}
