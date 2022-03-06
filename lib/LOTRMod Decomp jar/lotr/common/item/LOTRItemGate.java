// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.entity.Entity;
import net.minecraft.util.Direction;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;
import lotr.common.block.LOTRBlockGate;
import net.minecraft.item.ItemBlock;

public class LOTRItemGate extends ItemBlock
{
    private LOTRBlockGate gateBlock;
    
    public LOTRItemGate(final Block block) {
        super(block);
        this.gateBlock = (LOTRBlockGate)block;
    }
    
    public boolean placeBlockAt(final ItemStack itemstack, final EntityPlayer entityplayer, final World world, final int i, final int j, final int k, final int side, final float f, final float f1, final float f2, int meta) {
        final int yaw = MathHelper.floor_double(((Entity)entityplayer).rotationYaw * 4.0f / 360.0f + 0.5) & 0x3;
        final float horizontalAngle = 40.0f;
        final boolean lookingUp = ((Entity)entityplayer).rotationPitch < -horizontalAngle;
        final boolean lookingDown = ((Entity)entityplayer).rotationPitch > horizontalAngle;
        final boolean fullBlock = this.gateBlock.fullBlockGate;
        if (side == 0 || side == 1) {
            meta = Direction.directionToFacing[yaw];
        }
        else if (lookingUp || lookingDown) {
            if (fullBlock) {
                if (((Entity)entityplayer).rotationPitch > 0.0f) {
                    meta = 0;
                }
                else {
                    meta = 1;
                }
            }
            else if (f1 > 0.5f) {
                meta = 0;
            }
            else {
                meta = 1;
            }
        }
        else {
            final int dir = Direction.facingToDirection[side];
            if (fullBlock) {
                meta = Direction.directionToFacing[Direction.rotateOpposite[dir]];
            }
            else {
                meta = Direction.directionToFacing[Direction.rotateLeft[dir]];
            }
        }
        return super.placeBlockAt(itemstack, entityplayer, world, i, j, k, side, f, f1, f2, meta);
    }
}
