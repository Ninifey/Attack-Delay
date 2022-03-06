// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import java.util.Random;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import lotr.common.LOTRMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import lotr.common.tileentity.LOTRTileEntityArmorStand;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.block.material.Material;
import net.minecraft.block.Block;

public class LOTRBlockArmorStand extends Block
{
    public LOTRBlockArmorStand() {
        super(Material.circuits);
        this.setHardness(0.5f);
        this.setResistance(1.0f);
        this.setStepSound(Block.soundTypeStone);
    }
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World world, final int i, final int j, final int k) {
        if (this.hasTileEntity(world.getBlockMetadata(i, j, k))) {
            return AxisAlignedBB.getBoundingBox((double)i, (double)j, (double)k, i + 1.0, j + 0.125, k + 1.0);
        }
        return null;
    }
    
    public boolean hasTileEntity(final int metadata) {
        return (metadata & 0x4) == 0x0;
    }
    
    public TileEntity createTileEntity(final World world, final int metadata) {
        if (this.hasTileEntity(metadata)) {
            return new LOTRTileEntityArmorStand();
        }
        return null;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        return Blocks.planks.getIcon(i, 0);
    }
    
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    public boolean isOpaqueCube() {
        return false;
    }
    
    public int getRenderType() {
        return -1;
    }
    
    public boolean onBlockActivated(final World world, final int i, int j, final int k, final EntityPlayer entityplayer, final int side, final float f, final float f1, final float f2) {
        if (!this.hasTileEntity(world.getBlockMetadata(i, j, k))) {
            --j;
        }
        if (this.hasTileEntity(world.getBlockMetadata(i, j, k))) {
            if (!world.isClient) {
                entityplayer.openGui((Object)LOTRMod.instance, 17, world, i, j, k);
            }
            return true;
        }
        return false;
    }
    
    public boolean canPlaceBlockAt(final World world, final int i, final int j, final int k) {
        return world.getBlock(i, j - 1, k).isSideSolid((IBlockAccess)world, i, j - 1, k, ForgeDirection.UP) && j < 255;
    }
    
    public boolean canBlockStay(final World world, final int i, final int j, final int k) {
        final int meta = world.getBlockMetadata(i, j, k);
        if (this.hasTileEntity(meta)) {
            return world.getBlock(i, j - 1, k).isSideSolid((IBlockAccess)world, i, j - 1, k, ForgeDirection.UP) && world.getBlock(i, j + 1, k) == this;
        }
        return world.getBlock(i, j - 1, k) == this;
    }
    
    public void onNeighborBlockChange(final World world, final int i, final int j, final int k, final Block block) {
        final int meta = world.getBlockMetadata(i, j, k);
        if (this.hasTileEntity(meta)) {
            if (!this.canBlockStay(world, i, j, k)) {
                world.setBlockToAir(i, j, k);
                if (!world.isClient) {
                    this.dropBlockAsItem(world, i, j, k, meta, 0);
                }
            }
        }
        else if (!this.canBlockStay(world, i, j, k)) {
            world.setBlockToAir(i, j, k);
        }
    }
    
    public void onBlockHarvested(final World world, final int i, final int j, final int k, final int meta, final EntityPlayer entityplayer) {
        if (entityplayer.capabilities.isCreativeMode && !this.hasTileEntity(meta) && world.getBlock(i, j - 1, k) == this) {
            world.setBlockToAir(i, j - 1, k);
        }
    }
    
    public Item getItemDropped(final int i, final Random random, final int j) {
        return this.hasTileEntity(i) ? LOTRMod.armorStandItem : null;
    }
    
    public void breakBlock(final World world, final int i, final int j, final int k, final Block block, final int meta) {
        final LOTRTileEntityArmorStand stand = (LOTRTileEntityArmorStand)world.getTileEntity(i, j, k);
        if (stand != null) {
            LOTRMod.dropContainerItems((IInventory)stand, world, i, j, k);
        }
        super.breakBlock(world, i, j, k, block, meta);
    }
    
    @SideOnly(Side.CLIENT)
    public Item getItem(final World world, final int i, final int j, final int k) {
        return LOTRMod.armorStandItem;
    }
}
