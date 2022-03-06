// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraftforge.common.EnumPlantType;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.world.IBlockAccess;
import lotr.common.LOTRMod;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.item.Item;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.IPlantable;
import net.minecraft.block.Block;

public class LOTRBlockReed extends Block implements IPlantable
{
    private static int MAX_GROW_HEIGHT;
    private static int META_GROW_END;
    @SideOnly(Side.CLIENT)
    private IIcon iconUpper;
    @SideOnly(Side.CLIENT)
    private IIcon iconLower;
    
    public LOTRBlockReed() {
        super(Material.plants);
        final float f = 0.375f;
        this.setBlockBounds(0.5f - f, 0.0f, 0.5f - f, 0.5f + f, 1.0f, 0.5f + f);
        this.setTickRandomly(true);
        this.setHardness(0.0f);
        this.setStepSound(Block.soundTypeGrass);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabDeco);
    }
    
    public boolean canPlaceBlockAt(final World world, final int i, final int j, final int k) {
        final Block below = world.getBlock(i, j - 1, k);
        final int belowMeta = world.getBlockMetadata(i, j - 1, k);
        return below == this || (below.getMaterial() == Material.water && belowMeta == 0);
    }
    
    public boolean canBlockStay(final World world, final int i, final int j, final int k) {
        return this.canPlaceBlockAt(world, i, j, k);
    }
    
    public void onNeighborBlockChange(final World world, final int i, final int j, final int k, final Block block) {
        this.checkCanStay(world, i, j, k);
    }
    
    public void updateTick(final World world, final int i, final int j, final int k, final Random random) {
        if (this.checkCanStay(world, i, j, k) && this.canReedGrow(world, i, j, k) && world.isAirBlock(i, j + 1, k)) {
            int belowReeds;
            for (belowReeds = 1; world.getBlock(i, j - belowReeds, k) == this; ++belowReeds) {}
            if (belowReeds < LOTRBlockReed.MAX_GROW_HEIGHT) {
                final int meta = world.getBlockMetadata(i, j, k);
                if (meta == LOTRBlockReed.META_GROW_END) {
                    world.setBlock(i, j + 1, k, (Block)this, 0, 3);
                    world.setBlockMetadata(i, j, k, 0, 4);
                }
                else {
                    world.setBlockMetadata(i, j, k, meta + 1, 4);
                }
            }
        }
    }
    
    protected boolean canReedGrow(final World world, final int i, final int j, final int k) {
        return true;
    }
    
    private boolean checkCanStay(final World world, final int i, final int j, final int k) {
        if (!this.canBlockStay(world, i, j, k)) {
            final int meta = world.getBlockMetadata(i, j, k);
            this.dropBlockAsItem(world, i, j, k, meta, 0);
            world.setBlockToAir(i, j, k);
            return false;
        }
        return true;
    }
    
    public Item getItemDropped(final int i, final Random random, final int j) {
        return Item.getItemFromBlock((Block)this);
    }
    
    public int damageDropped(final int i) {
        return 0;
    }
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World world, final int i, final int j, final int k) {
        return null;
    }
    
    public boolean isOpaqueCube() {
        return false;
    }
    
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    public int getRenderType() {
        return LOTRMod.proxy.getReedsRenderID();
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final IBlockAccess world, final int i, final int j, final int k, final int side) {
        if (side == -2) {
            return this.iconLower;
        }
        if (side == -1) {
            return super.blockIcon;
        }
        final Block below = world.getBlock(i, j - 1, k);
        final Block above = world.getBlock(i, j + 1, k);
        if (above != this) {
            return this.iconUpper;
        }
        return super.blockIcon;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        if (i == -2) {
            return this.iconLower;
        }
        if (i == -1) {
            return super.blockIcon;
        }
        return super.blockIcon;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        super.blockIcon = iconregister.registerIcon(this.getTextureName() + "_mid");
        this.iconUpper = iconregister.registerIcon(this.getTextureName() + "_upper");
        this.iconLower = iconregister.registerIcon(this.getTextureName() + "_lower");
    }
    
    @SideOnly(Side.CLIENT)
    public String getItemIconName() {
        return this.getTextureName();
    }
    
    public EnumPlantType getPlantType(final IBlockAccess world, final int i, final int j, final int k) {
        return EnumPlantType.Water;
    }
    
    public Block getPlant(final IBlockAccess world, final int i, final int j, final int k) {
        return this;
    }
    
    public int getPlantMetadata(final IBlockAccess world, final int i, final int j, final int k) {
        return world.getBlockMetadata(i, j, k);
    }
    
    static {
        LOTRBlockReed.MAX_GROW_HEIGHT = 3;
        LOTRBlockReed.META_GROW_END = 15;
    }
}
