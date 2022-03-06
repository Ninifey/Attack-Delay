// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityGulfHaradrim;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityGulfBlacksmith;
import lotr.common.LOTRFoods;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Blocks;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.LOTRMod;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenGulfSmithy extends LOTRWorldGenGulfStructure
{
    public LOTRWorldGenGulfSmithy(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 6);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -14; i2 <= 6; ++i2) {
                for (int k2 = -6; k2 <= 6; ++k2) {
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
        for (int i3 = -14; i3 <= 6; ++i3) {
            for (int k3 = -6; k3 <= 6; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                if (i4 * i4 + k4 * k4 < 25 || (i3 <= -7 && k4 <= 5)) {
                    for (int j2 = 1; j2 <= 5; ++j2) {
                        this.setAir(world, i3, j2, k3);
                    }
                }
            }
        }
        this.loadStrScan("gulf_smithy");
        this.associateBlockMetaAlias("BRICK", super.brickBlock, super.brickMeta);
        this.associateBlockAlias("BRICK_STAIR", super.brickStairBlock);
        this.associateBlockMetaAlias("BRICK_WALL", super.brickWallBlock, super.brickWallMeta);
        this.associateBlockMetaAlias("WOOD", super.woodBlock, super.woodMeta);
        this.associateBlockMetaAlias("PLANK", super.plankBlock, super.plankMeta);
        this.associateBlockAlias("PLANK_STAIR", super.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", super.fenceBlock, super.fenceMeta);
        this.associateBlockAlias("FENCE_GATE", super.fenceGateBlock);
        this.associateBlockAlias("DOOR", super.doorBlock);
        this.associateBlockMetaAlias("BEAM", super.beamBlock, super.beamMeta);
        this.associateBlockMetaAlias("PLANK2", super.plank2Block, super.plank2Meta);
        this.associateBlockMetaAlias("PLANK2_SLAB", super.plank2SlabBlock, super.plank2SlabMeta);
        this.associateBlockMetaAlias("PLANK2_SLAB_INV", super.plank2SlabBlock, super.plank2SlabMeta | 0x8);
        this.associateBlockAlias("PLANK2_STAIR", super.plank2StairBlock);
        this.associateBlockMetaAlias("ROOF", super.roofBlock, super.roofMeta);
        this.associateBlockMetaAlias("ROOF_SLAB", super.roofSlabBlock, super.roofSlabMeta);
        this.associateBlockMetaAlias("ROOF_SLAB_INV", super.roofSlabBlock, super.roofSlabMeta | 0x8);
        this.associateBlockAlias("ROOF_STAIR", super.roofStairBlock);
        this.associateBlockMetaAlias("FLAG", super.flagBlock, super.flagMeta);
        this.associateBlockMetaAlias("BONE", super.boneBlock, super.boneMeta);
        this.generateStrScan(world, random, 0, 0, 0);
        this.setBlockAndMetadata(world, 0, 1, 3, super.bedBlock, 0);
        this.setBlockAndMetadata(world, 0, 1, 4, super.bedBlock, 8);
        this.placeChest(world, random, -4, 1, -2, LOTRMod.chestBasket, 3, LOTRChestContents.GULF_HOUSE);
        this.placeFlowerPot(world, 2, 2, -4, this.getRandomFlower(world, random));
        this.placeFlowerPot(world, -2, 2, 4, this.getRandomFlower(world, random));
        this.placeFlowerPot(world, -4, 1, 1, new ItemStack(Blocks.cactus));
        this.placeMug(world, random, 4, 2, -1, 1, LOTRFoods.GULF_HARAD_DRINK);
        this.placeMug(world, random, 2, 2, 4, 0, LOTRFoods.GULF_HARAD_DRINK);
        this.placePlate(world, random, 4, 2, 0, LOTRMod.woodPlateBlock, LOTRFoods.GULF_HARAD);
        this.placePlate(world, random, 4, 2, 1, LOTRMod.woodPlateBlock, LOTRFoods.GULF_HARAD);
        if (random.nextBoolean()) {
            this.placeArmorStand(world, -7, 1, -2, 1, new ItemStack[] { new ItemStack(LOTRMod.helmetGulfHarad), new ItemStack(LOTRMod.bodyGulfHarad), new ItemStack(LOTRMod.legsGulfHarad), new ItemStack(LOTRMod.bootsGulfHarad) });
        }
        else {
            this.placeArmorStand(world, -7, 1, -2, 1, new ItemStack[] { null, new ItemStack(LOTRMod.bodyGulfHarad), null, null });
        }
        this.placeWeaponRack(world, -13, 3, 0, 5, this.getRandomGulfWeapon(random));
        final LOTREntityGulfHaradrim smith = new LOTREntityGulfBlacksmith(world);
        this.spawnNPCAndSetHome(smith, world, -6, 1, 0, 8);
        for (int maxSteps = 12, step = 0; step < maxSteps; ++step) {
            final int i5 = -9;
            final int j2 = 0 - step;
            final int k5 = -5 - step;
            if (this.isOpaque(world, i5, j2, k5)) {
                break;
            }
            this.setBlockAndMetadata(world, i5, j2, k5, LOTRMod.stairsRedSandstone, 2);
            this.setGrassToDirt(world, i5, j2 - 1, k5);
            for (int j3 = j2 - 1; !this.isOpaque(world, i5, j3, k5) && this.getY(j3) >= 0; --j3) {
                this.setBlockAndMetadata(world, i5, j3, k5, LOTRMod.redSandstone, 0);
                this.setGrassToDirt(world, i5, j3 - 1, k5);
            }
        }
        return true;
    }
}
