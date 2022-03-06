// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class LOTRItemBlockMetadata extends ItemBlock
{
    public LOTRItemBlockMetadata(final Block block) {
        super(block);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(final int i) {
        return super.field_150939_a.getIcon(2, i);
    }
    
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(final ItemStack itemstack, final int pass) {
        return super.field_150939_a.getRenderColor(itemstack.getItemDamage());
    }
    
    public int getMetadata(final int i) {
        return i;
    }
    
    public String getUnlocalizedName(final ItemStack itemstack) {
        return super.getUnlocalizedName() + "." + itemstack.getItemDamage();
    }
}
