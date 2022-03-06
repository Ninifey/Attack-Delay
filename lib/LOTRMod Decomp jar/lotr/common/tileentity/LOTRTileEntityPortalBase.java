// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.AxisAlignedBB;
import lotr.common.block.LOTRBlockPortal;
import net.minecraft.tileentity.TileEntity;

public abstract class LOTRTileEntityPortalBase extends TileEntity
{
    public void updateEntity() {
        if (!super.worldObj.isClient) {
            final LOTRBlockPortal portalBlock = (LOTRBlockPortal)this.getBlockType();
            for (int i1 = super.xCoord - 1; i1 <= super.xCoord + 1; ++i1) {
                for (int k1 = super.zCoord - 1; k1 <= super.zCoord + 1; ++k1) {
                    if (portalBlock.isValidPortalLocation(super.worldObj, i1, super.yCoord, k1, true)) {
                        return;
                    }
                }
            }
            super.worldObj.setBlockToAir(super.xCoord, super.yCoord, super.zCoord);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return AxisAlignedBB.getBoundingBox((double)(super.xCoord - 1), (double)super.yCoord, (double)(super.zCoord - 1), (double)(super.xCoord + 2), (double)(super.yCoord + 2), (double)(super.zCoord + 2));
    }
}
