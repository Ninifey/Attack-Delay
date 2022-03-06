// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.world.IBlockAccess;
import lotr.common.tileentity.LOTRTileEntityMillstone;
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

public class LOTRBlockMillstone extends BlockContainer
{
    @SideOnly(Side.CLIENT)
    private IIcon iconSide;
    @SideOnly(Side.CLIENT)
    private IIcon iconTop;
    @SideOnly(Side.CLIENT)
    private IIcon iconSideActive;
    @SideOnly(Side.CLIENT)
    private IIcon iconTopActive;
    
    public LOTRBlockMillstone() {
        super(Material.rock);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabUtil);
        this.setHardness(4.0f);
        this.setStepSound(Block.soundTypeStone);
    }
    
    public TileEntity createNewTileEntity(final World world, final int i) {
        return new LOTRTileEntityMillstone();
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final IBlockAccess world, final int i, final int j, final int k, final int side) {
        final boolean active = isMillstoneActive(world, i, j, k);
        if (side == 1 || side == 0) {
            return active ? this.iconTopActive : this.iconTop;
        }
        return active ? this.iconSideActive : this.iconSide;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        return (i == 1 || i == 0) ? this.iconTop : this.iconSide;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        this.iconSide = iconregister.registerIcon(this.getTextureName() + "_side");
        this.iconTop = iconregister.registerIcon(this.getTextureName() + "_top");
        this.iconSideActive = iconregister.registerIcon(this.getTextureName() + "_side_active");
        this.iconTopActive = iconregister.registerIcon(this.getTextureName() + "_top_active");
    }
    
    public boolean onBlockActivated(final World world, final int i, final int j, final int k, final EntityPlayer entityplayer, final int side, final float f, final float f1, final float f2) {
        if (!world.isClient) {
            entityplayer.openGui((Object)LOTRMod.instance, 52, world, i, j, k);
        }
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(final World world, final int i, final int j, final int k, final Random random) {
        if (isMillstoneActive((IBlockAccess)world, i, j, k)) {
            for (int l = 0; l < 6; ++l) {
                final float f10 = 0.5f + MathHelper.randomFloatClamp(random, -0.2f, 0.2f);
                final float f11 = 0.5f + MathHelper.randomFloatClamp(random, -0.2f, 0.2f);
                final float f12 = 0.9f + random.nextFloat() * 0.2f;
                world.spawnParticle("smoke", (double)(i + f10), (double)(j + f12), (double)(k + f11), 0.0, 0.0, 0.0);
            }
        }
    }
    
    public void onBlockPlacedBy(final World world, final int i, final int j, final int k, final EntityLivingBase entity, final ItemStack itemstack) {
        if (itemstack.hasDisplayName()) {
            ((LOTRTileEntityMillstone)world.getTileEntity(i, j, k)).setSpecialMillstoneName(itemstack.getDisplayName());
        }
    }
    
    public static boolean isMillstoneActive(final IBlockAccess world, final int i, final int j, final int k) {
        final int meta = world.getBlockMetadata(i, j, k);
        return (meta & 0x8) != 0x0;
    }
    
    public static void toggleMillstoneActive(final World world, final int i, final int j, final int k) {
        final int meta = world.getBlockMetadata(i, j, k);
        world.setBlockMetadata(i, j, k, meta ^ 0x8, 2);
    }
    
    public void breakBlock(final World world, final int i, final int j, final int k, final Block block, final int meta) {
        final LOTRTileEntityMillstone millstone = (LOTRTileEntityMillstone)world.getTileEntity(i, j, k);
        if (millstone != null) {
            LOTRMod.dropContainerItems((IInventory)millstone, world, i, j, k);
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
