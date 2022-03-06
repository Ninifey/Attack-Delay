// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.entity.Entity;
import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import lotr.common.LOTRMod;
import net.minecraft.util.Facing;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.item.Item;

public class LOTRItemArmorStand extends Item
{
    public LOTRItemArmorStand() {
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabDeco);
        this.setMaxStackSize(1);
    }
    
    public boolean onItemUse(final ItemStack itemstack, final EntityPlayer entityplayer, final World world, int i, int j, int k, final int side, final float f, final float f1, final float f2) {
        if (world.isClient) {
            return true;
        }
        i += Facing.offsetsXForSide[side];
        j += Facing.offsetsYForSide[side];
        k += Facing.offsetsZForSide[side];
        final Block block = LOTRMod.armorStand;
        final Block block2 = world.getBlock(i, j, k);
        final Block block3 = world.getBlock(i, j + 1, k);
        if ((block2 != null && !block2.isReplaceable((IBlockAccess)world, i, j, k)) || (block3 != null && !block3.isReplaceable((IBlockAccess)world, i, j + 1, k))) {
            return false;
        }
        if (!entityplayer.canPlayerEdit(i, j, k, side, itemstack) || !entityplayer.canPlayerEdit(i, j + 1, k, side, itemstack)) {
            return false;
        }
        if (!block.canPlaceBlockAt(world, i, j, k)) {
            return false;
        }
        final int l = MathHelper.floor_double(((Entity)entityplayer).rotationYaw * 4.0f / 360.0f + 0.5) & 0x3;
        world.setBlock(i, j, k, block, l, 3);
        world.setBlock(i, j + 1, k, block, l | 0x4, 3);
        world.playSoundEffect(i + 0.5, j + 0.5, k + 0.5, block.stepSound.func_150496_b(), (block.stepSound.getVolume() + 1.0f) / 2.0f, block.stepSound.getFrequency() * 0.8f);
        --itemstack.stackSize;
        return true;
    }
}
