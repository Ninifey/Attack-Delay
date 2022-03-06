// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import java.util.ArrayList;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.world.IBlockAccess;
import lotr.common.world.biome.LOTRBiomeGenMordor;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.IShearable;
import net.minecraft.block.BlockBush;

public class LOTRBlockMordorGrass extends BlockBush implements IShearable
{
    public LOTRBlockMordorGrass() {
        super(Material.vine);
        this.setBlockBounds(0.1f, 0.0f, 0.1f, 0.9f, 0.8f, 0.9f);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabDeco);
        this.setHardness(0.0f);
        this.setStepSound(Block.soundTypeGrass);
    }
    
    public boolean canBlockStay(final World world, final int i, final int j, final int k) {
        return j >= 0 && j < 256 && LOTRBiomeGenMordor.isSurfaceMordorBlock(world, i, j - 1, k);
    }
    
    public boolean isReplaceable(final IBlockAccess world, final int i, final int j, final int k) {
        return true;
    }
    
    public int getRenderType() {
        return LOTRMod.proxy.getGrassRenderID();
    }
    
    public Item getItemDropped(final int i, final Random random, final int j) {
        return null;
    }
    
    public boolean isShearable(final ItemStack item, final IBlockAccess world, final int i, final int j, final int k) {
        return true;
    }
    
    public ArrayList onSheared(final ItemStack item, final IBlockAccess world, final int i, final int j, final int k, final int fortune) {
        final ArrayList list = new ArrayList();
        list.add(new ItemStack((Block)this, 1, world.getBlockMetadata(i, j, k)));
        return list;
    }
}
