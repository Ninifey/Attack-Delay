// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityGulfHaradrim;
import lotr.common.LOTRFoods;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenGulfHouse extends LOTRWorldGenGulfStructure
{
    public LOTRWorldGenGulfHouse(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 8);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -8; i2 <= 8; ++i2) {
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
                    if (maxHeight - minHeight > 6) {
                        return false;
                    }
                }
            }
        }
        for (int i3 = -8; i3 <= 8; ++i3) {
            for (int k3 = -8; k3 <= 8; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                if (i4 * i4 + k4 * k4 < 64) {
                    for (int j2 = 1; j2 <= 5; ++j2) {
                        this.setAir(world, i3, j2, k3);
                    }
                }
            }
        }
        this.loadStrScan("gulf_house");
        this.associateBlockMetaAlias("WOOD", super.woodBlock, super.woodMeta);
        this.associateBlockMetaAlias("PLANK", super.plankBlock, super.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", super.plankSlabBlock, super.plankSlabMeta);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.associateBlockAlias("PLANK_STAIR", super.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", super.fenceBlock, super.fenceMeta);
        this.associateBlockAlias("DOOR", super.doorBlock);
        this.associateBlockMetaAlias("BEAM", super.beamBlock, super.beamMeta);
        this.associateBlockMetaAlias("PLANK2", super.plank2Block, super.plank2Meta);
        this.associateBlockMetaAlias("PLANK2_SLAB", super.plank2SlabBlock, super.plank2SlabMeta);
        this.associateBlockMetaAlias("ROOF", super.roofBlock, super.roofMeta);
        this.associateBlockMetaAlias("ROOF_SLAB", super.roofSlabBlock, super.roofSlabMeta);
        this.associateBlockMetaAlias("ROOF_SLAB_INV", super.roofSlabBlock, super.roofSlabMeta | 0x8);
        this.associateBlockAlias("ROOF_STAIR", super.roofStairBlock);
        this.generateStrScan(world, random, 0, 0, 0);
        this.setBlockAndMetadata(world, 0, 1, 5, super.bedBlock, 0);
        this.setBlockAndMetadata(world, 0, 1, 6, super.bedBlock, 8);
        this.placeChest(world, random, 6, 1, 0, LOTRMod.chestBasket, 5, LOTRChestContents.GULF_HOUSE);
        for (final int k2 : new int[] { -2, 0, 2 }) {
            final int i5 = -6;
            final int j3 = 2;
            if (random.nextBoolean()) {
                this.placePlate(world, random, i5, j3, k2, LOTRMod.woodPlateBlock, LOTRFoods.GULF_HARAD);
            }
            else {
                this.placeMug(world, random, i5, j3, k2, 3, LOTRFoods.GULF_HARAD_DRINK);
            }
        }
        for (int men = 1 + random.nextInt(2), l = 0; l < men; ++l) {
            final LOTREntityGulfHaradrim gulfman = new LOTREntityGulfHaradrim(world);
            this.spawnNPCAndSetHome(gulfman, world, 0, 0, 0, 16);
        }
        return true;
    }
}
