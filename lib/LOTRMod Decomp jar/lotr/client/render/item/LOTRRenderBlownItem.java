// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.item;

import net.minecraft.util.IIcon;
import net.minecraft.item.Item;
import net.minecraft.client.renderer.ItemRenderer;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class LOTRRenderBlownItem implements IItemRenderer
{
    public boolean handleRenderType(final ItemStack itemstack, final IItemRenderer.ItemRenderType type) {
        return type == IItemRenderer.ItemRenderType.EQUIPPED;
    }
    
    public boolean shouldUseRenderHelper(final IItemRenderer.ItemRenderType type, final ItemStack itemstack, final IItemRenderer.ItemRendererHelper helper) {
        return false;
    }
    
    public void renderItem(final IItemRenderer.ItemRenderType type, final ItemStack itemstack, final Object... data) {
        final EntityLivingBase equipper = (EntityLivingBase)data[1];
        final Item item = itemstack.getItem();
        final Tessellator tessellator = Tessellator.instance;
        if (Minecraft.getMinecraft().gameSettings.thirdPersonView != 0 || equipper != Minecraft.getMinecraft().thePlayer) {
            GL11.glScalef(-1.0f, 1.0f, 1.0f);
            GL11.glTranslatef(-1.35f, 0.0f, 0.0f);
            GL11.glRotatef(-45.0f, 0.0f, 0.0f, 1.0f);
            GL11.glTranslatef(0.0f, 0.3f, 0.0f);
            GL11.glTranslatef(0.0f, 0.0f, 0.15f);
        }
        for (int passes = item.getRenderPasses(itemstack.getItemDamage()), pass = 0; pass < passes; ++pass) {
            final int color = item.getColorFromItemStack(itemstack, pass);
            final float r = (color >> 16 & 0xFF) / 255.0f;
            final float g = (color >> 8 & 0xFF) / 255.0f;
            final float b = (color & 0xFF) / 255.0f;
            GL11.glColor3f(r, g, b);
            final IIcon icon = equipper.getItemIcon(itemstack, pass);
            final float f = icon.getMinU();
            final float f2 = icon.getMaxU();
            final float f3 = icon.getMinV();
            final float f4 = icon.getMaxV();
            ItemRenderer.renderItemIn2D(tessellator, f2, f3, f, f4, icon.getIconWidth(), icon.getIconHeight(), 0.0625f);
        }
        GL11.glColor3f(1.0f, 1.0f, 1.0f);
        GL11.glDisable(32826);
    }
}
