// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import lotr.common.entity.npc.LOTRNames;
import java.util.Random;

public class LOTRWorldGenSouthronTownGate extends LOTRWorldGenSouthronStructure
{
    private String[] signText;
    
    public LOTRWorldGenSouthronTownGate(final boolean flag) {
        super(flag);
        this.signText = LOTRNames.getHaradVillageName(new Random());
    }
    
    public LOTRWorldGenSouthronTownGate setSignText(final String[] s) {
        this.signText = s;
        return this;
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
            for (int i2 = -8; i2 <= 8; ++i2) {
                for (int k2 = -3; k2 <= 3; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2) - 1;
                    if (!this.isSurface(world, i2, j2, k2)) {
                        return false;
                    }
                }
            }
        }
        for (int i2 = -8; i2 <= 8; ++i2) {
            for (int k2 = -3; k2 <= 3; ++k2) {
                for (int j2 = 1; j2 <= 12; ++j2) {
                    this.setAir(world, i2, j2, k2);
                }
            }
        }
        this.loadStrScan("southron_town_gate");
        this.associateBlockMetaAlias("STONE", super.stoneBlock, super.stoneMeta);
        this.associateBlockAlias("STONE_STAIR", super.stoneStairBlock);
        this.associateBlockMetaAlias("BRICK", super.brickBlock, super.brickMeta);
        this.associateBlockMetaAlias("BRICK_SLAB", super.brickSlabBlock, super.brickSlabMeta);
        this.associateBlockMetaAlias("BRICK_SLAB_INV", super.brickSlabBlock, super.brickSlabMeta | 0x8);
        this.associateBlockAlias("BRICK_STAIR", super.brickStairBlock);
        this.associateBlockMetaAlias("BRICK_WALL", super.brickWallBlock, super.brickWallMeta);
        this.associateBlockMetaAlias("PILLAR", super.pillarBlock, super.pillarMeta);
        this.associateBlockMetaAlias("BRICK2", super.brick2Block, super.brick2Meta);
        this.associateBlockMetaAlias("BRICK2_SLAB", super.brick2SlabBlock, super.brick2SlabMeta);
        this.associateBlockMetaAlias("BRICK2_SLAB_INV", super.brick2SlabBlock, super.brick2SlabMeta | 0x8);
        this.associateBlockAlias("PLANK_STAIR", super.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", super.fenceBlock, super.fenceMeta);
        this.associateBlockMetaAlias("BEAM", super.woodBeamBlock, super.woodBeamMeta);
        this.associateBlockMetaAlias("BEAM|4", super.woodBeamBlock, super.woodBeamMeta4);
        this.associateBlockAlias("GATE_METAL", super.gateMetalBlock);
        this.generateStrScan(world, random, 0, 0, 0);
        if (this.signText != null) {
            this.placeSign(world, -3, 3, -4, Blocks.wall_sign, 2, this.signText);
            this.placeSign(world, 3, 3, -4, Blocks.wall_sign, 2, this.signText);
        }
        this.placeWallBanner(world, -6, 4, -2, super.bannerType, 2);
        this.placeWallBanner(world, 6, 4, -2, super.bannerType, 2);
        final int maxSteps = 12;
        for (int step = 0; step < 12; ++step) {
            final int i3 = -7 - step;
            final int j3 = 5 - step;
            final int k3 = 2;
            if (this.isOpaque(world, i3, j3, k3)) {
                break;
            }
            if (j3 <= 1) {
                this.setBlockAndMetadata(world, i3, j3, k3, super.stoneStairBlock, 1);
            }
            else {
                this.setBlockAndMetadata(world, i3, j3, k3, super.brickStairBlock, 1);
            }
            this.setGrassToDirt(world, i3, j3 - 1, k3);
            for (int j4 = j3 - 1; !this.isOpaque(world, i3, j4, k3) && this.getY(j4) >= 0; --j4) {
                if (j4 <= 1) {
                    this.setBlockAndMetadata(world, i3, j4, k3, super.stoneBlock, super.stoneMeta);
                }
                else {
                    this.setBlockAndMetadata(world, i3, j4, k3, super.brickBlock, super.brickMeta);
                }
                this.setGrassToDirt(world, i3, j4 - 1, k3);
            }
        }
        for (int step = 0; step < 12; ++step) {
            final int i3 = 7 + step;
            final int j3 = 5 - step;
            final int k3 = 2;
            if (this.isOpaque(world, i3, j3, k3)) {
                break;
            }
            if (j3 <= 1) {
                this.setBlockAndMetadata(world, i3, j3, k3, super.stoneStairBlock, 0);
            }
            else {
                this.setBlockAndMetadata(world, i3, j3, k3, super.brickStairBlock, 0);
            }
            this.setGrassToDirt(world, i3, j3 - 1, k3);
            for (int j4 = j3 - 1; !this.isOpaque(world, i3, j4, k3) && this.getY(j4) >= 0; --j4) {
                if (j4 <= 1) {
                    this.setBlockAndMetadata(world, i3, j4, k3, super.stoneBlock, super.stoneMeta);
                }
                else {
                    this.setBlockAndMetadata(world, i3, j4, k3, super.brickBlock, super.brickMeta);
                }
                this.setGrassToDirt(world, i3, j4 - 1, k3);
            }
        }
        return true;
    }
}
