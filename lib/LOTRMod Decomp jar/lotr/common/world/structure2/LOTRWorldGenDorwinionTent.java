// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityDorwinionGuard;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.world.World;
import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.block.Block;

public class LOTRWorldGenDorwinionTent extends LOTRWorldGenStructureBase2
{
    protected Block woodBeamBlock;
    protected int woodBeamMeta;
    protected Block plankBlock;
    protected int plankMeta;
    protected Block plankSlabBlock;
    protected int plankSlabMeta;
    protected Block plankStairBlock;
    protected Block fenceBlock;
    protected int fenceMeta;
    protected Block floorBlock;
    protected int floorMeta;
    protected Block wool1Block;
    protected int wool1Meta;
    protected Block clay1Block;
    protected int clay1Meta;
    protected Block clay1SlabBlock;
    protected int clay1SlabMeta;
    protected Block clay1StairBlock;
    protected Block wool2Block;
    protected int wool2Meta;
    protected Block clay2Block;
    protected int clay2Meta;
    protected Block clay2SlabBlock;
    protected int clay2SlabMeta;
    protected Block clay2StairBlock;
    
    public LOTRWorldGenDorwinionTent(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        final int randomWood = random.nextInt(3);
        if (randomWood == 0) {
            this.woodBeamBlock = LOTRMod.woodBeamV1;
            this.woodBeamMeta = 0;
            this.plankBlock = Blocks.planks;
            this.plankMeta = 0;
            this.plankSlabBlock = (Block)Blocks.wooden_slab;
            this.plankSlabMeta = 0;
            this.plankStairBlock = Blocks.oak_stairs;
            this.fenceBlock = Blocks.fence;
            this.fenceMeta = 0;
        }
        else if (randomWood == 1) {
            this.woodBeamBlock = LOTRMod.woodBeam6;
            this.woodBeamMeta = 2;
            this.plankBlock = LOTRMod.planks2;
            this.plankMeta = 10;
            this.plankSlabBlock = LOTRMod.woodSlabSingle4;
            this.plankSlabMeta = 2;
            this.plankStairBlock = LOTRMod.stairsCypress;
            this.fenceBlock = LOTRMod.fence2;
            this.fenceMeta = 10;
        }
        else if (randomWood == 2) {
            this.woodBeamBlock = LOTRMod.woodBeam6;
            this.woodBeamMeta = 3;
            this.plankBlock = LOTRMod.planks2;
            this.plankMeta = 11;
            this.plankSlabBlock = LOTRMod.woodSlabSingle4;
            this.plankSlabMeta = 3;
            this.plankStairBlock = LOTRMod.stairsOlive;
            this.fenceBlock = LOTRMod.fence2;
            this.fenceMeta = 11;
        }
        final int randomFloor = random.nextInt(4);
        if (randomFloor == 0) {
            this.floorBlock = Blocks.stained_hardened_clay;
            this.floorMeta = 2;
        }
        else if (randomFloor == 1) {
            this.floorBlock = Blocks.stained_hardened_clay;
            this.floorMeta = 3;
        }
        else if (randomFloor == 2) {
            this.floorBlock = Blocks.stained_hardened_clay;
            this.floorMeta = 14;
        }
        else if (randomFloor == 3) {
            this.floorBlock = Blocks.stained_hardened_clay;
            this.floorMeta = 10;
        }
        final int randomWool1 = random.nextInt(3);
        if (randomWool1 == 0) {
            this.wool1Block = Blocks.wool;
            this.wool1Meta = 10;
            this.clay1Block = LOTRMod.clayTileDyed;
            this.clay1Meta = 10;
            this.clay1SlabBlock = LOTRMod.slabClayTileDyedSingle2;
            this.clay1SlabMeta = 2;
            this.clay1StairBlock = LOTRMod.stairsClayTileDyedPurple;
        }
        else if (randomWool1 == 1) {
            this.wool1Block = Blocks.wool;
            this.wool1Meta = 2;
            this.clay1Block = LOTRMod.clayTileDyed;
            this.clay1Meta = 2;
            this.clay1SlabBlock = LOTRMod.slabClayTileDyedSingle;
            this.clay1SlabMeta = 2;
            this.clay1StairBlock = LOTRMod.stairsClayTileDyedMagenta;
        }
        else if (randomWool1 == 2) {
            this.wool1Block = Blocks.wool;
            this.wool1Meta = 14;
            this.clay1Block = LOTRMod.clayTileDyed;
            this.clay1Meta = 14;
            this.clay1SlabBlock = LOTRMod.slabClayTileDyedSingle2;
            this.clay1SlabMeta = 6;
            this.clay1StairBlock = LOTRMod.stairsClayTileDyedRed;
        }
        final int randomWool2 = random.nextInt(2);
        if (randomWool2 == 0) {
            this.wool2Block = Blocks.wool;
            this.wool2Meta = 4;
            this.clay2Block = LOTRMod.clayTileDyed;
            this.clay2Meta = 4;
            this.clay2SlabBlock = LOTRMod.slabClayTileDyedSingle;
            this.clay2SlabMeta = 4;
            this.clay2StairBlock = LOTRMod.stairsClayTileDyedYellow;
        }
        else if (randomWool2 == 1) {
            this.wool2Block = Blocks.wool;
            this.wool2Meta = 0;
            this.clay2Block = LOTRMod.clayTileDyed;
            this.clay2Meta = 0;
            this.clay2SlabBlock = LOTRMod.slabClayTileDyedSingle;
            this.clay2SlabMeta = 0;
            this.clay2StairBlock = LOTRMod.stairsClayTileDyedWhite;
        }
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 3);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            for (int i2 = -2; i2 <= 2; ++i2) {
                for (int k2 = -3; k2 <= 3; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2) - 1;
                    if (!this.isSurface(world, i2, j2, k2)) {
                        return false;
                    }
                }
            }
        }
        for (int i2 = -2; i2 <= 2; ++i2) {
            for (int k2 = -2; k2 <= 2; ++k2) {
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                for (int j3 = 0; (j3 == 0 || !this.isOpaque(world, i2, j3, k2)) && this.getY(j3) >= 0; --j3) {
                    this.setBlockAndMetadata(world, i2, j3, k2, this.floorBlock, this.floorMeta);
                    this.setGrassToDirt(world, i2, j3 - 1, k2);
                }
                for (int j3 = 1; j3 <= 4; ++j3) {
                    this.setAir(world, i2, j3, k2);
                }
                if (i3 == 2 && k3 == 2) {
                    for (int j3 = 1; j3 <= 2; ++j3) {
                        this.setBlockAndMetadata(world, i2, j3, k2, this.woodBeamBlock, this.woodBeamMeta);
                    }
                }
                else if (i3 == 2) {
                    for (int j3 = 1; j3 <= 2; ++j3) {
                        if (k2 % 2 == 0) {
                            this.setBlockAndMetadata(world, i2, j3, k2, this.wool1Block, this.wool1Meta);
                        }
                        else {
                            this.setBlockAndMetadata(world, i2, j3, k2, this.wool2Block, this.wool2Meta);
                        }
                    }
                }
                if (i3 == 0 && k3 == 2) {
                    for (int j3 = 1; j3 <= 3; ++j3) {
                        this.setBlockAndMetadata(world, i2, j3, k2, this.fenceBlock, this.fenceMeta);
                    }
                }
            }
        }
        for (int k4 = -2; k4 <= 2; ++k4) {
            if (k4 % 2 == 0) {
                this.setBlockAndMetadata(world, -2, 3, k4, this.clay1StairBlock, 1);
                this.setBlockAndMetadata(world, 2, 3, k4, this.clay1StairBlock, 0);
                this.setBlockAndMetadata(world, -1, 3, k4, this.clay1StairBlock, 4);
                this.setBlockAndMetadata(world, 1, 3, k4, this.clay1StairBlock, 5);
                this.setBlockAndMetadata(world, -1, 4, k4, this.clay1StairBlock, 1);
                this.setBlockAndMetadata(world, 1, 4, k4, this.clay1StairBlock, 0);
                this.setBlockAndMetadata(world, 0, 4, k4, this.clay1Block, this.clay1Meta);
                this.setBlockAndMetadata(world, 0, 5, k4, this.clay1SlabBlock, this.clay1SlabMeta);
            }
            else {
                this.setBlockAndMetadata(world, -2, 3, k4, this.clay2StairBlock, 1);
                this.setBlockAndMetadata(world, 2, 3, k4, this.clay2StairBlock, 0);
                this.setBlockAndMetadata(world, -1, 3, k4, this.clay2StairBlock, 4);
                this.setBlockAndMetadata(world, 1, 3, k4, this.clay2StairBlock, 5);
                this.setBlockAndMetadata(world, -1, 4, k4, this.clay2StairBlock, 1);
                this.setBlockAndMetadata(world, 1, 4, k4, this.clay2StairBlock, 0);
                this.setBlockAndMetadata(world, 0, 4, k4, this.clay2Block, this.clay2Meta);
                this.setBlockAndMetadata(world, 0, 5, k4, this.clay2SlabBlock, this.clay2SlabMeta);
            }
        }
        if (random.nextBoolean()) {
            this.placeChest(world, random, -1, 1, 0, 4, LOTRChestContents.DORWINION_CAMP);
            this.setBlockAndMetadata(world, 1, 2, 0, Blocks.torch, 1);
        }
        else {
            this.placeChest(world, random, 1, 1, 0, 4, LOTRChestContents.DORWINION_CAMP);
            this.setBlockAndMetadata(world, -1, 2, 0, Blocks.torch, 2);
        }
        final LOTREntityDorwinionGuard guard = new LOTREntityDorwinionGuard(world);
        this.spawnNPCAndSetHome(guard, world, 0, 1, 0, 16);
        return true;
    }
}
