// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import lotr.common.LOTRMod;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraft.init.Items;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.block.BlockColored;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.item.Item;

public class LOTRItemDye extends Item
{
    @SideOnly(Side.CLIENT)
    private IIcon[] dyeIcons;
    private String[] dyeNames;
    
    public LOTRItemDye() {
        this.dyeNames = new String[] { "elanor", "niphredil", "bluebell", "green", "charcoal", "brown" };
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab((CreativeTabs)LOTRCreativeTabs.tabMaterials);
    }
    
    public boolean itemInteractionForEntity(final ItemStack itemstack, final EntityPlayer entityplayer, final EntityLivingBase entityliving) {
        if (!(entityliving instanceof EntitySheep)) {
            return false;
        }
        final EntitySheep sheep = (EntitySheep)entityliving;
        final int dye = isItemDye(itemstack);
        if (dye == -1) {
            return false;
        }
        final int blockDye = BlockColored.func_150031_c(dye);
        if (!sheep.getSheared() && sheep.getFleeceColor() != blockDye) {
            sheep.setFleeceColor(blockDye);
            --itemstack.stackSize;
        }
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int i) {
        if (i >= this.dyeIcons.length) {
            i = 0;
        }
        return this.dyeIcons[i];
    }
    
    public String getUnlocalizedName(final ItemStack itemstack) {
        return super.getUnlocalizedName() + "." + itemstack.getItemDamage();
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconregister) {
        this.dyeIcons = new IIcon[this.dyeNames.length];
        for (int i = 0; i < this.dyeNames.length; ++i) {
            this.dyeIcons[i] = iconregister.registerIcon(this.getIconString() + "_" + this.dyeNames[i]);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubItems(final Item item, final CreativeTabs tab, final List list) {
        for (int i = 0; i < this.dyeNames.length; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }
    
    public static int isItemDye(final ItemStack itemstack) {
        if (itemstack.getItem() == Items.dye) {
            return itemstack.getItemDamage();
        }
        final int[] oreIDs2;
        final int[] oreIDs = oreIDs2 = OreDictionary.getOreIDs(itemstack);
        for (final int id : oreIDs2) {
            final String oreName = OreDictionary.getOreName(id);
            for (int j = 0; j <= 15; ++j) {
                final ItemStack dye = new ItemStack(Items.dye, 1, j);
                if (LOTRMod.isOreNameEqual(dye, oreName)) {
                    return j;
                }
            }
        }
        return -1;
    }
}
