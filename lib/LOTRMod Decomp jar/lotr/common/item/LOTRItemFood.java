// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.item.ItemFood;

public class LOTRItemFood extends ItemFood
{
    public LOTRItemFood(final int healAmount, final float saturation, final boolean canWolfEat) {
        super(healAmount, saturation, canWolfEat);
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabFood);
    }
    
    public ItemStack onEaten(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        if (!world.isClient && this == LOTRMod.maggotyBread) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.eatMaggotyBread);
        }
        return super.onEaten(itemstack, world, entityplayer);
    }
}
