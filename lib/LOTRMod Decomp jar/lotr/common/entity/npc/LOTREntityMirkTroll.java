// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import lotr.common.LOTRMod;
import lotr.common.LOTRAchievement;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.Potion;
import net.minecraft.entity.EntityLivingBase;
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

public class LOTREntityMirkTroll extends LOTREntityTroll
{
    public LOTREntityMirkTroll(final World world) {
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
        return 1.2f;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(70.0);
        this.getEntityAttribute(LOTREntityNPC.npcAttackDamage).setBaseValue(6.0);
    }
    
    @Override
    public int getTotalArmorValue() {
        return 12;
    }
    
    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.DOL_GULDUR;
    }
    
    @Override
    protected boolean hasTrollName() {
        return false;
    }
    
    @Override
    protected boolean canTrollBeTickled(final EntityPlayer entityplayer) {
        return false;
    }
    
    @Override
    public boolean attackEntityAsMob(final Entity entity) {
        if (super.attackEntityAsMob(entity)) {
            if (entity instanceof EntityLivingBase) {
                final int difficulty = ((Entity)this).worldObj.difficultySetting.getDifficultyId();
                final int duration = difficulty * 3 - 1;
                if (duration > 0) {
                    ((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.poison.id, duration * 20, 0));
                }
            }
            return true;
        }
        return false;
    }
    
    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killMirkTroll;
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
        return 4 + ((Entity)this).rand.nextInt(7);
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        return null;
    }
}
