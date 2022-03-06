// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemPotion;

public class LOTRItemPotion extends ItemPotion
{
    public boolean onItemUse(final ItemStack itemstack, final EntityPlayer entityplayer, final World world, final int i, final int j, final int k, final int side, final float f, final float f1, final float f2) {
        return LOTRItemMug.tryPlaceMug(itemstack, entityplayer, world, i, j, k, side);
    }
}
