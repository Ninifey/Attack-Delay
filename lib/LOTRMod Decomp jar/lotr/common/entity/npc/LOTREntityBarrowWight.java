// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Items;
import net.minecraft.entity.EnumCreatureAttribute;
import lotr.common.LOTRAchievement;
import net.minecraft.potion.PotionEffect;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.LOTRMod;
import net.minecraft.util.MathHelper;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.Entity;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIWander;
import lotr.common.entity.ai.LOTREntityAIFollowHiringPlayer;
import lotr.common.entity.ai.LOTREntityAIHiredRemainStill;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.world.World;
import net.minecraft.potion.Potion;

public class LOTREntityBarrowWight extends LOTREntityNPC
{
    private static Potion[] attackEffects;
    
    public LOTREntityBarrowWight(final World world) {
        super(world);
        this.setSize(0.8f, 2.5f);
        this.getNavigator().setAvoidsWater(true);
        ((EntityLiving)this).tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        ((EntityLiving)this).tasks.addTask(1, (EntityAIBase)new LOTREntityAIHiredRemainStill(this));
        ((EntityLiving)this).tasks.addTask(2, this.getWightAttackAI());
        ((EntityLiving)this).tasks.addTask(3, (EntityAIBase)new LOTREntityAIFollowHiringPlayer(this));
        ((EntityLiving)this).tasks.addTask(4, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0));
        ((EntityLiving)this).tasks.addTask(5, (EntityAIBase)new EntityAIWatchClosest2((EntityLiving)this, (Class)EntityPlayer.class, 12.0f, 0.02f));
        ((EntityLiving)this).tasks.addTask(5, (EntityAIBase)new EntityAIWatchClosest2((EntityLiving)this, (Class)LOTREntityNPC.class, 8.0f, 0.02f));
        ((EntityLiving)this).tasks.addTask(6, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityLiving.class, 12.0f, 0.02f));
        ((EntityLiving)this).tasks.addTask(7, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.addTargetTasks(true);
        super.spawnsInDarkness = true;
    }
    
    public EntityAIBase getWightAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.5, false);
    }
    
    public void entityInit() {
        super.entityInit();
        ((Entity)this).dataWatcher.addObject(16, (Object)(-1));
    }
    
    public int getTargetEntityID() {
        return ((Entity)this).dataWatcher.getWatchableObjectInt(16);
    }
    
    public void setTargetEntityID(final Entity entity) {
        ((Entity)this).dataWatcher.updateObject(16, (Object)((entity == null) ? -1 : entity.getEntityId()));
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50.0);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
        this.getEntityAttribute(LOTREntityNPC.npcAttackDamage).setBaseValue(6.0);
    }
    
    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.HOSTILE;
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (((Entity)this).worldObj.isClient) {
            for (int l = 0; l < 1; ++l) {
                final double d = ((Entity)this).posX + ((Entity)this).width * MathHelper.getRandomDoubleInRange(((Entity)this).rand, -0.5, 0.5);
                final double d2 = ((Entity)this).posY + ((Entity)this).height * MathHelper.getRandomDoubleInRange(((Entity)this).rand, 0.4, 0.8);
                final double d3 = ((Entity)this).posZ + ((Entity)this).width * MathHelper.getRandomDoubleInRange(((Entity)this).rand, -0.5, 0.5);
                final double d4 = MathHelper.getRandomDoubleInRange(((Entity)this).rand, -0.1, 0.1);
                final double d5 = MathHelper.getRandomDoubleInRange(((Entity)this).rand, -0.2, -0.05);
                final double d6 = MathHelper.getRandomDoubleInRange(((Entity)this).rand, -0.1, 0.1);
                if (((Entity)this).rand.nextBoolean()) {
                    LOTRMod.proxy.spawnParticle("morgulPortal", d, d2, d3, d4, d5, d6);
                }
                else {
                    ((Entity)this).worldObj.spawnParticle("smoke", d, d2, d3, d4, d5, d6);
                }
            }
        }
    }
    
    @Override
    public void setAttackTarget(final EntityLivingBase target) {
        super.setAttackTarget(target);
        if (!((Entity)this).worldObj.isClient) {
            this.setTargetEntityID((Entity)target);
        }
    }
    
    @Override
    public boolean attackEntityAsMob(final Entity entity) {
        if (super.attackEntityAsMob(entity)) {
            if (entity instanceof EntityLivingBase) {
                final int difficulty = ((Entity)this).worldObj.difficultySetting.getDifficultyId();
                final int duration = difficulty * (difficulty + 5) / 2;
                if (duration > 0) {
                    for (final Potion effect : LOTREntityBarrowWight.attackEffects) {
                        ((EntityLivingBase)entity).addPotionEffect(new PotionEffect(effect.id, duration * 20, 0));
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killBarrowWight;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 0.0f;
    }
    
    @Override
    protected int getExperiencePoints(final EntityPlayer entityplayer) {
        return 4 + ((Entity)this).rand.nextInt(5);
    }
    
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEAD;
    }
    
    @Override
    protected void dropFewItems(final boolean flag, final int i) {
        super.dropFewItems(flag, i);
        for (int bones = 1 + ((Entity)this).rand.nextInt(3) + ((Entity)this).rand.nextInt(i + 1), l = 0; l < bones; ++l) {
            this.func_145779_a(Items.bone, 1);
        }
        if (((Entity)this).rand.nextBoolean()) {
            this.dropChestContents(LOTRChestContents.BARROW_DOWNS, 1, 2 + i + 1);
        }
    }
    
    @Override
    public boolean canDropRares() {
        return true;
    }
    
    protected String getHurtSound() {
        return "lotr:wight.hurt";
    }
    
    protected String getDeathSound() {
        return "lotr:wight.death";
    }
    
    protected void func_145780_a(final int i, final int j, final int k, final Block block) {
    }
    
    @Override
    public boolean getCanSpawnHere() {
        if (!super.getCanSpawnHere()) {
            return false;
        }
        if (super.liftSpawnRestrictions) {
            return true;
        }
        final int i = MathHelper.floor_double(((Entity)this).posX);
        final int j = MathHelper.floor_double(((Entity)this).boundingBox.minY);
        final int k = MathHelper.floor_double(((Entity)this).posZ);
        return j > 62 && ((Entity)this).worldObj.getBlock(i, j - 1, k) == ((Entity)this).worldObj.getBiomeGenForCoords(i, k).topBlock;
    }
    
    @Override
    public boolean canReEquipHired(final int slot, final ItemStack itemstack) {
        return false;
    }
    
    static {
        LOTREntityBarrowWight.attackEffects = new Potion[] { Potion.moveSlowdown, Potion.digSlowdown, Potion.wither };
    }
}
