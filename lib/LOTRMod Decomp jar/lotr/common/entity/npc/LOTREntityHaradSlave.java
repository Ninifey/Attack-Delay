// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.entity.Entity;
import lotr.common.fac.LOTRFaction;
import net.minecraft.init.Items;
import net.minecraftforge.common.IPlantable;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.MathHelper;
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
import net.minecraft.item.Item;

public class LOTREntityHaradSlave extends LOTREntityMan implements LOTRFarmhand
{
    public Item seedsItem;
    
    public LOTREntityHaradSlave(final World world) {
        super(world);
        this.setSize(0.6f, 1.8f);
        this.getNavigator().setAvoidsWater(true);
        this.getNavigator().setBreakDoors(true);
        ((EntityLiving)this).tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        ((EntityLiving)this).tasks.addTask(1, (EntityAIBase)new LOTREntityAIAttackOnCollide(this, 1.3, false));
        ((EntityLiving)this).tasks.addTask(2, (EntityAIBase)new LOTREntityAIFollowHiringPlayer(this));
        ((EntityLiving)this).tasks.addTask(3, (EntityAIBase)new LOTREntityAIFarm(this, 1.0, 1.0f));
        ((EntityLiving)this).tasks.addTask(4, (EntityAIBase)new EntityAIOpenDoor((EntityLiving)this, true));
        ((EntityLiving)this).tasks.addTask(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0));
        ((EntityLiving)this).tasks.addTask(6, (EntityAIBase)new LOTREntityAIEat(this, LOTRFoods.HARAD_SLAVE, 12000));
        ((EntityLiving)this).tasks.addTask(6, (EntityAIBase)new LOTREntityAIDrink(this, LOTRFoods.HARAD_SLAVE_DRINK, 8000));
        ((EntityLiving)this).tasks.addTask(7, (EntityAIBase)new EntityAIWatchClosest2((EntityLiving)this, (Class)EntityPlayer.class, 8.0f, 0.02f));
        ((EntityLiving)this).tasks.addTask(7, (EntityAIBase)new EntityAIWatchClosest2((EntityLiving)this, (Class)LOTREntityNPC.class, 5.0f, 0.02f));
        ((EntityLiving)this).tasks.addTask(8, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityLiving.class, 8.0f, 0.02f));
        ((EntityLiving)this).tasks.addTask(9, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        ((EntityLiving)this).targetTasks.taskEntries.clear();
        ((EntityLiving)this).targetTasks.addTask(1, (EntityAIBase)new LOTREntityAINPCHurtByTarget(this, false));
    }
    
    @Override
    public void setupNPCGender() {
        super.familyInfo.setMale(true);
    }
    
    @Override
    public void setupNPCName() {
        ((Entity)this).dataWatcher.addObject(20, (Object)0);
        final float f = ((Entity)this).rand.nextFloat();
        if (f < 0.05f) {
            this.setSlaveType(SlaveType.TAURETHRIM);
        }
        else if (f < 0.2f) {
            this.setSlaveType(SlaveType.MORWAITH);
        }
        else if (f < 0.7f) {
            this.setSlaveType(SlaveType.NEAR_HARAD);
        }
        else {
            this.setSlaveType(SlaveType.GONDOR);
        }
        final SlaveType type = this.getSlaveType();
        if (type == SlaveType.GONDOR) {
            super.familyInfo.setName(LOTRNames.getGondorName(((Entity)this).rand, super.familyInfo.isMale()));
        }
        else if (type == SlaveType.NEAR_HARAD) {
            if (((Entity)this).rand.nextBoolean()) {
                super.familyInfo.setName(LOTRNames.getHarnedorName(((Entity)this).rand, super.familyInfo.isMale()));
            }
            else {
                super.familyInfo.setName(LOTRNames.getNomadName(((Entity)this).rand, super.familyInfo.isMale()));
            }
        }
        else if (type == SlaveType.MORWAITH) {
            super.familyInfo.setName(LOTRNames.getMoredainName(((Entity)this).rand, super.familyInfo.isMale()));
        }
        else if (type == SlaveType.TAURETHRIM) {
            super.familyInfo.setName(LOTRNames.getTauredainName(((Entity)this).rand, super.familyInfo.isMale()));
        }
    }
    
    public SlaveType getSlaveType() {
        int i = ((Entity)this).dataWatcher.getWatchableObjectByte(20);
        i = MathHelper.clamp_int(i, 0, SlaveType.values().length);
        return SlaveType.values()[i];
    }
    
    public void setSlaveType(final SlaveType t) {
        final int i = t.ordinal();
        ((Entity)this).dataWatcher.updateObject(20, (Object)(byte)i);
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
        super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.hoeBronze));
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
        if (this.seedsItem == null) {
            return (IPlantable)Items.wheat_seeds;
        }
        return (IPlantable)this.seedsItem;
    }
    
    @Override
    public LOTRFaction getFaction() {
        return this.getSlaveType().faction;
    }
    
    @Override
    public LOTRFaction getHiringFaction() {
        return LOTRFaction.NEAR_HARAD;
    }
    
    @Override
    public boolean canBeFreelyTargetedBy(final EntityLiving attacker) {
        return LOTRMod.getNPCFaction((Entity)attacker).isBadRelation(this.getHiringFaction()) && super.canBeFreelyTargetedBy(attacker);
    }
    
    @Override
    public String getNPCName() {
        return super.familyInfo.getName();
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setString("SlaveType", this.getSlaveType().saveName());
        if (this.seedsItem != null) {
            nbt.setInteger("SeedsID", Item.getIdFromItem(this.seedsItem));
        }
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        if (nbt.hasKey("SlaveType")) {
            final SlaveType type = SlaveType.forName(nbt.getString("SlaveType"));
            if (type != null) {
                this.setSlaveType(type);
            }
        }
        if (nbt.hasKey("SeedsID")) {
            final Item item = Item.getItemById(nbt.getInteger("SeedsID"));
            if (item != null && item instanceof IPlantable) {
                this.seedsItem = item;
            }
        }
    }
    
    @Override
    protected void dropFewItems(final boolean flag, final int i) {
        super.dropFewItems(flag, i);
        for (int bones = ((Entity)this).rand.nextInt(2) + ((Entity)this).rand.nextInt(i + 1), l = 0; l < bones; ++l) {
            this.func_145779_a(Items.bone, 1);
        }
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (super.hiredNPCInfo.getHiringPlayer() == entityplayer) {
            return "nearHarad/slave/hired";
        }
        return "nearHarad/slave/neutral";
    }
    
    public enum SlaveType
    {
        GONDOR(LOTRFaction.GONDOR, "gondor"), 
        NEAR_HARAD(LOTRFaction.NEAR_HARAD, "nearHarad"), 
        MORWAITH(LOTRFaction.MOREDAIN, "morwaith"), 
        TAURETHRIM(LOTRFaction.TAUREDAIN, "taurethrim");
        
        public LOTRFaction faction;
        public String skinDir;
        
        private SlaveType(final LOTRFaction f, final String s) {
            this.faction = f;
            this.skinDir = s;
        }
        
        public String saveName() {
            return this.name();
        }
        
        public static SlaveType forName(final String s) {
            for (final SlaveType type : values()) {
                if (type.saveName().equals(s)) {
                    return type;
                }
            }
            return null;
        }
    }
}
