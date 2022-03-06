// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.animal;

import lotr.common.entity.LOTREntities;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.block.material.Material;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.init.Items;
import net.minecraft.util.DamageSource;
import lotr.common.block.LOTRBlockBerryBush;
import net.minecraft.block.BlockCrops;
import java.util.ArrayList;
import net.minecraft.block.Block;
import net.minecraftforge.common.util.ForgeDirection;
import java.util.List;
import lotr.common.entity.LOTRScarecrows;
import net.minecraft.command.IEntitySelector;
import net.minecraft.item.Item;
import lotr.common.item.LOTRValuableItems;
import lotr.common.LOTRMod;
import net.minecraft.item.ItemFood;
import net.minecraftforge.common.EnumPlantType;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.IPlantable;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.Entity;
import java.util.UUID;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.world.biome.LOTRBiomeGenFarHarad;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.item.EntityItem;
import lotr.common.inventory.LOTREntityInventory;
import net.minecraft.util.ChunkCoordinates;
import lotr.common.entity.LOTRRandomSkinEntity;
import net.minecraft.entity.EntityLiving;

public class LOTREntityBird extends EntityLiving implements LOTRAmbientCreature, LOTRRandomSkinEntity
{
    private ChunkCoordinates currentFlightTarget;
    private int flightTargetTime;
    private static final int flightTargetTimeMax = 400;
    public int flapTime;
    private LOTREntityInventory birdInv;
    private EntityItem stealTargetItem;
    private EntityPlayer stealTargetPlayer;
    private static final int maxStealAmount = 4;
    private int stolenTime;
    private static final int stolenTimeMax = 200;
    private boolean stealingCrops;
    
    public LOTREntityBird(final World world) {
        super(world);
        this.flightTargetTime = 0;
        this.flapTime = 0;
        this.birdInv = new LOTREntityInventory("BirdItems", (EntityLivingBase)this, 9);
        this.stolenTime = 0;
        this.stealingCrops = false;
        this.setSize(0.5f, 0.5f);
        super.tasks.addTask(0, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityPlayer.class, 12.0f, 0.05f));
        super.tasks.addTask(1, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityLiving.class, 12.0f, 0.1f));
        super.tasks.addTask(2, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    }
    
    public void entityInit() {
        super.entityInit();
        ((Entity)this).dataWatcher.addObject(16, (Object)0);
        ((Entity)this).dataWatcher.addObject(17, (Object)1);
    }
    
    public BirdType getBirdType() {
        int i = ((Entity)this).dataWatcher.getWatchableObjectByte(16);
        if (i < 0 || i >= BirdType.values().length) {
            i = 0;
        }
        return BirdType.values()[i];
    }
    
    public void setBirdType(final BirdType type) {
        this.setBirdType(type.ordinal());
    }
    
    public void setBirdType(final int i) {
        ((Entity)this).dataWatcher.updateObject(16, (Object)(byte)i);
    }
    
    public boolean isBirdStill() {
        return ((Entity)this).dataWatcher.getWatchableObjectByte(17) == 1;
    }
    
    public void setBirdStill(final boolean flag) {
        ((Entity)this).dataWatcher.updateObject(17, (Object)(byte)(flag ? 1 : 0));
    }
    
    public String getBirdTextureDir() {
        return this.getBirdType().textureDir;
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(MathHelper.getRandomDoubleInRange(((Entity)this).rand, 0.08, 0.13));
    }
    
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        final int i = MathHelper.floor_double(((Entity)this).posX);
        final int j = MathHelper.floor_double(((Entity)this).posY);
        final int k = MathHelper.floor_double(((Entity)this).posZ);
        final BiomeGenBase biome = ((Entity)this).worldObj.getBiomeGenForCoords(i, k);
        if (biome instanceof LOTRBiomeGenFarHarad) {
            if (((Entity)this).rand.nextInt(8) == 0) {
                this.setBirdType(BirdType.CROW);
            }
            else {
                this.setBirdType(BirdType.FAR_HARAD);
            }
        }
        else if (((Entity)this).rand.nextInt(6) == 0) {
            this.setBirdType(BirdType.CROW);
        }
        else if (((Entity)this).rand.nextInt(10) == 0) {
            this.setBirdType(BirdType.MAGPIE);
        }
        else {
            this.setBirdType(BirdType.COMMON);
        }
        return data;
    }
    
    public void setUniqueID(final UUID uuid) {
        ((Entity)this).entityUniqueID = uuid;
    }
    
    public boolean canBePushed() {
        return false;
    }
    
    protected void collideWithEntity(final Entity entity) {
    }
    
    protected void collideWithNearbyEntities() {
    }
    
    protected boolean isAIEnabled() {
        return true;
    }
    
    protected boolean canStealItems() {
        return this.getBirdType().canSteal;
    }
    
    protected boolean isStealable(final ItemStack itemstack) {
        final BirdType type = this.getBirdType();
        final Item item = itemstack.getItem();
        if (type == BirdType.COMMON) {
            return item instanceof IPlantable && ((IPlantable)item).getPlantType((IBlockAccess)((Entity)this).worldObj, -1, -1, -1) == EnumPlantType.Crop;
        }
        if (type == BirdType.CROW) {
            return item instanceof ItemFood || LOTRMod.isOreNameEqual(itemstack, "bone");
        }
        return type == BirdType.MAGPIE && LOTRValuableItems.canMagpieSteal(itemstack);
    }
    
    public ItemStack getStolenItem() {
        return this.getEquipmentInSlot(4);
    }
    
    public void setStolenItem(final ItemStack itemstack) {
        this.setCurrentItemOrArmor(4, itemstack);
    }
    
    public void onUpdate() {
        super.onUpdate();
        if (this.isBirdStill()) {
            final double motionX = 0.0;
            ((Entity)this).motionZ = motionX;
            ((Entity)this).motionY = motionX;
            ((Entity)this).motionX = motionX;
            ((Entity)this).posY = MathHelper.floor_double(((Entity)this).posY);
            if (((Entity)this).worldObj.isClient) {
                if (((Entity)this).rand.nextInt(200) == 0) {
                    this.flapTime = 40;
                }
                if (this.flapTime > 0) {
                    --this.flapTime;
                }
            }
        }
        else {
            ((Entity)this).motionY *= 0.6;
            if (((Entity)this).worldObj.isClient) {
                this.flapTime = 0;
            }
        }
    }
    
    protected void updateAITasks() {
        super.updateAITasks();
        if (this.getStolenItem() != null) {
            ++this.stolenTime;
            if (this.stolenTime >= 200) {
                this.setStolenItem(null);
                this.stolenTime = 0;
            }
        }
        if (this.isBirdStill()) {
            if (!this.canBirdSit()) {
                this.setBirdStill(false);
            }
            else if (((Entity)this).rand.nextInt(400) == 0 || ((Entity)this).worldObj.getClosestPlayerToEntity((Entity)this, 6.0) != null) {
                this.setBirdStill(false);
            }
        }
        else {
            if (this.canStealItems() && !this.stealingCrops && this.stealTargetItem == null && this.stealTargetPlayer == null && !this.birdInv.isFull() && ((Entity)this).rand.nextInt(100) == 0) {
                final double range = 16.0;
                final List<EntityPlayer> players = (List<EntityPlayer>)((Entity)this).worldObj.selectEntitiesWithinAABB((Class)EntityPlayer.class, ((Entity)this).boundingBox.expand(range, range, range), (IEntitySelector)new IEntitySelector() {
                    public boolean isEntityApplicable(final Entity e) {
                        if (e instanceof EntityPlayer) {
                            final EntityPlayer entityplayer = (EntityPlayer)e;
                            if (LOTREntityBird.this.canStealPlayer(entityplayer)) {
                                final ChunkCoordinates coords = LOTREntityBird.this.getPlayerFlightTarget(entityplayer);
                                return LOTREntityBird.this.isValidFlightTarget(coords);
                            }
                        }
                        return false;
                    }
                });
                if (!players.isEmpty()) {
                    this.stealTargetPlayer = players.get(((Entity)this).rand.nextInt(players.size()));
                    this.currentFlightTarget = this.getPlayerFlightTarget(this.stealTargetPlayer);
                    this.newFlight();
                }
                else {
                    final List<EntityItem> entityItems = (List<EntityItem>)((Entity)this).worldObj.selectEntitiesWithinAABB((Class)EntityItem.class, ((Entity)this).boundingBox.expand(range, range, range), (IEntitySelector)new IEntitySelector() {
                        public boolean isEntityApplicable(final Entity e) {
                            if (e instanceof EntityItem) {
                                final EntityItem eItem = (EntityItem)e;
                                if (LOTREntityBird.this.canStealItem(eItem)) {
                                    final ChunkCoordinates coords = LOTREntityBird.this.getItemFlightTarget(eItem);
                                    return LOTREntityBird.this.isValidFlightTarget(coords);
                                }
                            }
                            return false;
                        }
                    });
                    if (!entityItems.isEmpty()) {
                        this.stealTargetItem = entityItems.get(((Entity)this).rand.nextInt(entityItems.size()));
                        this.currentFlightTarget = this.getItemFlightTarget(this.stealTargetItem);
                        this.newFlight();
                    }
                }
            }
            if (this.stealTargetItem != null || this.stealTargetPlayer != null) {
                if (this.birdInv.isFull() || this.currentFlightTarget == null || !this.isValidFlightTarget(this.currentFlightTarget)) {
                    this.cancelFlight();
                }
                else if (this.stealTargetItem != null && !this.canStealItem(this.stealTargetItem)) {
                    this.cancelFlight();
                }
                else if (this.stealTargetPlayer != null && !this.canStealPlayer(this.stealTargetPlayer)) {
                    this.cancelFlight();
                }
                else {
                    if (this.stealTargetItem != null) {
                        this.currentFlightTarget = this.getItemFlightTarget(this.stealTargetItem);
                    }
                    else if (this.stealTargetPlayer != null) {
                        this.currentFlightTarget = this.getPlayerFlightTarget(this.stealTargetPlayer);
                    }
                    if (this.getDistanceSqToFlightTarget() < 1.0) {
                        ItemStack stolenItem = null;
                        if (this.stealTargetItem != null) {
                            final ItemStack itemstack = this.stealTargetItem.getEntityItem();
                            final ItemStack stealCopy = itemstack.copy();
                            stealCopy.stackSize = MathHelper.getRandomIntegerInRange(((Entity)this).rand, 1, Math.min(stealCopy.stackSize, 4));
                            final ItemStack safeCopy = stealCopy.copy();
                            if (this.birdInv.addItemToInventory(stealCopy)) {
                                final ItemStack itemStack = itemstack;
                                itemStack.stackSize -= safeCopy.stackSize - stealCopy.stackSize;
                                if (itemstack.stackSize <= 0) {
                                    this.stealTargetItem.setDead();
                                }
                                stolenItem = safeCopy;
                            }
                        }
                        else if (this.stealTargetPlayer != null) {
                            final List<Integer> slots = this.getStealablePlayerSlots(this.stealTargetPlayer);
                            final int randSlot = slots.get(((Entity)this).rand.nextInt(slots.size()));
                            ItemStack itemstack2 = this.stealTargetPlayer.inventory.getStackInSlot(randSlot);
                            final ItemStack stealCopy2 = itemstack2.copy();
                            stealCopy2.stackSize = MathHelper.getRandomIntegerInRange(((Entity)this).rand, 1, Math.min(stealCopy2.stackSize, 4));
                            final ItemStack safeCopy2 = stealCopy2.copy();
                            if (this.birdInv.addItemToInventory(stealCopy2)) {
                                final ItemStack itemStack2 = itemstack2;
                                itemStack2.stackSize -= safeCopy2.stackSize - stealCopy2.stackSize;
                                if (itemstack2.stackSize <= 0) {
                                    itemstack2 = null;
                                }
                                this.stealTargetPlayer.inventory.setInventorySlotContents(randSlot, itemstack2);
                                stolenItem = safeCopy2;
                            }
                        }
                        if (stolenItem != null) {
                            this.stolenTime = 0;
                            this.setStolenItem(stolenItem);
                            this.playSound("random.pop", 0.5f, ((((Entity)this).rand.nextFloat() - ((Entity)this).rand.nextFloat()) * 0.7f + 1.0f) * 2.0f);
                        }
                        this.cancelFlight();
                    }
                }
            }
            else if (this.stealingCrops) {
                if (!LOTRMod.canGrief(((Entity)this).worldObj)) {
                    this.stealingCrops = false;
                }
                else if (this.currentFlightTarget == null || !this.isValidFlightTarget(this.currentFlightTarget)) {
                    this.cancelFlight();
                }
                else {
                    final int i = this.currentFlightTarget.posX;
                    final int j = this.currentFlightTarget.posY;
                    final int k = this.currentFlightTarget.posZ;
                    if (this.getDistanceSqToFlightTarget() < 1.0) {
                        if (this.canStealCrops(i, j, k)) {
                            this.eatCropBlock(i, j, k);
                            this.playSound("random.eat", 1.0f, (((Entity)this).worldObj.rand.nextFloat() - ((Entity)this).worldObj.rand.nextFloat()) * 0.2f + 1.0f);
                        }
                        this.cancelFlight();
                    }
                    else if (!this.canStealCrops(i, j, k)) {
                        this.cancelFlight();
                    }
                    else if (this.flightTargetTime % 100 == 0 && LOTRScarecrows.anyScarecrowsNearby(((Entity)this).worldObj, i, j, k)) {
                        this.cancelFlight();
                    }
                }
            }
            else {
                if (LOTRMod.canGrief(((Entity)this).worldObj) && !this.stealingCrops && ((Entity)this).rand.nextInt(100) == 0) {
                    final int i = MathHelper.floor_double(((Entity)this).posX);
                    final int j = MathHelper.floor_double(((Entity)this).posY);
                    final int k = MathHelper.floor_double(((Entity)this).posZ);
                    final int range2 = 16;
                    final int yRange = 8;
                    for (int attempts = 32, l = 0; l < attempts; ++l) {
                        final int i2 = i + MathHelper.getRandomIntegerInRange(((Entity)this).rand, -range2, range2);
                        final int j2 = j + MathHelper.getRandomIntegerInRange(((Entity)this).rand, -yRange, yRange);
                        final int k2 = k + MathHelper.getRandomIntegerInRange(((Entity)this).rand, -range2, range2);
                        if (this.canStealCrops(i2, j2, k2) && !LOTRScarecrows.anyScarecrowsNearby(((Entity)this).worldObj, i2, j2, k2)) {
                            this.stealingCrops = true;
                            this.currentFlightTarget = new ChunkCoordinates(i2, j2, k2);
                            this.newFlight();
                            break;
                        }
                    }
                }
                if (!this.stealingCrops) {
                    if (this.currentFlightTarget != null && !this.isValidFlightTarget(this.currentFlightTarget)) {
                        this.cancelFlight();
                    }
                    if (this.currentFlightTarget == null || ((Entity)this).rand.nextInt(50) == 0 || this.getDistanceSqToFlightTarget() < 4.0) {
                        int i = MathHelper.floor_double(((Entity)this).posX);
                        int j = MathHelper.floor_double(((Entity)this).posY);
                        int k = MathHelper.floor_double(((Entity)this).posZ);
                        i += ((Entity)this).rand.nextInt(16) - ((Entity)this).rand.nextInt(16);
                        k += ((Entity)this).rand.nextInt(16) - ((Entity)this).rand.nextInt(16);
                        j += MathHelper.getRandomIntegerInRange(((Entity)this).rand, -2, 3);
                        this.currentFlightTarget = new ChunkCoordinates(i, j, k);
                        this.newFlight();
                    }
                }
            }
            if (this.currentFlightTarget != null) {
                final double speed = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
                final double d0 = this.currentFlightTarget.posX + 0.5 - ((Entity)this).posX;
                final double d2 = this.currentFlightTarget.posY + 0.5 - ((Entity)this).posY;
                final double d3 = this.currentFlightTarget.posZ + 0.5 - ((Entity)this).posZ;
                ((Entity)this).motionX += (Math.signum(d0) * 0.5 - ((Entity)this).motionX) * speed;
                ((Entity)this).motionY += (Math.signum(d2) * 0.8 - ((Entity)this).motionY) * speed;
                ((Entity)this).motionZ += (Math.signum(d3) * 0.5 - ((Entity)this).motionZ) * speed;
                final float f = (float)(Math.atan2(((Entity)this).motionZ, ((Entity)this).motionX) * 180.0 / 3.141592653589793) - 90.0f;
                final float f2 = MathHelper.wrapAngleTo180_float(f - ((Entity)this).rotationYaw);
                ((EntityLivingBase)this).moveForward = 0.5f;
                ((Entity)this).rotationYaw += f2;
                ++this.flightTargetTime;
                if (this.flightTargetTime >= 400) {
                    this.cancelFlight();
                }
            }
            if (((Entity)this).rand.nextInt(200) == 0 && this.canBirdSit()) {
                this.setBirdStill(true);
                this.cancelFlight();
            }
        }
    }
    
    private boolean canBirdSit() {
        final int i = MathHelper.floor_double(((Entity)this).posX);
        final int j = MathHelper.floor_double(((Entity)this).posY);
        final int k = MathHelper.floor_double(((Entity)this).posZ);
        final Block block = ((Entity)this).worldObj.getBlock(i, j, k);
        final Block below = ((Entity)this).worldObj.getBlock(i, j - 1, k);
        return block.getBlocksMovement((IBlockAccess)((Entity)this).worldObj, i, j, k) && below.isSideSolid((IBlockAccess)((Entity)this).worldObj, i, j - 1, k, ForgeDirection.UP);
    }
    
    private boolean isValidFlightTarget(final ChunkCoordinates coords) {
        final int i = coords.posX;
        final int j = coords.posY;
        final int k = coords.posZ;
        if (j >= 1) {
            final Block block = ((Entity)this).worldObj.getBlock(i, j, k);
            return block.getBlocksMovement((IBlockAccess)((Entity)this).worldObj, i, j, k);
        }
        return false;
    }
    
    private double getDistanceSqToFlightTarget() {
        final double d = this.currentFlightTarget.posX + 0.5;
        final double d2 = this.currentFlightTarget.posY + 0.5;
        final double d3 = this.currentFlightTarget.posZ + 0.5;
        return this.getDistanceSq(d, d2, d3);
    }
    
    private void cancelFlight() {
        this.currentFlightTarget = null;
        this.flightTargetTime = 0;
        this.stealTargetItem = null;
        this.stealTargetPlayer = null;
        this.stealingCrops = false;
    }
    
    private void newFlight() {
        this.flightTargetTime = 0;
    }
    
    private boolean canStealItem(final EntityItem entity) {
        return entity.isEntityAlive() && this.isStealable(entity.getEntityItem());
    }
    
    private boolean canStealPlayer(final EntityPlayer entityplayer) {
        if (entityplayer.capabilities.isCreativeMode || !entityplayer.isEntityAlive()) {
            return false;
        }
        final List<Integer> slots = this.getStealablePlayerSlots(entityplayer);
        return !slots.isEmpty();
    }
    
    private List<Integer> getStealablePlayerSlots(final EntityPlayer entityplayer) {
        final List<Integer> slots = new ArrayList<Integer>();
        for (int i = 0; i <= 8; ++i) {
            if (i == entityplayer.inventory.currentItem) {
                final ItemStack itemstack = entityplayer.inventory.getStackInSlot(i);
                if (itemstack != null && this.isStealable(itemstack)) {
                    slots.add(i);
                }
            }
        }
        return slots;
    }
    
    private ChunkCoordinates getItemFlightTarget(final EntityItem entity) {
        final int i = MathHelper.floor_double(((Entity)entity).posX);
        final int j = MathHelper.floor_double(((Entity)entity).boundingBox.minY);
        final int k = MathHelper.floor_double(((Entity)entity).posZ);
        return new ChunkCoordinates(i, j, k);
    }
    
    private ChunkCoordinates getPlayerFlightTarget(final EntityPlayer entityplayer) {
        final int i = MathHelper.floor_double(((Entity)entityplayer).posX);
        final int j = MathHelper.floor_double(((Entity)entityplayer).boundingBox.minY + 1.0);
        final int k = MathHelper.floor_double(((Entity)entityplayer).posZ);
        return new ChunkCoordinates(i, j, k);
    }
    
    private boolean canStealCrops(final int i, final int j, final int k) {
        final Block block = ((Entity)this).worldObj.getBlock(i, j, k);
        if (block instanceof BlockCrops) {
            return true;
        }
        if (block instanceof LOTRBlockBerryBush) {
            final int meta = ((Entity)this).worldObj.getBlockMetadata(i, j, k);
            return LOTRBlockBerryBush.hasBerries(meta);
        }
        return false;
    }
    
    private void eatCropBlock(final int i, final int j, final int k) {
        final Block block = ((Entity)this).worldObj.getBlock(i, j, k);
        if (block instanceof LOTRBlockBerryBush) {
            int meta = ((Entity)this).worldObj.getBlockMetadata(i, j, k);
            meta = LOTRBlockBerryBush.setHasBerries(meta, false);
            ((Entity)this).worldObj.setBlockMetadata(i, j, k, meta, 3);
        }
        else {
            ((Entity)this).worldObj.setBlockToAir(i, j, k);
        }
    }
    
    protected boolean canTriggerWalking() {
        return false;
    }
    
    protected void fall(final float f) {
    }
    
    protected void updateFallState(final double d, final boolean flag) {
    }
    
    public boolean doesEntityNotTriggerPressurePlate() {
        return true;
    }
    
    public boolean attackEntityFrom(final DamageSource damagesource, final float f) {
        final boolean flag = super.attackEntityFrom(damagesource, f);
        if (flag && !((Entity)this).worldObj.isClient && this.isBirdStill()) {
            this.setBirdStill(false);
        }
        return flag;
    }
    
    protected void dropFewItems(final boolean flag, final int i) {
        for (int feathers = ((Entity)this).rand.nextInt(3) + ((Entity)this).rand.nextInt(i + 1), l = 0; l < feathers; ++l) {
            this.func_145779_a(Items.feather, 1);
        }
    }
    
    public void onDeath(final DamageSource damagesource) {
        super.onDeath(damagesource);
        if (!((Entity)this).worldObj.isClient) {
            this.birdInv.dropAllItems();
        }
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.setBirdType(nbt.getInteger("BirdType"));
        this.setBirdStill(nbt.getBoolean("BirdStill"));
        this.birdInv.writeToNBT(nbt);
        nbt.setShort("StealTime", (short)this.stolenTime);
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setInteger("BirdType", this.getBirdType().ordinal());
        nbt.setBoolean("BirdStill", this.isBirdStill());
        this.birdInv.readFromNBT(nbt);
        this.stolenTime = nbt.getShort("StealTime");
    }
    
    protected boolean canDespawn() {
        return super.canDespawn();
    }
    
    public boolean getCanSpawnHere() {
        return super.getCanSpawnHere() && this.canBirdSpawnHere();
    }
    
    protected boolean canBirdSpawnHere() {
        return LOTRAmbientSpawnChecks.canSpawn(this, 8, 12, 40, 4, Material.leaves);
    }
    
    public boolean allowLeashing() {
        return false;
    }
    
    protected boolean interact(final EntityPlayer entityplayer) {
        return false;
    }
    
    public int getTalkInterval() {
        return 60;
    }
    
    public void playLivingSound() {
        boolean sound = true;
        if (!((Entity)this).worldObj.isDaytime()) {
            sound = (((Entity)this).rand.nextInt(20) == 0);
        }
        if (sound) {
            super.playLivingSound();
        }
    }
    
    protected float getSoundVolume() {
        return 1.0f;
    }
    
    protected String getLivingSound() {
        final BirdType type = this.getBirdType();
        if (type == BirdType.CROW) {
            return "lotr:bird.crow.say";
        }
        return "lotr:bird.say";
    }
    
    protected String getHurtSound() {
        final BirdType type = this.getBirdType();
        if (type == BirdType.CROW) {
            return "lotr:bird.crow.hurt";
        }
        return "lotr:bird.hurt";
    }
    
    protected String getDeathSound() {
        final BirdType type = this.getBirdType();
        if (type == BirdType.CROW) {
            return "lotr:bird.crow.hurt";
        }
        return "lotr:bird.hurt";
    }
    
    public ItemStack getPickedResult(final MovingObjectPosition target) {
        return new ItemStack(LOTRMod.spawnEgg, 1, LOTREntities.getEntityID((Entity)this));
    }
    
    public enum BirdType
    {
        COMMON("common", true), 
        CROW("crow", true), 
        MAGPIE("magpie", true), 
        FAR_HARAD("farHarad", true);
        
        public final String textureDir;
        public final boolean canSteal;
        
        private BirdType(final String s, final boolean flag) {
            this.textureDir = s;
            this.canSteal = flag;
        }
    }
}
