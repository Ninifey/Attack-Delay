// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.block.material.Material;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class LOTRItemWaterPlant extends ItemBlock
{
    public LOTRItemWaterPlant(final Block block) {
        super(block);
    }
    
    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        return tryPlaceWaterPlant(this, itemstack, world, entityplayer, this.getMovingObjectPositionFromPlayer(world, entityplayer, true));
    }
    
    public static ItemStack tryPlaceWaterPlant(final ItemBlock itemblock, final ItemStack itemstack, final World world, final EntityPlayer entityplayer, final MovingObjectPosition targetBlock) {
        if (targetBlock == null) {
            return itemstack;
        }
        if (targetBlock.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            final int i = targetBlock.blockX;
            final int j = targetBlock.blockY;
            final int k = targetBlock.blockZ;
            if (!world.canMineBlock(entityplayer, i, j, k)) {
                return itemstack;
            }
            if (!entityplayer.canPlayerEdit(i, j, k, targetBlock.sideHit, itemstack)) {
                return itemstack;
            }
            final Block block = itemblock.field_150939_a;
            final int meta = itemblock.getMetadata(itemstack.getItemDamage());
            if (world.getBlock(i, j, k).getMaterial() == Material.water && world.getBlockMetadata(i, j, k) == 0 && world.isAirBlock(i, j + 1, k) && block.canPlaceBlockAt(world, i, j + 1, k) && itemblock.placeBlockAt(itemstack, entityplayer, world, i, j + 1, k, 1, 0.5f, 0.5f, 0.5f, meta)) {
                final Block.SoundType stepsound = block.stepSound;
                world.playSoundEffect((double)(i + 0.5f), (double)(j + 0.5f), (double)(k + 0.5f), stepsound.func_150496_b(), (stepsound.getVolume() + 1.0f) / 2.0f, stepsound.getFrequency() * 0.8f);
                if (!entityplayer.capabilities.isCreativeMode) {
                    --itemstack.stackSize;
                }
            }
        }
        return itemstack;
    }
}
