// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import lotr.common.LOTRMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.world.IBlockAccess;
import net.minecraft.util.MathHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.tileentity.LOTRTileEntityDartTrap;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import net.minecraft.block.Block;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.block.BlockContainer;

public class LOTRBlockDartTrap extends BlockContainer
{
    @SideOnly(Side.CLIENT)
    private IIcon trapIcon;
    private Block modelBlock;
    private int modelBlockMeta;
    
    public LOTRBlockDartTrap(final Block block, final int meta) {
        super(Material.rock);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabUtil);
        this.setHardness(4.0f);
        this.setStepSound(Block.soundTypeStone);
        this.modelBlock = block;
        this.modelBlockMeta = meta;
    }
    
    public TileEntity createNewTileEntity(final World world, final int i) {
        return (TileEntity)new LOTRTileEntityDartTrap();
    }
    
    public void onBlockAdded(final World world, final int i, final int j, final int k) {
        super.onBlockAdded(world, i, j, k);
        this.setDefaultDirection(world, i, j, k);
    }
    
    private void setDefaultDirection(final World world, final int i, final int j, final int k) {
        if (!world.isClient) {
            final Block i2 = world.getBlock(i, j, k - 1);
            final Block j2 = world.getBlock(i, j, k + 1);
            final Block k2 = world.getBlock(i - 1, j, k);
            final Block l1 = world.getBlock(i + 1, j, k);
            byte meta = 3;
            if (i2.isOpaqueCube() && !j2.isOpaqueCube()) {
                meta = 3;
            }
            if (j2.isOpaqueCube() && !i2.isOpaqueCube()) {
                meta = 2;
            }
            if (k2.isOpaqueCube() && !l1.isOpaqueCube()) {
                meta = 5;
            }
            if (l1.isOpaqueCube() && !k2.isOpaqueCube()) {
                meta = 4;
            }
            world.setBlockMetadata(i, j, k, (int)meta, 2);
        }
    }
    
    public void onBlockPlacedBy(final World world, final int i, final int j, final int k, final EntityLivingBase entity, final ItemStack itemstack) {
        final int rotation = MathHelper.floor_double(((Entity)entity).rotationYaw * 4.0f / 360.0f + 0.5) & 0x3;
        if (rotation == 0) {
            world.setBlockMetadata(i, j, k, 2, 2);
        }
        if (rotation == 1) {
            world.setBlockMetadata(i, j, k, 5, 2);
        }
        if (rotation == 2) {
            world.setBlockMetadata(i, j, k, 3, 2);
        }
        if (rotation == 3) {
            world.setBlockMetadata(i, j, k, 4, 2);
        }
        if (itemstack.hasDisplayName()) {
            ((LOTRTileEntityDartTrap)world.getTileEntity(i, j, k)).func_146018_a(itemstack.getDisplayName());
        }
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final IBlockAccess world, final int i, final int j, final int k, final int side) {
        final int meta = world.getBlockMetadata(i, j, k);
        if (side == meta) {
            return this.trapIcon;
        }
        return this.modelBlock.getIcon(i, this.modelBlockMeta);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        if (i == 3) {
            return this.trapIcon;
        }
        return this.modelBlock.getIcon(i, this.modelBlockMeta);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        this.trapIcon = iconregister.registerIcon(this.getTextureName() + "_face");
    }
    
    public boolean onBlockActivated(final World world, final int i, final int j, final int k, final EntityPlayer entityplayer, final int side, final float f, final float f1, final float f2) {
        if (!world.isClient) {
            entityplayer.openGui((Object)LOTRMod.instance, 40, world, i, j, k);
        }
        return true;
    }
    
    public void breakBlock(final World world, final int i, final int j, final int k, final Block block, final int meta) {
        final LOTRTileEntityDartTrap trap = (LOTRTileEntityDartTrap)world.getTileEntity(i, j, k);
        if (trap != null) {
            LOTRMod.dropContainerItems((IInventory)trap, world, i, j, k);
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
}
