// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.entity;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import lotr.common.block.LOTRBlockTreasurePile;
import org.lwjgl.opengl.GL11;
import net.minecraft.util.MathHelper;
import lotr.common.entity.item.LOTREntityFallingTreasure;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;

public class LOTRRenderFallingCoinPile extends Render
{
    private static final RenderBlocks blockRenderer;
    
    public LOTRRenderFallingCoinPile() {
        super.shadowSize = 0.5f;
    }
    
    public void doRender(final Entity entity, final double d, final double d1, final double d2, final float f, final float f1) {
        final LOTREntityFallingTreasure fallingCoin = (LOTREntityFallingTreasure)entity;
        final World world = fallingCoin.worldObj;
        final Block block = fallingCoin.theBlock;
        final int meta = fallingCoin.theBlockMeta;
        final int i = MathHelper.floor_double(fallingCoin.posX);
        final int j = MathHelper.floor_double(fallingCoin.posY);
        final int k = MathHelper.floor_double(fallingCoin.posZ);
        if (block != null && block != world.getBlock(i, j, k)) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float)d, (float)d1, (float)d2);
            this.bindEntityTexture((Entity)fallingCoin);
            GL11.glDisable(2896);
            LOTRBlockTreasurePile.setTreasureBlockBounds(block, meta);
            LOTRRenderFallingCoinPile.blockRenderer.setRenderBoundsFromBlock(block);
            LOTRRenderFallingCoinPile.blockRenderer.renderBlockSandFalling(block, world, i, j, k, meta);
            GL11.glEnable(2896);
            GL11.glPopMatrix();
        }
    }
    
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return TextureMap.locationBlocksTexture;
    }
    
    static {
        blockRenderer = new RenderBlocks();
    }
}
