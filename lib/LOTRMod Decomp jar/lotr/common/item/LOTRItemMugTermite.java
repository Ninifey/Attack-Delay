// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.StatCollector;
import net.minecraft.util.EnumChatFormatting;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;

public class LOTRItemMugTermite extends LOTRItemMug
{
    public LOTRItemMugTermite(final float f) {
        super(f);
    }
    
    @Override
    public ItemStack onEaten(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        final ItemStack result = super.onEaten(itemstack, world, entityplayer);
        if (!world.isClient && world.rand.nextInt(6) == 0) {
            world.createExplosion((Entity)null, ((Entity)entityplayer).posX, ((Entity)entityplayer).posY, ((Entity)entityplayer).posZ, 3.0f, false);
        }
        return result;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(final ItemStack itemstack, final EntityPlayer entityplayer, final List list, final boolean flag) {
        super.addInformation(itemstack, entityplayer, list, flag);
        list.add(EnumChatFormatting.DARK_GRAY + StatCollector.translateToLocal("item.lotr.drink.explode"));
    }
}
