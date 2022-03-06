// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import java.util.List;
import lotr.common.tileentity.LOTRTileEntityGulduril;
import net.minecraft.tileentity.TileEntity;
import java.util.ArrayList;
import net.minecraft.client.renderer.texture.IIconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRMod;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import net.minecraft.block.Block;

public class LOTRBlockGuldurilBrick extends Block
{
    public LOTRBlockGuldurilBrick() {
        super(Material.rock);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabBlock);
        this.setHardness(3.0f);
        this.setResistance(10.0f);
        this.setStepSound(Block.soundTypeStone);
        this.setLightLevel(0.75f);
    }
    
    public static int guldurilMetaForBlock(final Block block, final int i) {
        if (block == null) {
            return -1;
        }
        if (block == LOTRMod.brick && i == 0) {
            return 0;
        }
        if (block == LOTRMod.brick && i == 7) {
            return 1;
        }
        if (block == LOTRMod.brick2 && i == 0) {
            return 2;
        }
        if (block == LOTRMod.brick2 && i == 1) {
            return 3;
        }
        if (block == LOTRMod.brick2 && i == 8) {
            return 4;
        }
        if (block == LOTRMod.brick2 && i == 9) {
            return 5;
        }
        if (block == LOTRMod.brick && i == 1) {
            return 6;
        }
        if (block == LOTRMod.brick && i == 2) {
            return 7;
        }
        if (block == LOTRMod.brick && i == 3) {
            return 8;
        }
        if (block == LOTRMod.brick2 && i == 11) {
            return 9;
        }
        return -1;
    }
    
    public static ItemStack blockForGuldurilMeta(final int i) {
        if (i == 0) {
            return new ItemStack(LOTRMod.brick, 1, 0);
        }
        if (i == 1) {
            return new ItemStack(LOTRMod.brick, 1, 7);
        }
        if (i == 2) {
            return new ItemStack(LOTRMod.brick2, 1, 0);
        }
        if (i == 3) {
            return new ItemStack(LOTRMod.brick2, 1, 1);
        }
        if (i == 4) {
            return new ItemStack(LOTRMod.brick2, 1, 8);
        }
        if (i == 5) {
            return new ItemStack(LOTRMod.brick2, 1, 9);
        }
        if (i == 6) {
            return new ItemStack(LOTRMod.brick, 1, 1);
        }
        if (i == 7) {
            return new ItemStack(LOTRMod.brick, 1, 2);
        }
        if (i == 8) {
            return new ItemStack(LOTRMod.brick, 1, 3);
        }
        if (i == 9) {
            return new ItemStack(LOTRMod.brick2, 1, 11);
        }
        return null;
    }
    
    public ItemStack getPickBlock(final MovingObjectPosition target, final World world, final int i, final int j, final int k) {
        return new ItemStack((Block)this, 1, world.getBlockMetadata(i, j, k));
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        final ItemStack itemstack = blockForGuldurilMeta(j);
        if (itemstack != null) {
            final Item item = itemstack.getItem();
            if (item instanceof ItemBlock) {
                final Block block = ((ItemBlock)item).field_150939_a;
                final int meta = itemstack.getItemDamage();
                return block.getIcon(i, meta);
            }
        }
        return LOTRMod.brick.getIcon(i, 0);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
    }
    
    public int getRenderType() {
        return LOTRMod.proxy.getGuldurilRenderID();
    }
    
    public ArrayList getDrops(final World world, final int i, final int j, final int k, final int metadata, final int fortune) {
        final ArrayList drops = new ArrayList();
        final ItemStack drop = blockForGuldurilMeta(metadata);
        if (drop != null) {
            drops.add(drop);
        }
        return drops;
    }
    
    public boolean hasTileEntity(final int metadata) {
        return true;
    }
    
    public TileEntity createTileEntity(final World world, final int metadata) {
        return new LOTRTileEntityGulduril();
    }
    
    protected boolean canSilkHarvest() {
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(final Item item, final CreativeTabs tab, final List list) {
        for (int i = 0; i <= 9; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }
}
