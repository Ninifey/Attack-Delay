// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.feature;

import net.minecraft.block.Block;
import lotr.common.LOTRMod;
import net.minecraftforge.common.IPlantable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.block.BlockSapling;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.init.Blocks;
import lotr.common.world.biome.LOTRBiomeGenMordor;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class LOTRWorldGenCharredTrees extends WorldGenAbstractTree
{
    public LOTRWorldGenCharredTrees() {
        super(false);
    }
    
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        final Block below = world.getBlock(i, j - 1, k);
        final int meta = world.getBlockMetadata(i, j - 1, k);
        if (!LOTRBiomeGenMordor.isSurfaceMordorBlock(world, i, j - 1, k) && below != Blocks.stone && below != Blocks.sand && below != Blocks.gravel && !below.canSustainPlant((IBlockAccess)world, i, j - 1, k, ForgeDirection.UP, (IPlantable)Blocks.sapling)) {
            return false;
        }
        below.onPlantGrow(world, i, j - 1, k, i, j, k);
        final int height = 2 + random.nextInt(5);
        for (int j2 = j; j2 < j + height; ++j2) {
            world.setBlock(i, j2, k, LOTRMod.wood, 3, 2);
        }
        if (height >= 4) {
            for (int branch = 0; branch < 4; ++branch) {
                final int branchLength = 2 + random.nextInt(4);
                int branchHorizontalPos = 0;
                int branchVerticalPos = j + height - random.nextInt(2);
                for (int l = 0; l < branchLength; ++l) {
                    if (random.nextInt(4) == 0) {
                        ++branchHorizontalPos;
                    }
                    if (random.nextInt(3) != 0) {
                        ++branchVerticalPos;
                    }
                    switch (branch) {
                        case 0: {
                            world.setBlock(i - branchHorizontalPos, branchVerticalPos, k, LOTRMod.wood, 15, 2);
                            break;
                        }
                        case 1: {
                            world.setBlock(i, branchVerticalPos, k + branchHorizontalPos, LOTRMod.wood, 15, 2);
                            break;
                        }
                        case 2: {
                            world.setBlock(i + branchHorizontalPos, branchVerticalPos, k, LOTRMod.wood, 15, 2);
                            break;
                        }
                        case 3: {
                            world.setBlock(i, branchVerticalPos, k - branchHorizontalPos, LOTRMod.wood, 15, 2);
                            break;
                        }
                    }
                }
            }
        }
        return true;
    }
}
