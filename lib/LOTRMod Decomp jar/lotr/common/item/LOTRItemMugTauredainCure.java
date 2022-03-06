// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import lotr.common.LOTRReflection;
import net.minecraft.potion.Potion;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;

public class LOTRItemMugTauredainCure extends LOTRItemMug
{
    public LOTRItemMugTauredainCure() {
        super(true, false);
    }
    
    @Override
    public ItemStack onEaten(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        final ItemStack result = super.onEaten(itemstack, world, entityplayer);
        if (!world.isClient) {
            for (int i = 0; i < Potion.potionTypes.length; ++i) {
                final Potion potion = Potion.potionTypes[i];
                if (potion != null && LOTRReflection.isBadEffect(potion)) {
                    entityplayer.removePotionEffect(potion.id);
                }
            }
        }
        return result;
    }
}
