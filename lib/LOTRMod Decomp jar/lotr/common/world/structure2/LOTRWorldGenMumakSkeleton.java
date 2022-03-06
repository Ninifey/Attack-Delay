// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.world.World;
import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.block.Block;

public class LOTRWorldGenMumakSkeleton extends LOTRWorldGenStructureBase2
{
    protected Block boneBlock;
    protected int boneMeta;
    
    public LOTRWorldGenMumakSkeleton(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        this.boneBlock = LOTRMod.boneBlock;
        this.boneMeta = 0;
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            for (int i2 = -3; i2 <= 3; ++i2) {
                for (int k2 = -3; k2 <= 17; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2) - 1;
                    if (j2 < -2) {
                        return false;
                    }
                }
            }
        }
        if (super.usingPlayer == null) {
            super.originY -= random.nextInt(6);
        }
        this.loadStrScan("mumak_skeleton");
        this.associateBlockMetaAlias("BONE", this.boneBlock, this.boneMeta);
        this.generateStrScan(world, random, 0, 1, 0);
        return true;
    }
}
