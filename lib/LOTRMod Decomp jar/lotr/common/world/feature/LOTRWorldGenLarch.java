// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.feature;

import net.minecraft.block.Block;
import net.minecraftforge.common.IPlantable;
import lotr.common.LOTRMod;
import lotr.common.block.LOTRBlockSaplingBase;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.world.IBlockAccess;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class LOTRWorldGenLarch extends WorldGenAbstractTree
{
    public LOTRWorldGenLarch(final boolean flag) {
        super(flag);
    }
    
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        final int height = random.nextInt(9) + 8;
        final int trunkBaseHeight = 2 + random.nextInt(2);
        final int leafStart = height - trunkBaseHeight;
        final int leafWidth = 2 + random.nextInt(2);
        boolean flag = true;
        if (j < 1 || j + height + 1 > 256) {
            return false;
        }
        for (int j2 = j; j2 <= j + 1 + height && flag; ++j2) {
            final boolean flag2 = true;
            int range = 0;
            if (j2 - j < trunkBaseHeight) {
                range = 0;
            }
            else {
                range = leafWidth;
            }
            for (int i2 = i - range; i2 <= i + range && flag; ++i2) {
                for (int k2 = k - range; k2 <= k + range && flag; ++k2) {
                    if (j2 >= 0 && j2 < 256) {
                        final Block block = world.getBlock(i2, j2, k2);
                        if (!block.isAir((IBlockAccess)world, i2, j2, k2) && !block.isLeaves((IBlockAccess)world, i2, j2, k2)) {
                            flag = false;
                        }
                    }
                    else {
                        flag = false;
                    }
                }
            }
        }
        if (!flag) {
            return false;
        }
        final Block soil = world.getBlock(i, j - 1, k);
        final boolean isSoil = soil.canSustainPlant((IBlockAccess)world, i, j - 1, k, ForgeDirection.UP, (IPlantable)LOTRMod.sapling3);
        if (isSoil && j < 256 - height - 1) {
            soil.onPlantGrow(world, i, j - 1, k, i, j, k);
            int leafRange = random.nextInt(2);
            int maxLeafRange = 1;
            int minLeafRange = 0;
            for (int leafLayer = 0; leafLayer <= leafStart; ++leafLayer) {
                final int j3 = j + height - leafLayer;
                for (int i3 = i - leafRange; i3 <= i + leafRange; ++i3) {
                    final int i4 = i3 - i;
                    for (int k3 = k - leafRange; k3 <= k + leafRange; ++k3) {
                        final int k4 = k3 - k;
                        if ((Math.abs(i4) != leafRange || Math.abs(k4) != leafRange || leafRange <= 0) && world.getBlock(i3, j3, k3).canBeReplacedByLeaves((IBlockAccess)world, i3, j3, k3)) {
                            this.func_150516_a(world, i3, j3, k3, LOTRMod.leaves3, 1);
                        }
                    }
                }
                if (leafRange >= maxLeafRange) {
                    leafRange = minLeafRange;
                    minLeafRange = 1;
                    if (++maxLeafRange > leafWidth) {
                        maxLeafRange = leafWidth;
                    }
                }
                else {
                    ++leafRange;
                }
            }
            int trunkTop;
            for (trunkTop = random.nextInt(3), int j3 = 0; j3 < height - trunkTop; ++j3) {
                final Block block2 = world.getBlock(i, j + j3, k);
                if (block2.isAir((IBlockAccess)world, i, j + j3, k) || block2.isLeaves((IBlockAccess)world, i, j + j3, k)) {
                    this.func_150516_a(world, i, j + j3, k, LOTRMod.wood3, 1);
                }
            }
            return true;
        }
        return false;
    }
}
