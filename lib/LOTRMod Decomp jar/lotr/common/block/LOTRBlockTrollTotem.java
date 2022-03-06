// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.entity.Entity;
import java.util.List;
import net.minecraft.item.Item;
import lotr.common.fac.LOTRFaction;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.client.renderer.texture.IIconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import lotr.common.LOTRMod;
import lotr.common.tileentity.LOTRTileEntityTrollTotem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockContainer;

public class LOTRBlockTrollTotem extends BlockContainer
{
    public LOTRBlockTrollTotem() {
        super(Material.rock);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabUtil);
    }
    
    public TileEntity createNewTileEntity(final World world, final int i) {
        return new LOTRTileEntityTrollTotem();
    }
    
    public boolean isOpaqueCube() {
        return false;
    }
    
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    public int getRenderType() {
        return LOTRMod.proxy.getTrollTotemRenderID();
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        return Blocks.stone.getIcon(i, 0);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
    }
    
    public void onBlockPlacedBy(final World world, final int i, final int j, final int k, final EntityLivingBase entity, final ItemStack itemstack) {
        final int meta = itemstack.getItemDamage();
        final int rotation = MathHelper.floor_double(((Entity)entity).rotationYaw * 4.0f / 360.0f + 0.5) & 0x3;
        world.setBlockMetadata(i, j, k, meta | rotation << 2, 2);
        if (meta == 0 && world.getBlock(i, j - 1, k) == this && (world.getBlockMetadata(i, j - 1, k) & 0x3) == 0x1) {
            world.setBlockMetadata(i, j - 1, k, 0x1 | rotation << 2, 3);
            if (world.getBlock(i, j - 2, k) == this && (world.getBlockMetadata(i, j - 2, k) & 0x3) == 0x2) {
                world.setBlockMetadata(i, j - 2, k, 0x2 | rotation << 2, 3);
            }
        }
        if (meta == 1) {
            if (world.getBlock(i, j - 1, k) == this && (world.getBlockMetadata(i, j - 1, k) & 0x3) == 0x2) {
                world.setBlockMetadata(i, j - 1, k, 0x2 | rotation << 2, 3);
            }
            if (world.getBlock(i, j + 1, k) == this && (world.getBlockMetadata(i, j + 1, k) & 0x3) == 0x0) {
                world.setBlockMetadata(i, j + 1, k, 0x0 | rotation << 2, 3);
            }
        }
        if (meta == 2 && world.getBlock(i, j + 1, k) == this && (world.getBlockMetadata(i, j + 1, k) & 0x3) == 0x1) {
            world.setBlockMetadata(i, j + 1, k, 0x1 | rotation << 2, 3);
            if (world.getBlock(i, j + 2, k) == this && (world.getBlockMetadata(i, j + 2, k) & 0x3) == 0x0) {
                world.setBlockMetadata(i, j + 2, k, 0x0 | rotation << 2, 3);
            }
        }
    }
    
    public boolean onBlockActivated(final World world, final int i, final int j, final int k, final EntityPlayer entityplayer, final int side, final float f, final float f1, final float f2) {
        if ((world.getBlockMetadata(i, j, k) & 0x3) == 0x0) {
            final TileEntity tileentity = world.getTileEntity(i, j, k);
            if (tileentity instanceof LOTRTileEntityTrollTotem) {
                final LOTRTileEntityTrollTotem totem = (LOTRTileEntityTrollTotem)tileentity;
                if (totem.canSummon() && LOTRLevelData.getData(entityplayer).getAlignment(LOTRFaction.ANGMAR) < 0.0f) {
                    final ItemStack itemstack = entityplayer.inventory.getCurrentItem();
                    if (itemstack != null && LOTRMod.isOreNameEqual(itemstack, "bone")) {
                        if (!entityplayer.capabilities.isCreativeMode) {
                            final ItemStack itemStack = itemstack;
                            --itemStack.stackSize;
                            if (itemstack.stackSize <= 0) {
                                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, (ItemStack)null);
                            }
                        }
                        totem.summon();
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public int damageDropped(final int i) {
        return i & 0x3;
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(final Item item, final CreativeTabs tab, final List list) {
        for (int i = 0; i <= 2; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }
}
