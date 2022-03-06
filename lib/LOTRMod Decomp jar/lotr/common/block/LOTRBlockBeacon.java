// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.block.BlockTorch;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import lotr.common.LOTRMod;
import lotr.common.tileentity.LOTRTileEntityBeacon;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.client.renderer.texture.IIconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockContainer;

public class LOTRBlockBeacon extends BlockContainer
{
    public LOTRBlockBeacon() {
        super(Material.wood);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabUtil);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.8125f, 1.0f);
        this.setHardness(0.0f);
        this.setResistance(5.0f);
        this.setStepSound(Block.soundTypeWood);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        return Blocks.planks.getIcon(i, 0);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
    }
    
    public TileEntity createNewTileEntity(final World world, final int i) {
        return new LOTRTileEntityBeacon();
    }
    
    public boolean isOpaqueCube() {
        return false;
    }
    
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    public int getRenderType() {
        return LOTRMod.proxy.getBeaconRenderID();
    }
    
    public boolean canBlockStay(final World world, final int i, final int j, final int k) {
        return world.getBlock(i, j - 1, k).isSideSolid((IBlockAccess)world, i, j - 1, k, ForgeDirection.UP);
    }
    
    public boolean canPlaceBlockAt(final World world, final int i, final int j, final int k) {
        return this.canBlockStay(world, i, j, k);
    }
    
    public void onNeighborBlockChange(final World world, final int i, final int j, final int k, final Block block) {
        if (!this.canBlockStay(world, i, j, k)) {
            this.dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
            world.setBlockToAir(i, j, k);
        }
        else if (isLit((IBlockAccess)world, i, j, k) && world.getBlock(i, j + 1, k).getMaterial() == Material.water) {
            world.playSoundEffect(i + 0.5, j + 0.5, k + 0.5, "random.fizz", 0.5f, 2.6f + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8f);
            if (!world.isClient) {
                setLit(world, i, j, k, false);
            }
        }
    }
    
    public boolean onBlockActivated(final World world, final int i, final int j, final int k, final EntityPlayer entityplayer, final int side, final float f, final float f1, final float f2) {
        final ItemStack itemstack = entityplayer.getCurrentEquippedItem();
        if (this.canItemLightBeacon(itemstack) && !isLit((IBlockAccess)world, i, j, k) && world.getBlock(i, j + 1, k).getMaterial() != Material.water) {
            world.playSoundEffect(i + 0.5, j + 0.5, k + 0.5, "fire.ignite", 1.0f, world.rand.nextFloat() * 0.4f + 0.8f);
            if (itemstack.getItem().isDamageable()) {
                itemstack.damageItem(1, (EntityLivingBase)entityplayer);
            }
            if (!world.isClient) {
                setLit(world, i, j, k, true);
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.lightGondorBeacon);
            }
            return true;
        }
        if (itemstack != null && itemstack.getItem() == Items.water_bucket && isLit((IBlockAccess)world, i, j, k)) {
            world.playSoundEffect(i + 0.5, j + 0.5, k + 0.5, "random.fizz", 0.5f, 2.6f + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8f);
            if (!entityplayer.capabilities.isCreativeMode) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(Items.bucket));
            }
            if (!world.isClient) {
                setLit(world, i, j, k, false);
            }
            return true;
        }
        entityplayer.openGui((Object)LOTRMod.instance, 50, world, i, j, k);
        return true;
    }
    
    private boolean canItemLightBeacon(final ItemStack itemstack) {
        if (itemstack == null) {
            return false;
        }
        final Item item = itemstack.getItem();
        return item == Items.flint_and_steel || (item instanceof ItemBlock && ((ItemBlock)item).field_150939_a instanceof BlockTorch);
    }
    
    public int getLightValue(final IBlockAccess world, final int i, final int j, final int k) {
        return isFullyLit(world, i, j, k) ? 15 : 0;
    }
    
    public static boolean isLit(final IBlockAccess world, final int i, final int j, final int k) {
        final TileEntity tileentity = world.getTileEntity(i, j, k);
        if (tileentity instanceof LOTRTileEntityBeacon) {
            final LOTRTileEntityBeacon beacon = (LOTRTileEntityBeacon)tileentity;
            return beacon.isLit();
        }
        return false;
    }
    
    public static boolean isFullyLit(final IBlockAccess world, final int i, final int j, final int k) {
        final TileEntity tileentity = world.getTileEntity(i, j, k);
        if (tileentity instanceof LOTRTileEntityBeacon) {
            final LOTRTileEntityBeacon beacon = (LOTRTileEntityBeacon)tileentity;
            return beacon.isFullyLit();
        }
        return false;
    }
    
    public static void setLit(final World world, final int i, final int j, final int k, final boolean lit) {
        final TileEntity tileentity = world.getTileEntity(i, j, k);
        if (tileentity instanceof LOTRTileEntityBeacon) {
            final LOTRTileEntityBeacon beacon = (LOTRTileEntityBeacon)tileentity;
            beacon.setLit(lit);
        }
    }
    
    public void onEntityCollidedWithBlock(final World world, final int i, final int j, final int k, final Entity entity) {
        if (entity.isBurning() && !isLit((IBlockAccess)world, i, j, k) && world.getBlock(i, j + 1, k).getMaterial() != Material.water) {
            world.playSoundEffect(i + 0.5, j + 0.5, k + 0.5, "fire.ignite", 1.0f, world.rand.nextFloat() * 0.4f + 0.8f);
            if (!world.isClient) {
                setLit(world, i, j, k, true);
                entity.setDead();
            }
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(final World world, final int i, final int j, final int k, final Random random) {
        if (!isLit((IBlockAccess)world, i, j, k)) {
            return;
        }
        if (random.nextInt(24) == 0) {
            world.playSound(i + 0.5, j + 0.5, k + 0.5, "fire.fire", 1.0f + random.nextFloat(), random.nextFloat() * 0.7f + 0.3f, false);
        }
        for (int l = 0; l < 3; ++l) {
            final double d = i + random.nextFloat();
            final double d2 = j + random.nextFloat() * 0.5 + 0.5;
            final double d3 = k + random.nextFloat();
            world.spawnParticle("largesmoke", d, d2, d3, 0.0, 0.0, 0.0);
        }
    }
}
