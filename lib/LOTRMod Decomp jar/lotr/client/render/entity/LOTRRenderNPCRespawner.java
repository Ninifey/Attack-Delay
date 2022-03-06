// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import lotr.common.LOTRMod;
import org.lwjgl.opengl.GL11;
import lotr.common.entity.LOTREntityNPCRespawner;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.client.renderer.entity.Render;

public class LOTRRenderNPCRespawner extends Render
{
    private ItemStack renderIcon;
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return TextureMap.locationItemsTexture;
    }
    
    public void doRender(final Entity entity, final double d, final double d1, final double d2, final float f, final float f1) {
        if (!((EntityPlayer)Minecraft.getMinecraft().thePlayer).capabilities.isCreativeMode) {
            return;
        }
        final LOTREntityNPCRespawner spawner = (LOTREntityNPCRespawner)entity;
        GL11.glPushMatrix();
        GL11.glEnable(32826);
        GL11.glTranslatef((float)d, (float)d1, (float)d2);
        final float rotation = this.interpolateRotation(spawner.prevSpawnerSpin, spawner.spawnerSpin, f1);
        final float scale = 2.0f;
        GL11.glRotatef(rotation, 0.0f, 1.0f, 0.0f);
        GL11.glTranslatef(-0.5f * scale, -spawner.height / 2.0f, 0.03125f * scale);
        GL11.glScalef(scale, scale, scale);
        if (this.renderIcon == null) {
            this.renderIcon = new ItemStack(LOTRMod.npcRespawner);
        }
        IIcon icon = this.renderIcon.getIconIndex();
        if (icon == null) {
            icon = (IIcon)((TextureMap)Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.locationItemsTexture)).getAtlasSprite("missingno");
        }
        final Tessellator tessellator = Tessellator.instance;
        final float f2 = icon.getMinU();
        final float f3 = icon.getMaxU();
        final float f4 = icon.getMinV();
        final float f5 = icon.getMaxV();
        this.bindEntityTexture((Entity)spawner);
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
