// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.feature;

import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;
import lotr.common.LOTRMod;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class LOTRWorldGenBanana extends WorldGenAbstractTree
{
    public LOTRWorldGenBanana(final boolean flag) {
        super(flag);
    }
    
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        final int height = 2 + random.nextInt(3);
        final int[] leaves = new int[4];
        for (int l = 0; l < 4; ++l) {
            leaves[l] = 1 + random.nextInt(3);
        }
        if (j < 1 || j + height + 5 > 256) {
            return false;
        }
        if (!this.isReplaceable(world, i, j, k)) {
            return false;
        }
        final Block below = world.getBlock(i, j - 1, k);
        if (!below.canSustainPlant((IBlockAccess)world, i, j - 1, k, ForgeDirection.UP, (IPlantable)LOTRMod.sapling2)) {
            return false;
        }
        for (int m = 0; m < height + 2; ++m) {
            if (!this.isReplaceable(world, i, j + m, k)) {
                return false;
            }
        }
        for (int m = 0; m < 4; ++m) {
            final ForgeDirection dir = ForgeDirection.getOrientation(m + 2);
            for (int l2 = -1; l2 < leaves[m]; ++l2) {
                if (!this.isReplaceable(world, i + dir.offsetX, j + height + l2, k + dir.offsetZ)) {
                    return false;
                }
            }
            for (int l2 = -1; l2 < 1; ++l2) {
                if (!this.isReplaceable(world, i + dir.offsetX * 2, j + height + leaves[m] + l2, k + dir.offsetZ * 2)) {
                    return false;
                }
            }
        }
        for (int m = 0; m < height + 2; ++m) {
            this.func_150516_a(world, i, j + m, k, LOTRMod.wood2, 3);
        }
        for (int m = 0; m < 4; ++m) {
            final ForgeDirection dir = ForgeDirection.getOrientation(m + 2);
            for (int l2 = 0; l2 < leaves[m]; ++l2) {
                this.func_150516_a(world, i + dir.offsetX, j + height + l2, k + dir.offsetZ, LOTRMod.leaves2, 3);
            }
            this.func_150516_a(world, i + dir.getOpposite().offsetX, j + height - 1, k + dir.getOpposite().offsetZ, LOTRMod.bananaBlock, m);
            for (int l2 = -1; l2 < 1; ++l2) {
                this.func_150516_a(world, i + dir.offsetX * 2, j + height + leaves[m] + l2, k + dir.offsetZ * 2, LOTRMod.leaves2, 3);
            }
        }
        world.getBlock(i, j - 1, k).onPlantGrow(world, i, j - 1, k, i, j, k);
        return true;
    }
}
