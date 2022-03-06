// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.util.MovingObjectPosition;
import lotr.common.item.LOTRItemAnimalJar;
import net.minecraft.item.ItemStack;
import java.util.ArrayList;
import net.minecraft.item.Item;
import java.util.Random;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import lotr.common.tileentity.LOTRTileEntityAnimalJar;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.entity.Entity;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockContainer;

public abstract class LOTRBlockAnimalJar extends BlockContainer
{
    public LOTRBlockAnimalJar(final Material material) {
        super(material);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabDeco);
    }
    
    public abstract boolean canCapture(final Entity p0);
    
    public abstract float getJarEntityHeight();
    
    public TileEntity createNewTileEntity(final World world, final int i) {
        return new LOTRTileEntityAnimalJar();
    }
    
    public boolean isOpaqueCube() {
        return false;
    }
    
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    public boolean canBlockStay(final World world, final int i, final int j, final int k) {
        return world.getBlock(i, j - 1, k).isSideSolid((IBlockAccess)world, i, j - 1, k, ForgeDirection.UP);
    }
    
    public boolean canPlaceBlockAt(final World world, final int i, final int j, final int k) {
        return this.canBlockStay(world, i, j, k);
    }
    
    public void onNeighborBlockChange(final World world, final int i, final int j, final int k, final Block block) {
        if (!this.canBlockStay(world, i, j, k)) {
            final int meta = world.getBlockMetadata(i, j, k);
            this.dropBlockAsItem(world, i, j, k, meta, 0);
            world.setBlockToAir(i, j, k);
        }
    }
    
    public void onBlockHarvested(final World world, final int i, final int j, final int k, int meta, final EntityPlayer entityplayer) {
        if (entityplayer.capabilities.isCreativeMode) {
            meta |= 0x8;
            world.setBlockMetadata(i, j, k, meta, 4);
        }
        this.dropBlockAsItem(world, i, j, k, meta, 0);
        super.onBlockHarvested(world, i, j, k, meta, entityplayer);
    }
    
    public Item getItemDropped(final int i, final Random random, final int j) {
        return null;
    }
    
    public int damageDropped(final int i) {
        return i;
    }
    
    public ArrayList<ItemStack> getDrops(final World world, final int i, final int j, final int k, final int meta, final int fortune) {
        final ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
        if ((meta & 0x8) == 0x0) {
            final ItemStack itemstack = this.getJarDrop(world, i, j, k, meta);
            final LOTRTileEntityAnimalJar jar = (LOTRTileEntityAnimalJar)world.getTileEntity(i, j, k);
            if (jar != null) {
                drops.add(itemstack);
            }
        }
        return drops;
    }
    
    public ItemStack getJarDrop(final World world, final int i, final int j, final int k, final int metadata) {
        final ItemStack itemstack = new ItemStack(Item.getItemFromBlock((Block)this), 1, this.damageDropped(metadata));
        final LOTRTileEntityAnimalJar jar = (LOTRTileEntityAnimalJar)world.getTileEntity(i, j, k);
        if (jar != null) {
            LOTRItemAnimalJar.setEntityData(itemstack, jar.getEntityData());
        }
        return itemstack;
    }
    
    public int getLightValue(final IBlockAccess world, final int i, final int j, final int k) {
        final TileEntity te = world.getTileEntity(i, j, k);
        if (te instanceof LOTRTileEntityAnimalJar) {
            final LOTRTileEntityAnimalJar jar = (LOTRTileEntityAnimalJar)te;
            final int light = jar.getLightValue();
            if (light > 0) {
                return light;
            }
        }
        return super.getLightValue(world, i, j, k);
    }
    
    public ItemStack getPickBlock(final MovingObjectPosition target, final World world, final int i, final int j, final int k) {
        world.markBlockForUpdate(i, j, k);
        return this.getJarDrop(world, i, j, k, world.getBlockMetadata(i, j, k));
    }
}
