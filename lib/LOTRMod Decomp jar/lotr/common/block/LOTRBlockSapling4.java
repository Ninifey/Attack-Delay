// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.init.Blocks;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.block.Block;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRBlockSapling4 extends LOTRBlockSaplingBase
{
    public LOTRBlockSapling4() {
        this.setSaplingNames("chestnut", "baobab", "cedar", "fir");
    }
    
    @Override
    public void incrementGrowth(final World world, final int i, final int j, final int k, final Random random) {
        final int meta = world.getBlockMetadata(i, j, k) & 0x7;
        if (meta == 1 && random.nextInt(4) > 0) {
            return;
        }
        super.incrementGrowth(world, i, j, k, random);
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
            final int[] partyTree = LOTRBlockSaplingBase.findPartyTree(world, i, j, k, (Block)this, meta);
            if (partyTree != null) {
                treeGen = (WorldGenerator)LOTRTreeType.CHESTNUT_PARTY.create(true, random);
                trunkPos = (trunkNeg = 1);
                xOffset = partyTree[0];
                zOffset = partyTree[1];
            }
            if (treeGen == null) {
                if (random.nextInt(10) == 0) {
                    treeGen = (WorldGenerator)LOTRTreeType.CHESTNUT_LARGE.create(true, random);
                }
                else {
                    treeGen = (WorldGenerator)LOTRTreeType.CHESTNUT.create(true, random);
                }
                trunkPos = (trunkNeg = 0);
                xOffset = 0;
                zOffset = 0;
            }
        }
        if (meta == 1) {
            treeGen = (WorldGenerator)LOTRTreeType.BAOBAB.create(true, random);
        }
        if (meta == 2) {
            treeGen = (WorldGenerator)LOTRTreeType.CEDAR.create(true, random);
        }
        if (meta == 3) {
            treeGen = (WorldGenerator)LOTRTreeType.FIR.create(true, random);
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
