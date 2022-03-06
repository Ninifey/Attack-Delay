// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.world.IBlockAccess;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.block.Block;

public class LOTRBlockDoubleTorch extends Block
{
    @SideOnly(Side.CLIENT)
    private IIcon[] torchIcons;
    public Item torchItem;
    
    public LOTRBlockDoubleTorch() {
        super(Material.circuits);
        this.setHardness(0.0f);
        this.setStepSound(Block.soundTypeWood);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        return (j == 1) ? this.torchIcons[1] : this.torchIcons[0];
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        (this.torchIcons = new IIcon[2])[0] = iconregister.registerIcon(this.getTextureName() + "_bottom");
        this.torchIcons[1] = iconregister.registerIcon(this.getTextureName() + "_top");
    }
    
    public boolean isOpaqueCube() {
        return false;
    }
    
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    public int getRenderType() {
        return LOTRMod.proxy.getDoubleTorchRenderID();
    }
    
    public Item getItemDropped(final int i, final Random random, final int j) {
        if (i == 0) {
            return this.torchItem;
        }
        return null;
    }
    
    public void onNeighborBlockChange(final World world, final int i, final int j, final int k, final Block block) {
        if (!this.canBlockStay(world, i, j, k)) {
            final int meta = world.getBlockMetadata(i, j, k);
            if (meta == 0) {
                this.dropBlockAsItem(world, i, j, k, meta, 0);
                if (world.getBlock(i, j + 1, k) == this) {
                    world.setBlock(i, j + 1, k, Blocks.air, 0, 2);
                }
            }
            world.setBlock(i, j, k, Blocks.air, 0, 2);
        }
    }
    
    public boolean canBlockStay(final World world, final int i, final int j, final int k) {
        if (world.getBlock(i, j, k) != this) {
            return super.canBlockStay(world, i, j, k);
        }
        final int l = world.getBlockMetadata(i, j, k);
        return (l == 1) ? (world.getBlock(i, j - 1, k) == this) : (world.getBlock(i, j + 1, k) == this && canPlaceTorchOn(world, i, j - 1, k));
    }
    
    public static boolean canPlaceTorchOn(final World world, final int i, final int j, final int k) {
        return world.getBlock(i, j, k).canPlaceTorchOnTop(world, i, j, k);
    }
    
    public void onBlockHarvested(final World world, final int i, final int j, final int k, final int meta, final EntityPlayer entityplayer) {
        if (meta == 1) {
            if (world.getBlock(i, j - 1, k) == this) {
                if (!entityplayer.capabilities.isCreativeMode) {
                    world.func_147480_a(i, j - 1, k, true);
                }
                else {
                    world.setBlockToAir(i, j - 1, k);
                }
            }
        }
        else if (entityplayer.capabilities.isCreativeMode && world.getBlock(i, j + 1, k) == this) {
            world.setBlock(i, j + 1, k, Blocks.air, 0, 2);
        }
        super.onBlockHarvested(world, i, j, k, meta, entityplayer);
    }
    
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBoxFromPool(final World world, final int i, final int j, final int k) {
        final int meta = world.getBlockMetadata(i, j, k);
        if (meta == 0) {
            this.setBlockBounds(0.4f, 0.0f, 0.4f, 0.6f, 1.0f, 0.6f);
        }
        else if (meta == 1) {
            this.setBlockBounds(0.4f, 0.0f, 0.4f, 0.6f, 0.5375f, 0.6f);
        }
        return super.getSelectedBoundingBoxFromPool(world, i, j, k);
    }
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World world, final int i, final int j, final int k) {
        return null;
    }
    
    public void setBlockBoundsBasedOnState(final IBlockAccess world, final int i, final int j, final int k) {
        final int meta = world.getBlockMetadata(i, j, k);
        if (meta == 0) {
            this.setBlockBounds(0.4375f, 0.0f, 0.4375f, 0.5625f, 1.0f, 0.5625f);
        }
        else if (meta == 1) {
            this.setBlockBounds(0.4375f, 0.0f, 0.4375f, 0.5625f, 0.5f, 0.5625f);
        }
    }
    
    public int getLightValue(final IBlockAccess world, final int i, final int j, final int k) {
        return (world.getBlockMetadata(i, j, k) == 1) ? 14 : 0;
    }
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(final World world, final int i, final int j, final int k, final Random random) {
        if (world.getBlockMetadata(i, j, k) == 1) {
            final double d = i + 0.5;
            final double d2 = j + 0.6;
            final double d3 = k + 0.5;
            world.spawnParticle("smoke", d, d2, d3, 0.0, 0.0, 0.0);
            world.spawnParticle("flame", d, d2, d3, 0.0, 0.0, 0.0);
        }
    }
    
    @SideOnly(Side.CLIENT)
    public Item getItem(final World world, final int i, final int j, final int k) {
        return this.torchItem;
    }
}
