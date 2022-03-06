// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity;

import java.util.Iterator;
import lotr.common.fac.LOTRFaction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import net.minecraft.entity.player.EntityPlayerMP;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketNPCRespawner;
import net.minecraft.block.Block;
import java.util.List;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.EntityList;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.entity.EntityLiving;
import net.minecraft.command.IEntitySelector;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.Item;
import net.minecraft.init.Blocks;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.LOTRMod;
import net.minecraft.world.World;
import net.minecraft.entity.Entity;

public class LOTREntityNPCRespawner extends Entity
{
    public float spawnerSpin;
    public float prevSpawnerSpin;
    private static final int spawnInterval_default = 3600;
    public int spawnInterval;
    public int noPlayerRange;
    public Class spawnClass1;
    public Class spawnClass2;
    public int checkHorizontalRange;
    public int checkVerticalMin;
    public int checkVerticalMax;
    public int spawnCap;
    public int spawnHorizontalRange;
    public int spawnVerticalMin;
    public int spawnVerticalMax;
    public int homeRange;
    private boolean setHomePosFromSpawn;
    public int mountSetting;
    public int blockEnemySpawns;
    public static final int MAX_SPAWN_BLOCK_RANGE = 64;
    
    public LOTREntityNPCRespawner(final World world) {
        super(world);
        this.spawnInterval = 3600;
        this.noPlayerRange = 24;
        this.checkHorizontalRange = 8;
        this.checkVerticalMin = -4;
        this.checkVerticalMax = 4;
        this.spawnCap = 4;
        this.spawnHorizontalRange = 4;
        this.spawnVerticalMin = -2;
        this.spawnVerticalMax = 2;
        this.homeRange = -1;
        this.setHomePosFromSpawn = false;
        this.mountSetting = 0;
        this.blockEnemySpawns = 0;
        this.setSize(1.0f, 1.0f);
        this.spawnerSpin = super.rand.nextFloat() * 360.0f;
    }
    
    public void setSpawnClass(final Class c) {
        this.spawnClass1 = c;
    }
    
    public void setSpawnClasses(final Class c1, final Class c2) {
        this.spawnClass1 = c1;
        this.spawnClass2 = c2;
    }
    
    public void setCheckRanges(final int xz, final int y, final int y1, final int l) {
        this.checkHorizontalRange = xz;
        this.checkVerticalMin = y;
        this.checkVerticalMax = y1;
        this.spawnCap = l;
    }
    
    public void setSpawnRanges(final int xz, final int y, final int y1, final int h) {
        this.spawnHorizontalRange = xz;
        this.spawnVerticalMin = y;
        this.spawnVerticalMax = y1;
        this.homeRange = h;
    }
    
    public void setHomePosFromSpawn() {
        this.setHomePosFromSpawn = true;
    }
    
    public boolean hasHomeRange() {
        return this.homeRange >= 0;
    }
    
    public void setMountSetting(final int i) {
        this.mountSetting = i;
    }
    
    public void toggleMountSetting() {
        if (this.mountSetting == 0) {
            this.mountSetting = 1;
        }
        else if (this.mountSetting == 1) {
            this.mountSetting = 2;
        }
        else {
            this.mountSetting = 0;
        }
    }
    
    public void setNoPlayerRange(final int i) {
        this.noPlayerRange = i;
    }
    
    public boolean blockEnemySpawns() {
        return this.blockEnemySpawns > 0;
    }
    
    public void setBlockEnemySpawnRange(int i) {
        i = Math.min(i, 64);
        this.blockEnemySpawns = i;
    }
    
    public void setSpawnInterval(final int i) {
        this.spawnInterval = i;
    }
    
    public void setSpawnIntervalMinutes(final int m) {
        final int s = m * 60;
        final int t = s * 20;
        this.setSpawnInterval(t);
    }
    
    protected void entityInit() {
    }
    
    public boolean isInvisible() {
        if (!super.worldObj.isClient) {
            return super.isInvisible();
        }
        final EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
        return entityplayer == null || !entityplayer.capabilities.isCreativeMode;
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbt) {
        this.writeSpawnerDataToNBT(nbt);
    }
    
    public void writeSpawnerDataToNBT(final NBTTagCompound nbt) {
        nbt.setInteger("SpawnInterval", this.spawnInterval);
        nbt.setByte("NoPlayerRange", (byte)this.noPlayerRange);
        String class1String = "";
        String class2String = "";
        if (this.spawnClass1 != null) {
            class1String = LOTREntities.getStringFromClass(this.spawnClass1);
        }
        if (this.spawnClass2 != null) {
            class2String = LOTREntities.getStringFromClass(this.spawnClass2);
        }
        nbt.setString("SpawnClass1", (class1String == null) ? "" : class1String);
        nbt.setString("SpawnClass2", (class2String == null) ? "" : class2String);
        nbt.setByte("CheckHorizontal", (byte)this.checkHorizontalRange);
        nbt.setByte("CheckVerticalMin", (byte)this.checkVerticalMin);
        nbt.setByte("CheckVerticalMax", (byte)this.checkVerticalMax);
        nbt.setByte("SpawnCap", (byte)this.spawnCap);
        nbt.setByte("SpawnHorizontal", (byte)this.spawnHorizontalRange);
        nbt.setByte("SpawnVerticalMin", (byte)this.spawnVerticalMin);
        nbt.setByte("SpawnVerticalMax", (byte)this.spawnVerticalMax);
        nbt.setByte("HomeRange", (byte)this.homeRange);
        nbt.setBoolean("HomeSpawn", this.setHomePosFromSpawn);
        nbt.setByte("MountSetting", (byte)this.mountSetting);
        nbt.setByte("BlockEnemy", (byte)this.blockEnemySpawns);
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        this.readSpawnerDataFromNBT(nbt);
    }
    
    public void readSpawnerDataFromNBT(final NBTTagCompound nbt) {
        this.spawnInterval = nbt.getInteger("SpawnInterval");
        if (this.spawnInterval <= 0) {
            this.spawnInterval = 3600;
        }
        this.noPlayerRange = nbt.getByte("NoPlayerRange");
        this.spawnClass1 = LOTREntities.getClassFromString(nbt.getString("SpawnClass1"));
        this.spawnClass2 = LOTREntities.getClassFromString(nbt.getString("SpawnClass2"));
        if (this.spawnClass1 != null && !LOTREntityNPC.class.isAssignableFrom(this.spawnClass1)) {
            this.spawnClass1 = null;
        }
        if (this.spawnClass2 != null && !LOTREntityNPC.class.isAssignableFrom(this.spawnClass2)) {
            this.spawnClass2 = null;
        }
        this.checkHorizontalRange = nbt.getByte("CheckHorizontal");
        this.checkVerticalMin = nbt.getByte("CheckVerticalMin");
        this.checkVerticalMax = nbt.getByte("CheckVerticalMax");
        this.spawnCap = nbt.getByte("SpawnCap");
        this.spawnHorizontalRange = nbt.getByte("SpawnHorizontal");
        this.spawnVerticalMin = nbt.getByte("SpawnVerticalMin");
        this.spawnVerticalMax = nbt.getByte("SpawnVerticalMax");
        this.homeRange = nbt.getByte("HomeRange");
        this.setHomePosFromSpawn = nbt.getBoolean("HomeSpawn");
        this.mountSetting = nbt.getByte("MountSetting");
        this.blockEnemySpawns = nbt.getByte("BlockEnemy");
    }
    
    public void onBreak() {
        super.worldObj.playSoundAtEntity((Entity)this, Blocks.glass.stepSound.getDigResourcePath(), (Blocks.glass.stepSound.getVolume() + 1.0f) / 2.0f, Blocks.glass.stepSound.getFrequency() * 0.8f);
        super.worldObj.setEntityState((Entity)this, (byte)16);
        this.setDead();
    }
    
    @SideOnly(Side.CLIENT)
    public void handleHealthUpdate(final byte b) {
        if (b == 16) {
            for (int l = 0; l < 16; ++l) {
                final double d = super.posX + (super.rand.nextDouble() - 0.5) * super.width;
                final double d2 = super.posY + super.rand.nextDouble() * super.height;
                final double d3 = super.posZ + (super.rand.nextDouble() - 0.5) * super.width;
                super.worldObj.spawnParticle("iconcrack_" + Item.getIdFromItem(LOTRMod.npcRespawner), d, d2, d3, 0.0, 0.0, 0.0);
            }
        }
        else {
            super.handleHealthUpdate(b);
        }
    }
    
    public void onUpdate() {
        super.prevPosX = super.posX;
        super.prevPosY = super.posY;
        super.prevPosZ = super.posZ;
        this.prevSpawnerSpin = this.spawnerSpin;
        this.spawnerSpin += 6.0f;
        this.prevSpawnerSpin = MathHelper.wrapAngleTo180_float(this.prevSpawnerSpin);
        this.spawnerSpin = MathHelper.wrapAngleTo180_float(this.spawnerSpin);
        super.motionX = 0.0;
        super.motionY = 0.0;
        super.motionZ = 0.0;
        this.moveEntity(super.motionX, super.motionY, super.motionZ);
        if (!super.worldObj.isClient && super.ticksExisted % this.spawnInterval == 0 && (this.spawnClass1 != null || this.spawnClass2 != null)) {
            final int i = MathHelper.floor_double(super.posX);
            final int j = MathHelper.floor_double(super.boundingBox.minY);
            final int k = MathHelper.floor_double(super.posZ);
            final int minX = i - this.checkHorizontalRange;
            final int minY = j + this.checkVerticalMin;
            final int minZ = k - this.checkHorizontalRange;
            final int maxX = i + this.checkHorizontalRange;
            final int maxY = j + this.checkVerticalMax;
            final int maxZ = k + this.checkHorizontalRange;
            if (super.worldObj.checkChunksExist(minX, minY, minZ, maxX, maxY, maxZ) && super.worldObj.getClosestPlayer(i + 0.5, j + 0.5, k + 0.5, (double)this.noPlayerRange) == null) {
                final AxisAlignedBB checkAABB = AxisAlignedBB.getBoundingBox((double)minX, (double)minY, (double)minZ, (double)(maxX + 1), (double)(maxY + 1), (double)(maxZ + 1));
                final IEntitySelector checkSelector = (IEntitySelector)new IEntitySelector() {
                    public boolean isEntityApplicable(final Entity entity) {
                        if (!entity.isEntityAlive()) {
                            return false;
                        }
                        final Class entityClass = entity.getClass();
                        return (LOTREntityNPCRespawner.this.spawnClass1 != null && LOTREntityNPCRespawner.this.spawnClass1.isAssignableFrom(entityClass)) || (LOTREntityNPCRespawner.this.spawnClass2 != null && LOTREntityNPCRespawner.this.spawnClass2.isAssignableFrom(entityClass));
                    }
                };
                final List nearbyEntities = super.worldObj.selectEntitiesWithinAABB((Class)EntityLiving.class, checkAABB, checkSelector);
                int entities = nearbyEntities.size();
                if (entities < this.spawnCap) {
                    for (int attempts = 16, l = 0; l < attempts; ++l) {
                        final int spawnX = i + MathHelper.getRandomIntegerInRange(super.rand, -this.spawnHorizontalRange, this.spawnHorizontalRange);
                        final int spawnY = j + MathHelper.getRandomIntegerInRange(super.rand, this.spawnVerticalMin, this.spawnVerticalMax);
                        final int spawnZ = k + MathHelper.getRandomIntegerInRange(super.rand, -this.spawnHorizontalRange, this.spawnHorizontalRange);
                        final Block belowBlock = super.worldObj.getBlock(spawnX, spawnY - 1, spawnZ);
                        final int belowMeta = super.worldObj.getBlockMetadata(spawnX, spawnY - 1, spawnZ);
                        final boolean belowSolid = belowBlock.isSideSolid((IBlockAccess)super.worldObj, spawnX, spawnY - 1, spawnZ, ForgeDirection.UP);
                        if (belowSolid && !super.worldObj.getBlock(spawnX, spawnY, spawnZ).isNormalCube() && !super.worldObj.getBlock(spawnX, spawnY + 1, spawnZ).isNormalCube()) {
                            Class entityClass = null;
                            if (this.spawnClass1 != null && this.spawnClass2 != null) {
                                entityClass = ((super.rand.nextInt(3) == 0) ? this.spawnClass2 : this.spawnClass1);
                            }
                            else if (this.spawnClass1 != null) {
                                entityClass = this.spawnClass1;
                            }
                            else if (this.spawnClass2 != null) {
                                entityClass = this.spawnClass2;
                            }
                            final String entityName = LOTREntities.getStringFromClass(entityClass);
                            final LOTREntityNPC entity = (LOTREntityNPC)EntityList.createEntityByName(entityName, super.worldObj);
                            entity.setLocationAndAngles(spawnX + 0.5, (double)spawnY, spawnZ + 0.5, super.rand.nextFloat() * 360.0f, 0.0f);
                            entity.isNPCPersistent = true;
                            entity.liftSpawnRestrictions = true;
                            if (entity.getCanSpawnHere()) {
                                entity.liftSpawnRestrictions = false;
                                super.worldObj.spawnEntityInWorld((Entity)entity);
                                if (this.mountSetting == 0) {
                                    entity.spawnRidingHorse = false;
                                }
                                else if (this.mountSetting == 1) {
                                    entity.spawnRidingHorse = true;
                                }
                                entity.onSpawnWithEgg(null);
                                if (this.hasHomeRange()) {
                                    if (this.setHomePosFromSpawn) {
                                        entity.setHomeArea(spawnX, spawnY, spawnZ, this.homeRange);
                                    }
                                    else {
                                        entity.setHomeArea(i, j, k, this.homeRange);
                                    }
                                }
                                else {
                                    entity.detachHome();
                                }
                                if (++entities >= this.spawnCap) {
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    public boolean interactFirst(final EntityPlayer entityplayer) {
        if (entityplayer.capabilities.isCreativeMode) {
            if (!super.worldObj.isClient) {
                final LOTRPacketNPCRespawner packet = new LOTRPacketNPCRespawner(this);
                LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, (EntityPlayerMP)entityplayer);
            }
            return true;
        }
        return false;
    }
    
    public boolean canBeCollidedWith() {
        if (!super.worldObj.isClient) {
            return false;
        }
        final EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
        return entityplayer != null && entityplayer.capabilities.isCreativeMode;
    }
    
    public void applyEntityCollision(final Entity entity) {
    }
    
    public ItemStack getPickedResult(final MovingObjectPosition target) {
        return new ItemStack(LOTRMod.npcRespawner);
    }
    
    public AxisAlignedBB createSpawnBlockRegion() {
        if (!this.blockEnemySpawns()) {
            return null;
        }
        final int i = MathHelper.floor_double(super.posX);
        final int j = MathHelper.floor_double(super.boundingBox.minY);
        final int k = MathHelper.floor_double(super.posZ);
        final int range = this.blockEnemySpawns;
        return AxisAlignedBB.getBoundingBox((double)i, (double)j, (double)k, (double)(i + 1), (double)(j + 1), (double)(k + 1)).expand((double)range, (double)range, (double)range);
    }
    
    public boolean isEnemySpawnBlocked(final LOTREntityNPC npc) {
        return this.isEnemySpawnBlocked(npc.getFaction());
    }
    
    public boolean isEnemySpawnBlocked(final LOTRFaction spawnFaction) {
        if (this.spawnClass1 != null) {
            final LOTRFaction faction1 = ((LOTREntityNPC)EntityList.createEntityByName(LOTREntities.getStringFromClass(this.spawnClass1), super.worldObj)).getFaction();
            if (faction1 != null && faction1.isBadRelation(spawnFaction)) {
                return true;
            }
        }
        if (this.spawnClass2 != null) {
            final LOTRFaction faction2 = ((LOTREntityNPC)EntityList.createEntityByName(LOTREntities.getStringFromClass(this.spawnClass2), super.worldObj)).getFaction();
            if (faction2 != null && faction2.isBadRelation(spawnFaction)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isSpawnBlocked(final LOTREntityNPC npc) {
        return isSpawnBlocked((Entity)npc, npc.getFaction());
    }
    
    public static boolean isSpawnBlocked(final Entity entity, final LOTRFaction spawnFaction) {
        final World world = entity.worldObj;
        final int i = MathHelper.floor_double(entity.posX);
        final int j = MathHelper.floor_double(entity.boundingBox.minY);
        final int k = MathHelper.floor_double(entity.posZ);
        final AxisAlignedBB originBB = AxisAlignedBB.getBoundingBox((double)i, (double)j, (double)k, (double)(i + 1), (double)(j + 1), (double)(k + 1));
        final int range = 64;
        final AxisAlignedBB searchBB = originBB.expand((double)range, (double)range, (double)range);
        final List spawners = world.getEntitiesWithinAABB((Class)LOTREntityNPCRespawner.class, searchBB);
        if (!spawners.isEmpty()) {
            for (final Object obj : spawners) {
                final LOTREntityNPCRespawner spawner = (LOTREntityNPCRespawner)obj;
                if (spawner.blockEnemySpawns()) {
                    final AxisAlignedBB spawnBlockBB = spawner.createSpawnBlockRegion();
                    if (spawnBlockBB.intersectsWith(searchBB) && spawnBlockBB.intersectsWith(originBB) && spawner.isEnemySpawnBlocked(spawnFaction)) {
                        return true;
                    }
                    continue;
                }
            }
        }
        return false;
    }
}
