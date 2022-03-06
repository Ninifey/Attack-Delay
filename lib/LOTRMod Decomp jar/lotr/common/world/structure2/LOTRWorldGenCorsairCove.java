// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.LOTRMod;
import net.minecraft.world.gen.feature.WorldGenerator;
import lotr.common.world.feature.LOTRTreeType;
import lotr.common.entity.npc.LOTREntityHaradSlave;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityCorsairSlaver;
import lotr.common.entity.npc.LOTREntityCorsairCaptain;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityCorsair;
import lotr.common.LOTRFoods;
import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.item.LOTRItemBanner;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenCorsairCove extends LOTRWorldGenCorsairStructure
{
    public LOTRWorldGenCorsairCove(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 0);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -15; i2 <= 9; ++i2) {
                for (int k2 = -1; k2 <= 12; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2) - 1;
                    final Block block = this.getBlock(world, i2, j2, k2);
                    if (!this.isSurface(world, i2, j2, k2) && block != Blocks.stone && block != Blocks.sandstone) {
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
        for (int i3 = -14; i3 <= 4; ++i3) {
            for (int k3 = 0; k3 <= 7; ++k3) {
                for (int j3 = 1; j3 <= 9; ++j3) {
                    this.setAir(world, i3, j3, k3);
                }
            }
        }
        this.loadStrScan("corsair_cove");
        this.addBlockMetaAliasOption("STONE", 10, Blocks.stone, 0);
        this.addBlockMetaAliasOption("STONE", 3, Blocks.sandstone, 0);
        this.addBlockMetaAliasOption("STONE", 3, Blocks.dirt, 1);
        this.associateBlockMetaAlias("BRICK", super.brickBlock, super.brickMeta);
        this.associateBlockMetaAlias("BRICK_SLAB", super.brickSlabBlock, super.brickSlabMeta);
        this.associateBlockMetaAlias("BRICK_SLAB_INV", super.brickSlabBlock, super.brickSlabMeta | 0x8);
        this.associateBlockAlias("BRICK_STAIR", super.brickStairBlock);
        this.associateBlockMetaAlias("BRICK_WALL", super.brickWallBlock, super.brickWallMeta);
        this.associateBlockMetaAlias("PILLAR", super.pillarBlock, super.pillarMeta);
        this.associateBlockMetaAlias("PILLAR_SLAB", super.pillarSlabBlock, super.pillarSlabMeta);
        this.associateBlockMetaAlias("PLANK", super.plankBlock, super.plankMeta);
        this.associateBlockMetaAlias("PLANK_SLAB", super.plankSlabBlock, super.plankSlabMeta);
        this.associateBlockMetaAlias("PLANK_SLAB_INV", super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.associateBlockAlias("PLANK_STAIR", super.plankStairBlock);
        this.associateBlockMetaAlias("FENCE", super.fenceBlock, super.fenceMeta);
        this.associateBlockAlias("FENCE_GATE", super.fenceGateBlock);
        this.generateStrScan(world, random, 0, 0, 0);
        this.placeSkull(world, random, -3, 7, 3);
        this.placeBanner(world, 1, 5, 3, LOTRItemBanner.BannerType.UMBAR, 2);
        this.placeChest(world, random, -14, 4, 4, (Block)Blocks.chest, 4, LOTRChestContents.CORSAIR, MathHelper.getRandomIntegerInRange(random, 6, 12));
        this.placeBarrel(world, random, -14, 5, 6, 4, LOTRFoods.CORSAIR_DRINK);
        this.placeWallBanner(world, -12, 8, 3, LOTRItemBanner.BannerType.UMBAR, 2);
        this.placeWallBanner(world, -12, 8, 7, LOTRItemBanner.BannerType.UMBAR, 0);
        this.placeWallBanner(world, -14, 8, 5, LOTRItemBanner.BannerType.UMBAR, 3);
        this.placeWallBanner(world, -10, 8, 5, LOTRItemBanner.BannerType.UMBAR, 1);
        this.placeWeaponRack(world, -7, 5, 8, 6, this.getRandomCorsairWeapon(random));
        this.placeWeaponRack(world, -6, 5, 8, 6, this.getRandomCorsairWeapon(random));
        this.placeWeaponRack(world, -5, 5, 8, 6, this.getRandomCorsairWeapon(random));
        if (random.nextInt(3) == 0) {
            this.placeTreasure(world, random, -14, 4, 2);
            this.placeTreasure(world, random, -14, 4, 1);
            this.placeTreasure(world, random, -13, 4, 1);
            this.placeTreasure(world, random, -12, 4, 1);
            this.placeTreasure(world, random, -12, 4, 0);
            this.placeTreasure(world, random, -11, 4, 0);
        }
        if (random.nextInt(3) == 0) {
            this.placeTreasure(world, random, -4, 4, 0);
            this.placeTreasure(world, random, -3, 5, 0);
            this.placeTreasure(world, random, -3, 4, 1);
            this.placeTreasure(world, random, -3, 4, 2);
            this.placeTreasure(world, random, -2, 4, 1);
        }
        for (int i3 = -14; i3 <= -5; ++i3) {
            for (int k3 = 0; k3 <= 8; ++k3) {
                final int j3 = 4;
                if (this.isAir(world, i3, j3, k3) && this.isOpaque(world, i3, j3 - 1, k3) && random.nextInt(20) == 0) {
                    this.placeFoodOrDrink(world, random, i3, j3, k3);
                }
            }
        }
        for (int corsairs = 2 + random.nextInt(2), l = 0; l < corsairs; ++l) {
            final LOTREntityCorsair corsair = new LOTREntityCorsair(world);
            this.spawnNPCAndSetHome(corsair, world, -9, 4, 4, 16);
        }
        final LOTREntityCorsair captain = (LOTREntityCorsair)(random.nextBoolean() ? new LOTREntityCorsairCaptain(world) : new LOTREntityCorsairSlaver(world));
        this.spawnNPCAndSetHome(captain, world, -9, 4, 4, 4);
        final LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        respawner.setSpawnClass(LOTREntityCorsair.class);
        respawner.setCheckRanges(24, -16, 12, 8);
        respawner.setSpawnRanges(3, -2, 2, 16);
        this.placeNPCRespawner(respawner, world, -9, 4, 4);
        final LOTREntityHaradSlave slave = new LOTREntityHaradSlave(world);
        this.spawnNPCAndSetHome(slave, world, -7, 7, 6, 8);
        for (int m = 0; m < 16; ++m) {
            final LOTRTreeType tree = LOTRTreeType.PALM;
            final WorldGenerator treeGen = (WorldGenerator)tree.create(super.notifyChanges, random);
            if (treeGen != null) {
                final int i4 = 2;
                final int j4 = 6;
                final int k4 = 7;
                if (treeGen.generate(world, random, this.getX(i4, k4), this.getY(j4), this.getZ(i4, k4))) {
                    break;
                }
            }
        }
        return true;
    }
    
    private void placeFoodOrDrink(final World world, final Random random, final int i, final int j, final int k) {
        if (random.nextInt(3) != 0) {
            this.placeMug(world, random, i, j, k, random.nextInt(4), LOTRFoods.CORSAIR_DRINK);
        }
        else {
            final Block plateBlock = LOTRMod.woodPlateBlock;
            if (random.nextBoolean()) {
                this.setBlockAndMetadata(world, i, j, k, plateBlock, 0);
            }
            else {
                this.placePlateWithCertainty(world, random, i, j, k, plateBlock, LOTRFoods.CORSAIR);
            }
        }
    }
    
    private void placeTreasure(final World world, final Random random, final int i, final int j, final int k) {
        final Block block = random.nextBoolean() ? LOTRMod.treasureGold : LOTRMod.treasureSilver;
        this.setBlockAndMetadata(world, i, j, k, block, random.nextInt(7));
    }
}
