// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.animal;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import lotr.common.entity.LOTREntities;
import lotr.common.LOTRMod;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemFishFood;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.SharedMonsterAttributes;
import java.util.UUID;
import net.minecraft.world.World;
import net.minecraft.util.ChunkCoordinates;
import lotr.common.entity.LOTRRandomSkinEntity;
import net.minecraft.entity.passive.EntityWaterMob;

public class LOTREntityFish extends EntityWaterMob implements LOTRRandomSkinEntity
{
    private ChunkCoordinates currentSwimTarget;
    private int swimTargetTime;
    private static final int swimTargetTimeMax = 200;
    
    public LOTREntityFish(final World world) {
        super(world);
        this.swimTargetTime = 0;
        this.setSize(0.5f, 0.5f);
    }
    
    public void setUniqueID(final UUID uuid) {
        ((Entity)this).entityUniqueID = uuid;
    }
    
    public void entityInit() {
        super.entityInit();
        ((Entity)this).dataWatcher.addObject(16, (Object)0);
    }
    
    public FishType getFishType() {
        int i = ((Entity)this).dataWatcher.getWatchableObjectByte(16);
        if (i < 0 || i >= FishType.values().length) {
            i = 0;
        }
        return FishType.values()[i];
    }
    
    public void setFishType(final FishType type) {
        this.setFishType(type.ordinal());
    }
    
    public void setFishType(final int i) {
        ((Entity)this).dataWatcher.updateObject(16, (Object)(byte)i);
    }
    
    public String getFishTextureDir() {
        return this.getFishType().textureDir;
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(MathHelper.getRandomDoubleInRange(((Entity)this).rand, 0.04, 0.08));
    }
    
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        final int i = MathHelper.floor_double(((Entity)this).posX);
        final int j = MathHelper.floor_double(((Entity)this).posY);
        final int k = MathHelper.floor_double(((Entity)this).posZ);
        final BiomeGenBase biome = ((Entity)this).worldObj.getBiomeGenForCoords(i, k);
        if (((Entity)this).rand.nextInt(30) == 0) {
            this.setFishType(FishType.CLOWNFISH);
        }
        else if (((Entity)this).rand.nextInt(8) == 0) {
            this.setFishType(FishType.SALMON);
        }
        else {
            this.setFishType(FishType.COMMON);
        }
        return data;
    }
    
    protected boolean canTriggerWalking() {
        return false;
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.setFishType(nbt.getInteger("FishType"));
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setInteger("FishType", this.getFishType().ordinal());
    }
    
    protected void dropFewItems(final boolean flag, final int i) {
        for (int drops = ((Entity)this).rand.nextInt(2 + i), l = 0; l < drops; ++l) {
            if (this.getFishType() == FishType.SALMON) {
                this.entityDropItem(new ItemStack(Items.fish, 1, ItemFishFood.FishType.SALMON.func_150976_a()), 0.0f);
            }
            else if (this.getFishType() == FishType.CLOWNFISH) {
                this.entityDropItem(new ItemStack(Items.fish, 1, ItemFishFood.FishType.CLOWNFISH.func_150976_a()), 0.0f);
            }
            else {
                this.entityDropItem(new ItemStack(Items.fish, 1, ItemFishFood.FishType.COD.func_150976_a()), 0.0f);
            }
        }
    }
    
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!this.isInWater()) {
            if (!((Entity)this).worldObj.isClient) {
                ((Entity)this).motionX = 0.0;
                ((Entity)this).motionY -= 0.08;
                ((Entity)this).motionY *= 0.98;
                ((Entity)this).motionZ = 0.0;
            }
        }
    }
    
    public boolean isInWater() {
        final double d = 0.5;
        return ((Entity)this).worldObj.isMaterialInBB(((Entity)this).boundingBox.expand(d, d, d), Material.water);
    }
    
    protected void updateEntityActionState() {
        ++((EntityLivingBase)this).entityAge;
        if (this.currentSwimTarget != null && !this.isValidSwimTarget(this.currentSwimTarget.posX, this.currentSwimTarget.posY, this.currentSwimTarget.posZ)) {
            this.currentSwimTarget = null;
            this.swimTargetTime = 0;
        }
        if (this.currentSwimTarget == null || ((Entity)this).rand.nextInt(200) == 0 || this.getDistanceSqToSwimTarget() < 4.0) {
            for (int l = 0; l < 16; ++l) {
                int i = MathHelper.floor_double(((Entity)this).posX);
                int j = MathHelper.floor_double(((Entity)this).posY);
                int k = MathHelper.floor_double(((Entity)this).posZ);
                i += ((Entity)this).rand.nextInt(16) - ((Entity)this).rand.nextInt(16);
                k += ((Entity)this).rand.nextInt(16) - ((Entity)this).rand.nextInt(16);
                j += MathHelper.getRandomIntegerInRange(((Entity)this).rand, -2, 4);
                if (this.isValidSwimTarget(i, j, k)) {
                    this.currentSwimTarget = new ChunkCoordinates(i, j, k);
                    this.swimTargetTime = 0;
                    break;
                }
            }
        }
        if (this.currentSwimTarget != null) {
            final double speed = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
            final double d0 = this.currentSwimTarget.posX + 0.5 - ((Entity)this).posX;
            final double d2 = this.currentSwimTarget.posY + 0.5 - ((Entity)this).posY;
            final double d3 = this.currentSwimTarget.posZ + 0.5 - ((Entity)this).posZ;
            ((Entity)this).motionX += (Math.signum(d0) * 0.5 - ((Entity)this).motionX) * speed;
            ((Entity)this).motionY += (Math.signum(d2) * 0.5 - ((Entity)this).motionY) * speed;
            ((Entity)this).motionZ += (Math.signum(d3) * 0.5 - ((Entity)this).motionZ) * speed;
            final float f = (float)(Math.atan2(((Entity)this).motionZ, ((Entity)this).motionX) * 180.0 / 3.141592653589793) - 90.0f;
            final float f2 = MathHelper.wrapAngleTo180_float(f - ((Entity)this).rotationYaw);
            ((EntityLivingBase)this).moveForward = 0.5f;
            ((Entity)this).rotationYaw += f2;
            ++this.swimTargetTime;
            if (this.swimTargetTime >= 200) {
                this.currentSwimTarget = null;
                this.swimTargetTime = 0;
            }
        }
        this.despawnEntity();
    }
    
    private boolean isValidSwimTarget(final int i, final int j, final int k) {
        return ((Entity)this).worldObj.getBlock(i, j, k).getMaterial() == Material.water;
    }
    
    private double getDistanceSqToSwimTarget() {
        final double d = this.currentSwimTarget.posX + 0.5;
        final double d2 = this.currentSwimTarget.posY + 0.5;
        final double d3 = this.currentSwimTarget.posZ + 0.5;
        return this.getDistanceSq(d, d2, d3);
    }
    
    public ItemStack getPickedResult(final MovingObjectPosition target) {
        return new ItemStack(LOTRMod.spawnEgg, 1, LOTREntities.getEntityID((Entity)this));
    }
    
    public enum FishType
    {
        COMMON("common"), 
        SALMON("salmon"), 
        CLOWNFISH("clownfish");
        
        public final String textureDir;
        
        private FishType(final String s) {
            this.textureDir = s;
        }
    }
}
