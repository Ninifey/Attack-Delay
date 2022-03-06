// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;
import lotr.common.entity.item.LOTREntityBarrel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import lotr.common.LOTRMod;
import net.minecraft.item.ItemStack;
import net.minecraft.client.renderer.entity.Render;

public class LOTRRenderEntityBarrel extends Render
{
    private ItemStack barrelItem;
    
    public LOTRRenderEntityBarrel() {
        this.barrelItem = new ItemStack(LOTRMod.barrel);
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return TextureMap.locationBlocksTexture;
    }
    
    public void doRender(final Entity entity, final double d, final double d1, final double d2, final float f, final float f1) {
        final LOTREntityBarrel barrel = (LOTREntityBarrel)entity;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)d, (float)d1 + 0.5f, (float)d2);
        GL11.glRotatef(180.0f - f, 0.0f, 1.0f, 0.0f);
        final float f2 = barrel.getTimeSinceHit() - f1;
        float f3 = barrel.getDamageTaken() - f1;
        if (f3 < 0.0f) {
            f3 = 0.0f;
        }
        if (f2 > 0.0f) {
            GL11.glRotatef(MathHelper.sin(f2) * f2 * f3 / 10.0f * barrel.getForwardDirection(), 1.0f, 0.0f, 0.0f);
        }
        this.bindEntityTexture((Entity)barrel);
        GL11.glScalef(1.5f, 1.5f, 1.5f);
        super.renderManager.itemRenderer.renderItem(super.renderManager.livingPlayer, this.barrelItem, 0);
        GL11.glPopMatrix();
    }
}
