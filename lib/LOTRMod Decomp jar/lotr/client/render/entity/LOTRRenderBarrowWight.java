// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import org.lwjgl.opengl.GL11;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.EntityLivingBase;
import lotr.client.LOTRTickHandlerClient;
import net.minecraft.client.Minecraft;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.npc.LOTREntityBarrowWight;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBiped;
import lotr.client.model.LOTRModelBarrowWight;

public class LOTRRenderBarrowWight extends LOTRRenderBiped
{
    private static LOTRRandomSkins wightSkins;
    
    public LOTRRenderBarrowWight() {
        super(new LOTRModelBarrowWight(), 0.0f);
        LOTRRenderBarrowWight.wightSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/barrowWight/wight");
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity entity) {
        final LOTREntityBarrowWight wight = (LOTREntityBarrowWight)entity;
        return LOTRRenderBarrowWight.wightSkins.getRandomSkin(wight);
    }
    
    public void doRender(final Entity entity, final double d, final double d1, final double d2, final float f, final float f1) {
        super.doRender(entity, d, d1, d2, f, f1);
        final LOTREntityBarrowWight wight = (LOTREntityBarrowWight)entity;
        if (((Entity)wight).addedToChunk) {
            final Entity viewer = (Entity)Minecraft.getMinecraft().renderViewEntity;
            if (viewer != null && wight.getTargetEntityID() == viewer.getEntityId()) {
                LOTRTickHandlerClient.anyWightsViewed = true;
            }
        }
    }
    
    @Override
    protected void preRenderCallback(final EntityLivingBase entity, final float f) {
        super.preRenderCallback(entity, f);
        final float hover = MathHelper.sin((((Entity)entity).ticksExisted + f) * 0.05f) * 0.2f;
        GL11.glTranslatef(0.0f, hover, 0.0f);
        if (entity.deathTime > 0) {
            float death = (entity.deathTime + f - 1.0f) / 20.0f;
            death = Math.max(0.0f, death);
            death = Math.min(1.0f, death);
            final float scale = 1.0f + death * 1.0f;
            GL11.glScalef(scale, scale, scale);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3008);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f - death);
        }
    }
    
    protected float getDeathMaxRotation(final EntityLivingBase entity) {
        return 0.0f;
    }
}
