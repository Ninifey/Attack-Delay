// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.item.ItemStack;
import java.util.List;
import net.minecraft.item.Item;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.block.Block;

public class LOTRBlockDirtPath extends Block
{
    @SideOnly(Side.CLIENT)
    protected IIcon[] pathIcons;
    protected String[] pathNames;
    
    public LOTRBlockDirtPath() {
        super(Material.ground);
        this.pathNames = new String[] { "dirt", "mud" };
        this.setHardness(0.5f);
        this.setStepSound(Block.soundTypeGravel);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabBlock);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, int j) {
        if (j >= this.pathNames.length) {
            j = 0;
        }
        return this.pathIcons[j];
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        this.pathIcons = new IIcon[this.pathNames.length];
        for (int i = 0; i < this.pathNames.length; ++i) {
            this.pathIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + this.pathNames[i]);
        }
    }
    
    public int damageDropped(final int i) {
        return i;
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(final Item item, final CreativeTabs tab, final List list) {
        for (int i = 0; i < this.pathNames.length; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }
}
