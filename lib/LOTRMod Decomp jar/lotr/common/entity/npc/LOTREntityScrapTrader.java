// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import net.minecraft.init.Items;
import net.minecraft.util.DamageSource;
import lotr.common.LOTRDimension;
import lotr.common.fac.LOTRFaction;
import lotr.common.item.LOTRItemLeatherHat;
import java.awt.Color;
import net.minecraft.util.MathHelper;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.entity.ai.LOTREntityAIDrink;
import lotr.common.entity.ai.LOTREntityAIEat;
import lotr.common.LOTRFoods;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.world.World;

public class LOTREntityScrapTrader extends LOTREntityMan implements LOTRTravellingTrader, LOTRTradeable.Smith
{
    private int timeUntilFadeOut;
    public static final int maxFadeoutTick = 60;
    
    public LOTREntityScrapTrader(final World world) {
        super(world);
        this.setSize(0.6f, 1.8f);
        this.getNavigator().setAvoidsWater(true);
        this.getNavigator().setBreakDoors(true);
        ((EntityLiving)this).tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        ((EntityLiving)this).tasks.addTask(1, (EntityAIBase)new LOTREntityAIAttackOnCollide(this, 1.3, true));
        ((EntityLiving)this).tasks.addTask(2, (EntityAIBase)new EntityAIOpenDoor((EntityLiving)this, true));
        ((EntityLiving)this).tasks.addTask(3, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0));
        ((EntityLiving)this).tasks.addTask(4, (EntityAIBase)new LOTREntityAIEat(this, LOTRFoods.DUNLENDING, 8000));
        ((EntityLiving)this).tasks.addTask(4, (EntityAIBase)new LOTREntityAIDrink(this, LOTRFoods.DUNLENDING_DRINK, 8000));
        ((EntityLiving)this).tasks.addTask(5, (EntityAIBase)new EntityAIWatchClosest2((EntityLiving)this, (Class)EntityPlayer.class, 10.0f, 0.1f));
        ((EntityLiving)this).tasks.addTask(5, (EntityAIBase)new EntityAIWatchClosest2((EntityLiving)this, (Class)LOTREntityNPC.class, 5.0f, 0.05f));
        ((EntityLiving)this).tasks.addTask(6, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityLiving.class, 8.0f, 0.02f));
        ((EntityLiving)this).tasks.addTask(7, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.addTargetTasks(false);
    }
    
    @Override
    public LOTRTradeEntries getBuyPool() {
        return LOTRTradeEntries.SCRAP_TRADER_BUY;
    }
    
    @Override
    public LOTRTradeEntries getSellPool() {
        return LOTRTradeEntries.SCRAP_TRADER_SELL;
    }
    
    public void entityInit() {
        super.entityInit();
        ((Entity)this).dataWatcher.addObject(20, (Object)(-1));
    }
    
    public int getFadeoutTick() {
        return ((Entity)this).dataWatcher.getWatchableObjectInt(20);
    }
    
    public void setFadeoutTick(final int i) {
        ((Entity)this).dataWatcher.updateObject(20, (Object)i);
    }
    
    public float getFadeoutProgress(final float f) {
        final int i = this.getFadeoutTick();
        if (i >= 0) {
            return (60 - i + f) / 60.0f;
        }
        return 0.0f;
    }
    
    @Override
    public void setupNPCGender() {
        super.familyInfo.setMale(true);
    }
    
    @Override
    public void setupNPCName() {
        final int i = ((Entity)this).rand.nextInt(3);
        if (i == 0) {
            super.familyInfo.setName(LOTRNames.getDunlendingName(((Entity)this).rand, super.familyInfo.isMale()));
        }
        else if (i == 1) {
            super.familyInfo.setName(LOTRNames.getRohirricName(((Entity)this).rand, super.familyInfo.isMale()));
        }
        else if (i == 2) {
            super.familyInfo.setName(LOTRNames.getGondorName(((Entity)this).rand, super.familyInfo.isMale()));
        }
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        final int weapon = ((Entity)this).rand.nextInt(2);
        if (weapon == 0) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerIron));
        }
        else if (weapon == 1) {
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.daggerBronze));
        }
        super.npcItemsInv.setIdleItem(new ItemStack(LOTRMod.shireHeather));
        final ItemStack hat = new ItemStack(LOTRMod.leatherHat);
        float h = 0.06111111f;
        float s = MathHelper.randomFloatClamp(((Entity)this).rand, 0.0f, 0.5f);
        float b = MathHelper.randomFloatClamp(((Entity)this).rand, 0.0f, 0.5f);
        final int hatColor = Color.HSBtoRGB(h, s, b) & 0xFFFFFF;
        LOTRItemLeatherHat.setHatColor(hat, hatColor);
        if (((Entity)this).rand.nextInt(3) == 0) {
            h = ((Entity)this).rand.nextFloat();
            s = MathHelper.randomFloatClamp(((Entity)this).rand, 0.7f, 0.9f);
            b = MathHelper.randomFloatClamp(((Entity)this).rand, 0.8f, 1.0f);
        }
        else {
            h = 0.0f;
            s = 0.0f;
            b = ((Entity)this).rand.nextFloat();
        }
        final int featherColor = Color.HSBtoRGB(h, s, b) & 0xFFFFFF;
        LOTRItemLeatherHat.setFeatherColor(hat, featherColor);
        this.setCurrentItemOrArmor(4, hat);
        return data;
    }
    
    @Override
    public String getNPCName() {
        return super.familyInfo.getName();
    }
    
    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.UNALIGNED;
    }
    
    @Override
    public LOTREntityNPC createTravellingEscort() {
        return null;
    }
    
    @Override
    public String getDepartureSpeech() {
        return "misc/scrapTrader/departure";
    }
    
    public void onAttackModeChange(final AttackMode mode, final boolean mounted) {
        if (mode == AttackMode.IDLE) {
            this.setCurrentItemOrArmor(0, super.npcItemsInv.getIdleItem());
        }
        else {
            this.setCurrentItemOrArmor(0, super.npcItemsInv.getMeleeWeapon());
        }
    }
    
    public int getTotalArmorValue() {
        return 5;
    }
    
    @Override
    public boolean interact(final EntityPlayer entityplayer) {
        final boolean flag = super.interact(entityplayer);
        if (flag && !((Entity)this).worldObj.isClient && LOTRDimension.getCurrentDimension(((Entity)this).worldObj) == LOTRDimension.UTUMNO && this.timeUntilFadeOut <= 0) {
            this.timeUntilFadeOut = 100;
        }
        return flag;
    }
    
    @Override
    public boolean canBeFreelyTargetedBy(final EntityLiving attacker) {
        return LOTRDimension.getCurrentDimension(((Entity)this).worldObj) != LOTRDimension.UTUMNO && super.canBeFreelyTargetedBy(attacker);
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damagesource, final float f) {
        if (damagesource.getEntity() != null && LOTRDimension.getCurrentDimension(((Entity)this).worldObj) == LOTRDimension.UTUMNO) {
            if (!((Entity)this).worldObj.isClient && this.getFadeoutTick() < 0) {
                this.setFadeoutTick(60);
            }
            return false;
        }
        return super.attackEntityFrom(damagesource, f);
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!((Entity)this).worldObj.isClient && LOTRDimension.getCurrentDimension(((Entity)this).worldObj) == LOTRDimension.UTUMNO) {
            if (this.timeUntilFadeOut > 0) {
                --this.timeUntilFadeOut;
                if (this.timeUntilFadeOut <= 0) {
                    this.setFadeoutTick(60);
                }
            }
            if (this.getFadeoutTick() > 0) {
                this.setFadeoutTick(this.getFadeoutTick() - 1);
                if (this.getFadeoutTick() <= 0) {
                    this.setDead();
                }
            }
        }
    }
    
    @Override
    public float getAlignmentBonus() {
        return 0.0f;
    }
    
    @Override
    protected void dropFewItems(final boolean flag, final int i) {
        super.dropFewItems(flag, i);
        for (int bones = ((Entity)this).rand.nextInt(2) + ((Entity)this).rand.nextInt(i + 1), l = 0; l < bones; ++l) {
            this.func_145779_a(Items.bone, 1);
        }
    }
    
    @Override
    public boolean canTradeWith(final EntityPlayer entityplayer) {
        return this.isFriendly(entityplayer) && LOTRDimension.getCurrentDimension(((Entity)this).worldObj) != LOTRDimension.UTUMNO;
    }
    
    @Override
    public void onPlayerTrade(final EntityPlayer entityplayer, final LOTRTradeEntries.TradeType type, final ItemStack itemstack) {
        LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.tradeScrapTrader);
    }
    
    @Override
    public boolean shouldTraderRespawn() {
        return false;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (!this.isFriendly(entityplayer)) {
            return "misc/scrapTrader/hostile";
        }
        if (LOTRDimension.getCurrentDimension(((Entity)this).worldObj) == LOTRDimension.UTUMNO) {
            return "misc/scrapTrader/utumno";
        }
        return "misc/scrapTrader/friendly";
    }
    
    public String getSmithSpeechBank() {
        return "misc/scrapTrader/smith";
    }
}
