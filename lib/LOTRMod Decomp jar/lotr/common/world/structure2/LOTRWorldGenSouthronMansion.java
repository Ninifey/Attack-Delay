// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityNearHaradrimBase;
import net.minecraft.entity.EntityCreature;
import net.minecraft.item.ItemStack;
import lotr.common.LOTRFoods;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.LOTRMod;
import net.minecraft.world.World;
import net.minecraft.init.Blocks;
import java.util.Random;

public class LOTRWorldGenSouthronMansion extends LOTRWorldGenSouthronStructure
{
    public LOTRWorldGenSouthronMansion(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        super.setupRandomBlocks(random);
        super.bedBlock = Blocks.bed;
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 9);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -5; i2 <= 11; ++i2) {
                for (int k2 = -9; k2 <= 5; ++k2) {
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
        for (int i3 = -5; i3 <= 11; ++i3) {
            for (int k3 = -9; k3 <= 5; ++k3) {
                final int i4 = Math.abs(i3);
                if (i3 >= -4 && i3 <= 10 && k3 >= -4 && k3 <= 4) {
                    for (int j3 = 0; !this.isOpaque(world, i3, j3, k3) && this.getY(j3) >= 0; --j3) {
                        this.setBlockAndMetadata(world, i3, j3, k3, super.stoneBlock, super.stoneMeta);
                        this.setGrassToDirt(world, i3, j3 - 1, k3);
                    }
                    for (int j3 = 1; j3 <= 8; ++j3) {
                        this.setAir(world, i3, j3, k3);
                    }
                }
                if ((i4 <= 2 && k3 == -9) || (i4 <= 4 && k3 >= -8 && k3 <= -5)) {
                    for (int j3 = -1; !this.isOpaque(world, i3, j3, k3) && this.getY(j3) >= 0; --j3) {
                        this.setBlockAndMetadata(world, i3, j3, k3, Blocks.dirt, 0);
                        this.setGrassToDirt(world, i3, j3 - 1, k3);
                    }
                    for (int j3 = 1; j3 <= 6; ++j3) {
                        this.setAir(world, i3, j3, k3);
                    }
                }
            }
        }
        this.loadStrScan("southron_mansion");
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
        this.associateBlockAlias("DOOR", super.doorBlock);
        this.associateBlockMetaAlias("ROOF", super.roofBlock, super.roofMeta);
        this.associateBlockMetaAlias("ROOF_SLAB", super.roofSlabBlock, super.roofSlabMeta);
        this.associateBlockMetaAlias("ROOF_SLAB_INV", super.roofSlabBlock, super.roofSlabMeta | 0x8);
        this.associateBlockAlias("ROOF_STAIR", super.roofStairBlock);
        this.associateBlockMetaAlias("TABLE", super.tableBlock, 0);
        this.associateBlockAlias("CROP", super.cropBlock);
        this.generateStrScan(world, random, 0, 0, 0);
        this.plantFlower(world, random, -2, 1, -5);
        this.plantFlower(world, random, -1, 1, -5);
        this.plantFlower(world, random, 1, 1, -5);
        this.plantFlower(world, random, 2, 1, -5);
        this.placeWallBanner(world, 3, 3, -4, super.bannerType, 0);
        this.placeChest(world, random, -3, 1, 3, LOTRMod.chestBasket, 2, LOTRChestContents.NEAR_HARAD_HOUSE);
        this.placeBarrel(world, random, 6, 2, 2, 4, LOTRFoods.SOUTHRON_DRINK);
        this.placePlateWithCertainty(world, random, 6, 2, 1, LOTRMod.ceramicPlateBlock, LOTRFoods.SOUTHRON);
        this.placeMug(world, random, 6, 2, 3, 3, LOTRFoods.SOUTHRON_DRINK);
        this.placeWeaponRack(world, 10, 2, -2, 7, this.getRandomHaradWeapon(random));
        this.setBlockAndMetadata(world, 8, 5, -1, super.bedBlock, 1);
        this.setBlockAndMetadata(world, 9, 5, -1, super.bedBlock, 9);
        this.setBlockAndMetadata(world, 8, 5, 1, super.bedBlock, 1);
        this.setBlockAndMetadata(world, 9, 5, 1, super.bedBlock, 9);
        this.placeFlowerPot(world, 4, 2, 1, this.getRandomFlower(world, random));
        this.placeFlowerPot(world, 9, 6, -3, new ItemStack(LOTRMod.sapling3, 1, 2));
        this.placeFlowerPot(world, 9, 6, 3, new ItemStack(LOTRMod.sapling3, 1, 2));
        for (int numHaradrim = 2, l = 0; l < numHaradrim; ++l) {
            final LOTREntityNearHaradrimBase haradrim = this.createHaradrim(world);
            this.spawnNPCAndSetHome(haradrim, world, 0, 1, 0, 16);
        }
        return true;
    }
}
