// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenMoredainMercDummy extends LOTRWorldGenStructureBase2
{
    public LOTRWorldGenMoredainMercDummy(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            for (int i2 = -1; i2 <= 1; ++i2) {
                for (int k2 = -1; k2 <= 1; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2) - 1;
                    if (j2 != this.getTopBlock(world, 0, 0) - 1) {
                        return false;
                    }
                    if (!this.isSurface(world, i2, j2, k2)) {
                        return false;
                    }
                    for (int j3 = j2 + 1; j3 <= j2 + 3; ++j3) {
                        if (this.isOpaque(world, i2, j3, k2)) {
                            return false;
                        }
                    }
                }
            }
        }
        this.setBlockAndMetadata(world, 0, 1, 0, LOTRMod.fence2, 2);
        this.setBlockAndMetadata(world, 0, 2, 0, Blocks.wool, 12);
        this.placeSkull(world, random, 0, 3, 0);
        this.setBlockAndMetadata(world, -1, 2, 0, Blocks.lever, 1);
        this.setBlockAndMetadata(world, 1, 2, 0, Blocks.lever, 2);
        return true;
    }
}
