// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.item;

import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.EntityList;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.Item;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import lotr.common.entity.npc.LOTRTraderNPCInfo;
import net.minecraft.util.ChunkCoordinates;
import lotr.common.entity.npc.LOTRTradeable;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.nbt.NBTBase;
import net.minecraft.world.World;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.entity.Entity;

public class LOTREntityTraderRespawn extends Entity
{
    private static int MAX_SCALE;
    private int timeUntilSpawn;
    private int prevBobbingTime;
    private int bobbingTime;
    private String traderClassID;
    private boolean traderHasHome;
    private int traderHomeX;
    private int traderHomeY;
    private int traderHomeZ;
    private float traderHomeRadius;
    private String traderLocationName;
    private NBTTagCompound traderData;
    public float spawnerSpin;
    public float prevSpawnerSpin;
    
    public LOTREntityTraderRespawn(final World world) {
        super(world);
        this.setSize(0.75f, 0.75f);
        this.spawnerSpin = super.rand.nextFloat() * 360.0f;
    }
    
    protected void entityInit() {
        super.dataWatcher.addObject(16, (Object)0);
        super.dataWatcher.addObject(17, (Object)0);
        super.dataWatcher.addObject(18, (Object)"");
    }
    
    public int getScale() {
        return super.dataWatcher.getWatchableObjectInt(16);
    }
    
    public void setScale(final int i) {
        super.dataWatcher.updateObject(16, (Object)i);
    }
    
    public boolean isSpawnImminent() {
        return super.dataWatcher.getWatchableObjectByte(17) == 1;
    }
    
    public void setSpawnImminent() {
        super.dataWatcher.updateObject(17, (Object)1);
    }
    
    public String getClientTraderString() {
        return super.dataWatcher.getWatchableObjectString(18);
    }
    
    public void setClientTraderString(final String s) {
        super.dataWatcher.updateObject(18, (Object)s);
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbt) {
        nbt.setInteger("Scale", this.getScale());
        nbt.setInteger("TimeUntilSpawn", this.timeUntilSpawn);
        nbt.setString("TraderClassID", this.traderClassID);
        nbt.setBoolean("TraderHasHome", this.traderHasHome);
        nbt.setInteger("TraderHomeX", this.traderHomeX);
        nbt.setInteger("TraderHomeY", this.traderHomeY);
        nbt.setInteger("TraderHomeZ", this.traderHomeZ);
        nbt.setFloat("TraderHomeRadius", this.traderHomeRadius);
        if (this.traderLocationName != null) {
            nbt.setString("TraderLocationName", this.traderLocationName);
        }
        if (this.traderData != null) {
            nbt.setTag("TraderData", (NBTBase)this.traderData);
        }
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        this.setScale(nbt.getInteger("Scale"));
        this.timeUntilSpawn = nbt.getInteger("TimeUntilSpawn");
        if (this.timeUntilSpawn <= 1200) {
            this.setSpawnImminent();
        }
        this.traderClassID = nbt.getString("TraderClassID");
        this.traderHasHome = nbt.getBoolean("TraderHasHome");
        this.traderHomeX = nbt.getInteger("TraderHomeX");
        this.traderHomeY = nbt.getInteger("TraderHomeY");
        this.traderHomeZ = nbt.getInteger("TraderHomeZ");
        this.traderHomeRadius = nbt.getFloat("TraderHomeRadius");
        if (nbt.hasKey("TraderLocationName")) {
            this.traderLocationName = nbt.getString("TraderLocationName");
        }
        if (nbt.hasKey("TraderData")) {
            this.traderData = nbt.getCompoundTag("TraderData");
        }
    }
    
    public void copyTraderDataFrom(final LOTREntityNPC entity) {
        this.traderClassID = LOTREntities.getStringFromClass(entity.getClass());
        this.traderHasHome = entity.hasHome();
        if (this.traderHasHome) {
            final ChunkCoordinates home = entity.getHomePosition();
            this.traderHomeX = home.posX;
            this.traderHomeY = home.posY;
            this.traderHomeZ = home.posZ;
            this.traderHomeRadius = entity.func_110174_bM();
        }
        if (entity.getHasSpecificLocationName()) {
            this.traderLocationName = entity.npcLocationName;
        }
        if (entity instanceof LOTRTradeable) {
            final LOTRTraderNPCInfo traderInfo = entity.traderNPCInfo;
            traderInfo.writeToNBT(this.traderData = new NBTTagCompound());
        }
    }
    
    public void onSpawn() {
        super.motionY = 0.25;
        this.timeUntilSpawn = MathHelper.getRandomIntegerInRange(super.rand, 10, 30) * 1200;
    }
    
    public boolean canBeCollidedWith() {
        return true;
    }
    
    public void applyEntityCollision(final Entity entity) {
    }
    
    public boolean hitByEntity(final Entity entity) {
        return entity instanceof EntityPlayer && this.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)entity), 0.0f);
    }
    
    public boolean attackEntityFrom(final DamageSource damagesource, final float f) {
        final Entity entity = damagesource.getEntity();
        if (entity instanceof EntityPlayer && ((EntityPlayer)entity).capabilities.isCreativeMode) {
            if (!super.worldObj.isClient) {
                final Block.SoundType sound = Blocks.glass.stepSound;
                super.worldObj.playSoundAtEntity((Entity)this, sound.getDigResourcePath(), (sound.getVolume() + 1.0f) / 2.0f, sound.getFrequency() * 0.8f);
                super.worldObj.setEntityState((Entity)this, (byte)16);
                this.setDead();
            }
            return true;
        }
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public void handleHealthUpdate(final byte b) {
        if (b == 16) {
            for (int l = 0; l < 16; ++l) {
                super.worldObj.spawnParticle("iconcrack_" + Item.getIdFromItem(LOTRMod.silverCoin), super.posX + (super.rand.nextDouble() - 0.5) * super.width, super.posY + super.rand.nextDouble() * super.height, super.posZ + (super.rand.nextDouble() - 0.5) * super.width, 0.0, 0.0, 0.0);
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
        if (this.isSpawnImminent()) {
            this.spawnerSpin += 24.0f;
        }
        else {
            this.spawnerSpin += 6.0f;
        }
        this.prevSpawnerSpin = MathHelper.wrapAngleTo180_float(this.prevSpawnerSpin);
        this.spawnerSpin = MathHelper.wrapAngleTo180_float(this.spawnerSpin);
        if (this.getScale() < LOTREntityTraderRespawn.MAX_SCALE) {
            if (!super.worldObj.isClient) {
                this.setScale(this.getScale() + 1);
            }
            super.motionX = 0.0;
            super.motionY *= 0.9;
            super.motionZ = 0.0;
        }
        else {
            super.motionX = 0.0;
            super.motionY = 0.0;
            super.motionZ = 0.0;
        }
        this.moveEntity(super.motionX, super.motionY, super.motionZ);
        if (!super.worldObj.isClient) {
            this.setClientTraderString(this.traderClassID);
            if (!this.isSpawnImminent() && this.timeUntilSpawn <= 1200) {
                this.setSpawnImminent();
            }
            if (this.timeUntilSpawn > 0) {
                --this.timeUntilSpawn;
            }
            else {
                boolean flag = false;
                final Entity entity = EntityList.createEntityByName(this.traderClassID, super.worldObj);
                if (entity != null && entity instanceof LOTREntityNPC) {
                    final LOTREntityNPC trader = (LOTREntityNPC)entity;
                    trader.setLocationAndAngles(super.posX, super.posY, super.posZ, super.rand.nextFloat() * 360.0f, 0.0f);
                    trader.spawnRidingHorse = false;
                    trader.liftSpawnRestrictions = true;
                    super.boundingBox.offset(0.0, 100.0, 0.0);
                    if (trader.getCanSpawnHere()) {
                        trader.liftSpawnRestrictions = false;
                        trader.onSpawnWithEgg(null);
                        if (this.traderHasHome) {
                            trader.setHomeArea(this.traderHomeX, this.traderHomeY, this.traderHomeZ, Math.round(this.traderHomeRadius));
                        }
                        if (this.traderLocationName != null) {
                            trader.setSpecificLocationName(this.traderLocationName);
                        }
                        flag = super.worldObj.spawnEntityInWorld((Entity)trader);
                        if (trader instanceof LOTRTradeable && this.traderData != null) {
                            trader.traderNPCInfo.readFromNBT(this.traderData);
                        }
                    }
                    super.boundingBox.offset(0.0, -100.0, 0.0);
                }
                if (flag) {
                    this.playSound("random.pop", 1.0f, 0.5f + super.rand.nextFloat() * 0.5f);
                    this.setDead();
                }
                else {
                    this.timeUntilSpawn = 60;
                    this.setLocationAndAngles(super.posX, super.posY + 1.0, super.posZ, super.rotationYaw, super.rotationPitch);
                }
            }
        }
        else if (this.isSpawnImminent()) {
            this.prevBobbingTime = this.bobbingTime;
            ++this.bobbingTime;
        }
    }
    
    public float getScaleFloat(final float tick) {
        float scale = (float)this.getScale();
        if (scale < LOTREntityTraderRespawn.MAX_SCALE) {
            scale += tick;
        }
        return scale / LOTREntityTraderRespawn.MAX_SCALE;
    }
    
    public float getBobbingOffset(final float tick) {
        float f = (float)(this.bobbingTime - this.prevBobbingTime);
        f *= tick;
        return MathHelper.sin((this.prevBobbingTime + f) / 5.0f) * 0.25f;
    }
    
    public ItemStack getPickedResult(final MovingObjectPosition target) {
        final int entityID = LOTREntities.getIDFromString(this.getClientTraderString());
        if (entityID > 0) {
            return new ItemStack(LOTRMod.spawnEgg, 1, entityID);
        }
        return null;
    }
    
    static {
        LOTREntityTraderRespawn.MAX_SCALE = 40;
    }
}
