// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.ai;

import net.minecraft.entity.Entity;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.world.World;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;

public class LOTREntityAIBurningPanic extends EntityAIBase
{
    private EntityCreature theEntity;
    private World theWorld;
    private double speed;
    private double randPosX;
    private double randPosY;
    private double randPosZ;
    private boolean avoidsWater;
    
    public LOTREntityAIBurningPanic(final EntityCreature entity, final double d) {
        this.theEntity = entity;
        this.theWorld = ((Entity)this.theEntity).worldObj;
        this.speed = d;
        this.setMutexBits(1);
    }
    
    public boolean shouldExecute() {
        if (this.theEntity.isBurning() && this.theEntity.getAttackTarget() == null) {
            Vec3 target = this.findWaterLocation();
            if (target == null) {
                target = RandomPositionGenerator.findRandomTarget(this.theEntity, 5, 4);
            }
            if (target != null) {
                this.randPosX = target.xCoord;
                this.randPosY = target.yCoord;
                this.randPosZ = target.zCoord;
                return true;
            }
        }
        return false;
    }
    
    private Vec3 findWaterLocation() {
        final Random random = this.theEntity.getRNG();
        for (int l = 0; l < 32; ++l) {
            final int i = MathHelper.floor_double(((Entity)this.theEntity).posX) + MathHelper.getRandomIntegerInRange(random, -8, 8);
            final int j = MathHelper.floor_double(((Entity)this.theEntity).boundingBox.minY) + MathHelper.getRandomIntegerInRange(random, -8, 8);
            final int k = MathHelper.floor_double(((Entity)this.theEntity).posZ) + MathHelper.getRandomIntegerInRange(random, -8, 8);
            if (!this.theWorld.getBlock(i, j + 1, k).isNormalCube() && !this.theWorld.getBlock(i, j, k).isNormalCube() && this.theWorld.getBlock(i, j - 1, k).getMaterial() == Material.water) {
                return Vec3.createVectorHelper(i + 0.5, j + 0.5, k + 0.5);
            }
        }
        return null;
    }
    
    public void startExecuting() {
        this.avoidsWater = this.theEntity.getNavigator().getAvoidsWater();
        this.theEntity.getNavigator().setAvoidsWater(false);
        this.theEntity.getNavigator().tryMoveToXYZ(this.randPosX, this.randPosY, this.randPosZ, this.speed);
    }
    
    public boolean continueExecuting() {
        return this.theEntity.isBurning() && this.theEntity.getAttackTarget() == null && !this.theEntity.getNavigator().noPath();
    }
    
    public void resetTask() {
        this.theEntity.getNavigator().setAvoidsWater(this.avoidsWater);
    }
}
