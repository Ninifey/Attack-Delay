// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.quest.LOTRMiniQuest;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.world.biome.LOTRBiomeGenRedMountains;
import lotr.common.world.biome.LOTRBiomeGenBlueMountains;
import lotr.common.world.biome.LOTRBiomeGenErebor;
import lotr.common.world.biome.LOTRBiomeGenIronHills;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.block.material.Material;
import net.minecraft.util.MathHelper;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.item.Item;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import lotr.common.fac.LOTRFaction;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.entity.ai.LOTREntityAIDrink;
import lotr.common.LOTRFoods;
import lotr.common.entity.ai.LOTREntityAIEat;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import lotr.common.entity.ai.LOTREntityAINPCFollowSpouse;
import lotr.common.entity.ai.LOTREntityAINPCFollowParent;
import lotr.common.entity.ai.LOTREntityAINPCMate;
import lotr.common.entity.ai.LOTREntityAINPCMarry;
import lotr.common.entity.ai.LOTREntityAIFollowHiringPlayer;
import lotr.common.entity.ai.LOTREntityAINPCAvoidEvilPlayer;
import lotr.common.entity.ai.LOTREntityAIHiredRemainStill;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.world.World;

public class LOTREntityDwarf extends LOTREntityNPC
{
    public LOTREntityDwarf(final World world) {
        super(world);
        this.setSize(0.5f, 1.5f);
        this.getNavigator().setAvoidsWater(true);
        this.getNavigator().setBreakDoors(true);
        ((EntityLiving)this).tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        ((EntityLiving)this).tasks.addTask(1, (EntityAIBase)new LOTREntityAIHiredRemainStill(this));
        ((EntityLiving)this).tasks.addTask(2, (EntityAIBase)new LOTREntityAINPCAvoidEvilPlayer(this, 8.0f, 1.5, 1.8));
        ((EntityLiving)this).tasks.addTask(3, this.getDwarfAttackAI());
        ((EntityLiving)this).tasks.addTask(4, (EntityAIBase)new LOTREntityAIFollowHiringPlayer(this));
        ((EntityLiving)this).tasks.addTask(5, (EntityAIBase)new LOTREntityAINPCMarry(this, 1.3));
        ((EntityLiving)this).tasks.addTask(6, (EntityAIBase)new LOTREntityAINPCMate(this, 1.3));
        ((EntityLiving)this).tasks.addTask(7, (EntityAIBase)new LOTREntityAINPCFollowParent(this, 1.4));
        ((EntityLiving)this).tasks.addTask(8, (EntityAIBase)new LOTREntityAINPCFollowSpouse(this, 1.1));
        ((EntityLiving)this).tasks.addTask(9, (EntityAIBase)new EntityAIOpenDoor((EntityLiving)this, true));
        ((EntityLiving)this).tasks.addTask(10, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0));
        ((EntityLiving)this).tasks.addTask(11, (EntityAIBase)new LOTREntityAIEat(this, this.getDwarfFoods(), 6000));
        ((EntityLiving)this).tasks.addTask(11, (EntityAIBase)new LOTREntityAIDrink(this, LOTRFoods.DWARF_DRINK, 6000));
        ((EntityLiving)this).tasks.addTask(12, (EntityAIBase)new EntityAIWatchClosest2((EntityLiving)this, (Class)EntityPlayer.class, 8.0f, 0.02f));
        ((EntityLiving)this).tasks.addTask(12, (EntityAIBase)new EntityAIWatchClosest2((EntityLiving)this, (Class)LOTREntityNPC.class, 5.0f, 0.02f));
        ((EntityLiving)this).tasks.addTask(13, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityLiving.class, 8.0f, 0.02f));
        ((EntityLiving)this).tasks.addTask(14, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.addTargetTasks(true);
        super.familyInfo.marriageEntityClass = LOTREntityDwarf.class;
        super.familyInfo.marriageRing = LOTRMod.dwarvenRing;
        super.familyInfo.marriageAlignmentRequired = 200.0f;
        super.familyInfo.marriageAchievement = LOTRAchievement.marryDwarf;
        super.familyInfo.potentialMaxChildren = 3;
        super.familyInfo.timeToMature = 72000;
        super.familyInfo.breedingDelay = 48000;
    }
    
    protected EntityAIBase getDwarfAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.4, false);
    }
    
    protected LOTRFoods getDwarfFoods() {
        return LOTRFoods.DWARF;
    }
    
    @Override
    public void setupNPCGender() {
        super.familyInfo.setMale(true);
    }
    
    @Override
    public void setupNPCName() {
        super.familyInfo.setName(LOTRNames.getDwarfName(((Entity)this).rand, super.familyInfo.isMale()));
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(26.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
    }
    
    @Override
    public IEntityLivingData initCreatureForHire(IEntityLivingData data) {
        data = super.initCreatureForHire(data);
        data = this.onSpawnWithEgg(data);
        if (this.getClass() == super.familyInfo.marriageEntityClass && ((Entity)this).rand.nextInt(3) == 0) {
            super.familyInfo.setMale(false);
            this.setupNPCName();
        }
        return data;
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerDwarven));
        super.npcItemsInv.setIdleItem(null);
        return data;
    }
    
    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.DWARF;
    }
    
    @Override
    public String getNPCName() {
        return super.familyInfo.getName();
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        if (nbt.hasKey("DwarfName")) {
            super.familyInfo.setName(nbt.getString("DwarfName"));
        }
    }
    
    @Override
    public void createNPCChildName(final LOTREntityNPC maleParent, final LOTREntityNPC femaleParent) {
        super.familyInfo.setName(LOTRNames.getDwarfChildNameForParent(((Entity)this).rand, super.familyInfo.isMale(), (LOTREntityDwarf)maleParent));
    }
    
    @Override
    public boolean interact(final EntityPlayer entityplayer) {
        return super.familyInfo.interact(entityplayer) || super.interact(entityplayer);
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
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killDwarf;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 1.0f;
    }
    
    @Override
    protected void dropFewItems(final boolean flag, final int i) {
        super.dropFewItems(flag, i);
        for (int bones = ((Entity)this).rand.nextInt(2) + ((Entity)this).rand.nextInt(i + 1), l = 0; l < bones; ++l) {
            this.func_145779_a(LOTRMod.dwarfBone, 1);
        }
        if (((Entity)this).rand.nextInt(4) == 0) {
            this.dropChestContents(this.getLarderDrops(), 1, 2 + i);
        }
        if (((Entity)this).rand.nextInt(8) == 0) {
            this.dropChestContents(this.getGenericDrops(), 1, 2 + i);
        }
        if (flag) {
            int rareDropChance = 20 - i * 4;
            rareDropChance = Math.max(rareDropChance, 1);
            if (((Entity)this).rand.nextInt(rareDropChance) == 0) {
                final int randDrop = ((Entity)this).rand.nextInt(4);
                switch (randDrop) {
                    case 0: {
                        this.entityDropItem(new ItemStack(Items.iron_ingot), 0.0f);
                        break;
                    }
                    case 1: {
                        this.entityDropItem(new ItemStack(this.getDwarfSteelDrop()), 0.0f);
                        break;
                    }
                    case 2: {
                        this.entityDropItem(new ItemStack(Items.gold_nugget, 1 + ((Entity)this).rand.nextInt(3)), 0.0f);
                        break;
                    }
                    case 3: {
                        this.entityDropItem(new ItemStack(LOTRMod.silverNugget, 1 + ((Entity)this).rand.nextInt(3)), 0.0f);
                        break;
                    }
                }
            }
            int mithrilBookChance = 40 - i * 5;
            mithrilBookChance = Math.max(mithrilBookChance, 1);
            if (((Entity)this).rand.nextInt(mithrilBookChance) == 0) {
                this.entityDropItem(new ItemStack(LOTRMod.mithrilBook), 0.0f);
            }
        }
    }
    
    protected Item getDwarfSteelDrop() {
        return LOTRMod.dwarfSteel;
    }
    
    protected LOTRChestContents getLarderDrops() {
        return LOTRChestContents.DWARF_HOUSE_LARDER;
    }
    
    protected LOTRChestContents getGenericDrops() {
        return LOTRChestContents.DWARVEN_TOWER;
    }
    
    @Override
    public boolean getCanSpawnHere() {
        return super.getCanSpawnHere() && (super.liftSpawnRestrictions || this.canDwarfSpawnHere());
    }
    
    protected boolean canDwarfSpawnHere() {
        final int i = MathHelper.floor_double(((Entity)this).posX);
        final int j = MathHelper.floor_double(((Entity)this).boundingBox.minY);
        final int k = MathHelper.floor_double(((Entity)this).posZ);
        if (((Entity)this).rand.nextInt(200) == 0) {
            return this.canDwarfSpawnAboveGround();
        }
        return j < 60 && ((Entity)this).worldObj.getBlock(i, j - 1, k).getMaterial() == Material.rock && !((Entity)this).worldObj.canBlockSeeTheSky(i, j, k) && ((Entity)this).worldObj.getSavedLightValue(EnumSkyBlock.Block, i, j, k) >= 10;
    }
    
    protected boolean canDwarfSpawnAboveGround() {
        return true;
    }
    
    @Override
    public float getBlockPathWeight(final int i, final int j, final int k) {
        float f = 0.0f;
        final BiomeGenBase biome = ((Entity)this).worldObj.getBiomeGenForCoords(i, k);
        if (biome instanceof LOTRBiomeGenIronHills || biome instanceof LOTRBiomeGenErebor || biome instanceof LOTRBiomeGenBlueMountains || biome instanceof LOTRBiomeGenRedMountains) {
            f += 20.0f;
        }
        return f;
    }
    
    public int getMaxSpawnedInChunk() {
        return 6;
    }
    
    protected float getSoundPitch() {
        float f = super.getSoundPitch();
        if (!super.familyInfo.isMale()) {
            f *= 1.4f;
        }
        return f;
    }
    
    public String getHurtSound() {
        return "lotr:dwarf.hurt";
    }
    
    public String getDeathSound() {
        return "lotr:dwarf.hurt";
    }
    
    @Override
    public String getAttackSound() {
        return "lotr:dwarf.attack";
    }
    
    @Override
    public void onKillEntity(final EntityLivingBase entity) {
        super.onKillEntity(entity);
        this.playSound("lotr:dwarf.kill", this.getSoundVolume(), this.getSoundPitch());
    }
    
    @Override
    protected LOTRAchievement getTalkAchievement() {
        if (!super.familyInfo.isMale()) {
            return LOTRAchievement.talkDwarfWoman;
        }
        return super.getTalkAchievement();
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (!this.isFriendly(entityplayer)) {
            return this.isChild() ? "dwarf/child/hostile" : "dwarf/dwarf/hostile";
        }
        if (super.hiredNPCInfo.getHiringPlayer() == entityplayer) {
            return "dwarf/dwarf/hired";
        }
        return this.isChild() ? "dwarf/child/friendly" : "dwarf/dwarf/friendly";
    }
    
    @Override
    public LOTRMiniQuest createMiniQuest() {
        return LOTRMiniQuestFactory.DURIN.createQuest(this);
    }
    
    @Override
    public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
        return LOTRMiniQuestFactory.DURIN;
    }
    
    @Override
    public void onArtificalSpawn() {
        if (this.getClass() == super.familyInfo.marriageEntityClass) {
            if (((Entity)this).rand.nextInt(3) == 0) {
                super.familyInfo.setMale(false);
                this.setupNPCName();
            }
            if (((Entity)this).rand.nextInt(20) == 0) {
                super.familyInfo.setChild();
            }
        }
    }
}
