// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.world.IBlockAccess;
import lotr.client.render.LOTRRenderBlocks;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.opengl.GL11;
import lotr.common.entity.projectile.LOTREntityPlate;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;

public class LOTRRenderPlate extends Render
{
    private RenderBlocks blockRenderer;
    
    public LOTRRenderPlate() {
        this.blockRenderer = new RenderBlocks();
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return TextureMap.locationBlocksTexture;
    }
    
    public void doRender(final Entity entity, final double d, final double d1, final double d2, final float f, final float f1) {
        final LOTREntityPlate plate = (LOTREntityPlate)entity;
        GL11.glEnable(32826);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)d, (float)d1, (float)d2);
        GL11.glRotatef(-f, 0.0f, 1.0f, 0.0f);
        final int i = entity.getBrightnessForRender(f1);
        final int j = i % 65536;
        final int k = i / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0f, k / 1.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.bindEntityTexture(entity);
        LOTRRenderBlocks.renderEntityPlate((IBlockAccess)entity.worldObj, 0, 0, 0, plate.getPlateBlock(), this.blockRenderer);
        GL11.glPopMatrix();
        GL11.glDisable(32826);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
}
