// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.entity.Entity;
import java.util.Random;
import net.minecraft.util.MovingObjectPosition;
import lotr.common.item.LOTRItemBarrel;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.entity.player.EntityPlayerMP;
import lotr.common.item.LOTRItemBottlePoison;
import lotr.common.recipe.LOTRBrewingRecipes;
import lotr.common.item.LOTRItemMug;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.EntityLivingBase;
import lotr.common.LOTRMod;
import net.minecraft.client.renderer.texture.IIconRegister;
import lotr.common.tileentity.LOTRTileEntityBarrel;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.material.Material;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.block.BlockContainer;

public class LOTRBlockBarrel extends BlockContainer
{
    @SideOnly(Side.CLIENT)
    private IIcon[] barrelIcons;
    
    public LOTRBlockBarrel() {
        super(Material.wood);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabFood);
        this.setBlockBounds(0.125f, 0.0f, 0.125f, 0.875f, 0.8125f, 0.875f);
        this.setHardness(3.0f);
        this.setResistance(5.0f);
        this.setStepSound(Block.soundTypeWood);
    }
    
    public TileEntity createNewTileEntity(final World world, final int i) {
        return new LOTRTileEntityBarrel();
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        if (i == -1) {
            return this.barrelIcons[2];
        }
        if (i < 2) {
            return this.barrelIcons[1];
        }
        return this.barrelIcons[0];
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
        (this.barrelIcons = new IIcon[3])[0] = iconregister.registerIcon(this.getTextureName() + "_side");
        this.barrelIcons[1] = iconregister.registerIcon(this.getTextureName() + "_top");
        this.barrelIcons[2] = iconregister.registerIcon(this.getTextureName() + "_tap");
    }
    
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    public boolean isOpaqueCube() {
        return false;
    }
    
    public int getRenderType() {
        return LOTRMod.proxy.getBarrelRenderID();
    }
    
    public void onBlockPlacedBy(final World world, final int i, final int j, final int k, final EntityLivingBase entity, final ItemStack itemstack) {
        final int rotation = MathHelper.floor_double(((Entity)entity).rotationYaw * 4.0f / 360.0f + 0.5) & 0x3;
        int meta = 0;
        if (rotation == 0) {
            meta = 2;
        }
        else if (rotation == 1) {
            meta = 5;
        }
        else if (rotation == 2) {
            meta = 3;
        }
        else if (rotation == 3) {
            meta = 4;
        }
        world.setBlockMetadata(i, j, k, meta, 2);
        if (itemstack.hasDisplayName()) {
            ((LOTRTileEntityBarrel)world.getTileEntity(i, j, k)).setBarrelName(itemstack.getDisplayName());
        }
    }
    
    public boolean onBlockActivated(final World world, final int i, final int j, final int k, final EntityPlayer entityplayer, final int side, final float f, final float f1, final float f2) {
        final LOTRTileEntityBarrel barrel = (LOTRTileEntityBarrel)world.getTileEntity(i, j, k);
        final ItemStack barrelDrink = barrel.getBrewedDrink();
        final ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        final Item item = (itemstack == null) ? null : itemstack.getItem();
        if (side == world.getBlockMetadata(i, j, k)) {
            if (barrelDrink != null && LOTRItemMug.isItemEmptyDrink(itemstack)) {
                final ItemStack playerDrink = barrelDrink.copy();
                playerDrink.stackSize = 1;
                final LOTRItemMug.Vessel v = LOTRItemMug.getVessel(itemstack);
                LOTRItemMug.setVessel(playerDrink, v, true);
                final ItemStack itemStack = itemstack;
                --itemStack.stackSize;
                if (itemstack.stackSize <= 0) {
                    entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, playerDrink);
                }
                else if (!entityplayer.inventory.addItemStackToInventory(playerDrink)) {
                    entityplayer.dropPlayerItemWithRandomChoice(playerDrink, false);
                }
                barrel.consumeMugRefill();
                world.playSoundEffect(i + 0.5, j + 0.5, k + 0.5, "lotr:item.mug_fill", 0.5f, 0.8f + world.rand.nextFloat() * 0.4f);
                return true;
            }
            if (itemstack != null && item instanceof LOTRItemMug && ((LOTRItemMug)item).isBrewable) {
                boolean match = false;
                if (barrel.barrelMode == 0) {
                    match = true;
                }
                else if (barrelDrink != null && barrelDrink.stackSize < LOTRBrewingRecipes.BARREL_CAPACITY) {
                    match = (barrelDrink.getItem() == itemstack.getItem() && LOTRItemMug.getStrength(barrelDrink) == LOTRItemMug.getStrength(itemstack));
                }
                if (match) {
                    if (barrelDrink == null) {
                        final ItemStack barrelFill = itemstack.copy();
                        barrelFill.stackSize = 1;
                        LOTRItemMug.setVessel(barrelFill, LOTRItemMug.Vessel.MUG, false);
                        barrel.setInventorySlotContents(9, barrelFill);
                    }
                    else {
                        final ItemStack itemStack2 = barrelDrink;
                        ++itemStack2.stackSize;
                        barrel.setInventorySlotContents(9, barrelDrink);
                    }
                    barrel.barrelMode = 2;
                    if (!entityplayer.capabilities.isCreativeMode) {
                        final LOTRItemMug.Vessel v = LOTRItemMug.getVessel(itemstack);
                        final ItemStack emptyMug = v.getEmptyVessel();
                        final ItemStack itemStack3 = itemstack;
                        --itemStack3.stackSize;
                        if (itemstack.stackSize <= 0) {
                            entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, emptyMug);
                        }
                        else if (!entityplayer.inventory.addItemStackToInventory(emptyMug)) {
                            entityplayer.dropPlayerItemWithRandomChoice(emptyMug, false);
                        }
                    }
                    world.playSoundEffect(i + 0.5, j + 0.5, k + 0.5, "lotr:item.mug_fill", 0.5f, 0.8f + world.rand.nextFloat() * 0.4f);
                    return true;
                }
            }
        }
        if (itemstack != null && item instanceof LOTRItemBottlePoison && barrel.canPoisonBarrel()) {
            if (!world.isClient) {
                barrel.poisonBarrel(entityplayer);
                if (!entityplayer.capabilities.isCreativeMode) {
                    final ItemStack containerItem = itemstack.getItem().getContainerItem(itemstack);
                    entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, containerItem);
                }
                entityplayer.openContainer.detectAndSendChanges();
                ((EntityPlayerMP)entityplayer).sendContainerToPlayer(entityplayer.openContainer);
            }
            return true;
        }
        if (!world.isClient) {
            entityplayer.openGui((Object)LOTRMod.instance, 16, world, i, j, k);
        }
        return true;
    }
    
    public void onBlockHarvested(final World world, final int i, final int j, final int k, int meta, final EntityPlayer entityplayer) {
        if (entityplayer.capabilities.isCreativeMode) {
            meta |= 0x8;
            world.setBlockMetadata(i, j, k, meta, 4);
        }
        super.onBlockHarvested(world, i, j, k, meta, entityplayer);
    }
    
    public void breakBlock(final World world, final int i, final int j, final int k, final Block block, final int meta) {
        final LOTRTileEntityBarrel barrel = (LOTRTileEntityBarrel)world.getTileEntity(i, j, k);
        if (barrel != null) {
            final ItemStack brewing = barrel.getStackInSlot(9);
            barrel.setInventorySlotContents(9, null);
            LOTRMod.dropContainerItems((IInventory)barrel, world, i, j, k);
            for (int slot = 0; slot < barrel.getSizeInventory(); ++slot) {
                barrel.setInventorySlotContents(slot, null);
            }
            barrel.setInventorySlotContents(9, brewing);
            if (!world.isClient && (meta & 0x8) == 0x0) {
                this.dropBlockAsItem_do(world, i, j, k, this.getBarrelDrop(world, i, j, k, meta));
            }
        }
        super.breakBlock(world, i, j, k, block, meta);
    }
    
    public ItemStack getBarrelDrop(final World world, final int i, final int j, final int k, final int metadata) {
        final ItemStack itemstack = new ItemStack(Item.getItemFromBlock((Block)this));
        final LOTRTileEntityBarrel barrel = (LOTRTileEntityBarrel)world.getTileEntity(i, j, k);
        if (barrel != null && barrel.barrelMode != 0) {
            LOTRItemBarrel.setBarrelDataFromTE(itemstack, barrel);
        }
        return itemstack;
    }
    
    public ItemStack getPickBlock(final MovingObjectPosition target, final World world, final int i, final int j, final int k) {
        world.markBlockForUpdate(i, j, k);
        return this.getBarrelDrop(world, i, j, k, world.getBlockMetadata(i, j, k));
    }
    
    public Item getItemDropped(final int i, final Random random, final int j) {
        return null;
    }
}
