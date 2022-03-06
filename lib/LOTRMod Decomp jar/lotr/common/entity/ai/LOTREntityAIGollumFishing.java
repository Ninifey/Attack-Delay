// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.ai;

import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTRSpeech;
import net.minecraft.entity.Entity;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import lotr.common.entity.npc.LOTREntityGollum;
import net.minecraft.entity.ai.EntityAIBase;

public class LOTREntityAIGollumFishing extends EntityAIBase
{
    private LOTREntityGollum theGollum;
    private double moveSpeed;
    private boolean avoidsWater;
    private World theWorld;
    private double xPosition;
    private double yPosition;
    private double zPosition;
    private int moveTick;
    private int fishTick;
    private boolean finished;
    
    public LOTREntityAIGollumFishing(final LOTREntityGollum entity, final double d) {
        this.theGollum = entity;
        this.moveSpeed = d;
        this.theWorld = ((Entity)entity).worldObj;
        this.setMutexBits(3);
    }
    
    public boolean shouldExecute() {
        if (this.theGollum.getGollumOwner() == null) {
            return false;
        }
        if (this.theGollum.isGollumSitting()) {
            return false;
        }
        if (this.theGollum.prevFishTime > 0) {
            return false;
        }
        if (this.theGollum.isFishing) {
            return false;
        }
        if (this.theGollum.getEquipmentInSlot(0) != null) {
            return false;
        }
        if (this.theGollum.getRNG().nextInt(60) != 0) {
            return false;
        }
        final Vec3 vec3 = this.findPossibleFishingLocation();
        if (vec3 == null) {
            return false;
        }
        this.xPosition = vec3.xCoord;
        this.yPosition = vec3.yCoord;
        this.zPosition = vec3.zCoord;
        return true;
    }
    
    private Vec3 findPossibleFishingLocation() {
        final Random random = this.theGollum.getRNG();
        for (int l = 0; l < 32; ++l) {
            final int i = MathHelper.floor_double(((Entity)this.theGollum).posX) - 16 + random.nextInt(33);
            final int j = MathHelper.floor_double(((Entity)this.theGollum).boundingBox.minY) - 8 + random.nextInt(17);
            final int k = MathHelper.floor_double(((Entity)this.theGollum).posZ) - 16 + random.nextInt(33);
            if (!this.theWorld.getBlock(i, j + 1, k).isNormalCube() && !this.theWorld.getBlock(i, j, k).isNormalCube() && this.theWorld.getBlock(i, j - 1, k).getMaterial() == Material.water) {
                return Vec3.createVectorHelper(i + 0.5, j + 0.5, k + 0.5);
            }
        }
        return null;
    }
    
    public boolean continueExecuting() {
        return this.theGollum.getGollumOwner() != null && !this.theGollum.isGollumSitting() && this.moveTick < 300 && !this.finished;
    }
    
    public void startExecuting() {
        this.avoidsWater = this.theGollum.getNavigator().getAvoidsWater();
        this.theGollum.getNavigator().setAvoidsWater(false);
        this.theGollum.isFishing = true;
    }
    
    public void resetTask() {
        this.theGollum.getNavigator().clearPathEntity();
        this.theGollum.getNavigator().setAvoidsWater(this.avoidsWater);
        this.moveTick = 0;
        this.fishTick = 0;
        if (this.finished) {
            this.finished = false;
            this.theGollum.prevFishTime = 3000;
        }
        else {
            this.theGollum.prevFishTime = 600;
        }
        this.theGollum.isFishing = false;
    }
    
    public void updateTask() {
        if (this.atFishingLocation()) {
            if (this.theGollum.isInWater()) {
                this.theWorld.setEntityState((Entity)this.theGollum, (byte)15);
                if (this.theGollum.getRNG().nextInt(4) == 0) {
                    this.theWorld.playSoundAtEntity((Entity)this.theGollum, this.theGollum.getSplashSound(), 1.0f, 1.0f + (this.theGollum.getRNG().nextFloat() - this.theGollum.getRNG().nextFloat()) * 0.4f);
                }
                this.theGollum.getJumpHelper().setJumping();
                if (this.theGollum.getRNG().nextInt(50) == 0) {
                    LOTRSpeech.sendSpeech(this.theGollum.getGollumOwner(), this.theGollum, LOTRSpeech.getRandomSpeechForPlayer(this.theGollum, "char/gollum/fishing", this.theGollum.getGollumOwner()));
                }
            }
            ++this.fishTick;
            if (this.fishTick > 100) {
                this.theGollum.setCurrentItemOrArmor(0, new ItemStack(Items.fish, 4 + this.theGollum.getRNG().nextInt(9)));
                this.finished = true;
                LOTRSpeech.sendSpeech(this.theGollum.getGollumOwner(), this.theGollum, LOTRSpeech.getRandomSpeechForPlayer(this.theGollum, "char/gollum/catchFish", this.theGollum.getGollumOwner()));
            }
        }
        else {
            this.theGollum.getNavigator().tryMoveToXYZ(this.xPosition, this.yPosition, this.zPosition, this.moveSpeed);
            ++this.moveTick;
        }
    }
    
    private boolean atFishingLocation() {
        if (this.theGollum.getDistanceSq(this.xPosition, this.yPosition, this.zPosition) < 4.0) {
            final int i = MathHelper.floor_double(((Entity)this.theGollum).posX);
            final int j = MathHelper.floor_double(((Entity)this.theGollum).boundingBox.minY);
            final int k = MathHelper.floor_double(((Entity)this.theGollum).posZ);
            return this.theWorld.getBlock(i, j, k).getMaterial() == Material.water || this.theWorld.getBlock(i, j - 1, k).getMaterial() == Material.water;
        }
        return false;
    }
}
