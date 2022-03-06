// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.item.ItemStack;
import net.minecraft.client.renderer.texture.IIconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;

public class LOTRItemKaftan extends LOTRItemHaradRobes
{
    @SideOnly(Side.CLIENT)
    private IIcon overlayIcon;
    
    public LOTRItemKaftan(final int slot) {
        super(LOTRMaterial.KAFTAN, slot);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister iconregister) {
        super.registerIcons(iconregister);
        this.overlayIcon = iconregister.registerIcon(this.getIconString() + "_overlay");
    }
    
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }
    
    public IIcon getIcon(final ItemStack itemstack, final int pass) {
        if (pass >= 1) {
            return this.overlayIcon;
        }
        return super.getIcon(itemstack, pass);
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public int getColorFromItemStack(final ItemStack itemstack, final int pass) {
        if (pass >= 1) {
            return 16777215;
        }
        return super.getColorFromItemStack(itemstack, pass);
    }
    
    public int getColor(final ItemStack itemstack) {
        return LOTRItemHaradRobes.getRobesColor(itemstack);
    }
}
