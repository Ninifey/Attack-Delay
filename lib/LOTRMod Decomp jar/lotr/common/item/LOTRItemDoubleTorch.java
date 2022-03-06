// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.entity.Entity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import lotr.common.block.LOTRBlockDoubleTorch;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class LOTRItemDoubleTorch extends Item
{
    private Block torchBlock;
    
    public LOTRItemDoubleTorch(final Block block) {
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabDeco);
        this.torchBlock = block;
        ((LOTRBlockDoubleTorch)this.torchBlock).torchItem = this;
    }
    
    public boolean onItemUse(final ItemStack itemstack, final EntityPlayer entityplayer, final World world, int i, int j, int k, int side, final float f, final float f1, final float f2) {
        final Block block = world.getBlock(i, j, k);
        if (block == Blocks.snow_layer) {
            side = 1;
        }
        else if (!block.isReplaceable((IBlockAccess)world, i, j, k)) {
            if (side == 0) {
                --j;
            }
            if (side == 1) {
                ++j;
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
        }
        if (!entityplayer.canPlayerEdit(i, j, k, side, itemstack) || !entityplayer.canPlayerEdit(i, j + 1, k, side, itemstack)) {
            return false;
        }
        if (!world.canPlaceEntityOnSide(block, i, j, k, false, side, (Entity)null, itemstack) || !world.canPlaceEntityOnSide(block, i, j + 1, k, false, side, (Entity)null, itemstack)) {
            return false;
        }
        if (!LOTRBlockDoubleTorch.canPlaceTorchOn(world, i, j - 1, k)) {
            return false;
        }
        if (!world.isClient) {
            world.setBlock(i, j, k, this.torchBlock, 0, 2);
            world.setBlock(i, j + 1, k, this.torchBlock, 1, 2);
            final Block.SoundType stepSound = this.torchBlock.stepSound;
            world.playSoundEffect(i + 0.5, j + 0.5, k + 0.5, stepSound.func_150496_b(), (stepSound.getVolume() + 1.0f) / 2.0f, stepSound.getFrequency() * 0.8f);
            --itemstack.stackSize;
        }
        return true;
    }
}
