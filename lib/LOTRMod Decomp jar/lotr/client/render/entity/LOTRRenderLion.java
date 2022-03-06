// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.client.renderer.entity.RendererLivingEntity;
import lotr.client.model.LOTRModelLionOld;
import lotr.client.model.LOTRModelLion;
import lotr.common.entity.animal.LOTREntityLioness;
import lotr.common.entity.animal.LOTREntityLionBase;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.entity.RenderLiving;

public class LOTRRenderLion extends RenderLiving
{
    public static ResourceLocation textureLion;
    public static ResourceLocation textureLioness;
    private static ResourceLocation textureTicket;
    private static ModelBase lionModel;
    private static ModelBase lionModelOld;
    
    public LOTRRenderLion() {
        super(LOTRRenderLion.lionModel, 0.5f);
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        final LOTREntityLionBase lion = (LOTREntityLionBase)entity;
        if (isTicket(lion)) {
            return LOTRRenderLion.textureTicket;
        }
        return (lion instanceof LOTREntityLioness) ? LOTRRenderLion.textureLioness : LOTRRenderLion.textureLion;
    }
    
    public void doRender(final Entity entity, final double d, final double d1, final double d2, final float f, final float f1) {
        final LOTREntityLionBase lion = (LOTREntityLionBase)entity;
        if (isTicket(lion)) {
            ((RendererLivingEntity)this).mainModel = LOTRRenderLion.lionModelOld;
        }
        else {
            ((RendererLivingEntity)this).mainModel = LOTRRenderLion.lionModel;
        }
        super.doRender(entity, d, d1, d2, f, f1);
    }
    
    private static boolean isTicket(final LOTREntityLionBase lion) {
        return lion.hasCustomNameTag() && lion.getCustomNameTag().equalsIgnoreCase("ticket lion");
    }
    
    static {
        LOTRRenderLion.textureLion = new ResourceLocation("lotr:mob/lion/lion.png");
        LOTRRenderLion.textureLioness = new ResourceLocation("lotr:mob/lion/lioness.png");
        LOTRRenderLion.textureTicket = new ResourceLocation("lotr:mob/lion/ticketlion.png");
        LOTRRenderLion.lionModel = new LOTRModelLion();
        LOTRRenderLion.lionModelOld = new LOTRModelLionOld();
    }
}
