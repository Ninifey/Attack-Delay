// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import lotr.common.LOTRMod;
import org.lwjgl.opengl.GL11;
import lotr.common.entity.item.LOTREntityOrcBomb;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;

public class LOTRRenderOrcBomb extends Render
{
    private RenderBlocks blockRenderer;
    
    public LOTRRenderOrcBomb() {
        this.blockRenderer = new RenderBlocks();
        super.shadowSize = 0.5f;
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return TextureMap.locationBlocksTexture;
    }
    
    public void doRender(final Entity entity, final double d, final double d1, final double d2, final float f, final float f1) {
        final LOTREntityOrcBomb bomb = (LOTREntityOrcBomb)entity;
        this.renderBomb((Entity)bomb, d, d1, d2, f1, bomb.orcBombFuse, bomb.getBombStrengthLevel(), 1.0f, entity.getBrightness(f1));
    }
    
    public void renderBomb(final Entity entity, final double d, final double d1, final double d2, final float ticks, final int fuse, final int strengthLevel, final float bombScale, final float brightness) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)d, (float)d1, (float)d2);
        GL11.glScalef(bombScale, bombScale, bombScale);
        if (fuse - ticks + 1.0f < 10.0f) {
            float f2 = 1.0f - (fuse - ticks + 1.0f) / 10.0f;
            if (f2 < 0.0f) {
                f2 = 0.0f;
            }
            if (f2 > 1.0f) {
                f2 = 1.0f;
            }
            f2 *= f2;
            f2 *= f2;
            final float scale = 1.0f + f2 * 0.3f;
            GL11.glScalef(scale, scale, scale);
        }
        float f2 = (1.0f - (fuse - ticks + 1.0f) / 100.0f) * 0.8f;
        this.bindEntityTexture(entity);
        this.blockRenderer.renderBlockAsItem(LOTRMod.orcBomb, strengthLevel, brightness);
        if (fuse / 5 % 2 == 0) {
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 772);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, f2);
            this.blockRenderer.renderBlockAsItem(LOTRMod.orcBomb, strengthLevel, 1.0f);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glDisable(3042);
            GL11.glEnable(2896);
            GL11.glEnable(3553);
        }
        GL11.glPopMatrix();
    }
}
