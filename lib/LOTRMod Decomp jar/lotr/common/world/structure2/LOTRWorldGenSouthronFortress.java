// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityNearHaradrimWarlord;
import lotr.common.entity.npc.LOTREntityNearHaradrimWarrior;
import lotr.common.entity.npc.LOTREntityNearHaradrimArcher;
import lotr.common.entity.npc.LOTREntityNearHaradrimBase;
import lotr.common.entity.LOTREntityNPCRespawner;
import net.minecraft.entity.EntityCreature;
import lotr.common.world.structure.LOTRChestContents;
import lotr.common.LOTRMod;
import lotr.common.LOTRFoods;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import java.util.Random;
import net.minecraft.world.World;

public class LOTRWorldGenSouthronFortress extends LOTRWorldGenSouthronStructure
{
    public LOTRWorldGenSouthronFortress(final boolean flag) {
        super(flag);
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 15);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -21; i2 <= 21; ++i2) {
                for (int k2 = -15; k2 <= 15; ++k2) {
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
                    if (maxHeight - minHeight > 12) {
                        return false;
                    }
                }
            }
        }
        for (int i3 = -21; i3 <= 21; ++i3) {
            for (int k3 = -15; k3 <= 15; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                if (i4 <= 17 && k4 <= 10) {
                    for (int j2 = 1; j2 <= 8; ++j2) {
                        this.setAir(world, i3, j2, k3);
                    }
                }
                else if (i4 >= 15 && i4 <= 21 && k4 >= 9 && k4 <= 15) {
                    for (int j2 = 1; j2 <= 9; ++j2) {
                        this.setAir(world, i3, j2, k3);
                    }
                }
                else if (i4 <= 2 && k3 <= -10 && k3 >= -15) {
                    for (int j2 = 1; j2 <= 12; ++j2) {
                        this.setAir(world, i3, j2, k3);
                    }
                }
            }
        }
        this.loadStrScan("southron_fort");
        this.associateBlockMetaAlias("STONE", super.stoneBlock, super.stoneMeta);
        this.associateBlockAlias("STONE_STAIR", super.stoneStairBlock);
        this.associateBlockMetaAlias("BRICK", super.brickBlock, super.brickMeta);
        this.associateBlockMetaAlias("BRICK_SLAB", super.brickSlabBlock, super.brickSlabMeta);
        this.associateBlockMetaAlias("BRICK_SLAB_INV", super.brickSlabBlock, super.brickSlabMeta | 0x8);
        this.associateBlockAlias("BRICK_STAIR", super.brickStairBlock);
        this.associateBlockMetaAlias("BRICK_WALL", super.brickWallBlock, super.brickWallMeta);
        this.associateBlockMetaAlias("PILLAR", super.pillarBlock, super.pillarMeta);
        this.associateBlockMetaAlias("BRICK2", super.brick2Block, super.brick2Meta);
        this.associateBlockMetaAlias("BRICK2_SLAB", super.brick2SlabBlock, super.brick2SlabMeta);
        this.associateBlockMetaAlias("BRICK2_SLAB_INV", super.brick2SlabBlock, super.brick2SlabMeta | 0x8);
        this.associateBlockMetaAlias("WOOD", super.woodBlock, super.woodMeta);
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
        this.associateBlockMetaAlias("PLANK2", super.plank2Block, super.plank2Meta);
        this.associateBlockMetaAlias("ROOF", super.roofBlock, super.roofMeta);
        this.associateBlockMetaAlias("ROOF_SLAB", super.roofSlabBlock, super.roofSlabMeta);
        this.associateBlockMetaAlias("ROOF_SLAB_INV", super.roofSlabBlock, super.roofSlabMeta | 0x8);
        this.associateBlockAlias("ROOF_STAIR", super.roofStairBlock);
        this.associateBlockAlias("GATE_METAL", super.gateMetalBlock);
        this.addBlockMetaAliasOption("GROUND", 5, (Block)Blocks.sand, 0);
        this.addBlockMetaAliasOption("GROUND", 3, Blocks.dirt, 1);
        this.addBlockMetaAliasOption("GROUND", 1, (Block)Blocks.sand, 1);
        this.generateStrScan(world, random, 0, 0, 0);
        this.placeWallBanner(world, -5, 8, -13, super.bannerType, 2);
        this.placeWallBanner(world, 5, 8, -13, super.bannerType, 2);
        for (final int k2 : new int[] { 4, 6, 8 }) {
            this.setBlockAndMetadata(world, -6, 1, k2, super.bedBlock, 1);
            this.setBlockAndMetadata(world, -5, 1, k2, super.bedBlock, 9);
            this.setBlockAndMetadata(world, -12, 1, k2, super.bedBlock, 3);
            this.setBlockAndMetadata(world, -13, 1, k2, super.bedBlock, 11);
            this.setBlockAndMetadata(world, 6, 1, k2, super.bedBlock, 3);
            this.setBlockAndMetadata(world, 5, 1, k2, super.bedBlock, 11);
            this.setBlockAndMetadata(world, 12, 1, k2, super.bedBlock, 1);
            this.setBlockAndMetadata(world, 13, 1, k2, super.bedBlock, 9);
        }
        this.setBlockAndMetadata(world, 0, 1, 9, Blocks.bed, 3);
        this.setBlockAndMetadata(world, -1, 1, 9, Blocks.bed, 11);
        this.setBlockAndMetadata(world, 0, 1, 10, Blocks.bed, 3);
        this.setBlockAndMetadata(world, -1, 1, 10, Blocks.bed, 11);
        this.placeWeaponRack(world, -14, 2, -6, 6, this.getRandomHaradWeapon(random));
        this.placeWeaponRack(world, -13, 2, -5, 5, this.getRandomHaradWeapon(random));
        this.placeWeaponRack(world, -15, 2, -5, 7, this.getRandomHaradWeapon(random));
        this.placeWeaponRack(world, -14, 2, -4, 4, this.getRandomHaradWeapon(random));
        this.placeWeaponRack(world, -14, 2, -2, 6, this.getRandomHaradWeapon(random));
        this.placeWeaponRack(world, -13, 2, -1, 5, this.getRandomHaradWeapon(random));
        this.placeWeaponRack(world, -15, 2, -1, 7, this.getRandomHaradWeapon(random));
        this.placeWeaponRack(world, -14, 2, 0, 4, this.getRandomHaradWeapon(random));
        this.placeBarrel(world, random, 3, 2, 4, 5, LOTRFoods.SOUTHRON_DRINK);
        this.placeMug(world, random, 3, 2, 5, 1, LOTRFoods.SOUTHRON_DRINK);
        this.placeChest(world, random, -1, 1, 8, LOTRMod.chestBasket, 4, LOTRChestContents.NEAR_HARAD_TOWER);
        this.setBlockAndMetadata(world, -5, 1, 1, LOTRMod.commandTable, 0);
        for (int warriors = 5 + random.nextInt(5), l = 0; l < warriors; ++l) {
            final LOTREntityNearHaradrimBase warrior = this.createWarrior(world, random);
            warrior.spawnRidingHorse = false;
            this.spawnNPCAndSetHome(warrior, world, 0, 1, 0, 24);
        }
        final LOTREntityNearHaradrimBase captain = this.createCaptain(world, random);
        captain.spawnRidingHorse = false;
        this.spawnNPCAndSetHome(captain, world, 0, 1, 4, 8);
        final LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        this.setSpawnClasses(respawner);
        respawner.setCheckRanges(24, -8, 20, 16);
        respawner.setSpawnRanges(12, -4, 6, 24);
        this.placeNPCRespawner(respawner, world, 0, 0, 0);
        return true;
    }
    
    protected LOTREntityNearHaradrimBase createWarrior(final World world, final Random random) {
        return (random.nextInt(3) == 0) ? new LOTREntityNearHaradrimArcher(world) : new LOTREntityNearHaradrimWarrior(world);
    }
    
    protected LOTREntityNearHaradrimBase createCaptain(final World world, final Random random) {
        return new LOTREntityNearHaradrimWarlord(world);
    }
    
    protected void setSpawnClasses(final LOTREntityNPCRespawner spawner) {
        spawner.setSpawnClasses(LOTREntityNearHaradrimWarrior.class, LOTREntityNearHaradrimArcher.class);
    }
}
