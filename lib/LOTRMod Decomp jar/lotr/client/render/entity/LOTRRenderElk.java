// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.entity.EntityLivingBase;
import lotr.common.entity.npc.LOTRNPCMount;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.animal.LOTREntityElk;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import lotr.client.model.LOTRModelElk;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.entity.RenderLiving;

public class LOTRRenderElk extends RenderLiving
{
    private static LOTRRandomSkins elkSkins;
    private static ResourceLocation saddleTexture;
    
    public LOTRRenderElk() {
        super((ModelBase)new LOTRModelElk(), 0.5f);
        this.setRenderPassModel((ModelBase)new LOTRModelElk(0.5f));
        LOTRRenderElk.elkSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/elk/elk");
    }
    
    public ResourceLocation getEntityTexture(final Entity entity) {
        final LOTREntityElk elk = (LOTREntityElk)entity;
        final ResourceLocation elkSkin = LOTRRenderElk.elkSkins.getRandomSkin(elk);
        return LOTRRenderHorse.getLayeredMountTexture(elk, elkSkin);
    }
    
    protected int shouldRenderPass(final EntityLivingBase entity, final int pass, final float f) {
        if (pass == 0 && ((LOTREntityElk)entity).isMountSaddled()) {
            this.bindTexture(LOTRRenderElk.saddleTexture);
            return 1;
        }
        return super.shouldRenderPass(entity, pass, f);
    }
    
    static {
        LOTRRenderElk.saddleTexture = new ResourceLocation("lotr:mob/elk/saddle.png");
    }
}
