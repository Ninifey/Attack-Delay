// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.item.ItemStack;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.item.ItemDye;
import net.minecraft.client.renderer.texture.IIconRegister;
import lotr.common.LOTRMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;

public class LOTRBlockStainedGlassPane extends LOTRBlockGlassPane
{
    private IIcon[] paneIcons;
    
    public LOTRBlockStainedGlassPane() {
        this.paneIcons = new IIcon[16];
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon func_149735_b(final int i, final int j) {
        return this.paneIcons[j % this.paneIcons.length];
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon func_150097_e() {
        return ((LOTRBlockPane)LOTRMod.glassPane).func_150097_e();
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        return this.func_149735_b(i, ~j & 0xF);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        for (int i = 0; i < this.paneIcons.length; ++i) {
            this.paneIcons[i] = iconregister.registerIcon("lotr:stainedGlass_" + ItemDye.field_150921_b[BlockStainedGlassPane.func_150103_c(i)]);
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
        for (int i = 0; i < this.paneIcons.length; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }
}
