// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.fx;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.client.particle.EntityFX;

public class LOTREntityDeadMarshFace extends EntityFX
{
    public float faceAlpha;
    
    public LOTREntityDeadMarshFace(final World world, final double d, final double d1, final double d2) {
        super(world, d, d1, d2, 0.0, 0.0, 0.0);
        super.particleMaxAge = 120 + ((Entity)this).rand.nextInt(120);
        ((Entity)this).rotationYaw = world.rand.nextFloat() * 360.0f;
        ((Entity)this).rotationPitch = -60.0f + world.rand.nextFloat() * 120.0f;
    }
    
    public void onUpdate() {
        ((Entity)this).prevPosX = ((Entity)this).posX;
        ((Entity)this).prevPosY = ((Entity)this).posY;
        ((Entity)this).prevPosZ = ((Entity)this).posZ;
        ++super.particleAge;
        this.faceAlpha = MathHelper.sin(super.particleAge / (float)super.particleMaxAge * 3.1415927f);
        if (super.particleAge > super.particleMaxAge) {
            this.setDead();
        }
    }
}
