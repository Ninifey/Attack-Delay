// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.World;
import lotr.common.LOTRMod;
import net.minecraft.item.Item;
import java.util.Random;
import net.minecraft.world.IBlockAccess;

public class LOTRBlockDate extends LOTRBlockHangingFruit
{
    public void setBlockBoundsBasedOnState(final IBlockAccess world, final int i, final int j, final int k) {
        final int dir = world.getBlockMetadata(i, j, k);
        switch (dir) {
            case 0: {
                this.setBlockBounds(0.375f, 0.3125f, 0.0f, 0.625f, 0.6875f, 0.25f);
                break;
            }
            case 1: {
                this.setBlockBounds(0.375f, 0.3125f, 0.75f, 0.625f, 0.6875f, 1.0f);
                break;
            }
            case 2: {
                this.setBlockBounds(0.0f, 0.3125f, 0.375f, 0.25f, 0.6875f, 0.625f);
                break;
            }
            case 3: {
                this.setBlockBounds(0.75f, 0.3125f, 0.375f, 1.0f, 0.6875f, 0.625f);
                break;
            }
        }
    }
    
    public Item getItemDropped(final int i, final Random random, final int j) {
        return LOTRMod.date;
    }
    
    @SideOnly(Side.CLIENT)
    public Item getItem(final World world, final int i, final int j, final int k) {
        return LOTRMod.date;
    }
}
