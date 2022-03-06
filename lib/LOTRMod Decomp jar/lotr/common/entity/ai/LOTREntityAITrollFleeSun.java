// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.ai;

import net.minecraft.entity.Entity;
import java.util.Random;
import net.minecraft.util.Vec3;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.RandomPositionGenerator;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import lotr.common.entity.npc.LOTREntityTroll;
import net.minecraft.entity.ai.EntityAIBase;

public class LOTREntityAITrollFleeSun extends EntityAIBase
{
    private LOTREntityTroll theTroll;
    private double xPosition;
    private double yPosition;
    private double zPosition;
    private double moveSpeed;
    private World theWorld;
    
    public LOTREntityAITrollFleeSun(final LOTREntityTroll troll, final double d) {
        this.theTroll = troll;
        this.moveSpeed = d;
        this.theWorld = ((Entity)troll).worldObj;
        this.setMutexBits(1);
    }
    
    public boolean shouldExecute() {
        if (!this.theWorld.isDaytime()) {
            return false;
        }
        if (!this.theWorld.canBlockSeeTheSky(MathHelper.floor_double(((Entity)this.theTroll).posX), (int)((Entity)this.theTroll).boundingBox.minY, MathHelper.floor_double(((Entity)this.theTroll).posZ))) {
            return false;
        }
        if (this.theTroll.trollImmuneToSun) {
            return false;
        }
        final BiomeGenBase biome = this.theWorld.getBiomeGenForCoords(MathHelper.floor_double(((Entity)this.theTroll).posX), MathHelper.floor_double(((Entity)this.theTroll).posZ));
        if (biome instanceof LOTRBiome && ((LOTRBiome)biome).canSpawnHostilesInDay()) {
            return false;
        }
        if (this.theTroll.getTrollBurnTime() == -1) {
            this.theTroll.setTrollBurnTime(300);
        }
        Vec3 vec3 = this.findPossibleShelter();
        if (vec3 == null) {
            vec3 = RandomPositionGenerator.findRandomTarget((EntityCreature)this.theTroll, 12, 6);
            if (vec3 == null) {
                return false;
            }
        }
        this.xPosition = vec3.xCoord;
        this.yPosition = vec3.yCoord;
        this.zPosition = vec3.zCoord;
        return true;
    }
    
    public boolean continueExecuting() {
        return !this.theTroll.getNavigator().noPath();
    }
    
    public void startExecuting() {
        this.theTroll.getNavigator().tryMoveToXYZ(this.xPosition, this.yPosition, this.zPosition, this.moveSpeed);
    }
    
    private Vec3 findPossibleShelter() {
        final Random random = this.theTroll.getRNG();
        for (int l = 0; l < 32; ++l) {
            final int i = MathHelper.floor_double(((Entity)this.theTroll).posX) - 24 + random.nextInt(49);
            final int j = MathHelper.floor_double(((Entity)this.theTroll).boundingBox.minY) - 12 + random.nextInt(25);
            final int k = MathHelper.floor_double(((Entity)this.theTroll).posZ) - 24 + random.nextInt(49);
            if (!this.theWorld.canBlockSeeTheSky(i, j, k) && this.theTroll.getBlockPathWeight(i, j, k) < 0.0f) {
                return Vec3.createVectorHelper((double)i, (double)j, (double)k);
            }
        }
        return null;
    }
}
