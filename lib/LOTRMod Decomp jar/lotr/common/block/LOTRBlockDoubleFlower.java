// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.entity.Entity;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.IBlockAccess;
import lotr.common.LOTRMod;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.block.BlockDoublePlant;

public class LOTRBlockDoubleFlower extends BlockDoublePlant
{
    public static final String[] flowerNames;
    @SideOnly(Side.CLIENT)
    private IIcon[] doublePlantBottomIcons;
    @SideOnly(Side.CLIENT)
    private IIcon[] doublePlantTopIcons;
    
    public LOTRBlockDoubleFlower() {
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabDeco);
    }
    
    public int getRenderType() {
        return LOTRMod.proxy.getDoublePlantRenderID();
    }
    
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(final IBlockAccess world, final int i, final int j, final int k) {
        return 16777215;
    }
    
    public void setBlockBoundsBasedOnState(final IBlockAccess world, final int i, final int j, final int k) {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public int func_149885_e(final IBlockAccess world, final int i, final int j, final int k) {
        final int l = world.getBlockMetadata(i, j, k);
        return isTop(l) ? (world.getBlockMetadata(i, j - 1, k) & 0x7) : (l & 0x7);
    }
    
    public boolean canPlaceBlockAt(final World world, final int i, final int j, final int k) {
        return super.canPlaceBlockAt(world, i, j, k) && world.isAirBlock(i, j + 1, k);
    }
    
    protected void func_149855_e(final World world, final int i, final int j, final int k) {
        if (!this.canBlockStay(world, i, j, k)) {
            final int l = world.getBlockMetadata(i, j, k);
            if (!isTop(l)) {
                this.dropBlockAsItem(world, i, j, k, l, 0);
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
        return isTop(l) ? (world.getBlock(i, j - 1, k) == this) : (world.getBlock(i, j + 1, k) == this && super.canBlockStay(world, i, j, k));
    }
    
    public Item getItemDropped(final int i, final Random random, final int j) {
        if (isTop(i)) {
            return null;
        }
        return Item.getItemFromBlock((Block)this);
    }
    
    public int damageDropped(final int i) {
        return isTop(i) ? 0 : (i & 0x7);
    }
    
    public static boolean isTop(final int i) {
        return (i & 0x8) != 0x0;
    }
    
    public static int getFlowerMeta(final int i) {
        return i & 0x7;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        if (isTop(j)) {
            return this.doublePlantBottomIcons[1];
        }
        int k = j & 0x7;
        if (k >= this.doublePlantBottomIcons.length) {
            k = 0;
        }
        return this.doublePlantBottomIcons[k];
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon func_149888_a(final boolean isTop, int i) {
        if (isTop) {
            if (i >= this.doublePlantTopIcons.length) {
                i = 0;
            }
            return this.doublePlantTopIcons[i];
        }
        if (i >= this.doublePlantBottomIcons.length) {
            i = 0;
        }
        return this.doublePlantBottomIcons[i];
    }
    
    public void onBlockPlacedBy(final World world, final int i, final int j, final int k, final EntityLivingBase entity, final ItemStack itemstack) {
        final int l = ((MathHelper.floor_double(((Entity)entity).rotationYaw * 4.0f / 360.0f + 0.5) & 0x3) + 2) % 4;
        world.setBlock(i, j + 1, k, (Block)this, 0x8 | l, 2);
    }
    
    public void onBlockHarvested(final World world, final int i, final int j, final int k, final int meta, final EntityPlayer entityplayer) {
        if (isTop(meta)) {
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
    public void registerBlockIcons(final IIconRegister iconregister) {
        this.doublePlantBottomIcons = new IIcon[LOTRBlockDoubleFlower.flowerNames.length];
        this.doublePlantTopIcons = new IIcon[LOTRBlockDoubleFlower.flowerNames.length];
        for (int i = 0; i < this.doublePlantBottomIcons.length; ++i) {
            this.doublePlantBottomIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + LOTRBlockDoubleFlower.flowerNames[i] + "_bottom");
            this.doublePlantTopIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + LOTRBlockDoubleFlower.flowerNames[i] + "_top");
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(final Item item, final CreativeTabs tab, final List list) {
        for (int i = 0; i < this.doublePlantBottomIcons.length; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }
    
    public int getDamageValue(final World world, final int i, final int j, final int k) {
        final int l = world.getBlockMetadata(i, j, k);
        return isTop(l) ? getFlowerMeta(world.getBlockMetadata(i, j - 1, k)) : getFlowerMeta(l);
    }
    
    public boolean func_149851_a(final World world, final int i, final int j, final int k, final boolean flag) {
        return true;
    }
    
    public boolean func_149852_a(final World world, final Random random, final int i, final int j, final int k) {
        return true;
    }
    
    public void func_149853_b(final World world, final Random random, final int i, final int j, final int k) {
        final int meta = this.func_149885_e((IBlockAccess)world, i, j, k);
        this.dropBlockAsItem_do(world, i, j, k, new ItemStack((Block)this, 1, meta));
    }
    
    static {
        flowerNames = new String[] { "blackIris", "yellowIris", "pink", "red" };
    }
}
