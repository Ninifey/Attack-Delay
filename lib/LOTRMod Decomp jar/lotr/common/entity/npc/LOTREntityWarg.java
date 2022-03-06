// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.util.DamageSource;
import lotr.common.LOTRMod;
import lotr.common.fac.LOTRAlignmentValues;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemFood;
import lotr.common.entity.LOTRMountFunctions;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.inventory.IInventory;
import lotr.common.item.LOTRItemMountArmor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.SharedMonsterAttributes;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import lotr.common.entity.animal.LOTREntityDeer;
import lotr.common.entity.ai.LOTREntityAINearestAttackableTargetBasic;
import lotr.common.entity.animal.LOTREntityRabbit;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIWander;
import lotr.common.entity.ai.LOTREntityAIFollowHiringPlayer;
import lotr.common.entity.ai.LOTREntityAIUntamedPanic;
import lotr.common.entity.ai.LOTREntityAIHiredRemainStill;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.world.World;
import net.minecraft.inventory.AnimalChest;
import net.minecraft.inventory.IInvBasic;

public abstract class LOTREntityWarg extends LOTREntityNPCRideable implements IInvBasic
{
    private int eatingTick;
    private AnimalChest wargInventory;
    
    public LOTREntityWarg(final World world) {
        super(world);
        this.setSize(1.5f, 1.7f);
        this.getNavigator().setAvoidsWater(true);
        ((EntityLiving)this).tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        ((EntityLiving)this).tasks.addTask(1, (EntityAIBase)new LOTREntityAIHiredRemainStill(this));
        ((EntityLiving)this).tasks.addTask(2, this.getWargAttackAI());
        ((EntityLiving)this).tasks.addTask(3, (EntityAIBase)new LOTREntityAIUntamedPanic(this, 1.2));
        ((EntityLiving)this).tasks.addTask(4, (EntityAIBase)new LOTREntityAIFollowHiringPlayer(this));
        ((EntityLiving)this).tasks.addTask(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0));
        ((EntityLiving)this).tasks.addTask(6, (EntityAIBase)new EntityAIWatchClosest2((EntityLiving)this, (Class)EntityPlayer.class, 12.0f, 0.02f));
        ((EntityLiving)this).tasks.addTask(6, (EntityAIBase)new EntityAIWatchClosest2((EntityLiving)this, (Class)LOTREntityNPC.class, 8.0f, 0.02f));
        ((EntityLiving)this).tasks.addTask(7, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityLiving.class, 12.0f, 0.02f));
        ((EntityLiving)this).tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        final int target = this.addTargetTasks(true);
        if (!(this instanceof LOTREntityWargBombardier)) {
            ((EntityLiving)this).targetTasks.addTask(target + 1, (EntityAIBase)new LOTREntityAINearestAttackableTargetBasic(this, LOTREntityRabbit.class, 500, false));
            ((EntityLiving)this).targetTasks.addTask(target + 1, (EntityAIBase)new LOTREntityAINearestAttackableTargetBasic(this, LOTREntityDeer.class, 1000, false));
        }
        super.isImmuneToFrost = true;
        super.spawnsInDarkness = true;
        this.setupWargInventory();
    }
    
    public EntityAIBase getWargAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.6, false);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        ((Entity)this).dataWatcher.addObject(18, (Object)0);
        ((Entity)this).dataWatcher.addObject(19, (Object)0);
        ((Entity)this).dataWatcher.addObject(20, (Object)0);
        if (((Entity)this).rand.nextInt(500) == 0) {
            this.setWargType(WargType.WHITE);
        }
        else if (((Entity)this).rand.nextInt(20) == 0) {
            this.setWargType(WargType.BLACK);
        }
        else if (((Entity)this).rand.nextInt(3) == 0) {
            this.setWargType(WargType.GREY);
        }
        else {
            this.setWargType(WargType.BROWN);
        }
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue((double)MathHelper.getRandomIntegerInRange(((Entity)this).rand, 20, 32));
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22);
        this.getEntityAttribute(LOTREntityNPC.npcAttackDamage).setBaseValue((double)MathHelper.getRandomIntegerInRange(((Entity)this).rand, 3, 5));
    }
    
    public boolean isMountSaddled() {
        return ((Entity)this).dataWatcher.getWatchableObjectByte(18) == 1;
    }
    
    public void setWargSaddled(final boolean flag) {
        ((Entity)this).dataWatcher.updateObject(18, (Object)(byte)(flag ? 1 : 0));
    }
    
    public WargType getWargType() {
        final int i = ((Entity)this).dataWatcher.getWatchableObjectByte(19);
        return WargType.forID(i);
    }
    
    public void setWargType(final WargType w) {
        ((Entity)this).dataWatcher.updateObject(19, (Object)(byte)w.wargID);
    }
    
    public ItemStack getWargArmorWatched() {
        final int ID = ((Entity)this).dataWatcher.getWatchableObjectInt(20);
        return new ItemStack(Item.getItemById(ID));
    }
    
    public String getMountArmorTexture() {
        final ItemStack armor = this.getWargArmorWatched();
        if (armor != null && armor.getItem() instanceof LOTRItemMountArmor) {
            return ((LOTRItemMountArmor)armor.getItem()).getArmorTexture();
        }
        return null;
    }
    
    private void setWargArmorWatched(final ItemStack itemstack) {
        if (itemstack == null) {
            ((Entity)this).dataWatcher.updateObject(20, (Object)0);
        }
        else {
            ((Entity)this).dataWatcher.updateObject(20, (Object)Item.getIdFromItem(itemstack.getItem()));
        }
    }
    
    @Override
    public IInventory getMountInventory() {
        return (IInventory)this.wargInventory;
    }
    
    public IEntityLivingData initCreatureForHire(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        return data;
    }
    
    public abstract LOTREntityNPC createWargRider();
    
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        if (!((Entity)this).worldObj.isClient && this.canWargBeRidden() && ((Entity)this).rand.nextInt(3) == 0) {
            final LOTREntityNPC rider = this.createWargRider();
            rider.setLocationAndAngles(((Entity)this).posX, ((Entity)this).posY, ((Entity)this).posZ, ((Entity)this).rotationYaw, 0.0f);
            rider.onSpawnWithEgg(null);
            rider.isNPCPersistent = super.isNPCPersistent;
            ((Entity)this).worldObj.spawnEntityInWorld((Entity)rider);
            rider.mountEntity((Entity)this);
        }
        return data;
    }
    
    public boolean canWargBeRidden() {
        return true;
    }
    
    public boolean getBelongsToNPC() {
        return false;
    }
    
    public void setBelongsToNPC(final boolean flag) {
    }
    
    private void setupWargInventory() {
        AnimalChest prevInv = this.wargInventory;
        (this.wargInventory = new AnimalChest("WargInv", 2)).func_110133_a(this.getCommandSenderName());
        if (prevInv != null) {
            prevInv.func_110132_b((IInvBasic)this);
            for (int invSize = Math.min(prevInv.getSizeInventory(), this.wargInventory.getSizeInventory()), slot = 0; slot < invSize; ++slot) {
                final ItemStack itemstack = prevInv.getStackInSlot(slot);
                if (itemstack != null) {
                    this.wargInventory.setInventorySlotContents(slot, itemstack.copy());
                }
            }
            prevInv = null;
        }
        this.wargInventory.func_110134_a((IInvBasic)this);
        this.checkWargInventory();
    }
    
    private void checkWargInventory() {
        if (!((Entity)this).worldObj.isClient) {
            this.setWargSaddled(this.wargInventory.getStackInSlot(0) != null);
            this.setWargArmorWatched(this.getWargArmor());
        }
    }
    
    public void onInventoryChanged(final InventoryBasic inv) {
        final boolean prevSaddled = this.isMountSaddled();
        final ItemStack prevArmor = this.getWargArmorWatched();
        this.checkWargInventory();
        final ItemStack wargArmor = this.getWargArmorWatched();
        if (((Entity)this).ticksExisted > 20) {
            if (!prevSaddled && this.isMountSaddled()) {
                this.playSound("mob.horse.leather", 0.5f, 1.0f);
            }
            if (!ItemStack.areItemStacksEqual(prevArmor, wargArmor)) {
                this.playSound("mob.horse.armor", 0.5f, 1.0f);
            }
        }
    }
    
    public void setWargArmor(final ItemStack itemstack) {
        this.wargInventory.setInventorySlotContents(1, itemstack);
        this.setupWargInventory();
        this.setWargArmorWatched(this.getWargArmor());
    }
    
    public ItemStack getWargArmor() {
        return this.wargInventory.getStackInSlot(1);
    }
    
    public int getTotalArmorValue() {
        final ItemStack itemstack = this.getWargArmor();
        if (itemstack != null && itemstack.getItem() instanceof LOTRItemMountArmor) {
            final LOTRItemMountArmor armor = (LOTRItemMountArmor)itemstack.getItem();
            return armor.getDamageReduceAmount();
        }
        return 0;
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setByte("WargType", (byte)this.getWargType().wargID);
        if (this.wargInventory.getStackInSlot(0) != null) {
            nbt.setTag("WargSaddleItem", (NBTBase)this.wargInventory.getStackInSlot(0).writeToNBT(new NBTTagCompound()));
        }
        if (this.getWargArmor() != null) {
            nbt.setTag("WargArmorItem", (NBTBase)this.getWargArmor().writeToNBT(new NBTTagCompound()));
        }
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.setWargType(WargType.forID(nbt.getByte("WargType")));
        if (nbt.hasKey("WargSaddleItem")) {
            final ItemStack saddle = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("WargSaddleItem"));
            if (saddle != null && saddle.getItem() == Items.saddle) {
                this.wargInventory.setInventorySlotContents(0, saddle);
            }
        }
        else if (nbt.getBoolean("Saddled")) {
            this.wargInventory.setInventorySlotContents(0, new ItemStack(Items.saddle));
        }
        if (nbt.hasKey("WargArmorItem")) {
            final ItemStack wargArmor = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("WargArmorItem"));
            if (wargArmor != null && this.isMountArmorValid(wargArmor)) {
                this.wargInventory.setInventorySlotContents(1, wargArmor);
            }
        }
        this.checkWargInventory();
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!((Entity)this).worldObj.isClient && ((Entity)this).riddenByEntity instanceof EntityPlayer) {
            final EntityPlayer entityplayer = (EntityPlayer)((Entity)this).riddenByEntity;
            if (LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) < 50.0f) {
                entityplayer.mountEntity((Entity)null);
            }
            else if (this.isNPCTamed() && this.isMountSaddled()) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.rideWarg);
            }
        }
        if (this.eatingTick > 0) {
            if (this.eatingTick % 4 == 0) {
                ((Entity)this).worldObj.playSoundAtEntity((Entity)this, "random.eat", 0.5f + 0.5f * ((Entity)this).rand.nextInt(2), 0.4f + ((Entity)this).rand.nextFloat() * 0.2f);
            }
            --this.eatingTick;
        }
    }
    
    public boolean interact(final EntityPlayer entityplayer) {
        if (((Entity)this).worldObj.isClient || super.hiredNPCInfo.isActive) {
            return false;
        }
        if (LOTRMountFunctions.interact(this, entityplayer)) {
            return true;
        }
        if (this.getAttackTarget() != entityplayer) {
            final boolean hasRequiredAlignment = LOTRLevelData.getData(entityplayer).getAlignment(this.getFaction()) >= 50.0f;
            boolean notifyNotEnoughAlignment = false;
            final ItemStack itemstack = entityplayer.inventory.getCurrentItem();
            if (!notifyNotEnoughAlignment && this.isNPCTamed() && entityplayer.isSneaking()) {
                if (hasRequiredAlignment) {
                    this.openGUI(entityplayer);
                    return true;
                }
                notifyNotEnoughAlignment = true;
            }
            if (!notifyNotEnoughAlignment && this.isNPCTamed() && itemstack != null && itemstack.getItem() instanceof ItemFood && ((ItemFood)itemstack.getItem()).isWolfsFavoriteMeat() && this.getHealth() < this.getMaxHealth()) {
                if (hasRequiredAlignment) {
                    if (!entityplayer.capabilities.isCreativeMode) {
                        final ItemStack itemStack = itemstack;
                        --itemStack.stackSize;
                        if (itemstack.stackSize == 0) {
                            entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, (ItemStack)null);
                        }
                    }
                    this.heal((float)((ItemFood)itemstack.getItem()).func_150905_g(itemstack));
                    this.eatingTick = 20;
                    return true;
                }
                notifyNotEnoughAlignment = true;
            }
            if (!notifyNotEnoughAlignment && this.isNPCTamed() && !this.isMountSaddled() && this.canWargBeRidden() && ((Entity)this).riddenByEntity == null && itemstack != null && itemstack.getItem() == Items.saddle) {
                if (hasRequiredAlignment) {
                    this.openGUI(entityplayer);
                    return true;
                }
                notifyNotEnoughAlignment = true;
            }
            if (!notifyNotEnoughAlignment && !this.isChild() && this.canWargBeRidden() && ((Entity)this).riddenByEntity == null) {
                if (itemstack != null && itemstack.interactWithEntity(entityplayer, (EntityLivingBase)this)) {
                    return true;
                }
                if (hasRequiredAlignment) {
                    entityplayer.mountEntity((Entity)this);
                    this.setAttackTarget(null);
                    this.getNavigator().clearPathEntity();
                    return true;
                }
                notifyNotEnoughAlignment = true;
            }
            if (notifyNotEnoughAlignment) {
                LOTRAlignmentValues.notifyAlignmentNotHighEnough(entityplayer, 50.0f, this.getFaction());
                return true;
            }
        }
        return super.interact(entityplayer);
    }
    
    protected void dropFewItems(final boolean flag, final int i) {
        super.dropFewItems(flag, i);
        for (int furs = 1 + ((Entity)this).rand.nextInt(3) + ((Entity)this).rand.nextInt(i + 1), l = 0; l < furs; ++l) {
            this.func_145779_a(LOTRMod.fur, 1);
        }
        for (int bones = 2 + ((Entity)this).rand.nextInt(2) + ((Entity)this).rand.nextInt(i + 1), j = 0; j < bones; ++j) {
            this.func_145779_a(LOTRMod.wargBone, 1);
        }
        if (flag) {
            int rugChance = 50 - i * 8;
            rugChance = Math.max(rugChance, 1);
            if (((Entity)this).rand.nextInt(rugChance) == 0) {
                this.entityDropItem(new ItemStack(LOTRMod.wargskinRug, 1, this.getWargType().wargID), 0.0f);
            }
        }
    }
    
    public boolean canDropRares() {
        return false;
    }
    
    public String getLivingSound() {
        return "lotr:warg.say";
    }
    
    public String getHurtSound() {
        return "lotr:warg.hurt";
    }
    
    public String getDeathSound() {
        return "lotr:warg.death";
    }
    
    public String getAttackSound() {
        return "lotr:warg.attack";
    }
    
    public void onDeath(final DamageSource damagesource) {
        super.onDeath(damagesource);
        if (!((Entity)this).worldObj.isClient) {
            if (this.getBelongsToNPC()) {
                this.wargInventory.setInventorySlotContents(0, (ItemStack)null);
                this.wargInventory.setInventorySlotContents(1, (ItemStack)null);
            }
            if (this.isNPCTamed()) {
                this.setWargSaddled(false);
                this.func_145779_a(Items.saddle, 1);
                final ItemStack wargArmor = this.getWargArmor();
                if (wargArmor != null) {
                    this.entityDropItem(wargArmor, 0.0f);
                    this.setWargArmor(null);
                }
            }
        }
    }
    
    public float getTailRotation() {
        final float f = (this.getMaxHealth() - this.getHealth()) / this.getMaxHealth();
        return f * -1.2f;
    }
    
    public boolean allowLeashing() {
        return this.isNPCTamed();
    }
    
    public boolean canReEquipHired(final int slot, final ItemStack itemstack) {
        return false;
    }
    
    public enum WargType
    {
        BROWN(0), 
        GREY(1), 
        BLACK(2), 
        WHITE(3), 
        ICE(4), 
        OBSIDIAN(5), 
        FIRE(6);
        
        public final int wargID;
        
        private WargType(final int i) {
            this.wargID = i;
        }
        
        public String textureName() {
            return this.name().toLowerCase();
        }
        
        public static WargType forID(final int ID) {
            for (final WargType w : values()) {
                if (w.wargID == ID) {
                    return w;
                }
            }
            return WargType.BROWN;
        }
        
        public static String[] wargTypeNames() {
            final String[] names = new String[values().length];
            for (int i = 0; i < names.length; ++i) {
                names[i] = values()[i].textureName();
            }
            return names;
        }
    }
}
