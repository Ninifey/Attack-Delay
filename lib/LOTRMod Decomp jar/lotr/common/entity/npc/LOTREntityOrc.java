// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import lotr.common.item.LOTRItemMug;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import java.util.List;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.LOTRMod;
import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;
import lotr.common.block.LOTRBlockOrcBomb;
import lotr.common.LOTRConfig;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.Potion;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import lotr.common.entity.animal.LOTREntityRabbit;
import lotr.common.entity.ai.LOTREntityAIOrcSkirmish;
import lotr.common.entity.ai.LOTREntityAINearestAttackableTargetBasic;
import lotr.common.entity.ai.LOTREntityAINearestAttackableTargetOrc;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.entity.ai.LOTREntityAIDrink;
import lotr.common.entity.ai.LOTREntityAIEat;
import lotr.common.LOTRFoods;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import lotr.common.entity.ai.LOTREntityAIFollowHiringPlayer;
import lotr.common.entity.ai.LOTREntityAIOrcAvoidGoodPlayer;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import lotr.common.entity.item.LOTREntityOrcBomb;
import lotr.common.entity.ai.LOTREntityAIHiredRemainStill;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.world.World;
import net.minecraft.entity.EntityLivingBase;

public abstract class LOTREntityOrc extends LOTREntityNPC
{
    public boolean isWeakOrc;
    private int orcSkirmishTick;
    public EntityLivingBase currentRevengeTarget;
    
    public LOTREntityOrc(final World world) {
        super(world);
        this.isWeakOrc = true;
        this.setSize(0.5f, 1.55f);
        this.getNavigator().setAvoidsWater(true);
        this.getNavigator().setBreakDoors(true);
        ((EntityLiving)this).tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        ((EntityLiving)this).tasks.addTask(1, (EntityAIBase)new LOTREntityAIHiredRemainStill(this));
        ((EntityLiving)this).tasks.addTask(2, (EntityAIBase)new EntityAIAvoidEntity((EntityCreature)this, (Class)LOTREntityOrcBomb.class, 12.0f, 1.5, 2.0));
        ((EntityLiving)this).tasks.addTask(3, (EntityAIBase)new LOTREntityAIOrcAvoidGoodPlayer(this, 8.0f, 1.5));
        ((EntityLiving)this).tasks.addTask(4, this.createOrcAttackAI());
        ((EntityLiving)this).tasks.addTask(5, (EntityAIBase)new LOTREntityAIFollowHiringPlayer(this));
        ((EntityLiving)this).tasks.addTask(6, (EntityAIBase)new EntityAIOpenDoor((EntityLiving)this, true));
        ((EntityLiving)this).tasks.addTask(7, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0));
        ((EntityLiving)this).tasks.addTask(8, (EntityAIBase)new LOTREntityAIEat(this, LOTRFoods.ORC, 6000));
        ((EntityLiving)this).tasks.addTask(8, (EntityAIBase)new LOTREntityAIDrink(this, LOTRFoods.ORC_DRINK, 6000));
        ((EntityLiving)this).tasks.addTask(9, (EntityAIBase)new EntityAIWatchClosest2((EntityLiving)this, (Class)EntityPlayer.class, 8.0f, 0.05f));
        ((EntityLiving)this).tasks.addTask(9, (EntityAIBase)new EntityAIWatchClosest2((EntityLiving)this, (Class)LOTREntityNPC.class, 5.0f, 0.05f));
        ((EntityLiving)this).tasks.addTask(10, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityLiving.class, 8.0f, 0.02f));
        ((EntityLiving)this).tasks.addTask(11, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        final int target = this.addTargetTasks(true, LOTREntityAINearestAttackableTargetOrc.class);
        ((EntityLiving)this).targetTasks.addTask(target + 1, (EntityAIBase)new LOTREntityAIOrcSkirmish(this, true));
        if (!this.isOrcBombardier()) {
            ((EntityLiving)this).targetTasks.addTask(target + 2, (EntityAIBase)new LOTREntityAINearestAttackableTargetOrc(this, LOTREntityRabbit.class, 2000, false));
        }
        super.spawnsInDarkness = true;
    }
    
    public abstract EntityAIBase createOrcAttackAI();
    
    @Override
    protected void entityInit() {
        super.entityInit();
        ((Entity)this).dataWatcher.addObject(17, (Object)(-1));
    }
    
    public boolean isOrcBombardier() {
        return false;
    }
    
    @Override
    public void setupNPCName() {
        super.familyInfo.setName(LOTRNames.getOrcName(((Entity)this).rand));
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(18.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        return data;
    }
    
    @Override
    protected void onAttackModeChange(final AttackMode mode, final boolean mounted) {
        if (super.npcItemsInv.getBomb() != null) {
            this.setCurrentItemOrArmor(0, super.npcItemsInv.getBombingItem());
        }
        else if (mode == AttackMode.IDLE) {
            this.setCurrentItemOrArmor(0, super.npcItemsInv.getIdleItem());
        }
        else {
            this.setCurrentItemOrArmor(0, super.npcItemsInv.getMeleeWeapon());
        }
    }
    
    public int getTotalArmorValue() {
        if (this.isWeakOrc) {
            return MathHelper.floor_double(super.getTotalArmorValue() * 0.75);
        }
        return super.getTotalArmorValue();
    }
    
    @Override
    public String getNPCName() {
        return super.familyInfo.getName();
    }
    
    public void setRevengeTarget(final EntityLivingBase entity) {
        super.setRevengeTarget(entity);
        if (entity != null) {
            this.currentRevengeTarget = entity;
        }
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!((Entity)this).worldObj.isClient && this.getAttackTarget() == null) {
            this.currentRevengeTarget = null;
        }
        if (!((Entity)this).worldObj.isClient && this.isWeakOrc) {
            final int i = MathHelper.floor_double(((Entity)this).posX);
            final int j = MathHelper.floor_double(((Entity)this).boundingBox.minY);
            final int k = MathHelper.floor_double(((Entity)this).posZ);
            final BiomeGenBase biome = ((Entity)this).worldObj.getBiomeGenForCoords(i, k);
            boolean flag = ((Entity)this).worldObj.isDaytime() && ((Entity)this).worldObj.canBlockSeeTheSky(i, j, k);
            if (biome instanceof LOTRBiome && ((LOTRBiome)biome).canSpawnHostilesInDay()) {
                flag = false;
            }
            if (flag && ((Entity)this).ticksExisted % 20 == 0) {
                this.addPotionEffect(new PotionEffect(Potion.resistance.id, 200, -1));
                this.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 200));
            }
        }
        if (!((Entity)this).worldObj.isClient && this.isOrcSkirmishing()) {
            if (!LOTRConfig.enableOrcSkirmish) {
                this.orcSkirmishTick = 0;
            }
            else if (!(this.getAttackTarget() instanceof LOTREntityOrc)) {
                --this.orcSkirmishTick;
            }
        }
        if (this.isOrcBombardier()) {
            if (!((Entity)this).worldObj.isClient) {
                final ItemStack bomb = super.npcItemsInv.getBomb();
                int meta = -1;
                if (bomb != null && Block.getBlockFromItem(bomb.getItem()) instanceof LOTRBlockOrcBomb) {
                    meta = bomb.getItemDamage();
                }
                ((Entity)this).dataWatcher.updateObject(17, (Object)(byte)meta);
            }
            else {
                final int meta2 = ((Entity)this).dataWatcher.getWatchableObjectByte(17);
                if (meta2 == -1) {
                    super.npcItemsInv.setBomb(null);
                }
                else {
                    super.npcItemsInv.setBomb(new ItemStack(LOTRMod.orcBomb, 1, meta2));
                }
            }
        }
    }
    
    public boolean canOrcSkirmish() {
        return !super.questInfo.anyActiveQuestPlayers();
    }
    
    public boolean isOrcSkirmishing() {
        return this.orcSkirmishTick > 0;
    }
    
    public void setOrcSkirmishing() {
        final int prevSkirmishTick = this.orcSkirmishTick;
        this.orcSkirmishTick = 160;
        if (!((Entity)this).worldObj.isClient && prevSkirmishTick == 0) {
            final List nearbyPlayers = ((Entity)this).worldObj.getEntitiesWithinAABB((Class)EntityPlayer.class, ((Entity)this).boundingBox.expand(24.0, 24.0, 24.0));
            for (int i = 0; i < nearbyPlayers.size(); ++i) {
                final EntityPlayer entityplayer = nearbyPlayers.get(i);
                LOTRSpeech.sendSpeech(entityplayer, this, LOTRSpeech.getRandomSpeechForPlayer(this, this.getOrcSkirmishSpeech(), entityplayer));
            }
        }
    }
    
    protected String getOrcSkirmishSpeech() {
        return "";
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setInteger("OrcSkirmish", this.orcSkirmishTick);
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        if (nbt.hasKey("OrcName")) {
            super.familyInfo.setName(nbt.getString("OrcName"));
        }
        this.orcSkirmishTick = nbt.getInteger("OrcSkirmish");
    }
    
    @Override
    protected float getPoisonedArrowChance() {
        return 0.06666667f;
    }
    
    @Override
    protected void dropFewItems(final boolean flag, final int i) {
        super.dropFewItems(flag, i);
        for (int flesh = ((Entity)this).rand.nextInt(3) + ((Entity)this).rand.nextInt(i + 1), l = 0; l < flesh; ++l) {
            this.func_145779_a(Items.rotten_flesh, 1);
        }
        for (int bones = ((Entity)this).rand.nextInt(2) + ((Entity)this).rand.nextInt(i + 1), j = 0; j < bones; ++j) {
            this.func_145779_a(LOTRMod.orcBone, 1);
        }
        if (((Entity)this).rand.nextInt(10) == 0) {
            for (int breads = 1 + ((Entity)this).rand.nextInt(2) + ((Entity)this).rand.nextInt(i + 1), k = 0; k < breads; ++k) {
                this.func_145779_a(LOTRMod.maggotyBread, 1);
            }
        }
        if (flag) {
            int rareDropChance = 20 - i * 4;
            rareDropChance = Math.max(rareDropChance, 1);
            if (((Entity)this).rand.nextInt(rareDropChance) == 0) {
                final int dropType = ((Entity)this).rand.nextInt(2);
                if (dropType == 0) {
                    final ItemStack orcDrink = new ItemStack(LOTRMod.mugOrcDraught);
                    orcDrink.setItemDamage(1 + ((Entity)this).rand.nextInt(3));
                    LOTRItemMug.setVessel(orcDrink, LOTRFoods.ORC_DRINK.getRandomVessel(((Entity)this).rand), true);
                    this.entityDropItem(orcDrink, 0.0f);
                }
                else if (dropType == 1) {
                    for (int ingots = 1 + ((Entity)this).rand.nextInt(2) + ((Entity)this).rand.nextInt(i + 1), m = 0; m < ingots; ++m) {
                        if (this instanceof LOTREntityUrukHai || this instanceof LOTREntityGundabadUruk) {
                            this.func_145779_a(LOTRMod.urukSteel, 1);
                        }
                        else if (this instanceof LOTREntityBlackUruk) {
                            this.func_145779_a(LOTRMod.blackUrukSteel, 1);
                        }
                        else {
                            this.func_145779_a(LOTRMod.orcSteel, 1);
                        }
                    }
                }
            }
        }
        this.dropOrcItems(flag, i);
    }
    
    protected void dropOrcItems(final boolean flag, final int i) {
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
        final BiomeGenBase biome = ((Entity)this).worldObj.getBiomeGenForCoords(i, k);
        return !(biome instanceof LOTRBiome) || !((LOTRBiome)biome).isDwarvenBiome(((Entity)this).worldObj) || ((Entity)this).worldObj.getBlock(i, j - 1, k) == biome.topBlock;
    }
    
    protected String getLivingSound() {
        return "lotr:orc.say";
    }
    
    protected String getHurtSound() {
        return "lotr:orc.hurt";
    }
    
    protected String getDeathSound() {
        return "lotr:orc.death";
    }
    
    @Override
    public ItemStack getHeldItemLeft() {
        if (this.isOrcBombardier() && super.npcItemsInv.getBomb() != null) {
            return super.npcItemsInv.getBomb();
        }
        return super.getHeldItemLeft();
    }
}
