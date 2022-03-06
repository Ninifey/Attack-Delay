// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;

public class LOTRItemHangingFruit extends LOTRItemFood
{
    private Block fruitBlock;
    
    public LOTRItemHangingFruit(final int i, final float f, final boolean flag, final Block block) {
        super(i, f, flag);
        this.fruitBlock = block;
    }
    
    public boolean onItemUse(final ItemStack itemstack, final EntityPlayer entityplayer, final World world, int i, final int j, int k, final int side, final float hitX, final float hitY, final float hitZ) {
        if (!world.getBlock(i, j, k).isWood((IBlockAccess)world, i, j, k)) {
            return false;
        }
        if (side == 0 || side == 1) {
            return false;
        }
        if (side == 2) {
            --k;
        }
        if (side == 3) {
            ++k;
        }
        if (side == 4) {
            --i;
        }
        if (side == 5) {
            ++i;
        }
        if (world.isAirBlock(i, j, k)) {
            final int meta = ForgeDirection.getOrientation(side - 2).getOpposite().ordinal();
            world.setBlock(i, j, k, this.fruitBlock, meta, 3);
            if (!entityplayer.capabilities.isCreativeMode) {
                --itemstack.stackSize;
            }
        }
        return true;
    }
}
