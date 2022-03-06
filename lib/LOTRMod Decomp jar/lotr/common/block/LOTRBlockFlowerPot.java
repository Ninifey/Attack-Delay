// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.init.Items;
import java.util.ArrayList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import lotr.common.LOTRMod;
import net.minecraft.client.renderer.texture.IIconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import lotr.common.tileentity.LOTRTileEntityFlowerPot;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.BlockFlowerPot;

public class LOTRBlockFlowerPot extends BlockFlowerPot implements ITileEntityProvider
{
    public TileEntity createNewTileEntity(final World world, final int i) {
        return new LOTRTileEntityFlowerPot();
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        return Blocks.flower_pot.getIcon(i, j);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
    }
    
    public int getRenderType() {
        return LOTRMod.proxy.getFlowerPotRenderID();
    }
    
    public ItemStack getPickBlock(final MovingObjectPosition target, final World world, final int i, final int j, final int k) {
        return getPlant((IBlockAccess)world, i, j, k);
    }
    
    public boolean onBlockActivated(final World world, final int i, final int j, final int k, final EntityPlayer entityplayer, final int side, final float f, final float f1, final float f2) {
        return false;
    }
    
    public void onBlockHarvested(final World world, final int i, final int j, final int k, int meta, final EntityPlayer entityplayer) {
        if (entityplayer.capabilities.isCreativeMode) {
            meta |= 0x8;
            world.setBlockMetadata(i, j, k, meta, 4);
        }
        super.onBlockHarvested(world, i, j, k, meta, entityplayer);
    }
    
    public ArrayList<ItemStack> getDrops(final World world, final int i, final int j, final int k, final int meta, final int fortune) {
        final ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
        drops.add(new ItemStack(Items.flower_pot));
        if ((meta & 0x8) == 0x0) {
            final ItemStack itemstack = getPlant((IBlockAccess)world, i, j, k);
            if (itemstack != null) {
                final LOTRTileEntityFlowerPot pot = (LOTRTileEntityFlowerPot)world.getTileEntity(i, j, k);
                if (pot != null) {
                    drops.add(itemstack);
                }
            }
        }
        return drops;
    }
    
    public static boolean canAcceptPlant(final ItemStack itemstack) {
        final Item item = itemstack.getItem();
        if (item instanceof ItemBlock) {
            final Block block = ((ItemBlock)item).field_150939_a;
            return block instanceof LOTRBlockFlower;
        }
        return false;
    }
    
    public static void setPlant(final World world, final int i, final int j, final int k, final ItemStack itemstack) {
        final TileEntity tileentity = world.getTileEntity(i, j, k);
        if (tileentity instanceof LOTRTileEntityFlowerPot) {
            final LOTRTileEntityFlowerPot flowerPot = (LOTRTileEntityFlowerPot)tileentity;
            flowerPot.item = itemstack.getItem();
            flowerPot.meta = itemstack.getItemDamage();
            world.markBlockForUpdate(i, j, k);
        }
    }
    
    public static ItemStack getPlant(final IBlockAccess world, final int i, final int j, final int k) {
        final TileEntity tileentity = world.getTileEntity(i, j, k);
        if (tileentity == null || !(tileentity instanceof LOTRTileEntityFlowerPot)) {
            return null;
        }
        final LOTRTileEntityFlowerPot flowerPot = (LOTRTileEntityFlowerPot)tileentity;
        if (flowerPot.item == null) {
            return null;
        }
        return new ItemStack(flowerPot.item, 1, flowerPot.meta);
    }
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(final World world, final int i, final int j, final int k, final Random random) {
        if (getPlant((IBlockAccess)world, i, j, k) != null && getPlant((IBlockAccess)world, i, j, k).getItem() == Item.getItemFromBlock(LOTRMod.pipeweedPlant)) {
            final double d = i + 0.2 + random.nextFloat() * 0.6f;
            final double d2 = j + 0.625 + random.nextFloat() * 0.1875f;
            final double d3 = k + 0.2 + random.nextFloat() * 0.6f;
            world.spawnParticle("smoke", d, d2, d3, 0.0, 0.0, 0.0);
        }
        if (getPlant((IBlockAccess)world, i, j, k) != null && getPlant((IBlockAccess)world, i, j, k).getItem() == Item.getItemFromBlock(LOTRMod.corruptMallorn)) {
            LOTRMod.corruptMallorn.randomDisplayTick(world, i, j, k, random);
        }
    }
}
