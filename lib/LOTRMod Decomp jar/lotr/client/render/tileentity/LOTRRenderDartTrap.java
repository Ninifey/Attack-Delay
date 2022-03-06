// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.client.renderer.RenderGlobal;
import lotr.common.tileentity.LOTRTileEntityDartTrap;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class LOTRRenderDartTrap extends TileEntitySpecialRenderer
{
    public void renderTileEntityAt(final TileEntity tileentity, final double d, final double d1, final double d2, final float f) {
        final Minecraft mc = Minecraft.getMinecraft();
        if (((EntityPlayer)mc.thePlayer).capabilities.isCreativeMode && mc.gameSettings.showDebugInfo && Minecraft.isGuiEnabled()) {
            GL11.glPushMatrix();
            GL11.glTranslated(-TileEntityRendererDispatcher.staticPlayerX, -TileEntityRendererDispatcher.staticPlayerY, -TileEntityRendererDispatcher.staticPlayerZ);
            GL11.glDepthMask(false);
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            GL11.glDisable(2884);
            GL11.glDisable(3042);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            final AxisAlignedBB range = ((LOTRTileEntityDartTrap)tileentity).getTriggerRange();
            RenderGlobal.drawOutlinedBoundingBox(range, 16777215);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glEnable(3553);
            GL11.glEnable(2896);
            GL11.glEnable(2884);
            GL11.glDisable(3042);
            GL11.glDepthMask(true);
            GL11.glPopMatrix();
        }
    }
}
