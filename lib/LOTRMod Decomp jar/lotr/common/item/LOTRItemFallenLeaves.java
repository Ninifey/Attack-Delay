// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.item.ItemBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.util.StatCollector;
import lotr.common.block.LOTRBlockFallenLeaves;
import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;

public class LOTRItemFallenLeaves extends LOTRItemBlockMetadata
{
    public LOTRItemFallenLeaves(final Block block) {
        super(block);
    }
    
    public String getItemStackDisplayName(final ItemStack itemstack) {
        final Object[] obj = ((LOTRBlockFallenLeaves)super.field_150939_a).leafBlockMetaFromFallenMeta(itemstack.getItemDamage());
        final ItemStack leaves = new ItemStack((Block)obj[0], 1, (int)obj[1]);
        final String name = leaves.getDisplayName();
        return StatCollector.translateToLocalFormatted("tile.lotr.fallenLeaves", new Object[] { name });
    }
    
    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        return LOTRItemWaterPlant.tryPlaceWaterPlant(this, itemstack, world, entityplayer, this.getMovingObjectPositionFromPlayer(world, entityplayer, true));
    }
}
