// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.client.render.LOTRConnectedTextures;
import net.minecraft.util.IIcon;
import net.minecraft.client.renderer.texture.IIconRegister;

public class LOTRBlockBrick extends LOTRBlockBrickBase implements LOTRConnectedBlock
{
    public LOTRBlockBrick() {
        this.setBrickNames("mordor", "gondor", "gondorMossy", "gondorCracked", "rohan", "gondorCarved", "dwarven", "mordorCracked", "dwarvenSilver", "dwarvenGold", "dwarvenMithril", "galadhrim", "galadhrimMossy", "galadhrimCracked", "blueRock", "nearHarad");
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(final IIconRegister iconregister) {
        super.brickIcons = new IIcon[super.brickNames.length];
        for (int i = 0; i < super.brickNames.length; ++i) {
            if (i == 8 || i == 9 || i == 10) {
                LOTRConnectedTextures.registerConnectedIcons(iconregister, this, i, false);
            }
            else {
                super.brickIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + super.brickNames[i]);
            }
        }
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final IBlockAccess world, final int i, final int j, final int k, final int side) {
        final int meta = world.getBlockMetadata(i, j, k);
        if (meta == 8 || meta == 9 || meta == 10) {
            return LOTRConnectedTextures.getConnectedIconBlock(this, world, i, j, k, side, false);
        }
        return super.getIcon(world, i, j, k, side);
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(final int i, final int j) {
        if (j == 8 || j == 9 || j == 10) {
            return LOTRConnectedTextures.getConnectedIconItem(this, j);
        }
        return super.getIcon(i, j);
    }
    
    @Override
    public String getConnectedName(final int meta) {
        return super.textureName + "_" + super.brickNames[meta];
    }
    
    @Override
    public boolean areBlocksConnected(final IBlockAccess world, final int i, final int j, final int k, final int i1, final int j1, final int k1) {
        return Checks.matchBlockAndMeta(this, world, i, j, k, i1, j1, k1);
    }
}
