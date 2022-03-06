// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import java.util.ArrayList;
import net.minecraft.world.IBlockAccess;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import java.util.Random;
import lotr.common.world.biome.LOTRBiomeGenMordor;
import net.minecraft.world.World;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.IShearable;
import net.minecraft.block.Block;

public class LOTRBlockMordorMoss extends Block implements IShearable
{
    public LOTRBlockMordorMoss() {
        super(Material.plants);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.0625f, 1.0f);
        this.setTickRandomly(true);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabDeco);
        this.setHardness(0.2f);
        this.setStepSound(Block.soundTypeGrass);
    }
    
    public boolean canPlaceBlockAt(final World world, final int i, final int j, final int k) {
        return super.canPlaceBlockAt(world, i, j, k) && this.canBlockStay(world, i, j, k);
    }
    
    public boolean canBlockStay(final World world, final int i, final int j, final int k) {
        return j >= 0 && j < 256 && LOTRBiomeGenMordor.isSurfaceMordorBlock(world, i, j - 1, k);
    }
    
    public void onNeighborBlockChange(final World world, final int i, final int j, final int k, final Block block) {
        super.onNeighborBlockChange(world, i, j, k, block);
        this.checkMossCanStay(world, i, j, k);
    }
    
    public void updateTick(final World world, final int i, final int j, final int k, final Random random) {
        this.checkMossCanStay(world, i, j, k);
    }
    
    private void checkMossCanStay(final World world, final int i, final int j, final int k) {
        if (!this.canBlockStay(world, i, j, k)) {
            this.dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
            world.setBlockToAir(i, j, k);
        }
    }
    
    public boolean isOpaqueCube() {
        return false;
    }
    
    public Item getItemDropped(final int i, final Random random, final int j) {
        return null;
    }
    
    public boolean isShearable(final ItemStack item, final IBlockAccess world, final int i, final int j, final int k) {
        return true;
    }
    
    public ArrayList onSheared(final ItemStack item, final IBlockAccess world, final int i, final int j, final int k, final int fortune) {
        final ArrayList drops = new ArrayList();
        drops.add(new ItemStack((Block)this));
        return drops;
    }
}
