// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.Potion;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import lotr.common.fac.LOTRFaction;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.item.ItemFood;

public class LOTRItemManFlesh extends ItemFood
{
    public LOTRItemManFlesh(final int i, final float f, final boolean flag) {
        super(i, f, flag);
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabFood);
    }
    
    public static List<LOTRFaction> getManFleshFactions() {
        return LOTRFaction.getAllOfType(LOTRFaction.FactionType.TYPE_ORC, LOTRFaction.FactionType.TYPE_TROLL);
    }
    
    public ItemStack onEaten(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        --itemstack.stackSize;
        boolean orcAligned = false;
        for (final LOTRFaction faction : getManFleshFactions()) {
            final float alignment = LOTRLevelData.getData(entityplayer).getAlignment(faction);
            if (alignment > 0.0f) {
                orcAligned = true;
                break;
            }
        }
        if (orcAligned) {
            entityplayer.getFoodStats().func_151686_a((ItemFood)this, itemstack);
            if (!world.isClient) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.eatManFlesh);
            }
        }
        else if (!world.isClient) {
            final int dur = 30;
            entityplayer.addPotionEffect(new PotionEffect(Potion.hunger.id, dur * 20));
        }
        world.playSoundAtEntity((Entity)entityplayer, "random.burp", 0.5f, world.rand.nextFloat() * 0.1f + 0.9f);
        this.onFoodEaten(itemstack, world, entityplayer);
        return itemstack;
    }
}
