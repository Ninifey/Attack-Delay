// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.item.ItemStack;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.item.ItemDye;
import net.minecraft.client.renderer.texture.IIconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;

public class LOTRBlockStainedGlass extends LOTRBlockGlass
{
    private IIcon[] glassIcons;
    
    public LOTRBlockStainedGlass() {
        this.glassIcons = new IIcon[16];
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        return this.glassIcons[j % this.glassIcons.length];
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(final IIconRegister iconregister) {
        for (int i = 0; i < this.glassIcons.length; ++i) {
            this.glassIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + ItemDye.field_150921_b[BlockStainedGlass.func_149997_b(i)]);
        }
    }
    
    public int damageDropped(final int i) {
        return i;
    }
    
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass() {
        return 1;
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(final Item item, final CreativeTabs tab, final List list) {
        for (int i = 0; i < this.glassIcons.length; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }
}
