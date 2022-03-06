// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.GL11;
import lotr.client.LOTRTickHandlerClient;
import org.lwjgl.input.Keyboard;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLiving;
import lotr.common.entity.LOTRRandomSkinEntity;
import lotr.common.entity.npc.LOTREntityScrapTrader;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelBiped;
import lotr.client.model.LOTRModelHuman;

public class LOTRRenderScrapTrader extends LOTRRenderBiped
{
    private static LOTRRandomSkins traderSkins;
    
    public LOTRRenderScrapTrader() {
        super(new LOTRModelHuman(), 0.5f);
        LOTRRenderScrapTrader.traderSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/scrapTrader");
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity entity) {
        return LOTRRenderScrapTrader.traderSkins.getRandomSkin((LOTRRandomSkinEntity)entity);
    }
    
    @Override
    public void doRender(final EntityLiving entity, final double d, double d1, final double d2, final float f, final float f1) {
        if (Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindScreenshot.getKeyCode())) {
            return;
        }
        if (LOTRTickHandlerClient.scrapTraderMisbehaveTick > 0) {
            for (int r = 3, i = -r; i <= r; ++i) {
                for (int k = -r; k <= r; ++k) {
                    if (Math.abs(i) + Math.abs(k) > 2) {
                        GL11.glPushMatrix();
                        GL11.glScalef(1.0f, 3.0f, 1.0f);
                        final double g = 6.0;
                        super.doRender(entity, i * g, 0.0, k * g, f, f1);
                        GL11.glPopMatrix();
                    }
                }
            }
            GL11.glPushMatrix();
            final float s = 6.0f;
            GL11.glScalef(1.0f, s, 1.0f);
            GL11.glColor3f(0.0f, 0.0f, 0.0f);
            d1 /= s;
            super.doRender(entity, d, d1, d2, f, f1);
            GL11.glPopMatrix();
            return;
        }
        super.doRender(entity, d, d1, d2, f, f1);
    }
    
    @Override
    protected void preRenderCallback(final EntityLivingBase entity, final float f) {
        super.preRenderCallback(entity, f);
        final float fadeout = ((LOTREntityScrapTrader)entity).getFadeoutProgress(f);
        if (fadeout > 0.0f) {
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(3008);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f - fadeout);
        }
    }
}
