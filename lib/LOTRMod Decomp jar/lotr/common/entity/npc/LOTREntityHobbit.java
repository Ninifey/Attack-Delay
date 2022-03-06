// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.quest.LOTRMiniQuest;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.world.biome.LOTRBiomeGenShire;
import net.minecraft.util.MathHelper;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.LOTRLevelData;
import net.minecraft.potion.Potion;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.SharedMonsterAttributes;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRMod;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.entity.ai.LOTREntityAIHobbitSmoke;
import lotr.common.entity.ai.LOTREntityAIDrink;
import lotr.common.entity.ai.LOTREntityAIEat;
import lotr.common.LOTRFoods;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import lotr.common.entity.ai.LOTREntityAINPCFollowSpouse;
import lotr.common.entity.ai.LOTREntityAINPCFollowParent;
import lotr.common.entity.ai.LOTREntityAINPCMate;
import lotr.common.entity.ai.LOTREntityAINPCMarry;
import lotr.common.entity.ai.LOTREntityAIHobbitChildFollowGoodPlayer;
import lotr.common.entity.ai.LOTREntityAINPCAvoidEvilPlayer;
import net.minecraft.entity.ai.EntityAIPanic;
import lotr.common.entity.ai.LOTREntityAIAvoidHuorn;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.world.World;

public class LOTREntityHobbit extends LOTREntityMan
{
    public LOTREntityHobbit(final World world) {
        super(world);
        this.setSize(0.45f, 1.2f);
        this.getNavigator().setAvoidsWater(true);
        this.getNavigator().setBreakDoors(true);
        ((EntityLiving)this).tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        ((EntityLiving)this).tasks.addTask(1, (EntityAIBase)new EntityAIAvoidEntity((EntityCreature)this, (Class)LOTREntityOrc.class, 12.0f, 1.5, 1.8));
        ((EntityLiving)this).tasks.addTask(1, (EntityAIBase)new EntityAIAvoidEntity((EntityCreature)this, (Class)LOTREntityWarg.class, 12.0f, 1.5, 1.8));
        ((EntityLiving)this).tasks.addTask(1, (EntityAIBase)new EntityAIAvoidEntity((EntityCreature)this, (Class)LOTREntityTroll.class, 12.0f, 1.5, 1.8));
        ((EntityLiving)this).tasks.addTask(1, (EntityAIBase)new EntityAIAvoidEntity((EntityCreature)this, (Class)LOTREntitySpiderBase.class, 12.0f, 1.5, 1.8));
        ((EntityLiving)this).tasks.addTask(1, (EntityAIBase)new LOTREntityAIAvoidHuorn(this, 12.0f, 1.5, 1.8));
        ((EntityLiving)this).tasks.addTask(2, (EntityAIBase)new EntityAIPanic((EntityCreature)this, 1.6));
        ((EntityLiving)this).tasks.addTask(3, (EntityAIBase)new LOTREntityAINPCAvoidEvilPlayer(this, 8.0f, 1.5, 1.8));
        ((EntityLiving)this).tasks.addTask(4, (EntityAIBase)new LOTREntityAIHobbitChildFollowGoodPlayer(this, 12.0f, 1.5));
        ((EntityLiving)this).tasks.addTask(5, (EntityAIBase)new LOTREntityAINPCMarry(this, 1.3));
        ((EntityLiving)this).tasks.addTask(6, (EntityAIBase)new LOTREntityAINPCMate(this, 1.3));
        ((EntityLiving)this).tasks.addTask(7, (EntityAIBase)new LOTREntityAINPCFollowParent(this, 1.4));
        ((EntityLiving)this).tasks.addTask(8, (EntityAIBase)new LOTREntityAINPCFollowSpouse(this, 1.1));
        ((EntityLiving)this).tasks.addTask(9, (EntityAIBase)new EntityAIOpenDoor((EntityLiving)this, true));
        ((EntityLiving)this).tasks.addTask(10, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.1));
        ((EntityLiving)this).tasks.addTask(11, (EntityAIBase)new LOTREntityAIEat(this, LOTRFoods.HOBBIT, 3000));
        ((EntityLiving)this).tasks.addTask(11, (EntityAIBase)new LOTREntityAIDrink(this, LOTRFoods.HOBBIT_DRINK, 3000));
        ((EntityLiving)this).tasks.addTask(11, (EntityAIBase)new LOTREntityAIHobbitSmoke(this, 4000));
        ((EntityLiving)this).tasks.addTask(12, (EntityAIBase)new EntityAIWatchClosest2((EntityLiving)this, (Class)EntityPlayer.class, 8.0f, 0.05f));
        ((EntityLiving)this).tasks.addTask(12, (EntityAIBase)new EntityAIWatchClosest2((EntityLiving)this, (Class)LOTREntityNPC.class, 5.0f, 0.05f));
        ((EntityLiving)this).tasks.addTask(13, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityLiving.class, 8.0f, 0.02f));
        ((EntityLiving)this).tasks.addTask(14, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        super.familyInfo.marriageEntityClass = LOTREntityHobbit.class;
        super.familyInfo.marriageRing = LOTRMod.hobbitRing;
        super.familyInfo.marriageAlignmentRequired = 100.0f;
        super.familyInfo.marriageAchievement = LOTRAchievement.marryHobbit;
        super.familyInfo.potentialMaxChildren = 4;
        super.familyInfo.timeToMature = 48000;
        super.familyInfo.breedingDelay = 24000;
    }
    
    @Override
    public void setupNPCGender() {
        super.familyInfo.setMale(((Entity)this).rand.nextBoolean());
    }
    
    @Override
    public void setupNPCName() {
        super.familyInfo.setName(LOTRNames.getHobbitName(((Entity)this).rand, super.familyInfo.isMale()));
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(16.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
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
        return LOTRFaction.HOBBIT;
    }
    
    @Override
    public String getNPCName() {
        return super.familyInfo.getName();
    }
    
    @Override
    public void changeNPCNameForMarriage(final LOTREntityNPC spouse) {
        if (super.familyInfo.isMale()) {
            LOTRNames.changeHobbitSurnameForMarriage(this, (LOTREntityHobbit)spouse);
        }
        else if (spouse.familyInfo.isMale()) {
            LOTRNames.changeHobbitSurnameForMarriage((LOTREntityHobbit)spouse, this);
        }
    }
    
    @Override
    public void createNPCChildName(final LOTREntityNPC maleParent, final LOTREntityNPC femaleParent) {
        super.familyInfo.setName(LOTRNames.getHobbitChildNameForParent(((Entity)this).rand, super.familyInfo.isMale(), (LOTREntityHobbit)maleParent));
    }
    
    @Override
    public boolean interact(final EntityPlayer entityplayer) {
        return super.familyInfo.interact(entityplayer) || super.interact(entityplayer);
    }
    
    @Override
    public boolean speakTo(final EntityPlayer entityplayer) {
        final boolean flag = super.speakTo(entityplayer);
        if (flag && this.isDrunkard() && entityplayer.isPotionActive(Potion.confusion.id)) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.speakToDrunkard);
        }
        return flag;
    }
    
    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killHobbit;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 1.0f;
    }
    
    @Override
    protected void dropFewItems(final boolean flag, final int i) {
        super.dropFewItems(flag, i);
        for (int bones = ((Entity)this).rand.nextInt(2) + ((Entity)this).rand.nextInt(i + 1), l = 0; l < bones; ++l) {
            this.func_145779_a(LOTRMod.hobbitBone, 1);
        }
        this.dropHobbitItems(flag, i);
    }
    
    protected void dropHobbitItems(final boolean flag, final int i) {
        if (((Entity)this).rand.nextInt(8) == 0) {
            this.dropChestContents(LOTRChestContents.HOBBIT_HOLE_STUDY, 1, 1 + i);
        }
        if (((Entity)this).rand.nextInt(4) == 0) {
            this.dropChestContents(LOTRChestContents.HOBBIT_HOLE_LARDER, 1, 2 + i);
        }
    }
    
    @Override
    protected int getExperiencePoints(final EntityPlayer entityplayer) {
        return 1 + ((Entity)this).rand.nextInt(3);
    }
    
    @Override
    public boolean getCanSpawnHere() {
        if (!super.getCanSpawnHere()) {
            return false;
        }
        if (super.liftSpawnRestrictions) {
            return true;
        }
        final int i = MathHelper.floor_double(((Entity)this).posX);
        final int j = MathHelper.floor_double(((Entity)this).boundingBox.minY);
        final int k = MathHelper.floor_double(((Entity)this).posZ);
        return j > 62 && ((Entity)this).worldObj.getBlock(i, j - 1, k) == ((Entity)this).worldObj.getBiomeGenForCoords(i, k).topBlock;
    }
    
    @Override
    public float getBlockPathWeight(final int i, final int j, final int k) {
        float f = 0.0f;
        final BiomeGenBase biome = ((Entity)this).worldObj.getBiomeGenForCoords(i, k);
        if (biome instanceof LOTRBiomeGenShire) {
            f += 20.0f;
        }
        return f;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (this.isDrunkard()) {
            return "hobbit/drunkard/neutral";
        }
        if (this.isFriendly(entityplayer)) {
            return this.isChild() ? "hobbit/child/friendly" : "hobbit/hobbit/friendly";
        }
        return this.isChild() ? "hobbit/child/hostile" : "hobbit/hobbit/hostile";
    }
    
    @Override
    public LOTRMiniQuest createMiniQuest() {
        return LOTRMiniQuestFactory.HOBBIT.createQuest(this);
    }
    
    @Override
    public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
        return LOTRMiniQuestFactory.HOBBIT;
    }
    
    @Override
    public void onArtificalSpawn() {
        if (this.getClass() == super.familyInfo.marriageEntityClass && ((Entity)this).rand.nextInt(10) == 0) {
            super.familyInfo.setChild();
        }
    }
}
