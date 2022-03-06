// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.animal;

import net.minecraft.entity.EntityLivingBase;
import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntities;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.DamageSource;
import java.util.List;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.util.MathHelper;
import lotr.common.world.biome.LOTRBiomeGenMidgewater;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.entity.EntityLiving;

public class LOTREntityMidges extends EntityLiving implements LOTRAmbientCreature
{
    private ChunkCoordinates currentFlightTarget;
    private EntityPlayer playerTarget;
    public Midge[] midges;
    
    public LOTREntityMidges(final World world) {
        super(world);
        this.setSize(2.0f, 2.0f);
        ((Entity)this).renderDistanceWeight = 0.5;
        this.midges = new Midge[3 + ((Entity)this).rand.nextInt(6)];
        for (int l = 0; l < this.midges.length; ++l) {
            this.midges[l] = new Midge();
        }
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(2.0);
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
        ((Entity)this).motionY *= 0.6;
        for (int l = 0; l < this.midges.length; ++l) {
            this.midges[l].update();
        }
        if (((Entity)this).rand.nextInt(5) == 0) {
            this.playSound("lotr:midges.swarm", this.getSoundVolume(), this.getSoundPitch());
        }
        if (!((Entity)this).worldObj.isClient && this.isEntityAlive()) {
            final boolean inMidgewater = ((Entity)this).worldObj.getBiomeGenForCoords(MathHelper.floor_double(((Entity)this).posX), MathHelper.floor_double(((Entity)this).posZ)) instanceof LOTRBiomeGenMidgewater;
            final int chance = inMidgewater ? 100 : 500;
            if (((Entity)this).rand.nextInt(chance) == 0) {
                final double range = inMidgewater ? 16.0 : 24.0;
                final int threshold = inMidgewater ? 6 : 5;
                final List list = ((Entity)this).worldObj.getEntitiesWithinAABB((Class)LOTREntityMidges.class, ((Entity)this).boundingBox.expand(range, range, range));
                if (list.size() < threshold) {
                    final LOTREntityMidges moreMidges = new LOTREntityMidges(((Entity)this).worldObj);
                    moreMidges.setLocationAndAngles(((Entity)this).posX, ((Entity)this).posY, ((Entity)this).posZ, ((Entity)this).rand.nextFloat() * 360.0f, 0.0f);
                    moreMidges.onSpawnWithEgg((IEntityLivingData)null);
                    ((Entity)this).worldObj.spawnEntityInWorld((Entity)moreMidges);
                }
            }
        }
    }
    
    protected void updateAITasks() {
        super.updateAITasks();
        if (this.currentFlightTarget != null && !((Entity)this).worldObj.isAirBlock(this.currentFlightTarget.posX, this.currentFlightTarget.posY, this.currentFlightTarget.posZ)) {
            this.currentFlightTarget = null;
        }
        if (this.playerTarget != null && (!this.playerTarget.isEntityAlive() || this.getDistanceSqToEntity((Entity)this.playerTarget) > 256.0)) {
            this.playerTarget = null;
        }
        if (this.playerTarget != null) {
            if (((Entity)this).rand.nextInt(400) == 0) {
                this.playerTarget = null;
            }
            else {
                this.currentFlightTarget = new ChunkCoordinates((int)((Entity)this.playerTarget).posX, (int)((Entity)this.playerTarget).posY + 3, (int)((Entity)this.playerTarget).posZ);
            }
        }
        else if (((Entity)this).rand.nextInt(100) == 0) {
            final EntityPlayer closestPlayer = ((Entity)this).worldObj.getClosestPlayerToEntity((Entity)this, 12.0);
            if (closestPlayer != null && ((Entity)this).rand.nextInt(7) == 0) {
                this.playerTarget = closestPlayer;
            }
            else {
                final int i = (int)((Entity)this).posX + ((Entity)this).rand.nextInt(7) - ((Entity)this).rand.nextInt(7);
                int j = (int)((Entity)this).posY + ((Entity)this).rand.nextInt(4) - ((Entity)this).rand.nextInt(3);
                final int k = (int)((Entity)this).posZ + ((Entity)this).rand.nextInt(7) - ((Entity)this).rand.nextInt(7);
                if (j < 1) {
                    j = 1;
                }
                final int height = ((Entity)this).worldObj.getTopSolidOrLiquidBlock(i, k);
                if (j > height + 8) {
                    j = height + 8;
                }
                this.currentFlightTarget = new ChunkCoordinates(i, j, k);
            }
        }
        if (this.currentFlightTarget != null) {
            final double dx = this.currentFlightTarget.posX + 0.5 - ((Entity)this).posX;
            final double dy = this.currentFlightTarget.posY + 0.5 - ((Entity)this).posY;
            final double dz = this.currentFlightTarget.posZ + 0.5 - ((Entity)this).posZ;
            ((Entity)this).motionX += (Math.signum(dx) * 0.5 - ((Entity)this).motionX) * 0.1;
            ((Entity)this).motionY += (Math.signum(dy) * 0.7 - ((Entity)this).motionY) * 0.1;
            ((Entity)this).motionZ += (Math.signum(dz) * 0.5 - ((Entity)this).motionZ) * 0.1;
            ((EntityLivingBase)this).moveForward = 0.2f;
        }
        else {
            final double motionX = 0.0;
            ((Entity)this).motionZ = motionX;
            ((Entity)this).motionY = motionX;
            ((Entity)this).motionX = motionX;
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
    
    public void onDeath(final DamageSource damagesource) {
        super.onDeath(damagesource);
        if (!((Entity)this).worldObj.isClient && damagesource instanceof EntityDamageSourceIndirect) {
            final Entity attacker = damagesource.getEntity();
            if (attacker instanceof LOTREntityNPC) {
                final LOTREntityNPC npc = (LOTREntityNPC)attacker;
                if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.getHiringPlayer() != null) {
                    final EntityPlayer entityplayer = npc.hiredNPCInfo.getHiringPlayer();
                    LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.shootDownMidges);
                }
            }
        }
    }
    
    protected boolean canDespawn() {
        return true;
    }
    
    public boolean getCanSpawnHere() {
        final int i = MathHelper.floor_double(((Entity)this).posX);
        final int j = MathHelper.floor_double(((Entity)this).posY);
        final int k = MathHelper.floor_double(((Entity)this).posZ);
        return j >= 62 && ((Entity)this).worldObj.getBlock(i, j - 1, k) == ((Entity)this).worldObj.getBiomeGenForCoords(i, k).topBlock && super.getCanSpawnHere();
    }
    
    public boolean allowLeashing() {
        return false;
    }
    
    protected boolean interact(final EntityPlayer entityplayer) {
        return false;
    }
    
    public ItemStack getPickedResult(final MovingObjectPosition target) {
        final int id = LOTREntities.getEntityID((Entity)this);
        if (id > 0 && LOTREntities.spawnEggs.containsKey(id)) {
            return new ItemStack(LOTRMod.spawnEgg, 1, id);
        }
        return null;
    }
    
    public class Midge
    {
        public float midge_posX;
        public float midge_posY;
        public float midge_posZ;
        public float midge_prevPosX;
        public float midge_prevPosY;
        public float midge_prevPosZ;
        private float midge_initialPosX;
        private float midge_initialPosY;
        private float midge_initialPosZ;
        public float midge_rotation;
        private int midgeTick;
        private int maxMidgeTick;
        
        public Midge() {
            this.maxMidgeTick = 80;
            final float n = -1.0f + ((Entity)LOTREntityMidges.this).rand.nextFloat() * 2.0f;
            this.midge_posX = n;
            this.midge_initialPosX = n;
            final float n2 = ((Entity)LOTREntityMidges.this).rand.nextFloat() * 2.0f;
            this.midge_posY = n2;
            this.midge_initialPosY = n2;
            final float n3 = -1.0f + ((Entity)LOTREntityMidges.this).rand.nextFloat() * 2.0f;
            this.midge_posZ = n3;
            this.midge_initialPosZ = n3;
            this.midge_rotation = ((Entity)LOTREntityMidges.this).rand.nextFloat() * 360.0f;
            this.midgeTick = ((Entity)LOTREntityMidges.this).rand.nextInt(this.maxMidgeTick);
        }
        
        public void update() {
            this.midge_prevPosX = this.midge_posX;
            this.midge_prevPosY = this.midge_posY;
            this.midge_prevPosZ = this.midge_posZ;
            ++this.midgeTick;
            if (this.midgeTick > this.maxMidgeTick) {
                this.midgeTick = 0;
            }
            this.midge_posY = this.midge_initialPosY + 0.5f * MathHelper.sin(this.midgeTick / 6.2831855f);
        }
    }
}
