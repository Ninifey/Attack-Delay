// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.item;

import net.minecraft.client.renderer.texture.TextureManager;
import lotr.client.render.entity.LOTRRenderBanner;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;
import net.minecraft.item.ItemStack;
import lotr.client.model.LOTRModelBanner;
import net.minecraftforge.client.IItemRenderer;

public class LOTRRenderBannerItem implements IItemRenderer
{
    private static LOTRModelBanner model;
    
    public boolean handleRenderType(final ItemStack itemstack, final IItemRenderer.ItemRenderType type) {
        return type == IItemRenderer.ItemRenderType.EQUIPPED || type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON;
    }
    
    public boolean shouldUseRenderHelper(final IItemRenderer.ItemRenderType type, final ItemStack itemstack, final IItemRenderer.ItemRendererHelper helper) {
        return false;
    }
    
    public void renderItem(final IItemRenderer.ItemRenderType type, final ItemStack itemstack, final Object... data) {
        GL11.glDisable(2884);
        final Entity holder = (Entity)data[1];
        final boolean isFirstPerson = holder == Minecraft.getMinecraft().thePlayer && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0;
        final boolean renderStand = false;
        final TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
        if (isFirstPerson) {
            GL11.glTranslatef(1.0f, 1.0f, 0.0f);
            GL11.glScalef(-1.0f, 1.0f, 1.0f);
        }
        else {
            GL11.glTranslatef(-1.5f, 0.85f, -0.1f);
            GL11.glRotatef(75.0f, 0.0f, 0.0f, 1.0f);
        }
        GL11.glScalef(1.0f, -1.0f, 1.0f);
        final LOTRItemBanner.BannerType bannerType = LOTRItemBanner.getBannerType(itemstack);
        textureManager.bindTexture(LOTRRenderBanner.getStandTexture(bannerType));
        if (renderStand) {
            LOTRRenderBannerItem.model.renderStand(0.0625f);
        }
        LOTRRenderBannerItem.model.renderPost(0.0625f);
        LOTRRenderBannerItem.model.renderLowerPost(0.0625f);
        textureManager.bindTexture(LOTRRenderBanner.getBannerTexture(bannerType));
        LOTRRenderBannerItem.model.renderBanner(0.0625f);
    }
    
    static {
        LOTRRenderBannerItem.model = new LOTRModelBanner();
    }
}
