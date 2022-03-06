// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import java.util.ArrayList;
import java.util.Iterator;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.Potion;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import java.util.List;

public class LOTRItemBerry extends LOTRItemFood
{
    private static List<Item> allBerries;
    private boolean isPoisonous;
    
    public LOTRItemBerry() {
        super(2, 0.2f, false);
        this.isPoisonous = false;
        LOTRItemBerry.allBerries.add((Item)this);
    }
    
    public LOTRItemBerry setPoisonous() {
        this.isPoisonous = true;
        return this;
    }
    
    @Override
    public ItemStack onEaten(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        final ItemStack ret = super.onEaten(itemstack, world, entityplayer);
        if (this.isPoisonous && !world.isClient) {
            final int duration = 3 + world.rand.nextInt(4);
            final PotionEffect poison = new PotionEffect(Potion.poison.id, duration * 20);
            entityplayer.addPotionEffect(poison);
        }
        return ret;
    }
    
    public static void registerAllBerries(final String name) {
        for (final Item berry : LOTRItemBerry.allBerries) {
            OreDictionary.registerOre(name, berry);
        }
    }
    
    static {
        LOTRItemBerry.allBerries = new ArrayList<Item>();
    }
}
