// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.client.renderer.texture.IIconRegister;
import lotr.common.LOTRMod;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import java.util.Random;
import lotr.common.tileentity.LOTRTileEntityPlate;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;

public class LOTRBlockPlate extends BlockContainer
{
    public static final Block.SoundType soundTypePlate;
    @SideOnly(Side.CLIENT)
    private IIcon[] plateIcons;
    private Item plateItem;
    
    public LOTRBlockPlate() {
        super(Material.circuits);
        this.setHardness(0.0f);
        this.setBlockBounds(0.125f, 0.0f, 0.125f, 0.875f, 0.125f, 0.875f);
    }
    
    public void setPlateItem(final Item item) {
        this.plateItem = item;
    }
    
    public TileEntity createNewTileEntity(final World world, final int i) {
        return new LOTRTileEntityPlate();
    }
    
    public Item getItemDropped(final int i, final Random random, final int j) {
        return this.plateItem;
    }
    
    public ItemStack getPickBlock(final MovingObjectPosition target, final World world, final int i, final int j, final int k, final EntityPlayer entityplayer) {
        final ItemStack foodItem = getFoodItem(world, i, j, k);
        if (foodItem != null) {
            final ItemStack copy = foodItem.copy();
            copy.stackSize = 1;
            return copy;
        }
        final int meta = world.getBlockMetadata(i, j, k);
        return new ItemStack(this.getItemDropped(meta, world.rand, 0), 1, this.damageDropped(meta));
    }
    
    public void breakBlock(final World world, final int i, final int j, final int k, final Block block, final int meta) {
        final TileEntity tileentity = world.getTileEntity(i, j, k);
        if (!world.isClient && tileentity instanceof LOTRTileEntityPlate) {
            final LOTRTileEntityPlate plate = (LOTRTileEntityPlate)tileentity;
            final ItemStack foodItem = plate.getFoodItem();
            if (foodItem != null) {
                this.dropBlockAsItem_do(world, i, j, k, foodItem);
            }
        }
        super.breakBlock(world, i, j, k, block, meta);
    }
    
    public boolean isOpaqueCube() {
        return false;
    }
    
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    public int getRenderType() {
        return LOTRMod.proxy.getPlateRenderID();
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        return (i == 1) ? this.plateIcons[0] : this.plateIcons[1];
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        (this.plateIcons = new IIcon[2])[0] = iconregister.registerIcon(this.getTextureName() + "_top");
        this.plateIcons[1] = iconregister.registerIcon(this.getTextureName() + "_base");
    }
    
    public boolean canBlockStay(final World world, final int i, final int j, final int k) {
        return world.getBlock(i, j - 1, k).isSideSolid((IBlockAccess)world, i, j - 1, k, ForgeDirection.UP);
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
    
    public boolean onBlockActivated(final World world, final int i, final int j, final int k, final EntityPlayer entityplayer, final int side, final float f, final float f1, final float f2) {
        final ItemStack itemstack = entityplayer.getCurrentEquippedItem();
        final TileEntity tileentity = world.getTileEntity(i, j, k);
        if (tileentity instanceof LOTRTileEntityPlate) {
            final LOTRTileEntityPlate plate = (LOTRTileEntityPlate)tileentity;
            ItemStack plateItem = plate.getFoodItem();
            if (plateItem == null && LOTRTileEntityPlate.isValidFoodItem(itemstack)) {
                if (!world.isClient) {
                    plateItem = itemstack.copy();
                    plateItem.stackSize = 1;
                    plate.setFoodItem(plateItem);
                }
                if (!entityplayer.capabilities.isCreativeMode) {
                    final ItemStack itemStack = itemstack;
                    --itemStack.stackSize;
                }
                return true;
            }
            if (plateItem != null) {
                if (itemstack != null && itemstack.isItemEqual(plateItem) && ItemStack.areItemStackTagsEqual(itemstack, plateItem)) {
                    if (plateItem.stackSize < plateItem.getMaxStackSize()) {
                        if (!world.isClient) {
                            final ItemStack itemStack2 = plateItem;
                            ++itemStack2.stackSize;
                            plate.setFoodItem(plateItem);
                        }
                        if (!entityplayer.capabilities.isCreativeMode) {
                            final ItemStack itemStack3 = itemstack;
                            --itemStack3.stackSize;
                        }
                        return true;
                    }
                }
                else if (entityplayer.canEat(false)) {
                    plateItem.getItem().onEaten(plateItem, world, entityplayer);
                    if (!world.isClient) {
                        plate.setFoodItem(plateItem);
                    }
                    return true;
                }
            }
        }
        return false;
    }
    
    public static ItemStack getFoodItem(final World world, final int i, final int j, final int k) {
        final TileEntity tileentity = world.getTileEntity(i, j, k);
        if (tileentity instanceof LOTRTileEntityPlate) {
            return ((LOTRTileEntityPlate)tileentity).getFoodItem();
        }
        return null;
    }
    
    public void dropPlateItem(final LOTRTileEntityPlate plate) {
        this.dropPlateItem(plate, plate.getFoodItem());
    }
    
    public void dropOnePlateItem(final LOTRTileEntityPlate plate) {
        final ItemStack item = plate.getFoodItem().copy();
        item.stackSize = 1;
        this.dropPlateItem(plate, item);
    }
    
    private void dropPlateItem(final LOTRTileEntityPlate plate, final ItemStack itemstack) {
        this.dropBlockAsItem_do(plate.getWorldObj(), plate.xCoord, plate.yCoord, plate.zCoord, itemstack);
    }
    
    static {
        soundTypePlate = new Block.SoundType("lotr:plate", 1.0f, 1.0f) {
            private Random rand = new Random();
            
            public float getFrequency() {
                return super.getFrequency();
            }
            
            public String getDigResourcePath() {
                return "lotr:block.plate.break";
            }
            
            public String getStepResourcePath() {
                return Block.soundTypeStone.getStepResourcePath();
            }
            
            public String func_150496_b() {
                return Block.soundTypeStone.func_150496_b();
            }
        };
    }
}
