// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.entity.player.EntityPlayerMP;
import lotr.common.tileentity.LOTRTileEntitySign;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Facing;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class LOTRItemChisel extends Item
{
    private Block signBlock;
    
    public LOTRItemChisel(final Block block) {
        this.signBlock = block;
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabTools);
        this.setMaxStackSize(1);
        this.setMaxDamage(100);
        this.setFull3D();
    }
    
    public boolean onItemUse(final ItemStack itemstack, final EntityPlayer entityplayer, final World world, int i, int j, int k, final int side, final float f, final float f1, final float f2) {
        if (side == 0 || side == 1) {
            return false;
        }
        final Block block = world.getBlock(i, j, k);
        final Material mt = block.getMaterial();
        if (!block.isOpaqueCube() || (mt != Material.rock && mt != Material.wood && mt != Material.iron)) {
            return false;
        }
        i += Facing.offsetsXForSide[side];
        j += Facing.offsetsYForSide[side];
        k += Facing.offsetsZForSide[side];
        if (!entityplayer.canPlayerEdit(i, j, k, side, itemstack)) {
            return false;
        }
        if (!this.signBlock.canPlaceBlockAt(world, i, j, k)) {
            return false;
        }
        if (!world.isClient) {
            world.setBlock(i, j, k, this.signBlock, side, 3);
            itemstack.damageItem(1, (EntityLivingBase)entityplayer);
            final LOTRTileEntitySign sign = (LOTRTileEntitySign)world.getTileEntity(i, j, k);
            if (sign != null) {
                sign.openEditGUI((EntityPlayerMP)entityplayer);
            }
        }
        return true;
    }
}
