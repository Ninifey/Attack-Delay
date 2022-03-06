// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import lotr.common.entity.item.LOTREntityBarrel;
import net.minecraft.block.material.Material;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.tileentity.LOTRTileEntityBarrel;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class LOTRItemBarrel extends ItemBlock
{
    public LOTRItemBarrel(final Block block) {
        super(block);
    }
    
    public static void setBarrelData(final ItemStack itemstack, final NBTTagCompound nbt) {
        itemstack.setTagCompound(new NBTTagCompound());
        itemstack.getTagCompound().setTag("LOTRBarrelData", (NBTBase)nbt);
    }
    
    public static NBTTagCompound getBarrelData(final ItemStack itemstack) {
        if (itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey("LOTRBarrelData")) {
            final NBTTagCompound barrelData = itemstack.getTagCompound().getCompoundTag("LOTRBarrelData");
            return barrelData;
        }
        return null;
    }
    
    public static void setBarrelDataFromTE(final ItemStack itemstack, final LOTRTileEntityBarrel barrel) {
        final NBTTagCompound nbt = new NBTTagCompound();
        barrel.writeBarrelToNBT(nbt);
        setBarrelData(itemstack, nbt);
    }
    
    public static void loadBarrelDataToTE(final ItemStack itemstack, final LOTRTileEntityBarrel barrel) {
        final NBTTagCompound nbt = getBarrelData(itemstack);
        if (nbt != null) {
            barrel.readBarrelFromNBT(nbt);
        }
    }
    
    public int getItemStackLimit(final ItemStack itemstack) {
        final NBTTagCompound nbt = getBarrelData(itemstack);
        if (nbt != null) {
            return 1;
        }
        return super.getItemStackLimit(itemstack);
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation(final ItemStack itemstack, final EntityPlayer entityplayer, final List list, final boolean flag) {
        final NBTTagCompound barrelData = getBarrelData(itemstack);
        if (barrelData != null) {
            final LOTRTileEntityBarrel tileEntity = new LOTRTileEntityBarrel();
            tileEntity.readBarrelFromNBT(barrelData);
            list.add(tileEntity.getInvSubtitle());
        }
    }
    
    public boolean placeBlockAt(final ItemStack itemstack, final EntityPlayer entityplayer, final World world, final int i, final int j, final int k, final int side, final float f, final float f1, final float f2, final int metadata) {
        if (super.placeBlockAt(itemstack, entityplayer, world, i, j, k, side, f, f1, f2, metadata)) {
            final TileEntity tileentity = world.getTileEntity(i, j, k);
            if (tileentity instanceof LOTRTileEntityBarrel) {
                final LOTRTileEntityBarrel barrel = (LOTRTileEntityBarrel)tileentity;
                loadBarrelDataToTE(itemstack, barrel);
            }
            return true;
        }
        return false;
    }
    
    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        final MovingObjectPosition m = this.getMovingObjectPositionFromPlayer(world, entityplayer, true);
        if (m == null) {
            return itemstack;
        }
        if (m.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            final int i = m.blockX;
            final int j = m.blockY;
            final int k = m.blockZ;
            if (world.getBlock(i, j, k).getMaterial() != Material.water || world.getBlockMetadata(i, j, k) != 0) {
                return itemstack;
            }
            final LOTREntityBarrel barrel = new LOTREntityBarrel(world, i + 0.5f, j + 1.0f, k + 0.5f);
            barrel.rotationYaw = ((MathHelper.floor_double(((Entity)entityplayer).rotationYaw * 4.0f / 360.0f + 0.5) & 0x3) - 1) * 90.0f;
            if (!world.getCollidingBoundingBoxes((Entity)barrel, barrel.boundingBox.expand(-0.1, -0.1, -0.1)).isEmpty()) {
                return itemstack;
            }
            if (!world.isClient) {
                final NBTTagCompound barrelData = getBarrelData(itemstack);
                barrel.barrelItemData = barrelData;
                world.spawnEntityInWorld((Entity)barrel);
            }
            if (!entityplayer.capabilities.isCreativeMode) {
                --itemstack.stackSize;
            }
        }
        return itemstack;
    }
}
