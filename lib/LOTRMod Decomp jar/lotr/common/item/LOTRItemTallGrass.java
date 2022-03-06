// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import net.minecraft.util.IIcon;
import lotr.common.block.LOTRBlockTallGrass;
import net.minecraft.block.Block;

public class LOTRItemTallGrass extends LOTRItemBlockMetadata
{
    public LOTRItemTallGrass(final Block block) {
        super(block);
    }
    
    public boolean requiresMultipleRenderPasses() {
        return true;
    }
    
    public int getRenderPasses(final int meta) {
        return LOTRBlockTallGrass.grassOverlay[meta] ? 2 : 1;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(final int meta, final int pass) {
        if (pass > 0) {
            return LOTRMod.tallGrass.getIcon(-1, meta);
        }
        return super.getIconFromDamageForRenderPass(meta, pass);
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public int getColorFromItemStack(final ItemStack itemstack, final int pass) {
        if (pass > 0) {
            return 16777215;
        }
        return super.getColorFromItemStack(itemstack, pass);
    }
}
