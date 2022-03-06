// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.inventory.IInventory;
import lotr.common.enchant.LOTREnchantmentHelper;
import lotr.common.enchant.LOTREnchantment;
import lotr.common.LOTRConfig;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.entity.Entity;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.item.Item;

public class LOTRItemAncientItem extends Item
{
    @SideOnly(Side.CLIENT)
    private IIcon[] icons;
    private String[] itemNames;
    
    public LOTRItemAncientItem() {
        this.itemNames = new String[] { "sword", "dagger", "helmet", "body", "legs", "boots" };
        this.setMaxStackSize(1);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabMisc);
    }
    
    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        if (!world.isClient) {
            final ItemStack ancientItem = getRandomItem(itemstack);
            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.craftAncientItem);
            world.playSoundAtEntity((Entity)entityplayer, "random.pop", 0.2f, ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7f + 1.0f) * 2.0f);
            return ancientItem;
        }
        return itemstack;
    }
    
    public static ItemStack getRandomItem(final ItemStack itemstack) {
        ItemStack randomItem = null;
        final IInventory randomItemInv = (IInventory)new InventoryBasic("ancientItem", true, 1);
        LOTRChestContents itemPool = null;
        if (itemstack.getItemDamage() == 0) {
            itemPool = LOTRChestContents.ANCIENT_SWORD;
        }
        else if (itemstack.getItemDamage() == 1) {
            itemPool = LOTRChestContents.ANCIENT_DAGGER;
        }
        else if (itemstack.getItemDamage() == 2) {
            itemPool = LOTRChestContents.ANCIENT_HELMET;
        }
        else if (itemstack.getItemDamage() == 3) {
            itemPool = LOTRChestContents.ANCIENT_BODY;
        }
        else if (itemstack.getItemDamage() == 4) {
            itemPool = LOTRChestContents.ANCIENT_LEGS;
        }
        else if (itemstack.getItemDamage() == 5) {
            itemPool = LOTRChestContents.ANCIENT_BOOTS;
        }
        LOTRChestContents.fillInventory(randomItemInv, Item.itemRand, itemPool, 1);
        randomItem = randomItemInv.getStackInSlot(0);
        if (randomItem != null) {
            if (LOTRConfig.enchantingLOTR) {
                final LOTREnchantment wraithbane = LOTREnchantment.baneWraith;
                if (wraithbane.canApply(randomItem, false) && Item.itemRand.nextInt(4) == 0) {
                    LOTREnchantmentHelper.setHasEnchant(randomItem, wraithbane);
                }
            }
            return randomItem;
        }
        return itemstack;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int i) {
        if (i >= this.icons.length) {
            i = 0;
        }
        return this.icons[i];
    }
    
    public String getUnlocalizedName(final ItemStack itemstack) {
        return super.getUnlocalizedName() + "." + itemstack.getItemDamage();
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconregister) {
        this.icons = new IIcon[this.itemNames.length];
        for (int i = 0; i < this.itemNames.length; ++i) {
            this.icons[i] = iconregister.registerIcon(this.getIconString() + "_" + this.itemNames[i]);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubItems(final Item item, final CreativeTabs tab, final List list) {
        for (int i = 0; i <= 5; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }
}
