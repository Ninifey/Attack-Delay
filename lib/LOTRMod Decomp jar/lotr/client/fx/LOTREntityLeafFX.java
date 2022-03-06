// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.fx;

import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;
import net.minecraft.client.particle.EntityFX;

public class LOTREntityLeafFX extends EntityFX
{
    private int leafAge;
    
    public LOTREntityLeafFX(final World world, final double d, final double d1, final double d2, final double d3, final double d4, final double d5, final int i) {
        super(world, d, d1, d2, d3, d4, d5);
        this.leafAge = 600;
        ((Entity)this).motionX = d3;
        ((Entity)this).motionY = d4;
        ((Entity)this).motionZ = d5;
        super.particleScale = 0.15f + ((Entity)this).rand.nextFloat() * 0.5f;
        this.setParticleTextureIndex(i);
    }
    
    public LOTREntityLeafFX(final World world, final double d, final double d1, final double d2, final double d3, final double d4, final double d5, final int i, final int j) {
        this(world, d, d1, d2, d3, d4, d5, i);
        this.leafAge = j;
    }
    
    public void onUpdate() {
        ((Entity)this).prevPosX = ((Entity)this).posX;
        ((Entity)this).prevPosY = ((Entity)this).posY;
        ((Entity)this).prevPosZ = ((Entity)this).posZ;
        if (this.leafAge > 0) {
            --this.leafAge;
        }
        this.moveEntity(((Entity)this).motionX, ((Entity)this).motionY, ((Entity)this).motionZ);
        if (((Entity)this).onGround || this.leafAge == 0 || ((Entity)this).posY < 0.0) {
            this.setDead();
        }
    }
    
    public void setParticleTextureIndex(final int i) {
        super.particleTextureIndexX = i % 8;
        super.particleTextureIndexY = i / 8;
    }
    
    public void renderParticle(final Tessellator tessellator, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        final float f6 = super.particleTextureIndexX / 8.0f;
        final float f7 = f6 + 0.125f;
        final float f8 = super.particleTextureIndexY / 8.0f;
        final float f9 = f8 + 0.125f;
        final float f10 = 0.2f * super.particleScale;
        final float f11 = (float)(((Entity)this).prevPosX + (((Entity)this).posX - ((Entity)this).prevPosX) * f - EntityFX.interpPosX);
        final float f12 = (float)(((Entity)this).prevPosY + (((Entity)this).posY - ((Entity)this).prevPosY) * f - EntityFX.interpPosY);
        final float f13 = (float)(((Entity)this).prevPosZ + (((Entity)this).posZ - ((Entity)this).prevPosZ) * f - EntityFX.interpPosZ);
        tessellator.setColorRGBA_F(super.particleRed, super.particleGreen, super.particleBlue, super.particleAlpha);
        tessellator.addVertexWithUV((double)(f11 - f1 * f10 - f4 * f10), (double)(f12 - f2 * f10), (double)(f13 - f3 * f10 - f5 * f10), (double)f7, (double)f9);
        tessellator.addVertexWithUV((double)(f11 - f1 * f10 + f4 * f10), (double)(f12 + f2 * f10), (double)(f13 - f3 * f10 + f5 * f10), (double)f7, (double)f8);
        tessellator.addVertexWithUV((double)(f11 + f1 * f10 + f4 * f10), (double)(f12 + f2 * f10), (double)(f13 + f3 * f10 + f5 * f10), (double)f6, (double)f8);
        tessellator.addVertexWithUV((double)(f11 + f1 * f10 - f4 * f10), (double)(f12 - f2 * f10), (double)(f13 + f3 * f10 - f5 * f10), (double)f6, (double)f9);
    }
}
