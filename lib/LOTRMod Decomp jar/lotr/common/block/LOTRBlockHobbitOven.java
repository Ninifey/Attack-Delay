// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import lotr.common.LOTRMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.util.MathHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.EntityLivingBase;
import java.util.Random;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.world.IBlockAccess;
import lotr.common.tileentity.LOTRTileEntityHobbitOven;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.block.BlockContainer;

public class LOTRBlockHobbitOven extends BlockContainer
{
    @SideOnly(Side.CLIENT)
    private IIcon[] ovenIcons;
    
    public LOTRBlockHobbitOven() {
        super(Material.rock);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabUtil);
        this.setHardness(3.5f);
        this.setStepSound(Block.soundTypeStone);
    }
    
    public TileEntity createNewTileEntity(final World world, final int i) {
        return new LOTRTileEntityHobbitOven();
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
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final IBlockAccess world, final int i, final int j, final int k, final int side) {
        if (side == 1 || side == 0) {
            return this.ovenIcons[1];
        }
        final int meta = world.getBlockMetadata(i, j, k) & 0x7;
        return (side != meta) ? this.ovenIcons[0] : (isOvenActive(world, i, j, k) ? this.ovenIcons[3] : this.ovenIcons[2]);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        return (i == 1 || i == 0) ? this.ovenIcons[1] : ((i == 3) ? this.ovenIcons[2] : this.ovenIcons[0]);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        (this.ovenIcons = new IIcon[4])[0] = iconregister.registerIcon(this.getTextureName() + "_side");
        this.ovenIcons[1] = iconregister.registerIcon(this.getTextureName() + "_top");
        this.ovenIcons[2] = iconregister.registerIcon(this.getTextureName() + "_front");
        this.ovenIcons[3] = iconregister.registerIcon(this.getTextureName() + "_active");
    }
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(final World world, final int i, final int j, final int k, final Random random) {
        if (isOvenActive((IBlockAccess)world, i, j, k)) {
            final int meta = world.getBlockMetadata(i, j, k) & 0x7;
            final float f = i + 0.5f;
            final float f2 = j + 0.0f + random.nextFloat() * 6.0f / 16.0f;
            final float f3 = k + 0.5f;
            final float f4 = 0.52f;
            final float f5 = random.nextFloat() * 0.6f - 0.3f;
            if (meta == 4) {
                world.spawnParticle("smoke", (double)(f - f4), (double)f2, (double)(f3 + f5), 0.0, 0.0, 0.0);
                world.spawnParticle("flame", (double)(f - f4), (double)f2, (double)(f3 + f5), 0.0, 0.0, 0.0);
            }
            else if (meta == 5) {
                world.spawnParticle("smoke", (double)(f + f4), (double)f2, (double)(f3 + f5), 0.0, 0.0, 0.0);
                world.spawnParticle("flame", (double)(f + f4), (double)f2, (double)(f3 + f5), 0.0, 0.0, 0.0);
            }
            else if (meta == 2) {
                world.spawnParticle("smoke", (double)(f + f5), (double)f2, (double)(f3 - f4), 0.0, 0.0, 0.0);
                world.spawnParticle("flame", (double)(f + f5), (double)f2, (double)(f3 - f4), 0.0, 0.0, 0.0);
            }
            else if (meta == 3) {
                world.spawnParticle("smoke", (double)(f + f5), (double)f2, (double)(f3 + f4), 0.0, 0.0, 0.0);
                world.spawnParticle("flame", (double)(f + f5), (double)f2, (double)(f3 + f4), 0.0, 0.0, 0.0);
            }
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
            ((LOTRTileEntityHobbitOven)world.getTileEntity(i, j, k)).setOvenName(itemstack.getDisplayName());
        }
    }
    
    public int getLightValue(final IBlockAccess world, final int i, final int j, final int k) {
        return isOvenActive(world, i, j, k) ? 13 : 0;
    }
    
    public static boolean isOvenActive(final IBlockAccess world, final int i, final int j, final int k) {
        final int meta = world.getBlockMetadata(i, j, k);
        return meta > 7;
    }
    
    public static void setOvenActive(final World world, final int i, final int j, final int k) {
        final int meta = world.getBlockMetadata(i, j, k);
        world.setBlockMetadata(i, j, k, meta ^ 0x8, 2);
        world.updateLightByType(EnumSkyBlock.Block, i, j, k);
    }
    
    public boolean onBlockActivated(final World world, final int i, final int j, final int k, final EntityPlayer entityplayer, final int side, final float f, final float f1, final float f2) {
        if (!world.isClient) {
            entityplayer.openGui((Object)LOTRMod.instance, 0, world, i, j, k);
        }
        return true;
    }
    
    public void breakBlock(final World world, final int i, final int j, final int k, final Block block, final int meta) {
        final LOTRTileEntityHobbitOven oven = (LOTRTileEntityHobbitOven)world.getTileEntity(i, j, k);
        if (oven != null) {
            LOTRMod.dropContainerItems((IInventory)oven, world, i, j, k);
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
