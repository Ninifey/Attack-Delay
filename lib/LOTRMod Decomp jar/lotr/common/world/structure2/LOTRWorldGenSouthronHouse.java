// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityNearHaradrimBase;
import net.minecraft.entity.EntityCreature;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenSouthronHouse extends LOTRWorldGenSouthronStructure
{
    public LOTRWorldGenSouthronHouse(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 5);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -3; i2 <= 3; ++i2) {
                for (int k2 = -5; k2 <= 6; ++k2) {
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
                    if (maxHeight - minHeight > 6) {
                        return false;
                    }
                }
            }
        }
        for (int i3 = -3; i3 <= 3; ++i3) {
            for (int k3 = -6; k3 <= 6; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                if (i4 <= 2 && k4 <= 4) {
                    for (int j2 = 0; !this.isOpaque(world, i3, j2, k3) && this.getY(j2) >= 0; --j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, super.stoneBlock, super.stoneMeta);
                        this.setGrassToDirt(world, i3, j2 - 1, k3);
                    }
                    for (int j2 = 1; j2 <= 7; ++j2) {
                        this.setAir(world, i3, j2, k3);
                    }
                }
                if ((i4 <= 2 && k3 == 5) || (i3 >= -3 && i3 <= 2 && k3 == 6)) {
                    for (int j2 = 0; !this.isOpaque(world, i3, j2, k3) && this.getY(j2) >= 0; --j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, super.brickBlock, super.brickMeta);
                        this.setGrassToDirt(world, i3, j2 - 1, k3);
                    }
                    for (int j2 = 1; j2 <= 7; ++j2) {
                        this.setAir(world, i3, j2, k3);
                    }
                }
            }
        }
        this.loadStrScan("southron_house");
        this.associateBlockMetaAlias("STONE", super.stoneBlock, super.stoneMeta);
        this.associateBlockMetaAlias("BRICK", super.brickBlock, super.brickMeta);
        this.associateBlockMetaAlias("BRICK_SLAB", super.brickSlabBlock, super.brickSlabMeta);
        this.associateBlockMetaAlias("BRICK_SLAB_INV", super.brickSlabBlock, super.brickSlabMeta | 0x8);
        this.associateBlockAlias("BRICK_STAIR", super.brickStairBlock);
        this.associateBlockMetaAlias("PLANK", super.plankBlock, super.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", super.plankSlabBlock, super.plankSlabMeta);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.associateBlockAlias("PLANK_STAIR", super.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", super.fenceBlock, super.fenceMeta);
        this.associateBlockMetaAlias("BEAM", super.woodBeamBlock, super.woodBeamMeta);
        this.associateBlockMetaAlias("BEAM|4", super.woodBeamBlock, super.woodBeamMeta4);
        this.associateBlockMetaAlias("BEAM|8", super.woodBeamBlock, super.woodBeamMeta8);
        this.associateBlockAlias("DOOR", super.doorBlock);
        this.associateBlockMetaAlias("ROOF", super.roofBlock, super.roofMeta);
        this.associateBlockMetaAlias("ROOF_SLAB", super.roofSlabBlock, super.roofSlabMeta);
        this.associateBlockMetaAlias("ROOF_SLAB_INV", super.roofSlabBlock, super.roofSlabMeta | 0x8);
        this.associateBlockAlias("ROOF_STAIR", super.roofStairBlock);
        this.associateBlockMetaAlias("TABLE", super.tableBlock, 0);
        this.generateStrScan(world, random, 0, 0, 0);
        if (!this.isOpaque(world, 0, 0, 7) || this.isOpaque(world, 0, 1, 7)) {
            for (int i3 = -4; i3 <= 2; ++i3) {
                for (int k3 = 6; k3 <= 7; ++k3) {
                    if (k3 == 7 || (i3 == -4 && k3 == 6)) {
                        for (int j3 = 0; (j3 >= 0 || !this.isOpaque(world, i3, j3, k3)) && this.getY(j3) >= 0; --j3) {
                            this.setBlockAndMetadata(world, i3, j3, k3, super.brickBlock, super.brickMeta);
                            this.setGrassToDirt(world, i3, j3 - 1, k3);
                        }
                        for (int j3 = 1; j3 <= 3; ++j3) {
                            this.setAir(world, i3, j3, k3);
                        }
                    }
                }
            }
        }
        this.placeWallBanner(world, -2, 3, 0, super.bannerType, 1);
        for (final int k2 : new int[] { -2, 0, 2 }) {
            final int i5 = -1;
            final int j4 = 2;
            if (random.nextBoolean()) {
                this.placePlate(world, random, i5, j4, k2, LOTRMod.woodPlateBlock, LOTRFoods.SOUTHRON);
            }
            else {
                this.placeMug(world, random, i5, j4, k2, 1, LOTRFoods.SOUTHRON_DRINK);
            }
        }
        this.setBlockAndMetadata(world, -1, 5, -2, super.bedBlock, 2);
        this.setBlockAndMetadata(world, -1, 5, -3, super.bedBlock, 10);
        this.setBlockAndMetadata(world, 1, 5, -2, super.bedBlock, 2);
        this.setBlockAndMetadata(world, 1, 5, -3, super.bedBlock, 10);
        this.placeFlowerPot(world, 0, 6, -3, this.getRandomFlower(world, random));
        this.placeChest(world, random, -1, 5, 3, LOTRMod.chestBasket, 2, LOTRChestContents.NEAR_HARAD_HOUSE);
        for (int men = 1 + random.nextInt(2), l = 0; l < men; ++l) {
            final LOTREntityNearHaradrimBase haradrim = this.createHaradrim(world);
            this.spawnNPCAndSetHome(haradrim, world, 0, 1, 0, 16);
        }
        return true;
    }
}
