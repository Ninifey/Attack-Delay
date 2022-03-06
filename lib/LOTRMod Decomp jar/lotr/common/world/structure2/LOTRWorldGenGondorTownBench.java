// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenGondorTownBench extends LOTRWorldGenGondorStructure
{
    public LOTRWorldGenGondorTownBench(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            for (int i2 = -2; i2 <= 2; ++i2) {
                for (int k2 = 0; k2 <= 2; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2) - 1;
                    if (!this.isSurface(world, i2, j2, k2)) {
                        return false;
                    }
                }
            }
        }
        int k3 = 0;
        int j3 = this.getTopBlock(world, 0, k3);
        this.setBlockAndMetadata(world, 0, j3, k3, super.rockSlabBlock, super.rockSlabMeta);
        this.setBlockAndMetadata(world, -1, j3, k3, super.rockStairBlock, 0);
        this.setBlockAndMetadata(world, 1, j3, k3, super.rockStairBlock, 1);
        for (int i3 = -1; i3 <= 1; ++i3) {
            this.setGrassToDirt(world, i3, j3 - 1, k3);
            this.layFoundation(world, i3, j3 - 1, k3);
        }
        k3 = 2;
        j3 = this.getTopBlock(world, 0, k3);
        for (int i3 = -1; i3 <= 1; ++i3) {
            this.setBlockAndMetadata(world, i3, j3, k3, super.rockSlabBlock, super.rockSlabMeta | 0x8);
        }
        for (final int i4 : new int[] { -2, 2 }) {
            this.setBlockAndMetadata(world, i4, j3, k3, super.rockSlabDoubleBlock, super.rockSlabDoubleMeta);
            this.setGrassToDirt(world, i4, j3 - 1, k3);
            this.layFoundation(world, i4, j3 - 1, k3);
        }
        return true;
    }
    
    private void layFoundation(final World world, final int i, final int j, final int k) {
        for (int j2 = j; (j2 >= j || !this.isOpaque(world, i, j2, k)) && this.getY(j2) >= 0; --j2) {
            this.setBlockAndMetadata(world, i, j2, k, super.rockSlabDoubleBlock, super.rockSlabDoubleMeta);
            this.setGrassToDirt(world, i, j2 - 1, k);
        }
    }
}
