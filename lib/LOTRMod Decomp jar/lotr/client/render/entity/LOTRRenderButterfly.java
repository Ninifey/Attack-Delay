// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import java.util.HashMap;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.entity.LOTRRandomSkinEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import lotr.client.model.LOTRModelButterfly;
import lotr.common.entity.animal.LOTREntityButterfly;
import java.util.Map;
import net.minecraft.client.renderer.entity.RenderLiving;

public class LOTRRenderButterfly extends RenderLiving
{
    private static Map<LOTREntityButterfly.ButterflyType, LOTRRandomSkins> textures;
    
    public LOTRRenderButterfly() {
        super((ModelBase)new LOTRModelButterfly(), 0.2f);
        for (final LOTREntityButterfly.ButterflyType t : LOTREntityButterfly.ButterflyType.values()) {
            LOTRRenderButterfly.textures.put(t, LOTRRandomSkins.loadSkinsList("lotr:mob/butterfly/" + t.textureDir));
        }
    }
    
    public void doRender(final Entity entity, final double d, final double d1, final double d2, final float f, final float f1) {
        final LOTREntityButterfly butterfly = (LOTREntityButterfly)entity;
        if (butterfly.getButterflyType() == LOTREntityButterfly.ButterflyType.LORIEN) {
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glDisable(2896);
        }
        super.doRender(entity, d, d1, d2, f, f1);
        GL11.glEnable(2896);
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        final LOTREntityButterfly butterfly = (LOTREntityButterfly)entity;
        final LOTRRandomSkins skins = LOTRRenderButterfly.textures.get(butterfly.getButterflyType());
        return skins.getRandomSkin(butterfly);
    }
    
    protected void preRenderCallback(final EntityLivingBase entity, final float f) {
        GL11.glScalef(0.3f, 0.3f, 0.3f);
    }
    
    protected float handleRotationFloat(final EntityLivingBase entity, final float f) {
        final LOTREntityButterfly butterfly = (LOTREntityButterfly)entity;
        if (butterfly.isButterflyStill() && butterfly.flapTime > 0) {
            return butterfly.flapTime - f;
        }
        return super.handleRotationFloat(entity, f);
    }
    
    static {
        LOTRRenderButterfly.textures = new HashMap<LOTREntityButterfly.ButterflyType, LOTRRandomSkins>();
    }
}
