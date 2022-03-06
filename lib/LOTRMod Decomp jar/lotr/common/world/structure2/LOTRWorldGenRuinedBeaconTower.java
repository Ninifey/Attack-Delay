// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenRuinedBeaconTower extends LOTRWorldGenStructureBase2
{
    public LOTRWorldGenRuinedBeaconTower(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, int j, final int k, final int rotation) {
        final int height = 4 + random.nextInt(4);
        j += height + 1;
        this.setOriginAndRotation(world, i, j, k, rotation, 3);
        for (int i2 = -2; i2 <= 2; ++i2) {
            for (int k2 = -2; k2 <= 2; ++k2) {
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                int j2 = 0;
                if ((i3 == 2 && k3 < 2) || (k3 == 2 && i3 < 2)) {
                    j2 -= random.nextInt(4);
                }
                while (!this.isOpaque(world, i2, j2, k2) && this.getY(j2) >= 0) {
                    if (i3 == 2 && k3 == 2) {
                        this.setBlockAndMetadata(world, i2, j2, k2, LOTRMod.pillar, 6);
                    }
                    else {
                        this.placeRandomBrick(world, random, i2, j2, k2);
                    }
                    this.setGrassToDirt(world, i2, j2 - 1, k2);
                    --j2;
                }
            }
        }
        for (int i2 = -1; i2 <= 1; ++i2) {
            for (int k2 = -1; k2 <= 1; ++k2) {
                this.setBlockAndMetadata(world, i2, 0, k2, LOTRMod.slabDouble, 2);
            }
        }
        this.setBlockAndMetadata(world, 0, 1, 0, LOTRMod.rock, 1);
        for (final int i4 : new int[] { -2, 2 }) {
            for (final int k4 : new int[] { -2, 2 }) {
                for (int pillarHeight = 1 + random.nextInt(5), j3 = 1; j3 <= pillarHeight; ++j3) {
                    this.setBlockAndMetadata(world, i4, j3, k4, LOTRMod.pillar, 6);
                }
            }
        }
        return true;
    }
    
    private void placeRandomBrick(final World world, final Random random, final int i, final int j, final int k) {
        if (random.nextInt(5) == 0) {
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick, 2 + random.nextInt(2));
        }
        else {
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.brick, 1);
        }
    }
    
    private void placeRandomSlab(final World world, final Random random, final int i, final int j, final int k, final boolean inverted) {
        if (random.nextInt(5) == 0) {
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle, 4 + random.nextInt(2) + (inverted ? 8 : 0));
        }
        else {
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.slabSingle, 3 + (inverted ? 8 : 0));
        }
    }
    
    private void placeRandomStairs(final World world, final Random random, final int i, final int j, final int k, final int metadata) {
        if (random.nextInt(5) == 0) {
            this.setBlockAndMetadata(world, i, j, k, random.nextBoolean() ? LOTRMod.stairsGondorBrickMossy : LOTRMod.stairsGondorBrickCracked, metadata);
        }
        else {
            this.setBlockAndMetadata(world, i, j, k, LOTRMod.stairsGondorBrick, metadata);
        }
    }
}
