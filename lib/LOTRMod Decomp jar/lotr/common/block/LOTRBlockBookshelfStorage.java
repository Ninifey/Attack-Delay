// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import lotr.common.LOTRMod;
import net.minecraft.item.Item;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import java.util.ArrayList;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import lotr.common.tileentity.LOTRTileEntityBookshelf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockContainer;

public class LOTRBlockBookshelfStorage extends BlockContainer
{
    public LOTRBlockBookshelfStorage() {
        super(Material.wood);
        this.setHardness(1.5f);
        this.setStepSound(Block.soundTypeWood);
        this.setcreativeTab((CreativeTabs)null);
    }
    
    public TileEntity createNewTileEntity(final World world, final int i) {
        return new LOTRTileEntityBookshelf();
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        return Blocks.bookshelf.getIcon(i, j);
    }
    
    public ArrayList<ItemStack> getDrops(final World world, final int i, final int j, final int k, final int meta, final int fortune) {
        return (ArrayList<ItemStack>)Blocks.bookshelf.getDrops(world, i, j, k, meta, fortune);
    }
    
    public static boolean canOpenBookshelf(final World world, final int i, final int j, final int k, final EntityPlayer entityplayer) {
        final ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        return itemstack == null || itemstack.getItem() != Item.getItemFromBlock(Blocks.bookshelf);
    }
    
    public boolean onBlockActivated(final World world, final int i, final int j, final int k, final EntityPlayer entityplayer, final int side, final float f, final float f1, final float f2) {
        if (!canOpenBookshelf(world, i, j, k, entityplayer)) {
            return false;
        }
        if (!world.isClient) {
            entityplayer.openGui((Object)LOTRMod.instance, 55, world, i, j, k);
        }
        return true;
    }
    
    public void breakBlock(final World world, final int i, final int j, final int k, final Block block, final int meta) {
        final LOTRTileEntityBookshelf bookshelf = (LOTRTileEntityBookshelf)world.getTileEntity(i, j, k);
        if (bookshelf != null) {
            LOTRMod.dropContainerItems((IInventory)bookshelf, world, i, j, k);
            world.func_147453_f(i, j, k, block);
        }
        super.breakBlock(world, i, j, k, block, meta);
    }
    
    public boolean hasComparatorInputOverride() {
        return true;
    }
    
    public int getComparatorInputOverride(final World world, final int i, final int j, final int k, final int direction) {
        return Container.calcRedstoneFromInventory((IInventory)world.getTileEntity(i, j, k));
    }
    
    protected boolean canSilkHarvest() {
        return true;
    }
    
    protected ItemStack createStackedBlock(final int i) {
        return new ItemStack(Blocks.bookshelf);
    }
}
