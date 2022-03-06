// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityEasterling;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityEasterlingGoldWarrior;
import lotr.common.entity.npc.LOTREntityEasterlingWarrior;
import lotr.common.entity.npc.LOTREntityEasterlingArcher;
import lotr.common.entity.npc.LOTREntityEasterlingWarlord;
import lotr.common.entity.animal.LOTREntityHorse;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityEasterlingBlacksmith;
import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import lotr.common.LOTRMod;
import java.util.Random;

public class LOTRWorldGenEasterlingFortress extends LOTRWorldGenEasterlingStructureTown
{
    public LOTRWorldGenEasterlingFortress(final boolean flag) {
        super(flag);
    }
    
    @Override
    protected void setupRandomBlocks(final Random random) {
        super.setupRandomBlocks(random);
        super.bedBlock = LOTRMod.strawBed;
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 13);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -12; i2 <= 12; ++i2) {
                for (int k2 = -12; k2 <= 12; ++k2) {
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
        for (int i3 = -12; i3 <= 12; ++i3) {
            for (int k3 = -12; k3 <= 12; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                for (int j2 = 1; j2 <= 10; ++j2) {
                    this.setAir(world, i3, j2, k3);
                }
                if (i4 <= 9 && k4 <= 9) {
                    for (int j2 = 0; (j2 >= 0 || !this.isOpaque(world, i3, j2, k3)) && this.getY(j2) >= 0; --j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, Blocks.dirt, 0);
                        this.setGrassToDirt(world, i3, j2 - 1, k3);
                    }
                    final int randomGround = random.nextInt(3);
                    if (randomGround == 0) {
                        this.setBlockAndMetadata(world, i3, 0, k3, (Block)Blocks.grass, 0);
                    }
                    else if (randomGround == 1) {
                        this.setBlockAndMetadata(world, i3, 0, k3, Blocks.dirt, 1);
                    }
                    else if (randomGround == 2) {
                        this.setBlockAndMetadata(world, i3, 0, k3, LOTRMod.dirtPath, 0);
                    }
                    if (random.nextInt(3) == 0) {
                        this.setBlockAndMetadata(world, i3, 1, k3, LOTRMod.thatchFloor, 0);
                    }
                }
                if ((i4 == 12 || i4 == 9) && (k4 == 12 || k4 == 9)) {
                    for (int j2 = 8; (j2 >= 0 || !this.isOpaque(world, i3, j2, k3)) && this.getY(j2) >= 0; --j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, super.pillarBlock, super.pillarMeta);
                        this.setGrassToDirt(world, i3, j2 - 1, k3);
                    }
                }
                else if ((i4 == 3 && (k4 == 9 || k4 == 12)) || (k4 == 3 && (i4 == 9 || i4 == 12))) {
                    for (int j2 = 7; (j2 >= 0 || !this.isOpaque(world, i3, j2, k3)) && this.getY(j2) >= 0; --j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, super.pillarBlock, super.pillarMeta);
                        this.setGrassToDirt(world, i3, j2 - 1, k3);
                    }
                    if (i4 == 12 || k4 == 12) {
                        this.setBlockAndMetadata(world, i3, 8, k3, super.brickWallBlock, super.brickWallMeta);
                        this.setBlockAndMetadata(world, i3, 9, k3, Blocks.torch, 5);
                    }
                }
                else {
                    if (i4 >= 10 || k4 >= 10) {
                        for (int j2 = 4; (j2 >= 0 || !this.isOpaque(world, i3, j2, k3)) && this.getY(j2) >= 0; --j2) {
                            this.setBlockAndMetadata(world, i3, j2, k3, super.brickBlock, super.brickMeta);
                            this.setGrassToDirt(world, i3, j2 - 1, k3);
                        }
                        this.setBlockAndMetadata(world, i3, 5, k3, super.brickGoldBlock, super.brickGoldMeta);
                    }
                    if (i4 <= 11 && k4 <= 11 && (i4 >= 10 || k4 >= 10)) {
                        this.setBlockAndMetadata(world, i3, 5, k3, super.pillarBlock, super.pillarMeta);
                    }
                    if ((i4 <= 12 && k4 <= 12 && (i4 == 12 || k4 == 12)) || (i4 <= 9 && k4 <= 9 && (i4 == 9 || k4 == 9))) {
                        this.setBlockAndMetadata(world, i3, 6, k3, super.brickRedWallBlock, super.brickRedWallMeta);
                    }
                    if (i3 == -9 && k4 <= 8) {
                        this.setBlockAndMetadata(world, i3, 5, k3, super.brickStairBlock, 4);
                    }
                    if (i3 == 9 && k4 <= 8) {
                        this.setBlockAndMetadata(world, i3, 5, k3, super.brickStairBlock, 5);
                    }
                    if (k3 == -9 && i4 <= 8) {
                        this.setBlockAndMetadata(world, i3, 5, k3, super.brickStairBlock, 7);
                    }
                    if (k3 == 9 && i4 <= 8) {
                        this.setBlockAndMetadata(world, i3, 5, k3, super.brickStairBlock, 6);
                    }
                }
            }
        }
        for (int i3 = -2; i3 <= 2; ++i3) {
            for (int k3 = -13, j3 = 0; (j3 >= 0 || !this.isOpaque(world, i3, j3, k3)) && this.getY(j3) >= 0; --j3) {
                this.setBlockAndMetadata(world, i3, j3, k3, super.pillarBlock, super.pillarMeta);
                this.setGrassToDirt(world, i3, j3 - 1, k3);
            }
        }
        for (final int i5 : new int[] { -6, 0, 6 }) {
            if (i5 != 0) {
                this.setBlockAndMetadata(world, i5 - 1, 0, -12, super.pillarBlock, super.pillarMeta);
                this.setBlockAndMetadata(world, i5, 0, -12, super.pillarRedBlock, super.pillarRedMeta);
                this.setBlockAndMetadata(world, i5 + 1, 0, -12, super.pillarBlock, super.pillarMeta);
                for (int i6 = i5 - 1; i6 <= i5 + 1; ++i6) {
                    this.setBlockAndMetadata(world, i6, 1, -12, super.barsBlock, 0);
                    this.setBlockAndMetadata(world, i6, 2, -12, super.barsBlock, 0);
                }
                this.setBlockAndMetadata(world, i5 - 1, 3, -12, super.brickStairBlock, 4);
                this.setBlockAndMetadata(world, i5, 3, -12, super.barsBlock, 0);
                this.setBlockAndMetadata(world, i5 + 1, 3, -12, super.brickStairBlock, 5);
                this.setBlockAndMetadata(world, i5, 4, -12, super.brickStairBlock, 6);
                this.setBlockAndMetadata(world, i5, 0, -11, LOTRMod.hearth, 0);
                this.setBlockAndMetadata(world, i5, 1, -11, (Block)Blocks.fire, 0);
                this.setBlockAndMetadata(world, i5, 2, -11, super.brickStairBlock, 6);
            }
            this.setBlockAndMetadata(world, i5 - 1, 0, 12, super.pillarBlock, super.pillarMeta);
            this.setBlockAndMetadata(world, i5, 0, 12, super.pillarRedBlock, super.pillarRedMeta);
            this.setBlockAndMetadata(world, i5 + 1, 0, 12, super.pillarBlock, super.pillarMeta);
            for (int i6 = i5 - 1; i6 <= i5 + 1; ++i6) {
                this.setAir(world, i6, 1, 12);
                this.setAir(world, i6, 2, 12);
            }
            this.setBlockAndMetadata(world, i5 - 1, 3, 12, super.brickStairBlock, 4);
            this.setAir(world, i5, 3, 12);
            this.setBlockAndMetadata(world, i5 + 1, 3, 12, super.brickStairBlock, 5);
            this.setBlockAndMetadata(world, i5, 4, 12, super.brickStairBlock, 7);
        }
        for (final int k2 : new int[] { -6, 0, 6 }) {
            this.setBlockAndMetadata(world, -12, 0, k2 - 1, super.pillarBlock, super.pillarMeta);
            this.setBlockAndMetadata(world, -12, 0, k2, super.pillarRedBlock, super.pillarRedMeta);
            this.setBlockAndMetadata(world, -12, 0, k2 + 1, super.pillarBlock, super.pillarMeta);
            for (int k5 = k2 - 1; k5 <= k2 + 1; ++k5) {
                this.setAir(world, -12, 1, k5);
                this.setAir(world, -12, 2, k5);
            }
            this.setBlockAndMetadata(world, -12, 3, k2 - 1, super.brickStairBlock, 7);
            this.setAir(world, -12, 3, k2);
            this.setBlockAndMetadata(world, -12, 3, k2 + 1, super.brickStairBlock, 6);
            this.setBlockAndMetadata(world, -12, 4, k2, super.brickStairBlock, 5);
            this.setBlockAndMetadata(world, 12, 0, k2 - 1, super.pillarBlock, super.pillarMeta);
            this.setBlockAndMetadata(world, 12, 0, k2, super.pillarRedBlock, super.pillarRedMeta);
            this.setBlockAndMetadata(world, 12, 0, k2 + 1, super.pillarBlock, super.pillarMeta);
            for (int k5 = k2 - 1; k5 <= k2 + 1; ++k5) {
                this.setAir(world, 12, 1, k5);
                this.setAir(world, 12, 2, k5);
            }
            this.setBlockAndMetadata(world, 12, 3, k2 - 1, super.brickStairBlock, 7);
            this.setAir(world, 12, 3, k2);
            this.setBlockAndMetadata(world, 12, 3, k2 + 1, super.brickStairBlock, 6);
            this.setBlockAndMetadata(world, 12, 4, k2, super.brickStairBlock, 4);
        }
        for (int i3 = -1; i3 <= 1; ++i3) {
            for (int j4 = 1; j4 <= 3; ++j4) {
                this.setAir(world, i3, j4, -12);
                this.setBlockAndMetadata(world, i3, j4, -11, super.gateBlock, 2);
                this.setAir(world, i3, j4, -10);
            }
        }
        this.setBlockAndMetadata(world, -1, 4, -12, super.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 0, 4, -12, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 1, 4, -12, super.brickStairBlock, 5);
        for (int i3 = -1; i3 <= 1; ++i3) {
            this.setBlockAndMetadata(world, i3, 6, -12, super.brickGoldBlock, super.brickGoldMeta);
        }
        this.setBlockAndMetadata(world, -2, 6, -12, super.brickCarvedBlock, super.brickCarvedMeta);
        this.setBlockAndMetadata(world, 2, 6, -12, super.brickCarvedBlock, super.brickCarvedMeta);
        this.setBlockAndMetadata(world, -2, 7, -12, super.brickStairBlock, 1);
        this.setBlockAndMetadata(world, -1, 7, -12, super.brickStairBlock, 0);
        this.setBlockAndMetadata(world, 0, 7, -12, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, 0, 8, -12, super.brickSlabBlock, super.brickSlabMeta);
        this.setBlockAndMetadata(world, 1, 7, -12, super.brickStairBlock, 1);
        this.setBlockAndMetadata(world, 2, 7, -12, super.brickStairBlock, 0);
        this.setBlockAndMetadata(world, -1, 4, -10, super.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 0, 4, -10, super.brickStairBlock, 7);
        this.setBlockAndMetadata(world, 1, 4, -10, super.brickStairBlock, 5);
        for (final int i5 : new int[] { -3, 3 }) {
            this.placeWallBanner(world, i5, 4, -12, super.bannerType, 2);
            this.placeWallBanner(world, i5, 4, 12, super.bannerType, 0);
        }
        for (final int k2 : new int[] { -3, 3 }) {
            this.placeWallBanner(world, -12, 4, k2, super.bannerType, 3);
            this.placeWallBanner(world, 12, 4, k2, super.bannerType, 1);
        }
        this.placeWallBanner(world, 0, 6, -12, super.bannerType, 2);
        for (final int i5 : new int[] { -12, 9 }) {
            for (final int k6 : new int[] { -12, 9 }) {
                this.setBlockAndMetadata(world, i5 + 1, 8, k6, super.brickStairBlock, 0);
                this.setBlockAndMetadata(world, i5 + 2, 8, k6, super.brickStairBlock, 1);
                this.setBlockAndMetadata(world, i5 + 1, 8, k6 + 3, super.brickStairBlock, 0);
                this.setBlockAndMetadata(world, i5 + 2, 8, k6 + 3, super.brickStairBlock, 1);
                this.setBlockAndMetadata(world, i5, 8, k6 + 1, super.brickStairBlock, 3);
                this.setBlockAndMetadata(world, i5, 8, k6 + 2, super.brickStairBlock, 2);
                this.setBlockAndMetadata(world, i5 + 3, 8, k6 + 1, super.brickStairBlock, 3);
                this.setBlockAndMetadata(world, i5 + 3, 8, k6 + 2, super.brickStairBlock, 2);
                this.setBlockAndMetadata(world, i5, 9, k6, super.brickWallBlock, super.brickWallMeta);
                this.setBlockAndMetadata(world, i5 + 3, 9, k6, super.brickWallBlock, super.brickWallMeta);
                this.setBlockAndMetadata(world, i5, 9, k6 + 3, super.brickWallBlock, super.brickWallMeta);
                this.setBlockAndMetadata(world, i5 + 3, 9, k6 + 3, super.brickWallBlock, super.brickWallMeta);
                for (int i7 = i5 + 1; i7 <= i5 + 2; ++i7) {
                    for (int k7 = k6 + 1; k7 <= k6 + 2; ++k7) {
                        this.setBlockAndMetadata(world, i7, 10, k7, super.roofBlock, super.roofMeta);
                    }
                }
                for (int i7 = i5; i7 <= i5 + 3; ++i7) {
                    this.setBlockAndMetadata(world, i7, 10, k6, super.roofStairBlock, 2);
                    this.setBlockAndMetadata(world, i7, 10, k6 + 3, super.roofStairBlock, 3);
                }
                for (int k8 = k6 + 1; k8 <= k6 + 2; ++k8) {
                    this.setBlockAndMetadata(world, i5, 10, k8, super.roofStairBlock, 1);
                    this.setBlockAndMetadata(world, i5 + 3, 10, k8, super.roofStairBlock, 0);
                }
                if (k6 == -12) {
                    this.setBlockAndMetadata(world, i5 + 1, 6, k6, super.brickStairBlock, 0);
                    this.setBlockAndMetadata(world, i5 + 2, 6, k6, super.brickStairBlock, 1);
                }
                if (k6 == 9) {
                    this.setBlockAndMetadata(world, i5 + 1, 6, k6 + 3, super.brickStairBlock, 0);
                    this.setBlockAndMetadata(world, i5 + 2, 6, k6 + 3, super.brickStairBlock, 1);
                }
                if (i5 == -12) {
                    this.setBlockAndMetadata(world, i5, 6, k6 + 1, super.brickStairBlock, 3);
                    this.setBlockAndMetadata(world, i5, 6, k6 + 2, super.brickStairBlock, 2);
                }
                if (i5 == 9) {
                    this.setBlockAndMetadata(world, i5 + 3, 6, k6 + 1, super.brickStairBlock, 3);
                    this.setBlockAndMetadata(world, i5 + 3, 6, k6 + 2, super.brickStairBlock, 2);
                }
            }
        }
        this.setBlockAndMetadata(world, -9, 7, -10, Blocks.torch, 4);
        this.setBlockAndMetadata(world, -10, 7, -9, Blocks.torch, 1);
        this.setBlockAndMetadata(world, 9, 7, -10, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 10, 7, -9, Blocks.torch, 2);
        this.setBlockAndMetadata(world, -9, 7, 10, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -10, 7, 9, Blocks.torch, 1);
        this.setBlockAndMetadata(world, 9, 7, 10, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 10, 7, 9, Blocks.torch, 2);
        for (int i3 = -1; i3 <= 1; ++i3) {
            for (int k3 = -12; k3 <= -4; ++k3) {
                if (i3 == 0) {
                    this.setBlockAndMetadata(world, i3, 0, k3, super.pillarRedBlock, super.pillarRedMeta);
                }
                else {
                    this.setBlockAndMetadata(world, i3, 0, k3, super.pillarBlock, super.pillarMeta);
                }
            }
        }
        for (int k9 = -1; k9 <= 1; ++k9) {
            for (int i8 = -9; i8 <= 9; ++i8) {
                if (i8 <= -4 || i8 >= 4) {
                    if (k9 == 0) {
                        this.setBlockAndMetadata(world, i8, 0, k9, super.pillarRedBlock, super.pillarRedMeta);
                    }
                    else {
                        this.setBlockAndMetadata(world, i8, 0, k9, super.pillarBlock, super.pillarMeta);
                    }
                }
            }
        }
        for (int i3 = -3; i3 <= 3; ++i3) {
            for (int k3 = -3; k3 <= 3; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                if (i4 == 3 && k4 == 3) {
                    for (int j2 = 0; j2 <= 5; ++j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, super.pillarBlock, super.pillarMeta);
                    }
                }
                else if (i4 == 3 || k4 == 3) {
                    for (int j2 = 0; j2 <= 5; ++j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, super.brickBlock, super.brickMeta);
                    }
                }
                else {
                    this.setBlockAndMetadata(world, i3, 0, k3, super.plankBlock, super.plankMeta);
                }
                if (i4 <= 2 && k4 <= 2) {
                    if (i4 == 2 && k4 == 2) {
                        for (int j2 = 6; j2 <= 11; ++j2) {
                            this.setBlockAndMetadata(world, i3, j2, k3, super.pillarBlock, super.pillarMeta);
                        }
                    }
                    else if (i4 == 2 || k4 == 2) {
                        for (int j2 = 6; j2 <= 11; ++j2) {
                            if (j2 == 11) {
                                this.setBlockAndMetadata(world, i3, j2, k3, super.brickGoldBlock, super.brickGoldMeta);
                            }
                            else {
                                this.setBlockAndMetadata(world, i3, j2, k3, super.brickBlock, super.brickMeta);
                            }
                        }
                    }
                    else {
                        this.setBlockAndMetadata(world, i3, 6, k3, super.pillarBlock, super.pillarMeta);
                    }
                }
            }
        }
        for (int i3 = -3; i3 <= 3; ++i3) {
            this.setBlockAndMetadata(world, i3, 6, -3, super.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i3, 6, 3, super.roofStairBlock, 3);
        }
        for (int k9 = -2; k9 <= 2; ++k9) {
            this.setBlockAndMetadata(world, -3, 6, k9, super.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 3, 6, k9, super.roofStairBlock, 0);
        }
        this.setBlockAndMetadata(world, -4, 6, -4, super.roofSlabBlock, super.roofSlabMeta);
        this.setBlockAndMetadata(world, -3, 5, -4, super.roofStairBlock, 5);
        this.setBlockAndMetadata(world, -2, 5, -4, super.roofStairBlock, 2);
        this.setBlockAndMetadata(world, -1, 5, -4, super.roofStairBlock, 4);
        this.setBlockAndMetadata(world, 0, 6, -4, super.roofSlabBlock, super.roofSlabMeta);
        this.setBlockAndMetadata(world, 1, 5, -4, super.roofStairBlock, 5);
        this.setBlockAndMetadata(world, 2, 5, -4, super.roofStairBlock, 2);
        this.setBlockAndMetadata(world, 3, 5, -4, super.roofStairBlock, 4);
        this.setBlockAndMetadata(world, 4, 6, -4, super.roofSlabBlock, super.roofSlabMeta);
        this.setBlockAndMetadata(world, 4, 5, -3, super.roofStairBlock, 6);
        this.setBlockAndMetadata(world, 4, 5, -2, super.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 4, 5, -1, super.roofStairBlock, 7);
        this.setBlockAndMetadata(world, 4, 6, 0, super.roofSlabBlock, super.roofSlabMeta);
        this.setBlockAndMetadata(world, 4, 5, 1, super.roofStairBlock, 6);
        this.setBlockAndMetadata(world, 4, 5, 2, super.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 4, 5, 3, super.roofStairBlock, 7);
        this.setBlockAndMetadata(world, 4, 6, 4, super.roofSlabBlock, super.roofSlabMeta);
        this.setBlockAndMetadata(world, 3, 5, 4, super.roofStairBlock, 4);
        this.setBlockAndMetadata(world, 2, 5, 4, super.roofStairBlock, 3);
        this.setBlockAndMetadata(world, 1, 5, 4, super.roofStairBlock, 5);
        this.setBlockAndMetadata(world, 0, 6, 4, super.roofSlabBlock, super.roofSlabMeta);
        this.setBlockAndMetadata(world, -1, 5, 4, super.roofStairBlock, 4);
        this.setBlockAndMetadata(world, -2, 5, 4, super.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -3, 5, 4, super.roofStairBlock, 5);
        this.setBlockAndMetadata(world, -4, 6, 4, super.roofSlabBlock, super.roofSlabMeta);
        this.setBlockAndMetadata(world, -4, 5, 3, super.roofStairBlock, 7);
        this.setBlockAndMetadata(world, -4, 5, 2, super.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -4, 5, 1, super.roofStairBlock, 6);
        this.setBlockAndMetadata(world, -4, 6, 0, super.roofSlabBlock, super.roofSlabMeta);
        this.setBlockAndMetadata(world, -4, 5, -1, super.roofStairBlock, 7);
        this.setBlockAndMetadata(world, -4, 5, -2, super.roofStairBlock, 1);
        this.setBlockAndMetadata(world, -4, 5, -3, super.roofStairBlock, 6);
        for (int i3 = -2; i3 <= 2; ++i3) {
            this.setBlockAndMetadata(world, i3, 12, -2, super.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i3, 12, 2, super.roofStairBlock, 3);
        }
        for (int k9 = -1; k9 <= 1; ++k9) {
            this.setBlockAndMetadata(world, -2, 12, k9, super.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 2, 12, k9, super.roofStairBlock, 0);
        }
        this.setBlockAndMetadata(world, -3, 12, -3, super.roofSlabBlock, super.roofSlabMeta);
        this.setBlockAndMetadata(world, -2, 11, -3, super.roofStairBlock, 5);
        this.setBlockAndMetadata(world, -1, 11, -3, super.roofStairBlock, 4);
        this.setBlockAndMetadata(world, 0, 12, -3, super.roofSlabBlock, super.roofSlabMeta);
        this.setBlockAndMetadata(world, 1, 11, -3, super.roofStairBlock, 5);
        this.setBlockAndMetadata(world, 2, 11, -3, super.roofStairBlock, 4);
        this.setBlockAndMetadata(world, 3, 12, -3, super.roofSlabBlock, super.roofSlabMeta);
        this.setBlockAndMetadata(world, 3, 11, -2, super.roofStairBlock, 6);
        this.setBlockAndMetadata(world, 3, 11, -1, super.roofStairBlock, 7);
        this.setBlockAndMetadata(world, 3, 12, 0, super.roofSlabBlock, super.roofSlabMeta);
        this.setBlockAndMetadata(world, 3, 11, 1, super.roofStairBlock, 6);
        this.setBlockAndMetadata(world, 3, 11, 2, super.roofStairBlock, 7);
        this.setBlockAndMetadata(world, 3, 12, 3, super.roofSlabBlock, super.roofSlabMeta);
        this.setBlockAndMetadata(world, 2, 11, 3, super.roofStairBlock, 4);
        this.setBlockAndMetadata(world, 1, 11, 3, super.roofStairBlock, 5);
        this.setBlockAndMetadata(world, 0, 12, 3, super.roofSlabBlock, super.roofSlabMeta);
        this.setBlockAndMetadata(world, -1, 11, 3, super.roofStairBlock, 4);
        this.setBlockAndMetadata(world, -2, 11, 3, super.roofStairBlock, 5);
        this.setBlockAndMetadata(world, -3, 12, 3, super.roofSlabBlock, super.roofSlabMeta);
        this.setBlockAndMetadata(world, -3, 11, 2, super.roofStairBlock, 7);
        this.setBlockAndMetadata(world, -3, 11, 1, super.roofStairBlock, 6);
        this.setBlockAndMetadata(world, -3, 12, 0, super.roofSlabBlock, super.roofSlabMeta);
        this.setBlockAndMetadata(world, -3, 11, -1, super.roofStairBlock, 7);
        this.setBlockAndMetadata(world, -3, 11, -2, super.roofStairBlock, 6);
        for (int i3 = -1; i3 <= 1; ++i3) {
            this.setBlockAndMetadata(world, i3, 12, -1, super.roofStairBlock, 7);
            this.setBlockAndMetadata(world, i3, 12, 1, super.roofStairBlock, 6);
        }
        this.setBlockAndMetadata(world, -1, 12, 0, super.roofStairBlock, 4);
        this.setBlockAndMetadata(world, 1, 12, 0, super.roofStairBlock, 5);
        this.setBlockAndMetadata(world, -1, 13, -1, super.roofBlock, super.roofMeta);
        this.setBlockAndMetadata(world, 0, 13, -1, super.roofStairBlock, 2);
        this.setBlockAndMetadata(world, 1, 13, -1, super.roofBlock, super.roofMeta);
        this.setBlockAndMetadata(world, 1, 13, 0, super.roofStairBlock, 0);
        this.setBlockAndMetadata(world, 1, 13, 1, super.roofBlock, super.roofMeta);
        this.setBlockAndMetadata(world, 0, 13, 1, super.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -1, 13, 1, super.roofBlock, super.roofMeta);
        this.setBlockAndMetadata(world, -1, 13, 0, super.roofStairBlock, 1);
        this.setBlockAndMetadata(world, 0, 13, 0, super.roofBlock, super.roofMeta);
        this.setBlockAndMetadata(world, 0, 14, 0, super.roofBlock, super.roofMeta);
        this.setBlockAndMetadata(world, 0, 15, 0, super.roofWallBlock, super.roofWallMeta);
        this.setBlockAndMetadata(world, 0, 16, 0, super.roofWallBlock, super.roofWallMeta);
        this.setBlockAndMetadata(world, -3, 4, -4, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 3, 4, -4, Blocks.torch, 4);
        this.setBlockAndMetadata(world, -3, 4, 4, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 3, 4, 4, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -4, 4, -3, Blocks.torch, 1);
        this.setBlockAndMetadata(world, -4, 4, 3, Blocks.torch, 1);
        this.setBlockAndMetadata(world, 4, 4, -3, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 4, 4, 3, Blocks.torch, 2);
        this.setBlockAndMetadata(world, -2, 10, -3, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 2, 10, -3, Blocks.torch, 4);
        this.setBlockAndMetadata(world, -2, 10, 3, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 2, 10, 3, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -3, 10, -2, Blocks.torch, 1);
        this.setBlockAndMetadata(world, -3, 10, 2, Blocks.torch, 1);
        this.setBlockAndMetadata(world, 3, 10, -2, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 3, 10, 2, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 0, 1, -3, super.doorBlock, 1);
        this.setBlockAndMetadata(world, 0, 2, -3, super.doorBlock, 8);
        this.setBlockAndMetadata(world, -3, 1, 0, super.doorBlock, 2);
        this.setBlockAndMetadata(world, -3, 2, 0, super.doorBlock, 8);
        this.setBlockAndMetadata(world, 3, 1, 0, super.doorBlock, 0);
        this.setBlockAndMetadata(world, 3, 2, 0, super.doorBlock, 8);
        for (int k9 = -3; k9 <= 0; ++k9) {
            this.setBlockAndMetadata(world, 0, 0, k9, super.pillarRedBlock, super.pillarRedMeta);
        }
        for (int i3 = -3; i3 <= 3; ++i3) {
            this.setBlockAndMetadata(world, i3, 0, 0, super.pillarRedBlock, super.pillarRedMeta);
        }
        for (int i3 = -2; i3 <= 2; ++i3) {
            this.setBlockAndMetadata(world, i3, 5, -2, super.brickStairBlock, 7);
            this.setBlockAndMetadata(world, i3, 5, 2, super.brickStairBlock, 6);
        }
        for (int k9 = -2; k9 <= 2; ++k9) {
            this.setBlockAndMetadata(world, -2, 5, k9, super.brickStairBlock, 4);
            this.setBlockAndMetadata(world, 2, 5, k9, super.brickStairBlock, 5);
        }
        for (int j5 = 1; j5 <= 6; ++j5) {
            this.setBlockAndMetadata(world, 0, j5, 1, super.pillarBlock, super.pillarMeta);
            this.setBlockAndMetadata(world, 0, j5, 0, Blocks.ladder, 2);
            if (j5 <= 5) {
                this.setBlockAndMetadata(world, -1, j5, 1, Blocks.ladder, 5);
                this.setBlockAndMetadata(world, 1, j5, 1, Blocks.ladder, 4);
            }
        }
        for (int j5 = 1; j5 <= 5; ++j5) {
            this.setBlockAndMetadata(world, 0, j5, 2, super.brickBlock, super.brickMeta);
        }
        this.setBlockAndMetadata(world, -2, 2, 2, super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.setBlockAndMetadata(world, -1, 2, 2, super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.setBlockAndMetadata(world, 1, 2, 2, super.plankSlabBlock, super.plankSlabMeta | 0x8);
        this.setBlockAndMetadata(world, 2, 2, 2, super.plankSlabBlock, super.plankSlabMeta | 0x8);
        for (final int j6 : new int[] { 1, 3 }) {
            this.setBlockAndMetadata(world, -2, j6, 2, super.bedBlock, 1);
            this.setBlockAndMetadata(world, -1, j6, 2, super.bedBlock, 9);
            this.setBlockAndMetadata(world, 2, j6, 2, super.bedBlock, 3);
            this.setBlockAndMetadata(world, 1, j6, 2, super.bedBlock, 11);
        }
        this.setBlockAndMetadata(world, -2, 4, -2, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 2, 4, -2, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -2, 4, 2, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 2, 4, 2, Blocks.torch, 4);
        this.placeArmorStand(world, -2, 1, -2, 2, null);
        this.placeArmorStand(world, 2, 1, -2, 2, null);
        this.placeWeaponRack(world, 0, 3, -2, 4, this.getEasterlingWeaponItem(random));
        this.placeWeaponRack(world, -2, 3, 0, 5, this.getEasterlingWeaponItem(random));
        this.placeWeaponRack(world, 2, 3, 0, 7, this.getEasterlingWeaponItem(random));
        for (int j5 = 8; j5 <= 9; ++j5) {
            this.setBlockAndMetadata(world, 0, j5, -2, super.barsBlock, 0);
            this.setBlockAndMetadata(world, 0, j5, 2, super.barsBlock, 0);
            this.setBlockAndMetadata(world, -2, j5, 0, super.barsBlock, 0);
            this.setBlockAndMetadata(world, 2, j5, 0, super.barsBlock, 0);
        }
        this.setBlockAndMetadata(world, -1, 11, -1, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 1, 11, -1, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -1, 11, 1, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 1, 11, 1, Blocks.torch, 4);
        for (int i3 = -9; i3 <= -6; ++i3) {
            for (int k3 = -9; k3 <= -6; ++k3) {
                this.setBlockAndMetadata(world, i3, 0, k3, super.pillarBlock, super.pillarMeta);
            }
        }
        for (int j5 = 1; j5 <= 4; ++j5) {
            this.setBlockAndMetadata(world, -6, j5, -6, super.woodBeamBlock, super.woodBeamMeta);
        }
        this.setBlockAndMetadata(world, -6, 5, -6, super.plankSlabBlock, super.plankSlabMeta);
        this.setBlockAndMetadata(world, -6, 2, -5, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -5, 2, -6, Blocks.torch, 2);
        for (int j5 = 1; j5 <= 3; ++j5) {
            this.setBlockAndMetadata(world, -9, j5, -6, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, -6, j5, -9, super.fenceBlock, super.fenceMeta);
        }
        this.setBlockAndMetadata(world, -7, 3, -6, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, -6, 3, -7, super.fenceBlock, super.fenceMeta);
        for (int i3 = -9; i3 <= -7; ++i3) {
            for (int k3 = -9; k3 <= -7; ++k3) {
                if (i3 >= -8 || k3 >= -8) {
                    this.setBlockAndMetadata(world, i3, 4, k3, super.plankBlock, super.plankMeta);
                }
            }
        }
        for (int i3 = -9; i3 <= -7; ++i3) {
            this.setBlockAndMetadata(world, i3, 4, -6, super.plankStairBlock, 3);
        }
        for (int k9 = -9; k9 <= -7; ++k9) {
            this.setBlockAndMetadata(world, -6, 4, k9, super.plankStairBlock, 0);
        }
        this.setBlockAndMetadata(world, -8, 1, -10, Blocks.stonebrick, 0);
        this.setBlockAndMetadata(world, -8, 2, -10, LOTRMod.alloyForge, 3);
        this.setBlockAndMetadata(world, -10, 1, -8, Blocks.stonebrick, 0);
        this.setBlockAndMetadata(world, -10, 2, -8, Blocks.furnace, 4);
        this.setBlockAndMetadata(world, -7, 1, -6, Blocks.anvil, 1);
        this.setBlockAndMetadata(world, -6, 1, -7, (Block)Blocks.cauldron, 3);
        final LOTREntityEasterling blacksmith = new LOTREntityEasterlingBlacksmith(world);
        this.spawnNPCAndSetHome(blacksmith, world, -8, 1, -8, 8);
        for (int j4 = 1; j4 <= 4; ++j4) {
            this.setBlockAndMetadata(world, 6, j4, -6, super.woodBeamBlock, super.woodBeamMeta);
        }
        this.setBlockAndMetadata(world, 6, 5, -6, super.plankSlabBlock, super.plankSlabMeta);
        this.setBlockAndMetadata(world, 6, 2, -5, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 5, 2, -6, Blocks.torch, 1);
        for (int j4 = 1; j4 <= 3; ++j4) {
            this.setBlockAndMetadata(world, 9, j4, -6, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, 6, j4, -9, super.fenceBlock, super.fenceMeta);
        }
        this.setBlockAndMetadata(world, 7, 3, -6, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, 6, 3, -7, super.fenceBlock, super.fenceMeta);
        for (int i8 = 7; i8 <= 9; ++i8) {
            for (int k10 = -9; k10 <= -7; ++k10) {
                if (i8 <= 8 || k10 >= -8) {
                    this.setBlockAndMetadata(world, i8, 4, k10, super.plankBlock, super.plankMeta);
                }
            }
        }
        for (int i8 = 7; i8 <= 9; ++i8) {
            this.setBlockAndMetadata(world, i8, 4, -6, super.plankStairBlock, 3);
        }
        for (int k3 = -9; k3 <= -7; ++k3) {
            this.setBlockAndMetadata(world, 6, 4, k3, super.plankStairBlock, 1);
        }
        for (int i8 = 7; i8 <= 8; ++i8) {
            this.placeChest(world, random, i8, 1, -9, 3, LOTRChestContents.EASTERLING_TOWER);
        }
        this.setBlockAndMetadata(world, 9, 1, -8, super.tableBlock, 0);
        this.setBlockAndMetadata(world, 9, 1, -7, Blocks.crafting_table, 0);
        this.setBlockAndMetadata(world, 8, 1, -5, LOTRMod.commandTable, 0);
        this.placeWeaponRack(world, -8, 2, -3, 5, new ItemStack(LOTRMod.rhunBow));
        this.placeWeaponRack(world, -8, 2, 3, 5, new ItemStack(LOTRMod.rhunBow));
        this.setBlockAndMetadata(world, -9, 1, -1, super.plankStairBlock, 6);
        this.setBlockAndMetadata(world, -9, 1, 0, Blocks.wool, 0);
        this.setBlockAndMetadata(world, -9, 1, 1, super.plankStairBlock, 7);
        this.setBlockAndMetadata(world, -9, 2, -1, Blocks.wool, 0);
        this.setBlockAndMetadata(world, -9, 2, 0, Blocks.wool, 14);
        this.setBlockAndMetadata(world, -9, 2, 1, Blocks.wool, 0);
        this.setBlockAndMetadata(world, -9, 3, -1, super.plankStairBlock, 2);
        this.setBlockAndMetadata(world, -9, 3, 0, Blocks.wool, 0);
        this.setBlockAndMetadata(world, -9, 3, 1, super.plankStairBlock, 3);
        this.setBlockAndMetadata(world, -8, 2, 0, Blocks.wooden_button, 2);
        this.placeWeaponRack(world, 8, 2, -3, 7, new ItemStack(LOTRMod.swordRhun));
        this.placeWeaponRack(world, 8, 2, 3, 7, new ItemStack(LOTRMod.swordRhun));
        this.setBlockAndMetadata(world, 8, 1, 0, super.fenceBlock, super.fenceMeta);
        this.setBlockAndMetadata(world, 8, 2, 0, Blocks.hay_block, 0);
        this.setBlockAndMetadata(world, 8, 3, 0, Blocks.pumpkin, 3);
        this.setBlockAndMetadata(world, 8, 2, -1, Blocks.lever, 4);
        this.setBlockAndMetadata(world, 8, 2, 1, Blocks.lever, 3);
        for (int j4 = 0; j4 <= 7; ++j4) {
            this.setBlockAndMetadata(world, -1, j4, 9, super.pillarBlock, super.pillarMeta);
            this.setBlockAndMetadata(world, 1, j4, 9, super.pillarBlock, super.pillarMeta);
        }
        for (int j4 = 1; j4 <= 4; ++j4) {
            this.setBlockAndMetadata(world, 0, j4, 9, Blocks.ladder, 2);
        }
        this.setAir(world, 0, 5, 9);
        this.setAir(world, 0, 6, 9);
        this.setBlockAndMetadata(world, 0, 5, 10, super.brickStairBlock, 2);
        for (final int i9 : new int[] { -6, 6 }) {
            for (int l = 0; l <= 4; ++l) {
                final int j7 = 1 + l;
                final int k6 = 6 + l;
                for (int i7 = i9 - 1; i7 <= i9 + 1; ++i7) {
                    this.setBlockAndMetadata(world, i7, j7, k6, super.brickStairBlock, 2);
                    for (int j8 = j7 - 1; j8 >= 1 && !this.isOpaque(world, i7, j8, k6); --j8) {
                        this.setBlockAndMetadata(world, i7, j8, k6, super.brickBlock, super.brickMeta);
                        this.setGrassToDirt(world, i7, j8 - 1, k6);
                    }
                    if (k6 == 9) {
                        this.setAir(world, i7, 5, k6);
                        this.setAir(world, i7, 6, k6);
                    }
                }
            }
        }
        for (int i8 = -4; i8 <= 4; ++i8) {
            if (Math.abs(i8) >= 2) {
                for (int k10 = 8; k10 <= 9; ++k10) {
                    if (!this.isOpaque(world, i8, 1, k10)) {
                        int h = 1;
                        if (random.nextInt(4) == 0) {
                            ++h;
                        }
                        this.setGrassToDirt(world, i8, 0, k10);
                        for (int j2 = 1; j2 < 1 + h; ++j2) {
                            this.setBlockAndMetadata(world, i8, j2, k10, Blocks.hay_block, 0);
                        }
                    }
                }
            }
        }
        this.setBlockAndMetadata(world, 4, 1, 7, (Block)Blocks.cauldron, 3);
        for (final int i9 : new int[] { -2, 2 }) {
            this.setBlockAndMetadata(world, i9, 1, 6, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, i9, 2, 6, super.fenceBlock, super.fenceMeta);
            final LOTREntityHorse horse = new LOTREntityHorse(world);
            this.spawnNPCAndSetHome((EntityCreature)horse, world, i9, 1, 5, 0);
            horse.setHorseType(0);
            horse.saddleMountForWorldGen();
            horse.detachHome();
            this.leashEntityTo((EntityCreature)horse, world, i9, 2, 6);
        }
        final LOTREntityEasterling captain = new LOTREntityEasterlingWarlord(world);
        captain.spawnRidingHorse = false;
        this.spawnNPCAndSetHome(captain, world, 0, 1, 0, 12);
        for (int soldiers = 4 + random.nextInt(4), m = 0; m < soldiers; ++m) {
            LOTREntityEasterling soldier = (random.nextInt(3) == 0) ? new LOTREntityEasterlingArcher(world) : new LOTREntityEasterlingWarrior(world);
            if (random.nextInt(3) == 0) {
                soldier = new LOTREntityEasterlingGoldWarrior(world);
            }
            soldier.spawnRidingHorse = false;
            this.spawnNPCAndSetHome(soldier, world, 0, 1, 0, 16);
        }
        final LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
        respawner.setSpawnClasses(LOTREntityEasterlingWarrior.class, LOTREntityEasterlingArcher.class);
        respawner.setCheckRanges(20, -8, 12, 10);
        respawner.setSpawnRanges(10, 0, 8, 16);
        this.placeNPCRespawner(respawner, world, 0, 0, 0);
        final LOTREntityNPCRespawner respawnerGold = new LOTREntityNPCRespawner(world);
        respawnerGold.setSpawnClass(LOTREntityEasterlingGoldWarrior.class);
        respawnerGold.setCheckRanges(20, -8, 12, 5);
        respawnerGold.setSpawnRanges(10, 0, 8, 16);
        this.placeNPCRespawner(respawnerGold, world, 0, 0, 0);
        return true;
    }
}
