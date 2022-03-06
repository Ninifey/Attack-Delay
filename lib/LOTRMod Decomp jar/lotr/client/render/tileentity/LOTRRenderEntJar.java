// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.tileentity;

import net.minecraft.util.IIcon;
import java.awt.Color;
import net.minecraft.world.IBlockAccess;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;
import lotr.common.tileentity.LOTRTileEntityEntJar;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class LOTRRenderEntJar extends TileEntitySpecialRenderer
{
    public void renderTileEntityAt(final TileEntity tileentity, final double d, final double d1, final double d2, final float f) {
        final LOTRTileEntityEntJar jar = (LOTRTileEntityEntJar)tileentity;
        if (jar.drinkAmount <= 0) {
            return;
        }
        GL11.glPushMatrix();
        GL11.glDisable(2884);
        GL11.glEnable(32826);
        GL11.glTranslatef((float)d + 0.5f, (float)d1, (float)d2 + 0.5f);
        GL11.glScalef(-1.0f, -1.0f, 1.0f);
        GL11.glDisable(2896);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        final float transparency = 0.5f;
        final Tessellator tessellator = Tessellator.instance;
        IIcon icon = null;
        float minU = 0.0f;
        float maxU = 0.0f;
        float minV = 0.0f;
        float maxV = 0.0f;
        int color = 16777215;
        if (jar.drinkMeta >= 0) {
            this.bindTexture(TextureMap.locationItemsTexture);
            final ItemStack drink = new ItemStack(LOTRMod.entDraught, 1, jar.drinkMeta);
            icon = drink.getIconIndex();
            minU = icon.getInterpolatedU(7.0);
            maxU = icon.getInterpolatedU(8.0);
            minV = icon.getInterpolatedV(7.0);
            maxV = icon.getInterpolatedV(8.0);
        }
        else {
            this.bindTexture(TextureMap.locationBlocksTexture);
            icon = Blocks.water.getBlockTextureFromSide(1);
            minU = icon.getInterpolatedU(0.0);
            maxU = icon.getInterpolatedU(6.0);
            minV = icon.getInterpolatedV(0.0);
            maxV = icon.getInterpolatedV(6.0);
            color = Blocks.water.colorMultiplier((IBlockAccess)jar.getWorldObj(), jar.xCoord, jar.yCoord, jar.zCoord);
        }
        final double d3 = 0.1875;
        final double d4 = -0.0625 - 0.75 * jar.drinkAmount / LOTRTileEntityEntJar.MAX_CAPACITY;
        tessellator.startDrawingQuads();
        final float[] colors = new Color(color).getColorComponents(null);
        tessellator.setColorRGBA_F(colors[0], colors[1], colors[2], transparency);
        tessellator.addVertexWithUV(-d3, d4, d3, (double)minU, (double)maxV);
        tessellator.addVertexWithUV(d3, d4, d3, (double)maxU, (double)maxV);
        tessellator.addVertexWithUV(d3, d4, -d3, (double)maxU, (double)minV);
        tessellator.addVertexWithUV(-d3, d4, -d3, (double)minU, (double)minV);
        tessellator.draw();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(3042);
        GL11.glEnable(2896);
        GL11.glDisable(32826);
        GL11.glEnable(2884);
        GL11.glPopMatrix();
    }
}
