// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import lotr.common.LOTRMod;
import lotr.common.quest.LOTRMiniQuestFactory;
import lotr.common.quest.LOTRMiniQuest;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.world.biome.LOTRBiomeGenDale;
import net.minecraft.util.MathHelper;
import lotr.common.LOTRAchievement;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Items;
import net.minecraft.util.StatCollector;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
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
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;

public class LOTREntityDaleMan extends LOTREntityMan
{
    private static ItemStack[] weapons;
    
    public LOTREntityDaleMan(final World world) {
        super(world);
        this.setSize(0.6f, 1.8f);
        this.getNavigator().setAvoidsWater(true);
        this.getNavigator().setBreakDoors(true);
        ((EntityLiving)this).tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        ((EntityLiving)this).tasks.addTask(1, (EntityAIBase)new LOTREntityAIHiredRemainStill(this));
        ((EntityLiving)this).tasks.addTask(2, this.createDaleAttackAI());
        ((EntityLiving)this).tasks.addTask(3, (EntityAIBase)new LOTREntityAIFollowHiringPlayer(this));
        ((EntityLiving)this).tasks.addTask(4, (EntityAIBase)new EntityAIOpenDoor((EntityLiving)this, true));
        ((EntityLiving)this).tasks.addTask(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0));
        ((EntityLiving)this).tasks.addTask(6, (EntityAIBase)new LOTREntityAIEat(this, LOTRFoods.DALE, 8000));
        ((EntityLiving)this).tasks.addTask(6, (EntityAIBase)new LOTREntityAIDrink(this, LOTRFoods.DALE_DRINK, 8000));
        ((EntityLiving)this).tasks.addTask(7, (EntityAIBase)new EntityAIWatchClosest2((EntityLiving)this, (Class)EntityPlayer.class, 8.0f, 0.02f));
        ((EntityLiving)this).tasks.addTask(7, (EntityAIBase)new EntityAIWatchClosest2((EntityLiving)this, (Class)LOTREntityNPC.class, 5.0f, 0.02f));
        ((EntityLiving)this).tasks.addTask(8, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityLiving.class, 8.0f, 0.02f));
        ((EntityLiving)this).tasks.addTask(9, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.addTargetTasks(false);
    }
    
    protected EntityAIBase createDaleAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.3, false);
    }
    
    @Override
    public void setupNPCGender() {
        super.familyInfo.setMale(((Entity)this).rand.nextBoolean());
    }
    
    @Override
    public void setupNPCName() {
        super.familyInfo.setName(LOTRNames.getDalishName(((Entity)this).rand, super.familyInfo.isMale()));
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
        this.getEntityAttribute(LOTREntityNPC.npcRangedAccuracy).setBaseValue(0.5);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        final int i = ((Entity)this).rand.nextInt(LOTREntityDaleMan.weapons.length);
        super.npcItemsInv.setMeleeWeapon(LOTREntityDaleMan.weapons[i].copy());
        super.npcItemsInv.setIdleItem(null);
        return data;
    }
    
    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.DALE;
    }
    
    @Override
    public String getNPCName() {
        return super.familyInfo.getName();
    }
    
    @Override
    public String getNPCFormattedName(final String npcName, final String entityName) {
        if (this.getClass() == LOTREntityDaleMan.class) {
            return StatCollector.translateToLocalFormatted("entity.lotr.DaleMan.entityName", new Object[] { npcName });
        }
        return super.getNPCFormattedName(npcName, entityName);
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
    protected void dropFewItems(final boolean flag, final int i) {
        super.dropFewItems(flag, i);
        for (int bones = ((Entity)this).rand.nextInt(2) + ((Entity)this).rand.nextInt(i + 1), l = 0; l < bones; ++l) {
            this.func_145779_a(Items.bone, 1);
        }
        this.dropDaleItems(flag, i);
    }
    
    protected void dropDaleItems(final boolean flag, final int i) {
        if (((Entity)this).rand.nextInt(6) == 0) {
            this.dropChestContents(LOTRChestContents.DALE_HOUSE, 1, 2 + i);
        }
    }
    
    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killDalish;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 1.0f;
    }
    
    @Override
    public boolean getCanSpawnHere() {
        if (super.getCanSpawnHere()) {
            if (super.liftSpawnRestrictions) {
                return true;
            }
            final int i = MathHelper.floor_double(((Entity)this).posX);
            final int j = MathHelper.floor_double(((Entity)this).boundingBox.minY);
            final int k = MathHelper.floor_double(((Entity)this).posZ);
            if (j > 62 && ((Entity)this).worldObj.getBlock(i, j - 1, k) == ((Entity)this).worldObj.getBiomeGenForCoords(i, k).topBlock) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public float getBlockPathWeight(final int i, final int j, final int k) {
        float f = 0.0f;
        final BiomeGenBase biome = ((Entity)this).worldObj.getBiomeGenForCoords(i, k);
        if (biome instanceof LOTRBiomeGenDale) {
            f += 20.0f;
        }
        return f;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (this.isFriendly(entityplayer)) {
            return "dale/man/friendly";
        }
        return "dale/man/hostile";
    }
    
    @Override
    public LOTRMiniQuest createMiniQuest() {
        return LOTRMiniQuestFactory.DALE.createQuest(this);
    }
    
    @Override
    public LOTRMiniQuestFactory getBountyHelpSpeechDir() {
        return LOTRMiniQuestFactory.DALE;
    }
    
    static {
        LOTREntityDaleMan.weapons = new ItemStack[] { new ItemStack(LOTRMod.daggerDale), new ItemStack(LOTRMod.daggerIron), new ItemStack(LOTRMod.daggerBronze), new ItemStack(Items.iron_axe), new ItemStack(LOTRMod.axeBronze), new ItemStack(Items.stone_axe) };
    }
}
