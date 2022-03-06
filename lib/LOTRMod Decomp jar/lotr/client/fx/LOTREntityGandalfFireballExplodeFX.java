// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.fx;

import net.minecraft.entity.Entity;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.World;
import net.minecraft.client.particle.EntityFireworkOverlayFX;

public class LOTREntityGandalfFireballExplodeFX extends EntityFireworkOverlayFX
{
    public LOTREntityGandalfFireballExplodeFX(final World world, final double d, final double d1, final double d2) {
        super(world, d, d1, d2);
        ((EntityFX)this).particleRed = 0.33f;
        ((EntityFX)this).particleGreen = 1.0f;
        ((EntityFX)this).particleBlue = 1.0f;
        ((EntityFX)this).particleMaxAge = 32;
    }
    
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender(final float f) {
        return 15728880;
    }
    
    public float getBrightness(final float f) {
        return 1.0f;
    }
    
    public void renderParticle(final Tessellator tessellator, final float f, final float f1, final float f2, final float f3, final float f4, final float f5) {
        final float var8 = 0.25f;
        final float var9 = var8 + 0.25f;
        final float var10 = 0.125f;
        final float var11 = var10 + 0.25f;
        final float var12 = 16.0f - ((EntityFX)this).particleAge * 0.2f;
        ((EntityFX)this).particleAlpha = 0.9f - (((EntityFX)this).particleAge + f - 1.0f) * 0.15f;
        final float var13 = (float)(((Entity)this).prevPosX + (((Entity)this).posX - ((Entity)this).prevPosX) * f - EntityFX.interpPosX);
        final float var14 = (float)(((Entity)this).prevPosY + (((Entity)this).posY - ((Entity)this).prevPosY) * f - EntityFX.interpPosY);
        final float var15 = (float)(((Entity)this).prevPosZ + (((Entity)this).posZ - ((Entity)this).prevPosZ) * f - EntityFX.interpPosZ);
        tessellator.setColorRGBA_F(((EntityFX)this).particleRed, ((EntityFX)this).particleGreen, ((EntityFX)this).particleBlue, ((EntityFX)this).particleAlpha);
        tessellator.addVertexWithUV((double)(var13 - f1 * var12 - f4 * var12), (double)(var14 - f2 * var12), (double)(var15 - f3 * var12 - f5 * var12), (double)var9, (double)var11);
        tessellator.addVertexWithUV((double)(var13 - f1 * var12 + f4 * var12), (double)(var14 + f2 * var12), (double)(var15 - f3 * var12 + f5 * var12), (double)var9, (double)var10);
        tessellator.addVertexWithUV((double)(var13 + f1 * var12 + f4 * var12), (double)(var14 + f2 * var12), (double)(var15 + f3 * var12 + f5 * var12), (double)var8, (double)var10);
        tessellator.addVertexWithUV((double)(var13 + f1 * var12 - f4 * var12), (double)(var14 - f2 * var12), (double)(var15 + f3 * var12 - f5 * var12), (double)var8, (double)var11);
    }
}
