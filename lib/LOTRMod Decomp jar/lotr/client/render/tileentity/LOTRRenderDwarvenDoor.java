// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.tileentity;

import net.minecraft.block.Block;
import lotr.client.render.LOTRRenderBlocks;
import lotr.common.block.LOTRBlockGateDwarvenIthildin;
import net.minecraft.client.renderer.texture.TextureMap;
import org.lwjgl.opengl.GL11;
import lotr.common.tileentity.LOTRTileEntityDwarvenDoor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class LOTRRenderDwarvenDoor extends TileEntitySpecialRenderer
{
    private RenderBlocks renderBlocks;
    
    public void func_147496_a(final World world) {
        this.renderBlocks = new RenderBlocks((IBlockAccess)world);
    }
    
    public void renderTileEntityAt(final TileEntity tileentity, final double d, final double d1, final double d2, final float f) {
        if (this.renderBlocks == null) {
            this.renderBlocks = new RenderBlocks((IBlockAccess)tileentity.getWorldObj());
        }
        final LOTRTileEntityDwarvenDoor door = (LOTRTileEntityDwarvenDoor)tileentity;
        GL11.glPushMatrix();
        GL11.glEnable(32826);
        GL11.glTranslatef((float)d, (float)d1, (float)d2);
        final float alphaFunc = LOTRRenderDwarvenGlow.setupGlow(door.getGlowBrightness(f));
        this.bindTexture(TextureMap.locationBlocksTexture);
        final Block block = door.getBlockType();
        if (block instanceof LOTRBlockGateDwarvenIthildin) {
            LOTRRenderBlocks.renderDwarvenDoorGlow((LOTRBlockGateDwarvenIthildin)block, this.renderBlocks, door.xCoord, door.yCoord, door.zCoord);
        }
        LOTRRenderDwarvenGlow.endGlow(alphaFunc);
        GL11.glDisable(32826);
        GL11.glPopMatrix();
    }
}
