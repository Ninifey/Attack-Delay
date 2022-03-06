// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenGondorLampPost extends LOTRWorldGenGondorStructure
{
    public LOTRWorldGenGondorLampPost(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            final int i2 = 0;
            final int k2 = 0;
            final int j2 = this.getTopBlock(world, i2, k2) - 1;
            if (!this.isSurface(world, i2, j2, k2)) {
                return false;
            }
        }
        for (int j3 = 0; (j3 >= 0 || !this.isOpaque(world, 0, j3, 0)) && this.getY(j3) >= 0; --j3) {
            this.setBlockAndMetadata(world, 0, j3, 0, super.brickBlock, super.brickMeta);
            this.setGrassToDirt(world, 0, j3 - 1, 0);
        }
        this.setBlockAndMetadata(world, 0, 1, 0, super.pillarBlock, super.pillarMeta);
        for (int j3 = 2; j3 <= 4; ++j3) {
            this.setBlockAndMetadata(world, 0, j3, 0, super.rockWallBlock, super.rockWallMeta);
        }
        this.setBlockAndMetadata(world, 0, 5, 0, super.rockSlabDoubleBlock, super.rockSlabDoubleMeta);
        this.setBlockAndMetadata(world, -1, 5, 0, Blocks.torch, 1);
        this.setBlockAndMetadata(world, 1, 5, 0, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 0, 5, -1, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 0, 5, 1, Blocks.torch, 3);
        return true;
    }
}
