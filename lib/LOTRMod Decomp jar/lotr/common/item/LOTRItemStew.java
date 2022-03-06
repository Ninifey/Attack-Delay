// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.item.ItemFood;

public class LOTRItemStew extends ItemFood
{
    public LOTRItemStew(final int j, final float f, final boolean flag) {
        super(j, f, flag);
        this.setMaxStackSize(1);
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabFood);
        this.setContainerItem(Items.bowl);
    }
    
    public ItemStack onEaten(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        super.onEaten(itemstack, world, entityplayer);
        return new ItemStack(Items.bowl);
    }
}
