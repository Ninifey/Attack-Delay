// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.item;

import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class LOTRRenderInvTableCommand implements IItemRenderer
{
    public boolean handleRenderType(final ItemStack itemstack, final IItemRenderer.ItemRenderType type) {
        return type == IItemRenderer.ItemRenderType.INVENTORY;
    }
    
    public boolean shouldUseRenderHelper(final IItemRenderer.ItemRenderType type, final ItemStack itemstack, final IItemRenderer.ItemRendererHelper helper) {
        return true;
    }
    
    public void renderItem(final IItemRenderer.ItemRenderType type, final ItemStack itemstack, final Object... data) {
        final Block block = Block.getBlockFromItem(itemstack.getItem());
        final int meta = itemstack.getItemDamage();
        final RenderBlocks rb = (RenderBlocks)data[0];
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0f, -0.18f, 0.0f);
        final float scale = 0.6f;
        GL11.glScalef(scale, scale, scale);
        rb.renderBlockAsItem(block, meta, 1.0f);
        GL11.glPopMatrix();
    }
}
