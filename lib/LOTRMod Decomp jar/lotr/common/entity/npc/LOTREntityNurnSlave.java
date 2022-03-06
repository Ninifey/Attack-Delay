// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.world.biome.LOTRBiomeGenNurn;
import net.minecraft.entity.Entity;
import lotr.common.fac.LOTRFaction;
import net.minecraft.init.Items;
import net.minecraftforge.common.IPlantable;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import lotr.common.entity.ai.LOTREntityAINPCHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.entity.ai.LOTREntityAIDrink;
import lotr.common.entity.ai.LOTREntityAIEat;
import lotr.common.LOTRFoods;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import lotr.common.entity.ai.LOTREntityAIFarm;
import lotr.common.entity.ai.LOTREntityAIFollowHiringPlayer;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.world.World;

public class LOTREntityNurnSlave extends LOTREntityMan implements LOTRFarmhand
{
    private boolean isFree;
    
    public LOTREntityNurnSlave(final World world) {
        super(world);
        this.isFree = false;
        this.setSize(0.6f, 1.8f);
        this.getNavigator().setAvoidsWater(true);
        this.getNavigator().setBreakDoors(true);
        ((EntityLiving)this).tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        ((EntityLiving)this).tasks.addTask(1, (EntityAIBase)new LOTREntityAIAttackOnCollide(this, 1.3, false));
        ((EntityLiving)this).tasks.addTask(2, (EntityAIBase)new LOTREntityAIFollowHiringPlayer(this));
        ((EntityLiving)this).tasks.addTask(3, (EntityAIBase)new LOTREntityAIFarm(this, 1.0, 1.0f));
        ((EntityLiving)this).tasks.addTask(4, (EntityAIBase)new EntityAIOpenDoor((EntityLiving)this, true));
        ((EntityLiving)this).tasks.addTask(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0));
        ((EntityLiving)this).tasks.addTask(6, (EntityAIBase)new LOTREntityAIEat(this, LOTRFoods.NURN_SLAVE, 12000));
        ((EntityLiving)this).tasks.addTask(6, (EntityAIBase)new LOTREntityAIDrink(this, LOTRFoods.NURN_SLAVE_DRINK, 12000));
        ((EntityLiving)this).tasks.addTask(7, (EntityAIBase)new EntityAIWatchClosest2((EntityLiving)this, (Class)EntityPlayer.class, 8.0f, 0.02f));
        ((EntityLiving)this).tasks.addTask(7, (EntityAIBase)new EntityAIWatchClosest2((EntityLiving)this, (Class)LOTREntityNPC.class, 5.0f, 0.02f));
        ((EntityLiving)this).tasks.addTask(8, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityLiving.class, 8.0f, 0.02f));
        ((EntityLiving)this).tasks.addTask(9, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        ((EntityLiving)this).targetTasks.taskEntries.clear();
        ((EntityLiving)this).targetTasks.addTask(1, (EntityAIBase)new LOTREntityAINPCHurtByTarget(this, false));
    }
    
    @Override
    public void setupNPCGender() {
        super.familyInfo.setMale(((Entity)this).rand.nextBoolean());
    }
    
    @Override
    public void setupNPCName() {
        super.familyInfo.setName(LOTRNames.getGondorName(((Entity)this).rand, super.familyInfo.isMale()));
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
        super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.hoeOrc));
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        return data;
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
    public IPlantable getUnhiredSeeds() {
        return (IPlantable)Items.wheat_seeds;
    }
    
    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.GONDOR;
    }
    
    @Override
    public LOTRFaction getHiringFaction() {
        if (!this.isFree) {
            return LOTRFaction.MORDOR;
        }
        return super.getHiringFaction();
    }
    
    @Override
    public boolean canBeFreelyTargetedBy(final EntityLiving attacker) {
        return (this.isFree || LOTRMod.getNPCFaction((Entity)attacker).isBadRelation(this.getHiringFaction())) && super.canBeFreelyTargetedBy(attacker);
    }
    
    @Override
    public String getNPCName() {
        return super.familyInfo.getName();
    }
    
    @Override
    protected void dropFewItems(final boolean flag, final int i) {
        super.dropFewItems(flag, i);
        for (int bones = ((Entity)this).rand.nextInt(2) + ((Entity)this).rand.nextInt(i + 1), l = 0; l < bones; ++l) {
            this.func_145779_a(Items.bone, 1);
        }
    }
    
    @Override
    public float getBlockPathWeight(final int i, final int j, final int k) {
        float f = 0.0f;
        final BiomeGenBase biome = ((Entity)this).worldObj.getBiomeGenForCoords(i, k);
        if (!this.isFree && biome instanceof LOTRBiomeGenNurn) {
            f += 20.0f;
        }
        return f;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (this.isFree) {
            if (!this.isFriendly(entityplayer)) {
                return "mordor/nurnSlave/free_hostile";
            }
            if (super.hiredNPCInfo.getHiringPlayer() == entityplayer) {
                return "mordor/nurnSlave/free_hired";
            }
            return "mordor/nurnSlave/free_friendly";
        }
        else {
            if (super.hiredNPCInfo.getHiringPlayer() == entityplayer) {
                return "mordor/nurnSlave/hired";
            }
            return "mordor/nurnSlave/neutral";
        }
    }
}
