// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.entity.Entity;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.world.World;
import net.minecraft.world.IBlockAccess;
import lotr.common.LOTRMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;

public class LOTRBlockRope extends LOTRBlockLadder
{
    private boolean canRetract;
    
    public LOTRBlockRope(final boolean flag) {
        this.setHardness(0.4f);
        this.setStepSound(Block.soundTypeCloth);
        this.canRetract = flag;
    }
    
    @SideOnly(Side.CLIENT)
    public String getItemIconName() {
        return this.getTextureName();
    }
    
    public int getRenderType() {
        return LOTRMod.proxy.getRopeRenderID();
    }
    
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(final IBlockAccess world, final int i, final int j, final int k, final int side) {
        if (side == 0 || side == 1) {
            final Block block = world.getBlock(i, j, k);
            return block != this && !block.isOpaqueCube();
        }
        return true;
    }
    
    public boolean canPlaceBlockAt(final World world, final int i, final int j, final int k) {
        return world.getBlock(i, j + 1, k) == this || super.canPlaceBlockAt(world, i, j, k);
    }
    
    public int onBlockPlaced(final World world, final int i, final int j, final int k, final int side, final float hitX, final float hitY, final float hitZ, final int meta) {
        int placeMeta = super.onBlockPlaced(world, i, j, k, side, hitX, hitY, hitZ, meta);
        if (placeMeta == 0 && world.getBlock(i, j + 1, k) == this) {
            placeMeta = world.getBlockMetadata(i, j + 1, k);
        }
        return placeMeta;
    }
    
    public boolean canBlockStay(final World world, final int i, final int j, final int k) {
        return this.canPlaceBlockAt(world, i, j, k);
    }
    
    public void onNeighborBlockChange(final World world, final int i, final int j, final int k, final Block block) {
        if (world.getBlock(i, j + 1, k) != this) {
            super.onNeighborBlockChange(world, i, j, k, block);
        }
    }
    
    private boolean canExtendRopeWithMetadata(final World world, final int i, final int j, final int k, final int meta) {
        if (world.getBlock(i, j + 1, k) == this) {
            return true;
        }
        if (meta == 2) {
            return world.isSideSolid(i, j, k + 1, ForgeDirection.NORTH);
        }
        if (meta == 3) {
            return world.isSideSolid(i, j, k - 1, ForgeDirection.SOUTH);
        }
        if (meta == 4) {
            return world.isSideSolid(i + 1, j, k, ForgeDirection.WEST);
        }
        return meta == 5 && world.isSideSolid(i - 1, j, k, ForgeDirection.EAST);
    }
    
    public boolean onBlockActivated(final World world, final int i, final int j, final int k, final EntityPlayer entityplayer, final int side, final float f, final float f1, final float f2) {
        final boolean lookingUpOrDown = ((Entity)entityplayer).rotationPitch <= 0.0f;
        final int lookDir = lookingUpOrDown ? 1 : -1;
        final ItemStack itemstack = entityplayer.getHeldItem();
        if (itemstack != null && itemstack.getItem() == Item.getItemFromBlock((Block)this)) {
            int j2;
            for (j2 = j; j2 >= 0; j2 += lookDir) {
                if (j2 >= world.getHeight()) {
                    break;
                }
                final Block block = world.getBlock(i, j2, k);
                if (block != this) {
                    break;
                }
            }
            if (j2 >= 0 && j2 < world.getHeight()) {
                final Block block = world.getBlock(i, j2, k);
                if (this.canPlaceBlockOnSide(world, i, j2, k, side) && block.isReplaceable((IBlockAccess)world, i, j2, k) && !block.getMaterial().isLiquid()) {
                    final int thisMeta = world.getBlockMetadata(i, j, k);
                    if (this.canExtendRopeWithMetadata(world, i, j2, k, thisMeta)) {
                        world.setBlock(i, j2, k, (Block)this, thisMeta, 3);
                        world.playSoundEffect((double)(i + 0.5f), (double)(j2 + 0.5f), (double)(k + 0.5f), ((Block)this).stepSound.func_150496_b(), (((Block)this).stepSound.getVolume() + 1.0f) / 2.0f, ((Block)this).stepSound.getFrequency() * 0.8f);
                        if (!entityplayer.capabilities.isCreativeMode) {
                            final ItemStack itemStack = itemstack;
                            --itemStack.stackSize;
                        }
                        if (itemstack.stackSize <= 0) {
                            entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, (ItemStack)null);
                        }
                        return true;
                    }
                }
            }
        }
        else if (!entityplayer.isOnLadder() && this.canRetract) {
            if (!world.isClient) {
                boolean invAdded = false;
                for (int j3 = j; j3 >= 0; j3 += lookDir) {
                    if (j3 >= world.getHeight()) {
                        break;
                    }
                    final Block block2 = world.getBlock(i, j3, k);
                    final int meta = world.getBlockMetadata(i, j3, k);
                    if (block2 != this) {
                        break;
                    }
                    if (!entityplayer.capabilities.isCreativeMode) {
                        final List<ItemStack> drops = (List<ItemStack>)block2.getDrops(world, i, j3, k, meta, 0);
                        for (final ItemStack drop : drops) {
                            if (entityplayer.inventory.addItemStackToInventory(drop)) {
                                invAdded = true;
                            }
                            else {
                                entityplayer.dropPlayerItemWithRandomChoice(drop, false);
                            }
                        }
                    }
                    world.setBlockToAir(i, j3, k);
                    world.playSoundEffect((double)(i + 0.5f), (double)(j3 + 0.5f), (double)(k + 0.5f), ((Block)this).stepSound.getDigResourcePath(), (((Block)this).stepSound.getVolume() + 1.0f) / 2.0f, ((Block)this).stepSound.getFrequency() * 0.8f);
                }
                if (invAdded) {
                    ((EntityPlayer)entityplayer).openContainer.detectAndSendChanges();
                }
            }
            return true;
        }
        return false;
    }
}
