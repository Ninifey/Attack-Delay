// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.util.MathHelper;
import net.minecraftforge.common.EnumPlantType;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.world.biome.LOTRBiomeGenDorwinion;
import java.util.Random;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import lotr.common.LOTRMod;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.block.IGrowable;
import net.minecraftforge.common.IPlantable;
import net.minecraft.block.Block;

public class LOTRBlockGrapevine extends Block implements IPlantable, IGrowable
{
    public final boolean hasGrapes;
    public static final int MAX_GROWTH = 7;
    public static final int MAX_HEIGHT = 3;
    public static boolean hoeing;
    @SideOnly(Side.CLIENT)
    private IIcon postIcon;
    @SideOnly(Side.CLIENT)
    private IIcon[] vineIcons;
    
    public LOTRBlockGrapevine(final boolean grapes) {
        super(Material.plants);
        if (!(this.hasGrapes = grapes)) {
            this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabDeco);
        }
        else {
            this.setcreativeTab((CreativeTabs)null);
        }
        if (this.hasGrapes) {
            this.setStepSound(Block.soundTypeGrass);
            this.setHardness(0.0f);
        }
        else {
            this.setStepSound(Block.soundTypeWood);
            this.setHardness(2.0f);
            this.setResistance(5.0f);
        }
        if (this.hasGrapes) {
            this.setTickRandomly(true);
        }
    }
    
    public Item getGrapeItem() {
        return null;
    }
    
    public Item getGrapeSeedsItem() {
        return null;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        if (i != -1) {
            return this.postIcon;
        }
        if (j >= 7) {
            return this.vineIcons[1];
        }
        return this.vineIcons[0];
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        if (!this.hasGrapes) {
            this.postIcon = iconregister.registerIcon(this.getTextureName());
        }
        else {
            this.postIcon = LOTRMod.grapevine.getIcon(0, 0);
        }
        if (this.hasGrapes) {
            (this.vineIcons = new IIcon[2])[0] = iconregister.registerIcon(this.getTextureName() + "_vine");
            this.vineIcons[1] = iconregister.registerIcon(this.getTextureName() + "_grapes");
        }
    }
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World world, final int i, final int j, final int k) {
        this.setBlockBoundsBasedOnState((IBlockAccess)world, i, j, k);
        return super.getCollisionBoundingBoxFromPool(world, i, j, k);
    }
    
    public void setBlockBoundsBasedOnState(final IBlockAccess world, final int i, final int j, final int k) {
        float f = 0.125f;
        if (this.hasGrapes) {
            final float min = 0.1875f;
            final float max = 0.375f;
            final int meta = world.getBlockMetadata(i, j, k);
            final float f2 = meta / 7.0f;
            f = min + (max - min) * f2;
        }
        this.setBlockBounds(0.5f - f, 0.0f, 0.5f - f, 0.5f + f, 1.0f, 0.5f + f);
    }
    
    public void setBlockBoundsForItemRender() {
        final float f = 0.125f;
        this.setBlockBounds(0.5f - f, 0.0f, 0.5f - f, 0.5f + f, 1.0f, 0.5f + f);
    }
    
    public boolean isOpaqueCube() {
        return false;
    }
    
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    public int getRenderType() {
        if (this.hasGrapes) {
            return LOTRMod.proxy.getGrapevineRenderID();
        }
        return 0;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(final IBlockAccess world, final int i, final int j, final int k, final int side) {
        if (this.hasGrapes) {
            final Block block = world.getBlock(i, j, k);
            final int meta = world.getBlockMetadata(i, j, k);
            final int i2 = i - Facing.offsetsXForSide[side];
            final int j2 = j - Facing.offsetsYForSide[side];
            final int k2 = k - Facing.offsetsZForSide[side];
            final int metaThis = world.getBlockMetadata(i2, j2, k2);
            if (block instanceof LOTRBlockGrapevine && ((LOTRBlockGrapevine)block).hasGrapes && meta == metaThis && (side == 0 || side == 1)) {
                return false;
            }
        }
        return super.shouldSideBeRendered(world, i, j, k, side);
    }
    
    @SideOnly(Side.CLIENT)
    public Item getItem(final World world, final int i, final int j, final int k) {
        if (this.hasGrapes) {
            return this.getGrapeSeedsItem();
        }
        return Item.getItemFromBlock((Block)this);
    }
    
    public boolean canPlaceBlockAt(final World world, final int i, final int j, final int k) {
        final Block below = world.getBlock(i, j - 1, k);
        return below.isSideSolid((IBlockAccess)world, i, j - 1, k, ForgeDirection.UP) || below.canSustainPlant((IBlockAccess)world, i, j, k, ForgeDirection.UP, (IPlantable)this) || below instanceof LOTRBlockGrapevine;
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
            if (this.hasGrapes) {
                world.setBlock(i, j, k, LOTRMod.grapevine, 0, 3);
                final Block newBlock = world.getBlock(i, j, k);
                newBlock.updateTick(world, i, j, k, world.rand);
            }
            else {
                world.setBlockToAir(i, j, k);
            }
            return false;
        }
        return true;
    }
    
    public void updateTick(final World world, final int i, final int j, final int k, final Random random) {
        super.updateTick(world, i, j, k, random);
        if (!this.checkCanStay(world, i, j, k)) {
            return;
        }
        if (this.hasGrapes && world.getBlockLightValue(i, j + 1, k) >= 9) {
            int meta = world.getBlockMetadata(i, j, k);
            if (meta < 7) {
                final float growth = this.getGrowthFactor(world, i, j, k);
                if (growth > 0.0f && random.nextInt((int)(80.0f / growth) + 1) == 0) {
                    ++meta;
                    world.setBlockMetadata(i, j, k, meta, 2);
                }
            }
        }
    }
    
    private float getGrowthFactor(final World world, final int i, final int j, final int k) {
        if (!canPlantGrapesAt(world, i, j, k, (IPlantable)this)) {
            return 0.0f;
        }
        int farmlandHeight = 0;
        for (int l = 1; l <= 3; ++l) {
            final int j2 = j - l;
            final Block block = world.getBlock(i, j2, k);
            if (block.canSustainPlant((IBlockAccess)world, i, j2, k, ForgeDirection.UP, (IPlantable)this)) {
                farmlandHeight = j2;
                break;
            }
        }
        float growth = 1.0f;
        for (int range = 1, i2 = -range; i2 <= range; ++i2) {
            for (int k2 = -range; k2 <= range; ++k2) {
                final int i3 = i + i2;
                final int k3 = k + k2;
                float f = 0.0f;
                final Block block2 = world.getBlock(i3, farmlandHeight, k3);
                if (block2.canSustainPlant((IBlockAccess)world, i3, farmlandHeight, k3, ForgeDirection.UP, (IPlantable)this)) {
                    f = 1.0f;
                    if (block2.isFertile(world, i3, farmlandHeight, k3)) {
                        f = 3.0f;
                    }
                }
                if (i2 != 0 || k2 != 0) {
                    f /= 4.0f;
                }
                growth += f;
            }
        }
        final BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
        if (biome instanceof LOTRBiomeGenDorwinion) {
            growth *= 1.6f;
        }
        return growth;
    }
    
    public static boolean canPlantGrapesAt(final World world, final int i, final int j, final int k, final IPlantable plantable) {
        for (int l = 1; l <= 3; ++l) {
            final int j2 = j - l;
            final Block block = world.getBlock(i, j2, k);
            if (block.canSustainPlant((IBlockAccess)world, i, j2, k, ForgeDirection.UP, plantable)) {
                return true;
            }
            if (!(block instanceof LOTRBlockGrapevine)) {
                return false;
            }
        }
        return false;
    }
    
    public boolean getBlocksMovement(final IBlockAccess world, final int i, final int j, final int k) {
        return false;
    }
    
    public boolean onBlockActivated(final World world, final int i, final int j, final int k, final EntityPlayer entityplayer, final int side, final float f, final float f1, final float f2) {
        if (this.hasGrapes) {
            final int meta = world.getBlockMetadata(i, j, k);
            if (meta >= 7) {
                if (!world.isClient) {
                    final List<ItemStack> drops = this.getVineDrops(world, i, j, k, meta, 0);
                    for (final ItemStack itemstack : drops) {
                        this.dropBlockAsItem_do(world, i, j, k, itemstack);
                    }
                    LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.harvestGrapes);
                }
                world.setBlock(i, j, k, LOTRMod.grapevine, 0, 3);
                return true;
            }
        }
        return false;
    }
    
    public ArrayList<ItemStack> getDrops(final World world, final int i, final int j, final int k, final int meta, final int fortune) {
        final ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
        if (this.hasGrapes) {
            drops.addAll(this.getVineDrops(world, i, j, k, meta, fortune));
        }
        else {
            drops.add(new ItemStack((Block)this));
        }
        return drops;
    }
    
    private ArrayList<ItemStack> getVineDrops(final World world, final int i, final int j, final int k, final int meta, final int fortune) {
        final ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
        for (int seeds = 3 + fortune, l = 0; l < seeds; ++l) {
            if (world.rand.nextInt(15) <= meta) {
                drops.add(new ItemStack(this.getGrapeSeedsItem()));
            }
        }
        if (meta >= 7) {
            int grapes = 1 + world.rand.nextInt(fortune + 1);
            if (world.rand.nextInt(3) == 0) {
                ++grapes;
            }
            for (int m = 0; m < grapes; ++m) {
                drops.add(new ItemStack(this.getGrapeItem()));
            }
        }
        return drops;
    }
    
    public boolean removedByPlayer(final World world, final EntityPlayer entityplayer, final int i, final int j, final int k, final boolean willHarvest) {
        if (this.hasGrapes && entityplayer != null) {
            if (!world.isClient) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.harvestGrapes);
            }
            return world.setBlock(i, j, k, LOTRMod.grapevine, 0, 3);
        }
        return super.removedByPlayer(world, entityplayer, i, j, k, willHarvest);
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
    
    public boolean func_149851_a(final World world, final int i, final int j, final int k, final boolean flag) {
        return this.hasGrapes && world.getBlockMetadata(i, j, k) < 7;
    }
    
    public boolean func_149852_a(final World world, final Random random, final int i, final int j, final int k) {
        return true;
    }
    
    public void func_149853_b(final World world, final Random random, final int i, final int j, final int k) {
        if (this.hasGrapes) {
            int meta = world.getBlockMetadata(i, j, k) + MathHelper.getRandomIntegerInRange(random, 2, 5);
            if (meta > 7) {
                meta = 7;
            }
            world.setBlockMetadata(i, j, k, meta, 2);
        }
    }
    
    public boolean isAir(final IBlockAccess world, final int i, final int j, final int k) {
        if (LOTRBlockGrapevine.hoeing) {
            LOTRBlockGrapevine.hoeing = false;
            return true;
        }
        return super.isAir(world, i, j, k);
    }
    
    public static boolean isFullGrownGrapes(final Block block, final int meta) {
        return block instanceof LOTRBlockGrapevine && ((LOTRBlockGrapevine)block).hasGrapes && meta >= 7;
    }
    
    static {
        LOTRBlockGrapevine.hoeing = false;
    }
}
