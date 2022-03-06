// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenRohanFortCorner extends LOTRWorldGenRohanStructure
{
    public LOTRWorldGenRohanFortCorner(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected boolean oneWoodType() {
        return true;
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        for (int width = 1, i2 = -width; i2 <= width; ++i2) {
            for (int k2 = -width; k2 <= width; ++k2) {
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                for (int j2 = 1; (j2 >= 0 || !this.isOpaque(world, i2, j2, k2)) && this.getY(j2) >= 0; --j2) {
                    this.setBlockAndMetadata(world, i2, j2, k2, super.rockSlabDoubleBlock, super.rockSlabDoubleMeta);
                    this.setGrassToDirt(world, i2, j2 - 1, k2);
                }
                for (int j2 = 2; j2 <= 3; ++j2) {
                    this.setBlockAndMetadata(world, i2, j2, k2, super.brickBlock, super.brickMeta);
                }
                for (int j2 = 4; j2 <= 7; ++j2) {
                    this.setBlockAndMetadata(world, i2, j2, k2, super.woodBeamBlock, super.woodBeamMeta);
                }
                this.setBlockAndMetadata(world, i2, 8, k2, super.plankSlabBlock, super.plankSlabMeta);
            }
        }
        return true;
    }
}
