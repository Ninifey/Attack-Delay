// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import org.lwjgl.opengl.GL11;
import lotr.common.entity.npc.LOTREntitySauron;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBiped;
import lotr.client.model.LOTRModelSauron;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.entity.RenderBiped;

public class LOTRRenderSauron extends RenderBiped
{
    private static ResourceLocation skin;
    
    public LOTRRenderSauron() {
        super((ModelBiped)new LOTRModelSauron(), 0.5f);
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return LOTRRenderSauron.skin;
    }
    
    public void doRender(final Entity entity, final double d, final double d1, final double d2, final float f, final float f1) {
        final LOTREntitySauron sauron = (LOTREntitySauron)entity;
        if (sauron.getIsUsingMace()) {
            final ModelBiped field_82423_g = super.field_82423_g;
            final ModelBiped field_82425_h = super.field_82425_h;
            final ModelBiped modelBipedMain = super.modelBipedMain;
            final int heldItemRight = 3;
            modelBipedMain.heldItemRight = heldItemRight;
            field_82425_h.heldItemRight = heldItemRight;
            field_82423_g.heldItemRight = heldItemRight;
            final ModelBiped field_82423_g2 = super.field_82423_g;
            final ModelBiped field_82425_h2 = super.field_82425_h;
            final ModelBiped modelBipedMain2 = super.modelBipedMain;
            final boolean aimedBow = true;
            modelBipedMain2.aimedBow = aimedBow;
            field_82425_h2.aimedBow = aimedBow;
            field_82423_g2.aimedBow = aimedBow;
        }
        super.doRender(entity, d, d1, d2, f, f1);
    }
    
    protected void func_82422_c() {
        GL11.glTranslatef(0.0f, 0.4375f, 0.0f);
    }
    
    static {
        LOTRRenderSauron.skin = new ResourceLocation("lotr:mob/char/sauron.png");
    }
}
