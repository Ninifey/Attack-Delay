// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.item.ItemStack;
import java.util.List;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockWall;
import net.minecraft.block.BlockFence;
import net.minecraft.world.World;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.block.Block;

public class LOTRBlockChandelier extends Block
{
    @SideOnly(Side.CLIENT)
    private IIcon[] chandelierIcons;
    private String[] chandelierNames;
    
    public LOTRBlockChandelier() {
        super(Material.circuits);
        this.chandelierNames = new String[] { "bronze", "iron", "silver", "gold", "mithril", "mallornSilver", "woodElven", "orc", "dwarven", "uruk", "highElven", "blueDwarven", "morgul", "mallornBlue", "mallornGold", "mallornGreen" };
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabDeco);
        this.setHardness(0.0f);
        this.setResistance(2.0f);
        this.setStepSound(Block.soundTypeMetal);
        this.setLightLevel(0.9375f);
        this.setBlockBounds(0.0625f, 0.1875f, 0.0625f, 0.9375f, 1.0f, 0.9375f);
    }
    
    public IIcon getIcon(final int i, int j) {
        if (j >= this.chandelierNames.length) {
            j = 0;
        }
        return this.chandelierIcons[j];
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        this.chandelierIcons = new IIcon[this.chandelierNames.length];
        for (int i = 0; i < this.chandelierNames.length; ++i) {
            this.chandelierIcons[i] = iconregister.registerIcon(this.getTextureName() + "_" + this.chandelierNames[i]);
        }
    }
    
    public int damageDropped(final int i) {
        return i;
    }
    
    public boolean isOpaqueCube() {
        return false;
    }
    
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    public int getRenderType() {
        return 1;
    }
    
    public boolean canBlockStay(final World world, final int i, final int j, final int k) {
        final Block block = world.getBlock(i, j + 1, k);
        final int meta = world.getBlockMetadata(i, j + 1, k);
        return block instanceof BlockFence || block instanceof BlockWall || (block instanceof BlockSlab && !block.isOpaqueCube() && (meta & 0x8) == 0x0) || (block instanceof BlockStairs && (meta & 0x4) == 0x0) || block instanceof LOTRBlockOrcChain || world.getBlock(i, j + 1, k).isSideSolid((IBlockAccess)world, i, j + 1, k, ForgeDirection.DOWN);
    }
    
    public boolean canPlaceBlockAt(final World world, final int i, final int j, final int k) {
        return this.canBlockStay(world, i, j, k);
    }
    
    public void onNeighborBlockChange(final World world, final int i, final int j, final int k, final Block block) {
        if (!this.canBlockStay(world, i, j, k)) {
            this.dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
            world.setBlockToAir(i, j, k);
        }
    }
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World world, final int i, final int j, final int k) {
        return null;
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(final Item item, final CreativeTabs tab, final List list) {
        for (int i = 0; i < this.chandelierNames.length; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(final World world, final int i, final int j, final int k, final Random random) {
        final int meta = world.getBlockMetadata(i, j, k);
        final double d = 0.13;
        final double d2 = 1.0 - d;
        final double d3 = 0.6875;
        this.spawnChandelierParticles(world, i + d, j + d3, k + d, random, meta);
        this.spawnChandelierParticles(world, i + d2, j + d3, k + d2, random, meta);
        this.spawnChandelierParticles(world, i + d, j + d3, k + d2, random, meta);
        this.spawnChandelierParticles(world, i + d2, j + d3, k + d, random, meta);
    }
    
    private void spawnChandelierParticles(final World world, final double d, final double d1, final double d2, final Random random, final int meta) {
        if (meta == 5 || meta == 13 || meta == 14 || meta == 15) {
            LOTRBlockTorch torchBlock = null;
            if (meta == 5) {
                torchBlock = (LOTRBlockTorch)LOTRMod.mallornTorchSilver;
            }
            else if (meta == 13) {
                torchBlock = (LOTRBlockTorch)LOTRMod.mallornTorchBlue;
            }
            else if (meta == 14) {
                torchBlock = (LOTRBlockTorch)LOTRMod.mallornTorchGold;
            }
            else if (meta == 15) {
                torchBlock = (LOTRBlockTorch)LOTRMod.mallornTorchGreen;
            }
            final LOTRBlockTorch.TorchParticle particle = torchBlock.createTorchParticle(random);
            if (particle != null) {
                particle.spawn(d, d1, d2);
            }
        }
        else if (meta == 6) {
            final String s = "leafRed_" + (10 + random.nextInt(20));
            final double d3 = -0.005 + random.nextFloat() * 0.01f;
            final double d4 = -0.005 + random.nextFloat() * 0.01f;
            final double d5 = -0.005 + random.nextFloat() * 0.01f;
            LOTRMod.proxy.spawnParticle(s, d, d1, d2, d3, d4, d5);
        }
        else if (meta == 10) {
            LOTRMod.proxy.spawnParticle("elvenGlow", d, d1, d2, 0.0, 0.0, 0.0);
        }
        else if (meta == 12) {
            final double d6 = -0.05 + random.nextFloat() * 0.1;
            final double d7 = 0.1 + random.nextFloat() * 0.1;
            final double d8 = -0.05 + random.nextFloat() * 0.1;
            LOTRMod.proxy.spawnParticle("morgulPortal", d, d1, d2, d6, d7, d8);
        }
        else {
            world.spawnParticle("smoke", d, d1, d2, 0.0, 0.0, 0.0);
            world.spawnParticle("flame", d, d1, d2, 0.0, 0.0, 0.0);
        }
    }
}
