// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.item.ItemStack;
import java.util.ArrayList;
import net.minecraft.init.Blocks;
import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraftforge.common.IPlantable;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.IShearable;
import net.minecraft.block.BlockBush;

public class LOTRBlockGrass extends BlockBush implements IShearable
{
    private boolean isSandy;
    
    public LOTRBlockGrass() {
        super(Material.vine);
        this.setBlockBounds(0.1f, 0.0f, 0.1f, 0.9f, 0.8f, 0.9f);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabDeco);
        this.setHardness(0.0f);
        this.setStepSound(Block.soundTypeGrass);
    }
    
    public LOTRBlockGrass setSandy() {
        this.isSandy = true;
        return this;
    }
    
    public boolean canBlockStay(final World world, final int i, final int j, final int k) {
        final Block below = world.getBlock(i, j - 1, k);
        return (this.isSandy && below.getMaterial() == Material.sand && below.isSideSolid((IBlockAccess)world, i, j - 1, k, ForgeDirection.UP)) || below.canSustainPlant((IBlockAccess)world, i, j, k, ForgeDirection.UP, (IPlantable)this);
    }
    
    public boolean isReplaceable(final IBlockAccess world, final int i, final int j, final int k) {
        return true;
    }
    
    public int getRenderType() {
        return LOTRMod.proxy.getGrassRenderID();
    }
    
    public int quantityDroppedWithBonus(final int i, final Random random) {
        return Blocks.tallgrass.quantityDroppedWithBonus(i, random);
    }
    
    public ArrayList getDrops(final World world, final int i, final int j, final int k, final int meta, final int fortune) {
        return Blocks.tallgrass.getDrops(world, i, j, k, meta, fortune);
    }
    
    public int getDamageValue(final World world, final int i, final int j, final int k) {
        return world.getBlockMetadata(i, j, k);
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
