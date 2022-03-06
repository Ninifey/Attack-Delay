// 
// Decompiled by Procyon v0.5.36
// 

package lotr.client.render.tileentity;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.block.ITileEntityProvider;
import lotr.common.block.LOTRBlockSpawnerChest;
import lotr.common.tileentity.LOTRTileEntitySpawnerChest;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class LOTRRenderSpawnerChest extends TileEntitySpecialRenderer
{
    public void renderTileEntityAt(final TileEntity tileentity, final double d, final double d1, final double d2, final float f) {
        final LOTRTileEntitySpawnerChest chest = (LOTRTileEntitySpawnerChest)tileentity;
        final Block block = chest.getBlockType();
        if (block instanceof LOTRBlockSpawnerChest) {
            final LOTRBlockSpawnerChest scBlock = (LOTRBlockSpawnerChest)block;
            final Block model = scBlock.chestModel;
            if (model instanceof ITileEntityProvider) {
                final ITileEntityProvider itep = (ITileEntityProvider)model;
                final TileEntity modelTE = itep.createNewTileEntity(chest.getWorldObj(), 0);
                modelTE.setWorldObj(chest.getWorldObj());
                modelTE.xCoord = ((TileEntity)chest).xCoord;
                modelTE.yCoord = ((TileEntity)chest).yCoord;
                modelTE.zCoord = ((TileEntity)chest).zCoord;
                TileEntityRendererDispatcher.instance.getSpecialRenderer(modelTE).renderTileEntityAt(modelTE, d, d1, d2, f);
            }
        }
    }
}
