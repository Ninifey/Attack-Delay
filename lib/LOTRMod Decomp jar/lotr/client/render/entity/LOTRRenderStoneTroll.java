// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import lotr.common.entity.item.LOTREntityStoneTroll;
import org.lwjgl.opengl.GL11;
import net.minecraft.entity.Entity;
import lotr.client.model.LOTRModelTroll;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.entity.Render;

public class LOTRRenderStoneTroll extends Render
{
    private static ResourceLocation texture;
    private static LOTRModelTroll model;
    private static LOTRModelTroll shirtModel;
    private static LOTRModelTroll trousersModel;
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return LOTRRenderStoneTroll.texture;
    }
    
    public void doRender(final Entity entity, final double d, final double d1, final double d2, final float f, final float f1) {
        GL11.glPushMatrix();
        GL11.glDisable(2884);
        GL11.glTranslatef((float)d, (float)d1 + 1.5f, (float)d2);
        this.bindEntityTexture(entity);
        GL11.glScalef(-1.0f, -1.0f, 1.0f);
        GL11.glRotatef(180.0f - entity.rotationYaw, 0.0f, 1.0f, 0.0f);
        LOTRRenderStoneTroll.model.render(entity, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        this.bindTexture(LOTRRenderTroll.trollOutfits[((LOTREntityStoneTroll)entity).getTrollOutfit()]);
        LOTRRenderStoneTroll.shirtModel.render(entity, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        LOTRRenderStoneTroll.trousersModel.render(entity, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0625f);
        GL11.glEnable(2884);
        GL11.glPopMatrix();
    }
    
    static {
        LOTRRenderStoneTroll.texture = new ResourceLocation("lotr:mob/troll/stone.png");
        LOTRRenderStoneTroll.model = new LOTRModelTroll();
        LOTRRenderStoneTroll.shirtModel = new LOTRModelTroll(1.0f, 0);
        LOTRRenderStoneTroll.trousersModel = new LOTRModelTroll(0.75f, 1);
    }
}
