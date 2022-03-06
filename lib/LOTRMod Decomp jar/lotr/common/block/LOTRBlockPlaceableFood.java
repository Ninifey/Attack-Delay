// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.item.ItemStack;
import java.util.ArrayList;
import java.util.Random;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.IBlockAccess;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.block.Block;

public class LOTRBlockPlaceableFood extends Block
{
    @SideOnly(Side.CLIENT)
    private IIcon iconBottom;
    @SideOnly(Side.CLIENT)
    private IIcon iconTop;
    @SideOnly(Side.CLIENT)
    private IIcon iconSide;
    @SideOnly(Side.CLIENT)
    private IIcon iconEaten;
    public Item foodItem;
    private float foodHalfWidth;
    private float foodHeight;
    private static int MAX_EATS;
    private int healAmount;
    private float saturationAmount;
    
    public LOTRBlockPlaceableFood() {
        this(0.4375f, 0.5f);
    }
    
    public LOTRBlockPlaceableFood(final float f, final float f1) {
        super(Material.field_151568_F);
        this.foodHalfWidth = f;
        this.foodHeight = f1;
        this.setHardness(0.5f);
        this.setStepSound(Block.soundTypeCloth);
        this.setTickRandomly(true);
        this.setFoodStats(2, 0.1f);
    }
    
    public LOTRBlockPlaceableFood setFoodStats(final int i, final float f) {
        this.healAmount = i;
        this.saturationAmount = f;
        return this;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        if (i == 0) {
            return this.iconBottom;
        }
        if (i == 1) {
            return this.iconTop;
        }
        if (j > 0 && i == 4) {
            return this.iconEaten;
        }
        return this.iconSide;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        this.iconBottom = iconregister.registerIcon(this.getTextureName() + "_bottom");
        this.iconTop = iconregister.registerIcon(this.getTextureName() + "_top");
        this.iconSide = iconregister.registerIcon(this.getTextureName() + "_side");
        this.iconEaten = iconregister.registerIcon(this.getTextureName() + "_inner");
    }
    
    public void setBlockBoundsBasedOnState(final IBlockAccess world, final int i, final int j, final int k) {
        final int l = world.getBlockMetadata(i, j, k);
        final float f = 0.5f - this.foodHalfWidth;
        final float f2 = 0.5f + this.foodHalfWidth;
        final float f3 = f + (f2 - f) * (world.getBlockMetadata(i, j, k) / (float)LOTRBlockPlaceableFood.MAX_EATS);
        this.setBlockBounds(f3, 0.0f, f, f2, this.foodHeight, f2);
    }
    
    public void setBlockBoundsForItemRender() {
        final float f = 0.5f - this.foodHalfWidth;
        final float f2 = 0.5f + this.foodHalfWidth;
        this.setBlockBounds(f, 0.0f, f, f2, this.foodHeight, f2);
    }
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World world, final int i, final int j, final int k) {
        final float f = 0.5f - this.foodHalfWidth;
        final float f2 = 0.5f + this.foodHalfWidth;
        final float f3 = f + (f2 - f) * (world.getBlockMetadata(i, j, k) / (float)LOTRBlockPlaceableFood.MAX_EATS);
        return AxisAlignedBB.getBoundingBox((double)(i + f3), (double)j, (double)(k + f), (double)(i + f2), (double)(j + this.foodHeight), (double)(k + f2));
    }
    
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBoxFromPool(final World world, final int i, final int j, final int k) {
        return this.getCollisionBoundingBoxFromPool(world, i, j, k);
    }
    
    public boolean onBlockActivated(final World world, final int i, final int j, final int k, final EntityPlayer entityplayer, final int side, final float f, final float f1, final float f2) {
        this.eatCake(world, i, j, k, entityplayer);
        return true;
    }
    
    private void eatCake(final World world, final int i, final int j, final int k, final EntityPlayer entityplayer) {
        if (!world.isClient && entityplayer.canEat(false)) {
            entityplayer.getFoodStats().addStats(this.healAmount, this.saturationAmount);
            entityplayer.playSound("random.burp", 0.5f, world.rand.nextFloat() * 0.1f + 0.9f);
            int meta = world.getBlockMetadata(i, j, k);
            if (++meta >= LOTRBlockPlaceableFood.MAX_EATS) {
                world.setBlockToAir(i, j, k);
            }
            else {
                world.setBlockMetadata(i, j, k, meta, 3);
            }
        }
    }
    
    public boolean canPlaceBlockAt(final World world, final int i, final int j, final int k) {
        return super.canPlaceBlockAt(world, i, j, k) && this.canBlockStay(world, i, j, k);
    }
    
    public boolean canBlockStay(final World world, final int i, final int j, final int k) {
        return world.getBlock(i, j - 1, k).isSideSolid((IBlockAccess)world, i, j - 1, k, ForgeDirection.UP);
    }
    
    public void onNeighborBlockChange(final World world, final int i, final int j, final int k, final Block block) {
        if (!this.canBlockStay(world, i, j, k)) {
            final int meta = world.getBlockMetadata(i, j, k);
            this.dropBlockAsItem(world, i, j, k, meta, 0);
            world.setBlockToAir(i, j, k);
        }
    }
    
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    public boolean isOpaqueCube() {
        return false;
    }
    
    public int quantityDropped(final Random random) {
        return 0;
    }
    
    public Item getItemDropped(final int i, final Random random, final int j) {
        return null;
    }
    
    public ArrayList<ItemStack> getDrops(final World world, final int i, final int j, final int k, final int meta, final int fortune) {
        final ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
        if (meta == 0) {
            if (this.foodItem != null) {
                drops.add(new ItemStack(this.foodItem));
            }
            else {
                drops.add(new ItemStack((Block)this, 1, 0));
            }
        }
        return drops;
    }
    
    @SideOnly(Side.CLIENT)
    public Item getItem(final World world, final int i, final int j, final int k) {
        if (this.foodItem != null) {
            return this.foodItem;
        }
        return Item.getItemFromBlock((Block)this);
    }
    
    static {
        LOTRBlockPlaceableFood.MAX_EATS = 6;
    }
}
