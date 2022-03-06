// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import org.lwjgl.opengl.GL11;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.animal.LOTREntityRabbit;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBase;
import lotr.client.model.LOTRModelRabbit;
import net.minecraft.client.renderer.entity.RenderLiving;

public class LOTRRenderRabbit extends RenderLiving
{
    private static LOTRRandomSkins rabbitSkins;
    
    public LOTRRenderRabbit() {
        super((ModelBase)new LOTRModelRabbit(), 0.3f);
        LOTRRenderRabbit.rabbitSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/rabbit");
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        final LOTREntityRabbit rabbit = (LOTREntityRabbit)entity;
        return LOTRRenderRabbit.rabbitSkins.getRandomSkin(rabbit);
    }
    
    protected void preRenderCallback(final EntityLivingBase entity, final float f) {
        GL11.glScalef(0.75f, 0.75f, 0.75f);
    }
}
