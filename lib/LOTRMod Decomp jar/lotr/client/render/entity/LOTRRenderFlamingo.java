// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.util.MathHelper;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.entity.animal.LOTREntityFlamingo;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import lotr.client.model.LOTRModelFlamingo;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.entity.RenderLiving;

public class LOTRRenderFlamingo extends RenderLiving
{
    private static ResourceLocation texture;
    private static ResourceLocation textureChick;
    
    public LOTRRenderFlamingo() {
        super((ModelBase)new LOTRModelFlamingo(), 0.5f);
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return ((LOTREntityFlamingo)entity).isChild() ? LOTRRenderFlamingo.textureChick : LOTRRenderFlamingo.texture;
    }
    
    protected float handleRotationFloat(final EntityLivingBase entity, final float f) {
        final LOTREntityFlamingo flamingo = (LOTREntityFlamingo)entity;
        final float f2 = flamingo.field_756_e + (flamingo.field_752_b - flamingo.field_756_e) * f;
        final float f3 = flamingo.field_757_d + (flamingo.destPos - flamingo.field_757_d) * f;
        return (MathHelper.sin(f2) + 1.0f) * f3;
    }
    
    static {
        LOTRRenderFlamingo.texture = new ResourceLocation("lotr:mob/flamingo/flamingo.png");
        LOTRRenderFlamingo.textureChick = new ResourceLocation("lotr:mob/flamingo/chick.png");
    }
}
