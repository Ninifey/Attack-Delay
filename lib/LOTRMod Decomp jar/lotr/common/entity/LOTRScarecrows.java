// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.block.BlockFence;
import net.minecraft.world.World;

public class LOTRScarecrows
{
    private static int RANGE;
    private static int Y_RANGE;
    
    public static boolean anyScarecrowsNearby(final World world, final int i, final int j, final int k) {
        for (int i2 = i - LOTRScarecrows.RANGE; i2 <= i + LOTRScarecrows.RANGE; ++i2) {
            for (int k2 = k - LOTRScarecrows.RANGE; k2 <= k + LOTRScarecrows.RANGE; ++k2) {
                for (int j2 = j - LOTRScarecrows.Y_RANGE; j2 <= j + LOTRScarecrows.Y_RANGE; ++j2) {
                    if (isScarecrowBase(world, i2, j2, k2)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public static boolean isScarecrowBase(final World world, final int i, final int j, final int k) {
        final Block block = world.getBlock(i, j, k);
        if (block instanceof BlockFence) {
            for (int j2 = j + 2; j2 <= j + 6; ++j2) {
                final Block above = world.getBlock(i, j2, k);
                if (above instanceof BlockPumpkin || above instanceof BlockSkull) {
                    final Block belowAbove = world.getBlock(i, j2 - 1, k);
                    if (belowAbove.isNormalCube()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    static {
        LOTRScarecrows.RANGE = 16;
        LOTRScarecrows.Y_RANGE = 8;
    }
}
