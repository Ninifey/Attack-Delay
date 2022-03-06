// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRAchievement;
import net.minecraft.item.ItemStack;
import lotr.common.item.LOTRItemBossTrophy;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.MathHelper;
import lotr.common.entity.projectile.LOTREntityThrownRock;
import java.util.List;
import net.minecraft.util.DamageSource;
import net.minecraft.entity.Entity;
import lotr.common.LOTRMod;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.IRangedAttackMob;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import net.minecraft.entity.ai.EntityAIBase;
import lotr.common.entity.ai.LOTREntityAIBossJumpAttack;
import net.minecraft.world.World;

public class LOTREntityMountainTrollChieftain extends LOTREntityMountainTroll implements LOTRBoss
{
    private static int SPAWN_TIME;
    private int trollDeathTick;
    private int healAmount;
    
    public LOTREntityMountainTrollChieftain(final World world) {
        super(world);
        ((EntityLiving)this).tasks.addTask(2, (EntityAIBase)new LOTREntityAIBossJumpAttack(this, 1.5, 0.03f));
    }
    
    @Override
    public float getTrollScale() {
        return 2.0f;
    }
    
    @Override
    protected EntityAIBase getTrollRangedAttackAI() {
        return new LOTREntityAIRangedAttack((IRangedAttackMob)this, 1.2, 20, 50, 24.0f);
    }
    
    @Override
    public void entityInit() {
        super.entityInit();
        ((Entity)this).dataWatcher.addObject(22, (Object)0);
        ((Entity)this).dataWatcher.addObject(23, (Object)(-1));
        ((Entity)this).dataWatcher.addObject(24, (Object)2);
    }
    
    @Override
    public boolean hasTwoHeads() {
        return true;
    }
    
    public int getTrollSpawnTick() {
        return ((Entity)this).dataWatcher.getWatchableObjectShort(22);
    }
    
    public void setTrollSpawnTick(final int i) {
        ((Entity)this).dataWatcher.updateObject(22, (Object)(short)i);
    }
    
    public int getHealingEntityID() {
        return ((Entity)this).dataWatcher.getWatchableObjectInt(23);
    }
    
    public void setHealingEntityID(final int i) {
        ((Entity)this).dataWatcher.updateObject(23, (Object)i);
    }
    
    public int getTrollArmorLevel() {
        return ((Entity)this).dataWatcher.getWatchableObjectByte(24);
    }
    
    public void setTrollArmorLevel(final int i) {
        ((Entity)this).dataWatcher.updateObject(24, (Object)(byte)i);
    }
    
    @Override
    public int getTotalArmorValue() {
        return 12;
    }
    
    public float getArmorLevelChanceModifier() {
        int i = 3 - this.getTrollArmorLevel();
        if (i < 1) {
            i = 1;
        }
        return (float)i;
    }
    
    @Override
    public float getBaseChanceModifier() {
        return super.bossInfo.getHealthChanceModifier() * this.getArmorLevelChanceModifier();
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
        this.getEntityAttribute(LOTREntityNPC.npcAttackDamage).setBaseValue(8.0);
        this.getEntityAttribute(LOTREntityMountainTroll.thrownRockDamage).setBaseValue(8.0);
    }
    
    public float getSpawningOffset(final float f) {
        float f2 = (this.getTrollSpawnTick() + f) / LOTREntityMountainTrollChieftain.SPAWN_TIME;
        f2 = Math.min(f2, 1.0f);
        return (1.0f - f2) * -5.0f;
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.getTrollSpawnTick() < LOTREntityMountainTrollChieftain.SPAWN_TIME) {
            if (!((Entity)this).worldObj.isClient) {
                this.setTrollSpawnTick(this.getTrollSpawnTick() + 1);
                if (this.getTrollSpawnTick() == LOTREntityMountainTrollChieftain.SPAWN_TIME) {
                    super.bossInfo.doJumpAttack(1.5);
                }
            }
            else {
                for (int l = 0; l < 32; ++l) {
                    final double d = ((Entity)this).posX + ((Entity)this).rand.nextGaussian() * ((Entity)this).width * 0.5;
                    final double d2 = ((Entity)this).posY + ((Entity)this).rand.nextDouble() * ((Entity)this).height + this.getSpawningOffset(0.0f);
                    final double d3 = ((Entity)this).posZ + ((Entity)this).rand.nextGaussian() * ((Entity)this).width * 0.5;
                    LOTRMod.proxy.spawnParticle("mtcSpawn", d, d2, d3, 0.0, 0.0, 0.0);
                }
            }
        }
        if (((Entity)this).worldObj.isClient && this.getTrollArmorLevel() == 0) {
            for (int i = 0; i < 4; ++i) {
                ((Entity)this).worldObj.spawnParticle("largesmoke", ((Entity)this).posX + (((Entity)this).rand.nextDouble() - 0.5) * ((Entity)this).width, ((Entity)this).posY + ((Entity)this).rand.nextDouble() * ((Entity)this).height, ((Entity)this).posZ + (((Entity)this).rand.nextDouble() - 0.5) * ((Entity)this).width, 0.0, 0.0, 0.0);
            }
        }
        if (!((Entity)this).worldObj.isClient && (this.getTrollBurnTime() >= 0 || this.trollDeathTick > 0)) {
            if (this.trollDeathTick == 0) {
                ((Entity)this).worldObj.playSoundAtEntity((Entity)this, "lotr:troll.transform", this.getSoundVolume(), this.getSoundPitch());
            }
            if (this.trollDeathTick % 5 == 0) {
                ((Entity)this).worldObj.setEntityState((Entity)this, (byte)15);
            }
            if (this.trollDeathTick % 10 == 0) {
                this.playSound(this.getLivingSound(), this.getSoundVolume() * 2.0f, 0.8f);
            }
            ++this.trollDeathTick;
            ((Entity)this).rotationYaw += 60.0f * (((Entity)this).rand.nextFloat() - 0.5f);
            ((EntityLivingBase)this).rotationYawHead += 60.0f * (((Entity)this).rand.nextFloat() - 0.5f);
            ((Entity)this).rotationPitch += 60.0f * (((Entity)this).rand.nextFloat() - 0.5f);
            ((EntityLivingBase)this).limbSwingAmount += 60.0f * (((Entity)this).rand.nextFloat() - 0.5f);
            if (this.trollDeathTick >= 200) {
                this.setDead();
            }
        }
        if (!((Entity)this).worldObj.isClient && this.getHealth() < this.getMaxHealth()) {
            float f = this.getBaseChanceModifier();
            f *= 0.02f;
            if (((Entity)this).rand.nextFloat() < f) {
                final List nearbyTrolls = ((Entity)this).worldObj.getEntitiesWithinAABB((Class)LOTREntityTroll.class, ((Entity)this).boundingBox.expand(24.0, 8.0, 24.0));
                if (!nearbyTrolls.isEmpty()) {
                    final LOTREntityTroll troll = nearbyTrolls.get(((Entity)this).rand.nextInt(nearbyTrolls.size()));
                    if (!(troll instanceof LOTREntityMountainTrollChieftain) && troll.isEntityAlive()) {
                        this.setHealingEntityID(troll.getEntityId());
                        this.healAmount = 8 + ((Entity)this).rand.nextInt(3);
                    }
                }
            }
        }
        if (this.getHealingEntityID() != -1) {
            final Entity entity = ((Entity)this).worldObj.getEntityByID(this.getHealingEntityID());
            if (entity != null && entity instanceof LOTREntityTroll && entity.isEntityAlive()) {
                if (!((Entity)this).worldObj.isClient) {
                    if (((Entity)this).ticksExisted % 20 == 0) {
                        this.heal(3.0f);
                        entity.attackEntityFrom(DamageSource.generic, 3.0f);
                        --this.healAmount;
                        if (!entity.isEntityAlive() || this.getHealth() >= this.getMaxHealth() || this.healAmount <= 0) {
                            this.setHealingEntityID(-1);
                        }
                    }
                }
                else {
                    final double d = entity.posX;
                    final double d2 = entity.posY + entity.height / 2.0;
                    final double d3 = entity.posZ;
                    double d4 = ((Entity)this).posX - d;
                    double d5 = ((Entity)this).posY + ((Entity)this).height / 2.0 - d2;
                    double d6 = ((Entity)this).posZ - d3;
                    d4 /= 30.0;
                    d5 /= 30.0;
                    d6 /= 30.0;
                    LOTRMod.proxy.spawnParticle("mtcHeal", d, d2, d3, d4, d5, d6);
                }
            }
            else if (!((Entity)this).worldObj.isClient) {
                this.setHealingEntityID(-1);
            }
        }
        if (!((Entity)this).worldObj.isClient && this.getHealth() < this.getMaxHealth() && ((Entity)this).rand.nextInt(50) == 0 && !this.isThrowingRocks()) {
            final LOTREntityThrownRock rock = this.getThrownRock();
            if (rock.getSpawnsTroll()) {
                rock.setLocationAndAngles(((Entity)this).posX, ((Entity)this).posY + ((Entity)this).height, ((Entity)this).posZ, 0.0f, 0.0f);
                ((Entity)rock).motionX = 0.0;
                ((Entity)rock).motionY = 1.5;
                ((Entity)rock).motionZ = 0.0;
                ((Entity)this).worldObj.spawnEntityInWorld((Entity)rock);
                this.swingItem();
            }
        }
        if (!((Entity)this).worldObj.isClient) {
            float f = this.getBaseChanceModifier();
            f *= 0.05f;
            if (((Entity)this).rand.nextFloat() < f) {
                super.bossInfo.doTargetedJumpAttack(1.5);
            }
        }
    }
    
    protected boolean isMovementBlocked() {
        return this.getTrollSpawnTick() < LOTREntityMountainTrollChieftain.SPAWN_TIME || this.trollDeathTick > 0 || super.isMovementBlocked();
    }
    
    @Override
    public void onJumpAttackFall() {
        ((Entity)this).worldObj.setEntityState((Entity)this, (byte)20);
        this.playSound("lotr:troll.rockSmash", 1.5f, 0.75f);
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void handleHealthUpdate(final byte b) {
        if (b == 20) {
            for (int i = 0; i < 360; i += 2) {
                final float angle = (float)Math.toRadians(i);
                final double distance = 2.0;
                final double d = distance * MathHelper.sin(angle);
                final double d2 = distance * MathHelper.cos(angle);
                LOTRMod.proxy.spawnParticle("largeStone", ((Entity)this).posX + d, ((Entity)this).boundingBox.minY + 0.1, ((Entity)this).posZ + d2, d * 0.2, 0.2, d2 * 0.2);
            }
        }
        else if (b == 21) {
            for (int i = 0; i < 64; ++i) {
                LOTRMod.proxy.spawnParticle("mtcArmor", ((Entity)this).posX + (((Entity)this).rand.nextDouble() - 0.5) * ((Entity)this).width, ((Entity)this).posY + ((Entity)this).rand.nextDouble() * ((Entity)this).height, ((Entity)this).posZ + (((Entity)this).rand.nextDouble() - 0.5) * ((Entity)this).width, 0.0, 0.0, 0.0);
            }
        }
        else {
            super.handleHealthUpdate(b);
        }
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setInteger("TrollSpawnTick", this.getTrollSpawnTick());
        nbt.setInteger("TrollDeathTick", this.trollDeathTick);
        nbt.setInteger("TrollArmorLevel", this.getTrollArmorLevel());
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.setTrollSpawnTick(nbt.getInteger("TrollSpawnTick"));
        this.trollDeathTick = nbt.getInteger("TrollDeathTick");
        if (nbt.hasKey("TrollArmorLevel")) {
            this.setTrollArmorLevel(nbt.getInteger("TrollArmorLevel"));
        }
    }
    
    @Override
    protected LOTREntityThrownRock getThrownRock() {
        final LOTREntityThrownRock rock = super.getThrownRock();
        float f = this.getBaseChanceModifier();
        f *= 0.4f;
        final int maxNearbyTrolls = 5;
        final List nearbyTrolls = ((Entity)this).worldObj.getEntitiesWithinAABB((Class)LOTREntityTroll.class, ((Entity)this).boundingBox.expand(24.0, 8.0, 24.0));
        final float nearbyModifier = (maxNearbyTrolls - nearbyTrolls.size()) / (float)maxNearbyTrolls;
        f *= nearbyModifier;
        if (((Entity)this).rand.nextFloat() < f) {
            rock.setSpawnsTroll(true);
        }
        return rock;
    }
    
    public boolean attackEntityFrom(final DamageSource damagesource, float f) {
        if (this.getTrollSpawnTick() < LOTREntityMountainTrollChieftain.SPAWN_TIME || this.trollDeathTick > 0) {
            return false;
        }
        if (LOTRMod.playerSourceOfDamage(damagesource) == null && f > 1.0f) {
            f = 1.0f;
        }
        final boolean flag = super.attackEntityFrom(damagesource, f);
        return flag;
    }
    
    protected void damageEntity(final DamageSource damagesource, final float f) {
        super.damageEntity(damagesource, f);
        if (!((Entity)this).worldObj.isClient && this.getTrollArmorLevel() > 0 && this.getHealth() <= 0.0f) {
            this.setTrollArmorLevel(this.getTrollArmorLevel() - 1);
            if (this.getTrollArmorLevel() == 0) {
                double speed = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
                speed *= 1.5;
                this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(speed);
            }
            double maxHealth = this.getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue();
            maxHealth *= 2.0;
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(maxHealth);
            this.setHealth(this.getMaxHealth());
            ((Entity)this).worldObj.setEntityState((Entity)this, (byte)21);
        }
    }
    
    @Override
    public void dropFewItems(final boolean flag, final int i) {
        super.dropFewItems(flag, i);
        for (int drops = 3 + ((Entity)this).rand.nextInt(4) + ((Entity)this).rand.nextInt(i * 2 + 1), j = 0; j < drops; ++j) {
            this.dropTrollItems(flag, i);
        }
        for (int bones = MathHelper.getRandomIntegerInRange(((Entity)this).rand, 4, 8) + ((Entity)this).rand.nextInt(i * 3 + 1), k = 0; k < bones; ++k) {
            this.func_145779_a(LOTRMod.trollBone, 1);
        }
        int dropped;
        for (int coins = MathHelper.getRandomIntegerInRange(((Entity)this).rand, 50, 100 + i * 100); coins > 0; coins -= dropped) {
            dropped = Math.min(20, coins);
            this.func_145779_a(LOTRMod.silverCoin, dropped);
        }
        this.dropChestContents(LOTRChestContents.TROLL_HOARD, 5, 8 + i * 3);
        this.entityDropItem(new ItemStack(LOTRMod.bossTrophy, 1, LOTRItemBossTrophy.TrophyType.MOUNTAIN_TROLL_CHIEFTAIN.trophyID), 0.0f);
        float swordChance = 0.3f;
        swordChance += i * 0.1f;
        if (((Entity)this).rand.nextFloat() < swordChance) {
            this.func_145779_a(LOTRMod.swordGondolin, 1);
        }
        float armorChance = 0.2f;
        armorChance += i * 0.05f;
        if (((Entity)this).rand.nextFloat() < armorChance) {
            this.func_145779_a(LOTRMod.helmetGondolin, 1);
        }
        if (((Entity)this).rand.nextFloat() < armorChance) {
            this.func_145779_a(LOTRMod.bodyGondolin, 1);
        }
        if (((Entity)this).rand.nextFloat() < armorChance) {
            this.func_145779_a(LOTRMod.legsGondolin, 1);
        }
        if (((Entity)this).rand.nextFloat() < armorChance) {
            this.func_145779_a(LOTRMod.bootsGondolin, 1);
        }
    }
    
    @Override
    public LOTRAchievement getBossKillAchievement() {
        return LOTRAchievement.killMountainTrollChieftain;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 50.0f;
    }
    
    @Override
    protected int getExperiencePoints(final EntityPlayer entityplayer) {
        return 100;
    }
    
    static {
        LOTREntityMountainTrollChieftain.SPAWN_TIME = 100;
    }
}
