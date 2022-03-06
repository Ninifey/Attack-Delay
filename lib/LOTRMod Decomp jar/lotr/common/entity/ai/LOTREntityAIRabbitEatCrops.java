// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import java.util.Random;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import lotr.common.LOTRMod;
import net.minecraft.world.World;
import lotr.common.entity.animal.LOTREntityRabbit;
import net.minecraft.entity.ai.EntityAIBase;

public class LOTREntityAIRabbitEatCrops extends EntityAIBase
{
    private LOTREntityRabbit theRabbit;
    private double xPos;
    private double yPos;
    private double zPos;
    private double moveSpeed;
    private World theWorld;
    private int pathingTick;
    private static final int maxPathingTick = 200;
    private int eatingTick;
    private static final int maxEatingTick = 60;
    private int rePathDelay;
    
    public LOTREntityAIRabbitEatCrops(final LOTREntityRabbit rabbit, final double d) {
        this.theRabbit = rabbit;
        this.moveSpeed = d;
        this.theWorld = ((Entity)rabbit).worldObj;
        this.setMutexBits(1);
    }
    
    public boolean shouldExecute() {
        if (!LOTRMod.canGrief(this.theWorld)) {
            return false;
        }
        if (this.theRabbit.getRNG().nextInt(20) == 0) {
            final Vec3 vec3 = this.findCropsLocation();
            if (vec3 != null) {
                this.xPos = vec3.xCoord;
                this.yPos = vec3.yCoord;
                this.zPos = vec3.zCoord;
                return true;
            }
        }
        return false;
    }
    
    public boolean continueExecuting() {
        if (!LOTRMod.canGrief(this.theWorld)) {
            return false;
        }
        if (this.pathingTick < 200 && this.eatingTick < 60) {
            final int i = MathHelper.floor_double(this.xPos);
            final int j = MathHelper.floor_double(this.yPos);
            final int k = MathHelper.floor_double(this.zPos);
            return this.canEatBlock(i, j, k);
        }
        return false;
    }
    
    public void resetTask() {
        this.pathingTick = 0;
        this.eatingTick = 0;
        this.rePathDelay = 0;
        this.theRabbit.setRabbitEating(false);
    }
    
    public void updateTask() {
        if (this.theRabbit.getDistanceSq(this.xPos, this.yPos, this.zPos) > 1.0) {
            this.theRabbit.setRabbitEating(false);
            this.theRabbit.getLookHelper().setLookPosition(this.xPos, this.yPos - 0.5, this.zPos, 10.0f, (float)this.theRabbit.getVerticalFaceSpeed());
            --this.rePathDelay;
            if (this.rePathDelay <= 0) {
                this.rePathDelay = 10;
                this.theRabbit.getNavigator().tryMoveToXYZ(this.xPos, this.yPos, this.zPos, this.moveSpeed);
            }
            ++this.pathingTick;
        }
        else {
            this.theRabbit.setRabbitEating(true);
            ++this.eatingTick;
            if (this.eatingTick % 6 == 0) {
                this.theRabbit.playSound("random.eat", 1.0f, (this.theWorld.rand.nextFloat() - this.theWorld.rand.nextFloat()) * 0.2f + 1.0f);
            }
            if (this.eatingTick >= 60) {
                this.theWorld.setBlockToAir(MathHelper.floor_double(this.xPos), MathHelper.floor_double(this.yPos), MathHelper.floor_double(this.zPos));
            }
        }
    }
    
    private Vec3 findCropsLocation() {
        final Random random = this.theRabbit.getRNG();
        for (int l = 0; l < 32; ++l) {
            final int i = MathHelper.floor_double(((Entity)this.theRabbit).posX) + MathHelper.getRandomIntegerInRange(random, -16, 16);
            final int j = MathHelper.floor_double(((Entity)this.theRabbit).boundingBox.minY) + MathHelper.getRandomIntegerInRange(random, -8, 8);
            final int k = MathHelper.floor_double(((Entity)this.theRabbit).posZ) + MathHelper.getRandomIntegerInRange(random, -16, 16);
            if (this.canEatBlock(i, j, k)) {
                return Vec3.createVectorHelper(i + 0.5, (double)j, k + 0.5);
            }
        }
        return null;
    }
    
    private boolean canEatBlock(final int i, final int j, final int k) {
        final Block block = this.theWorld.getBlock(i, j, k);
        return block instanceof BlockCrops && !this.theRabbit.anyFarmhandsNearby(i, j, k);
    }
}
