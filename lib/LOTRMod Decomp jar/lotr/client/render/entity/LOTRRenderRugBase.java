// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import org.lwjgl.opengl.GL11;
import lotr.common.entity.item.LOTREntityRugBase;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;

public abstract class LOTRRenderRugBase extends Render
{
    private ModelBase rugModel;
    
    public LOTRRenderRugBase(final ModelBase m) {
        this.rugModel = m;
    }
    
    public void doRender(final Entity entity, final double d, final double d1, final double d2, final float f, final float f1) {
        final LOTREntityRugBase rug = (LOTREntityRugBase)entity;
        GL11.glPushMatrix();
        GL11.glDisable(2884);
        GL11.glTranslatef((float)d, (float)d1, (float)d2);
        this.bindEntityTexture((Entity)rug);
        GL11.glScalef(-1.0f, -1.0f, 1.0f);
        GL11.glRotatef(180.0f - rug.rotationYaw, 0.0f, 1.0f, 0.0f);
        this.preRenderCallback();
        this.rugModel.render((Entity)rug, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        GL11.glEnable(2884);
        GL11.glPopMatrix();
    }
    
    protected void preRenderCallback() {
    }
}
