// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityNearHaradBlacksmith;
import lotr.common.entity.npc.LOTREntityUmbarBlacksmith;
import lotr.common.entity.npc.LOTREntityNearHaradrimBase;
import net.minecraft.entity.EntityCreature;
import lotr.common.LOTRFoods;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenSouthronSmithy extends LOTRWorldGenSouthronStructure
{
    public LOTRWorldGenSouthronSmithy(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 5, -1);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -4; i2 <= 4; ++i2) {
                for (int k2 = -5; k2 <= 13; ++k2) {
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
        for (int i3 = -4; i3 <= 4; ++i3) {
            for (int k3 = -5; k3 <= 13; ++k3) {
                final int i4 = Math.abs(i3);
                if ((i4 <= 3 && k3 >= -4 && k3 <= 6) || (i4 <= 4 && k3 >= 7 && k3 <= 12)) {
                    for (int j3 = 0; !this.isOpaque(world, i3, j3, k3) && this.getY(j3) >= 0; --j3) {
                        this.setBlockAndMetadata(world, i3, j3, k3, super.stoneBlock, super.stoneMeta);
                        this.setGrassToDirt(world, i3, j3 - 1, k3);
                    }
                    for (int j3 = 1; j3 <= 8; ++j3) {
                        this.setAir(world, i3, j3, k3);
                    }
                }
            }
        }
        this.loadStrScan("southron_smithy");
        this.associateBlockMetaAlias("STONE", super.stoneBlock, super.stoneMeta);
        this.associateBlockMetaAlias("BRICK", super.brickBlock, super.brickMeta);
        this.associateBlockMetaAlias("BRICK_SLAB", super.brickSlabBlock, super.brickSlabMeta);
        this.associateBlockMetaAlias("BRICK_SLAB_INV", super.brickSlabBlock, super.brickSlabMeta | 0x8);
        this.associateBlockAlias("BRICK_STAIR", super.brickStairBlock);
        this.associateBlockMetaAlias("BRICK_WALL", super.brickWallBlock, super.brickWallMeta);
        this.associateBlockMetaAlias("PILLAR", super.pillarBlock, super.pillarMeta);
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
        this.generateStrScan(world, random, 0, 1, 0);
        this.setBlockAndMetadata(world, -1, 5, -2, super.bedBlock, 2);
        this.setBlockAndMetadata(world, -2, 5, -2, super.bedBlock, 2);
        this.setBlockAndMetadata(world, -1, 5, -3, super.bedBlock, 10);
        this.setBlockAndMetadata(world, -2, 5, -3, super.bedBlock, 10);
        this.placeChest(world, random, 3, 1, 6, LOTRMod.chestBasket, 5, LOTRChestContents.NEAR_HARAD_HOUSE);
        this.placeChest(world, random, 2, 5, -3, LOTRMod.chestBasket, 5, LOTRChestContents.NEAR_HARAD_HOUSE);
        this.placePlateWithCertainty(world, random, -1, 6, 3, LOTRMod.ceramicPlateBlock, LOTRFoods.SOUTHRON);
        this.placeMug(world, random, -2, 6, 3, 0, LOTRFoods.SOUTHRON_DRINK);
        this.placeWeaponRack(world, -3, 1, 8, 1, this.getRandomHaradWeapon(random));
        this.placeWeaponRack(world, 3, 1, 8, 3, this.getRandomHaradWeapon(random));
        final LOTREntityNearHaradrimBase smith = this.createSmith(world);
        this.spawnNPCAndSetHome(smith, world, 0, 1, 6, 8);
        return true;
    }
    
    protected LOTREntityNearHaradrimBase createSmith(final World world) {
        if (this.isUmbar()) {
            return new LOTREntityUmbarBlacksmith(world);
        }
        return new LOTREntityNearHaradBlacksmith(world);
    }
}
