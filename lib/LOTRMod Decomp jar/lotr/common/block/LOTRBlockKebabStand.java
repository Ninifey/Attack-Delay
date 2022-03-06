// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.entity.Entity;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;
import lotr.common.item.LOTRItemKebabStand;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import java.util.ArrayList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import lotr.common.tileentity.LOTRTileEntityKebabStand;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.creativetab.CreativeTabs;
import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockContainer;

public class LOTRBlockKebabStand extends BlockContainer
{
    private String standTextureName;
    
    public LOTRBlockKebabStand(final String s) {
        super(Material.circuits);
        this.standTextureName = s;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        this.setHardness(0.0f);
        this.setResistance(1.0f);
        this.setStepSound(Block.soundTypeWood);
        this.setcreativeTab((CreativeTabs)LOTRCreativeTabs.tabUtil);
    }
    
    public TileEntity createNewTileEntity(final World world, final int i) {
        return new LOTRTileEntityKebabStand();
    }
    
    public String getStandTextureName() {
        return this.standTextureName;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister iconregister) {
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final int i, final int j) {
        return Blocks.planks.getIcon(i, 0);
    }
    
    @SideOnly(Side.CLIENT)
    public String getItemIconName() {
        return this.getTextureName();
    }
    
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    public boolean isOpaqueCube() {
        return false;
    }
    
    public int getRenderType() {
        return -1;
    }
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World world, final int i, final int j, final int k) {
        return null;
    }
    
    public boolean canBlockStay(final World world, final int i, final int j, final int k) {
        return true;
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
            meta |= 0x8;
            world.setBlockMetadata(i, j, k, meta, 4);
        }
        this.dropBlockAsItem(world, i, j, k, meta, 0);
        super.onBlockHarvested(world, i, j, k, meta, entityplayer);
    }
    
    public ArrayList<ItemStack> getDrops(final World world, final int i, final int j, final int k, final int meta, final int fortune) {
        final ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
        if ((meta & 0x8) == 0x0) {
            final ItemStack itemstack = this.getKebabStandDrop(world, i, j, k, meta);
            final LOTRTileEntityKebabStand kebabStand = (LOTRTileEntityKebabStand)world.getTileEntity(i, j, k);
            if (kebabStand != null) {
                drops.add(itemstack);
            }
        }
        return drops;
    }
    
    public ItemStack getKebabStandDrop(final World world, final int i, final int j, final int k, final int metadata) {
        final ItemStack itemstack = new ItemStack(Item.getItemFromBlock((Block)this));
        final LOTRTileEntityKebabStand kebabStand = (LOTRTileEntityKebabStand)world.getTileEntity(i, j, k);
        if (kebabStand != null) {
            LOTRItemKebabStand.setKebabData(itemstack, kebabStand);
        }
        return itemstack;
    }
    
    public ItemStack getPickBlock(final MovingObjectPosition target, final World world, final int i, final int j, final int k) {
        world.markBlockForUpdate(i, j, k);
        return this.getKebabStandDrop(world, i, j, k, world.getBlockMetadata(i, j, k));
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
        final TileEntity tileentity = world.getTileEntity(i, j, k);
        if (tileentity instanceof LOTRTileEntityKebabStand) {
            final LOTRTileEntityKebabStand kebabStand = (LOTRTileEntityKebabStand)tileentity;
            LOTRItemKebabStand.loadKebabData(itemstack, kebabStand);
            kebabStand.onReplaced();
        }
    }
    
    public boolean onBlockActivated(final World world, final int i, final int j, final int k, final EntityPlayer entityplayer, final int side, final float f, final float f1, final float f2) {
        final TileEntity tileentity = world.getTileEntity(i, j, k);
        if (tileentity instanceof LOTRTileEntityKebabStand) {
            final LOTRTileEntityKebabStand stand = (LOTRTileEntityKebabStand)tileentity;
            final ItemStack heldItem = entityplayer.getHeldItem();
            if (!stand.isCooked() && stand.isMeat(heldItem)) {
                if (stand.hasEmptyMeatSlot()) {
                    if (!world.isClient && stand.addMeat(heldItem) && !entityplayer.capabilities.isCreativeMode) {
                        final ItemStack itemStack = heldItem;
                        --itemStack.stackSize;
                    }
                    return true;
                }
            }
            else if (stand.getMeatAmount() > 0) {
                if (!world.isClient) {
                    final boolean wasCooked = stand.isCooked();
                    final ItemStack meat = stand.removeFirstMeat();
                    if (meat != null) {
                        if (!entityplayer.inventory.addItemStackToInventory(meat)) {
                            this.dropBlockAsItem_do(world, i, j, k, meat);
                        }
                        entityplayer.inventoryContainer.detectAndSendChanges();
                        world.playSoundEffect(i + 0.5, j + 0.5, k + 0.5, "random.pop", 0.5f, 0.5f + world.rand.nextFloat() * 0.5f);
                        if (wasCooked) {
                            LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.cookKebab);
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }
}
