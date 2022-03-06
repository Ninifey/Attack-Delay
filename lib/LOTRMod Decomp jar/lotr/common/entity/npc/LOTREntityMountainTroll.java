// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import lotr.common.LOTRAchievement;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import lotr.common.entity.projectile.LOTREntityThrownRock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.IRangedAttackMob;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.world.World;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.attributes.IAttribute;

public class LOTREntityMountainTroll extends LOTREntityTroll
{
    public static IAttribute thrownRockDamage;
    private EntityAIBase rangedAttackAI;
    private EntityAIBase meleeAttackAI;
    
    public LOTREntityMountainTroll(final World world) {
        super(world);
        this.rangedAttackAI = this.getTrollRangedAttackAI();
    }
    
    @Override
    public float getTrollScale() {
        return 1.6f;
    }
    
    @Override
    public EntityAIBase getTrollAttackAI() {
        return this.meleeAttackAI = new LOTREntityAIAttackOnCollide(this, 1.8, false);
    }
    
    protected EntityAIBase getTrollRangedAttackAI() {
        return new LOTREntityAIRangedAttack((IRangedAttackMob)this, 1.2, 30, 60, 24.0f);
    }
    
    public void entityInit() {
        super.entityInit();
        ((Entity)this).dataWatcher.addObject(21, (Object)0);
    }
    
    public boolean isThrowingRocks() {
        return ((Entity)this).dataWatcher.getWatchableObjectByte(21) == 1;
    }
    
    public void setThrowingRocks(final boolean flag) {
        ((Entity)this).dataWatcher.updateObject(21, (Object)(byte)(flag ? 1 : 0));
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(70.0);
        this.getEntityAttribute(LOTREntityNPC.npcAttackDamage).setBaseValue(7.0);
        this.getAttributeMap().registerAttribute(LOTREntityMountainTroll.thrownRockDamage);
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
    public double getMeleeRange() {
        return 12.0;
    }
    
    public void onAttackModeChange(final AttackMode mode, final boolean mounted) {
        if (mode == AttackMode.IDLE) {
            ((EntityLiving)this).tasks.removeTask(this.meleeAttackAI);
            ((EntityLiving)this).tasks.removeTask(this.rangedAttackAI);
            this.setThrowingRocks(false);
        }
        if (mode == AttackMode.MELEE) {
            ((EntityLiving)this).tasks.removeTask(this.meleeAttackAI);
            ((EntityLiving)this).tasks.removeTask(this.rangedAttackAI);
            ((EntityLiving)this).tasks.addTask(3, this.meleeAttackAI);
            this.setThrowingRocks(false);
        }
        if (mode == AttackMode.RANGED) {
            ((EntityLiving)this).tasks.removeTask(this.meleeAttackAI);
            ((EntityLiving)this).tasks.removeTask(this.rangedAttackAI);
            ((EntityLiving)this).tasks.addTask(3, this.rangedAttackAI);
            this.setThrowingRocks(true);
        }
    }
    
    @Override
    public void attackEntityWithRangedAttack(final EntityLivingBase target, final float f) {
        final EntityArrow template = new EntityArrow(((Entity)this).worldObj, (EntityLivingBase)this, target, f * 1.5f, 0.5f);
        final LOTREntityThrownRock rock = this.getThrownRock();
        rock.setLocationAndAngles(((Entity)template).posX, ((Entity)template).posY, ((Entity)template).posZ, ((Entity)template).rotationYaw, ((Entity)template).rotationPitch);
        ((Entity)rock).motionX = ((Entity)template).motionX;
        ((Entity)rock).motionY = ((Entity)template).motionY + 0.6;
        ((Entity)rock).motionZ = ((Entity)template).motionZ;
        ((Entity)this).worldObj.spawnEntityInWorld((Entity)rock);
        this.playSound(this.getLivingSound(), this.getSoundVolume(), this.getSoundPitch() * 0.75f);
        this.swingItem();
    }
    
    protected LOTREntityThrownRock getThrownRock() {
        final LOTREntityThrownRock rock = new LOTREntityThrownRock(((Entity)this).worldObj, (EntityLivingBase)this);
        rock.setDamage((float)this.getEntityAttribute(LOTREntityMountainTroll.thrownRockDamage).getAttributeValue());
        return rock;
    }
    
    @Override
    public void onTrollDeathBySun() {
        ((Entity)this).worldObj.playSoundAtEntity((Entity)this, "lotr:troll.transform", this.getSoundVolume(), this.getSoundPitch());
        ((Entity)this).worldObj.setEntityState((Entity)this, (byte)15);
        this.setDead();
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void handleHealthUpdate(final byte b) {
        if (b == 15) {
            super.handleHealthUpdate(b);
            for (int l = 0; l < 64; ++l) {
                LOTRMod.proxy.spawnParticle("largeStone", ((Entity)this).posX + ((Entity)this).rand.nextGaussian() * ((Entity)this).width * 0.5, ((Entity)this).posY + ((Entity)this).rand.nextDouble() * ((Entity)this).height, ((Entity)this).posZ + ((Entity)this).rand.nextGaussian() * ((Entity)this).width * 0.5, 0.0, 0.0, 0.0);
            }
        }
        else {
            super.handleHealthUpdate(b);
        }
    }
    
    public void dropFewItems(final boolean flag, final int i) {
        super.dropFewItems(flag, i);
        int totemChance = 15 - i * 3;
        totemChance = Math.max(totemChance, 1);
        if (((Entity)this).rand.nextInt(totemChance) == 0) {
            this.entityDropItem(new ItemStack(LOTRMod.trollTotem, 1, ((Entity)this).rand.nextInt(3)), 0.0f);
        }
    }
    
    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killMountainTroll;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 4.0f;
    }
    
    @Override
    protected int getExperiencePoints(final EntityPlayer entityplayer) {
        return 7 + ((Entity)this).rand.nextInt(6);
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        return null;
    }
    
    static {
        LOTREntityMountainTroll.thrownRockDamage = (IAttribute)new RangedAttribute("lotr.thrownRockDamage", 5.0, 0.0, 100.0).setDescription("LOTR Thrown Rock Damage");
    }
}
