// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.feature;

import net.minecraft.init.Blocks;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.world.IBlockAccess;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.WorldGenTrees;

public class LOTRWorldGenShrub extends WorldGenTrees
{
    private Block woodBlock;
    private int woodMeta;
    private Block leafBlock;
    private int leafMeta;
    
    public LOTRWorldGenShrub(final Block w1, final int w2, final Block l1, final int l2) {
        super(false);
        this.woodBlock = w1;
        this.woodMeta = w2;
        this.leafBlock = l1;
        this.leafMeta = l2;
    }
    
    public boolean generate(final World world, final Random random, final int i, int j, final int k) {
        do {
            final Block block = world.getBlock(i, j, k);
            if (!block.isLeaves((IBlockAccess)world, i, j, k) && !block.isAir((IBlockAccess)world, i, j, k)) {
                break;
            }
        } while (--j > 0);
        final Block below = world.getBlock(i, j, k);
        if (below.canSustainPlant((IBlockAccess)world, i, j, k, ForgeDirection.UP, (IPlantable)Blocks.sapling)) {
            ++j;
            this.func_150516_a(world, i, j, k, this.woodBlock, this.woodMeta);
            for (int j2 = j; j2 <= j + 2; ++j2) {
                final int j3 = j2 - j;
                for (int range = 2 - j3, i2 = i - range; i2 <= i + range; ++i2) {
                    for (int k2 = k - range; k2 <= k + range; ++k2) {
                        final int i3 = i2 - i;
                        final int k3 = k2 - k;
                        if ((Math.abs(i3) != range || Math.abs(k3) != range || random.nextInt(2) != 0) && world.getBlock(i2, j2, k2).canBeReplacedByLeaves((IBlockAccess)world, i2, j2, k2)) {
                            this.func_150516_a(world, i2, j2, k2, this.leafBlock, this.leafMeta);
                        }
                    }
                }
            }
        }
        return true;
    }
}
