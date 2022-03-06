// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import lotr.common.entity.animal.LOTREntityGiraffe;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import lotr.client.model.LOTRModelGiraffe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.entity.RenderLiving;

public class LOTRRenderGiraffe extends RenderLiving
{
    public static ResourceLocation texture;
    private static ResourceLocation saddleTexture;
    
    public LOTRRenderGiraffe() {
        super((ModelBase)new LOTRModelGiraffe(0.0f), 0.5f);
        this.setRenderPassModel((ModelBase)new LOTRModelGiraffe(0.5f));
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return LOTRRenderGiraffe.texture;
    }
    
    protected int shouldRenderPass(final EntityLivingBase entity, final int pass, final float f) {
        if (pass == 0 && ((LOTREntityGiraffe)entity).isMountSaddled()) {
            this.bindTexture(LOTRRenderGiraffe.saddleTexture);
            return 1;
        }
        return super.shouldRenderPass(entity, pass, f);
    }
    
    static {
        LOTRRenderGiraffe.texture = new ResourceLocation("lotr:mob/giraffe/giraffe.png");
        LOTRRenderGiraffe.saddleTexture = new ResourceLocation("lotr:mob/giraffe/saddle.png");
    }
}
