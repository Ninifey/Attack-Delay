// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.npc.LOTREntityEasterlingWarrior;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import lotr.common.entity.npc.LOTRNames;
import java.util.Random;

public class LOTRWorldGenEasterlingGatehouse extends LOTRWorldGenEasterlingStructureTown
{
    private String[] signText;
    private boolean enableSigns;
    
    public LOTRWorldGenEasterlingGatehouse(final boolean flag) {
        super(flag);
        this.enableSigns = true;
        this.signText = LOTRNames.getRhunVillageName(new Random());
    }
    
    public LOTRWorldGenEasterlingGatehouse setSignText(final String[] s) {
        this.signText = s;
        return this;
    }
    
    public LOTRWorldGenEasterlingGatehouse disableSigns() {
        this.enableSigns = false;
        return this;
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 4);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            for (int i2 = -7; i2 <= 7; ++i2) {
                for (int k2 = -4; k2 <= 4; ++k2) {
                    final int j2 = this.getTopBlock(world, i2, k2) - 1;
                    if (!this.isSurface(world, i2, j2, k2)) {
                        return false;
                    }
                }
            }
        }
        for (int i2 = -7; i2 <= 7; ++i2) {
            for (int k2 = -3; k2 <= 3; ++k2) {
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                for (int j3 = 1; j3 <= 12; ++j3) {
                    this.setAir(world, i2, j3, k2);
                }
                if ((i3 <= 3 && k3 <= 3) || (i3 <= 6 && k3 <= 2) || (i3 <= 7 && k3 <= 1)) {
                    for (int j3 = 0; (j3 >= 0 || !this.isOpaque(world, i2, j3, k2)) && this.getY(j3) >= 0; --j3) {
                        this.setBlockAndMetadata(world, i2, j3, k2, super.brickBlock, super.brickMeta);
                        this.setGrassToDirt(world, i2, j3 - 1, k2);
                    }
                }
                if (i3 == 3 && k3 <= 2) {
                    if (k3 == 0) {
                        for (int j3 = 1; j3 <= 6; ++j3) {
                            this.setBlockAndMetadata(world, i2, j3, k2, super.pillarBlock, super.pillarMeta);
                        }
                    }
                    else {
                        for (int j3 = 1; j3 <= 6; ++j3) {
                            this.setBlockAndMetadata(world, i2, j3, k2, super.brickBlock, super.brickMeta);
                        }
                    }
                }
            }
        }
        for (int i2 = -2; i2 <= 2; ++i2) {
            this.setBlockAndMetadata(world, i2, 0, 0, super.pillarBlock, super.pillarMeta);
            this.setBlockAndMetadata(world, i2, 0, -2, super.brickStairBlock, 3);
            this.setBlockAndMetadata(world, i2, 0, 2, super.brickStairBlock, 2);
        }
        for (int k4 = -1; k4 <= 1; ++k4) {
            this.setBlockAndMetadata(world, -2, 6, k4, super.brickStairBlock, 4);
            this.setBlockAndMetadata(world, 2, 6, k4, super.brickStairBlock, 5);
        }
        for (final int k5 : new int[] { -3, 3 }) {
            for (final int i4 : new int[] { -3, 3 }) {
                for (int j4 = 1; j4 <= 6; ++j4) {
                    this.setBlockAndMetadata(world, i4, j4, k5, super.brickRedBlock, super.brickRedMeta);
                }
            }
            this.setBlockAndMetadata(world, -2, 6, k5, super.brickRedStairBlock, 4);
            this.setBlockAndMetadata(world, 2, 6, k5, super.brickRedStairBlock, 5);
            this.setBlockAndMetadata(world, -3, 7, k5, super.brickRedStairBlock, 1);
            this.setBlockAndMetadata(world, -2, 7, k5, super.brickRedBlock, super.brickRedMeta);
            this.setBlockAndMetadata(world, -1, 7, k5, super.brickRedBlock, super.brickRedMeta);
            this.setBlockAndMetadata(world, 0, 7, k5, super.brickRedStairBlock, (k5 > 0) ? 7 : 6);
            this.setBlockAndMetadata(world, 1, 7, k5, super.brickRedBlock, super.brickRedMeta);
            this.setBlockAndMetadata(world, 2, 7, k5, super.brickRedBlock, super.brickRedMeta);
            this.setBlockAndMetadata(world, 3, 7, k5, super.brickRedStairBlock, 0);
        }
        for (int i2 = -2; i2 <= 2; ++i2) {
            for (int k2 = -2; k2 <= 2; ++k2) {
                if (k2 == 0) {
                    this.setBlockAndMetadata(world, i2, 7, k2, super.brickRedBlock, super.brickRedMeta);
                }
                else {
                    this.setBlockAndMetadata(world, i2, 7, k2, super.pillarBlock, super.pillarMeta);
                }
            }
        }
        for (final int k5 : new int[] { -2, 2 }) {
            for (int i5 = -2; i5 <= 2; ++i5) {
                for (int j5 = 1; j5 <= ((Math.abs(i5) <= 1) ? 7 : 6); ++j5) {
                    this.setBlockAndMetadata(world, i5, j5, k5, super.gateBlock, (k5 > 0) ? 2 : 3);
                }
            }
            this.setBlockAndMetadata(world, -1, 8, k5, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, 0, 8, k5, super.brickBlock, super.brickMeta);
            this.setBlockAndMetadata(world, 1, 8, k5, super.fenceBlock, super.fenceMeta);
            this.setBlockAndMetadata(world, 0, 9, k5, Blocks.lever, 14);
        }
        for (int j6 = 7; j6 <= 10; ++j6) {
            this.setBlockAndMetadata(world, -3, j6, -2, super.brickBlock, super.brickMeta);
            this.setBlockAndMetadata(world, 3, j6, -2, super.brickBlock, super.brickMeta);
            this.setBlockAndMetadata(world, -3, j6, 2, super.brickBlock, super.brickMeta);
            this.setBlockAndMetadata(world, 3, j6, 2, super.brickBlock, super.brickMeta);
        }
        for (final int k5 : new int[] { -3, 3 }) {
            this.setBlockAndMetadata(world, -3, 8, k5, super.brickStairBlock, 5);
            this.setBlockAndMetadata(world, -2, 8, k5, super.brickStairBlock, (k5 > 0) ? 3 : 2);
            this.setBlockAndMetadata(world, -1, 8, k5, super.brickBlock, super.brickMeta);
            this.setBlockAndMetadata(world, 0, 8, k5, super.brickStairBlock, (k5 > 0) ? 3 : 2);
            this.setBlockAndMetadata(world, 1, 8, k5, super.brickBlock, super.brickMeta);
            this.setBlockAndMetadata(world, 2, 8, k5, super.brickStairBlock, (k5 > 0) ? 3 : 2);
            this.setBlockAndMetadata(world, 3, 8, k5, super.brickStairBlock, 4);
            this.setBlockAndMetadata(world, -3, 9, k5, super.brickWallBlock, super.brickWallMeta);
            this.setBlockAndMetadata(world, -1, 9, k5, super.brickWallBlock, super.brickWallMeta);
            this.setBlockAndMetadata(world, 1, 9, k5, super.brickWallBlock, super.brickWallMeta);
            this.setBlockAndMetadata(world, 3, 9, k5, super.brickWallBlock, super.brickWallMeta);
            this.setBlockAndMetadata(world, -3, 10, k5, super.brickBlock, super.brickMeta);
            this.setBlockAndMetadata(world, -2, 10, k5, super.brickStairBlock, (k5 > 0) ? 7 : 6);
            this.setBlockAndMetadata(world, -1, 10, k5, super.brickBlock, super.brickMeta);
            this.setBlockAndMetadata(world, 0, 10, k5, super.brickStairBlock, (k5 > 0) ? 7 : 6);
            this.setBlockAndMetadata(world, 1, 10, k5, super.brickBlock, super.brickMeta);
            this.setBlockAndMetadata(world, 2, 10, k5, super.brickStairBlock, (k5 > 0) ? 7 : 6);
            this.setBlockAndMetadata(world, 3, 10, k5, super.brickBlock, super.brickMeta);
            for (int i5 = -3; i5 <= 3; ++i5) {
                this.setBlockAndMetadata(world, i5, 11, k5, super.brickBlock, super.brickMeta);
            }
        }
        for (int k4 = -2; k4 <= 2; ++k4) {
            this.setBlockAndMetadata(world, -3, 11, k4, super.brickBlock, super.brickMeta);
            this.setBlockAndMetadata(world, 3, 11, k4, super.brickBlock, super.brickMeta);
        }
        for (int i2 = -3; i2 <= 3; ++i2) {
            for (int k2 = -3; k2 <= 3; ++k2) {
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                if (i3 + k3 <= 1) {
                    this.setBlockAndMetadata(world, i2, 12, k2, super.brickSlabBlock, super.brickSlabMeta | 0x8);
                }
                else {
                    this.setBlockAndMetadata(world, i2, 12, k2, super.brickBlock, super.brickMeta);
                }
                if (i3 == 2 && k3 == 2) {
                    this.setBlockAndMetadata(world, i2, 11, k2, super.brickSlabBlock, super.brickSlabMeta | 0x8);
                }
            }
        }
        this.setBlockAndMetadata(world, -2, 10, -2, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 2, 10, -2, Blocks.torch, 1);
        this.setBlockAndMetadata(world, -2, 10, 2, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 2, 10, 2, Blocks.torch, 1);
        for (int i2 = -4; i2 <= 4; ++i2) {
            for (int k2 = -4; k2 <= 4; ++k2) {
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                if (i3 == 4 || k3 == 4) {
                    if ((i3 + k3) % 2 == 0) {
                        this.setBlockAndMetadata(world, i2, 13, k2, super.roofSlabBlock, super.roofSlabMeta);
                        if (i3 <= 2 || k3 <= 2) {
                            this.setBlockAndMetadata(world, i2, 12, k2, super.fenceBlock, super.fenceMeta);
                        }
                    }
                    else if (k3 == 4 && i3 == 3) {
                        this.setBlockAndMetadata(world, i2, 12, k2, super.roofStairBlock, (k2 > 0) ? 7 : 6);
                    }
                    else if (i3 == 4 && k3 == 3) {
                        this.setBlockAndMetadata(world, i2, 12, k2, super.roofStairBlock, (i2 > 0) ? 4 : 5);
                    }
                    else {
                        this.setBlockAndMetadata(world, i2, 12, k2, super.roofSlabBlock, super.roofSlabMeta | 0x8);
                    }
                }
            }
        }
        for (int i2 = -3; i2 <= 3; ++i2) {
            this.setBlockAndMetadata(world, i2, 13, -3, super.roofStairBlock, 2);
            this.setBlockAndMetadata(world, i2, 13, 3, super.roofStairBlock, 3);
        }
        for (int k4 = -3; k4 <= 3; ++k4) {
            this.setBlockAndMetadata(world, -3, 13, k4, super.roofStairBlock, 1);
            this.setBlockAndMetadata(world, 3, 13, k4, super.roofStairBlock, 0);
        }
        for (int i2 = -2; i2 <= 2; ++i2) {
            for (int k2 = -2; k2 <= 2; ++k2) {
                final int i3 = Math.abs(i2);
                final int k3 = Math.abs(k2);
                this.setBlockAndMetadata(world, i2, 13, k2, super.roofBlock, super.roofMeta);
                if (i3 == 2 && k3 == 2) {
                    this.setBlockAndMetadata(world, i2, 14, k2, super.roofSlabBlock, super.roofSlabMeta);
                }
                else {
                    this.setBlockAndMetadata(world, i2, 14, k2, super.roofBlock, super.roofMeta);
                }
                if ((i3 == 2 && k3 == 0) || (k3 == 2 && i3 == 0)) {
                    this.setBlockAndMetadata(world, i2, 15, k2, super.roofSlabBlock, super.roofSlabMeta);
                }
                if (i3 <= 1 && k3 <= 1) {
                    this.setBlockAndMetadata(world, i2, 15, k2, super.roofBlock, super.roofMeta);
                }
            }
        }
        this.setBlockAndMetadata(world, 0, 16, 0, super.roofBlock, super.roofMeta);
        this.setBlockAndMetadata(world, 0, 17, 0, super.roofBlock, super.roofMeta);
        this.setBlockAndMetadata(world, 0, 18, 0, super.roofWallBlock, super.roofWallMeta);
        this.setBlockAndMetadata(world, 0, 19, 0, super.roofWallBlock, super.roofWallMeta);
        this.setBlockAndMetadata(world, 0, 16, -1, super.roofStairBlock, 2);
        this.setBlockAndMetadata(world, 0, 16, 1, super.roofStairBlock, 3);
        this.setBlockAndMetadata(world, -1, 16, 0, super.roofStairBlock, 1);
        this.setBlockAndMetadata(world, 1, 16, 0, super.roofStairBlock, 0);
        for (int i2 = -7; i2 <= 7; ++i2) {
            final int i6 = Math.abs(i2);
            if (i6 >= 4 && i6 <= 6) {
                for (final int k6 : new int[] { -2, 2 }) {
                    for (int j7 = 1; j7 <= 8; ++j7) {
                        if (j7 == 1) {
                            this.setBlockAndMetadata(world, i2, j7, k6, super.brickRedBlock, super.brickRedMeta);
                        }
                        else {
                            this.setBlockAndMetadata(world, i2, j7, k6, super.brickBlock, super.brickMeta);
                        }
                        if (j7 == 3 && i6 == 5) {
                            this.setBlockAndMetadata(world, i2, j7, k6, super.brickCarvedBlock, super.brickCarvedMeta);
                        }
                    }
                    this.setBlockAndMetadata(world, i2, 9, k6, super.brickWallBlock, super.brickWallMeta);
                }
            }
            if (i6 >= 4 && i6 <= 7) {
                for (int k7 = -1; k7 <= 2; ++k7) {
                    for (int j8 = 1; j8 <= 5; ++j8) {
                        if (k7 == 0 || j8 == 1) {
                            this.setBlockAndMetadata(world, i2, j8, k7, super.brickRedBlock, super.brickRedMeta);
                        }
                        else {
                            this.setBlockAndMetadata(world, i2, j8, k7, super.brickBlock, super.brickMeta);
                        }
                    }
                }
            }
        }
        for (int step = 0; step <= 1; ++step) {
            final int j9 = 6 + step;
            for (int k7 = -1; k7 <= 1; ++k7) {
                this.setBlockAndMetadata(world, -4 + step, j9, k7, (k7 == 0) ? super.brickRedStairBlock : super.brickStairBlock, 1);
                this.setBlockAndMetadata(world, 4 - step, j9, k7, (k7 == 0) ? super.brickRedStairBlock : super.brickStairBlock, 0);
            }
        }
        this.setBlockAndMetadata(world, -7, 5, -2, super.brickStairBlock, 5);
        this.setBlockAndMetadata(world, -7, 6, -2, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, -7, 7, -2, super.brickStairBlock, 3);
        this.setBlockAndMetadata(world, 7, 5, -2, super.brickStairBlock, 4);
        this.setBlockAndMetadata(world, 7, 6, -2, super.brickBlock, super.brickMeta);
        this.setBlockAndMetadata(world, 7, 7, -2, super.brickStairBlock, 3);
        this.placeWallBanner(world, -5, 6, -2, super.bannerType, 2);
        this.placeWallBanner(world, 5, 6, -2, super.bannerType, 2);
        this.placeWallBanner(world, -5, 6, 2, super.bannerType, 0);
        this.placeWallBanner(world, 5, 6, 2, super.bannerType, 0);
        if (this.enableSigns && this.signText != null) {
            this.placeSign(world, -3, 3, -4, Blocks.wall_sign, 2, this.signText);
            this.placeSign(world, 3, 3, -4, Blocks.wall_sign, 2, this.signText);
        }
        final int maxStep = 12;
        for (int k2 = 2; k2 <= 2; ++k2) {
            for (int step2 = 0; step2 < maxStep; ++step2) {
                final int i7 = -8 - step2;
                final int j3 = 5 - step2;
                if (this.isOpaque(world, i7, j3, k2)) {
                    break;
                }
                this.setBlockAndMetadata(world, i7, j3, k2, super.brickStairBlock, 1);
                this.setGrassToDirt(world, i7, j3 - 1, k2);
                for (int j10 = j3 - 1; !this.isOpaque(world, i7, j10, k2) && this.getY(j10) >= 0; --j10) {
                    this.setBlockAndMetadata(world, i7, j10, k2, super.brickBlock, super.brickMeta);
                    this.setGrassToDirt(world, i7, j10 - 1, k2);
                }
            }
            for (int step2 = 0; step2 < maxStep; ++step2) {
                final int i7 = 8 + step2;
                final int j3 = 5 - step2;
                if (this.isOpaque(world, i7, j3, k2)) {
                    break;
                }
                this.setBlockAndMetadata(world, i7, j3, k2, super.brickStairBlock, 0);
                this.setGrassToDirt(world, i7, j3 - 1, k2);
                for (int j10 = j3 - 1; !this.isOpaque(world, i7, j10, k2) && this.getY(j10) >= 0; --j10) {
                    this.setBlockAndMetadata(world, i7, j10, k2, super.brickBlock, super.brickMeta);
                    this.setGrassToDirt(world, i7, j10 - 1, k2);
                }
            }
        }
        for (int men = 2, l = 0; l < men; ++l) {
            final int i7 = 0;
            final int j3 = 8;
            final int k6 = 0;
            final LOTREntityNPC guard = new LOTREntityEasterlingWarrior(world);
            guard.spawnRidingHorse = false;
            this.spawnNPCAndSetHome(guard, world, i7, j3, k6, 8);
        }
        return true;
    }
}
