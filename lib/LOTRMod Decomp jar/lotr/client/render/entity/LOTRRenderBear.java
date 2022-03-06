// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import java.util.HashMap;
import org.lwjgl.opengl.GL11;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.entity.animal.LOTREntityBear;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import lotr.client.model.LOTRModelBear;
import java.util.Map;
import net.minecraft.client.renderer.entity.RenderLiving;

public class LOTRRenderBear extends RenderLiving
{
    private static Map bearSkins;
    
    public LOTRRenderBear() {
        super((ModelBase)new LOTRModelBear(), 0.5f);
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        final LOTREntityBear bear = (LOTREntityBear)entity;
        return getBearSkin(bear.getBearType());
    }
    
    public static ResourceLocation getBearSkin(final LOTREntityBear.BearType type) {
        final String s = type.textureName();
        ResourceLocation skin = LOTRRenderBear.bearSkins.get(s);
        if (skin == null) {
            skin = new ResourceLocation("lotr:mob/bear/" + s + ".png");
            LOTRRenderBear.bearSkins.put(s, skin);
        }
        return skin;
    }
    
    public void preRenderCallback(final EntityLivingBase entity, final float f) {
        scaleBearModel();
    }
    
    public static void scaleBearModel() {
        final float scale = 1.2f;
        GL11.glScalef(scale, scale, scale);
    }
    
    static {
        LOTRRenderBear.bearSkins = new HashMap();
    }
}
