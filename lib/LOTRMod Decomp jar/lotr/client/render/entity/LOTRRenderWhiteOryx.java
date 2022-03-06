// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import org.lwjgl.opengl.GL11;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.animal.LOTREntityWhiteOryx;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;

public class LOTRRenderWhiteOryx extends LOTRRenderGemsbok
{
    private LOTRRandomSkins oryxSkins;
    
    public LOTRRenderWhiteOryx() {
        this.oryxSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/whiteOryx");
    }
    
    public ResourceLocation getEntityTexture(final Entity entity) {
        return this.oryxSkins.getRandomSkin((LOTRRandomSkinEntity)entity);
    }
    
    protected void preRenderCallback(final EntityLivingBase entity, final float f) {
        final float scale = 0.9f;
        GL11.glScalef(scale, scale, scale);
    }
}
