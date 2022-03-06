// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.fx;

import net.minecraft.entity.Entity;
import net.minecraft.block.Block;
import lotr.common.LOTRMod;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.client.particle.EntityFlameFX;

public class LOTREntityUtumnoKillFX extends EntityFlameFX
{
    private double paramMotionX;
    private double paramMotionY;
    private double paramMotionZ;
    
    public LOTREntityUtumnoKillFX(final World world, final double d, final double d1, final double d2, final double d3, final double d4, final double d5) {
        super(world, d, d1, d2, d3, d4, d5);
        ((Entity)this).motionX = d3;
        this.paramMotionX = d3;
        ((Entity)this).motionY = d4;
        this.paramMotionY = d4;
        ((Entity)this).motionZ = d5;
        this.paramMotionZ = d5;
        this.setParticleTextureIndex(144 + ((Entity)this).rand.nextInt(3));
    }
    
    public void onUpdate() {
        super.onUpdate();
        ((Entity)this).motionX = this.paramMotionX;
        ((Entity)this).motionY = this.paramMotionY;
        ((Entity)this).motionZ = this.paramMotionZ;
        final Block block = ((Entity)this).worldObj.getBlock(MathHelper.floor_double(((Entity)this).posX), MathHelper.floor_double(((Entity)this).posY), MathHelper.floor_double(((Entity)this).posZ));
        if (block == LOTRMod.utumnoReturnPortalBase) {
            this.setDead();
        }
    }
}
