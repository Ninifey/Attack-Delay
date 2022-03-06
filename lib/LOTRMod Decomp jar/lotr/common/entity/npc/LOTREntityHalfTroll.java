// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import lotr.common.item.LOTRMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.quest.LOTRMiniQuest;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.ai.LOTREntityAINearestAttackableTargetBasic;
import lotr.common.entity.animal.LOTREntityRabbit;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.entity.ai.LOTREntityAIDrink;
import lotr.common.entity.ai.LOTREntityAIEat;
import lotr.common.LOTRFoods;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIWander;
import lotr.common.entity.ai.LOTREntityAIFollowHiringPlayer;
import lotr.common.entity.ai.LOTREntityAIHiredRemainStill;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.world.World;

public class LOTREntityHalfTroll extends LOTREntityNPC
{
    public LOTREntityHalfTroll(final World world) {
        super(world);
        this.setSize(1.0f, 2.4f);
        this.getNavigator().setAvoidsWater(true);
        ((EntityLiving)this).tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        ((EntityLiving)this).tasks.addTask(1, (EntityAIBase)new LOTREntityAIHiredRemainStill(this));
        ((EntityLiving)this).tasks.addTask(2, this.createHalfTrollAttackAI());
        ((EntityLiving)this).tasks.addTask(3, (EntityAIBase)new LOTREntityAIFollowHiringPlayer(this));
        ((EntityLiving)this).tasks.addTask(4, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0));
        ((EntityLiving)this).tasks.addTask(5, (EntityAIBase)new LOTREntityAIEat(this, LOTRFoods.HALF_TROLL, 6000));
        ((EntityLiving)this).tasks.addTask(5, (EntityAIBase)new LOTREntityAIDrink(this, LOTRFoods.HALF_TROLL_DRINK, 6000));
        ((EntityLiving)this).tasks.addTask(6, (EntityAIBase)new EntityAIWatchClosest2((EntityLiving)this, (Class)EntityPlayer.class, 8.0f, 0.02f));
        ((EntityLiving)this).tasks.addTask(6, (EntityAIBase)new EntityAIWatchClosest2((EntityLiving)this, (Class)LOTREntityNPC.class, 5.0f, 0.02f));
        ((EntityLiving)this).tasks.addTask(7, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityLiving.class, 8.0f, 0.02f));
        ((EntityLiving)this).tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        final int target = this.addTargetTasks(true);
        ((EntityLiving)this).targetTasks.addTask(target + 1, (EntityAIBase)new LOTREntityAINearestAttackableTargetBasic(this, LOTREntityRabbit.class, 1000, false));
        super.spawnsInDarkness = true;
    }
    
    public EntityAIBase createHalfTrollAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.4, false);
    }
    
    public void entityInit() {
        super.entityInit();
        ((Entity)this).dataWatcher.addObject(17, (Object)0);
    }
    
    @Override
    public void setupNPCName() {
        super.familyInfo.setName(LOTRNames.getOrcName(((Entity)this).rand));
    }
    
    private boolean getHalfTrollModelFlag(final int part) {
        final int i = ((Entity)this).dataWatcher.getWatchableObjectByte(17);
        final int pow2 = 1 << part;
        return (i & pow2) != 0x0;
    }
    
    private void setHalfTrollModelFlag(final int part, final boolean flag) {
        int i = ((Entity)this).dataWatcher.getWatchableObjectByte(17);
        final int pow2 = 1 << part;
        if (flag) {
            i |= pow2;
        }
        else {
            i &= ~pow2;
        }
        ((Entity)this).dataWatcher.updateObject(17, (Object)(byte)i);
    }
    
    public boolean hasMohawk() {
        return this.getHalfTrollModelFlag(1);
    }
    
    public void setHasMohawk(final boolean flag) {
        this.setHalfTrollModelFlag(1, flag);
    }
    
    public boolean hasHorns() {
        return this.getHalfTrollModelFlag(2);
    }
    
    public void setHasHorns(final boolean flag) {
        this.setHalfTrollModelFlag(2, flag);
    }
    
    public boolean hasFullHorns() {
        return this.getHalfTrollModelFlag(3);
    }
    
    public void setHasFullHorns(final boolean flag) {
        this.setHalfTrollModelFlag(3, flag);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(35.0);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
        this.getEntityAttribute(LOTREntityNPC.npcAttackDamage).setBaseValue(6.0);
        this.getEntityAttribute(LOTREntityNPC.horseAttackSpeed).setBaseValue(1.5);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.setHasMohawk(((Entity)this).rand.nextBoolean());
        if (((Entity)this).rand.nextBoolean()) {
            this.setHasHorns(true);
            this.setHasFullHorns(((Entity)this).rand.nextBoolean());
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
    
    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.HALF_TROLL;
    }
    
    @Override
    public String getNPCName() {
        return super.familyInfo.getName();
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setBoolean("Mohawk", this.hasMohawk());
        nbt.setBoolean("Horns", this.hasHorns());
        nbt.setBoolean("HornsFull", this.hasFullHorns());
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.setHasMohawk(nbt.getBoolean("Mohawk"));
        this.setHasHorns(nbt.getBoolean("Horns"));
        this.setHasFullHorns(nbt.getBoolean("HornsFull"));
        if (nbt.hasKey("HalfTrollName")) {
            super.familyInfo.setName(nbt.getString("HalfTrollName"));
        }
    }
    
    @Override
    protected void dropFewItems(final boolean flag, final int i) {
        super.dropFewItems(flag, i);
        for (int flesh = ((Entity)this).rand.nextInt(3) + ((Entity)this).rand.nextInt(i + 1), l = 0; l < flesh; ++l) {
            this.func_145779_a(Items.rotten_flesh, 1);
        }
        for (int bones = ((Entity)this).rand.nextInt(2) + ((Entity)this).rand.nextInt(i + 1), j = 0; j < bones; ++j) {
            this.func_145779_a(LOTRMod.trollBone, 1);
        }
    }
    
    @Override
    public float getAlignmentBonus() {
        return 1.0f;
    }
    
    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killHalfTroll;
    }
    
    protected String getLivingSound() {
        return "lotr:halfTroll.say";
    }
    
    protected String getHurtSound() {
        return "lotr:halfTroll.hurt";
    }
    
    protected String getDeathSound() {
        return "lotr:halfTroll.death";
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (!this.isFriendly(entityplayer)) {
            return "halfTroll/halfTroll/hostile";
        }
        if (super.hiredNPCInfo.getHiringPlayer() == entityplayer) {
            return "halfTroll/halfTroll/hired";
        }
        return "halfTroll/halfTroll/friendly";
    }
    
    @Override
    public LOTRMiniQuest createMiniQuest() {
        return LOTRMiniQuestFactory.HALF_TROLL.createQuest(this);
    }
    
    @Override
    public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
        return LOTRMiniQuestFactory.HALF_TROLL;
    }
    
    @Override
    public boolean canReEquipHired(final int slot, final ItemStack itemstack) {
        final LOTRInventoryHiredReplacedItems hiredReplacedInv = super.hiredReplacedInv;
        if (slot != 0) {
            final LOTRInventoryHiredReplacedItems hiredReplacedInv2 = super.hiredReplacedInv;
            if (slot != 1) {
                final LOTRInventoryHiredReplacedItems hiredReplacedInv3 = super.hiredReplacedInv;
                if (slot != 2) {
                    final LOTRInventoryHiredReplacedItems hiredReplacedInv4 = super.hiredReplacedInv;
                    if (slot != 3) {
                        return super.canReEquipHired(slot, itemstack);
                    }
                }
            }
        }
        return itemstack != null && itemstack.getItem() instanceof ItemArmor && ((ItemArmor)itemstack.getItem()).getArmorMaterial() == LOTRMaterial.HALF_TROLL.toArmorMaterial();
    }
}
