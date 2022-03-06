// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.client.renderer.texture.IIconRegister;
import lotr.common.LOTRMod;
import java.util.Collection;
import java.util.ArrayList;
import net.minecraft.item.Item;
import java.util.Iterator;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.EnumPlantType;
import net.minecraft.world.IBlockAccess;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.util.ForgeDirection;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.block.IGrowable;
import net.minecraftforge.common.IPlantable;
import net.minecraft.block.Block;

public class LOTRBlockCorn extends Block implements IPlantable, IGrowable
{
    public static int MAX_GROW_HEIGHT;
    private static int META_GROW_END;
    @SideOnly(Side.CLIENT)
    private IIcon cornIcon;
    
    public LOTRBlockCorn() {
        super(Material.plants);
        final float f = 0.375f;
        this.setBlockBounds(0.5f - f, 0.0f, 0.5f - f, 0.5f + f, 1.0f, 0.5f + f);
        this.setTickRandomly(true);
        this.setHardness(0.0f);
        this.setStepSound(Block.soundTypeGrass);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabDeco);
    }
    
    public void updateTick(final World world, final int i, final int j, final int k, final Random random) {
        if (this.checkCanStay(world, i, j, k)) {
            int cornHeight;
            for (cornHeight = 1; world.getBlock(i, j - cornHeight, k) == this; ++cornHeight) {}
            final float growth = this.getGrowthFactor(world, i, j - cornHeight + 1, k);
            if (world.isAirBlock(i, j + 1, k) && cornHeight < LOTRBlockCorn.MAX_GROW_HEIGHT) {
                int meta = world.getBlockMetadata(i, j, k);
                final int corn = meta & 0x8;
                int grow = meta & 0x7;
                if (grow == LOTRBlockCorn.META_GROW_END) {
                    world.setBlock(i, j + 1, k, (Block)this, 0, 3);
                    grow = 0;
                }
                else {
                    ++grow;
                }
                meta = (corn | grow);
                world.setBlockMetadata(i, j, k, meta, 4);
            }
            if (!hasCorn(world, i, j, k) && this.canGrowCorn(world, i, j, k) && world.rand.nextFloat() < growth) {
                setHasCorn(world, i, j, k, true);
            }
        }
    }
    
    private float getGrowthFactor(final World world, final int i, final int j, final int k) {
        float growth = 1.0f;
        final Block below = world.getBlock(i, j - 1, k);
        if (below.canSustainPlant((IBlockAccess)world, i, j - 1, k, ForgeDirection.UP, (IPlantable)Blocks.wheat)) {
            growth = 3.0f;
            if (below.isFertile(world, i, j - 1, k)) {
                growth = 9.0f;
            }
        }
        if (world.isRaining()) {
            growth *= 3.0f;
        }
        return growth / 250.0f;
    }
    
    private boolean canGrowCorn(final World world, final int i, final int j, final int k) {
        return world.getBlock(i, j - 1, k) == this;
    }
    
    public static boolean hasCorn(final World world, final int i, final int j, final int k) {
        final int meta = world.getBlockMetadata(i, j, k);
        return metaHasCorn(meta);
    }
    
    private static boolean metaHasCorn(final int l) {
        return (l & 0x8) != 0x0;
    }
    
    public static void setHasCorn(final World world, final int i, final int j, final int k, final boolean flag) {
        int meta = world.getBlockMetadata(i, j, k);
        if (flag) {
            meta |= 0x8;
        }
        else {
            meta &= 0x7;
        }
        world.setBlockMetadata(i, j, k, meta, 3);
    }
    
    public boolean canPlaceBlockAt(final World world, final int i, final int j, final int k) {
        final Block below = world.getBlock(i, j - 1, k);
        if (below == this) {
            return true;
        }
        final IPlantable beachTest = (IPlantable)new IPlantable() {
            public EnumPlantType getPlantType(final IBlockAccess world, final int i, final int j, final int k) {
                return EnumPlantType.Beach;
            }
            
            public Block getPlant(final IBlockAccess world, final int i, final int j, final int k) {
                return LOTRBlockCorn.this;
            }
            
            public int getPlantMetadata(final IBlockAccess world, final int i, final int j, final int k) {
                return 0;
            }
        };
        return below.canSustainPlant((IBlockAccess)world, i, j - 1, k, ForgeDirection.UP, (IPlantable)this) || below.canSustainPlant((IBlockAccess)world, i, j - 1, k, ForgeDirection.UP, beachTest);
    }
    
    public boolean canBlockStay(final World world, final int i, final int j, final int k) {
        return this.canPlaceBlockAt(world, i, j, k);
    }
    
    public void onNeighborBlockChange(final World world, final int i, final int j, final int k, final Block block) {
        this.checkCanStay(world, i, j, k);
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
        return 1;
    }
    
    public boolean onBlockActivated(final World world, final int i, final int j, final int k, final EntityPlayer entityplayer, final int l, final float f, final float f1, final float f2) {
        if (hasCorn(world, i, j, k)) {
            if (!world.isClient) {
                final int preMeta = world.getBlockMetadata(i, j, k);
                setHasCorn(world, i, j, k, false);
                if (!world.isClient) {
                    final List<ItemStack> cornDrops = this.getCornDrops(world, i, j, k, preMeta);
                    for (final ItemStack corn : cornDrops) {
                        this.dropBlockAsItem_do(world, i, j, k, corn);
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    public Item getItemDropped(final int i, final Random random, final int j) {
        return Item.getItemFromBlock((Block)this);
    }
    
    public int damageDropped(final int i) {
        return 0;
    }
    
    public ArrayList<ItemStack> getDrops(final World world, final int i, final int j, final int k, final int meta, final int fortune) {
        final ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
        drops.addAll(super.getDrops(world, i, j, k, meta, fortune));
        drops.addAll(this.getCornDrops(world, i, j, k, meta));
        return drops;
    }
    
    public ArrayList<ItemStack> getCornDrops(final World world, final int i, final int j, final int k, final int meta) {
        final ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
        if (metaHasCorn(meta)) {
            int corns = 1;
            if (world.rand.nextInt(4) == 0) {
                ++corns;
            }
            for (int l = 0; l < corns; ++l) {
                drops.add(new ItemStack(LOTRMod.corn));
            }
        }
        return drops;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        if (metaHasCorn(j)) {
            return this.cornIcon;
        }
        return super.getIcon(i, j);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        super.registerBlockIcons(iconregister);
        this.cornIcon = iconregister.registerIcon(this.getTextureName() + "_corn");
    }
    
    @SideOnly(Side.CLIENT)
    public String getItemIconName() {
        return this.getTextureName();
    }
    
    public EnumPlantType getPlantType(final IBlockAccess world, final int i, final int j, final int k) {
        return EnumPlantType.Crop;
    }
    
    public Block getPlant(final IBlockAccess world, final int i, final int j, final int k) {
        return this;
    }
    
    public int getPlantMetadata(final IBlockAccess world, final int i, final int j, final int k) {
        return world.getBlockMetadata(i, j, k);
    }
    
    public boolean func_149851_a(final World world, final int i, final int j, final int k, final boolean isRemote) {
        return (world.getBlock(i, j - 1, k) != this && world.isAirBlock(i, j + 1, k)) || (!hasCorn(world, i, j, k) && this.canGrowCorn(world, i, j, k));
    }
    
    public boolean func_149852_a(final World world, final Random random, final int i, final int j, final int k) {
        return true;
    }
    
    public void func_149853_b(final World world, final Random random, final int i, final int j, final int k) {
        if (world.getBlock(i, j - 1, k) != this && world.isAirBlock(i, j + 1, k)) {
            world.setBlock(i, j + 1, k, (Block)this, 0, 3);
        }
        else if (!hasCorn(world, i, j, k) && this.canGrowCorn(world, i, j, k) && random.nextInt(2) == 0) {
            setHasCorn(world, i, j, k, true);
        }
    }
    
    static {
        LOTRBlockCorn.MAX_GROW_HEIGHT = 3;
        LOTRBlockCorn.META_GROW_END = 7;
    }
}
