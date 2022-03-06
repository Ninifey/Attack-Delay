// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.item.ItemStack;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import java.util.Random;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.block.BlockLog;

public abstract class LOTRBlockWoodBase extends BlockLog
{
    @SideOnly(Side.CLIENT)
    private IIcon[][] woodIcons;
    private String[] woodNames;
    
    public LOTRBlockWoodBase() {
        this.setHardness(2.0f);
        this.setStepSound(Block.soundTypeWood);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabBlock);
    }
    
    public void setWoodNames(final String... s) {
        this.woodNames = s;
    }
    
    public Item getItemDropped(final int i, final Random random, final int j) {
        return Item.getItemFromBlock((Block)this);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, int j) {
        final int j2 = j & 0xC;
        j &= 0x3;
        if (j >= this.woodNames.length) {
            j = 0;
        }
        if ((j2 == 0 && (i == 0 || i == 1)) || (j2 == 4 && (i == 4 || i == 5)) || (j2 == 8 && (i == 2 || i == 3))) {
            return this.woodIcons[j][0];
        }
        return this.woodIcons[j][1];
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        this.woodIcons = new IIcon[this.woodNames.length][2];
        for (int i = 0; i < this.woodNames.length; ++i) {
            this.woodIcons[i][0] = iconregister.registerIcon(this.getTextureName() + "_" + this.woodNames[i] + "_top");
            this.woodIcons[i][1] = iconregister.registerIcon(this.getTextureName() + "_" + this.woodNames[i] + "_side");
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(final Item item, final CreativeTabs tab, final List list) {
        for (int i = 0; i < this.woodNames.length; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }
}
