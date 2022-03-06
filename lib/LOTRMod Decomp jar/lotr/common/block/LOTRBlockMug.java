// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import lotr.common.LOTRMod;
import net.minecraft.item.Item;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraft.entity.player.EntityPlayerMP;
import lotr.common.item.LOTRItemBottlePoison;
import net.minecraft.entity.Entity;
import lotr.common.item.LOTRItemMug;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.item.ItemStack;
import java.util.ArrayList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.renderer.texture.IIconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import lotr.common.tileentity.LOTRTileEntityMug;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockContainer;

public class LOTRBlockMug extends BlockContainer
{
    public static final float MUG_SCALE = 0.75f;
    
    public LOTRBlockMug() {
        this(3.0f, 8.0f);
    }
    
    public LOTRBlockMug(float f, float f1) {
        super(Material.circuits);
        f /= 16.0f;
        f1 /= 16.0f;
        f *= 0.75f;
        f1 *= 0.75f;
        this.setBlockBounds(0.5f - f, 0.0f, 0.5f - f, 0.5f + f, f1, 0.5f + f);
        this.setHardness(0.0f);
        this.setStepSound(Block.soundTypeWood);
    }
    
    public TileEntity createNewTileEntity(final World world, final int i) {
        return new LOTRTileEntityMug();
    }
    
    public boolean isOpaqueCube() {
        return false;
    }
    
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    public int getRenderType() {
        return -1;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        return Blocks.planks.getIcon(i, 0);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
    }
    
    public boolean canBlockStay(final World world, final int i, final int j, final int k) {
        final Block block = world.getBlock(i, j - 1, k);
        return block.canPlaceTorchOnTop(world, i, j - 1, k);
    }
    
    public boolean canPlaceBlockAt(final World world, final int i, final int j, final int k) {
        return this.canBlockStay(world, i, j, k);
    }
    
    public void onNeighborBlockChange(final World world, final int i, final int j, final int k, final Block block) {
        if (!this.canBlockStay(world, i, j, k)) {
            final int meta = world.getBlockMetadata(i, j, k);
            this.dropBlockAsItem(world, i, j, k, meta, 0);
            world.setBlockToAir(i, j, k);
        }
    }
    
    public void onBlockHarvested(final World world, final int i, final int j, final int k, int meta, final EntityPlayer entityplayer) {
        if (entityplayer.capabilities.isCreativeMode) {
            meta |= 0x4;
            world.setBlockMetadata(i, j, k, meta, 4);
        }
        this.dropBlockAsItem(world, i, j, k, meta, 0);
        super.onBlockHarvested(world, i, j, k, meta, entityplayer);
    }
    
    public ArrayList<ItemStack> getDrops(final World world, final int i, final int j, final int k, final int meta, final int fortune) {
        final ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
        if ((meta & 0x4) == 0x0) {
            final ItemStack itemstack = getMugItem(world, i, j, k);
            final LOTRTileEntityMug mug = (LOTRTileEntityMug)world.getTileEntity(i, j, k);
            if (mug != null) {
                drops.add(itemstack);
            }
        }
        return drops;
    }
    
    public ItemStack getPickBlock(final MovingObjectPosition target, final World world, final int i, final int j, final int k) {
        return getMugItem(world, i, j, k);
    }
    
    public boolean onBlockActivated(final World world, final int i, final int j, final int k, final EntityPlayer entityplayer, final int side, final float f, final float f1, final float f2) {
        final ItemStack itemstack = entityplayer.getCurrentEquippedItem();
        final TileEntity tileentity = world.getTileEntity(i, j, k);
        if (tileentity != null && tileentity instanceof LOTRTileEntityMug) {
            final LOTRTileEntityMug mug = (LOTRTileEntityMug)tileentity;
            final ItemStack mugItem = mug.getMugItem();
            if (!mug.isEmpty() && LOTRItemMug.isItemEmptyDrink(itemstack)) {
                final ItemStack takenDrink = mugItem.copy();
                final LOTRItemMug.Vessel v = LOTRItemMug.getVessel(itemstack);
                LOTRItemMug.setVessel(takenDrink, v, true);
                if (entityplayer.capabilities.isCreativeMode) {
                    entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, takenDrink);
                }
                else {
                    final ItemStack itemStack = itemstack;
                    --itemStack.stackSize;
                    if (itemstack.stackSize <= 0) {
                        entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, takenDrink);
                    }
                    else if (!entityplayer.inventory.addItemStackToInventory(takenDrink)) {
                        entityplayer.dropPlayerItemWithRandomChoice(takenDrink, false);
                    }
                }
                mug.setEmpty();
                world.playSoundAtEntity((Entity)entityplayer, "lotr:item.mug_fill", 0.5f, 0.8f + world.rand.nextFloat() * 0.4f);
                return true;
            }
            if (mug.isEmpty() && LOTRItemMug.isItemFullDrink(itemstack)) {
                final ItemStack emptyMug = LOTRItemMug.getVessel(itemstack).getEmptyVessel();
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, emptyMug);
                final ItemStack mugFill = itemstack.copy();
                mugFill.stackSize = 1;
                mug.setMugItem(mugFill);
                world.playSoundEffect(i + 0.5, j + 0.5, k + 0.5, "lotr:item.mug_fill", 0.5f, 0.8f + world.rand.nextFloat() * 0.4f);
                return true;
            }
            if (!mug.isEmpty()) {
                if (itemstack != null && itemstack.getItem() instanceof LOTRItemBottlePoison && mug.canPoisonMug()) {
                    if (!world.isClient) {
                        mug.poisonMug(entityplayer);
                        if (!entityplayer.capabilities.isCreativeMode) {
                            final ItemStack containerItem = itemstack.getItem().getContainerItem(itemstack);
                            entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, containerItem);
                        }
                        entityplayer.openContainer.detectAndSendChanges();
                        ((EntityPlayerMP)entityplayer).sendContainerToPlayer(entityplayer.openContainer);
                    }
                    return true;
                }
                final ItemStack equivalentDrink = LOTRItemMug.getEquivalentDrink(mugItem);
                final Item eqItem = equivalentDrink.getItem();
                boolean canDrink = false;
                if (eqItem instanceof LOTRItemMug) {
                    canDrink = ((LOTRItemMug)eqItem).canPlayerDrink(entityplayer);
                }
                if (canDrink) {
                    ItemStack mugItemResult = mugItem.onFoodEaten(world, entityplayer);
                    mugItemResult = ForgeEventFactory.onItemUseFinish(entityplayer, mugItem, mugItem.getMaxItemUseDuration(), mugItemResult);
                    mug.setEmpty();
                    world.markBlockForUpdate(i, j, k);
                    world.playSoundAtEntity((Entity)entityplayer, "random.drink", 0.5f, world.rand.nextFloat() * 0.1f + 0.9f);
                    return true;
                }
            }
        }
        return false;
    }
    
    public static ItemStack getMugItem(final World world, final int i, final int j, final int k) {
        final TileEntity tileentity = world.getTileEntity(i, j, k);
        if (tileentity instanceof LOTRTileEntityMug) {
            final LOTRTileEntityMug mug = (LOTRTileEntityMug)tileentity;
            return mug.getMugItem();
        }
        return new ItemStack(LOTRMod.mug);
    }
    
    public static void setMugItem(final World world, final int i, final int j, final int k, final ItemStack itemstack, final LOTRItemMug.Vessel vessel) {
        final TileEntity te = world.getTileEntity(i, j, k);
        if (te instanceof LOTRTileEntityMug) {
            final LOTRTileEntityMug mug = (LOTRTileEntityMug)te;
            mug.setMugItem(itemstack);
            mug.setVessel(vessel);
        }
    }
}
