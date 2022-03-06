// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDoor;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class LOTRItemDoor extends ItemBlock
{
    public LOTRItemDoor(final Block block) {
        super(block);
        ((Item)this).maxStackSize = 1;
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabDeco);
    }
    
    public boolean onItemUse(final ItemStack itemstack, final EntityPlayer entityplayer, final World world, final int i, int j, final int k, final int side, final float f, final float f1, final float f2) {
        if (side != 1) {
            return false;
        }
        ++j;
        final Block doorBlock = super.field_150939_a;
        if (!entityplayer.canPlayerEdit(i, j, k, side, itemstack) || !entityplayer.canPlayerEdit(i, j + 1, k, side, itemstack)) {
            return false;
        }
        if (!doorBlock.canPlaceBlockAt(world, i, j, k)) {
            return false;
        }
        final int doorMeta = MathHelper.floor_double((((Entity)entityplayer).rotationYaw + 180.0f) * 4.0f / 360.0f - 0.5) & 0x3;
        ItemDoor.func_150924_a(world, i, j, k, doorMeta, doorBlock);
        --itemstack.stackSize;
        return true;
    }
}
