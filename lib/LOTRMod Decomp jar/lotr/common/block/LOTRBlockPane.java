// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockPane;

public class LOTRBlockPane extends BlockPane
{
    public LOTRBlockPane(final String s, final String s1, final Material material, final boolean flag) {
        super(s, s1, material, flag);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabDeco);
    }
}
