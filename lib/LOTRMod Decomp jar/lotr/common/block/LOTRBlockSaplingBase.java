// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.item.ItemStack;
import java.util.List;
import net.minecraft.item.Item;
import net.minecraft.block.Block;
import net.minecraftforge.event.terraingen.TerrainGen;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;

public abstract class LOTRBlockSaplingBase extends LOTRBlockFlower
{
    @SideOnly(Side.CLIENT)
    private IIcon[] saplingIcons;
    private String[] saplingNames;
    
    public LOTRBlockSaplingBase() {
        final float f = 0.4f;
        this.setBlockBounds(0.5f - f, 0.0f, 0.5f - f, 0.5f + f, f * 2.0f, 0.5f + f);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabDeco);
    }
    
    public void setSaplingNames(final String... s) {
        this.saplingNames = s;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, int j) {
        j &= 0x7;
        if (j >= this.saplingNames.length) {
            j = 0;
        }
        return this.saplingIcons[j];
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        this.saplingIcons = new IIcon[this.saplingNames.length];
        for (int i = 0; i < this.saplingNames.length; ++i) {
            this.saplingIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + this.saplingNames[i]);
        }
    }
    
    public void updateTick(final World world, final int i, final int j, final int k, final Random random) {
        if (!world.isClient) {
            super.updateTick(world, i, j, k, random);
            if (world.getBlockLightValue(i, j + 1, k) >= 9 && random.nextInt(7) == 0) {
                this.incrementGrowth(world, i, j, k, random);
            }
        }
    }
    
    public void incrementGrowth(final World world, final int i, final int j, final int k, final Random random) {
        final int meta = world.getBlockMetadata(i, j, k);
        if ((meta & 0x8) == 0x0) {
            world.setBlockMetadata(i, j, k, meta | 0x8, 4);
        }
        else {
            if (!TerrainGen.saplingGrowTree(world, random, i, j, k)) {
                return;
            }
            this.growTree(world, i, j, k, random);
        }
    }
    
    public abstract void growTree(final World p0, final int p1, final int p2, final int p3, final Random p4);
    
    public boolean isSameSapling(final World world, final int i, final int j, final int k, final int meta) {
        return isSameSapling(world, i, j, k, (Block)this, meta);
    }
    
    public static boolean isSameSapling(final World world, final int i, final int j, final int k, final Block block, final int meta) {
        if (world.getBlock(i, j, k) == block) {
            final int blockMeta = world.getBlockMetadata(i, j, k);
            return (blockMeta & 0x7) == meta;
        }
        return false;
    }
    
    public static int[] findPartyTree(final World world, final int i, final int j, final int k, final Block block, final int meta) {
        return findSaplingSquare(world, i, j, k, block, meta, -1, 1, -2, 2);
    }
    
    public static int[] findSaplingSquare(final World world, final int i, final int j, final int k, final Block block, final int meta, final int squareMin, final int squareMax, final int searchMin, final int searchMax) {
        for (int i2 = searchMin; i2 <= searchMax; ++i2) {
            for (int k2 = searchMin; k2 <= searchMax; ++k2) {
                boolean canGenerate = true;
            Label_0099:
                for (int i3 = squareMin; i3 <= squareMax; ++i3) {
                    for (int k3 = squareMin; k3 <= squareMax; ++k3) {
                        final int i4 = i + i2 + i3;
                        final int k4 = k + k2 + k3;
                        if (!isSameSapling(world, i4, j, k4, block, meta)) {
                            canGenerate = false;
                            break Label_0099;
                        }
                    }
                }
                if (canGenerate) {
                    return new int[] { i2, k2 };
                }
            }
        }
        return null;
    }
    
    public static int[] findCrossShape(final World world, final int i, final int j, final int k, final Block block, final int meta) {
        for (int i2 = -2; i2 <= 2; ++i2) {
            for (int k2 = -2; k2 <= 2; ++k2) {
                if (Math.abs(i2) == 0 || Math.abs(k2) == 0) {
                    boolean canGenerate = true;
                Label_0131:
                    for (int i3 = -1; i3 <= 1; ++i3) {
                        for (int k3 = -1; k3 <= 1; ++k3) {
                            if (Math.abs(i3) == 0 || Math.abs(k3) == 0) {
                                final int i4 = i + i2 + i3;
                                final int k4 = k + k2 + k3;
                                if (!isSameSapling(world, i4, j, k4, block, meta)) {
                                    canGenerate = false;
                                    break Label_0131;
                                }
                            }
                        }
                    }
                    if (canGenerate) {
                        return new int[] { i2, k2 };
                    }
                }
            }
        }
        return null;
    }
    
    public int damageDropped(final int i) {
        return i & 0x7;
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(final Item item, final CreativeTabs tab, final List list) {
        for (int i = 0; i < this.saplingNames.length; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }
    
    @Override
    public int getRenderType() {
        return 1;
    }
}
