// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.item;

import net.minecraft.entity.Entity;
import net.minecraft.util.IIcon;
import java.util.List;
import net.minecraft.item.Item;
import lotr.common.LOTRConfig;
import lotr.client.LOTRClientProxy;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.RenderBlocks;
import lotr.common.item.LOTRItemSword;
import org.lwjgl.opengl.GL11;
import lotr.common.entity.npc.LOTREntityOrc;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class LOTRRenderElvenBlade implements IItemRenderer
{
    private double distance;
    private LOTRRenderLargeItem largeItemRenderer;
    
    public LOTRRenderElvenBlade(final double d, final LOTRRenderLargeItem large) {
        this.distance = d;
        this.largeItemRenderer = large;
    }
    
    public boolean handleRenderType(final ItemStack itemstack, final IItemRenderer.ItemRenderType type) {
        return type == IItemRenderer.ItemRenderType.EQUIPPED || type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON;
    }
    
    public boolean shouldUseRenderHelper(final IItemRenderer.ItemRenderType type, final ItemStack itemstack, final IItemRenderer.ItemRendererHelper helper) {
        return false;
    }
    
    public void renderItem(final IItemRenderer.ItemRenderType type, final ItemStack itemstack, final Object... data) {
        final EntityLivingBase entity = (EntityLivingBase)data[1];
        final Item item = itemstack.getItem();
        ((Entity)entity).worldObj.theProfiler.startSection("elvenBlade");
        boolean glows = false;
        final List orcs = ((Entity)entity).worldObj.getEntitiesWithinAABB((Class)LOTREntityOrc.class, ((Entity)entity).boundingBox.expand(this.distance, this.distance, this.distance));
        if (!orcs.isEmpty()) {
            glows = true;
        }
        if (glows) {
            GL11.glDisable(2896);
        }
        if (this.largeItemRenderer != null) {
            if (glows) {
                this.largeItemRenderer.renderLargeItem("glowing");
            }
            else {
                this.largeItemRenderer.renderLargeItem();
            }
        }
        else {
            IIcon icon = ((EntityLivingBase)data[1]).getItemIcon(itemstack, 0);
            if (glows) {
                icon = ((LOTRItemSword)item).glowingIcon;
            }
            icon = RenderBlocks.getInstance().getIconSafe(icon);
            final float minU = icon.getMinU();
            final float maxU = icon.getMaxU();
            final float minV = icon.getMinV();
            final float maxV = icon.getMaxV();
            final int width = icon.getIconWidth();
            final int height = icon.getIconWidth();
            final Tessellator tessellator = Tessellator.instance;
            ItemRenderer.renderItemIn2D(tessellator, maxU, minV, minU, maxV, width, height, 0.0625f);
        }
        if (itemstack != null && itemstack.hasEffect(0)) {
            LOTRClientProxy.renderEnchantmentEffect();
        }
        if (glows) {
            GL11.glEnable(2896);
            if (LOTRConfig.elvenBladeGlow) {
                for (int i = 0; i < 4; ++i) {
                    LOTRClientProxy.renderEnchantmentEffect();
                }
            }
        }
        GL11.glDisable(32826);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        ((Entity)entity).worldObj.theProfiler.endSection();
    }
}
