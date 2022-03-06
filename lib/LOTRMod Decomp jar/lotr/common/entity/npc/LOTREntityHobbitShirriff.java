// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import lotr.common.entity.projectile.LOTREntityPebble;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.entity.animal.LOTREntityShirePony;
import lotr.common.item.LOTRItemLeatherHat;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.IRangedAttackMob;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import lotr.common.entity.ai.LOTREntityAIFollowHiringPlayer;
import lotr.common.entity.ai.LOTREntityAIHiredRemainStill;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.world.World;
import net.minecraft.entity.ai.EntityAIBase;

public class LOTREntityHobbitShirriff extends LOTREntityHobbit
{
    public EntityAIBase rangedAttackAI;
    public EntityAIBase meleeAttackAI;
    
    public LOTREntityHobbitShirriff(final World world) {
        super(world);
        this.rangedAttackAI = this.createHobbitRangedAttackAI();
        this.meleeAttackAI = this.createHobbitMeleeAttackAI();
        ((EntityLiving)this).tasks.taskEntries.clear();
        ((EntityLiving)this).tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        ((EntityLiving)this).tasks.addTask(1, (EntityAIBase)new LOTREntityAIHiredRemainStill(this));
        ((EntityLiving)this).tasks.addTask(3, (EntityAIBase)new LOTREntityAIFollowHiringPlayer(this));
        ((EntityLiving)this).tasks.addTask(4, (EntityAIBase)new EntityAIOpenDoor((EntityLiving)this, true));
        ((EntityLiving)this).tasks.addTask(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0));
        ((EntityLiving)this).tasks.addTask(6, (EntityAIBase)new EntityAIWatchClosest2((EntityLiving)this, (Class)EntityPlayer.class, 8.0f, 0.02f));
        ((EntityLiving)this).tasks.addTask(6, (EntityAIBase)new EntityAIWatchClosest2((EntityLiving)this, (Class)LOTREntityNPC.class, 5.0f, 0.02f));
        ((EntityLiving)this).tasks.addTask(7, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityLiving.class, 8.0f, 0.02f));
        ((EntityLiving)this).tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.addTargetTasks(true);
        super.spawnRidingHorse = (((Entity)this).rand.nextInt(3) == 0);
    }
    
    public EntityAIBase createHobbitRangedAttackAI() {
        return new LOTREntityAIRangedAttack((IRangedAttackMob)this, 1.5, 20, 40, 12.0f);
    }
    
    public EntityAIBase createHobbitMeleeAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.5, false);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
        this.getEntityAttribute(LOTREntityNPC.horseAttackSpeed).setBaseValue(2.0);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        final int i = ((Entity)this).rand.nextInt(3);
        if (i == 0 || i == 1) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerIron));
        }
        else if (i == 2) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerBronze));
        }
        super.npcItemsInv.setRangedWeapon(new ItemStack(LOTRMod.sling));
        super.npcItemsInv.setIdleItem(null);
        final ItemStack hat = new ItemStack(LOTRMod.leatherHat);
        LOTRItemLeatherHat.setHatColor(hat, 6834742);
        LOTRItemLeatherHat.setFeatherColor(hat, 16777215);
        this.setCurrentItemOrArmor(4, hat);
        return data;
    }
    
    @Override
    public LOTRNPCMount createMountToRide() {
        return new LOTREntityShirePony(((Entity)this).worldObj);
    }
    
    public void onAttackModeChange(final AttackMode mode, final boolean mounted) {
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
    public void attackEntityWithRangedAttack(final EntityLivingBase target, final float f) {
        final EntityArrow template = new EntityArrow(((Entity)this).worldObj, (EntityLivingBase)this, target, 1.0f, 0.5f);
        final LOTREntityPebble pebble = new LOTREntityPebble(((Entity)this).worldObj, (EntityLivingBase)this).setSling();
        pebble.setLocationAndAngles(((Entity)template).posX, ((Entity)template).posY, ((Entity)template).posZ, ((Entity)template).rotationYaw, ((Entity)template).rotationPitch);
        ((Entity)pebble).motionX = ((Entity)template).motionX;
        ((Entity)pebble).motionY = ((Entity)template).motionY;
        ((Entity)pebble).motionZ = ((Entity)template).motionZ;
        this.playSound("random.bow", 1.0f, 1.0f / (((Entity)this).rand.nextFloat() * 0.4f + 0.8f));
        ((Entity)this).worldObj.spawnEntityInWorld((Entity)pebble);
    }
    
    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }
    
    @Override
    protected void dropFewItems(final boolean flag, final int i) {
        super.dropFewItems(flag, i);
        int dropChance = 10 - i * 2;
        if (dropChance < 1) {
            dropChance = 1;
        }
        if (((Entity)this).rand.nextInt(dropChance) == 0) {
            this.func_145779_a(LOTRMod.pebble, 1 + ((Entity)this).rand.nextInt(3) + ((Entity)this).rand.nextInt(i + 1));
        }
    }
    
    @Override
    protected int getExperiencePoints(final EntityPlayer entityplayer) {
        return 2 + ((Entity)this).rand.nextInt(3);
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (!this.isFriendly(entityplayer)) {
            return "hobbit/shirriff/hostile";
        }
        if (super.hiredNPCInfo.getHiringPlayer() == entityplayer) {
            return "hobbit/shirriff/hired";
        }
        return "hobbit/shirriff/friendly";
    }
}
