// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import java.util.ArrayList;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.world.IBlockAccess;
import net.minecraft.client.renderer.texture.IIconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import lotr.common.LOTRMod;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import java.util.Iterator;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import java.util.Random;
import java.util.List;
import net.minecraftforge.common.IShearable;
import net.minecraft.block.Block;

public class LOTRBlockFallenLeaves extends Block implements IShearable
{
    public static List<LOTRBlockFallenLeaves> allFallenLeaves;
    private static Random leafRand;
    private Block[] leafBlocks;
    
    public LOTRBlockFallenLeaves() {
        super(Material.vine);
        LOTRBlockFallenLeaves.allFallenLeaves.add(this);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabDeco);
        this.setHardness(0.2f);
        this.setStepSound(Block.soundTypeGrass);
        super.field_149783_u = true;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.125f, 1.0f);
    }
    
    public static void assignLeaves(final Block fallenLeaves, final Block... leaves) {
        ((LOTRBlockFallenLeaves)fallenLeaves).leafBlocks = leaves;
    }
    
    public Block[] getLeafBlocks() {
        return this.leafBlocks;
    }
    
    public Object[] leafBlockMetaFromFallenMeta(final int meta) {
        final Block leaf = this.leafBlocks[meta / 4];
        final int leafMeta = meta & 0x3;
        return new Object[] { leaf, leafMeta };
    }
    
    public static Object[] fallenBlockMetaFromLeafBlockMeta(final Block block, int meta) {
        meta &= 0x3;
        for (final LOTRBlockFallenLeaves fallenLeaves : LOTRBlockFallenLeaves.allFallenLeaves) {
            for (int i = 0; i < fallenLeaves.leafBlocks.length; ++i) {
                final Block leafBlock = fallenLeaves.leafBlocks[i];
                if (leafBlock == block) {
                    return new Object[] { fallenLeaves, i * 4 + meta };
                }
            }
        }
        return null;
    }
    
    public void addCollisionBoxesToList(final World world, final int i, final int j, final int k, final AxisAlignedBB bb, final List boxes, final Entity entity) {
    }
    
    public boolean isOpaqueCube() {
        return false;
    }
    
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    public int getRenderType() {
        return LOTRMod.proxy.getFallenLeavesRenderID();
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        final Object[] obj = this.leafBlockMetaFromFallenMeta(j);
        return ((Block)obj[0]).getIcon(i, (int)obj[1]);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
    }
    
    @SideOnly(Side.CLIENT)
    public int getRenderColor(final int i) {
        final Object[] obj = this.leafBlockMetaFromFallenMeta(i);
        return ((Block)obj[0]).getRenderColor((int)obj[1]);
    }
    
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(final IBlockAccess world, final int i, final int j, final int k) {
        final int meta = world.getBlockMetadata(i, j, k);
        final Object[] obj = this.leafBlockMetaFromFallenMeta(meta);
        return ((Block)obj[0]).colorMultiplier(world, i, j, k);
    }
    
    public boolean canBlockStay(final World world, final int i, final int j, final int k) {
        final Block below = world.getBlock(i, j - 1, k);
        final int belowMeta = world.getBlockMetadata(i, j - 1, k);
        return (below.getMaterial() == Material.water && belowMeta == 0) || below.isSideSolid((IBlockAccess)world, i, j - 1, k, ForgeDirection.UP);
    }
    
    public boolean canPlaceBlockAt(final World world, final int i, final int j, final int k) {
        final Block block = world.getBlock(i, j, k);
        return !block.getMaterial().isLiquid() && this.canBlockStay(world, i, j, k);
    }
    
    public void onNeighborBlockChange(final World world, final int i, final int j, final int k, final Block block) {
        if (!this.canBlockStay(world, i, j, k)) {
            this.dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
            world.setBlockToAir(i, j, k);
        }
    }
    
    public Item getItemDropped(final int i, final Random random, final int j) {
        return null;
    }
    
    public int damageDropped(final int i) {
        return i;
    }
    
    public boolean isShearable(final ItemStack item, final IBlockAccess world, final int i, final int j, final int k) {
        return true;
    }
    
    public ArrayList onSheared(final ItemStack item, final IBlockAccess world, final int i, final int j, final int k, final int fortune) {
        final ArrayList drops = new ArrayList();
        drops.add(new ItemStack((Block)this, 1, world.getBlockMetadata(i, j, k)));
        return drops;
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(final Item item, final CreativeTabs tab, final List list) {
        for (int i = 0; i < this.leafBlocks.length; ++i) {
            final Block leaf = this.leafBlocks[i];
            final List<ItemStack> leafTypes = new ArrayList<ItemStack>();
            leaf.getSubBlocks(Item.getItemFromBlock(leaf), leaf.getCreativeTabToDisplayOn(), (List)leafTypes);
            for (final ItemStack leafItem : leafTypes) {
                final int meta = leafItem.getItemDamage();
                list.add(new ItemStack(item, 1, i * 4 + meta));
            }
        }
    }
    
    static {
        LOTRBlockFallenLeaves.allFallenLeaves = new ArrayList<LOTRBlockFallenLeaves>();
        LOTRBlockFallenLeaves.leafRand = new Random();
    }
}
