// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.world.World;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.block.Block;

public class LOTRWorldGenRohanVillageGarden extends LOTRWorldGenRohanStructure
{
    private Block leafBlock;
    private int leafMeta;
    
    public LOTRWorldGenRohanVillageGarden(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        super.setupRandomBlocks(random);
        this.leafBlock = (Block)Blocks.leaves;
        this.leafMeta = 4;
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 2);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            for (int i2 = -3; i2 <= 3; ++i2) {
                for (int k2 = -1; k2 <= 1; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2) - 1;
                    if (!this.isSurface(world, i2, j2, k2)) {
                        return false;
                    }
                }
            }
        }
        for (int i2 = -3; i2 <= 3; ++i2) {
            for (int k2 = -1; k2 <= 1; ++k2) {
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                int j3 = 5;
                boolean foundSurface = false;
                while (j3 >= -5) {
                    if (this.isSurface(world, i2, j3 - 1, k2)) {
                        foundSurface = true;
                        break;
                    }
                    --j3;
                }
                if (foundSurface) {
                    if (i3 <= 2) {
                        if (random.nextInt(3) == 0) {
                            this.plantFlower(world, random, i2, j3, k2);
                        }
                        else {
                            int j4 = j3;
                            if (random.nextInt(5) == 0) {
                                ++j4;
                            }
                            for (int j5 = j3; j5 <= j4; ++j5) {
                                this.setBlockAndMetadata(world, i2, j5, k2, this.leafBlock, this.leafMeta);
                            }
                        }
                    }
                    if (i3 == 3) {
                        this.setBlockAndMetadata(world, i2, j3, k2, super.fenceBlock, super.fenceMeta);
                    }
                }
            }
        }
        return true;
    }
}
