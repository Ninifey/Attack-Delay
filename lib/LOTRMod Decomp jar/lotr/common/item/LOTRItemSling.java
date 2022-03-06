// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.init.Items;
import lotr.common.entity.projectile.LOTREntityPebble;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.LOTRMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.item.Item;

public class LOTRItemSling extends Item
{
    public LOTRItemSling() {
        this.setMaxStackSize(1);
        this.setMaxDamage(250);
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabCombat);
    }
    
    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        if (entityplayer.inventory.hasItem(LOTRMod.pebble) || entityplayer.capabilities.isCreativeMode) {
            itemstack.damageItem(1, (EntityLivingBase)entityplayer);
            if (!entityplayer.capabilities.isCreativeMode) {
                entityplayer.inventory.consumeInventoryItem(LOTRMod.pebble);
            }
            world.playSoundAtEntity((Entity)entityplayer, "random.bow", 0.5f, 0.4f / (Item.itemRand.nextFloat() * 0.4f + 0.8f));
            if (!world.isClient) {
                world.spawnEntityInWorld((Entity)new LOTREntityPebble(world, (EntityLivingBase)entityplayer).setSling());
            }
        }
        return itemstack;
    }
    
    public boolean getIsRepairable(final ItemStack itemstack, final ItemStack repairItem) {
        return repairItem.getItem() == Items.leather || super.getIsRepairable(itemstack, repairItem);
    }
}
