// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.animal.LOTREntityDesertScorpion;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.entity.npc.LOTREntityHaradPyramidWraith;
import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenHaradPyramid extends LOTRWorldGenStructureBase2
{
    public LOTRWorldGenHaradPyramid(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        final int pyramidRadius = 27;
        this.setOriginAndRotation(world, i, j, k, rotation, (super.usingPlayer != null) ? pyramidRadius : 0);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            for (int i2 = -pyramidRadius; i2 <= pyramidRadius; ++i2) {
                for (int k2 = -pyramidRadius; k2 <= pyramidRadius; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2) - 1;
                    final Block block = this.getBlock(world, i2, j2, k2);
                    if (!this.isSurface(world, i2, j2, k2) && block != Blocks.stone && block != Blocks.sandstone && block != LOTRMod.redSandstone) {
                        return false;
                    }
                }
            }
        }
        super.originY += MathHelper.getRandomIntegerInRange(random, -2, 4);
        this.loadStrScan("harad_pyramid");
        this.addBlockMetaAliasOption("BRICK", 3, LOTRMod.brick, 15);
        this.addBlockMetaAliasOption("BRICK", 1, LOTRMod.brick3, 11);
        this.addBlockMetaAliasOption("BRICK_MAYBE", 4, Blocks.air, 0);
        this.addBlockMetaAliasOption("BRICK_MAYBE", 3, LOTRMod.brick, 15);
        this.addBlockMetaAliasOption("BRICK_MAYBE", 1, LOTRMod.brick3, 11);
        this.addBlockMetaAliasOption("BRICK_SLAB", 3, LOTRMod.slabSingle4, 0);
        this.addBlockMetaAliasOption("BRICK_SLAB", 1, LOTRMod.slabSingle7, 1);
        this.addBlockAliasOption("BRICK_STAIR", 3, LOTRMod.stairsNearHaradBrick);
        this.addBlockAliasOption("BRICK_STAIR", 1, LOTRMod.stairsNearHaradBrickCracked);
        this.addBlockMetaAliasOption("BRICK_WALL", 3, LOTRMod.wall, 15);
        this.addBlockMetaAliasOption("BRICK_WALL", 1, LOTRMod.wall3, 3);
        this.addBlockMetaAliasOption("PILLAR", 4, LOTRMod.pillar, 5);
        this.addBlockMetaAliasOption("PILLAR_SLAB", 4, LOTRMod.slabSingle4, 7);
        this.addBlockMetaAliasOption("BRICK2", 3, LOTRMod.brick3, 13);
        this.addBlockMetaAliasOption("BRICK2", 1, LOTRMod.brick3, 14);
        this.addBlockMetaAliasOption("BRICK2_SLAB", 3, LOTRMod.slabSingle7, 2);
        this.addBlockMetaAliasOption("BRICK2_SLAB", 1, LOTRMod.slabSingle7, 3);
        this.addBlockAliasOption("BRICK2_STAIR", 3, LOTRMod.stairsNearHaradBrickRed);
        this.addBlockAliasOption("BRICK2_STAIR", 1, LOTRMod.stairsNearHaradBrickRedCracked);
        this.addBlockMetaAliasOption("TUNNEL", 5, (Block)Blocks.sand, 0);
        this.addBlockMetaAliasOption("TUNNEL", 5, Blocks.air, 0);
        this.addBlockMetaAliasOption("ROOF", 4, (Block)Blocks.sand, 1);
        this.addBlockMetaAliasOption("ROOF", 4, LOTRMod.redSandstone, 0);
        this.addBlockMetaAliasOption("ROOF", 2, LOTRMod.brick3, 13);
        this.addBlockMetaAliasOption("ROOF", 2, LOTRMod.brick3, 14);
        this.generateStrScan(world, random, 0, 0, 0);
        this.placePyramidChest(world, random, -4, -6, 3, 2);
        this.placePyramidChest(world, random, 0, -6, 3, 2);
        this.placePyramidChest(world, random, 4, -6, 3, 2);
        this.placePyramidChest(world, random, -5, -5, -7, 4);
        this.placePyramidChest(world, random, -3, -5, -7, 5);
        this.placePyramidChest(world, random, 3, -5, -7, 4);
        this.placePyramidChest(world, random, 5, -5, -7, 5);
        this.placePyramidChest(world, random, -4, -5, -5, 2);
        this.placePyramidChest(world, random, 4, -5, -5, 2);
        this.placeSpawnerChest(world, random, 0, -6, 15, LOTRMod.spawnerChestAncientHarad, 2, LOTREntityHaradPyramidWraith.class, LOTRChestContents.NEAR_HARAD_PYRAMID, 12);
        this.placeMobSpawner(world, 0, -2, 15, LOTREntityDesertScorpion.class);
        this.placeMobSpawner(world, -12, -2, -12, LOTREntityDesertScorpion.class);
        this.placeMobSpawner(world, 12, -2, -12, LOTREntityDesertScorpion.class);
        this.placeMobSpawner(world, 0, 8, 0, LOTREntityDesertScorpion.class);
        this.placePyramidChest(world, random, -12, -1, -12, 2, true);
        this.placePyramidChest(world, random, 12, -1, -12, 2, true);
        this.placePyramidChest(world, random, 0, 9, 0, 2, true);
        return true;
    }
    
    private void placePyramidChest(final World world, final Random random, final int i, final int j, final int k, final int meta) {
        this.placePyramidChest(world, random, i, j, k, meta, random.nextBoolean());
    }
    
    private void placePyramidChest(final World world, final Random random, final int i, final int j, final int k, final int meta, final boolean trap) {
        final int amount = MathHelper.getRandomIntegerInRange(random, 3, 5);
        if (trap) {
            this.placeSpawnerChest(world, random, i, j, k, LOTRMod.spawnerChestStone, meta, LOTREntityHaradPyramidWraith.class, LOTRChestContents.NEAR_HARAD_PYRAMID, amount);
        }
        else {
            this.placeChest(world, random, i, j, k, LOTRMod.chestStone, meta, LOTRChestContents.NEAR_HARAD_PYRAMID, amount);
        }
    }
}
