// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenRohanFortWall extends LOTRWorldGenRohanStructure
{
    private int xMin;
    private int xMax;
    
    public LOTRWorldGenRohanFortWall(final boolean flag) {
        this(flag, -4, 4);
    }
    
    public LOTRWorldGenRohanFortWall(final boolean flag, final int x0, final int x1) {
        super(flag);
        this.xMin = x0;
        this.xMax = x1;
    }
    
    @Override
    protected boolean oneWoodType() {
        return true;
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        for (int i2 = this.xMin; i2 <= this.xMax; ++i2) {
            final int i3 = Math.abs(i2);
            final int k2 = 0;
            this.findSurface(world, i2, k2);
            this.setupRandomBlocks(random);
            for (int j2 = 1; (j2 >= 0 || !this.isOpaque(world, i2, j2, k2)) && this.getY(j2) >= 0; --j2) {
                this.setBlockAndMetadata(world, i2, j2, k2, super.rockSlabDoubleBlock, super.rockSlabDoubleMeta);
                this.setGrassToDirt(world, i2, j2 - 1, k2);
            }
            for (int j2 = 2; j2 <= 2; ++j2) {
                this.setBlockAndMetadata(world, i2, j2, k2, super.brickBlock, super.brickMeta);
            }
            final int h = 5 + random.nextInt(2);
            for (int j3 = 3; j3 <= h; ++j3) {
                this.setBlockAndMetadata(world, i2, j3, k2, super.woodBeamBlock, super.woodBeamMeta);
            }
            if (random.nextBoolean()) {
                this.setBlockAndMetadata(world, i2, h + 1, k2, super.plankSlabBlock, super.plankSlabMeta);
            }
        }
        return true;
    }
}
