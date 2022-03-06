// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import lotr.common.LOTRMod;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.tileentity.LOTRTileEntityDwarvenForge;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class LOTRBlockDwarvenForge extends LOTRBlockForgeBase
{
    public TileEntity createNewTileEntity(final World world, final int i) {
        return new LOTRTileEntityDwarvenForge();
    }
    
    public boolean onBlockActivated(final World world, final int i, final int j, final int k, final EntityPlayer entityplayer, final int side, final float f, final float f1, final float f2) {
        if (!world.isClient) {
            entityplayer.openGui((Object)LOTRMod.instance, 5, world, i, j, k);
        }
        return true;
    }
    
    @Override
    protected boolean useLargeSmoke() {
        return true;
    }
}
