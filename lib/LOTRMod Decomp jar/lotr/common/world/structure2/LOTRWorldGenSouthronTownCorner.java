// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenSouthronTownCorner extends LOTRWorldGenSouthronStructure
{
    public LOTRWorldGenSouthronTownCorner(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected boolean canUseRedBricks() {
        return false;
    }
    
    @Override
    protected boolean forceCedarWood() {
        return true;
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 4);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            for (int i2 = -3; i2 <= 3; ++i2) {
                for (int k2 = -3; k2 <= 3; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2) - 1;
                    if (!this.isSurface(world, i2, j2, k2)) {
                        return false;
                    }
                }
            }
        }
        for (int i2 = -3; i2 <= 3; ++i2) {
            for (int k2 = -3; k2 <= 3; ++k2) {
                for (int j2 = 1; j2 <= 12; ++j2) {
                    this.setAir(world, i2, j2, k2);
                }
            }
        }
        this.loadStrScan("southron_town_corner");
        this.associateBlockMetaAlias("STONE", super.stoneBlock, super.stoneMeta);
        this.associateBlockMetaAlias("BRICK", super.brickBlock, super.brickMeta);
        this.associateBlockMetaAlias("BRICK_SLAB", super.brickSlabBlock, super.brickSlabMeta);
        this.associateBlockAlias("BRICK_STAIR", super.brickStairBlock);
        this.associateBlockMetaAlias("BRICK2", super.brick2Block, super.brick2Meta);
        this.associateBlockMetaAlias("BRICK2_SLAB", super.brick2SlabBlock, super.brick2SlabMeta);
        this.associateBlockMetaAlias("BRICK2_SLAB_INV", super.brick2SlabBlock, super.brick2SlabMeta | 0x8);
        this.associateBlockMetaAlias("FENCE", super.fenceBlock, super.fenceMeta);
        this.associateBlockAlias("GATE_METAL", super.gateMetalBlock);
        this.generateStrScan(world, random, 0, 0, 0);
        return true;
    }
}
