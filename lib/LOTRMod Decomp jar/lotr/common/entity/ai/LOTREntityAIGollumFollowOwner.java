// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.ai;

import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.entity.npc.LOTREntityGollum;
import net.minecraft.entity.ai.EntityAIBase;

public class LOTREntityAIGollumFollowOwner extends EntityAIBase
{
    private LOTREntityGollum theGollum;
    private EntityPlayer theOwner;
    private double moveSpeed;
    private PathNavigate theGollumPathfinder;
    private int followTick;
    private float maxDist;
    private float minDist;
    private boolean avoidsWater;
    private World theWorld;
    
    public LOTREntityAIGollumFollowOwner(final LOTREntityGollum entity, final double d, final float f, final float f1) {
        this.theGollum = entity;
        this.moveSpeed = d;
        this.theGollumPathfinder = entity.getNavigator();
        this.minDist = f;
        this.maxDist = f1;
        this.theWorld = ((Entity)entity).worldObj;
        this.setMutexBits(3);
    }
    
    public boolean shouldExecute() {
        final EntityPlayer entityplayer = this.theGollum.getGollumOwner();
        if (entityplayer == null) {
            return false;
        }
        if (this.theGollum.isGollumSitting()) {
            return false;
        }
        if (this.theGollum.getDistanceSqToEntity((Entity)entityplayer) < this.minDist * this.minDist) {
            return false;
        }
        this.theOwner = entityplayer;
        return true;
    }
    
    public boolean continueExecuting() {
        return this.theGollum.getGollumOwner() != null && !this.theGollumPathfinder.noPath() && this.theGollum.getDistanceSqToEntity((Entity)this.theOwner) > this.maxDist * this.maxDist && !this.theGollum.isGollumSitting();
    }
    
    public void startExecuting() {
        this.followTick = 0;
        this.avoidsWater = this.theGollum.getNavigator().getAvoidsWater();
        this.theGollum.getNavigator().setAvoidsWater(false);
    }
    
    public void resetTask() {
        this.theOwner = null;
        this.theGollumPathfinder.clearPathEntity();
        this.theGollum.getNavigator().setAvoidsWater(this.avoidsWater);
    }
    
    public void updateTask() {
        this.theGollum.getLookHelper().setLookPositionWithEntity((Entity)this.theOwner, 10.0f, (float)this.theGollum.getVerticalFaceSpeed());
        if (!this.theGollum.isGollumSitting() && --this.followTick <= 0) {
            this.followTick = 10;
            if (!this.theGollumPathfinder.tryMoveToEntityLiving((Entity)this.theOwner, this.moveSpeed) && this.theGollum.getDistanceSqToEntity((Entity)this.theOwner) >= 256.0) {
                final int i = MathHelper.floor_double(((Entity)this.theOwner).posX);
                final int j = MathHelper.floor_double(((Entity)this.theOwner).boundingBox.minY);
                final int k = MathHelper.floor_double(((Entity)this.theOwner).posZ);
                final float f = ((Entity)this.theGollum).width / 2.0f;
                final float f2 = ((Entity)this.theGollum).height;
                final AxisAlignedBB theGollumBoundingBox = AxisAlignedBB.getBoundingBox(((Entity)this.theOwner).posX - f, ((Entity)this.theOwner).posY - ((Entity)this.theGollum).yOffset + ((Entity)this.theGollum).ySize, ((Entity)this.theOwner).posZ - f, ((Entity)this.theOwner).posX + f, ((Entity)this.theOwner).posY - ((Entity)this.theGollum).yOffset + ((Entity)this.theGollum).ySize + f2, ((Entity)this.theOwner).posZ + f);
                if (this.theWorld.func_147461_a(theGollumBoundingBox).isEmpty() && this.theWorld.getBlock(i, j - 1, k).isSideSolid((IBlockAccess)this.theWorld, i, j - 1, k, ForgeDirection.UP)) {
                    this.theGollum.setLocationAndAngles(((Entity)this.theOwner).posX, ((Entity)this.theOwner).boundingBox.minY, ((Entity)this.theOwner).posZ, ((Entity)this.theGollum).rotationYaw, ((Entity)this.theGollum).rotationPitch);
                    ((Entity)this.theGollum).fallDistance = 0.0f;
                    this.theGollum.getNavigator().clearPathEntity();
                }
            }
        }
    }
}
