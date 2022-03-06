// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityTauredain;
import lotr.common.LOTRFoods;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.LOTRMod;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenTauredainHouseSimple extends LOTRWorldGenTauredainHouse
{
    public LOTRWorldGenTauredainHouseSimple(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected int getOffset() {
        return 4;
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 3);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int range = 4, i2 = -range; i2 <= range; ++i2) {
                for (int k2 = -range; k2 <= range; ++k2) {
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
            for (int k3 = -3; k3 <= 3; ++k3) {
                for (int j3 = 1; j3 <= 6; ++j3) {
                    this.setAir(world, i3, j3, k3);
                }
            }
        }
        this.loadStrScan("taurethrim_house");
        this.associateBlockMetaAlias("BRICK", super.brickBlock, super.brickMeta);
        this.associateBlockAlias("BRICK_STAIR", super.brickStairBlock);
        this.associateBlockMetaAlias("BRICK_WALL", super.brickWallBlock, super.brickWallMeta);
        this.associateBlockMetaAlias("WOOD|4", super.woodBlock, super.woodMeta | 0x4);
        this.associateBlockMetaAlias("PLANK", super.plankBlock, super.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", super.plankSlabBlock, super.plankSlabMeta);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.associateBlockAlias("PLANK_STAIR", super.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", super.fenceBlock, super.fenceMeta);
        this.associateBlockAlias("DOOR", super.doorBlock);
        this.associateBlockMetaAlias("ROOF", super.thatchBlock, super.thatchMeta);
        this.associateBlockMetaAlias("ROOF_SLAB", super.thatchSlabBlock, super.thatchSlabMeta);
        this.associateBlockMetaAlias("ROOF_SLAB_INV", super.thatchSlabBlock, super.thatchSlabMeta | 0x8);
        this.associateBlockAlias("ROOF_STAIR", super.thatchStairBlock);
        this.associateBlockMetaAlias("WALL", Blocks.stained_hardened_clay, 12);
        this.addBlockMetaAliasOption("GROUND", 10, super.floorBlock, super.floorMeta);
        this.addBlockMetaAliasOption("GROUND", 10, LOTRMod.mud, 0);
        this.generateStrScan(world, random, 0, 0, 0);
        this.setBlockAndMetadata(world, -2, 1, 1, super.bedBlock, 0);
        this.setBlockAndMetadata(world, -2, 1, 2, super.bedBlock, 8);
        this.setBlockAndMetadata(world, 2, 1, 1, super.bedBlock, 0);
        this.setBlockAndMetadata(world, 2, 1, 2, super.bedBlock, 8);
        this.placeChest(world, random, 0, 1, 2, LOTRMod.chestBasket, 2, LOTRChestContents.TAUREDAIN_HOUSE);
        this.placeTauredainFlowerPot(world, -2, 2, 0, random);
        this.placeTauredainFlowerPot(world, -1, 2, 2, random);
        this.placeTauredainFlowerPot(world, 0, 4, -2, random);
        this.placeTauredainFlowerPot(world, 0, 4, 2, random);
        this.placePlateWithCertainty(world, random, 1, 2, 2, LOTRMod.woodPlateBlock, LOTRFoods.TAUREDAIN);
        this.placeMug(world, random, 2, 2, 0, 3, LOTRFoods.TAUREDAIN_DRINK);
        for (int men = 1 + random.nextInt(2), l = 0; l < men; ++l) {
            final LOTREntityTauredain tauredain = new LOTREntityTauredain(world);
            this.spawnNPCAndSetHome(tauredain, world, 0, 1, 0, 8);
        }
        return true;
    }
}
