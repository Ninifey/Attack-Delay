// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.init.Blocks;
import net.minecraft.block.Block;
import lotr.common.world.feature.LOTRTreeType;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRBlockSapling8 extends LOTRBlockSaplingBase
{
    public LOTRBlockSapling8() {
        this.setSaplingNames("plum", "redwood", "pomegranate", "palm");
    }
    
    @Override
    public void growTree(final World world, final int i, final int j, final int k, final Random random) {
        final int meta = world.getBlockMetadata(i, j, k) & 0x7;
        WorldGenerator treeGen = null;
        int trunkNeg = 0;
        int trunkPos = 0;
        int xOffset = 0;
        int zOffset = 0;
        if (meta == 0) {
            treeGen = (WorldGenerator)LOTRTreeType.PLUM.create(true, random);
        }
        if (meta == 1) {
            final int[] tree5x5 = LOTRBlockSaplingBase.findSaplingSquare(world, i, j, k, (Block)this, meta, -2, 2, -4, 4);
            if (tree5x5 != null) {
                treeGen = (WorldGenerator)LOTRTreeType.REDWOOD_5.create(true, random);
                trunkNeg = 2;
                trunkPos = 2;
                xOffset = tree5x5[0];
                zOffset = tree5x5[1];
            }
            if (treeGen == null) {
                final int[] tree4x4 = LOTRBlockSaplingBase.findSaplingSquare(world, i, j, k, (Block)this, meta, -1, 2, -2, 1);
                if (tree4x4 != null) {
                    treeGen = (WorldGenerator)LOTRTreeType.REDWOOD_4.create(true, random);
                    trunkNeg = 1;
                    trunkPos = 2;
                    xOffset = tree4x4[0];
                    zOffset = tree4x4[1];
                }
            }
            if (treeGen == null) {
                final int[] tree3x3 = LOTRBlockSaplingBase.findPartyTree(world, i, j, k, (Block)this, meta);
                if (tree3x3 != null) {
                    treeGen = (WorldGenerator)LOTRTreeType.REDWOOD_3.create(true, random);
                    trunkNeg = 1;
                    trunkPos = 1;
                    xOffset = tree3x3[0];
                    zOffset = tree3x3[1];
                }
            }
            if (treeGen == null) {
                final int[] tree2x2 = LOTRBlockSaplingBase.findSaplingSquare(world, i, j, k, (Block)this, meta, 0, 1, -1, 0);
                if (tree2x2 != null) {
                    treeGen = (WorldGenerator)LOTRTreeType.REDWOOD_2.create(true, random);
                    trunkNeg = 0;
                    trunkPos = 1;
                    xOffset = tree2x2[0];
                    zOffset = tree2x2[1];
                }
            }
            if (treeGen == null) {
                trunkPos = (trunkNeg = 0);
                xOffset = 0;
                zOffset = 0;
                treeGen = (WorldGenerator)LOTRTreeType.REDWOOD.create(true, random);
            }
        }
        if (meta == 2) {
            treeGen = (WorldGenerator)LOTRTreeType.POMEGRANATE.create(true, random);
        }
        if (meta == 3) {
            treeGen = (WorldGenerator)LOTRTreeType.PALM.create(true, random);
        }
        for (int i2 = -trunkNeg; i2 <= trunkPos; ++i2) {
            for (int k2 = -trunkNeg; k2 <= trunkPos; ++k2) {
                world.setBlock(i + xOffset + i2, j, k + zOffset + k2, Blocks.air, 0, 4);
            }
        }
        if (treeGen != null && !treeGen.generate(world, random, i + xOffset, j, k + zOffset)) {
            for (int i2 = -trunkNeg; i2 <= trunkPos; ++i2) {
                for (int k2 = -trunkNeg; k2 <= trunkPos; ++k2) {
                    world.setBlock(i + xOffset + i2, j, k + zOffset + k2, (Block)this, meta, 4);
                }
            }
        }
    }
}
