// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.client.render.LOTRConnectedTextures;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import net.minecraft.block.Block;

public class LOTRBlockDaub extends Block implements LOTRConnectedBlock
{
    public LOTRBlockDaub() {
        super(Material.grass);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabBlock);
        this.setHardness(1.0f);
        this.setStepSound(Block.soundTypeGrass);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        LOTRConnectedTextures.registerConnectedIcons(iconregister, this, 0, false);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final IBlockAccess world, final int i, final int j, final int k, final int side) {
        return LOTRConnectedTextures.getConnectedIconBlock(this, world, i, j, k, side, false);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        return LOTRConnectedTextures.getConnectedIconItem(this, j);
    }
    
    public String getConnectedName(final int meta) {
        return super.textureName;
    }
    
    public boolean areBlocksConnected(final IBlockAccess world, final int i, final int j, final int k, final int i1, final int j1, final int k1) {
        final int meta = world.getBlockMetadata(i, j, k);
        final Block otherBlock = world.getBlock(i1, j1, k1);
        final int otherMeta = world.getBlockMetadata(i1, j1, k1);
        return otherBlock == this && otherMeta == meta;
    }
}
