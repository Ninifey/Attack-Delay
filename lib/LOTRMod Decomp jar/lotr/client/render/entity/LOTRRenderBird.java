// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import java.util.HashMap;
import net.minecraft.item.ItemStack;
import lotr.common.entity.animal.LOTREntitySeagull;
import lotr.common.entity.animal.LOTREntityGorcrow;
import org.lwjgl.opengl.GL11;
import lotr.common.entity.animal.LOTREntityCrebain;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.animal.LOTREntityBird;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import lotr.client.model.LOTRModelBird;
import java.util.Map;
import net.minecraft.client.renderer.entity.RenderLiving;

public class LOTRRenderBird extends RenderLiving
{
    private static Map<String, LOTRRandomSkins> birdTypeSkins;
    public static boolean renderStolenItem;
    
    public LOTRRenderBird() {
        super((ModelBase)new LOTRModelBird(), 0.2f);
    }
    
    private LOTRRandomSkins getBirdSkins(final String s) {
        LOTRRandomSkins skins = LOTRRenderBird.birdTypeSkins.get(s);
        if (skins == null) {
            skins = LOTRRandomSkins.loadSkinsList("lotr:mob/bird/" + s);
            LOTRRenderBird.birdTypeSkins.put(s, skins);
        }
        return skins;
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        final LOTREntityBird bird = (LOTREntityBird)entity;
        final String type = bird.getBirdTextureDir();
        final LOTRRandomSkins skins = this.getBirdSkins(type);
        return skins.getRandomSkin(bird);
    }
    
    public void preRenderCallback(final EntityLivingBase entity, final float f) {
        if (entity instanceof LOTREntityCrebain) {
            final float scale = LOTREntityCrebain.CREBAIN_SCALE;
            GL11.glScalef(scale, scale, scale);
        }
        else if (entity instanceof LOTREntityGorcrow) {
            final float scale = LOTREntityGorcrow.GORCROW_SCALE;
            GL11.glScalef(scale, scale, scale);
        }
        else if (entity instanceof LOTREntitySeagull) {
            final float scale = LOTREntitySeagull.SEAGULL_SCALE;
            GL11.glScalef(scale, scale, scale);
        }
    }
    
    protected float handleRotationFloat(final EntityLivingBase entity, final float f) {
        final LOTREntityBird bird = (LOTREntityBird)entity;
        if (bird.isBirdStill() && bird.flapTime > 0) {
            return bird.flapTime - f;
        }
        return super.handleRotationFloat(entity, f);
    }
    
    protected void renderEquippedItems(final EntityLivingBase entity, final float f) {
        final LOTREntityBird bird = (LOTREntityBird)entity;
        if (LOTRRenderBird.renderStolenItem) {
            GL11.glColor3f(1.0f, 1.0f, 1.0f);
            final ItemStack stolenItem = bird.getStolenItem();
            if (stolenItem != null) {
                GL11.glPushMatrix();
                ((LOTRModelBird)((RendererLivingEntity)this).mainModel).head.postRender(0.0625f);
                GL11.glTranslatef(0.05f, 1.4f, -0.1f);
                final float scale = 0.25f;
                GL11.glScalef(scale, scale, scale);
                ((Render)this).renderManager.itemRenderer.renderItem(entity, stolenItem, 0);
                GL11.glPopMatrix();
            }
        }
    }
    
    static {
        LOTRRenderBird.birdTypeSkins = new HashMap<String, LOTRRandomSkins>();
        LOTRRenderBird.renderStolenItem = true;
    }
}
