// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.client.renderer.texture.IIconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;

public abstract class LOTRBlockSmoothStoneBase extends LOTRBlockBrickBase
{
    @SideOnly(Side.CLIENT)
    private IIcon[] topIcons;
    @SideOnly(Side.CLIENT)
    private IIcon[] sideIcons;
    
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(final int i, int j) {
        if (j >= super.brickNames.length) {
            j = 0;
        }
        if (i == 0 || i == 1) {
            return this.topIcons[j];
        }
        return this.sideIcons[j];
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(final IIconRegister iconregister) {
        this.topIcons = new IIcon[super.brickNames.length];
        this.sideIcons = new IIcon[super.brickNames.length];
        for (int i = 0; i < super.brickNames.length; ++i) {
            this.topIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + super.brickNames[i] + "_top");
            this.sideIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + super.brickNames[i] + "_side");
        }
    }
}
