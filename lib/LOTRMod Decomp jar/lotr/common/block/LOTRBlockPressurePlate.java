// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockPressurePlate;

public class LOTRBlockPressurePlate extends BlockPressurePlate
{
    public LOTRBlockPressurePlate(final String iconPath, final Material material, final BlockPressurePlate.Sensitivity triggerType) {
        super(iconPath, material, triggerType);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabMisc);
    }
}
