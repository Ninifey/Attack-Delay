// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.init.Items;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.item.Item;

public class LOTRItemMatch extends Item
{
    public LOTRItemMatch() {
        this.setFull3D();
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabTools);
    }
    
    public boolean onItemUse(final ItemStack itemstack, final EntityPlayer entityplayer, final World world, final int i, final int j, final int k, final int side, final float f, final float f1, final float f2) {
        if (Items.flint_and_steel.onItemUse(new ItemStack(Items.flint_and_steel), entityplayer, world, i, j, k, side, f, f1, f2)) {
            --itemstack.stackSize;
            return true;
        }
        return false;
    }
}
