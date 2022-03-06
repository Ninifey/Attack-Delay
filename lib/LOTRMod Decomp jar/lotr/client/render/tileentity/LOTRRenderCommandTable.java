// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.tileentity;

import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import lotr.client.LOTRTextures;
import lotr.common.world.genlayer.LOTRGenLayerWorld;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import lotr.common.tileentity.LOTRTileEntityCommandTable;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class LOTRRenderCommandTable extends TileEntitySpecialRenderer
{
    public void renderTileEntityAt(final TileEntity tileentity, final double d, final double d1, final double d2, final float f) {
        final LOTRTileEntityCommandTable table = (LOTRTileEntityCommandTable)tileentity;
        this.renderTableAt(d, d1, d2, tileentity.xCoord + 0.5, tileentity.zCoord + 0.5, table.getZoomExp());
    }
    
    public void renderInvTable() {
        GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
        GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
        final EntityLivingBase viewer = Minecraft.getMinecraft().renderViewEntity;
        this.renderTableAt(0.0, 0.0, 0.0, ((Entity)viewer).posX, ((Entity)viewer).posZ, 0);
        this.bindTexture(TextureMap.locationBlocksTexture);
    }
    
    private void renderTableAt(final double d, final double d1, final double d2, final double viewerX, final double viewerZ, final int zoomExp) {
        GL11.glEnable(32826);
        GL11.glDisable(2884);
        GL11.glDisable(2896);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)d + 0.5f, (float)d1 + 1.1f, (float)d2 + 0.5f);
        float posX = (float)(Math.round(viewerX / LOTRGenLayerWorld.scale) + 810L);
        float posY = (float)(Math.round(viewerZ / LOTRGenLayerWorld.scale) + 730L);
        int viewportWidth = 400;
        viewportWidth = (int)Math.round(viewportWidth * Math.pow(2.0, zoomExp));
        final double radius = 0.9;
        final float minX = posX - viewportWidth / 2;
        final float maxX = posX + viewportWidth / 2;
        if (minX < 0.0f) {
            posX = (float)(0 + viewportWidth / 2);
        }
        if (maxX >= LOTRGenLayerWorld.imageWidth) {
            posX = (float)(LOTRGenLayerWorld.imageWidth - viewportWidth / 2);
        }
        final float minY = posY - viewportWidth / 2;
        final float maxY = posY + viewportWidth / 2;
        if (minY < 0.0f) {
            posY = (float)(0 + viewportWidth / 2);
        }
        if (maxY >= LOTRGenLayerWorld.imageHeight) {
            posY = (float)(LOTRGenLayerWorld.imageHeight - viewportWidth / 2);
        }
        final double minU = (posX - viewportWidth / 2) / (double)LOTRGenLayerWorld.imageWidth;
        final double maxU = (posX + viewportWidth / 2) / (double)LOTRGenLayerWorld.imageWidth;
        final double minV = (posY - viewportWidth / 2) / (double)LOTRGenLayerWorld.imageHeight;
        final double maxV = (posY + viewportWidth / 2) / (double)LOTRGenLayerWorld.imageHeight;
        GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        LOTRTextures.drawMap((EntityPlayer)Minecraft.getMinecraft().thePlayer, true, -radius, radius, -radius, radius, 0.0, minU, maxU, minV, maxV, 1.0f);
        LOTRTextures.drawMapOverlay((EntityPlayer)Minecraft.getMinecraft().thePlayer, -radius, radius, -radius, radius, 0.0, minU, maxU, minV, maxV);
        final double compassInset = radius * 0.05;
        LOTRTextures.drawMapCompassBottomLeft(-radius + compassInset, radius - compassInset, -0.01, 0.15 * radius * 0.0625);
        GL11.glDisable(3553);
        final Tessellator tess = Tessellator.instance;
        final double rRed = radius + 0.015;
        final double rBlack = rRed + 0.015;
        GL11.glTranslatef(0.0f, 0.0f, 0.01f);
        tess.startDrawingQuads();
        tess.setColorOpaque_I(-6156032);
        tess.addVertex(-rRed, rRed, 0.0);
        tess.addVertex(rRed, rRed, 0.0);
        tess.addVertex(rRed, -rRed, 0.0);
        tess.addVertex(-rRed, -rRed, 0.0);
        tess.draw();
        GL11.glTranslatef(0.0f, 0.0f, 0.01f);
        tess.startDrawingQuads();
        tess.setColorOpaque_I(-16777216);
        tess.addVertex(-rBlack, rBlack, 0.0);
        tess.addVertex(rBlack, rBlack, 0.0);
        tess.addVertex(rBlack, -rBlack, 0.0);
        tess.addVertex(-rBlack, -rBlack, 0.0);
        tess.draw();
        GL11.glEnable(3553);
        GL11.glPopMatrix();
        GL11.glEnable(2896);
    }
}
