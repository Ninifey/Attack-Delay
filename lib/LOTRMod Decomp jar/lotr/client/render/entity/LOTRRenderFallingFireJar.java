// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.world.IBlockAccess;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;
import lotr.common.block.LOTRBlockRhunFireJar;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderFallingBlock;

public class LOTRRenderFallingFireJar extends RenderFallingBlock
{
    private static RenderBlocks renderBlocks;
    
    public void doRender(final Entity entity, final double d, final double d1, final double d2, final float f, final float f1) {
        final EntityFallingBlock falling = (EntityFallingBlock)entity;
        final World world = falling.func_145807_e();
        final Block block = falling.func_145805_f();
        final int i = MathHelper.floor_double(((Entity)falling).posX);
        final int j = MathHelper.floor_double(((Entity)falling).posY);
        final int k = MathHelper.floor_double(((Entity)falling).posZ);
        if (block instanceof LOTRBlockRhunFireJar) {
            if (block != world.getBlock(i, j, k)) {
                GL11.glPushMatrix();
                GL11.glTranslatef((float)d, (float)d1, (float)d2);
                this.bindEntityTexture(entity);
                GL11.glDisable(2896);
                LOTRRenderFallingFireJar.renderBlocks.blockAccess = (IBlockAccess)world;
                final Tessellator tessellator = Tessellator.instance;
                tessellator.startDrawingQuads();
                tessellator.setTranslation((double)(-i - 0.5f), (double)(-j - 0.5f), (double)(-k - 0.5f));
                LOTRRenderFallingFireJar.renderBlocks.renderBlockByRenderType(block, i, j, k);
                tessellator.setTranslation(0.0, 0.0, 0.0);
                tessellator.draw();
                GL11.glEnable(2896);
                GL11.glPopMatrix();
            }
        }
        else {
            super.doRender(entity, d, d1, d2, f, f1);
        }
    }
    
    static {
        LOTRRenderFallingFireJar.renderBlocks = new RenderBlocks();
    }
}
