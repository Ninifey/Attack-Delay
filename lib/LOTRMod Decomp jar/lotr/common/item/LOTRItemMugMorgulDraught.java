// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import lotr.common.fac.LOTRFaction;
import lotr.common.LOTRLevelData;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.Potion;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;

public class LOTRItemMugMorgulDraught extends LOTRItemMug
{
    public LOTRItemMugMorgulDraught() {
        super(0.0f);
    }
    
    @Override
    public ItemStack onEaten(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        if (!this.shouldApplyPotionEffects(itemstack, entityplayer)) {
            final ItemStack result = super.onEaten(itemstack, world, entityplayer);
            if (!world.isClient) {
                entityplayer.addPotionEffect(new PotionEffect(Potion.poison.id, 100));
            }
            return result;
        }
        return super.onEaten(itemstack, world, entityplayer);
    }
    
    @Override
    protected boolean shouldApplyPotionEffects(final ItemStack itemstack, final EntityPlayer entityplayer) {
        return LOTRLevelData.getData(entityplayer).getAlignment(LOTRFaction.MORDOR) > 0.0f;
    }
}
