// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.animal;

import net.minecraft.entity.EntityLivingBase;
import lotr.common.entity.LOTREntities;
import lotr.common.LOTRMod;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.block.material.Material;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import lotr.common.world.structure2.LOTRWorldGenElfHouse;
import java.util.Random;
import net.minecraft.entity.Entity;
import java.util.UUID;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.world.biome.LOTRBiomeGenFarHaradJungle;
import lotr.common.world.biome.LOTRBiomeGenLothlorien;
import lotr.common.world.biome.LOTRBiomeGenWoodlandRealm;
import lotr.common.world.biome.LOTRBiomeGenMirkwood;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;
import net.minecraft.util.ChunkCoordinates;
import lotr.common.block.LOTRBlockTorch;
import lotr.common.entity.LOTRRandomSkinEntity;
import net.minecraft.entity.EntityLiving;

public class LOTREntityButterfly extends EntityLiving implements LOTRAmbientCreature, LOTRRandomSkinEntity
{
    private LOTRBlockTorch elfTorchBlock;
    private ChunkCoordinates currentFlightTarget;
    public int flapTime;
    
    public LOTREntityButterfly(final World world) {
        super(world);
        this.flapTime = 0;
        this.setSize(0.5f, 0.5f);
    }
    
    public void entityInit() {
        super.entityInit();
        ((Entity)this).dataWatcher.addObject(16, (Object)0);
        ((Entity)this).dataWatcher.addObject(17, (Object)0);
    }
    
    public ButterflyType getButterflyType() {
        int i = ((Entity)this).dataWatcher.getWatchableObjectByte(16);
        if (i < 0 || i >= ButterflyType.values().length) {
            i = 0;
        }
        return ButterflyType.values()[i];
    }
    
    public void setButterflyType(final ButterflyType type) {
        this.setButterflyType(type.ordinal());
    }
    
    public void setButterflyType(final int i) {
        ((Entity)this).dataWatcher.updateObject(16, (Object)(byte)i);
    }
    
    public boolean isButterflyStill() {
        return ((Entity)this).dataWatcher.getWatchableObjectByte(17) == 1;
    }
    
    public void setButterflyStill(final boolean flag) {
        ((Entity)this).dataWatcher.updateObject(17, (Object)(byte)(flag ? 1 : 0));
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(2.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(MathHelper.getRandomDoubleInRange(((Entity)this).rand, 0.08, 0.12));
    }
    
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        final int i = MathHelper.floor_double(((Entity)this).posX);
        final int j = MathHelper.floor_double(((Entity)this).posY);
        final int k = MathHelper.floor_double(((Entity)this).posZ);
        final BiomeGenBase biome = ((Entity)this).worldObj.getBiomeGenForCoords(i, k);
        if (biome instanceof LOTRBiomeGenMirkwood || biome instanceof LOTRBiomeGenWoodlandRealm) {
            this.setButterflyType(ButterflyType.MIRKWOOD);
        }
        else if (biome instanceof LOTRBiomeGenLothlorien) {
            this.setButterflyType(ButterflyType.LORIEN);
        }
        else if (biome instanceof LOTRBiomeGenFarHaradJungle) {
            this.setButterflyType(ButterflyType.JUNGLE);
        }
        else {
            this.setButterflyType(ButterflyType.COMMON);
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
    
    public void onUpdate() {
        super.onUpdate();
        if (this.isButterflyStill()) {
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
            if (this.getButterflyType() == ButterflyType.LORIEN) {
                final double d = ((Entity)this).posX;
                final double d2 = ((Entity)this).posY;
                final double d3 = ((Entity)this).posZ;
                final double d4 = ((Entity)this).motionX * -0.2;
                final double d5 = ((Entity)this).motionY * -0.2;
                final double d6 = ((Entity)this).motionZ * -0.2;
                if (this.elfTorchBlock == null) {
                    final Random torchRand = new Random();
                    torchRand.setSeed(((Entity)this).entityUniqueID.getLeastSignificantBits());
                    this.elfTorchBlock = (LOTRBlockTorch)LOTRWorldGenElfHouse.getRandomTorch(torchRand);
                }
                final LOTRBlockTorch.TorchParticle particle = this.elfTorchBlock.createTorchParticle(((Entity)this).rand);
                if (particle != null) {
                    particle.spawn(d, d2, d3);
                }
            }
        }
    }
    
    protected void updateAITasks() {
        super.updateAITasks();
        if (this.isButterflyStill()) {
            final int i = MathHelper.floor_double(((Entity)this).posX);
            final int j = (int)((Entity)this).posY - 1;
            final int k = MathHelper.floor_double(((Entity)this).posZ);
            if (!((Entity)this).worldObj.getBlock(i, j, k).isSideSolid((IBlockAccess)((Entity)this).worldObj, i, j, k, ForgeDirection.UP)) {
                this.setButterflyStill(false);
            }
            else if (((Entity)this).rand.nextInt(400) == 0 || ((Entity)this).worldObj.getClosestPlayerToEntity((Entity)this, 3.0) != null) {
                this.setButterflyStill(false);
            }
        }
        else {
            if (this.currentFlightTarget != null && (!((Entity)this).worldObj.isAirBlock(this.currentFlightTarget.posX, this.currentFlightTarget.posY, this.currentFlightTarget.posZ) || this.currentFlightTarget.posY < 1)) {
                this.currentFlightTarget = null;
            }
            if (this.currentFlightTarget == null || ((Entity)this).rand.nextInt(30) == 0 || this.currentFlightTarget.getDistanceSquared((int)((Entity)this).posX, (int)((Entity)this).posY, (int)((Entity)this).posZ) < 4.0f) {
                this.currentFlightTarget = new ChunkCoordinates((int)((Entity)this).posX + ((Entity)this).rand.nextInt(7) - ((Entity)this).rand.nextInt(7), (int)((Entity)this).posY + ((Entity)this).rand.nextInt(6) - 2, (int)((Entity)this).posZ + ((Entity)this).rand.nextInt(7) - ((Entity)this).rand.nextInt(7));
            }
            final double speed = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
            final double d0 = this.currentFlightTarget.posX + 0.5 - ((Entity)this).posX;
            final double d2 = this.currentFlightTarget.posY + 0.5 - ((Entity)this).posY;
            final double d3 = this.currentFlightTarget.posZ + 0.5 - ((Entity)this).posZ;
            ((Entity)this).motionX += (Math.signum(d0) * 0.5 - ((Entity)this).motionX) * speed;
            ((Entity)this).motionY += (Math.signum(d2) * 0.7 - ((Entity)this).motionY) * speed;
            ((Entity)this).motionZ += (Math.signum(d3) * 0.5 - ((Entity)this).motionZ) * speed;
            final float f = (float)(Math.atan2(((Entity)this).motionZ, ((Entity)this).motionX) * 180.0 / 3.141592653589793) - 90.0f;
            final float f2 = MathHelper.wrapAngleTo180_float(f - ((Entity)this).rotationYaw);
            ((EntityLivingBase)this).moveForward = 0.5f;
            ((Entity)this).rotationYaw += f2;
            if (((Entity)this).rand.nextInt(150) == 0 && ((Entity)this).worldObj.getBlock(MathHelper.floor_double(((Entity)this).posX), (int)((Entity)this).posY - 1, MathHelper.floor_double(((Entity)this).posZ)).isNormalCube()) {
                this.setButterflyStill(true);
            }
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
        if (flag && !((Entity)this).worldObj.isClient && this.isButterflyStill()) {
            this.setButterflyStill(false);
        }
        return flag;
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.setButterflyType(nbt.getInteger("ButterflyType"));
        this.setButterflyStill(nbt.getBoolean("ButterflyStill"));
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setInteger("ButterflyType", this.getButterflyType().ordinal());
        nbt.setBoolean("ButterflyStill", this.isButterflyStill());
    }
    
    protected boolean canDespawn() {
        return true;
    }
    
    public boolean getCanSpawnHere() {
        return super.getCanSpawnHere() && LOTRAmbientSpawnChecks.canSpawn(this, 8, 4, 32, 4, Material.plants, Material.vine);
    }
    
    public boolean allowLeashing() {
        return false;
    }
    
    public ItemStack getPickedResult(final MovingObjectPosition target) {
        return new ItemStack(LOTRMod.spawnEgg, 1, LOTREntities.getEntityID((Entity)this));
    }
    
    public enum ButterflyType
    {
        MIRKWOOD("mirkwood"), 
        LORIEN("lorien"), 
        COMMON("common"), 
        JUNGLE("jungle");
        
        public final String textureDir;
        
        private ButterflyType(final String s) {
            this.textureDir = s;
        }
    }
}
