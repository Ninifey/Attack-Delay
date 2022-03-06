// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.util.MathHelper;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenGulfPyramid extends LOTRWorldGenGulfStructure
{
    public LOTRWorldGenGulfPyramid(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 11);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            for (int i2 = -11; i2 <= 11; ++i2) {
                for (int k2 = -11; k2 <= 11; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2) - 1;
                    if (!this.isSurface(world, i2, j2, k2)) {
                        return false;
                    }
                }
            }
        }
        for (int i2 = -10; i2 <= 10; ++i2) {
            for (int k2 = -10; k2 <= 10; ++k2) {
                for (int j2 = 1; j2 <= 20; ++j2) {
                    this.setAir(world, i2, j2, k2);
                }
            }
        }
        this.loadStrScan("gulf_pyramid");
        this.associateBlockMetaAlias("STONE", Blocks.sandstone, 0);
        this.associateBlockAlias("STONE_STAIR", Blocks.sandstone_stairs);
        this.associateBlockMetaAlias("STONE2", LOTRMod.redSandstone, 0);
        this.associateBlockAlias("STONE2_STAIR", LOTRMod.stairsRedSandstone);
        this.addBlockMetaAliasOption("BRICK", 8, LOTRMod.brick, 15);
        this.addBlockMetaAliasOption("BRICK", 2, LOTRMod.brick3, 11);
        this.addBlockAliasOption("BRICK_STAIR", 8, LOTRMod.stairsNearHaradBrick);
        this.addBlockAliasOption("BRICK_STAIR", 2, LOTRMod.stairsNearHaradBrickCracked);
        this.addBlockMetaAliasOption("BRICK_WALL", 8, LOTRMod.wall, 15);
        this.addBlockMetaAliasOption("BRICK_WALL", 2, LOTRMod.wall3, 3);
        this.addBlockMetaAliasOption("PILLAR", 10, LOTRMod.pillar, 5);
        this.addBlockMetaAliasOption("BRICK2", 8, LOTRMod.brick3, 13);
        this.addBlockMetaAliasOption("BRICK2", 2, LOTRMod.brick3, 14);
        this.associateBlockMetaAlias("BRICK2_CARVED", LOTRMod.brick3, 15);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.associateBlockMetaAlias("FENCE", super.fenceBlock, super.fenceMeta);
        this.associateBlockMetaAlias("ROOF_SLAB", super.roofSlabBlock, super.roofSlabMeta);
        this.associateBlockMetaAlias("ROOF_SLAB_INV", super.roofSlabBlock, super.roofSlabMeta | 0x8);
        this.associateBlockAlias("ROOF_STAIR", super.roofStairBlock);
        this.generateStrScan(world, random, 0, 1, 0);
        for (int i2 = -5; i2 <= 5; ++i2) {
            for (int k2 = -5; k2 <= 5; ++k2) {
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                final int j3 = 11;
                if ((i3 > 2 || k3 > 2) && this.isOpaque(world, i2, j3 - 1, k2) && this.isAir(world, i2, j3, k2) && random.nextInt(12) == 0) {
                    this.placeChest(world, random, i2, j3, k2, LOTRMod.chestBasket, MathHelper.getRandomIntegerInRange(random, 2, 5), LOTRChestContents.GULF_PYRAMID);
                }
            }
        }
        final int maxStep = 4;
        for (final int k4 : new int[] { -11, 11 }) {
            for (int step = 0; step < maxStep; ++step) {
                final int i4 = -7 - step;
                final int j4 = 0 - step;
                if (this.isOpaque(world, i4, j4, k4)) {
                    break;
                }
                this.setBlockAndMetadata(world, i4, j4, k4, Blocks.sandstone_stairs, 1);
                this.setGrassToDirt(world, i4, j4 - 1, k4);
                for (int j5 = j4 - 1; !this.isOpaque(world, i4, j5, k4) && this.getY(j5) >= 0; --j5) {
                    this.setBlockAndMetadata(world, i4, j5, k4, Blocks.sandstone, 0);
                    this.setGrassToDirt(world, i4, j5 - 1, k4);
                }
            }
            for (int step = 0; step < maxStep; ++step) {
                final int i4 = 7 + step;
                final int j4 = 0 - step;
                if (this.isOpaque(world, i4, j4, k4)) {
                    break;
                }
                this.setBlockAndMetadata(world, i4, j4, k4, Blocks.sandstone_stairs, 0);
                this.setGrassToDirt(world, i4, j4 - 1, k4);
                for (int j5 = j4 - 1; !this.isOpaque(world, i4, j5, k4) && this.getY(j5) >= 0; --j5) {
                    this.setBlockAndMetadata(world, i4, j5, k4, Blocks.sandstone, 0);
                    this.setGrassToDirt(world, i4, j5 - 1, k4);
                }
            }
        }
        for (final int i5 : new int[] { -11, 11 }) {
            for (int step = 0; step < maxStep; ++step) {
                final int k5 = -7 - step;
                final int j4 = 0 - step;
                if (this.isOpaque(world, i5, j4, k5)) {
                    break;
                }
                this.setBlockAndMetadata(world, i5, j4, k5, Blocks.sandstone_stairs, 2);
                this.setGrassToDirt(world, i5, j4 - 1, k5);
                for (int j5 = j4 - 1; !this.isOpaque(world, i5, j5, k5) && this.getY(j5) >= 0; --j5) {
                    this.setBlockAndMetadata(world, i5, j5, k5, Blocks.sandstone, 0);
                    this.setGrassToDirt(world, i5, j5 - 1, k5);
                }
            }
            for (int step = 0; step < maxStep; ++step) {
                final int k5 = 7 + step;
                final int j4 = 0 - step;
                if (this.isOpaque(world, i5, j4, k5)) {
                    break;
                }
                this.setBlockAndMetadata(world, i5, j4, k5, Blocks.sandstone_stairs, 3);
                this.setGrassToDirt(world, i5, j4 - 1, k5);
                for (int j5 = j4 - 1; !this.isOpaque(world, i5, j5, k5) && this.getY(j5) >= 0; --j5) {
                    this.setBlockAndMetadata(world, i5, j5, k5, Blocks.sandstone, 0);
                    this.setGrassToDirt(world, i5, j5 - 1, k5);
                }
            }
        }
        return true;
    }
}
