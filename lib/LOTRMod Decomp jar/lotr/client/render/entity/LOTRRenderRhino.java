// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.entity.EntityLivingBase;
import lotr.common.entity.npc.LOTRNPCMount;
import lotr.common.entity.animal.LOTREntityRhino;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import lotr.client.model.LOTRModelRhino;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.entity.RenderLiving;

public class LOTRRenderRhino extends RenderLiving
{
    private static ResourceLocation rhinoTexture;
    private static ResourceLocation saddleTexture;
    
    public LOTRRenderRhino() {
        super((ModelBase)new LOTRModelRhino(), 0.5f);
        this.setRenderPassModel((ModelBase)new LOTRModelRhino(0.5f));
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        final LOTREntityRhino rhino = (LOTREntityRhino)entity;
        return LOTRRenderHorse.getLayeredMountTexture(rhino, LOTRRenderRhino.rhinoTexture);
    }
    
    protected int shouldRenderPass(final EntityLivingBase entity, final int pass, final float f) {
        if (pass == 0 && ((LOTREntityRhino)entity).isMountSaddled()) {
            this.bindTexture(LOTRRenderRhino.saddleTexture);
            return 1;
        }
        return super.shouldRenderPass(entity, pass, f);
    }
    
    static {
        LOTRRenderRhino.rhinoTexture = new ResourceLocation("lotr:mob/rhino/rhino.png");
        LOTRRenderRhino.saddleTexture = new ResourceLocation("lotr:mob/rhino/saddle.png");
    }
}
