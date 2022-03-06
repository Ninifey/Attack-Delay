// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.potion.PotionEffect;
import lotr.common.fac.LOTRFaction;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.ai.LOTREntityAISauronUseMace;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.world.World;

public class LOTREntitySauron extends LOTREntityNPC
{
    public LOTREntitySauron(final World world) {
        super(world);
        this.setSize(0.8f, 2.2f);
        ((Entity)this).isImmuneToFire = true;
        this.getNavigator().setAvoidsWater(true);
        ((EntityLiving)this).tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        ((EntityLiving)this).tasks.addTask(1, (EntityAIBase)new LOTREntityAISauronUseMace(this));
        ((EntityLiving)this).tasks.addTask(2, (EntityAIBase)new LOTREntityAIAttackOnCollide(this, 2.0, false));
        ((EntityLiving)this).tasks.addTask(3, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0));
        ((EntityLiving)this).tasks.addTask(4, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityPlayer.class, 10.0f, 0.02f));
        ((EntityLiving)this).tasks.addTask(5, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.addTargetTasks(true);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        ((Entity)this).dataWatcher.addObject(17, (Object)0);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(500.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.18);
        this.getEntityAttribute(LOTREntityNPC.npcAttackDamage).setBaseValue(8.0);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.setCurrentItemOrArmor(0, new ItemStack(LOTRMod.sauronMace));
        return data;
    }
    
    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.MORDOR;
    }
    
    public int getTotalArmorValue() {
        return 20;
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!((Entity)this).worldObj.isClient && this.getHealth() < this.getMaxHealth() && ((Entity)this).ticksExisted % 10 == 0) {
            this.heal(2.0f);
        }
        if (this.getIsUsingMace() && ((Entity)this).worldObj.isClient) {
            for (int i = 0; i < 6; ++i) {
                final double d = ((Entity)this).posX - 2.0 + ((Entity)this).rand.nextFloat() * 4.0f;
                final double d2 = ((Entity)this).posY + ((Entity)this).rand.nextFloat() * 3.0f;
                final double d3 = ((Entity)this).posZ - 2.0 + ((Entity)this).rand.nextFloat() * 4.0f;
                final double d4 = (((Entity)this).posX - d) / 8.0;
                final double d5 = (((Entity)this).posY + 0.5 - d2) / 8.0;
                final double d6 = (((Entity)this).posZ - d3) / 8.0;
                final double d7 = Math.sqrt(d4 * d4 + d5 * d5 + d6 * d6);
                double d8 = 1.0 - d7;
                double d9 = 0.0;
                double d10 = 0.0;
                double d11 = 0.0;
                if (d8 > 0.0) {
                    d8 *= d8;
                    d9 += d4 / d7 * d8 * 0.2;
                    d10 += d5 / d7 * d8 * 0.2;
                    d11 += d6 / d7 * d8 * 0.2;
                }
                ((Entity)this).worldObj.spawnParticle("smoke", d, d2, d3, d9, d10, d11);
            }
        }
    }
    
    @Override
    protected void fall(final float f) {
    }
    
    public void addPotionEffect(final PotionEffect effect) {
    }
    
    protected int decreaseAirSupply(final int i) {
        return i;
    }
    
    @Override
    public void onDeath(final DamageSource damagesource) {
        super.onDeath(damagesource);
        if (!((Entity)this).worldObj.isClient) {
            ((Entity)this).worldObj.createExplosion((Entity)this, ((Entity)this).posX, ((Entity)this).posY, ((Entity)this).posZ, 3.0f, false);
            this.setDead();
        }
    }
    
    public boolean getIsUsingMace() {
        return ((Entity)this).dataWatcher.getWatchableObjectByte(17) == 1;
    }
    
    public void setIsUsingMace(final boolean flag) {
        ((Entity)this).dataWatcher.updateObject(17, (Object)(byte)(flag ? 1 : 0));
    }
}
