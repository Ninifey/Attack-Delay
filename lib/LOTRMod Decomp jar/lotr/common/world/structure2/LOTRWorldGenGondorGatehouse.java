// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.world.structure2;

import net.minecraft.entity.EntityCreature;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.init.Blocks;
import lotr.common.LOTRMod;
import net.minecraft.world.World;
import lotr.common.entity.npc.LOTRNames;
import java.util.Random;

public class LOTRWorldGenGondorGatehouse extends LOTRWorldGenGondorStructure
{
    private String[] signText;
    
    public LOTRWorldGenGondorGatehouse(final boolean flag) {
        super(flag);
        this.signText = LOTRNames.getGondorVillageName(new Random());
    }
    
    public LOTRWorldGenGondorGatehouse setSignText(final String[] s) {
        this.signText = s;
        return this;
    }
    
    @Override
    public boolean generateWithSetRotation(final World world, final Random random, final int i, final int j, final int k, final int rotation) {
        this.setOriginAndRotation(world, i, j, k, rotation, 4);
        this.setupRandomBlocks(random);
        if (super.restrictions) {
            int minHeight = 0;
            int maxHeight = 0;
            for (int i2 = -6; i2 <= 6; ++i2) {
                for (int k2 = -5; k2 <= 5; ++k2) {
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
        for (int i3 = -3; i3 <= 3; ++i3) {
            for (int k3 = -3; k3 <= 3; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                for (int j2 = 0; (j2 >= 0 || !this.isOpaque(world, i3, j2, k3)) && this.getY(j2) >= 0; --j2) {
                    this.setBlockAndMetadata(world, i3, j2, k3, super.cobbleBlock, super.cobbleMeta);
                    this.setGrassToDirt(world, i3, j2 - 1, k3);
                }
                for (int j2 = 1; j2 <= 14; ++j2) {
                    this.setAir(world, i3, j2, k3);
                }
                if (i4 == 3 && k4 == 3) {
                    for (int j2 = 1; j2 <= 10; ++j2) {
                        this.setBlockAndMetadata(world, i3, j2, k3, super.pillarBlock, super.pillarMeta);
                    }
                }
                else {
                    if (i4 == 3) {
                        for (int j2 = 1; j2 <= 6; ++j2) {
                            this.setBlockAndMetadata(world, i3, j2, k3, super.brickBlock, super.brickMeta);
                        }
                    }
                    this.setBlockAndMetadata(world, i3, 7, k3, super.brickBlock, super.brickMeta);
                }
                if (i4 <= 3 && k4 <= 3) {
                    if (i4 == 3 || k4 == 3) {
                        this.setBlockAndMetadata(world, i3, 11, k3, super.rockSlabDoubleBlock, super.rockSlabDoubleMeta);
                    }
                    else {
                        this.setBlockAndMetadata(world, i3, 11, k3, super.rockSlabBlock, super.rockSlabMeta | 0x8);
                    }
                }
            }
        }
        for (int i3 = -2; i3 <= 2; ++i3) {
            this.setBlockAndMetadata(world, i3, 0, -1, super.cobbleStairBlock, 3);
            this.setBlockAndMetadata(world, i3, 0, 1, super.cobbleStairBlock, 2);
            this.setBlockAndMetadata(world, i3, -1, 0, super.cobbleBlock, super.cobbleMeta);
            this.setGrassToDirt(world, i3, -2, 0);
            this.setAir(world, i3, 0, 0);
        }
        for (int i3 = -2; i3 <= 2; ++i3) {
            for (int j3 = 1; j3 <= 7; ++j3) {
                if (j3 <= 6 || i3 == 0) {
                    this.setBlockAndMetadata(world, i3, j3, -1, super.gateBlock, 10);
                    this.setBlockAndMetadata(world, i3, j3, 1, LOTRMod.gateIronBars, 10);
                }
            }
        }
        for (final int k2 : new int[] { -3, 3 }) {
            this.setBlockAndMetadata(world, -2, 6, k2, super.brickStairBlock, 4);
            this.setBlockAndMetadata(world, 2, 6, k2, super.brickStairBlock, 5);
            this.setBlockAndMetadata(world, -2, 5, k2, Blocks.torch, 2);
            this.setBlockAndMetadata(world, 2, 5, k2, Blocks.torch, 1);
            for (int i5 = -2; i5 <= 2; ++i5) {
                final int i6 = Math.abs(i5);
                this.setBlockAndMetadata(world, i5, 8, k2, super.rockSlabDoubleBlock, super.rockSlabDoubleMeta);
                if (i6 % 2 == 0) {
                    this.setBlockAndMetadata(world, i5, 9, k2, LOTRMod.gateIronBars, 2);
                }
                else {
                    this.setBlockAndMetadata(world, i5, 9, k2, super.brickBlock, super.brickMeta);
                }
                if (i6 == 0) {
                    this.setBlockAndMetadata(world, i5, 10, k2, LOTRMod.brick, 5);
                }
                else {
                    this.setBlockAndMetadata(world, i5, 10, k2, super.brickBlock, super.brickMeta);
                }
            }
        }
        for (final int i7 : new int[] { -3, 3 }) {
            for (final int k5 : new int[] { -2, 2 }) {
                this.setBlockAndMetadata(world, i7, 8, k5, super.rockSlabDoubleBlock, super.rockSlabDoubleMeta);
                this.setBlockAndMetadata(world, i7, 9, k5, super.brickBlock, super.brickMeta);
                this.setBlockAndMetadata(world, i7, 10, k5, super.brickBlock, super.brickMeta);
            }
            this.setBlockAndMetadata(world, i7, 10, -1, super.brickStairBlock, 7);
            this.setBlockAndMetadata(world, i7, 10, 1, super.brickStairBlock, 6);
        }
        for (int i3 = -3; i3 <= 3; ++i3) {
            this.setBlockAndMetadata(world, i3, 11, -4, super.brickStairBlock, 6);
            this.setBlockAndMetadata(world, i3, 11, 4, super.brickStairBlock, 7);
        }
        for (int k6 = -3; k6 <= 3; ++k6) {
            this.setBlockAndMetadata(world, -4, 11, k6, super.brickStairBlock, 5);
            this.setBlockAndMetadata(world, 4, 11, k6, super.brickStairBlock, 4);
        }
        for (int i3 = -4; i3 <= 4; ++i3) {
            for (int k3 = -4; k3 <= 4; ++k3) {
                final int i4 = Math.abs(i3);
                final int k4 = Math.abs(k3);
                if ((i4 <= 3 || k4 <= 3) && (i4 == 4 || k4 == 4)) {
                    if ((i4 + k4) % 2 == 1) {
                        this.setBlockAndMetadata(world, i3, 12, k3, super.brickBlock, super.brickMeta);
                        this.setBlockAndMetadata(world, i3, 13, k3, super.brickSlabBlock, super.brickSlabMeta);
                    }
                    else {
                        this.setBlockAndMetadata(world, i3, 12, k3, super.brickWallBlock, super.brickWallMeta);
                    }
                }
            }
        }
        this.setBlockAndMetadata(world, 0, 8, -1, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, 0, 8, 0, super.fenceBlock, super.fenceMeta);
        this.setAir(world, 0, 7, 0);
        this.setBlockAndMetadata(world, 0, 8, 1, super.plankBlock, super.plankMeta);
        this.setBlockAndMetadata(world, 0, 9, -1, Blocks.lever, 14);
        this.setBlockAndMetadata(world, 0, 9, 1, Blocks.lever, 14);
        for (final int i7 : new int[] { -1, 1 }) {
            for (int j2 = 8; j2 <= 11; ++j2) {
                this.setBlockAndMetadata(world, i7, j2, 2, Blocks.ladder, 2);
            }
        }
        this.setBlockAndMetadata(world, -2, 10, -2, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 2, 10, -2, Blocks.torch, 1);
        this.setBlockAndMetadata(world, -2, 10, 2, Blocks.torch, 2);
        this.setBlockAndMetadata(world, 2, 10, 2, Blocks.torch, 1);
        this.placeWallBanner(world, 1, 10, -3, super.bannerType2, 0);
        this.placeWallBanner(world, -1, 10, -3, super.bannerType, 0);
        this.setBlockAndMetadata(world, -3, 12, -3, Blocks.torch, 3);
        this.setBlockAndMetadata(world, 3, 12, -3, Blocks.torch, 3);
        this.setBlockAndMetadata(world, -3, 12, 3, Blocks.torch, 4);
        this.setBlockAndMetadata(world, 3, 12, 3, Blocks.torch, 4);
        this.placeWallBanner(world, -3, 7, -3, super.bannerType2, 2);
        this.placeWallBanner(world, 3, 7, -3, super.bannerType, 2);
        this.placeWallBanner(world, 3, 7, 3, super.bannerType2, 0);
        this.placeWallBanner(world, -3, 7, 3, super.bannerType, 0);
        if (this.signText != null) {
            this.placeSign(world, -3, 3, -4, Blocks.wall_sign, 2, this.signText);
            this.placeSign(world, 3, 3, -4, Blocks.wall_sign, 2, this.signText);
        }
        for (int i3 = -5; i3 <= 5; ++i3) {
            final int i8 = Math.abs(i3);
            if (i8 >= 4) {
                for (int k7 = -1; k7 <= 1; ++k7) {
                    for (int j4 = 4; (j4 >= 0 || !this.isOpaque(world, i3, j4, k7)) && this.getY(j4) >= 0; --j4) {
                        this.setBlockAndMetadata(world, i3, j4, k7, super.brickBlock, super.brickMeta);
                        this.setGrassToDirt(world, i3, j4 - 1, k7);
                    }
                }
                int k7 = -2;
                for (int j4 = 7; (j4 >= 0 || !this.isOpaque(world, i3, j4, k7)) && this.getY(j4) >= 0; --j4) {
                    this.setBlockAndMetadata(world, i3, j4, k7, super.brickBlock, super.brickMeta);
                    this.setGrassToDirt(world, i3, j4 - 1, k7);
                }
                this.setBlockAndMetadata(world, i3, 4, k7, super.brick2Block, super.brick2Meta);
                this.setBlockAndMetadata(world, i3, 8, k7, super.rockWallBlock, super.rockWallMeta);
            }
            if (i8 == 5) {
                final int k7 = -3;
                for (int j4 = 7; (j4 >= 0 || !this.isOpaque(world, i3, j4, k7)) && this.getY(j4) >= 0; --j4) {
                    this.setBlockAndMetadata(world, i3, j4, k7, super.pillarBlock, super.pillarMeta);
                    this.setGrassToDirt(world, i3, j4 - 1, k7);
                }
                this.setBlockAndMetadata(world, i3, 8, k7, super.rockWallBlock, super.rockWallMeta);
            }
            if (i8 == 4) {
                final int k7 = -3;
                this.setBlockAndMetadata(world, i3, 5, k7, super.brickStairBlock, 6);
                this.setBlockAndMetadata(world, i3, 6, k7, super.rockWallBlock, super.rockWallMeta);
            }
        }
        for (int k6 = -1; k6 <= 1; ++k6) {
            this.setBlockAndMetadata(world, -3, 7, k6, super.brickStairBlock, 1);
            this.setBlockAndMetadata(world, -4, 6, k6, super.brickStairBlock, 1);
            this.setBlockAndMetadata(world, -5, 5, k6, super.brickStairBlock, 1);
            this.setBlockAndMetadata(world, -4, 5, k6, super.brickBlock, super.brickMeta);
            this.setBlockAndMetadata(world, 3, 7, k6, super.brickStairBlock, 0);
            this.setBlockAndMetadata(world, 4, 6, k6, super.brickStairBlock, 0);
            this.setBlockAndMetadata(world, 5, 5, k6, super.brickStairBlock, 0);
            this.setBlockAndMetadata(world, 4, 5, k6, super.brickBlock, super.brickMeta);
        }
        for (int i3 = -8; i3 <= 8; ++i3) {
            final int i8 = Math.abs(i3);
            if (i8 >= 6) {
                for (int k7 = 0; k7 <= 1; ++k7) {
                    for (int j4 = 4; (j4 >= 0 || !this.isOpaque(world, i3, j4, k7)) && this.getY(j4) >= 0; --j4) {
                        this.setBlockAndMetadata(world, i3, j4, k7, super.brickBlock, super.brickMeta);
                        this.setGrassToDirt(world, i3, j4 - 1, k7);
                    }
                }
            }
        }
        for (int k6 = 0; k6 <= 1; ++k6) {
            final int maxStep = 12;
            for (int step = 0; step < maxStep; ++step) {
                final int i7 = -9 - step;
                final int j2 = 4 - step;
                if (this.isOpaque(world, i7, j2, k6)) {
                    break;
                }
                this.setBlockAndMetadata(world, i7, j2, k6, super.brickStairBlock, 1);
                this.setGrassToDirt(world, i7, j2 - 1, k6);
                for (int j5 = j2 - 1; !this.isOpaque(world, i7, j5, k6) && this.getY(j5) >= 0; --j5) {
                    this.setBlockAndMetadata(world, i7, j5, k6, super.brickBlock, super.brickMeta);
                    this.setGrassToDirt(world, i7, j5 - 1, k6);
                }
            }
            for (int step = 0; step < maxStep; ++step) {
                final int i7 = 9 + step;
                final int j2 = 4 - step;
                if (this.isOpaque(world, i7, j2, k6)) {
                    break;
                }
                this.setBlockAndMetadata(world, i7, j2, k6, super.brickStairBlock, 0);
                this.setGrassToDirt(world, i7, j2 - 1, k6);
                for (int j5 = j2 - 1; !this.isOpaque(world, i7, j5, k6) && this.getY(j5) >= 0; --j5) {
                    this.setBlockAndMetadata(world, i7, j5, k6, super.brickBlock, super.brickMeta);
                    this.setGrassToDirt(world, i7, j5 - 1, k6);
                }
            }
        }
        for (int i3 = -9; i3 <= 9; ++i3) {
            final int i8 = Math.abs(i3);
            if (i8 == 5 || i8 == 8) {
                this.setBlockAndMetadata(world, i3, 3, 1, LOTRMod.brick, 5);
            }
            else if (i8 >= 4) {
                this.setBlockAndMetadata(world, i3, 3, 1, super.brickStairBlock, 7);
            }
        }
        final int[] array5 = { -1, 1 };
        for (int length5 = array5.length, n4 = 0; n4 < length5; ++n4) {
            final int i7 = array5[n4];
            final int j2 = 8;
            final int k8 = 0;
            final LOTREntityNPC levyman = (LOTREntityNPC)LOTREntities.createEntityByClass(super.strFief.getLevyClasses()[0], world);
            this.spawnNPCAndSetHome(levyman, world, i7, j2, k8, 8);
        }
        return true;
    }
}
