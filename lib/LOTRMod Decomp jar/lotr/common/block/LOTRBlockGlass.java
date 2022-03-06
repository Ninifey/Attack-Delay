// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.world.World;
import net.minecraft.client.renderer.texture.IIconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockGlass;

public class LOTRBlockGlass extends BlockGlass
{
    private boolean thirdParam;
    
    public LOTRBlockGlass() {
        super(Material.glass, false);
        this.thirdParam = false;
        this.setHardness(0.3f);
        this.setStepSound(Block.soundTypeGlass);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabBlock);
    }
    
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(final IBlockAccess world, final int i, final int j, final int k, final int side) {
        final Block block = world.getBlock(i, j, k);
        return world.getBlockMetadata(i, j, k) != world.getBlockMetadata(i - Facing.offsetsXForSide[side], j - Facing.offsetsYForSide[side], k - Facing.offsetsZForSide[side]) || (block != this && (this.thirdParam || block != this) && super.shouldSideBeRendered(world, i, j, k, side));
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        ((Block)this).blockIcon = iconregister.registerIcon(this.getTextureName());
    }
    
    public boolean canPlaceTorchOnTop(final World world, final int i, final int j, final int k) {
        return true;
    }
}
