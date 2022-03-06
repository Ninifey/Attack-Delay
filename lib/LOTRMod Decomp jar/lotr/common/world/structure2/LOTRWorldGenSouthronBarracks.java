// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityNearHaradrimWarrior;
import lotr.common.entity.npc.LOTREntityNearHaradrimArcher;
import lotr.common.entity.npc.LOTREntityNearHaradrimBase;
import net.minecraft.entity.EntityCreature;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenSouthronBarracks extends LOTRWorldGenSouthronStructure
{
    public LOTRWorldGenSouthronBarracks(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 8);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -6; i2 <= 5; ++i2) {
                for (int k2 = -8; k2 <= 8; ++k2) {
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
        for (int i3 = -5; i3 <= 4; ++i3) {
            for (int k3 = -7; k3 <= 7; ++k3) {
                for (int j3 = 1; j3 <= 5; ++j3) {
                    this.setAir(world, i3, j3, k3);
                }
            }
        }
        this.loadStrScan("southron_barracks");
        this.associateBlockMetaAlias("STONE", super.stoneBlock, super.stoneMeta);
        this.associateBlockMetaAlias("BRICK", super.brickBlock, super.brickMeta);
        this.associateBlockAlias("BRICK_STAIR", super.brickStairBlock);
        this.associateBlockMetaAlias("PLANK", super.plankBlock, super.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", super.plankSlabBlock, super.plankSlabMeta);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.associateBlockAlias("PLANK_STAIR", super.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", super.fenceBlock, super.fenceMeta);
        this.associateBlockMetaAlias("BEAM", super.woodBeamBlock, super.woodBeamMeta);
        this.associateBlockMetaAlias("BEAM|4", super.woodBeamBlock, super.woodBeamMeta4);
        this.associateBlockMetaAlias("BEAM|8", super.woodBeamBlock, super.woodBeamMeta8);
        this.associateBlockMetaAlias("ROOF_SLAB", super.roofSlabBlock, super.roofSlabMeta);
        this.associateBlockMetaAlias("ROOF_SLAB_INV", super.roofSlabBlock, super.roofSlabMeta | 0x8);
        this.generateStrScan(world, random, 0, 0, 0);
        for (int k4 = -4; k4 <= 4; k4 += 2) {
            if (random.nextBoolean()) {
                this.placeChest(world, random, -4, 1, k4, LOTRMod.chestBasket, 4, LOTRChestContents.NEAR_HARAD_TOWER, 1 + random.nextInt(2));
            }
            else {
                this.setBlockAndMetadata(world, -4, 1, k4, LOTRMod.chestBasket, 4);
            }
            if (random.nextBoolean()) {
                this.placeChest(world, random, 3, 1, k4, LOTRMod.chestBasket, 5, LOTRChestContents.NEAR_HARAD_TOWER, 1 + random.nextInt(2));
            }
            else {
                this.setBlockAndMetadata(world, 3, 1, k4, LOTRMod.chestBasket, 5);
            }
        }
        for (int k4 = -5; k4 <= 5; k4 += 2) {
            for (int j4 = 1; j4 <= 2; ++j4) {
                this.setBlockAndMetadata(world, -3, j4, k4, super.bedBlock, 3);
                this.setBlockAndMetadata(world, -4, j4, k4, super.bedBlock, 11);
                this.setBlockAndMetadata(world, 2, j4, k4, super.bedBlock, 1);
                this.setBlockAndMetadata(world, 3, j4, k4, super.bedBlock, 9);
            }
        }
        for (int warriors = 2 + random.nextInt(3), l = 0; l < warriors; ++l) {
            final LOTREntityNearHaradrimBase haradrim = this.createWarrior(world, random);
            this.spawnNPCAndSetHome(haradrim, world, random.nextBoolean() ? -1 : 0, 1, 0, 16);
        }
        return true;
    }
    
    protected LOTREntityNearHaradrimBase createWarrior(final World world, final Random random) {
        return (random.nextInt(3) == 0) ? new LOTREntityNearHaradrimArcher(world) : new LOTREntityNearHaradrimWarrior(world);
    }
}
