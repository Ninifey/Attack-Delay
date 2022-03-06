// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.client.renderer.texture.IIconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;

public class LOTRBlockOreStorage2 extends LOTRBlockOreStorageBase
{
    public LOTRBlockOreStorage2() {
        this.setOreStorageNames("blackUrukSteel", "elfSteel", "gildedIron", "salt");
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(final int i, final int j) {
        return super.getIcon(i, j);
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(final IIconRegister iconregister) {
        super.registerBlockIcons(iconregister);
    }
}
