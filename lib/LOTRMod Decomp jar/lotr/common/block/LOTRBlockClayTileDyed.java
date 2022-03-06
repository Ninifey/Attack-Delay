// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.item.ItemStack;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.block.BlockColored;
import net.minecraft.client.renderer.texture.IIconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;

public class LOTRBlockClayTileDyed extends LOTRBlockClayTile
{
    @SideOnly(Side.CLIENT)
    private IIcon[] clayIcons;
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        this.clayIcons = new IIcon[16];
        for (int i = 0; i < this.clayIcons.length; ++i) {
            final int dyeMeta = BlockColored.func_150031_c(i);
            this.clayIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + ItemDye.field_150923_a[dyeMeta]);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, int j) {
        if (j >= 16) {
            j = 0;
        }
        return this.clayIcons[j];
    }
    
    public int damageDropped(final int i) {
        return i;
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(final Item item, final CreativeTabs tab, final List list) {
        for (int i = 0; i < 16; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }
}
