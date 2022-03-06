// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import lotr.common.recipe.LOTRRecipesTreasurePile;
import net.minecraft.item.crafting.IRecipe;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import java.util.List;
import net.minecraft.util.MathHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import lotr.common.entity.item.LOTREntityFallingTreasure;
import net.minecraft.block.BlockFalling;
import java.util.Random;
import lotr.common.LOTRMod;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.world.IBlockAccess;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.block.Block;

public class LOTRBlockTreasurePile extends Block
{
    public static final Block.SoundType soundTypeTreasure;
    public static final int MAX_META = 7;
    @SideOnly(Side.CLIENT)
    private IIcon sideIcon;
    
    public LOTRBlockTreasurePile() {
        super(Material.circuits);
        this.setHardness(0.0f);
        this.setStepSound(LOTRBlockTreasurePile.soundTypeTreasure);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabDeco);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        super.registerBlockIcons(iconregister);
        this.sideIcon = iconregister.registerIcon(this.getTextureName() + "_side");
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        if (i == 0 || i == 1) {
            return super.blockIcon;
        }
        return this.sideIcon;
    }
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World world, final int i, final int j, final int k) {
        this.setBlockBoundsBasedOnState((IBlockAccess)world, i, j, k);
        if (super.maxY >= 1.0) {
            super.maxY = 1.0;
        }
        else if (super.maxY >= 0.5) {
            super.maxY = 0.5;
        }
        else {
            super.maxY = 0.0625;
        }
        return super.getCollisionBoundingBoxFromPool(world, i, j, k);
    }
    
    public void setBlockBoundsBasedOnState(final IBlockAccess world, final int i, final int j, final int k) {
        final int meta = world.getBlockMetadata(i, j, k);
        this.setBlockBoundsMeta(meta);
    }
    
    public void setBlockBoundsForItemRender() {
        this.setBlockBoundsMeta(0);
    }
    
    private void setBlockBoundsMeta(final int meta) {
        final float f = (meta + 1) / 8.0f;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, f, 1.0f);
    }
    
    public static void setTreasureBlockBounds(final Block block, final int meta) {
        if (block instanceof LOTRBlockTreasurePile) {
            ((LOTRBlockTreasurePile)block).setBlockBoundsMeta(meta);
        }
    }
    
    public boolean isSideSolid(final IBlockAccess world, final int i, final int j, final int k, final ForgeDirection side) {
        final int meta = world.getBlockMetadata(i, j, k);
        return (meta == 7 && side == ForgeDirection.UP) || super.isSideSolid(world, i, j, k, side);
    }
    
    public boolean isOpaqueCube() {
        return false;
    }
    
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    public int getRenderType() {
        return LOTRMod.proxy.getTreasureRenderID();
    }
    
    public boolean canBlockStay(final World world, final int i, final int j, final int k) {
        return world.getBlock(i, j - 1, k).isSideSolid((IBlockAccess)world, i, j - 1, k, ForgeDirection.UP);
    }
    
    public boolean canPlaceBlockAt(final World world, final int i, final int j, final int k) {
        return super.canPlaceBlockAt(world, i, j, k);
    }
    
    public void onBlockAdded(final World world, final int i, final int j, final int k) {
        world.scheduleBlockUpdate(i, j, k, (Block)this, this.func_149738_a(world));
    }
    
    public void onNeighborBlockChange(final World world, final int i, final int j, final int k, final Block block) {
        world.scheduleBlockUpdate(i, j, k, (Block)this, this.func_149738_a(world));
    }
    
    public int func_149738_a(final World world) {
        return 2;
    }
    
    public void updateTick(final World world, final int i, final int j, final int k, final Random random) {
        if (!world.isClient && !this.tryFall(world, i, j, k) && !this.canBlockStay(world, i, j, k)) {
            this.dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
            world.setBlockToAir(i, j, k);
        }
    }
    
    private boolean tryFall(final World world, final int i, int j, final int k) {
        final int meta = world.getBlockMetadata(i, j, k);
        if (canFallUpon(world, i, j - 1, k, this, meta) && j >= 0) {
            final int range = 32;
            if (!BlockFalling.fallInstantly && world.checkChunksExist(i - range, j - range, k - range, i + range, j + range, k + range)) {
                if (!world.isClient) {
                    final LOTREntityFallingTreasure fallingBlock = new LOTREntityFallingTreasure(world, i + 0.5f, j + 0.5f, k + 0.5f, this, meta);
                    world.spawnEntityInWorld((Entity)fallingBlock);
                    return true;
                }
            }
            else {
                world.setBlockToAir(i, j, k);
                while (canFallUpon(world, i, j - 1, k, this, meta) && j > 0) {
                    --j;
                }
                if (j > 0) {
                    world.setBlock(i, j, k, (Block)this, meta, 3);
                    return true;
                }
            }
        }
        return false;
    }
    
    public static boolean canFallUpon(final World world, final int i, final int j, final int k, final Block thisBlock, final int thisMeta) {
        final Block block = world.getBlock(i, j, k);
        final int meta = world.getBlockMetadata(i, j, k);
        return (block == thisBlock && meta < 7) || BlockFalling.func_149831_e(world, i, j, k);
    }
    
    public boolean onBlockActivated(final World world, final int i, final int j, final int k, final EntityPlayer entityplayer, final int side, final float f, final float f1, final float f2) {
        final ItemStack itemstack = entityplayer.getHeldItem();
        if (itemstack != null && itemstack.getItem() == Item.getItemFromBlock((Block)this) && side == 1) {
            int itemMeta = itemstack.getItemDamage();
            boolean placedTreasure = false;
            int meta = world.getBlockMetadata(i, j, k);
            if (meta < 7) {
                while (meta < 7 && itemMeta >= 0) {
                    ++meta;
                    --itemMeta;
                }
                world.setBlockMetadata(i, j, k, meta, 3);
                placedTreasure = true;
                if (itemMeta >= 0) {
                    final Block above = world.getBlock(i, j + 1, k);
                    if (above.isReplaceable((IBlockAccess)world, i, j + 1, k)) {
                        world.setBlock(i, j + 1, k, (Block)this, itemMeta, 3);
                        itemMeta = -1;
                        placedTreasure = true;
                    }
                }
                if (placedTreasure) {
                    world.playSoundEffect((double)(i + 0.5f), (double)(j + 0.5f), (double)(k + 0.5f), super.stepSound.func_150496_b(), (super.stepSound.getVolume() + 1.0f) / 2.0f, super.stepSound.getFrequency() * 0.8f);
                    if (!entityplayer.capabilities.isCreativeMode) {
                        if (itemMeta < 0) {
                            final ItemStack itemStack = itemstack;
                            --itemStack.stackSize;
                        }
                        else {
                            final ItemStack itemStack2 = itemstack;
                            --itemStack2.stackSize;
                            final ItemStack remainder = itemstack.copy();
                            remainder.stackSize = 1;
                            remainder.setItemDamage(itemMeta);
                            if (itemstack.stackSize <= 0) {
                                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, remainder);
                            }
                            else if (!entityplayer.inventory.addItemStackToInventory(remainder)) {
                                entityplayer.dropPlayerItemWithRandomChoice(remainder, false);
                            }
                        }
                        if (!world.isClient) {
                            entityplayer.openContainer.detectAndSendChanges();
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }
    
    public int damageDropped(final int i) {
        return i;
    }
    
    public int quantityDropped(final int meta, final int fortune, final Random random) {
        return 1;
    }
    
    public void onEntityWalking(final World world, final int i, final int j, final int k, final Entity entity) {
        this.setBlockBoundsBasedOnState((IBlockAccess)world, i, j, k);
        for (int l = 0; l < 8; ++l) {
            final double d = i + world.rand.nextFloat();
            final double d2 = j + super.maxY;
            final double d3 = k + world.rand.nextFloat();
            final double d4 = MathHelper.randomFloatClamp(world.rand, -0.15f, 0.15f);
            final double d5 = MathHelper.randomFloatClamp(world.rand, 0.1f, 0.4f);
            final double d6 = MathHelper.randomFloatClamp(world.rand, -0.15f, 0.15f);
            world.spawnParticle("blockdust_" + Block.getIdFromBlock((Block)this) + "_0", d, d2, d3, d4, d5, d6);
        }
    }
    
    public void onFallenUpon(final World world, final int i, final int j, final int k, final Entity entity, final float f) {
        this.onEntityWalking(world, i, j, k, entity);
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(final Item item, final CreativeTabs tab, final List list) {
        list.add(new ItemStack(item, 1, 0));
        list.add(new ItemStack(item, 1, 7));
    }
    
    public static void generateTreasureRecipes(final Block block, final Item ingot) {
        GameRegistry.addRecipe((IRecipe)new ShapedOreRecipe(new ItemStack(block, 8, 0), new Object[] { "XX", "XX", 'X', ingot }));
        GameRegistry.addRecipe((IRecipe)new LOTRRecipesTreasurePile(block, ingot));
    }
    
    static {
        soundTypeTreasure = new Block.SoundType("lotr:treasure", 1.0f, 1.0f) {
            private Random rand = new Random();
            
            public float getFrequency() {
                return super.getFrequency() * (0.85f + this.rand.nextFloat() * 0.3f);
            }
            
            public String getDigResourcePath() {
                return "lotr:block.treasure.break";
            }
            
            public String getStepResourcePath() {
                return "lotr:block.treasure.step";
            }
            
            public String func_150496_b() {
                return "lotr:block.treasure.place";
            }
        };
    }
}
