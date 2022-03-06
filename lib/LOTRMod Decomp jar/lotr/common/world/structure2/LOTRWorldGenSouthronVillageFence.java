// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.world.World;
import lotr.common.LOTRMod;
import java.util.Random;

public class LOTRWorldGenSouthronVillageFence extends LOTRWorldGenSouthronStructure
{
    private int leftExtent;
    private int rightExtent;
    
    public LOTRWorldGenSouthronVillageFence(final boolean flag) {
        super(flag);
    }
    
    public LOTRWorldGenSouthronVillageFence setLeftRightExtent(final int l, final int r) {
        this.leftExtent = l;
        this.rightExtent = r;
        return this;
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        super.setupRandomBlocks(random);
        super.fenceBlock = LOTRMod.fence2;
        super.fenceMeta = 2;
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        for (int i2 = -this.leftExtent; i2 <= this.rightExtent; ++i2) {
            final int k2 = 0;
            final int j2 = this.getTopBlock(world, i2, k2) - 1;
            if (this.isSurface(world, i2, j2, k2) && !this.isOpaque(world, i2, j2 + 1, k2)) {
                this.setBlockAndMetadata(world, i2, j2 + 1, k2, super.fenceBlock, super.fenceMeta);
            }
        }
        return true;
    }
}
