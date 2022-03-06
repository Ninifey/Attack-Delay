// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.entity.projectile.LOTREntityPebble;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import lotr.common.dispenser.LOTRDispensePebble;
import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.item.Item;

public class LOTRItemPebble extends Item
{
    public LOTRItemPebble() {
        this.setMaxStackSize(64);
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabCombat);
        BlockDispenser.dispenseBehaviorRegistry.putObject((Object)this, (Object)new LOTRDispensePebble());
    }
    
    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        if (!world.isClient) {
            world.spawnEntityInWorld((Entity)new LOTREntityPebble(world, (EntityLivingBase)entityplayer));
            world.playSoundAtEntity((Entity)entityplayer, "random.bow", 0.5f, 0.4f / (Item.itemRand.nextFloat() * 0.4f + 0.8f));
            if (!entityplayer.capabilities.isCreativeMode) {
                --itemstack.stackSize;
            }
        }
        return itemstack;
    }
}
