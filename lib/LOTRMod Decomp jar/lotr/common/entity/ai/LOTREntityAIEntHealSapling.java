// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.ai;

import net.minecraft.entity.Entity;
import java.util.Iterator;
import lotr.common.tileentity.LOTRTileEntityCorruptMallorn;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.block.Block;
import lotr.common.LOTRMod;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import lotr.common.entity.npc.LOTREntityEnt;
import net.minecraft.entity.ai.EntityAIBase;

public class LOTREntityAIEntHealSapling extends EntityAIBase
{
    private LOTREntityEnt theEnt;
    private World theWorld;
    private double moveSpeed;
    private double xPos;
    private double yPos;
    private double zPos;
    private int healingTick;
    private static int HEAL_TIME;
    private int pathingTick;
    private int rePathDelay;
    
    public LOTREntityAIEntHealSapling(final LOTREntityEnt ent, final double d) {
        this.theEnt = ent;
        this.moveSpeed = d;
        this.theWorld = ((Entity)ent).worldObj;
        this.setMutexBits(3);
    }
    
    public boolean shouldExecute() {
        if (this.theEnt.canHealSapling) {
            final Vec3 vec3 = this.findSapling();
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
        if (!this.theEnt.canHealSapling) {
            return false;
        }
        if (this.pathingTick < 300 && this.healingTick < LOTREntityAIEntHealSapling.HEAL_TIME) {
            final Block block = this.theWorld.getBlock(MathHelper.floor_double(this.xPos), MathHelper.floor_double(this.yPos), MathHelper.floor_double(this.zPos));
            return block == LOTRMod.corruptMallorn;
        }
        return false;
    }
    
    public void resetTask() {
        this.pathingTick = 0;
        this.healingTick = 0;
        this.rePathDelay = 0;
        this.theEnt.setHealingSapling(false);
    }
    
    public void updateTask() {
        if (this.theEnt.getDistanceSq(this.xPos, this.yPos, this.zPos) > 9.0) {
            this.theEnt.setHealingSapling(false);
            --this.rePathDelay;
            if (this.rePathDelay <= 0) {
                this.rePathDelay = 10;
                this.theEnt.getNavigator().tryMoveToXYZ(this.xPos, this.yPos, this.zPos, this.moveSpeed);
            }
            ++this.pathingTick;
        }
        else {
            this.theEnt.getNavigator().clearPathEntity();
            this.theEnt.getLookHelper().setLookPosition(this.xPos, this.yPos + 0.5, this.zPos, 10.0f, (float)this.theEnt.getVerticalFaceSpeed());
            this.theEnt.setHealingSapling(true);
            this.theEnt.saplingHealTarget = new ChunkCoordinates(MathHelper.floor_double(this.xPos), MathHelper.floor_double(this.yPos), MathHelper.floor_double(this.zPos));
            ++this.healingTick;
            if (this.healingTick >= LOTREntityAIEntHealSapling.HEAL_TIME) {
                this.theWorld.setBlock(MathHelper.floor_double(this.xPos), MathHelper.floor_double(this.yPos), MathHelper.floor_double(this.zPos), LOTRMod.sapling, 1, 3);
                this.theEnt.setHealingSapling(false);
            }
        }
    }
    
    private Vec3 findSapling() {
        double leastDistSq = 576.0;
        LOTRTileEntityCorruptMallorn mallorn = null;
        for (final Object obj : this.theWorld.field_147482_g) {
            if (obj instanceof LOTRTileEntityCorruptMallorn) {
                final LOTRTileEntityCorruptMallorn te = (LOTRTileEntityCorruptMallorn)obj;
                final double distSq = this.theEnt.getDistanceSq(te.xCoord + 0.5, (double)te.yCoord, te.zCoord + 0.5);
                if (distSq >= leastDistSq) {
                    continue;
                }
                mallorn = te;
                leastDistSq = distSq;
            }
        }
        if (mallorn != null) {
            return Vec3.createVectorHelper(mallorn.xCoord + 0.5, (double)mallorn.yCoord, mallorn.zCoord + 0.5);
        }
        return null;
    }
    
    static {
        LOTREntityAIEntHealSapling.HEAL_TIME = 160;
    }
}
