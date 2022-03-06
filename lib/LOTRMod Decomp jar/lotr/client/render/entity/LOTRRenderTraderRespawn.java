// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.util.IIcon;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import lotr.common.entity.item.LOTREntityTraderRespawn;
import lotr.common.LOTRMod;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.client.renderer.entity.Render;

public class LOTRRenderTraderRespawn extends Render
{
    private ItemStack theItemStack;
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return TextureMap.locationItemsTexture;
    }
    
    public void doRender(final Entity entity, final double d, final double d1, final double d2, final float f, final float f1) {
        if (this.theItemStack == null) {
            this.theItemStack = new ItemStack(LOTRMod.silverCoin);
        }
        final LOTREntityTraderRespawn traderRespawn = (LOTREntityTraderRespawn)entity;
        this.bindEntityTexture((Entity)traderRespawn);
        GL11.glPushMatrix();
        GL11.glEnable(32826);
        GL11.glTranslatef((float)d, (float)d1, (float)d2);
        final float rotation = this.interpolateRotation(traderRespawn.prevSpawnerSpin, traderRespawn.spawnerSpin, f1);
        final float scale = traderRespawn.getScaleFloat(f1);
        GL11.glRotatef(rotation, 0.0f, 1.0f, 0.0f);
        GL11.glTranslatef(-0.5f * scale, traderRespawn.getBobbingOffset(f1), 0.03125f * scale);
        GL11.glScalef(scale, scale, scale);
        IIcon icon = this.theItemStack.getIconIndex();
        if (icon == null) {
            icon = (IIcon)((TextureMap)Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.locationItemsTexture)).getAtlasSprite("missingno");
        }
        final Tessellator tessellator = Tessellator.instance;
        final float f2 = icon.getMinU();
        final float f3 = icon.getMaxU();
        final float f4 = icon.getMinV();
        final float f5 = icon.getMaxV();
        ItemRenderer.renderItemIn2D(tessellator, f3, f4, f2, f5, icon.getIconWidth(), icon.getIconHeight(), 0.0625f);
        GL11.glDisable(32826);
        GL11.glPopMatrix();
    }
    
    private float interpolateRotation(final float prevRotation, final float newRotation, final float tick) {
        float interval;
        for (interval = newRotation - prevRotation; interval < -180.0f; interval += 360.0f) {}
        while (interval >= 180.0f) {
            interval -= 360.0f;
        }
        return prevRotation + tick * interval;
    }
}
