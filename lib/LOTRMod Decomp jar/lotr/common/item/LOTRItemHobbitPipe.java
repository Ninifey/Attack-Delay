// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.StatCollector;
import java.util.List;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.item.EnumAction;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.Entity;
import lotr.common.entity.projectile.LOTREntitySmokeRing;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.LOTRMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.item.Item;

public class LOTRItemHobbitPipe extends Item
{
    public static final int MAGIC_COLOR = 16;
    
    public LOTRItemHobbitPipe() {
        this.setMaxDamage(300);
        this.setMaxStackSize(1);
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabMisc);
    }
    
    public ItemStack onEaten(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        if (entityplayer.inventory.hasItem(LOTRMod.pipeweed) || entityplayer.capabilities.isCreativeMode) {
            itemstack.damageItem(1, (EntityLivingBase)entityplayer);
            if (!entityplayer.capabilities.isCreativeMode) {
                entityplayer.inventory.consumeInventoryItem(LOTRMod.pipeweed);
            }
            if (entityplayer.canEat(false)) {
                entityplayer.getFoodStats().addStats(2, 0.3f);
            }
            if (!world.isClient) {
                final LOTREntitySmokeRing smoke = new LOTREntitySmokeRing(world, (EntityLivingBase)entityplayer);
                final int color = getSmokeColor(itemstack);
                smoke.setSmokeColour(color);
                world.spawnEntityInWorld((Entity)smoke);
                if (color == 16) {
                    LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.useMagicPipe);
                }
            }
            world.playSoundAtEntity((Entity)entityplayer, "lotr:item.puff", 1.0f, (Item.itemRand.nextFloat() - Item.itemRand.nextFloat()) * 0.2f + 1.0f);
        }
        return itemstack;
    }
    
    public int getMaxItemUseDuration(final ItemStack itemstack) {
        return 40;
    }
    
    public EnumAction getItemUseAction(final ItemStack itemstack) {
        return EnumAction.bow;
    }
    
    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        if (entityplayer.inventory.hasItem(LOTRMod.pipeweed) || entityplayer.capabilities.isCreativeMode) {
            entityplayer.setItemInUse(itemstack, this.getMaxItemUseDuration(itemstack));
        }
        return itemstack;
    }
    
    public static int getSmokeColor(final ItemStack itemstack) {
        if (itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey("SmokeColour")) {
            return itemstack.getTagCompound().getInteger("SmokeColour");
        }
        return 0;
    }
    
    public static void setSmokeColor(final ItemStack itemstack, final int i) {
        if (itemstack.getTagCompound() == null) {
            itemstack.setTagCompound(new NBTTagCompound());
        }
        itemstack.getTagCompound().setInteger("SmokeColour", i);
    }
    
    public static boolean isPipeDyed(final ItemStack itemstack) {
        final int color = getSmokeColor(itemstack);
        return color != 0 && color != 16;
    }
    
    public static void removePipeDye(final ItemStack itemstack) {
        setSmokeColor(itemstack, 0);
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation(final ItemStack itemstack, final EntityPlayer entityplayer, final List list, final boolean flag) {
        final int color = getSmokeColor(itemstack);
        list.add(StatCollector.translateToLocal(this.getUnlocalizedName() + ".subtitle." + color));
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubItems(final Item item, final CreativeTabs tab, final List list) {
        for (int i = 0; i <= 16; ++i) {
            final ItemStack itemstack = new ItemStack((Item)this);
            setSmokeColor(itemstack, i);
            list.add(itemstack);
        }
    }
}
