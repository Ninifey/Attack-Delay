// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import org.lwjgl.opengl.GL11;
import lotr.client.fx.LOTREntityDeadMarshFace;
import net.minecraft.entity.Entity;
import lotr.client.model.LOTRModelMarshWraith;
import net.minecraft.client.model.ModelBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.entity.Render;

public class LOTRRenderDeadMarshFace extends Render
{
    private static ResourceLocation skin;
    private ModelBase model;
    
    public LOTRRenderDeadMarshFace() {
        this.model = new LOTRModelMarshWraith();
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return LOTRRenderDeadMarshFace.skin;
    }
    
    public void doRender(final Entity entity, final double d, final double d1, final double d2, final float f, final float f1) {
        final LOTREntityDeadMarshFace face = (LOTREntityDeadMarshFace)entity;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)d, (float)d1, (float)d2);
        GL11.glEnable(32826);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, face.faceAlpha);
        GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(((Entity)face).rotationYaw, 0.0f, 0.0f, 1.0f);
        GL11.glRotatef(((Entity)face).rotationPitch, 0.0f, 1.0f, 0.0f);
        this.bindEntityTexture((Entity)face);
        this.model.render((Entity)null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        GL11.glDisable(3042);
        GL11.glDisable(32826);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }
    
    static {
        LOTRRenderDeadMarshFace.skin = new ResourceLocation("lotr:mob/wraith/marshWraith.png");
    }
}
