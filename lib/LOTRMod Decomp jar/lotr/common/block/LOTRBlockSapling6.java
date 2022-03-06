// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.block;

import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import lotr.common.world.feature.LOTRTreeType;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRBlockSapling6 extends LOTRBlockSaplingBase
{
    public LOTRBlockSapling6() {
        this.setSaplingNames("mahogany", "willow", "cypress", "olive");
    }
    
    @Override
    public void growTree(final World world, final int i, final int j, final int k, final Random random) {
        final int meta = world.getBlockMetadata(i, j, k) & 0x7;
        WorldGenerator treeGen = null;
        int extraTrunkWidth = 0;
        int xOffset = 0;
        int zOffset = 0;
        if (meta == 0) {
            treeGen = (WorldGenerator)LOTRTreeType.MAHOGANY.create(true, random);
        }
        else if (meta == 1) {
            treeGen = (WorldGenerator)LOTRTreeType.WILLOW.create(true, random);
        }
        else if (meta == 2) {
            for (int i2 = 0; i2 >= -1; --i2) {
                for (int k2 = 0; k2 >= -1; --k2) {
                    if (this.isSameSapling(world, i + i2, j, k + k2, meta) && this.isSameSapling(world, i + i2 + 1, j, k + k2, meta) && this.isSameSapling(world, i + i2, j, k + k2 + 1, meta) && this.isSameSapling(world, i + i2 + 1, j, k + k2 + 1, meta)) {
                        treeGen = (WorldGenerator)LOTRTreeType.CYPRESS_LARGE.create(true, random);
                        extraTrunkWidth = 1;
                        xOffset = i2;
                        zOffset = k2;
                        break;
                    }
                }
                if (treeGen != null) {
                    break;
                }
            }
            if (treeGen == null) {
                xOffset = 0;
                zOffset = 0;
                treeGen = (WorldGenerator)LOTRTreeType.CYPRESS.create(true, random);
            }
        }
        else if (meta == 3) {
            for (int i2 = 0; i2 >= -1; --i2) {
                for (int k2 = 0; k2 >= -1; --k2) {
                    if (this.isSameSapling(world, i + i2, j, k + k2, meta) && this.isSameSapling(world, i + i2 + 1, j, k + k2, meta) && this.isSameSapling(world, i + i2, j, k + k2 + 1, meta) && this.isSameSapling(world, i + i2 + 1, j, k + k2 + 1, meta)) {
                        treeGen = (WorldGenerator)LOTRTreeType.OLIVE_LARGE.create(true, random);
                        extraTrunkWidth = 1;
                        xOffset = i2;
                        zOffset = k2;
                        break;
                    }
                }
                if (treeGen != null) {
                    break;
                }
            }
            if (treeGen == null) {
                xOffset = 0;
                zOffset = 0;
                treeGen = (WorldGenerator)LOTRTreeType.OLIVE.create(true, random);
            }
        }
        for (int i2 = 0; i2 <= extraTrunkWidth; ++i2) {
            for (int k2 = 0; k2 <= extraTrunkWidth; ++k2) {
                world.setBlock(i + xOffset + i2, j, k + zOffset + k2, Blocks.air, 0, 4);
            }
        }
        if (treeGen != null && !treeGen.generate(world, random, i + xOffset, j, k + zOffset)) {
            for (int i2 = 0; i2 <= extraTrunkWidth; ++i2) {
                for (int k2 = 0; k2 <= extraTrunkWidth; ++k2) {
                    world.setBlock(i + xOffset + i2, j, k + zOffset + k2, (Block)this, meta, 4);
                }
            }
        }
    }
}
