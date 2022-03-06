// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.entity.npc.LOTREntityNPCRideable;
import net.minecraft.entity.ai.EntityAIBase;

public class LOTREntityAIUntamedPanic extends EntityAIBase
{
    private LOTREntityNPCRideable theMount;
    private double speed;
    private double targetX;
    private double targetY;
    private double targetZ;
    
    public LOTREntityAIUntamedPanic(final LOTREntityNPCRideable mount, final double d) {
        this.theMount = mount;
        this.speed = d;
        this.setMutexBits(1);
    }
    
    public boolean shouldExecute() {
        if (this.theMount.isNPCTamed() || !(((Entity)this.theMount).riddenByEntity instanceof EntityPlayer)) {
            return false;
        }
        final Vec3 vec3 = RandomPositionGenerator.findRandomTarget((EntityCreature)this.theMount, 5, 4);
        if (vec3 == null) {
            return false;
        }
        this.targetX = vec3.xCoord;
        this.targetY = vec3.yCoord;
        this.targetZ = vec3.zCoord;
        return true;
    }
    
    public void startExecuting() {
        this.theMount.getNavigator().tryMoveToXYZ(this.targetX, this.targetY, this.targetZ, this.speed);
    }
    
    public boolean continueExecuting() {
        return !this.theMount.getNavigator().noPath() && ((Entity)this.theMount).riddenByEntity instanceof EntityPlayer && !this.theMount.isNPCTamed();
    }
    
    public void updateTask() {
        if (this.theMount.getRNG().nextInt(50) == 0) {
            if (((Entity)this.theMount).riddenByEntity instanceof EntityPlayer) {
                final int i = this.theMount.getNPCTemper();
                final int j = this.theMount.getMaxNPCTemper();
                if (j > 0 && this.theMount.getRNG().nextInt(j) < i) {
                    this.theMount.tameNPC((EntityPlayer)((Entity)this.theMount).riddenByEntity);
                    this.theMount.spawnHearts();
                    return;
                }
                this.theMount.increaseNPCTemper(5);
            }
            ((Entity)this.theMount).riddenByEntity.mountEntity((Entity)null);
            ((Entity)this.theMount).riddenByEntity = null;
            this.theMount.angerNPC();
            this.theMount.spawnSmokes();
        }
    }
}
