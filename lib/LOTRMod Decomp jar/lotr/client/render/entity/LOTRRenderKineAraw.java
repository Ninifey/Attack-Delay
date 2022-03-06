// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import org.lwjgl.opengl.GL11;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.animal.LOTREntityKineAraw;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;

public class LOTRRenderKineAraw extends LOTRRenderAurochs
{
    private static LOTRRandomSkins kineSkins;
    
    public LOTRRenderKineAraw() {
        LOTRRenderKineAraw.kineSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/kineAraw");
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity entity) {
        final LOTREntityKineAraw kine = (LOTREntityKineAraw)entity;
        final ResourceLocation skin = LOTRRenderKineAraw.kineSkins.getRandomSkin(kine);
        return skin;
    }
    
    protected void preRenderCallback(final EntityLivingBase entity, final float f) {
        final float scale = LOTREntityKineAraw.KINE_SCALE;
        GL11.glScalef(scale, scale, scale);
    }
}
