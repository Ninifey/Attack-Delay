// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.entity.EntityLivingBase;
import lotr.common.entity.npc.LOTRNPCMount;
import lotr.common.entity.animal.LOTREntityWildBoar;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import lotr.client.model.LOTRModelBoar;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.entity.RenderLiving;

public class LOTRRenderWildBoar extends RenderLiving
{
    public static ResourceLocation boarSkin;
    private static ResourceLocation saddleTexture;
    
    public LOTRRenderWildBoar() {
        super((ModelBase)new LOTRModelBoar(), 0.7f);
        this.setRenderPassModel((ModelBase)new LOTRModelBoar(0.5f));
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        final LOTREntityWildBoar boar = (LOTREntityWildBoar)entity;
        return LOTRRenderHorse.getLayeredMountTexture(boar, LOTRRenderWildBoar.boarSkin);
    }
    
    protected int shouldRenderPass(final EntityLivingBase entity, final int pass, final float f) {
        if (pass == 0 && ((LOTREntityWildBoar)entity).isMountSaddled()) {
            this.bindTexture(LOTRRenderWildBoar.saddleTexture);
            return 1;
        }
        return super.shouldRenderPass(entity, pass, f);
    }
    
    static {
        LOTRRenderWildBoar.boarSkin = new ResourceLocation("lotr:mob/boar/boar.png");
        LOTRRenderWildBoar.saddleTexture = new ResourceLocation("lotr:mob/boar/saddle.png");
    }
}
