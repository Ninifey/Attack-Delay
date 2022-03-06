// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.block.LOTRBlockDoubleFlower;
import net.minecraft.util.IIcon;
import net.minecraft.block.Block;

public class LOTRItemDoubleFlower extends LOTRItemBlockMetadata
{
    public LOTRItemDoubleFlower(final Block block) {
        super(block);
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIconFromDamage(final int i) {
        return ((LOTRBlockDoubleFlower)super.field_150939_a).func_149888_a(true, i);
    }
}
