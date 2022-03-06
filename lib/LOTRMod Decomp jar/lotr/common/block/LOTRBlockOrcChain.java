// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockWall;
import net.minecraft.block.BlockFence;
import net.minecraft.world.World;
import lotr.common.LOTRMod;
import net.minecraft.world.IBlockAccess;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.block.Block;

public class LOTRBlockOrcChain extends Block
{
    @SideOnly(Side.CLIENT)
    private IIcon iconMiddle;
    @SideOnly(Side.CLIENT)
    private IIcon iconTop;
    @SideOnly(Side.CLIENT)
    private IIcon iconBottom;
    @SideOnly(Side.CLIENT)
    private IIcon iconSingle;
    
    public LOTRBlockOrcChain() {
        super(Material.circuits);
        this.setHardness(1.0f);
        this.setStepSound(Block.soundTypeMetal);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabUtil);
        final float f = 0.2f;
        this.setBlockBounds(0.5f - f, 0.0f, 0.5f - f, 0.5f + f, 1.0f, 0.5f + f);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        this.iconMiddle = iconregister.registerIcon(this.getTextureName() + "_mid");
        this.iconTop = iconregister.registerIcon(this.getTextureName() + "_top");
        this.iconBottom = iconregister.registerIcon(this.getTextureName() + "_bottom");
        this.iconSingle = iconregister.registerIcon(this.getTextureName() + "_single");
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final IBlockAccess world, final int i, final int j, final int k, final int side) {
        final Block above = world.getBlock(i, j + 1, k);
        final Block below = world.getBlock(i, j - 1, k);
        final boolean chainAbove = above instanceof LOTRBlockOrcChain;
        final boolean chainBelow = below instanceof LOTRBlockOrcChain || below instanceof LOTRBlockChandelier;
        if (chainAbove && chainBelow) {
            return this.iconMiddle;
        }
        if (chainAbove) {
            return this.iconBottom;
        }
        if (chainBelow) {
            return this.iconTop;
        }
        return this.iconSingle;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        return this.iconMiddle;
    }
    
    public boolean isOpaqueCube() {
        return false;
    }
    
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    public int getRenderType() {
        return LOTRMod.proxy.getOrcChainRenderID();
    }
    
    public boolean canPlaceBlockAt(final World world, final int i, final int j, final int k) {
        final Block block = world.getBlock(i, j + 1, k);
        final int meta = world.getBlockMetadata(i, j + 1, k);
        return block instanceof LOTRBlockOrcChain || (block instanceof BlockFence || block instanceof BlockWall) || (block instanceof BlockSlab && !block.isOpaqueCube() && (meta & 0x8) == 0x0) || (block instanceof BlockStairs && (meta & 0x4) == 0x0) || world.getBlock(i, j + 1, k).isSideSolid((IBlockAccess)world, i, j + 1, k, ForgeDirection.DOWN);
    }
    
    public boolean canBlockStay(final World world, final int i, final int j, final int k) {
        return this.canPlaceBlockAt(world, i, j, k);
    }
    
    public void onNeighborBlockChange(final World world, final int i, final int j, final int k, final Block block) {
        if (!this.canBlockStay(world, i, j, k)) {
            final int meta = world.getBlockMetadata(i, j, k);
            this.dropBlockAsItem(world, i, j, k, meta, 0);
            world.setBlockToAir(i, j, k);
        }
        super.onNeighborBlockChange(world, i, j, k, block);
    }
    
    public boolean onBlockActivated(final World world, final int i, final int j, final int k, final EntityPlayer entityplayer, final int side, final float f, final float f1, final float f2) {
        final ItemStack itemstack = entityplayer.getHeldItem();
        if (itemstack != null && itemstack.getItem() == Item.getItemFromBlock((Block)this)) {
            int j2;
            for (j2 = j; j2 >= 0; --j2) {
                if (j2 >= world.getHeight()) {
                    break;
                }
                final Block block = world.getBlock(i, j2, k);
                if (block != this) {
                    break;
                }
            }
            if (j2 >= 0 && j2 < world.getHeight()) {
                final Block block = world.getBlock(i, j2, k);
                if (this.canPlaceBlockOnSide(world, i, j2, k, side) && block.isReplaceable((IBlockAccess)world, i, j2, k) && !block.getMaterial().isLiquid()) {
                    final int thisMeta = world.getBlockMetadata(i, j, k);
                    world.setBlock(i, j2, k, (Block)this, thisMeta, 3);
                    world.playSoundEffect((double)(i + 0.5f), (double)(j2 + 0.5f), (double)(k + 0.5f), super.stepSound.func_150496_b(), (super.stepSound.getVolume() + 1.0f) / 2.0f, super.stepSound.getFrequency() * 0.8f);
                    if (!entityplayer.capabilities.isCreativeMode) {
                        final ItemStack itemStack = itemstack;
                        --itemStack.stackSize;
                    }
                    if (itemstack.stackSize <= 0) {
                        entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, (ItemStack)null);
                    }
                    return true;
                }
            }
        }
        return false;
    }
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World world, final int i, final int j, final int k) {
        final float f = 0.01f;
        return AxisAlignedBB.getBoundingBox((double)(i + 0.5f - f), (double)j, (double)(k + 0.5f - f), (double)(i + 0.5f + f), (double)(j + 1), (double)(k + 0.5f + f));
    }
    
    public boolean isLadder(final IBlockAccess world, final int i, final int j, final int k, final EntityLivingBase entity) {
        return true;
    }
}
