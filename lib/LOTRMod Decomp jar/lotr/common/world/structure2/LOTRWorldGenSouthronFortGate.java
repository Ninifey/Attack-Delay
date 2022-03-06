// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenSouthronFortGate extends LOTRWorldGenSouthronStructure
{
    public LOTRWorldGenSouthronFortGate(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected boolean canUseRedBricks() {
        return false;
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 1);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            for (int i2 = -5; i2 <= 5; ++i2) {
                for (int k2 = -2; k2 <= 2; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2) - 1;
                    if (!this.isSurface(world, i2, j2, k2)) {
                        return false;
                    }
                }
            }
        }
        for (int i2 = -5; i2 <= 5; ++i2) {
            for (int k2 = 1; k2 <= 1; ++k2) {
                for (int j2 = 1; j2 <= 8; ++j2) {
                    this.setAir(world, i2, j2, k2);
                }
            }
        }
        this.loadStrScan("southron_fortress_gate");
        this.associateBlockMetaAlias("BRICK", super.brickBlock, super.brickMeta);
        this.associateBlockAlias("BRICK_STAIR", super.brickStairBlock);
        this.associateBlockMetaAlias("PLANK", super.plankBlock, super.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", super.plankSlabBlock, super.plankSlabMeta);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.associateBlockAlias("PLANK_STAIR", super.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", super.fenceBlock, super.fenceMeta);
        this.associateBlockMetaAlias("BEAM|8", super.woodBeamBlock, super.woodBeamMeta8);
        this.associateBlockAlias("GATE_METAL", super.gateMetalBlock);
        this.generateStrScan(world, random, 0, 0, 0);
        this.placeWallBanner(world, -3, 5, -1, super.bannerType, 2);
        this.placeWallBanner(world, 3, 5, -1, super.bannerType, 2);
        return true;
    }
}
