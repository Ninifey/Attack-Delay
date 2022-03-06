// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import lotr.common.LOTRAchievement;
import net.minecraft.util.DamageSource;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import lotr.common.world.LOTRWorldProviderUtumno;
import lotr.common.fac.LOTRFaction;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.ai.EntityAIWander;
import lotr.common.entity.ai.LOTREntityAIFollowHiringPlayer;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.ai.LOTREntityAIBalrogCharge;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.world.World;
import net.minecraft.entity.ai.attributes.IAttribute;

public class LOTREntityBalrog extends LOTREntityNPC
{
    public static final IAttribute balrogChargeDamage;
    private int chargeLean;
    private int prevChargeLean;
    private static final int chargeLeanTime = 10;
    public int chargeFrustration;
    
    public LOTREntityBalrog(final World world) {
        super(world);
        this.chargeFrustration = 0;
        this.setSize(2.4f, 5.0f);
        this.getNavigator().setAvoidsWater(true);
        ((EntityLiving)this).tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        ((EntityLiving)this).tasks.addTask(1, (EntityAIBase)new LOTREntityAIBalrogCharge(this, 3.0, 20.0f, 200));
        ((EntityLiving)this).tasks.addTask(2, (EntityAIBase)new LOTREntityAIAttackOnCollide(this, 1.6, false));
        ((EntityLiving)this).tasks.addTask(3, (EntityAIBase)new LOTREntityAIFollowHiringPlayer(this));
        ((EntityLiving)this).tasks.addTask(4, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0));
        ((EntityLiving)this).tasks.addTask(5, (EntityAIBase)new EntityAIWatchClosest2((EntityLiving)this, (Class)EntityPlayer.class, 24.0f, 0.02f));
        ((EntityLiving)this).tasks.addTask(5, (EntityAIBase)new EntityAIWatchClosest2((EntityLiving)this, (Class)LOTREntityNPC.class, 16.0f, 0.02f));
        ((EntityLiving)this).tasks.addTask(6, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityLiving.class, 12.0f, 0.02f));
        ((EntityLiving)this).tasks.addTask(7, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.addTargetTasks(true);
        super.spawnsInDarkness = true;
        ((Entity)this).isImmuneToFire = true;
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        ((Entity)this).dataWatcher.addObject(20, (Object)0);
    }
    
    public boolean isBalrogCharging() {
        return ((Entity)this).dataWatcher.getWatchableObjectByte(20) == 1;
    }
    
    public void setBalrogCharging(final boolean flag) {
        ((Entity)this).dataWatcher.updateObject(20, (Object)(byte)(flag ? 1 : 0));
    }
    
    public float getInterpolatedChargeLean(final float f) {
        return (this.prevChargeLean + (this.chargeLean - this.prevChargeLean) * f) / 10.0f;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(300.0);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
        this.getEntityAttribute(LOTREntityNPC.npcAttackDamage).setBaseValue(10.0);
        this.getAttributeMap().registerAttribute(LOTREntityBalrog.balrogChargeDamage).setBaseValue(15.0);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        if (((Entity)this).rand.nextBoolean()) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.balrogWhip));
        }
        else {
            final int i = ((Entity)this).rand.nextInt(3);
            if (i == 0) {
                super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.swordUtumno));
            }
            else if (i == 1) {
                super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.battleaxeUtumno));
            }
            else if (i == 2) {
                super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.hammerUtumno));
            }
        }
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        return data;
    }
    
    @Override
    protected void onAttackModeChange(final AttackMode mode, final boolean mounted) {
        if (mode == AttackMode.IDLE) {
            this.setCurrentItemOrArmor(0, super.npcItemsInv.getIdleItem());
        }
        else {
            this.setCurrentItemOrArmor(0, super.npcItemsInv.getMeleeWeapon());
        }
    }
    
    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.UTUMNO;
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.getHealth() < this.getMaxHealth() && ((Entity)this).ticksExisted % 10 == 0) {
            this.heal(1.0f);
        }
        if (!((Entity)this).worldObj.isClient) {
            if (this.getAttackTarget() == null) {
                this.chargeFrustration = 0;
            }
            else if (this.isBalrogCharging()) {
                this.chargeFrustration = 0;
            }
            else {
                ++this.chargeFrustration;
            }
        }
        this.prevChargeLean = this.chargeLean;
        if (this.isBalrogCharging()) {
            if (this.chargeLean < 10) {
                ++this.chargeLean;
            }
        }
        else if (this.chargeLean > 0) {
            --this.chargeLean;
        }
        if (this.isWreathedInFlame()) {
            if (!((Entity)this).worldObj.isClient && ((Entity)this).rand.nextInt(80) == 0) {
                final boolean isUtumno = ((Entity)this).worldObj.provider instanceof LOTRWorldProviderUtumno;
                for (int l = 0; l < 24; ++l) {
                    int i = MathHelper.floor_double(((Entity)this).posX);
                    int j = MathHelper.floor_double(((Entity)this).boundingBox.minY);
                    int k = MathHelper.floor_double(((Entity)this).posZ);
                    i += MathHelper.getRandomIntegerInRange(((Entity)this).rand, -8, 8);
                    j += MathHelper.getRandomIntegerInRange(((Entity)this).rand, -4, 8);
                    k += MathHelper.getRandomIntegerInRange(((Entity)this).rand, -8, 8);
                    final Block block = ((Entity)this).worldObj.getBlock(i, j, k);
                    final float maxResistance = Blocks.obsidian.getExplosionResistance((Entity)this);
                    if ((block.isReplaceable((IBlockAccess)((Entity)this).worldObj, i, j, k) || (isUtumno && block.getExplosionResistance((Entity)this) <= maxResistance)) && Blocks.fire.canPlaceBlockAt(((Entity)this).worldObj, i, j, k)) {
                        ((Entity)this).worldObj.setBlock(i, j, k, (Block)Blocks.fire, 0, 3);
                    }
                }
            }
            if (this.isBalrogCharging()) {
                for (int m = 0; m < 10; ++m) {
                    final String s = (((Entity)this).rand.nextInt(3) == 0) ? "flame" : "largesmoke";
                    final double d0 = ((Entity)this).posX + (((Entity)this).rand.nextDouble() - 0.5) * ((Entity)this).width * 1.5;
                    final double d2 = ((Entity)this).posY + ((Entity)this).height * MathHelper.getRandomDoubleInRange(((Entity)this).rand, 0.5, 1.5);
                    final double d3 = ((Entity)this).posZ + (((Entity)this).rand.nextDouble() - 0.5) * ((Entity)this).width * 1.5;
                    final double d4 = ((Entity)this).motionX * -2.0;
                    final double d5 = ((Entity)this).motionY * -0.5;
                    final double d6 = ((Entity)this).motionZ * -2.0;
                    ((Entity)this).worldObj.spawnParticle(s, d0, d2, d3, d4, d5, d6);
                }
            }
            else {
                for (int m = 0; m < 3; ++m) {
                    final String s = (((Entity)this).rand.nextInt(3) == 0) ? "flame" : "largesmoke";
                    final double d0 = ((Entity)this).posX + (((Entity)this).rand.nextDouble() - 0.5) * ((Entity)this).width * 2.0;
                    final double d2 = ((Entity)this).posY + 0.5 + ((Entity)this).rand.nextDouble() * ((Entity)this).height * 1.5;
                    final double d3 = ((Entity)this).posZ + (((Entity)this).rand.nextDouble() - 0.5) * ((Entity)this).width * 2.0;
                    ((Entity)this).worldObj.spawnParticle(s, d0, d2, d3, 0.0, 0.0, 0.0);
                }
            }
        }
    }
    
    public boolean isWreathedInFlame() {
        return this.isEntityAlive() && !this.isWet();
    }
    
    public void knockBack(final Entity entity, final float f, final double d, final double d1) {
        super.knockBack(entity, f, d, d1);
        ((Entity)this).motionX /= 4.0;
        ((Entity)this).motionY /= 4.0;
        ((Entity)this).motionZ /= 4.0;
    }
    
    @Override
    public boolean attackEntityAsMob(final Entity entity) {
        if (super.attackEntityAsMob(entity)) {
            final EntityLivingBase curTarget = this.getAttackTarget();
            if (curTarget != null && entity == curTarget) {
                this.chargeFrustration = 0;
            }
            if (this.getHeldItem() == null) {
                entity.setFire(5);
            }
            final float attackDamage = (float)this.getEntityAttribute(LOTREntityNPC.npcAttackDamage).getAttributeValue();
            final float knockbackModifier = 0.25f * attackDamage;
            entity.addVelocity((double)(-MathHelper.sin(((Entity)this).rotationYaw * 3.1415927f / 180.0f) * knockbackModifier * 0.5f), knockbackModifier * 0.1, (double)(MathHelper.cos(((Entity)this).rotationYaw * 3.1415927f / 180.0f) * knockbackModifier * 0.5f));
            return true;
        }
        return false;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damagesource, final float f) {
        if (damagesource == DamageSource.fall) {
            return false;
        }
        final boolean flag = super.attackEntityFrom(damagesource, f);
        if (flag) {
            final EntityLivingBase curTarget = this.getAttackTarget();
            if (curTarget != null && damagesource.getEntity() == curTarget && damagesource.getSourceOfDamage() == curTarget) {
                this.chargeFrustration = 0;
            }
            return true;
        }
        return false;
    }
    
    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killBalrog;
    }
    
    @Override
    protected int getExperiencePoints(final EntityPlayer entityplayer) {
        return 15 + ((Entity)this).rand.nextInt(10);
    }
    
    @Override
    protected void dropFewItems(final boolean flag, final int i) {
        super.dropFewItems(flag, i);
        for (int coals = MathHelper.getRandomIntegerInRange(((Entity)this).rand, 4, 16 + i * 8), l = 0; l < coals; ++l) {
            this.func_145779_a(Items.coal, 1);
        }
        int fires = 1;
        if (i > 0) {
            float x;
            for (x = MathHelper.randomFloatClamp(((Entity)this).rand, 0.0f, i * 0.667f); x > 1.0f; --x, ++fires) {}
            if (((Entity)this).rand.nextFloat() < x) {
                ++fires;
            }
        }
        for (int j = 0; j < fires; ++j) {
            this.func_145779_a(LOTRMod.balrogFire, 1);
        }
    }
    
    @Override
    public void dropNPCEquipment(final boolean flag, final int i) {
        if (flag && ((Entity)this).rand.nextInt(3) == 0) {
            final ItemStack heldItem = this.getHeldItem();
            if (heldItem != null) {
                this.entityDropItem(heldItem, 0.0f);
                this.setCurrentItemOrArmor(0, (ItemStack)null);
            }
        }
        super.dropNPCEquipment(flag, i);
    }
    
    @Override
    public void onDeath(final DamageSource damagesource) {
        if (!((Entity)this).worldObj.isClient) {
            final int exRange = 8;
            final int i = MathHelper.floor_double(((Entity)this).posX);
            final int j = MathHelper.floor_double(((Entity)this).posY);
            final int k = MathHelper.floor_double(((Entity)this).posZ);
            for (int i2 = i - exRange; i2 <= i + exRange; ++i2) {
                for (int j2 = j - exRange; j2 <= j + exRange; ++j2) {
                    for (int k2 = k - exRange; k2 <= k + exRange; ++k2) {
                        final Block block = ((Entity)this).worldObj.getBlock(i2, j2, k2);
                        if (block.getMaterial() == Material.fire) {
                            ((Entity)this).worldObj.setBlockToAir(i2, j2, k2);
                        }
                    }
                }
            }
        }
        else {
            for (int l = 0; l < 100; ++l) {
                final double d = ((Entity)this).posX + ((Entity)this).width * MathHelper.randomFloatClamp(((Entity)this).rand, -1.0f, 1.0f);
                final double d2 = ((Entity)this).posY + ((Entity)this).height * MathHelper.randomFloatClamp(((Entity)this).rand, 0.0f, 1.0f);
                final double d3 = ((Entity)this).posZ + ((Entity)this).width * MathHelper.randomFloatClamp(((Entity)this).rand, -1.0f, 1.0f);
                final double d4 = ((Entity)this).rand.nextGaussian() * 0.03;
                final double d5 = ((Entity)this).rand.nextGaussian() * 0.03;
                final double d6 = ((Entity)this).rand.nextGaussian() * 0.03;
                if (((Entity)this).rand.nextInt(3) == 0) {
                    ((Entity)this).worldObj.spawnParticle("explode", d, d2, d3, d4, d5, d6);
                }
                else {
                    ((Entity)this).worldObj.spawnParticle("flame", d, d2, d3, d4, d5, d6);
                }
            }
        }
        super.onDeath(damagesource);
        this.playSound(this.getHurtSound(), this.getSoundVolume() * 2.0f, this.getSoundPitch() * 0.75f);
    }
    
    public String getLivingSound() {
        return "lotr:troll.say";
    }
    
    public String getHurtSound() {
        return "lotr:troll.say";
    }
    
    protected float getSoundVolume() {
        return 1.5f;
    }
    
    protected void func_145780_a(final int i, final int j, final int k, final Block block) {
        this.playSound("lotr:troll.step", 0.75f, this.getSoundPitch());
    }
    
    static {
        balrogChargeDamage = (IAttribute)new RangedAttribute("lotr.balrogChargeDamage", 2.0, 0.0, Double.MAX_VALUE).setDescription("Balrog Charge Damage");
    }
}
