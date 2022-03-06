// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.world.World;
import lotr.common.LOTRMod;
import java.util.Random;

public class LOTRWorldGenSouthronVillagePost extends LOTRWorldGenSouthronStructure
{
    public LOTRWorldGenSouthronVillagePost(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        super.setupRandomBlocks(random);
        super.plankSlabBlock = LOTRMod.woodSlabSingle3;
        super.plankSlabMeta = 2;
        super.woodBeamBlock = LOTRMod.woodBeam4;
        super.woodBeamMeta = 2;
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
            this.setBlockAndMetadata(world, 0, j3, 0, super.woodBeamBlock, super.woodBeamMeta);
            this.setGrassToDirt(world, 0, j3 - 1, 0);
        }
        this.setBlockAndMetadata(world, 0, 1, 0, super.woodBeamBlock, super.woodBeamMeta);
        this.setBlockAndMetadata(world, 0, 2, 0, super.woodBeamBlock, super.woodBeamMeta);
        this.setBlockAndMetadata(world, 0, 3, 0, super.plankSlabBlock, super.plankSlabMeta);
        return true;
    }
}
