// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.entity.Entity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import lotr.common.block.LOTRBlockBed;
import net.minecraft.item.Item;

public class LOTRItemBed extends Item
{
    private LOTRBlockBed theBedBlock;
    
    public LOTRItemBed(final Block block) {
        this.setMaxStackSize(1);
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabUtil);
        this.theBedBlock = (LOTRBlockBed)block;
        this.theBedBlock.bedItem = this;
    }
    
    public boolean onItemUse(final ItemStack itemstack, final EntityPlayer entityplayer, final World world, final int i, int j, final int k, final int side, final float f, final float f1, final float f2) {
        if (world.isClient) {
            return true;
        }
        if (side != 1) {
            return false;
        }
        ++j;
        final int i2 = MathHelper.floor_double(((Entity)entityplayer).rotationYaw * 4.0f / 360.0f + 0.5) & 0x3;
        byte b0 = 0;
        byte b2 = 0;
        if (i2 == 0) {
            b2 = 1;
        }
        if (i2 == 1) {
            b0 = -1;
        }
        if (i2 == 2) {
            b2 = -1;
        }
        if (i2 == 3) {
            b0 = 1;
        }
        if (!entityplayer.canPlayerEdit(i, j, k, side, itemstack) || !entityplayer.canPlayerEdit(i + b0, j, k + b2, side, itemstack)) {
            return false;
        }
        if (world.isAirBlock(i, j, k) && world.isAirBlock(i + b0, j, k + b2) && world.getBlock(i, j - 1, k).isSideSolid((IBlockAccess)world, i, j - 1, k, ForgeDirection.UP) && world.getBlock(i + b0, j - 1, k + b2).isSideSolid((IBlockAccess)world, i + b0, j - 1, k + b2, ForgeDirection.UP)) {
            world.setBlock(i, j, k, (Block)this.theBedBlock, i2, 3);
            if (world.getBlock(i, j, k) == this.theBedBlock) {
                world.setBlock(i + b0, j, k + b2, (Block)this.theBedBlock, i2 + 8, 3);
            }
            world.playSoundEffect(i + 0.5, j + 0.5, k + 0.5, ((Block)this.theBedBlock).stepSound.func_150496_b(), (((Block)this.theBedBlock).stepSound.getVolume() + 1.0f) / 2.0f, ((Block)this.theBedBlock).stepSound.getFrequency() * 0.8f);
            --itemstack.stackSize;
            return true;
        }
        return false;
    }
}
