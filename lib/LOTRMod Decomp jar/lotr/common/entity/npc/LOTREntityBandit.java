// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import net.minecraft.util.DamageSource;
import lotr.common.item.LOTRItemMug;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import lotr.common.fac.LOTRFaction;
import lotr.common.item.LOTRItemLeatherHat;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import lotr.common.entity.ai.LOTREntityAINearestAttackableTargetBasic;
import lotr.common.entity.ai.LOTREntityAINearestAttackableTargetBandit;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import lotr.common.entity.ai.LOTREntityAIBanditFlee;
import lotr.common.entity.ai.LOTREntityAIBanditSteal;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.world.World;
import lotr.common.inventory.LOTRInventoryNPC;
import net.minecraft.item.ItemStack;

public class LOTREntityBandit extends LOTREntityMan
{
    public static int MAX_THEFTS;
    private static ItemStack[] weapons;
    public LOTRInventoryNPC banditInventory;
    
    public LOTREntityBandit(final World world) {
        super(world);
        this.banditInventory = new LOTRInventoryNPC("BanditInventory", this, LOTREntityBandit.MAX_THEFTS);
        this.setSize(0.6f, 1.8f);
        this.getNavigator().setAvoidsWater(true);
        this.getNavigator().setBreakDoors(true);
        ((EntityLiving)this).tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        ((EntityLiving)this).tasks.addTask(1, (EntityAIBase)new LOTREntityAIAttackOnCollide(this, 1.0, false));
        ((EntityLiving)this).tasks.addTask(2, (EntityAIBase)new LOTREntityAIBanditSteal(this, 1.2));
        ((EntityLiving)this).tasks.addTask(3, (EntityAIBase)new LOTREntityAIBanditFlee(this, 1.0));
        ((EntityLiving)this).tasks.addTask(4, (EntityAIBase)new EntityAIOpenDoor((EntityLiving)this, true));
        ((EntityLiving)this).tasks.addTask(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0));
        ((EntityLiving)this).tasks.addTask(6, (EntityAIBase)new EntityAIWatchClosest2((EntityLiving)this, (Class)EntityPlayer.class, 8.0f, 0.1f));
        ((EntityLiving)this).tasks.addTask(6, (EntityAIBase)new EntityAIWatchClosest2((EntityLiving)this, (Class)LOTREntityNPC.class, 5.0f, 0.05f));
        ((EntityLiving)this).tasks.addTask(7, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityLiving.class, 8.0f, 0.02f));
        ((EntityLiving)this).tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.addTargetTasks(true, LOTREntityAINearestAttackableTargetBandit.class);
    }
    
    @Override
    public void setupNPCGender() {
        super.familyInfo.setMale(true);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        final int i = ((Entity)this).rand.nextInt(LOTREntityBandit.weapons.length);
        super.npcItemsInv.setMeleeWeapon(LOTREntityBandit.weapons[i].copy());
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        if (((Entity)this).rand.nextInt(3) == 0) {
            final ItemStack hat = new ItemStack(LOTRMod.leatherHat);
            LOTRItemLeatherHat.setHatColor(hat, 0);
            LOTRItemLeatherHat.setFeatherColor(hat, 16777215);
            this.setCurrentItemOrArmor(4, hat);
        }
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
    
    public int getTotalArmorValue() {
        return 10;
    }
    
    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.HOSTILE;
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        this.banditInventory.writeToNBT(nbt);
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.banditInventory.readFromNBT(nbt);
    }
    
    @Override
    protected void dropFewItems(final boolean flag, final int i) {
        super.dropFewItems(flag, i);
        for (int bones = ((Entity)this).rand.nextInt(2) + ((Entity)this).rand.nextInt(i + 1), l = 0; l < bones; ++l) {
            this.func_145779_a(Items.bone, 1);
        }
        for (int coins = 10 + ((Entity)this).rand.nextInt(10) + ((Entity)this).rand.nextInt((i + 1) * 10), j = 0; j < coins; ++j) {
            this.func_145779_a(LOTRMod.silverCoin, 1);
        }
        if (((Entity)this).rand.nextInt(5) == 0) {
            this.entityDropItem(LOTRItemMug.Vessel.SKULL.getEmptyVessel(), 0.0f);
        }
    }
    
    @Override
    public void onDeath(final DamageSource damagesource) {
        super.onDeath(damagesource);
        if (!((Entity)this).worldObj.isClient && damagesource.getEntity() instanceof EntityPlayer && !this.banditInventory.isEmpty()) {
            final EntityPlayer entityplayer = (EntityPlayer)damagesource.getEntity();
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.killThievingBandit);
        }
        if (!((Entity)this).worldObj.isClient) {
            this.banditInventory.dropAllItems();
        }
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        return "misc/bandit/hostile";
    }
    
    public boolean canStealFromPlayerInv(final EntityPlayer entityplayer) {
        for (int slot = 0; slot < entityplayer.inventory.mainInventory.length; ++slot) {
            if (slot != entityplayer.inventory.currentItem) {
                if (entityplayer.inventory.getStackInSlot(slot) != null) {
                    return true;
                }
            }
        }
        return false;
    }
    
    static {
        LOTREntityBandit.MAX_THEFTS = 3;
        LOTREntityBandit.weapons = new ItemStack[] { new ItemStack(LOTRMod.daggerBronze), new ItemStack(LOTRMod.daggerIron) };
    }
}
