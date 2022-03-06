// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import lotr.common.quest.LOTRMiniQuest;
import lotr.common.LOTRPlayerData;
import lotr.common.quest.LOTRMiniQuestWelcome;
import lotr.common.LOTRLevelData;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import lotr.common.fac.LOTRFaction;
import lotr.common.LOTRGreyWandererTracker;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import lotr.common.LOTRCapes;
import lotr.common.entity.ai.LOTREntityAINearestAttackableTargetBasic;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.entity.ai.LOTREntityAIGandalfSmoke;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.world.World;

public class LOTREntityGandalf extends LOTREntityNPC
{
    private static final double msgRange = 64.0;
    
    public LOTREntityGandalf(final World world) {
        super(world);
        this.setSize(0.6f, 1.8f);
        this.getNavigator().setAvoidsWater(true);
        this.getNavigator().setBreakDoors(true);
        ((EntityLiving)this).tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        ((EntityLiving)this).tasks.addTask(1, (EntityAIBase)new LOTREntityAIAttackOnCollide(this, 1.8, false));
        ((EntityLiving)this).tasks.addTask(2, (EntityAIBase)new EntityAIOpenDoor((EntityLiving)this, true));
        ((EntityLiving)this).tasks.addTask(3, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0));
        ((EntityLiving)this).tasks.addTask(4, (EntityAIBase)new LOTREntityAIGandalfSmoke(this, 3000));
        ((EntityLiving)this).tasks.addTask(5, (EntityAIBase)new EntityAIWatchClosest2((EntityLiving)this, (Class)EntityPlayer.class, 8.0f, 0.05f));
        ((EntityLiving)this).tasks.addTask(5, (EntityAIBase)new EntityAIWatchClosest2((EntityLiving)this, (Class)LOTREntityNPC.class, 5.0f, 0.05f));
        ((EntityLiving)this).tasks.addTask(5, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityLiving.class, 8.0f, 0.02f));
        ((EntityLiving)this).tasks.addTask(6, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        final int target = this.addTargetTasks(false);
        ((EntityLiving)this).targetTasks.addTask(target + 1, (EntityAIBase)new LOTREntityAINearestAttackableTargetBasic(this, LOTREntityBalrog.class, 0, true));
        ((EntityLiving)this).targetTasks.addTask(target + 2, (EntityAIBase)new LOTREntityAINearestAttackableTargetBasic(this, LOTREntitySaruman.class, 0, true));
        super.npcCape = LOTRCapes.GANDALF;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.glamdring));
        super.npcItemsInv.setIdleItem(new ItemStack(LOTRMod.gandalfStaffGrey));
        return data;
    }
    
    @Override
    public void onArtificalSpawn() {
        LOTRGreyWandererTracker.addNewWanderer(this.getUniqueID());
        this.arriveAt(null);
    }
    
    public void onAttackModeChange(final AttackMode mode, final boolean mounted) {
        if (mode == AttackMode.IDLE) {
            this.setCurrentItemOrArmor(0, super.npcItemsInv.getIdleItem());
        }
        else {
            this.setCurrentItemOrArmor(0, super.npcItemsInv.getMeleeWeapon());
        }
    }
    
    @Override
    public ItemStack getHeldItemLeft() {
        final ItemStack heldItem = this.getHeldItem();
        if (heldItem != null && heldItem.getItem() == LOTRMod.glamdring) {
            return new ItemStack(LOTRMod.gandalfStaffGrey);
        }
        return super.getHeldItemLeft();
    }
    
    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.UNALIGNED;
    }
    
    @Override
    public boolean canBeFreelyTargetedBy(final EntityLiving attacker) {
        return false;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damagesource, float f) {
        final Entity entity = damagesource.getEntity();
        if (entity instanceof EntityPlayer && ((EntityPlayer)entity).capabilities.isCreativeMode) {
            return super.attackEntityFrom(damagesource, f);
        }
        f = 0.0f;
        return super.attackEntityFrom(damagesource, f);
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!((Entity)this).worldObj.isClient && !LOTRGreyWandererTracker.isWandererActive(this.getUniqueID()) && this.getAttackTarget() == null) {
            this.depart();
        }
    }
    
    private void doGandalfFX() {
        this.playSound("random.pop", 2.0f, 0.5f + ((Entity)this).rand.nextFloat() * 0.5f);
        ((Entity)this).worldObj.setEntityState((Entity)this, (byte)16);
    }
    
    @SideOnly(Side.CLIENT)
    public void handleHealthUpdate(final byte b) {
        if (b == 16) {
            for (int i = 0; i < 20; ++i) {
                final double d0 = ((Entity)this).posX + MathHelper.randomFloatClamp(((Entity)this).rand, -1.0f, 1.0f) * ((Entity)this).width;
                final double d2 = ((Entity)this).posY + MathHelper.randomFloatClamp(((Entity)this).rand, 0.0f, 1.0f) * ((Entity)this).height;
                final double d3 = ((Entity)this).posZ + MathHelper.randomFloatClamp(((Entity)this).rand, -1.0f, 1.0f) * ((Entity)this).width;
                final double d4 = ((Entity)this).rand.nextGaussian() * 0.02;
                final double d5 = 0.05 + ((Entity)this).rand.nextGaussian() * 0.02;
                final double d6 = ((Entity)this).rand.nextGaussian() * 0.02;
                ((Entity)this).worldObj.spawnParticle("explode", d0, d2, d3, d4, d5, d6);
            }
        }
        else {
            super.handleHealthUpdate(b);
        }
    }
    
    public void arriveAt(final EntityPlayer entityplayer) {
        final List<EntityPlayer> msgPlayers = new ArrayList<EntityPlayer>();
        if (entityplayer != null) {
            msgPlayers.add(entityplayer);
        }
        final List worldPlayers = ((Entity)this).worldObj.playerEntities;
        for (final Object obj : worldPlayers) {
            final EntityPlayer player = (EntityPlayer)obj;
            if (!msgPlayers.contains(player)) {
                final double d = 64.0;
                final double dSq = d * d;
                if (this.getDistanceSqToEntity((Entity)player) >= dSq) {
                    continue;
                }
                msgPlayers.add(player);
            }
        }
        for (final EntityPlayer player2 : msgPlayers) {
            LOTRSpeech.sendSpeechAndChatMessage(player2, this, "char/gandalf/arrive");
        }
        this.doGandalfFX();
    }
    
    private void depart() {
        final List<EntityPlayer> msgPlayers = new ArrayList<EntityPlayer>();
        final List worldPlayers = ((Entity)this).worldObj.playerEntities;
        for (final Object obj : worldPlayers) {
            final EntityPlayer player = (EntityPlayer)obj;
            if (!msgPlayers.contains(player)) {
                final double d = 64.0;
                final double dSq = d * d;
                if (this.getDistanceSqToEntity((Entity)player) >= dSq) {
                    continue;
                }
                msgPlayers.add(player);
            }
        }
        for (final EntityPlayer player2 : msgPlayers) {
            LOTRSpeech.sendSpeechAndChatMessage(player2, this, "char/gandalf/depart");
        }
        this.doGandalfFX();
        this.setDead();
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (this.isFriendly(entityplayer)) {
            return "char/gandalf/friendly";
        }
        return "char/gandalf/hostile";
    }
    
    @Override
    public boolean speakTo(final EntityPlayer entityplayer) {
        if (LOTRGreyWandererTracker.isWandererActive(this.getUniqueID())) {
            if (super.questInfo.getOfferFor(entityplayer) != null) {
                return super.speakTo(entityplayer);
            }
            if (this.addMQOfferFor(entityplayer)) {
                LOTRGreyWandererTracker.setWandererActive(this.getUniqueID());
                final String speechBank = "char/gandalf/welcome";
                this.sendSpeechBank(entityplayer, speechBank);
                return true;
            }
        }
        return super.speakTo(entityplayer);
    }
    
    private boolean addMQOfferFor(final EntityPlayer entityplayer) {
        final LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
        if (pd.getMiniQuestsForEntity(this, true).isEmpty()) {
            final LOTRMiniQuest quest = new LOTRMiniQuestWelcome(null, this);
            if (quest.canPlayerAccept(entityplayer)) {
                super.questInfo.setPlayerSpecificOffer(entityplayer, quest);
                return true;
            }
        }
        return false;
    }
}
