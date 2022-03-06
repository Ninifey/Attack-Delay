// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import com.google.common.math.IntMath;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenGondorFortWallCorner extends LOTRWorldGenGondorStructure
{
    public LOTRWorldGenGondorFortWallCorner(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        for (int l = -8; l <= 8; ++l) {
            int i2;
            int k2;
            if (l >= 0) {
                i2 = l / 2;
                k2 = -((l + 1) / 2);
            }
            else {
                i2 = -(Math.abs(l) + 1) / 2;
                k2 = Math.abs(l) / 2;
            }
            this.findSurface(world, i2, k2);
            final boolean pillar = Math.abs(l) == 3;
            if (pillar) {
                for (int j2 = 4; (j2 >= 1 || !this.isOpaque(world, i2, j2, k2)) && this.getY(j2) >= 0; --j2) {
                    this.setBlockAndMetadata(world, i2, j2, k2, super.pillar2Block, super.pillar2Meta);
                    this.setGrassToDirt(world, i2, j2 - 1, k2);
                }
            }
            else {
                for (int j2 = 4; (j2 >= 1 || !this.isOpaque(world, i2, j2, k2)) && this.getY(j2) >= 0; --j2) {
                    this.setBlockAndMetadata(world, i2, j2, k2, super.brickBlock, super.brickMeta);
                    this.setGrassToDirt(world, i2, j2 - 1, k2);
                }
            }
            this.setBlockAndMetadata(world, i2, 5, k2, super.brick2WallBlock, super.brick2WallMeta);
            if (pillar) {
                this.setBlockAndMetadata(world, i2, 6, k2, Blocks.torch, 5);
            }
            if (IntMath.mod(l, 2) == 0) {
                if (l >= 0) {
                    this.setBlockAndMetadata(world, i2, 4, k2 + 1, super.rockSlabBlock, super.rockSlabMeta | 0x8);
                }
                else {
                    this.setBlockAndMetadata(world, i2 + 1, 4, k2, super.rockSlabBlock, super.rockSlabMeta | 0x8);
                }
            }
        }
        return true;
    }
}
