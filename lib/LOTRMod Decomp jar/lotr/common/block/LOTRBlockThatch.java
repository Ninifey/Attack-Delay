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

public class LOTRBlockThatch extends Block
{
    @SideOnly(Side.CLIENT)
    private IIcon[] thatchIcons;
    private static String[] thatchNames;
    
    public LOTRBlockThatch() {
        super(Material.grass);
        this.setHardness(0.5f);
        this.setStepSound(Block.soundTypeGrass);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabBlock);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, int j) {
        if (j >= LOTRBlockThatch.thatchNames.length) {
            j = 0;
        }
        return this.thatchIcons[j];
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        this.thatchIcons = new IIcon[LOTRBlockThatch.thatchNames.length];
        for (int i = 0; i < LOTRBlockThatch.thatchNames.length; ++i) {
            this.thatchIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + LOTRBlockThatch.thatchNames[i]);
        }
    }
    
    public int damageDropped(final int i) {
        return i;
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(final Item item, final CreativeTabs tab, final List list) {
        for (int i = 0; i < LOTRBlockThatch.thatchNames.length; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }
    
    static {
        LOTRBlockThatch.thatchNames = new String[] { "thatch", "reed" };
    }
}
