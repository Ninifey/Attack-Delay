// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.item.EnumAction;
import net.minecraft.entity.Entity;
import lotr.common.LOTRAchievement;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.Potion;
import lotr.common.fac.LOTRFaction;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class LOTRItemMorgulShroom extends ItemBlock
{
    public LOTRItemMorgulShroom(final Block block) {
        super(block);
    }
    
    public ItemStack onEaten(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        --itemstack.stackSize;
        if (LOTRLevelData.getData(entityplayer).getAlignment(LOTRFaction.MORDOR) > 0.0f) {
            entityplayer.getFoodStats().addStats(4, 0.4f);
        }
        else if (!world.isClient) {
            entityplayer.addPotionEffect(new PotionEffect(Potion.poison.id, 80));
        }
        if (!world.isClient) {
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.eatMorgulShroom);
        }
        world.playSoundAtEntity((Entity)entityplayer, "random.burp", 0.5f, world.rand.nextFloat() * 0.1f + 0.9f);
        return itemstack;
    }
    
    public int getMaxItemUseDuration(final ItemStack itemstack) {
        return 32;
    }
    
    public EnumAction getItemUseAction(final ItemStack itemstack) {
        return EnumAction.eat;
    }
    
    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        if (entityplayer.canEat(false)) {
            entityplayer.setItemInUse(itemstack, this.getMaxItemUseDuration(itemstack));
        }
        return itemstack;
    }
}
