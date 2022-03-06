// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.animal;

import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.inventory.AnimalChest;
import java.util.List;
import net.minecraft.util.DamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.EntityList;
import lotr.common.entity.LOTREntities;
import net.minecraft.entity.EntityAgeable;
import lotr.common.LOTRMod;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import lotr.common.item.LOTRItemMountArmor;
import net.minecraft.item.Item;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.LOTRReflection;
import lotr.common.world.biome.LOTRBiomeGenDorEnErnil;
import lotr.common.world.biome.LOTRBiomeGenRohan;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.LOTREntityUtils;
import net.minecraft.entity.ai.EntityAIPanic;
import lotr.common.entity.ai.LOTREntityAIHorseFollowHiringPlayer;
import lotr.common.entity.ai.LOTREntityAIHorseMoveToRiderTarget;
import lotr.common.entity.ai.LOTREntityAIHiredHorseRemainStill;
import net.minecraft.world.World;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import lotr.common.entity.npc.LOTRNPCMount;
import net.minecraft.entity.passive.EntityHorse;

public class LOTREntityHorse extends EntityHorse implements LOTRNPCMount
{
    private boolean isMoving;
    private ItemStack prevMountArmor;
    private EntityAIBase attackAI;
    private EntityAIBase panicAI;
    private boolean prevIsChild;
    
    public LOTREntityHorse(final World world) {
        super(world);
        this.prevIsChild = true;
        ((EntityLiving)this).tasks.addTask(0, (EntityAIBase)new LOTREntityAIHiredHorseRemainStill(this));
        ((EntityLiving)this).tasks.addTask(0, (EntityAIBase)new LOTREntityAIHorseMoveToRiderTarget(this));
        ((EntityLiving)this).tasks.addTask(0, (EntityAIBase)new LOTREntityAIHorseFollowHiringPlayer(this));
        final EntityAITasks.EntityAITaskEntry panic = LOTREntityUtils.removeAITask((EntityCreature)this, EntityAIPanic.class);
        ((EntityLiving)this).tasks.addTask(panic.priority, panic.action);
        this.panicAI = panic.action;
        this.attackAI = this.createMountAttackAI();
        if (this.isMountHostile()) {
            ((EntityLiving)this).targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false));
        }
    }
    
    protected EntityAIBase createMountAttackAI() {
        return null;
    }
    
    protected boolean isMountHostile() {
        return false;
    }
    
    protected void entityInit() {
        super.entityInit();
        ((Entity)this).dataWatcher.addObject(25, (Object)0);
        ((Entity)this).dataWatcher.addObject(26, (Object)1);
        ((Entity)this).dataWatcher.addObject(27, (Object)0);
        ((Entity)this).dataWatcher.addObject(28, (Object)0);
        ((Entity)this).dataWatcher.addObject(29, (Object)0);
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
        if (this.isMountHostile()) {
            this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
        }
    }
    
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        if (!((Entity)this).worldObj.isClient) {
            data = super.onSpawnWithEgg(data);
            this.onLOTRHorseSpawn();
            this.setHealth(this.getMaxHealth());
            return data;
        }
        final int j = ((Entity)this).rand.nextInt(7);
        final int k = ((Entity)this).rand.nextInt(5);
        final int i = j | k << 8;
        this.setHorseVariant(i);
        return data;
    }
    
    protected void onLOTRHorseSpawn() {
        final int i = MathHelper.floor_double(((Entity)this).posX);
        final int k = MathHelper.floor_double(((Entity)this).posZ);
        final BiomeGenBase biome = ((Entity)this).worldObj.getBiomeGenForCoords(i, k);
        if (this.getClass() == LOTREntityHorse.class) {
            float healthBoost = 0.0f;
            float speedBoost = 0.0f;
            float jumpAdd = 0.0f;
            if (biome instanceof LOTRBiomeGenRohan) {
                healthBoost = 0.5f;
                speedBoost = 0.3f;
                jumpAdd = 0.2f;
            }
            if (biome instanceof LOTRBiomeGenDorEnErnil) {
                healthBoost = 0.3f;
                speedBoost = 0.2f;
                jumpAdd = 0.1f;
            }
            if (healthBoost > 0.0f) {
                double maxHealth = this.getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue();
                maxHealth *= 1.0f + ((Entity)this).rand.nextFloat() * healthBoost;
                this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(maxHealth);
                this.setHealth(this.getMaxHealth());
            }
            if (speedBoost > 0.0f) {
                double movementSpeed = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
                movementSpeed *= 1.0f + ((Entity)this).rand.nextFloat() * speedBoost;
                this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(movementSpeed);
            }
            double jumpStrength = this.getEntityAttribute(LOTRReflection.getHorseJumpStrength()).getAttributeValue();
            final double jumpLimit = Math.max(jumpStrength, 1.0);
            if (jumpAdd > 0.0f) {
                jumpStrength += jumpAdd;
            }
            jumpStrength = Math.min(jumpStrength, jumpLimit);
            this.getEntityAttribute(LOTRReflection.getHorseJumpStrength()).setBaseValue(jumpStrength);
        }
    }
    
    public boolean getBelongsToNPC() {
        return ((Entity)this).dataWatcher.getWatchableObjectByte(25) == 1;
    }
    
    public void setBelongsToNPC(final boolean flag) {
        ((Entity)this).dataWatcher.updateObject(25, (Object)(byte)(flag ? 1 : 0));
        if (flag) {
            this.setHorseTamed(true);
            this.setHorseSaddled(true);
            if (this.getGrowingAge() < 0) {
                this.setGrowingAge(0);
            }
            if (this.getClass() == LOTREntityHorse.class) {
                this.setHorseType(0);
            }
        }
    }
    
    public boolean getMountable() {
        return ((Entity)this).dataWatcher.getWatchableObjectByte(26) == 1;
    }
    
    public void setMountable(final boolean flag) {
        ((Entity)this).dataWatcher.updateObject(26, (Object)(byte)(flag ? 1 : 0));
    }
    
    public ItemStack getMountArmor() {
        final int ID = ((Entity)this).dataWatcher.getWatchableObjectInt(27);
        final int meta = ((Entity)this).dataWatcher.getWatchableObjectByte(28);
        return new ItemStack(Item.getItemById(ID), 1, meta);
    }
    
    public String getMountArmorTexture() {
        final ItemStack armor = this.getMountArmor();
        if (armor != null && armor.getItem() instanceof LOTRItemMountArmor) {
            return ((LOTRItemMountArmor)armor.getItem()).getArmorTexture();
        }
        return null;
    }
    
    private void setMountArmorWatched(final ItemStack itemstack) {
        if (itemstack == null) {
            ((Entity)this).dataWatcher.updateObject(27, (Object)0);
            ((Entity)this).dataWatcher.updateObject(28, (Object)0);
        }
        else {
            ((Entity)this).dataWatcher.updateObject(27, (Object)Item.getIdFromItem(itemstack.getItem()));
            ((Entity)this).dataWatcher.updateObject(28, (Object)(byte)itemstack.getItemDamage());
        }
    }
    
    public boolean isMountEnraged() {
        return ((Entity)this).dataWatcher.getWatchableObjectByte(29) == 1;
    }
    
    public void setMountEnraged(final boolean flag) {
        ((Entity)this).dataWatcher.updateObject(29, (Object)(byte)(flag ? 1 : 0));
    }
    
    public boolean isMountSaddled() {
        return this.isHorseSaddled();
    }
    
    public boolean isHorseSaddled() {
        return (!this.isMoving || !this.getBelongsToNPC()) && super.isHorseSaddled();
    }
    
    public void saddleMountForWorldGen() {
        this.setGrowingAge(0);
        LOTRReflection.getHorseInv(this).setInventorySlotContents(0, new ItemStack(Items.saddle));
        LOTRReflection.setupHorseInv(this);
        this.setHorseTamed(true);
    }
    
    public void setChestedForWorldGen() {
        this.setChested(true);
        LOTRReflection.setupHorseInv(this);
    }
    
    public void setMountArmor(final ItemStack itemstack) {
        LOTRReflection.getHorseInv(this).setInventorySlotContents(1, itemstack);
        LOTRReflection.setupHorseInv(this);
        this.setMountArmorWatched(itemstack);
    }
    
    public boolean isMountArmorValid(final ItemStack itemstack) {
        if (itemstack != null && itemstack.getItem() instanceof LOTRItemMountArmor) {
            final LOTRItemMountArmor armor = (LOTRItemMountArmor)itemstack.getItem();
            return armor.isValid(this);
        }
        return false;
    }
    
    public int getTotalArmorValue() {
        final ItemStack itemstack = LOTRReflection.getHorseInv(this).getStackInSlot(1);
        if (itemstack != null && itemstack.getItem() instanceof LOTRItemMountArmor) {
            final LOTRItemMountArmor armor = (LOTRItemMountArmor)itemstack.getItem();
            return armor.getDamageReduceAmount();
        }
        return 0;
    }
    
    public void onLivingUpdate() {
        if (!((Entity)this).worldObj.isClient) {
            final ItemStack armor = LOTRReflection.getHorseInv(this).getStackInSlot(1);
            if (((Entity)this).ticksExisted > 20 && !ItemStack.areItemStacksEqual(this.prevMountArmor, armor)) {
                this.playSound("mob.horse.armor", 0.5f, 1.0f);
            }
            this.setMountArmorWatched(this.prevMountArmor = armor);
        }
        super.onLivingUpdate();
        if (!((Entity)this).worldObj.isClient && ((Entity)this).riddenByEntity instanceof EntityPlayer && this.isInWater() && ((Entity)this).motionY < 0.0) {
            final AxisAlignedBB swimCheckBox = ((Entity)this).boundingBox.copy().addCoord(0.0, -1.0, 0.0);
            if (((Entity)this).worldObj.func_147461_a(swimCheckBox).isEmpty() && ((Entity)this).rand.nextFloat() < 0.55f) {
                ((Entity)this).motionY += 0.05;
                ((Entity)this).isAirBorne = true;
            }
        }
        if (!((Entity)this).worldObj.isClient && this.isMountHostile()) {
            final boolean isChild = this.isChild();
            if (isChild != this.prevIsChild) {
                if (isChild) {
                    final EntityAITasks.EntityAITaskEntry taskEntry = LOTREntityUtils.removeAITask((EntityCreature)this, this.attackAI.getClass());
                    ((EntityLiving)this).tasks.addTask(taskEntry.priority, this.panicAI);
                }
                else {
                    final EntityAITasks.EntityAITaskEntry taskEntry = LOTREntityUtils.removeAITask((EntityCreature)this, this.panicAI.getClass());
                    ((EntityLiving)this).tasks.addTask(taskEntry.priority, this.attackAI);
                }
            }
            if (this.getAttackTarget() != null) {
                final EntityLivingBase target = this.getAttackTarget();
                if (!target.isEntityAlive() || (target instanceof EntityPlayer && ((EntityPlayer)target).capabilities.isCreativeMode)) {
                    this.setAttackTarget((EntityLivingBase)null);
                }
            }
            if (((Entity)this).riddenByEntity instanceof EntityLiving) {
                final EntityLivingBase target = ((EntityLiving)((Entity)this).riddenByEntity).getAttackTarget();
                this.setAttackTarget(target);
            }
            else if (((Entity)this).riddenByEntity instanceof EntityPlayer) {
                this.setAttackTarget((EntityLivingBase)null);
            }
            this.setMountEnraged(this.getAttackTarget() != null);
        }
        this.prevIsChild = this.isChild();
    }
    
    protected boolean isMovementBlocked() {
        this.isMoving = true;
        final boolean flag = super.isMovementBlocked();
        this.isMoving = false;
        return flag;
    }
    
    public void moveEntityWithHeading(final float f, final float f1) {
        this.isMoving = true;
        super.moveEntityWithHeading(f, f1);
        this.isMoving = false;
    }
    
    public void super_moveEntityWithHeading(final float strafe, final float forward) {
        super.moveEntityWithHeading(strafe, forward);
    }
    
    public float getBlockPathWeight(final int i, final int j, final int k) {
        if (this.getBelongsToNPC() && ((Entity)this).riddenByEntity instanceof LOTREntityNPC) {
            return ((LOTREntityNPC)((Entity)this).riddenByEntity).getBlockPathWeight(i, j, k);
        }
        return super.getBlockPathWeight(i, j, k);
    }
    
    public double getMountedYOffset() {
        double d = ((Entity)this).height * 0.5;
        if (((Entity)this).riddenByEntity != null) {
            d += ((Entity)this).riddenByEntity.yOffset - ((Entity)this).riddenByEntity.getYOffset();
        }
        return d;
    }
    
    public boolean isBreedingItem(final ItemStack itemstack) {
        return itemstack != null && LOTRMod.isOreNameEqual(itemstack, "apple");
    }
    
    public EntityAgeable createChild(final EntityAgeable otherParent) {
        final EntityHorse superChild = (EntityHorse)super.createChild(otherParent);
        final LOTREntityHorse child = (LOTREntityHorse)EntityList.createEntityByName(LOTREntities.getStringFromClass(this.getClass()), ((Entity)this).worldObj);
        child.setHorseType(superChild.getHorseType());
        child.setHorseVariant(superChild.getHorseVariant());
        double maxHealth = this.getChildAttribute((EntityAgeable)this, otherParent, SharedMonsterAttributes.maxHealth, 3.0);
        maxHealth = this.clampChildHealth(maxHealth);
        child.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(maxHealth);
        child.setHealth(child.getMaxHealth());
        double jumpStrength = this.getChildAttribute((EntityAgeable)this, otherParent, LOTRReflection.getHorseJumpStrength(), 0.1);
        jumpStrength = this.clampChildJump(jumpStrength);
        child.getEntityAttribute(LOTRReflection.getHorseJumpStrength()).setBaseValue(jumpStrength);
        double moveSpeed = this.getChildAttribute((EntityAgeable)this, otherParent, SharedMonsterAttributes.movementSpeed, 0.03);
        moveSpeed = this.clampChildSpeed(moveSpeed);
        child.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(moveSpeed);
        if (this.isTame() && ((LOTREntityHorse)otherParent).isTame()) {
            child.setHorseTamed(true);
        }
        return (EntityAgeable)child;
    }
    
    private double getChildAttribute(final EntityAgeable parent, final EntityAgeable otherParent, final IAttribute stat, final double variance) {
        final double val1 = parent.getEntityAttribute(stat).getBaseValue();
        final double val2 = otherParent.getEntityAttribute(stat).getBaseValue();
        if (val1 <= val2) {
            return MathHelper.getRandomDoubleInRange(((Entity)this).rand, val1 - variance, val2 + variance);
        }
        return MathHelper.getRandomDoubleInRange(((Entity)this).rand, val2 - variance, val1 + variance);
    }
    
    protected double clampChildHealth(final double health) {
        return MathHelper.clamp_double(health, 12.0, 48.0);
    }
    
    protected double clampChildJump(final double jump) {
        return MathHelper.clamp_double(jump, 0.3, 1.0);
    }
    
    protected double clampChildSpeed(final double speed) {
        return MathHelper.clamp_double(speed, 0.08, 0.45);
    }
    
    public boolean interact(final EntityPlayer entityplayer) {
        if (!this.getMountable()) {
            return false;
        }
        if (this.isMountEnraged()) {
            return false;
        }
        if (this.getBelongsToNPC()) {
            if (((Entity)this).riddenByEntity == null) {
                if (!((Entity)this).worldObj.isClient) {
                    entityplayer.addChatMessage((IChatComponent)new ChatComponentTranslation("chat.lotr.mountOwnedByNPC", new Object[0]));
                }
                return true;
            }
            return false;
        }
        else {
            final ItemStack itemstack = entityplayer.getHeldItem();
            if (itemstack != null && this.isBreedingItem(itemstack) && this.getGrowingAge() == 0 && !this.isInLove() && this.isTame()) {
                if (!entityplayer.capabilities.isCreativeMode) {
                    final ItemStack itemStack = itemstack;
                    --itemStack.stackSize;
                    if (itemstack.stackSize <= 0) {
                        entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, (ItemStack)null);
                    }
                }
                this.func_146082_f(entityplayer);
                return true;
            }
            final boolean prevInLove = this.isInLove();
            final boolean flag = super.interact(entityplayer);
            if (this.isInLove() && !prevInLove) {
                this.resetInLove();
            }
            return flag;
        }
    }
    
    public boolean attackEntityAsMob(final Entity entity) {
        final float f = (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
        final boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), f);
        return flag;
    }
    
    public boolean attackEntityFrom(final DamageSource damagesource, final float f) {
        final boolean flag = super.attackEntityFrom(damagesource, f);
        if (flag && this.isChild() && this.isMountHostile()) {
            final Entity attacker = damagesource.getEntity();
            if (attacker instanceof EntityLivingBase) {
                final List list = ((Entity)this).worldObj.getEntitiesWithinAABBExcludingEntity((Entity)this, ((Entity)this).boundingBox.expand(12.0, 12.0, 12.0));
                for (int i = 0; i < list.size(); ++i) {
                    final Entity entity = list.get(i);
                    if (entity.getClass() == this.getClass()) {
                        final LOTREntityHorse mount = (LOTREntityHorse)entity;
                        if (!mount.isChild() && !mount.isTame()) {
                            mount.setAttackTarget((EntityLivingBase)attacker);
                        }
                    }
                }
            }
        }
        return flag;
    }
    
    public void openGUI(final EntityPlayer entityplayer) {
        if (!((Entity)this).worldObj.isClient && (((Entity)this).riddenByEntity == null || ((Entity)this).riddenByEntity == entityplayer) && this.isTame()) {
            final AnimalChest animalchest = LOTRReflection.getHorseInv(this);
            animalchest.func_110133_a(this.getCommandSenderName());
            entityplayer.openGui((Object)LOTRMod.instance, 29, ((Entity)this).worldObj, this.getEntityId(), animalchest.getSizeInventory(), 0);
        }
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setBoolean("BelongsNPC", this.getBelongsToNPC());
        nbt.setBoolean("Mountable", this.getMountable());
        final AnimalChest inv = LOTRReflection.getHorseInv(this);
        if (inv.getStackInSlot(1) != null) {
            nbt.setTag("LOTRMountArmorItem", (NBTBase)inv.getStackInSlot(1).writeToNBT(new NBTTagCompound()));
        }
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        boolean pre35 = false;
        if (nbt.hasKey("BelongsToNPC")) {
            pre35 = true;
            this.setBelongsToNPC(nbt.getBoolean("BelongsToNPC"));
        }
        else {
            this.setBelongsToNPC(nbt.getBoolean("BelongsNPC"));
        }
        if (nbt.hasKey("Mountable")) {
            this.setMountable(nbt.getBoolean("Mountable"));
        }
        final AnimalChest inv = LOTRReflection.getHorseInv(this);
        if (nbt.hasKey("LOTRMountArmorItem")) {
            final ItemStack armor = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("LOTRMountArmorItem"));
            if (armor != null && this.isMountArmorValid(armor)) {
                inv.setInventorySlotContents(1, armor);
            }
        }
        if (pre35) {
            double jumpStrength = this.getEntityAttribute(LOTRReflection.getHorseJumpStrength()).getAttributeValue();
            if (jumpStrength > 1.0) {
                System.out.println("Reducing horse jump strength from " + jumpStrength);
                jumpStrength = 1.0;
                this.getEntityAttribute(LOTRReflection.getHorseJumpStrength()).setBaseValue(jumpStrength);
                System.out.println("Jump strength now " + this.getEntityAttribute(LOTRReflection.getHorseJumpStrength()).getAttributeValue());
            }
        }
    }
    
    public boolean canDespawn() {
        return this.getBelongsToNPC() && ((Entity)this).riddenByEntity == null;
    }
    
    public void onDeath(final DamageSource damagesource) {
        if (this.getBelongsToNPC()) {
            final AnimalChest inv = LOTRReflection.getHorseInv(this);
            inv.setInventorySlotContents(0, (ItemStack)null);
            inv.setInventorySlotContents(1, (ItemStack)null);
        }
        super.onDeath(damagesource);
    }
    
    public String getCommandSenderName() {
        if (this.getClass() == LOTREntityHorse.class) {
            return super.getCommandSenderName();
        }
        if (this.hasCustomNameTag()) {
            return this.getCustomNameTag();
        }
        final String s = EntityList.getEntityString((Entity)this);
        return StatCollector.translateToLocal("entity." + s + ".name");
    }
    
    public ItemStack getPickedResult(final MovingObjectPosition target) {
        return new ItemStack(LOTRMod.spawnEgg, 1, LOTREntities.getEntityID((Entity)this));
    }
    
    public boolean allowLeashing() {
        return !this.getBelongsToNPC() && super.allowLeashing();
    }
    
    public boolean shouldDismountInWater(final Entity rider) {
        return false;
    }
}
