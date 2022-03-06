// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.feature;

import net.minecraft.world.IBlockAccess;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class LOTRWorldGenDeadTrees extends WorldGenAbstractTree
{
    private Block woodBlock;
    private int woodMeta;
    
    public LOTRWorldGenDeadTrees(final Block block, final int i) {
        super(false);
        this.woodBlock = block;
        this.woodMeta = i;
    }
    
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        final Block below = world.getBlock(i, j - 1, k);
        if (!below.canSustainPlant((IBlockAccess)world, i, j - 1, k, ForgeDirection.UP, (IPlantable)Blocks.sapling) && below != Blocks.stone && below != Blocks.sand && below != Blocks.gravel) {
            return false;
        }
        below.onPlantGrow(world, i, j - 1, k, i, j, k);
        final int height = 3 + random.nextInt(4);
        for (int j2 = j; j2 < j + height; ++j2) {
            this.func_150516_a(world, i, j2, k, this.woodBlock, this.woodMeta);
        }
        for (int branch = 0; branch < 4; ++branch) {
            final int branchLength = 3 + random.nextInt(5);
            int branchHorizontalPos = 0;
            int branchVerticalPos = j + height - 1 - random.nextInt(2);
            for (int l = 0; l < branchLength; ++l) {
                if (random.nextInt(4) == 0) {
                    ++branchHorizontalPos;
                }
                if (random.nextInt(3) != 0) {
                    ++branchVerticalPos;
                }
                switch (branch) {
                    case 0: {
                        this.func_150516_a(world, i - branchHorizontalPos, branchVerticalPos, k, this.woodBlock, this.woodMeta | 0xC);
                        break;
                    }
                    case 1: {
                        this.func_150516_a(world, i, branchVerticalPos, k + branchHorizontalPos, this.woodBlock, this.woodMeta | 0xC);
                        break;
                    }
                    case 2: {
                        this.func_150516_a(world, i + branchHorizontalPos, branchVerticalPos, k, this.woodBlock, this.woodMeta | 0xC);
                        break;
                    }
                    case 3: {
                        this.func_150516_a(world, i, branchVerticalPos, k - branchHorizontalPos, this.woodBlock, this.woodMeta | 0xC);
                        break;
                    }
                }
            }
        }
        return true;
    }
}
