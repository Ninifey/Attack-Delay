// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;

public class LOTRItemKebab extends LOTRItemFood
{
    public LOTRItemKebab(final int healAmount, final float saturation, final boolean canWolfEat) {
        super(healAmount, saturation, canWolfEat);
    }
    
    @Override
    public ItemStack onEaten(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        if (!world.isClient && world.rand.nextInt(100) == 0) {
            entityplayer.addChatMessage((IChatComponent)new ChatComponentText("That was a good kebab. You feel a lot better."));
        }
        return super.onEaten(itemstack, world, entityplayer);
    }
}
