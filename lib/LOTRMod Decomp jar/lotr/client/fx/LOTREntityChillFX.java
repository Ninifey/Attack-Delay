// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.fx;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.client.particle.EntitySmokeFX;

public class LOTREntityChillFX extends EntitySmokeFX
{
    public LOTREntityChillFX(final World world, final double d, final double d1, final double d2, final double d3, final double d4, final double d5) {
        super(world, d, d1, d2, d3, d4, d5);
        final float randomFloatClamp;
        final float grey = randomFloatClamp = MathHelper.randomFloatClamp(((Entity)this).rand, 0.8f, 1.0f);
        ((EntityFX)this).particleBlue = randomFloatClamp;
        ((EntityFX)this).particleGreen = randomFloatClamp;
        ((EntityFX)this).particleRed = randomFloatClamp;
        this.setParticleTextureIndex(((Entity)this).rand.nextInt(8));
        ((EntityFX)this).particleMaxAge *= 6;
        final float blue = ((Entity)this).rand.nextFloat() * 0.25f;
        ((EntityFX)this).particleRed *= 1.0f - blue;
        ((EntityFX)this).particleGreen *= 1.0f - blue;
    }
    
    public void onUpdate() {
        ((Entity)this).prevPosX = ((Entity)this).posX;
        ((Entity)this).prevPosY = ((Entity)this).posY;
        ((Entity)this).prevPosZ = ((Entity)this).posZ;
        ++((EntityFX)this).particleAge;
        if (((EntityFX)this).particleAge >= ((EntityFX)this).particleMaxAge) {
            this.setDead();
        }
        this.moveEntity(((Entity)this).motionX, ((Entity)this).motionY, ((Entity)this).motionZ);
        ((Entity)this).motionX *= 0.96;
        ((Entity)this).motionY *= 0.96;
        ((Entity)this).motionZ *= 0.96;
        ((Entity)this).motionY -= 0.005;
        if (((Entity)this).onGround) {
            ((Entity)this).motionX *= 0.7;
            ((Entity)this).motionZ *= 0.7;
        }
    }
}
