// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.entity.Entity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Direction;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import lotr.common.tileentity.LOTRTileEntityWeaponRack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockContainer;

public class LOTRBlockWeaponRack extends BlockContainer
{
    public LOTRBlockWeaponRack() {
        super(Material.circuits);
        this.setHardness(0.5f);
        this.setResistance(1.0f);
        this.setStepSound(Block.soundTypeWood);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabDeco);
    }
    
    public TileEntity createNewTileEntity(final World world, final int i) {
        return new LOTRTileEntityWeaponRack();
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        return Blocks.planks.getIcon(i, 0);
    }
    
    @SideOnly(Side.CLIENT)
    public String getItemIconName() {
        return this.getTextureName();
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
    
    public void setBlockBoundsBasedOnState(final IBlockAccess world, final int i, final int j, final int k) {
        final float f = 0.2f;
        final float h = 0.9f;
        final int meta = world.getBlockMetadata(i, j, k);
        if (meta == 0 || meta == 2) {
            this.setBlockBounds(0.0f, 0.0f, f, 1.0f, h, 1.0f - f);
        }
        else if (meta == 1 || meta == 3) {
            this.setBlockBounds(f, 0.0f, 0.0f, 1.0f - f, h, 1.0f);
        }
        else if (meta == 4) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, h, 1.0f - f * 2.0f);
        }
        else if (meta == 6) {
            this.setBlockBounds(0.0f, 0.0f, f * 2.0f, 1.0f, h, 1.0f);
        }
        else if (meta == 5) {
            this.setBlockBounds(f * 2.0f, 0.0f, 0.0f, 1.0f, h, 1.0f);
        }
        else if (meta == 7) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f - f * 2.0f, h, 1.0f);
        }
    }
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World world, final int i, final int j, final int k) {
        return null;
    }
    
    public boolean onBlockActivated(final World world, final int i, final int j, final int k, final EntityPlayer entityplayer, final int side, final float f, final float f1, final float f2) {
        final TileEntity tileentity = world.getTileEntity(i, j, k);
        if (tileentity instanceof LOTRTileEntityWeaponRack) {
            final LOTRTileEntityWeaponRack rack = (LOTRTileEntityWeaponRack)tileentity;
            final ItemStack heldItem = entityplayer.getHeldItem();
            final ItemStack rackItem = rack.getWeaponItem();
            if (rackItem != null) {
                if (!world.isClient) {
                    if (entityplayer.getHeldItem() == null) {
                        entityplayer.setCurrentItemOrArmor(0, rackItem);
                        world.playSoundEffect(i + 0.5, j + 0.5, k + 0.5, "random.pop", 0.2f, ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7f + 1.0f) * 2.0f);
                    }
                    else {
                        this.dropBlockAsItem_do(world, i, j, k, rackItem);
                    }
                    rack.setWeaponItem(null);
                }
                return true;
            }
            if (rack.canAcceptItem(heldItem)) {
                if (!world.isClient) {
                    rack.setWeaponItem(heldItem.copy());
                }
                if (!entityplayer.capabilities.isCreativeMode) {
                    final ItemStack itemStack = heldItem;
                    --itemStack.stackSize;
                }
                return true;
            }
        }
        return false;
    }
    
    public boolean canPlaceBlockOnSide(final World world, final int i, final int j, final int k, final int side) {
        if (side == 1) {
            return world.getBlock(i, j - 1, k).isSideSolid((IBlockAccess)world, i, j - 1, k, ForgeDirection.UP);
        }
        if (side != 0) {
            final ForgeDirection dir = ForgeDirection.getOrientation(side);
            final int i2 = i - dir.offsetX;
            final int k2 = k - dir.offsetZ;
            return world.getBlock(i2, j, k2).isSideSolid((IBlockAccess)world, i2, j, k2, dir);
        }
        return false;
    }
    
    public boolean canBlockStay(final World world, final int i, final int j, final int k) {
        final int meta = world.getBlockMetadata(i, j, k);
        if ((meta & 0x4) == 0x0) {
            return this.canPlaceBlockOnSide(world, i, j, k, 1);
        }
        final int l = meta & 0x3;
        final int dir = Direction.directionToFacing[l];
        return this.canPlaceBlockOnSide(world, i, j, k, dir);
    }
    
    public void onNeighborBlockChange(final World world, final int i, final int j, final int k, final Block block) {
        if (!this.canBlockStay(world, i, j, k)) {
            final int meta = world.getBlockMetadata(i, j, k);
            this.dropBlockAsItem(world, i, j, k, meta, 0);
            world.setBlockToAir(i, j, k);
        }
    }
    
    public int onBlockPlaced(final World world, final int i, final int j, final int k, final int side, final float f, final float f1, final float f2, final int meta) {
        if (side == 1) {
            return 0;
        }
        if (side != 0) {
            return Direction.facingToDirection[side] | 0x4;
        }
        return 0;
    }
    
    public void onBlockPlacedBy(final World world, final int i, final int j, final int k, final EntityLivingBase entity, final ItemStack itemstack) {
        int meta = world.getBlockMetadata(i, j, k);
        if ((meta & 0x4) == 0x0) {
            final int rotation = MathHelper.floor_double(((Entity)entity).rotationYaw * 4.0f / 360.0f + 2.5) & 0x3;
            meta |= rotation;
            world.setBlockMetadata(i, j, k, meta, 2);
        }
    }
    
    public void breakBlock(final World world, final int i, final int j, final int k, final Block block, final int meta) {
        final LOTRTileEntityWeaponRack rack = (LOTRTileEntityWeaponRack)world.getTileEntity(i, j, k);
        if (rack != null) {
            final ItemStack weaponItem = rack.getWeaponItem();
            if (weaponItem != null) {
                this.dropBlockAsItem_do(world, i, j, k, weaponItem);
            }
        }
        super.breakBlock(world, i, j, k, block, meta);
    }
    
    public ItemStack getPickBlock(final MovingObjectPosition target, final World world, final int i, final int j, final int k, final EntityPlayer entityplayer) {
        final LOTRTileEntityWeaponRack rack = (LOTRTileEntityWeaponRack)world.getTileEntity(i, j, k);
        if (rack != null) {
            final ItemStack weaponItem = rack.getWeaponItem();
            if (weaponItem != null) {
                return weaponItem.copy();
            }
        }
        return super.getPickBlock(target, world, i, j, k, entityplayer);
    }
}
