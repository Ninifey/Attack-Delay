// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.feature;

import net.minecraft.world.IBlockAccess;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.util.MathHelper;
import java.util.Random;
import net.minecraft.world.World;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class LOTRWorldGenFir extends WorldGenAbstractTree
{
    private Block woodBlock;
    private int woodMeta;
    private Block leafBlock;
    private int leafMeta;
    private int minHeight;
    private int maxHeight;
    
    public LOTRWorldGenFir(final boolean flag) {
        super(flag);
        this.woodBlock = LOTRMod.wood4;
        this.woodMeta = 3;
        this.leafBlock = LOTRMod.leaves4;
        this.leafMeta = 3;
        this.minHeight = 6;
        this.maxHeight = 13;
    }
    
    public LOTRWorldGenFir setMinMaxHeight(final int min, final int max) {
        this.minHeight = min;
        this.maxHeight = max;
        return this;
    }
    
    public boolean generate(final World world, final Random random, final int i, final int j, final int k) {
        final int height = MathHelper.getRandomIntegerInRange(random, this.minHeight, this.maxHeight);
        boolean flag = true;
        if (j >= 1 && height + 2 <= 256) {
            for (int j2 = j; j2 <= j + height + 2; ++j2) {
                int range = 1;
                if (j2 == j) {
                    range = 0;
                }
                if (j2 >= j + height - 1) {
                    range = 2;
                }
                for (int i2 = i - range; i2 <= i + range && flag; ++i2) {
                    for (int k2 = k - range; k2 <= k + range && flag; ++k2) {
                        if (j2 >= 0 && j2 < 256) {
                            if (!this.isReplaceable(world, i2, j2, k2)) {
                                flag = false;
                            }
                        }
                        else {
                            flag = false;
                        }
                    }
                }
            }
        }
        else {
            flag = false;
        }
        final Block below = world.getBlock(i, j - 1, k);
        final boolean isSoil = below.canSustainPlant((IBlockAccess)world, i, j - 1, k, ForgeDirection.UP, (IPlantable)Blocks.sapling);
        if (!isSoil) {
            flag = false;
        }
        if (!flag) {
            return false;
        }
        below.onPlantGrow(world, i, j - 1, k, i, j, k);
        int leafLevel = j + height + 2;
        for (int leafLayers = 3, l = 0; l <= leafLayers * 2; ++l) {
            for (int leafRange = l / 2, i3 = i - leafRange; i3 <= i + leafRange; ++i3) {
                for (int k3 = k - leafRange; k3 <= k + leafRange; ++k3) {
                    final Block block = world.getBlock(i3, leafLevel, k3);
                    final int i4 = Math.abs(i3 - i);
                    final int k4 = Math.abs(k3 - k);
                    if (i4 + k4 <= leafRange && (block.isReplaceable((IBlockAccess)world, i3, leafLevel, k3) || block.isLeaves((IBlockAccess)world, i3, leafLevel, k3))) {
                        this.func_150516_a(world, i3, leafLevel, k3, this.leafBlock, this.leafMeta);
                    }
                }
            }
            --leafLevel;
        }
        for (int j3 = 0; j3 < height; ++j3) {
            this.func_150516_a(world, i, j + j3, k, this.woodBlock, this.woodMeta);
        }
        return true;
    }
}
