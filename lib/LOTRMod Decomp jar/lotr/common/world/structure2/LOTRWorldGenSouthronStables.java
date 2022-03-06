// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityNearHaradrimBase;
import lotr.common.entity.animal.LOTREntityHorse;
import net.minecraft.entity.EntityCreature;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.LOTRMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenSouthronStables extends LOTRWorldGenSouthronStructure
{
    public LOTRWorldGenSouthronStables(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 8);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -4; i2 <= 8; ++i2) {
                for (int k2 = -7; k2 <= 7; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2) - 1;
                    if (!this.isSurface(world, i2, j2, k2)) {
                        return false;
                    }
                    if (j2 < minHeight) {
                        minHeight = j2;
                    }
                    if (j2 > maxHeight) {
                        maxHeight = j2;
                    }
                    if (maxHeight - minHeight > 8) {
                        return false;
                    }
                }
            }
        }
        for (int i3 = -4; i3 <= 8; ++i3) {
            for (int k3 = -7; k3 <= 7; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                if ((i4 <= 4 && k4 <= 7) || (i3 >= 5 && i3 <= 8 && k4 <= 6)) {
                    for (int j2 = 0; (j2 >= 0 || !this.isOpaque(world, i3, j2, k3)) && this.getY(j2) >= 0; --j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, super.stoneBlock, super.stoneMeta);
                        this.setGrassToDirt(world, i3, j2 - 1, k3);
                    }
                    for (int j2 = 1; j2 <= 8; ++j2) {
                        this.setAir(world, i3, j2, k3);
                    }
                }
                if ((i4 <= 3 && k4 <= 6) || (i3 >= 4 && i3 <= 7 && k4 <= 5)) {
                    final int randomGround = random.nextInt(2);
                    if (random.nextBoolean()) {
                        this.setBlockAndMetadata(world, i3, 0, k3, (Block)Blocks.sand, 0);
                    }
                    else {
                        this.setBlockAndMetadata(world, i3, 0, k3, LOTRMod.dirtPath, 0);
                    }
                    if (random.nextInt(4) == 0) {
                        this.setBlockAndMetadata(world, i3, 1, k3, LOTRMod.thatchFloor, 0);
                    }
                }
            }
        }
        this.loadStrScan("southron_stable");
        this.associateBlockMetaAlias("STONE", super.stoneBlock, super.stoneMeta);
        this.associateBlockMetaAlias("BRICK", super.brickBlock, super.brickMeta);
        this.associateBlockMetaAlias("BRICK_SLAB", super.brickSlabBlock, super.brickSlabMeta);
        this.associateBlockMetaAlias("BRICK_SLAB_INV", super.brickSlabBlock, super.brickSlabMeta | 0x8);
        this.associateBlockAlias("BRICK_STAIR", super.brickStairBlock);
        this.associateBlockMetaAlias("PILLAR", super.pillarBlock, super.pillarMeta);
        this.associateBlockMetaAlias("PLANK", super.plankBlock, super.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", super.plankSlabBlock, super.plankSlabMeta);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.associateBlockAlias("PLANK_STAIR", super.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", super.fenceBlock, super.fenceMeta);
        this.associateBlockAlias("FENCE_GATE", super.fenceGateBlock);
        this.associateBlockMetaAlias("BEAM", super.woodBeamBlock, super.woodBeamMeta);
        this.associateBlockMetaAlias("BEAM|4", super.woodBeamBlock, super.woodBeamMeta4);
        this.associateBlockMetaAlias("BEAM|8", super.woodBeamBlock, super.woodBeamMeta8);
        this.associateBlockMetaAlias("ROOF", super.roofBlock, super.roofMeta);
        this.associateBlockMetaAlias("ROOF_SLAB", super.roofSlabBlock, super.roofSlabMeta);
        this.associateBlockMetaAlias("ROOF_SLAB_INV", super.roofSlabBlock, super.roofSlabMeta | 0x8);
        this.associateBlockAlias("ROOF_STAIR", super.roofStairBlock);
        this.generateStrScan(world, random, 0, 1, 0);
        this.placeChest(world, random, -3, 1, 6, LOTRMod.chestBasket, 2, LOTRChestContents.NEAR_HARAD_HOUSE);
        for (int numHaradrim = 1 + random.nextInt(2), l = 0; l < numHaradrim; ++l) {
            final LOTREntityNearHaradrimBase haradrim = this.createHaradrim(world);
            this.spawnNPCAndSetHome(haradrim, world, 0, 1, 0, 8);
        }
        for (final int k5 : new int[] { -4, 0, 4 }) {
            final int i5 = 5;
            final int j3 = 1;
            final LOTREntityHorse horse = new LOTREntityHorse(world);
            this.spawnNPCAndSetHome((EntityCreature)horse, world, i5, j3, k5, 0);
            horse.setHorseType(0);
            horse.saddleMountForWorldGen();
            horse.detachHome();
        }
        return true;
    }
}
