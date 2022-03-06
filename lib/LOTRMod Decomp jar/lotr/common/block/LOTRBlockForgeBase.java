// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import lotr.common.LOTRMod;
import net.minecraft.world.EnumSkyBlock;
import lotr.common.tileentity.LOTRTileEntityForgeBase;
import net.minecraft.util.MathHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.EntityLivingBase;
import java.util.Random;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.block.BlockContainer;

public abstract class LOTRBlockForgeBase extends BlockContainer
{
    @SideOnly(Side.CLIENT)
    private IIcon[] forgeIcons;
    
    public LOTRBlockForgeBase() {
        super(Material.rock);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabUtil);
        this.setHardness(4.0f);
        this.setStepSound(Block.soundTypeStone);
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
            return this.forgeIcons[1];
        }
        final int meta = world.getBlockMetadata(i, j, k) & 0x7;
        return (side != meta) ? this.forgeIcons[0] : (isForgeActive(world, i, j, k) ? this.forgeIcons[3] : this.forgeIcons[2]);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        return (i == 1 || i == 0) ? this.forgeIcons[1] : ((i == 3) ? this.forgeIcons[2] : this.forgeIcons[0]);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        (this.forgeIcons = new IIcon[4])[0] = iconregister.registerIcon(this.getTextureName() + "_side");
        this.forgeIcons[1] = iconregister.registerIcon(this.getTextureName() + "_top");
        this.forgeIcons[2] = iconregister.registerIcon(this.getTextureName() + "_front");
        this.forgeIcons[3] = iconregister.registerIcon(this.getTextureName() + "_active");
    }
    
    protected abstract boolean useLargeSmoke();
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(final World world, final int i, final int j, final int k, final Random random) {
        if (isForgeActive((IBlockAccess)world, i, j, k)) {
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
            if (this.useLargeSmoke()) {
                for (int l = 0; l < 6; ++l) {
                    float f6 = random.nextBoolean() ? 0.0f : 1.0f;
                    float f7 = random.nextBoolean() ? 0.0f : 1.0f;
                    final float f8 = 0.5f;
                    f6 += -0.1f + random.nextFloat() * 0.2f;
                    f7 += -0.1f + random.nextFloat() * 0.2f;
                    if (random.nextInt(3) > 0) {
                        world.spawnParticle("largesmoke", (double)(i + f6), (double)(j + f8), (double)(k + f7), 0.0, 0.0, 0.0);
                    }
                    else {
                        world.spawnParticle("smoke", (double)(i + f6), (double)(j + f8), (double)(k + f7), 0.0, 0.0, 0.0);
                    }
                }
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
            ((LOTRTileEntityForgeBase)world.getTileEntity(i, j, k)).setSpecialForgeName(itemstack.getDisplayName());
        }
    }
    
    public int getLightValue(final IBlockAccess world, final int i, final int j, final int k) {
        return isForgeActive(world, i, j, k) ? 13 : 0;
    }
    
    public static boolean isForgeActive(final IBlockAccess world, final int i, final int j, final int k) {
        final int meta = world.getBlockMetadata(i, j, k);
        return (meta & 0x8) != 0x0;
    }
    
    public static void toggleForgeActive(final World world, final int i, final int j, final int k) {
        final int meta = world.getBlockMetadata(i, j, k);
        world.setBlockMetadata(i, j, k, meta ^ 0x8, 2);
        world.updateLightByType(EnumSkyBlock.Block, i, j, k);
    }
    
    public void breakBlock(final World world, final int i, final int j, final int k, final Block block, final int meta) {
        final LOTRTileEntityForgeBase forge = (LOTRTileEntityForgeBase)world.getTileEntity(i, j, k);
        if (forge != null) {
            LOTRMod.dropContainerItems((IInventory)forge, world, i, j, k);
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
