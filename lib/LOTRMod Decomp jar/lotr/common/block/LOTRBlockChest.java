// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.MathHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import lotr.common.LOTRMod;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.tileentity.LOTRTileEntityChest;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;

public class LOTRBlockChest extends BlockContainer
{
    private Block baseBlock;
    private int baseMeta;
    private String chestTextureName;
    
    public LOTRBlockChest(final Material m, final Block b, final int i, final String s) {
        super(m);
        this.baseBlock = b;
        this.baseMeta = i;
        this.setStepSound(b.stepSound);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabUtil);
        this.setBlockBounds(0.0625f, 0.0f, 0.0625f, 0.9375f, 0.875f, 0.9375f);
        this.chestTextureName = s;
    }
    
    public TileEntity createNewTileEntity(final World world, final int i) {
        final LOTRTileEntityChest chest = new LOTRTileEntityChest();
        chest.textureName = this.getChestTextureName();
        return chest;
    }
    
    public String getChestTextureName() {
        return this.chestTextureName;
    }
    
    public boolean onBlockActivated(final World world, final int i, final int j, final int k, final EntityPlayer entityplayer, final int side, final float f, final float f1, final float f2) {
        if (world.isSideSolid(i, j + 1, k, ForgeDirection.DOWN)) {
            return false;
        }
        if (!world.isClient) {
            entityplayer.openGui((Object)LOTRMod.instance, 41, world, i, j, k);
        }
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        return this.baseBlock.getIcon(i, this.baseMeta);
    }
    
    public boolean isOpaqueCube() {
        return false;
    }
    
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    public int getRenderType() {
        return LOTRMod.proxy.getChestRenderID();
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
        int meta = 0;
        final int l = MathHelper.floor_double(((Entity)entity).rotationYaw * 4.0f / 360.0f + 0.5) & 0x3;
        if (l == 0) {
            meta = 2;
        }
        if (l == 1) {
            meta = 5;
        }
        if (l == 2) {
            meta = 3;
        }
        if (l == 3) {
            meta = 4;
        }
        world.setBlockMetadata(i, j, k, meta, 3);
        if (itemstack.hasDisplayName()) {
            ((LOTRTileEntityChest)world.getTileEntity(i, j, k)).setCustomName(itemstack.getDisplayName());
        }
    }
    
    public void breakBlock(final World world, final int i, final int j, final int k, final Block block, final int meta) {
        final LOTRTileEntityChest chest = (LOTRTileEntityChest)world.getTileEntity(i, j, k);
        if (chest != null) {
            LOTRMod.dropContainerItems((IInventory)chest, world, i, j, k);
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
