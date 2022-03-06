// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityEasterling;
import lotr.common.entity.LOTREntityNPCRespawner;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityEasterlingWarrior;
import lotr.common.entity.npc.LOTREntityEasterlingArcher;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import lotr.common.LOTRMod;
import java.util.Random;

public class LOTRWorldGenEasterlingTower extends LOTRWorldGenEasterlingStructureTown
{
    private boolean enableDoor;
    private boolean frontLadder;
    private boolean backLadder;
    private boolean leftLadder;
    private boolean rightLadder;
    
    public LOTRWorldGenEasterlingTower(final boolean flag) {
        super(flag);
        this.enableDoor = true;
        this.frontLadder = false;
        this.backLadder = false;
        this.leftLadder = false;
        this.rightLadder = false;
    }
    
    public LOTRWorldGenEasterlingTower disableDoor() {
        this.enableDoor = false;
        return this;
    }
    
    public LOTRWorldGenEasterlingTower setFrontLadder() {
        this.frontLadder = true;
        return this;
    }
    
    public LOTRWorldGenEasterlingTower setBackLadder() {
        this.backLadder = true;
        return this;
    }
    
    public LOTRWorldGenEasterlingTower setLeftLadder() {
        this.leftLadder = true;
        return this;
    }
    
    public LOTRWorldGenEasterlingTower setRightLadder() {
        this.rightLadder = true;
        return this;
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        super.setupRandomBlocks(random);
        super.bedBlock = LOTRMod.strawBed;
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 3);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            for (int i2 = -3; i2 <= 3; ++i2) {
                for (int k2 = -3; k2 <= 3; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2) - 1;
                    if (!this.isSurface(world, i2, j2, k2)) {
                        return false;
                    }
                }
            }
        }
        for (int i2 = -2; i2 <= 2; ++i2) {
            for (int k2 = -2; k2 <= 2; ++k2) {
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                for (int j3 = 1; j3 <= 15; ++j3) {
                    this.setAir(world, i2, j3, k2);
                }
                if (i3 == 2 && k3 == 2) {
                    for (int j3 = 13; (j3 >= 0 || !this.isOpaque(world, i2, j3, k2)) && this.getY(j3) >= 0; --j3) {
                        this.setBlockAndMetadata(world, i2, j3, k2, super.woodBeamBlock, super.woodBeamMeta);
                        this.setGrassToDirt(world, i2, j3 - 1, k2);
                    }
                }
                else {
                    for (int j3 = 0; (j3 >= 0 || !this.isOpaque(world, i2, j3, k2)) && this.getY(j3) >= 0; --j3) {
                        this.setBlockAndMetadata(world, i2, j3, k2, super.brickBlock, super.brickMeta);
                        this.setGrassToDirt(world, i2, j3 - 1, k2);
                    }
                    if (i3 == 2 || k3 == 2) {
                        if ((i3 == 2 && k3 == 0) || (k3 == 2 && i3 == 0)) {
                            for (int j3 = 1; j3 <= 9; ++j3) {
                                this.setBlockAndMetadata(world, i2, j3, k2, super.pillarBlock, super.pillarMeta);
                            }
                        }
                        else {
                            for (int j3 = 1; j3 <= 2; ++j3) {
                                this.setBlockAndMetadata(world, i2, j3, k2, super.brickBlock, super.brickMeta);
                            }
                            int stairMeta = 0;
                            if (i2 == -2) {
                                stairMeta = 1;
                            }
                            else if (i2 == 2) {
                                stairMeta = 0;
                            }
                            else if (k2 == -2) {
                                stairMeta = 2;
                            }
                            else if (k2 == 2) {
                                stairMeta = 3;
                            }
                            for (int j4 = 3; j4 <= 8; ++j4) {
                                if (j4 == 4) {
                                    this.setBlockAndMetadata(world, i2, j4, k2, super.brickRedStairBlock, stairMeta);
                                }
                                else {
                                    this.setBlockAndMetadata(world, i2, j4, k2, super.brickStairBlock, stairMeta);
                                }
                            }
                            this.setBlockAndMetadata(world, i2, 9, k2, super.brickBlock, super.brickMeta);
                        }
                        this.setBlockAndMetadata(world, i2, 10, k2, super.brickRedBlock, super.brickRedMeta);
                        this.setBlockAndMetadata(world, i2, 11, k2, super.fenceBlock, super.fenceMeta);
                    }
                    else {
                        this.setBlockAndMetadata(world, i2, 4, k2, super.brickBlock, super.brickMeta);
                        this.setBlockAndMetadata(world, i2, 10, k2, super.brickBlock, super.brickMeta);
                    }
                }
            }
        }
        for (int i2 = -1; i2 <= 1; ++i2) {
            for (int k2 = -1; k2 <= 1; ++k2) {
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                if (i3 == 0 || k3 == 0) {
                    this.setBlockAndMetadata(world, i2, 0, k2, super.pillarBlock, super.pillarMeta);
                }
                else {
                    this.setBlockAndMetadata(world, i2, 0, k2, super.brickRedBlock, super.brickRedMeta);
                }
            }
        }
        if (this.enableDoor) {
            this.setBlockAndMetadata(world, 0, 0, -2, super.pillarBlock, super.pillarMeta);
            this.setBlockAndMetadata(world, 0, 1, -2, super.doorBlock, 1);
            this.setBlockAndMetadata(world, 0, 2, -2, super.doorBlock, 8);
        }
        this.setBlockAndMetadata(world, -1, 3, -1, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 1, 3, -1, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -1, 3, 1, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 1, 3, 1, Blocks.torch, 4);
        this.placeWeaponRack(world, -1, 2, 0, 5, this.getEasterlingWeaponItem(random));
        this.placeArmorStand(world, 1, 1, 0, 1, null);
        for (int j5 = 1; j5 <= 9; ++j5) {
            this.setBlockAndMetadata(world, 0, j5, 1, Blocks.ladder, 2);
        }
        this.setBlockAndMetadata(world, 0, 10, 1, Blocks.trapdoor, 9);
        this.setBlockAndMetadata(world, -1, 6, -1, super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.setBlockAndMetadata(world, 0, 6, -1, super.plankSlabBlock, super.plankSlabMeta | 0x8);
        for (final int j6 : new int[] { 5, 7 }) {
            this.setBlockAndMetadata(world, 0, j6, -1, super.bedBlock, 3);
            this.setBlockAndMetadata(world, -1, j6, -1, super.bedBlock, 11);
        }
        for (int j5 = 6; j5 <= 9; ++j5) {
            this.setBlockAndMetadata(world, 1, j5, -1, Blocks.ladder, 3);
        }
        this.placeChest(world, random, 1, 5, -1, 3, LOTRChestContents.EASTERLING_TOWER);
        this.setBlockAndMetadata(world, -1, 8, 0, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 1, 8, 0, Blocks.torch, 1);
        this.spawnItemFrame(world, -2, 7, 0, 1, this.getEasterlingFramedItem(random));
        this.spawnItemFrame(world, 2, 7, 0, 3, this.getEasterlingFramedItem(random));
        this.placeWallBanner(world, 0, 9, -2, super.bannerType, 2);
        this.setBlockAndMetadata(world, -3, 14, -3, super.roofSlabBlock, super.roofSlabMeta);
        this.setBlockAndMetadata(world, -2, 13, -3, super.roofStairBlock, 5);
        this.setBlockAndMetadata(world, -1, 13, -3, super.roofStairBlock, 2);
        this.setBlockAndMetadata(world, 0, 13, -3, super.roofSlabBlock, super.roofSlabMeta | 0x8);
        this.setBlockAndMetadata(world, 1, 13, -3, super.roofStairBlock, 2);
        this.setBlockAndMetadata(world, 2, 13, -3, super.roofStairBlock, 4);
        this.setBlockAndMetadata(world, 3, 14, -3, super.roofSlabBlock, super.roofSlabMeta);
        this.setBlockAndMetadata(world, 3, 13, -2, super.roofStairBlock, 6);
        this.setBlockAndMetadata(world, 3, 13, -1, super.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 3, 13, 0, super.roofSlabBlock, super.roofSlabMeta | 0x8);
        this.setBlockAndMetadata(world, 3, 13, 1, super.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 3, 13, 2, super.roofStairBlock, 7);
        this.setBlockAndMetadata(world, 3, 14, 3, super.roofSlabBlock, super.roofSlabMeta);
        this.setBlockAndMetadata(world, 2, 13, 3, super.roofStairBlock, 4);
        this.setBlockAndMetadata(world, 1, 13, 3, super.roofStairBlock, 3);
        this.setBlockAndMetadata(world, 0, 13, 3, super.roofSlabBlock, super.roofSlabMeta | 0x8);
        this.setBlockAndMetadata(world, -1, 13, 3, super.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -2, 13, 3, super.roofStairBlock, 5);
        this.setBlockAndMetadata(world, -3, 14, 3, super.roofSlabBlock, super.roofSlabMeta);
        this.setBlockAndMetadata(world, -3, 13, 2, super.roofStairBlock, 7);
        this.setBlockAndMetadata(world, -3, 13, 1, super.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -3, 13, 0, super.roofSlabBlock, super.roofSlabMeta | 0x8);
        this.setBlockAndMetadata(world, -3, 13, -1, super.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -3, 13, -2, super.roofStairBlock, 6);
        for (int i2 = -2; i2 <= 2; ++i2) {
            this.setBlockAndMetadata(world, i2, 14, -2, super.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i2, 14, 2, super.roofStairBlock, 3);
        }
        for (int k4 = -2; k4 <= 2; ++k4) {
            this.setBlockAndMetadata(world, -2, 14, k4, super.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 2, 14, k4, super.roofStairBlock, 0);
        }
        for (int i2 = -1; i2 <= 1; ++i2) {
            for (int k2 = -1; k2 <= 1; ++k2) {
                if (i2 != 0 || k2 != 0) {
                    this.setBlockAndMetadata(world, i2, 14, k2, super.roofSlabBlock, super.roofSlabMeta | 0x8);
                }
                if (i2 == 0 || k2 == 0) {
                    this.setBlockAndMetadata(world, i2, 15, k2, super.roofBlock, super.roofMeta);
                }
                else {
                    this.setBlockAndMetadata(world, i2, 15, k2, super.roofSlabBlock, super.roofSlabMeta);
                }
            }
        }
        this.setBlockAndMetadata(world, 0, 16, 0, super.roofWallBlock, super.roofWallMeta);
        this.setBlockAndMetadata(world, 0, 17, 0, super.roofWallBlock, super.roofWallMeta);
        this.setBlockAndMetadata(world, -2, 12, -1, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -1, 12, -2, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 1, 12, -2, Blocks.torch, 1);
        this.setBlockAndMetadata(world, 2, 12, -1, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -2, 12, 1, Blocks.torch, 4);
        this.setBlockAndMetadata(world, -1, 12, 2, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 1, 12, 2, Blocks.torch, 1);
        this.setBlockAndMetadata(world, 2, 12, 1, Blocks.torch, 4);
        if (this.frontLadder) {
            this.setBlockAndMetadata(world, 0, 11, -2, super.fenceGateBlock, 0);
            this.placeSideLadder(world, 0, 10, -3, 2);
        }
        if (this.backLadder) {
            this.setBlockAndMetadata(world, 0, 11, 2, super.fenceGateBlock, 2);
            this.placeSideLadder(world, 0, 10, 3, 3);
        }
        if (this.leftLadder) {
            this.setBlockAndMetadata(world, -2, 11, 0, super.fenceGateBlock, 3);
            this.placeSideLadder(world, -3, 10, 0, 5);
        }
        if (this.rightLadder) {
            this.setBlockAndMetadata(world, 2, 11, 0, super.fenceGateBlock, 1);
            this.placeSideLadder(world, 3, 10, 0, 4);
        }
        for (int soldiers = 1 + random.nextInt(3), l = 0; l < soldiers; ++l) {
            final LOTREntityEasterling soldier = (random.nextInt(3) == 0) ? new LOTREntityEasterlingArcher(world) : new LOTREntityEasterlingWarrior(world);
            soldier.spawnRidingHorse = false;
            this.spawnNPCAndSetHome(soldier, world, 0, 1, 0, 16);
        }
        final LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        respawner.setSpawnClasses(LOTREntityEasterlingWarrior.class, LOTREntityEasterlingArcher.class);
        respawner.setCheckRanges(16, -8, 8, 6);
        respawner.setSpawnRanges(3, -6, 6, 16);
        this.placeNPCRespawner(respawner, world, 0, 6, 0);
        return true;
    }
    
    private void placeSideLadder(final World world, final int i, final int j, final int k, final int meta) {
        for (int j2 = j; !this.isOpaque(world, i, j2, k) && this.getY(j2) >= 0; --j2) {
            this.setBlockAndMetadata(world, i, j2, k, Blocks.ladder, meta);
        }
    }
}
