// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.fx;

import net.minecraft.entity.Entity;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.util.MathHelper;
import net.minecraft.client.renderer.Tessellator;
import java.awt.Color;
import net.minecraft.world.World;
import net.minecraft.client.particle.EntityFlameFX;

public class LOTREntityElvenGlowFX extends EntityFlameFX
{
    public LOTREntityElvenGlowFX(final World world, final double d, final double d1, final double d2, final double d3, final double d4, final double d5) {
        super(world, d, d1, d2, d3, d4, d5);
        ((EntityFX)this).particleRed = 0.15f;
        ((EntityFX)this).particleGreen = 0.9f;
        ((EntityFX)this).particleBlue = 1.0f;
        ((EntityFX)this).particleMaxAge *= 3;
    }
    
    public LOTREntityElvenGlowFX setElvenGlowColor(final int color) {
        final float[] rgb = new Color(color).getColorComponents(null);
        ((EntityFX)this).particleRed = rgb[0];
        ((EntityFX)this).particleGreen = rgb[1];
        ((EntityFX)this).particleBlue = rgb[2];
        return this;
    }
    
    public void renderParticle(final Tessellator tessellator, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        final float minU = 0.25f;
        final float maxU = minU + 0.25f;
        final float minV = 0.125f;
        final float maxV = minV + 0.25f;
        final float var12 = 0.25f + 0.002f * MathHelper.sin((((EntityFX)this).particleAge + f - 1.0f) * 0.25f * 3.1415927f);
        ((EntityFX)this).particleAlpha = 0.9f - (((EntityFX)this).particleAge + f - 1.0f) * 0.02f;
        final float var13 = (float)(((Entity)this).prevPosX + (((Entity)this).posX - ((Entity)this).prevPosX) * f - EntityFX.interpPosX);
        final float var14 = (float)(((Entity)this).prevPosY + (((Entity)this).posY - ((Entity)this).prevPosY) * f - EntityFX.interpPosY);
        final float var15 = (float)(((Entity)this).prevPosZ + (((Entity)this).posZ - ((Entity)this).prevPosZ) * f - EntityFX.interpPosZ);
        tessellator.setColorRGBA_F(((EntityFX)this).particleRed, ((EntityFX)this).particleGreen, ((EntityFX)this).particleBlue, ((EntityFX)this).particleAlpha);
        tessellator.addVertexWithUV((double)(var13 - f1 * var12 - f4 * var12), (double)(var14 - f2 * var12), (double)(var15 - f3 * var12 - f5 * var12), (double)maxU, (double)maxV);
        tessellator.addVertexWithUV((double)(var13 - f1 * var12 + f4 * var12), (double)(var14 + f2 * var12), (double)(var15 - f3 * var12 + f5 * var12), (double)maxU, (double)minV);
        tessellator.addVertexWithUV((double)(var13 + f1 * var12 + f4 * var12), (double)(var14 + f2 * var12), (double)(var15 + f3 * var12 + f5 * var12), (double)minU, (double)minV);
        tessellator.addVertexWithUV((double)(var13 + f1 * var12 - f4 * var12), (double)(var14 - f2 * var12), (double)(var15 + f3 * var12 - f5 * var12), (double)minU, (double)maxV);
    }
}
