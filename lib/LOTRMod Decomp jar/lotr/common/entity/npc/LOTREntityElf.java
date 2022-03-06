// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.item.ItemStack;
import java.util.List;
import lotr.common.LOTRPatron;
import net.minecraft.entity.Entity;
import net.minecraft.command.IEntitySelector;
import lotr.common.LOTRMod;
import net.minecraft.util.MathHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.entity.SharedMonsterAttributes;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.IRangedAttackMob;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.entity.ai.LOTREntityAIDrink;
import lotr.common.entity.ai.LOTREntityAIEat;
import lotr.common.LOTRFoods;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import lotr.common.entity.ai.LOTREntityAIFollowHiringPlayer;
import lotr.common.entity.ai.LOTREntityAIHiredRemainStill;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.world.World;
import net.minecraft.entity.ai.EntityAIBase;

public abstract class LOTREntityElf extends LOTREntityNPC
{
    protected EntityAIBase rangedAttackAI;
    protected EntityAIBase meleeAttackAI;
    private int soloTick;
    private float soloSpinSpeed;
    private float soloSpin;
    private float prevSoloSpin;
    private static final int bowTickMax = 40;
    private float bowAmount;
    private float prevBowAmount;
    private static final float bowStep = 0.2f;
    
    public LOTREntityElf(final World world) {
        super(world);
        this.rangedAttackAI = this.createElfRangedAttackAI();
        this.meleeAttackAI = this.createElfMeleeAttackAI();
        this.setSize(0.6f, 1.8f);
        this.getNavigator().setAvoidsWater(true);
        this.getNavigator().setBreakDoors(true);
        ((EntityLiving)this).tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        ((EntityLiving)this).tasks.addTask(1, (EntityAIBase)new LOTREntityAIHiredRemainStill(this));
        ((EntityLiving)this).tasks.addTask(3, (EntityAIBase)new LOTREntityAIFollowHiringPlayer(this));
        ((EntityLiving)this).tasks.addTask(4, (EntityAIBase)new EntityAIOpenDoor((EntityLiving)this, true));
        ((EntityLiving)this).tasks.addTask(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0));
        ((EntityLiving)this).tasks.addTask(6, (EntityAIBase)new LOTREntityAIEat(this, LOTRFoods.ELF, 12000));
        ((EntityLiving)this).tasks.addTask(6, (EntityAIBase)new LOTREntityAIDrink(this, this.getElfDrinks(), 8000));
        ((EntityLiving)this).tasks.addTask(7, (EntityAIBase)new EntityAIWatchClosest2((EntityLiving)this, (Class)EntityPlayer.class, 8.0f, 0.02f));
        ((EntityLiving)this).tasks.addTask(7, (EntityAIBase)new EntityAIWatchClosest2((EntityLiving)this, (Class)LOTREntityNPC.class, 5.0f, 0.02f));
        ((EntityLiving)this).tasks.addTask(8, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityLiving.class, 8.0f, 0.02f));
        ((EntityLiving)this).tasks.addTask(9, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.addTargetTasks(true);
    }
    
    protected LOTRFoods getElfDrinks() {
        return LOTRFoods.ELF_DRINK;
    }
    
    protected EntityAIBase createElfRangedAttackAI() {
        return new LOTREntityAIRangedAttack((IRangedAttackMob)this, 1.25, 40, 60, 16.0f);
    }
    
    protected EntityAIBase createElfMeleeAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.5, false);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        ((Entity)this).dataWatcher.addObject(22, (Object)0);
        ((Entity)this).dataWatcher.addObject(23, (Object)0);
    }
    
    @Override
    public void setupNPCGender() {
        super.familyInfo.setMale(((Entity)this).rand.nextBoolean());
    }
    
    @Override
    public void setupNPCName() {
        super.familyInfo.setName(LOTRNames.getSindarinOrQuenyaName(((Entity)this).rand, super.familyInfo.isMale()));
    }
    
    @Override
    public String getNPCName() {
        return super.familyInfo.getName();
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
        this.getEntityAttribute(LOTREntityNPC.npcRangedAccuracy).setBaseValue(0.5);
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setBoolean("BoopBoopBaDoop", this.isJazz());
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.setJazz(nbt.getBoolean("BoopBoopBaDoop"));
    }
    
    @Override
    public String getEntityClassName() {
        if (this.isJazz()) {
            return "Jazz-elf";
        }
        return super.getEntityClassName();
    }
    
    private boolean getJazzFlag(final int i) {
        final byte b = ((Entity)this).dataWatcher.getWatchableObjectByte(22);
        final int pow2 = 1 << i;
        return (b & pow2) != 0x0;
    }
    
    private void setJazzFlag(final int i, final boolean flag) {
        byte b = ((Entity)this).dataWatcher.getWatchableObjectByte(22);
        final int pow2 = 1 << i;
        if (flag) {
            b |= (byte)pow2;
        }
        else {
            b &= (byte)~pow2;
        }
        ((Entity)this).dataWatcher.updateObject(22, (Object)b);
    }
    
    public boolean isJazz() {
        return this.getJazzFlag(0);
    }
    
    public void setJazz(final boolean flag) {
        this.setJazzFlag(0, flag);
    }
    
    public boolean isSolo() {
        return this.getJazzFlag(1);
    }
    
    public void setSolo(final boolean flag) {
        this.setJazzFlag(1, flag);
    }
    
    private int getBowingTick() {
        return ((Entity)this).dataWatcher.getWatchableObjectShort(23);
    }
    
    private void setBowingTick(final int i) {
        ((Entity)this).dataWatcher.updateObject(23, (Object)(short)i);
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.isJazz()) {
            if (!((Entity)this).worldObj.isClient) {
                if (this.soloTick > 0) {
                    --this.soloTick;
                    ((Entity)this).rotationPitch = -10.0f + (MathHelper.sin(this.soloTick * 0.3f) + 1.0f) / 2.0f * -30.0f;
                }
                else if (((Entity)this).rand.nextInt(200) == 0) {
                    this.soloTick = 60 + ((Entity)this).rand.nextInt(300);
                }
                this.setSolo(this.soloTick > 0);
            }
            else if (this.isSolo()) {
                if (((Entity)this).rand.nextInt(3) == 0) {
                    final double d = ((Entity)this).posX;
                    final double d2 = ((Entity)this).boundingBox.minY + this.getEyeHeight();
                    final double d3 = ((Entity)this).posZ;
                    final double d4 = MathHelper.getRandomDoubleInRange(((Entity)this).rand, -0.1, 0.1);
                    final double d5 = MathHelper.getRandomDoubleInRange(((Entity)this).rand, -0.1, 0.1);
                    final double d6 = MathHelper.getRandomDoubleInRange(((Entity)this).rand, -0.1, 0.1);
                    LOTRMod.proxy.spawnParticle("music", d, d2, d3, d4, d5, d6);
                }
                if (this.soloSpinSpeed == 0.0f || ((Entity)this).rand.nextInt(30) == 0) {
                    this.soloSpinSpeed = MathHelper.randomFloatClamp(((Entity)this).rand, -25.0f, 25.0f);
                }
                this.prevSoloSpin = this.soloSpin;
                this.soloSpin += this.soloSpinSpeed;
            }
            else {
                final float n = 0.0f;
                this.soloSpin = n;
                this.prevSoloSpin = n;
                this.soloSpinSpeed = 0.0f;
            }
        }
        if (!((Entity)this).worldObj.isClient) {
            final double range = 8.0;
            final double rangeSq = range * range;
            EntityPlayer bowingPlayer = null;
            final List players = ((Entity)this).worldObj.selectEntitiesWithinAABB((Class)EntityPlayer.class, ((Entity)this).boundingBox.expand(range, range, range), (IEntitySelector)new IEntitySelector() {
                public boolean isEntityApplicable(final Entity entity) {
                    final EntityPlayer entityplayer = (EntityPlayer)entity;
                    return entityplayer.isEntityAlive() && LOTREntityElf.this.isFriendly(entityplayer) && LOTREntityElf.this.getDistanceSqToEntity((Entity)entityplayer) <= rangeSq && entityplayer.getUniqueID().equals(LOTRPatron.elfBowPlayer);
                }
            });
            if (players.isEmpty() || this.getAttackTarget() != null) {
                this.setBowingTick(0);
            }
            else {
                int tick = this.getBowingTick();
                if (tick >= 0) {
                    ++tick;
                }
                if (tick > 40) {
                    tick = -1;
                }
                this.setBowingTick(tick);
                if (tick >= 0) {
                    this.getNavigator().clearPathEntity();
                    bowingPlayer = players.get(0);
                    float bowLook = (float)Math.toDegrees(Math.atan2(((Entity)bowingPlayer).posZ - ((Entity)this).posZ, ((Entity)bowingPlayer).posX - ((Entity)this).posX));
                    final float n2;
                    bowLook = (n2 = bowLook - 90.0f);
                    ((EntityLivingBase)this).rotationYawHead = n2;
                    ((Entity)this).rotationYaw = n2;
                }
            }
        }
        else {
            this.prevBowAmount = this.bowAmount;
            final int tick2 = this.getBowingTick();
            if (tick2 <= 0 && this.bowAmount > 0.0f) {
                this.bowAmount -= 0.2f;
                this.bowAmount = Math.max(this.bowAmount, 0.0f);
            }
            else if (tick2 > 0 && this.bowAmount < 1.0f) {
                this.bowAmount += 0.2f;
                this.bowAmount = Math.min(this.bowAmount, 1.0f);
            }
        }
    }
    
    public float getBowingAmount(final float f) {
        return this.prevBowAmount + (this.bowAmount - this.prevBowAmount) * f;
    }
    
    public float getSoloSpin(final float f) {
        return this.prevSoloSpin + (this.soloSpin - this.prevSoloSpin) * f;
    }
    
    public ItemStack getHeldItem() {
        if (((Entity)this).worldObj.isClient && this.isJazz() && this.isSolo()) {
            return null;
        }
        return super.getHeldItem();
    }
    
    @Override
    protected void onAttackModeChange(final AttackMode mode, final boolean mounted) {
        if (mode == AttackMode.IDLE) {
            ((EntityLiving)this).tasks.removeTask(this.meleeAttackAI);
            ((EntityLiving)this).tasks.removeTask(this.rangedAttackAI);
            this.setCurrentItemOrArmor(0, super.npcItemsInv.getIdleItem());
        }
        if (mode == AttackMode.MELEE) {
            ((EntityLiving)this).tasks.removeTask(this.meleeAttackAI);
            ((EntityLiving)this).tasks.removeTask(this.rangedAttackAI);
            ((EntityLiving)this).tasks.addTask(2, this.meleeAttackAI);
            this.setCurrentItemOrArmor(0, super.npcItemsInv.getMeleeWeapon());
        }
        if (mode == AttackMode.RANGED) {
            ((EntityLiving)this).tasks.removeTask(this.meleeAttackAI);
            ((EntityLiving)this).tasks.removeTask(this.rangedAttackAI);
            ((EntityLiving)this).tasks.addTask(2, this.rangedAttackAI);
            this.setCurrentItemOrArmor(0, super.npcItemsInv.getRangedWeapon());
        }
    }
    
    @Override
    protected void dropFewItems(final boolean flag, final int i) {
        super.dropFewItems(flag, i);
        for (int bones = ((Entity)this).rand.nextInt(2) + ((Entity)this).rand.nextInt(i + 1), l = 0; l < bones; ++l) {
            this.func_145779_a(LOTRMod.elfBone, 1);
        }
        this.dropNPCArrows(i);
        this.dropElfItems(flag, i);
    }
    
    protected void dropElfItems(final boolean flag, final int i) {
        if (flag) {
            int dropChance = 40 - i * 8;
            dropChance = Math.max(dropChance, 1);
            if (((Entity)this).rand.nextInt(dropChance) == 0) {
                this.func_145779_a(LOTRMod.lembas, 1);
            }
        }
    }
    
    @Override
    public boolean getCanSpawnHere() {
        return super.getCanSpawnHere() && (super.liftSpawnRestrictions || this.canElfSpawnHere());
    }
    
    public abstract boolean canElfSpawnHere();
    
    public void addPotionEffect(final PotionEffect effect) {
        if (effect.getPotionID() == Potion.poison.id) {
            return;
        }
        super.addPotionEffect(effect);
    }
    
    public String getLivingSound() {
        if (this.getAttackTarget() == null && ((Entity)this).rand.nextInt(10) == 0 && super.familyInfo.isMale()) {
            return "lotr:elf.male.say";
        }
        return super.getLivingSound();
    }
    
    @Override
    public String getAttackSound() {
        return super.familyInfo.isMale() ? "lotr:elf.male.attack" : super.getAttackSound();
    }
}
