// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.tileentity;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.network.Packet;
import net.minecraft.world.World;
import net.minecraft.nbt.NBTBase;
import net.minecraft.entity.IEntityLivingData;
import java.util.List;
import lotr.common.entity.LOTRMobSpawnerCondition;
import java.util.ArrayList;
import net.minecraft.util.AxisAlignedBB;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityList;
import lotr.common.entity.LOTREntities;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;

public class LOTRTileEntityMobSpawner extends TileEntity
{
    public int delay;
    private String entityClassName;
    public double yaw;
    public double prevYaw;
    private Entity spawnedMob;
    public int active;
    public boolean spawnsPersistentNPCs;
    public int minSpawnDelay;
    public int maxSpawnDelay;
    public int nearbyMobLimit;
    public int nearbyMobCheckRange;
    public int requiredPlayerRange;
    public int maxSpawnCount;
    public int maxSpawnRange;
    public int maxSpawnRangeVertical;
    public int maxHealth;
    public int navigatorRange;
    public float moveSpeed;
    public float attackDamage;
    private NBTTagCompound customSpawnData;
    
    public LOTRTileEntityMobSpawner() {
        this.delay = -1;
        this.entityClassName = "";
        this.prevYaw = 0.0;
        this.active = 1;
        this.spawnsPersistentNPCs = false;
        this.minSpawnDelay = 200;
        this.maxSpawnDelay = 800;
        this.nearbyMobLimit = 6;
        this.nearbyMobCheckRange = 8;
        this.requiredPlayerRange = 16;
        this.maxSpawnCount = 4;
        this.maxSpawnRange = 4;
        this.maxSpawnRangeVertical = 1;
        this.maxHealth = 20;
        this.navigatorRange = 16;
        this.moveSpeed = 0.2f;
        this.attackDamage = 2.0f;
        this.delay = 20;
    }
    
    public String getEntityClassName() {
        return this.entityClassName;
    }
    
    public void setEntityClassID(final int i) {
        this.setEntityClassName(LOTREntities.getStringFromID(i));
    }
    
    public void setEntityClassName(final String s) {
        this.entityClassName = s;
        if (!super.worldObj.isClient) {
            final Entity entity = EntityList.createEntityByName(this.entityClassName, super.worldObj);
            if (entity instanceof EntityLiving) {
                final EntityLiving entityliving = (EntityLiving)entity;
                if (entityliving.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.maxHealth) != null) {
                    this.maxHealth = (int)entityliving.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.maxHealth).getBaseValue();
                }
                if (entityliving.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.followRange) != null) {
                    this.navigatorRange = (int)entityliving.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.followRange).getBaseValue();
                }
                if (entityliving.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed) != null) {
                    this.moveSpeed = (float)entityliving.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed).getBaseValue();
                }
                if (entityliving.getAttributeMap().getAttributeInstance(LOTREntityNPC.npcAttackDamage) != null) {
                    this.attackDamage = (float)entityliving.getAttributeMap().getAttributeInstance(LOTREntityNPC.npcAttackDamage).getBaseValue();
                }
            }
        }
    }
    
    public boolean anyPlayerInRange() {
        return super.worldObj.getClosestPlayer(super.xCoord + 0.5, super.yCoord + 0.5, super.zCoord + 0.5, (double)this.requiredPlayerRange) != null;
    }
    
    public boolean isActive() {
        return this.active == 1 || (this.active == 2 && super.worldObj.isBlockIndirectlyGettingPowered(super.xCoord, super.yCoord, super.zCoord));
    }
    
    public void updateEntity() {
        if (this.anyPlayerInRange() && this.isActive()) {
            if (super.worldObj.isClient) {
                final double d = super.xCoord + super.worldObj.rand.nextFloat();
                final double d2 = super.yCoord + super.worldObj.rand.nextFloat();
                final double d3 = super.zCoord + super.worldObj.rand.nextFloat();
                super.worldObj.spawnParticle("smoke", d, d2, d3, 0.0, 0.0, 0.0);
                super.worldObj.spawnParticle("flame", d, d2, d3, 0.0, 0.0, 0.0);
                if (this.delay > 0) {
                    --this.delay;
                }
                this.prevYaw = this.yaw;
                this.yaw = (this.yaw + 1000.0f / (this.delay + 200.0f)) % 360.0;
            }
            else {
                if (this.delay == -1) {
                    this.updateDelay();
                }
                if (this.delay > 0) {
                    --this.delay;
                    return;
                }
                boolean needsDelayUpdate = false;
                for (int i = 0; i < this.maxSpawnCount; ++i) {
                    final Entity entity = EntityList.createEntityByName(this.entityClassName, super.worldObj);
                    if (entity == null) {
                        return;
                    }
                    final List nearbyEntitiesList = super.worldObj.getEntitiesWithinAABB((Class)entity.getClass(), AxisAlignedBB.getBoundingBox((double)super.xCoord, (double)super.yCoord, (double)super.zCoord, (double)(super.xCoord + 1), (double)(super.yCoord + 1), (double)(super.zCoord + 1)).expand((double)this.nearbyMobCheckRange, (double)this.nearbyMobCheckRange, (double)this.nearbyMobCheckRange));
                    final List nearbySameEntitiesList = new ArrayList();
                    for (int l = 0; l < nearbyEntitiesList.size(); ++l) {
                        final Entity nearbyEntity = nearbyEntitiesList.get(l);
                        if (nearbyEntity.getClass() == entity.getClass()) {
                            nearbySameEntitiesList.add(nearbyEntity);
                        }
                    }
                    int nearbyEntities = nearbySameEntitiesList.size();
                    if (nearbyEntities >= this.nearbyMobLimit) {
                        this.updateDelay();
                        return;
                    }
                    if (entity != null) {
                        final double d4 = super.xCoord + (super.worldObj.rand.nextDouble() - super.worldObj.rand.nextDouble()) * this.maxSpawnRange;
                        final double d5 = super.yCoord + (super.worldObj.rand.nextDouble() - super.worldObj.rand.nextDouble()) * this.maxSpawnRangeVertical;
                        final double d6 = super.zCoord + (super.worldObj.rand.nextDouble() - super.worldObj.rand.nextDouble()) * this.maxSpawnRange;
                        final EntityLiving entityliving = (entity instanceof EntityLiving) ? entity : null;
                        entity.setLocationAndAngles(d4, d5, d6, super.worldObj.rand.nextFloat() * 360.0f, 0.0f);
                        if (entityliving instanceof LOTREntityNPC) {
                            ((LOTREntityNPC)entityliving).isNPCPersistent = this.spawnsPersistentNPCs;
                            ((LOTREntityNPC)entityliving).liftSpawnRestrictions = true;
                        }
                        if (entity instanceof LOTRMobSpawnerCondition) {
                            ((LOTRMobSpawnerCondition)entity).setSpawningFromMobSpawner(true);
                        }
                        if (entityliving == null || entityliving.getCanSpawnHere()) {
                            if (entityliving instanceof LOTREntityNPC) {
                                ((LOTREntityNPC)entityliving).liftSpawnRestrictions = false;
                            }
                            if (entity instanceof LOTRMobSpawnerCondition) {
                                ((LOTRMobSpawnerCondition)entity).setSpawningFromMobSpawner(false);
                            }
                            this.writeNBTTagsTo(entity);
                            if (entity instanceof LOTREntityNPC) {
                                ((LOTREntityNPC)entity).onArtificalSpawn();
                            }
                            super.worldObj.spawnEntityInWorld(entity);
                            super.worldObj.playAuxSFX(2004, super.xCoord, super.yCoord, super.zCoord, 0);
                            if (entityliving != null) {
                                entityliving.spawnExplosionParticle();
                            }
                            needsDelayUpdate = true;
                            if (++nearbyEntities >= this.nearbyMobLimit) {
                                break;
                            }
                        }
                    }
                }
                if (needsDelayUpdate) {
                    this.updateDelay();
                }
            }
            super.updateEntity();
        }
    }
    
    public void writeNBTTagsTo(final Entity entity) {
        if (entity instanceof EntityLiving && entity.worldObj != null) {
            final EntityLiving entityliving = (EntityLiving)entity;
            if (!super.worldObj.isClient) {
                if (entityliving.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.maxHealth) != null) {
                    entityliving.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue((double)this.maxHealth);
                }
                if (entityliving.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.followRange) != null) {
                    entityliving.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue((double)this.navigatorRange);
                }
                if (entityliving.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed) != null) {
                    entityliving.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue((double)this.moveSpeed);
                }
                if (entityliving.getAttributeMap().getAttributeInstance(LOTREntityNPC.npcAttackDamage) != null) {
                    entityliving.getEntityAttribute(LOTREntityNPC.npcAttackDamage).setBaseValue((double)this.attackDamage);
                }
            }
            entityliving.onSpawnWithEgg((IEntityLivingData)null);
            if (this.customSpawnData != null) {
                entityliving.readFromNBT(this.customSpawnData);
            }
        }
    }
    
    private void updateDelay() {
        if (this.maxSpawnDelay <= this.minSpawnDelay) {
            this.delay = this.minSpawnDelay;
        }
        else {
            this.delay = this.minSpawnDelay + super.worldObj.rand.nextInt(this.maxSpawnDelay - this.minSpawnDelay);
        }
        super.worldObj.func_147452_c(super.xCoord, super.yCoord, super.zCoord, this.getBlockType(), 1, 0);
    }
    
    public void readFromNBT(final NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        if (nbt.func_150297_b("EntityID", 3)) {
            final int id = nbt.getInteger("EntityID");
            this.entityClassName = LOTREntities.getStringFromID(id);
        }
        else {
            this.entityClassName = nbt.getString("EntityID");
        }
        this.delay = nbt.getShort("Delay");
        if (nbt.hasKey("MinSpawnDelay")) {
            this.minSpawnDelay = nbt.getShort("MinSpawnDelay");
            this.maxSpawnDelay = nbt.getShort("MaxSpawnDelay");
            this.maxSpawnCount = nbt.getShort("MaxSpawnCount");
        }
        if (nbt.hasKey("NearbyMobLimit")) {
            this.nearbyMobLimit = nbt.getShort("NearbyMobLimit");
            this.requiredPlayerRange = nbt.getShort("RequiredPlayerRange");
        }
        if (nbt.hasKey("MaxSpawnRange")) {
            this.maxSpawnRange = nbt.getShort("MaxSpawnRange");
        }
        if (nbt.hasKey("MaxSpawnRangeVertical")) {
            this.maxSpawnRangeVertical = nbt.getShort("MaxSpawnRangeVertical");
        }
        if (nbt.hasKey("SpawnsPersistentNPCs")) {
            this.spawnsPersistentNPCs = nbt.getBoolean("SpawnsPersistentNPCs");
            this.active = nbt.getByte("ActiveMode");
            this.nearbyMobCheckRange = nbt.getShort("MobCheckRange");
        }
        if (nbt.hasKey("MaxHealth")) {
            this.maxHealth = nbt.getShort("MaxHealth");
            this.navigatorRange = nbt.getShort("NavigatorRange");
            this.moveSpeed = nbt.getFloat("MoveSpeed");
            this.attackDamage = nbt.getFloat("AttackDamage");
        }
        if (nbt.hasKey("CustomSpawnData")) {
            this.customSpawnData = nbt.getCompoundTag("CustomSpawnData");
        }
        if (super.worldObj != null && super.worldObj.isClient) {
            this.spawnedMob = null;
        }
    }
    
    public void writeToNBT(final NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setString("EntityID", this.getEntityClassName());
        nbt.setShort("Delay", (short)this.delay);
        nbt.setShort("MinSpawnDelay", (short)this.minSpawnDelay);
        nbt.setShort("MaxSpawnDelay", (short)this.maxSpawnDelay);
        nbt.setShort("MaxSpawnCount", (short)this.maxSpawnCount);
        nbt.setShort("NearbyMobLimit", (short)this.nearbyMobLimit);
        nbt.setShort("RequiredPlayerRange", (short)this.requiredPlayerRange);
        nbt.setShort("MaxSpawnRange", (short)this.maxSpawnRange);
        nbt.setShort("MaxSpawnRangeVertical", (short)this.maxSpawnRangeVertical);
        nbt.setBoolean("SpawnsPersistentNPCs", this.spawnsPersistentNPCs);
        nbt.setByte("ActiveMode", (byte)this.active);
        nbt.setShort("MobCheckRange", (short)this.nearbyMobCheckRange);
        nbt.setShort("MaxHealth", (short)this.maxHealth);
        nbt.setShort("NavigatorRange", (short)this.navigatorRange);
        nbt.setFloat("MoveSpeed", this.moveSpeed);
        nbt.setFloat("AttackDamage", this.attackDamage);
        if (this.customSpawnData != null) {
            nbt.setTag("CustomSpawnData", (NBTBase)this.customSpawnData);
        }
    }
    
    public Entity getMobEntity(final World world) {
        if (this.spawnedMob == null) {
            final Entity entity = EntityList.createEntityByName(this.entityClassName, world);
            if (entity instanceof LOTREntityNPC) {
                ((LOTREntityNPC)entity).onArtificalSpawn();
            }
            this.writeNBTTagsTo(entity);
            this.spawnedMob = entity;
        }
        return this.spawnedMob;
    }
    
    public Packet getDescriptionPacket() {
        final NBTTagCompound data = new NBTTagCompound();
        this.writeToNBT(data);
        return (Packet)new S35PacketUpdateTileEntity(super.xCoord, super.yCoord, super.zCoord, 0, data);
    }
    
    public void onDataPacket(final NetworkManager manager, final S35PacketUpdateTileEntity packet) {
        final NBTTagCompound data = packet.func_148857_g();
        this.readFromNBT(data);
    }
    
    public boolean receiveClientEvent(final int i, final int j) {
        if (i == 1 && super.worldObj.isClient) {
            this.delay = this.minSpawnDelay;
            return true;
        }
        if (i == 2 && super.worldObj.isClient) {
            this.delay = -1;
            return true;
        }
        return false;
    }
}
