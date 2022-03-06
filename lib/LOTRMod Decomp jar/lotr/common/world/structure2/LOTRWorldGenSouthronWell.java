// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenSouthronWell extends LOTRWorldGenSouthronStructure
{
    public LOTRWorldGenSouthronWell(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 2);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            for (int i2 = -2; i2 <= 2; ++i2) {
                for (int k2 = -2; k2 <= 2; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2) - 1;
                    if (!this.isSurface(world, i2, j2, k2)) {
                        return false;
                    }
                }
            }
        }
        for (int i2 = -2; i2 <= 2; ++i2) {
            for (int k2 = -2; k2 <= 2; ++k2) {
                for (int j2 = 1; j2 <= 5; ++j2) {
                    this.setAir(world, i2, j2, k2);
                }
            }
        }
        this.loadStrScan("southron_well");
        this.associateBlockMetaAlias("STONE", super.stoneBlock, super.stoneMeta);
        this.associateBlockMetaAlias("BRICK", super.brickBlock, super.brickMeta);
        this.associateBlockMetaAlias("BRICK_SLAB", super.brickSlabBlock, super.brickSlabMeta);
        this.associateBlockMetaAlias("BRICK_SLAB_INV", super.brickSlabBlock, super.brickSlabMeta | 0x8);
        this.associateBlockAlias("BRICK_STAIR", super.brickStairBlock);
        this.associateBlockMetaAlias("BRICK_WALL", super.brickWallBlock, super.brickWallMeta);
        this.associateBlockMetaAlias("BRICK2", super.brick2Block, super.brick2Meta);
        this.associateBlockMetaAlias("BRICK2_SLAB", super.brick2SlabBlock, super.brick2SlabMeta);
        this.associateBlockMetaAlias("BRICK2_SLAB_INV", super.brick2SlabBlock, super.brick2SlabMeta | 0x8);
        this.associateBlockMetaAlias("FENCE", super.fenceBlock, super.fenceMeta);
        this.generateStrScan(world, random, 0, 0, 0);
        return true;
    }
}
