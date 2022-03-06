// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import org.lwjgl.opengl.GL11;
import lotr.common.entity.animal.LOTREntityScorpion;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.entity.animal.LOTREntityDesertScorpion;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import lotr.client.model.LOTRModelScorpion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.entity.RenderLiving;

public class LOTRRenderScorpion extends RenderLiving
{
    private static ResourceLocation jungleTexture;
    private static ResourceLocation desertTexture;
    
    public LOTRRenderScorpion() {
        super((ModelBase)new LOTRModelScorpion(), 1.0f);
        this.setRenderPassModel((ModelBase)new LOTRModelScorpion());
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        if (entity instanceof LOTREntityDesertScorpion) {
            return LOTRRenderScorpion.desertTexture;
        }
        return LOTRRenderScorpion.jungleTexture;
    }
    
    protected void preRenderCallback(final EntityLivingBase entity, final float f) {
        final float scale = ((LOTREntityScorpion)entity).getScorpionScaleAmount();
        GL11.glScalef(scale, scale, scale);
    }
    
    protected float getDeathMaxRotation(final EntityLivingBase entity) {
        return 180.0f;
    }
    
    public float handleRotationFloat(final EntityLivingBase entity, final float f) {
        float strikeTime = (float)((LOTREntityScorpion)entity).getStrikeTime();
        if (strikeTime > 0.0f) {
            strikeTime -= f;
        }
        return strikeTime / 20.0f;
    }
    
    static {
        LOTRRenderScorpion.jungleTexture = new ResourceLocation("lotr:mob/scorpion/jungle.png");
        LOTRRenderScorpion.desertTexture = new ResourceLocation("lotr:mob/scorpion/desert.png");
    }
}
